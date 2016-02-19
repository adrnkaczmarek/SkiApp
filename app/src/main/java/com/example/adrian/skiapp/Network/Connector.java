package com.example.adrian.skiapp.Network;

import android.os.AsyncTask;

import com.example.adrian.skiapp.OnTaskCompleted;
import com.example.adrian.skiapp.beans.Table;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector extends AsyncTask<URL, Void, Table[]>
{
    private HttpURLConnection urlConnection;
    private OnTaskCompleted callbackObject;
    private String httpMethod, query;

    public Connector( OnTaskCompleted listener, String method )
    {
        callbackObject = listener;
        httpMethod = method;
    }

    @Override
    protected Table[] doInBackground( URL... urls)
    {
        try
        {
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod(httpMethod);

            if( httpMethod.equals("POST"))
            {
                urlConnection.setDoOutput(true);
                byte[] byteArray = query.getBytes();
                DataOutputStream wr = new DataOutputStream( urlConnection.getOutputStream());
                wr.write( byteArray );
                wr.close();
            }

            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            Table[] a = JSONParser.parseMessage(in);
            return a;
        }
        catch( Exception e){}
        return null;
    }

    @Override
    protected void onPostExecute(Table[] objects)
    {
        if (this.callbackObject!=null) {
            this.callbackObject.onContentReceived(objects);
        }
    }

    public void setQuery(String query)
    {
        this.query = "query="+query;
    }
}