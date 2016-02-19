package com.example.adrian.skiapp.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adrian.skiapp.beans.Table;
import com.example.adrian.skiapp.Network.Connector;
import com.example.adrian.skiapp.OnTaskCompleted;
import com.example.adrian.skiapp.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileListFragment extends Fragment implements OnTaskCompleted
{
    private String query;
    View currentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);
        currentView = view;
        String targetURL = getString(R.string.connectionURL) + getString(R.string.selectPhp);
        query = this.getArguments().getString("query");

        try {
            URL url = new URL(targetURL);
            Connector connect = new Connector(this, getString(R.string.post));
            connect.setQuery( query );
            connect.execute(url);
        }
        catch(Exception e){}
        return view;
    }

    @Override
    public void onContentReceived(Table[] objects)
    {
        try{
            ArrayAdapter<String> adapter;
            ListView listView = (ListView) currentView.findViewById(R.id.contentList);

            String[] List = new String[objects.length];

            for( int i=0 ; i < objects.length ; i++ )
            {
                List[i] = objects[i].getName();
            }

            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList( List));

            adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_profile_list_element, list);
            listView.setAdapter(adapter);
        }
        catch(NullPointerException e){}
    }
}