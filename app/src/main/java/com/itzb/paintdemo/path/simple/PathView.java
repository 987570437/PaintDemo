package com.itzb.paintdemo.path.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PathView extends View {

    private Paint mPaint;
    private Path mPath;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.一阶贝塞尔曲线，表示的是一条直线
//        mPath.moveTo(500, 500);//移动
//        mPath.lineTo(600, 600);//连线
////        mPath.rLineTo(100, 100);//等同于上一行代码效果
//        mPath.lineTo(500, 600);
//        mPath.close();//设置曲线是否闭合


        //2.添加子图形addXXX
//        //添加弧形 addArc(float left, float top, float right, float bottom, float startAngle,float sweepAngle)
//        mPath.addArc(0, 0, 200, 200, 45, 270);
//
//        //添加矩形 addRect(float left, float top, float right, float bottom, Direction dir)
//        mPath.addRect(300, 5, 900, 200, Path.Direction.CW);//Path.Direction.CW表示顺时针方向绘制，CCW表示逆时针方向
//
//        //添加一个圆 addCircle(float x, float y, float radius, Direction dir)
//        mPath.addCircle(200,400, 100, Path.Direction.CW);
//
//        //添加一个椭圆 addOval(float left, float top, float right, float bottom, Direction dir)
//        mPath.addOval(400,300,900,500, Path.Direction.CCW);


        //3.追加图形
        //xxxTo画线
//        mPath.moveTo(0, 0);
//        mPath.lineTo(100, 100);
//        //arcTo(float left, float top, float right, float bottom, float startAngle,float sweepAngle, boolean forceMoveTo)
//        //forceMoveTo，true，绘制时移动起点，false，绘制时连接最后一个点与圆弧起点
//        mPath.arcTo(400, 0, 600, 200, -180, 225, false);


        //4.添加一个路径
        //添加一个路径
//        mPath.moveTo(100, 70);
//        mPath.lineTo(140, 180);
//        mPath.lineTo(250, 330);
//        mPath.lineTo(400, 630);
//        mPath.lineTo(100, 830);
//
//        Path newPath = new Path();
//        newPath.moveTo(100, 1000);
//        newPath.lineTo(600, 1300);
//        newPath.lineTo(400, 1700);
//        mPath.addPath(newPath);


        //5.添加圆角矩形， CW顺时针，CCW逆时针
//        RectF rectF5 = new RectF(200, 800, 700, 1200);
//        mPath.addRoundRect(rectF5, 100, 100, Path.Direction.CCW);


        //6.画二阶贝塞尔曲线
//        mPath.moveTo(300, 500);
//        mPath.quadTo(500, 100, 800, 500);//前两个是控制点，后两个是终点坐标
//        //参数表示相对位置，等同于上面一行代码
////        mPath.rQuadTo(200, -400, 500, 0);


        //7.画三阶贝塞尔曲线
        mPath.moveTo(300, 500);
        mPath.cubicTo(500, 100, 600, 1200, 800, 500);//前4个代表两个控制点，后2个参数代表终点坐标
        //参数表示相对位置，等同于上面一行代码
//        mPath.rCubicTo(200, -400, 300, 700, 500, 0);


        canvas.drawPath(mPath, mPaint);
    }
}
