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

public class QijingmoshiActivity extends BaseActivity implements OnClickListener {

	public static ProgressDialog dialog;
	
	TextView mSenLin;
	TextView mYuanYe;
	TextView mHaiyang;
	SharedPreferences sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qingjingmoshi_activity);
		sp = getSharedPreferences(ShebeiguanliActivity.SP_NAME,
				MODE_PRIVATE);
		dialog = new ProgressDialog(this);
		initView();
		updateUI();
	}

	public void initView() {
		mSenLin = (TextView)findViewById(R.id.tv_senlin);
		mYuanYe = (TextView)findViewById(R.id.tv_yuanye);
		mHaiyang = (TextView)findViewById(R.id.tv_haiyang);
		mSenLin.setOnClickListener(this);
		mYuanYe.setOnClickListener(this);
		mHaiyang.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.home:
			Intent intent = new Intent(QijingmoshiActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(QijingmoshiActivity.this , ShebeiguanliActivity.class);
			startActivity(intent);
			break;
		case R.id.dingshi_add:
			intent = new Intent(QijingmoshiActivity.this, TimeActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_senlin:
		case R.id.tv_yuanye:
		case R.id.tv_haiyang:
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_CONTROLL,
					controlResponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("managerMode", "4");
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
							finish();
						} else {
							dialog.dismiss();
							Toast.makeText(QijingmoshiActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						Toast.makeText(QijingmoshiActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	@Override
	public void updateUI() {
	}
	
	
	public void onCreateDialog() {
		dialog.setTitle("处理中");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setIndeterminate(true);
		dialog.setMessage("请稍后~");
		dialog.show();
	}
}