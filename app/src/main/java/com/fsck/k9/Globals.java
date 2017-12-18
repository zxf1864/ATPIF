package com.fsck.k9;

/**
 * Created by afei on 2017/12/5.
 */

import android.content.Context;


public class Globals {
    private static Context context;

    static void setContext(Context context) {
        Globals.context = context;
    }

    public static Context getContext() {
        if (context == null) {
            throw new IllegalStateException("No context provided");
        }

        return context;
    }
}
