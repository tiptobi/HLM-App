package com.oztz.hackinglabmobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Challenge;

/**
 * Created by Tobi on 25.03.2015.
 */
public class ChallengesAdapter extends ArrayAdapter {

    public ChallengesAdapter(Context context, int resource, Challenge[] challenges) {
        super(context, resource, challenges);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.item_challenges, null);
        }

        Challenge item = (Challenge)getItem(position);

        if (item != null) {
            TextView challengeName = (TextView) v.findViewById(R.id.item_challenge_name);
            ImageView symbol = (ImageView) v.findViewById(R.id.item_challenge_symbol);
            LinearLayout main = (LinearLayout) v.findViewById(R.id.item_challenge_mainLayout);
            if (challengeName != null) {
                challengeName.setText(item.title);
            }
            switch(item.level){
                case 1:
                    main.setBackgroundColor(Color.parseColor("#FF009B00"));
                    break;
                case 2:
                    main.setBackgroundColor(Color.parseColor("#FFFFB000"));
                    break;
                case 3:
                    main.setBackgroundColor(Color.RED);
                    break;
            }


        }
        return v;
    }
}
