package cn.com.cis.job;

public class ETLServerException extends  Exception{

    private static final long serialVersionUID = 1461775838809834746L;

    public ETLServerException() {
        super();
    }

    public ETLServerException(String message) {
        super(message);
    }

    public ETLServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ETLServerException(Throwable cause) {
        super(cause);
    }

    protected ETLServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
