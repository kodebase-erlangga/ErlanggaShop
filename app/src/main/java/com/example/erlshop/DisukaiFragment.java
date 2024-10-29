package com.example.erlshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DisukaiFragment extends Fragment {
    private RecyclerView recyclerViewDisukai;
    private DisukaiAdapter disukaiAdapter;
    private DatabaseHelper databaseHelper;
    private List<String> productLinks;

    public DisukaiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disukai, container, false);

        // Initialize RecyclerView
        recyclerViewDisukai = view.findViewById(R.id.recyclerViewDisukai);
        recyclerViewDisukai.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Load URLs from SQLite
        loadProductLinks();

        return view;
    }

    private void loadProductLinks() {
        productLinks = databaseHelper.getAllLinks(); // Ambil semua link dari SQLite
        disukaiAdapter = new DisukaiAdapter(productLinks); // Inisialisasi adapter
        recyclerViewDisukai.setAdapter(disukaiAdapter); // Set adapter ke RecyclerView
    }
}
