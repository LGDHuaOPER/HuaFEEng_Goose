package com.eoulu.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import me.hupeng.ipLocationService.IpLocationResult;
import me.hupeng.ipLocationService.IpLocationService;

public class IPLocationUtil {

	private final static String Key = "WURBZ-4R6WD-Z4J4B-HIBX6-PC5LV-7MBBE";
	private final static String ReqUrl = "http://apis.map.qq.com/ws/location/v1/ip";

	public static String getUrl(String ip) {

		return ReqUrl + "?ip=" + ip + "&key=" + Key + "&output=json";
	}

	public static JSONObject getResult(String url) {
		String result = new URLUtil().getHttp(url);
		JSONObject obj = JSONObject.parseObject(result);
		return obj;
	}

	 /** 
	   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	   * 
	   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	   * 
	   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	   * 192.168.1.100 
	   * 
	   * 用户真实IP为： 192.168.1.110 
	   * 
	   * @param request 
	   * @return 
	   */
	  public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getRemoteAddr(); 
	    } 
	    int index = ip.indexOf(",");
	    if(index != -1){
	    	ip = ip.substring(0, index);
	    }
	    if(ip.equals("0:0:0:0:0:0:0:1")){
	    	try {
				ip=InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	    }
	    return ip; 
	  } 
	
	  public void getIPAndCity(HttpServletRequest request){
			IPLocationUtil util = new IPLocationUtil();
			
			String ip = util.getIpAddress(request);
			request.getSession().setAttribute("IP", ip);
		//	IpLocationService ipLocationService = new IpLocationService();
		//	IpLocationResult ipLocationResult = ipLocationService.getIpLocationResult(util.getIpAddress(request));
		//	String city =  ipLocationResult.getCity()==null?"本地":ipLocationResult.getCity();
			String city = "--";
			/*
			try {
				city = getAddress("ip="+ip, "utf-8");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			request.getSession().setAttribute("City", city);
			
	  }
	  public static String getAddress(String content, String encodingString)
				throws UnsupportedEncodingException {
			// 这里调用淘宝API
			String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
			// 从http://whois.pconline.com.cn取得IP所在的省市区信息
			String returnStr = getResult(urlStr, content, encodingString);
		
			JSONObject json = null;
			if(returnStr != null){  
	              
	            json = JSONObject.parseObject(returnStr);  
	            JSONObject obj =  (JSONObject) json.get("data");
	        
	            if("0".equals(json.get("code").toString())){  
	            	return (String) obj.get("city");
	            }
			}
			return "--";
		}
	  
	  private static String getResult(String urlStr, String content, String encoding) {
			URL url = null;
			HttpURLConnection connection = null;
			try {
				url = new URL(urlStr);
				connection = (HttpURLConnection) url.openConnection();// 新建连接实例
				connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
				connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
				connection.setDoOutput(true);// 是否打开输出流 true|false
				connection.setDoInput(true);// 是否打开输入流true|false
				connection.setRequestMethod("POST");// 提交方法POST|GET
				connection.setUseCaches(false);// 是否缓存true|false
				connection.connect();// 打开连接端口
				DataOutputStream out = new DataOutputStream(connection
						.getOutputStream());// 打开输出流往对端服务器写数据
				out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
				out.flush();// 刷新
				out.close();// 关闭输出流
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
				// ,以BufferedReader流来读取
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();
				return buffer.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.disconnect();// 关闭连接
				}
			}
			return null;
		}


	  
	public static void main(String[] args) {
		 //System.out.println(getResult(getUrl("192.168.3.100")));
		IpLocationService ipLocationService = new IpLocationService();
		IpLocationResult ipLocationResult = ipLocationService.getIpLocationResult("183.175.12.160");
		System.out.println(ipLocationResult.getCountry() + " -----" + ipLocationResult.getProvince() + "--"
				+ ipLocationResult.getCity());
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String date = df.format(new Date());
		System.out.println(date);
	}
}
