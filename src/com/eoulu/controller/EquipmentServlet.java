package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SupplierDao;
import com.eoulu.entity.Equipment;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;

/**
 * Servlet implementation class EquipmentServlet
 */
@WebServlet("/Equipment")
public class EquipmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EquipmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String queryType = null;
		String suppiler = null;
		String model = null;
		
		Equipment equipment = new Equipment();
		InformationBankService informationBankService = new InformationBankServiceImpl();
		
		
		Page page = new Page();
		int currentPage = 1;
		try{currentPage = Integer.parseInt(request.getParameter("currentPage").toString());}catch(Exception e){}
		page.setRows(10);
		page.setCurrentPage(currentPage);
		
		
		List<Map<String,Object>> ls;
		try{queryType= request.getParameter("query_type");}catch(Exception e){}
		try{suppiler= request.getParameter("supplier");}catch(Exception e){}
		try{model= request.getParameter("model");}catch(Exception e){}
		
		if(queryType==null || queryType.equals("common") ||  queryType.equals("") || (queryType.equals("mix")&&Integer.parseInt(suppiler)==15)){
			page.setRecordCounts(informationBankService.getEquipmentCountsByCommon());
			request.setAttribute("suppliers", new SupplierDao().getAllSupplier());
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageCounts", page.getPageCounts());
			request.setAttribute("equipments", informationBankService.EquipmentQueryByCommon(page));
			request.setAttribute("queryType", "common");
			request.setAttribute("supplierName", suppiler);
			new AccessStatistics().operateAccess(request, "资料库");
			
		}else if(queryType.equals("mix")){
			page.setRecordCounts(informationBankService.getEquipmentCountsBySupiler(Integer.parseInt(suppiler), model));
			request.setAttribute("suppliers", new SupplierDao().getAllSupplier());
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageCounts", page.getPageCounts());
			request.setAttribute("equipments", informationBankService.EquipmentQueryBySuppiler(page, Integer.parseInt(suppiler), model));
			request.setAttribute("queryType", "mix");
			request.setAttribute("modelName", model);
			request.setAttribute("supplierName", suppiler);
		}
		request.getRequestDispatcher("WEB-INF\\equipment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
