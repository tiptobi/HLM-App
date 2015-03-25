package com.oztz.hackinglabmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.News;

/**
 * Created by Tobi on 25.03.2015.
 */
public class NewsAdapter extends ArrayAdapter {

    public NewsAdapter(Context context, int resource, News[] news) {
        super(context, resource, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.item_article_textonly, null);
        }

        News item = (News)getItem(position);

        if (item != null) {

            TextView title = (TextView) v.findViewById(R.id.article_textonly_title);
            TextView text = (TextView) v.findViewById(R.id.article_textonly_text);

            if (title != null) {
                title.setText(item.author);
            }
            if (text != null) {
                text.setText(item.text);
            }
        }
        return v;
    }
}
