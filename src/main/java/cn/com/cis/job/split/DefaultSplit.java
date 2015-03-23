package cn.com.cis.job.split;


import cn.com.cis.domain.JobInfo;
import cn.com.cis.task.entity.TaskInfo;
import cn.com.cis.utils.DataSourceFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultSplit implements Splittable {

    private JobInfo jobEntity;

    public DefaultSplit(JobInfo jobEntity) {
        this.jobEntity = jobEntity;
    }

    @Override
    public List<TaskInfo> split() {
        List<TaskInfo> taskEntityList = new ArrayList<TaskInfo>();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskName(jobEntity.getJobName() + "-NonParallelTask");
        taskInfo.setSource(DataSourceFactory.getDataSource(jobEntity.getSourceDatabase()));
        taskInfo.setTarget(DataSourceFactory.getDataSource(jobEntity.getTargetDatabase()));
        taskInfo.setSqlScript(jobEntity.getSqlScript());
        taskInfo.setType(jobEntity.getType());
        taskInfo.setTargetTable(jobEntity.getTargetTable());
        taskInfo.setParameterInfoList(jobEntity.getParameterInfoList());
        taskEntityList.add(taskInfo);
        return taskEntityList;
    }
}
