package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.OriginalQuotation;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.OriginalQuotationService;
import com.eoulu.service.impl.OriginalQuotationServiceImpl;
import com.eoulu.util.DocumentUploadUtilY;
import com.google.gson.Gson;

/**
 * Servlet implementation class OriginalQuotationServlet
 */
@WebServlet("/OriginalQuotation")
public class OriginalQuotationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OriginalQuotationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String queryType = request.getParameter("QueryType") == null?"common":request.getParameter("QueryType");
			String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
			String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
			String column2 = request.getParameter("Column2") == null?"":request.getParameter("Column2");
			String content2 = request.getParameter("Content2") == null?"":request.getParameter("Content2");
			String currentPage = request.getParameter("CurrentPage");
			OriginalQuotationService service = new OriginalQuotationServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(queryType, column1, content1, column2, content2));
			List<Map<String, Object>> list = service.getDataByPage(page, queryType, column1, content1, column2, content2);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "原厂报价单");
			request.getRequestDispatcher("WEB-INF//origQuotSheet.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		int CustomerID = request.getParameter("CustomerID") == null?0:Integer.parseInt(request.getParameter("CustomerID"));
		String Model = request.getParameter("Model") == null?"":request.getParameter("Model").trim();
		String ChuckType = request.getParameter("ChuckType") == null?"":request.getParameter("ChuckType");
		String TemperatureRequirement = request.getParameter("TemperatureRequirement") == null?"":request.getParameter("TemperatureRequirement");
		String Microscope = request.getParameter("Microscope") == null?"":request.getParameter("Microscope");
		String VersionNumber = request.getParameter("VersionNumber") == null?"":request.getParameter("VersionNumber");
		String FilePath = request.getParameter("FilePath") == null?"":request.getParameter("FilePath");
		String Remarks = request.getParameter("Remarks") == null?"":request.getParameter("Remarks");
		String QuoteTotal = request.getParameter("QuoteTotal") == null?"":request.getParameter("QuoteTotal");
		OriginalQuotation originalQuotation = new OriginalQuotation();
		originalQuotation.setID(ID);
		originalQuotation.setCustomerID(CustomerID);
		originalQuotation.setModel(Model);
		originalQuotation.setChuckType(ChuckType);
		originalQuotation.setTemperatureRequirement(TemperatureRequirement);
		originalQuotation.setMicroscope(Microscope);
		originalQuotation.setVersionNumber(VersionNumber);
		originalQuotation.setFilePath(FilePath);
		originalQuotation.setRemarks(Remarks);
		originalQuotation.setQuoteTotal(QuoteTotal);
		
		OriginalQuotationService service = new OriginalQuotationServiceImpl();
		
		boolean result = false;
		switch (type) {
		case "add":
			result = service.insert(originalQuotation);
			break;

		case "update":
			result = service.update(originalQuotation);
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
	
	}

}
