package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.AcceptanceDao;
import com.eoulu.entity.Acceptance;
import com.eoulu.service.AcceptanceService;
import com.eoulu.syn.ExportFAT;
import com.eoulu.util.DBUtil;

public class AcceptanceServiceImpl implements AcceptanceService{

	public static final Map<String, Object> classify_MAP; 
	static{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "Customer");
		map.put("合同号", "ContractNO");
		map.put("订单号", "PONO");
		map.put("信用证号", "DCNO");
		map.put("货物名称", "Model");
	 
		classify_MAP = map;
	}
	
	@Override
	public int getAllCounts() {
	
		return new AcceptanceDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getAcceptance(Page page) {
		
		return new AcceptanceDao().getAcceptance(page);
	}

	@Override
	public boolean addAcceptance(HttpServletRequest request) {
		
		Acceptance acceptance = new Acceptance();
		String ContractNO = request.getParameter("ContractNO");
		String DCNO = request.getParameter("DCNO");
		String CommoditySpecifications = request.getParameter("CommoditySpecifications");
		String Qty = request.getParameter("Qty");
		String Model = request.getParameter("Model");
		String PriceTerm = request.getParameter("PriceTerm");
		String Packing = request.getParameter("Packing");
		String TotalValue = request.getParameter("TotalValue");
		String Balance = request.getParameter("Balance");
		String Percent = request.getParameter("Percent");
		String EndUser = request.getParameter("EndUser");
		String Beneficiary = request.getParameter("Beneficiary");
		String Applicant = request.getParameter("Applicant");
		acceptance.setContractNO(ContractNO);
		acceptance.setDCNO(DCNO);
		acceptance.setCommoditySpecifications(CommoditySpecifications);
		acceptance.setQty(Qty);
		acceptance.setModel(Model);
		acceptance.setPriceTerm(PriceTerm);
		acceptance.setPacking(Packing);
		acceptance.setTotalValue(Double.parseDouble(TotalValue));
		acceptance.setBalance(Double.parseDouble(Balance));
		acceptance.setPercent(Double.parseDouble(Percent));
		acceptance.setEndUser(EndUser);
		acceptance.setBeneficiary(Beneficiary);
		acceptance.setApplicant(Applicant);
		AcceptanceDao dao = new AcceptanceDao();
		boolean flag = dao.insert(acceptance);
		if(flag){
			new LogInfoServiceImpl().insert(request, "商务部-FAT页面", "添加--"+Model+"--"+ContractNO);
		}
		return flag;
	}

	@Override
	public boolean updateAcceptance(HttpServletRequest request) {
		Acceptance acceptance = new Acceptance();
		String ContractNO = request.getParameter("ContractNO");
		String DCNO = request.getParameter("DCNO");
		String CommoditySpecifications = request.getParameter("CommoditySpecifications");
		String Qty = request.getParameter("Qty");
		String Model = request.getParameter("Model");
		String PriceTerm = request.getParameter("PriceTerm");
		String Packing = request.getParameter("Packing");
		String TotalValue = request.getParameter("TotalValue");
		String Balance = request.getParameter("Balance");
		String Percent = request.getParameter("Percent");
		String EndUser = request.getParameter("EndUser");
		String Beneficiary = request.getParameter("Beneficiary");
		String Applicant = request.getParameter("Applicant");
		int id = Integer.parseInt(request.getParameter("ID"));
		acceptance.setContractNO(ContractNO);
		acceptance.setDCNO(DCNO);
		acceptance.setCommoditySpecifications(CommoditySpecifications);
		acceptance.setQty(Qty);
		acceptance.setModel(Model);
		acceptance.setPriceTerm(PriceTerm);
		acceptance.setPacking(Packing);
		acceptance.setTotalValue(Double.parseDouble(TotalValue));
		acceptance.setBalance(Double.parseDouble(Balance));
		acceptance.setPercent(Double.parseDouble(Percent));
		acceptance.setEndUser(EndUser);
		acceptance.setBeneficiary(Beneficiary);
		acceptance.setApplicant(Applicant);
		acceptance.setID(id);
		AcceptanceDao dao = new AcceptanceDao();
		boolean flag = dao.updateAcceptance(acceptance);
		if(flag){
			new LogInfoServiceImpl().insert(request, "商务部-FAT页面", "修改--"+Model+"--"+ContractNO);
		}
		return flag;
	}

	@Override
	public boolean deleteAcceptance(int id) {
		
		return new AcceptanceDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_acceptance_certificate.ID) from t_acceptance_certificate "
				+ "left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_acceptance_certificate."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getAcceptanceByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select t_order.ContractTitle,t_order.Customer,t_acceptance_certificate.ContractNO,"
				+ "t_acceptance_certificate.Applicant,t_acceptance_certificate.Balance,"
				+ "t_acceptance_certificate.Beneficiary,t_acceptance_certificate.CommoditySpecifications,"
				+ "t_acceptance_certificate.DCNO,t_acceptance_certificate.EndUser,t_acceptance_certificate.ID,"
				+ "t_acceptance_certificate.Model,t_acceptance_certificate.OperatingTime,"
				+ "t_acceptance_certificate.Packing,t_acceptance_certificate.Percent,"
				+ "t_acceptance_certificate.PriceTerm,t_acceptance_certificate.Qty,"
				+ "t_acceptance_certificate.TotalValue "
				+ "from t_acceptance_certificate left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_acceptance_certificate."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by t_acceptance_certificate.OperatingTime desc limit ?,?";
		Object[] param;
		if(obj.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else{
			param = new Object[obj.length+2];
			for(int i=0 ; i< obj.length ; i++){
				param[i] = obj[i];
			}
			param[obj.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj.length+1] = page.getRows();
		}
		return new AcceptanceDao().getAcceptance(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_acceptance_certificate.ID) from t_acceptance_certificate "
				+ "left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_acceptance_certificate."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_acceptance_certificate."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2;
		Object[] param = new Object[obj1.length+obj2.length];
		param[0]=obj1[0];
		param[1]=obj2[0];

		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getAcceptanceByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select t_order.ContractTitle,t_order.Customer,t_acceptance_certificate.ContractNO,"
				+ "t_acceptance_certificate.Applicant,t_acceptance_certificate.Balance,"
				+ "t_acceptance_certificate.Beneficiary,t_acceptance_certificate.CommoditySpecifications,"
				+ "t_acceptance_certificate.DCNO,t_acceptance_certificate.EndUser,t_acceptance_certificate.ID,"
				+ "t_acceptance_certificate.Model,t_acceptance_certificate.OperatingTime,"
				+ "t_acceptance_certificate.Packing,t_acceptance_certificate.Percent,"
				+ "t_acceptance_certificate.PriceTerm,t_acceptance_certificate.Qty,"
				+ "t_acceptance_certificate.TotalValue "
				+ "from t_acceptance_certificate left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_acceptance_certificate."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_acceptance_certificate."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_acceptance_certificate.OperatingTime desc limit ?,?";
		Object[] param;
		if(obj1.length == 0 && obj2.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else if(obj1.length != 0 && obj2.length == 0){
			param = new Object[obj1.length+2];
			for(int i=0 ; i<obj1.length ; i++){
				param[i] = obj1[i];
			}
			param[obj1.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj1.length+1] = page.getRows();
		}else if(obj1.length == 0 && obj2.length != 0){
			param = new Object[obj2.length+2];
			for(int i=0 ; i<obj2.length ; i++){
				param[i] = obj2[i];
			}
			param[obj2.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj2.length+1] = page.getRows();
		}else{
			param = new Object[obj1.length+obj2.length+2];
			
			for(int i=0 ; i<param.length-2 ; i++){
				if(i == 0){
					param[i] = obj1[0];
				}
				if(i == 1){
					param[i] = obj2[0];
				}
				
			}
			param[param.length-2] = (page.getCurrentPage()-1)*page.getRows();
			param[param.length-1] = page.getRows();
		}
		return new AcceptanceDao().getAcceptance(sql, param);
	}
	
	public String exportReceiving(String contractNO){
		List<Map<String, Object>> list = new AcceptanceDao().getDataByContractNO(contractNO);
		if(list.size()>1){
			ExportFAT util = new ExportFAT();
			return util.exportFile(list);
		}else{
			return "无FAT";
		}
	}

}
