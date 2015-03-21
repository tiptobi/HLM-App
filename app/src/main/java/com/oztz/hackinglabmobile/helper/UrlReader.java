package com.oztz.hackinglabmobile.helper;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * Created by Tobi on 21.03.2015.
 */
public class UrlReader extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        String line;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            line = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        } catch (MalformedURLException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        } catch (IOException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        }
        return line;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("DEBUG", result);
    }

}
