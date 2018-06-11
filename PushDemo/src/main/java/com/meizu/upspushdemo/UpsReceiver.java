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


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.meizu.upspushsdklib.CommandType;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;
import com.meizu.upspushsdklib.util.UpsLogger;

import static com.meizu.upspushdemo.UpsDemoApplication.sendMessage;


public class UpsReceiver extends UpsPushMessageReceiver {
    @Override
    public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {
         sendMessage("onThroughMessage: "+upsPushMessage.getContent());
    }

    @Override
    public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationClicked: "+upsPushMessage);
    }

    @Override
    public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationArrived: "+upsPushMessage);
    }

    @Override
    public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {
        sendMessage("onNotificationDeleted: "+upsPushMessage);
    }

    @Override
    public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
        UpsLogger.i(this,"UpsReceiver "+upsCommandMessage);
        switch (upsCommandMessage.getCompany()){
            case HUAWEI:
                if(upsCommandMessage.getCommandType() == CommandType.REGISTER){
                    Bundle bundle = (Bundle) upsCommandMessage.getExtra();
                    String belongId = bundle.getString("belongId");
                    UpsLogger.i(this,"hw belongId "+belongId);
                } else if(upsCommandMessage.getCommandType() == CommandType.UNREGISTER){
                    UpsLogger.i(this,"hw unregister "+upsCommandMessage);
                }

                break;
            default:
        }

        sendMessage("onUpsCommandResult 如下:"
                +"\n CommandType-> "+upsCommandMessage.getCommandType().name()
                +"\n Company-> "+upsCommandMessage.getCompany()
                +"\n Code-> "+upsCommandMessage.getCode()
                +"\n CommandResult-> "+upsCommandMessage.getCommandResult()
                + (TextUtils.isEmpty(upsCommandMessage.getMessage()) ? "":"\n Message-> "+upsCommandMessage.getMessage()));
    }



}
