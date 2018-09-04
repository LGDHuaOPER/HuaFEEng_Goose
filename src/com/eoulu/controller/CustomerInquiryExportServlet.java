package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CustomerInquiryService;
import com.eoulu.service.impl.CustomerInquiryServiceImpl;
import com.eoulu.util.DownloadUrl;

/**
 * Servlet implementation class CustomerInquiryExportServlet
 */
@WebServlet("/CustomerInquiryExport")
public class CustomerInquiryExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInquiryExportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String path = request.getServletContext().getRealPath("/")+"down/"+"客户询价信息表-"+df.format(new Date())+".xlsx";
		service.buildExcel(path);
		path = "down/"+"客户询价信息表-"+df.format(new Date())+".xlsx";
		URLDecoder.decode(path,"UTF-8");
		response.getWriter().write(path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerInquiryService service = new CustomerInquiryServiceImpl();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String path = new DownloadUrl().getRootUrl()+service.getFileName(id);
		
		URLDecoder.decode(path, "UTF-8");
		response.getWriter().write(path);
	}

}
