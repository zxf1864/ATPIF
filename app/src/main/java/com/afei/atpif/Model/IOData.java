package com.afei.atpif.Model;

import android.graphics.Color;

import com.afei.atpif.CustomWidget.Entry;
import com.afei.atpif.CustomWidget.ILineDataSet;
import com.afei.atpif.SocketClient.ATPIFSocketProtocol;
import com.afei.atpif.SocketClient.ByteUtil;


import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by afei on 2017-06-01.
 */


public class IOData {

    ArrayList<Entry> TIU = new ArrayList<Entry>();
    ArrayList<Entry> BIU = new ArrayList<Entry>();
    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    private TIU tiudata;
    private BIU biudata;

    public class TIU
    {
        int DO01_ATPPWR = 0;
        int DO02_CAB = 0;
        int DO03_FWR = 0;
        int DO04_BCK = 0;
        int DO05_SLP = 0;
        int DO06_EB = 0;
        int DO07_TCO = 0;
        int DO08_RB = 0;

        public int getDO01_ATPPWR()
        {
            return DO01_ATPPWR;
        }

        String[] YLabel = new String[]{
                "PWR", "CAB", "FWR", "BCK", "SLP", " EB", "TCO", " RB"
        };

        public void ConfigTIU(Short tiudata)
        {
            DO01_ATPPWR = (int)((tiudata>>0x07)&0x80);
            DO02_CAB = (int)((tiudata>>0x06)&0x40);
            DO03_FWR = (int)((tiudata>>0x05)&0x20);
            DO04_BCK = (int)((tiudata>>0x04)&0x10);
            DO05_SLP = (int)((tiudata>>0x03)&0x08);
            DO06_EB = (int)((tiudata>>0x02)&0x04);
            DO07_TCO = (int)((tiudata>>0x01)&0x02);
            DO08_RB = (int)((tiudata>>0x00)&0x01);
            return;
        }

        TIU()
        {}
    }

    public TIU getTIU()
    {return tiudata;}

    public BIU getBIU()
    {return biudata;}

    public void setTiudata(Short data)
    {
        tiudata.ConfigTIU(data);
    }

    public void setBiudata(int data)
    {
        biudata.ConfigBIU(data);
    }

    public class BIU
    {
        int DI01_TCO = 0;
        int DI02_SB1 = 0;
        int DI03_SB4 = 0;
        int DI04_SB7RB = 0;
        int DI05_EB = 0;
        int DI06_LDoorOpen = 0;
        int DI07_RDoorOpen = 0;
        int DI08_GFX3Disable = 0;
        int DI09_Reentry = 0;
        int DI10_MSwitchOFF = 0;

        String[] YLabel = new String[]{
                "PWR", "CAB", "FWR", "BCK", "SLP", " EB", "TCO", " RB"
        };

        public int GetDI05_EB()
        {
            return DI05_EB;
        }

        void ConfigBIU(int biudata)
        {
            DI01_TCO = (int)((biudata>>0x07)&0x80);
            return;
        }

        BIU()
        {}
    }


    int tiunum = 0;
    int biunum = 0;

    private static volatile IOData instance=null;

    public static  IOData getInstance(){
        if(instance==null){
            synchronized(LineData .class){
                if(instance==null){
                    instance=new IOData ();
                }
            }
        }
        return instance;
    }

    public ArrayList<ILineDataSet> GetDataSets()
    {
        return dataSets;
    }

    private IOData()
    {
        // create a dataset and give it a type
        tiudata = new TIU();
        biudata = new BIU();
    }

    public ArrayList<Entry> GetTIUData()
    {
        return TIU;
    }
    public ArrayList<Entry> GetBIUData()
    {
        return BIU;
    }


    public void AddTIUData(ATPIFSocketProtocol protocol)
    {
        byte[] temp = protocol.getContent();
        Short TIUData = ByteUtil.getShortinvert(temp,3);
        TIU.add(new Entry(tiunum, TIUData));
        tiunum++;
    }

    public void AddBIUData(ATPIFSocketProtocol protocol)
    {
        byte[] temp = protocol.getContent();
        int BIUData = ByteUtil.getIntinvert(temp,1);
        BIU.add(new Entry(biunum, BIUData));
        biunum++;
    }


}
