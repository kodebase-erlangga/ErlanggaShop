package com.example.erlshop;

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

public class GaleriFragment extends Fragment {

    private RecyclerView recyclerView;
    private BannerAdapter bannerAdapter;
    private List<String> imageUrls = new ArrayList<>(); // Menyimpan URL gambar dari API
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private TextView errorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment
        View view = inflater.inflate(R.layout.fragment_galeri, container, false);

        errorTextView = view.findViewById(R.id.errorTextView);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Menambahkan layout manager agar gambar-gambar ditampilkan dalam grid
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 kolom

        // Memanggil method untuk mengambil gambar dari API
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

                                // Ambil maksimal 20 gambar dari array JSON
                                for (int i = 0; i < bannerArray.length() && i < 10; i++) {
                                    JSONObject bannerItem = bannerArray.getJSONObject(i);
                                    if (bannerItem.has("url_banner")) {  // Validasi jika key ada
                                        String bannerCover = bannerItem.getString("url_banner");
                                        imageUrls.add(bannerCover); // Tambahkan URL gambar ke list
                                    }
                                }
                                // Set adapter setelah mendapatkan data gambar
                                bannerAdapter = new BannerAdapter(getContext(), imageUrls);
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
                params.put("user_email", "mihsanrahman19@gmail.com");  // Ganti dengan otentikasi aman
                params.put("user_password", "ihsan111");               // Ganti dengan otentikasi aman
                params.put("galery_device_id", "fae3876e39143557");
                params.put("user_version", "proteksi");
                params.put("id", "100"); // Sesuai permintaan
                params.put("aksi", "ambilbanner_slider");
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    // Method untuk menampilkan error
    private void showError(String message) {
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }

    // Method untuk menampilkan gambar di ImageView (opsional jika diperlukan)
    private void loadImage(String imageUrl) {
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Penggunaan respons gambar
                    }
                },
                0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError("Image load error: " + error.getMessage());
                    }
                });
        Volley.newRequestQueue(getContext()).add(imageRequest);
    }
}
