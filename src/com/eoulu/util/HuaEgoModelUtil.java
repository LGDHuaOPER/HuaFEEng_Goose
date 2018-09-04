package com.eoulu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HuaEgoModelUtil {

	public void buildEgoComplete(List<Map<String, Object>> list, String path) {

		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet xsheet = xwk.createSheet("item");
		String[] colName = new String[] { "Item", "Item Description", "Quantity" };
		int rowsCount = list.size();
		int colsCount = colName.length;
		xsheet.setColumnWidth(0, (int) 5000);
		xsheet.setColumnWidth(1, (int) 9000);
		xsheet.setColumnWidth(2, (int) 4000);
		XSSFCellStyle center1 = xwk.createCellStyle();
		center1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center1.setWrapText(true);// 自动换行
		center1.setBorderBottom(CellStyle.BORDER_THIN);
		center1.setBorderLeft(CellStyle.BORDER_THIN);
		center1.setBorderRight(CellStyle.BORDER_THIN);
		center1.setBorderTop(CellStyle.BORDER_THIN);
		XSSFCellStyle style = xwk.createCellStyle();// 创建单元格样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);// 自动换行
		XSSFCellStyle cell = xwk.createCellStyle();
		cell.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 居左
		cell.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cell.setWrapText(true);// 自动换行
		cell.setBorderBottom(CellStyle.BORDER_THIN);
		cell.setBorderLeft(CellStyle.BORDER_THIN);
		cell.setBorderRight(CellStyle.BORDER_THIN);
		cell.setBorderTop(CellStyle.BORDER_THIN);
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		center.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		center.setWrapText(true);// 自动换行
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		XSSFRow xrow = xsheet.createRow(0);
		for (int i = 0; i < colsCount; i++) {
			// 创建单元格
			XSSFCell xcell = xrow.createCell(i);
			// 在单元格中添加数据
			xcell.setCellValue(colName[i]);
			xcell.setCellStyle(center1);
		}
		for (int i = 1; i < rowsCount; i++) {
			XSSFRow dataRow = xsheet.createRow(i);
			for (int j = 0; j < colsCount; j++) {
				XSSFCell dataCell = dataRow.createCell(j);
				Map<String, Object> map = list.get(i);
				String val = "";
				if (colName[j].equals("Item")) {
					dataCell.setCellStyle(center);
					val = map.get("Item")==null?"":map.get("Item").toString();
				}
				if (colName[j].equals("Item Description")) {
					dataCell.setCellStyle(cell);
					val = map.get("ItemDescription")==null?"":map.get("ItemDescription").toString();
				}
				if (colName[j].equals("Quantity")) {
					dataCell.setCellStyle(center);
					val = map.get("Qty")=="--"?"0":map.get("Qty").toString();
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void buildEgoParts (List<Map<String, Object>> list, String path) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet xsheet = xwk.createSheet("配件");
		String[] colName = new String[] { "序号", "部件编码", "Item描述","型号","描述","单价USD","折扣USD","数量","总价","折扣率","备注" };
		int rowsCount = list.size() ;
		int colsCount = colName.length;
		xsheet.setColumnWidth(0, (int) 1500);
		xsheet.setColumnWidth(1, (int) 3000);
		xsheet.setColumnWidth(2, (int) 8000);
		xsheet.setColumnWidth(3, (int) 3000);
		xsheet.setColumnWidth(4, (int) 6000);
		xsheet.setColumnWidth(5, (int) 3000);
		xsheet.setColumnWidth(6, (int) 3000);
		xsheet.setColumnWidth(7, (int) 3000);
		xsheet.setColumnWidth(8, (int) 3000);
		xsheet.setColumnWidth(9, (int) 3000);
		xsheet.setColumnWidth(10, (int) 3000);
		XSSFCellStyle center1 = xwk.createCellStyle();
		center1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center1.setWrapText(true);// 自动换行
		center1.setBorderBottom(CellStyle.BORDER_THIN);
		center1.setBorderLeft(CellStyle.BORDER_THIN);
		center1.setBorderRight(CellStyle.BORDER_THIN);
		center1.setBorderTop(CellStyle.BORDER_THIN);
		XSSFCellStyle style = xwk.createCellStyle();// 创建单元格样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);// 自动换行
		XSSFCellStyle cell = xwk.createCellStyle();
		cell.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 居左
		cell.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cell.setWrapText(true);// 自动换行
		cell.setBorderBottom(CellStyle.BORDER_THIN);
		cell.setBorderLeft(CellStyle.BORDER_THIN);
		cell.setBorderRight(CellStyle.BORDER_THIN);
		cell.setBorderTop(CellStyle.BORDER_THIN);
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		center.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		center.setWrapText(true);// 自动换行
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		XSSFRow first = xsheet.createRow(0);
		XSSFCell xcell1 = first.createCell(0);
		first.setHeightInPoints(25);
		xsheet.autoSizeColumn(1, true);// 自适应宽度
		xcell1.setCellValue("华为配件采购报价单");
		xcell1.setCellStyle(center1);
		XSSFRow xrow = xsheet.createRow(1);
		for (int i = 0; i < colsCount; i++) {
			// 创建单元格
			XSSFCell xcell = xrow.createCell(i);
			// 在单元格中添加数据
			xcell.setCellValue(colName[i]);
			xcell.setCellStyle(center);
		}
		for (int i = 1; i < rowsCount; i++) {
			XSSFRow dataRow = xsheet.createRow(i+1);
				for (int j = 0; j < colsCount; j++) {
					XSSFCell dataCell = dataRow.createCell(j);
					Map<String, Object> map = list.get(i);
					System.out.println(map);
					String val = "";
					if (colName[j].equals("序号")) {
						dataCell.setCellStyle(center);
						val = ""+i;
					}
					if (colName[j].equals("部件编码")) {
						dataCell.setCellStyle(center);
						val = map.get("Item")==null?"":map.get("Item").toString();
					}
					if (colName[j].equals("Item描述")) {
						dataCell.setCellStyle(cell);
						val = map.get("ItemDescription")==null?"":map.get("ItemDescription").toString();
					}
					if (colName[j].equals("型号")) {
						dataCell.setCellStyle(center);
						val = map.get("Model")==null?"":map.get("Model").toString();
					}
					if (colName[j].equals("描述")) {
						dataCell.setCellStyle(center);
						val = map.get("CommodityName")==null?"":map.get("CommodityName").toString();
					}
					if (colName[j].equals("单价USD")) {
						dataCell.setCellStyle(center);
						val = map.get("UnitUSD")=="--"?"0":map.get("UnitUSD").toString();
						if(Double.parseDouble(val)==0){
							val = "";
						}
					}
					if (colName[j].equals("折扣USD")) {
						dataCell.setCellStyle(center);
						val = map.get("DiscountedUSD")=="--"?"0":map.get("DiscountedUSD").toString();
						if(Double.parseDouble(val)==0){
							val = "";
						}
					}
					if (colName[j].equals("数量")) {
						dataCell.setCellStyle(center);
						val = map.get("Qty")=="--"?"0":map.get("Qty").toString();
					}
					if (colName[j].equals("总价")) {
						dataCell.setCellStyle(center);
						val = map.get("TotalPrice")=="--"?"0":map.get("TotalPrice").toString();
						if(Double.parseDouble(val)==0){
							val = "";
						}
					}
					if (colName[j].equals("折扣率")) {
						dataCell.setCellStyle(center);
						val = map.get("DiscountedPercent")=="--"?"0":map.get("DiscountedPercent").toString();
						if(Double.parseDouble(val)==0){
							val = "";
						}
					}
					if (colName[j].equals("备注")) {
						dataCell.setCellStyle(center);
						val = map.get("Remarks")==null?"":map.get("Remarks").toString();
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	

}
