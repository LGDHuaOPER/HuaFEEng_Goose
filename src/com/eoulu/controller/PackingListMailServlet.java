package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.PackingList;
import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.PackingListServiceImpl;

/**
 * Servlet implementation class PackingListMailServlet
 */
@WebServlet("/PackingListMail")
public class PackingListMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PackingListMailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String LogisticsCompany = request.getParameter("LogisticsCompany");
		String TrackingNO = request.getParameter("TrackingNO");
		String ToList = request.getParameter("ToList");
		String CopyList = request.getParameter("CopyList");
		String Subject = request.getParameter("Subject");
		String Content = request.getParameter("Content");
		String Attachment = request.getParameter("Attachment");
		
		PackingList pList = new PackingList();
		pList.setID(ID);
		pList.setLogisticsCompany(LogisticsCompany);
		pList.setTrackingNO(TrackingNO);
		pList.setToList(ToList);
		pList.setCopyList(CopyList);
		pList.setSubject(Subject);
		pList.setContent(Content);
		pList.setAttachment(Attachment);
		
		PackingListService service = new PackingListServiceImpl();
		response.getWriter().write(service.sendLogisticsMail(pList));
	}

}
