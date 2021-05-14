package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bookreviewer.R;

public class WebsiteActivity extends AppCompatActivity {
    private WebView webView;
    public static final String INFO_LINK = "info_link";
    public static final String BUY_LINK = "buy_link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Intent intent = getIntent();
        String url = "";
        if (intent != null) {
            url = intent.getStringExtra(INFO_LINK);
            if (url == null) {
                url = intent.getStringExtra(BUY_LINK);
            }
            webView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}