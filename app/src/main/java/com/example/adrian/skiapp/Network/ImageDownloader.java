package com.example.adrian.skiapp.Network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap>
{
    private ImageView imageToLoad;

    public ImageDownloader(ImageView image)
    {
        imageToLoad = image;
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        InputStream stream = null;
        try{
            stream = (new URL(params[0])).openStream();
        }
        catch (Exception e){}
        return BitmapFactory.decodeStream(stream);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        try {
            imageToLoad.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false));
        }catch (NullPointerException e){}
    }
}
