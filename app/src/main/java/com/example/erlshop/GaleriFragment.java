package com.example.erlshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.erlshop.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GaleriFragment extends Fragment {

    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;
    private LinearLayout dotIndicatorLayout;
    private Handler handler;
    private Runnable updateRunnable;
    private int currentPage = 0;
    private Timer timer;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galeri, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        dotIndicatorLayout = view.findViewById(R.id.dotIndicatorLayout);

        // Tambahkan gambar banner dari drawable
        ArrayList<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner1);
        bannerList.add(R.drawable.banner2);
        bannerList.add(R.drawable.banner3);
        bannerList.add(R.drawable.banner4);

        // Setup adapter
        bannerAdapter = new BannerAdapter(getContext(), bannerList);
        viewPager.setAdapter(bannerAdapter);

        // Inisialisasi dot indicator
        setupDotIndicator(bannerList.size());

        // Listener untuk update dot saat halaman berubah
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateDotIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Timer untuk auto-slide
        handler = new Handler(Looper.getMainLooper());
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == bannerList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        // Mulai auto-slide dengan interval 3 detik
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(updateRunnable);
            }
        }, 3000, 3000);

        return view;
    }

    // Setup indikator titik sesuai jumlah gambar
    private void setupDotIndicator(int count) {
        dots = new ImageView[count];
        dotIndicatorLayout.removeAllViews();  // Clear any existing dots

        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.dot_unselected);  // Initial state
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);  // Margins between dots
            dotIndicatorLayout.addView(dots[i], params);
        }

        dots[0].setImageResource(R.drawable.dot_selected);  // Set the first dot as selected
    }

    // Update indikator titik sesuai halaman yang sedang ditampilkan
    private void updateDotIndicator(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setImageResource(R.drawable.dot_selected);
            } else {
                dots[i].setImageResource(R.drawable.dot_unselected);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }
}
