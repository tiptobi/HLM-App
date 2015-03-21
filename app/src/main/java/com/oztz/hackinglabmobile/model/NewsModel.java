package com.oztz.hackinglabmobile.model;

import android.util.Log;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonHelper;
import com.oztz.hackinglabmobile.helper.UrlReader;

/**
 * Created by Tobi on 20.03.2015.
 */
public class NewsModel{
    private JsonHelper jhelper;

    public NewsModel(){
        jhelper = new JsonHelper();
    }


    public Speaker[] getSpeaker(){
        new UrlReader().execute("http://152.96.56.40:8080/hlmng/rest/speaker");
        return null;
    }

}
