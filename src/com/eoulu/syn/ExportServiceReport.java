package com.eoulu.syn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.util.Base64Util;
import com.eoulu.util.Java2Word;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportServiceReport {
	private Lock lock = new ReentrantLock();
	
	public  String exportServiceReport(HttpServletRequest request){
		lock.lock();
		long time1 = System.currentTimeMillis();
		try {
			
			String Number = request.getParameter("Number") == null?"":request.getParameter("Number").trim();
			String Project = request.getParameter("Project") == null?"":request.getParameter("Project").trim();
			String CustomerTitle = request.getParameter("CustomerTitle") == null?"":request.getParameter("CustomerTitle").trim();
			String CustomerName = request.getParameter("CustomerName") == null?"":request.getParameter("CustomerName").trim();
			String LinkInfo = request.getParameter("LinkInfo") == null?"":request.getParameter("LinkInfo").trim();
			String StaffName = request.getParameter("StaffName") == null?"":request.getParameter("StaffName").trim();
			String ContractNo = request.getParameter("ContractNo") == null?"":request.getParameter("ContractNo").trim();
			String ProductVersion = request.getParameter("ProductVersion") == null?"":request.getParameter("ProductVersion").trim();
			String FileName = request.getParameter("FileName") == null?("服务完成报告:"+Number+".doc"):request.getParameter("FileName");
			System.out.println(FileName);
			String previewJson = request.getParameter("PreviewJson") == null?"":request.getParameter("PreviewJson");
			
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			JSONArray array = null;
			List<Map<String,Object>> list = new ArrayList<>();
			if(!previewJson.equals("")){
				array = JSONArray.fromObject(previewJson);
			
				
				Map<String, Object> updateMap = null;
				for(int i = 0;i < array.size();i ++){
					JSONObject object = array.getJSONObject(i);
					updateMap = new HashMap<>();
					updateMap.put("ServiceItem", (String)object.get("ServiceItem"));
					updateMap.put("Isfinished", (String)object.get("Isfinished"));
					updateMap.put("Remarks", (String)object.get("Remarks"));
					updateMap.put("ConfirmedSignature", object.getString("ConfirmedSignature"));
					updateMap.put("ConfirmDate", object.getString("ConfirmDate"));
					list.add(updateMap);
				}
			}
			HashMap<String,Object> data = new HashMap<>();
			HashMap<String,Object> imgData = new HashMap<>();
			if(Project.equals("EUCP")){
				data.put("${Number}", Number);
				data.put("${CustomerTitleA}", CustomerTitle);
				data.put("${CustomerNameA}", CustomerName);
				data.put("${LinkInfoA}", LinkInfo);
				data.put("${StaffNameA}", StaffName);
				data.put("${ContractNoA}", ContractNo);
				data.put("${ProductVersionA}", ProductVersion);
			
				data.put("${CustomerTitleB}", "");
				data.put("${CustomerNameB}", "");
				data.put("${LinkInfoB}", "");
				data.put("${StaffNameB}", "");
				data.put("${ContractNoB}", "");
				data.put("${ProductVersionB}", "");
			}else{
				data.put("${Number}", Number);
				data.put("${CustomerTitleB}", CustomerTitle);
				data.put("${CustomerNameB}", CustomerName);
				data.put("${LinkInfoB}", LinkInfo);
				data.put("${StaffNameB}", StaffName);
				data.put("${ContractNoB}", ContractNo);
				data.put("${ProductVersionB}", ProductVersion);
				
				data.put("${CustomerTitleA}", "");
				data.put("${CustomerNameA}", "");
				data.put("${LinkInfoA}", "");
				data.put("${StaffNameA}", "");
				data.put("${ContractNoA}", "");
				data.put("${ProductVersionA}", "");
			}
			List<String[]> table = new ArrayList<>();
			String[] colNum = new String[]{"1","2","3","4","5","6"};
			table.add(colNum);
			for(int i = 0;i < list.size();i ++){
				String[] field = new String[]{i+1+"",(String) list.get(i).get("ServiceItem"),
						(String) list.get(i).get("Isfinished"),(String) list.get(i).get("Remarks"),
						"${imageSignature"+(i+1)+"}","${imageDate"+(i+1)+"}"};
				if(!list.get(i).get("ConfirmedSignature").toString().equals("")){
				
				
					Base64Util.GenerateImage( list.get(i).get("ConfirmedSignature").toString().split(",")[1], 
						"E:\\LogisticsFile\\File\\Signature\\"+Number+"-signature-"+(i+1)+".png");
					imgData.put("${imageSignature"+(i+1)+"}", "E:\\LogisticsFile\\File\\Signature\\"+Number+"-signature-"+(i+1)+".png");
				}
				else{
					imgData.put("${imageSignature"+(i+1)+"}", "");
				}
				if(!list.get(i).get("ConfirmDate").toString().equals("")){
					Base64Util.GenerateImage(list.get(i).get("ConfirmDate").toString().split(",")[1], 
						"E:\\LogisticsFile\\File\\Signature\\"+Number+"-date-"+(i+1)+".png");
					imgData.put("${imageDate"+(i+1)+"}", "E:\\LogisticsFile\\File\\Signature\\"+Number+"-date-"+(i+1)+".png");
				}else{
					imgData.put("${imageDate"+(i+1)+"}","");
				}
			
			
				table.add(field);
									
			}
			if(list.size() > 0){
				data.put("table$2@2", table);
			}
			data.put("${Submitter}", StaffName);
			data.put("${Day}",year+"年"+month+"月"+day+"日" );
			
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\"+FileName;
			Java2Word word = new Java2Word();
			//String outputPath = "E:\\LogisticsFile\\File\\"+FileName;
			word.toWord("E:\\Model\\服务完成报告模板.doc", downLoadUrl, data,"end");
			Java2Word word2 = new Java2Word();
			word2.toWord(downLoadUrl,downLoadUrl, imgData,"end");
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			
	
			
			return "down\\"+FileName;
		
			
		} finally {
			lock.unlock();
		}
		
	}

}
