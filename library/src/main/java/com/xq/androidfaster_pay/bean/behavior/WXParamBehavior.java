package com.xq.androidfaster_pay.bean.behavior;

public interface WXParamBehavior {

    public String getAppid();

    public String getNoncestr();

    public String getPackage();

    public String getPartnerid();

    public String getPrepayid();

    public String getTimestamp();

    public String getSign();

}
