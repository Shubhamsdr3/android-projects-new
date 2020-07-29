package com.pandey.popcorn4.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.moviedetails.data.MovieDto;

import java.lang.reflect.Type;
import java.util.List;

public class DataUtils {

    public static List<MoviesResponseDto> parseJSONArrayToList(JsonArray jsonArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MoviesResponseDto>>(){}.getType();
        return gson.fromJson(jsonArray, type);
    }

    public static MovieDto parseJSON(JsonObject jsonObject) {
        Gson gson = new Gson();
        Type type = new TypeToken<MovieDto>(){}.getType();
        return gson.fromJson(jsonObject, type);
    }
}
