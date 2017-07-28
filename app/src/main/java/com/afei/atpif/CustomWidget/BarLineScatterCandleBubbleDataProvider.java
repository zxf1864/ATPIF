package com.afei.atpif.CustomWidget;

/**
 * Created by afei on 2017/7/21.
 */
import com.afei.atpif.CustomWidget.YAxis;



public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);

    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
