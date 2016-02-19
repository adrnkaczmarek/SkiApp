package com.example.adrian.skiapp.Network;

import com.example.adrian.skiapp.beans.Resort;
import com.example.adrian.skiapp.beans.Table;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONParser
{
    private static String name = "Name: ";
    private static String street = "Street: ";
    private static String rate = "Rate: ";
    private static String mountainName = "Mountain name: ";
    private static String elevation = "Elevation: ";
    private static String type = "Type: ";
    private static String length = "Length: ";
    private static String difficulty = "Difficulty: ";
    private static String website = "Website: ";

    public static Table[] parseMessage( InputStream input ) throws NullPointerException
    {
        int itr = 0;
        Table[] objectArray = null;
        String result = "";
        JSONArray jsonArray = null;
        JSONObject jsonObj = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }

            result = sb.toString();
            jsonArray = new JSONArray( result );

            if ( result.contains( "ResortName" ) )
            {
                objectArray = new Resort[jsonArray.length()];
                while (itr < jsonArray.length())
                {
                    jsonObj = jsonArray.getJSONObject(itr);
                    objectArray[itr] = new Resort(jsonObj.getInt("ResortID"), jsonObj.getString("ResortName"), jsonObj.getString("Country"),
                            (float) jsonObj.getDouble("Latitude"), (float) jsonObj.getDouble("Longitude"), jsonObj.getString("Website"),
                            jsonObj.getString("ImageUrl"));
                    if (jsonObj.has("Rate"))
                    {
                        ((Resort) objectArray[itr]).setRate((float) jsonObj.getDouble("Rate"));
                    }
                    itr++;
                }
            }
            else
            {
                objectArray = new Table[jsonArray.length()];
                if ( result.contains( "MountainName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("MountainID") , mountainName + jsonObj.getString("MountainName")
                                + "\n"+ elevation + jsonObj.getInt( "Elevation" ));
                        itr++;
                    }
                }
                else if ( result.contains( "TownName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("TownID") , name + jsonObj.getString("TownName"));
                        itr++;
                    }
                }
                else if ( result.contains( "PisteName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("PisteID") , length + jsonObj.getInt("Length")
                                + "\n" + difficulty + jsonObj.getString("Level") + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
                else if ( result.contains( "BarName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("BarID") , name + jsonObj.getString("BarName")
                                + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
                else if ( result.contains( "LiftName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("LiftID") , name + jsonObj.getString("LiftName")
                                + "\n" + type + jsonObj.getString( "Type" ) + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
                else if ( result.contains( "HotelName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("HotelID") , name + jsonObj.getString("HotelName")
                                + "\n" + street + jsonObj.getString( "Street" ) + "\n" + website + jsonObj.getString("Website")
                                + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
                else if ( result.contains( "RestaurantName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("RestaurantID") , name + jsonObj.getString("RestaurantName")
                                + "\n" + street + jsonObj.getString("Street") + "\n" + type + jsonObj.getString("Type")
                                + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
                else if ( result.contains( "RentalName" ) )
                {
                    while (itr < jsonArray.length())
                    {
                        jsonObj = jsonArray.getJSONObject(itr);
                        objectArray[itr] = new Table( jsonObj.getInt("RentalID") , name + jsonObj.getString("RentalName")
                                + "\n" + street + jsonObj.getString("Street") + "\n" + rate + jsonObj.getDouble("Rate"));
                        itr++;
                    }
                }
            }

            return objectArray;
        }
        catch( Exception ex ){}
        return null;
    }
}
