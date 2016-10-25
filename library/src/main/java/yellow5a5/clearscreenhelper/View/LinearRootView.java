package yellow5a5.clearscreenhelper.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import yellow5a5.clearscreenhelper.Constants;
import yellow5a5.clearscreenhelper.IClearRootView;
import yellow5a5.clearscreenhelper.IPositionCallBack;

/**
 * Created by Yellow5A5 on 16/10/24.
 */

public class LinearRootView extends LinearLayout implements IClearRootView {


    private final int MIN_SCROLL_SIZE = 130;
    private final int LEFT_SIDE_X = 0;
    private final int RIGHT_SIDE_X = getResources().getDisplayMetrics().widthPixels;

    private int mDownX;
    private int mEndX;
    private ValueAnimator mEndAnimator;

    private boolean isCanSrcoll;

    private Constants.Orientation mOrientation;

    private IPositionCallBack mIPositionCallBack;

    @Override
    public void setIPositionCallBack(IPositionCallBack l) {
        mIPositionCallBack = l;
    }

    public LinearRootView(Context context) {
        this(context, null);
    }

    public LinearRootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearRootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mEndAnimator = ValueAnimator.ofFloat(0, 1.0f).setDuration(200);
        mEndAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float factor = (float) valueAnimator.getAnimatedValue();
                int diffX = mEndX - mDownX;
                Log.e(ScreenSideView.class.getName(), "onAnimationUpdate: " + (mDownX + diffX * factor));
                mIPositionCallBack.onPositionChange((int) (mDownX + diffX * factor), 0);
            }
        });
        mEndAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOrientation.equals(Constants.Orientation.LEFT) && mEndX == RIGHT_SIDE_X) {
                    mIPositionCallBack.onClearEnd();
                    mOrientation = Constants.Orientation.RIGHT;
                } else if (mOrientation.equals(Constants.Orientation.RIGHT) && mEndX == LEFT_SIDE_X) {
                    mIPositionCallBack.onRecovery();
                    mOrientation = Constants.Orientation.LEFT;
                }
                mDownX = mEndX;
                isCanSrcoll = false;
            }
        });
    }

    @Override
    public void setClearSide(Constants.Orientation orientation) {
        mOrientation = orientation;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(LinearRootView.class.getName(), "dispatchTouchEvent: " + ev.getX());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        int offsetX = x - mDownX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isGreaterThanMinSize(mDownX, x) && isCanSrcoll) {
                    mIPositionCallBack.onPositionChange(getPositionChangeX(offsetX), 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isGreaterThanMinSize(mDownX, x) && isCanSrcoll) {
                    mDownX = getPositionChangeX(offsetX);
                    fixPostion(offsetX);
                    mEndAnimator.start();
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(LinearRootView.class.getName(), "onInterceptTouchEvent: ");
                mDownX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isGreaterThanMinSize(mDownX, x)) {
                    isCanSrcoll = true;
                    return true;
                }
        }
        return super.onInterceptTouchEvent(event);
    }

    private int getPositionChangeX(int offsetX) {
        int absOffsetX = Math.abs(offsetX);
        if (mOrientation.equals(Constants.Orientation.LEFT)) {
            Log.e(LinearRootView.class.getName(), "getPositionChangeX: 1");
            return absOffsetX - MIN_SCROLL_SIZE;
        } else {
            Log.e(LinearRootView.class.getName(), "getPositionChangeX: 2");
            return RIGHT_SIDE_X - (absOffsetX - MIN_SCROLL_SIZE);
        }
    }

    private void fixPostion(int offsetX) {
        int absOffsetX = Math.abs(offsetX);
        if (mOrientation.equals(Constants.Orientation.LEFT) && absOffsetX > RIGHT_SIDE_X / 3) {
            mEndX = RIGHT_SIDE_X;
        } else if (mOrientation.equals(Constants.Orientation.RIGHT) && (absOffsetX > RIGHT_SIDE_X / 3)) {
            mEndX = LEFT_SIDE_X;
        }
    }

    public boolean isGreaterThanMinSize(int x1, int x2) {
        int absX = Math.abs(x2 - x1);
        return absX > MIN_SCROLL_SIZE;
    }

}
