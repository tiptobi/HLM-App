package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Voting;
import com.oztz.hackinglabmobile.helper.JsonResult;

import java.util.ArrayList;
import java.util.List;

public class VotingDetailActivity extends ActionBarActivity implements JsonResult {

    Voting voting;
    TextView title;
    List<TextView> labels;
    List<SeekBar> scrollBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        voting = loadVoting();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_detail);
        title = (TextView) findViewById(R.id.voting_detail_votingName);

        labels = new ArrayList<TextView>();
        labels.add((TextView) findViewById(R.id.voting_detail_scroll_label1));
        labels.add((TextView) findViewById(R.id.voting_detail_scroll_label2));
        labels.add((TextView) findViewById(R.id.voting_detail_scroll_label3));

        scrollBars = new ArrayList<SeekBar>();
        scrollBars.add((SeekBar) findViewById(R.id.voting_detail_scrollbar1));
        scrollBars.add((SeekBar) findViewById(R.id.voting_detail_scrollbar2));
        scrollBars.add((SeekBar) findViewById(R.id.voting_detail_scrollbar3));
        SetupView();
    }

    private void SetupView(){
        title.setText(voting.name);
        for(int i=0; i<scrollBars.size(); i++){
            labels.get(i).setText(String.valueOf(voting.sliderMaxValue / 2) + "/" + String.valueOf(voting.sliderMaxValue));
            scrollBars.get(i).setMax(voting.sliderMaxValue);
            scrollBars.get(i).setProgress(voting.sliderMaxValue / 2);
            final int index = i;
            scrollBars.get(i).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    labels.get(index).setText(String.valueOf(progress) + "/" + String.valueOf(voting.sliderMaxValue));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    private Voting loadVoting(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("voting"), Voting.class);
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
        actionBar.setTitle(getResources().getString(R.string.navigationItem_voting));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {

    }
}
