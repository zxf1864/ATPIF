package com.afei.atpif.FChart;

import android.graphics.Color;

import com.afei.atpif.CustomWidget.AxisBase;
import com.afei.atpif.CustomWidget.ComponentBase;

import java.util.List;

/**
 * Created by afei on 2017/7/28.
 */

public class axisBase extends ComponentBase {

    public IAxisValueFormatter getmAxisValueFormatter() {
        return mAxisValueFormatter;
    }

    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    protected IAxisValueFormatter mAxisValueFormatter;

    private int mGridColor = Color.GRAY;


    private float mGridLineWidth = 1f;

    private int mAxisLineColor = Color.GRAY;

    private float mAxisLineWidth = 1f;

    public void setAxisMax(float axisMax) {
        this.axisMax = axisMax;
    }

    public float getAxisMax() {
        return axisMax;
    }

    private float axisMax = 0f;

    public void setAxisMin(float axisMin) {
        this.axisMin = axisMin;
    }

    public float getAxisMin() {
        return axisMin;
    }

    private float axisMin = 0f;

    public float getAxisRange() {
        return axisRange;
    }

    public void setAxisRange(float axisRange) {
        this.axisRange = axisRange;
    }

    private float axisRange;

    private int axisDataCount;

    private float axisSpan ;

    public int getAxisLabelsCount() {
        return axisLabelsCount;
    }

    private int axisLabelsCount = 5;

    /**
     * the number of decimal digits to use
     */
    public int mDecimals;


    public axisBase()
    {

    }


    /**
     * Sets the formatter to be used for formatting the axis labels. If no formatter is set, the
     * chart will
     * automatically determine a reasonable formatting (concerning decimals) for all the values
     * that are drawn inside
     * the chart. Use chart.getDefaultValueFormatter() to use the formatter calculated by the chart.
     *
     * @param f
     */
    public void setValueFormatter(IAxisValueFormatter f) {

        if (f == null)
            mAxisValueFormatter = new DefaultAxisValueFormatter(mDecimals);
        else
            mAxisValueFormatter = f;
    }


    public void calAxisInfo()
    {
        axisRange = axisMax - axisMin;

        axisDataCount =  Math.round(axisRange/axisSpan);


    }



}
