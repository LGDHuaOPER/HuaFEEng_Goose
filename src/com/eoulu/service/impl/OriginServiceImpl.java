package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.OriginDao;
import com.eoulu.entity.Origin;
import com.eoulu.entity.OriginGoods;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OriginService;
import com.eoulu.util.DBUtil;

public class OriginServiceImpl implements OriginService{

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
		
		return new OriginDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getOrigin(Page page) {
		
		return new OriginDao().getOrigin(page);
	}

	@Override
	public boolean addOrigin(HttpServletRequest request) {
		Origin origin = new Origin();
		String ManufactoryName = request.getParameter("ManufactoryName");
		String ManufactoryAddress = request.getParameter("ManufactoryAddress");
		String Goods = request.getParameter("Goods");
		String LCNO = request.getParameter("LCNO");
		String ContractNO = request.getParameter("ContractNO");
		String PurchaseOrderNO = request.getParameter("PurchaseOrderNO");
		String OriginCountry = request.getParameter("OriginCountry");
		String Tel = request.getParameter("Tel");
		String Date = request.getParameter("Date");
		String modelNumber = request.getParameter("ModelNumber");
		String quality = request.getParameter("Quality");
		origin.setManufactoryAddress(ManufactoryAddress);
		origin.setManufactoryName(ManufactoryName);
		origin.setGoods(Goods);
		origin.setLCNO(LCNO);
		origin.setContractNO(ContractNO);
		origin.setPurchaseOrderNO(PurchaseOrderNO);
		origin.setOriginCountry(OriginCountry);
		origin.setTel(Tel);
		origin.setDate(Date);
		origin.setModelNumber(modelNumber);
		origin.setQuality(quality);
		List<String> ls = new ArrayList<>();
		OriginDao dao = new OriginDao();
		boolean flag = dao.insert(origin);
		int id = dao.getOriginID(ContractNO);
//		if(request.getParameter("isExist").equals("yes")){
//			String[] modelNumber = request.getParameterValues("Model[]");
//			String[] quality = request.getParameterValues("Quality[]");
//			String[] name = request.getParameterValues("Name[]");
//			for(int i=0 ; i<modelNumber.length ; i++){
//				OriginGoods goods = new OriginGoods();
//				goods.setModel(modelNumber[i]);
//				goods.setQuality(quality[i]);
//				goods.setName(name[i]);
//				goods.setOriginID(id);
//				if(dao.insertGoods(goods)){
//					ls.add("true");
//				}else{
//					ls.add("false");
//				}
//			}
//		}
		if(flag && !ls.contains("false")){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-原产地证明";
			String description = "新增-"+ContractNO;
			log.insert(request, JspInfo, description);
			flag = true;
		}else{
			flag = false;
		}
		
		return flag;
	}

	@Override
	public boolean updateOrigin(HttpServletRequest request) {
		Origin origin = new Origin();
		String ManufactoryName = request.getParameter("ManufactoryName");
		String ManufactoryAddress = request.getParameter("ManufactoryAddress");
		String Goods = request.getParameter("Goods");
		String LCNO = request.getParameter("LCNO");
		String ContractNO = request.getParameter("ContractNO");
		String PurchaseOrderNO = request.getParameter("PurchaseOrderNO");
		String OriginCountry = request.getParameter("OriginCountry");
		String Tel = request.getParameter("Tel");
		String Date = request.getParameter("Date");
		String modelNumber = request.getParameter("ModelNumber");
		String quality = request.getParameter("Quality");
		int id = Integer.parseInt(request.getParameter("ID"));
		origin.setManufactoryAddress(ManufactoryAddress);
		origin.setManufactoryName(ManufactoryName);
		origin.setGoods(Goods);
		origin.setLCNO(LCNO);
		origin.setContractNO(ContractNO);
		origin.setPurchaseOrderNO(PurchaseOrderNO);
		origin.setOriginCountry(OriginCountry);
		origin.setTel(Tel);
		origin.setDate(Date);
		origin.setModelNumber(modelNumber);
		origin.setQuality(quality);
		origin.setID(id);
		List<String> ls = new ArrayList<>();
		OriginDao dao = new OriginDao();
		boolean flag = dao.updateOrigin(origin);
		
//		if(request.getParameter("isExist").equals("yes")){
//			String[] modelNumber = request.getParameterValues("Model[]");
//			String[] quality = request.getParameterValues("Quality[]");
//			String[] name = request.getParameterValues("Name[]");
//			String[] ids = request.getParameterValues("goodsID");
//			for(int i=0 ; i<modelNumber.length ; i++){
//				OriginGoods goods = new OriginGoods();
//				goods.setModel(modelNumber[i]);
//				goods.setQuality(quality[i]);
//				goods.setName(name[i]);
//				goods.setID(Integer.parseInt(ids[i]));
//				if(Integer.parseInt(ids[i]) == 0){
//					if(dao.insertGoods(goods)){
//						ls.add("true");
//					}else{
//						ls.add("false");
//					}
//				}else{
//					if(dao.updateGoods(goods)){
//						ls.add("true");
//					}else{
//						ls.add("false");
//					}
//				}
//				
//			}
//		}
		if(flag && !ls.contains("false")){
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-原产地证明";
			String description = "修改-"+ContractNO;
			log.insert(request, JspInfo, description);
		}else{
			flag = false;
		}
		
		return flag;
		
	}

	@Override
	public boolean deleteOrigin(int id) {
		
		return new OriginDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_origin_certificate.ID) from t_origin_certificate "
				+ "left join t_order on t_order.ContractNo=t_origin_certificate.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_origin_certificate."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getOriginByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql =  "select t_origin_certificate.Date,t_origin_certificate.ContractNO,t_origin_certificate.Goods,t_origin_certificate.ID,t_origin_certificate.LCNO,t_origin_certificate.ManufactoryAddress,"
				+ "t_origin_certificate.ManufactoryName,t_origin_certificate.OriginCountry,t_origin_certificate.PurchaseOrderNO,"
				+ "t_origin_certificate.Tel,t_order.ContractTitle,t_order.Customer "
				+ "from t_origin_certificate left join t_order on t_order.ContractNo=t_origin_certificate.ContractNO ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_origin_certificate."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by t_origin_certificate.OperatingTime desc limit ?,?";
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
		
		return new OriginDao().getOrigin(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_origin_certificate.ID) from t_origin_certificate "
				+ "left join t_order on t_order.ContractNo=t_origin_certificate.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_origin_certificate."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_origin_certificate."+classify_MAP.get(classify2)+" like ?";
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
	public List<Map<String, Object>> getOriginByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 =  "select t_origin_certificate.Date,t_origin_certificate.ContractNO,t_origin_certificate.Goods,t_origin_certificate.ID,t_origin_certificate.LCNO,t_origin_certificate.ManufactoryAddress,"
				+ "t_origin_certificate.ManufactoryName,t_origin_certificate.OriginCountry,t_origin_certificate.PurchaseOrderNO,"
				+ "t_origin_certificate.Tel,t_order.ContractTitle,t_order.Customer "
				+ "from t_origin_certificate left join t_order on t_order.ContractNo=t_origin_certificate.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_origin_certificate."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_origin_certificate."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_origin_certificate.OperatingTime desc limit ?,?";
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
		return new OriginDao().getOrigin(sql, param);
	}

	@Override
	public List<Map<String, Object>> getGoods(int id) {
		
		return new OriginDao().getGoodsByOriginID(id);
	}

	@Override
	public boolean deleteGoods(int id) {
	
		return new OriginDao().deleteGoods(id);
	}
}
