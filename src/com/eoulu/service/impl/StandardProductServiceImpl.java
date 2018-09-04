package com.eoulu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.eoulu.commonality.Page;
import com.eoulu.dao.StandardProductDao;
import com.eoulu.entity.StandardProduct;
import com.eoulu.service.StandardProductService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class StandardProductServiceImpl implements StandardProductService {
	
	@Override
	public List<Map<String, Object>> getAllData(Page page, String type, String column1, String content1, String column2,
			String content2) {
		
		return new StandardProductDao().getAllData(page, type, column1, content1, column2, content2);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
		
		return new StandardProductDao().getCounts(type, column1, content1, column2, content2);
	}

	@Override
	public boolean insert(StandardProduct product) {
		boolean result = false;
		result = new StandardProductDao().insert(product);
	
		return result;
	}

	@Override
	public boolean update(StandardProduct product) {
	
		return new StandardProductDao().update(product);
	}

	@Override
	public String sendReviewMail(int ID, String title) {
		String subject = "Eoulu：标准产品"+title+"审核";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>肖宇，你好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>标准产品"+title+"已完成文档上传，请审核。</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(), "NA");
	
		return emailConfig(subject, content);
		

	}
	
	public String emailConfig(String subject,String content){
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("standardProduct.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		int toCount = Integer.parseInt(pro2.getProperty("to"));
		for(int i=0 ; i<toCount ; i++){
			int temp = i+1;
			String key = "To"+temp;
			list.add(pro2.getProperty(key));
			
		}
		to = new String[toCount];
		for(int i=0 ; i<list.size();i++){
			to[i] = list.get(i);
		}
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		return new JavaMailToolsUtil(user, uname, pwd).sendHtmlEmail(subject, content, null, to, copyto);
	
	}

	@Override
	public boolean review(int ID) {
		return new StandardProductDao().setReview(ID);
	}

}
