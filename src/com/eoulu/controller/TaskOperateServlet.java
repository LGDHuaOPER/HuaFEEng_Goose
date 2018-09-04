package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.AuthorityResource;
import com.eoulu.entity.Task;
import com.eoulu.service.TaskService;
import com.eoulu.service.impl.TaskServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TaskOperateServlet
 */
@WebServlet("/TaskOperate")
public class TaskOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskOperateServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String PublishedDate = request.getParameter("PublishedDate") == null?"":request.getParameter("PublishedDate").trim();
		String LimitDate = request.getParameter("LimitDate") == null?"":request.getParameter("LimitDate").trim();
		String CompleteDate = request.getParameter("CompleteDate") == null?"":request.getParameter("CompleteDate").trim();
		String Title = request.getParameter("Title") == null?"":request.getParameter("Title").trim();
		String Description = request.getParameter("Description") == null?"":request.getParameter("Description").trim();
		String Classify = request.getParameter("Classify") == null?"":request.getParameter("Classify").trim();
		String Publisher = request.getParameter("Publisher") == null?"":request.getParameter("Publisher").trim();
		String ResponsibleMan = request.getParameter("ResponsibleMan") == null?"":request.getParameter("ResponsibleMan").trim();
		int Review = request.getParameter("Review") == null?0:Integer.parseInt(request.getParameter("Review"));
		String Progress = request.getParameter("Progress") == null?"":request.getParameter("Progress").trim();
		String IsCompleted = request.getParameter("IsCompleted") == null?"":request.getParameter("IsCompleted").trim();
		String RefNo = request.getParameter("RefNo") == null?"":request.getParameter("RefNo").trim();
		Task task = new Task();
		task.setClassify(Classify);
		task.setCompleteDate(CompleteDate);
		task.setDescription(Description);
		task.setID(ID);
		task.setIsCompleted(IsCompleted);
		task.setLimitDate(LimitDate);
		task.setProgress(Progress);
		task.setPublishedDate(PublishedDate);
		task.setPublisher(Publisher);
		task.setRefNo(RefNo);
		task.setResponsibleMan(ResponsibleMan);
		task.setReview(Review);
		task.setTitle(Title);
		TaskService service = new TaskServiceImpl();
		boolean result = false;
		switch (type) {
		case "add":
			result = service.insert(task);
			break;

		case "update":
			result = service.update(task);
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String ResponsibleMan = request.getParameter("ResponsibleMan") == null?"":request.getParameter("ResponsibleMan").trim();
		String Title = request.getParameter("Title") == null?"":request.getParameter("Title").trim();
		String PublishedDate = request.getParameter("PublishedDate") == null?"":request.getParameter("PublishedDate").trim();
		String LimitDate = request.getParameter("LimitDate") == null?"":request.getParameter("LimitDate").trim();
		String Description = request.getParameter("Description") == null?"":request.getParameter("Description").trim();
		Task task = new Task();
		task.setID(ID);
		task.setTitle(Title);
		task.setResponsibleMan(ResponsibleMan);
		task.setPublishedDate(PublishedDate);
		task.setLimitDate(LimitDate);
		task.setDescription(Description);
		
		AuthorityResource auth = new AuthorityResource();
		boolean authority = auth.isExist(request, "Tasks");
		if(authority){
			TaskService service = new TaskServiceImpl();
			response.getWriter().write(service.sendMail(task));
		}else{
			response.getWriter().write(new Gson().toJson("{message:没有对应权限}"));
		}
		
	}

}
