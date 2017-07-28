package com.afei.atpif.FChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.afei.atpif.CustomWidget.LineData;
import com.afei.atpif.CustomWidget.Utils;



import java.util.List;

/**
 * Created by afei on 2017/7/27.
 */

public class FChart extends View{

    private Context mContext;
    /* 画笔 */
    private Paint mPaint = null;

    private List<LineData> linedata;

    /**
     * the object representing the labels on the x-axis
     */
    protected XAxis mXAxis;

    protected YAxis mYAxisSpeed;

    protected YAxis mYAxisIO;

    /**
     * object that manages the bounds and drawing constraints of the chart
     */
    //protected ViewPortHandler mViewPortHandler = new ViewPortHandler();

    /* 画布颜色 */
    private int color_canvas = Color.parseColor("#212933");
    /* 上下分割线颜色 */
    private int color_cut_line = Color.parseColor("#808080");
    /* 水平线颜色 */
    private int color_horizontal_line = Color.parseColor("#323e4d");
    /* 垂直线颜色 */
    private int color_vertical_line = Color.parseColor("#50b255");
    /* 数据线颜色 */
    private int color_data_line = Color.parseColor("#ffaa00");



    /**
     * default constructor for initialization in code
     */
    public FChart(Context context) {
        super(context);

    }

    /**
     * constructor for initialization in xml
     */
    public FChart(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();

        // 设置画布颜色
        canvas.drawColor(color_canvas);
        // 绘制数据加载提示
        if (null == this.linedata) {
            drawChart(canvas);
        }
        // 绘制背景和数据
        else {
            drawChart(canvas);
        }
    }

    /**
     * initialize all paints and stuff
     */
    protected void init() {

        setWillNotDraw(false);
        // setLayerType(View.LAYER_TYPE_HARDWARE, null);

        mXAxis = new XAxis();

        // initialize the utils
        Utils.init(getContext());

        /*

          if (android.os.Build.VERSION.SDK_INT < 11)
            mAnimator = new ChartAnimator();
        else
            mAnimator = new ChartAnimator(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // ViewCompat.postInvalidateOnAnimation(Chart.this);
                    postInvalidate();
                }
            });

        mMaxHighlightDistance = Utils.convertDpToPixel(500f);

        mDescription = new Description();
        mLegend = new Legend();

        mLegendRenderer = new LegendRenderer(mViewPortHandler, mLegend);

        mDescPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (mLogEnabled)
            Log.i("", "Chart.init()");
         */

    }

    protected void drawChart(Canvas canvas)
    {
        if(mXAxis.isEnabled() == true)
            ;

        if(mXAxis.isEnabled() == true)
            ;

        if(mXAxis.isEnabled() == true)
            ;

        drawXaxis(canvas);

    }

    protected void drawXaxis(Canvas canvas)
    {
        int w = getWidth();
        int h = getHeight();

        Paint mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(Color.RED);
        mAxisLinePaint.setStrokeWidth(1f);
        mAxisLinePaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(this.getLeft(),
                h/2, this.getRight(),
                h/2, mAxisLinePaint);
    }

    protected void drawXaxisLabel(Canvas canvas)
    {
        long curTimeMilis = System.currentTimeMillis();




    }









}
