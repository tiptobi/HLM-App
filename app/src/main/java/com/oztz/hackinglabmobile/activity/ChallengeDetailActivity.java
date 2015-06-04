package com.oztz.hackinglabmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Challenge;

public class ChallengeDetailActivity extends Activity {

    Challenge challenge;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        challenge = loadChallenge();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        videoView = (VideoView) findViewById(R.id.challenge_detail_video);
        setupVideo();
    }

    private void setupVideo(){
        if(challenge.aboutchallenge != null && challenge.aboutchallenge.length() > 1){
            Uri videoUrl = Uri.parse(challenge.aboutchallenge + ".webm");
            videoView.setVideoURI(videoUrl);
            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            videoView.setMediaController(mc);
            videoView.start();
        }
    }

    private Challenge loadChallenge(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("challenge"), Challenge.class);
    }
}
