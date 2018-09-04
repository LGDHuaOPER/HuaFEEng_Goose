package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ReceivingDao;
import com.eoulu.entity.Receiving;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ReceivingService;
import com.eoulu.syn.ExportReceiving;
import com.eoulu.util.DBUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;

public class ReceivingServiceImpl implements ReceivingService{

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
		
		return new ReceivingDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getReceiving(Page page) {
		
		return new ReceivingDao().getReceiving(page);
	}

	@Override
	public boolean addReceiving(HttpServletRequest request) {
		Receiving receiving = new Receiving();
		String PONO = request.getParameter("PONO");
		String ContractNO = request.getParameter("ContractNO");
		String Engineer = request.getParameter("Engineer");
		String Model = request.getParameter("Model");
		String Warranty = request.getParameter("Warranty");
		String GuaranteeDate = request.getParameter("GuaranteeDate");
		String TestDate = request.getParameter("TestDate");
		String ConfirmDate = request.getParameter("ConfirmDate");
		String FirstParty = request.getParameter("Sender");
		String SecondParty = request.getParameter("Receptor");
		receiving.setPONO(PONO);
		receiving.setContractNO(ContractNO);
		receiving.setEngineer(Engineer);
		receiving.setModel(Model);
		receiving.setWarranty(Warranty);
		receiving.setGuaranteeDate(GuaranteeDate);
		receiving.setTestDate(TestDate);
		receiving.setConfirmDate(ConfirmDate);
		receiving.setFirstParty(FirstParty);
		receiving.setSecondParty(SecondParty);
		ReceivingDao dao = new ReceivingDao();
		boolean flag = dao.insert(receiving);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-验收报告";
			String description = "新增-"+ContractNO;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean updateReceiving(HttpServletRequest request) {
		Receiving receiving = new Receiving();
		String PONO = request.getParameter("PONO");
		String ContractNO = request.getParameter("ContractNO");
		String Engineer = request.getParameter("Engineer");
		String Model = request.getParameter("Model");
		String Warranty = request.getParameter("Warranty");
		String GuaranteeDate = request.getParameter("GuaranteeDate");
		String TestDate = request.getParameter("TestDate");
		String ConfirmDate = request.getParameter("ConfirmDate");
		String FirstParty = request.getParameter("Sender");
		String SecondParty = request.getParameter("Receptor");
		int id = Integer.parseInt(request.getParameter("ID"));
		receiving.setPONO(PONO);
		receiving.setContractNO(ContractNO);
		receiving.setEngineer(Engineer);
		receiving.setModel(Model);
		receiving.setWarranty(Warranty);
		receiving.setGuaranteeDate(GuaranteeDate);
		receiving.setTestDate(TestDate);
		receiving.setConfirmDate(ConfirmDate);
		receiving.setFirstParty(FirstParty);
		receiving.setSecondParty(SecondParty);
		receiving.setID(id);
		ReceivingDao dao = new ReceivingDao();
		boolean flag = dao.updateReceiving(receiving);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "商务部-验收报告";
			String description = "修改-"+ContractNO;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean deleteReceiving(int id) {
		
		return  new ReceivingDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_receiving_report.ID) from t_receiving_report "
				+ "left join t_order on t_order.ContractNo=t_receiving_report.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_receiving_report."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getReceivingByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select t_order.ContractTitle,t_order.Customer,t_receiving_report.ContractNO,"
				+ "t_receiving_report.ConfirmDate,t_receiving_report.Engineer,t_receiving_report.FirstParty Sender,"
				+ "t_receiving_report.GuaranteeDate,t_receiving_report.ID,t_receiving_report.Model,"
				+ "t_receiving_report.PONO,t_receiving_report.SecondParty Receptor,t_receiving_report.TestDate,"
				+ "t_receiving_report.Warranty from t_receiving_report left join t_order on "
				+ "t_receiving_report.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_receiving_report."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by t_receiving_report.OperatingTime desc limit ?,?";
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
		return new ReceivingDao().getReceiving(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_receiving_report.ID) from t_receiving_report "
				+ "left join t_order on t_order.ContractNo=t_receiving_report.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_receiving_report."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_receiving_report."+classify_MAP.get(classify2)+" like ?";
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
	public List<Map<String, Object>> getReceivingByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select t_order.ContractTitle,t_order.Customer,t_receiving_report.ContractNO,"
				+ "t_receiving_report.ConfirmDate,t_receiving_report.Engineer,t_receiving_report.FirstParty Sender,"
				+ "t_receiving_report.GuaranteeDate,t_receiving_report.ID,t_receiving_report.Model,"
				+ "t_receiving_report.PONO,t_receiving_report.SecondParty Receptor,t_receiving_report.TestDate,"
				+ "t_receiving_report.Warranty from t_receiving_report left join t_order on "
				+ "t_receiving_report.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_receiving_report."+classify_MAP.get(classify1)+" like ?";
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
				sql2 += "t_receiving_report."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_receiving_report.OperatingTime desc limit ?,?";
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
		return new ReceivingDao().getReceiving(sql, param);
	}
	
	public String exportReceiving(String contractNO){
		List<Map<String, Object>> list = new ReceivingDao().getReceivingByContract(contractNO);
		if(list.size()>1){
			ExportReceiving util = new ExportReceiving();
			return util.exportFile(list);
		}else{
			return "无验收报告";
		}
	}
	@Test
	public void test(){
		new ReceivingServiceImpl().exportReceiving("910084728");
	}

}
