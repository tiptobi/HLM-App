package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;

/**
 * Created by Tobi on 20.03.2015.
 */
public class ShareFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ShareFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "PlaceholderFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        ShareFragment fragment = new ShareFragment();
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
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

}
