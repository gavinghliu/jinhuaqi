package com.example.jinghuaqi;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.example.jinghuaqi.R.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShezhiActivity extends BaseActivity implements OnClickListener {

	public static ProgressDialog dialog;

	TextView mKongqizhibiao;
	TextView mMoshiguanli;
	TextView mYaobaoguanli;
	TextView mShezhi;
	TextView mKaiguan;
	TextView mQitaxingxi;
	
	TextView yingyueTv;
	TextView dengTv;
	TextView jiashiTv;

	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;
	
	LinearLayout mFengshuLinearLayout;
	ImageView mFengshu1;
	ImageView mFengshu2;
	ImageView mFengshu3;
	ImageView mFengshu4;
	ImageView mFengshu5;
	
	ImageView yingyueIv;
	ImageView dengIv;
	ImageView jiashiIv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shezhi_activity);
		initView();
		dialog = new ProgressDialog(this);
		init();
	}

	public void initView() {
		mKongqizhibiao = (TextView) findViewById(R.id.kongqizhibiao);
		mMoshiguanli = (TextView) findViewById(R.id.moshiguanli);
		mYaobaoguanli = (TextView) findViewById(R.id.yaobaoguanli);
		mShezhi = (TextView) findViewById(R.id.shezhi);
		mKaiguan = (TextView) findViewById(R.id.kaiguan);
		mQitaxingxi = (TextView) findViewById(R.id.qitaxingxi);

		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		
		dengTv = (TextView) findViewById(R.id.fengwei);
		yingyueTv = (TextView) findViewById(R.id.yingyue);
		jiashiTv = (TextView) findViewById(R.id.jiashi);
		
		mKongqizhibiao.setOnClickListener(this);
		mMoshiguanli.setOnClickListener(this);
		mYaobaoguanli.setOnClickListener(this);
		mShezhi.setOnClickListener(this);
		mKaiguan.setOnClickListener(this);
		mQitaxingxi.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		dengTv.setOnClickListener(this);
		yingyueTv.setOnClickListener(this);
		jiashiTv.setOnClickListener(this);
		
		mFengshuLinearLayout = (LinearLayout) findViewById(R.id.select_fengsu);
		mFengshu1 = (ImageView) findViewById(R.id.shengzhi_fengsu1);
		mFengshu2 = (ImageView) findViewById(R.id.shengzhi_fengsu2);
		mFengshu3 = (ImageView) findViewById(R.id.shengzhi_fengsu3);
		mFengshu4 = (ImageView) findViewById(R.id.shengzhi_fengsu4);
		mFengshu5 = (ImageView) findViewById(R.id.shengzhi_fengsu5);
		
		mFengshuLinearLayout.setVisibility(View.GONE);
		mFengshu1.setOnClickListener(this);
		mFengshu2.setOnClickListener(this);
		mFengshu3.setOnClickListener(this);
		mFengshu4.setOnClickListener(this);
		mFengshu5.setOnClickListener(this);
		mFengshuLinearLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		Intent intent;
		
		if (id != R.id.back && id != R.id.shebie_list && R.id.sebeiguanli != id) {
			if (DeviceInfo.deviceInfo != null && DeviceInfo.deviceInfo.managerMode != 4) {
				Toast.makeText(ShezhiActivity.this, "只有在情景模式下才可以更改!",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
		
		switch (id) {
		case R.id.select_fengsu:
			mFengshuLinearLayout.setVisibility(View.GONE);
			break;
		case R.id.kongqizhibiao:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("childLockSwitch", DeviceInfo.deviceInfo.childLockSwitch ? "false" : "true");
				}
			});
			break;

		case R.id.moshiguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("uvSwitch", DeviceInfo.deviceInfo.uvSwitch ? "false" : "true");
				}
			});
			break;

		case R.id.yaobaoguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("espSwitch", DeviceInfo.deviceInfo.esp ? "false" : "true");
				}
			});
			break;

		case R.id.shezhi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("anionSwitch", DeviceInfo.deviceInfo.anionSwitch ? "false" : "true");
				}
			});
			break;
			
		case R.id.kaiguan:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("windSwitch", DeviceInfo.deviceInfo.windSwitch ? "false" : "true");
				}
			});
			break;
			
		case R.id.qitaxingxi:
			mFengshuLinearLayout.setVisibility(View.VISIBLE);
			break;
			
		case R.id.shengzhi_fengsu1:
			mFengshuLinearLayout.setVisibility(View.GONE);
			if (DeviceInfo.deviceInfo.windSpeed == 1) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("windSpeed", "1");
				}
			});
			break;
		case R.id.shengzhi_fengsu2:
			mFengshuLinearLayout.setVisibility(View.GONE);
			if (DeviceInfo.deviceInfo.windSpeed == 2) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("windSpeed", "2");
				}
			});
			break;
		case R.id.shengzhi_fengsu3:
			mFengshuLinearLayout.setVisibility(View.GONE);
			if (DeviceInfo.deviceInfo.windSpeed == 3) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("windSpeed", "3");
				}
			});
			break;
		case R.id.shengzhi_fengsu4:
			mFengshuLinearLayout.setVisibility(View.GONE);
			if (DeviceInfo.deviceInfo.windSpeed == 4) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("windSpeed", "4");
				}
			});
			break;
		case R.id.shengzhi_fengsu5:
			mFengshuLinearLayout.setVisibility(View.GONE);
			if (DeviceInfo.deviceInfo.windSpeed == 5) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("windSpeed", "5");
				}
			});
			break;
		case R.id.fengwei:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("decorativeLightSwitch", DeviceInfo.deviceInfo.decorativeLightSwitch ? "false" : "true");
				}
			});
			break;
			
		case R.id.jiashi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("humidifierSwitch", (DeviceInfo.deviceInfo.humidifyLevel + 1) % 4 + "");
				}
			});
			break;
		case R.id.yingyue:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", DeviceInfo.curDeviceID).with("password", DeviceInfo.curPassword).with("musicSwitch", DeviceInfo.deviceInfo.musicSwitch? "false" : "true");
				}
			});
			break;

		case R.id.back:
			finish();
			break;

		case R.id.home:
			intent = new Intent(ShezhiActivity.this , MainActivity.class);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(ShezhiActivity.this , ShebeiguanliActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						if (result.getBoolean("success")) {
							executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
									inforesponseListener(), errorListener()) {
								protected Map<String, String> getParams() {
									return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
								}
							});
						} else {
							dialog.dismiss();
							Toast.makeText(ShezhiActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						dialog.dismiss();
						Toast.makeText(ShezhiActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private Response.Listener<String> inforesponseListener() {
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
						
						if (result.has("success") && !result.getBoolean("success")) {
							Toast.makeText(ShezhiActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
							init();
						}
					} else {
						// 提示错误
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	
	@Override
	public void updateUI() {
		init();
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(ShezhiActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
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
	
	public void init() {
		Drawable drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.childLockSwitch ? R.drawable.shezhi_ertongsou_press : R.drawable.shezhi_ertongsou); 
		mKongqizhibiao.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.uvSwitch ? R.drawable.shezhi_uv_press : R.drawable.shezhi_uv); 
		mMoshiguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.esp ? R.drawable.shezhi_esp_press : R.drawable.shezhi_esp); 
		mYaobaoguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.anionSwitch ? R.drawable.shezhi_fulizi_press : R.drawable.shezhi_fulizi); 
		mShezhi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.windSwitch ? R.drawable.shezhi_fengshu_press : R.drawable.shezhi_fengshu); 
		mKaiguan.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(R.drawable.shezhi_baifeng); 
		mQitaxingxi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.musicSwitch ? R.drawable.shezhi_yingyue_press : R.drawable.shezhi_yingyue); 
		yingyueTv.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= ShezhiActivity.this.getResources().getDrawable(DeviceInfo.deviceInfo.decorativeLightSwitch ? R.drawable.shezhi_deng_press : R.drawable.shezhi_deng); 
		dengTv.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		
		
		
		drawable= ShezhiActivity.this.getResources().getDrawable(R.drawable.shezhi_jiashi0 + DeviceInfo.deviceInfo.humidifyLevel);
		jiashiTv.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		
		mFengshu1.setImageResource(R.drawable.shezhi_fengsu1);
		mFengshu2.setImageResource(R.drawable.shezhi_fengsu2);
		mFengshu3.setImageResource(R.drawable.shezhi_fengsu3);
		mFengshu4.setImageResource(R.drawable.shezhi_fengsu4);
		mFengshu5.setImageResource(R.drawable.shezhi_fengsu5);
		
		int speed = DeviceInfo.deviceInfo.windSpeed;
		if (speed != 0) {
			drawable= ShezhiActivity.this.getResources().getDrawable(R.drawable.shezhi_fengshu1 + speed - 1); 
			mQitaxingxi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			
			if (speed == 1) {
				mFengshu1.setImageDrawable(drawable);
			} else if (speed == 2) {
				mFengshu2.setImageDrawable(drawable);
			} else if (speed == 3) {
				mFengshu3.setImageDrawable(drawable);
			} else if (speed == 4) {
				mFengshu4.setImageDrawable(drawable);
			} else if (speed == 5) {
				mFengshu5.setImageDrawable(drawable);
			}
		}
	}
		
}