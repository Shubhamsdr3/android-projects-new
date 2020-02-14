package com.pandey.popcorn4.appdata;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ConfigResponseDto implements Serializable {

    @Nullable
    private ImageDto images;

    @Nullable
    private List<String> change_keys;

    @Nullable
    public ImageDto getImages() {
        return images;
    }

    @Nullable
    public List<String> getChange_keys() {
        return change_keys;
    }

    static class ImageDto implements Serializable {

        @Nullable
        private String base_url;

        @Nullable
        private String secure_base_url;

        @Nullable
        private List<String> backdrop_sizes;

        @Nullable
        private List<String> logo_sizes;

        @Nullable
        private List<String> poster_sizes;

        @Nullable
        private List<String> profile_sizes;

        @Nullable
        private List<String> still_sizes;

        public ImageDto(@Nullable String base_url,
                        @Nullable String secure_base_url,
                        @Nullable List<String> backdrop_sizes,
                        @Nullable List<String> logo_sizes,
                        @Nullable List<String> poster_sizes,
                        @Nullable List<String> profile_sizes,
                        @Nullable List<String> still_sizes) {
            this.base_url = base_url;
            this.secure_base_url = secure_base_url;
            this.backdrop_sizes = backdrop_sizes;
            this.logo_sizes = logo_sizes;
            this.poster_sizes = poster_sizes;
            this.profile_sizes = profile_sizes;
            this.still_sizes = still_sizes;
        }

        @Nullable
        public String getBase_url() {
            return base_url;
        }

        @Nullable
        public String getSecure_base_url() {
            return secure_base_url;
        }

        @Nullable
        public List<String> getBackdrop_sizes() {
            return backdrop_sizes;
        }

        @Nullable
        public List<String> getLogo_sizes() {
            return logo_sizes;
        }

        @Nullable
        public List<String> getPoster_sizes() {
            return poster_sizes;
        }

        @Nullable
        public List<String> getProfile_sizes() {
            return profile_sizes;
        }

        @Nullable
        public List<String> getStill_sizes() {
            return still_sizes;
        }
    }

}
