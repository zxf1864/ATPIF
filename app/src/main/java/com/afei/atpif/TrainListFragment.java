package com.afei.atpif;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afei.atpif.CustomListView.ATPIFListAdapter;
import com.afei.atpif.CustomListView.ATPIFListModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainListFragment extends BaseFragment {

    private ArrayList<ATPIFListModel> productList = new  ArrayList<ATPIFListModel>();

    public TrainListFragment() {
        // Required empty public constructor
        createProductList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View trainListView = inflater.inflate(R.layout.fragment_train_list, container, false);

        ATPIFListAdapter adapter = new ATPIFListAdapter(getContext(), R.layout.sample_custom_cell, productList);
        ListView listView = (ListView)trainListView.findViewById(R.id.ATPIF_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ATPIFListModel product = productList.get(position);
                Toast.makeText(getContext(), product.atpifName, Toast.LENGTH_SHORT).show();
            }
        });

        return trainListView;

    }

    private  void createProductList() {
        for (int i=0; i<20; i++) {
            ATPIFListModel product = new ATPIFListModel();
            product.atpifName = "产品名称" + i;
            if (i % 2 == 0){
                product.atpifRUNState = "连接中";
            } else {
                product.atpifRUNState = "连接成功";
            }
            product.atpifIP = "" + (i * 100 + i);
            product.clientNum = "" + (i * 10);
            if (i < 10) {
                product.lastConnectDate = "2016-01-0" + i;
            } else {
                product.lastConnectDate = "2016-01-" + i;
            }
            productList.add(i, product);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
