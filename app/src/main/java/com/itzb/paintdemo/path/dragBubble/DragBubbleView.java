package com.itzb.paintdemo.path.dragBubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.itzb.paintdemo.R;

public class DragBubbleView extends View {

    private static final int BUBBLE_STATE_DEFAULT = 0;//气泡默认状态--静止
    private static final int BUBBLE_STATE_CONNECT = 1;//气泡相连
    private static final String TAG = "DragBubbleView";
    private final int BUBBLE_STATE_APART = 2;//气泡分离
    private final int BUBBLE_STATE_DISMISS = 3;//气泡消失
    private float mBubbleRadius = 60;//气泡半径
    private int mBubbleColor = Color.RED;//气泡颜色
    private String mTextStr = "99+";//气泡消息文字
    private int mTextColor = Color.WHITE;//气泡消息文字颜色
    private float mTextSize = 60;//气泡消息文字大小
    private float mBubFixedRadius = mBubbleRadius;//不动气泡的半径
    private float mBubMovableRadius = mBubbleRadius;//可动气泡的半径
    private PointF mBubFixedCenter;//不动气泡的圆心
    private PointF mBubMovableCenter;//可动气泡的圆心
    private Paint mBubblePaint;//气泡的画笔
    private Path mBezierPath;//贝塞尔曲线path
    private Paint mTextPaint;//文本绘制画笔
    private Rect mTextRect;//文本绘制区域
    private Paint mBurstPaint;//气泡爆炸消失画笔
    private Rect mBurstRect;//爆炸绘制区域
    private int mBubbleState = BUBBLE_STATE_DEFAULT;//气泡状态标志
    private float mDist;//两气泡圆心距离
    private float mMaxDist = 4 * mBubbleRadius;//气泡相连状态最大圆心距离
    private float MOVE_OFFSET = 2 * mBubbleRadius;//手指触摸偏移量
    private Bitmap[] mBurstBitmapsArray;//气泡爆炸的bitmap数组
    private boolean mIsBurstAnimStart = false;//是否在执行气泡爆炸动画
    private int mCurDrawableIndex;//当前气泡爆炸图片index

