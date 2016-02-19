package com.example.adrian.skiapp.MainActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.skiapp.beans.Table;
import com.example.adrian.skiapp.Network.Connector;
import com.example.adrian.skiapp.Network.ImageDownloader;
import com.example.adrian.skiapp.OnTaskCompleted;
import com.example.adrian.skiapp.beans.Resort;
import com.example.adrian.skiapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.net.URL;

public class BestResortFragment extends Fragment implements OnMapReadyCallback, OnTaskCompleted
{
    private static GoogleMap mapFragment;
    private View view;
    private LatLng location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try{
            connect();
            view = inflater.inflate(R.layout.best_resort, container, false);
            SupportMapFragment resortLocation = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.resortMap));
            resortLocation.getMapAsync(this);
        } catch (InflateException e) {}

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mapFragment = googleMap;
        while(location==null);
        mapFragment.addMarker(new MarkerOptions().position(location));
        mapFragment.moveCamera(CameraUpdateFactory.newLatLng(location));
        location = null;
    }

    @Override
    public void onDestroyView ()
    {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.resortMap);
        if (fragment != null)
        {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
        super.onDestroyView();
    }

    @Override
    public void onContentReceived(Table[] content)
    {
        Resort resort = (Resort) content[0];
        try {
            location = new LatLng(resort.getLatitude(), resort.getLongitude());
            ((TextView) getActivity().findViewById(R.id.resortName)).setText(resort.getName());
            ((TextView) getActivity().findViewById(R.id.resortCountry)).setText(resort.getCountry());
            (new ImageDownloader((ImageView) getActivity().findViewById(R.id.resortImage))).execute(resort.getImageUrl());
        }catch (NullPointerException e){}
    }

    public void connect()
    {
        String targetUrl = getString( R.string.connectionURL ) + getString(R.string.selectPhp);
        try {
            URL url = new URL(targetUrl);
            Connector connect =  new Connector(this, getString(R.string.post));
            connect.setQuery(getString(R.string.bestResortQ));
            connect.execute( url );
        }
        catch( Exception e ){}
    }
}
