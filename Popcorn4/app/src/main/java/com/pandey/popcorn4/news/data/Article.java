package com.pandey.popcorn4.news.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class Article implements Serializable {

    @Nullable
    private String publishedAt;

    @Nullable
    private String author;

    @Nullable
    private String urlToImage;

    @Nullable
    private String description;

    @Nullable
    private Source source;

    @Nullable
    private String title;

    @Nullable
    private String url;

    @Nullable
    private String content;

    @Nullable
    public String getPublishedAt() {
        return publishedAt;
    }

    @Nullable
    public String getAuthor() {
        return author;
    }

    @Nullable
    public String getUrlToImage() {
        return urlToImage;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public Source getSource() {
        return source;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setPublishedAt(@Nullable String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setAuthor(@Nullable String author) {
        this.author = author;
    }

    public void setUrlToImage(@Nullable String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setSource(@Nullable Source source) {
        this.source = source;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    public class Source {

        private String name;

        private String id;

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getId () {
            return id;
        }

        public void setId (String id) {
            this.id = id;
        }

    }
}

