package cn.com.cis.task;


import cn.com.cis.task.entity.TaskInfo;

public class TaskFactory {

    public static Task<Long> getTaskInstance(TaskInfo task) {
        Task<Long> instance;
        switch (task.getType()) {
            case SELECT:
                instance = new TaskProxy<Long>(new InsertTask(task));
                break;
            case INSERT:
                instance = new TaskProxy<Long>(new InsertTask(task));
                break;
            case UPDATE:
                instance = new TaskProxy<Long>(new UpdateTask(task));
                break;
            case DELETE:
                instance = new TaskProxy<Long>(new DeleteTask(task));
                break;
            case CALL:
                instance = new TaskProxy<Long>(new CallableTask(task));
                break;
            default:
                instance = null;
        }
        return instance;
    }
}
