package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lei on 2018/3/5.
 * 默认情况下，画布坐标的原点就是绘图区的左上角，向左为负，向右为正，向上为负，向下
 为正，但是通过 Canvas 提供的方法可以对坐标进行转换。转换的方式主要有 4 种：平移、旋转、
 缩放和拉斜：
 1  public void translate(float dx, float dy)
 坐标平移，在当前原点的基础上水平移动 dx 个距离，垂直移动 dy 个距离，正负符号
 决定方向。坐标原点改变后，所有的坐标都是以新的原点为参照进行定位。
 图示 3-2：坐标平移
   下面两段代码是等效的：
 代码段 1：canvas.drawPoint(10, 10, paint);
 代码段 2：canvas.translate(10, 10); canvas.drawPoint(0, 0, paint);
 2  public void rotate(float degrees)
 将画布的坐标以当前原点为中心旋转指定的角度，如果角度为正，则为顺时针旋转，
 否则为逆时针旋转。
   public final void rotate(float degrees, float px, float py)
 以点(px, py)为中心对画布坐标进行旋转 degrees 度，为正表示顺时针，为负表示逆时
 针。
 3  public void scale(float sx, float sy)
 缩放画布的坐标，sx、sy 分别是 x 方向和 y 方向的缩放比例，小于 1 表示缩小，等于
 1 表示不变，大于 1 表示放大。画布缩放后，绘制在画布上的图形也会等比例缩放。
 缩放的单位是倍数，比如 sx 为 0.5 时，就是在 x 方向缩小 0.5 倍。
   public final void scale(float sx, float sy, float px, float py)
 以（px，py）为中心对画布进行缩放。
 4  public void skew(float sx, float sy)
 将画布分别在 x 方向和 y 方向拉斜一定的角度，sx 为 x 方向倾斜角度的 tan 值，sy 为
 y 方向倾斜角度的 tan 值，比如我们打算在 X 轴方向上倾斜 45 度，则 tan45=1，写成：
 canvas.skew(1, 0)。
 坐标转换后，后面的图形绘制功能将跟随新坐标，转换前已经绘制的图形不会有任何的变
 化。另外，为了能恢复到坐标变化之前的状态，Canvas 定义了两个方法用于保存现场和恢复现
 场：
   public int save()
   保存现场。
  public void restore()
   恢复现场到 save()执行之前的状态。

 下面的案例用于演示坐标平移、旋转、缩放三种变换的使用，通过平移，我们沿 45 度方向
 绘制出一系列的正方形；通过缩放，我们为您绘制一个万花筒；通过旋转，绘制出手表四周的刻
 度。
 */

public class CoordinateView extends View{
    private Paint mPaint;

    public CoordinateView(Context context) {
        super(context);
    }

    public CoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //保存现场
        canvas.save();
        //坐标平移
        for(int i = 0; i < 10; i++){
            canvas.drawRect(20, 20, 120, 120, mPaint);
            //坐标向x轴平移10，y轴平移10
            canvas.translate(20, 20);
        }
        //恢复现场
        canvas.restore();

        //坐标向y轴移动350，x轴移动20
        canvas.translate(20, 350);
        canvas.save();
        //坐标旋转
        mPaint.setColor(Color.GREEN);
        //画圆
        int radius = 150;
        canvas.drawCircle(150, 150, radius, mPaint);
        for(int i = 0; i < 12; i++){
            //画线，为圆的直径的1/10,半径的1/5
            canvas.drawLine(radius + 4 * radius / 5, radius, 2 * radius, radius, mPaint);
            //坐标根据圆心(200，200)顺时针旋转30度
            canvas.rotate(30, 150, 150);
        }

        //恢复现场
        canvas.restore();

        //坐标向y轴移动300，x轴不变
        canvas.translate(0, 350);
        canvas.save();
        //坐标伸缩
        mPaint.setColor(Color.GRAY);
        for(int i = 0; i < 10; i++){
            canvas.drawRect(0, 0, 380, 380, mPaint);
            //坐标根据矩形的中心(210，210)进行缩放0.9f
            canvas.scale(0.9f, 0.9f, 190, 190);
        }
        //恢复现场
        canvas.restore();
        //坐标向y轴移动400，x轴不变
        canvas.translate(0, 400);
        canvas.save();
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < 3; i++){
            canvas.drawRect(0, 0, 380, 380, mPaint);
            //坐标在x轴方向倾斜26.565度 tan26.565 = 0.5;
            canvas.skew(0.5f, 0);
        }
        //恢复现场
        canvas.restore();
    }

    /**
     * 另外，Android 中定义了一个名为 Matrix 的类，该类定义了一个 3 * 3 的矩阵，关于矩阵涉
     及到《高等数学》方面的课程，我们不想过多讲解，只需知道通过 Matrix 同样可以实现坐标的变
     换，相关的方法如下：
     1  移位
     public void setTranslate(float dx, float dy)
     2  旋转
     public void setRotate(float degrees, float px, float py)
     public void setRotate(float degrees)
     3  缩放
     public void setScale(float sx, float sy)
     public void setScale(float sx, float sy, float px, float py)
     4  拉斜
     public void setSkew(float kx, float ky)
     public void setSkew(float kx, float ky, float px, float py)
     Matrix 的应用范围很广，Canvas、Shader 等都支持通过 Matrix 实现移位、旋转、缩放等效
     果。Matrix 的基本使用形如：
     Matrix matrix = new Matrix();
     matrix.setTranslate(10, 10);
     canvas.setMatrix(matrix);
     */

}
