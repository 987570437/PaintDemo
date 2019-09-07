package com.itzb.paintdemo.canvas.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.itzb.paintdemo.R;

public class SplashView extends View {

    private Paint mPaint;//画小圆的画笔
    private Paint mHolePaint;//画水波纹的画笔
    private int[] circleColors = {0xFFFF9600, 0xFF02D1AC, 0xFFFFD200, 0xFF00C6FF, 0xFF00E099, 0xFFFF3892};//6个小圆的颜色的数组
    private float mCenterX;//旋转圆的中心横坐标
    private float mCenterY;//旋转圆的中心纵坐标
    private float mDistance;//表示斜对角线长度的一半,扩散圆最大半径
    private float mCircleRadius = 18; //6个小球的半径
    private float mRotateRadius = 90;//旋转大圆的半径
    private float mCurrentRotateRadius = mRotateRadius;//当前旋转圆的半径
    private float mCurrentRotateAngle = 0F; //当前旋转圆的旋转角度
    private float mCurrentHoleRadius = 0F;//扩散圆的半径
    private int mRotateDuration = 1000; //旋转动画的时长
    private ValueAnimator mValueAnimator;//属性动画
    private SplashState mSplashState;

    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.girl);//先设置一张背景，模拟splash后面的界面

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setStyle(Paint.Style.STROKE);
        mHolePaint.setColor(Color.WHITE);//扩散圆的颜色为白色
        mSplashState = new RotateState();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = MeasureSpec.getSize(widthMeasureSpec) / 2;
        mCenterY = MeasureSpec.getSize(heightMeasureSpec) / 2;
        mDistance = (float) Math.hypot(mCenterX, mCenterY); //hypot函数是取平方根
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSplashState.drawState(canvas);

    }

    /**
     * 抽象类，三种状态继承自该类，实现drawState
     */
    private abstract class SplashState {
        abstract void drawState(Canvas canvas);
    }

    /**
     * 1.旋转动画
     */
    private class RotateState extends SplashState {

        private RotateState() {
            mValueAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setDuration(mRotateDuration);
            mValueAnimator.setRepeatCount(1);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mSplashState = new MerginState();
                }
            });

            mValueAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);//画背景
            drawCircles(canvas);//画小圆
        }
    }


    /**
     * 2.扩散后缩放动画
     */
    private class MerginState extends SplashState {
        private MerginState() {
            mValueAnimator = ValueAnimator.ofFloat(0, mRotateRadius);
            mValueAnimator.setDuration(mRotateDuration);
            mValueAnimator.setRepeatCount(0);
            mValueAnimator.setInterpolator(new OvershootInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSplashState = new ExpandState();
                }
            });
            mValueAnimator.reverse();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);//画背景
            drawCircles(canvas);//画小圆
        }
    }

    /**
     * 3.水波纹动画
     */
    private class ExpandState extends SplashState {
        private ExpandState() {
            mValueAnimator = ValueAnimator.ofFloat(0, mDistance);
            mValueAnimator.setDuration(mRotateDuration);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);//画背景
        }
    }

    /**
     * 画小圆
     */
    private void drawCircles(Canvas canvas) {
        for (int i = 0; i < circleColors.length; i++) {
            float rotate = (float) (i * Math.PI * 2 / circleColors.length) + mCurrentRotateAngle;
            mPaint.setColor(circleColors[i]);
            float cX = (float) (mCenterX + (mCurrentRotateRadius * Math.cos(rotate)));
            float cY = (float) (mCenterY + (mCurrentRotateRadius * Math.sin(rotate)));
            canvas.drawCircle(cX, cY, mCircleRadius, mPaint);
        }
    }

    /**
     * 画背景
     */
    private void drawBackground(Canvas canvas) {
        if (mCurrentHoleRadius > 0) {
            mHolePaint.setStrokeWidth(2 * (mDistance - mCurrentHoleRadius));
            canvas.drawCircle(mCenterX, mCenterY, mDistance, mHolePaint);
        } else {
            canvas.drawColor(Color.WHITE);//绘制白色背景
        }
    }

}
