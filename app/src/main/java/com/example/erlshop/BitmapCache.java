package com.example.erlshop;
// BitmapCache.java
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapCache() {
        super(getDefaultLruCacheSize());
    }

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);  // Mengambil bitmap dari cache
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);  // Menyimpan bitmap ke cache
    }
}
