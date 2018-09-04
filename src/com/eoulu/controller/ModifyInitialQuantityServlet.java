package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InventoryInfoService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InventoryInfoServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;

/**
 * ������޸��ڳ�������servlet
 */
@WebServlet("/ModifyInitialQuantity")
public class ModifyInitialQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyInitialQuantityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		
		int equipmentID = Integer.parseInt(request.getParameter("equipment_id"));
		int initialQuantity = Integer.parseInt(request.getParameter("initial_quantity"));
		
		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();
		
		flag = inventoryInfoService.modifyInitQuantity(equipmentID, initialQuantity);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "库存页面";
			String description = "修改-型号为"+inventoryInfoService.getModel(equipmentID)+"的期初数量";
			log.insert(request, JspInfo, description);
		}
		
		response.getWriter().write("{\"message\":\""+flag+"\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
