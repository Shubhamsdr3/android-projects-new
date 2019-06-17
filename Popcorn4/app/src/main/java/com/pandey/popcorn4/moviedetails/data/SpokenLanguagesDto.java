package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class SpokenLanguagesDto implements Serializable {

    @Nullable
    private String name;

    @Nullable
    private String iso_639_1;

    @Nullable
    public String getName ()
    {
        return name;
    }

    public void setName (@Nullable String name)
    {
        this.name = name;
    }

    @Nullable
    public String getIso_639_1()
    {
        return iso_639_1;
    }

    public void setIso_639_1(@Nullable String iso_639_1)
    {
        this.iso_639_1 = iso_639_1;
    }
}
