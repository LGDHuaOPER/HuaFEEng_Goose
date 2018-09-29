 package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SoftwareProjectService;
import com.eoulu.service.impl.SoftwareProjectServiceImpl;

@WebServlet("/SoftwareProjectOperate")
public class SoftwareProjectOperateServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type") == null?"":req.getParameter("type");
		SoftwareProjectService service = new SoftwareProjectServiceImpl();
		if(type.equals("add")){
			resp.getWriter().write(service.insert(req));
		}else if(type.equals("update")){
			resp.getWriter().write(service.update(req));
		}else if(type.equals("delete")){
			int id = Integer.parseInt(req.getParameter("ID"));
			resp.getWriter().write(service.delete(id));
		}
	}

}
