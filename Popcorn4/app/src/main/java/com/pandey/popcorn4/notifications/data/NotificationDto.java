package com.pandey.popcorn4.notifications.data;

public class NotificationDto {

    private String title;

    private String content;

    private String expandedTitle;

    private String expandedContent;

    private String imageUrl;

    private Object data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExpandedTitle() {
        return expandedTitle;
    }

    public void setExpandedTitle(String expandedTitle) {
        this.expandedTitle = expandedTitle;
    }

    public String getExpandedContent() {
        return expandedContent;
    }

    public void setExpandedContent(String expandedContent) {
        this.expandedContent = expandedContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
