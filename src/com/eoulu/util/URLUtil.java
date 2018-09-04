package com.eoulu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class URLUtil {

	/*
	 * 返回字段
	 * 
	 * marketrate 汇率 Integer 9(05)V9(05) 10 N N
	 * 当帐户类型为“SY、FD、CD”时，该汇率为客户的账户开户省行的汇率。 ratetime 汇率时间 Integer 12 N N
	 * YYMMDDHHMMSS
	 * 
	 */

	public String getResponse(String reqUrl, String content, String clentid, String userid, String acton, String chnflg,
			String trandt, String trantm) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true); // 使用 URL 连接进行输出
			httpConn.setDoInput(true); // 使用 URL 连接进行输入
			httpConn.setUseCaches(false); // 忽略缓存
			httpConn.setRequestMethod("POST"); // 设置URL请求方法
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); // 解决乱码
			httpConn.setRequestProperty("clentid", clentid);
			httpConn.setRequestProperty("userid", userid);
			httpConn.setRequestProperty("acton", acton);
			httpConn.setRequestProperty("chnflg", chnflg);
			httpConn.setRequestProperty("trandt", trandt);
			httpConn.setRequestProperty("trantm", trantm);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.connect();
			OutputStream out = httpConn.getOutputStream();
			out.write(content.getBytes("UTF-8"));
			out.close();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String readLine;
			StringBuffer responseSb = new StringBuffer();
			while ((readLine = responseReader.readLine()) != null) {
				responseSb.append(readLine);
			}
			responseReader.close();
			httpConn.disconnect();
			return responseSb.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "ERROR";
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

	public String convert(String content) {
		if (content.equals("ERROR")) {
			content = "{\"error\":\"ERROR\"}";
		}

		String str = JSON.parse(content).toString();

		return str;
	}

	public String getResponse(String reqUrl) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true); // 使用 URL 连接进行输出
			httpConn.setDoInput(true); // 使用 URL 连接进行输入
			httpConn.setUseCaches(false); // 忽略缓存
			httpConn.setRequestMethod("POST"); // 设置URL请求方法
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); // 解决乱码
			httpConn.connect();
			OutputStream out = httpConn.getOutputStream();
			out.close();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String readLine;
			StringBuffer responseSb = new StringBuffer();
			while ((readLine = responseReader.readLine()) != null) {
				responseSb.append(readLine);
			}
			responseReader.close();
			httpConn.disconnect();
			return responseSb.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "ERROR";
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

	public String getHttp(String urlInfo) {
		  try {  
	            URL url = new URL(urlInfo);    // 把字符串转换为URL请求地址  
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
	            connection.connect();// 连接会话  
	            // 获取输入流  
	            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));  
	            String line;  
	            StringBuilder sb = new StringBuilder();  
	            while ((line = br.readLine()) != null) {// 循环读取流  
	                sb.append(line);  
	            }  
	            br.close();// 关闭流  
	            connection.disconnect();// 断开连接  
	            System.out.println(sb.toString());  
	            return sb.toString();
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            System.out.println("失败!");  
	            return "ERROR";
	        }  
		  
	}

}
