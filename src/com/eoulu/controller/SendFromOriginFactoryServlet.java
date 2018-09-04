package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.OriginFactory;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendDelayEmailUtil;
import com.google.gson.Gson;

@WebServlet("/SendFromOriginFactory")
public class SendFromOriginFactoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendFromOriginFactoryServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginFactoryService service = new OriginFactoryServiceImpl();

		String type = req.getParameter("Type");
		int ID = Integer.parseInt(req.getParameter("ID"));
		OriginFactory entity = new OriginFactory();
		boolean flag = false;
		if (type.equals("yes")) {
			String PO = req.getParameter("PO");
			String SO = req.getParameter("SO");
			String FactoryPeriod = req.getParameter("FactoryPeriod");
			String DelayPeriod = req.getParameter("DelayPeriod");
			String DelayReason = req.getParameter("DelayReason");
			String input = "<span style='font-family:Arial,微软雅黑;font-size:14px;'>PO号：</span>" + PO + "<span "
					+ "style='font-family:Arial,微软雅黑;font-size:14px;'>，SO号：</span>" + SO + "<span "
					+ "style='font-family:微软雅黑;font-size:14px;'>，发生工厂货期延迟</span><br>" + "<br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>工厂货期由" + "</span>" + FactoryPeriod
					+ "<span style='font-family:微软雅黑;" + "font-size:14px;'>延迟至</span>" + DelayPeriod + "<br><br>" + ""
					+ "<span style='font-family:微软雅黑;font-size:14px;'>延迟原因：</span>" + DelayReason + "<br><br>";
			MethodUtil mu = new MethodUtil();
			
			String content = mu.getEmailSign(input, null);
			String subject = "Eoulu:工厂货期延迟";
			String[] fileList = null;
			SendDelayEmailUtil util = new SendDelayEmailUtil();
			boolean temp = util.doSendHtmlEmail(subject, content, fileList);
			if (temp) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "CASCADE页面";
				String description = "邮件-延迟提醒-"+PO;
				log.insert(req, JspInfo, description);
				entity.setType(type);
				entity.setID(ID);
				if (service.updateType(entity)) {
					flag = true;
				}
			}
		} else {
			entity.setType(type);
			entity.setID(ID);
			if (service.updateType(entity)) {
				flag = true;
			}
		}
		resp.getWriter().write(new Gson().toJson(flag));

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
