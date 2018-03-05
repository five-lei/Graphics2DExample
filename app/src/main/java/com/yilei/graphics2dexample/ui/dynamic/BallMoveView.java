package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lei on 2018/3/5.
 * View 类定义了一组 invalidate()方法，该方法有好几个版本：
 1  public void invalidate()
 2  public void invalidate(int l, int t, int r, int b)
 3  public void invalidate(Rect dirty)
 invalidate()用于重绘组件，不带参数表示重绘整个视图区域，带参数表示重绘指定的区域。
 如果要去追溯该方法的源码，大概就是将重绘请求一级级往上提交到 ViewRoot，调用 ViewRoot
 的 scheduleTraversals()方法重新发起重绘请求，scheduleTraversals()方法会发送一个异步消息，调
 用 performTraversals()方法执行重绘，而 performTraversals()方法最终调用 onDraw()方法,
 所以，简单来说，调用 View 的 invalidate()方法就相当于调用了 onDraw()方法，
 而 onDraw()方法中就是我们编写的绘图代码。
 如果要刷新组件或者让画面动起来，我们只需调用 invalidate()方法即可。通过改变数据来影
 响绘制结果，这是实现组件刷新或实现动画的基本思路。
 invalidate()方法只能在 UI 线程中调用，如果是在子线程中刷新组件，View 类还定义了另一
 组名为 postInvalidate 的方法：
 1  public void postInvalidate()
 2  public void postInvalidate(int left, int top, int right, int bottom)
 现在我们编写一个案例，让小球在 View 的 Canvas 中水平往返移动。当小球触碰到左边边界
 时往右移动，小球触碰到右边边界时往左移动，循环往复。
 */

public class BallMoveView extends View{
    private int x;//小球的水平位置
    private static final int y = 200;//小球的垂直位置。固定为200
    private static final int radius = 50;//小球的半径；
    private static final int bollColor = Color.BLUE;//小球的颜色
    private Paint mPaint;
    private boolean direction;//移动的方向
    public BallMoveView(Context context) {
        this(context, null);
    }

    public BallMoveView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        //初始化属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(bollColor);
        mPaint.setStyle(Paint.Style.FILL);
        x = radius;
    }

    public BallMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startMove(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 200, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据x，y，radius和mPaint画一个圆形的小球
        canvas.drawCircle(x, y, radius, mPaint);

        //改变x的值，调用invalidate()方法后
        //小球因x改变而产生移动的效果

        //获取组件的宽度
        int width = this.getMeasuredWidth();
        if(x <= radius){
            direction = true;
        }

        if(x >= width - radius){
            direction = false;
        }

        x = direction ? x+5 : x-5;

    }
}
