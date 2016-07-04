package com.woofer.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;


import com.woofer.activity.userhomepage.FansActivity;
import com.woofer.activity.userhomepage.FollowersActivity;
import com.woofer.activity.userhomepage.ParhsActivity;
import com.woofer.adapter.ViewPagerAdapter;
import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.StatusFalseResponse;
import com.woofer.net.UserInfoResponse;
import com.woofer.titlebar.TitleBar;
import com.woofer.userInfo;

import java.util.ArrayList;
import java.util.List;

import woofer.com.test.R;

public class OtherUserHomePage extends Activity {
    private TextView fans,followers ,parhs;
    private TextView tv1 ,tv2 ,tv3;
    private ViewPager vp;
    private TitleBar titleBar;

    private String LoginToken;
    private int UserId;

    private TextView UserName;
    private ImageView imgsex;
    private TextView sign;

    private LocalActivityManager manager;
    private ViewPagerAdapter viewPageAdapter;
    private OnClickListener clickListener;
    private OnPageChangeListener pageChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_home_page);
        Intent intent = getIntent();

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        vp = (ViewPager) findViewById(R.id.OT_home_viewpager);

        String Id = intent.getStringExtra("ID");
        UserId = intent.getIntExtra("UserID", 0);
        LoginToken = intent.getStringExtra("LoginToken");


        String Username = intent.getStringExtra("Username");
        UserName = (TextView)findViewById(R.id.OT_home_username);
        UserName.setText(Username);

        String Sign = intent.getStringExtra("Sign");
        sign = (TextView)findViewById(R.id.OT_home_sign);
        sign.setText(Sign);

        String Avater = intent.getStringExtra("Avater");

        int sex = intent.getIntExtra("Sex", 3);
        imgsex = (ImageView)findViewById(R.id.OT_home_sex);
        if(sex==3){
            imgsex.setVisibility(View.INVISIBLE);
        }else if(sex==0){
            imgsex.setImageResource(R.drawable.img_small_male);
        }
        else
            imgsex.setImageResource(R.drawable.img_small_female);

        InitView();
        titleBar = (TitleBar)findViewById(R.id.OT_home_title);
        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserHomePage.this.finish();
            }
        });

    }
    protected void InitView() {
        // TODO Auto-generated method stub
        tv1 = (TextView)findViewById(R.id.OT_home_tv1);
        tv2 = (TextView)findViewById(R.id.OT_home_tv2);
        tv3 = (TextView)findViewById(R.id.OT_home_tv3);
        fans = (TextView) findViewById(R.id.OT_home_tv_fans);
        followers = (TextView) findViewById(R.id.OT_home_tv_following);
        parhs = (TextView) findViewById(R.id.OT_home_tv_parhs);
        clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.OT_home_tv_fans:
                        /*设置转场效果
                        * params two*/
                        vp.setCurrentItem(0);
                        break;
                    case R.id.OT_home_tv_following:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.OT_home_tv_parhs:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.OT_home_tv1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.OT_home_tv2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.OT_home_tv3:
                        vp.setCurrentItem(2);
                        break;
                }
            }
        };
        fans.setOnClickListener(clickListener);
        followers.setOnClickListener(clickListener);
        parhs.setOnClickListener(clickListener);
        tv1.setOnClickListener(clickListener);
        tv2.setOnClickListener(clickListener);
        tv3.setOnClickListener(clickListener);
        InitPager();
        getuserInfo();
    }

    private void InitPager(){
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tv1.setBackgroundColor(Color.rgb(25,142,123));
                        tv2.setBackgroundColor(Color.rgb(255,255,255));
                        tv3.setBackgroundColor(Color.rgb(255,255,255));
                        break;
                    case 1:
                        tv2.setBackgroundColor(Color.rgb(25,142,123));
                        tv3.setBackgroundColor(Color.rgb(255,255,255));
                        tv1.setBackgroundColor(Color.rgb(255,255,255));
                        break;
                    case 2:
                        tv3.setBackgroundColor(Color.rgb(25,142,123));
                        tv2.setBackgroundColor(Color.rgb(255,255,255));
                        tv1.setBackgroundColor(Color.rgb(255,255,255));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        AddActivitiesToViewPager();
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(pageChangeListener);
    }
    private void AddActivitiesToViewPager() {
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();

        intent.setClass(this, FansActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("QualityActivity1", intent));

        intent.setClass(this, FollowersActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("QualityActivity2", intent));

        intent.setClass(this, ParhsActivity.class);
        intent.putExtra("id", 3);
        mViews.add(getView("QualityActivity3", intent));

        viewPageAdapter = new ViewPagerAdapter(mViews);
        vp.setAdapter(viewPageAdapter);
    }
    private View getView(String id, Intent intent) {

        return manager.startActivity(id, intent).getDecorView();

    }
    private void getuserInfo(){

        //启动新线程！
        new Thread(new Runnable() {
            @Override
            public void run() {

                final RomauntNetWork romauntNetWork = new RomauntNetWork();

                Object response = romauntNetWork.getUserInfoSync(LoginToken, Integer.toString(UserId));
                if (response instanceof UserInfoResponse) {
                    UserInfoResponse userInfoResponse = (UserInfoResponse) response;

                    String username = userInfoResponse.msg.user.userName;
                    int sex = userInfoResponse.msg.user.sex;
                }
                else
                {
                   Log.e("Romaunt","status false");
                }


            }
        }).start();

    }
}
