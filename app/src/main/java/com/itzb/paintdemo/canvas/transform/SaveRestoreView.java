package com.itzb.paintdemo.canvas.transform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 状态保存和恢复
 */
public class SaveRestoreView extends View {

    private Paint mPaint;
    private static final String TAG = "SaveRestoreView";

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 1.canvas内部对于状态的保存存放在栈中
         * 2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数
         * 3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
         * 4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
         * 注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息
         */

//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印1
//        canvas.drawRect(200, 200, 700, 700, mPaint);
//        int state = canvas.save();//save一次，state=1，getSaveCount+1 = 2
//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印2
//        canvas.translate(50, 50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0, 0, 500, 500, mPaint);
//        canvas.save();//save一次，getSaveCount+1 = 3
//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印3
//        canvas.translate(50, 50);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(0, 0, 500, 500, mPaint);
//        canvas.restore();//restore一次，getSaveCount -1 =2
//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印2
//        canvas.restore();//restore一次，getSaveCount -1 = 1
//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印1
//        canvas.restoreToCount(state);//恢复到statue = 1之前，即getSaveCount = 1
//        Log.e(TAG, "onDraw: " + canvas.getSaveCount());//打印1
//        /*canvas.restore(); //此时已经没有save状态了，restore会直接报错，程序crash*/


        canvas.drawRect(200,200, 700,700, mPaint);
        int layerId = canvas.saveLayer(0,0, 700, 700, mPaint);
        mPaint.setColor(Color.GRAY);
        Matrix matrix = new Matrix();
        matrix.setTranslate(100,100);
        canvas.setMatrix(matrix);
        canvas.drawRect(0,0,700,700, mPaint); //由于平移操作，导致绘制的矩形超出了图层的大小，所以绘制不完全
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100, mPaint);

    }
}
