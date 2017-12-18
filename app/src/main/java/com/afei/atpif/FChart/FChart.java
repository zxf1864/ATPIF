package com.afei.atpif.FChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.afei.atpif.CustomWidget.DataSet;
import com.afei.atpif.CustomWidget.Entry;
import com.afei.atpif.CustomWidget.LineData;
import com.afei.atpif.CustomWidget.MPPointF;
import com.afei.atpif.CustomWidget.Utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by afei on 2017/7/27.
 */

public class FChart extends View {

    private Context mContext;
    /* 画笔 */
    private Paint mPaint = null;

    public void setLinedata(List<LineData> linedata) {
        this.linedata = linedata;
    }

    public void addLineData(LineData ld) {
        this.linedata.add(ld);
    }

    public List<LineData> getLinedata() {
        return linedata;
    }

    private List<LineData> linedata;

    public XAxis getmXAxis() {
        return mXAxis;
    }


    /**
     * the object representing the labels on the x-axis
     */
    protected XAxis mXAxis;

    protected YAxis mYAxisSpeed;

    public YAxis getmYAxisIO() {
        return mYAxisIO;
    }

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

    /* 画布宽高 */
    private int w = 0;
    private int h = 0;
    /**
     * default constructor for initialization in code
     */
    public FChart(Context context) {
        super(context);
        init();
    }

    /**
     * constructor for initialization in xml
     */
    public FChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        w = getWidth();
        h = getHeight();

