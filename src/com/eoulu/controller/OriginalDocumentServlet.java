package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.InstallationManualService;
import com.eoulu.service.OriginalDocumentService;
import com.eoulu.service.impl.InstallationManualServiceImpl;
import com.eoulu.service.impl.OriginalDocumentServiceImpl;
@WebServlet("/OriginalDocument")
public class OriginalDocumentServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public OriginalDocumentServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OriginalDocumentService service = new OriginalDocumentServiceImpl();
		String type = req.getParameter("Type");
		Page page = new Page();
		int currentPage = Integer.parseInt(req.getParameter("currentPage")==null ? "1":req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		page.setRecordCounts(service.getAllcounts(type));
		req.setAttribute("document", service.getOriginalDocument(page, type));
		req.setAttribute("queryType", "common");
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("documentCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.getRequestDispatcher("WEB-INF//documentUpload.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
