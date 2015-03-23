package cn.com.cis.service;

import cn.com.cis.domain.*;
import cn.com.cis.job.ETLServer;
import cn.com.cis.utils.JobInfoUtils;
import com.foundationdb.sql.StandardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;

@Service
@Transactional
public class ExecutorService {

    @Autowired
    private JobEntityService jobEntityService;

    @Autowired
    private ETLServer etlServer;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private LayerService layerService;

    @Transactional
    public void execute(JobEntity jobEntity, User user) throws StandardException, ParseException {
        BlockingDeque<JobInfo> deque = etlServer.getBlockingDeque();
        HashMap<String, Parameter> parameterHashMap = parameterService.getParameterByJobId(jobEntity.getId());
        JobInfo jobInfo = JobInfoUtils.getJobInfo(jobEntity, parameterHashMap);
        jobInfo.setSubmitUser(user.getUsername());
        deque.add(jobInfo);
    }

    public void execute(Layer layer, User user) throws StandardException, ParseException {
        List<JobEntity> jobEntityList = jobEntityService.selectJobEntityByLayer(layer.getId(),true);
        for (JobEntity jobEntity : jobEntityList) {
            execute(jobEntity, user);
        }
    }

    public void execute(User user) throws StandardException, ParseException {
        List<Layer> layerList = layerService.selectAllLayer();
        for (Layer layer : layerList) {
            execute(layer, user);
        }
    }

}
