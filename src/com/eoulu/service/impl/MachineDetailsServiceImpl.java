package com.eoulu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.MachineDetailsDao;
import com.eoulu.entity.MachineDetails;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.MachineDetailsService;
import com.eoulu.util.DBUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MachineDetailsServiceImpl implements MachineDetailsService{
	public static final Map<String, Object> classify_MAP; 
	private String table = "";

	static{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("客户单位", "t_customer.CustomerName");
		map.put("合同号", "ContractNO");
		map.put("用户姓名", "t_customer.Contact");
		map.put("SN", "SN");
		map.put("Model", "Model");
		map.put("装机服务时间", "InstalledTime");
		map.put("项目状态", "t_machine_details.Status");
		map.put("负责人", "t_machine_details.Responsible");
		
		classify_MAP = map;
	}
	@Override
	public List<Map<String, Object>> getMachineDetails(Page page) {
		MachineDetailsDao dao = new MachineDetailsDao();
		List<Map<String, Object>> list = dao.getMachineDetails(page);
		return getCurrentProgress(list);
		
	}
	public List<Map<String, Object>> getCurrentProgress(List<Map<String, Object>> list){
		MachineDetailsDao dao = new MachineDetailsDao();
		for(int i = 1;i < list.size();i ++){
			List<Map<String, Object>> progress = dao.getCurrentProgress(Integer.parseInt(list.get(i).get("ID").toString()));
			JSONArray progressJson = JSONArray.fromObject(progress);
			list.get(i).put("CurrentProgress", progressJson.toString());
		}
		return list;
	}

	@Override
	public int getAllcounts() {
		
		return new MachineDetailsDao().getAllCounts();
	}

	@Override
	public boolean machineDetailsAdd(HttpServletRequest request) {
		MachineDetailsDao dao = new MachineDetailsDao();
		MachineDetails machine = new MachineDetails();
		String unit = request.getParameter("CustomerUnit");
	
		String model = request.getParameter("Model");
		String sn = request.getParameter("SN");
		String contractNO = request.getParameter("ContractNO");
		String installedTime = request.getParameter("InstalledTime");
		int CustomerID = Integer.parseInt(request.getParameter("CustomerID"));
		int status = Integer.parseInt(request.getParameter("Status"));
		String responsible = request.getParameter("Responsible");
		String progress = request.getParameter("CurrentProgress")==null?"":request.getParameter("CurrentProgress");
		String latestProgress = request.getParameter("LatestProgress")==null?"":request.getParameter("LatestProgress");

		machine.setModel(model);
		machine.setSN(sn);
		machine.setCustomerID(CustomerID);
		machine.setStatus(status);
		machine.setResponsible(responsible);
		machine.setCurrentProgress(latestProgress);
		if(installedTime.equals("")){
			machine.setInstalledTime("0000-00-00");
		}else{
			machine.setInstalledTime(installedTime);
		}
		
		machine.setContractNO(contractNO);
		int ID = 0;
		try {
			ID =  dao.insert(machine);
		} catch (Exception e) {
	
			e.printStackTrace();
			return false;
		}
		boolean flag = false;
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
			flag = dao.insertProgress(list,ID);
			
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-机台统计";
			String description = "新增-"+unit;
			log.insert(request, JspInfo, description);
		}
		return flag;
	
	}

	@Override
	public boolean machineDetailsUpdate(HttpServletRequest request) {
		MachineDetailsDao dao = new MachineDetailsDao();
		MachineDetails machine = new MachineDetails();
		int id = Integer.parseInt(request.getParameter("ID"));
		String unit = request.getParameter("CustomerUnit");
	
		String model = request.getParameter("Model");
		String sn = request.getParameter("SN");
		String contractNO = request.getParameter("ContractNO");
		String installedTime = request.getParameter("InstalledTime");
		int CustomerID = Integer.parseInt(request.getParameter("CustomerID"));
		int status = Integer.parseInt(request.getParameter("Status"));
		String responsible = request.getParameter("Responsible");
		String progress = request.getParameter("CurrentProgress")==null?"":request.getParameter("CurrentProgress");
		String latestProgress = request.getParameter("LatestProgress")==null?"":request.getParameter("LatestProgress");
	
		machine.setModel(model);
		machine.setSN(sn);
		machine.setCustomerID(CustomerID);
		machine.setStatus(status);
		machine.setResponsible(responsible);
		machine.setCurrentProgress(latestProgress);
		
		if(installedTime.equals("")){
			machine.setInstalledTime("0000-00-00");
		}else{
			machine.setInstalledTime(installedTime);
		}
		machine.setContractNO(contractNO);
		machine.setID(id);
		
		try {
			dao.update(machine);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		boolean flag = false;
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
			flag = dao.insertProgress(list,id);
			
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-机台统计";
			String description = "修改-"+unit;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean machineDetailsDelete(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID"));
		return new MachineDetailsDao().delete(id);
	}

	@Override
	public List<Map<String, Object>> getConfigure(String contractNo) {
		
		return new MachineDetailsDao().getConfigure(contractNo);
	}

	@Override
	public List<Map<String, Object>> getMachineDetailsByPageInOne(String classify, Object parameter, Page page) {
		

		String sql = "select t_machine_details.ID,t_machine_details.Model,t_machine_details.SN,t_machine_details.ContractNO,"
				+ "t_machine_details.InstalledTime,t_customer.CustomerName CustomerUnit,t_customer.Contact CustomerName,t_customer.CustomerLevel,t_machine_details.CustomerID, "
				+ "t_machine_details.Status,t_machine_details.LatestProgress,t_machine_details.Responsible "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID ";
	
		sql += "where "+classify_MAP.get(classify)+" like ? order by InstalledTime desc,CASE WHEN Status IS NULL THEN 4 END limit ?,?";
		Object[] param = new Object[3];
		param[0] = "%"+parameter+"%";
		param[1] = (page.getCurrentPage()-1)*page.getRows();
		param[2] = page.getRows();
		MachineDetailsDao dao = new MachineDetailsDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		return getCurrentProgress(list);
	}

	@Override
	public int getCountByClassify(String classify, Object parameter) {

		String sql = "select count(t_machine_details.ID),t_customer.Contact CustomerName from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID ";
		sql += "where "+classify_MAP.get(classify)+" like ? ";
	
		return new DBUtil().getCountsByName(sql, new Object[]{"%"+parameter+"%"});
	}

	@Override
	public List<Map<String, Object>> getMachineDetailsByTime(String classify, String start_time1, String end_time1,
			Page page) {
		String sql=null;
		Object[] obj = new Object[2];

		//生成参数数组
		 obj[0]=start_time1;
		 obj[1]=end_time1;

		int length = obj.length;
		sql = "select ID,Model,SN,ContractNO,InstalledTime,t_customer.CustomerName CustomerUnit,"
				+ "t_customer.Contact CustomerName,t_machine_details.CustomerID,t_customer.CustomerLevel, "
				+ "t_machine_details.Status,t_machine_details.LatestProgress,t_machine_details.Responsible "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID ";	
		Object[] param;
		if(!"".equals(start_time1) && !"".equals(end_time1)){
			sql+=" where "+classify_MAP.get(classify)+" between ? and ?";

				sql+=" order by t_machine_details.InstalledTime desc,CASE WHEN Status IS NULL THEN 4 END limit ?,?";

			
			//构建带有分页信息的参数数组
			param= new Object[obj.length+2];
			for(int i=0; i<length; i++){
				param[i]=obj[i];
			}
			param[length]=(page.getCurrentPage()-1)*page.getRows();
			param[length+1]=page.getRows();
		}else{

				sql+=" order by t_machine_details.InstalledTime desc limit ?,?";

			param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
			
		}
		MachineDetailsDao dao = new MachineDetailsDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		return getCurrentProgress(list);
	}

	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1) {
		String sql=null;
		Object[] obj ;
		sql = "select count(t_machine_details.ID) from t_machine_details  ";
		if(!"".equals(start_time1) && !"".equals(end_time1)){
			obj = new Object[2];
			//生成参数数组
			 obj[0]=start_time1;
			 obj[1]=end_time1;
			sql+="where "+classify_MAP.get(classify)+" between ? and ?";
		}else{
			obj=null;
		}
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2) {
		String sql1=null;
		Object[] obj1 = null;
	
		switch(classify_MAP.get(classify1).toString()){
		case "InstalledTime":obj1=new Object[2]; obj1[0]=map1.get("1").toString();obj1[1]=map1.get("2").toString();
		default: obj1=new Object[1]; obj1[0]="%"+map1.get("1").toString()+"%";
		}
		int length11 = map1.size();
		
		if(length11==1){
			sql1 = "select count(t_machine_details.ID) from t_machine_details  where ";
			for(int i=0; i<obj1.length; i++){
				sql1+=classify_MAP.get(classify1)+" like ?";
				if(i<obj1.length-1){
					sql1+=" or ";
				}
			}
			sql1+=" and ";
		}else if(length11==2){
			if(!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())){
				sql1 = "select count(t_machine_details.ID) from t_machine_details  where ";
				sql1+=classify_MAP.get(classify1)+" between ? and ?";
				sql1+=" and ";
			}else{
				sql1 = "select count(t_machine_details.ID) from t_machine_details  where ";
				obj1=null;
			}
		}
	//后半句sql语句构建
		String sql2=null;
		Object[] obj2 = null;

		switch(classify_MAP.get(classify2).toString()){
		case "InstalledTime":obj2=new Object[2]; obj2[0]=map2.get("1").toString();obj2[1]=map2.get("2").toString();
		default: obj2=new Object[1]; obj2[0]="%"+map2.get("1").toString()+"%";
		}
		int length22 = map2.size();
		
		if(length22==1){
			sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
			for(int i=0; i<obj2.length; i++){
				sql2+="t_invoice."+classify_MAP.get(classify2)+" like ?";
				if(i<obj2.length-1)
					sql2+=" or ";
			}
		}else if(length22==2){
			if(!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())){
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
				sql2+="where t_invoice."+classify_MAP.get(classify2)+" between ? and ?";
			}else{
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice ";
				obj2=null;
			}
		}
		String sql = sql1 +"t_invoice."+classify_MAP.get(classify2)+" in(" + sql2+")";
		int length1;
		int length2; 
		int allCounts=0;
		if(obj1!=null && obj2!=null ){
			allCounts=obj1.length+obj2.length;
			length1=obj1.length;
			length2=obj2.length;
		}else if(obj1==null && obj2!=null){
			allCounts = obj2.length;
			length1=0;
			length2=obj2.length;
		}else if(obj2==null && obj1!=null){
			allCounts = obj1.length;
			length2=0;
			length1=obj1.length;
		}else{
			allCounts=0;
			length1=0;
			length2=0;
		}
		Object[] sqlObj = new Object[allCounts];
		int times = 0;
		for(int i=0; i<length1; i++){
			sqlObj[i]=obj1[i];
		}
		for(int i=0; i<length2; i++){
			sqlObj[length1]=obj2[i];
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getMachineDetailsByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2) {

		String sql1 =  "select count(t_machine_details.ID) ,t_customer.Contact CustomerName from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID ";
	
		sql1 += "where "+table+classify_MAP.get(classify1)+" like ?";
		String sql2 = classify_MAP.get(classify2)+" like ?";

		String sql = sql1 +" and "+sql2;
		Object[] param = new Object[2];
		param[0]="%"+parameter1+"%";
		param[1]="%"+parameter2+"%";
	
		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getMachineDetailsByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
	
		String sql1 = "select t_machine_details.ID,t_machine_details.Model,t_machine_details.SN,t_machine_details.ContractNO,"
				+ "t_machine_details.InstalledTime,t_customer.CustomerName CustomerUnit,t_customer.Contact CustomerName,"
				+ "t_machine_details.CustomerID,t_customer.CustomerLevel,"
				+ "t_machine_details.Status,t_machine_details.LatestProgress,t_machine_details.Responsible "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID ";

				sql1 += "where "+classify_MAP.get(classify1)+" like ?";

	
		String sql2 = classify_MAP.get(classify2)+" like ?";
		String sql = sql1 +" and "+sql2+" order by t_machine_details.InstalledTime desc,CASE WHEN Status IS NULL THEN 4 END limit ?,?";
		Object[] param = new Object[4];
		param[0] = "%"+parameter1+"%";	
		param[1] = "%"+parameter2+"%";
			
		param[2] = (page.getCurrentPage()-1)*page.getRows();
		param[3] = page.getRows();
		
		MachineDetailsDao dao = new MachineDetailsDao();
		List<Map<String, Object>> list = dao.getQueryList(sql, param);
		return getCurrentProgress(list);
	}

	

}
