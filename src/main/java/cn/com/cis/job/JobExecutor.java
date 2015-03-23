package cn.com.cis.job;

public interface JobExecutor {

    /**
     * 执行作业
     */
    void execute() throws Exception;

    /**
     * Task是否执行完毕
     * @return 完成返回true
     */
    boolean isDone();

    /**
     * Task是否出错
     * @return 出错返回true
     */
    boolean isError();

    /**
     * 终止当前作业
     * @return 成功 true 失败 false
     */
    boolean shutdown() throws InterruptedException;

    String getProgress();

    long getPower();

    String getErrorMessage();

    long getUpdateCount();

    Throwable getErrorThrowable();

}