        // 设置画布颜色
        canvas.drawColor(Color.BLACK);
//        RectF rc = new RectF();
//        rc.set(0,0,w,h);
//        Path path = new Path();
//        path.addRect(rc, Path.Direction.CW);
//
//        mPaint.setColor(0x6634DE71);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAlpha(38);
//        canvas.drawPath(path, mPaint);
        // 绘制数据加载提示
        if (null == this.linedata) {
            drawChart(canvas);
        }
        // 绘制背景和数据
        else {
            drawChart(canvas);
        }
    }

    private float lastDistance = -1;
    private float currentDistance =-1;
    //private MPPointF[] lastPoint = new MPPointF[2];
    /**
     * initialize all paints and stuff
     */
    protected void init() {

        setWillNotDraw(false);
        // setLayerType(View.LAYER_TYPE_HARDWARE, null);

        mXAxis = new XAxis();

        mYAxisSpeed = new YAxis();

        mYAxisIO = new YAxis();
        mYAxisIO.CalIOnum();


        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // initialize the utils
        Utils.init(getContext());

        this.linedata = new ArrayList<LineData>();

        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //触摸
                        System.out.println("action down");
                        break;
                    case MotionEvent.ACTION_MOVE: //移动
                        System.out.println("action move");
                        //获取到当前坐标
                        System.out.println(String.format("x:%f,y:%f",event.getX(),event.getY()));

                        if (event.getPointerCount()>=2){
                            float offSetX=event.getX(0)-event.getX(1);
                            float offSetY=event.getY(0)-event.getY(1);
                            currentDistance= (float) Math.sqrt(offSetX*offSetX+offSetY*offSetY);

                            if (lastDistance<0){
                                lastDistance=currentDistance;
                            }
                            else if (currentDistance-lastDistance>5){
                                System.out.println("放大");

                                float middleY = mYAxisSpeed.getAxisMin() +
                                        mYAxisSpeed.getAxisRange()*(mSpeedBottom - event.getY(0))/(mSpeedBottom -mSpeedTop);

                                float span = mYAxisSpeed.getAxisRange()*currentDistance/lastDistance;

                                float max = middleY + (mYAxisSpeed.getAxisMax() - middleY)*currentDistance/lastDistance;

                                float min = middleY + (mYAxisSpeed.getAxisMin() - middleY)*currentDistance/lastDistance;
                                if(min<0)
                                    min =0;

                                mYAxisSpeed.setAxisMax(max);

                                mYAxisSpeed.setAxisMin(min);

                                mYAxisSpeed.setAxisRange(max-min);


                                lastDistance=-1;
                            }

                            else if (lastDistance-currentDistance>5){
                                System.out.println("缩小");

                                float middleY = mYAxisSpeed.getAxisMin() +
                                        mYAxisSpeed.getAxisRange()*(mSpeedBottom - event.getY(0))/(mSpeedBottom -mSpeedTop);

                                float span = mYAxisSpeed.getAxisRange()*currentDistance/lastDistance;

                                mYAxisSpeed.setAxisMax(middleY + (mYAxisSpeed.getAxisMax() - middleY)*currentDistance/lastDistance);

                                mYAxisSpeed.setAxisMin(middleY + (mYAxisSpeed.getAxisMin() - middleY)*currentDistance/lastDistance);

                                mYAxisSpeed.setAxisRange(span);

                                lastDistance=-1;
                            }
                        }

                    case MotionEvent.ACTION_UP: //离开
                        System.out.println("action up");
                        break;
                }

                System.out.println("count:"+event.getPointerCount());//获取触摸点个数
                          System.out.println(String.format("x1:%f,y1:%f,x2:%f,y2:%f",
                                  event.getX(0),event.getY(0),event.getX(1),event.getY(1)));
                          //获取多个触摸点的坐标
                return true;
            }
        });

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

    private float mSpeedTop = 0f;
    private float mSpeedBottom ;

    private float mTCRheight = 100f;
    private float mBTMheight = 40f;

    private float mIOTop;
    private float mIOBottom;

    protected void CalspeedIOpartNum()
    {
        int w = getWidth();
        int h = getHeight();

        IOYFormatter io = (IOYFormatter)mYAxisIO.getmAxisValueFormatter();

        if(io != null)
        {
            mIOBottom = h - 40f;
            mIOTop = mIOBottom - 30*io.mValues.size() - 40f;
            mSpeedBottom = mIOTop - mBTMheight - mTCRheight -40f;
            mSpeedTop = 40f;
        }
        else
        {
            mIOBottom = h - 40f;
            mIOTop = mIOBottom;
            mSpeedBottom = mIOTop - mBTMheight - mTCRheight - 40f;
            mSpeedTop = 40f;
        }


    }

    protected void drawChart(Canvas canvas) {


        if (mXAxis.isEnabled() == true)
            ;


        CalspeedIOpartNum();

        drawSpeedBottomLine(canvas);

        drawBaseLine(canvas);

        drawXaxisLabel(canvas);

        drawYaxis(canvas);

        drawSpeedYaxisLabel(canvas);

        DrawSpeedDataGrid(canvas);

        drawSpeedData(canvas);

        drawIOYaxisLabel(canvas);

        DrawIODataBaseLine(canvas);

        drawTCRLabel(canvas);

        drawTCR(canvas);

        drawBTMLabel(canvas);

        drawBalise(canvas);

        drawIO(canvas);

    }

    private void DrawIODataBaseLine(Canvas canvas)
    {
        int w = getWidth();
        int h = getHeight();

        mPaint.setTextSize(15);

        IOYFormatter io = (IOYFormatter)mYAxisIO.getmAxisValueFormatter();

        if(io == null)
            return;

        for (int i = 0; i < io.getmValues().size(); i++) {
            canvas.drawLine(100,mIOTop + 40f + 15f + 30*i,this.getRight(),mIOTop + 40f + 15f + 30*i,mPaint);
        }

    }

    private void DrawSpeedDataGrid(Canvas canvas)
    {
        mYAxisSpeed.calAxisInfo();

        int w = getWidth();
        int h = getHeight();

        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(2f);

        for (int i = 1; i < mYAxisSpeed.getAxisLabelsCount(); i++) {

            float y_pos = mSpeedBottom-i*(mSpeedBottom - mSpeedTop)/mYAxisSpeed.getAxisLabelsCount();

            canvas.drawLine(100, y_pos, this.getRight(), y_pos, mPaint);// 画线

        }
    }

    private void drawSpeedData(Canvas canvas)
    {
        Paint mSpeedLinePaint = new Paint();
        mSpeedLinePaint.setColor(Color.GREEN);
        mSpeedLinePaint.setStrokeWidth(3f);
        mSpeedLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mYAxisSpeed.calAxisInfo();

        int w = getWidth();
        int h = getHeight();

        float x = 100f;
        float y = mSpeedBottom;

        LineData ld = linedata.get(0);

//        mYAxisSpeed.setAxisMin(((DataSet<Entry>) ld.getDataSetByIndex(0)).getYMin());
//        mYAxisSpeed.setAxisMax(((DataSet<Entry>) ld.getDataSetByIndex(0)).getYMax());
//        mYAxisSpeed.setAxisRange(mYAxisSpeed.getAxisMax()-mYAxisSpeed.getAxisMin());

        for (int i = 0; i < ld.getDataSetByIndex(0).getEntryCount(); i++) {
            Entry data = ((DataSet<Entry>) ld.getDataSetByIndex(0)).getEntryForIndex(i);
            float curX = 100 + (data.getX() - mXAxis.getAxisMin()) * (w-100) / mXAxis.getAxisRange();
            float curY = 40 + mSpeedTop + (data.getY() - mYAxisSpeed.getAxisMin())*(mSpeedBottom-mSpeedTop)/mYAxisSpeed.getAxisRange();
            canvas.drawLine(x, y, curX, curY, mSpeedLinePaint);// 画线

            x=curX;
            y=curY;
        }


    }

    protected void drawSpeedBottomLine(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        Paint mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(Color.RED);
        mAxisLinePaint.setStrokeWidth(4f);
        mAxisLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawLine(this.getLeft(),
                mSpeedBottom, this.getRight(),
                mSpeedBottom, mAxisLinePaint);
    }

    protected void drawBaseLine(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        Paint mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(Color.WHITE);
        mAxisLinePaint.setStrokeWidth(4f);
        mAxisLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawLine(this.getLeft(),
                mIOTop, this.getRight(),
                mIOTop, mAxisLinePaint);
    }

    protected void drawXaxis(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        Paint mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(Color.WHITE);
        mAxisLinePaint.setStrokeWidth(2f);
        mAxisLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawLine(this.getLeft(),
                mSpeedBottom, this.getRight(),
                mSpeedBottom, mAxisLinePaint);
    }

    protected void drawYaxis(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        Paint mAxisLinePaint = new Paint();
        mAxisLinePaint.setColor(Color.WHITE);
        mAxisLinePaint.setStrokeWidth(2f);
        mAxisLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawLine(mYAxisSpeed.getOffsetYAxis(),
                mSpeedTop, mYAxisSpeed.getOffsetYAxis(),
                mIOBottom, mAxisLinePaint);
    }

    protected void drawXaxisLabel(Canvas canvas) {
        mXAxis.calAxisInfo();

        int w = getWidth();
        int h = getHeight();

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(20);


        for (int i = 0; i < mXAxis.getAxisLabelsCount(); i++) {

            Date d = new Date((long)mXAxis.getAxisMin() + mXAxis.getBaseTime() + i*60000);
            SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

            String str = sdf.format(d);

            canvas.drawText(str, 100 + i * (w -100) / mXAxis.getAxisLabelsCount() + 5, mIOTop + 20 + 5, mPaint);

        }

    }

    protected void drawSpeedYaxisLabel(Canvas canvas) {
        mYAxisSpeed.calAxisInfo();

        int w = getWidth();
        int h = getHeight();

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(36);

        for (int i = 0; i < mYAxisSpeed.getAxisLabelsCount(); i++) {

            String ylabel = "";

            float ylabelnum = mYAxisSpeed.getAxisMin() + i*mYAxisSpeed.getAxisRange()/mYAxisSpeed.getAxisLabelsCount();

            int ylabel_int = Math.round(ylabelnum);

            ylabel = String.valueOf(ylabel_int);

            float ylabel_width = mPaint.measureText(ylabel);

            canvas.drawText(ylabel, -5 + 100 - ylabel_width, mSpeedBottom -i*(mSpeedBottom - mSpeedTop)/mYAxisSpeed.getAxisLabelsCount(), mPaint);

        }

    }

    protected void drawIOYaxisLabel(Canvas canvas) {

        int w = getWidth();
        int h = getHeight();

        mPaint.setTextSize(15);

        IOYFormatter io = (IOYFormatter)mYAxisIO.getmAxisValueFormatter();

        if(io == null)
            return;

        for (int i = 0; i < io.getmValues().size(); i++) {

              String ylabel = io.mValues.get(i);
//
//            float ylabelnum = i*mYAxisSpeed.getAxisRange()/mYAxisSpeed.getAxisLabelsCount();
//
//            int ylabel_int = Math.round(ylabelnum);
//
//            ylabel = String.valueOf(ylabel_int);

              float ylabel_width = mPaint.measureText(ylabel);
//
              canvas.drawText(ylabel, -5 + 100 - ylabel_width, mIOTop +40f +20f + 30*i, mPaint);

        }

    }

    protected void drawTCRLabel(Canvas canvas) {

        mPaint.setTextSize(20);

        float ylabel_width = mPaint.measureText("TCR");

        canvas.drawText("TCR", -5 + 100 - ylabel_width, mSpeedBottom + mTCRheight/2, mPaint);

    }

    protected void drawTCR(Canvas canvas) {
        Paint mTCRLinePaint = new Paint();
        mTCRLinePaint.setColor(Color.RED);
        mTCRLinePaint.setStrokeWidth(1f);
        mTCRLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        int w = getWidth();
        int h = getHeight();

        LineData ld = linedata.get(0);

        float x = 100f;

        for (int i = 0; i < ld.getDataSetByIndex(0).getEntryCount(); i++) {
            Entry data = ((DataSet<Entry>) ld.getDataSetByIndex(0)).getEntryForIndex(i);
            float curX = 100 + (data.getX() - mXAxis.getAxisMin()) * (w-100) / mXAxis.getAxisRange();

            switch (0x3a) {
                case 0x00:
                    mTCRLinePaint.setColor(Color.GRAY);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/2);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x03:  //H
                    mTCRLinePaint.setColor(Color.RED);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/2);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x05: //27.9
                    mTCRLinePaint.setColor(Color.parseColor("#FFC0CB"));
                    mTCRLinePaint.setStrokeWidth(mTCRheight/2);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x06: //L4
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/10);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/10, curX, mSpeedBottom +mTCRheight/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/10, curX, mSpeedBottom +mTCRheight*3/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*7/10, curX, mSpeedBottom +mTCRheight*7/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*9/10, curX, mSpeedBottom +mTCRheight*9/10, mTCRLinePaint);
                break;

                case 0x09: //HU
                    mTCRLinePaint.setColor(Color.RED);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.YELLOW);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;
                case 0x0a: //U3
                    mTCRLinePaint.setColor(Color.YELLOW);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/6);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/6, curX, mSpeedBottom +mTCRheight/6, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/6, curX, mSpeedBottom +mTCRheight*3/6, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*5/6, curX, mSpeedBottom +mTCRheight*5/6, mTCRLinePaint);
                    break;

                case 0x0c: //UUS
                    mTCRLinePaint.setColor(Color.parseColor("#FFA500"));
                    mTCRLinePaint.setStrokeWidth(mTCRheight*4/5);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.BLACK);
                    mTCRLinePaint.setStrokeWidth(5f);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x11: //25.7
                    mTCRLinePaint.setColor(Color.parseColor("#F5F5F5"));
                    mTCRLinePaint.setStrokeWidth(mTCRheight*4/5);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x12: //L5
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/10);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/10, curX, mSpeedBottom +mTCRheight/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/10, curX, mSpeedBottom +mTCRheight*3/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*5/10, curX, mSpeedBottom +mTCRheight*5/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*7/10, curX, mSpeedBottom +mTCRheight*7/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*9/10, curX, mSpeedBottom +mTCRheight*9/10, mTCRLinePaint);
                    break;

                case 0x14: //UU
                    mTCRLinePaint.setColor(Color.YELLOW);
                    mTCRLinePaint.setStrokeWidth(mTCRheight*4/5);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.BLACK);
                    mTCRLinePaint.setStrokeWidth(5f);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

                case 0x18:  //LU2

                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/6, curX, mSpeedBottom +mTCRheight/6, mTCRLinePaint);

                    mTCRLinePaint.setColor(Color.YELLOW);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/3);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*4/6, curX, mSpeedBottom +mTCRheight*4/6, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.BLACK);
                    mTCRLinePaint.setStrokeWidth(5f);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*4/6, curX, mSpeedBottom +mTCRheight*4/6, mTCRLinePaint);
                    break;

                case 0x21: //HB
                    mTCRLinePaint.setColor(Color.RED);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.WHITE);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;

                case 0x22: //U2S
                    mTCRLinePaint.setColor(Color.parseColor("#FFA500"));
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;

                case 0x24:
                    mTCRLinePaint.setColor(Color.YELLOW);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;

                case 0x28: //U2
                    mTCRLinePaint.setColor(Color.YELLOW);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;
                case 0x30:  //LU
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/4);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/4, curX, mSpeedBottom +mTCRheight/4, mTCRLinePaint);
                    mTCRLinePaint.setColor(Color.YELLOW);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*3/4, curX, mSpeedBottom +mTCRheight*3/4, mTCRLinePaint);
                    break;

                case 0x36: //绿3
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/10);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/10, curX, mSpeedBottom +mTCRheight/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*5/10, curX, mSpeedBottom +mTCRheight*5/10, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*9/10, curX, mSpeedBottom +mTCRheight*9/10, mTCRLinePaint);
                    break;

                case 0x3a: //绿1
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight*4/5);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;
                case 0x3c: //绿2
                    mTCRLinePaint.setColor(Color.GREEN);
                    mTCRLinePaint.setStrokeWidth(mTCRheight/3);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/6, curX, mSpeedBottom +mTCRheight/6, mTCRLinePaint);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight*5/6, curX, mSpeedBottom +mTCRheight*5/6, mTCRLinePaint);
                    break;


                default:
                    mTCRLinePaint.setColor(Color.WHITE);
                    mTCRLinePaint.setStrokeWidth(mTCRheight*4/5);
                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2, curX, mSpeedBottom +mTCRheight/2, mTCRLinePaint);
                    break;

