package com.example.erlshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.erlshop.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> bannerList;
    private LayoutInflater layoutInflater;

    public BannerAdapter(Context context, List<Integer> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.banner_item, container, false);
        ImageView bannerImage = view.findViewById(R.id.bannerImage);
        bannerImage.setImageResource(bannerList.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
