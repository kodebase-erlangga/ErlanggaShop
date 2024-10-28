package com.example.erlshop;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        String url = getIntent().getStringExtra("URL");
        if (url != null) {
            webView.loadUrl(url);
        }
    }
}