package com.afei.atpif;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afei.atpif.CustomWidget.Legend;
import com.afei.atpif.CustomWidget.LineChart;
import com.afei.atpif.CustomWidget.LineData;
import com.afei.atpif.CustomWidget.XAxis;
import com.afei.atpif.CustomWidget.YAxis;
import com.afei.atpif.R;

import android.widget.TextView;
import android.os.SystemClock;
import android.util.Log;
import hugo.weaving.DebugLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    private View view;
    TextView tv;

    private LineChart ss;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        tv = (TextView) view.findViewById(R.id.tv_checkHugo);
        tv.setText("Check logcat!");


        printArgs("The", "Quick", "Brown", "Fox");

        Log.i("Fibonacci", "fibonacci's 4th number is " + fibonacci(4));

        Greeter greeter = new Greeter("Jake");
        Log.d("Greeting", greeter.sayHello());

        Charmer charmer = new Charmer("Jake");
        Log.d("Charming", charmer.askHowAreYou());

        startSleepyThread();

        ss = (LineChart) view.findViewById(R.id.id_line_ll);

        // enable description text
        ss.getDescription().setEnabled(true);

        // enable touch gestures
        ss.setTouchEnabled(true);

        // enable scaling and dragging
        ss.setDragEnabled(true);
        ss.setScaleEnabled(true);
        ss.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        ss.setPinchZoom(true);

        // set an alternative background color
        ss.setBackgroundColor(Color.LTGRAY);


        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        ss.setData(data);

        // get the legend (only possible after setting data)
        Legend l = ss.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        l.setTextColor(Color.WHITE);

        XAxis xl = ss.getXAxis();

        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = ss.getAxisLeft();

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = ss.getAxisRight();
        rightAxis.setEnabled(false);


        return view;
    }






    @DebugLog
    private void printArgs(String... args) {
        for (String arg : args) {
            Log.i("Args", arg);
        }
    }

    @DebugLog
    private int fibonacci(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be greater than zero.");
        }
        if (number == 1 || number == 2) {
            return 1;
        }
        // NOTE: Don't ever do this. Use the iterative approach!
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    private void startSleepyThread() {
        new Thread(new Runnable() {
            private static final long SOME_POINTLESS_AMOUNT_OF_TIME = 50;

            @Override public void run() {
                sleepyMethod(SOME_POINTLESS_AMOUNT_OF_TIME);
            }

            @DebugLog
            private void sleepyMethod(long milliseconds) {
                SystemClock.sleep(milliseconds);
            }
        }, "I'm a lazy thr.. bah! whatever!").start();
    }

    @DebugLog
    static class Greeter {
        private final String name;

        Greeter(String name) {
            this.name = name;
        }

        private String sayHello() {
            return "Hello, " + name;
        }
    }

    @DebugLog
    static class Charmer {
        private final String name;

        private Charmer(String name) {
            this.name = name;
        }

        public String askHowAreYou() {
            return "How are you " + name + "?";
        }
    }

}
