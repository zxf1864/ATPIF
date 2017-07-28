package com.afei.atpif.CustomWidget;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by afei on 2017/7/16.
 */

public class SensorScopeDisplay extends SurfaceView implements SurfaceHolder.Callback {

    private ScopeThread renderer;
    private int width;
    private int height;
    private double[][] data = null;

    /* 数据集合 */
    private DataSet<Entry> PVHolders;
    /* 数据坐标点集合 */

    private List<Entry> PointsList = null;

    Paint[] availableColors = null;
    boolean entryEnabled[];

    int dataSize = 0;

    ScaleGestureDetector scaleDetector;
    // Loop counter for circular buffer
    int newestData = 0;
    // Number of entries to draw
    //设置默认的显示数据点个数，初始化为100个数据点，通过seekbar对numPtsToDraw进行操作。
    int numPtsToDraw = 100;

    // range values to display
    //!!!设置默认的显示数据的最大值为+/-700，建议在+/-500范围内为最佳显示效果，在手势动作中对range进行操作，以合适的比例显示，若要改变显示的幅值，请修改range的数值
    public int range = 700;
    private Paint grid_paint = new Paint();
    private Paint coordinate_paint = new Paint();
    Integer[] colors;
    private String[] names = null;



    private int selectIndex = -1;
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


    public SensorScopeDisplay(Context context, AttributeSet attributeSet) {
    // TODO Auto-generated constructor stub
        super(context, attributeSet);
        getHolder().addCallback(this);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        grid_paint.setColor(Color.rgb(232, 232 ,232));
        coordinate_paint.setColor(Color.rgb(139 ,137, 137));

        coordinate_paint.setTextSize(10.0f*getContext().getResources().getDisplayMetrics().density);
        coordinate_paint.setStrokeWidth(3.0f);

        data = new double[10][10];

        this.PointsList = new ArrayList<Entry>();
        this.PVHolders = new DataSet<Entry>(PointsList,"speed") {
            @Override
            public DataSet<Entry> copy() {
                return null;
            }
        };

    }

     public void setColors(Paint[] p){

       if( p.length != dataSize)
           dataSize = 0;

        availableColors = p;

        for( Paint p1 : availableColors) {
            p1.setTextSize(12.0f * getContext().getResources().getDisplayMetrics().density);
            p1.setStrokeWidth(2.0f);
        }
     }

    public void SetData(double[][] dd)
    {
        if(data == null)
            data = new double[dd.length][10];

        for(int i = 0; i < dd.length ; i++)
        {
            for(int j = 0; j<10;j++)
                data[i][j] = i;
        }
    }

     public void setNames(String[] n){
        names = n;
       }

     public void setDataSize( int d){
        dataSize = d;
        data = new double[dataSize][width];
        entryEnabled = new boolean[dataSize];

        }


    @Override
     protected void onDraw(Canvas canvas) {

      // clear screen
        canvas.drawColor(color_canvas);
        drawGrid(canvas);

        Paint mPaint = new Paint();


        Rect rt = canvas.getClipBounds();

        mPaint.setColor(0x6634DE71);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(38);

        canvas.drawRect(rt, mPaint);

        // scale the data to +- 500
        // target 0-height
        // so D in the range +-500
        // (D + 500) / 1000 * height


        float delta = (float)width / (float)numPtsToDraw;

        if((null == PointsList)||(PointsList.size()==0))
            return;

        for(int k= 0; k<PointsList.size(); k++){
            if(!entryEnabled[k])
                continue;

            if( PointsList.get(k).getX() > 0){
                int start = (newestData - numPtsToDraw + data[0].length) % data[0].length;
                int pos = 0;

                for(int i = start; i < start+numPtsToDraw; i++){

                    double y_i = data[k][i % data[0].length];
                    y_i = (y_i + range) / (2*range) * height;

                    double y_i1 = data[k][(i+1)% data[0].length];
                    y_i1 = (y_i1 + range) / (2*range) * height;

                    canvas.drawLine((float)pos*delta, (float)y_i, (float)(pos+1)*delta, (float)y_i1, availableColors[k]);
                    pos++;
                 }
            }
        }
        canvas.translate(20*(width/40)+1, height/2.0f);
        drawCoordinate(canvas,coordinate_paint);
        // lets draw us some labels

        /*if( names != null){
            canvas.translate(width/2, height/2);
             
            int pos = 0;
            float textHeight = availableColors[0].getFontMetrics().bottom - availableColors[0].getFontMetrics().top;
            int numEntries = 0;
            for( int i = 0; i < names.length; i++)
                if(entryEnabled[i])
                    numEntries++;
             
            float offset = (float)numEntries * textHeight * 1.2f - textHeight*.2f;
            canvas.translate(0, -height/2.0f + offset/2.0f);
 
            for( int i = 0; i < names.length; i++){
                if(entryEnabled[i]){
                    drawText(canvas, pos, names[i], availableColors[i], true);
                    pos ++;
                }
            }   
        }*/
     }

