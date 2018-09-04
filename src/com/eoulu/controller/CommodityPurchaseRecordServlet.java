package com.eoulu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.dao.SupplierInfoDao;
import com.eoulu.entity.SupplierInfo;
import com.eoulu.service.CommodityService;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class CommodityPurchaseRecordServlet
 */
@WebServlet(description = "历史采购记录文件上传", urlPatterns = { "/CommodityPurchaseRecord" })
public class CommodityPurchaseRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommodityPurchaseRecordServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommodityServiceImpl service = new CommodityServiceImpl();
		String result = "上传失败！";
		boolean flag = false;
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String tempPath = "E:\\LogisticsFile\\File\\";
		try {
			Map<String,String> ls = service.getForm(filePath, request, tempPath);
			System.out.println(ls.get("SupplierID")+"---"+ls.get("filePath"));
			if(ls.get("SupplierID").equals("0")){
				request.getSession().setAttribute("fileName", ls.get("fileName"));
				request.getSession().setAttribute("filePath", ls.get("filePath"));
				request.getSession().setAttribute("Commodity", ls.get("Commodity"));
				result = "上传成功,最后记得保存！";
			}else{
				SupplierInfo info = new SupplierInfo();
				info.setID(Integer.parseInt(ls.get("SupplierID")));
				info.setCommodity(Integer.parseInt(ls.get("Commodity")));
				info.setPurchaseRecord(ls.get("fileName"));
				info.setPurchaseRecordPath(ls.get("filePath"));
				CommodityService ser = new CommodityServiceImpl();
				flag = ser.operateFile(info);
				if(flag){
					result = "上传成功！";
				}
			}
			System.out.println(result);
			response.getWriter().write(result);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}

}
