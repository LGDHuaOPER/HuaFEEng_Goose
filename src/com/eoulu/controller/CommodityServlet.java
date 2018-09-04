package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.CommodityService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
@WebServlet("/Commodity")
public class CommodityServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public CommodityServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		CommodityService comm = new CommodityServiceImpl();
		String select = req.getParameter("selected")==null?"":req.getParameter("selected");
		String classify1 = req.getParameter("type1")==null?"":req.getParameter("type1");
		String parameter1 = req.getParameter("searchContent1")==null?"":req.getParameter("searchContent1");
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		if(select.equals("singleSelect")){
			page.setRecordCounts(comm.getCountByClassifyInOne(classify1, parameter1));
			req.setAttribute("commodity", comm.getQueryByClassifyInOne(classify1, parameter1, page));
			req.setAttribute("queryType", "singleSelect");
			req.setAttribute("str", "singleSelect");
			req.setAttribute("classify1", classify1);
			req.setAttribute("parameter1", parameter1);
		}else if(select.equals("mixSelect")){
			String classify2 = req.getParameter("type2")==null?"":req.getParameter("type2");
			String parameter2 = req.getParameter("searchContent2")==null?"":req.getParameter("searchContent2");
			page.setRecordCounts(comm.getCountByClassifyInTwo(classify1, parameter1, classify2, parameter2));
			req.setAttribute("commodity", comm.getQueryByClassifyInTwo(classify1, parameter1, classify2, parameter2, page));
			req.setAttribute("queryType", "mixSelect");
			req.setAttribute("str", "mixSelect");
			req.setAttribute("classify1", classify1);
			req.setAttribute("parameter1", parameter1);
			req.setAttribute("classify2", classify2);
			req.setAttribute("parameter2", parameter2);
		}else{
			new AccessStatistics().operateAccess(req, "商品管理");
			page.setRecordCounts(service.getCommodityCounts());
			req.setAttribute("commodity", service.getCommodityInfo(page));
			req.setAttribute("queryType", "common");
		}
		
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("commodityCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.getRequestDispatcher("WEB-INF//MerchandiseControl.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
