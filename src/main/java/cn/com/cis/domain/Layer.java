package cn.com.cis.domain;

import java.io.Serializable;

/**
 * etl层级
 */
public class Layer implements Serializable {

    private static final long serialVersionUID = -3056283981089206065L;
    // 层级ID
    private int id;
    // 层级名称
    private String name;
    // 层级执行顺序
    private int sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
