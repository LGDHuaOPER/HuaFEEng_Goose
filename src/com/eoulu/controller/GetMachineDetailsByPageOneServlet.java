package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
@WebServlet("/GetMachineDetailsByPageOne")
public class GetMachineDetailsByPageOneServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetMachineDetailsByPageOneServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MachineDetailsService service = new MachineDetailsServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify = req.getParameter("type1");
		String parameter = req.getParameter("searchContent1").trim();
		if(classify.equals("项目状态")){
			switch (parameter) {
			case "交付":
				parameter = "1"; 
				
				break;
			case "尾款":
				parameter = "2";
				break;
			case "完结":
				parameter = "3";
				break;

			default:
				break;
			}
		}
		System.out.println(parameter+"---"+classify);
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify, parameter));
		req.setAttribute("machine", service.getMachineDetailsByPageInOne(classify, parameter, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("machineCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify);
		req.setAttribute("parameter1", parameter);
		req.setAttribute("queryType", "singleSelect");
		//req.setAttribute("customers", new CustomerDao().getAllCustomer());
		req.getRequestDispatcher("WEB-INF//machine.jsp").forward(req, resp);
		
		
	}
	

}
