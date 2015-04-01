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
import com.oztz.hackinglabmobile.businessclasses.Speaker;

/**
 * Created by Tobi on 25.03.2015.
 */
public class SpeakerAdapter extends ArrayAdapter {

    public SpeakerAdapter(Context context, int resource, Speaker[] speakers) {
        super(context, resource, speakers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.item_speaker, null);
        }

        Speaker item = (Speaker)getItem(position);

        if (item != null) {
            TextView name = (TextView) v.findViewById(R.id.speaker_name);
            ImageView flag = (ImageView) v.findViewById(R.id.speaker_flag);

            if (name != null) {
                if(item.title != null){
                    name.setText(item.title + " " + item.name);
                } else {
                    name.setText(item.name);
                }
            }
            if (flag != null) {
                flag.setImageURI(Uri.parse("android.resource://com.oztz.hackinglabmobile/drawable/flag_"
                        + item.nationality.toLowerCase()));
            }
        }
        return v;
    }
}
