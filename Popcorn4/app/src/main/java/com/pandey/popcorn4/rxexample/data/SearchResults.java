package com.pandey.popcorn4.rxexample.data;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {
    private List<Geoname> geonames = new ArrayList<>();

    private static class Geoname {
        private String lat;
        private String lng;
        private Integer geonameId;
        private Integer population;
        private String countryCode;
        private String name;
    }

}