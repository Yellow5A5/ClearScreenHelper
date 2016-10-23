package yellow5a5.clearscreenhelper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * Created by Yellow5A5 on 16/10/21.
 */

public class CleanScreenHelper {

    private Context mContext;
    private ViewGroup mDecorView;

    private ScreenSideView mScreenSideView;
    private LinkedList<View> mClearList;

    public CleanScreenHelper(Context context) {
        initView(context);
        initPara();
        initCallback();
    }

    private void initView(Context context) {
        mDecorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mScreenSideView = new ScreenSideView(context);
        mDecorView.addView(mScreenSideView, params);
    }

    private void initPara() {
        mClearList = new LinkedList<>();
        setOrentation(Constants.Orientation.LEFT);
    }

    private void initCallback() {
        mScreenSideView.setIPositionCallBack(new IPositionCallBack() {
            @Override
            public void onPositionChange(int offsetX, int offsetY) {
                for (int i = 0; i < mClearList.size(); i++) {
                    mClearList.get(i).setTranslationX(offsetX);
                    mClearList.get(i).setTranslationY(offsetY);
                }
            }

            @Override
            public void onClearEnd() {

            }

            @Override
            public void onRecovery() {

            }
        });
    }

    public void setOrentation(Constants.Orientation orientation) {
        mScreenSideView.setClearSide(orientation);
    }

    public void bind(@NonNull View... cellList) {
        for (View cell : cellList) {
            if (!mClearList.contains(cell)) {
                mClearList.add(cell);
            }
        }
    }

    public void unbind(@NonNull View... cellList) {
        for (View cell : cellList) {
            if (mClearList.contains(cell)) {
                mClearList.remove(cell);
            }
        }
    }

    public void unbindAllCell(){
        mClearList.clear();
    }
}
