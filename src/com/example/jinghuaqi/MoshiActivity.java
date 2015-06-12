package com.example.jinghuaqi;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MoshiActivity extends BaseActivity implements OnClickListener {

	public static ProgressDialog dialog;

	TextView mKongqizhibiao;
	TextView mMoshiguanli;
	TextView mYaobaoguanli;
	TextView mShezhi;
	TextView mKaiguan;
	TextView mQitaxingxi;

	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moshi_activity);
		initView();
		dialog = new ProgressDialog(this);
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
		
		mKongqizhibiao.setOnClickListener(this);
		mMoshiguanli.setOnClickListener(this);
		mYaobaoguanli.setOnClickListener(this);
		mShezhi.setOnClickListener(this);
		mKaiguan.setOnClickListener(this);
		mQitaxingxi.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		init(DeviceInfo.deviceInfo.managerMode);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent;
		int id = arg0.getId();
		switch (id) {
		case R.id.kongqizhibiao:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "0");
				}
			});
			break;
		case R.id.moshiguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "3");
				}
			});
			break;
		case R.id.yaobaoguanli:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "2");
				}
			});
			break;
		case R.id.shezhi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "1");
				}
			});
			break;
		case R.id.kaiguan:
			intent = new Intent(MoshiActivity.this, QijingmoshiActivity.class);
			startActivity(intent);
			break;
		case R.id.qitaxingxi:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "5");
				}
			});
			break;

		case R.id.back:
			finish();
			break;

		case R.id.home:
			intent = new Intent(MoshiActivity.this , MainActivity.class);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(MoshiActivity.this , ShebeiguanliActivity.class);
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
						}else {
							dialog.dismiss();
							// 提示错误
							Toast.makeText(MoshiActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						dialog.dismiss();
						Toast.makeText(MoshiActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
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
						JSONObject deviceInfo = result
								.getJSONObject("deviceInfo");
						DeviceInfo.parse(deviceInfo);
						DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
						init(DeviceInfo.deviceInfo.managerMode);
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
		init(DeviceInfo.deviceInfo.managerMode);
	}
	
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(MoshiActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
				dialog.dismiss();
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
	
	public void init(int moshi) {
		Drawable drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_jisu_disable); 
		mKongqizhibiao.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_huafen_disable); 
		mMoshiguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_shuimian_disable); 
		mYaobaoguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_zidong_disable); 
		mShezhi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_qingjing_disable); 
		mKaiguan.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_zidingying_disable); 
		mQitaxingxi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		
		switch (moshi) {
		case 0:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_jisu); 
			mKongqizhibiao.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			break;
		case 3:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_huafen); 
			mMoshiguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
		break;
		
		case 2:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_shuimian); 
			mYaobaoguanli.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			break;
		case 1:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_zidong); 
			mShezhi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			break;
		case 4:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_qingjing); 
			mKaiguan.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			
			break;
		case 5:
			drawable= MoshiActivity.this.getResources().getDrawable(R.drawable.moshi_zidingying_disable); 
			mQitaxingxi.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			break;

		default:
			break;
		}
	}
}