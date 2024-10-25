package com.example.erlshop;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // Initialize WebView
        webView = findViewById(R.id.webView);

        // Enable JavaScript for WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Ensure links open in WebView, not the default browser
        webView.setWebViewClient(new WebViewClient());

        // Get URL passed through intent
        String url = getIntent().getStringExtra("URL");
        if (url != null) {
            webView.loadUrl(url);  // Load the URL in WebView
        }
    }

    // Ensure the back button navigates back in WebView history if possible
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
