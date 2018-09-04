package com.eoulu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.LogInfoServiceImpl;

public class ExportLogUtil {

	public static boolean buidExcel(List<Map<String,Object>> ls,String path){
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet sheet = xwk.createSheet("操作日志");
		String[] colName = new String[]{"序号","操作账户","页面","操作记录","日期","时间","IP地址","地理位置"};
		int rowsCount = ls.size();
		int colCount = colName.length;
		boolean flag = false;
		XSSFRow xrow = sheet.createRow(0);
		for(int i=0;i<colCount;i++){
			
			xrow.createCell(i).setCellValue(colName[i]);
		}
		for(int i=1;i<rowsCount;i++){
			XSSFRow dataRow = sheet.createRow(i);
			
			for(int j=0;j<colCount;j++){
				XSSFCell dataCell = dataRow.createCell(j);
				Map<String,Object> map = ls.get(i);
				String val = "";
				if(colName[j].equals("序号")){
					val = i+"";
				}
				if(colName[j].equals("操作账户")){
					val = map.get("Account").toString()==null?"": map.get("Account").toString();
				}
				if(colName[j].equals("页面")){
					val = map.get("JspInfo").toString()==null?"": map.get("JspInfo").toString();
				}
				if(colName[j].equals("操作记录")){
					val = map.get("Description").toString()==null?"": map.get("Description").toString();
				}
				if(colName[j].equals("日期")){
					val = map.get("Date").toString()==null?"": map.get("Date").toString();
				}
				if(colName[j].equals("时间")){
					val = map.get("Time").toString()==null?"": map.get("Time").toString();
				}
				if(colName[j].equals("IP地址")){
					val = map.get("IpInfo").toString()==null?"": map.get("IpInfo").toString();
				}
				if(colName[j].equals("地理位置")){
					val = map.get("Location").toString()==null?"": map.get("Location").toString();
				}

				dataCell.setCellValue(val);
			}
			
		}
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static void main(String[] args) {
		LogInfoService service = new LogInfoServiceImpl();
		String path = "E:/test.xlsx";
	System.out.println(buidExcel(service.getAllData(), path));
	}
	
}
