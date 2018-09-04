package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.eoulu.commonality.Page;
import com.eoulu.dao.CertificateQualityDao;
import com.eoulu.entity.Quality;
import com.eoulu.service.CertificateQaulityService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.DBUtil;

public class CertificateQualityServiceImpl implements CertificateQaulityService{

	public static final Map<String, Object> classify_MAP; 


	static{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "Customer");
		map.put("合同号", "ContractNO");
		map.put("订单号", "PONO");
		map.put("信用证号", "DCNO");
	
		classify_MAP = map;
	}
	
	@Override
	public int getAllCounts() {
		
		return new CertificateQualityDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getCertificateQuality(Page page) {
		
		return new CertificateQualityDao().getCertificateQuantity(page);
	}

	@Override
	public boolean addCertificateQuality(HttpServletRequest request) {
		Quality quantity = new Quality();
		quantity.setContractNO(request.getParameter("ContractNO"));
		quantity.setDate(request.getParameter("Date"));
		quantity.setDCNO(request.getParameter("DCNO"));
		quantity.setPONO(request.getParameter("PONO"));
		CertificateQualityDao dao = new CertificateQualityDao();
		boolean flag = dao.insert(quantity);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-质量证明";
			String description = "新增-"+request.getParameter("ContractNO");
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean updateCertificateQuality(HttpServletRequest request) {
		Quality quantity = new Quality();
		quantity.setContractNO(request.getParameter("ContractNO"));
		quantity.setDate(request.getParameter("Date"));
		quantity.setDCNO(request.getParameter("DCNO"));
		quantity.setPONO(request.getParameter("PONO"));
		quantity.setID(Integer.parseInt(request.getParameter("ID")));
		CertificateQualityDao dao = new CertificateQualityDao();
		boolean flag = dao.updateCertificateQuantity(quantity);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-质量证明";
			String description = "新增-"+request.getParameter("ContractNO");
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean deleteCertificateQuality(int id) {
		
		return new CertificateQualityDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_certificate_quantity.ID) from t_certificate_quantity "
				+ "left join t_order on t_order.ContractNo=t_certificate_quantity.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_certificate_quantity."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
		
	}

	@Override
	public List<Map<String, Object>> getCertificateQualityByClassifyInOne(String classify, Object parameter,
			Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select t_order.Customer,t_order.ContractTitle,t_certificate_quantity.ContractNO,t_certificate_quantity.Date,"
				+ "t_certificate_quantity.DCNO,t_certificate_quantity.ID,t_certificate_quantity.PONO "
				+ "from t_certificate_quantity left join t_order on t_certificate_quantity.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_certificate_quantity."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by OperatingTime desc limit ?,?";
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
		return new CertificateQualityDao().getCertificateQuantity(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_certificate_quantity.ID) from t_certificate_quantity "
				+ "left join t_order on t_order.ContractNo=t_certificate_quantity.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_certificate_quantity."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_certificate_quantity."+classify_MAP.get(classify2)+" like ?";
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
	public List<Map<String, Object>> getCertificateQualityByClassifyInTwo(String classify1, Object parameter1,
			String classify2, Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 ="select t_order.Customer,t_order.ContractTitle,t_certificate_quantity.ContractNO,t_certificate_quantity.Date,"
				+ "t_certificate_quantity.DCNO,t_certificate_quantity.ID,t_certificate_quantity.PONO "
				+ "from t_certificate_quantity left join t_order on t_certificate_quantity.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_certificate_quantity."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_certificate_quantity."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_certificate_quantity.OperatingTime desc limit ?,?";
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
		
		return new CertificateQualityDao().getCertificateQuantity(sql, param);
	}

	

}
