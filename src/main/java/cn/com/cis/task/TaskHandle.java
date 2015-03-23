package cn.com.cis.task;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TaskHandle implements InvocationHandler {

    private Object target;

    public TaskHandle(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("execute".equals(method.getName())){
            testConnection();
            testParameter();
        }
        Object obj = method.invoke(target,args);
        return obj;
    }

    private boolean testConnection(){
        System.out.println("testConnection.........");
        return true;
    }
    private boolean testParameter(){
        System.out.println("testParameter.........");
        return true;
    }
}
