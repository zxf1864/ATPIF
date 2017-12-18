package com.afei.atpif;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afei.atpif.CustomListView.ATPIFListAdapter;
import com.afei.atpif.CustomListView.ATPIFListModel;
import com.afei.atpif.Model.TrainListData;
import com.afei.atpif.Util.DatabaseAdapter;
import com.afei.atpif.Util.DatabaseHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainListFragment extends BaseFragment {
    private DatabaseHelper dbHelper;
    private DatabaseAdapter dbAdapter;
    //private ArrayList<ATPIFListModel> productList;

    private ATPIFListAdapter adapter;
    private ListView listView;

    public TrainListFragment() {
        // Required empty public constructor
        super();

        //createProductList();

    }


    private void PopMenu(View v)
    {
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(getActivity(), v);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.menu_pop_dev_op, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {
                    case R.id.dev_Top:
                        Toast.makeText(getActivity(), "置顶", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.dev_Bottom:
                        Toast.makeText(getActivity(), "置底", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dev_edit:
                        Toast.makeText(getActivity(), "编辑", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dev_delete:
                        Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        //显示(这一行代码不要忘记了)
        popup.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View trainListView = inflater.inflate(R.layout.fragment_train_list, container, false);

        initSqlite();

        initDBAdapter();

        //insertProductList();

        TrainListData.getInstance().productList.clear();

        TrainListData.getInstance().productList = dbAdapter.findAll();


        adapter = new ATPIFListAdapter(getContext(), R.layout.sample_custom_cell,   TrainListData.getInstance().productList);
        listView = (ListView)trainListView.findViewById(R.id.ATPIF_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ATPIFListModel product =   TrainListData.getInstance().productList.get(position);
                Toast.makeText(getContext(), product.atpifName, Toast.LENGTH_SHORT).show();
                PopMenu(view);

            }
        });

        return trainListView;

    }

    public void AddItemListView(ATPIFListModel product)
    {
        //adapter.update(product.atpifid,(ListView) listView);
    }

    public void UpdateListView()
    {
        //adapter.update(product.atpifid,(ListView) listView);
        adapter.notifyDataSetChanged();
    }

    private void initSqlite()
    {
        dbHelper = DatabaseHelper.getInstance(getActivity());
        dbHelper.onCreate(dbHelper.getWritableDatabase());
    }

    private void initDBAdapter()
    {
        dbAdapter = DatabaseAdapter.getInstance(getActivity());
    }

    private  void createProductList() {

        for (int i=0; i<3; i++) {
            ATPIFListModel product = new ATPIFListModel();
            product.atpifName = "产品名称" + i;
            if (i % 2 == 0){
                product.atpifRUNState = "连接中";
            } else {
                product.atpifRUNState = "连接成功";
            }
            product.atpifIP = "" + (i * 100 + i);
            product.clientNum = "" + (i * 10);
            if (i < 2) {
                product.lastConnectDate = "2016-01-0" + i;
            } else {
                product.lastConnectDate = "2016-01-" + i;
            }
            TrainListData.getInstance().productList.add(i, product);

        }
    }

    private  void insertProductList() {
        dbAdapter.removeALL();

        for (int i=0; i<3; i++) {
            dbAdapter.create(  TrainListData.getInstance().productList.get(i));
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
