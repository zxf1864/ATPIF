package com.afei.atpif;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineFragment extends BaseFragment {

    private View view;
    private LineChart mChart;


    public LineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_line, container, false);

        mChart = (LineChart) view.findViewById(R.id.line_speed);


        // enable description text
        mChart.getDescription().setEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        l.setTextColor(Color.WHITE);

        XAxis xl = mChart.getXAxis();

        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        RefreshUI();

        return view;
    }


    private void setAxis()
    {
        //上面右边效果图的部分代码，设置X轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
//        xAxis.setTypeface(mTf); // 设置字体
        xAxis.setEnabled(false);
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        xAxis.setDrawAxisLine(true);

        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
        xAxis.setDrawGridLines(true); // 效果如下图

        // 上面左边效果图的代码
        // 两个都设置为 true
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);

        // 上面右边效果图的代码
        xAxis.setEnabled(false);
        // xAxis.setEnabled(false);则下面绘制的Grid不会有 "竖的线(与X轴有关)"
        xAxis.setDrawGridLines(true);

        xAxis.setTextColor(Color.BLUE);
        xAxis.setTextSize(24f);
        xAxis.setGridLineWidth(10f);
        xAxis.setGridColor(Color.RED);
        xAxis.setAxisLineColor(Color.GREEN);
        xAxis.setAxisLineWidth(5f);

    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    //ArrayList<Entry> speed = new ArrayList<Entry>();
    //ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    private void setData(int count, float range) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> valuesA = new ArrayList<Entry>();

        ArrayList<Entry> valuesB = new ArrayList<Entry>();

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        float from = now;

        // count = hours
        float to = now + count;

        // increment by 1 hour
        for (float x = from; x < to; x++) {

            float y = 50 + getRandom(range, 10);
            valuesA.add(new Entry(x, y)); // add one entry per hour
            valuesB.add(new Entry(x, y+50));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(valuesA, "速度");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        LineDataSet set2 = new LineDataSet(valuesB, "DataSet 2");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(ColorTemplate.getHoloBlue());
        set2.setValueTextColor(ColorTemplate.getHoloBlue());
        set2.setLineWidth(1.5f);
        set2.setDrawCircles(false);
        set2.setDrawValues(false);
        set2.setFillAlpha(65);
        set2.setFillColor(ColorTemplate.getHoloBlue());
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setDrawCircleHole(false);

        dataSets.add(set1);
        dataSets.add(set2);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);


        // set data
        mChart.setData(data);
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


    private synchronized void UpdateLine()
    {
        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            //mChart.setVisibleXRangeMinimum(0);
            //mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }

    }

    private Thread thread;
    private void RefreshUI() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                UpdateLine();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (thread != null) {

                    // Don't generate garbage runnables inside the loop.
                    getActivity().runOnUiThread(runnable);

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


}