    //气泡爆炸的图片id数组
    private int[] mBurstDrawablesArray = {
            R.drawable.burst_1,
            R.drawable.burst_2,
            R.drawable.burst_3,
            R.drawable.burst_4,
            R.drawable.burst_5
    };

    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化气泡画笔
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);

        //初始化文本绘制画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextRect = new Rect();

        //初始化爆炸画笔
        mBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBurstPaint.setFilterBitmap(true);

        //初始化贝赛尔曲线path
        mBezierPath = new Path();

        mBurstRect = new Rect();

        //将气泡爆炸的drawable转为bitmap
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //不动气泡圆心
        if (mBubFixedCenter == null) {
            mBubFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mBubFixedCenter.set(w / 2, h / 2);
        }

        //可动气泡圆心
        if (mBubMovableCenter == null) {
            mBubMovableCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMovableCenter.set(w / 2, h / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1，静止状态，一个气泡加消息数据
        //2, 连接状态，一个气泡加消息数据，贝塞尔曲线，本身位置上气泡，大小可变化
        //3，分离状态，一个气泡加消息数据
        //4，消失状态，爆炸效果
        Log.d(TAG, "onDraw mBubbleState: " + mBubbleState);
        if (mBubbleState == BUBBLE_STATE_CONNECT) {

            //绘制不动圆
            canvas.drawCircle(mBubFixedCenter.x, mBubFixedCenter.y, mBubFixedRadius, mBubblePaint);

            //绘制贝塞尔曲线

            //控制点坐标
            float ctrlX = (mBubFixedCenter.x + mBubMovableCenter.x) / 2;
            float ctrlY = (mBubFixedCenter.y + mBubMovableCenter.y) / 2;

            float sinTheta = (mBubMovableCenter.y - mBubFixedCenter.y) / mDist;
            float cosTheta = (mBubMovableCenter.x - mBubFixedCenter.x) / mDist;

            //B
            float iBubMovableStartX = mBubMovableCenter.x + sinTheta * mBubMovableRadius;
            float iBubMovableStartY = mBubMovableCenter.y - cosTheta * mBubMovableRadius;

            //A
            float iBubFixedEndX = mBubFixedCenter.x + mBubFixedRadius * sinTheta;
            float iBubFixedEndY = mBubFixedCenter.y - mBubFixedRadius * cosTheta;

            //D
            float iBubFixedStartX = mBubFixedCenter.x - mBubFixedRadius * sinTheta;
            float iBubFixedStartY = mBubFixedCenter.y + mBubFixedRadius * cosTheta;
            //C
            float iBubMovableEndX = mBubMovableCenter.x - mBubMovableRadius * sinTheta;
            float iBubMovableEndY = mBubMovableCenter.y + mBubMovableRadius * cosTheta;

            mBezierPath.reset();
            mBezierPath.moveTo(iBubFixedStartX, iBubFixedStartY);
            mBezierPath.quadTo(ctrlX, ctrlY, iBubMovableEndX, iBubMovableEndY);//二阶贝塞尔曲线
            mBezierPath.lineTo(iBubMovableStartX, iBubMovableStartY);//一阶贝赛尔曲线
            mBezierPath.quadTo(ctrlX, ctrlY, iBubFixedEndX, iBubFixedEndY);//二阶贝塞尔曲线
            mBezierPath.close();//闭和曲线

            canvas.drawPath(mBezierPath, mBubblePaint);//绘制两圆中间的区域

        }

        //一个气泡加消息数据
        if (mBubbleState != BUBBLE_STATE_DISMISS) {
            canvas.drawCircle(mBubMovableCenter.x, mBubMovableCenter.y, mBubMovableRadius, mBubblePaint);
            mTextPaint.getTextBounds(mTextStr, 0, mTextStr.length(), mTextRect);
            canvas.drawText(mTextStr, mBubMovableCenter.x - mTextRect.width() / 2, mBubMovableCenter.y + mTextRect.height() / 2, mTextPaint);
        }

        //爆炸效果
        if (mBubbleState == BUBBLE_STATE_DISMISS && mCurDrawableIndex < mBurstBitmapsArray.length) {
            mBurstRect.set((int) (mBubMovableCenter.x - mBubMovableRadius),
                    (int) (mBubMovableCenter.y - mBubMovableRadius),
                    (int) (mBubMovableCenter.x + mBubMovableRadius),
                    (int) (mBubMovableCenter.y + mBubMovableRadius)
            );
            canvas.drawBitmap(mBurstBitmapsArray[mCurDrawableIndex], null, mBurstRect, mBurstPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBubbleState != BUBBLE_STATE_DISMISS) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);
                    Log.d(TAG, "mDist: " + (mDist < mBubbleRadius + MOVE_OFFSET));
                    if (mDist < mBubbleRadius + MOVE_OFFSET) {
                        mBubbleState = BUBBLE_STATE_CONNECT;
                    } else {
                        mBubbleState = BUBBLE_STATE_DEFAULT;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "mBubbleState: " + mBubbleState);
                if (mBubbleState != BUBBLE_STATE_DISMISS && mBubbleState != BUBBLE_STATE_DEFAULT) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);
                    Log.d(TAG, "mDist: " + mDist);
                    mBubMovableCenter.set(event.getX(), event.getY());//设置移动气泡的坐标
                    mBubFixedRadius = mBubbleRadius - mDist / 8;//缩小固定气泡的半径
                    if (mDist < mMaxDist + MOVE_OFFSET) {
                        mBubbleState = BUBBLE_STATE_CONNECT;
                    } else {
                        mBubbleState = BUBBLE_STATE_APART;//将气泡变为断开状态
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mBubbleState == BUBBLE_STATE_CONNECT) {
                    startBubbleResetAnim();//回弹效果
                } else if (mBubbleState == BUBBLE_STATE_APART) {
                    startBubbleBurstAnim();//爆炸动画
                }
                break;
        }
        return true;
    }

    //回弹动画
    private void startBubbleResetAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new PointFEvaluator(), mBubMovableCenter, mBubFixedCenter);
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBubMovableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBubbleState = BUBBLE_STATE_DEFAULT;
            }
        });
        animator.start();
    }

    //爆炸动画
    private void startBubbleBurstAnim() {
        mBubbleState = BUBBLE_STATE_DISMISS;
        ValueAnimator animator = ValueAnimator.ofInt(0, mBurstBitmapsArray.length * 2);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDrawableIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBubMovableCenter.set(mBubFixedCenter.x, mBubFixedCenter.y);
                mBubbleState = BUBBLE_STATE_DEFAULT;
            }
        });
        animator.start();
    }

}
