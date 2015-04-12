package com.oztz.hackinglabmobile.helper;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tobi on 20.03.2015.
 */
public class JsonHelper {

    /*public void getWebsite(String url){
        new RequestTask().execute(url);
    }*/

    public String readUrl(String urlString) throws IOException {
        InputStream inStream = null;
        try{
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            inStream = con.getInputStream();
            String result = readIt(inStream);
            Log.d("DEBUG", result);
            return result;
        } finally {
            if(inStream != null){
                inStream.close();
            }
        }
    }

    public String doPost(String urlString, String jsonData){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        StringEntity se = null;
        String responseText = null;
        try {
            se = new StringEntity(jsonData);
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }

    }

    private String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
    }
}


