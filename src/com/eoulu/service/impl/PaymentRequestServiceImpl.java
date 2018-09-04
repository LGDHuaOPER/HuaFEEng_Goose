package com.eoulu.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
		String Payee = list.get(1).get("Payee").toString();
		String Account = list.get(1).get("Account").toString();
		String DepositBank = list.get(1).get("DepositBank").toString();
		String PaymentRemark = list.get(1).get("PaymentRemark").toString();
		String StoreName = list.get(1).get("StoreName").toString();
		String OrderNO = list.get(1).get("OrderNO").toString();
		String Link = list.get(1).get("Link").toString();
		String LinkRemark = list.get(1).get("LinkRemark").toString();
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+ExpenseDetails+"。</span>");
		if(!Payee.equals("")||!Account.equals("")||!DepositBank.equals("")||!PaymentRemark.equals("")){
			sBuilder.append("<br><br><table border='1' style='font-family:微软雅黑;text-align: center;box-sizing: border-box;border-collapse: collapse;font-size: 14px;'><thead><tr><th colspan='2'>付款信息</th></tr></thead>");
			sBuilder.append("<tbody><tr style='height: 30px;background: #fff;'><td>收款户名：</td><td>"+Payee+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: rgba(195,206,220,0.9);'><td>账号：</td><td>"+Account+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: #fff;'><td>开户行：</td><td>"+DepositBank+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: rgba(195,206,220,0.9);'><td>备注：</td><td>"+PaymentRemark+"</td></tr></tbody></table>");
		}
		
		if(!StoreName.equals("")||!OrderNO.equals("")||!Link.equals("")||!LinkRemark.equals("")){
			sBuilder.append("<br><br><table border='1' style='font-family:微软雅黑;text-align: center;box-sizing: border-box;border-collapse: collapse;font-size: 14px;'><thead><tr><th colspan='2'>链接信息</th></tr></thead>");
			sBuilder.append("<tbody><tr style='height: 30px;background: #fff;'><td>店家名称：</td><td>"+StoreName+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: rgba(195,206,220,0.9);'><td>订单号：</td><td>"+OrderNO+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: #fff;'><td>链接：</td><td>"+Link+"</td></tr>");
			sBuilder.append("<tr style='height: 30px;background: rgba(195,206,220,0.9);'><td>备注：</td><td>"+LinkRemark+"</td></tr></tbody></table>");
		}
		
		sBuilder.append("<br><br><span style='font-family:微软雅黑;font-size:14px;'>请协助尽快安排付款，非常感谢！</span><br><br>");
		String content = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
		String[] to = request.getToList().split(";");
		String[] copyto = request.getCopyList().split(";");
		String[] files = Attachment.split("::");
		String folderPath = "E:\\LogisticsFile\\File\\PaymentRequest\\";
		for(int i = 0;i < files.length;i ++){
			String fileName = files[i];
			files[i] = folderPath + fileName;
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



}
