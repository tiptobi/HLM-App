package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonResult;

public class SpeakerDetailActivity extends ActionBarActivity implements JsonResult {

    Speaker speaker;
    TextView title, description;
    ImageView flag, speakerImage;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        speaker = loadSpeaker();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_detail);
        title = (TextView) findViewById(R.id.speakerDetail_Title_TextView);
        description = (TextView) findViewById(R.id.speakerDetail_Description_TextView);
        flag = (ImageView) findViewById(R.id.speakerDetail_Flag_ImageView);
        speakerImage = (ImageView) findViewById(R.id.speakerDetail_speakerImage);
        SetupView();
    }

    private void SetupView(){
        if(speaker.title != null){
            title.setText(speaker.title + " " + speaker.name);
        } else{
            title.setText(speaker.name);
        }
        flag.setImageURI(Uri.parse("android.resource://com.oztz.hackinglabmobile/drawable/flag_"
                + speaker.nationality.toLowerCase()));
        description.setText(speaker.description);
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                //.imageDownloader(new AuthImageDownloader(getApplicationContext(), 5000, 20000))
                .diskCacheFileCount(50)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true).build())
                .build();
        imageLoader.init(config);
        imageLoader.displayImage("http://www.moviepilot.de/files/images/0798/4048/Mr._Bean.jpg", speakerImage);
    }

    private Speaker loadSpeaker(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("speaker"), Speaker.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speaker_detail, menu);
        restoreActionBar();
        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.navigationItem_speaker));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {

    }
}