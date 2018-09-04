package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.EquipmentService;
import com.eoulu.service.InventoryInfoService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.InventoryInfoServiceImpl;

/**
 * ������ѯ�����Ϣ
 */
@WebServlet("/InventoryByQueryOne")
public class InventoryByQueryOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryByQueryOneServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String condition = request.getParameter("type1");
		Page page = new Page();
		EquipmentService equipmentService = new EquipmentServiceImpl();
		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();
		try{page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));}catch(Exception e){page.setCurrentPage(1);}
		page.setRows(10);
		


		Map<String,Object> map = new HashMap<String,Object>();
		switch(condition){
		case "入库时间":
		case "出库时间":map.put("1", request.getParameter("start_time1"));map.put("2", request.getParameter("end_time1"));break;
		case "库存" :map.put("1", request.getParameter("inventory_time1"));break;
		default :map.put("1", request.getParameter("searchContent1"));
		}
		page.setRecordCounts(inventoryInfoService.QueryInventoryInfoByOneCounts(condition, map));

		//���ݵĲ���
		request.setAttribute("queryType", "singleSelect");
		request.setAttribute("condition", condition);
		request.setAttribute("searchContent1", map.get("1"));
		try{request.setAttribute("inventory_time1", map.get("1"));}catch(Exception e){}
		try{request.setAttribute("start_time1", map.get("1"));}catch(Exception e){}
		try{request.setAttribute("end_time1", map.get("2"));}catch(Exception e){}
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("inventories", inventoryInfoService.QueryInventoryInfoByOne(condition, map, page));
		request.getRequestDispatcher("WEB-INF\\inventory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
