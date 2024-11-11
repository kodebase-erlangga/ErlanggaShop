package com.example.erlshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaleriFragment extends Fragment {

    private ViewPager2 viewPager;
    private BannerSliderAdapter bannerSliderAdapter;
    private List<BannerItem> bannerItems = new ArrayList<>();
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private TextView errorTextView;
    private LinearLayout dotIndicator;
    private ShimmerFrameLayout shimmerLayout;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private final int SLIDE_INTERVAL = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galeri, container, false);

        // Initialize views
        errorTextView = view.findViewById(R.id.errorTextView);
        viewPager = view.findViewById(R.id.viewPager);
        dotIndicator = view.findViewById(R.id.dotIndicator);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        // Check if device is a tablet and adjust layout parameters
        if (ScreenUtils.isTablet(getContext())) {
            adjustLayoutForTablet(view);
        }

        // Start shimmer effect initially
        startShimmer();

        // Fetch data from API
        fetchBanner();

        return view;
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

                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
                                    if (bannerItem.has("url_banner")) {
                                        String bannerCover = bannerItem.getString("url_banner");
                                        String linkUrl = bannerItem.getString("url_produk");
                                        bannerItems.add(new BannerItem(bannerCover, linkUrl));
                                    }
                                }

                                bannerSliderAdapter = new BannerSliderAdapter(requireContext(), bannerItems);
                                viewPager.setAdapter(bannerSliderAdapter);
                                errorTextView.setVisibility(View.GONE);

                                setupDotIndicators();
                                setupViewPager();
                                startAutoSlide();
                                stopShimmer(); // Stop shimmer after loading data

                                // Set the ViewPager2 to allow looping
                                viewPager.setClipToPadding(false);
                                viewPager.setClipChildren(false);
                                viewPager.setOffscreenPageLimit(3);
                                viewPager.setPageTransformer(new LoopingPageTransformer());

                                startAutoSlide();
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
                params.put("user_email", "mihsanrahman19@gmail.com");
                params.put("user_password", "ihsan111");
                params.put("galery_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("id", "100");
                params.put("aksi", "ambilbanner_slider");
                return params;
            }
        };

        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }

    private void adjustLayoutForTablet(View view) {
        // Convert 150dp to pixels
        int heightInPixels = (int) (300 * getResources().getDisplayMetrics().density);

        // Adjust ViewPager to match_parent width and 150dp height for tablet
        ViewGroup.LayoutParams viewPagerLayoutParams = viewPager.getLayoutParams();
        viewPagerLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewPagerLayoutParams.height = heightInPixels;  // Set height in pixels
        viewPager.setLayoutParams(viewPagerLayoutParams);

        // Adjust ShimmerLayout to match_parent width and 150dp height for tablet
        ViewGroup.LayoutParams shimmerLayoutParams = shimmerLayout.getLayoutParams();
        shimmerLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        shimmerLayoutParams.height = heightInPixels;  // Set height in pixels
        shimmerLayout.setLayoutParams(shimmerLayoutParams);
    }


    private void setupDotIndicators() {
        // Clear existing indicators
        dotIndicator.removeAllViews();
        for (int i = 0; i < bannerItems.size(); i++) {
            View dot = new View(requireContext());
            dot.setBackgroundResource(R.drawable.indicator_inactive); // Set the inactive dot drawable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0); // Set margins between dots
            dotIndicator.addView(dot, params);
        }

        // Set the first dot as active
        updateDotIndicators(0);

        // Add page change listener to update dots
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDotIndicators(position);
            }
        });
    }

    private void updateDotIndicators(int position) {
        // Memastikan fragment terhubung ke activity sebelum mengakses getResources()
        if (isAdded()) {
            for (int i = 0; i < dotIndicator.getChildCount(); i++) {
                View dot = dotIndicator.getChildAt(i);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dot.getLayoutParams();

                if (i == position) {
                    // Mengatur drawable dan ukuran untuk titik aktif
                    dot.setBackgroundResource(R.drawable.indicator_active); // Drawable untuk titik aktif
                    params.width = (int) getResources().getDimension(R.dimen.dot_active_size); // Ukuran titik aktif
                    params.height = (int) getResources().getDimension(R.dimen.dot_active_size);
                } else {
                    // Mengatur drawable dan ukuran untuk titik tidak aktif
                    dot.setBackgroundResource(R.drawable.indicator_inactive); // Drawable untuk titik tidak aktif
                    params.width = (int) getResources().getDimension(R.dimen.dot_inactive_size); // Ukuran titik tidak aktif
                    params.height = (int) getResources().getDimension(R.dimen.dot_inactive_size);
                }
                dot.setLayoutParams(params);
            }
        }
    }

    private void startAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() == bannerItems.size() - 1) {
                    viewPager.setCurrentItem(0, true);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
                handler.postDelayed(this, SLIDE_INTERVAL);
            }
        };
        handler.postDelayed(runnable, SLIDE_INTERVAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Stop auto slide
    }

    @Override
    public void onResume() {
        super.onResume();
        // Restart auto slide if banners are available
        if (bannerItems.size() > 0) {
            handler.postDelayed(runnable, SLIDE_INTERVAL);
        }
    }

    private void startShimmer() {
        shimmerLayout.startShimmer();
        shimmerLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        dotIndicator.setVisibility(View.GONE);
    }

    private void stopShimmer() {
        shimmerLayout.stopShimmer();
        shimmerLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        dotIndicator.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        stopShimmer();
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        dotIndicator.setVisibility(View.GONE);
    }

    private void setupViewPager() {
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(new LoopingPageTransformer());
    }

    // Custom PageTransformer for looping effect
    private class LoopingPageTransformer implements ViewPager2.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);
            } else {
                page.setAlpha(0);
            }
        }
    }
}
