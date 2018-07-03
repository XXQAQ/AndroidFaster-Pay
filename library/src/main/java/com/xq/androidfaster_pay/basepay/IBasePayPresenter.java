package com.xq.androidfaster_pay.basepay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.xq.androidfaster_pay.FasterPayInterface;
import com.xq.androidfaster_pay.bean.behavior.WXParamBehavior;
import com.xq.androidfaster_pay.bean.entity.AliResult;
import com.xq.androidfaster_pay.bean.entity.WXResult;
import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.util.tools.CacheDiskUtils;

import java.util.Map;

public interface IBasePayPresenter<T extends AbsView> extends AbsPresenter<T> {

    public static final int FLAG_ALIPAY = 1;

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

        WXResult result = (WXResult) CacheDiskUtils.getInstance().getSerializable(WXResult.class.getName());
        if (result != null)
        {
            CacheDiskUtils.getInstance().remove(WXResult.class.getName());

            afterWXPay(result);
            onPayFinish(result.getErroCode() ==0?true:false);
        }
    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //阿里支付
    default void aliPay(final String orderInfo){
        @SuppressLint("HandlerLeak")
        final Handler aliPayHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_ALIPAY: {
                        //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                        @SuppressWarnings("unchecked")
                        AliResult aliResult = new AliResult((Map<String, String>) msg.obj);
                        // 同步返回需要验证的信息
                        String resultInfo = aliResult.getResult();
                        String resultStatus = aliResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            afterAliPay(aliResult);
                            onPayFinish(true);
                        } else {
                            afterAliPay(aliResult);
                            onPayFinish(false);
                        }
                        break;
                    }
                }
            }
        };

        Thread payThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask((Activity) getContext());
                Map<String, String> result = payTask.payV2(orderInfo, true);

                Message msg = Message.obtain();
                msg.what = FLAG_ALIPAY;
                msg.obj = result;
                aliPayHandler.sendMessage(msg);
            }
        });
        payThread.start();
    }

    //微信支付
    default void wxPay(final WXParamBehavior WXParamBehavior){

        Thread payThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PayReq payReq = new PayReq();
                payReq.appId = WXParamBehavior.getAppid();
                payReq.partnerId = WXParamBehavior.getPartnerid();
                payReq.prepayId = WXParamBehavior.getPrepayid();
                payReq.packageValue = WXParamBehavior.getPackage();
                payReq.nonceStr = WXParamBehavior.getNoncestr();
                payReq.timeStamp = WXParamBehavior.getTimestamp();
                payReq.sign = WXParamBehavior.getSign();
                //发起支付请求
                FasterPayInterface.getApi().sendReq(payReq);
            }
        });
        payThread.start();
    }

    //该方法在微信支付完成后回调
    public void afterWXPay(WXResult result);

    //该方法在支付宝完成后回调
    public void afterAliPay(AliResult aliResult);

    //所有支付方法完成后都会回调到该方法中
    public void onPayFinish(boolean isSuccess);

}