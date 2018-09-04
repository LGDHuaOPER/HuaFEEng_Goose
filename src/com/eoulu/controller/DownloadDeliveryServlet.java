package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;

@WebServlet("/DownloadDelivery")
public class DownloadDeliveryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DownloadDeliveryServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		int quoteID = Integer.parseInt(req.getParameter("QuoteID"));
		List<Map<String, Object>> ls = service.getExcelQuoteRequest(quoteID);
		String path = req.getServletContext().getRealPath("/") + "down\\" + "EOULU发货清单&包装&运输.xlsx";
		service.exportExcel(ls, path);
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "报价系统";
		String description = "下载-EOULU发货清单&包装&运输.xlsx-" + quoteID;
		log.insert(req, JspInfo, description);
		resp.getWriter().write("down//" + "EOULU发货清单&包装&运输.xlsx");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
