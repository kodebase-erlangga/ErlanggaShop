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
    private BannerSliderAdapter bannerSliderAdapter;
    private List<BannerItem> bannerItems = new ArrayList<>();
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private final int SLIDE_INTERVAL = 3000; // Interval for sliding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        viewPager = findViewById(R.id.viewPager);
        tabIndicator = findViewById(R.id.tabIndicator);

        fetchBanner(); // Memanggil metode untuk mengambil banner
    }

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

                                // Tambahkan banner items
                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
                                    if (bannerItem.has("url_banner") && bannerItem.has("url_produk")) {
                                        String imageUrl = bannerItem.getString("url_banner");
                                        String linkUrl = bannerItem.getString("url_produk");
                                        bannerItems.add(new BannerItem(imageUrl, linkUrl));
                                    }
                                }

                                // Set the adapter for ViewPager2
                                bannerSliderAdapter = new BannerSliderAdapter(BannerGalery.this, bannerItems);
                                viewPager.setAdapter(bannerSliderAdapter);

                                // Set TabLayout indicator with ViewPager2
                                new TabLayoutMediator(tabIndicator, viewPager, (tab, position) -> {
                                    // Set content description for accessibility
                                    String bannerDescription = "Banner " + (position + 1);
                                    tab.setText(bannerDescription); // Optional, if you want to show text
                                    tab.view.setContentDescription(bannerDescription); // Set content description for accessibility
                                }).attach();

                                // Start automatic sliding
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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_email", "mihsanrahman19@gmail.com");
                params.put("user_password", "ihsan111");
                params.put("galery_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("id", "100");
                params.put("aksi", "ambilbanner_slider");
                return params;
            }
        };

        // Tambahkan request ke RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void startAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // If we are at the last item, go back to the first item
                if (viewPager.getCurrentItem() == bannerItems.size() - 1) {
                    viewPager.setCurrentItem(0, true);
                } else {
                    // Otherwise, go to the next item
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
                handler.postDelayed(this, SLIDE_INTERVAL); // Set the next slide after the interval
            }
        };
        handler.postDelayed(runnable, SLIDE_INTERVAL); // Start the auto sliding
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Stop auto sliding when the activity is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, SLIDE_INTERVAL); // Resume auto sliding when the activity is resumed
    }
}
