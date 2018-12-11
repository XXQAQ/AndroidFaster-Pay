package com.xq.androidfaster_pay.basepay;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster_pay.bean.behavior.WXParamBehavior;

public interface IAbsPayPresenter<T extends IAbsView> extends IAbsPresenter<T> {

    //阿里支付
    public void aliPay(final String orderInfo);

    //微信支付
    public void wxPay(final WXParamBehavior WXParamBehavior);

}