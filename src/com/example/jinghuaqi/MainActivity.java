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

import android.R.bool;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

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
		// Configuration.init(getApplication());
		setContentView(R.layout.main_activity);
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
		updateKaiGuan();
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();

		if (R.id.back != id && R.id.sebeiguanli != id && R.id.home != id
				&& R.id.kaiguan != id) {

			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}

			if (!DeviceInfo.deviceInfo.powerSwitch) {
				Toast.makeText(MainActivity.this, "当前设备为关机状态",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
		switch (id) {
		case R.id.kongqizhibiao:

			Intent intent = new Intent(MainActivity.this,
					KongqizhibiaoActivity.class);
			startActivity(intent);
			break;

		case R.id.moshiguanli:
			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}
			intent = new Intent(MainActivity.this, MoshiActivity.class);
			startActivity(intent);
			break;

		case R.id.yaobaoguanli:

			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}
			intent = new Intent(MainActivity.this, YaobaoActivity.class);
			startActivity(intent);
			break;

		case R.id.shezhi:
			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}
			intent = new Intent(MainActivity.this, ShezhiActivity.class);
			startActivity(intent);
			break;

		case R.id.kaiguan:

			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}

			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST,
					ApiParams.API_CONTROLL, responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams()
							.with("password", DeviceInfo.curPassword)
							.with("deviceId", DeviceInfo.curDeviceID)
							.with("powerSwitch",
									DeviceInfo.deviceInfo.powerSwitch ? "false"
											: "true");
				}
			});
			break;

		case R.id.qitaxingxi:

			if (null == DeviceInfo.deviceInfo) {
				Toast.makeText(MainActivity.this, "连接异常，请重新设置当前设备",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (DeviceInfo.curDeviceID.equals("")) {
				Toast.makeText(MainActivity.this, "当前未连接设备，请连接...",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!DeviceInfo.deviceInfo.isOnline) {
				Toast.makeText(MainActivity.this, "当前设备不在线", Toast.LENGTH_LONG)
						.show();
				return;
			}
			intent = new Intent(MainActivity.this, QitaxingxiActivity.class);
			startActivity(intent);
			break;

		case R.id.back:
			exit();
			break;

		case R.id.home:
			break;

		case R.id.sebeiguanli:
			intent = new Intent(MainActivity.this, ShebeiguanliActivity.class);
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
							executeRequest(new StringRequest(Method.POST,
									ApiParams.API_INFO, inforesponseListener(),
									errorListener()) {
								protected Map<String, String> getParams() {
									return new ApiParams().with("password",
											DeviceInfo.curPassword).with(
											"deviceId", DeviceInfo.curDeviceID);
								}
							});
						} else {
							// 提示错误
							dialog.dismiss();
							Toast.makeText(MainActivity.this, "操作失败",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						// 提示错误
						dialog.dismiss();
						Toast.makeText(MainActivity.this,
								object.getString("errorMsg"),
								Toast.LENGTH_SHORT).show();
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

						if (result.has("success")
								&& !result.getBoolean("success")) {
							Toast.makeText(MainActivity.this,
									object.getString("errorMsg"),
									Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result
									.has("online") ? result
									.getBoolean("online") : false);
							updateKaiGuan();
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
		updateKaiGuan();
	}

	public void updateKaiGuan() {
		boolean isClose = false;
		if (DeviceInfo.deviceInfo != null && DeviceInfo.deviceInfo.isOnline
				&& DeviceInfo.deviceInfo.powerSwitch) {
			isClose = true;
		}

		mKaiguan.setText(isClose ? "开" : "关");
		Drawable drawable = MainActivity.this.getResources().getDrawable(
				isClose ? R.drawable.icon_kaiguang_press
						: R.drawable.icon_kaiguang);
		mKaiguan.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
				null);

	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(MainActivity.this, error.getMessage(),
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
		}

		return false;

	}

	private void exit() {
		// 创建退出对话框
		AlertDialog isExit = new AlertDialog.Builder(this).create();
		// 设置对话框标题
		isExit.setTitle("系统提示");
		// 设置对话框消息
		isExit.setMessage("确定要退出吗");
		// 添加选择按钮并注册监听
		isExit.setButton("确定", listener);
		isExit.setButton2("取消", listener);
		// 显示对话框
		isExit.show();

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

}