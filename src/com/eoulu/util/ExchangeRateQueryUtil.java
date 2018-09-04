package com.eoulu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

public class ExchangeRateQueryUtil {

	private final String reqUrl = "http://openapi.boc.cn/foreigner/query_exchange_rates";

	private final static String appkey = "30373";
	private final static String sign = "e45a793d6a02c95364ee5f9ed077c344";
	private final static String format = "json";

	/*
	 * 报文
	 * 
	 * clentid :第三方应用编号 最大长度18 userid ：第三方用户ID 最大长度18 acton ：访问令牌 最大长度256
	 * 第三方应用注销传刷新令牌 chnflg ：渠道标识 最大长度1 1-移动终端 2-PC终端 trandt ：交易日期 最大长度8 yyyymmdd
	 * trantm ：交易时间 最大长度6 hhmmss
	 */

	/*
	 * 接口上送字段
	 * 
	 * 字段名 描述 类型 最大长度 是否必输 是否循环 备注 userid 用户ID String 50 Y N 必输 accrem 唯一标识
	 * String 16 Y N 必输 term 终端标识 String 6 Y N 必输 channel 渠道标识 String 1 Y N 必输
	 * C----Counter柜台 P----Phone电话(与Call Center同) N----Net网银
	 * T----SelfTerminal自助终端 A----Client Application客户端 O-中银开放平台 jobtype 账户类型
	 * String 2 N N SY-活一本交易查询; FD-定一本交易查询; CD-借记卡交易查询
	 * CS-现金交易查询或者交易行汇率查询活一本、定一本、持卡交易过程中的汇率查询，需要填写。电话银行发起的查询或者直接查询交易行的汇率可以填写CS.
	 * actseq 多功能借记 Integer 3 N N 当账户类型为借记卡CD、渠道标识为柜台C时，该栏位必须有值 卡关联账户序号 acttype
	 * 卡关联账户类型 String 2 N N SY-活一本; FD-定一本; 当账户类型为借记卡CD、渠道标识为柜台C时，该栏位必须有值.
	 * 当账户类型为借记卡CD、渠道标识为电话P或者自助终端T时，该栏位必须有值； sellcuu 客户卖出币别 Integer 3 N N buycuu
	 * 客户买入币别 Integer 3 N N
	 * 
	 */

	public String getQuery(String userid, String accrem, String term, String channel, String jobtype, String actseq,
			String acttype, String sellcuu, String buycuu, String clentid, String acton, String trandt, String trantm,
			String chnflg) {// 调用接口查询
		JSONObject obj = getJSONObject(userid, accrem, term, channel, jobtype, actseq, acttype, sellcuu, buycuu);
		String content = obj.toString();
		return new URLUtil().getResponse(reqUrl, content, clentid, userid, acton, chnflg, trandt, trantm);
	}

	public static JSONObject getJSONObject(String userid, String accrem, String term, String channel, String jobtype,
			String actseq, String acttype, String sellcuu, String buycuu) {// 接口上送参数

		Map<String, String> map = new HashMap<>();
		map.put("userid", userid);
		map.put("accrem", accrem);
		map.put("term", term);
		map.put("channel", channel);
		map.put("jobtype", jobtype);
		map.put("actseq", actseq);
		map.put("acttype", acttype);
		map.put("sellcuu", sellcuu);
		map.put("buycuu", buycuu);
		JSONObject obj = JSONObject.fromObject(map);

		return obj;
	}

	

	/**
	 * 
	 * @param scur
	 *            必须 原币种编号 如:USD 所有支持币种
	 * @param tcur
	 *            必须 目标币种编号 如:CNY 所有支持币种
	 * @param appkey
	 *            必须 使用API的唯一凭证
	 * @param sign
	 *            必须 md5后的32位密文,登陆用
	 * @param format
	 *            不 返回数据格式
	 * @param jsoncallback
	 *            不 js跨域使用jsonp时可使用此参数
	 * @return
	 */

	public static String getURL(String scur, String tcur) {

		return "http://api.k780.com?app=finance.rate&scur=" + scur + "&tcur=" + tcur + "&appkey=" + appkey + "&sign=" + sign
				+ "&format=" + format;
	}

	public static com.alibaba.fastjson.JSONObject getReturn(String url) {
		URLUtil util = new URLUtil();
		String result = util.getResponse(url);
		com.alibaba.fastjson.JSONObject obj = JSON.parseObject(result);

		return obj;
	}

	public static String getResult(com.alibaba.fastjson.JSONObject obj) {
		String success = obj.get("success").toString();
		String message = "";
		if (success.equals("1")) {
			JSONObject temp = (JSONObject) obj.get("result");
			message = temp.getString("rate");
		} else {
			message = obj.get("msg").toString();
			System.out.println("接口异常："+message);
			message = "接口异常！";
		}

		return message;
	}

	public static void main(String[] args) {
//		JSONObject obj = getJSONObject("123", "123", "123", "123", "123", "123", "123", "123", "123");
//		String str = obj.toString();
//		System.out.println(str);
		String URL= getURL("USD","CNY");
		System.out.println(URL);
		com.alibaba.fastjson.JSONObject obj = getReturn(URL);
//		String result = getResult(obj);
		System.out.println("test:"+obj);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
//		String key = df.format(new Date());
//		System.out.println(key);
	}
	
}
