package com.oztz.hackinglabmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Event;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

public class ChooseEventActivity extends Activity implements JsonResult{

    LinearLayout loadingHolder, eventsHolder, tryAgainHolder;
    Button tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);
        loadingHolder = (LinearLayout) findViewById(R.id.choose_event_loading_holder);
        eventsHolder = (LinearLayout) findViewById(R.id.choose_event_holder);
        tryAgainHolder = (LinearLayout) findViewById(R.id.choose_event_try_again_holder);
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event", "events");
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void refresh(){
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event", "events");
        tryAgainHolder.setVisibility(View.GONE);
        loadingHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(JsonString != null && requestCode != null && requestCode.equals("events")){
            try{
                final Event[] events = new Gson().fromJson(JsonString, Event[].class);
                if(events != null && events.length > 1){
                    loadingHolder.setVisibility(View.GONE);
                    eventsHolder.setVisibility(View.VISIBLE);
                    for(int i=0; i<events.length; i++){
                        final int index = i;
                        Button b = new Button(getApplicationContext());
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = events[index].eventID;
                                App.setEventId(id);
                                Log.d("DEBUG", "EventId is set to " + String.valueOf(id));
                                startMainActivity();
                            }
                        });
                        b.setText(events[index].name);
                        eventsHolder.addView(b);
                    }
                } else if(events != null && events.length == 1){
                    App.setEventId(events[0].eventID);
                    Log.d("DEBUG", "EventId is set to " + String.valueOf(events[0].eventID));
                    startMainActivity();
                } else {
                    loadingHolder.setVisibility(View.GONE);
                    tryAgainHolder.setVisibility(View.VISIBLE);
                    tryAgainButton = (Button) findViewById(R.id.choose_event_btn_try_again);
                    tryAgainButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refresh();
                        }
                    });
                }
            } catch (Exception e){
                Log.d("DEBUG", e.getMessage());
            }
        } else {
            loadingHolder.setVisibility(View.GONE);
            tryAgainHolder.setVisibility(View.VISIBLE);
            tryAgainButton = (Button) findViewById(R.id.choose_event_btn_try_again);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }
    }
}
