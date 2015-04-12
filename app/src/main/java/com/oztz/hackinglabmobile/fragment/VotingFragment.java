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
import com.oztz.hackinglabmobile.adapter.VotingAdapter;
import com.oztz.hackinglabmobile.businessclasses.Voting;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class VotingFragment extends Fragment implements JsonResult {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView votingListView;

    public static VotingFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "ConferenceFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        VotingFragment fragment = new VotingFragment();
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
        View view = inflater.inflate(R.layout.fragment_voting, container, false);
        votingListView = (ListView)view.findViewById(R.id.voting_listview);
        new RequestTask(this).execute("http://152.96.56.40:8080/hlmng/rest/voting");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestType) {
        Voting[] votings = null;
        try {
            votings = new Gson().fromJson(JsonString, Voting[].class);
            votingListView.setAdapter(new VotingAdapter(getActivity(), R.layout.item_voting, votings));
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT);
        }

    }
}
