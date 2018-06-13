package com.xq.androidfaster_pay.event;

import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WXPayEvent {

    private BaseResp resp;

    public WXPayEvent(BaseResp resp) {
        this.resp = resp;
    }

    public BaseResp getResp() {
        return resp;
    }

    public WXPayEvent setResp(BaseResp resp) {
        this.resp = resp;
        return this;
    }
}
