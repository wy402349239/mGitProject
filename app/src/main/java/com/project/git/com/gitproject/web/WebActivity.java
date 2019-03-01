package com.project.git.com.gitproject.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @author jx_wy
 * @date 6:14 PM 2019/3/1
 * @email wangyu@51dianshijia.com
 * @description
 */
public class WebActivity extends BaseActivity {

    private WebView mWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setCj();
        mWeb = findViewById(R.id.web_webview);
        final String stringExtra = getIntent().getStringExtra("url");
        initWebView();
        if (!TextUtils.isEmpty(stringExtra)){
            mWeb.loadUrl(stringExtra);
            mWeb.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(stringExtra);
                    return true;
                }
            });
        }
    }

    /**
     * 初始化webview相关设置
     */
    private void initWebView(){
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
    }
}
