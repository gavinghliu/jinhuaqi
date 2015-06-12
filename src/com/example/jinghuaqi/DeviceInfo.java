package com.example.jinghuaqi;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.util.Log;

public class DeviceInfo {
	
	public static String cURRENT_DEVICE = "current_device";
	public static String cURRENT_DEVICE_PW = "current_password";
	
	public static String Version = "0.1";
	
	public String id; // 设备的唯一码
	public String pw;
	public String PM2_5; // PM2.5
	public int light; // 光照度
	public double temperature;// 温度
	public double CO2;// 二氧化碳浓度
	public int smell;// 气味
	public double anion; // 负离子浓度
	public double humidity;// 空气湿度
	
	public boolean esp;
	public int purifyProgress;

	public boolean powerSwitch; // 电源开关
	public boolean anionSwitch; // 负离子开关
	public boolean uvSwitch; // UV灯开关
//	public boolean highPressureLampSwitch;// 高压灯开关
	public boolean humidifierSwitch;// 加湿开关
	public boolean windSwitch;// 摆风开关
	public boolean musicSwitch;// 音乐开关
	public boolean childLockSwitch;// 儿童锁
	public int ambientLightMode;// 氛围灯模式，0=关闭 1=手动模式2=环境质量模式3=音乐模式
	public int ambientLightStaticColor;// 氛围灯颜色值，R:0-255 G:0-255 B:0-255
	public int ambientLightBrightness;// 氛围灯亮度，1-15级
	public int ambientLightDynamicLevel;// 氛围灯动态1-7，1=三色跳,2=7色跳，3=三色渐变，4=7色渐变
	public int ambientLightSpeed;// 氛围灯速度，1-15级
	public int clockShowMode;// 时钟显示模式，0=时钟，1=定时开，2=定时关，3=药水雾化，4=运行时间
	public int windSpeed;// 风速度，0=自动凤，1-5档风速
	public int voiceLevel;// 音量，0=关闭1-4档音量
	public int healthManagerMode;// 健康管理模式，1=呼吸保健，2=预防感冒，3=鼻/咽炎，4=皮肤过敏，5=失眠，6=药水雾化，7=空气消毒，8=驱蚊防虫。
	public long lastUpdateTime;// 设备状态最后更新的日期
	public int managerMode;   //模式管理，0=极速，1=自动，2=睡眠，3=花粉，4=情景，5=自定义
	public boolean isOnline;
	public boolean decorativeLightSwitch;
	public int humidifyLevel;
	public int motorPower;
	
	public int systemTimeHour;
	public int systemTimeMin;
	public int bootTimeHour;
	public int bootTimeMin;
	public int shutdownHour;
	public int shutdownMin;
	public int atomizeTimeMin;
	public int atomizeTimeSec;
	
	
	public static DeviceInfo deviceInfo;
	public static String curDeviceID = "";
	public static String curPassword = "00000000";
	
	public static Context context;
	
