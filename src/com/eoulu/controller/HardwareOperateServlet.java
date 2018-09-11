package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.HardwareAdvancesService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.HardwareAdvancesServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;
@WebServlet("/HardwareOperate")
public class HardwareOperateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public HardwareOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HardwareAdvancesService service = new HardwareAdvancesServiceImpl();
		String classify = req.getParameter("Classify");
		boolean flag = false;
		switch (classify) {
		case "Add":flag = service.hardwareAdvancesAdd(req);break;
		case "Delete":flag = service.hardwareAdvancesDelete(req);
		case "Modify":flag = service.hardwareAdvancesUpdate(req);break;
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-装机进展";
			String description = "";
			if(classify.equals("Add")){
				description = "新增-"+ req.getParameter("Customer");
			}
			if(classify.equals("Modify")){
				description = "修改-"+ req.getParameter("Customer");
			}
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	

}
