package com.xq.androidfaster_pay;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class FasterPayInterface {

    private static Application app;
    private static IWXAPI api;

    public void init(Application app,String wxKey){
        FasterPayInterface.app = app;

        api = WXAPIFactory.createWXAPI(app, wxKey,true);
        api.registerApp(wxKey);
    }

    public static Application getApp() {
        return app;
    }

    public static IWXAPI getApi() {
        return api;
    }
}
