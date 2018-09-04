package com.eoulu.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 封装工具类：中文名字转拼音
 * @author zuo
 *
 */
public class ChineseToEnglishUtil {
	// 将汉字转换为全拼
	public static String getPingYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	// 返回中文的首字母
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	// 将字符串转移为ASCII码
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static String HeaderPinyinName(String name){//中文名字转拼音后，首字母大写
		String result = "";
		for(int i=0;i<name.length();i++){
			String first = getPingYin(name.charAt(i)+"");
			String header = getPinYinHeadChar(name.charAt(i)+"");
			if(name.length()>2 ){
				if(i!=2){
					first = first.replaceFirst(header, header.toUpperCase());
				}
				if(i==1){
					result += " "+first;
				}else{
					result += first;
				}
			}else{
				first = first.replaceFirst(header, header.toUpperCase());
				if(i==1){
					result += " "+first;
				}else{
					result += first;
				}
			}
			
			
		}
		
		return result;
		}
	
	public static String cityPinyin(String city){
		switch (city) {
		case "重庆":
			city = "Chongqing";
			break;
		case "厦门":
			city = "Xiamen";
			break;
		case "东莞":
			city = "Dongguan";
			break;
		case "蚌埠":
			city = "Bengbu";
			break;
		case "长春":
			city = "Changchun";
			break;
		case "长沙":
			city = "Changsha";
			break;

		default:
			String pinyin = getPingYin(city);
			city = pinyin.substring(0, 1).toUpperCase() + pinyin.substring(1);
			break;
		}
		
		return city;
	}
	
	
	public static void main(String[] args) {
		System.out.println(HeaderPinyinName("孟迪"));
		System.out.println(getPinYinHeadChar("是"));
		System.out.println(getCnASCII("小胖子"));
		System.out.println("陈鸿雁".length());
		System.out.println(getPingYin("秦皇岛"));
	}
}
