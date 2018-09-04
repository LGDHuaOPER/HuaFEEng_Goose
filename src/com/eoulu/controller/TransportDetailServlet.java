package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TransportDetailServlet
 */
@WebServlet("/TransportDetail")
public class TransportDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransportDetailServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF\\TransportDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String condition = request.getParameter("transportCAT")==null?"":request.getParameter("transportCAT");
		OrderService orderService = new OrderServiceImpl();
		
		if(!condition.equals("")){
			List<Map<String, Object>> list = null;
			Page page = new Page();
			String currentPage = request.getParameter("currentPage");
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			
			if(condition.equals("ShippedMonth")){
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
				String month = simpleDateFormat.format(new Date());
				page.setRecordCounts(orderService.getShippedCounts(0, month, month));
				list = orderService.getShippedDetail(page,0,month, month);
			}else{
				page.setRecordCounts(orderService.getAllCountsIfActualDelivery(condition, "CargoPeriod", 0));
				list = orderService.getTransportDetail(page, condition, 0);
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("transportCAT", condition);
			map.put("currentPage", currentPage);
			map.put("pageCount", page.getPageCounts());
			map.put("transportDetail",list);
			response.getWriter().write(new Gson().toJson(map));
		}
	}

}
