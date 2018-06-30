package com.xq.androidfaster_pay;

import android.text.TextUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xq.projectdefine.FasterInterface;

public class FasterPayInterface {

    private static IWXAPI api;

    public static void init(String wxKey){

        if (!TextUtils.isEmpty(wxKey))
        {
            api = WXAPIFactory.createWXAPI(FasterInterface.getApp(), wxKey,true);
            api.registerApp(wxKey);
        }
    }

    public static IWXAPI getApi() {
        return api;
    }
}
