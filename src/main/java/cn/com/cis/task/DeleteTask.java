package cn.com.cis.task;

import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.task.entity.TaskInfo;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTask extends AbstractTask<Long> {

    private Logger logger = LoggerFactory.getLogger(DeleteTask.class);

    private TaskInfo taskInfo;
    private Connection target;
    public static final int MAX_INIT_NUM = 3;


    public DeleteTask(TaskInfo taskInfo) {
        super(taskInfo);
        this.taskInfo = taskInfo;
    }

    @Override
    public Long execute() throws TaskException {
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        TypeHandler typeHandler;
        long deleteCount = 0l;
        try {
            PreparedStatement tps = target.prepareStatement(taskInfo.getSqlScript());
            for (ParameterInfo p : taskInfo.getParameterInfoList()) {
                typeHandler = typeHandlerRegistry.getTypeHandler(p.getParamType());
                typeHandler.setParameter(tps, p.getParamIndex(),
                        p.getParamVal(),
                        p.getParamType());
            }
            deleteCount = tps.executeUpdate();
            target.commit();
        }catch (SQLException e){
            logger.error("{} 执行出错:{}",taskInfo.getTaskName(),e);
            throw new TaskException(e);
        }finally {
            try {
                if (!target.isClosed())
                    target.close();
                logger.debug("{}: 销毁完成.", taskInfo.getTaskName());
            }catch (SQLException e){

            }
        }
        return deleteCount;
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
