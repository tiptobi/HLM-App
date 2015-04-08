package com.oztz.hackinglabmobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.adapter.NewsAdapter;
import com.oztz.hackinglabmobile.businessclasses.News;
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
        requestTask = new RequestTask(this);
        requestTask.execute("http://152.96.56.40:8080/hlmng/rest/news");
        return view;
    }

    @Override
    public void onTaskCompleted(String result) {
        News[] news = null;
        try {
            news = new Gson().fromJson(result, News[].class);
        } catch(Exception e){
            News item = new News();
            item.author = "Error";
            item.text = "Server not Found";
            news = new News[1];
            news[0] = item;
        } finally{
            newsListView.setAdapter(new NewsAdapter(getActivity(), R.layout.item_article_textonly, news));
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d("DEBUG", "NewsFragment.onDestroyView()");
        requestTask.cancel(true);
    }
}
