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
import com.oztz.hackinglabmobile.adapter.NewsAdapter;
import com.oztz.hackinglabmobile.businessclasses.News;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class NewsFragment extends Fragment implements JsonResult {

    private ListView newsListView;
    private RequestTask requestTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "NewsFragment.onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("DEBUG", "NewsFragment.onCreateView()");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsListView = (ListView) view.findViewById(R.id.news_listview);

        String url = getResources().getString(R.string.rootURL) + "event/" +
                String.valueOf(App.eventId) + "/news";
        updateView(App.db.getContentFromDataBase(url));
        requestTask = new RequestTask(this);
        requestTask.execute(url, "news");

        return view;
    }

    @Override
    public void onTaskCompleted(String result, String requestCode) {
        if(requestCode.equals("news") && result != null) {
            updateView(result);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Error Getting Data",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d("DEBUG", "NewsFragment.onDestroyView()");
        requestTask.cancel(true);
    }

    private void updateView(String json){
        if(json != null){
            News[] news = null;
            try {
                news = new Gson().fromJson(json, News[].class);
                newsListView.setAdapter(new NewsAdapter(getActivity(), R.layout.item_article_textonly, news));
            } catch(Exception e){
                Toast.makeText(getActivity().getApplicationContext(), "Error loading Data",Toast.LENGTH_SHORT);
            }
        }
    }
}
