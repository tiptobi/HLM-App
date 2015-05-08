package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Slider;
import com.oztz.hackinglabmobile.businessclasses.Vote;
import com.oztz.hackinglabmobile.businessclasses.Voting;
import com.oztz.hackinglabmobile.database.DbOperator;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.PostTask;
import com.oztz.hackinglabmobile.helper.RequestTask;

import java.util.ArrayList;
import java.util.List;

public class VotingDetailActivity extends ActionBarActivity implements JsonResult {

    Voting voting;
    TextView title;
    List<TextView> labels;
    List<SeekBar> scrollBars;
    Button voteButton;
    LinearLayout scrollBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        voting = loadVoting();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_detail);
        scrollBarHolder = (LinearLayout) findViewById(R.id.voting_detail_scrollbar_holder);
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "voting/" + voting.votingID + "/sliders", "sliders");
        title = (TextView) findViewById(R.id.voting_detail_votingName);
        voteButton = (Button) findViewById(R.id.voting_button_vote);
        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVoting();
            }
        });

        labels = new ArrayList<TextView>();
        scrollBars = new ArrayList<SeekBar>();
    }

    private void postVoting(){
        DbOperator operator = new DbOperator(getApplicationContext());
        String qrCode = operator.getQrCode("jury", 1);
        for(int i=0; i<scrollBars.size(); i++){
            SeekBar s = scrollBars.get(i);
            if(qrCode != null){ //Ist ein Jurymitglied
                Vote v = new Vote(0, s.getProgress(), true, (int)s.getTag(), App.userId);
                String json = new Gson().toJson(v, Vote.class);
                new PostTask(this).execute(getResources().getString(R.string.rootURL) + "vote", json, qrCode);
            } else { //Ist kein Jurymitglied
                Vote v = new Vote(0, s.getProgress(), false, Integer.parseInt((String) s.getTag()), App.userId);
                String json = new Gson().toJson(v, Vote.class);
                new PostTask(this).execute(getResources().getString(R.string.rootURL) + "vote", json);
            }
        }
    }

    private void SetupView(Slider[] sliders){
        title.setText(voting.name);
        for(int i=0; i<sliders.length; i++){
            final int index = i;

            SeekBar s = new SeekBar(getApplicationContext());
            s.setMax(voting.sliderMaxValue);
            s.setIndeterminate(false);
            s.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            s.setProgress(voting.sliderMaxValue / 2);
            s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            s.setTag(sliders[i].sliderID);
            scrollBars.add(s);

            TextView scrollBarValue = new TextView(getApplicationContext());
            scrollBarValue.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scrollBarValue.setText(String.valueOf(voting.sliderMaxValue / 2) + "/" + String.valueOf(voting.sliderMaxValue));
            scrollBarValue.setTextColor(Color.BLACK);
            labels.add(scrollBarValue);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setGravity(Gravity.CENTER_VERTICAL);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(s);
            layout.addView(scrollBarValue);

            TextView sliderTitle = new TextView(getApplicationContext());
            sliderTitle.setTextAppearance(getApplicationContext(), R.style.Base_TextAppearance_AppCompat_Large);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 20, 0, 0);
            sliderTitle.setLayoutParams(lp);
            sliderTitle.setTextColor(Color.BLACK);
            sliderTitle.setText(sliders[i].name);

            scrollBarHolder.addView(sliderTitle);
            scrollBarHolder.addView(layout);
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
        try {
            //Slider[] sliders = new Gson().fromJson(JsonString, Slider[].class);
            Slider[] sliders = {new Slider(1, 1, 2, "Language"), new Slider(2, 1, 1, "Graphics"), new Slider(3, 1, 3, "Solution")};
            SetupView(sliders);

        } catch(Exception e){
            Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT);
        }
    }
}