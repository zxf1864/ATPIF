package com.afei.atpif.CustomListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.afei.atpif.R;

import java.util.List;

/**
 * Created by afei on 2017/9/27.
 */

public class ATPIFListAdapter extends ArrayAdapter<ATPIFListModel> {

    private int resourceId;
    /**
     * Constructor
     *
     * @param context  listView所在的上下文，也就是ListView所在的Activity
     * @param resource Cell的布局资源文件
     * @param objects  Cell上要显示的数据list，也就是实体类集合
     */
    public ATPIFListAdapter(Context context, int resource, List<ATPIFListModel> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    /**
     * @param position 当前设置的Cell行数，类似于iOS开发中的indexPath.row
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        ATPIFListModel product = getItem(position);

        View productView = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView productName = (TextView) productView.findViewById(R.id.product_name);
        TextView productStatus = (TextView) productView.findViewById(R.id.product_status);
        TextView productLendMoney = (TextView) productView.findViewById(R.id.product_lend_money);
        TextView productInterest = (TextView) productView.findViewById(R.id.product_interest);
        TextView productEndDate = (TextView) productView.findViewById(R.id.product_date);

        productName.setText(product.atpifName);
        productStatus.setText(product.atpifRUNState);
        productLendMoney.setText(product.atpifIP);
        productInterest.setText(product.clientNum);
        productEndDate.setText(product.lastConnectDate);

        return productView;
    }


}
