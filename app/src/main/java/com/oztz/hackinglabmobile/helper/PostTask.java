package com.oztz.hackinglabmobile.helper;

import android.os.AsyncTask;

/**
 * Created by Tobi on 25.03.2015.
 */
public class PostTask extends AsyncTask<String, String, String> {
    private JsonResult listener;
    String result;

    public PostTask(JsonResult listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {
        try {
            if(uri.length == 2) {
                result = new JsonHelper().doPost(uri[0], uri[1]); //URL & JSONData
            } else if(uri.length == 3){
                result = new JsonHelper().doPost(uri[0], uri[1], uri[2]); // Contains QR-Code
            }
        } catch (Exception e){
            result = "Site not available";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result, "POST");
    }
}