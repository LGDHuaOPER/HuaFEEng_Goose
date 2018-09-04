package com.eoulu.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StockListExcelUtil {

	public static void buildStockListExcel(List<Map<String, Object>> list, String path, String contractName,XSSFWorkbook xwk, int sheetNum,
			String sheetTitle) {
		XSSFSheet xsheet = xwk.createSheet();
		xwk.setSheetName(sheetNum, sheetTitle);
		xsheet.setColumnWidth(0, (int) 1500);
		xsheet.setColumnWidth(1, (int) 3000);
		xsheet.setColumnWidth(2, (int) 9000);
		xsheet.setColumnWidth(3, (int) 3000);
		xsheet.setColumnWidth(4, (int) 5000);
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setWrapText(true);// 自动换行
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);

		String[] colName = new String[] { "序号", "型号", "描述", "数量", "备注" };

		int rowsCount = list.size();
		int colsCount = colName.length; // 列数

		// 设置合并单元格的区域 行开始,行结束,列开始,列结束 标题
		xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// 创建第一行
		XSSFRow first = xsheet.createRow(0);
		first.setHeightInPoints(25);
		xsheet.autoSizeColumn(1, true);// 自适应宽度
		XSSFCell c1 = first.createCell(0);
		c1.setCellValue("发货清单-"+contractName);
		c1.setCellStyle(center);
		XSSFRow xrow = xsheet.createRow(1);
		xrow.setHeightInPoints(20);
		for (int i = 0; i < colsCount; i++) {
			// 创建单元格
			XSSFCell xcell = xrow.createCell(i);
			// 在单元格中添加数据
			xcell.setCellValue(colName[i]);
			xcell.setCellStyle(center);
		}
		for (int i = 2; i < rowsCount + 2; i++) {

			XSSFRow dataRow = xsheet.createRow(i);
			dataRow.setHeightInPoints(20);
			for (int j = 0; j < colsCount; j++) {

				XSSFCell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(center);
				Map<String, Object> map = list.get(i - 2);
				String val = "";

				if (colName[j].equals("序号")) {
					val = i - 1 + "";
				}
				if (colName[j].equals("数量")) {
					val = map.get("Quantity").toString().equals("") ? "--" : map.get("Quantity").toString();
				}
				if (colName[j].equals("型号")) {
					val = map.get("Model").toString().equals("") ? "--" : map.get("Model").toString();
					dataCell.setCellStyle(center);
				}
				if (colName[j].equals("描述")) {

					val = map.get("Description").toString().equals("") ? "--" : map.get("Description").toString();
				}
				if (colName[j].equals("备注")) {
					val = map.get("Remarks").toString().equals("") ? "--" : map.get("Remarks").toString();
				}

				dataCell.setCellValue(val);
			}

		}

	}

	public static void buildPackingExcel(List<Map<String, Object>> list, String contractNO, String po, String so,
			String path, XSSFWorkbook xwk, int sheetNum, String sheetTitle) {
		XSSFSheet sheet2 = xwk.createSheet();
		xwk.setSheetName(sheetNum, sheetTitle);

		CellStyle cs = xwk.createCellStyle();// 单元格样式
		cs.setWrapText(true);
		cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		CellStyle left = xwk.createCellStyle();// 单元格样式
		left.setWrapText(true);
		left.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		left.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		left.setBorderBottom(CellStyle.BORDER_THIN);
		left.setBorderLeft(CellStyle.BORDER_THIN);
		left.setBorderRight(CellStyle.BORDER_THIN);
		left.setBorderTop(CellStyle.BORDER_THIN);
		CellStyle right = xwk.createCellStyle();// 单元格样式
		right.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		right.setBorderBottom(CellStyle.BORDER_THIN);
		right.setBorderLeft(CellStyle.BORDER_THIN);
		right.setBorderRight(CellStyle.BORDER_THIN);
		right.setBorderTop(CellStyle.BORDER_THIN);
		String[] colName = new String[] { "序号", "合同信息", "要求", "选择" };
		int rowsCount = 9;
		int colsCount = colName.length;
		sheet2.autoSizeColumn((short) 1);
		sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		sheet2.setColumnWidth(0, (short) 1500);
		sheet2.setColumnWidth(1, (short) 5000);
		sheet2.setColumnWidth(2, (short) 6000);
		sheet2.setColumnWidth(3, (short) 5000);
		XSSFRow first = sheet2.createRow(0);
		first.setHeightInPoints(25);
		sheet2.autoSizeColumn(1, true);// 自适应宽度
		XSSFCell c1 = first.createCell(0);
		c1.setCellStyle(cs);
		c1.setCellValue("包装要求");
		c1.setCellStyle(cs);
		XSSFRow xrow = sheet2.createRow(1);
		xrow.setHeightInPoints(20);
		for (int i = 0; i < colsCount; i++) {
			XSSFCell xcell = xrow.createCell(i);
			xcell.setCellValue(colName[i]);
			xcell.setCellStyle(cs);
		}
		sheet2.addMergedRegion(new CellRangeAddress(2, 10, 1, 1));
		for (int i = 2; i < rowsCount + 2; i++) {
			XSSFRow dataRow = sheet2.createRow(i);
			dataRow.setHeightInPoints(20);
			for (int j = 0; j < colsCount; j++) {

				XSSFCell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(cs);
				Map<String, Object> map = list.get(i - 2);
				String val = "";
				if (colName[j].equals("序号")) {
					val = i - 1 + "";
				}
				if (colName[j].equals("要求") && i == 2) {
					val = "熏蒸木箱";
				}
				if (colName[j].equals("要求") && i == 3) {
					val = "尺寸";
				}
				if (colName[j].equals("要求") && i == 4) {
					val = "重量";
				}
				if (colName[j].equals("要求") && i == 5) {
					val = "产品图片";
				}
				if (colName[j].equals("要求") && i == 6) {
					val = "铭牌图片";
				}
				if (colName[j].equals("要求") && i == 7) {
					val = "原产地信息";
				}
				if (colName[j].equals("要求") && i == 8) {
					val = "产品品牌";
				}
				if (colName[j].equals("要求") && i == 9) {
					val = "包装箱数量";
				}
				if (colName[j].equals("要求") && i == 10) {
					val = "唛头";
				}
				if (colName[j].equals("选择") && i == 2) {
					if( map.get("Fumigation").toString()==null || map.get("Fumigation").toString().equals("")){
						val = "--";
					}else{
						val = map.get("Fumigation").toString();
					}
				}
				if (colName[j].equals("选择") && i == 3) {
					if( map.get("Size").toString()==null || map.get("Size").toString().equals("")){
						val = "--";
					}else{
						val = map.get("Size").toString();
					}
				}
				if (colName[j].equals("选择") && i == 4) {
					if( map.get("Weight").toString()==null || map.get("Weight").toString().equals("")){
						val = "--";
					}else{
						val = map.get("Weight").toString();
					}
				}
				if (colName[j].equals("选择") && i == 5) {
					if( map.get("ProductImg").toString()==null || map.get("ProductImg").toString().equals("")){
						val = "--";
					}else{
						val = map.get("ProductImg").toString();
					}
				}
				if (colName[j].equals("选择") && i == 6) {
					if( map.get("NamePlateImg").toString()==null || map.get("NamePlateImg").toString().equals("")){
						val = "--";
					}else{
						val = map.get("NamePlateImg").toString();
					}
				}
				if (colName[j].equals("选择") && i == 7) {
					if( map.get("OriginInfo").toString()==null || map.get("OriginInfo").toString().equals("")){
						val = "--";
					}else{
						val = map.get("OriginInfo").toString();
					}
				}
				if (colName[j].equals("选择") && i == 8) {
					if( map.get("ProductName").toString()==null || map.get("ProductName").toString().equals("")){
						val = "--";
					}else{
						val = map.get("ProductName").toString();
					}
				}
				if (colName[j].equals("选择") && i == 9) {
					if( map.get("PackingQty").toString()==null || map.get("PackingQty").toString().equals("")){
						val = "--";
					}else{
						val = map.get("PackingQty").toString();
					}
				}
				if (colName[j].equals("选择") && i == 10) {
					if( map.get("ShippingMark").toString()==null || map.get("ShippingMark").toString().equals("")){
						val = "--";
					}else{
						val = map.get("ShippingMark").toString();
					}
				}
				
				if (colName[j].equals("合同信息") && i == 2) {
					val = "Contract No.:" + contractNO + "\n " + "PO:" + po + "\n " + "SO:" + so;

				}
				dataCell.setCellValue(val);
			}

		}
		sheet2.addMergedRegion(new CellRangeAddress(11, 11, 1, 3));
		XSSFRow row12 = sheet2.createRow(11);
		XSSFCell c0 = row12.createCell(0);
		c0.setCellValue("备注：");
		c0.setCellStyle(right);
		row12.setHeightInPoints(45);
		XSSFCell c = row12.createCell(1);
		c.setCellStyle(left);
		String remark = "1、如需要物流部门提供以上信息，请进行勾选；" + "\n" 
		+ "2、如未勾选，物流部门将根据默认要求安排包装；" + "\n"
				+ "3、PO号和SO号由物流部门填写，其他请商务完善。";
		c.setCellValue(remark);
		row12.createCell(2).setCellStyle(left);
		row12.createCell(3).setCellStyle(left);

	}

	public static void buildTransportExcel(List<Map<String, Object>> list, String contractNO, String po, String so,
			String path, XSSFWorkbook xwk, int sheetNum, String sheetTitle) {
		XSSFSheet sheet3 = xwk.createSheet();
		xwk.setSheetName(sheetNum, sheetTitle);
		sheet3.setColumnWidth(0, (int) 1500);
		sheet3.setColumnWidth(1, (int) 5000);
		sheet3.setColumnWidth(2, (int) 4000);
		sheet3.setColumnWidth(3, (int) 4800);
		sheet3.setColumnWidth(4, (int) 5000);
		CellStyle cs = xwk.createCellStyle();// 单元格样式
		cs.setWrapText(true);
		cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		CellStyle left = xwk.createCellStyle();// 单元格样式
		left.setWrapText(true);
		left.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		left.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		left.setBorderBottom(CellStyle.BORDER_THIN);
		left.setBorderLeft(CellStyle.BORDER_THIN);
		left.setBorderRight(CellStyle.BORDER_THIN);
		left.setBorderTop(CellStyle.BORDER_THIN);
		CellStyle right = xwk.createCellStyle();// 单元格样式
		right.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		right.setBorderBottom(CellStyle.BORDER_THIN);
		right.setBorderLeft(CellStyle.BORDER_THIN);
		right.setBorderRight(CellStyle.BORDER_THIN);
		right.setBorderTop(CellStyle.BORDER_THIN);
		String[] colName = new String[] { "序号", "合同信息", "选项", "", "信息" };
		int rowsCount = 9;
		int colsCount = colName.length;
		sheet3.autoSizeColumn((short) 1);
		sheet3.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

		XSSFRow first = sheet3.createRow(0);
		first.setHeightInPoints(25);
		sheet3.autoSizeColumn(1, true);// 自适应宽度
		XSSFCell c1 = first.createCell(0);
		c1.setCellStyle(cs);
		c1.setCellValue("运输要求");
		XSSFRow xrow = sheet3.createRow(1);
		xrow.setHeightInPoints(20);
		sheet3.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
		for (int i = 0; i < colsCount; i++) {

			XSSFCell xcell = xrow.createCell(i);
			xcell.setCellValue(colName[i]);
			xcell.setCellStyle(cs);
		}
		sheet3.addMergedRegion(new CellRangeAddress(2, 10, 1, 1));
		Map<String, Object> map = list.get(0);
		for (int i = 2; i < rowsCount + 2; i++) {
			if (i == 2) {
				sheet3.addMergedRegion(new CellRangeAddress(2, 5, 2, 2));
			}
			if (i == 6) {
				sheet3.addMergedRegion(new CellRangeAddress(6, 8, 2, 2));
			}
			if (i == 9) {
				sheet3.addMergedRegion(new CellRangeAddress(9, 10, 2, 2));
			}
			XSSFRow dataRow = sheet3.createRow(i);
			dataRow.setHeightInPoints(20);
			if(i>1 && i<11){
				for (int j = 0; j < colsCount; j++) {

					XSSFCell dataCell = dataRow.createCell(j);
					String val = "";
					if (colName[j].equals("序号")) {
						val = i - 1 + "";
					}
					if (colName[j].equals("选项") && i == 2) {
						val = "发货信息";
					}
					if (colName[j].equals("选项") && i == 6) {
						val = "运输方式";
					}
					if (colName[j].equals("选项") && i == 9) {
						val = "送货方式";
					}
					if (colName[j].equals("") && i == 2) {
						val = "起运港";
					}
					if (colName[j].equals("") && i == 3) {
						val = "目的港";
					}
					if (colName[j].equals("") && i == 4) {
						val = "收货地址";
					}
					if (colName[j].equals("") && i == 5) {
						val = "是否接受分批发货";
					}
					if (colName[j].equals("") && i == 6) {
						val = "空运";
					}
					if (colName[j].equals("") && i == 7) {
						val = "中港卡车";
					}
					if (colName[j].equals("") && i == 8) {
						val = "快递";
					}
					if (colName[j].equals("") && i == 9) {
						val = "是否需要尾板车派送";
					}
					if (colName[j].equals("") && i == 10) {
						val = "是否需要卸货工具";
					}
					if (colName[j].equals("信息")) {
						if( map.get("Departure").toString()==null || map.get("Departure").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Departure").toString();
						}
					}
					if (colName[j].equals("信息") && i == 3) {
						if( map.get("Destination").toString()==null || map.get("Destination").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Destination").toString();
						}
					}
					if (colName[j].equals("信息") && i == 4) {
						if( map.get("Receiving").toString()==null || map.get("Receiving").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Receiving").toString();
						}
					}
					if (colName[j].equals("信息") && i == 5) {
						if( map.get("SplitShipment").toString()==null || map.get("SplitShipment").toString().equals("")){
							val = "--";
						}else{
							val = map.get("SplitShipment").toString();
						}
					}
					if (colName[j].equals("信息") && i == 6) {
						if( map.get("Airelift").toString()==null || map.get("Airelift").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Airelift").toString();
						}
					}
					if (colName[j].equals("信息") && i == 7) {
						if( map.get("Truck").toString()==null || map.get("Truck").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Truck").toString();
						}
					}
					if (colName[j].equals("信息") && i == 8) {
						if( map.get("FastMail").toString()==null || map.get("FastMail").toString().equals("")){
							val = "--";
						}else{
							val = map.get("FastMail").toString();
						}
					}
					if (colName[j].equals("信息") && i == 9) {
						if( map.get("TailCar").toString()==null || map.get("TailCar").toString().equals("")){
							val = "--";
						}else{
							val = map.get("TailCar").toString();
						}
					}
					if (colName[j].equals("信息") && i == 10) {
						if( map.get("Unloading").toString()==null || map.get("Unloading").toString().equals("")){
							val = "--";
						}else{
							val = map.get("Unloading").toString();
						}
					}
					if (colName[j].equals("合同信息") && i == 2 && j == 1) {
						val = "Contract No.:" + contractNO + "\n " + "PO:" + po + "\n " + "SO:" + so;

					}
					dataCell.setCellValue(val);
					dataCell.setCellStyle(cs);
				}
			}
			

		}

		sheet3.addMergedRegion(new CellRangeAddress(11, 11, 1, 4));
		XSSFRow row12 = sheet3.createRow(11);
		row12.setHeightInPoints(45);
		XSSFCell c0 = row12.createCell(0);
		c0.setCellValue("备注：");
		c0.setCellStyle(right);
		XSSFCell c = row12.createCell(1);
		c.setCellStyle(left);
		String remark = "1、空运，中港卡车，快递三者选其一；" + "\n" + "2、大型货物（超过100公斤）货物需要确认是否需要尾板车派送；" + "\n"
				+ "3、PO号和SO号由物流部门填写，其他请商务完善。";
		c.setCellValue(remark);
		row12.createCell(2).setCellStyle(cs);
		row12.createCell(3).setCellStyle(cs);
		row12.createCell(4).setCellStyle(cs);

	}
