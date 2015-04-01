package com.oztz.hackinglabmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.oztz.hackinglabmobile.fragment.AgendaFragment;
import com.oztz.hackinglabmobile.fragment.ChallengesFragment;
import com.oztz.hackinglabmobile.fragment.ConferenceFragment;
import com.oztz.hackinglabmobile.fragment.MainFragment;
import com.oztz.hackinglabmobile.fragment.ScoringFragment;
import com.oztz.hackinglabmobile.fragment.ShareFragment;
import com.oztz.hackinglabmobile.fragment.SpeakerFragment;
import com.oztz.hackinglabmobile.fragment.TeamsFragment;
import com.oztz.hackinglabmobile.fragment.VotingFragment;

public class BaseActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
    int[] titleArray = {R.string.navigationItem_news,
            R.string.navigationItem_share,
            R.string.navigationItem_conference,
            R.string.navigationItem_agenda,
            R.string.navigationItem_speaker,
            R.string.navigationItem_voting,
            R.string.navigationItem_scoring,
            R.string.navigationItem_challenges,
            R.string.navigationItem_teams
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		Log.d("DEBUG", "onNavigationDrawerItemSelected("+ String.valueOf(position) +")");

        Fragment fragment;
        switch(position){
            case 0:
                fragment = MainFragment.newInstance(position+1);
                break;
            case 1:
                fragment = ShareFragment.newInstance(position+1);
                break;
            case 2:
                fragment = ConferenceFragment.newInstance(position+1);
                break;
            case 3:
                fragment = AgendaFragment.newInstance(position+1);
                break;
            case 4:
                fragment = SpeakerFragment.newInstance(position+1);
                break;
            case 5:
                fragment = VotingFragment.newInstance(position+1);
                break;
            case 6:
                fragment = ScoringFragment.newInstance(position+1);
                break;
            case 7:
                fragment = ChallengesFragment.newInstance(position+1);
                break;
            case 8:
                fragment = TeamsFragment.newInstance(position+1);
                break;
            default:
                fragment = new MainFragment();
        }
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						fragment).commit();
	}

	public void onSectionAttached(int number) {
		Log.d("DEBUG", "onSectionAttached("+ String.valueOf(number) +")");
		mTitle = getResources().getString(titleArray[number-1]);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("DEBUG", "onCreateOptionsMenu()");
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

}
