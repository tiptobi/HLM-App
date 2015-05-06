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
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

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
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "social", "social");

        return view;
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        try {
            Social[] socialnews = new Gson().fromJson(JsonString, Social[].class);
            SocialNewsListView.setAdapter(new SocialAdapter(getActivity(), R.layout.item_article_textonly, socialnews));
        } catch (Exception e){
            Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT);
        }
    }
}
