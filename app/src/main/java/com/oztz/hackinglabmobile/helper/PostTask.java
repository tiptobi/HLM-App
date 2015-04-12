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
            result = new JsonHelper().doPost(uri[0], uri[1]);
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