package com.eoulu.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;

import com.eoulu.commonality.Page;
import com.eoulu.dao.KeysightDao;
import com.eoulu.entity.Keysight;
import com.eoulu.service.KeysightService;
import com.eoulu.util.ChineseToEnglishUtil;

public class KeysightServiceImpl implements KeysightService{

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String type, String column1, String content1,
			String column2, String content2) {
		KeysightDao dao = new KeysightDao();
		return dao.getDataByPage(page, type, column1, content1, column2, content2);
	}

	@Override
	public boolean insert(Keysight keysight) {
		
		return new KeysightDao().insert(keysight);
	}

	@Override
	public boolean update(Keysight keysight) {
		
		return new KeysightDao().update(keysight);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
	
		return new KeysightDao().getCounts(type, column1, content1, column2, content2);
	}
	
	public List<Map<String,Object>> getExcelData(){
		return new KeysightDao().getExcelData();
	}
	
	public void exportExcel(List<Map<String, Object>> list,HttpServletResponse response){
		String[] headName = new String[]{"PartnerID","Opportunity Create Date (DD/MM/YYYY)","Deal ID",
				"CustomerName","Street Address","City","State/Province","Postal Code","Country Code",
				"Deal Status (Open/Won/Lost or Cancelled)","% Win Probability","Purchasing Agent (Partner/Customer or  Keysight Reseller)",
				"Ship-To Location","Keysight FE Name","Line #","Keysight Model Number-Option. One per line only (Example N9010B-503)",
				"Qty","Forecasted Order Date (DD/MM/YYYY)","Keysight Sales Order#","Actual Order Booking Date (DD/MM/YYYY)",
				"Estimated Keysight Deal Value","Currency Code","Customer Attn","Customer Tel","Customer Email",
				"Status"};
		Map<String, String> map = new HashMap<>();
		map.put("PartnerID", "PartnerID");
		map.put("Opportunity Create Date (DD/MM/YYYY)", "OpportunityCreateDate");
		map.put("Deal ID", "DealID");
		map.put("CustomerName", "CustomerName");
		map.put("Street Address", "StreetAddress");
		map.put("City", "City");
		map.put("State/Province", "Area");
		map.put("Postal Code", "PostalCode");
		map.put("Country Code", "CountryCode");
		map.put("Deal Status (Open/Won/Lost or Cancelled)", "DealStatus");
		map.put("% Win Probability", "WinProbability");
		map.put("Purchasing Agent (Partner/Customer or  Keysight Reseller)", "KeysightReseller");
		map.put("Ship-To Location", "ShipToLocation");
		map.put("Keysight FE Name", "KeysightName");
		map.put("Line #", "Line");
		map.put("Keysight Model Number-Option. One per line only (Example N9010B-503)", "Model");
		map.put("Qty", "Qty");
		map.put("Forecasted Order Date (DD/MM/YYYY)", "OrderDate");
		map.put("Keysight Sales Order#", "SalesOrder");
		map.put("Actual Order Booking Date (DD/MM/YYYY)", "BookingDate");
		map.put("Estimated Keysight Deal Value", "SellerPriceOne");
		map.put("Currency Code", "CurrencyCode");
		map.put("Customer Attn", "Contact");
		map.put("Customer Tel", "ContactInfo1");
		map.put("Customer Email", "Email");
		map.put("Status", "Status");

		
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Funnel-Eoulu-"+sdFormat.format(new Date())+".xls");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
	    style.setBottomBorderColor(HSSFColor.BLACK.index);
	    style.setBorderTop(BorderStyle.THIN);
	    style.setTopBorderColor(HSSFColor.BLACK.index);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setLeftBorderColor(HSSFColor.BLACK.index);
	    style.setBorderRight(BorderStyle.THIN);
	    style.setRightBorderColor(HSSFColor.BLACK.index);
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	    HSSFFont font = workbook.createFont();
	    font.setFontHeightInPoints((short) 11);
	    font.setFontName("微软雅黑");
	    style.setFont(font);
	    
	    HSSFFont font1 = workbook.createFont();
	    font1.setFontHeightInPoints((short) 11);
	    font1.setFontName("宋体");
	  
	    
	    HSSFCellStyle styleGreen = workbook.createCellStyle();
		styleGreen.setBorderBottom(BorderStyle.THIN);
	    styleGreen.setBottomBorderColor(HSSFColor.BLACK.index);
	    styleGreen.setBorderTop(BorderStyle.THIN);
	    styleGreen.setTopBorderColor(HSSFColor.BLACK.index);
	    styleGreen.setBorderLeft(BorderStyle.THIN);
	    styleGreen.setLeftBorderColor(HSSFColor.BLACK.index);
	    styleGreen.setBorderRight(BorderStyle.THIN);
	    styleGreen.setRightBorderColor(HSSFColor.BLACK.index);
	    styleGreen.setFillForegroundColor(HSSFColor.LIME.index);
	    styleGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleGreen.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleGreen.setFont(font1);
	    styleGreen.setWrapText(true);
	    
	    HSSFCellStyle styleYellow = workbook.createCellStyle();
		styleYellow.setBorderBottom(BorderStyle.THIN);
	    styleYellow.setBottomBorderColor(HSSFColor.BLACK.index);
	    styleYellow.setBorderTop(BorderStyle.THIN);
	    styleYellow.setTopBorderColor(HSSFColor.BLACK.index);
	    styleYellow.setBorderLeft(BorderStyle.THIN);
	    styleYellow.setLeftBorderColor(HSSFColor.BLACK.index);
	    styleYellow.setBorderRight(BorderStyle.THIN);
	    styleYellow.setRightBorderColor(HSSFColor.BLACK.index);
	    styleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);
	    styleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleYellow.setFont(font1);
	    styleYellow.setWrapText(true);
	    
	    HSSFCellStyle styleOrange = workbook.createCellStyle();
		styleOrange.setBorderBottom(BorderStyle.THIN);
	    styleOrange.setBottomBorderColor(HSSFColor.BLACK.index);
	    styleOrange.setBorderTop(BorderStyle.THIN);
	    styleOrange.setTopBorderColor(HSSFColor.BLACK.index);
	    styleOrange.setBorderLeft(BorderStyle.THIN);
	    styleOrange.setLeftBorderColor(HSSFColor.BLACK.index);
	    styleOrange.setBorderRight(BorderStyle.THIN);
	    styleOrange.setRightBorderColor(HSSFColor.BLACK.index);
	    styleOrange.setFillForegroundColor(HSSFColor.GOLD.index);
	    styleOrange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    styleOrange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleOrange.setFont(font1);
	    styleOrange.setWrapText(true);
	    
	    
	   

	
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(45);
		for(int i = 0;i < 17;i ++){
			HSSFCell head = titleRow.createCell(i);
			head.setCellValue(headName[i]);
			sheet.autoSizeColumn((short)i);
			if(i == 4||i == 7||i == 10||i == 12||i == 13){
				
			    head.setCellStyle(styleGreen);
			}else{

			    head.setCellStyle(styleYellow);
			}

		}
		for(int i = 17;i < headName.length;i ++){
			HSSFCell head = titleRow.createCell(i);
			head.setCellValue(headName[i]);
			
		
		    head.setCellStyle(styleOrange);
			
		}
		 
		for(int i = 1;i < list.size();i ++){
			String city = list.get(i).get("City").toString();
			String area = list.get(i).get("Area").toString();
			String city1 = ChineseToEnglishUtil.cityPinyin(city);
			String area1 = ChineseToEnglishUtil.cityPinyin(area);
			
			list.get(i).put("City", city1);
			list.get(i).put("Area",area1);
			HSSFRow row = sheet.createRow(i);
			row.setHeightInPoints(20);
			for(int j = 0;j < headName.length;j ++){
				
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(list.get(i).get(map.get(headName[j])).toString());
				cell.setCellStyle(style);
				sheet.autoSizeColumn((short)j);
			}
		}
		
		 BufferedOutputStream fos = null;  
	      try {  
	          String fileName = "Funnel-Eoulu-"+sdFormat.format(new Date())+".xls"; 
	          response.setContentType("application/x-msdownload");  
	          response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));  
	          fos = new BufferedOutputStream(response.getOutputStream());  
	          workbook.write(fos);  
	      } catch (Exception e) {  
	          e.printStackTrace();  
	      } finally {  
	    	  try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	          if (fos != null) {  
	              try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	          }  
	      }  
	}
	

}
