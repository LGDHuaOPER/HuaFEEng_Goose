package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CommonProblemService;
import com.eoulu.service.impl.CommonProblemServiceImpl;
import com.eoulu.syn.PreviewSoftwarePDF;
import com.eoulu.util.DownloadUrl;

/**
 * Servlet implementation class CommonProblemDownloadServlet
 */
@WebServlet(description = "常见问题下载", urlPatterns = { "/CommonProblemDownload" })
public class CommonProblemDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonProblemDownloadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CommonProblemService service = new CommonProblemServiceImpl();
		int id = Integer.parseInt(request.getParameter("ID"));
		
		String path = service.getCommonProblemByID(id);
		String downloadPath = new DownloadUrl().getRootUrl()+path.substring(path.lastIndexOf("\\")+1);//相对路径
		String Preview = request.getParameter("Preview")==null?"":request.getParameter("Preview");
		if(Preview.equals("Preview")){
			PreviewSoftwarePDF pdf = new PreviewSoftwarePDF();
			downloadPath = pdf.exportSoftware(path,path);
		}
		System.out.println(downloadPath);
		response.getWriter().write(downloadPath);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
