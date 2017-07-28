package com.afei.atpif.CustomWidget;

/**
 * Created by afei on 2017/7/21.
 */



public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
