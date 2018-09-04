package com.eoulu.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * Servlet implementation class GetLabPictureServlet
 */
@WebServlet("/GetLabPicture")
public class GetLabPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLabPictureServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("FileName");
		
	
        FileInputStream fis = null;
        try{
        	 fis = new FileInputStream("E:\\LogisticsFile\\File\\Lab\\"+fileName); // 以byte流的方式打开文件
        }catch(FileNotFoundException e){
        	response.getWriter().write("图片已被删除！");	
        }
        
        response.setCharacterEncoding("UTF-8");
        response.reset(); 
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isJPG = StringUtils.isBlank(extension) || extension.equalsIgnoreCase("jpg");
        extension = isJPG ? "jpeg" : extension;
        response.setContentType("image/".concat(extension).concat(";charset=UTF-8"));
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        int i = fis.available(); // 得到文件大小
        byte data[] = new byte[i];
        fis.read(data); // 读数据
        fis.close();
        ServletOutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}


