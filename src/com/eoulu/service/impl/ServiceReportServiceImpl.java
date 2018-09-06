package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ServiceReportDao;
import com.eoulu.entity.ServiceReport;
import com.eoulu.service.ServiceReportService;
import com.eoulu.syn.ExportServiceReport;
import com.eoulu.util.Base64Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServiceReportServiceImpl implements ServiceReportService{

	@Override
	public List<Map<String, Object>> getAllData(Page page, String type, String column1, String content1, String column2,
			String content2) {
		
		return new ServiceReportDao().getAllData(page, type, column1, content1, column2, content2);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
		
		return new ServiceReportDao().getCounts(type, column1, content1, column2, content2);
	}

	@Override
	public boolean insert(ServiceReport report) {
		
		return new ServiceReportDao().insert(report);
	}

	@Override
	public boolean update(ServiceReport report) {
	
		return new ServiceReportDao().update(report);
	}

	@Override
	public String getReportNumber(String email) {
		ServiceReportDao dao = new ServiceReportDao();
		List<Map<String, Object>> list = dao.getStaffInfo(email);
		String staffName = "NA";
		String staffCode = "NA";
		if(list.size()>1){
			try{
				staffName = list.get(1).get("StaffName").toString();
				staffCode = list.get(1).get("StaffCode").toString();
			}catch(Exception e){
				System.out.println("无姓名或员工编号");
			}
		}
		List<Map<String, Object>> list1 = dao.getNumber(staffName);
		System.out.println("list:"+list1);
		String maxNumber = "";
		String number = "";
		if(list1.size()>1){
			maxNumber = list1.get(1).get("Number").toString();
			String[] str = maxNumber.split("-");
			str[2] = Integer.parseInt(str[2])+1+"";
			switch (str[2].length()) {
			case 1:
				str[2] = "00"+str[2];
				break;

			case 2:
				str[2] = "0"+str[2];
				break;
			}
			number = str[0]+"-"+str[1]+"-"+str[2];
			
		}
		if(number.equals("")){
			number = "SRV-"+staffCode+"-"; 
		}
		return number;
	}

	@Override
	public List<Map<String, Object>> preview(int reportID) {
		List<Map<String, Object>> list = new ServiceReportDao().preview(reportID);
		for(int i = 1;i <list.size();i ++ ){
			if(!list.get(i).get("ConfirmedSignature").toString().equals("")){
				list.get(i).put("ConfirmedSignature", Base64Util.GetImageStr(list.get(i).get("ConfirmedSignature").toString()));
			}
			if(!list.get(i).get("ConfirmDate").toString().equals("")){
				list.get(i).put("ConfirmDate", Base64Util.GetImageStr(list.get(i).get("ConfirmDate").toString()));
			}
		}
		return list;
	}

	@Override
	public boolean savePreview(String previewJson,int reportID) {
		JSONArray array = null;
		List<Map<String,Object>> list = new ArrayList<>();
		if(!previewJson.equals("")){
			array = JSONArray.fromObject(previewJson);
			Map<String, Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				JSONObject object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				if(!object.get("ConfirmedSignature").toString().equals("")){
					
					Base64Util.GenerateImage( object.get("ConfirmedSignature").toString().split(",")[1], 
						"E:\\LogisticsFile\\File\\Signature\\"+reportID+"-signature-"+(i+1)+".png");
					updateMap.put("ConfirmedSignature", "E:\\LogisticsFile\\File\\Signature\\"+reportID+"-signature-" + (i+1)+".png");
				}else{
					updateMap.put("ConfirmedSignature","");
				}
				
				if(!object.get("ConfirmDate").toString().equals("")){
					Base64Util.GenerateImage(object.get("ConfirmDate").toString().split(",")[1], 
						"E:\\LogisticsFile\\File\\Signature\\"+reportID+"-date-"+(i+1)+".png");
					updateMap.put("ConfirmDate", "E:\\LogisticsFile\\File\\Signature\\"+reportID+"-date-" + (i+1)+".png");
				}else{
					updateMap.put("ConfirmDate","");
				}
				
				
				updateMap.put("ServiceItem", (String)object.get("ServiceItem"));
				updateMap.put("Isfinished", (String)object.get("Isfinished"));
				updateMap.put("Remarks", (String)object.get("Remarks"));
				
				
				list.add(updateMap);
			}
		}
		
		return new ServiceReportDao().savePreview(list, reportID);
	}

	@Override
	public String exportFile(HttpServletRequest request) {
		ExportServiceReport util = new ExportServiceReport();
		
		return util.exportServiceReport(request);
	}

	@Override
	public boolean deleteRecord(int ID) {
		return new ServiceReportDao().deleteRecord(ID);
	}

}
