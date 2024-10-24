package com.example.erlshop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerGalery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BannerAdapter bannerAdapter;
    private List<BannerItem> bannerItems = new ArrayList<>();  // Gunakan List<BannerItem>
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_galery);

        errorTextView = findViewById(R.id.errorTextView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch banner data
        fetchBanner();
    }

    private void fetchBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");
                            if (erlStatusId.equals("true")) {
                                JSONArray bannerArray = jsonResponse.getJSONArray("data");

                                // Tambahkan data ke bannerItems sebagai BannerItem (berisi URL gambar dan URL link)
                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
                                    if (bannerItem.has("url_banner") && bannerItem.has("url_produk")) {
                                        String bannerCover = bannerItem.getString("url_banner");
                                        String linkUrl = bannerItem.getString("url_produk");
                                        String imageUrl = "https://e-library.erlanggaonline.co.id/upload/cover/" + bannerCover;
                                        bannerItems.add(new BannerItem(imageUrl, linkUrl));  // Buat objek BannerItem
                                    }
                                }

                                // Set adapter dengan List<BannerItem>
                                bannerAdapter = new BannerAdapter(BannerGalery.this, bannerItems);
                                recyclerView.setAdapter(bannerAdapter);
                            } else {
                                showError("Error: " + jsonResponse.getString("message"));
                            }
                        } catch (JSONException e) {
                            showError("Parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError("Request error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_email", "mihsanrahman19@gmail.com");  // Sesuaikan dengan autentikasi yang benar
                params.put("user_password", "ihsan111");               // Sesuaikan dengan autentikasi yang benar
                params.put("galery_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("id", "100");
                params.put("aksi", "ambilbanner_slider");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void showError(String message) {
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }
}
