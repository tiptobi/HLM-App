package com.oztz.hackinglabmobile.helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tobi on 20.03.2015.
 */
public class JsonHelper {

    public String readUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        StringBuilder total = new StringBuilder();
        Log.d("DEBUG", "1");
        URLConnection urlConnection = url.openConnection();
        Log.d("DEBUG", "2");
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e){
            Log.d("DEBUG", e.getMessage());
        }

        Log.d("DEBUG", "3");

        /*try {
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        }
        finally {
            in.close();
        }*/
        return total.toString();
    }
}
