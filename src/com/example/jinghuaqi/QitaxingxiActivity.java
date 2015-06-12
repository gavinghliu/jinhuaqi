package com.example.jinghuaqi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QitaxingxiActivity extends BaseActivity implements OnClickListener {


	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;
	
	ImageView ivJiashi1;
	ImageView ivJiashi2;
	ImageView ivJiashi3;
	
	ImageView ivYingliang1;
	ImageView ivYingliang2;
	ImageView ivYingliang3;
	
	TextView tvCPZ;
	TextView tvGonglv;
	TextView tvVersion;

	RelativeLayout rlKaiguan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qitaxingxi_activity);

		initView();
	}

	public void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		
		ivJiashi1 = (ImageView)findViewById(R.id.jiashi1);
		ivJiashi2 = (ImageView)findViewById(R.id.jiashi2);
		ivJiashi3 = (ImageView)findViewById(R.id.jiashi3);
		
		ivYingliang1 = (ImageView)findViewById(R.id.yingliang1);
		ivYingliang2 = (ImageView)findViewById(R.id.yingliang2);
		ivYingliang3 = (ImageView)findViewById(R.id.yingliang3);
		
		rlKaiguan = (RelativeLayout)findViewById(R.id.dingshi);
		
		ivJiashi1.setVisibility(View.GONE);
		ivJiashi2.setVisibility(View.GONE);
		ivJiashi3.setVisibility(View.GONE);
		
		ivYingliang1.setVisibility(View.GONE);
		ivYingliang2.setVisibility(View.GONE);
		ivYingliang3.setVisibility(View.GONE);
		
		if (DeviceInfo.deviceInfo != null) {
			if (DeviceInfo.deviceInfo.humidifyLevel == 1) {
				ivJiashi1.setVisibility(View.VISIBLE);
			}
			
			if (DeviceInfo.deviceInfo.humidifyLevel == 2) {
				ivJiashi2.setVisibility(View.VISIBLE);
			}
			
			if (DeviceInfo.deviceInfo.humidifyLevel == 3) {
				ivJiashi3.setVisibility(View.VISIBLE);
			}
		}
		
		if (DeviceInfo.deviceInfo != null) {
			if (DeviceInfo.deviceInfo.voiceLevel == 1) {
				ivYingliang1.setVisibility(View.VISIBLE);
			}
			
			if (DeviceInfo.deviceInfo.voiceLevel == 2) {
				ivYingliang2.setVisibility(View.VISIBLE);
			}
			
			if (DeviceInfo.deviceInfo.voiceLevel == 3) {
				ivYingliang3.setVisibility(View.VISIBLE);
			}
			
		}
		
		tvCPZ = (TextView)findViewById(R.id.tv_cpz);
		tvGonglv = (TextView)findViewById(R.id.tv_gonglv);
		tvVersion = (TextView)findViewById(R.id.tv_version);
		rlKaiguan = (RelativeLayout)findViewById(R.id.dingshi);
		rlKaiguan.setOnClickListener(this);
		
		tvGonglv.setText("电机功率：" + DeviceInfo.deviceInfo.motorPower);
		tvVersion.setText("版本号：v" + DeviceInfo.Version);
	}
	

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.home:
			Intent intent = new Intent(QitaxingxiActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(QitaxingxiActivity.this , ShebeiguanliActivity.class);
			startActivity(intent);
			break;
		case R.id.dingshi:
			intent = new Intent(QitaxingxiActivity.this , DingshikaiguaniActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}