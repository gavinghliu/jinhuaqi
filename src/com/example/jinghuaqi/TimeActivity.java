package com.example.jinghuaqi;

import java.util.Calendar;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.jinghuaqi.R.id;

import kankan.wheel.widget.NumericWheelAdapter;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeActivity extends BaseActivity implements OnClickListener {
	// Time changed flag
	private boolean timeChanged = false;
	
	//
	public static ProgressDialog dialog;
	private boolean timeScrolled = false;
	static private int curHour;
	static private int curMinute;
	
	static private int curWhMin;
	static private int curWhSec;
	private Button btnCommit;
	SharedPreferences sp;
	
	RadioButton rdKai;
	RadioButton rdGuan;
	RadioButton rdXt;
	RadioButton rdWh;
	
	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;
	
	LinearLayout llTime;
	LinearLayout llWh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.time_layout);
		sp = getSharedPreferences(ShebeiguanliActivity.SP_NAME, MODE_PRIVATE);
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 23));
		hours.setLabel("hours");
		dialog = new ProgressDialog(this);
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setLabel("mins");
		mins.setCyclic(true);
		
		llTime = (LinearLayout) findViewById(R.id.time_sel);
		llWh = (LinearLayout) findViewById(R.id.wh_sel);
		final WheelView whMin = (WheelView) findViewById(R.id.wh_min);
		whMin.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		whMin.setLabel("mins");
		whMin.setCyclic(true);
		
		final WheelView whSec = (WheelView) findViewById(R.id.wh_sec);
		whSec.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		whSec.setLabel("sec");
		
		llWh.setVisibility(View.GONE);
		
		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		btnCommit = (Button)findViewById(R.id.btn_commit);
		btnCommit.setOnClickListener(this);
		
		rdGuan = (RadioButton)findViewById(R.id.rd_guan);
		rdKai = (RadioButton)findViewById(R.id.rd_kai);
		rdXt = (RadioButton)findViewById(R.id.rd_xt);
		rdWh = (RadioButton)findViewById(R.id.rd_wh);
//		rdGuan.setOnCheckedChangeListener(changeListener);
//		rdKai.setOnCheckedChangeListener(changeListener);
//		rdXt.setOnCheckedChangeListener(changeListener);
		rdWh.setOnCheckedChangeListener(changeListener);
		rdGuan.setChecked(true);
		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
	
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
	
		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
		
		addChangingListener(whMin,"min");
		addChangingListener(whSec,"sec");
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					curHour = hours.getCurrentItem();
					curMinute = mins.getCurrentItem();
					
					curWhMin = whMin.getCurrentItem();
					curWhSec = whSec.getCurrentItem();
					timeChanged = false;
				}
			}
		};

		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				curHour = hours.getCurrentItem();
				curMinute = mins.getCurrentItem();
				
				curWhMin = whMin.getCurrentItem();
				curWhSec = whSec.getCurrentItem();
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		
		whMin.addScrollingListener(scrollListener);
		whSec.addScrollingListener(scrollListener);
		
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
	
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.home:
			Intent intent = new Intent(TimeActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(TimeActivity.this , ShebeiguanliActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_commit:
			if (rdKai.isChecked()) {
				onCreateDialog();
				executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
						controlResponseListener(), errorListener()) {
					protected Map<String, String> getParams() {
						return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("bootTimeHour", curHour + "").with("bootTimeMin", curMinute+"");
					}
				});
			}
			
			if (rdGuan.isChecked()) {
				onCreateDialog();
				executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
						controlResponseListener(), errorListener()) {
					protected Map<String, String> getParams() {
						return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("shutdownTimeHour", curHour + "").with("shutdownTimeMin", curMinute+"");
					}
				});
			}
			
			if (rdXt.isChecked()) {
				onCreateDialog();
				executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
						controlResponseListener(), errorListener()) {
					protected Map<String, String> getParams() {
						return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("systemTimeHour", curHour + "").with("systemTimeMin", curMinute+"");
					}
				});
			}
			
			if (rdWh.isChecked()) {
				onCreateDialog();
				executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
						controlResponseListener(), errorListener()) {
					protected Map<String, String> getParams() {
						return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("atomizeTimeMin", curWhMin + "").with("atomizeTimeSec", curWhSec+"");
					}
				});
			}
		default:
			break;
		}
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
							finish();
						} else {
							dialog.dismiss();
							Toast.makeText(TimeActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						Toast.makeText(TimeActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	
	public void onCreateDialog() {
		dialog.setTitle("处理中");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setIndeterminate(true);
		dialog.setMessage("请稍后~");
		dialog.show();
	}
	
	CompoundButton.OnCheckedChangeListener changeListener =  new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton btn, boolean arg1) {
			
			if (!arg1) {
				llTime.setVisibility(View.VISIBLE);
				llWh.setVisibility(View.GONE);
			} else {
				llTime.setVisibility(View.GONE);
				llWh.setVisibility(View.VISIBLE);
			}
			
		}
	};
}