//                case 5:
//                    mTCRLinePaint.setColor(Color.GREEN);
//                    mTCRLinePaint.setStrokeWidth(2f);
//                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2 - 10, curX, mSpeedBottom +mTCRheight/2 - 10, mTCRLinePaint);
//                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2 - 5, curX, mSpeedBottom +mTCRheight/2 - 5, mTCRLinePaint);
//                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2 , curX, mSpeedBottom +mTCRheight/2 , mTCRLinePaint);
//                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2 + 5, curX, mSpeedBottom +mTCRheight/2 + 5, mTCRLinePaint);
//                    canvas.drawLine(x, mSpeedBottom +mTCRheight/2 + 10, curX, mSpeedBottom +mTCRheight/2 + 10, mTCRLinePaint);
//                    break;

            }


            x = curX;

        }

    }

    protected void drawBTMLabel(Canvas canvas) {

        mPaint.setTextSize(20);

        float ylabel_width = mPaint.measureText("BTM");

        canvas.drawText("BTM", -5 + 100 - ylabel_width, mSpeedBottom + mTCRheight + mBTMheight/2, mPaint);

    }

    private void drawBalise(Canvas canvas) {
        Paint mBTMLinePaint = new Paint();
        mBTMLinePaint.setColor(Color.BLUE);
        mBTMLinePaint.setStrokeWidth(5f);
        mBTMLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        int w = getWidth();
        int h = getHeight();

        LineData ld = linedata.get(0);

        float x = 100f;

        for (int i = 0; i < ld.getDataSetByIndex(1).getEntryCount(); i++) {
            Entry data = ((DataSet<Entry>) ld.getDataSetByIndex(1)).getEntryForIndex(i);
            float curX = 100 + (data.getX() - mXAxis.getAxisMin()) * (w-100) / mXAxis.getAxisRange();
            // 绘制这个三角形,你可以绘制任意多边形
            Path path = new Path();
            path.moveTo(curX, mSpeedBottom +mTCRheight + mBTMheight/2);// 此点为多边形的起点
            path.lineTo(curX + 10, mSpeedBottom + +mTCRheight + mBTMheight/2 - 10);
            path.lineTo(curX + 20, mSpeedBottom  +mTCRheight + mBTMheight/2);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, mBTMLinePaint);

        }


    }

    private void drawIO(Canvas canvas) {
        Paint mIOLinePaint = new Paint();
        mIOLinePaint.setColor(Color.parseColor("#FFA500"));
        mIOLinePaint.setStrokeWidth(5f);
        //mIOLinePaint.setStyle(Paint.Style.STROKE);
        mIOLinePaint.setStyle(Paint.Style.FILL);//设置填满

        int w = getWidth();
        int h = getHeight();

        LineData ld = linedata.get(0);

        float x = 100f;

        for (int i = 0; i < ld.getDataSetByIndex(2).getEntryCount(); i++) {
            Entry data = ((DataSet<Entry>) ld.getDataSetByIndex(2)).getEntryForIndex(i);
            float curX = 100 + (data.getX() - mXAxis.getAxisMin()) * (w-100) / mXAxis.getAxisRange();

            if (data.getY() > 0) {
                canvas.drawRect(x, mIOTop + 40f +5f, curX, mIOTop + 40f +25f, mIOLinePaint);// 长方形
                canvas.drawRect(x, mIOTop + 40f + 30f +5f, curX, mIOTop + 40f +30f+25f, mIOLinePaint);// 长方形  }
                canvas.drawRect(x, mIOTop + 40f + 60f +5f, curX, mIOTop + 40f +60f+25f, mIOLinePaint);// 长方形  }
                canvas.drawRect(x, mIOTop + 40f + 90f +5f, curX, mIOTop + 40f +90f+25f, mIOLinePaint);// 长方形  }
                canvas.drawRect(x, mIOTop + 40f + 120f +5f, curX, mIOTop + 40f +120f+25f, mIOLinePaint);// 长方形  }
                canvas.drawRect(x, mIOTop + 40f + 150f +5f, curX, mIOTop + 40f +150f+25f, mIOLinePaint);// 长方形  }
                canvas.drawRect(x, mIOTop + 40f + 180f +5f, curX, mIOTop + 40f +180f+25f, mIOLinePaint);// 长方形  }

            }

            x = curX;
        }


    }







}
