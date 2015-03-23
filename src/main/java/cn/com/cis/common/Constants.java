package cn.com.cis.common;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    /**
     * 批量更新数
     */
    public static final int  BATCH_UPDATE_SIZE = 10000;
    /**
     * 批量提交数量
     */
    public static final int BATCH_COMMIT_SIZE = 20000;

    public static final int BATCH_VIEW_SIZE = 100000;

    public static final int DEFAULT_MIN_SPLIT = 5;

    public static final String WEBSOCKET_USERNAME = "WEBSOCKET_USERNAME";

    public static final String GLOBAL = "GLOBAL";

    public static final String LOCAL = "LOCAL";

    public static final Set<String> PARAMETER_SCOPE =  new HashSet<String>();

    static {
        PARAMETER_SCOPE.add(GLOBAL);
        PARAMETER_SCOPE.add(LOCAL);
    }

    private Constants(){}
}
