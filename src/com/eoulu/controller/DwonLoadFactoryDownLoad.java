package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.google.gson.Gson;

@WebServlet("/dwonLoad")
public class DwonLoadFactoryDownLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DwonLoadFactoryDownLoad() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OriginFactoryService service = new OriginFactoryServiceImpl();
		response.getWriter().write(service.downLoadFiles(request));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
