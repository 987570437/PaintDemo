package com.itzb.paintdemo.canvas.split;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.itzb.paintdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplitView extends View {

    private static final String TAG = "SplitView";
    private Paint mPaint;
    private float r = 15f;//爆炸后粒子的半径
    private boolean isSplit = false;//是否开始爆炸
    List<Ball> balls;
    private Bitmap mBitmap;

    public SplitView(Context context) {
        this(context, null);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        balls = new ArrayList<>();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        Random random = new Random();

        for (int i = 0; i < width; i += 2 * r) {
            for (int j = 0; j < height; j += 2 * r) {
                Ball ball = new Ball();
                ball.color = mBitmap.getPixel(i, j);
                ball.x = i;
                ball.y = j;
                ball.r = r;
                ball.vX = random.nextInt(39) + random.nextFloat() - 20.0f;//(-20 , 20)
                ball.vY = random.nextInt(39) + random.nextFloat() - 20.0f;//(-20 , 20)
                ball.aX = 0;
                ball.aY = 6f;
                balls.add(ball);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isSplit) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        } else {
            for (Ball ball : balls) {
                mPaint.setColor(ball.color);
                canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
            }
        }
    }

    private void updateBall() {
        isSplit = true;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(30);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (Ball ball : balls) {
                    ball.x += ball.vX;
                    ball.y += ball.vY;
                    ball.vX += ball.aX;
                    ball.vY += ball.aY;
                    Log.d(TAG, "onAnimationUpdate: ");
                }
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            updateBall();
        }
        return super.onTouchEvent(event);
    }

}
