package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.HardwareAdvancesDao;
import com.eoulu.entity.HardwareAdvances;
import com.eoulu.service.HardwareAdvancesService;
import com.eoulu.util.DBUtil;


public class HardwareAdvancesServiceImpl implements HardwareAdvancesService{
	public static final Map<String, Object> classify_MAP; 

	static{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("客户", "Customer");
		map.put("项目状态", "Status");
		map.put("负责人及进展", "ResponsibleAndProcess");
		map.put("装机时间", "InstalledTime");
	
		classify_MAP = map;
	}
	@Override
	public List<Map<String, Object>> getHardwareAdvances(Page page) {
		
		return new HardwareAdvancesDao().getHardwareAdvances(page);
	}

	@Override
	public int getAllcounts() {
		
		return new HardwareAdvancesDao().getAllCounts();
	}

	@Override
	public boolean hardwareAdvancesAdd(HttpServletRequest request) {
		
		HardwareAdvances hardware = new HardwareAdvances();
		String customer = request.getParameter("Customer");
		int status = Integer.parseInt(request.getParameter("Status"));
		String installedTime = request.getParameter("InstalledTime");
		String responsible = request.getParameter("ResponsibleAndProcess");
		hardware.setCustomer(customer);
		hardware.setStatus(status);
		hardware.setResponsibleAndProcess(responsible);
		if(installedTime.equals("")){
			hardware.setInstalledTime("0000-00-00");
		}else{
			hardware.setInstalledTime(installedTime);	
		}
		return new HardwareAdvancesDao().insert(hardware);
	}

	@Override
	public boolean hardwareAdvancesUpdate(HttpServletRequest request) {
		HardwareAdvances hardware = new HardwareAdvances();
		int id = Integer.parseInt(request.getParameter("ID"));
		String customer = request.getParameter("Customer");
		int status = Integer.parseInt(request.getParameter("Status"));
		String installedTime = request.getParameter("InstalledTime");
		String responsible = request.getParameter("ResponsibleAndProcess");
		
		hardware.setCustomer(customer);
		hardware.setStatus(status);
		hardware.setResponsibleAndProcess(responsible);
		if(installedTime.equals("")){
			hardware.setInstalledTime("0000-00-00");
		}else{
			hardware.setInstalledTime(installedTime);	
		}
		hardware.setID(id);

		return new HardwareAdvancesDao().update(hardware);
	}

	@Override
	public boolean hardwareAdvancesDelete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID"));
		return new HardwareAdvancesDao().delete(id);
	}

	@Override
	public List<Map<String, Object>> getHardwareAdvancesByPageInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		case "Status":obj=new Object[1]; obj[0]=Integer.parseInt(parameter.toString());break;
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select ID, Customer,`Status`,InstalledTime,ResponsibleAndProcess,OperatingTime"
				+ " from t_hardware_advances ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("Status")){
				sql += "where  "+classify_MAP.get(classify)+" =?";

			}else{
				sql += "where  "+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by Status,InstalledTime  desc  limit ?,?";
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
		return new HardwareAdvancesDao().getQueryList(sql, param);
	}

	@Override
	public int getCountByClassify(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		case "Status":obj=new Object[1]; obj[0]=Integer.parseInt(parameter.toString());break;
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_hardware_advances.ID) from t_hardware_advances  ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("Status")){
				sql += "where  "+classify_MAP.get(classify)+" =?";
			}else{
				sql += "where  "+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}

		return new DBUtil().getCountsByName(sql, obj);
		
	}

	@Override
	public List<Map<String, Object>> getHardwareAdvancesByTime(String classify, String start_time1, String end_time1,
			Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getHardwareAdvancesByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		case "Status":obj1=new Object[1]; obj1[0]=Integer.parseInt(parameter1.toString());break;
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 =  "select count(t_hardware_advances.ID) from t_hardware_advances  ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("Status")){
				sql1 += "where "+classify_MAP.get(classify1)+" =?";
			}else{
				sql1 += "where "+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		
		switch(classify_MAP.get(classify2).toString()){
		case "Status":obj2=new Object[1]; obj2[0]=Integer.parseInt(parameter2.toString());break;
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("Status")){
				sql2 += classify_MAP.get(classify2)+" =?";
			}else{
				sql2 += classify_MAP.get(classify2)+" like ?";
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
	public List<Map<String, Object>> getHardwareAdvancesByPageInTwo(String classify1, Object parameter1,
			String classify2, Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		case "Status":obj1=new Object[1]; obj1[0]=Integer.parseInt(parameter1.toString());break;
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select ID, Customer,`Status`,InstalledTime,ResponsibleAndProcess,OperatingTime"
				+ " from t_hardware_advances ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("Status")){
				sql1 += "where "+classify_MAP.get(classify1)+" =?";
			}else{
				sql1 += "where "+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		case "Status":obj2=new Object[1]; obj2[0]=Integer.parseInt(parameter2.toString());break;
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("Status")){
				sql2 += classify_MAP.get(classify2)+" =?";
			}else{
				sql2 += classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by Status,InstalledTime desc limit ?,?";
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
		return new HardwareAdvancesDao().getQueryList(sql, param);
	}

}
