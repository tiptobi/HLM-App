package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Team;
import com.oztz.hackinglabmobile.helper.JsonResult;

public class TeamDetailActivity extends ActionBarActivity implements JsonResult {

    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        team = loadTeam();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_detail);
        SetupView();
    }

    private void SetupView(){

    }

    private Team loadTeam(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("team"), Team.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voting_detail, menu);
        restoreActionBar();
        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(team.groupname);
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {

    }
}
