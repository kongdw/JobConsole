package cn.com.cis.service;

import cn.com.cis.domain.JobEntity;
import cn.com.cis.persistence.JobMapper;
import cn.com.cis.plugins.mybatis.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobEntityService {

    @Autowired
    private JobMapper mapper;
    @Autowired
    private ParameterService parameterService;

    public void insertJobEntity(JobEntity jobEntity) {
        mapper.insertJobEntity(jobEntity);
    }

    public void updateJobEntity(JobEntity jobEntity) {
        mapper.updateJobEntity(jobEntity);
    }

    public void deleteJobEntity(int id) {
        parameterService.deleteParameterByJobId(id);
        mapper.deleteJobEntity(id);
    }

    public void disableJobEntity(int id) {
        mapper.disableJobEntity(id);
    }

    @Transactional(readOnly = true)
    public List<JobEntity> selectJobEntityByLayer(int layerId,boolean enable) {
        return mapper.selectJobEntityByLayer(layerId,enable);
    }

    @Transactional(readOnly = true)
    public JobEntity selectJobEntityById(int id) {
        return mapper.selectJobEntityById(id);
    }

    @Transactional(readOnly = true)
    public List<JobEntity> selectAllJobEntity(int page,int pageSize,boolean enable) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectAllJobEntity(enable);
    }

    @Transactional(readOnly = true)
    public List<JobEntity> selectAllJobEntityForMain(boolean enable) {
        return mapper.selectAllJobEntity(enable);
    }

    public JobEntity selectJobEntityByLayerAndSequence(int layerId,int sequence){
        return mapper.selectJobEntityByLayerAndSequence(layerId,sequence);
    }

    public JobEntity selectJobEntityByJobName(String jobName){
        return mapper.selectJobEntityByJobName(jobName);
    }
    public void enableJobEntity(int id){
        mapper.enableJobEntity(id);
    }
    public int selectAllJobByDatabaseId(int id){
        return mapper.selectAllJobByDatabaseId(id);
    }
}
