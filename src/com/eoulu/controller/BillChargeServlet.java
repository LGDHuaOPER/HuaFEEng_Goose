package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.BillCharge;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class BillChargeServlet
 */
@WebServlet("/BillCharge")
public class BillChargeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillChargeServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		List<Map<String, Object>> list = new OrderServiceImpl().getBillCharge(ID);
		response.getWriter().write(new Gson().toJson(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String InvoiceTitle = request.getParameter("InvoiceTitle") == null?"":request.getParameter("InvoiceTitle").trim();
		String TaxPayerIdentityNO = request.getParameter("TaxPayerIdentityNO") == null?"":request.getParameter("TaxPayerIdentityNO").trim();
		String RegisterAddress = request.getParameter("RegisterAddress") == null?"":request.getParameter("RegisterAddress").trim();
		String Telephone = request.getParameter("Telephone") == null?"":request.getParameter("Telephone").trim();
		String DepositBank = request.getParameter("DepositBank") == null?"":request.getParameter("DepositBank").trim();
		String Account = request.getParameter("Account") == null?"":request.getParameter("Account").trim();
		String InvoiceRecepter = request.getParameter("InvoiceRecepter") == null?"":request.getParameter("InvoiceRecepter").trim();
		String LinkAddress = request.getParameter("LinkAddress") == null?"":request.getParameter("LinkAddress");
		String LinkTel = request.getParameter("LinkTel") == null?"":request.getParameter("LinkTel");
		String LinkZipCode = request.getParameter("LinkZipCode") == null?"":request.getParameter("LinkZipCode").trim();
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		int SumOfQuantity = request.getParameter("SumOfQuantity") == null?0:Integer.parseInt(request.getParameter("SumOfQuantity"));
		double SumOfTaxPrice = request.getParameter("SumOfTaxPrice") == null?0.00:Double.parseDouble(request.getParameter("SumOfTaxPrice"));

		String Goods = request.getParameter("Goods") == null?"":request.getParameter("Goods");
		List<Map<String, Object>> list = new ArrayList<>();
		
		JSONArray array = JSONArray.fromObject(Goods);
		if(array.size()>0){
			JSONObject object = null;
			Map<String,Object> updateMap = null;
			
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new LinkedHashMap<>();
				updateMap.put("GoodsTaxName",(String)object.get("GoodsTaxName"));
				updateMap.put("MeasurementUnit", (String)object.get("MeasurementUnit"));
				updateMap.put("TypeSpecification", (String)object.get("TypeSpecification"));
				updateMap.put("Quantity", Integer.parseInt(object.get("Quantity").toString()));
				updateMap.put("UnitPrice", Double.parseDouble(object.get("UnitPrice").toString()));
				updateMap.put("SumOfMoney", Double.parseDouble(object.get("SumOfMoney").toString()));
				updateMap.put("TaxRate", Double.parseDouble(object.get("TaxRate").toString()));
				updateMap.put("TaxAmount", Double.parseDouble(object.get("TaxAmount").toString()));
				updateMap.put("TotalPriceTax", Double.parseDouble(object.get("TotalPriceTax").toString()));
				
				
				updateMap.put("OrderID",ID);
				updateMap.put("OrderInfoID", Integer.parseInt(object.get("OrderInfoID").toString()));
				list.add(updateMap);
			}
			
		}
		
		
		
		
		BillCharge charge = new BillCharge();
		charge.setAccount(Account);
		charge.setDepositBank(DepositBank);
		charge.setInvoiceRecepter(InvoiceRecepter);
		charge.setInvoiceTitle(InvoiceTitle);
		charge.setLinkAddress(LinkAddress);
		charge.setLinkTel(LinkTel);
		charge.setLinkZipCode(LinkZipCode);
		charge.setOrderID(ID);
		charge.setRegisterAddress(RegisterAddress);
		charge.setSaleGoods(list);
		charge.setSumOfQuantity(SumOfQuantity);
		charge.setSumOfTaxPrice(SumOfTaxPrice);
		charge.setTaxPayerIdentityNO(TaxPayerIdentityNO);
		charge.setTelephone(Telephone);
		OrderService service = new OrderServiceImpl();
		response.getWriter().write(service.saveBillCharge(charge));
	}

}
