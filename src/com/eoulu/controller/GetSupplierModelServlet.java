package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CommodityService;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetSupplierModelServlet
 */
@WebServlet(description = "修改中的商品管理与邮件模板", urlPatterns = { "/GetSupplierModel" })
public class GetSupplierModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSupplierModelServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommodityService service = new CommodityServiceImpl();
		int id = Integer.parseInt(request.getParameter("Commodity"));
		response.getWriter().write(new Gson().toJson(service.getAllInfo(id)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
