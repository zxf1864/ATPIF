package com.afei.atpif;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.afei.atpif.CustomWidget.ColorTemplate;
import com.afei.atpif.CustomWidget.Entry;
import com.afei.atpif.CustomWidget.ILineDataSet;
import com.afei.atpif.CustomWidget.IOYFormatter;
import com.afei.atpif.CustomWidget.Legend;
import com.afei.atpif.CustomWidget.LineData;
import com.afei.atpif.CustomWidget.LineDataSet;
import com.afei.atpif.CustomWidget.XAxis;
import com.afei.atpif.CustomWidget.YAxis;
import com.afei.atpif.FChart.FChart;
import com.afei.atpif.Model.IOData;
import com.afei.atpif.Model.SpeedData;
import com.afei.atpif.CustomWidget.LineChart;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineFragment extends BaseFragment {

    private View view;
    private FChart mSpeedChart;
    private LineChart mIOChart;



    public LineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_line, container, false);

        ConfigSpeedChart();

        //ConfigIOChart();


        //RefreshUI();


        return view;
    }

    private void ConfigSpeedChart()
    {
        mSpeedChart = (FChart) view.findViewById(R.id.line_speed);

        mSpeedChart.invalidate();


        /*
        // enable description text
        mSpeedChart.getDescription().setEnabled(true);

        // enable touch gestures
        mSpeedChart.setTouchEnabled(true);

        // enable scaling and dragging
        mSpeedChart.setDragEnabled(true);
        mSpeedChart.setScaleEnabled(true);
        mSpeedChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mSpeedChart.setPinchZoom(true);

        // set an alternative background color
        mSpeedChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mSpeedChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mSpeedChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        l.setTextColor(Color.WHITE);

        XAxis xl = mSpeedChart.getXAxis();

        xl.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置

        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisLineColor(Color.BLUE);
        xl.setAxisMaximum(100f);
        xl.setAxisMinimum(0f);

        xl.setEnabled(true);


        YAxis leftAxis = mSpeedChart.getAxisLeft();
        leftAxis.setAxisLineColor(Color.BLUE);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);


        YAxis rightAxis = mSpeedChart.getAxisRight();
        rightAxis.setAxisLineColor(Color.RED);
        rightAxis.setAxisMaximum(2000f);
        rightAxis.setAxisMinimum(0f);

        rightAxis.setEnabled(true);


        */
    }


    private void ConfigIOChart()
    {
        //mIOChart = (LineChart) view.findViewById(R.id.id_line_IO);

        // enable description text
        mIOChart.getDescription().setEnabled(true);

        // enable touch gestures
        mIOChart.setTouchEnabled(true);

        // enable scaling and dragging
        mIOChart.setDragEnabled(true);
        mIOChart.setScaleEnabled(true);
        mIOChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mIOChart.setPinchZoom(true);

        // set an alternative background color
        mIOChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mIOChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mIOChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        l.setTextColor(Color.WHITE);

        XAxis xl = mIOChart.getXAxis();

        xl.setTextColor(Color.WHITE);
        xl.setAxisLineColor(Color.RED);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mIOChart.getAxisLeft();

        leftAxis.setTextColor(Color.RED);

        leftAxis.setDrawGridLines(true);

        List<String> labels =  new ArrayList<>();
        labels.add("PWN");
        labels.add("CAB");
        labels.add(" EB");

        IOYFormatter myXFormatter = new IOYFormatter(labels);
        leftAxis.setValueFormatter(myXFormatter);

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
        //mSpeedChart.setData(data);
    }

    private LineDataSet createSpeedSet() {

        LineDataSet set = new LineDataSet(null, "速度");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.BLUE);
        //set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createAccSet() {

        LineDataSet set = new LineDataSet(null, "加速度");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.BLUE);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.RED);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createPostionSet() {

        LineDataSet set = new LineDataSet(null, "位置");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createPWRSet() {

        LineDataSet set = new LineDataSet(null, "电源");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createCABSet() {

        LineDataSet set = new LineDataSet(null, "激活");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createFWRSet() {

        LineDataSet set = new LineDataSet(null, "前向");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


    private synchronized void UpdateIOLine()
    {
        LineData data = mIOChart.getData();

        com.afei.atpif.Model.LineData ld = com.afei.atpif.Model.LineData.getInstance();
        IOData IO = ld.GetIOData();

        ArrayList<Entry> TIUtemp = IO.GetTIUData();
        ArrayList<Entry> BIUtemp = IO.GetBIUData();


        if ((data != null)&&((TIUtemp.size()>0)||(BIUtemp.size()>0))) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createPWRSet();
                data.addDataSet(set);
                /*
                ILineDataSet cabset = createCABSet();
                data.addDataSet(cabset);
                ILineDataSet fwrset = createFWRSet();
                data.addDataSet(fwrset);
                */
            }


            for (int i=data.getEntryCount();i<BIUtemp.size();i++)
            {
                IO.setBiudata((int)BIUtemp.get(i).getY());
                data.addEntry(new Entry(BIUtemp.get(i).getX(),IO.getBIU().GetDI05_EB()), 0);

            }

            data.notifyDataChanged();

            // let the chart know it's data has changed
            mIOChart.notifyDataSetChanged();

            // limit the number of visible entries
            //mChart.setVisibleXRangeMinimum(0);
            //mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mIOChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }

    }

    /*
    private synchronized void UpdateSpeedLine()
    {
        LineData data = mSpeedChart.getData();

        com.afei.atpif.Model.LineData ld = com.afei.atpif.Model.LineData.getInstance();
        SpeedData sd = ld.GetSpeedData();

        ArrayList<Entry> acctemp = sd.GetaccData();
        ArrayList<Entry> speedtemp = sd.GetSpeedData();
        ArrayList<Entry> postiontemp = sd.GetpotionData();

        if ((data != null)&&(speedtemp.size()>0)) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSpeedSet();
                data.addDataSet(set);
                ILineDataSet accset = createAccSet();
                data.addDataSet(accset);
                ILineDataSet postionset = createPostionSet();
                data.addDataSet(postionset);
            }


            for (int i=data.getEntryCount()/3;i<speedtemp.size();i++)
            {
                data.addEntry(speedtemp.get(i), 0);
                data.addEntry(acctemp.get(i), 1);
                data.addEntry(postiontemp.get(i), 2);
            }

            data.notifyDataChanged();

            // let the chart know it's data has changed
            mSpeedChart.notifyDataSetChanged();

            // limit the number of visible entries
            //mChart.setVisibleXRangeMinimum(0);
            //mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mSpeedChart.moveViewToX(data.getEntryCount());

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
                UpdateSpeedLine();
                UpdateIOLine();
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
   */

}
