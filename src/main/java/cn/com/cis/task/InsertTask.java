package cn.com.cis.task;

import cn.com.cis.common.Constants;
import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.task.entity.TaskInfo;
import cn.com.cis.utils.SqlScriptUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class InsertTask extends AbstractTask<Long> {


    private Logger logger = LoggerFactory.getLogger(InsertTask.class);

    private long insertCount = 0;
    private long batchSize = 0;
    private long commitSize = 0;
    private long commitView = 0;
    public static final int MAX_INIT_NUM = 3;
    private Connection sourceConnection;

    private Connection targetConnection;

    private TaskInfo taskEntity;

    public InsertTask(TaskInfo taskEntity) {
        super(taskEntity);
        this.taskEntity = taskEntity;
    }


    @Override
    public Long execute() throws TaskException {
        TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
        TypeHandler typeHandler;
        logger.debug("执行脚本:{}", taskEntity.getSqlScript());
        PreparedStatement sourcePs = null;
        ResultSet input = null;
        PreparedStatement targetPs = null;
        try {
            sourcePs = sourceConnection.prepareStatement(taskEntity.getSqlScript());
            //替换参数
            for (ParameterInfo p : taskEntity.getParameterInfoList()) {
                typeHandler = typeHandlerRegistry.getTypeHandler(p.getParamType());
                typeHandler.setParameter(sourcePs, p.getParamIndex(),
                        p.getParamVal(),
                        p.getParamType());
            }

            input = sourcePs.executeQuery();
            ResultSetMetaData resultSetMetaData = input.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();
            int[] columnsType = new int[columnCount];
            String[] columnsName = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnsName[i] = resultSetMetaData.getColumnName(i + 1);
                columnsType[i] = resultSetMetaData.getColumnType(i + 1);
            }
            targetPs = targetConnection.prepareStatement(SqlScriptUtil.getInsertSql(columnsName, taskEntity.getTargetTable()));
            while (input.next()) {
                for (int i = 0; i < columnsName.length; i++) {
                    typeHandler = typeHandlerRegistry.getTypeHandler(JdbcType.forCode(columnsType[i]));
                    typeHandler.setParameter(targetPs, i + 1, typeHandler.getResult(input, i + 1), JdbcType.forCode(columnsType[i]));
                }
                targetPs.addBatch();
                batchSize++;
                commitSize++;
                commitView++;
                // 是否达到批量执行上限
                if (batchSize >= Constants.BATCH_UPDATE_SIZE) {
                    targetPs.executeBatch();
                    insertCount += targetPs.getUpdateCount();
                    batchSize = 0;
                }
                // 是否达到提交上限
                if (commitSize >= Constants.BATCH_COMMIT_SIZE) {
                    targetPs.executeBatch();
                    targetConnection.commit();
                    commitSize = 0;
                }
                // 是否达到显示上限
                if (commitView >= Constants.BATCH_VIEW_SIZE) {
                    commitView = 0;
                    logger.info("{} 已提交{}.", taskEntity.getTaskName(), insertCount);
                }
            }
            if (targetPs != null) {
                targetPs.executeBatch();
                insertCount += targetPs.getUpdateCount();
            }
            logger.info("{} 已提交{}.", taskEntity.getTaskName(), insertCount);
            targetConnection.commit();
        } catch (SQLException e) {
            logger.error("{} 执行异常：{}", taskEntity.getTaskName(), e);
            throw new TaskException(e);
        } finally {
            try {
                if (!this.sourceConnection.isClosed())
                    this.sourceConnection.close();
                if (!this.targetConnection.isClosed())
                    this.targetConnection.close();
                logger.debug("{}: 销毁完成.", taskEntity.getTaskName());
            } catch (SQLException e) {
                logger.error("error:", e);
            }
        }
        return insertCount;
    }

    @Override
    public void init() throws Exception {
        int ic = 0;
        while (ic < MAX_INIT_NUM) {
            try {
                sourceConnection = taskEntity.getSource().getConnection();
                if (sourceConnection != null) {
                    ic = 0;
                    break;
                }
            } catch (SQLException e) {
                ic++;
                logger.warn("初始化数据库链接错误，正在第{}重试链接", ic);
            }
        }
        while (ic < MAX_INIT_NUM) {
            try {
                targetConnection = taskEntity.getTarget().getConnection();
                if (targetConnection != null) {
                    ic = 0;
                    break;
                }
            } catch (SQLException e) {
                ic++;
                logger.warn("初始化数据库链接错误，正在第{}重试链接", ic);
            }
        }
        if (sourceConnection == null || targetConnection == null) {
            throw new TaskException("数据库链接初始化失败，请检查数据库链接配置是否正确。");
        }
        logger.debug("{}: 初始化完成.", taskEntity.getTaskName());
    }

    @Override
    public void destroy() throws SQLException {
    }

}
