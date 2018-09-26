package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.eoulu.commonality.Page;
import com.eoulu.dao.PaymentRequestDao;
import com.eoulu.entity.PaymentRequest;
import com.eoulu.service.PaymentRequestService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class PaymentRequestServiceImpl implements PaymentRequestService{

	@Override
	public String insert(PaymentRequest request) {
		
		PaymentRequestDao dao = new PaymentRequestDao();
		boolean flag = dao.insert(request);
		return flag?"添加成功":"添加失败";
	}

	@Override
	public String update(PaymentRequest request) {
		PaymentRequestDao dao = new PaymentRequestDao();
		boolean flag = dao.update(request);
		return flag?"修改成功":"修改失败";
	}

	@Override
	public List<Map<String, Object>> getDataByPage(Page page) {
		PaymentRequestDao dao = new PaymentRequestDao();
		return dao.getDataByPage(page);
	}

	@Override
	public int getCounts() {
		PaymentRequestDao dao = new PaymentRequestDao();
		return dao.getCounts();
	}

	@Override
	public boolean sendMail(PaymentRequest request) {
		
		Properties pro = new Properties();
		String SEND_USER = "";
		String SEND_UNAME = "";
		String SEND_PWD = "";
		InputStream in = SendMailUtil.class.getResourceAsStream("email.properties");
		try {
			pro.load(in);
			SEND_USER = pro.getProperty("SEND_USER");
			SEND_UNAME = pro.getProperty("SEND_UNAME");
			SEND_PWD = pro.getProperty("SEND_PWD");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		PaymentRequestDao dao = new PaymentRequestDao();
		List<Map<String, Object>> list = dao.getMailInfo(request.getID());
		String Attachment = list.get(1).get("Attachment").toString();
		String ExpenseDetails = list.get(1).get("ExpenseDetails").toString();
	
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>以下是"+ExpenseDetails+"付款申请，请您查看。</span><br><br>");
		sBuilder.append(request.getContent());
		sBuilder.append("<br><br><span style='font-family:微软雅黑;font-size:14px;'>请协助尽快安排付款，非常感谢！</span><br><br>");
		String content = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
		String[] to = request.getToList().split(";");
		String[] copyto = request.getCopyList().split(";");
		String[] files = null;
		if(!Attachment.equals("")){
			files = Attachment.split("::");
			String folderPath = "E:\\LogisticsFile\\File\\PaymentRequest\\";
			for(int i = 0;i < files.length;i ++){
				String fileName = files[i];
				files[i] = folderPath + fileName;
			}
		}
	
		
		boolean flag = new JavaMailToolsUtil(SEND_USER, SEND_UNAME,SEND_PWD).doSendHtmlEmail(request.getSubject(), content,files , to, copyto);
		if(flag){
			dao.updateSendState(request.getID());
		}
		return flag;
		
	}

	@Override
	public boolean updatePayState(int ID) {
		PaymentRequestDao dao = new PaymentRequestDao();
		return dao.updatePayState(ID);
	}

	@Override
	public boolean deleteFile(int ID, String fileName) {
		boolean flag = false;
		PaymentRequestDao dao = new PaymentRequestDao();
		List<Map<String, Object>> list = dao.getAttachement(ID);
		String fileStr = "";
		if(list.size()>1){
			fileStr = list.get(1).get("Attachment").toString();
		}
		String[] fileArr = fileStr.split("::");
		fileStr = "";
		for(int i = 0;i < fileArr.length;i++){
			if(!fileArr[i].equals(fileName)){
				fileStr += (fileArr[i]+"::");
			}
		}
		if(ID!= 0){
			dao.updateAttachemnt(ID, fileStr);
		}
	
		String folder = "E:\\LogisticsFile\\File\\PaymentRequest\\";
		if(!fileName.equals("")){
			try {
				FileUtils.forceDelete(new File(folder+fileName));
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
	
		return flag;
	}



}
