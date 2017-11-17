package com.afei.atpif.CustomListView;

/**
 * Created by afei on 2017/9/29.
 */

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afei.atpif.R;

public class ListViewScrollAdapter extends SimpleAdapter {

    private List<? extends Map<String, ?>> datas;
    private int res;
    private String[] from;
    private int[] to;
    private Context context;
    private int resScroll;
    private AutoListView lstv;

    public ListViewScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
                                 int[] to, int resourceItem,AutoListView autoListView) {
        super(context, data, resource, from, to);
        this.context = context;
        this.datas = data;
        this.res = resource;
        this.from = from;
        this.to = to;
        this.resScroll = resourceItem;
        this.lstv = autoListView;
    }

    @Override
    public int getCount() {

        return this.datas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(res, null);
            // 第一次初始化的时候装进来
            CHScrollView.CHScrollViewHelper.addHViews((CHScrollView) v.findViewById(resScroll), lstv);
            View[] views = new View[to.length];
            for (int i = 0; i < to.length; i++) {
                View tv = v.findViewById(to[i]);
                views[i] = tv;
            }
            v.setTag(views);
        }
        v.setBackgroundResource(R.drawable.selector_bg_white_gray);
        View[] holders = (View[]) v.getTag();
        int len = holders.length;
        for (int i = 0; i < len; i++) {
            ((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
        }
        return v;
    }

    public Context getContext() {
        return context;
    }

}
