package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InsuranceSlipService;
import com.eoulu.service.impl.InsuranceSlipServiceImpl;
import com.google.gson.Gson;

@WebServlet("/DownloadInsurancePolicy")
public class DownloadInsurancePolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DownloadInsurancePolicyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InsuranceSlipService service = new InsuranceSlipServiceImpl();
		response.getWriter().write(new Gson().toJson(service.DownloadInsurancePolicy(request)));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
