package com.oztz.hackinglabmobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.oztz.hackinglabmobile.fragment.MainFragment;
import com.oztz.hackinglabmobile.fragment.NewsFragment;
import com.oztz.hackinglabmobile.fragment.ShareFragment;
import com.oztz.hackinglabmobile.fragment.SocialNewsFragment;
import com.oztz.hackinglabmobile.helper.TabListener;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AddTabsToActionBar();
    }

    private void AddTabsToActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tabItem_news)
                .setTabListener(new TabListener<NewsFragment>(
                        this, "artist", NewsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tabItem_social)
                .setTabListener(new TabListener<SocialNewsFragment>(
                        this, "album", SocialNewsFragment.class));
        actionBar.addTab(tab);

    }


}
