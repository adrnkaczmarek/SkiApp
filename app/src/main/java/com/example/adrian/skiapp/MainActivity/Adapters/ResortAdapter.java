package com.example.adrian.skiapp.MainActivity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.skiapp.Network.ImageDownloader;
import com.example.adrian.skiapp.R;

public class ResortAdapter extends ArrayAdapter<RowResort>
{
    private Context context;
    private int layoutResourceId;
    private RowResort data[];

    public ResortAdapter(Context context, int layoutResourceId, RowResort[] data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public RowResort getDataIndex( int index )
    {
        return data[index];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        RowResortHolder holder = new RowResortHolder();

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder.txtTitle = (TextView)row.findViewById(R.id.resortTitle);
            holder.img = (ImageView)row.findViewById(R.id.resortView);
            row.setTag(holder);
        }
        else if(row!=null)
        {
            holder = (RowResortHolder)row.getTag();
        }
        holder.txtTitle.setText(data[position].getResortName());
        holder.id = data[position].getResortId();
        holder.img.setImageBitmap(null);
        //new ImageDownloader( holder.img ).execute(data[position].getIconUrl());

        return row;
    }

    class RowResortHolder
    {
        public TextView txtTitle;
        public ImageView img;
        public int id;
    }
}
