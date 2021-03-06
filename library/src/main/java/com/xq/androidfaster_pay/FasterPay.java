package com.xq.androidfaster_pay;

import android.text.TextUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xq.androidfaster.util.tools.ResourceUtils;

import static com.xq.androidfaster.util.tools.Utils.getApp;

public class FasterPay {

    private static IWXAPI api;

    public static void init(){

        String wxKey = ResourceUtils.getString(R.string.WXPay_key);
        if (!TextUtils.isEmpty(wxKey))
        {
            api = WXAPIFactory.createWXAPI(getApp(), wxKey,true);
            api.registerApp(wxKey);
        }
    }

    public static IWXAPI getWXApi() {
        return api;
    }
}
