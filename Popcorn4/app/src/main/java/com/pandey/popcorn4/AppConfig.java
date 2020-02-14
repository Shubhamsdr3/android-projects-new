package com.pandey.popcorn4;

public class AppConfig {

    private static final String GOOGLE_API_KEY = "AIzaSyA0hb5TcUksPsMlAKVnBRFj41LqwhMCaSg";

    private static final String MOVIE_API_KEY = "8d0bbe47677faff5e8d33e89d1aac537";

    private static final String YOUTUBE_LINK = "https://www.youtube.com/watch?v=";

    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original";

    private static final String ENG_LANG = "en-US";

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String CONFIG_BASE_URL = "https://api.themoviedb.org/3/configuration";

    private static final String APPEND_VIDEO_WITH_RESPONSE = "videos";

    private static final String MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/search/movie?page=1";

    public static String getMovieSearchUrl() {
        return MOVIE_SEARCH_URL;
    }

    public static String getMovieBaseUrl() {
        return MOVIE_BASE_URL;
    }

    public static String getAppendVideoWithResponse() {
        return APPEND_VIDEO_WITH_RESPONSE;
    }

    public static String getEngLang() {
        return ENG_LANG;
    }

    public static String getGoogleApiKey() {
        return GOOGLE_API_KEY;
    }

    public static String getMovieApiKey() {
        return MOVIE_API_KEY;
    }

    public static String getYoutubeLink() {
        return YOUTUBE_LINK;
    }

    public static String getMovieImageBaseUrl() {
        return  MOVIE_IMAGE_BASE_URL;
    }

    public static String getConfigUrl() {
        return CONFIG_BASE_URL;
    }
}
