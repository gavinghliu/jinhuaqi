package com.example.jinghuaqi;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.down.DownLoadDemoActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WelcomeActivity extends BaseActivity {
	
	SharedPreferences sp;
	ProgressBar progressBar;
	String apkName;
	
	public static String FTP_DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/" + "JHQ/";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		RequestManager.init(getApplication());
		
		sp = getApplicationContext().getSharedPreferences(ShebeiguanliActivity.SP_NAME, getApplicationContext().MODE_PRIVATE);
		DeviceInfo.curDeviceID = sp.getString(DeviceInfo.cURRENT_DEVICE, "");
		DeviceInfo.curPassword = sp.getString(DeviceInfo.cURRENT_DEVICE_PW, "");
		
		DeviceInfo.context = getApplicationContext();
		
		executeRequest(new StringRequest(Method.GET, ApiParams.API_UPDATE,
				updateListener(), errorListener()) {
			protected Map<String, String> getParams() {
				return new ApiParams();
			}
		});
		
		
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		View v = inflater.inflate(
				R.layout.selfdef_welcome_progress_dialog_layout, null);
		progressBar = (ProgressBar) v.findViewById(R.id.rectangleProgressBar);
		progressBar.setIndeterminate(false);
		progressBar.setMax((int) (1 * 1000 * 1024));
		progressBar.setProgress(0);
		
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
						
						if (result.has("success") && !result.getBoolean("success")) {
							Toast.makeText(WelcomeActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
						}
						final Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(intent);
						WelcomeActivity.this.finish();
					} else {
						// 提示错误
						final Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(intent);
						WelcomeActivity.this.finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					final Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(intent);
					WelcomeActivity.this.finish();
				}
			}
		};
	}
	
	private Response.Listener<String> updateListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					final String lastVersion = object.getString("lastVersion");
					final String type = object.getString("deviceType");
					if (DeviceInfo.needUpdate(lastVersion)) {
						//需要下载版本
//						 {deviceType}-{lastVersion}.apk
//
//						 http://54.65.148.83:8080/resources/apk/airpurifier/airpurifier-0.0.1.apk
						final String downLoadUrl = ApiParams.API_HOST + "resources/apk/" + type + "/" + type + "-" + lastVersion + ".apk";
						apkName =  type + "-" + lastVersion + ".apk";
						
						File downLoadFile = new File(FTP_DOWNLOAD_PATH+ apkName);
						if (downLoadFile.exists()) {
							Intent apkIntent = new Intent(); 
				            apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				            apkIntent.setAction(android.content.Intent.ACTION_VIEW);
				            apkIntent.setDataAndType(Uri.fromFile(downLoadFile), "application/vnd.android.package-archive");
				            startActivity(apkIntent);
				            finish();
						} else {
							Intent downIntent = new Intent(WelcomeActivity.this, DownLoadDemoActivity.class);
							downIntent.putExtra(DownLoadDemoActivity.FILE_PATH, FTP_DOWNLOAD_PATH);
							downIntent.putExtra(DownLoadDemoActivity.FILE_URL, downLoadUrl);
							startActivity(downIntent);
							finish();
						}
						 
						
					} else {
						//没有版本更新
						if (!DeviceInfo.curDeviceID.equals("")) {
							executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
									responseListener(), errorListener()) {
								protected Map<String, String> getParams() {
									return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
								}
							});
						} else {
							final Intent intent = new Intent(WelcomeActivity.this,
									MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getApplicationContext().startActivity(intent);
							WelcomeActivity.this.finish();
						}
					}
					
				} catch (JSONException e) {
					if (!DeviceInfo.curDeviceID.equals("")) {
						executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
								responseListener(), errorListener()) {
							protected Map<String, String> getParams() {
								return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
							}
						});
					} else {
						final Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(intent);
						WelcomeActivity.this.finish();
					}
					e.printStackTrace();
				}
			}
		};
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(WelcomeActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
				if (!DeviceInfo.curDeviceID.equals("")) {
					executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
							responseListener(), errorListener()) {
						protected Map<String, String> getParams() {
							return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
						}
					});
				} else {
					final Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(intent);
					WelcomeActivity.this.finish();
				}
			}
		};
	}
	
	final Handler handler = new Handler(){  
	    @Override  
	    public void handleMessage(Message msg) {  
	        super.handleMessage(msg);  
	        //这里就一条消息  
	        int pro = progressBar.getProgress() + msg.arg1;  
	        progressBar.setProgress(pro);  
	        if (pro >= progressBar.getMax()) {  
	           progressBar.setVisibility(View.GONE);
	            
	            File file = new File(FTP_DOWNLOAD_PATH+ apkName);
				if (null != file && file.exists()) {
					Intent apkIntent = new Intent(); 
		            apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            apkIntent.setAction(android.content.Intent.ACTION_VIEW);
		            apkIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		            startActivity(apkIntent);
		            finish();
				}  else {
					if (!DeviceInfo.curDeviceID.equals("")) {
						executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
								responseListener(), errorListener()) {
							protected Map<String, String> getParams() {
								return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID);
							}
						});
					} else {
						final Intent intent = new Intent(WelcomeActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(intent);
						WelcomeActivity.this.finish();
					}
				}
	        }  
	    }  
	};  
}