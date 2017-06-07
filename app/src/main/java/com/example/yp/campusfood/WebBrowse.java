package com.example.yp.campusfood;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowse extends Activity{
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        webView = (android.webkit.WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        // get url message passed dowm from cafe view
        Bundle b = getIntent().getExtras();
        String url =b.getString("URL");

        // load url based on cafe selected
        webView.loadUrl(url);

        // intercept URL and load in widget
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading (WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }

    // backspace button navigates back to the previous web page
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
