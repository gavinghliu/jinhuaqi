package com.down;

import java.io.File;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.StaticLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jinghuaqi.R;
import com.util.DownloadUtil;

public class DownLoadDemoActivity extends Activity implements OnClickListener{
	static public String FILE_PATH = "filePath";
	static public String FILE_URL = "fileUrl";
	
    private Button download_btn;//下载
    private Button download_ok;//确定并返回
    private Button download_again;//重新下载
    private TextView downfileName;//名字显示
    private TextView downProgress;//当前下载大小显示
    private TextView downRatio;//百分比显示
    private TextView downsuccess;//下载成功或失败显示
    private TextView downFileSize;//成功后文件大小
    private ProgressBar downProgressBar;//下载的进度条
    public static final int DOWN_LOADING = 1;//正在下载
    public static final int DOWN_FAILD = 2;//下载失败
    public static final int DOWN_SUCCESS = 3;//下载成功
    DownLoadItem downLoadFileItem;
    String filePathPhoto;
    String url;
    String fileName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_down_item);
        filePathPhoto = getIntent().getExtras().getString(FILE_PATH);
        url = getIntent().getExtras().getString(FILE_URL);
        init_views();
        init_data();
    }
    private void init_data(){
    	downLoadFileItem=new DownLoadItem();
    	downLoadFileItem.setDownFileUrl(url);
    	fileName=getFileNameByUrl(downLoadFileItem.getDownFileUrl());
    	downLoadFileItem.setDownFileName("空气养生仪新版本下载");
    	downLoadFileItem.setFileSdPath(filePathPhoto+fileName);
    	downLoadFileItem.setTemp_downMaxValues((int)(1*1024*1024));
    	downfileName.setText(downLoadFileItem.getDownFileName());
    }
    private void init_views(){
    	 download_btn=(Button)findViewById(R.id.download_main_all);
         download_btn.setOnClickListener(this);
         download_ok=(Button)findViewById(R.id.download_main_ok);
         download_ok.setOnClickListener(this);
         download_again=(Button)findViewById(R.id.download_main_again);
         download_again.setOnClickListener(this);
         
         downfileName=(TextView)findViewById(R.id.download_main_fileName);
         downProgress=(TextView)findViewById(R.id.download_main_tvProgress);
         downRatio=(TextView)findViewById(R.id.download_main_tvRatio);
         downsuccess=(TextView)findViewById(R.id.download_main_success);
         downFileSize=(TextView)findViewById(R.id.download_main_fileSize);
         downProgressBar=(ProgressBar)findViewById(R.id.download_main_progressBarlist);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.download_main_all://	确认下载
			new DownLoadThread().start();
			break;
		case R.id.download_main_ok://确认并返回
			
			break;
		case R.id.download_main_again://重新上传
			new DownLoadThread().start();
			break;

		default:
			break;
		}
	}
	
	class DownLoadThread extends Thread{
		@Override
		public void run() {
			downLoadFileItem.setTemp_downLoading(true);
			Message message=myHandler.obtainMessage();
			message.what=DOWN_LOADING;
			message.obj=downLoadFileItem;
			message.sendToTarget();
			UpdateProgress updateProgress=new UpdateProgress(downLoadFileItem);
			boolean is_success=DownloadUtil.download(downLoadFileItem.getDownFileUrl(), 
					downLoadFileItem.getFileSdPath(), updateProgress);
			if(is_success){
				downLoadFileItem.setTemp_downLoading(false);
				downLoadFileItem.setTemp_downSuccess(true);
				Message message2=myHandler.obtainMessage();
				message2.what=DOWN_SUCCESS;
				message2.sendToTarget();
				
			}else{
				downLoadFileItem.setTemp_downLoading(false);
				downLoadFileItem.setTemp_downSuccess(false);
				Message message2=myHandler.obtainMessage();
				message2.what=DOWN_FAILD;
				message2.sendToTarget();
			}
		}
	}
	
	/**
	 * 用于更新进度条和更新界面显示的Handler
	 */
	Handler myHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_LOADING:
				DownLoadItem downLoadItem=(DownLoadItem)msg.obj;
				downLoading(downLoadItem);
				break;
			case DOWN_FAILD:
				downLoadFailed();
				break;
			case DOWN_SUCCESS:
				downLoadSuccess();
				break;
			default:
				break;
			}
			
		}
	};
	/**
	 * 正在下载的界面显示
	 */
	private void downLoading(DownLoadItem downLoadItem){
		downProgressBar.setVisibility(View.VISIBLE);
		downProgress.setVisibility(View.VISIBLE);
		downRatio.setVisibility(View.VISIBLE);
		downsuccess.setVisibility(View.GONE);
		downFileSize.setVisibility(View.GONE);
		download_again.setVisibility(View.GONE);
		downProgressBar.setMax((int)downLoadItem.getTemp_downMaxValues());
		downProgressBar.setProgress((int)downLoadItem.getTemp_downProgress());
		downProgress.setText(downLoadItem.getTemp_downProgress()/1024+"KB"+"/"+
		downLoadItem.getTemp_downMaxValues()/1024+"KB");
		//上传的百分比
    	String strRatio=percent(downProgressBar.getProgress(), downLoadItem.getTemp_downMaxValues());
    	if(!strRatio.contains("%"))
    		downRatio.setText("0%");
    	else
    		downRatio.setText(strRatio);
	}
	/**
	 * 下载成功的界面显示
	 */
	private void downLoadSuccess(){
		downProgressBar.setVisibility(View.GONE);
		downProgress.setVisibility(View.GONE);
		downRatio.setVisibility(View.GONE);
		download_again.setVisibility(View.GONE);
		downsuccess.setVisibility(View.VISIBLE);
		downFileSize.setVisibility(View.VISIBLE);
		downsuccess.setText("下载成功!");
		downFileSize.setText("("+downLoadFileItem.getTemp_downMaxValues()/1024+"KB)");
		
		
		Intent apkIntent = new Intent(); 
        apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        apkIntent.setAction(android.content.Intent.ACTION_VIEW);
        
        apkIntent.setDataAndType(Uri.fromFile(new File(filePathPhoto+fileName) ), "application/vnd.android.package-archive");
        startActivity(apkIntent);
        finish();
	}
	/**
	 * 下载失败的界面显示
	 */
	private void downLoadFailed(){
		downProgressBar.setVisibility(View.GONE);
		downProgress.setVisibility(View.GONE);
		downRatio.setVisibility(View.GONE);
		downFileSize.setVisibility(View.GONE);
		downsuccess.setVisibility(View.VISIBLE);
		download_again.setVisibility(View.VISIBLE);
		downsuccess.setText("下载失败!");
	}
	class UpdateProgress extends DownloadFileListener{
		DownLoadItem downLoadItem;
		public UpdateProgress(DownLoadItem downLoadItem){
			this.downLoadItem=downLoadItem;
		}
		@Override
		public void downLoadNotify(long currentNumber) {
			super.downLoadNotify(currentNumber);
			downLoadItem.setTemp_downProgress(currentNumber);
			Message message=myHandler.obtainMessage();
			message.what=DOWN_LOADING;
			message.obj=downLoadItem;
			message.sendToTarget();
		}
	}
	private String percent(double d1, double d2) {
  	  String strRatio;
  	  double p3 = d1 / d2;
  	  NumberFormat nf = NumberFormat.getPercentInstance();
  	  nf.setMinimumFractionDigits(2);
  	  strRatio = nf.format(p3);
  	  return strRatio;
  	 }
	public String getFileNameByUrl(String downFileUrl){
		int index = downFileUrl.lastIndexOf("/");
		String fileName=downFileUrl.substring(index+1);
		return fileName;
	}
}