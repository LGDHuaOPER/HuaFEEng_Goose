package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.EquipmentService;
import com.eoulu.service.InventoryInfoService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.InventoryInfoServiceImpl;
import com.eoulu.service.impl.InventoryServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.util.EXCELUtil;

/**
 * Servlet implementation class ExportInventoryServlet
 */
@WebServlet("/ExportInventory")
public class ExportInventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportInventoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		System.out.println(1234);
//		String startTime = request.getParameter("startTime");
//		String endTime = request.getParameter("endTime");
//		//获取设备信息与库存
//		EquipmentService equipmentService = new EquipmentServiceImpl();
//		List<Map<String,Object>> equipmentList = equipmentService.getAllEquipmentInfo();
//		//获取出入库信息
//		InventoryInfoService inventoryInfoService = new InventoryInfoServiceImpl();
//		Map<String,List<Map<String,Object>>> inventoryInfoList = inventoryInfoService.getinventoryInfoByEquipment(equipmentList,startTime,endTime);
//		//写成文件，返回地址
//		String date = startTime+"-"+endTime;
//		String date2 = startTime+","+endTime;
//		String path = request.getServletContext().getRealPath("/")+"down\\"+"InventoryInfo"+date+".xlsx";
//		new EXCELUtil().buildExcel(equipmentList, inventoryInfoList, date2, path);
//		System.out.println("down//"+"InventoryInfo"+date+".xlsx");
		List<Map<String,Object>> ls = new InventoryServiceImpl().getAllData();
		String path = request.getServletContext().getRealPath("/")+"down\\"+"InventoryInfo.xlsx";
		new EXCELUtil().buildExcel(ls, path);
		response.getWriter().write("down//"+"InventoryInfo.xlsx");
		}

}
