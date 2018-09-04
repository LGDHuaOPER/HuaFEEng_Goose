package com.eoulu.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuotationService;
import com.eoulu.service.impl.EquipmentServiceImpl;
import com.eoulu.service.impl.QuotationServiceImpl;
import com.eoulu.util.PDFUtil;

/**
 * Servlet implementation class ExportQuotation
 */
@WebServlet("/ExportQuotation")
public class ExportQuotationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExportQuotationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuotationService quotationService = new QuotationServiceImpl();
		
		String QuotationID=request.getParameter("QuotationID");
		String OrderID =request.getParameter("OrderID");
		String classify=request.getParameter("classify");
		//获取报价单关键信息
		List<Map<String,Object>> Quotations=quotationService.getQuotationByQuotationID(QuotationID);
		//获取报价单设备信息
		List<Map<String, Object>> equipment=new EquipmentServiceImpl().getAllEquipmentByOrderID(OrderID);
		if(classify.equals("Show")){
			//ajax传集合？
			request.setAttribute("quotations", Quotations);
			//获取报价单的配置信息
			request.setAttribute("equipment", equipment);
			//response.getWriter().write("down//"+fileName+".pdf");
		}else if(classify.equals("Export")){
			
			Calendar calendar = Calendar.getInstance();
			String fileName = calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+calendar.get(Calendar.DAY_OF_MONTH);
			String path = request.getServletContext().getRealPath("/")+"down\\"+fileName+".pdf";
			PDFUtil.pdf(Quotations,equipment,path, fileName);
			response.getWriter().write("down//"+fileName+".pdf");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
