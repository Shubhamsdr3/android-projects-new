package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

public class ProductionCompaniesDto implements Serializable {

    private int id;

    @Nullable
    private String logo_path;

    @Nullable
    private String name;

    @Nullable
    private String origin_country;

    @Nullable
    public String getLogo_path ()
    {
        return logo_path;
    }

    @Nullable
    public void setLogo_path (@Nullable String logo_path)
    {
        this.logo_path = logo_path;
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

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getOrigin_country ()
    {
        return origin_country;
    }

    public void setOrigin_country (String origin_country)
    {
        this.origin_country = origin_country;
    }
}
