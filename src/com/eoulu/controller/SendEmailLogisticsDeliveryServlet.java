package com.eoulu.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.DeliveryAdviceDao;
import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.entity.QuoteSystem;
import com.eoulu.service.DeliveryAdviceService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.DeliveryAdviceServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendEmailTempUtil;
import com.google.gson.Gson;
@WebServlet("/SendEmailLogisticsDelivery")
public class SendEmailLogisticsDeliveryServlet extends HttpServlet{
	private static final String rootURL = "E:\\LogisticsFile\\File\\";
	private static final long serialVersionUID = 1L;
	public SendEmailLogisticsDeliveryServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		QuoteSystem quoteSystem = new QuoteSystem();
		QuoteSystemDao dao = new QuoteSystemDao();
		DeliveryAdviceDao advice = new DeliveryAdviceDao();
		String status = req.getParameter("Status");
		int quoteID = Integer.parseInt(req.getParameter("QuoteID")); 
		String name = dao.getQuoteRequestName(quoteID).size()>1?dao.getQuoteRequestName(quoteID).get(1).get("Name").toString():"无客户信息";
		String contractNO = dao.getQuoteRequestContractNO(quoteID).size()>1?dao.getQuoteRequestContractNO(quoteID).get(1).get("ContractNO").toString():"无Contract No";
		String file = advice.getContractFile(quoteID);
		String contractFile = "";
		String technology = "";
		if(!file.equals("")){
			if(!file.contains(";")||(file.contains(";") && file.endsWith(";"))){
				contractFile = file.contains(";")?rootURL  +file.subSequence(0, file.length()-1):rootURL  +file;
			}else{
				contractFile = rootURL  + file.split(";")[0];
				technology = rootURL  + file.split(";")[1];
			}
		}
		boolean success = false;
	
		List<Map<String,Object>> ls = service.getExcelQuoteRequest(quoteID);
		String path = req.getServletContext().getRealPath("/") + "down\\"  + "EOULU发货清单&包装&运输.xlsx";
		service.exportExcel(ls, path);
		SendEmailTempUtil util = new SendEmailTempUtil();
		MethodUtil mu = new MethodUtil();
		String input = "<span style='font-family:微软雅黑;font-size:14px;'>晓亮姐，您好！</span><br><br>";
		
		String[] fileList = new String[]{path};
		if(!contractFile.equals("") && !technology.equals("")){
			fileList = new String[]{contractFile,technology,path};
			input +="<span style='font-family:微软雅黑;font-size:14px;'>1、附件是此票合同，请您保存留档。</span><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>2、附件是此票技术协议，请您保存留档。</span><br><br>";
		}else if(!technology.equals("")){
			fileList = new String[]{technology,path};
			input +="<span style='font-family:微软雅黑;font-size:14px;'>1、此票无合同文件。</span><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>2、附件是此票技术协议，请您保存留档。</span><br><br>";
		}else if(!contractFile.equals("") ){
			fileList = new String[]{contractFile,path};
			input +="<span style='font-family:微软雅黑;font-size:14px;'>1、附件是此票合同，请您保存留档。</span><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>2、此票无技术协议。</span><br><br>";
		}else{
			input +="<span style='font-family:微软雅黑;font-size:14px;'>1、此票无合同。</span><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>2、此票无技术协议。</span><br><br>";
		}
		input +="<span style='font-family:微软雅黑;font-size:14px;'>小蒋，你好！</span><br><br>"
				+ "<span style='font-family:微软雅黑;font-size:14px;'>附件EOULU发货清单&包装&运输模板.xls，是备货清单、运输要求与包装要求，请阅读。</span><br><br>"
				+ "<span style='font-family:微软雅黑;font-size:14px;'>以上请查收，烦请尽快备货，感谢支持，如有疑问请随时联系，谢谢！</span><br>";
		System.out.println("test::::"+Arrays.toString(fileList));
		String content = mu.getEmailSign(input,"NA");
		String subject = "Eoulu:留档&备货-"+contractNO+"-"+name;
		success = util.doSendHtmlEmail(subject, content, fileList);
		if(success){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "邮件-备货清单"+service.getQuoteNumber(quoteID);
			log.insert(req, JspInfo, description);
			quoteSystem.setMailStatus(contractNO);
			quoteSystem.setID(quoteID);
			success = dao.updateMailStatus(quoteSystem);
		}
	
		
		
		resp.getWriter().write(new Gson().toJson(success));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DeliveryAdviceService service = new DeliveryAdviceServiceImpl();
		int quoteID = Integer.parseInt(req.getParameter("QuoteID"));
		resp.getWriter().write(service.getContractFile(quoteID));
		
	}

}
