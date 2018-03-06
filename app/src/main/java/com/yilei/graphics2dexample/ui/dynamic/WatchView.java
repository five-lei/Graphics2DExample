package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lei on 2018/3/6.
 */

public class WatchView extends View{
    private Paint mPaint;//表盘画笔
    private int radius;//圆盘半径
    private int scaleNum = 60;//表盘刻度总数
    private Calendar calendar;

    public WatchView(Context context) {
        super(context);
    }

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        calendar = Calendar.getInstance();

    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void run(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int len = Math.min(width, height);
        radius = len/2;
        //画表盘
        drawPlate(canvas);
        //画指针
        drawPointer(canvas);
    }

    private void drawPlate(Canvas canvas){
        canvas.save();
        //画圆盘背景
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(radius, radius, radius, mPaint);
        //因为0度是从3点中方向开始，时间是从12点开始，所以逆时针旋转90度
        canvas.rotate(-90, radius, radius);
        //画表盘刻度
        for(int i = 0; i < scaleNum; i++){
            if(i % 5 == 0){
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                mPaint.getTextBounds(text,0,text.length(),textBound);
                mPaint.setTextSize(Sp2Px(getContext(), 14));

                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(4);
                canvas.drawLine(radius + 9 * radius / 10, radius, 2 * radius-radius/50, radius, mPaint);
                canvas.save();

                canvas.drawText(text, radius + (radius-radius/50-radius/10-(textBound.width()/2)-20), radius + (textBound.height()/2), mPaint);
                canvas.restore();
            }else{
                //画短刻度 长度为半径的1/15
                mPaint.setColor(Color.GRAY);
                canvas.drawLine(radius + 14 * radius / 15, radius, radius * 2-radius/50, radius, mPaint);
            }
            canvas.rotate(6, radius, radius);
        }
        canvas.restore();

    }

    private void drawPointer(Canvas canvas){
        //现获取系统时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hours = calendar.get(Calendar.HOUR_OF_DAY)%12;//转换成12小时制
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);


        Log.i("team", "hours: "+ calendar.get(Calendar.HOUR)+""+"minute:  "+minutes);
        //画时针
        //计算角度  顺时针
        int degrees = 360 / 60 * minutes /12;
        int degree = 360 / 12 * hours;
        //转换成弧度，为了计算终点的xy坐标
        double radians = Math.toRadians(degrees+degree);

        //画线
        int startX = radius;
        int startY = radius;
        int endX = (int) (startX + radius * 0.5 * Math.cos(radians));
        int endY = (int) (startY + radius * 0.5 * Math.sin(radians));
        canvas.save();

        mPaint.setStrokeWidth(8);
        //因为0度是从3点中方向开始，时间是从12点开始，所以逆时针旋转90度
        canvas.rotate(-90, radius, radius);
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        //再给时针画个“尾巴”
        radians = Math.toRadians(degrees+degree - 180);
        endX = (int) (startX + radius * 0.10 * Math.cos(radians));
        endY = (int) (startY + radius * 0.10 * Math.sin(radians));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        canvas.restore();

        //画分针
        degree = 360 / 60 * minutes;
        //转换成弧度，为了计算终点的xy坐标
        radians = Math.toRadians(degree);

        //画线
        startX = radius;
        startY = radius;
        endX = (int) (startX + radius * 0.6 * Math.cos(radians));
        endY = (int) (startY + radius * 0.6 * Math.sin(radians));
        canvas.save();

        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        //因为0度是从3点中方向开始，时间是从12点开始，所以逆时针旋转90度
        canvas.rotate(-90, radius, radius);
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        //再给分针画个“尾巴”
        radians = Math.toRadians(degree - 180);
        endX = (int) (startX + radius * 0.12 * Math.cos(radians));
        endY = (int) (startY + radius * 0.12 * Math.sin(radians));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        canvas.restore();

        //画秒针
        degree = 360 / 60 * seconds;
        //转换成弧度，为了计算终点的xy坐标
        radians = Math.toRadians(degree);

        //画线
        startX = radius;
        startY = radius;
        endX = (int) (startX + radius * 0.8 * Math.cos(radians));
        endY = (int) (startY + radius * 0.8 * Math.sin(radians));
        canvas.save();

        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.YELLOW);
        //因为0度是从3点中方向开始，时间是从12点开始，所以逆时针旋转90度
        canvas.rotate(-90, radius, radius);
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        //再给分针画个“尾巴”
        radians = Math.toRadians(degree - 180);
        endX = (int) (startX + radius * 0.15 * Math.cos(radians));
        endY = (int) (startY + radius * 0.15 * Math.sin(radians));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        canvas.restore();


        //绘制中心小圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(radius, radius, 20, mPaint);
    }

    //sp转px
    public static float Sp2Px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
