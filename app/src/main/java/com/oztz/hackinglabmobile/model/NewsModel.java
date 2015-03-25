package com.oztz.hackinglabmobile.model;

import android.util.Log;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.businessclasses.News;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonHelper;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class NewsModel{
    private JsonHelper jhelper;

    public NewsModel(){
        jhelper = new JsonHelper();
    }


    public News[] getNews(){
        /*try {
            String JsonString = new RequestTask(this).execute("http://152.96.56.40:8080/hlmng/rest/news");
            News[] result = new Gson().fromJson()
        } catch (Exception e){
            Log.d("DEBUG", e.getMessage());
        }*/
        return null;
    }

}
