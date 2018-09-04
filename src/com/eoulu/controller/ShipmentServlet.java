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
import com.eoulu.service.ShipmentService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.ShipmentServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

@WebServlet("/Shipment")
public class ShipmentServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public ShipmentServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShipmentService service = new ShipmentServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts());
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("shipmentCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("shipment", service.getShipment(page));
		new AccessStatistics().operateAccess(req, "发货通知");
		req.getRequestDispatcher("WEB-INF//Shipment.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	
	
}
