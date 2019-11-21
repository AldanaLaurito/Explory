package com.example.figur.ui;

import com.example.figur.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int imageTotal = 7;

    private ArrayList<String> mThumbIds2 = new ArrayList<String>();

    public static String[] mThumbIds = {
            "https://www.nationalgeographic.com.es/medio/2019/09/19/across-the-sky-of-history_82c9fd85_800x533.jpg",
            "https://www.nationalgeographic.com.es/medio/2019/09/19/the-watcher_4c5b7692_540x351.jpg",
            "https://www.nationalgeographic.com.es/medio/2019/09/19/galactic-lighthouse_ea927e18_1109x1200.jpg",
            "https://www.nationalgeographic.com.es/medio/2019/09/19/flower-power_7da0a74e_1335x2000.jpg",
            "https://www.nationalgeographic.com.es/medio/2019/10/31/montesfoja61_497b4943_800x800.jpg"
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds2.size();
        //return imageTotal;
    }

    @Override
    public String getItem(int position) {
        //return mThumbIds[position];
        return mThumbIds2.get(position);
    }

    public void setItem(String url){mThumbIds2.add(url);}

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(480, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position);
        //Picasso.with(mContext)
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}
