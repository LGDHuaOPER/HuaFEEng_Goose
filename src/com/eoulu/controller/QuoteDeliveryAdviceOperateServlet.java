package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.service.DeliveryAdviceService;
import com.eoulu.service.impl.DeliveryAdviceServiceImpl;
import com.eoulu.util.MethodUtil;

/**
 * Servlet implementation class QuoteDeliveryAdviceOperateServlet
 */
@WebServlet("/QuoteDeliveryAdviceOperate")
public class QuoteDeliveryAdviceOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuoteDeliveryAdviceOperateServlet() {
        super();
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
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		MethodUtil util = new MethodUtil();
		List<Map<String, String>> ls = null;
		String file = "";
		try {
			ls = util.getForm(filePath, request, tempPath);
			for(int i=0;i<ls.size();i++){
				if(i!=0){
					file += ";";
				}
				file += ls.get(i).get("fileName").toString();
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		response.getWriter().write(file);
		
	}

}
