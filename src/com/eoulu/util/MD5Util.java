package com.eoulu.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * <p>���ַ�������MD5���ܵ���</p>
 * 
 * @author zhangkai
 * 
 * @date 2017/3/21
 * */
public class MD5Util {

	
	/**
	 * ���ַ���md5���ܣ����ؼ��ܹ����ַ���
	 * 
	 * 
	 * @param  param  ��Ҫ���ܵ��ַ���
	 * 
	 * 
	 * @return String ���ܹ����ַ���
	 * */
	public String md5ToString(String param){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(param.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}
	
	@Test
	public void test(){
//		System.out.println(md5ToString("OperatorAdmin"));
	System.out.println("原厂："+md5ToString("currentMonth"));
//		System.out.println("mengdi---"+md5ToString("mdmdEoulu"));
		System.out.println("---"+md5ToString("EOULU2017"));
	}
	
}
