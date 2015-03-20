package com.oztz.hackinglabmobile.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.model.NewsModel;

/**
 * Created by Tobi on 20.03.2015.
 */
public class NewsFragment extends Fragment {

    private NewsModel model;
    private Speaker[] speakers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new NewsModel();
        speakers = model.getSpeaker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView txt = (TextView)view.findViewById(R.id.textNews);
        txt.setText("Bla");
        return view;
    }
}
