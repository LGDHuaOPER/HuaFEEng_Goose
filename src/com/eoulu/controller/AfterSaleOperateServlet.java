package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.AfterSaleService;
import com.eoulu.service.impl.AfterSaleServiceImpl;
import com.google.gson.Gson;
@WebServlet("/AfterSaleOperate")
public class AfterSaleOperateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public AfterSaleOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AfterSaleService service = new AfterSaleServiceImpl();
		String classify = req.getParameter("Classify");
		boolean flag = false;
		switch (classify) {
		case "Add":flag = service.afterSaleAdd(req);break;
		case "Delete":flag = service.afterSaleDelete(req);break;
		case "Modify":flag = service.afterSaleUpdate(req);break;
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

}
