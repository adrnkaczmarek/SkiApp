package com.example.adrian.skiapp.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adrian.skiapp.R;

public class PagerFragment extends Fragment
{
    private static final String param = "tabsNames";
    private static final String intParam = "resortId";

    private String[] tabNames;
    private int resortId;

    private FragmentTabHost tabs;

    public static PagerFragment newInstance(String[] tabsNames, int id)
    {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putStringArray(param, tabsNames);
        args.putInt(intParam, id);
        fragment.setArguments(args);
        return fragment;
    }

    public PagerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabNames = getArguments().getStringArray(param);
            resortId = getArguments().getInt(intParam);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle argsToPass;
        String joinString = "";

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        tabs = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        tabs.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent );

        if(tabNames[0] == "Town" || tabNames[0] == "Mountain")
        {
            joinString = " " + getString(R.string.secondLevel) + " ";
        }
        else if(tabNames[0] == "Bar" || tabNames[0] == "Piste" || tabNames[0] == "Lift")
        {
            joinString = " " + getString(R.string.thirdLevelMountain) + " ";
        }
        else
        {
            joinString = " " + getString(R.string.thirdLevelTown) + " ";
        }

        for( int i = 0 ; i < tabNames.length ; i++ )
        {
            argsToPass =  new Bundle();
            argsToPass.putString("query", getString(R.string.selectFrom)+" "+tabNames[i]+joinString+resortId);
            tabs.addTab(tabs.newTabSpec(tabNames[i]).setIndicator(tabNames[i]), ProfileListFragment.class, argsToPass );
        }

        return view;
    }
}
