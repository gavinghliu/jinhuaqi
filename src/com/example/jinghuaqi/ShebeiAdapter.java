package com.example.jinghuaqi;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShebeiAdapter extends BaseAdapter{

	List<String> mlist;
	Context mContext;
	Handler mHandler;
	static AlertDialog dlg;
	public static ProgressDialog dialog;
	SharedPreferences sp;
	
	public ShebeiAdapter(Context context, List<String> list, Handler handler) {
		mlist = list;
		mContext = context;
		mHandler = handler;
		dlg = new AlertDialog.Builder(mContext).create();
		dlg.setCanceledOnTouchOutside(false);
		dialog = new ProgressDialog(mContext);
		sp = context.getSharedPreferences(ShebeiguanliActivity.SP_NAME, context.MODE_PRIVATE);
	}
	
	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setList(List<String> list) {
		mlist = list;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		ViewHoder viewHoder = null;
		if (null == view) {
			viewHoder = new ViewHoder();
			view = View.inflate(mContext , R.layout.shebei_item, null);
			viewHoder.mPostion = (TextView)view.findViewById(R.id.text_id);
			viewHoder.mName = (TextView)view.findViewById(R.id.text_name);
			viewHoder.mDelBtn = (Button)view.findViewById(R.id.del_btn);
			viewHoder.mCpwTextView = (Button)view.findViewById(R.id.changpw);
			view.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder)view.getTag();
		}
		
		final String name = mlist.get(position);
		 
		viewHoder.mPostion.setText(position + 1 + ".");
		viewHoder.mName.setText(name);
		viewHoder.mCpwTextView.setVisibility(View.GONE);
		if (name.equals(DeviceInfo.curDeviceID)) {
			viewHoder.mCpwTextView.setVisibility(View.VISIBLE);
		}
		viewHoder.mDelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				SharedPreferences sp = mContext.getSharedPreferences(ShebeiguanliActivity.SP_NAME, ShebeiguanliActivity.MODE_PRIVATE);
				
				for (int i = 0; i < mlist.size(); i++) {
					if (mlist.get(i).trim().equals(name.trim())) {
						mlist.remove(i);
						break;
					}
				}
				
				String shebeiString = "";
				for (int i = 0; i < mlist.size(); i++) {
					if (shebeiString.trim().length() > 0) {
						shebeiString += "&";
					}
					shebeiString += mlist.get(i);
				}
				
				sp.edit().putString(ShebeiguanliActivity.SHEBEI_LIST, shebeiString).commit();
				notifyDataSetChanged();
			}
		});
		
		viewHoder.mCpwTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				dlg.show();
				dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				Window window = dlg.getWindow();
				Window dialogWindow = dlg.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.CENTER);
				dialogWindow.setAttributes(lp);
				window.setContentView(R.layout.changepw_dialog);
				
				ImageButton ivButton = (ImageButton) dlg.findViewById(R.id.dialog_close);
				final EditText pw1 = (EditText) dlg.findViewById(R.id.et_pw);
				final EditText pw2 = (EditText) dlg.findViewById(R.id.et_pw2);
				Button commitBtn = (Button) dlg.findViewById(R.id.dialog_ok);
				
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
						if (!pw1.getText().toString().trim().equals(pw2.getText().toString().trim())) {
							Toast.makeText(mContext, "两次输入不一致", Toast.LENGTH_SHORT).show();
							return;
						}
						
						
						onCreateDialog();
						//deviceId=12345&password=88888888&newPassword=12345678
						executeRequest(new StringRequest(Method.POST, ApiParams.PWD_CHANGE,
								responseListener(), errorListener()) {
							protected Map<String, String> getParams() {
								return new ApiParams().with("password", DeviceInfo.curPassword).with("deviceId", DeviceInfo.curDeviceID).with("newPassword", pw2.getText().toString().trim());
							}
						});
					}
				});
				
			}
		});
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
//				dlg.show();
//				dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//				Window window = dlg.getWindow();
//				Window dialogWindow = dlg.getWindow();
//				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//				dialogWindow.setGravity(Gravity.CENTER);
//				dialogWindow.setAttributes(lp);
//				window.setContentView(R.layout.changesb_dialog);
//				
//				ImageButton ivButton = (ImageButton) dlg.findViewById(R.id.dialog_close);
//				final EditText pw1 = (EditText) dlg.findViewById(R.id.et_pw);
//				Button commitBtn = (Button) dlg.findViewById(R.id.dialog_ok);
//				
//				ivButton.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						if (dlg.isShowing()) {
//							dlg.dismiss();
//						}
//					}
//				});
//				
//				commitBtn.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						if (pw1.getText().toString().trim().length() == 0) {
//							Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
//							return;
//						}
//						
//						Message msg = mHandler.obtainMessage();
//						msg.obj = name;
//						Intent data = new Intent();
//						data.putExtra("pw", pw1.getText().toString());
//						msg.setData(data.getExtras());
//						mHandler.sendMessage(msg);
//						dlg.dismiss();
//					}
//				});
				Message msg = mHandler.obtainMessage();
				msg.obj = name;
				Intent data = new Intent();
				data.putExtra("pw", sp.getString("", DeviceInfo.curPassword));
				msg.setData(data.getExtras());
				mHandler.sendMessage(msg);
			}
		});
		
		return view;
	}

	public class ViewHoder {
		TextView mPostion;
		TextView mName;
		Button mDelBtn;
		Button mCpwTextView;
	}
	
	protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}
	
	private Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("jinghuaqi", "response=" + response);
				EditText pwView = (EditText) dlg.findViewById(R.id.et_pw2);
				String pw = pwView.getText().toString();
				dialog.dismiss();
				JSONObject object
				;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("resultCode");
					if (200 == resultCode) {
						dlg.dismiss();
						boolean success = object.getJSONObject("result").getBoolean("success");
						if (success) {
							DeviceInfo.setCurrentPw(pw);
							sp.edit().putString(ShebeiguanliActivity.PW_ID +  DeviceInfo.curDeviceID.trim(), pw);
							
							Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(mContext, object.getString("errorMsg"), Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(mContext, "修改失败", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					dialog.dismiss();
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
				Toast.makeText(mContext, error.getMessage(),
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
}
