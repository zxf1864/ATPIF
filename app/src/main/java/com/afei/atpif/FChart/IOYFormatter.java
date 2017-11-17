package com.afei.atpif.FChart;

import java.util.List;

/**
 * Created by afei on 2017/7/27.
 */


public class IOYFormatter implements IAxisValueFormatter {
    public List<String> getmValues() {
        return mValues;
    }

    List<String> mValues;

    public IOYFormatter(List<String> values) {
        this.mValues = values;
    }
    @Override
    public String getFormattedValue(float value, axisBase axis) {
        int i = (int) value % mValues.size();
        return mValues.get(i);
    }
}