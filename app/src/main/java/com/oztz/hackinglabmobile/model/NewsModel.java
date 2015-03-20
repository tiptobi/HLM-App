package com.oztz.hackinglabmobile.model;

import android.util.Log;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonHelper;

/**
 * Created by Tobi on 20.03.2015.
 */
public class NewsModel{
    private JsonHelper jhelper;

    public NewsModel(){
        jhelper = new JsonHelper();
    }


    public Speaker[] getSpeaker(){
        try {
            String content = jhelper.readUrl("http://152.96.56.40:8080/hlmng/rest/speaker");
            Log.d("DEBUG", "Content: " + content);
            return new Gson().fromJson(content, Speaker[].class);
        } catch (Exception e){
            Log.d("DEBUG", "ERROR");
        }
        return null;
    }

}
