package com.example.jinghuaqi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShebeiguanliActivity extends BaseActivity implements OnClickListener {

	final static public String SHEBEI_LIST = "shebei_list";
	final static public String CURRENT_SHEBEI = "current_shebei";
	final static public String SP_NAME= "jinghuaqi";
	final static public String CURRENT_PASSWORD= "password";
	final static public String PW_ID = "pw_id";
	
	
	public static ProgressDialog dialog;
	static AlertDialog dlg;
	SharedPreferences sp;

	ImageView mBack;
	ImageView mHome;
	ImageView mSebeiguanli;

	ListView mListView;
	EditText mEditText;
	EditText mEditPwText;
	Button mCommitBtn;
	TextView mAddTv;
	List<String> mList;
	ShebeiAdapter mAdapter;
	
	TextView mCurrentTv;
	TextView mHttp;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			onCreateDialog();
			final String id = (String)msg.obj;
			final String pw = msg.getData().getString("pw");
			
			executeRequest(new StringRequest(Method.POST,  ApiParams.API_INFO,
					inforesponseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("password", pw).with("deviceId", id);
				}
			});
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);

		initView();
		dialog = new ProgressDialog(this);
		mList = new ArrayList<String>();
		
		dlg = new AlertDialog.Builder(this).create();
		dlg.setCanceledOnTouchOutside(false);
		
		mHttp = (TextView) findViewById(R.id.http);
		
		mHttp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dlg.show();
				dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				Window window = dlg.getWindow();
				Window dialogWindow = dlg.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.CENTER);
				dialogWindow.setAttributes(lp);
				window.setContentView(R.layout.changeip_dialog);
				
				ImageButton ivButton = (ImageButton) dlg.findViewById(R.id.dialog_close);
				TextView tView = (TextView)dlg.findViewById(R.id.cur_ip);
				final EditText etIP = (EditText) dlg.findViewById(R.id.et_ip);
				Button commitBtn = (Button) dlg.findViewById(R.id.dialog_ok);
				tView.setText(ApiParams.API_HOST);
				
				ivButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						if (dlg.isShowing()) {
							dlg.dismiss();
						}
					}
				});
				
				commitBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ApiParams.API_HOST = etIP.getText().toString();
						ApiParams.updatHost();
						dlg.dismiss();
					}
				});
				
			}
		});
		
		mAdapter = new ShebeiAdapter(this, mList, mHandler);
		upDatedate();
		
	}

	public void initView() {

		mListView = (ListView) findViewById(R.id.shebie_list);
		
		mCommitBtn = (Button) findViewById(R.id.btn_add);
		mCommitBtn.setOnClickListener(this);
		mEditText = (EditText) findViewById(R.id.et_add);
		mEditPwText = (EditText) findViewById(R.id.et_add_pw);
		
		mBack = (ImageView) findViewById(R.id.back);
		mHome = (ImageView) findViewById(R.id.home);
		mSebeiguanli = (ImageView) findViewById(R.id.sebeiguanli);
		mAddTv = (TextView) findViewById(R.id.tv_add);
		
		mCurrentTv = (TextView) findViewById(R.id.tv_current);
		
		mAddTv.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mHome.setOnClickListener(this);
		mSebeiguanli.setOnClickListener(this);
		
		mCurrentTv.setText("当前设备：" + DeviceInfo.curDeviceID);
	}
	
	public void upDatedate() {
		String shebeiString = sp.getString(SHEBEI_LIST, "");
		if (shebeiString.trim().length() > 0) {
			mEditText.setVisibility(View.VISIBLE);
			mEditPwText.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
			mAddTv.setVisibility(View.GONE);
			mCommitBtn.setVisibility(View.VISIBLE);
			mCurrentTv.setVisibility(View.VISIBLE);
		} else {
			mEditText.setVisibility(View.GONE);
			mEditPwText.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
			mAddTv.setVisibility(View.VISIBLE);
			mCommitBtn.setVisibility(View.GONE);
			mCurrentTv.setVisibility(View.GONE);
		}
		
		String[] shebStrings = shebeiString.split("&");
		mList.clear();
		for (int i = 0; i < shebStrings.length; i++) {
			mList.add(shebStrings[i]);
		}
		mAdapter.setList(mList);
		mAdapter.notifyDataSetChanged();
		mListView.setAdapter(mAdapter);
		mCurrentTv.setText("当前设备：" + DeviceInfo.curDeviceID);
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int id,
//					long position) {
//				ViewHoder viewHoder = (ViewHoder)view.getTag();
//				DeviceInfo.setCurrentId(viewHoder.mName.toString(), getApplicationContext());
//				mCurrentTv.setText("当前设备：" + DeviceInfo.curDeviceID);
//			}
//		});
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.btn_add:
			final String sheBeiId = mEditText.getText().toString();
			final String sheBeipw = mEditPwText.getText().toString();
			
			if (sheBeiId.length() == 0 || sheBeipw.length() == 0) {
				return;
			}
			onCreateDialog();
			executeRequest(new StringRequest(Method.POST, ApiParams.API_EXIST,
					responseListener(), errorListener()) {
				protected Map<String, String> getParams() {
					return new ApiParams().with("deviceId", sheBeiId).with("password", sheBeipw);
				}
			});
			break;
		case R.id.back:
			finish();
			break;
		case R.id.tv_add:
			mEditText.setVisibility(View.VISIBLE);
			mEditPwText.setVisibility(View.VISIBLE);
			mAddTv.setVisibility(View.GONE);
			mCommitBtn.setVisibility(View.VISIBLE);
			mEditText.requestFocus();
			break;
		case R.id.home:
			Intent intent = new Intent(ShebeiguanliActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sebeiguanli:
			break;
		default:
			break;
		}
	}

	public void onCreateDialog() {
		dialog.setTitle("处理中");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setIndeterminate(true);
		dialog.setMessage("请稍后~");
		dialog.show();
	}
	
	private Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				dialog.dismiss();
				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						boolean exist = object.getJSONObject("result").getBoolean("exist");
						if (exist) {
							String shebeiString = sp.getString(SHEBEI_LIST, "");
							String id = mEditText.getText().toString();
							final String curId = mEditText.getText().toString();
							final String pw = mEditPwText.getText().toString();
							
							mEditText.setText("");
							mEditPwText.setText("");
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
							
							for (String s : mList) {
								if (s.equals(id)) {
									Toast.makeText(ShebeiguanliActivity.this, "该设备已添加.请勿重复添加", Toast.LENGTH_LONG).show();
									return;
								}
							}
							
							DeviceInfo.setCurrentId(id);
							
							if (shebeiString.trim().length() > 0) {
								id += "&";
							}
							sp.edit().putString(PW_ID + shebeiString.trim(), pw);
							shebeiString = id + shebeiString;
							sp.edit().putString(ShebeiguanliActivity.SHEBEI_LIST, shebeiString).commit();
							
							upDatedate();
							executeRequest(new StringRequest(Method.POST, ApiParams.API_INFO,
									inforesponseListener(), errorListener()) {
								protected Map<String, String> getParams() {
									return new ApiParams().with("password", pw).with("deviceId", curId);
								}
							});
							
						} else {
							Toast.makeText(ShebeiguanliActivity.this, "该设备不存在", Toast.LENGTH_LONG).show();
						}
					} else {
					}
				} catch (JSONException e) {
					dialog.dismiss();
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
							Toast.makeText(ShebeiguanliActivity.this, object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject deviceInfo = result
									.getJSONObject("deviceInfo");
							DeviceInfo.parse(deviceInfo);
							
							DeviceInfo.setCurrentId(DeviceInfo.deviceInfo.id);
							DeviceInfo.setCurrentPw(DeviceInfo.deviceInfo.pw);
							mCurrentTv.setText("当前设备：" + DeviceInfo.curDeviceID);
							mAdapter.notifyDataSetChanged();
							DeviceInfo.deviceInfo.setIsOnline(result.has("online") ? result.getBoolean("online") : false);
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

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(ShebeiguanliActivity.this, error.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
}