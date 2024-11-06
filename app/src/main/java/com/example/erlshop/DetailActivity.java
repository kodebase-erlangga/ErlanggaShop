package com.example.erlshop;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private int clickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Buku"); // Set title for the App Bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button

        ImageView detailImage = findViewById(R.id.detailImageView);
        TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
        TextView clickCountTextView = findViewById(R.id.clickCountTextView);
        WebView webView = findViewById(R.id.webView);

        // Ambil data dari Intent
        int imageResId = getIntent().getIntExtra("imageResId", -1);
        clickCount = getIntent().getIntExtra("clickCount", 0);
        String bookTitle = getIntent().getStringExtra("bookTitle");

        // Set data ke UI
        if (imageResId != -1) {
            detailImage.setImageResource(imageResId);
        }
        bookTitleTextView.setText(bookTitle);
        clickCountTextView.setText("Jumlah Klik: " + clickCount);

        // Inisialisasi dan set WebView Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.erlanggaonline.com/");

        // Tambahkan OnClickListener untuk detailImage
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                clickCountTextView.setText("Jumlah Klik: " + clickCount);
            }
        });
    }
}
