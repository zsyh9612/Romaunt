package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.StorydegitalActivity;

import java.util.List;
import java.util.Map;

import woofer.com.test.R;
import com.woofer.adapter.FansAdapter;
public class FollowingsActivity extends Activity{
    private ListView mDataLV;
    private List<Map<String,Object>> dataList;
    private BroadcastReceiver mBroadcastReceiver;
    private int followingEnable;
    private RelativeLayout relativeLayout;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        relativeLayout = (RelativeLayout)findViewById(R.id.OT_home_follow_Rlayout);

        mDataLV = (ListView) findViewById(R.id.OT_fans_data);


        SharedPreferences EnableSp = getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);

        followingEnable = EnableSp.getInt("FOLLOWINGENABLE", 1);
        if(followingEnable==0){
            mDataLV.setBackgroundResource(R.drawable.followingunavalible);
        }
        else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    dataList= OtherUserHomePage.otherUserHomePageTransfer.followingList;
                    mDataLV.setAdapter(new FansAdapter(FollowingsActivity.this,dataList));

                    //unregisterReceiver(mBroadcastReceiver);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.zaizai1.broadcast.notifyFollowingsGot");
            registerReceiver(mBroadcastReceiver, intentFilter);

        }
    }
}