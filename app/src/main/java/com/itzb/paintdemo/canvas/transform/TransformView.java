package com.itzb.paintdemo.canvas.transform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * 变换操作
 */
public class TransformView extends View {

    private Paint mPaint;

    public TransformView(Context context) {
        this(context, null);
    }

    public TransformView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //1，平移操作
//        canvas.translate(50, 50);//将画布坐标右移50,下移50
//        canvas.drawRect(0,0, 400, 400, mPaint);//画一个红色矩形
//        canvas.translate(50, 50);//将画布坐标再次右移50,下移50
//        mPaint.setColor(Color.GRAY);//画笔颜色改为灰色
//        canvas.drawRect(0,0, 400, 400, mPaint);//画一个相对画布不变的灰色矩形
//        canvas.drawLine(0, 0, 600,600, mPaint);//画一条线，起点左边0,0，终点坐标600,600


//        //2.1缩放操纵1
//        mPaint.setColor(Color.RED);//画笔颜色改为红色
//        canvas.drawRect(200,200, 700,700, mPaint);//画一个红色矩形
//        canvas.scale(0.5f, 0.5f);//画布缩放为原来的0.5倍
//        mPaint.setColor(Color.GRAY);//画笔颜色改为灰色
//        canvas.drawRect(200,200, 700,700, mPaint);//画一个相对画布不变的灰色矩形
//        canvas.drawLine(200,200, 600, 600, mPaint);//画一条线，起点左边200,200，终点坐标600,600

        //2.2缩放操纵2
//        mPaint.setColor(Color.RED);//画笔颜色改为红色
//        canvas.drawRect(200,200, 700,700, mPaint);//画一个红色矩形
//        canvas.scale(0.5f, 0.5f, 700,700);//画布缩放为原来的0.5倍，并且缩放前后坐标重叠点在缩放前的700,700处
//        mPaint.setColor(Color.GRAY);//画笔颜色改为灰色
//        canvas.drawRect(200,200, 700,700, mPaint);//画一个相对画布不变的灰色矩形
//        canvas.drawLine(200,200, 600, 600, mPaint);//画一条线，起点左边200,200，终点坐标600,600




        //3.1旋转操作1
//        canvas.translate(50,50);//先将画布右下平移50px，方便查看效果
//        mPaint.setColor(Color.RED);//画笔颜色改为红色
//        canvas.drawRect(0,0, 700,700, mPaint);//画一个红色矩形
//        canvas.rotate(45);//旋转45°
//        mPaint.setColor(Color.GRAY);//更改画笔为灰色
//        canvas.drawRect(0,0, 700,700, mPaint);//画一个大小相同灰色矩形

        //3.2旋转操作2
//        canvas.translate(50,50);//先将画布右下平移50px，方便查看效果
//        mPaint.setColor(Color.RED);//画笔颜色改为红色
//        canvas.drawRect(0,0, 700,700, mPaint);//画一个红色矩形
//        canvas.rotate(45, 350, 350); //px, py表示旋转中心的坐标
//        mPaint.setColor(Color.GRAY);//更改画笔为灰色
//        canvas.drawRect(0,0, 700,700, mPaint);//画一个大小相同灰色矩形


        //4倾斜操作
//        canvas.drawRect(0,0, 400, 400, mPaint);
//        canvas.skew(1, 0); //在X方向倾斜45度,Y轴逆时针旋转45
////        canvas.skew(0, 1); //在y方向倾斜45度， X轴顺时针旋转45
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0, 0, 400, 400, mPaint);

        //切割
//        canvas.drawRect(200, 200,700, 700, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200, 800,700, 1300, mPaint);
//        canvas.clipRect(200, 200,700, 700); //画布被裁剪
//        canvas.drawCircle(100,100, 100,mPaint); //坐标超出裁剪区域，无法绘制
//        canvas.drawCircle(300, 300, 100, mPaint); //坐标区域在裁剪范围内，绘制成功

//        canvas.drawRect(200, 200,700, 700, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(200, 800,700, 1300, mPaint);
//        canvas.clipOutRect(200,200,700,700); //画布裁剪外的区域
//        canvas.drawCircle(100,100,100,mPaint); //坐标区域在裁剪范围内，绘制成功
//        canvas.drawCircle(300, 300, 100, mPaint);//坐标超出裁剪区域，无法绘制

        //矩阵
        canvas.drawRect(0,0,700,700, mPaint);
        Matrix matrix = new Matrix();
        matrix.setTranslate(50,50);
//        matrix.setRotate(45);
//        matrix.setScale(0.5f, 0.5f);
        canvas.setMatrix(matrix);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0,0,700,700, mPaint);

    }

}
