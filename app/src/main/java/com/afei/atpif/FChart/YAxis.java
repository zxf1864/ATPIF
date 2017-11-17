package com.afei.atpif.FChart;

/**
 * Created by afei on 2017/7/28.
 */

public class YAxis extends axisBase {


    public int getOffsetYAxis() {
        return offsetYAxis;
    }

    private int offsetYAxis = 100;

    public float getPartNum() {
        return partNum;
    }

    public void setPartNum(float partNum) {
        this.partNum = partNum;
    }

    private float partNum = 0.5f;

    public YAxis()
    {
        super();

        this.setAxisMin(0);
        this.setAxisMax(400);
    }

    public void CalIOnum()
    {


    }
}
