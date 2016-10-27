package yellow5a5.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import yellow5a5.clearscreenhelper.CleanScreenHelper;
import yellow5a5.clearscreenhelper.IClearEvent;
import yellow5a5.clearscreenhelper.IClearRootView;
import yellow5a5.clearscreenhelper.View.RelativeRootView;

public class SampleFirActivity extends AppCompatActivity {

    private IClearRootView mClearRootLayout;
    private CleanScreenHelper mCleanScreenHelper;
    private Button mLeftBottomBtn;
    private Button mRightBottomBtn;
    private Button mCenterBtn;
    private Button mBindBtn;
    private Button mUnBindBtn;
    private TextView mRightTopTextV;
    private TextView mInfoTextV;
    private TextView mFansTextV;
    private TextView mShowTextV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fir);

        initView();
        initListener();

        /**
         *  CleanScreenHelper API Usage:
         */
        mClearRootLayout = (RelativeRootView) findViewById(R.id.sample_clear_root_layout);
        mCleanScreenHelper = new CleanScreenHelper(this, mClearRootLayout);
        mCleanScreenHelper.bind(mLeftBottomBtn, mRightBottomBtn, mRightTopTextV, mFansTextV, mInfoTextV);
        mCleanScreenHelper.setIClearEvent(new IClearEvent() {
            @Override
            public void onClearEnd() {
                Toast.makeText(SampleFirActivity.this, "Clear End...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRecovery() {
                Toast.makeText(SampleFirActivity.this, "Recovery Now...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        this.mBindBtn = (Button) findViewById(R.id.sample_bind_btn);
        this.mUnBindBtn = (Button) findViewById(R.id.sample_unbind_btn);
        this.mLeftBottomBtn = (Button) findViewById(R.id.sample_left_bottom_btn);
        this.mRightBottomBtn = (Button) findViewById(R.id.sample_right_bottom_btn);
        this.mCenterBtn = (Button) findViewById(R.id.sample_center_btn);
        this.mRightTopTextV = (TextView) findViewById(R.id.sample_right_top_text);
        this.mFansTextV = (TextView) findViewById(R.id.sample_person_fans);
        this.mShowTextV = (TextView) findViewById(R.id.sample_text_show);
        this.mInfoTextV = (TextView) findViewById(R.id.sample_person_info);
    }

    private void initListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == mBindBtn.getId()) {
                    mCleanScreenHelper.bind(mShowTextV);
                    mCleanScreenHelper.bind(mCenterBtn);
                } else if (view.getId() == mUnBindBtn.getId()) {
                    mCleanScreenHelper.unbind(mShowTextV);
                    mCleanScreenHelper.unbind(mCenterBtn);
                } else if (view.getId() == mCenterBtn.getId()) {
                    Toast.makeText(SampleFirActivity.this, "Triggers the click event.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mBindBtn.setOnClickListener(listener);
        mUnBindBtn.setOnClickListener(listener);
        mCenterBtn.setOnClickListener(listener);
    }
}
