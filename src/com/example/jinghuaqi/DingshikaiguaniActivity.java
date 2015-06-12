package com.example.jinghuaqi;

import java.util.Calendar;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DingshikaiguaniActivity extends BaseActivity implements OnClickListener {

	final static public String DINGSHIKAI = "dingshikai";
	final static public String DINGSHIGUANG = "dingshiguang";
	
	public static ProgressDialog dialog;
	
	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;
	RelativeLayout rlKaiLayout;
	RelativeLayout rlGuanLayout;
	
	ListView mListView;
	Button mAddTv;
	
	TextView mkaiTime;
	TextView mguanTime;
	Button mKaiDelBtn;
	Button mGuanDelBtn;
	
	TextView mSysTime;
	TextView mWhTime;
	
	SharedPreferences sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingshikaiguan_activity);
		sp = getSharedPreferences(ShebeiguanliActivity.SP_NAME,
				MODE_PRIVATE);
		dialog = new ProgressDialog(this);
		initView();
		updateUI();
	}

	public void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		mAddTv = (Button)findViewById(R.id.dingshi_add);
		mAddTv.setOnClickListener(this);
		
		rlKaiLayout = (RelativeLayout)findViewById(R.id.rl_dingshikai);
		rlGuanLayout = (RelativeLayout)findViewById(R.id.rl_dingshiguang);
		
		mkaiTime = (TextView)findViewById(R.id.tv_time);
		mguanTime = (TextView)findViewById(R.id.tv_guang_time);
		mKaiDelBtn = (Button)findViewById(R.id.del_btn);
		mGuanDelBtn = (Button)findViewById(R.id.del_guang_btn);
		mKaiDelBtn.setOnClickListener(this);
		mGuanDelBtn.setOnClickListener(this);
		
		mSysTime = (TextView)findViewById(R.id.tv_xt_time);
		mWhTime = (TextView)findViewById(R.id.tv_wh_time);
		
	}
	

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.home:
			Intent intent = new Intent(DingshikaiguaniActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(DingshikaiguaniActivity.this , ShebeiguanliActivity.class);
			startActivity(intent);
			break;
		case R.id.dingshi_add:
			intent = new Intent(DingshikaiguaniActivity.this, TimeActivity.class);
			startActivity(intent);
			break;
		case R.id.del_btn:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("bootTimeHour", 0 + "").with("bootTimeMin", 0+"");
				}
			});
			
			break;
		case R.id.del_guang_btn:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlGuanResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("shutdownTimeHour", 0 + "").with("shutdownTimeMin", 0+"");
				}
			});
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private Response.Listener<String> controlResponseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				JSONObject object;
				dialog.dismiss();
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						if (result.getBoolean("success")) {
							dialog.dismiss();
							updateUI();
						} else {
							dialog.dismiss();
							Toast.makeText(DingshikaiguaniActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						Toast.makeText(DingshikaiguaniActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private Response.Listener<String> controlGuanResponseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				JSONObject object;
				dialog.dismiss();
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						if (result.getBoolean("success")) {
							dialog.dismiss();
							updateUI();
						} else {
							dialog.dismiss();
							Toast.makeText(DingshikaiguaniActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						Toast.makeText(DingshikaiguaniActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	@Override
	public void updateUI() {
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
		
//		if (DeviceInfo.deviceInfo.bootTimeHour == -1 || (curHours > DeviceInfo.deviceInfo.bootTimeHour) || (curHours == DeviceInfo.deviceInfo.bootTimeHour && curMinutes > DeviceInfo.deviceInfo.bootTimeMin)) {
//			rlKaiLayout.setVisibility(View.GONE);
//		} else {
			rlKaiLayout.setVisibility(View.VISIBLE);
			mkaiTime.setText(DeviceInfo.deviceInfo.bootTimeHour + ":" + (DeviceInfo.deviceInfo.bootTimeMin < 10 ? "0" + DeviceInfo.deviceInfo.bootTimeMin : DeviceInfo.deviceInfo.bootTimeMin));
//		}
		
//		if (DeviceInfo.deviceInfo.shutdownHour == -1 || (curHours > DeviceInfo.deviceInfo.shutdownHour) || (curHours == DeviceInfo.deviceInfo.shutdownHour && curMinutes > DeviceInfo.deviceInfo.shutdownMin)) {
//			rlGuanLayout.setVisibility(View.GONE);
//		} else {
			rlKaiLayout.setVisibility(View.VISIBLE);
			mguanTime.setText(DeviceInfo.deviceInfo.shutdownHour + ":" + (DeviceInfo.deviceInfo.shutdownMin < 10 ? "0" + DeviceInfo.deviceInfo.shutdownMin : DeviceInfo.deviceInfo.shutdownMin));
//		}
		
		mSysTime.setText(DeviceInfo.deviceInfo.systemTimeHour + ":" + (DeviceInfo.deviceInfo.systemTimeMin < 10 ? "0" + DeviceInfo.deviceInfo.systemTimeMin : DeviceInfo.deviceInfo.systemTimeMin));
		mWhTime.setText(DeviceInfo.deviceInfo.atomizeTimeMin + " min:" + (DeviceInfo.deviceInfo.atomizeTimeSec < 10 ? "0" + DeviceInfo.deviceInfo.atomizeTimeSec : DeviceInfo.deviceInfo.atomizeTimeSec) + "s");
	}
	
	
	public void onCreateDialog() {
		dialog.setTitle("处理中");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setIndeterminate(true);
		dialog.setMessage("请稍后~");
		dialog.show();
	}
}