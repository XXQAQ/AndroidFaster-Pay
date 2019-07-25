package com.xq.androidfaster_pay.bean;

import java.io.Serializable;

public class WXResult implements Serializable{

    private int erroCode;

    public WXResult(int erroCode) {
        this.erroCode = erroCode;
    }

    public int getErroCode() {
        return erroCode;
    }

    public WXResult setErroCode(int erroCode) {
        this.erroCode = erroCode;
        return this;
    }
}
