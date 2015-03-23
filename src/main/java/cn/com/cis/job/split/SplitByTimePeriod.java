package cn.com.cis.job.split;

import cn.com.cis.domain.JobInfo;
import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.task.entity.TaskInfo;
import cn.com.cis.utils.DataSourceFactory;
import cn.com.cis.utils.DateUtil;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * 按时间段分割作业并返回taskInfo集合
 */
public class SplitByTimePeriod implements Splittable {

    private static final String BEGIN_DATE = "BEGIN_DATE";
    private static final String END_DATE = "END_DATE";
    private JobInfo jobInfo;
    private List<Map<String, Date>> params = new ArrayList<Map<String, Date>>();

    public SplitByTimePeriod(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    @Override
    public List<TaskInfo> split() throws Exception {
        jobInfo.getParameterInfoList();
        List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
        Date beginDate = getParamDate(BEGIN_DATE);
        Date endDate =getParamDate(END_DATE);
        getSplitParameter(beginDate, endDate);
        int i = 0;
        for (Map<String, Date> param : params) {
            i++;
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskName(jobInfo.getJobName() + "-ParallelTask" + i);
            taskInfo.setSource(DataSourceFactory.getDataSource(jobInfo.getSourceDatabase()));
            taskInfo.setTarget(DataSourceFactory.getDataSource(jobInfo.getTargetDatabase()));
            taskInfo.setSqlScript(jobInfo.getSqlScript());
            taskInfo.setType(jobInfo.getType());
            taskInfo.setTargetTable(jobInfo.getTargetTable());
            // 替换参数
            List<ParameterInfo> jobParams = jobInfo.getParameterInfoList();
            List<ParameterInfo> taskParams = new ArrayList<ParameterInfo>();
            Iterator<ParameterInfo> iterator = jobParams.iterator();
            while (iterator.hasNext()) {
                ParameterInfo parameterInfo = iterator.next();
                if (BEGIN_DATE.equals(parameterInfo.getParamName())) {
                    ParameterInfo taskBeginParam = new ParameterInfo();
                    BeanUtils.copyProperties(parameterInfo, taskBeginParam);
                    taskBeginParam.setParamVal(new Date(param.get(BEGIN_DATE).getTime()));
                    taskParams.add(taskBeginParam);
                } else if (END_DATE.equals(parameterInfo.getParamName())) {
                    ParameterInfo taskEndParam = new ParameterInfo();
                    BeanUtils.copyProperties(parameterInfo, taskEndParam);
                    taskEndParam.setParamVal(new Date(param.get(END_DATE).getTime()));
                    taskParams.add(taskEndParam);
                } else {
                    taskParams.add(parameterInfo);
                }
            }
            taskInfo.setParameterInfoList(taskParams);
            taskInfoList.add(taskInfo);
        }
        return taskInfoList;
    }

    private Date getParamDate(String s) {
        List<ParameterInfo> parameterInfos = jobInfo.getParameterInfoList();
        for (ParameterInfo p : parameterInfos) {
            if (s.equals(p.getParamName())) {
                return new Date(((Date) p.getParamVal()).getTime());
            }
        }
        return null;
    }


    private void getSplitParameter(Date beginDate, Date endDate) {
        long days = DateUtil.getDaysBetween(beginDate, endDate);
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        long currentTime;
        Map<String, Date> p;
        if (days > jobInfo.getSplitVal()) {
            int splitPoint = (int) days / 2;
            currentTime = beginTime + ONE_DAY * splitPoint;
            getSplitParameter(new Date(beginTime), new Date(currentTime));
            getSplitParameter(new Date(currentTime), new Date(endTime));
        } else {
            p = new HashMap<String, Date>();
            p.put(BEGIN_DATE, new Date(beginTime));
            p.put(END_DATE, new Date(endTime));
            params.add(p);
        }
    }
}
