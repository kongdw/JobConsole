package cn.com.cis.task;

import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.task.entity.TaskInfo;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 执行oracle存储过程
 */
public class CallableTask extends AbstractTask<Long> {
    private TaskInfo taskInfo;
    private Connection target;
    public static final int MAX_INIT_NUM = 3;

    private Logger logger = LoggerFactory.getLogger(CallableTask.class);

    public CallableTask(TaskInfo taskInfo) {
        super(taskInfo);
        this.taskInfo = taskInfo;
    }

    @Override
    public Long execute() throws TaskException {
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        TypeHandler typeHandler;
        try {
            CallableStatement call = target.prepareCall(taskInfo.getSqlScript());
            for (ParameterInfo p : taskInfo.getParameterInfoList()) {
                typeHandler = typeHandlerRegistry.getTypeHandler(p.getParamType());
                typeHandler.setParameter(call, p.getParamIndex(),
                        p.getParamVal(),
                        p.getParamType());
            }
            call.execute();
            target.commit();
        }catch (SQLException e){
            logger.error("CallableTask.java error:{}",e);
            throw new TaskException(e);
        }finally {
            try {
                if (!target.isClosed())
                    target.close();
                logger.debug("{}: 销毁完成.", taskInfo.getTaskName());
            }catch (SQLException e){

            }
        }
        return 0l;
    }

    @Override
    public void init() throws Exception {
        int ic =0;
        while (ic < MAX_INIT_NUM) {
            try {
                target = taskInfo.getTarget().getConnection();
                if (target != null) {
                    ic = 0;
                    break;
                }
            } catch (SQLException e) {
                ic++;
                logger.warn("初始化数据库链接错误，正在第{}重试链接", ic);
            }
        }
        if (target == null) {
            throw new TaskException("数据库链接初始化失败，请检查数据库链接配置是否正确。");
        }
        logger.debug("{}: 初始化完成.", taskInfo.getTaskName());
    }

    @Override
    public void destroy() throws Exception {

    }
}
