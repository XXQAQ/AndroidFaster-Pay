package com.xq.androidfaster_pay.bean.entity;

import java.io.Serializable;

public class WXResult implements Serializable{

    private int type;

    public WXResult(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public WXResult setType(int type) {
        this.type = type;
        return this;
    }
}
