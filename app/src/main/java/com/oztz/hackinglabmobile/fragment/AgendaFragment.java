package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.EventItem;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tobi on 20.03.2015.
 */
public class AgendaFragment extends Fragment implements JsonResult, WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    WeekView mWeekView;
    private List<WeekViewEvent> eventList;


    public static AgendaFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "AgendaFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = new ArrayList<WeekViewEvent>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);

        new RequestTask(this).execute("http://152.96.56.40:8080/hlmng/rest/eventitem");

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    @Override
    public void onTaskCompleted(String JsonString) {
        EventItem[] eventItems = null;
        try {
            eventItems = new Gson().fromJson(JsonString, EventItem[].class);
            for(int i=0;i<eventItems.length;i++){
                WeekViewEvent e = getEvent(eventItems[i]);
                eventList.add(e);
            }
            mWeekView.notifyDatasetChanged();
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
        Toast.makeText(getActivity(), String.valueOf(weekViewEvent.getColor()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return eventList;
    }

    private WeekViewEvent getEvent(EventItem item){
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = (Calendar) startTime.clone();
        try {
            startTime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(item.date + " " + item.startTime));
            endTime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(item.date + " " + item.endTime));
            WeekViewEvent event = new WeekViewEvent(1, item.name, startTime, endTime);
            event.setColor(Color.BLUE);
            return event;
        } catch(Exception e){
            Toast.makeText(getActivity(), "Error Parsing Dates", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
}
