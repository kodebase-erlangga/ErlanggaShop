package com.example.erlshop;

public class BannerItem {
    private String bannerCover;  // The URL of the banner image
    private String linkUrl;      // The URL to which the banner links (if any)

    public BannerItem(String bannerCover, String linkUrl) {
        this.bannerCover = bannerCover;
        this.linkUrl = linkUrl;
    }

    // Getter method for the banner URL
    public String getBannerCover() {
        return bannerCover;
    }

    // Getter method for the link URL (optional if you need to use it)
    public String getLinkUrl() {
        return linkUrl;
    }
}
