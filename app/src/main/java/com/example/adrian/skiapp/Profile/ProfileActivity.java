package com.example.adrian.skiapp.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adrian.skiapp.Network.Connector;
import com.example.adrian.skiapp.Network.ImageDownloader;
import com.example.adrian.skiapp.R;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity
{
    private static final int NUM_PAGES = 3;
    private int resortId;
    private String[][] tabNames = {{"Mountain", "Town"},{"Bar", "Piste", "Lift"},
            {"Restaurant", "Hotel", "Rental"}};

    private PopupWindow rateResortWindow;

    private ViewPager pager;

    private PagerAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resort_profile);

        Intent intent = getIntent();
        new ImageDownloader(((ImageView)findViewById(R.id.resortProfileBg))).execute(intent.getStringExtra("URL"));

        resortId = intent.getIntExtra( "resortId" , 1 );
        ((TextView)findViewById(R.id.resortTitle)).setText(intent.getStringExtra("resortName"));

        pager = (ViewPager) findViewById(R.id.pager);
        pAdapter = new ScreenSlidePagerAdapter( getSupportFragmentManager() );
        pager.setAdapter( pAdapter );

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar( toolbar );
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return new PagerFragment().newInstance( tabNames[position], resortId );
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
                initiatePopupWindow();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initiatePopupWindow()
    {
        Button sendRate;
        final RatingBar bar;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_rate , (ViewGroup)findViewById(R.id.layout) );
        rateResortWindow = new PopupWindow( layout, 400, 180, true );
        rateResortWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        sendRate = (Button) layout.findViewById(R.id.rateButton);
        bar = (RatingBar) layout.findViewById(R.id.ratingBar);
        sendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector connect = new Connector(null,"POST");
                connect.setQuery(ProfileActivity.this.getString(R.string.insertRate) + resortId + ", " + bar.getRating() + ")");
                try {
                    connect.execute(new URL(getString(R.string.connectionURL) + getString(R.string.insertPhp)));
                }
                catch (MalformedURLException e){}
                rateResortWindow.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache(getApplicationContext());
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }
}
