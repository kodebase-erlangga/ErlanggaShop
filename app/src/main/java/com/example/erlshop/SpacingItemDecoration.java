package com.example.erlshop;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int space; // Space in pixels

    public SpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        outRect.left = space;  // Left space for each item
        outRect.right = space; // Right space for each item
        // You can also set top and bottom spacing if needed
        outRect.top = space;
        outRect.bottom = space;
    }
}
