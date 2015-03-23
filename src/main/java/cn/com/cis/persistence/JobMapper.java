package cn.com.cis.persistence;

import cn.com.cis.domain.JobEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobMapper {

    void insertJobEntity(JobEntity jobEntity);

    void updateJobEntity(JobEntity jobEntity);

    void deleteJobEntity(int id);

    void disableJobEntity(int id);

    void enableJobEntity(int id);
    int selectAllJobByDatabaseId(int id);
    List<JobEntity> selectJobEntityByLayer(@Param(value = "layerId") int layerId,
                                           @Param(value = "enable") boolean enable);

    JobEntity selectJobEntityById(int id);

    List<JobEntity> selectAllJobEntity(@Param(value = "enable") boolean enable);

    JobEntity selectJobEntityByLayerAndSequence(int layerId, int sequence);

    JobEntity selectJobEntityByJobName(String jobName);
}
