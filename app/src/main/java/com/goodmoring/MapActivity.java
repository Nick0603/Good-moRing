package com.goodmoring;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

/**
 * Created by Nick on 2017/7/22.
 */

public class MapActivity extends Activity {

    WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageButton iBtnBack = (ImageButton)findViewById(R.id.iBtn_back);
        iBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        Intent intent = this.getIntent();
//        //取得傳遞過來的資料
//        String url = intent.getStringExtra("url");
//
//        WebView webview = (WebView) findViewById(R.id.webview);
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        setContentView(webview);
//        webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl(url);
    }
}