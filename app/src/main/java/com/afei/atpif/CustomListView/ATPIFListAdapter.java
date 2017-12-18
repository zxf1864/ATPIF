package com.afei.atpif.CustomListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afei.atpif.CustomWidget.CircleImage;
import com.afei.atpif.Model.TrainListData;
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

        //ATPIFListModel product = getItem(position);

        //View productView = LayoutInflater.from(getContext()).inflate(resourceId, null);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);

        setData( convertView, position);

        return convertView;
    }

    private void setData(View productView, int index){
        ATPIFListModel product = TrainListData.getInstance().productList.get(index);

        TextView productid = (TextView) productView.findViewById(R.id.product_id);
        TextView productName = (TextView) productView.findViewById(R.id.product_name);
        TextView productStatus = (TextView) productView.findViewById(R.id.product_status);
        TextView productLendMoney = (TextView) productView.findViewById(R.id.product_lend_money);
        TextView productInterest = (TextView) productView.findViewById(R.id.product_interest);
        TextView productEndDate = (TextView) productView.findViewById(R.id.product_date);
        CircleImage circleImage = (CircleImage) productView.findViewById(R.id.product_photo);

        productid.setText(String.valueOf(product.atpifid));
        productName.setText(product.atpifName);
        productStatus.setText(product.atpifRUNState);
        productLendMoney.setText(product.atpifIP);
        productInterest.setText(product.clientNum);
        productEndDate.setText(product.lastConnectDate);

        if((product.atpif_photo_path!=null)&&(!product.atpif_photo_path.equalsIgnoreCase("")))
        {
            Bitmap photo = BitmapFactory.decodeFile(product.atpif_photo_path);;
            Drawable drawable = new BitmapDrawable(null, photo);
            circleImage.setImageDrawable(drawable);
        }
        else
            circleImage.setImageDrawable( ContextCompat.getDrawable(getContext(),R.drawable.dev_disconnect));

    }

    public void ADD(ListView listview)
    {



    }

    public void update(int index,ListView listview){
        //得到第一个可见item项的位置
        int visiblePosition = listview.getFirstVisiblePosition();
        //得到指定位置的视图，对listview的缓存机制不清楚的可以去了解下
        View view = listview.getChildAt(index - visiblePosition);
        setData(view,index);
    }



}
