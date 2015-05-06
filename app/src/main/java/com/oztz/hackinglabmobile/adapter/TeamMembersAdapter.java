package com.oztz.hackinglabmobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Participant;

/**
 * Created by Tobi on 25.03.2015.
 */
public class TeamMembersAdapter extends ArrayAdapter {

    public TeamMembersAdapter(Context context, int resource, Participant[] participants) {
        super(context, resource, participants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.item_team_member, null);
        }

        Participant item = (Participant)getItem(position);

        if (item != null) {
            TextView name = (TextView) v.findViewById(R.id.team_member_name);
            TextView nick = (TextView) v.findViewById(R.id.team_member_nickname);
            TextView gender = (TextView) v.findViewById(R.id.team_member_gender);
            TextView age = (TextView) v.findViewById(R.id.team_member_age);
            ImageView flag = (ImageView) v.findViewById(R.id.team_member_flag);
            if (name != null) {
                name.setText(item.firstname + " " + item.lastname);
            }
            if(nick != null){
                nick.setText(item.nick);
            }
            if(gender != null){
                gender.setText(item.gender);
            }
            if(age != null){
                age.setText(String.valueOf(item.ageAsYear));
            }
            if (flag != null) {
                flag.setImageURI(Uri.parse("android.resource://com.oztz.hackinglabmobile/drawable/flag_"
                        + item.nationality.toLowerCase()));
            }
        }
        return v;
    }
}
