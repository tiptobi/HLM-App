package com.oztz.hackinglabmobile.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.User;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.PostTask;

import java.io.IOException;

public class RegisterActivity extends Activity implements JsonResult{

    EditText nameEditText;

    final static String PROJECT_NUMBER = "182393118726";
    private GoogleCloudMessaging gcm;
    private String regId, deviceId, userName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(userExists()){
            startMainActivity();
        }
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

    private boolean userExists(){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preferences_file), Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        return !username.equals("");
    }

    private void saveUserData(){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userId", user.userID);
        editor.putString("username", userName);
        editor.putString("deviceId", deviceId);
        editor.putInt("eventId", 786);
        editor.commit();
    }

    private void postUser(String msg){
        deviceId = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        userName = nameEditText.getText().toString();
        user = new User(deviceId, userName, msg, 0);
        String jsonString = new Gson().toJson(user);

        Log.d("DEBUG", "POST DATA: " + jsonString);
        new PostTask(this).execute(getResources().getString(R.string.rootURL) + "user", jsonString);
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(requestCode.equals("POST")){
            if(!JsonString.equals("ERROR")){
                Log.d("DEBUG", "Created User: " + JsonString);
                user = new Gson().fromJson(JsonString, User.class);
                saveUserData();
                startMainActivity();
            }
        }
    }
}
