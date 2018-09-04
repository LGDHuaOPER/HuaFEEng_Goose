package com.eoulu.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TransportToolsUtil {

	public final static String reqUrl = "http://apis.juhe.cn/train/s2swithprice";
	public final static String key = "5fc8484a6c594262d9a3e9a0edbbaf81";
	

	public String getUrl(String start, String end,String date) {

		return reqUrl + "?start=" + start + "&end=" + end + "&traintype=json&key=" + key+"&date="+date;
	}

	public String getResult(String url) {
		URLUtil util = new URLUtil();
		String str = util.getHttp(url);
		String result = "";
		JSONObject obj =  JSONObject.fromObject(str);
		if(obj.getInt("error_code")==0){
			JSONObject list = obj.getJSONObject("result");
			JSONArray arr = list.getJSONArray("list");
			
			for(int i=0; i<arr.size();i++){
				if(result.equals("")){
					result +=arr.getJSONObject(i).getString("train_no");
				}else{
					result += ","+arr.getJSONObject(i).getString("train_no");
				}
			}
		}else{
			result = obj.getString("reason");
		}
		
		return result;
	}

	public static void main(String[] args) {
		String from = "苏州";
		String to = "上海";
		TransportToolsUtil a = new TransportToolsUtil();
		String url = a.getUrl(from, to,"2017-12-28");
		System.out.println(a.getResult(url));
	}
	
	public static final String flightUrl = "http://op.juhe.cn/flight/sk";
	public static final String flightKey = "107f96e67dd62f9c6999a64539dc94b9";
	
	/*
	 * 	key	string	是	应用APPKEY(应用详细页查询)
 	dtype	string	否	返回数据的格式,xml或json，默认json
 	orgCity	string	是	始发城市的三字码
 	dstCity	string	是	到达城市的三字码
 	flightDate	Date	是	日期（年月日）
 	airline	string	否	航空公司的两字码或者"ALL"或者""
 	direct	boolean	是	直航/转飞
 	nostop	boolean	是	是否经停
	 */
	public String getFlightUrl(String orgCity,String dstCity,String flightDate){
		
		return flightUrl+"?key="+flightKey+"&orgCity="+orgCity+"&dstCity="+dstCity+"&flightDate="+flightDate+"&airline=ALL"+"&direct=true&nostop=true";
	}
	
	public String getFlightResult(String url){
		URLUtil util = new URLUtil();
		String str = util.getHttp(url);
		String result = "";
		JSONObject obj =  JSONObject.fromObject(str);
		if(obj.getInt("resultcode")==200){
			JSONArray arr = obj.getJSONArray("result");
			
			for(int i=0; i<arr.size();i++){
				if(result.equals("")){
					result +=arr.getJSONObject(i).getString("flightNo");
				}else{
					result += ","+arr.getJSONObject(i).getString("flightNo");
				}
			}
		}else{
			result = obj.getString("reason");
		}
		
		return result;
	}
	
}
