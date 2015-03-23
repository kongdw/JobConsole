package cn.com.cis.domain;

import java.io.Serializable;

/**
 *
 * ETL 模式字典
 * @author kongdw
 */
public class Mode implements Serializable {

    private static final long serialVersionUID = -6857972399117356117L;

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
