package com.xq.androidfaster_pay.bean;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1.
 */

public interface ParamBean {

    public String getAppid();

    public String getNoncestr();

    public String getPackage();

    public String getPartnerid();

    public String getPrepayid();

    public String getTimestamp();

    public String getSign();

}
