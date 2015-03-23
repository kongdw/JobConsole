package cn.com.cis.service;


import cn.com.cis.domain.Parameter;
import cn.com.cis.domain.ParameterRef;
import cn.com.cis.domain.ParameterType;
import cn.com.cis.persistence.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ParameterService {

    @Autowired
    private ParameterMapper mapper;

    @Transactional
    public void insertParameter(Parameter parameter) {
        mapper.insertParameter(parameter);
    }

    @Transactional
    public void deleteParameter(int id) {
        mapper.deleteParameter(id);
    }

    @Transactional
    public void updateParameter(Parameter parameter) {
        mapper.updateParameter(parameter);
    }

    @Transactional
    public void updateParameterRef(ParameterRef parameterRef){
        mapper.updateParameterRef(parameterRef);
    }

    @Transactional(readOnly = true)
    public List<Parameter> selectParameterByJobId(int jobId) {
        return mapper.selectParameterByJobId(jobId);
    }

    @Transactional(readOnly = true)
    public Parameter selectParameterById(int id) {
        return mapper.selectParameterById(id);
    }

    @Transactional(readOnly = true)
    public List<ParameterType> selectAllParameterType() {
        return mapper.selectAllParameterType();
    }

    public void deleteParameterByJobId(int jobId) {
        mapper.deleteParameterByJobId(jobId);
    }

    public void insertParameterRef(ParameterRef ref) {
        mapper.insertParameterRef(ref);
    }

    public ParameterType selectParameterTypeById(String code) {
        return mapper.selectParameterTypeById(code);
    }

    public List<ParameterRef> selectAllParameterRef() {
        return mapper.selectAllParameterRef();
    }

    public ParameterRef selectParameterRefByName(String name) {
        return mapper.selectParameterRefByName(name);
    }

    public ParameterRef selectParameterRefById(int id){
        return mapper.selectParameterRefById(id);
    }

    public void insertParameterList(int jobId, List<Parameter> list) {
        mapper.deleteParameterByJobId(jobId);
        for (Parameter parameter : list) {
            mapper.insertParameter(parameter);
        }
    }

    public HashMap<String, Parameter> getParameterByJobId(int jobId) {
        List<Parameter> list = this.selectParameterByJobId(jobId);
        HashMap<String, Parameter> hashMap = new HashMap<String, Parameter>();
        for (Parameter parameter : list) {
            hashMap.put(parameter.getParameterName(), parameter);
        }
        return hashMap;
    }

    public List<Parameter> selectParameterByRefId(int id) {
       return mapper.selectParameterByRefId(id);
    }

    public int UpdateParamByIDAjax(ParameterRef parameterRef){return mapper.UpdateParamByIDAjax(parameterRef);}

    public void deleteParameterRef(int id) {
        mapper.deleteParameterRef(id);
    }
}
