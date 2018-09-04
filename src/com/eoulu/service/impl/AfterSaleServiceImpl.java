package com.eoulu.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.AfterSaleDao;
import com.eoulu.entity.AfterSale;
import com.eoulu.service.AfterSaleService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AfterSaleServiceImpl implements AfterSaleService{
	public static final Map<String, String> classify_MAP; 

	static{
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("客户单位", "CustomerUnit");
		map.put("项目状态", "ProjectStatus");
		map.put("姓名", "CustomerName");
		map.put("服务项目", "ServiceProject");
		map.put("目前进展", "CurrentProgress");
		map.put("服务完成时间", "EndDate");
		classify_MAP = map;
	}
	@Override
	public List<Map<String, Object>> getAfterSale(Page page) {
		AfterSaleDao dao = new AfterSaleDao();
		List<Map<String, Object>> list = dao.getAfterSale(page);
		for(int i = 1;i < list.size();i ++){
			List<Map<String, Object>> progress = dao.getCurrentProgress(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray progressJson = JSONArray.fromObject(progress);
			list.get(i).put("CurrentProgress", progressJson.toString());
		}
		return list;
	}

	@Override
	public int getAllcounts() {
	
		return new AfterSaleDao().getAllCounts();
	}

	@Override
	public boolean afterSaleAdd(HttpServletRequest request) {
		AfterSale sale = new AfterSale();
		String unit = request.getParameter("CustomerUnit");
		String name = request.getParameter("CustomerName");
		String project = request.getParameter("ServiceProject");
		String progress = request.getParameter("CurrentProgress")==null?"":request.getParameter("CurrentProgress");
		String latestProgress = request.getParameter("LatestProgress")==null?"":request.getParameter("LatestProgress");
		int status = Integer.parseInt(request.getParameter("ProjectStatus"));
		String date = request.getParameter("EndDate");
		sale.setCustomerUnit(unit);
		sale.setCustomerName(name);
		sale.setCurrentProgress(latestProgress);
		sale.setServiceProject(project);
		sale.setProjectStatus(status);
		if(date.equals("")){
			sale.setEndDate("0000-00-00");
		}else{
			sale.setEndDate(date);
		}
		AfterSaleDao dao = new AfterSaleDao();
		int maxID;
			try {
				maxID = dao.insert(sale);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	
		boolean flag1 = false;
		if(!progress.equals("")){
			JSONArray array = JSONArray.fromObject(progress);
			JSONObject object = null;
			Map<String,String> updateMap = null;
			List<Map<String, String>> list = new ArrayList<>();
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("CurrentProgress",(String)object.get("CurrentProgress"));
				updateMap.put("Date", ((String)object.get("Date")).equals("")?"0000-00-00":(String)object.get("Date"));
				list.add(updateMap);
			}
			flag1 = dao.insertProgress(list,maxID);
			
		}
		if(flag1){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-售后维修";
			String description = "添加-"+project;
			log.insert(request, JspInfo, description);
			int count = getProjectCount();
			if(count >19&&count%10 == 0){
				sendInformEmail(request, count);
			}
			
		}
		return flag1;
	}

	@Override
	public boolean afterSaleUpdate(HttpServletRequest request) {
		AfterSale sale = new AfterSale();
		AfterSaleDao dao = new AfterSaleDao();
		int oldCount = getProjectCount();
		int id = Integer.parseInt(request.getParameter("ID"));
		String unit = request.getParameter("CustomerUnit");
		String name = request.getParameter("CustomerName");
		String project = request.getParameter("ServiceProject");
		String progress = request.getParameter("CurrentProgress")==null?"":request.getParameter("CurrentProgress");
		String latestProgress = request.getParameter("LatestProgress")==null?"":request.getParameter("LatestProgress");
		boolean flag1 = false;

		if(!progress.equals("")){
			JSONArray array = JSONArray.fromObject(progress);
			JSONObject object = null;
			Map<String,String> updateMap = null;
			List<Map<String, String>> list = new ArrayList<>();
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("CurrentProgress",(String)object.get("CurrentProgress"));
				updateMap.put("Date", ((String)object.get("Date")).equals("")?"0000-00-00":(String)object.get("Date"));
				list.add(updateMap);
			}
			flag1 = dao.insertProgress(list,id);
			
		}
		int status = Integer.parseInt(request.getParameter("ProjectStatus"));
		String date = request.getParameter("EndDate");
		sale.setCustomerUnit(unit);
		sale.setCustomerName(name);
		sale.setServiceProject(project);
		sale.setProjectStatus(status);
		sale.setCurrentProgress(latestProgress);
		if(date.equals("")){
			sale.setEndDate("0000-00-00");
		}else{
			sale.setEndDate(date);
		}
		sale.setID(id);
		boolean flag = dao.update(sale);
		if(flag&&flag1){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-售后维修";
			String description = "修改-"+project;
			log.insert(request, JspInfo, description);
			int count = getProjectCount();
			if(count >19&&count%10 == 0 && oldCount != count){
				sendInformEmail(request, count);
			}
		}
		return flag;
	}

	@Override
	public boolean afterSaleDelete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID"));
		return new AfterSaleDao().delete(id);
	}

	@Override
	public List<Map<String, Object>> getAfterSaleByPageInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		case "ProjectStatus":obj=new Object[1]; obj[0]=parameter;break;
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select ID,CustomerName,CustomerUnit,ProjectStatus,EndDate,ServiceProject,"
				+ "OperatingTime from t_after_sale ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("项目状态")){
				sql += "where  "+classify_MAP.get(classify)+" =?";
			}else{
				sql += "where  "+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by ProjectStatus,EndDate  desc  limit ?,?";
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
		
		System.out.println(sql);
		AfterSaleDao dao = new AfterSaleDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		for(int i = 1;i < list.size();i ++){
			List<Map<String, Object>> progress = dao.getCurrentProgress(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray progressJson = JSONArray.fromObject(progress);
			list.get(i).put("CurrentProgress", progressJson.toString());
		}
		return list;
	}

	@Override
	public int getCountByClassify(String classify, Object parameter) {
		Object[] obj = null;
	
		switch(classify_MAP.get(classify).toString()){
		case "ProjectStatus":obj=new Object[1]; obj[0]=parameter;break;
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_after_sale.ID) from t_after_sale  ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("项目状态")){
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
	public List<Map<String, Object>> getAfterSaleByTime(String classify, String start_time1, String end_time1,
			Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		
		default: obj=new Object[2]; obj[0]=start_time1;obj[1]=end_time1;
		}
		String sql = "select ID,CustomerName,CustomerUnit,ProjectStatus,EndDate,ServiceProject,"
				+ "OperatingTime from t_after_sale ";
		sql += "where  "+classify_MAP.get(classify)+" between ? and ?";
		sql += " order by ProjectStatus,EndDate  desc  limit ?,?";
		Object[] param;
		param = new Object[obj.length+2];
		for(int i=0 ; i< obj.length ; i++){
			param[i] = obj[i];
		}
		param[obj.length] = (page.getCurrentPage()-1)*page.getRows();
		param[obj.length+1] = page.getRows();
		AfterSaleDao dao = new AfterSaleDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		for(int i = 1;i < list.size();i ++){
			List<Map<String, Object>> progress = dao.getCurrentProgress(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray progressJson = JSONArray.fromObject(progress);
			list.get(i).put("CurrentProgress", progressJson.toString());
		}
		return list;
	}

	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1) {
		Object[] obj = null;

		switch(classify_MAP.get(classify).toString()){
		
		default: obj=new Object[2]; obj[0]=start_time1;obj[1]=end_time1;
		}
		String sql = "select count(t_after_sale.ID) from t_after_sale  ";
		sql += "where  "+classify_MAP.get(classify)+" between ? and ?";

		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2) {
				return 0;
		
	}

	@Override
	public List<Map<String, Object>> getAfterSaleByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		case "ProjectStatus":obj1=new Object[1]; obj1[0]=parameter1;break;
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 =  "select count(t_after_sale.ID) from t_after_sale  ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("项目状态")){
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
		case "ProjectStatus":obj2=new Object[1]; obj2[0]=parameter2;break;
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("项目状态")){
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
	public List<Map<String, Object>> getAfterSaleByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		case "ProjectStatus":obj1=new Object[1]; obj1[0]=parameter1;break;
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select ID,CustomerName,CustomerUnit,ProjectStatus,EndDate,ServiceProject,"
				+ "OperatingTime from t_after_sale ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("项目状态")){
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
		case "ProjectStatus":obj2=new Object[1]; obj2[0]=parameter2;break;
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("项目状态")){
				sql2 += classify_MAP.get(classify2)+" =?";
			}else{
				sql2 += classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by ProjectStatus,EndDate desc limit ?,?";
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
		AfterSaleDao dao = new AfterSaleDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		for(int i = 1;i < list.size();i ++){
			List<Map<String, Object>> progress = dao.getCurrentProgress(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray progressJson = JSONArray.fromObject(progress);
			list.get(i).put("CurrentProgress", progressJson.toString());
		}
		return list;
	}
	
	public int getProjectCount(){
		AfterSaleDao dao = new AfterSaleDao();
		return Integer.parseInt(dao.getNotSolvedCount().get(1).get("Count").toString());
	
	}
	
	public void sendInformEmail(HttpServletRequest req,int count){
		MethodUtil util = new MethodUtil();
		String subject = "售后维修待解决项目已超"+count+"个";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>张工，您好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>目前售后维修待解决的项目较多，请尽快处理。</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>非常感谢！</span><br>");
		String content = util.getEmailSign(sBuilder.toString(),"NA");
		if(!sendAfterSaleEmailConfig(subject, content, null)){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-售后维修";
			String description = "发送提醒邮件失败";
			log.insert(req, JspInfo, description);
		}
	}
	
	public boolean sendAfterSaleEmailConfig(String subject,String content,String[] fileList){
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		String user = "";
		String uname = "";
		String pwd = "";
	
	
		InputStream in = SendMailUtil.class.getResourceAsStream("email.properties");
	
		try {
			pro.load(in);
			user = pro.getProperty("SEND_USER");
			uname = pro.getProperty("SEND_UNAME");
			pwd = pro.getProperty("SEND_PWD");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		InputStream in1 = SendMailUtil.class.getResourceAsStream("afterSale.properties");
		try {
			pro2.load(in1);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		int toCount = Integer.parseInt(pro2.getProperty("to"));
		for(int i=0 ; i<toCount ; i++){
			int temp = i+1;
			String key = "To"+temp;
			list.add(pro2.getProperty(key));
			
		}
		to = new String[toCount];
		for(int i=0 ; i<list.size();i++){
			to[i] = list.get(i);
		}
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		try {
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, fileList, to, copyto);
	}
		
}
