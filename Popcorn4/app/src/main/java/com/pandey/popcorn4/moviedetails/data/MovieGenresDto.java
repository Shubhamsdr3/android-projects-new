package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class MovieGenresDto implements Serializable {

    @Nullable
    private String name;

    private int id;

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
