package com.example.jinghuaqi;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YaobaoActivity extends BaseActivity implements OnClickListener {

	public static ProgressDialog dialog;

	LinearLayout mKongqizhibiao;
	LinearLayout mMoshiguanli;
	LinearLayout mYaobaoguanli;
	LinearLayout mShezhi;
	LinearLayout mKaiguan;
	LinearLayout mQitaxingxi;
	LinearLayout mKongqixiaodu;
	LinearLayout mShachuqumeng;
	
	ImageView mKongqizhibiaoIv;
	ImageView mMoshiguanliIv;
	ImageView mYaobaoguanliIv;
	ImageView mShezhiIv;
	ImageView mKaiguanIv;
	ImageView mQitaxingxiIv;
	ImageView mKongqixiaoduIv;
	ImageView mShachuqumengIv;

	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Configuration.init(getApplication());
		setContentView(R.layout.yaobaoi_activity);
		initView();
		dialog = new ProgressDialog(this);
		initIcon(DeviceInfo.deviceInfo.healthManagerMode);
	}

	public void initView() {
		mKongqizhibiao = (LinearLayout) findViewById(R.id.kongqizhibiao);
		mMoshiguanli = (LinearLayout) findViewById(R.id.moshiguanli);
		mYaobaoguanli = (LinearLayout) findViewById(R.id.yaobaoguanli);
		mShezhi = (LinearLayout) findViewById(R.id.shezhi);
		mKaiguan = (LinearLayout) findViewById(R.id.kaiguan);
		mQitaxingxi = (LinearLayout) findViewById(R.id.qitaxingxi);
		mKongqixiaodu = (LinearLayout) findViewById(R.id.xiaodu);
		mShachuqumeng = (LinearLayout) findViewById(R.id.shachong);
		
		mKongqizhibiaoIv = (ImageView) findViewById(R.id.kongqizhibiao_iv);
		mMoshiguanliIv = (ImageView) findViewById(R.id.moshiguanli_iv);
		mYaobaoguanliIv = (ImageView) findViewById(R.id.yaobaoguanli_iv);
		mShezhiIv = (ImageView) findViewById(R.id.shezhi_iv);
		mKaiguanIv = (ImageView) findViewById(R.id.kaiguan_iv);
		mQitaxingxiIv = (ImageView) findViewById(R.id.qitaxingxi_iv);
		mKongqixiaoduIv = (ImageView) findViewById(R.id.xiaodu_iv);
		mShachuqumengIv = (ImageView) findViewById(R.id.shachong_iv);

		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		
		mKongqizhibiao.setOnClickListener(this);
		mMoshiguanli.setOnClickListener(this);
		mYaobaoguanli.setOnClickListener(this);
		mShezhi.setOnClickListener(this);
		mKaiguan.setOnClickListener(this);
		mQitaxingxi.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		mKongqixiaodu.setOnClickListener(this);
		mShachuqumeng.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		
		if (id != R.id.back && id != R.id.shebie_list && R.id.sebeiguanli != id) {
			if (DeviceInfo.deviceInfo != null && DeviceInfo.deviceInfo.managerMode != 4) {
				Toast.makeText(YaobaoActivity.this, "只有在情景模式下才可以更改!",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
		switch (id) {
		case R.id.kongqizhibiao:
			Intent intent;
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 1) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 1) : (DeviceInfo.deviceInfo.healthManagerMode - 1)) + "");
				}
			});
			break;

		case R.id.moshiguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 2) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 2) : (DeviceInfo.deviceInfo.healthManagerMode - 2)) + "");
				}
			});
			break;

		case R.id.yaobaoguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 4) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 4) : (DeviceInfo.deviceInfo.healthManagerMode - 4)) + "");
				}
			});
			break;

		case R.id.shezhi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 8) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 8) : (DeviceInfo.deviceInfo.healthManagerMode - 8)) + "");
				}
			});
			break;
		case R.id.kaiguan:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 16) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 16) : (DeviceInfo.deviceInfo.healthManagerMode - 16)) + "");
				}
			});
			break;
		case R.id.qitaxingxi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 32) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 32) : (DeviceInfo.deviceInfo.healthManagerMode - 32)) + "");
				}
			});
			break;
		case R.id.xiaodu:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 64) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 64) : (DeviceInfo.deviceInfo.healthManagerMode - 64)) + "");
				}
			});
			break;
			
		case R.id.shachong:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("healthManagerMode", ((DeviceInfo.deviceInfo.healthManagerMode & 128) == 0 ? (DeviceInfo.deviceInfo.healthManagerMode + 128) : (DeviceInfo.deviceInfo.healthManagerMode - 128)) + "");
				}
			});
			break;
		case R.id.back:
			finish();
			break;

		case R.id.home:
			intent = new Intent(YaobaoActivity.this , MainActivity.class);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(YaobaoActivity.this , ShebeiguanliActivity.class);
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
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						boolean isExist = result.getBoolean("exist");
					} else {
						// 提示错误
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(YaobaoActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		};
	}
	
	public void initIcon(int type) {
		Drawable drawable = getResources().getDrawable(R.drawable.yb_bg);
		mKongqizhibiaoIv.setBackgroundDrawable((type & 1) == 1 ? drawable : null);
		mMoshiguanliIv.setBackgroundDrawable((type & 2) == 2 ? drawable : null);
		mYaobaoguanliIv.setBackgroundDrawable((type & 4) == 4 ? drawable : null);
		mShezhiIv.setBackgroundDrawable((type & 8) == 8? drawable : null);
		mKaiguanIv.setBackgroundDrawable((type & 16) == 16 ? drawable : null);
		mQitaxingxiIv.setBackgroundDrawable((type & 32) == 32 ? drawable : null);
		mKongqixiaoduIv.setBackgroundDrawable((type & 64) == 64 ? drawable : null);
		mShachuqumengIv.setBackgroundDrawable((type & 128) == 128 ? drawable : null);
	}

	public void onCreateDialog() {
		dialog.setTitle("处理中");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setIndeterminate(true);
		dialog.setMessage("请稍后~");
		dialog.show();
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
							executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
									inforesponseListener(), errorListener()) {
								protected Map<String, String> getParams() {
									return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
								}
							});
						} else {
							dialog.dismiss();
							Toast.makeText(YaobaoActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						Toast.makeText(YaobaoActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
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
				 ;
				JSONObject object;
				dialog.dismiss();
				try {
					Log.d("jinghuaqi", "response=" + response);
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						
						if (result.has("success") && !result.getBoolean("success")) {
							Toast.makeText(YaobaoActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
							initIcon(DeviceInfo.deviceInfo.healthManagerMode);
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
		initIcon(DeviceInfo.deviceInfo.healthManagerMode);
	}
}