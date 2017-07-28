package com.afei.atpif.CustomWidget;

/**
 * Created by afei on 2017/7/21.
 */

/**
 * Abstract baseclass of all Renderers.
 *
 * @author Philipp Jahoda
 */
public abstract class Renderer {

    /**
     * the component that handles the drawing area of the chart and it's offsets
     */
    protected ViewPortHandler mViewPortHandler;

    public Renderer(ViewPortHandler viewPortHandler) {
        this.mViewPortHandler = viewPortHandler;
    }
}