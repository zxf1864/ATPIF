package com.afei.atpif;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.afei.atpif.CustomListView.AutoListView;
import com.afei.atpif.CustomListView.CHScrollView;
import com.afei.atpif.CustomListView.ListViewScrollAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaliseListFragment extends BaseFragment implements AutoListView.OnRefreshListener, AutoListView.OnLoadListener, AdapterView.OnItemClickListener {

    private AutoListView lstv;
    private CHScrollView headerScroll;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private ListViewScrollAdapter adapter;

    private View BaliseListView;


    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            List<Map<String, String>> result = (List<Map<String, String>>) msg.obj;
            switch (msg.what) {
                case AutoListView.REFRESH:
                    lstv.onRefreshComplete();
                    list.clear();
                    list.addAll(result);
                    break;
                case AutoListView.LOAD:
                    lstv.onLoadComplete();
                    list.addAll(result);
                    break;
            }
            lstv.setResultSize(result.size());
            adapter.notifyDataSetChanged();
        };
    };

    public BaliseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        BaliseListView = inflater.inflate(R.layout.fragment_balise_list, container, false);


        initView();
        initData();


        return  BaliseListView;

    }



    private void initView() {

        headerScroll = (CHScrollView) BaliseListView.findViewById(R.id.item_scroll_title);
        CHScrollView.CHScrollViewHelper.mHScrollViews.clear();
        // 添加头滑动事件
        CHScrollView.CHScrollViewHelper.mHScrollViews.add(headerScroll);
        lstv = (AutoListView) BaliseListView.findViewById(R.id.scroll_list);

        adapter = new ListViewScrollAdapter(getContext(), list, R.layout.auto_listview_item,
                new String[] { "时间", "公里标", "数据类型", "数据内容", "备注信息", "应答器ID", "预留", },
                new int[] { R.id.item_title, R.id.item_data1, R.id.item_data2, R.id.item_data3, R.id.item_data4,
                        R.id.item_data5, R.id.item_data6 },
                R.id.item_scroll, lstv);

        lstv.setAdapter(adapter);
        lstv.setOnRefreshListener(this);
        lstv.setOnLoadListener(this);
        lstv.setOnItemClickListener(this);
    }

    private void initData() {
        loadData(AutoListView.REFRESH);
    }

    private void loadData(final int what) {
        // 这里模拟从服务器获取数据
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = getData();
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        loadData(AutoListView.REFRESH);
    }

    @Override
    public void onLoad() {
        loadData(AutoListView.LOAD);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        try {
            TextView textView = (TextView) arg1.findViewById(R.id.item_data2);

            Toast.makeText(getContext(), "你点击了：" + textView.getText(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {

        }
    }





    // 测试数据
    public List<Map<String, String>> getData() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> data = null;
        for (int i = 0; i < 10; i++) {
            data = new HashMap<String, String>();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String timestr = formatter.format(curDate);

            data.put("时间", timestr);
            data.put("公里标", String.valueOf(i));
            data.put("数据类型", "轨道电路");
            data.put("数据内容", "0112");
            data.put("备注信息", "XXB:12141:00");
            data.put("应答器ID", " ");
            data.put("预留", " ");
            result.add(data);
        }
        return result;
    }

}
