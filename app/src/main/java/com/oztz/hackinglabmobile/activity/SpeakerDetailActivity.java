package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonResult;

public class SpeakerDetailActivity extends ActionBarActivity implements JsonResult {

    Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        speaker = loadSpeaker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_detail);
    }

    private Speaker loadSpeaker(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("speaker"), Speaker.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speaker_detail, menu);
        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(speaker.name);
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestType) {

    }
}
