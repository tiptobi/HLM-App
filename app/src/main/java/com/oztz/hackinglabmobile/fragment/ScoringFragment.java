package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.adapter.ScoringAdapter;
import com.oztz.hackinglabmobile.businessclasses.Team;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class ScoringFragment extends Fragment implements JsonResult {

    private static final String ARG_SECTION_NUMBER = "section_number";
    ListView scoringListView;

    public static ScoringFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "ScoringFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        ScoringFragment fragment = new ScoringFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_scoring, container, false);
        scoringListView = (ListView) view.findViewById(R.id.scoring_listview);
        new RequestTask(this).execute(getResources().getString(R.string.hackingLabUrl) +
                "SlideService/GetGroupRanking/" + String.valueOf(App.eventId), "groupRanking");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        Team[] teams = null;
        try {
            teams = new Gson().fromJson(JsonString, Team[].class);
            scoringListView.setAdapter(new ScoringAdapter(getActivity(),R.layout.item_scoring,
                    teams));
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT);
        }

    }
}
