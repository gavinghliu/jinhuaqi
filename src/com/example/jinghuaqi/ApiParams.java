package com.example.jinghuaqi;

import java.util.HashMap;

public class ApiParams extends HashMap<String, String> {
	private static final long serialVersionUID = 8112047472727256876L;

	// API 定义
	public static String API_HOST = "http://54.65.148.83:8080/";

	// 查询设备是否存在
	public static String API_EXIST = API_HOST + "api/airpurifier/exist";

	// 查询设备状态信息
	// http://54.65.148.83:8080/api/airpurifier/info?deviceId=12345
	public static String API_INFO = API_HOST + "api/airpurifier/info";

	// 订阅设备状态信息
	// http://54.65.148.83:8080/api/airpurifier/subscribe?deviceId=12345
	// 当设备状态更新时，会返回设备状态，如果超时，则返回最新的结果。客户端要异步长轮循来实时获取设备的更新。
	public static String API_SUBCRIBE = API_HOST + "api/airpurifier/subscribe";

	// 控制设备状态
	// http://54.65.148.83:8080/api/airpurifier/controll?deviceId=12345&childLockSwitch=true
	/**
	 * 参数： 主要参数是deviceId，后面其它的参数对应修改哪个值，可以一次性修改多个值。比如：
	 * childLockSwitch=true，开启儿童锁。 windSpeed=3，设置风速为3级。
	 **/
	public static String API_CONTROLL = API_HOST + "api/airpurifier/control";
	
	public static String API_UPDATE = API_HOST + "resources/apk/airpurifier/version.json";

	// http://54.65.148.83:8080/api/airpurifier/updatePassword?deviceId=12345&password=88888888&newPassword=12345678
	public static String PWD_CHANGE = API_HOST
			+ "api/airpurifier/updatePassword";

	public ApiParams() {
		put("v", "1");
	}

	public ApiParams with(String key, String value) {
		put(key, value);
		return this;
	}

	public static void updatHost() {
		API_EXIST = API_HOST + "api/airpurifier/exist";
		API_INFO = API_HOST + "api/airpurifier/info";
		API_SUBCRIBE = API_HOST + "api/airpurifier/subscribe";
		API_CONTROLL = API_HOST + "api/airpurifier/control";
		PWD_CHANGE = API_HOST + "api/airpurifier/updatePassword";
		API_UPDATE = API_HOST + "resources/apk/airpurifier/version.json";
	}

}
