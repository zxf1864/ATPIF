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

    private SpeedData sd;
    private IOData io;

    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();



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
        io = IOData.getInstance();
        sd = new SpeedData();
    }

    public IOData GetIOData()
    {
        return io;
    }

    public SpeedData GetSpeedData()
    {
        return sd;
    }

    public void AddData(ATPIFSocketProtocol protocol)
    {
        switch(protocol.getContentFunc())
        {
            case 1:
                io.AddBIUData(protocol);
                break;
            case 2:
                io.AddTIUData(protocol);
                break;
            case 4:
                sd.AddData(protocol);
                break;

        }

    }

}
