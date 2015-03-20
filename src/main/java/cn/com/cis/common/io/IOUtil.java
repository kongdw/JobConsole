package cn.com.cis.common.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * I/O实用工具方法集合。
 * 
 */
public class IOUtil {

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }

}
