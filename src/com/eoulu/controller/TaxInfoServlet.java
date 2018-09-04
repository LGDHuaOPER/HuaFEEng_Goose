package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.TaxInfo;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class TaxInfoServlet
 */
@WebServlet("/TaxInfo")
public class TaxInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaxInfoServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		InformationBankService service = new InformationBankServiceImpl();
		if(ID != 0){
			response.getWriter().write(new Gson().toJson(service.getTaxInfo(ID)));
		}else{
			String customerName = request.getParameter("Customer") == null?"":request.getParameter("Customer");
			String contact = request.getParameter("Contact") == null?"":request.getParameter("Contact");
			response.getWriter().write(new Gson().toJson(service.getTaxInfoForBill(customerName, contact)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		String InvoiceTitle = request.getParameter("InvoiceTitle");
		String TaxPayerIdentityNO = request.getParameter("TaxPayerIdentityNO");
		String RegisterAddress = request.getParameter("RegisterAddress");
		String Telephone = request.getParameter("Telephone");
		String DepositBank = request.getParameter("DepositBank");
		String Account = request.getParameter("Account");
		String InvoiceRecepter = request.getParameter("InvoiceRecepter");
		String LinkAddress = request.getParameter("LinkAddress");
		String LinkTel = request.getParameter("LinkTel");
		String LinkEmail = request.getParameter("LinkEmail");
		TaxInfo taxInfo = new TaxInfo();
		taxInfo.setID(ID);
		taxInfo.setInvoiceTitle(InvoiceTitle);
		taxInfo.setTaxPayerIdentityNO(TaxPayerIdentityNO);
		taxInfo.setRegisterAddress(RegisterAddress);
		taxInfo.setTelephone(Telephone);
		taxInfo.setDepositBank(DepositBank);
		taxInfo.setAccount(Account);
		taxInfo.setInvoiceRecepter(InvoiceRecepter);
		taxInfo.setLinkAddress(LinkAddress);
		taxInfo.setLinkTel(LinkTel);
		taxInfo.setLinkEmail(LinkEmail);
		InformationBankService service = new InformationBankServiceImpl();
		response.getWriter().write(service.saveTaxInfo(taxInfo));
		
		
	}

}
