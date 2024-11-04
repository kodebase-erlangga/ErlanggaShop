package com.example.erlshop;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;

public class DataFetcher {
    private static final String URL = "https://ebook.erlanggaonline.co.id";
    private Context context;
    private DatabaseHelper dbHelper;

    public DataFetcher(Context context, DatabaseHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public void fetchData(final FetchDataCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray linksArray = response.getJSONArray("links");
                            for (int i = 0; i < linksArray.length(); i++) {
                                String urlProduk = linksArray.getString(i);
                                dbHelper.insertLink(urlProduk);
                            }
                            callback.onFetchComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("DataFetcher", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("DataFetcher", "Volley error: " + error.getMessage());
                    }
                }
        ) {
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

        requestQueue.add(jsonObjectRequest);
    }

    public interface FetchDataCallback {
        void onFetchComplete();
    }
}
