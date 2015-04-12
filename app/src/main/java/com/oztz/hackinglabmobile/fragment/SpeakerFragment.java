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
import com.oztz.hackinglabmobile.adapter.SpeakerAdapter;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

/**
 * Created by Tobi on 20.03.2015.
 */
public class SpeakerFragment extends Fragment implements JsonResult {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView speakerListView;

    public static SpeakerFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "SpeakerFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        SpeakerFragment fragment = new SpeakerFragment();
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
        View view = inflater.inflate(R.layout.fragment_speaker, container, false);
        speakerListView = (ListView)view.findViewById(R.id.speaker_listview);
        new RequestTask(this).execute("http://152.96.56.40:8080/hlmng/rest/speaker");
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
        Speaker[] speakers = null;
        try {
            speakers = new Gson().fromJson(JsonString, Speaker[].class);
            speakerListView.setAdapter(new SpeakerAdapter(getActivity(),R.layout.item_speaker, speakers));
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT);
        }

    }
}
