package com.afei.atpif.Model;

import com.afei.atpif.CustomWidget.Entry;
import com.afei.atpif.SocketClient.ATPIFSocketProtocol;
import com.afei.atpif.SocketClient.ByteUtil;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by afei on 2017-05-26.
 */

public class SpeedData {

    ArrayList<Entry> speed = new ArrayList<Entry>();
    ArrayList<Entry> acc = new ArrayList<Entry>();
    ArrayList<Entry> postion = new ArrayList<Entry>();

    public ArrayList<Entry> GetaccData()
    {
        return acc;
    }

    public ArrayList<Entry> GetSpeedData()
    {
        return speed;
    }

    public ArrayList<Entry> GetpotionData()
    {
        return postion;
    }


    int num = 0;

    public void AddData(ATPIFSocketProtocol protocol)
    {
        byte[] temp = protocol.getContent();
        int accdata = ByteUtil.getShortinvert(temp,2);
        float accdata_f = (float)accdata;

        int speeddata = ByteUtil.getShortinvert(temp,4);
        float speeddata_f = (float)0.036*speeddata;

        int postiondata = ByteUtil.getIntinvert(temp,10);
        float postiondata_f = (float)0.00001*postiondata;

        long now = TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis());

        acc.add(new Entry(num, accdata_f));
        speed.add(new Entry(num, speeddata_f)); // add one entry per hour
        postion.add(new Entry(num, postiondata_f));

        num++;

        return;
    }

    class speed
    {
        private int speed;
        private int postion;
        private int acc;
        private int track;

        public speed(ATPIFSocketProtocol protocol)
        {
            speed = ByteUtil.byteArrayToInt(ByteUtil.subBytes(protocol.getContent(),0,2));
            postion = ByteUtil.byteArrayToInt(ByteUtil.subBytes(protocol.getContent(),0,2));
        }

    }




}


