package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
import com.google.gson.Gson;

@WebServlet("/MachineDetailsOperate")
public class MachineDetailsOperateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MachineDetailsOperateServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String classify = req.getParameter("classify");
		boolean flag = false;
		MachineDetailsService service = new MachineDetailsServiceImpl();
		switch (classify) {
		case "Add":
			flag = service.machineDetailsAdd(req);
			break;
		case "Delete":
			flag = service.machineDetailsDelete(req);
			break;
		case "Modify":
			flag = service.machineDetailsUpdate(req);
			break;
		}
		
		resp.getWriter().write(new Gson().toJson("{message:" + flag + "}"));
	}

}
