package com.example.erlshop;

public class BannerItem {
    private String imageUrl; // URL gambar dari API
    private String linkUrl;  // URL link untuk halaman yang dituju

    public BannerItem(String imageUrl, String linkUrl) {
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }
}
