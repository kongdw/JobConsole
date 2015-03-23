package cn.com.cis.task;


import cn.com.cis.task.entity.TaskInfo;

public abstract class AbstractTask<V> implements Task<V>{

    private TaskInfo taskInfo;

    public AbstractTask(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public abstract V execute() throws Exception;

    @Override
    public String getName() {
        return taskInfo.getTaskName();
    }

    @Override
    public V call() throws Exception {
        init();
        V result = execute();
        destroy();
        return result;
    }

    @Override
    public abstract void destroy() throws Exception;

    @Override
    public abstract void init() throws Exception;
}
