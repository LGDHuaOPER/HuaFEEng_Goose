package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.HuaEgoModelUtil;

/**
 * Servlet implementation class ExportHuaEgoExcelServlet
 */
@WebServlet("/ExportHuaEgoExcel")
public class ExportHuaEgoExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportHuaEgoExcelServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<Map<String,Object>> ls = new QuoteSystemServiceImpl().getHuaEgoModel(request);
		HuaEgoModelUtil util = new HuaEgoModelUtil();
		String url = "";
		if(request.getParameter("ExcelType").equals("Parts")){
			String path = request.getServletContext().getRealPath("/") + "down\\华为Ego配件.xlsx";
			 util.buildEgoParts(ls, path);
			 url = "down\\华为Ego配件.xlsx";
		}else{
			String path = request.getServletContext().getRealPath("/") + "down\\华为Ego整机.xlsx";
			util.buildEgoComplete(ls, path);
			 url = "down\\华为Ego整机.xlsx";
		}
		URLDecoder.decode(url,"UTF-8");
		response.getWriter().write(url);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
