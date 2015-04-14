package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Event;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class ChallengesFragment extends Fragment implements JsonResult {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView titleTextView, descriptionTextView;

    public static ChallengesFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "ConferenceFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        ChallengesFragment fragment = new ChallengesFragment();
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
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        titleTextView = (TextView)view.findViewById(R.id.conference_title);
        descriptionTextView = (TextView)view.findViewById(R.id.conference_text);
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event");
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
        Event[] events = null;
        try {
            events = new Gson().fromJson(JsonString, Event[].class);
            titleTextView.setText(events[0].name);
            descriptionTextView.setText(events[0].description);
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT);
        }

    }
}
