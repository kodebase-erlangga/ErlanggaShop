package com.example.erlshop;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.facebook.shimmer.ShimmerFrameLayout;

public class KoleksiFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView shimmerRecyclerView;
    private TextView errorTextView;
    private ShimmerFrameLayout shimmerLayout;
    private ShimmerAdapterKoleksi shimmerAdapter;
    private ImageAdapterKoleksi imageAdapterKoleksi;
    private List<String> imageUrls = new ArrayList<>();

    private static final String URL = "https://ebook.erlanggaonline.co.id/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment
        View view = inflater.inflate(R.layout.fragment_koleksi, container, false);

        errorTextView = view.findViewById(R.id.errorTextView);
        recyclerView = view.findViewById(R.id.recyclerView);
        shimmerRecyclerView = view.findViewById(R.id.shimmerRecyclerView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        int orientation = getResources().getConfiguration().orientation;
        int columnCount = getResources().getBoolean(R.bool.isTablet) ?
                (orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 5) : 3;

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        shimmerRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));

        shimmerAdapter = new ShimmerAdapterKoleksi();
        shimmerRecyclerView.setAdapter(shimmerAdapter);
        shimmerLayout.startShimmer();

        fetchGallery();

        return view;
    }

    private void fetchGallery() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shimmerLayout.stopShimmer();
                        shimmerLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");
                            if (erlStatusId.equals("true")) {
                                JSONArray gallery = jsonResponse.getJSONArray("data");
                                for (int i = 0; i < gallery.length() && i < 20; i++) {
                                    JSONObject book = gallery.getJSONObject(i);
                                    String galeryCover = book.getString("galery_cover");
                                    String imageUrl = "https://e-library.erlanggaonline.co.id/upload/cover/" + galeryCover;
                                    imageUrls.add(imageUrl);
                                }
                                imageAdapterKoleksi = new ImageAdapterKoleksi(getContext(), imageUrls);
                                recyclerView.setAdapter(imageAdapterKoleksi);
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
                        shimmerLayout.stopShimmer();
                        shimmerLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        showError("Request error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_email", "mihsanrahman19@gmail.com");
                params.put("user_password", "ihsan111");
                params.put("user_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("user_jenjang", "SEMUA");
                params.put("halaman", "1");
                params.put("id", "14");
                params.put("aksi", "ambilgalery");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void showError(String message) {
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }
}
