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
 * Created by Yellow5A5 on 16/10/21.
 */

public class ScreenSideView extends LinearLayout implements IClearRootView {

    private final int MIN_SCROLL_SIZE = 30;
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

    public ScreenSideView(Context context) {
        this(context, null);
    }

    public ScreenSideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenSideView(Context context, AttributeSet attrs, int defStyle) {
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
                if (mOrientation.equals(Constants.Orientation.RIGHT) && mEndX == RIGHT_SIDE_X) {
                    mIPositionCallBack.onClearEnd();
                    mOrientation = Constants.Orientation.LEFT;
                } else if (mOrientation.equals(Constants.Orientation.LEFT) && mEndX == LEFT_SIDE_X) {
                    mIPositionCallBack.onRecovery();
                    mOrientation = Constants.Orientation.RIGHT;
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
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isScrollFromSide(x)) {
                    isCanSrcoll = true;
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                Log.e(ScreenSideView.class.getName(), "onTouchEvent: " + (x));
                if (isGreaterThanMinSize(x) && isCanSrcoll) {
                    mIPositionCallBack.onPositionChange(getRealTimeX(x), 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isGreaterThanMinSize(x) && isCanSrcoll) {
                    mDownX = getRealTimeX(x);
                    fixPostion();
                    mEndAnimator.start();
                }
        }
        return super.onTouchEvent(event);
    }

    private int getRealTimeX(int x) {
        if (mOrientation.equals(Constants.Orientation.RIGHT) && mDownX > RIGHT_SIDE_X / 3
                || mOrientation.equals(Constants.Orientation.LEFT) && (mDownX > RIGHT_SIDE_X * 2 / 3)) {
            return x + MIN_SCROLL_SIZE;
        } else {
            return x - MIN_SCROLL_SIZE;
        }
    }

    private void fixPostion() {
        if (mOrientation.equals(Constants.Orientation.RIGHT) && mDownX > RIGHT_SIDE_X / 3) {
            mEndX = RIGHT_SIDE_X;
        } else if (mOrientation.equals(Constants.Orientation.LEFT) && (mDownX < RIGHT_SIDE_X * 2 / 3)) {
            mEndX = LEFT_SIDE_X;
        }
    }

    private boolean isGreaterThanMinSize(int x) {
        int absX = Math.abs(mDownX - x);
        return absX > MIN_SCROLL_SIZE;
    }

    private boolean isScrollFromSide(int x) {
        if (x <= LEFT_SIDE_X + MIN_SCROLL_SIZE && mOrientation.equals(Constants.Orientation.RIGHT)
                || (x > RIGHT_SIDE_X - MIN_SCROLL_SIZE && mOrientation.equals(Constants.Orientation.LEFT))) {
            return true;
        } else {
            return false;
        }
    }
}
