package com.example.jinghuaqi;


public class CgiResponse {
	public int ret;
	public String data;
	public String msg;
	
	public String toString() {
		return "ret=" + ret + ";data=" + data.toString() + ";msg=" + msg;
	}
}
