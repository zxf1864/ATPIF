package com.afei.atpif.CustomWidget;

/**
 * Created by afei on 2017/7/21.
 */

/**
 * Interface for providing a custom logic to where the filling line of a LineDataSet
 * should end. This of course only works if setFillEnabled(...) is set to true.
 *
 * @author Philipp Jahoda
 */
public interface IFillFormatter
{

    /**
     * Returns the vertical (y-axis) position where the filled-line of the
     * LineDataSet should end.
     *
     * @param dataSet the ILineDataSet that is currently drawn
     * @param dataProvider
     * @return
     */
    float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider);
}
