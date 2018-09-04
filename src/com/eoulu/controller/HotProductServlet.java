package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.SalesStatisticsService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.service.impl.SalesStatisticsServiceImpl;
import com.google.gson.Gson;
@WebServlet("/HotProduct")
public class HotProductServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public HotProductServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long timeStart = System.currentTimeMillis();
		SalesStatisticsService service = new SalesStatisticsServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify = req.getParameter("classify");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(cal.getTime());
		String date2 = df.format(new Date());
		String start = req.getParameter("StartTime")==null?date.split("-")[0]+"-01-01":req.getParameter("StartTime");
		String end = req.getParameter("EndTime")==null?date2:req.getParameter("EndTime");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		
		System.out.println("字段为："+classify);
		
		long time1 = System.currentTimeMillis();
		System.out.println("time1:"+(time1-timeStart));
			page.setRecordCounts(service.getHotProductByTimeCount(page, start, end));
			long time2 = System.currentTimeMillis();
			
			req.setAttribute("queryType", "SingleSelect");
			req.setAttribute("pageCounts", page.getPageCounts());
			req.setAttribute("sales", service.getHotProductByTime(page, start, end,classify));
			req.setAttribute("StartTime", start);
			req.setAttribute("EndTime", end);
			if(classify!=null){
				req.setAttribute("classify", classify);
			}
		Map<String, String> map = new HashMap<>();
		QuoteSystemService service2 = new QuoteSystemServiceImpl();
		map = service2.getTime();
		req.setAttribute("rada", new Gson().toJson(map));
		
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("salesCounts", page.getRecordCounts());
		long timeEnd = System.currentTimeMillis();
//		System.out.println("END:"+(timeEnd-time1)/1000);
		new AccessStatistics().operateAccess(req, "热销产品");
		req.getRequestDispatcher("WEB-INF//HotProduct.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String productCategory = req.getParameter("ProductCategory")==null?"":req.getParameter("ProductCategory");
		String model = req.getParameter("Model")==null?"":req.getParameter("Model");
		String column = req.getParameter("Column")==null?"":req.getParameter("Column");
		int currentPage = req.getParameter("CurrentPage")==null?1:Integer.parseInt(req.getParameter("CurrentPage"));
		QuoteSystemService service = new QuoteSystemServiceImpl();
		Page page = new Page();
		page.setRows(10);
		page.setCurrentPage(currentPage);
		page.setRecordCounts(service.getModelCounts(productCategory,model));
		Map<String, Object> map = new HashMap<>();
		map.put("data", service.getSalesVolume(productCategory,model,column, page));
		map.put("pageCount", page.getPageCounts());
		map.put("currentPage", currentPage);
		resp.getWriter().write(new Gson().toJson(map));
		
	}
	

}
