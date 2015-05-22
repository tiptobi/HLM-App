package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
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
import com.oztz.hackinglabmobile.businessclasses.EventItem;
import com.oztz.hackinglabmobile.businessclasses.EventRoom;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.AuthImageDownloader;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

public class EventItemDetailActivity extends ActionBarActivity implements JsonResult {

    EventItem eventItem;
    TextView title, date, time, room, speaker, description;
    ImageView speakerImage;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventItem = loadEventItem();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventitem_detail);
        title = (TextView) findViewById(R.id.eventItemDetail_Title_TextView);
        description = (TextView) findViewById(R.id.eventItemDetail_description);
        speaker = (TextView) findViewById(R.id.eventItemDetail_speaker);
        date = (TextView) findViewById(R.id.eventItemDetail_date);
        time = (TextView) findViewById(R.id.eventItemDetail_time);
        room = (TextView) findViewById(R.id.eventItemDetail_room);
        speakerImage = (ImageView) findViewById(R.id.eventItemDetail_speakerImage);
        SetupView();
    }

    private void SetupView(){
        title.setText(eventItem.name);
        date.setText(eventItem.date);
        time.setText(eventItem.startTime + " - " + eventItem.endTime);
        description.setText(eventItem.description);

        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "speaker/" +
                String.valueOf(eventItem.speakerIDFK), "speaker");
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "eventroom/" +
                String.valueOf(eventItem.roomIDFK), "room");
    }

    private EventItem loadEventItem(){
        Intent intent = getIntent();
        String eventItemJson = intent.getStringExtra("eventItem");
        if(eventItemJson != null && eventItemJson.length() > 1){
            return new Gson().fromJson(eventItemJson, EventItem.class);
        }
        else{
            return null;
        }
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
        actionBar.setTitle(getResources().getString(R.string.event));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(requestCode.equals("speaker")){
            try{
                Speaker s = new Gson().fromJson(JsonString, Speaker.class);
                if(s.media != null && s.media.length() > 1) {
                    imageLoader = ImageLoader.getInstance();
                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                            .imageDownloader(new AuthImageDownloader(getApplicationContext(), 5000, 20000))
                            .diskCacheFileCount(50)
                            .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                                    .cacheInMemory(true)
                                    .cacheOnDisk(true).build())
                            .build();
                    imageLoader.init(config);
                    imageLoader.displayImage(s.media, speakerImage);
                }
                if(s.title != null){
                    speaker.setText(s.title + " " + s.name);
                } else{
                    speaker.setText(s.name);
                }
            }catch(Exception e){}
        } else if(requestCode.equals("room")){
            try{
                EventRoom eventRoom = new Gson().fromJson(JsonString, EventRoom.class);
                room.setText(eventRoom.name);
            }catch(Exception e){}
        }
    }
}
