package yellow5a5.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import yellow5a5.clearscreenhelper.CleanScreenHelper;
import yellow5a5.clearscreenhelper.IClearRootView;
import yellow5a5.clearscreenhelper.View.LinearRootView;
import yellow5a5.clearscreenhelper.View.RelativeRootView;

public class SampleFirActivity extends AppCompatActivity {


    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private IClearRootView activitysamplefir;

    private Scroller mScroller;
    private CleanScreenHelper mCleanScreenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fir);
        activitysamplefir = (LinearRootView) findViewById(R.id.activity_sample_fir);
        mCleanScreenHelper = new CleanScreenHelper(this, activitysamplefir);
        initView();
        initPara();
    }

    private void initView() {
        tv7 = (TextView) findViewById(R.id.tv7);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv1 = (TextView) findViewById(R.id.tv1);
        mCleanScreenHelper.bind(tv1);
        mCleanScreenHelper.bind(tv2);
        mCleanScreenHelper.bind(tv3, tv4, tv5);
    }

    private void initPara() {
        mScroller = new Scroller(this);
    }

}
