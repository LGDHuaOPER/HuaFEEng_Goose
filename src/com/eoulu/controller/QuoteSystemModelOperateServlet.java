package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.QuoteSystemModel;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QuoteSystemModelOperate")
public class QuoteSystemModelOperateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; 
	public QuoteSystemModelOperateServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		QuoteSystemModel model = new QuoteSystemModel();
		int id = Integer.parseInt(req.getParameter("ID")) ;
		int quoteID = Integer.parseInt(req.getParameter("QuoteID"));
		String type = req.getParameter("Type");
		String gifts  = req.getParameter("Gifts");
		String modelJson = req.getParameter("ModelJson")==null?"":req.getParameter("ModelJson");
		double subTotal = 0;
		System.out.println("Sub:"+req.getParameter("SubTotal"));
		if(req.getParameter("SubTotal")==null || req.getParameter("SubTotal").equals("")){
			subTotal = 0;
		}else{
			subTotal = Double.parseDouble(req.getParameter("SubTotal")); 
		}
		double finalTotal = 0;
		if(req.getParameter("FinalTotal")==null || req.getParameter("FinalTotal").equals("")){
			finalTotal = 0;
		}else{
			finalTotal = Double.parseDouble(req.getParameter("FinalTotal")); 
		}

		double giftsTotal = 0;
		if(req.getParameter("GiftsTotal")==null || req.getParameter("GiftsTotal").equals(" ")|| req.getParameter("GiftsTotal").equals("")){
			giftsTotal = 0;
		}else{
			giftsTotal = Double.parseDouble(req.getParameter("GiftsTotal")); 
		}
		model.setID(id);
		model.setQuoteID(quoteID);
		model.setType(type);
		model.setGifts(gifts);
		model.setSubTotal(subTotal);
		model.setGiftsTotal(giftsTotal);
		model.setFinalTotal(finalTotal);
		model.setModelJson(modelJson);
		if(id == 0){
			boolean flag = service.insertModel(model);
			if(flag){
				LogInfoService logs = new LogInfoServiceImpl();
				String JspInfo = "报价系统";
				String description = "保存-";
				if(type.equals("PartsRMB")){
					description+="RMB配件报价单模板"+quoteID;
				}
				if(type.equals("PartsUSD")){
					description+="USD配件报价单模板"+quoteID;
				}
				if(type.equals("CompleteUSD")){
					description+="USD整机报价单模板"+quoteID;
				}
				if(type.equals("CompleteRMB")){
					description+="RMB整机报价单模板"+quoteID;
				}
				
				logs.insert(req, JspInfo, description);
			}
			resp.getWriter().write(new Gson().toJson(flag));
		}else{
			boolean flag = service.modifyModel(model);
			if(flag){
				LogInfoService logs = new LogInfoServiceImpl();
				String JspInfo = "报价系统";
				String description = "修改-";
				if(type.equals("PartsRMB")){
					description+="RMB配件报价单模板"+service.getQuoteNumber(quoteID);
				}
				if(type.equals("PartsUSD")){
					description+="USD配件报价单模板"+service.getQuoteNumber(quoteID);
				}
				if(type.equals("CompleteUSD")){
					description+="USD整机报价单模板"+service.getQuoteNumber(quoteID);
				}
				if(type.equals("CompleteRMB")){
					description+="RMB整机报价单模板"+service.getQuoteNumber(quoteID);
				}
				
				logs.insert(req, JspInfo, description);
			}
			resp.getWriter().write(new Gson().toJson(flag));
		}
	}
	

}
