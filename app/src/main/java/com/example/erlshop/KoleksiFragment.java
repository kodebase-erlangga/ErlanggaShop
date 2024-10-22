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

public class KoleksiFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView bookCoverImageView;
    private ImageAdapterKoleksi imageAdapterKoleksi;
    private List<String> imageUrls = new ArrayList<>(); // Menyimpan URL gambar dari API

    private static final String URL = "https://ebook.erlanggaonline.co.id/";
    private TextView errorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment
        View view = inflater.inflate(R.layout.fragment_koleksi, container, false);

        errorTextView = view.findViewById(R.id.errorTextView);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set RecyclerView untuk menggunakan GridLayoutManager dengan 3 kolom
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Memanggil method untuk mengambil gambar dari API
        fetchGallery();

        return view;
    }

    private void fetchGallery() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");
                            if (erlStatusId.equals("true")) {
                                JSONArray gallery = jsonResponse.getJSONArray("data");

                                // Ambil maksimal 20 gambar dari array JSON
                                for (int i = 0; i < gallery.length() && i < 20; i++) {
                                    JSONObject book = gallery.getJSONObject(i);
                                    String galeryCover = book.getString("galery_cover");
                                    String imageUrl = "https://e-library.erlanggaonline.co.id/upload/cover/" + galeryCover;
                                    imageUrls.add(imageUrl); // Tambahkan URL gambar ke list
                                }

                                // Set adapter setelah mendapatkan data gambar
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
                        bookCoverImageView.setImageBitmap(response);
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
