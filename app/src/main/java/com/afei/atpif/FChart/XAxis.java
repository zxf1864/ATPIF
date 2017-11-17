package com.afei.atpif.FChart;

/**
 * Created by afei on 2017/7/28.
 */

public class XAxis extends axisBase{

    public int getOffsetXAixs() {
        return offsetXAixs;
    }

    private int offsetXAixs = 10;

    public void setBaseTime(long baseTime) {
        this.baseTime = baseTime;
    }

    public long getBaseTime() {
        return baseTime;
    }

    private long baseTime = 0;

    public XAxis()
    {}

}
