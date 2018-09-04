package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OriginService;
import com.eoulu.service.impl.OriginServiceImpl;
import com.google.gson.Gson;
@WebServlet("/OriginGoods")
public class OriginGoodsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public OriginGoodsServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginService service = new OriginServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		resp.getWriter().write(new Gson().toJson(service.getGoods(id)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
