package com.eoulu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.eoulu.util.FileUtil;



/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUpload")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	//	FileUtil fu = new FileUtil(path1);
		
		
		Iterator<FileItem> it = items.iterator();
		while(it.hasNext()){
			FileItem item = it.next();
			if(!item.isFormField()){
				String path = request.getServletContext().getRealPath("/")+"WEB-INF\\upload\\"+item.getName();
				FileUtil fu = new FileUtil(path);
				InputStream is = null;
				try {
					is = item.getInputStream();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				fu.saveFile(is);
			}
		}
		
		try {
			response.getWriter().write("保存成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
