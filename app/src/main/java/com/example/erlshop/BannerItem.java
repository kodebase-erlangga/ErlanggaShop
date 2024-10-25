package com.example.erlshop;

public class BannerItem {
    private String imageUrl;
    private String linkUrl;

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
