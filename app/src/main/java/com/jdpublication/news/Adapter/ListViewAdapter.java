package com.jdpublication.news.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jdpublication.news.Parse.NewsListParse;
import com.jdpublication.news.R;
import com.jdpublication.news.utils.CommonFunctions;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<NewsListParse> {

    ArrayList<NewsListParse> arrayList;
    Context context;
    int resource;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NewsListParse> objects) {
        super(context, resource, objects);
        this.arrayList = objects;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsListParse newsListParse = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_view, null, true);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        CommonFunctions.loadImage(context, imageView, newsListParse.getImageURL(), R.drawable.loading);

        TextView textView1 = convertView.findViewById(R.id.textView1);
        textView1.setText(newsListParse.getTitle());

        TextView textView2 = convertView.findViewById(R.id.textView2);
        textView2.setText(newsListParse.getDescription());

        TextView textView3 = convertView.findViewById(R.id.textView3);
        textView3.setText(newsListParse.getPublishedAt());

        TextView textView4 = convertView.findViewById(R.id.textView4);
        textView4.setText("source: " + newsListParse.getSource());

        TextView textView5 = convertView.findViewById(R.id.textView5);
        textView5.setText("author:" + newsListParse.getAuthor());

        convertView.findViewById(R.id.removeTextView).setOnClickListener(view -> {
            remove(newsListParse);
            notifyDataSetChanged();
        });

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return Math.max(getCount(), 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