	public static DeviceInfo parse(JSONObject jsonObject) {
		DeviceInfo deviceInfo = new DeviceInfo();
		try {
			deviceInfo.id = jsonObject.getString("id");
			deviceInfo.pw = jsonObject.getString("password");
			deviceInfo.light = jsonObject.getInt("light");
			deviceInfo.temperature = jsonObject.getDouble("temperature");
			deviceInfo.smell = jsonObject.getInt("smell");
			deviceInfo.anion = jsonObject.getDouble("anion");
			deviceInfo.humidity = jsonObject.getDouble("humidity");
			deviceInfo.powerSwitch = jsonObject.getBoolean("powerSwitch");
			deviceInfo.anionSwitch = jsonObject.getBoolean("anionSwitch");
			deviceInfo.uvSwitch =  jsonObject.getBoolean("uvSwitch");
//			deviceInfo.highPressureLampSwitch =  jsonObject.getBoolean("highPressureLampSwitch");
			deviceInfo.humidifierSwitch =  jsonObject.getBoolean("humidifierSwitch");
			deviceInfo.windSwitch =  jsonObject.getBoolean("windSwitch");
			deviceInfo.musicSwitch =  jsonObject.getBoolean("musicSwitch");
			deviceInfo.childLockSwitch =  jsonObject.getBoolean("childLockSwitch");
			deviceInfo.ambientLightMode = jsonObject.getInt("ambientLightMode");
			deviceInfo.ambientLightStaticColor = jsonObject.getInt("ambientLightStaticColor");
			deviceInfo.ambientLightBrightness = jsonObject.getInt("ambientLightBrightness");
			deviceInfo.ambientLightDynamicLevel = jsonObject.getInt("ambientLightDynamicLevel");
			deviceInfo.ambientLightSpeed = jsonObject.getInt("ambientLightSpeed");
			deviceInfo.clockShowMode = jsonObject.getInt("clockShowMode");
			deviceInfo.windSpeed = jsonObject.getInt("windSpeed");
			deviceInfo.voiceLevel = jsonObject.getInt("voiceLevel");
			deviceInfo.healthManagerMode = jsonObject.getInt("healthManagerMode");
			deviceInfo.managerMode = jsonObject.getInt("managerMode");
			deviceInfo.purifyProgress = jsonObject.getInt("purifyProgress");
			deviceInfo.decorativeLightSwitch = jsonObject.getBoolean("decorativeLightSwitch");
			deviceInfo.humidifyLevel = jsonObject.getInt("humidifyLevel");
			deviceInfo.motorPower = jsonObject.getInt("motorPower");
			
			deviceInfo.systemTimeHour = jsonObject.getInt("systemTimeHour");
			deviceInfo.systemTimeMin = jsonObject.getInt("systemTimeMin");
			deviceInfo.bootTimeHour = jsonObject.getInt("bootTimeHour");
			deviceInfo.bootTimeMin = jsonObject.getInt("bootTimeMin");
			deviceInfo.shutdownHour = jsonObject.getInt("shutdownTimeHour");
			deviceInfo.shutdownMin = jsonObject.getInt("shutdownTimeMin");
			deviceInfo.atomizeTimeMin = jsonObject.getInt("atomizeTimeMin");
			deviceInfo.atomizeTimeSec = jsonObject.getInt("atomizeTimeSec");
			
			deviceInfo.esp =  jsonObject.getBoolean("espSwitch");
			deviceInfo.CO2 = jsonObject.getDouble("co2");
			double pm = jsonObject.getDouble("pm2_5");;
			java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
			deviceInfo.PM2_5 = df.format(pm);
			deviceInfo.lastUpdateTime = jsonObject.getLong("lastUpdateTime");
			Log.d("jinghuaqi", "deviceInfo.lastUpdateTime=" + deviceInfo.lastUpdateTime);
			DeviceInfo.deviceInfo = deviceInfo;
			Log.d("jinghuaqi", "DeviceInfo.deviceInfo .lastUpdateTime=" + DeviceInfo.deviceInfo.lastUpdateTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return deviceInfo;
	}
	
	public static void setCurrentId(String currentId) {
		SharedPreferences sp = context.getSharedPreferences(ShebeiguanliActivity.SP_NAME, context.MODE_PRIVATE);
		sp.edit().putString(DeviceInfo.cURRENT_DEVICE, currentId).commit();
		DeviceInfo.curDeviceID = currentId;
	}
	
	public static void setCurrentPw(String pw) {
		SharedPreferences sp = context.getSharedPreferences(ShebeiguanliActivity.SP_NAME, context.MODE_PRIVATE);
		sp.edit().putString(DeviceInfo.cURRENT_DEVICE_PW, pw).commit();
		DeviceInfo.curPassword = pw;
	}
	
	public void setIsOnline(boolean l) {
		isOnline = l;
	}
	
	
	public static boolean needUpdate(String v) {
		String sp = v.substring(0, v.length() -2 );
		return Float.parseFloat(sp) > Float.parseFloat(Version);
	}
}
