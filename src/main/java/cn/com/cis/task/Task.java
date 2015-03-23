package cn.com.cis.task;


import cn.com.cis.common.ILifeCycle;

import java.util.concurrent.Callable;

public interface Task<V> extends Callable<V>, ILifeCycle {

    /**
     * 执行 Task
     */
    V execute() throws Exception;

    /**
     * Task 名称
     * @return
     */
    String getName();
}
