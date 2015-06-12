package com.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.down.DownloadFileListener;

public class DownloadUtil {
	public static boolean download(String urlStr,String filePath,DownloadFileListener downloadFileListener){
		int contentLength = 0 ;
		try{
		System.out.println("filePath="+filePath);
		URL url = new URL(urlStr);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setConnectTimeout(5000);
		httpURLConnection.setRequestMethod("GET");
			contentLength = httpURLConnection.getContentLength();
			File file = new File(filePath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
			randomAccessFile.setLength(contentLength);
				InputStream inputStream = httpURLConnection.getInputStream();
				int currentNumber = 0;
				byte[]buf = new byte[1024];
				int length = 0;
				while ((length=inputStream.read(buf))!=-1) {
					randomAccessFile.write(buf,0,length);
					currentNumber+=length;
					downloadFileListener.downLoadNotify(currentNumber);
				}
				inputStream.close();
				randomAccessFile.close();}
		catch (Exception e) {
			e.printStackTrace();
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
			return false;
		}
				return true;
	}
}
