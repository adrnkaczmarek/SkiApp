package com.example.adrian.skiapp.beans;

public class Resort extends Table
{
    private float longitude;
    private String website;
    private String imageUrl;
    private String country;
    private float rate;
    private float latitude;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCountry() {
        return country;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getWebsite() {
        return website;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Resort( int id, String name, String country, float ltd, float lng, String url, String image )
    {
        super( id, name );
        this.country = country;
        this.latitude = ltd;
        this.longitude = lng;
        this.website = url;
        this.imageUrl = image;
    }
}
