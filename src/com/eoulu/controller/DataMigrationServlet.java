package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.InsuranceDirectiveService;
import com.eoulu.service.impl.InsuranceDirectiveServiceImpl;
import com.eoulu.util.DBUtil;

@WebServlet("/DataMigration")
public class DataMigrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataMigrationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InsuranceDirectiveService service = new InsuranceDirectiveServiceImpl();
		DBUtil db = new DBUtil();
		String sql = "select ContractNO,DCNO,InvoiceUSD,FinalDestination,Address,Currency "
				+ "from t_transport_insurance_directive where 1=?";
		Object[] param = new Object[]{1};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String sql2 = "insert into t_transport_insurance_slip (ContractNO,DCNO,InvoiceAmount,Destination,PayableAt,Indemnity,OperatingTime,InsuranceType,InsuredName)"
				+ "values (?,?,?,?,?,?,?,?,?)";
		String Type = "InOutAir";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		for(int i =1;i<ls.size();i++) {
			Map<String,Object> map = ls.get(i);
			Object[] param2 = new Object[]{map.get("ContractNO").toString(),
					map.get("DCNO").toString(),
					map.get("InvoiceUSD").toString(),
					map.get("FinalDestination").toString(),
					map.get("Address").toString(),
					map.get("Currency").toString(),date,Type,"HK EOULU TRADING LIMITED"
					};
			DBUtil db2 = new DBUtil();
			db2.executeUpdate(sql2, param2);
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
