package com.eoulu.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.CommodityService;
import com.eoulu.service.impl.CommodityServiceImpl;
import com.eoulu.syn.PreviewCommodityPDF;
import com.eoulu.util.DownloadUrl;
import com.google.gson.Gson;
import com.sun.xml.internal.fastinfoset.sax.Properties;

/**
 * Servlet implementation class DownloadCommodityPurchaseRecordServlet
 */
@WebServlet("/DownloadCommodityPurchaseRecord")
public class DownloadCommodityPurchaseRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String rootUrl = new DownloadUrl().getRootUrl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadCommodityPurchaseRecordServlet() {
		
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		CommodityService service = new CommodityServiceImpl();
		int id = Integer.parseInt(request.getParameter("Commodity"));
		String type=request.getParameter("Preview")==null?"":request.getParameter("Preview");
		String path = service.getPath(id);
		String name = service.getFileName(id);
		String loadUrl = rootUrl+name;
		if(path==null || path.equals("")||path.equals("--")){
			loadUrl = "";
			if(request.getSession().getAttribute("filePath")==null){
				
			}else{
				path = request.getSession().getAttribute("filePath").toString();
				loadUrl = rootUrl +request.getSession().getAttribute("fileName").toString();
			}
		}
		System.out.println("loadUrl---"+loadUrl);
		if(type.equals("Preview") && !path.endsWith(".pdf")){
			PreviewCommodityPDF pdf = new PreviewCommodityPDF();
			System.out.println(path+":-----:"+loadUrl);
			loadUrl = pdf.exportCommodity(path,loadUrl);
		}
		URLDecoder.decode(loadUrl, "utf-8");
		response.getWriter().write(new Gson().toJson(loadUrl));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
