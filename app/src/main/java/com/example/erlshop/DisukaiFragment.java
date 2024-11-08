package com.example.erlshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
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

public class DisukaiFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinksAdapter linksAdapter;
    private TextView errorTextView;
    private List<String> linkList = new ArrayList<>();
    private static final String URL = "https://ebook.erlanggaonline.co.id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disukai, container, false);

        // Initialize RecyclerView and TextView for error messages
        recyclerView = view.findViewById(R.id.recyclerViewDisukai);
        errorTextView = view.findViewById(R.id.errorTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch links from the database to display
        fetchLinksFromDatabase();
        fetchLinks();

        return view;
    }

    private void fetchLinksFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        linkList.clear();

        List<String> savedLinks = databaseHelper.getAllLinks();
        linkList.addAll(savedLinks);

        displayLinks();
    }

    private void fetchLinks() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String erlStatusId = jsonResponse.getString("erlStatusId");

                            if (erlStatusId.equals("true")) {
                                JSONArray linksArray = jsonResponse.getJSONArray("data");
                                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());

                                // Clear previous links from database before adding new ones
                                databaseHelper.clearLinks();

                                for (int i = 0; i < linksArray.length(); i++) {
                                    JSONObject linkItem = linksArray.getJSONObject(i);
                                    String linkUrl = linkItem.getString("url_produk");
                                    linkList.add(linkUrl);

                                    // Save link to database
                                    databaseHelper.addLink(linkUrl);
                                }
                                displayLinks(); // Display the URLs in the RecyclerView
                            } else {
                                showError("Error: " + jsonResponse.getString("message"));
                            }
                        } catch (JSONException e) {
                            showError("Parsing error: " + e.getMessage());
                            e.printStackTrace();
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

    private void displayLinks() {
        linksAdapter = new LinksAdapter(linkList);
        recyclerView.setAdapter(linksAdapter);
//        errorTextView.setVisibility(View.GONE);
    }

    private void showError(String message) {
        if (errorTextView != null) {
            errorTextView.setText(message);
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setTextColor(getResources().getColor(R.color.black, null));
        }
    }
}
