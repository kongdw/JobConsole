package cn.com.cis.task;

public class TaskProxy<V> implements Task<V> {

    private Task<V> target;

    public TaskProxy(Task<V> target) {
        this.target = target;
    }

    @Override
    public V execute() throws Exception {
        return target.execute();
    }

    @Override
    public String getName() {
        return target.getName();
    }


    @Override
    public V call() throws Exception {
        init();
        V result =  execute();
        destroy();
        return result;
    }

    @Override
    public void init() throws Exception {
        target.init();
    }

    @Override
    public void destroy() throws Exception {
        target.destroy();
    }
}
