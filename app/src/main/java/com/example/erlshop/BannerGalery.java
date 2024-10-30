package com.example.erlshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerGalery extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabIndicator;
//    private BannerSliderAdapter bannerSliderAdapter;
    private List<BannerItem> bannerItems = new ArrayList<>();
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private final int SLIDE_INTERVAL = 1000; // Interval untuk sliding otomatis dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

//        viewPager = findViewById(R.id.viewPager);
//        tabIndicator = findViewById(R.id.tabIndicator);

//        fetchBanner(); // Memanggil metode untuk mengambil banner dari server
    }

    // Metode untuk mengambil data banner dari server
    private void fetchBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");
                            if ("true".equals(erlStatusId)) {
                                JSONArray bannerArray = jsonResponse.getJSONArray("data");

                                // Memproses setiap item dalam array banner
//                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
//                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
//                                    if (bannerItem.has("url_banner") && bannerItem.has("url_produk")) {
//                                        String imageUrl = bannerItem.getString("url_banner");
//                                        String linkUrl = bannerItem.getString("url_produk");
//                                        bannerItems.add(new BannerItem(imageUrl, linkUrl));
//                                    }
//                                }

//                                // Menyiapkan adapter untuk ViewPager2
//                                bannerSliderAdapter = new BannerSliderAdapter(BannerGalery.this, bannerItems);
//                                viewPager.setAdapter(bannerSliderAdapter);

                                // Menghubungkan TabLayout dengan ViewPager2
                                new TabLayoutMediator(tabIndicator, viewPager, (tab, position) -> {
                                    String bannerDescription = "Banner " + (position + 1);
                                    tab.setText(bannerDescription);
                                    tab.view.setContentDescription(bannerDescription);
                                }).attach();

                                // Memulai sliding otomatis
                                startAutoSlide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_email", "mihsanrahman19@gmail.com");
//                params.put("user_password", "ihsan111");
//                params.put("galery_device_id", "fae3876e39143557");
//                params.put("user_version", "proteksi");
//                params.put("id", "100");
//                params.put("aksi", "ambilbanner_slider");
//                return params;
//            }
        };

        // Menambahkan request ke RequestQueue Volley
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Memulai fungsi sliding otomatis pada ViewPager2
    private void startAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Mengecek apakah posisi saat ini adalah item terakhir
                if (viewPager.getCurrentItem() == bannerItems.size() - 1) {
                    viewPager.setCurrentItem(0, true); // Kembali ke item pertama jika mencapai item terakhir
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true); // Pindah ke item berikutnya
                }
                handler.postDelayed(this, SLIDE_INTERVAL); // Mengatur slide berikutnya setelah interval
            }
        };
        handler.postDelayed(runnable, SLIDE_INTERVAL); // Memulai sliding otomatis pertama kali
    }

//    // Menghentikan sliding otomatis saat activity dijeda (misal ketika pindah ke aplikasi lain)
//    @Override
//    protected void onPause() {
//        super.onPause();
//        handler.removeCallbacks(runnable); // Menghentikan sliding otomatis
//    }
//
//    // Melanjutkan sliding otomatis saat activity kembali ke layar
//    @Override
//    protected void onResume() {
//        super.onResume();
//        handler.postDelayed(runnable, SLIDE_INTERVAL); // Melanjutkan sliding otomatis
//    }
}
