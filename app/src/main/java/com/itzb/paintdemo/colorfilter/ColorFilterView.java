package com.itzb.paintdemo.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import com.itzb.paintdemo.R;

public class ColorFilterView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private ColorMatrixColorFilter mColorMatrixColorFilter;


    public ColorFilterView(Context context) {
        super(context);
        mPaint = new Paint();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
    }

    public ColorFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//       1.LightingColorFilter
        /**
         * R' = R * mul.R / 0xff + add.R
         * G' = G * mul.G / 0xff + add.G
         * B' = B * mul.B / 0xff + add.B
         */

        //原始图片效果
//        LightingColorFilter lighting = new LightingColorFilter(0xffffff,0x000000);
//        mPaint.setColorFilter(lighting);
//        canvas.drawBitmap(mBitmap, 0,0, mPaint);

//        红色去除掉
//        LightingColorFilter lighting = new LightingColorFilter(0x00ffff,0x000000);
//        mPaint.setColorFilter(lighting);
//        canvas.drawBitmap(mBitmap, 0,0,mPaint);

        //绿色更亮
//        LightingColorFilter lighting = new LightingColorFilter(0xffffff,0x00f600);
//        mPaint.setColorFilter(lighting);
//        canvas.drawBitmap(mBitmap, 0,0, mPaint);


//        2.PorterDuffColorFilter
//        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
//        mPaint.setColorFilter(porterDuffColorFilter);
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);


//        3.ColorMatrixColorFilter
        /**
         *  float[] colorMatrix = {
         *                 2, 0, 0, 0, 0,   //red
         *                 0, 1, 0, 0, 0,   //green
         *                 0, 0, 1, 0, 0,   //blue
         *                 0, 0, 0, 1, 0    //alpha
         *         };
         */

//         胶片
//        float colormatrix_fanse[] = {
//                -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
//                0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
//                0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
//                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
//        };
//        mColorMatrixColorFilter = new ColorMatrixColorFilter(colormatrix_fanse);
//        mPaint.setColorFilter(mColorMatrixColorFilter);
//        canvas.drawBitmap(mBitmap, 100, 0, mPaint);


//        ColorMatrix cm = new ColorMatrix();
//        cm.setScale(1, 2, 1, 1);//亮度调节
//        cm.setSaturation(2);//饱和度调节0-无色彩， 1- 默认效果， >1饱和度加强
//        cm.setRotate(0, 45);//        色调调节
//        mColorMatrixColorFilter = new ColorMatrixColorFilter(cm);
//        mPaint.setColorFilter(mColorMatrixColorFilter);
//        canvas.drawBitmap(mBitmap, 100, 0, mPaint);

    }

}
