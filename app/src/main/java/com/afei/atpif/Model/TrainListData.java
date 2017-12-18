package com.afei.atpif.Model;

import android.content.Context;

import com.afei.atpif.CustomListView.ATPIFListModel;
import com.afei.atpif.Util.DatabaseAdapter;
import com.afei.atpif.Util.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by afei on 2017/11/27.
 */

public class TrainListData {

    public ArrayList<ATPIFListModel> productList;

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static TrainListData instance = null;

    /* 私有构造方法，防止被实例化 */
    private TrainListData() {
        productList = new  ArrayList<ATPIFListModel>();
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static TrainListData getInstance() {
        if (instance == null) {
            instance = new TrainListData();
        }
        return instance;
    }


}
