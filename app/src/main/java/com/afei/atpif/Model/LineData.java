package com.afei.atpif.Model;

import android.graphics.Color;

import com.afei.atpif.SocketClient.ATPIFSocketProtocol;
import com.afei.atpif.SocketClient.ByteUtil;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by afei on 2017-05-26.
 */

public class LineData {

    ArrayList<Entry> speed = new ArrayList<Entry>();
    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    int num = 0;

    private static volatile LineData instance=null;

    public static  LineData getInstance(){
        if(instance==null){
            synchronized(LineData .class){
                if(instance==null){
                    instance=new LineData ();
                }
            }
        }
        return instance;
    }

    public ArrayList<ILineDataSet> GetDataSets()
    {
        return dataSets;
    }

    private LineData()
    {
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(speed, "速度");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);


        dataSets.add(set1);


    }

    public void AddData(ATPIFSocketProtocol protocol)
    {
        if(protocol.getContentFunc() == 4)
        {
            byte[] temp = protocol.getContent();
            int speeddata = ByteUtil.getShortinvert(temp,4);
            float speeddata_f = (float)0.036*speeddata;
            long now = TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis());

            speed.add(new Entry(num, 1)); // add one entry per hour
            num++;

        }
    }

}
