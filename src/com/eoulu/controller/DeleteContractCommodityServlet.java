package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;
@WebServlet("/DeleteContractCommodity")
public class DeleteContractCommodityServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public DeleteContractCommodityServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String type = req.getParameter("Type");
		int id= Integer.parseInt(req.getParameter("ID"));
		boolean flag = false;
		switch(type){
		case "RMBContract":flag = service.deleteRMBCommodity(id);break;
		case "USDContract":flag = service.deleteUSDCommodity(id);break;
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "删除-"+type+"的商品信息";
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
