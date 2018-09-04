package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.entity.OriginFactory;
import com.eoulu.entity.QuoteSystem;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.MailRecordService;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.MailRecordServiceImpl;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.syn.POAsEmailFilePDF;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendPOEmailUtil;
import com.google.gson.Gson;

@WebServlet("/SendCascadeEmail")
public class SendCascadeEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendCascadeEmailServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		OriginFactoryService factory = new OriginFactoryServiceImpl();
		QuoteSystemDao dao = new QuoteSystemDao();
		String Type = req.getParameter("FileType")==null?"":req.getParameter("FileType");
		int id = Integer.parseInt(req.getParameter("QuoteID"));
		List<Map<String, Object>> ls2 = service.getCascadePOByQuoteID(id);
		String[] to = req.getParameter("Recepter").split(";");
		String[] copy = req.getParameter("CopyList").split(";");
		String content = new MethodUtil().getEmailSign(req.getParameter("Content"), "NA");
		POAsEmailFilePDF pdf = new POAsEmailFilePDF();
		Map<String, String> result = new HashMap<>();
		if(Type.equals("Parts")){
			result = pdf.exportCascadePOFile(req);
		}else{
			result = pdf.exportCascadePOCompleteFile(req);
		}
		String endOne = result.get("endOne");
		String number = result.get("number");
		String loadUrl = result.get("loadUrl");
		String subject = "Eoulu：New PO of " + endOne.trim() + "  " + number.trim();
		String[] fileList = new String[] { req.getServletContext().getRealPath("/") + loadUrl };
		SendPOEmailUtil util = new SendPOEmailUtil();
		boolean temp = util.doSendHtmlEmail(to, copy, subject, content, fileList);
		String flag = temp?"发送成功！":"邮箱发送失败！";
		if (temp) {
			int POID = Integer.parseInt(result.get("POID"));
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = Type.equals("Parts")?("邮件-Cascade PO配件" + service.getQuoteNumber(id)):"邮件-Cascade PO整机" + service.getQuoteNumber(id);
			log.insert(req, JspInfo, description);
			if (ls2.size() > 1) {
				
				String po = number;
				if (po != null && !po.equals("") && !factory.getInfoByPO(POID, "Cascade") && !factory.getInfoByPO(POID, "CascadeComplete")) {
					OriginFactory entity = new OriginFactory();
					entity.setPO(po);
					entity.setSO("");
					entity.setFactoryPeriod("0000-00-00");
					entity.setDelayPeriod("0000-00-00");
					entity.setDelayReason("");
					entity.setPOID(POID);
					String poType = Type.equals("Parts")?"Cascade":"CascadeComplete";
					entity.setPOType(poType);
					entity.setType("no");
					flag = factory.insert(entity)?"发送成功！":"原厂信息生成失败！";
				}
				
			}
			QuoteSystem quoteSystem = new QuoteSystem();
			quoteSystem.setID(id);
			if(Type.equals("Parts")){
				quoteSystem.setCascadeStatus("yes");
				flag = (dao.updateCascadeStatus(quoteSystem) && service.operateCascadeMail(req,POID))?"发送成功！":"邮件模板存储失败！";
			}else{
				quoteSystem.setCascadeCompleteStatus("yes");
				flag = (dao.updateCascadeCompleteStatus(quoteSystem) && service.operateCascadeMail(req,POID))?"发送成功！":"邮件模板存储失败！";
			}
			
		}
		resp.getWriter().write(new Gson().toJson(flag));

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		int poID = Integer.parseInt(req.getParameter("POID"));
		resp.getWriter().write(new Gson().toJson(service.getCascadeMail(poID)));
	}

}
