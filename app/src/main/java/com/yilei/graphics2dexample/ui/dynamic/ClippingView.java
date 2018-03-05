package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lei on 2018/3/5.
 */

public class ClippingView extends View{
    public ClippingView(Context context) {
        this(context, null);
    }

    public ClippingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClippingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
