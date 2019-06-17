package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class ProductionCountriesDto implements Serializable {

    @Nullable
    private String iso_3166_1;

    @Nullable
    private String name;

    @Nullable
    public String getIso_3166_1()
    {
        return iso_3166_1;
    }

    public void setIso_3166_1 (@Nullable String iso_3166_1)
    {
        this.iso_3166_1 = iso_3166_1;
    }

    @Nullable
    public String getName ()
    {
        return name;
    }

    public void setName (@Nullable String name)
    {
        this.name = name;
    }
}
