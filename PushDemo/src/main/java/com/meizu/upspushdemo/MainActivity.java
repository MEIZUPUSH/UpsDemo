/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.meizu.upspushdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.UpsPushManager;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnRegister;
    private Button btnUnRegister;
    private Button btnSetAlias;
    private Button btnUnsetAlias;
    private Button btnEnableDirectMode;
    private Button btnDisableDirectMode;
    //选择长连接通知栏类型，用于测试用
    private Button btnChoosePushChannel;

    private Button btnServerPush;

    private String xmAppId;
    private String xmAppKey;
    private String mzAppId;
    private String mzAppKey;

    public static List<String> logList = new CopyOnWriteArrayList<String>();
    private TextView mLogView = null;

    public static String xmToken;


    public static String UPS_APP_ID = "1000000";
    public static String UPS_APP_KEY = "38caef3fbc1347c1ba8e983226dc2c4f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpsDemoApplication.setMainActivity(this);
        setContentView(R.layout.activity_main);
        initView();
        initMetaData();

    }

    private void initView(){
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnUnRegister = (Button) findViewById(R.id.btn_unregister);
        btnUnRegister.setOnClickListener(this);
        btnSetAlias = (Button) findViewById(R.id.btn_set_alias);
        btnSetAlias.setOnClickListener(this);
        btnUnsetAlias = (Button) findViewById(R.id.btn_unset_alias);
        btnUnsetAlias.setOnClickListener(this);
        mLogView = (TextView) findViewById(R.id.log);
        btnServerPush = (Button) findViewById(R.id.btn_server_push);
        btnServerPush.setOnClickListener(this);
        btnEnableDirectMode = findViewById(R.id.btn_enable_direct_mode);
        btnEnableDirectMode.setOnClickListener(this);
        btnDisableDirectMode = findViewById(R.id.btn_disable_direct_mode);
        btnDisableDirectMode.setOnClickListener(this);
        btnChoosePushChannel = findViewById(R.id.btn_choose_company_channel);
        btnChoosePushChannel.setOnClickListener(this);
    }

    private void initMetaData(){
        xmAppId = UpsUtils.getMetaStringValueByName(this, UpsConstants.XIAOMI_APP_ID);
        xmAppKey = UpsUtils.getMetaStringValueByName(this,UpsConstants.XIAOMI_APP_KEY);
        mzAppId = UpsUtils.getMetaIntValueByName(this, UpsConstants.MEIZU_APP_ID);
        mzAppKey = UpsUtils.getMetaStringValueByName(this,UpsConstants.MEIZU_APP_KEY);
        UpsUtils.getMetaIntValueByName(this,"com.huawei.hms.client.appid");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enable_direct_mode:
                UpsPushManager.enableDirectMode(this,true);
                break;
            case R.id.btn_disable_direct_mode:
                UpsPushManager.enableDirectMode(this,false);
                break;
            case R.id.btn_choose_company_channel:
                showChooseChannelDiglog();
                break;
            case R.id.btn_register:
                UpsPushManager.register(this,UPS_APP_ID,UPS_APP_KEY);
                //MiPushClient.registerPush(this,xmAppId,xmAppKey);
                //hwIntentUri();
                //intentToUri();
                UpsLogger.e(this,"MANUFACTURER:"+ Build.MANUFACTURER+" model:"+Build.MODEL+" brand:"+Build.BOARD);
                break;
            case R.id.btn_unregister:
                UpsPushManager.unRegister(this);
                //intentToUri();
                break;
            case R.id.btn_set_alias:
                UpsPushManager.setAlias(this,"ups");
                break;
            case R.id.btn_unset_alias:
                UpsPushManager.unSetAlias(this,"ups");
                break;
            case R.id.btn_server_push:
                break;
            default:
                break;
        }
    }

    private void intentToUri(){
        Intent intent = new Intent(this,TestActivity.class);
        intent.putExtra("key","value");
        UpsLogger.i(this,"intent uri "+intent.toUri(Intent.URI_INTENT_SCHEME));

        Intent hwIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
        hwIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UpsLogger.i(this,"http uri "+hwIntent.toUri(Intent.URI_INTENT_SCHEME));
    }

    private void hwIntentUri(){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("upspushscheme://com.meizu.upspush/notify_detail?title=ups title&content=ups content"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UpsLogger.e(this,intent.toUri(Intent.URI_INTENT_SCHEME));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpsDemoApplication.setMainActivity(null);
    }


    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        mLogView.setText(AllLog);
    }


    private void showChooseChannelDiglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_channel_tile)
                .setItems(R.array.channel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String channelName = "";
                        switch (which){
                            case 0:
                                channelName = "自动选择";
                                UpsPushManager.setCurrentChannelType(MainActivity.this,which);
                                break;
                            case 1:
                                channelName = "魅族";
                                UpsPushManager.setCurrentChannelType(MainActivity.this,which);
                                break;
                            case 2:
                                channelName = "小米";
                                UpsPushManager.setCurrentChannelType(MainActivity.this,which);
                                break;
                            case 3:
                                channelName = "华为";
                                UpsPushManager.setCurrentChannelType(MainActivity.this,which);
                                break;
                            case 4:
                                channelName = "魅族全平台";
                                UpsPushManager.setCurrentChannelType(MainActivity.this,40);
                                break;
                        }

                        UpsLogger.e(this,"channel which "+which+" company "+Company.fromValue(which));
                        UpsDemoApplication.sendMessage("使用 "+ channelName+" 推送通道");
                    }
                });
        builder.create().show();
    }


}
