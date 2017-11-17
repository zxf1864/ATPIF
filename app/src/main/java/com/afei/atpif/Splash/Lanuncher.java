package com.afei.atpif.Splash;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afei.atpif.MainActivity;
import com.afei.atpif.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Function:
 * Created by dubin on 2016/12/26.
 */


/*****
public class Lanuncher extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ExtendedViewPager mVpVideo;
    private TextView mTvEnter;
    private CirclePageIndicator mViewPagerIndicator;
    private boolean mVisible;
    private ViewPagerAdapter mVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lanuncher);

        mVisible = true;

        mVpVideo = (ExtendedViewPager) findViewById(R.id.vp_video);
        mVpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVpVideo.setAdapter(mVpAdapter);
        mVpVideo.setOffscreenPageLimit(4);

        mTvEnter = (TextView) findViewById(R.id.tv_enter);
        mTvEnter.setOnClickListener(this);

        mViewPagerIndicator = (CirclePageIndicator) findViewById(R.id.view_pager_indicator);
        mViewPagerIndicator.setViewPager(mVpVideo);

    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final int[] videoRes;
        private final int[] slogonImgRes;

        public ViewPagerAdapter(FragmentManager paramFragmentManager) {
            super(paramFragmentManager);
            this.videoRes = new int[]{R.raw.splash_1, R.raw.splash_2, R.raw.splash_3, R.raw.splash_4};
            this.slogonImgRes = new int[]{R.drawable.slogan_1, R.drawable.slogan_2, R.drawable.slogan_3, R.drawable.slogan_4};
        }

        public int getCount() {
            return this.videoRes.length;
        }

        public Fragment getItem(int paramInt) {
            VideoItemFragment videoItemFragment = new VideoItemFragment();
            Bundle localBundle = new Bundle();
            localBundle.putInt("position", paramInt);
            localBundle.putInt("videoRes", this.videoRes[paramInt]);
            localBundle.putInt("imgRes", this.slogonImgRes[paramInt]);
            videoItemFragment.setArguments(localBundle);
            if (paramInt < getCount()) {
                return videoItemFragment;
            }
            throw new RuntimeException("Position out of range. Adapter has " + getCount() + " items");
        }
    }


    public void next(int positon) {
        int i = this.mVpVideo.getCurrentItem();
        if (positon == i) {
            positon += 1;

            this.mVpVideo.setCurrentItem(positon, true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((VideoItemFragment) (mVpAdapter.getItem(position))).play();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enter:
                Toast.makeText(this, "欢迎使用！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Lanuncher.this, MainActivity.class));
        }
    }
}

****/




public class Lanuncher extends AppCompatActivity {


    @BindView(R.id.launcher_logo)
    ImageView mLogo;
    @BindView(R.id.launcher_icon)
    ImageView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuncher);
        ButterKnife.bind(this);
        initWindow();

        startAnimation();
    }

    private void initWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.launcher_logo);
        animation.setDuration(1500);
        animation.setFillAfter(true);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Lanuncher.this, MainActivity.class));
                Lanuncher.this.finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLogo.startAnimation(animation);
        mDesc.startAnimation(animation);
    }


}