package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.InstallationImageService;
import com.eoulu.service.InstallationManualService;
import com.eoulu.service.InstallationReportLogService;
import com.eoulu.service.OriginalDocumentService;
import com.eoulu.service.RequestAchieveService;
import com.eoulu.service.SoftwareDepartmentService;
import com.eoulu.service.SoftwareVisitReportService;
import com.eoulu.service.impl.InstallationImageServiceImpl;
import com.eoulu.service.impl.InstallationManualServiceImpl;
import com.eoulu.service.impl.InstallationReportLogServiceImpl;
import com.eoulu.service.impl.OriginalDocumentServiceImpl;
import com.eoulu.service.impl.RequestAchieveServiceImpl;
import com.eoulu.service.impl.SoftwareDepartmentServiceImpl;
import com.eoulu.service.impl.SoftwareVisitReportServiceImpl;

@WebServlet("/DocumentUpload")
public class DocumentUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DocumentUploadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InstallationManualService service = new InstallationManualServiceImpl();
		InstallationReportLogService logService = new InstallationReportLogServiceImpl();
		InstallationImageService ImageService = new InstallationImageServiceImpl();
		RequestAchieveService achieveService = new RequestAchieveServiceImpl();
		OriginalDocumentService OriginalService = new OriginalDocumentServiceImpl();
		SoftwareDepartmentService softService = new SoftwareDepartmentServiceImpl();
		SoftwareVisitReportService visitService = new SoftwareVisitReportServiceImpl();
		String area = req.getParameter("Area");
		String year = req.getParameter("Year");
		String catalog = req.getParameter("catalog")==null?"Manual":req.getParameter("catalog");
		String type = req.getParameter("Type");
		String content = req.getParameter("content");
		String queryType = req.getParameter("queryType");
		Page page = new Page();
		AuthorityResource auth = new AuthorityResource();
		boolean flag = auth.isExist(req, "SoftwareDocument");
		boolean flag2 = auth.isExist(req, "OtherDocument");
		if(flag && !flag2){
			type = "Software";
		}
		int currentPage = Integer
				.parseInt(req.getParameter("currentPage") == null ? "1" : req.getParameter("currentPage"));
		page.setCurrentPage(currentPage);
		page.setRows(10);
		if (queryType == null || queryType.equals("common")) {
			new AccessStatistics().operateAccess(req, "文档管理");
			if(flag2){
				if (catalog.equalsIgnoreCase("Manual")) {
					page.setRecordCounts(service.getAllcounts(area,"InstallationManual"));
					req.setAttribute("manual", service.getInstallationManual(page, area,"InstallationManual"));
				}
				if (catalog.equalsIgnoreCase("Log")) {
					page.setRecordCounts(logService.getAllcounts(area, year));

					req.setAttribute("manual", logService.getInstallationReportLog(page, area, year));

				}
				if (catalog.equalsIgnoreCase("Image")) {
					page.setRecordCounts(ImageService.getAllcounts());
					req.setAttribute("manual", ImageService.getInstallationImage(page));

				}
				if (catalog.equalsIgnoreCase("Achieve")) {
					page.setRecordCounts(achieveService.getAllcounts(area));
					req.setAttribute("manual", achieveService.getRequestAchieve(page, area));

				}
				if (catalog.equalsIgnoreCase("Original")) {
					page.setRecordCounts(OriginalService.getAllcounts(type));
					req.setAttribute("manual", OriginalService.getOriginalDocument(page, type));

				}
				if (catalog.equalsIgnoreCase("Sales")) {
					page.setRecordCounts(OriginalService.getAllcounts(type));
					req.setAttribute("manual", OriginalService.getOriginalDocument(page, type));

				}
				if (catalog.equals("VisitReport")) {
					page.setRecordCounts(visitService.getCounts(content, year));
					req.setAttribute("manual", visitService.getAllData(page, content, year));
				}
				if(catalog.equals("Environment")){
					page.setRecordCounts(service.getAllcounts(area,"InstallationEnvironment"));
					req.setAttribute("manual", service.getInstallationManual(page, area,"InstallationEnvironment"));
				}
				if(catalog.equals("InstallationPackage")){
					page.setRecordCounts(service.getAllcounts("",catalog));
					req.setAttribute("manual", service.getInstallationManual(page, "",catalog));
				}
			}
			if(flag){
				if (catalog.equalsIgnoreCase("Software")) {
					page.setRecordCounts(softService.getAllcounts(type));
					req.setAttribute("manual", softService.getSoftwareDepartment(page, type,"DESC"));

				}
			}
			
			
		} else {
			if(flag2){
				if (catalog.equalsIgnoreCase("Manual")) {
					page.setRecordCounts(service.queryAllCounts(area, content,"InstallationManual"));
					req.setAttribute("manual", service.queryInstallationManual(page, area, content,"InstallationManual"));
				}
				if (catalog.equalsIgnoreCase("Log")) {
					page.setRecordCounts(logService.queryAllCounts(area, year, content));
					req.setAttribute("manual", logService.queryInstallationReportLog(page, area, year, content));

				}
				if (catalog.equalsIgnoreCase("Image")) {
					page.setRecordCounts(ImageService.queryAllCounts(content));
					req.setAttribute("manual", ImageService.queryInstallationImage(page, content));

				}
				if (catalog.equalsIgnoreCase("Achieve")) {
					page.setRecordCounts(achieveService.queryAllCounts(area, content));
					req.setAttribute("manual", achieveService.queryRequestAchieve(page, area, content));

				}
				if (catalog.equalsIgnoreCase("Original")) {
					page.setRecordCounts(OriginalService.queryAllCounts(type, content));
					req.setAttribute("manual", OriginalService.queryOriginalDocument(page, type, content));

				}
				if (catalog.equalsIgnoreCase("Sales")) {
					page.setRecordCounts(OriginalService.queryAllCounts(type, content));
					req.setAttribute("manual", OriginalService.queryOriginalDocument(page, type, content));

				}
				if (catalog.equals("VisitReport")) {
					page.setRecordCounts(visitService.getCounts(content, year));
					req.setAttribute("manual", visitService.getAllData(page, content, year));
				}
				if(catalog.equals("Environment")){
					page.setRecordCounts(service.queryAllCounts(area,content,"InstallationEnvironment"));
					req.setAttribute("manual", service.queryInstallationManual(page, area, content, "InstallationEnvironment"));
				}
				if(catalog.equals("InstallationPackage")){
					page.setRecordCounts(service.queryAllCounts("", content, queryType));
					req.setAttribute("manual", service.queryInstallationManual(page, "",content,catalog));
				}
			}
			if(flag){
				if (catalog.equalsIgnoreCase("Software")) {
					page.setRecordCounts(softService.getAllcounts(type));
					req.setAttribute("manual", softService.getSoftwareDepartment(page, type,"DESC"));

				}
			}
			
			req.setAttribute("content", content);
		}
		
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("manualCounts", page.getRecordCounts());
		req.setAttribute("area", area);
		req.setAttribute("catalog", catalog);
		req.setAttribute("year", year);
		req.setAttribute("type", type);
		req.setAttribute("queryType",queryType);
		req.setAttribute("software",flag);
		req.setAttribute("otherDocument",flag2);
		req.getRequestDispatcher("WEB-INF\\documentUpload.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
