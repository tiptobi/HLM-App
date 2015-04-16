package com.oztz.hackinglabmobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Speaker;
import com.oztz.hackinglabmobile.helper.AuthImageDownloader;

/**
 * Created by Tobi on 25.03.2015.
 */
public class SpeakerAdapter extends ArrayAdapter {

    ImageLoader imageLoader;
    private final Context context;

    public SpeakerAdapter(Context context, int resource, Speaker[] speakers) {
        super(context, resource, speakers);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .imageDownloader(new AuthImageDownloader(context, 5000, 20000))
                .build();
        imageLoader.init(config);
    }

    private static class ViewHolder {
        TextView name;
        ImageView flag;
        ImageView speakerImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View v;
        Speaker item = (Speaker)getItem(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item_speaker, null);

            holder.flag = (ImageView) v.findViewById(R.id.speaker_flag);
            holder.flag.setImageURI(Uri.parse("android.resource://com.oztz.hackinglabmobile/drawable/flag_"
                    + item.nationality.toLowerCase()));

            holder.name = (TextView) v.findViewById(R.id.speaker_name);
            if(item.title != null){
                holder.name.setText(item.title + " " + item.name);
            } else {
                holder.name.setText(item.name);
            }

            holder.speakerImage = (ImageView) v.findViewById(R.id.speaker_portrait);
            if(item.media != null && item.media.length() > 0){
                imageLoader.displayImage(item.media, holder.speakerImage);
            }
            else{
                holder.speakerImage.setImageResource(R.drawable.speaker_icon);
            }

            v.setTag(holder);
        }
        else {
            v = convertView;
        }
        return v;
    }
}
