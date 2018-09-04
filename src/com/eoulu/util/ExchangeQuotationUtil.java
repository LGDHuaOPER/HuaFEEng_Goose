package com.eoulu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;

public class ExchangeQuotationUtil {

	private final static String reqUrl = "http://api.k780.com";

	private final static String appkey = "30373";
	private final static String sign = "e45a793d6a02c95364ee5f9ed077c344";
	private final static String format = "json";
	

	public static String getURL(String curno,String date){
		return reqUrl+"?app=finance.rate_history&curno="+curno+"&date="+date+"&appkey="+appkey+"&sign="+sign+"&format="+format;
	}
	
	public static com.alibaba.fastjson.JSONObject getReturn(String url) {
		URLUtil util = new URLUtil();
		String result = util.getResponse(url);
		com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result);

		return obj;
	}
	
	public static void main(String[] args) {
		String curno = "CNY";
//		String url = getURL(curno,"2017-12-12");
//		com.alibaba.fastjson.JSONObject obj = getReturn(url);
//		System.out.println(obj);
		try {
			System.out.println("本机的IP = " + InetAddress.getLocalHost());
//			 CityResponse response = reader.city(ipAddress);  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(new Date()));
		System.out.println("today:"+df.format(Calendar.getInstance().getTime()));
		System.out.println(df.format(System.currentTimeMillis()));
		
		
		try
		  {
		    String strIP = "192.168.3.100";
		    URL url = new URL( "http://ip.qq.com/cgi-bin/searchip?searchip1=" + strIP); 
		    URLConnection conn = url.openConnection(); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK")); 
		    String line = null; 
		    StringBuffer result = new StringBuffer(); 
		    while((line = reader.readLine()) != null)
		    { 
		      result.append(line); 
		    } 
		    reader.close(); 
		    System.out.println(result);
		    strIP = result.substring(result.indexOf( "该IP所在地为：" ));
		    strIP = strIP.substring(strIP.indexOf( "：") + 1);
		    String province = strIP.substring(6, strIP.indexOf("省"));
		    String city = strIP.substring(strIP.indexOf("省") + 1, strIP.indexOf("市"));
		    
		  }
		  catch( IOException e)
		  { 
		  }
	}
}
