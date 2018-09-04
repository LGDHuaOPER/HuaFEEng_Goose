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
import com.eoulu.entity.ProductDrawings;
import com.eoulu.entity.Scheme;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.ApplicationGalleryService;
import com.eoulu.service.impl.ApplicationGalleryServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ApplicationGalleryServlet
 */
@WebServlet("/ApplicationGallery")
public class ApplicationGalleryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationGalleryServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String category = request.getParameter("Category") == null?"scheme":request.getParameter("Category");
 			String queryType = request.getParameter("QueryType") == null?"common":request.getParameter("QueryType");
			String column1 = request.getParameter("Column1") == null?"":request.getParameter("Column1");
			String content1 = request.getParameter("Content1") == null?"":request.getParameter("Content1");
			String column2 = request.getParameter("Column2") == null?"":request.getParameter("Column2");
			String content2 = request.getParameter("Content2") == null?"":request.getParameter("Content2");
			String currentPage = request.getParameter("CurrentPage");
			ApplicationGalleryService service = new ApplicationGalleryServiceImpl();
			Page page = new Page();
			List<Map<String, Object>> list = null;
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			if(category.equals("scheme")){
				page.setRecordCounts(service.getSchemeCount(queryType, column1, content1, column2, content2));
				list = service.getSchemeByPage(page, queryType, column1, content1, column2, content2);
			}else{
				page.setRecordCounts(service.getDrawingsCount(category, queryType, column1, content1, column2, content2));
				list = service.getDrawingsByPage(page, category, queryType, column1, content1, column2, content2);
			}
			

			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "研发图库");
			request.getRequestDispatcher("WEB-INF//ApplicationGallery.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("Category") == null?"scheme":request.getParameter("Category");
		String type = request.getParameter("Type");
		
		ApplicationGalleryService service = new ApplicationGalleryServiceImpl();
		boolean result = false;	
		
		if(category.equals("scheme")){
			int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
			String ApplicationType = request.getParameter("ApplicationType");
			String CustomerName = request.getParameter("CustomerName");
			String SchemeName = request.getParameter("SchemeName");
			String MachineModel = request.getParameter("MachineModel");
			String MakeTime = request.getParameter("MakeTime");
			String CADdrawings = request.getParameter("CADdrawings");
			String PDFdrawings = request.getParameter("PDFdrawings");
			String Remark = request.getParameter("Remark");
			
			Scheme scheme = new Scheme();
			scheme.setID(ID);
			scheme.setApplicationType(ApplicationType);
			scheme.setCustomerName(CustomerName);
			scheme.setSchemeName(SchemeName);
			scheme.setMachineModel(MachineModel);
			scheme.setMakeTime(MakeTime);
			scheme.setCADdrawings(CADdrawings);
			scheme.setPDFdrawings(PDFdrawings);
			scheme.setRemark(Remark);
			
			switch (type) {
			case "add":
				result = service.addScheme(scheme);
				break;

			case "update":
				result = service.updateScheme(scheme);
				break;
			}
		}else{
			int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
			String ProductName = request.getParameter("ProductName");
			String ProductType = request.getParameter("ProductType");
		    String Machine = request.getParameter("Machine");
		    String VersionNO = request.getParameter("VersionNO");
			String DesignTime = request.getParameter("DesignTime");
			String Usage = request.getParameter("Usage");
			String CADdrawings = request.getParameter("CADdrawings");
			String PDFdrawings = request.getParameter("PDFdrawings");
			String Remark = request.getParameter("Remark");
			
			ProductDrawings drawings = new ProductDrawings();
			drawings.setID(ID);
			drawings.setCategory(category);
			drawings.setProductName(ProductName);
			drawings.setProductType(ProductType);
			drawings.setMachine(Machine);
			drawings.setVersionNO(VersionNO);
			drawings.setDesignTime(DesignTime);
			drawings.setUsage(Usage);
			drawings.setCADdrawings(CADdrawings);
			drawings.setPDFdrawings(PDFdrawings);
			drawings.setRemark(Remark);
			
			switch (type) {
			case "add":
				result = service.addProduct(drawings);
				break;

			case "update":
				result = service.updateProduct(drawings);
				break;
			}
			
		}
		
		response.getWriter().write(new Gson().toJson(result));
		
		
		
	}

}
