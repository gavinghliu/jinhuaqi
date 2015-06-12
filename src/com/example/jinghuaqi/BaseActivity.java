package com.example.jinghuaqi;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.DialerFilter;
import android.widget.Toast;

public class BaseActivity extends Activity{
	
	protected ProgressDialog pDialog;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		pDialog = ProgressDialog.show(BaseActivity.this, "", "请稍后。。", true);  
		pDialog.dismiss();
		timer.schedule(task, 1000, 2000);
	}
	
	protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		RequestManager.cancelAll(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (!shouldBackToHome()) {
			super.onBackPressed();	
			return;
		}
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
	
	public boolean shouldBackToHome() {
		return false;
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
				 ;
				JSONObject object;
				try {
					Log.d("jinghuaqi", "response=" + response);
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						JSONObject result = object.getJSONObject("result");
						
						if (result.has("success") && !result.getBoolean("success")) {
//							Toast.makeText(BaseActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
							if (!DeviceInfo.deviceInfo.powerSwitch || !DeviceInfo.deviceInfo.isOnline) {
								if (!(BaseActivity.this instanceof MainActivity) && !(BaseActivity.this instanceof ShebeiguanliActivity)) {
									finish();
								}
							}
							
							updateUI();
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
	
	public void updateUI() {
	}
	
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		};
	}
	
}
