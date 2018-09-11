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
import com.eoulu.entity.StandardProduct;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.StandardProductService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.StandardProductServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class StandardProductServlet
 */
@WebServlet("/StandardProduct")
public class StandardProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StandardProductServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			StandardProductService service = new StandardProductServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts(queryType, column1, content1, column2, content2));
			List<Map<String, Object>> list = service.getAllData(page, queryType, column1, content1, column2, content2);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
			
		}else{
			new AccessStatistics().operateAccess(request, "标准产品");
			request.getRequestDispatcher("WEB-INF//StandardProduct.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Operate") == null?"":request.getParameter("Operate");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String Model = request.getParameter("Model") == null?"":request.getParameter("Model").trim();
		String Title = request.getParameter("Title") == null?"":request.getParameter("Title").trim();
		String Type = request.getParameter("Type") == null?"":request.getParameter("Type").trim();
		String Machine = request.getParameter("Machine") == null?"":request.getParameter("Machine").trim();
		String InstallInstructions = request.getParameter("InstallInstructions") == null?"":request.getParameter("InstallInstructions");
		String TestInstructions = request.getParameter("TestInstructions") == null?"":request.getParameter("TestInstructions");
		String CheckingReport = request.getParameter("CheckingReport") == null?"":request.getParameter("CheckingReport");
		String DocumentIntegrity = request.getParameter("DocumentIntegrity") == null?"":request.getParameter("DocumentIntegrity");
		String ProductInstructions = request.getParameter("ProductInstructions")==null?"":request.getParameter("ProductInstructions");
		String UpdateTime = request.getParameter("UpdateTime")==null?"":request.getParameter("UpdateTime");
		StandardProduct product = new StandardProduct();
		product.setID(ID);
		product.setModel(Model);
		product.setTitle(Title);
		product.setType(Type);
		product.setInstallInstructions(InstallInstructions);
		product.setMachine(Machine);
		product.setTestInstructions(TestInstructions);
		product.setDocumentIntegrity(DocumentIntegrity);
		product.setCheckingReport(CheckingReport);
		product.setProductInstructions(ProductInstructions);
		product.setUpdateTime(UpdateTime);
	
		StandardProductService service = new StandardProductServiceImpl();
		LogInfoService service2 = new LogInfoServiceImpl();
	
		boolean result = true;
		switch (type) {
		case "add":
			result = service.insert(product);
			if(result){
				service2.insert(request, "服务部-标准产品", "新增");
			}
			response.getWriter().write(new Gson().toJson(result));
			break;

		case "update":
		
			result = service.update(product);
			if(result){
				service2.insert(request, "服务部-标准产品", "修改");
			}
			response.getWriter().write(new Gson().toJson(result));
			break;
		}
		
	}

}
