package com.pandey.popcorn4.mediaplayer.data;

import androidx.annotation.Nullable;

public class VideoDto {

    @Nullable
    private String id;

    @Nullable
    private String iso_639_1;

    @Nullable
    private String iso_3166_1;

    @Nullable
    private String key;

    @Nullable
    private String name;

    @Nullable
    private String site;

    private int size;

    @Nullable
    private String type;


    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(@Nullable String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    @Nullable
    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(@Nullable String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    public void setKey(@Nullable String key) {
        this.key = key;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getSite() {
        return site;
    }

    public void setSite(@Nullable String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }
}
