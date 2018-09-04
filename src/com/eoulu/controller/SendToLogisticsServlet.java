package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.CompleteOrder;
import com.eoulu.entity.Consignee;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.Order;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.Quotes;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;
import com.google.gson.Gson;

@WebServlet("/SendToLogistics")
public class SendToLogisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendToLogisticsServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Order order = new Order();
		Consignee consignee = new Consignee();
		OrderService service = new OrderServiceImpl();
		System.out.println(11111111);
		String id = request.getParameter("id");// new
												// String(request.getParameter("id").getBytes("ISO-8859-1"),"utf-8");
		String isSend = request.getParameter("isSend");// new
														// String(request.getParameter("isSend").getBytes("ISO-8859-1"),"utf-8");
		System.out.println(isSend);
		if (isSend.equals("是")) {
			String ContractNo = request.getParameter("ContractNo");// new
																	// String(request.getParameter("ContractNo").getBytes("ISO-8859-1"),"UTF-8");
			// System.out.println("ContractNo:"+ContractNo);
			String ContractTitle = request.getParameter("ContractTitle");
			// System.out.println("ContractTitle"+ContractTitle);
			String company = request.getParameter("ConsigneeCompany");
			String address = request.getParameter("ConsigneeAddress");
			String contacts = request.getParameter("ConsigneeContacts");
			String telephone = request.getParameter("ConsigneeTel");
			SendMailUtil util = new SendMailUtil();
			MethodUtil mu = new MethodUtil();
			String input = "<span style='font-family:微软雅黑;font-size:14px;'>合同号：</span>" + ContractNo
					+ "<span style='font-family:微软雅黑;font-size:14px;'>，合同名称：</span>" + ContractTitle
					+ "<span style='font-family:微软雅黑;font-size:14px;'>，经确认已经可以进行发货！</span><br><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>公司名称：</span>" + company + "<span "
					+ "style='font-family:微软雅黑;font-size:14px;'>，地址：</span>" + address + "。<br><br>"
					+ "<span style='font-family:微软雅黑;font-size:14px;'>联系人：</span>" + contacts + "<span "
					+ "style='font-family:微软雅黑;font-size:14px;'>，联系电话：</span>" + telephone + "。<br><br>";
			String content = mu.getEmailSign(input, null);
			String subject = "Eoulu:" + ContractNo + "发货通知";
			String[] fileList = null;
			boolean success = util.doSendHtmlEmail(subject, content, fileList);

			if (success) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "合同统计";
				String description = "邮件-"+ ContractNo + "发货通知";
				log.insert(request, JspInfo, description);
				order.setID(Integer.parseInt(id));
				order.setIsSend(isSend);
				service.updateIsSend(order);
				consignee.setAddress(address);
				consignee.setCompany(company);
				consignee.setContacts(contacts);
				consignee.setTel(telephone);
				service.ConsigneeAdd(consignee);

				response.getWriter().write(new Gson().toJson("{\"message\":\"true\"}"));
			} else {
				response.getWriter().write(new Gson().toJson("{\"message\":\"false\"}"));
			}
		} else {
			order.setID(Integer.parseInt(id));
			order.setIsSend("否");
			service.updateIsSend(order);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
