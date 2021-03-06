package com.afei.atpif;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afei.atpif.CustomListView.ATPIFListModel;
import com.afei.atpif.Model.TrainListData;
import com.afei.atpif.SocketClient.NettyService;
import com.afei.atpif.Util.DatabaseHelper;
import com.afei.atpif.Util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar mToolBar;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private ViewPager mdrawerViewPager;
    private FragmentPagerAdapter mdrawerAdapter;
    private List<Fragment> mdrawerFragments = new ArrayList<Fragment>();

    /**
     * 底部四个按钮
     */
    private LinearLayout mTabBtnWeixin;
    private LinearLayout mTabBtnFrd;
    private LinearLayout mTabBtnAddress;
    private LinearLayout mTabBtnSettings;


    private TrainListFragment TrainList;
    private BaliseListFragment BaliseList;
    private LineFragment Line;
    private SettingFragment Setting;

    private FrameDrawerFragment FrameDrawer;
    private ControlFragment Control;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.header_color);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mdrawerViewPager = (ViewPager) findViewById(R.id.id_drawerviewpager);

        initView();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };

        mdrawerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public int getCount()
            {
                return mdrawerFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mdrawerFragments.get(arg0);
            }
        };

        try {
            mViewPager.setAdapter(mAdapter);
            mdrawerViewPager.setAdapter(mdrawerAdapter);
        }
        catch (Exception ex)
        {
            Log.d("zgx","mUserId====="+ex.toString());
        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public int getCurrentIndex() {
                return currentIndex;
            }

            private int currentIndex;

            @Override
            public void onPageSelected(int position)
            {
                resetTabBtn();
                switch (position)
                {
                    case 0:
                        ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
                                .setImageResource(R.drawable.tab_weixin_pressed);
                        mToolBar.setVisibility(View.VISIBLE);

                        mdrawerViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
                                .setImageResource(R.drawable.tab_find_frd_pressed);
                        mToolBar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
                                .setImageResource(R.drawable.tab_address_pressed);
                        mToolBar.setVisibility(View.GONE);
                        break;
                    case 3:
                        ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
                                .setImageResource(R.drawable.tab_settings_pressed);
                        mToolBar.setVisibility(View.VISIBLE);
                        break;
                }

                currentIndex = position;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });



        Timber.plant(new Timber.DebugTree());
        startService(new Intent(this, NettyService.class));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Toast.makeText(MainActivity.this, "添加", Toast.LENGTH_SHORT).show();
                startAddDev();
                break;
            case R.id.action_setting:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void resetTabBtn()
    {
        ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
                .setImageResource(R.drawable.tab_weixin_normal);
        ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
                .setImageResource(R.drawable.tab_find_frd_normal);
        ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
                .setImageResource(R.drawable.tab_address_normal);
        ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
                .setImageResource(R.drawable.tab_settings_normal);
    }


    private void initView()
    {
        TrainList = new TrainListFragment();
        BaliseList = new BaliseListFragment();
        Line = new LineFragment();
        Setting = new SettingFragment();

        mFragments.add(TrainList);
        mFragments.add(BaliseList);
        mFragments.add(Line);
        mFragments.add(Setting);

        FrameDrawer = new FrameDrawerFragment();
        Control = new ControlFragment();
        mdrawerFragments.add(FrameDrawer);
        mdrawerFragments.add(Control);

        mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
        //1.匿名内部类
        mTabBtnWeixin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("匿名内部类", "点击事件");

                resetTabBtn();

                ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
                        .setImageResource(R.drawable.tab_weixin_pressed);

                mdrawerViewPager.setCurrentItem(0);
                mViewPager.setCurrentItem(0);
            }
        });

        mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);

        //1.匿名内部类
        mTabBtnFrd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("匿名内部类", "点击事件");

                resetTabBtn();

                ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
                        .setImageResource(R.drawable.tab_find_frd_pressed);

                mdrawerViewPager.setCurrentItem(1);
                mViewPager.setCurrentItem(1);
            }
        });

        mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);

        //1.匿名内部类
        mTabBtnAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("匿名内部类", "点击事件");

                resetTabBtn();

                ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
                        .setImageResource(R.drawable.tab_address_pressed);
                mToolBar.setVisibility(View.GONE);

                mdrawerViewPager.setCurrentItem(2);
                mViewPager.setCurrentItem(2);
            }
        });

        mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);

        //1.匿名内部类
        mTabBtnSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("匿名内部类", "点击事件");

                resetTabBtn();

                ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
                        .setImageResource(R.drawable.tab_settings_pressed);

                mdrawerViewPager.setCurrentItem(3);
                mViewPager.setCurrentItem(3);
            }
        });

    }


    public void startAddDev()
    {
        Intent i = new Intent(this, AddDevActivity.class);
        startActivityForResult(i,0);
    }
    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){

            ATPIFListModel product = (ATPIFListModel) data.getSerializableExtra("product");
            Toast.makeText(MainActivity.this, "添加界面返回  "+product.atpifName, Toast.LENGTH_SHORT).show();

            TrainListData.getInstance().productList.add(product);
            TrainList.UpdateListView();
        }
    }

}
