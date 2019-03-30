package com.xiongya.netty.heartBeat.entity;

import java.io.Serializable;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2610:31
 */

public class CustomProtocol implements Serializable {

    private static final long seriaVersionUID = 290429819350651974L;

    private long id;

    private String content;




    public CustomProtocol(long id, String ping) {
        this.id = id;
        this.content = ping;
    }

    public CustomProtocol(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CustomProtocol{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }
}
