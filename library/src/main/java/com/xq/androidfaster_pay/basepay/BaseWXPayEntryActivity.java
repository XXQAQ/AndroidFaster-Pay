package com.xq.androidfaster_pay.basepay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xq.androidfaster_pay.FasterPayInterface;
import com.xq.androidfaster_pay.bean.entity.WXResult;
import com.xq.projectdefine.util.tools.CacheDiskUtils;


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

        if (ConstantsAPI.COMMAND_PAY_BY_WX != resp.getType())
        {
            finish();
            return;
        }

        CacheDiskUtils.getInstance().put(WXResult.class.getName(),new WXResult(resp.errCode));

        finish();
    }

}