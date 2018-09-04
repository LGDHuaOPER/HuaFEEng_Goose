package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;

@WebServlet("/GetCustomerInfo2")
public class GetCustomerInfo2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String query = req.getParameter("query");
		String Type1 = req.getParameter("Type1");
		String Contect1 = req.getParameter("Contect1");
		String currentPageStr = req.getParameter("currentPage");
		int currentPage = currentPageStr==null?1:Integer.parseInt(currentPageStr); 
		req.setAttribute("query", query);
		req.setAttribute("Type1", Type1);
		req.setAttribute("Contect1", Contect1);
		req.setAttribute("currentPage", currentPage);
		if(query.equals("mix")){
			String Type2 = req.getParameter("Type2");
			String Contect2 = req.getParameter("Contect2");
			req.setAttribute("Type2", Type2);
			req.setAttribute("Contect2", Contect2);
			//查询条数
			int Count = service.getCountByTwo(Type1,Contect1,Type2,Contect2);
			int pageCounts = Count%10==0?Count/10:Count/10+1;
			req.setAttribute("pageCounts", pageCounts);
			req.setAttribute("customers", service.getcustomersByTwo(Type1,Contect1,Type2,Contect2,currentPage));
			
		}else{
			int Count = service.getCountByOne(Type1,Contect1);
			int pageCounts = Count%10==0?Count/10:Count/10+1;
			req.setAttribute("pageCounts", pageCounts);
			req.setAttribute("customers", service.getcustomersByOne(Type1,Contect1,currentPage));
			
		}
		req.getRequestDispatcher("WEB-INF\\customer.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
