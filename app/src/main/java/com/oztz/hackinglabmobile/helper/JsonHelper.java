package com.oztz.hackinglabmobile.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.businessclasses.Speaker;

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
            con.setReadTimeout(7000);
            con.setConnectTimeout(7000);
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


