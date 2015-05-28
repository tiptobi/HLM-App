package com.oztz.hackinglabmobile.helper;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Tobi on 25.03.2015.
 */
public class RequestTask extends AsyncTask<String, String, String> {
    private JsonResult listener;
    String result;
    String requestCode;

    public RequestTask(JsonResult listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... uri) {
        if(uri.length > 1){
            requestCode = uri[1];
        }
        try {
            Log.d("DEBUG", "Read " + uri[0]);
            result = new JsonHelper().readUrl(uri[0]);
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result, requestCode);
    }
}