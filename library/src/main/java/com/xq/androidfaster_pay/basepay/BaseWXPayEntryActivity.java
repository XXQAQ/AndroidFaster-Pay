package com.xq.androidfaster_pay.basepay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xq.androidfaster_pay.FasterPayInterface;
import com.xq.projectdefine.util.ACache;



public class BaseWXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            FasterPayInterface.getApi().handleIntent(getIntent(), this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        FasterPayInterface.getApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.getType();
        switch (code) {
            case 0:
                Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(this,"用户取消支付",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
                break;
        }

        ACache.get(getFilesDir()).put(BaseResp.class.getName(),new Gson().toJson(resp),30);

        finish();
    }

}