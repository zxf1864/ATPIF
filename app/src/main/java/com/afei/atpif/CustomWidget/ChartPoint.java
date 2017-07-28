package com.afei.atpif.CustomWidget;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.utils.Utils;

/**
 * Created by afei on 2017/7/18.
 */

public class ChartPoint extends Point {

    public int x;
    public int y;

    public ChartPoint(int x, int y) {
        set(x, y);
        this.x = x;
        this.y = y;
    }
}


