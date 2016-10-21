package yellow5a5.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class SampleFirActivity extends AppCompatActivity {


    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private LinearLayout activitysamplefir;

    private Scroller mScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fir);
        initView();
        initPara();
    }

    private void initView() {

        this.tv7 = (TextView) findViewById(R.id.tv7);
        this.tv6 = (TextView) findViewById(R.id.tv6);
        this.tv5 = (TextView) findViewById(R.id.tv5);
        this.tv4 = (TextView) findViewById(R.id.tv4);
        this.tv3 = (TextView) findViewById(R.id.tv3);
        this.tv2 = (TextView) findViewById(R.id.tv2);
        this.tv1 = (TextView) findViewById(R.id.tv1);
    }

    private void initPara() {
        mScroller = new Scroller(this);
    }

}
