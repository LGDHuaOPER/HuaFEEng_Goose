package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InsuranceDirectiveService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InsuranceDirectiveServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;
@WebServlet("/DeleteGoodsInfo")
public class DeleteGoodsInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public DeleteGoodsInfoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InsuranceDirectiveService service = new InsuranceDirectiveServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		boolean flag = service.deleteGoodsInfo(id);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-运输保险指令";
			String description = "删除-货物信息"+id;
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	
	
}
