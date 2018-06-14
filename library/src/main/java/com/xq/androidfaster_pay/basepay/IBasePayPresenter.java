package com.xq.androidfaster_pay.basepay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.xq.androidfaster_pay.FasterPayInterface;
import com.xq.androidfaster_pay.bean.behavior.WXParamBehavior;
import com.xq.androidfaster_pay.bean.entity.PayResult;
import com.xq.androidfaster_pay.event.WXPayEvent;
import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.Map;

public interface IBasePayPresenter<T extends AbsView> extends AbsPresenter<T> {

    public static final int FLAG_ALIPAY = 1;

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    default void onWXPayEvent(WXPayEvent event) {
        int code = event.getResp().getType();
        switch (code) {
            case 0:
                Toast.makeText(getContext(),"支付成功",Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(getContext(),"支付失败",Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(getContext(),"用户取消支付",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(),"未知错误",Toast.LENGTH_SHORT).show();
                break;
        }
        afterWXPay(event.getResp());
        onPayFinish(event.getResp().getType() ==0?true:false);
    }

    default void aliPay(final String orderInfo){

        @SuppressLint("HandlerLeak")
        final Handler aliPayHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_ALIPAY: {
                        //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        // 同步返回需要验证的信息
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            afterAliPay(payResult);
                            onPayFinish(true);
                        } else {
                            afterAliPay(payResult);
                            onPayFinish(false);
                        }
                        break;
                    }
                }
            }
        };

        // 必须异步调用
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

    public void afterWXPay(BaseResp resp);

    public void afterAliPay(PayResult payResult);

    public void onPayFinish(boolean isSuccess);

}