package com.afei.atpif.FChart;

import android.graphics.Color;
import android.graphics.Typeface;

import com.afei.atpif.CustomWidget.ComponentBase;
import com.afei.atpif.CustomWidget.IAxisValueFormatter;
import com.afei.atpif.CustomWidget.Utils;

/**
 * Created by afei on 2017/7/28.
 */

public class axisBase extends ComponentBase {

    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    protected IAxisValueFormatter mAxisValueFormatter;

    private int mGridColor = Color.GRAY;

    private float mGridLineWidth = 1f;

    private int mAxisLineColor = Color.GRAY;

    private float mAxisLineWidth = 1f;

    private float axisMax = 0f;

    private float axisMin = 0f;

    private float axisRange;

    private int axisDataCount = 100;

    private int axisLabelsCount = 10;







}
