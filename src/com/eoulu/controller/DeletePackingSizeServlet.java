package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.google.gson.Gson;
@WebServlet("/DeletePackingSize")
public class DeletePackingSizeServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	public DeletePackingSizeServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PackingListService service = new PackingListServiceImpl();
		int id = Integer.parseInt(req.getParameter("sizeID"));
		boolean flag = service.deletePackingSize(id);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "箱单页面";
			String description = "删除-尺寸信息"+id;
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
