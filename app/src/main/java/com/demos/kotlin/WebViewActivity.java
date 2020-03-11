package com.demos.kotlin;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.web_view);
        setWebView();
        loadLocal();
    }

    private void setWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new AndroidJs(), "android");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //将alert以toast显示
                Toast.makeText(WebViewActivity.this, message, Toast.LENGTH_SHORT).show();
                //不加下面这行，只显示一次
                result.confirm();
                return true;
            }
        });
    }

    /**
     * 加载本地h5文件
     */
    private void loadLocal() {
        webView.loadUrl("file:///android_asset/test.html");
    }

    public class AndroidJs {

        @JavascriptInterface
        public void fromAndroid() {
            Log.e("webview", "finish");
            finish();
        }
    }

    /**
     *
     */
    private void loadWeb() {

    }
}
