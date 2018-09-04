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
import com.eoulu.dao.CustomerDao;
import com.eoulu.dao.SingleProbabilityDao;
import com.eoulu.entity.UserAccess;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.AreaService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.SalesRepresentativeService;
import com.eoulu.service.UserAccessService;
import com.eoulu.service.impl.AreaServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.service.impl.SalesRepresentativeServiceImpl;
import com.eoulu.service.impl.UserAccessServiceImpl;
import com.eoulu.util.ExportExcelUtil;

/**
 * ��������ҳ��Ľӿڣ��˽ӿڿ��Դ��޲����ķ�ҳ
 */
@WebServlet("/Requirement")
public class RequirementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequirementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		Page page = new Page();
		RequirementService requirementService = new RequirementServiceImpl();
		SalesRepresentativeService representativeService = new SalesRepresentativeServiceImpl();
		AreaService areaService = new AreaServiceImpl();
		
		String str = request.getParameter("selected");
		try{currentPage = Integer.parseInt(request.getParameter("currentPage"));}catch(Exception e){currentPage=1;}
		String classify1 = request.getParameter("type1")==null?"":request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1")==null?"":request.getParameter("searchContent1");
		page.setCurrentPage(currentPage);
		page.setRows(10);
		if("singleSelect".equals(str)||str == null){
			new AccessStatistics().operateAccess(request, "需求统计");
			page.setRecordCounts(requirementService.getRequirementCounts(classify1,parameter1));
			request.setAttribute("classify1", classify1);
			request.setAttribute("parameter1", parameter1);
			request.setAttribute("queryType", "common");
			request.setAttribute("str", "singleSelect");
			request.setAttribute("requirements", requirementService.RequirementQuery(page,classify1,parameter1));
			
		}else if("mixSelect".equals(str)){
			String classify2 = request.getParameter("type2");
			String parameter2 = request.getParameter("searchContent2");
			page.setRecordCounts(requirementService.getRequirementCounts(classify1,parameter1,classify2,parameter2));
			request.setAttribute("requirements", requirementService.MixRequirementQuery(page,classify1,parameter1,classify2,parameter2));	
			request.setAttribute("classify1", classify1);
			request.setAttribute("parameter1", parameter1);
			request.setAttribute("classify2", classify2);
			request.setAttribute("parameter2", parameter2);
			request.setAttribute("str", "mixSelect");
			request.setAttribute("queryType", "mixSelect");
		}
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCounts", page.getPageCounts());
		request.setAttribute("salesStatics", requirementService.getStatisticsBySales("2018-01-01", "2019-01-01"));
		request.setAttribute("areasStatics", requirementService.getStatisticsByArea("2018-01-01", "2019-01-01"));
		request.setAttribute("brands", requirementService.getAllRequirementBrand());
		request.setAttribute("sources", requirementService.getAllRequirementSource());
		request.setAttribute("classifys", requirementService.getAllRequirementClassify());
		//request.setAttribute("queryArea", area);
		request.setAttribute("quotes", requirementService.getAllRequirementQuotes());
		request.setAttribute("sales", representativeService.getAllSalesRep());
		request.setAttribute("areas", areaService.getAllArea());
		//request.setAttribute("customers", new CustomerDao().getAllCustomer());
		request.setAttribute("probabilities", new SingleProbabilityDao().getAllProbability());
		request.getRequestDispatcher("WEB-INF\\requirement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("selected");
		String classify1 = request.getParameter("type1")==null?"":request.getParameter("type1");
		String parameter1 = request.getParameter("searchContent1")==null?"":request.getParameter("searchContent1");
		List<Map<String,Object>> ls = null;
		System.out.println(classify1+"---"+parameter1);
		if("singleSelect".equals(str)||str == null){
			ls = new RequirementServiceImpl().Export(classify1, parameter1, null, null);
		}else if("mixSelect".equals(str)){
			String classify2 = request.getParameter("type2")==null?"":request.getParameter("type2");
			String parameter2 = request.getParameter("searchContent2")==null?"":request.getParameter("searchContent2");
			ls = new RequirementServiceImpl().Export(classify1, parameter1, classify2, parameter2);
			System.out.println(classify2+"---"+parameter2);
		}
		String[] titleNames = new String[]{"序号","时间","客户名称","联系人","联系方式","区域","品牌","具体需求","需求类别","需求来源","是否报价","销售负责人","商务负责人","成单率","预计下单时间","是否成单","进度状况","报价单号","美金报价","人民币报价","报备状态","Ref.No"};
		Map<String,Object> maps = new HashMap<>();
		maps.put("时间", "RequirementDate");
		maps.put("客户名称", "CustomerName");
		maps.put("联系人", "Contact");
		maps.put("联系方式", "ContactInfo");
		maps.put("区域", "AreaName");
		maps.put("品牌", "Brand");
		maps.put("具体需求", "RequirementContent");
		maps.put("需求类别", "Classify");
		maps.put("需求来源", "Source");
		maps.put("是否报价", "WhetherQuotes");
		maps.put("销售负责人", "SalesMan");
		maps.put("商务负责人", "BusinessMan");
		maps.put("成单率", "SingleProbability");
		maps.put("预计下单时间", "ExceptedPayTime");
		maps.put("是否成单", "WhetherSingle");
		maps.put("进度状况", "ProgressStatus");
		maps.put("报价单号", "QuotationNumber");
		maps.put("美金报价", "USDQuotes");
		maps.put("人民币报价", "RMBQuotes");
		maps.put("报备状态", "Preparation");
		maps.put("Ref.No", "RefNo");
		String path = request.getServletContext().getRealPath("/")+"down/需求录入.xlsx";
		new ExportExcelUtil().ExportExcelUtil(ls, titleNames, maps, path, "需求录入");
		response.getWriter().write("down/需求录入.xlsx");
	}

}
