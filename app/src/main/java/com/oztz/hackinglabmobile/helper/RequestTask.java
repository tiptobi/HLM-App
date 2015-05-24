package com.oztz.hackinglabmobile.helper;

import android.os.AsyncTask;

import org.apache.commons.lang3.StringEscapeUtils;

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
        try {
            result = new JsonHelper().readUrl(uri[0]);
            requestCode = uri[1];
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(StringEscapeUtils.unescapeJava(result), requestCode);
    }
}