/*
	public static void main(String[] args) {
		String path = "D:\\temp\\stockList.xlsx";
		String contractNO = "深圳华为杨和钱EPS150机台";
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<>();
		m.put("Model", "asad");
		m.put("Quantity", "123");
		m.put("Description", "test");
		m.put("Remark", "test");
		list1.add(m);
		Map<String, Object> map = new HashMap<>();
		map.put("Request", "asad");
		map.put("Choose", "EXA001");
		map.put("Description", "test");
		map.put("Remark", "test");
		Map<String, Object> map1 = new HashMap<>();
		map1.put("Request", 22);
		map1.put("Choose", "EXA001");
		Map<String, Object> map2 = new HashMap<>();
		map2.put("Request", 22);
		map2.put("Choose", "EXA001");
		Map<String, Object> map3 = new HashMap<>();
		map3.put("Request", 22);
		map3.put("Choose", "EXA001");
		Map<String, Object> map4 = new HashMap<>();
		map4.put("Request", 22);
		map4.put("Choose", "EXA001");
		Map<String, Object> map5 = new HashMap<>();
		map5.put("Request", 22);
		map5.put("Choose", "EXA001");
		Map<String, Object> map6 = new HashMap<>();
		map6.put("Request", 22);
		map6.put("Choose", "EXA001");
		Map<String, Object> map7 = new HashMap<>();
		map7.put("Request", 22);
		map7.put("Choose", "EXA001");
		Map<String, Object> map8 = new HashMap<>();
		map8.put("Request", 22);
		map8.put("Choose", "EXA001");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		XSSFWorkbook xwk = new XSSFWorkbook();
		buildStockListExcel(list1, path, xwk, 0, "备货清单");
		// buildPackingExcel(list, path,xwk,1,"包装要求");
		// buildTransportExcel(list, path,xwk,2,"运输要求");
		// buidExcelMoreSheet(list1, list, list, path,contractNO);

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
	*/
}
