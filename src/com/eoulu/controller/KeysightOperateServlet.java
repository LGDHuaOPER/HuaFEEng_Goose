package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Keysight;
import com.eoulu.service.KeysightService;
import com.eoulu.service.impl.KeysightServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class KeysightOperateServlet
 */
@WebServlet("/KeysightOperate")
public class KeysightOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeysightOperateServlet() {
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
		String type = request.getParameter("Type") == null?"":request.getParameter("Type");
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		int PartnerID = request.getParameter("PartnerID").equals("")?0:Integer.parseInt(request.getParameter("PartnerID"));
		String DealID = request.getParameter("DealID") == null?"":request.getParameter("DealID").trim();
		String StreetAddress = request.getParameter("StreetAddress") == null?"":request.getParameter("StreetAddress").trim();
		int CustomerID = request.getParameter("CustomerID") == null?0:Integer.parseInt(request.getParameter("CustomerID"));
		String PostalCode = request.getParameter("PostalCode") == null?"":request.getParameter("PostalCode").trim();
		String CountryCode = request.getParameter("CountryCode") == null?"":request.getParameter("CountryCode").trim();
		String DealStatus = request.getParameter("DealStatus") == null?"":request.getParameter("DealStatus").trim();
		int WinProbability = request.getParameter("WinProbability").equals("")?0:Integer.parseInt(request.getParameter("WinProbability"));
		String KeysightReseller = request.getParameter("KeysightReseller") == null?"":request.getParameter("KeysightReseller").trim();
		String ShipToLocation = request.getParameter("ShipToLocation") == null?"":request.getParameter("ShipToLocation").trim();
		String KeysightName = request.getParameter("KeysightName") == null?"":request.getParameter("KeysightName").trim();
		String Line = request.getParameter("Line") == null?"":request.getParameter("Line").trim();
		int CommodityID = request.getParameter("CommodityID") == null?0:Integer.parseInt(request.getParameter("CommodityID"));
		int Qty = request.getParameter("Qty").equals("")?0:Integer.parseInt(request.getParameter("Qty"));
		String OrderDate = request.getParameter("OrderDate") .equals("")?"0000-00-00":request.getParameter("OrderDate").trim();
		String SalesOrder = request.getParameter("SalesOrder") == null?"":request.getParameter("SalesOrder").trim();
		String BookingDate = request.getParameter("BookingDate").equals("")?"0000-00-00":request.getParameter("BookingDate").trim();
		String CurrencyCode = request.getParameter("CurrencyCode") == null?"":request.getParameter("CurrencyCode").trim();
		String Status = request.getParameter("Status") == null?"":request.getParameter("Status").trim();
		double DealValue = request.getParameter("DealValue").equals("")?0.0:Double.parseDouble(request.getParameter("DealValue"));
		
		Keysight keysight = new Keysight();
		keysight.setID(ID);
		keysight.setPartnerID(PartnerID);
		keysight.setDealID(DealID);
		keysight.setStreetAddress(StreetAddress);
		keysight.setCustomerID(CustomerID);
		keysight.setPostalCode(PostalCode);
		keysight.setCountryCode(CountryCode);
		keysight.setDealStatus(DealStatus);
		keysight.setWinProbability(WinProbability);
		keysight.setKeysightReseller(KeysightReseller);
		keysight.setShipToLocation(ShipToLocation);
		keysight.setKeysightName(KeysightName);
		keysight.setLine(Line);
		keysight.setCommodityID(CommodityID);
		keysight.setQty(Qty);
		keysight.setOrderDate(OrderDate);
		keysight.setSalesOrder(SalesOrder);
		keysight.setBookingDate(BookingDate);
		keysight.setCurrencyCode(CurrencyCode);
		keysight.setStatus(Status);
		keysight.setDealValue(DealValue);
		
		KeysightService service = new KeysightServiceImpl();
		boolean result = false;
		switch (type) {
		case "add":
			result = service.insert(keysight);
			break;

		case "update":
			result = service.update(keysight);
			break;
		}
		response.getWriter().write(new Gson().toJson(result));
		
		
	}

}
