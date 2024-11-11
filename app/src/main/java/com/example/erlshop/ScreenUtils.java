package com.example.erlshop;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

    public class ScreenUtils {

        // Fungsi untuk mendapatkan lebar dan tinggi layar dalam pixel
        public static int[] getScreenSizeInPixels(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidthPx = displayMetrics.widthPixels;
            int screenHeightPx = displayMetrics.heightPixels;
            return new int[]{screenWidthPx, screenHeightPx};
        }

        // Fungsi untuk mendapatkan lebar dan tinggi layar dalam dp
        public static int[] getScreenSizeInDp(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidthDp = (int) (displayMetrics.widthPixels / displayMetrics.density);
            int screenHeightDp = (int) (displayMetrics.heightPixels / displayMetrics.density);
            return new int[]{screenWidthDp, screenHeightDp};
        }

        // Fungsi untuk mengecek apakah perangkat adalah tablet berdasarkan ukuran layar
        public static boolean isTablet(Context context) {
            int[] screenSize = getScreenSizeInDp(context);
            int screenWidthDp = screenSize[0];
            int screenHeightDp = screenSize[1];

            if (screenHeightDp >= 600 && screenWidthDp >= 800) {
                Log.d("ScreenUtils", "Perangkat terdeteksi sebagai tablet dalam orientasi landscape");
                return true;
            } else if (screenWidthDp >= 600 && screenHeightDp >= 800) {
                Log.d("ScreenUtils", "Perangkat terdeteksi sebagai tablet dalam orientasi portrait");
                return true;
            }
            Log.d("ScreenUtils", "Perangkat terdeteksi sebagai non-tablet");
            return false;
        }
    }
