package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.InventoryService;
import com.eoulu.service.impl.InventoryServiceImpl;

/**
 * Servlet implementation class InventoryServlet
 */
@WebServlet("/Inventory")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Page page = new Page();
		String classify = request.getParameter("classify")==null?"":request.getParameter("classify").trim();
		String content = request.getParameter("content")==null?"":request.getParameter("content").trim();
		String column = request.getParameter("column") == null?"":request.getParameter("column");
		if(classify.contains("时间")){
			String start = request.getParameter("start_time1");
			String end = request.getParameter("end_time1");
			content = start+";"+end;
		}
		
		try{page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));}catch(Exception e){page.setCurrentPage(1);}
//		page.setRows(10);
//		page.setRecordCounts(inventoryInfoService.getAllEquipmentCounts());
		InventoryService service = new InventoryServiceImpl();
		page.setRows(10);
		page.setRecordCounts(service.getAllCounts(classify,content));
		
		//���ݵĲ���
//		request.setAttribute("queryType", "common");
		request.setAttribute("content", content);
		request.setAttribute("classify", classify);
		request.setAttribute("currentPage", page.getCurrentPage());
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("operationTime", service.getTimeInfo().get(1).get("OperationTime").toString());
		request.setAttribute("latestInventory", service.getTimeInfo().get(1).get("LatestInventory").toString());

//		request.setAttribute("inventories", inventoryInfoService.getInventoryByCommon(page));
		request.setAttribute("inventories", service.getAllData(page,classify,content,column));
		request.setAttribute("column", column);
		new AccessStatistics().operateAccess(request, "库存页面");
		request.getRequestDispatcher("WEB-INF\\inventory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
