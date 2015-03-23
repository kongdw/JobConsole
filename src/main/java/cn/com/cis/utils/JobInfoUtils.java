package cn.com.cis.utils;

import cn.com.cis.domain.JobEntity;
import cn.com.cis.domain.JobInfo;
import cn.com.cis.domain.Parameter;
import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.enums.Model;
import cn.com.cis.enums.SupportDataType;
import cn.com.cis.enums.Type;
import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import org.apache.ibatis.type.JdbcType;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobInfoUtils {

    public static JobInfo getJobInfo(JobEntity jobEntity, HashMap<String, Parameter> params) throws StandardException, ParseException {
        SQLParser parser = new SQLParser();
        JobInfo jobInfo = new JobInfo();
        List<String> paramNames = SqlScriptUtil.getSqlParameter(jobEntity.getSqlScript());
        jobInfo.setSqlScript(SqlScriptUtil.replaceSql(jobEntity.getSqlScript()));
        StatementNode stmt = parser.parseStatement(jobInfo.getSqlScript());
        jobInfo.setModel(Model.valueOf(jobEntity.getMode().getCode()));
        jobInfo.setJobId(jobEntity.getId());
        jobInfo.setJobName(jobEntity.getName());
        jobInfo.setSourceDatabase(jobEntity.getSourceDatabase());
        jobInfo.setTargetDatabase(jobEntity.getTargetDatabase());
        jobInfo.setSplitVal(jobEntity.getSplitValue());
        if("i".equals(jobEntity.getSqlType()) || "u".equals(jobEntity.getSqlType())||"d".equals(jobEntity.getSqlType())){
            jobInfo.setType(Type.valueOf(stmt.statementToString()));
        }else if("c".equals(jobEntity.getSqlType())){
            jobInfo.setType(Type.CALL);
        }
        jobInfo.setTargetTable(jobEntity.getTargetTable());
        jobInfo.setTruncateFlag(jobEntity.isTruncateFlag());
        // 取得参数的索引值
        List<ParameterInfo> parameterInfoList = new ArrayList<ParameterInfo>();
        for (int i = 0; i < paramNames.size(); i++) {
            ParameterInfo parameterInfo = new ParameterInfo();
            parameterInfo.setParamIndex(i+1);
            parameterInfo.setParamName(paramNames.get(i));
            Parameter parameter = params.get(paramNames.get(i));
            parameterInfo.setParamType(JdbcType.forCode(parameter.getParameterType().getJdbcType()));
            if("GLOBAL".equals(parameter.getScope())){
                if(parameter.getParameterRef().getReturnType().getCode().equals(SupportDataType.DATE.toString())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    parameterInfo.setParamVal(sdf.parse(parameter.getParameterRef().getDefaultVal()));
                }else if(parameter.getParameterRef().getReturnType().getCode().equals(SupportDataType.NUMBER.toString())) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                    parameterInfo.setParamVal(numberFormat.parse(parameter.getParameterRef().getDefaultVal()).intValue());
                }else {
                    parameterInfo.setParamVal(parameter.getParameterRef().getDefaultVal());
                }
            }else {
                if(parameter.getParameterType().getCode().equals(SupportDataType.DATE.toString())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    parameterInfo.setParamVal(sdf.parse((String)parameter.getParameterValue()));
                }else if(parameter.getParameterType().getCode().equals(SupportDataType.NUMBER.toString())) {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                    parameterInfo.setParamVal(numberFormat.parse((String)parameter.getParameterValue()).intValue());
                }else {
                    parameterInfo.setParamVal(parameter.getParameterValue());
                }
            }
            parameterInfoList.add(parameterInfo);
        }
        jobInfo.setParameterInfoList(parameterInfoList);
//        HashMap<String, Integer> paramIndex = new HashMap<String, Integer>();
//        SqlScriptUtil.getSqlParameter(paramIndex, jobEntity.getSqlScript(), 1);
//        if (null != params && params.size() > 0) {
//            Map<String, ParameterInfo> parameterInfoMap = new HashMap<String, ParameterInfo>();
//            Iterator<Map.Entry<String, Parameter>> iterator = params.entrySet().iterator();
//            while (iterator.hasNext()) {
//
//                Map.Entry<String, Parameter> entry = iterator.next();
//                // 替换索引值
//                ParameterInfo parameterInfo = new ParameterInfo();
//                if (paramIndex.containsKey(entry.getValue().getParameterName())) {
//                    parameterInfo.setParamIndex(paramIndex.get(entry.getKey()));
//                }
//
//                parameterInfo.setParamType(JdbcType.forCode(entry.getValue().getParameterType().getJdbcType()));
//                if (entry.getValue().getScope().equals("GLOBAL")) {
//                    if (entry.getValue().getParameterRef().getReturnType().getCode().equals(SupportDataType.DATE.toString())) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        parameterInfo.setParamVal(sdf.parse(entry.getValue().getParameterRef().getDefaultVal()));
//                    } else {
//                        parameterInfo.setParamVal(entry.getValue().getParameterRef().getDefaultVal());
//                    }
//
//                } else {
//                    parameterInfo.setParamVal(entry.getValue().getParameterValue());
//                }
//                parameterInfo.setParamName(entry.getValue().getParameterName());
//                parameterInfoMap.put(entry.getKey(), parameterInfo);
//            }
//            jobInfo.setParameterEntityMap(parameterInfoMap);
//        }
        return jobInfo;
    }

}
