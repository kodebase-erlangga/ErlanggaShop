package com.example.erlshop;

import android.os.Bundle;
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
    private SliderAdapter sliderAdapter;
    private List<BannerItem> bannerItems = new ArrayList<>();
    private static final String URL = "https://ebook.erlanggaonline.co.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        viewPager = findViewById(R.id.viewPager);
        tabIndicator = findViewById(R.id.tabIndicator);

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

                                // Add banner items
                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
                                    if (bannerItem.has("url_banner")) {
                                        String imageUrl = bannerItem.getString("url_banner");
                                        bannerItems.add(new BannerItem(imageUrl, null));
                                    }
                                }

                                // Set the adapter for ViewPager2
                                sliderAdapter = new SliderAdapter(BannerGalery.this, bannerItems);
                                viewPager.setAdapter(sliderAdapter);

                                // Set TabLayout indicator with ViewPager2
                                new TabLayoutMediator(tabIndicator, viewPager, (tab, position) -> {}).attach();

                            } else {
                                // Handle error
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
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

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
