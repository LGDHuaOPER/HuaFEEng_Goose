package com.eoulu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eoulu.dao.CustomerInquiryDao;
import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.RequirementServiceImpl;

public class ExportExcelUtil {

	public void export(List<Map<String, Object>> list, String path, String[] titleNames, String sheetName,
			int rowsCount) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet sheet = xwk.createSheet(sheetName);
		int colCount = titleNames.length;
		XSSFRow xrow = sheet.createRow(0);
		for (int i = 0; i < colCount; i++) {
			xrow.createCell(i).setCellValue(titleNames[i]);
		}
		int mainLength = list.size();
		int temp = 1;//标记合并项的第一行
		for (int k = 0; k < mainLength; k++) {// 遍历复杂的集合
			Map<String, Object> map = list.get(k);
			String CustomerName = map.get("CustomerName").toString();
			String QuoteTime = map.get("QuoteTime").toString();
			String TotalPrice = map.get("TotalPrice").toString();
			String Type = map.get("Type").toString();
			List<Map<String, Object>> ls = (List<Map<String, Object>>) map.get("records");
			int merge = ls.size() - 1;
			int c = 1;
			for (int i = temp; i <= rowsCount; i++) {
				XSSFRow xrows = sheet.createRow(i);
				for (int j = 0; j < colCount; j++) {
					XSSFCell xcell = xrows.createCell(j);
					if (i == temp) {
						if (merge > 1) {
							if (j == 0) {
								sheet.addMergedRegion(new CellRangeAddress(temp, temp + merge - 1, j, j));
								xcell.setCellValue(k + 1 + "");
							}
							if (j == 1) {
								sheet.addMergedRegion(new CellRangeAddress(temp, temp + merge - 1, j, j));
								xcell.setCellValue(CustomerName);
							}
							if (j == 6) {
								sheet.addMergedRegion(new CellRangeAddress(temp, temp + merge - 1, j, j));
								xcell.setCellValue(TotalPrice);
							}
							if (j == 7) {
								sheet.addMergedRegion(new CellRangeAddress(temp, temp + merge - 1, j, j));
								xcell.setCellValue(Type);
							}
							if (j == 8) {
								sheet.addMergedRegion(new CellRangeAddress(temp, temp + merge - 1, j, j));
								xcell.setCellValue(QuoteTime);
							}
						} else {
							if (j == 0) {
								xcell.setCellValue(k + 1 + "");
							}
							if (j == 1) {
								xcell.setCellValue(CustomerName);
							}
							if (j == 6) {
								xcell.setCellValue(TotalPrice);
							}
							if (j == 7) {
								xcell.setCellValue(Type);
							}
							if (j == 8) {
								xcell.setCellValue(QuoteTime);
							}
						}

					}
					for (int l = 1; l < ls.size(); l++) {
						if (l == c) {
							if (j == 2) {
								xcell.setCellValue(
										ls.get(l).get("Model") == null ? "" : ls.get(l).get("Model").toString());
							}
							if (j == 3) {
								xcell.setCellValue(
										ls.get(l).get("Remarks") == null ? "" : ls.get(l).get("Remarks").toString());
							}
							if (j == 5) {
								xcell.setCellValue(
										ls.get(l).get("Price") == null ? "" : ls.get(l).get("Price").toString());
							}
							if (j == 4) {
								xcell.setCellValue(
										ls.get(l).get("Quantity") == null ? "" : ls.get(l).get("Quantity").toString());
							}
							break;
						}
					}

				}
				c++;
			}

				temp = merge + temp;
		}
		// 设置合并单元格的区域 行开始,行结束,列开始,列结束 标题
		// xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出Excel（不带任何格式）
	 * @param list 查询结果
	 * @param titleNames 标题栏
	 * @param maps 标题集合
	 * @param path 生成路径
	 * @param sheetName Excel名称
	 */
	public void ExportExcelUtil(List<Map<String, Object>> list, String[] titleNames,Map<String,Object> maps,String path,  String sheetName){
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet sheet = xwk.createSheet(sheetName);
		for(int i=0;i<list.size();i++){
			XSSFRow xrow = sheet.createRow(i);
			for(int j=0;j<titleNames.length;j++){
				XSSFCell xcell = xrow.createCell(j);
				if(i==0){
					xcell.setCellValue(titleNames[j]);
				}else{
					if(j==0){
						xcell.setCellValue(i+"");
					}else{
						Map<String,Object> map = list.get(i);
						String key = maps.get(titleNames[j]).toString();
						xcell.setCellValue(map.get(key)==null?"":map.get(key).toString());
					}
					
				}
			}
			
		}
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RequirementService service = new RequirementServiceImpl();
		Map<String,Object> map = null;
		List<Map<String,Object>> ls = service.getTime();
		String start = ls.get(1).get("StartTime").toString();
		String end = ls.get(1).get("EndTime").toString();
		map = service.getStatisticsByArea(start, end);
		String path = "E:详细记录.xlsx";
//		 new RequirementServiceImpl().builDetailsExcel(map, path);
		 System.out.println(111);
	}
	
}
