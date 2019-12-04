package com.pandey.popcorn4.mediaplayer.data;

import java.util.List;

public class VideoResponseDto {

    private int id;

    private List<VideoDto> results;

    public int getId() {
        return id;
    }

    public List<VideoDto> getResults() {
        return results;
    }
}
