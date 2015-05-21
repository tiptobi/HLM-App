package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.EventItem;
import com.oztz.hackinglabmobile.businessclasses.EventRoom;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobi on 20.03.2015.
 */
public class AgendaTabHolderFragment extends Fragment implements JsonResult {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentTabHost mTabHost;
    private String roomsJson, itemsJson;

    public static AgendaTabHolderFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "AgendaTabHolderFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        AgendaTabHolderFragment fragment = new AgendaTabHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_main);

        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event/" +
                String.valueOf(App.eventId) + "/eventrooms", "eventRooms");
        new RequestTask(this).execute(getResources().getString(R.string.rootURL) + "event/" +
                String.valueOf(App.eventId) + "/eventitems", "eventItems");

        /*Bundle arg1 = new Bundle();
        arg1.putInt("Arg for Frag1", 1);
        mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("News"),
                AgendaFragment.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putInt("Arg for Frag2", 2);
*/
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Agenda"),
                AgendaFragment.class, new Bundle());
        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(requestCode.equals("eventRooms")){
            roomsJson = JsonString;
        }
        if(requestCode.equals("eventItems")){
            itemsJson = JsonString;
        }
        if(roomsJson != null && itemsJson != null){
            updateView();
        }
    }

    private EventItem[] getRoomItems(EventItem[] items, int roomID){
        List<EventItem> list = new ArrayList<EventItem>();
        for(int i=0; i<items.length; i++){
            if(items[i].roomIDFK == roomID){
                list.add(items[i]);
            }
        }
        return list.toArray(new EventItem[list.size()]);
    }

    private void updateView(){
        if(roomsJson != null && itemsJson != null){
            try {
                EventRoom[] rooms = new Gson().fromJson(roomsJson, EventRoom[].class);
                EventItem[] items = new Gson().fromJson(itemsJson, EventItem[].class);
                if(mTabHost.getChildCount() != rooms.length + 1) {
                    mTabHost.clearAllTabs();
                    //Load Overview
                    Bundle OverviewArgs = new Bundle();
                    OverviewArgs.putString("eventitems", new Gson().toJson(items));
                    mTabHost.addTab(mTabHost.newTabSpec("Tab" + rooms.length).setIndicator("Overview"),
                            AgendaFragment.class, OverviewArgs);

                    //Load room Views if there is more than one room
                    if(rooms.length > 1) {
                        for (int i = 0; i < rooms.length; i++) {
                            EventItem[] roomItems = getRoomItems(items, rooms[i].eventRoomID);
                            String jsonItems = new Gson().toJson(roomItems);
                            Bundle args = new Bundle();
                            args.putString("eventitems", jsonItems);
                            mTabHost.addTab(mTabHost.newTabSpec("Tab" + String.valueOf(i)).setIndicator(rooms[i].name),
                                    AgendaFragment.class, args);
                        }
                    }
                }
            } catch(Exception e){
                Toast.makeText(getActivity().getApplicationContext(), "Error Getting Data", Toast.LENGTH_SHORT);
            }
        }
    }
}

