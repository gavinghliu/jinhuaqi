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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class KongqizhibiaoActivity extends BaseActivity implements OnClickListener {

	public static ProgressDialog dialog;

	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;
	
	ImageView jindu1;
	ImageView jindu2;
	ImageView jindu3;
	
	
	TextView keliwu;
	TextView qiti;
	TextView guangzhaodu;
	TextView fulizi;
	TextView sidu;
	TextView wendu;
	TextView jingdu;
	
	TextView zhiliang_keliwu;
	TextView zhiliang_qiti;
	TextView zhiliang_guangzhaodu;
	TextView zhiliang_fulizi;
	TextView zhiliang_sidu;
	TextView zhiliang_wendu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Configuration.init(getApplication());
		setContentView(R.layout.kongqi_activity);
		initView();
		dialog = new ProgressDialog(this);
//		timer.schedule(task, 2000, 5000);
	}

	public void initView() {

		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		
		keliwu = (TextView) findViewById(R.id.keliwu);
		qiti = (TextView) findViewById(R.id.qiti);
		guangzhaodu = (TextView) findViewById(R.id.guangzhaodu);
		fulizi = (TextView) findViewById(R.id.fulizi);
		sidu = (TextView) findViewById(R.id.sidu);
		wendu = (TextView) findViewById(R.id.wendu);
		jingdu = (TextView) findViewById(R.id.jindu);
		
		zhiliang_keliwu = (TextView) findViewById(R.id.zhiliang_keliwu);
		zhiliang_qiti = (TextView) findViewById(R.id.zhiliang_qiti);
		zhiliang_guangzhaodu = (TextView) findViewById(R.id.zhiliang_guangzhaodu);
		zhiliang_fulizi = (TextView) findViewById(R.id.zhiliang_fulizi);
		zhiliang_sidu = (TextView) findViewById(R.id.zhiliang_sidu);
		zhiliang_wendu = (TextView) findViewById(R.id.zhiliang_wendu);
		
		jindu1 = (ImageView) findViewById(R.id.jindu1);
		jindu2 = (ImageView) findViewById(R.id.jindu2);
		jindu3 = (ImageView) findViewById(R.id.jindu3);
		
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		updateData();
	}

	private void updateData() {
		keliwu.setText(DeviceInfo.deviceInfo.PM2_5 + "");
		qiti.setText(DeviceInfo.deviceInfo.CO2 + "");
		guangzhaodu.setText(DeviceInfo.deviceInfo.light + "");
		fulizi.setText(DeviceInfo.deviceInfo.anion + "");
		sidu.setText(DeviceInfo.deviceInfo.humidity + "");
		wendu.setText(DeviceInfo.deviceInfo.temperature + "");
		jingdu.setText(DeviceInfo.deviceInfo.purifyProgress + "%");
		
		String zhiliangString = "优";
		if (Float.parseFloat(DeviceInfo.deviceInfo.PM2_5) > 900.0 ) {
			zhiliangString = "差";
		} else if (Float.parseFloat(DeviceInfo.deviceInfo.PM2_5) > 600.0 ) {
			zhiliangString = "良";
		}  else {
			zhiliangString = "优";
		}
		zhiliang_keliwu.setText(zhiliangString);
		
		//气味浓度
		if (DeviceInfo.deviceInfo.CO2 > 5000.0 ) {
			zhiliangString = "差";
		} else if (DeviceInfo.deviceInfo.CO2 > 3600.0 ) {
			zhiliangString = "良";
		}  else {
			zhiliangString = "优";
		}
		zhiliang_qiti.setText(zhiliangString);
		
		//光照度
		if (DeviceInfo.deviceInfo.light < 100 ) {
			zhiliangString = "差";
		} else if (DeviceInfo.deviceInfo.light < 400 ) {
			zhiliangString = "良";
		}  else {
			zhiliangString = "优";
		}
		zhiliang_guangzhaodu.setText(zhiliangString);
		
		//负离子浓度
		if (DeviceInfo.deviceInfo.anion < 300 ) {
			zhiliangString = "差";
		} else if (DeviceInfo.deviceInfo.anion < 700 ) {
			zhiliangString = "良";
		}  else {
			zhiliangString = "优";
		}
		zhiliang_fulizi.setText(zhiliangString);
		
		//温度
		if (DeviceInfo.deviceInfo.temperature < 18 || DeviceInfo.deviceInfo.temperature >33) {
			zhiliangString = "差";
		} else if (DeviceInfo.deviceInfo.temperature < 28) {
			zhiliangString = "优";
		}  else {
			zhiliangString = "良";
		}
		zhiliang_wendu.setText(zhiliangString);
		
		//湿度
		if (DeviceInfo.deviceInfo.humidity < 45) {
			zhiliangString = "差";
		} else if (DeviceInfo.deviceInfo.humidity < 70) {
			zhiliangString = "良";
		}  else {
			zhiliangString = "优";
		}
		zhiliang_wendu.setText(zhiliangString);
		
		
		if (DeviceInfo.deviceInfo.purifyProgress == 0) {
			jindu1.setVisibility(View.INVISIBLE);
			jindu2.setVisibility(View.INVISIBLE);
			jindu3.setVisibility(View.INVISIBLE);
		} else if (DeviceInfo.deviceInfo.purifyProgress <=33) {
			jindu1.setVisibility(View.VISIBLE);
			jindu2.setVisibility(View.INVISIBLE);
			jindu3.setVisibility(View.INVISIBLE);
			
		}  else if (DeviceInfo.deviceInfo.purifyProgress <=66) {
			jindu1.setVisibility(View.VISIBLE);
			jindu2.setVisibility(View.VISIBLE);
			jindu3.setVisibility(View.INVISIBLE);
			
		} else if (DeviceInfo.deviceInfo.purifyProgress <=100) {
			jindu1.setVisibility(View.VISIBLE);
			jindu2.setVisibility(View.VISIBLE);
			jindu3.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {

		case R.id.back:
			finish();
			break;

		case R.id.home:
			Intent intent = new Intent(KongqizhibiaoActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			intent = new Intent(KongqizhibiaoActivity.this , ShebeiguanliActivity.class);
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
	
	
	@Override
	protected void onDestroy() {
//		timer.cancel();
		super.onDestroy();
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(KongqizhibiaoActivity.this, error.getMessage(),
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
	
private final Timer timer = new Timer();
	
	Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	
	    	if (DeviceInfo.curDeviceID.equals("")) {
				return;
			}
	    	
	    	executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
					inforesponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
				}
			});
	        super.handleMessage(msg);
	    }
	};
	
	private TimerTask task = new TimerTask() {
	    @Override
	    public void run() {
	        Message message = new Message();
	        message.what = 1;
	        handler.sendMessage(message);
	    }
	}; 

	private Response.Listener<String> inforesponseListener() {
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
						if (result.has("success") && !result.getBoolean("success")) {
							Toast.makeText(KongqizhibiaoActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
							updateData();
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
		updateData();
	}
	
}