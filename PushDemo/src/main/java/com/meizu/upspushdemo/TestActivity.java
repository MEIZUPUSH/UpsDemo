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
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.meizu.upspushsdklib.util.UpsLogger;

public class TestActivity extends Activity{

    TextView tvExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvExtra = (TextView) findViewById(R.id.tv_extra);
        tvExtra.setText("key "+getIntent().getStringExtra("key"));

        Uri uri =getIntent().getData();
        if(uri != null){
            UpsLogger.e(this, "scheme: "+uri.getScheme());
            UpsLogger.e(this, "host: "+uri.getHost());
            UpsLogger.e(this, "port: "+uri.getPort());
            UpsLogger.e(this, "path: "+uri.getPath());
            UpsLogger.e(this, "queryString: "+uri.getQuery());
            UpsLogger.e(this, "queryParameter: "+uri.getQueryParameter("key"));

            tvExtra.setText("scheme: "+uri.getScheme() +"\n" +
                    "host: "+uri.getHost()+"\n" +
                    "path: "+uri.getPath()+"\n" +
                    "queryString: "+uri.getQuery()
            );
        }

    }

}
