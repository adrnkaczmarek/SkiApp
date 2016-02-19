package com.example.adrian.skiapp.MainActivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adrian.skiapp.beans.Table;
import com.example.adrian.skiapp.Network.Connector;
import com.example.adrian.skiapp.OnTaskCompleted;
import com.example.adrian.skiapp.beans.Resort;
import com.example.adrian.skiapp.R;
import com.example.adrian.skiapp.MainActivity.Adapters.ResortAdapter;
import com.example.adrian.skiapp.Profile.ProfileActivity;
import com.example.adrian.skiapp.MainActivity.Adapters.RowResort;

import java.net.URL;

public class ResortListFragment extends Fragment implements OnTaskCompleted
{
    private final String query = "SELECT * FROM resort";

    private ListView resortsList;
    private View listFragment;
    private ResortAdapter adapter;

    private static Resort[] resortsArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        listFragment = inflater.inflate(R.layout.resorts_list, container, false);

        String targetUrl = getString( R.string.connectionURL ) + getString(R.string.selectPhp);
        try {
            URL url = new URL(targetUrl);
            Connector connect = new Connector(this, getString(R.string.post));
            connect.setQuery(query);
            connect.execute(url);
        }
        catch( Exception e ){}

        return listFragment;
    }

    @Override
    public void onContentReceived(Table[] resorts)
    {
        resortsList = (ListView) listFragment.findViewById(R.id.resortsList);
        try {
            resortsArray = new Resort[resorts.length];

            RowResort[] resortList = new RowResort[resorts.length];
            for (int i = 0; i < resorts.length; i++) {
                resortsArray[i] = (Resort) resorts[i];
                resortList[i] = new RowResort(resortsArray[i].getImageUrl(), resortsArray[i].getName(), resortsArray[i].getId());
            }
            adapter = new ResortAdapter(getActivity(), R.layout.resorts_list_element, resortList);
            resortsList.setAdapter(adapter);
            resortsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RowResort resort = adapter.getDataIndex(position);
                    Intent intent = new Intent(ResortListFragment.this.getActivity(), ProfileActivity.class);
                    intent.putExtra("resortId", resort.getResortId());
                    intent.putExtra("resortName", resort.getResortName());
                    intent.putExtra("URL", resort.getIconUrl());
                    startActivity(intent);
                }
            });
        }catch(NullPointerException e){}
    }
}