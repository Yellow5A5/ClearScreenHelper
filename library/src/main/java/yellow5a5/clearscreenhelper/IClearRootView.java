package yellow5a5.clearscreenhelper;

import android.view.View;

/**
 * Created by Yellow5A5 on 16/10/24.
 */

public interface IClearRootView {

    void setClearSide(Constants.Orientation orientation);

    void setIPositionCallBack(IPositionCallBack l);

    void addView(View child, int index);
}
