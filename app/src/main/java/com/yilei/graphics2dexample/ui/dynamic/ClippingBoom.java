package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.yilei.graphics2dexample.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lei on 2018/3/6.
 * 播放过程中，剪切区（clip）是固定不动的，实际上移动的恰恰是图片，图片每次向左移
 动一帧。假设图片总长度为 70，显示第一帧时，图片的 left 为 0，然后向左移动一帧，left 为-
 10，向左移动两帧，left 为-20……向左移动 6 帧，left 为-60，此时，整个动画播放完毕。如果要
 循环播放，将 left 的值重新置 0 即可
 */

public class ClippingBoom extends View{
    private int i = 0;
    private Bitmap bmpBoom;

    public ClippingBoom(Context context) {
        this(context, null);
    }

    public ClippingBoom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        bmpBoom = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_boon);
    }

    public ClippingBoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开始播放
     */
    public void startPlay(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },200, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = bmpBoom.getWidth();
        int height = bmpBoom.getHeight();

        int boomWidth = width / 7;
        Rect rect = new Rect(0, 0, boomWidth, height);
        canvas.save();
        //创建剪切区
        canvas.translate(100, 100);//平移坐标
        canvas.clipRect(rect);
        //向左移动图片，绘制每帧
        canvas.drawBitmap(bmpBoom, -i * boomWidth, 0, null);
        canvas.restore();
        i++;//i+1以播放下一帧
        if(i==7)i=0;//播放完毕后，将i重置为0重新播放；

    }
}
