package com.example.erlshop;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    // Ukuran cache maksimum (dalam byte)
    public BitmapCache() {
        super(getDefaultLruCacheSize());
    }

    // Mendapatkan ukuran default untuk cache
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8; // Mengambil 1/8 dari total memori untuk cache
        return cacheSize;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url); // Mengambil bitmap dari cache
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap); // Menyimpan bitmap ke cache
    }
}
