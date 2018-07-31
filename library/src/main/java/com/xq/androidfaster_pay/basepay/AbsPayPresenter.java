package com.xq.androidfaster_pay.basepay;


import com.xq.androidfaster_pay.bean.behavior.WXParamBehavior;
import com.xq.androidfaster_pay.bean.entity.AliResult;
import com.xq.androidfaster_pay.bean.entity.WXResult;
import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsView;

public interface AbsPayPresenter<T extends AbsView> extends AbsPresenter<T> {

    //阿里支付
    public void aliPay(final String orderInfo);

    //微信支付
    public void wxPay(final WXParamBehavior WXParamBehavior);

    //该方法在微信支付完成后回调
    public abstract void afterWXPay(WXResult result);

    //该方法在支付宝完成后回调
    public abstract void afterAliPay(AliResult aliResult);

    //所有支付方法完成后都会回调到该方法中
    public abstract void onPayFinish(boolean isSuccess);

}