    // Draw text aligned correctly.
    private void drawText(Canvas canvas, int i, String text, Paint p, boolean left){
        Rect bounds = new Rect();
        p.getTextBounds(text, 0, text.length(), bounds);
        float textHeight = p.getFontMetrics().bottom - p.getFontMetrics().top;

        float y = (float) (height/2.0 - (float)i * textHeight * 1.2f - textHeight*.2);
        canvas.drawText(text, (float)(-width/2.0 + textHeight*.2f), y, p);
    }


    // Draw Coordinate correctly
    private void drawCoordinate(Canvas canvas,Paint p){
        float textHeight = p.getFontMetrics().bottom - p.getFontMetrics().top;

        coordinate_paint.setColor(color_cut_line);

        canvas.drawLine(-width/2.0f - 5.0f, 0, width/2.0f + 5.0f, 0,coordinate_paint);
        canvas.drawLine(0, -height/2.0f , 0, height/2.0f , coordinate_paint);
        for(int horizontal = 1; horizontal<10; horizontal++){
            NumberFormat numberformat = new DecimalFormat("0");
            canvas.drawLine(
                    -10.0f, -height/2.0f + 2*horizontal*(height/20),
                    10.0f, -height/2.0f + 2*horizontal*(height/20),
                    coordinate_paint);
            canvas.drawText(numberformat.format((5 - horizontal)*range/5.0),
                    20.0f, -height/2.0f + 2*horizontal*(height/20) + textHeight/4.0f, coordinate_paint);
        }
    }

    // Draw the grid
    private void drawGrid(Canvas canvas) {

        for(int vertical = 1; vertical<40; vertical++){
            canvas.drawLine(vertical*(width/40), 0,vertical*(width/40), height,grid_paint);
        }
        grid_paint.setColor(color_horizontal_line);
        for(int horizontal = 1; horizontal<20; horizontal++){
            canvas.drawLine(0, horizontal*(height/20),width, horizontal*(height/20),grid_paint);
        }
    }

    public void newFlightData(double[] d) {

        if( d.length != data.length){
            Log.d("Scope", "Incopatible data sizes");
            return;
        }

        if( data.length > 0){
            int newIndex = (newestData+1)% data[0].length;
            for( int i = 0; i < data.length; i++)
                if( data[i].length > newIndex)
                    data[i][newIndex] = d[i];
            newestData = newIndex;

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
        data = new double[dataSize][width];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderer = new ScopeThread(getHolder(), this);
        if(! renderer.isRunning()){
            renderer.setRunning(true);
            renderer.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        renderer.setRunning(false);
        while (retry) {
            try {
                renderer.join();
                renderer = null;
                retry = false;

            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    private class ScopeThread extends Thread {

        private SurfaceHolder _surfaceHolder;
        private SensorScopeDisplay scope;
        private boolean running = false;

        public ScopeThread(SurfaceHolder surfaceHolder, SensorScopeDisplay panel) {
            _surfaceHolder = surfaceHolder;
            scope = panel;
        }

        public boolean isRunning(){
            return running;
        }

        public void setRunning(boolean run) {
            running = run;
        }


        //SurfaceView 返回或Home后出错的问题参见：[url]http://blog.csdn.net/xiaominghimi/article/details/6149816[/url]
        //备注1：把 sfh.unlockCanvasAndPost(canvas); 写在finally中，主要是为了保证能正常的提交画布
        //备注2：在调用OnDraw之前一定要判定canvas是否为空，因为当程序切入后台的时候，canvas无法获取，canvas为空，
        //       这时提交画布就会出现参数异常的错误！
        public void run() {
            Canvas c;
            while (running) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        if (c != null) {
                            scope.onDraw(c);
                        }
                        //scope.onDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }


    }

    public int getEntryColor(int i) {

        if( i < availableColors.length)
            return availableColors[i].getColor();

        return Color.WHITE;
    }

    public boolean isActive(int i) {
        if( i < entryEnabled.length)
            return entryEnabled[i];

        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        super.onTouchEvent(ev);
        scaleDetector.onTouchEvent(ev);
        return true;

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            range /= detector.getScaleFactor();

            Log.d("Scale", range+"");
            range = Math.max(50, Math.min(range, 8000));//限制：range>50 , range<3000

            return true;
        }
    }

    public void disableEntry(int i) {
        if( i < entryEnabled.length)
            entryEnabled[i] = false;

    }

    public int enableEntry(int i) {
        if( i < entryEnabled.length){
            entryEnabled[i] = true;
            return availableColors[i].getColor();
        }

        return -1;
    }

    public void setDrawRate(int p) {
        if( p > 0)
            numPtsToDraw = width/p;
    }


}