package com.down;


public class DownLoadItem {
	private String downFileId;//暂时无用
	private String downFileName;//下载文件的名字
	private long temp_downProgress = 0;//当前的进度值
	private long temp_downMaxValues;//文件的大小
	private boolean temp_downLoading = false;//是否正在下载
	private boolean temp_downSuccess = false;//是否下载成功
	private String fileSdPath;//下载文件保存的本地路径
	private String downFileUrl;//文件的url地址
	
	public String getDownFileId() {
		return downFileId;
	}
	public void setDownFileId(String downFileId) {
		this.downFileId = downFileId;
	}
	public String getDownFileName() {
		return downFileName;
	}
	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}
	public long getTemp_downProgress() {
		return temp_downProgress;
	}
	public void setTemp_downProgress(long temp_downProgress) {
		this.temp_downProgress = temp_downProgress;
	}
	public long getTemp_downMaxValues() {
		return temp_downMaxValues;
	}
	public void setTemp_downMaxValues(long temp_downMaxValues) {
		this.temp_downMaxValues = temp_downMaxValues;
	}
	public boolean isTemp_downLoading() {
		return temp_downLoading;
	}
	public void setTemp_downLoading(boolean temp_downLoading) {
		this.temp_downLoading = temp_downLoading;
	}
	public boolean isTemp_downSuccess() {
		return temp_downSuccess;
	}
	public void setTemp_downSuccess(boolean temp_downSuccess) {
		this.temp_downSuccess = temp_downSuccess;
	}
	public String getFileSdPath() {
		return fileSdPath;
	}
	public void setFileSdPath(String fileSdPath) {
		this.fileSdPath = fileSdPath;
	}
	public String getDownFileUrl() {
		return downFileUrl;
	}
	public void setDownFileUrl(String downFileUrl) {
		this.downFileUrl = downFileUrl;
	}
	
}
