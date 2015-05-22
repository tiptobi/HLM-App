package com.oztz.hackinglabmobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.adapter.SocialAdapter;
import com.oztz.hackinglabmobile.businessclasses.Social;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Tobi on 20.03.2015.
 */
public class SocialNewsFragment extends Fragment implements JsonResult {

    ListView SocialNewsListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "SocialNewsFragment.onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("DEBUG", "SocialNewsFragment.onCreateView()");
        View view = inflater.inflate(R.layout.fragment_socialnews, container, false);
        SocialNewsListView = (ListView) view.findViewById(R.id.SocialNews_List_View);
        updateView(App.db.getContentFromDataBase("news"), "db");
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event/" +
                String.valueOf(App.eventId) + "/socials/newest", "newest");

        return view;
    }

    private void loadMoreData(){
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event/" +
                String.valueOf(App.eventId) + "/socials", "all");
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(JsonString != null) {
            updateView(JsonString, requestCode);
            App.db.saveToDataBase("social", JsonString);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Error Getting Data",Toast.LENGTH_SHORT);
        }
    }

    private void updateView(String json, String requestCode){
        if(json != null){
            try {
                Social[] socialnews = new Gson().fromJson(json, Social[].class);
                Arrays.sort(socialnews, new Comparator<Social>() {
                    @Override
                    public int compare(Social lhs, Social rhs) {
                        return lhs.socialID - rhs.socialID;
                    }
                });
                SocialNewsListView.setAdapter(new SocialAdapter(getActivity(), R.layout.item_article_textonly, socialnews));

                LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
                View v = inflater.inflate(R.layout.item_load_more_data, null);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadMoreData();
                    }
                });
                SocialNewsListView.removeFooterView(v);

                if(requestCode.equals("newest") && socialnews.length == 15){
                    SocialNewsListView.addFooterView(v);
                }
                else if(requestCode.equals("all")){

                }
            } catch (Exception e){
                Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT);
            }
        }
    }
}
