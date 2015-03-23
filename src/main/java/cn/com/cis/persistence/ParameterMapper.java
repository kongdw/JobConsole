package cn.com.cis.persistence;


import cn.com.cis.domain.Parameter;
import cn.com.cis.domain.ParameterRef;
import cn.com.cis.domain.ParameterType;

import java.util.List;

public interface ParameterMapper {

    void insertParameter(Parameter parameter);

    void deleteParameter(int id);

    void updateParameter(Parameter parameter);

    int UpdateParamByIDAjax(ParameterRef parameterRef);

    void updateParameterRef(ParameterRef parameterRef);

    List<Parameter> selectParameterByJobId(int jobId);

    Parameter selectParameterById(int id);

    List<ParameterType> selectAllParameterType();

    void deleteParameterByJobId(int jobId);

    void insertParameterRef(ParameterRef ref);

    ParameterType selectParameterTypeById(String code);

    List<ParameterRef> selectAllParameterRef();

    ParameterRef selectParameterRefByName(String name);

    ParameterRef selectParameterRefById(int id);

    List<Parameter> selectParameterByRefId(int id);

    void deleteParameterRef(int id);


}
