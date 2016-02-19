package com.example.adrian.skiapp.MainActivity.Adapters;

public class RowResort
{
    private String iconUrl;
    private String resortName;
    private int resortId;

    public String getIconUrl() {
        return iconUrl;
    }

    public String getResortName() {
        return resortName;
    }

    public int getResortId() {
        return resortId;
    }


    public RowResort( String url , String name, int id )
    {
        this.iconUrl = url;
        this.resortName = name;
        this.resortId = id;
    }
}
