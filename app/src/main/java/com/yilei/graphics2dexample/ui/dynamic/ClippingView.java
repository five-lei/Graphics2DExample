package com.yilei.graphics2dexample.ui.dynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.yilei.graphics2dexample.R;

/**
 * Created by lei on 2018/3/5.
 * Android提供了剪切区的功能 剪切区可以是一个Rect或者是一个Path，
 * 两个剪切区还能进行图形运算，得到更加复杂的剪切区。我们来看看相关的方法
 * 1.public boolean clipRect(Rect rect)
 * 2.public boolean clipRect(RectF rect)
 * 3.public boolean clipRect(float left, float top, float right, float bottom)
 * 4.public boolean clipRect(int left, int top, int right, int bottom)
 * 以上 4 个方法定义一个矩形的剪切区
 * public boolean clipRect(Path path)
 * 以上方法定义一个Path剪切区用于定义更加复杂的区域
 *
 * 剪切区的图形运算
 * 1.public boolean clipRect(RectF rect, Op op)
 * 2.public boolean clipRect(Rect rect, Op op)
 * 3.public boolean clipRect(float left, float top, float right, float bottom, Op op)
 * 4.public boolean clipPath(Path path, Op op)
 * Op 运算也没什么太大的不同，一共有 6 种
 * Op.DIFFERENCE：计算 A 和 B 的差集范围，即 A-B，只有在此范围内的绘制内容才会被显示
 * Op.REVERSE_DIFFERENCE：计算 B 和 A 的差集范围，即 B-A，只有在此范围内的绘制内容才会被显示
 * Op.INTERSECT：即 A 和 B 的交集范围，只有在此范围内的绘制内容才会被显示。
 * Op.REPLACE：不论 A 和 B 的集合状况，B 的范围将全部进行显示，如果和 A 有交集，则将覆盖 A 的交集范围。
 * Op.UNION：A 和 B 的并集范围，两者所包括的范围的绘制内容都会被显示。
 * Op.XOR：A 和 B 的补集范围，也就是先获取 A 和 B 的并集再减去 A 和 B 的交集，只有在此范围内的绘制内容才会被显示。
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
        //根据Resource创建bitmap位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo);
        //绘制完整的照片
        canvas.drawBitmap(bitmap, 10, 0, null);

        canvas.translate(0, 600);
        //定义剪切区
        //在 Rect(100, 100, 500, 500)区域定义了一个剪切区，接下来绘制的图片只有该剪切区才会显示了。
        canvas.clipRect(new Rect(100, 100, 500, 500));
        canvas.drawBitmap(bitmap, 10, 0, null);

        //剪切区图形运算
        Path path = new Path();
        path.addCircle(500, 350, 200, Path.Direction.CCW);
        //进行图形运算
        canvas.clipPath(path, Region.Op.UNION);
        canvas.drawBitmap(bitmap, 0, 0, null);

    }
}
