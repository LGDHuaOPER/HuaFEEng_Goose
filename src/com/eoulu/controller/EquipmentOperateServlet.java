package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.EquipmentDao;
import com.eoulu.entity.Equipment;
import com.eoulu.service.EquipmentService;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class EquipmentOperateServlet
 */
@WebServlet("/EquipmentOperate")
public class EquipmentOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EquipmentOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classify = request.getParameter("classify");
		
		
		Equipment equipment = new Equipment();
		InformationBankService informationBankService = new InformationBankServiceImpl();
		
		try{equipment.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{equipment.setModel(request.getParameter("model"));}catch(Exception e){}
		try{equipment.setRemarks(request.getParameter("remarks"));}catch(Exception e){}
		try{equipment.setEquipmentUnit(request.getParameter("equipment_unit"));}catch(Exception e){}
		try{equipment.setDeliveryTime(request.getParameter("delivery_time"));}catch(Exception e){}
		try{equipment.setSourceArea(request.getParameter("source_area"));}catch(Exception e){}
		try{equipment.setItemCode(request.getParameter("item_code"));}catch(Exception e){}
		try{equipment.setCommodityCategory(request.getParameter("commodity_category"));}catch(Exception e){}
		try{equipment.setSuppiler(Integer.parseInt(request.getParameter("supplier")));}catch(Exception e){}
		System.out.println(equipment.getID());
		
		boolean flag = false;
		String result = "";
		
		switch(classify){
		case "新增":
			if(informationBankService.isExist(request.getParameter("model"))){
				result = "型号已存在！";
			}else{
				flag = informationBankService.EquipmentAdd(equipment);
				if(flag){
					result = "添加成功！";
				}else{
					result = "添加失败！";
				}
			}
			break;
		case "删除":
			flag = informationBankService.EquipmentDelete(equipment.getID());
			if(flag){
				result = "删除成功！";
			}else{
				result = "删除失败！";
			}
			break;
		case "修改":
			flag = informationBankService.EquipmentModify(equipment);
			if(flag){
				result = "修改成功！";
			}else{
				result = "修改失败！";
			}
			break;
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "资料库-产品型号表";
			String description = classify+"-"+request.getParameter("model");
			log.insert(request, JspInfo, description);
		}
		
		
		response.getWriter().write(new Gson().toJson(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
