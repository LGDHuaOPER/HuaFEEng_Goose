package com.eoulu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.NonStandard;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.NonStandardService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.NonStandardReminderServiceImpl;
import com.eoulu.service.impl.NonStandardServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class NonStandardOperateServlet
 */
@WebServlet("/NonStandardOperate")
public class NonStandardOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NonStandardOperateServlet() {
        super();
    
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String ResponsibleMan = request.getParameter("ResponsibleMan").trim();
		String ProjectName = request.getParameter("ProjectName").trim();
		String PublishDate = request.getParameter("PublishDate");
		String StageExpectedDate = request.getParameter("StageExpectedDate").equals("")?"0000-00-00":request.getParameter("StageExpectedDate");
		String ProjectStage = request.getParameter("ProjectStage").trim();
		String ProjectProgress = request.getParameter("ProjectProgress");
		NonStandard nonStandard = new NonStandard();
		nonStandard.setResponsibleMan(ResponsibleMan);
		nonStandard.setProjectName(ProjectName);
		nonStandard.setPublishDate(PublishDate);
		nonStandard.setStageExpectedDate(StageExpectedDate);
		nonStandard.setProjectProgress(ProjectProgress);
		nonStandard.setProjectStage(ProjectStage);
		nonStandard.setID(ID);
		NonStandardService service = new NonStandardServiceImpl();
		String result = service.sendMail(nonStandard);
		LogInfoService logs = new LogInfoServiceImpl();
		String JspInfo = "非标项目-任务分配";
		String description = "发布-"+ProjectName+"-"+result;
		logs.insert(request, JspInfo, description);
		response.getWriter().write(result);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int CustomerID = request.getParameter("CustomerID") == null?0:Integer.parseInt(request.getParameter("CustomerID"));
		String ProjectName = request.getParameter("ProjectName")==null?"":request.getParameter("ProjectName").trim();
		String ProjectStage = request.getParameter("ProjectStage")==null?"":request.getParameter("ProjectStage").trim();
		String Issuer = request.getParameter("Issuer")==null?"":request.getParameter("Issuer").trim();
		String ResponsibleMan = request.getParameter("ResponsibleMan")==null?"":request.getParameter("ResponsibleMan").trim();
		String PublishDate = request.getParameter("PublishDate");
		String StageExpectedDate = request.getParameter("StageExpectedDate")==null?"":request.getParameter("StageExpectedDate");
		String ProjectExpectedDate = request.getParameter("ProjectExpectedDate")==null?"":request.getParameter("ProjectExpectedDate");
		
		String ProjectProgress = request.getParameter("ProjectProgress");
		String StageActualDate = request.getParameter("StageActualDate");
		NonStandard nonStandard = new NonStandard();
		nonStandard.setID(ID);
		nonStandard.setCustomerID(CustomerID);
		nonStandard.setProjectName(ProjectName);
		nonStandard.setProjectStage(ProjectStage);
		nonStandard.setIssuer(Issuer);
		nonStandard.setResponsibleMan(ResponsibleMan);
		nonStandard.setPublishDate(PublishDate);
		nonStandard.setStageExpectedDate(StageExpectedDate.equals("")?"0000-00-00":StageExpectedDate);
		nonStandard.setProjectExpectedDate(ProjectExpectedDate.equals("")?"0000-00-00":ProjectExpectedDate);
		nonStandard.setProjectProgress(ProjectProgress);
		nonStandard.setStageActualDate(StageActualDate);
		
		NonStandardService service = new NonStandardServiceImpl();
		boolean result = false;
		boolean exist = false;
		List<NonStandard> list = service.getRemindProject();
		for(int i = 0;i < list.size();i ++){
			if(ID == list.get(i).getID()){
				exist = true;
				break;
			}
		}
		ServletContext context = this.getServletContext();
		switch (type) {
		case "add":
			ID = service.insert(nonStandard);
			if(ID>0){
				result = true;
			}
			if(result){
				NonStandardReminderServiceImpl reminder = new NonStandardReminderServiceImpl();
				reminder.setNonStandard(nonStandard);
				context.setAttribute("reminder"+ID, reminder);
				reminder.load();
				LogInfoService logs = new LogInfoServiceImpl();
				String JspInfo = "非标项目-任务分配";
				String description = "新增-"+nonStandard.getProjectName();
				logs.insert(request, JspInfo, description);
			}
			break;

		case "update":
			
			result = service.update(nonStandard);
			if(result){
				if(exist){
					NonStandardReminderServiceImpl reminder = (NonStandardReminderServiceImpl) context.getAttribute("reminder"+ID);
					reminder.setNonStandard(nonStandard);
					reminder.load();
				}
				LogInfoService logs = new LogInfoServiceImpl();
				String JspInfo = "非标项目-任务分配";
				String description = "修改-"+nonStandard.getProjectName();
				logs.insert(request, JspInfo, description);
			}
			break;
		case "delete":
			result = service.deleteProject(ID);
			if(result){
				if(exist){
					NonStandardReminderServiceImpl reminder = (NonStandardReminderServiceImpl) context.getAttribute("reminder"+ID);
					reminder.getTimer().cancel();
					context.removeAttribute("reminder"+ID);
				}
				LogInfoService logs = new LogInfoServiceImpl();
				String JspInfo = "非标项目-任务分配";
				String description = "删除-"+nonStandard.getProjectName();
				logs.insert(request, JspInfo, description);
			
			}
			break;	
		}
		response.getWriter().write(new Gson().toJson(result));
		
		
	}

}
