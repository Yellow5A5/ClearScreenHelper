package yellow5a5.clearscreenhelper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yellow5A5 on 16/10/21.
 */

public class CleanScreenHelper {

    private Context mContext;
    private ViewGroup mDecorView;

    public CleanScreenHelper(Context context){
        mContext = context;
        mDecorView = (ViewGroup) ((Activity)context).getWindow().getDecorView();
    }


}
