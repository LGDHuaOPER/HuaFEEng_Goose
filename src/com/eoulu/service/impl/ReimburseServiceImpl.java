package com.eoulu.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ReimburseDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.Reimburse;
import com.eoulu.service.ReimburseService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.DownloadUrl;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;
import com.eoulu.util.ZipUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReimburseServiceImpl implements ReimburseService{

	@Override
	public List<Map<String, Object>> getAllData(Page page, String startTime, String endTime) {
		
		return new ReimburseDao().getAllData(page, startTime, endTime);
	}

	@Override
	public int getCounts(String startTime, String endTime) {
	
		return new ReimburseDao().getCounts(startTime, endTime);
	}
	
	@Override
	public List<Map<String, Object>> getOnlyData(Page page, String startTime, String endTime,String email) {
		
		return new ReimburseDao().getOnlyData(page, startTime, endTime,email);
	}

	@Override
	public int getOnlyCounts(String startTime, String endTime,String email) {
	
		return new ReimburseDao().getOnlyCounts(startTime, endTime,email);
	}

	@Override
	public String addReimburse(Reimburse reimburse, String detailJson, String travelJson) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		reimburse.setFilingDate(sdf.format(new Date()));
		ReimburseDao dao = new ReimburseDao();
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		
		List<Map<String, Object>> details = new ArrayList<>();
		List<Map<String, Object>> travels = new ArrayList<>();
		if(!detailJson.equals("")){
			JSONArray array = JSONArray.fromObject(detailJson);
			JSONObject object = null;
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("Type",(String)object.get("Type"));
				try{
					updateMap.put("Amount", Double.parseDouble(object.get("Amount").toString()));
				}catch(NumberFormatException e){
					return "0";
				}
				updateMap.put("MainContent", (String)object.get("MainContent"));
				updateMap.put("CustomerName", (String)object.get("CustomerName"));
				updateMap.put("City", (String)object.get("City"));
				updateMap.put("Time", (String)object.getString("Time"));
				details.add(updateMap);
			}
			
		}
		
		if(!travelJson.equals("")){
			JSONArray array = JSONArray.fromObject(travelJson);
			JSONObject object = null;
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("TravelPlace",(String)object.get("TravelPlace"));
				updateMap.put("MainContent", (String)object.get("MainContent"));
				updateMap.put("TravelTime", (String)object.get("TravelTime"));
				updateMap.put("Days", Float.parseFloat(object.get("Days").toString()));
				travels.add(updateMap);
			}
			
		}
		try {
			int ID = 0;
			conn.setAutoCommit(false);
			try {
				ID = dao.insertRequest(reimburse, dbUtil);
			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
			for(int i = 0;i < details.size();i ++){
				Map<String,Object> detail = details.get(i);
				detail.put("RequestID", ID);
				dao.insertDetails(detail, dbUtil);
			}
			for(int i = 0;i < travels.size();i ++){
				Map<String,Object> travel = travels.get(i);
				travel.put("RequestID", ID);
				dao.insertTravel(travel, dbUtil);	
			}
			conn.commit();
			return String.valueOf(ID);
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return "0";
		}finally {
			dbUtil.closed();
		}
	

	}

	@Override
	public String getUserName(String email) {
		StaffInfoDao dao = new StaffInfoDao();
		List<Map<String, Object>> list = dao.getTelAndName(email);
		String name = "";
		if(list.size()>1){
			name = list.get(1).get("StaffName").toString();
		}
		return name;
	}

	@Override
	public boolean updateReimburse(Reimburse reimburse, String detailJson, String travelJson) {
		
		
		ReimburseDao dao = new ReimburseDao();
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		
		List<Map<String, Object>> details = new ArrayList<>();
		List<Map<String, Object>> travels = new ArrayList<>();
		if(!detailJson.equals("")){
			JSONArray array = JSONArray.fromObject(detailJson);
			JSONObject object = null;
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("Type",(String)object.get("Type"));
				try{
					updateMap.put("Amount", Double.parseDouble(object.get("Amount").toString()));
				}catch(NumberFormatException e){
					return false;
				}
				updateMap.put("MainContent", (String)object.get("MainContent"));
				updateMap.put("CustomerName", (String)object.get("CustomerName"));
				updateMap.put("City", (String)object.get("City"));
				updateMap.put("Time", (String)object.getString("Time"));
				updateMap.put("Attachment", object.get("Attachment"));
				details.add(updateMap);
			}
			
		}
		
		if(!travelJson.equals("")){
			JSONArray array = JSONArray.fromObject(travelJson);
			JSONObject object = null;
			Map<String,Object> updateMap = null;
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("TravelPlace",(String)object.get("TravelPlace"));
				updateMap.put("MainContent",(String) object.get("MainContent"));
				updateMap.put("TravelTime",(String)object.get("TravelTime"));
				updateMap.put("Days", Float.parseFloat((object.get("Days")).toString()));
				travels.add(updateMap);
			}
			
		}
		int ID = reimburse.getID();
		try {
			conn.setAutoCommit(false);
			dao.updateRequest(reimburse, dbUtil);
			dao.deleteDetails(ID, dbUtil);
			for(int i = 0;i < details.size();i ++){
				Map<String,Object> detail = details.get(i);
				detail.put("RequestID", ID);
				dao.insertDetails(detail, dbUtil);
			}
			dao.deleteTravels(ID, dbUtil);
			for(int i = 0;i < travels.size();i ++){
				Map<String,Object> travel = travels.get(i);
				travel.put("RequestID", ID);
				dao.insertTravel(travel, dbUtil);	
			}
			conn.commit();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}
	
	}


	

	@Override
	public Map<String, Object> getApplication(int RequestID) {
		ReimburseDao dao = new ReimburseDao();
		List<Map<String, Object>> detail = dao.getDetails(RequestID);
		List<Map<String, Object>> travel = dao.getTravel(RequestID);
		Map<String, Object> map = new HashMap<>();
		map.put("detail", detail);
		map.put("travel", travel);
		return map;
	}

	@Override
	public String review(int ID, String state,String name,String reason,String filingDate) {
		ReimburseDao dao = new ReimburseDao();
		boolean flag = dao.updateReview(ID,state,reason);
		String result;
		if(flag){
			result = "操作成功";
			if(state.equals("未通过")){
				if(!sendReviewMail(name, reason, filingDate)){
					result+="，邮件发送失败！";
				}
			}else{
				if(!sendReviewMail(name, null, filingDate)){
					result+="，邮件发送失败！";
				}
			}
		}else{
			result = "操作失败";
		}
		return result;
	}
	
	public boolean sendReviewMail(String name,String reason,String filingDate){
		String user;
		String uname;
		String pwd;
		StaffInfoDao dao = new StaffInfoDao();
		MethodUtil util = new MethodUtil();
		String email = "";
		List<Map<String, Object>> list = dao.getMail(name);
		if(list.size()>1){
			email = list.get(1).get("StaffMail").toString();
		}
		String[] to = new String[]{email};
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		
		InputStream in = SendMailUtil.class.getResourceAsStream("email.properties");
		try {
			pro.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		InputStream in1 = SendMailUtil.class.getResourceAsStream("afterSale.properties");
		try {
			pro2.load(in1);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			copyto[i] = pro2.getProperty(key);
			
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		try {
			in.close();
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String subject = "Eoulu:报销申请审批结果";
		StringBuilder sBuilder = new StringBuilder();
		if(reason == null){
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您于"+filingDate+"提交的报销申请已经审批通过，请知晓。</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>此邮件为系统自动发出，无需回复。</span><br><br>");
		}else{
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您于"+filingDate+"提交的报销申请审批未通过，未通过原因："+reason+"，请于2个工作日内前往系统进行修改。</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>此邮件为系统自动发出，无需回复。</span><br><br>");
		}
		String content = util.getEmailSign(sBuilder.toString(),"NA");
		return new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, null, to, null);			
	}
	
	@Override
	public void sendNoticeMail(){
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
		List<String> ls = new ArrayList<>();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("reimburse.properties"));
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
		String subject = "Eoulu: 票据报销事宜";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int month = calendar.get(Calendar.MONTH)+1;
		int next = 0;
		if(month == 12){
			next = 1;
		}else{
			next = month + 1;
		}
	
		int end = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>Hi, All,</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>关于票据报销事宜，通知如下：</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>1. 请尽快在冲锋鹅系统中提交报销申请，报销申请最迟需在"+month+"月"+end+"号之前完成，否则延迟到下月报销；</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>2. 票据截图、电子发票或行程单，请直接在冲锋鹅系统中上传，请确保与报销申请一一对应；</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>3. "+month+"月份需要报销的纸质票据，请于"+next+"月3号前，寄到苏州办公室范敏敏处；</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>注意：纸质票据的整理，请按照附件Eoulu报销规范要求文档执行。非常感谢大家的支持配合！</span><br><br>");
		
		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(),"NA");
		String[] fileList = new String[]{"E:\\Model\\Eoulu报销规范要求.pdf"};
		JavaMailToolsUtil mailUtil = new JavaMailToolsUtil(user, uname, pwd);
		ReimburseDao dao = new ReimburseDao();
	
		if(mailUtil.doSendHtmlEmail(subject, content, fileList, to, copyto)){
			dao.addNoticeMail(month, simpleDateFormat.format(new Date()), "已发布");
		}else{
			if(mailUtil.doSendHtmlEmail(subject, content, fileList, to, copyto)){
				dao.addNoticeMail(month, simpleDateFormat.format(new Date()), "已发布");
			}else{
				dao.addNoticeMail(month, simpleDateFormat.format(new Date()), "发布失败");
			}
		}
	}
	
	@Override
	public String exportFile(String ids,String year,String month){
		ReimburseDao dao = new ReimburseDao();
		File targetDir = new File("E:\\LogisticsFile\\File\\报销申请");
		if(!targetDir.exists()){
			targetDir.mkdirs();
		}
		String[] idArr = null;
		if(ids.equals("")){
			String startTime = "";
			String endTime = "";
			if(!year.equals("")){
					
				if (month.equals("All")) {//按年份查询
					 startTime = year+"-"+"-01";
					 endTime = year+"-12";
				}else{//按年月查询
					 startTime = year+"-"+month;
					 endTime = year+"-"+month;
				}
					
			}
			List<Map<String, Object>> list = dao.getAllID(startTime,endTime);
			idArr = new String[list.size()-1];
			for(int i = 1;i < list.size();i ++){
				idArr[i - 1] = list.get(i).get("ID").toString();
			}
		}else {
			idArr = ids.split(",");
		}
		for(int i = 0;i < idArr.length;i ++){
			int ID = Integer.parseInt(idArr[i]);
			String name = dao.getInfo(ID).get(1).get("Name").toString();
			String filingDate = dao.getInfo(ID).get(1).get("FilingDate").toString();
			String Month = filingDate.split("-")[0]+filingDate.split("-")[1];

			List<Map<String, Object>> detail = dao.getDetails(ID);
			List<Map<String, Object>> travel = dao.getTravel(ID);
			String savePath = "E:\\LogisticsFile\\File\\报销申请"+Month+"-"+name+"-"+ID;
			File sourceDir = new File(savePath);
			if(!sourceDir.exists()){
				sourceDir.mkdirs();
			}
			String path = savePath+"\\报销申请表-"+name+".xlsx";
			makeExcel(detail, travel, path);
			try {
				FileUtils.copyDirectoryToDirectory(sourceDir,targetDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("E:\\LogisticsFile\\File\\报销申请.zip"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    ZipUtils.toZip("E:\\LogisticsFile\\File\\报销申请", fos, true);
	    try {
			FileUtils.deleteDirectory(targetDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    String rootUrl = new DownloadUrl().getRootUrl();
		String filePath = rootUrl+"报销申请.zip";
		return filePath;
		
	}
	
	public void makeExcel(List<Map<String, Object>> detail,List<Map<String, Object>> travel,String path){
		
		String[] detailHead = new String[]{"报销类型","金额","事由","客户名","往返城市","往返时间"};
		String[] travelHead = new String[]{"出差地点","往返时间","事由","天数"};
		Map<String, String> detailMap = new HashMap<>();
		Map<String, String> travelMap = new HashMap<>();
		detailMap.put("报销类型", "Type");
		detailMap.put("金额","Amount");
		detailMap.put("事由", "MainContent");
		detailMap.put("客户名", "CustomerName");
		detailMap.put("往返城市","City");
		detailMap.put("往返时间", "Time");
		travelMap.put("出差地点","TravelPlace");
		travelMap.put("往返时间","TravelTime");
		travelMap.put("事由","MainContent");
		travelMap.put("天数","Days");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("报销申请表");

		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("微软雅黑");
	
		
		XSSFCellStyle center = workbook.createCellStyle();
		center.setWrapText(true);// 自动换行
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		center.setFont(font);
		
		XSSFCellStyle title = workbook.createCellStyle();
		title.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		title.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title.setBorderBottom(CellStyle.BORDER_THIN);
		title.setBorderLeft(CellStyle.BORDER_THIN);
		title.setBorderRight(CellStyle.BORDER_THIN);
		title.setBorderTop(CellStyle.BORDER_THIN);
		title.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		title.setFont(font);
		XSSFRow detailTitle = sheet.createRow(0);
		detailTitle.setHeightInPoints(30);
		sheet.setColumnWidth(0, (int) 3500);
		sheet.setColumnWidth(1, (int) 3500);
		sheet.setColumnWidth(2, (int) 9000);
		sheet.setColumnWidth(3, (int) 5000);
		sheet.setColumnWidth(4, (int) 4500);
		sheet.setColumnWidth(5, (int) 7000);
		

		
		for(int i = 0;i < detailHead.length;i ++){
			XSSFCell head = detailTitle.createCell(i);
			head.setCellStyle(title);
			head.setCellValue(detailHead[i]);
	
		}
		for(int i = 1;i < detail.size();i ++){
			XSSFRow row = sheet.createRow(i);
			for(int j = 0;j < detailHead.length;j++){
				XSSFCell cell = row.createCell(j);
				if(detailHead[j].equals("金额")){
					cell.setCellValue(Double.parseDouble(detail.get(i).get(detailMap.get(detailHead[j])).toString()));
					cell.setCellStyle(center);
				}else{
					cell.setCellValue(detail.get(i).get(detailMap.get(detailHead[j])).toString().replaceAll(";;", "--"));
					cell.setCellStyle(center);
				}
			
			}
		}
		XSSFRow travelTitle = sheet.createRow(detail.size()+2);
		travelTitle.setHeightInPoints(30);
		for(int i = 0;i < travelHead.length;i ++){
			XSSFCell head = travelTitle.createCell(i);
			head.setCellValue(travelHead[i]);
			head.setCellStyle(title);
		}
		for(int i = 1;i < travel.size();i ++){
			XSSFRow row = sheet.createRow(i+detail.size()+2);
			for(int j = 0;j < travelHead.length;j++){
				XSSFCell cell = row.createCell(j);
				if(travelHead[j].equals("天数")){
					cell.setCellValue(Float.parseFloat(travel.get(i).get(travelMap.get(travelHead[j])).toString()));
					cell.setCellStyle(center);
				}else{
					cell.setCellValue(travel.get(i).get(travelMap.get(travelHead[j])).toString().replaceAll(";;", "--"));
					cell.setCellStyle(center);
				}
				
			}
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public List<Map<String, Object>> getList(Page page) {
		ReimburseDao dao = new ReimburseDao();
		return dao.getList(page);
	}

	@Override
	public int getListCount() {
		ReimburseDao dao = new ReimburseDao();
		return Integer.parseInt(dao.getListCount().get(1).get("Count").toString());
	}

	@Override
	public boolean saveAttachment(Reimburse reimburse,String folder,String deleteFile,String isRevoke) {
		boolean flag = false;
		String path = "E:\\LogisticsFile\\File\\"+folder+"\\";
		if(isRevoke.equals("YES")){
			if(!deleteFile.equals("")){
				System.out.println(deleteFile);
			
				String[] file = deleteFile.split("::");
				for(int i = 0;i < file.length;i ++){
					if(!file[i].equals("")){
						try {
							FileUtils.forceDelete(new File(path+file[i]));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			flag = true;
		}else{
			ReimburseDao dao = new ReimburseDao();
			DBUtil db = new DBUtil();
			String attachmentJson = reimburse.getAttachmentJson();
			List<Map<String, String>> list = new ArrayList<>();
			if(!attachmentJson.equals("")){
				JSONArray array = JSONArray.fromObject(attachmentJson);
				JSONObject object = null;
				Map<String,String> updateMap = null;	
				for(int i = 0;i < array.size();i ++){
					object = array.getJSONObject(i);
					updateMap = new HashMap<>();
					updateMap.put("ID",(String)object.get("ID"));
					String OperateType = (String)object.get("OperateType");
					String Attachment = null;
					String OldAttachment = null;
		
			
					switch (OperateType) {
					case "update":
						Attachment = (String)object.get("Attachment");
						OldAttachment = (String)object.get("OldAttachment");
					
						deleteFile += (OldAttachment + "::");
						
						updateMap.put("Attachment", Attachment);
						break;
	
					case "delete":
						OldAttachment = (String)object.get("OldAttachment");
						deleteFile += (OldAttachment + "::");
						updateMap.put("Attachment", null);
						break;
					case "add":
						Attachment = (String)object.get("Attachment");
						updateMap.put("Attachment", Attachment);
						break;
					}
					
					list.add(updateMap);
				}
			}
			Connection conn = db.getConnection();
			try {
				conn.setAutoCommit(false);
				dao.saveFileName(reimburse, db);
				for(int i = 0;i < list.size();i ++){
					dao.updateAttachment(Integer.parseInt(list.get(i).get("ID")), list.get(i).get("Attachment"), db);
				}
				
				conn.commit();
				flag = true;
				
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				flag = false;
			}finally {
				db.closed();
			}
			if(!deleteFile.equals("")){
				System.out.println(deleteFile);
			
				String[] file = deleteFile.split("::");
				System.out.println(file.length);
				for(int i = 0;i < file.length;i ++){
					if(!file[i].equals("")){
						try {
							FileUtils.forceDelete(new File(path+file[i]));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
			
		return flag;
		
	}
	
	
	


}
