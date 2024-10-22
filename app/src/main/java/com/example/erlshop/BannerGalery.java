package com.example.erlshop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class BannerGalery extends AppCompatActivity {  // Harus mewarisi AppCompatActivity

    private RecyclerView recyclerView;
    private BannerAdapter bannerAdapter;  // Pastikan adapter sesuai dengan kelas yang digunakan
    private List<String> imageUrls = new ArrayList<>();  // Menyimpan URL gambar dari API
    private static final String URL = "https://ebook.erlanggaonline.co.id";  // URL API yang digunakan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_galery);  // Set layout yang benar

        // Inisialisasi RecyclerView dan menggunakan GridLayoutManager dengan 3 kolom
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));  // Menggunakan 3 kolom untuk grid layout

        // Memanggil method untuk mengambil gambar dari API
        fetchGallery();
    }

    private void fetchGallery() {
        // Membuat request dengan metode POST ke URL API
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsing response JSON
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");

                            // Mengecek jika status dari API adalah "true"
                            if (erlStatusId.equals("true")) {
                                JSONArray gallery = jsonResponse.getJSONArray("data");

                                // Ambil maksimal 20 gambar dari array JSON
                                for (int i = 0; i < gallery.length() && i < 20; i++) {
                                    JSONObject book = gallery.getJSONObject(i);
                                    String galery_cover = book.getString("url_produk");
                                    String imageUrl = "https://e-library.erlanggaonline.co.id/upload/cover/" + galery_cover;
                                    imageUrls.add(imageUrl);  // Menambahkan URL gambar ke list
                                }

                                // Setelah data diambil, set adapter ke RecyclerView
                                bannerAdapter = new BannerAdapter(BannerGalery.this, imageUrls);  // Sesuaikan dengan adapter yang benar
                                recyclerView.setAdapter(bannerAdapter);
                            }
                        } catch (JSONException e) {
                            // Menangkap kesalahan parsing JSON jika terjadi
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Menangani kesalahan response dari Volley
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Menyediakan parameter POST yang diperlukan oleh API
                Map<String, String> params = new HashMap<>();
                params.put("user_email", "mihsanrahman19@gmail.com");
                params.put("user_password", "ihsan111");
                params.put("user_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("id", "100");
                params.put("aksi", "ambilgalery");
                return params;
            }
        };

        // Menambahkan request ke Volley queue untuk dieksekusi
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
