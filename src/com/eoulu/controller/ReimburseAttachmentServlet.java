package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Reimburse;
import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.ReimburseServiceImpl;
import com.eoulu.util.DownloadUrl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ReimburseAttachmentServlet
 */
@WebServlet("/ReimburseAttachment")
public class ReimburseAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimburseAttachmentServlet() {
        super();
     
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		switch (type) {
		case "export":
			ReimburseService service = new ReimburseServiceImpl();
			String ids = request.getParameter("ids")==null?"":request.getParameter("ids");
			String year = request.getParameter("Year")==null?"":request.getParameter("Year");
			String month = request.getParameter("Month")==null?"":request.getParameter("Month");
			response.getWriter().write(service.exportFile(ids,year,month));
			break;

		case "download":
			String ID = request.getParameter("ID");
			String name = request.getParameter("StaffName");
			String folderMonth = request.getParameter("FolderMonth");
			String filename = request.getParameter("FileName");
			
			String rootUrl = new DownloadUrl().getRootUrl();
			String filePath = rootUrl+"报销申请"+folderMonth+"-"+name+"-"+ID+"\\"+filename;
			response.getWriter().write(filePath);
			break;
		}

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String ElectronicInvoice = request.getParameter("ElectronicInvoice")==null?"":request.getParameter("ElectronicInvoice");
		String TravelPaper = request.getParameter("TravelPaper")==null?"":request.getParameter("TravelPaper");
		String Others = request.getParameter("Others")==null?"":request.getParameter("Others");	
		String AttachmentJson = request.getParameter("AttachmentJson")==null?"":request.getParameter("AttachmentJson");
		String folder = request.getParameter("Folder");
		String deleteFile = request.getParameter("DeleteFile");
		String isRevoke = request.getParameter("IsRevoke")==null?"":request.getParameter("IsRevoke");
		Reimburse reimburse = new Reimburse();
		reimburse.setID(ID);
		reimburse.setElectronicInvoice(ElectronicInvoice);
		reimburse.setTravelPaper(TravelPaper);
		reimburse.setOthers(Others);
		reimburse.setAttachmentJson(AttachmentJson);
		
		ReimburseService service = new ReimburseServiceImpl();
		response.getWriter().write(new Gson().toJson(service.saveAttachment(reimburse, folder,deleteFile,isRevoke)));
		
		
	}
	

}
