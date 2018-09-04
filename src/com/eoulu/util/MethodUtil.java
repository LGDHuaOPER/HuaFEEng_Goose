package com.eoulu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MethodUtil {

	public int[] StringtoInt(String str) {
		str = str.trim();
		String[] ss = str.split(",");
		int[] ret = new int[ss.length];

		for (int i = 0; i < ss.length; i++) {
			ret[i] = Integer.parseInt(ss[i]);
		}

		return ret;

	}

	// 将Unicode字符串转换成bool型数组
	public boolean[] StrToBool(String input) {
		boolean[] output = Binstr16ToBool(BinstrToBinstr16(StrToBinstr(input)));
		return output;
	}

	// 将bool型数组转换成Unicode字符串
	public String BoolToStr(boolean[] input) {
		String output = BinstrToStr(Binstr16ToBinstr(BoolToBinstr16(input)));
		return output;
	}

	// 将字符串转换成二进制字符串，以空格相隔
	private String StrToBinstr(String str) {
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]) + " ";
		}
		return result;
	}

	// 将二进制字符串转换成Unicode字符串
	public static String BinstrToStr(String binStr) {
		String[] tempStr = StrToStrArray(binStr);
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++) {
			tempChar[i] = BinstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
	}

	// 将二进制字符串格式化成全16位带空格的Binstr
	private String BinstrToBinstr16(String input) {
		StringBuffer output = new StringBuffer();
		String[] tempStr = StrToStrArray(input);
		for (int i = 0; i < tempStr.length; i++) {
			for (int j = 16 - tempStr[i].length(); j > 0; j--)
				output.append('0');
			output.append(tempStr[i] + " ");
		}
		return output.toString();
	}

	// 将全16位带空格的Binstr转化成去0前缀的带空格Binstr
	private String Binstr16ToBinstr(String input) {
		StringBuffer output = new StringBuffer();
		String[] tempStr = StrToStrArray(input);
		for (int i = 0; i < tempStr.length; i++) {
			for (int j = 0; j < 16; j++) {
				if (tempStr[i].charAt(j) == '1') {
					output.append(tempStr[i].substring(j) + " ");
					break;
				}
				if (j == 15 && tempStr[i].charAt(j) == '0')
					output.append("0" + " ");
			}
		}
		return output.toString();
	}

	// 二进制字串转化为boolean型数组 输入16位有空格的Binstr
	private boolean[] Binstr16ToBool(String input) {
		String[] tempStr = StrToStrArray(input);
		boolean[] output = new boolean[tempStr.length * 16];
		for (int i = 0, j = 0; i < input.length(); i++, j++)
			if (input.charAt(i) == '1')
				output[j] = true;
			else if (input.charAt(i) == '0')
				output[j] = false;
			else
				j--;
		return output;
	}

	// boolean型数组转化为二进制字串 返回带0前缀16位有空格的Binstr
	private String BoolToBinstr16(boolean[] input) {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < input.length; i++) {
			if (input[i])
				output.append('1');
			else
				output.append('0');
			if ((i + 1) % 16 == 0)
				output.append(' ');
		}
		output.append(' ');
		return output.toString();
	}

	// 将二进制字符串转换为char
	private static char BinstrToChar(String binStr) {
		int[] temp = BinstrToIntArray(binStr);
		int sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;
	}

	// 将初始二进制字符串转换成字符串数组，以空格相隔
	private static String[] StrToStrArray(String str) {
		return str.split(" ");
	}

	// 将二进制字符串转换成int数组
	private static int[] BinstrToIntArray(String binStr) {
		char[] temp = binStr.toCharArray();
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = temp[i] - 48;
		}
		return result;
	}

	public String getEmailSign(String content, String nick) {

		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head><META content='text/html; charset=gbk' http-equiv=Content-Type>");
		sb.append("<META name=GENERATOR content='MSHTML 8.00.6001.18702'>");
		sb.append("<style type='text/css'>");
		sb.append(".STYLE1 {color: #000000}");
		sb.append(
				"TABLE {FONT-SIZE: 12px; COLOR: #444444;LINE-HEIGHT: 14px; FONT-FAMILY: '宋体', 'Arial'; TEXT-DECORATION: none;}");
		sb.append(".STYLE3 { font-size: 13px;color: #FD9800; font-weight: bold;}");
		sb.append("</style>");
		sb.append("</head>");
		// 显示邮件内容
		sb.append("<body style='font-family:微软雅黑;font-size:14px;'>");
		if (nick == null || nick.equals("")) {
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
			sb.append(content);
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		} else if (nick.equals("NA")) {
			sb.append(content);
		} else {
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>" + nick + ",您好！</span><br><br>");
			sb.append(content);
			sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		}

		sb.append("<span style='color:#000080'>------------------------</span><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-size:18px;font-weight:800;'>Eoulu<span><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-size:13px;font-weight:800;'>苏州伊欧陆系统集成有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:13px;font-weight:800;'>Suzhou Eoulu System Integration Co., Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址: 苏州工业园区星湖街218号生物纳米园A7楼305室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 305, Building A7, No.218 Xinghu Road, Suzhou Industrial Park, Suzhou, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:215000  电话/Tel: +86-512-62757360  传真/Fax: +86-512-62757313<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>手机/MP: +86-18566664208 邮箱:remind@eoulu.com<span><br>");
		sb.append("<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>公司网址: <span>");
		sb.append(
				"<span style='color:rgb(87,0,255);font-family:Arial,宋体;font-size:12px;font-weight:200;'> http://www.eoulu.com <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>厦门办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Xiamen Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址： 厦门思明区世茂海峡大厦A塔4508-1室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 4508-1, Block A, Shimao Haixia Mansion, Siming District, Xiamen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:361001 电话/Tel:+86-592-2510021  <span><br><br>");

		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>深圳伊欧陆微电子系统有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Shenzhen Eoulu Micro-System Co.,Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：深圳宝安国际机场中城T3·space产业创新园D座501<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Unit 501,Floor 5, Block D of Zhongcheng T3·space Industrial Innovation Park,  Bao'an International Airport, Bao'an, Shenzhen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:518100 电话/Tel: +86-755-2162 7980/7981 传真/Fax: +86-755-26975515<span><br><br>");
		// sb.append("<span
		// style='color:#000080;font-family:宋体;font-weight:800;font-size:13px;'>北京办事处/<span>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:Arial;font-weight:800;font-size:13px;'>Beijing
		// Office<span><br>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址:
		// 北京市朝阳区广渠路金茂府23号院6号楼520室<span><br>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:100085
		// 电话/Tel: +86-10-62915850 传真/Fax: +86-10-62916716<span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>成都办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Chengdu Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：成都市成华区二环路东二段7号招商东城国际A栋2005室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:610000  电话/Tel: +86-028-84474790  <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>合肥办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Hefei Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：合肥高新区望江西路800号高新创业园A4栋302室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 302, Building A4, No.800 Wangjiangxi Road, High-tech Starting-up business incubation, Hefei, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:230088  电话/Tel: +86-551-64382598<span><br><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-weight:800;'>HK EOULU TRADING LIMITED<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Suite 2, Room1201, 12/F., Chinachem Johnston Plaza, 178-186 Johnston Road, Wanchai, Hong Kong<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Tel:00852-21527388   Fax:00852-35719160<span><br><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Please consider your environmental responsibility before printing this e-mail.This e-mail may contain confidential or privileged information.<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>If you are not the intended recipient (or received it in error) please notify the sender immediately and delete this e-mail. Unauthorized copying,<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>disclosure or distribution of the material in this e-mail is strictly forbidden.<span><br><br>");
		sb.append("</body></html>");

		String result = sb.toString();

		return result;
	}

	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public String getPOEmailSign() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head><META content='text/html; charset=gbk' http-equiv=Content-Type>");
		sb.append("<META name=GENERATOR content='MSHTML 8.00.6001.18702'>");
		sb.append("<style type='text/css'>");
		sb.append(".STYLE1 {color: #000000}");
		sb.append(
				"TABLE {FONT-SIZE: 12px; COLOR: #444444;LINE-HEIGHT: 14px; FONT-FAMILY: '宋体', 'Arial'; TEXT-DECORATION: none;}");
		sb.append(".STYLE3 { font-size: 13px;color: #FD9800; font-weight: bold;}");
		sb.append("</style>");
		sb.append("</head>");
		// 显示邮件内容
		sb.append("<body style='font-family:微软雅黑;font-size:14px;'>");

		sb.append("<span style='font-family:Arial;font-size:14px;'>Hi,All,</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Please kindly refer to the attachment.</span><br>");
		sb.append(
				"<span style='font-family:Arial;font-size:14px;'>Would you please help enter it and arrange a soonest ship date?</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Thanks for your support！</span><br>");
		sb.append("<span style='color:#000080'>------------------------</span><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-size:18px;font-weight:800;'>Eoulu<span><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-size:13px;font-weight:800;'>苏州伊欧陆系统集成有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:13px;font-weight:800;'>Suzhou Eoulu System Integration Co., Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址: 苏州工业园区星湖街218号生物纳米园A7楼305室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 305, Building A7, No.218 Xinghu Road, Suzhou Industrial Park, Suzhou, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:215000  电话/Tel: +86-512-62757360  传真/Fax: +86-512-62757313<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>手机/MP: +86-18566664208 邮箱:remind@eoulu.com<span><br>");
		sb.append("<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>公司网址: <span>");
		sb.append(
				"<span style='color:rgb(87,0,255);font-family:Arial,宋体;font-size:12px;font-weight:200;'> http://www.eoulu.com <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>厦门办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Xiamen Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址： 厦门思明区世茂海峡大厦A塔4508-1室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 4508-1, Block A, Shimao Haixia Mansion, Siming District, Xiamen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:361001 电话/Tel:+86-592-2510021  <span><br><br>");

		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>深圳伊欧陆微电子系统有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Shenzhen Eoulu Micro-System Co.,Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：深圳宝安国际机场中城T3·space产业创新园D座501<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Unit 501,Floor 5, Block D of Zhongcheng T3·space Industrial Innovation Park,  Bao'an International Airport, Bao'an, Shenzhen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:518100 电话/Tel: +86-755-2162 7980/7981 传真/Fax: +86-755-26975515<span><br><br>");
		// sb.append("<span
		// style='color:#000080;font-family:宋体;font-weight:800;font-size:13px;'>北京办事处/<span>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:Arial;font-weight:800;font-size:13px;'>Beijing
		// Office<span><br>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址:
		// 北京市朝阳区广渠路金茂府23号院6号楼520室<span><br>");
		// sb.append(
		// "<span
		// style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:100085
		// 电话/Tel: +86-10-62915850 传真/Fax: +86-10-62916716<span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>成都办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Chengdu Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：成都市成华区二环路东二段7号招商东城国际A栋2005室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:610000  电话/Tel: +86-028-84474790  <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>合肥办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Hefei Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：合肥高新区望江西路800号高新创业园A4栋302室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 302, Building A4, No.800 Wangjiangxi Road, High-tech Starting-up business incubation, Hefei, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:230088  电话/Tel: +86-551-64382598<span><br><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-weight:800;'>HK EOULU TRADING LIMITED<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Suite 2, Room1201, 12/F., Chinachem Johnston Plaza, 178-186 Johnston Road, Wanchai, Hong Kong<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Tel:00852-21527388   Fax:00852-35719160 <span><br><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Please consider your environmental responsibility before printing this e-mail.This e-mail may contain confidential or privileged information.<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>If you are not the intended recipient (or received it in error) please notify the sender immediately and delete this e-mail. Unauthorized copying,<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>disclosure or distribution of the material in this e-mail is strictly forbidden.<span><br><br>");
		sb.append("</body></html>");

		String content = sb.toString();
		return content;
	}

	public String getStaffEmailSign(String content,String name,String tel,String email) {

		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head><META content='text/html; charset=gbk' http-equiv=Content-Type>");
		sb.append("<META name=GENERATOR content='MSHTML 8.00.6001.18702'>");
		sb.append("<style type='text/css'>");
		sb.append(".STYLE1 {color: #000000}");
		sb.append(
				"TABLE {FONT-SIZE: 12px; COLOR: #444444;LINE-HEIGHT: 14px; FONT-FAMILY: '宋体', 'Arial'; TEXT-DECORATION: none;}");
		sb.append(".STYLE3 { font-size: 13px;color: #FD9800; font-weight: bold;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body style='font-family:微软雅黑;font-size:14px;'>");
		sb.append(content);
		sb.append("<span style='color:#000080'>------------------------</span><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-size:19px;font-weight:800;'>顺颂商祺！<span><br>");
		ChineseToEnglishUtil util = new ChineseToEnglishUtil();
		@SuppressWarnings("static-access")
		String EnglishName = util.HeaderPinyinName(name);
		sb.append("<span style='color:#000080;font-family:宋体;font-size:13px;font-weight:200;'>"+name+"/<span>"+"<span style='color:#000080;font-family:微软雅黑;font-size:13px;font-weight:200;'>"+EnglishName+"<span><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-size:18px;font-weight:800;'>Eoulu<span><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-size:13px;font-weight:800;'>苏州伊欧陆系统集成有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:13px;font-weight:800;'>Suzhou Eoulu System Integration Co., Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址: 苏州工业园区星湖街218号生物纳米园A7楼305室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 305, Building A7, No.218 Xinghu Road, Suzhou Industrial Park, Suzhou, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:215000  电话/Tel: +86-512-62757360  传真/Fax: +86-512-62757313<span><br>");
		if(tel.equals("NA") && email.equals("NA")){
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>手机/MP: +86-18566664208 邮箱:remind@eoulu.com<span><br>");
		}else{
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>手机/MP: +"+tel+" 邮箱:"+email+"<span><br>");
		}
		
		sb.append("<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>公司网址: <span>");
		sb.append(
				"<span style='color:rgb(87,0,255);font-family:Arial,宋体;font-size:12px;font-weight:200;'> http://www.eoulu.com <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>厦门办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Xiamen Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址： 厦门思明区世茂海峡大厦A塔4508-1室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 4508-1, Block A, Shimao Haixia Mansion, Siming District, Xiamen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:361001 电话/Tel:+86-592-2510021  <span><br><br>");

		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>深圳伊欧陆微电子系统有限公司/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Shenzhen Eoulu Micro-System Co.,Ltd<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：深圳宝安国际机场中城T3·space产业创新园D座501<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Unit 501,Floor 5, Block D of Zhongcheng T3·space Industrial Innovation Park,  Bao'an International Airport, Bao'an, Shenzhen<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:518100 电话/Tel: +86-755-2162 7980/7981 传真/Fax: +86-755-26975515<span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>成都办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Chengdu Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：成都市成华区二环路东二段7号招商东城国际A栋2005室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:610000  电话/Tel: +86-028-84474790  <span><br><br>");
		sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>合肥办事处/<span>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Hefei Office<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：合肥高新区望江西路800号高新创业园A4栋302室<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 302, Building A4, No.800 Wangjiangxi Road, High-tech Starting-up business incubation, Hefei, China<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:230088  电话/Tel: +86-551-64382598<span><br><br>");
		sb.append("<span style='color:#000080;font-family:Arial;font-weight:800;'>HK EOULU TRADING LIMITED<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Suite 2, Room1201, 12/F., Chinachem Johnston Plaza, 178-186 Johnston Road, Wanchai, Hong Kong<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Tel:00852-21527388   Fax:00852-35719160<span><br><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Please consider your environmental responsibility before printing this e-mail.This e-mail may contain confidential or privileged information.<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>If you are not the intended recipient (or received it in error) please notify the sender immediately and delete this e-mail. Unauthorized copying,<span><br>");
		sb.append(
				"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>disclosure or distribution of the material in this e-mail is strictly forbidden.<span><br><br>");
		sb.append("</body></html>");

		String result = sb.toString();

		return result;
	}
	
	
	public List<Map<String, String>> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		List<Map<String, String>> ls = new ArrayList<>();
		String fileName = null;
		String isExist = "notExists";
		DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
		factory.setRepository(file01);// 设置临时目录
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		ServletFileUpload upload = new ServletFileUpload(factory);// 2、创建一个文件上传解析器
		upload.setHeaderEncoding("UTF-8");// 解决上传文件名的中文乱码
		if (!upload.isMultipartContent(request)) {// 3、判断提交上来的数据是否是上传表单的数据

		}
		List<FileItem> items = null;
		items = upload.parseRequest(request); // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		byte data[] = new byte[1024];
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {
					
				} else {
					Map<String, String> tempMap = new HashMap<String, String>();
					fileName = item.getName();
					System.out.println("文件：" + fileName);
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					InputStream inputStream = item.getInputStream();
					File tempFile = new File(tempPath + fileName);
					if (tempFile.exists()) {
						System.out.println("cunzai?:" + tempFile.exists());
						tempFile.delete();
						isExist = "exists";
					}
					// 创建一个文件输出流
					FileOutputStream outputStream = new FileOutputStream(tempPath + File.separator + fileName);
					int i;
					while ((i = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, i);
					}
					inputStream.close();
					outputStream.close();
					fileName = tempPath + fileName;
					System.out.println(fileName);
					tempMap.put("fileName", fileName);
					ls.add(tempMap);
				}
			}
		}
		return ls;
	}
	
	
}
