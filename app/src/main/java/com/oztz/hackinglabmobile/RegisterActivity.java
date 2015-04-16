package com.oztz.hackinglabmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.oztz.hackinglabmobile.businessclasses.User;
import com.oztz.hackinglabmobile.helper.JsonResult;

import java.io.IOException;

public class RegisterActivity extends Activity implements JsonResult{

    EditText nameEditText;

    final static String PROJECT_NUMBER = "182393118726";
    private GoogleCloudMessaging gcm;
    private String regId, deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = (EditText) findViewById(R.id.register_editText_name);
        Button startButton = (Button) findViewById(R.id.register_button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegId();
            }
        });
    }

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcm.register(PROJECT_NUMBER);
                } catch (IOException ex) {
                    regId = "";
                }
                return regId;
            }

            @Override
            protected void onPostExecute(String msg) {
                if(msg.length() > 1){
                    postUser(msg);
                }

            }
        }.execute(null, null, null);
    }

    private void postUser(String msg){
        deviceId = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        User u = new User(deviceId, nameEditText.getText().toString(), msg, 0);
        String jsonString = new Gson().toJson(u);

        Log.d("DEBUG", "POST DATA: " + jsonString);
        //new PostTask(this).execute(getResources().getString(R.string.rootURL) + "user", jsonString);
        startMainActivity();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestType) {
        if(requestType.equals("POST")){
            if(!JsonString.equals("ERROR")){
                Log.d("DEBUG", "Created User: " + JsonString);
                startMainActivity();
            }
        }
        startMainActivity();
    }
}
