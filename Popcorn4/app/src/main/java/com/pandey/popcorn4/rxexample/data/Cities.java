package com.pandey.popcorn4.rxexample.data;

import java.util.List;

public class Cities {

    private List<City> results;

    private static class City {
        private String city;
        private String country;
        private Double distance;
        private Integer id;
        private Double lat;
        private String localizedCountryName;
        private Double lon;
        private Integer memberCount;
        private Integer ranking;
        private String zip;
    }
}
