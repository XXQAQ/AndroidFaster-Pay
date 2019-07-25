package com.xq.androidfaster_pay.base.basepay;

import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster_pay.bean.behavior.WXParamBehavior;

public interface IBasePayBehavior<T extends Controler> extends Controler<T> {

    //阿里支付
    public void aliPay(final String orderInfo);

    //微信支付
    public void wxPay(final WXParamBehavior WXParamBehavior);

}