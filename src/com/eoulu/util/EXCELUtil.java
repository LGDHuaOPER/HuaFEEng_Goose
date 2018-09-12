package com.eoulu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.eoulu.dao.OrderInfoDao;

/**
 * excel�ļ��Ĳ�����
 * 
 * @author zhangkai
 * 
 * @date 2017/3/23
 * 
 * */
public class EXCELUtil {


	/**
	 * excel xlsx ת list
	 * 
	 * 
	 * @param is excel�ļ���������
	 * */
	public Map<String, Object> xlsxToListByIndex(InputStream is){		
		List<Map<String,Object>> ls = null;
		XSSFWorkbook xwb = null;
		try {
			xwb = new XSSFWorkbook(is);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		//取得sheet的数目 
		int sheetCounts = xwb.getNumberOfSheets();
		Map<String, Object> excelMap = new TreeMap<String, Object>();
		
		for(int z=0; z<sheetCounts; z++){
			XSSFSheet sheet = xwb.getSheetAt(z);
			String sheetName = sheet.getSheetName();
			//������ȡ  ��ʼ
			XSSFRow xrCol = sheet.getRow(0);
			String[] colName=new String[xrCol.getPhysicalNumberOfCells()];
			Map<String, Object> mapColName = new TreeMap<String, Object>();
			for(int m=0; m<colName.length; m++){
				XSSFCell xcCol = xrCol.getCell(m);
				mapColName.put("col"+m,xcCol.getStringCellValue());
				colName[m] = xcCol.getStringCellValue();
			}
			ls = new ArrayList<Map<String,Object>>();
			ls.add(mapColName);
			//������ȡ  ����

			//�����ݶ�ȡ  ��ʼ
			int rowsCount = sheet.getLastRowNum()-sheet.getFirstRowNum()+1;
			for(int j=1; j<rowsCount; j++){
				XSSFRow xr = sheet.getRow(j);
				int colNum =   xr.getPhysicalNumberOfCells();
				Map<String, Object> map = new TreeMap<String,Object>();;
				for(int k=0; k<colNum; k++){
					XSSFCell xc = xr.getCell(k);

					try {
						if(xc==null){
							map.put(colName[k], "  ");
						}
						else if(xc.getCellType() == xc.CELL_TYPE_NUMERIC){
							map.put(colName[k], xc.getNumericCellValue());
						}
						else if(xc.getCellType() == xc.CELL_TYPE_STRING){
							map.put(colName[k], xc.getStringCellValue()+"");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				ls.add(map);

			}
			excelMap.put(sheetName, ls);
		}
		//�����ݶ�ȡ  ����
		return excelMap;
	}

	/**
	 * excel  xls ת list
	 * 
	 * 
	 * @param is excel�ļ���������
	 * */
	public Map<String, Object> xlsToListByIndex(InputStream is){
		List<Map<String,Object>> ls = null;
		HSSFWorkbook xwb = null;
		try {
			xwb = new HSSFWorkbook(is);
		}  catch (IOException e) {
			e.printStackTrace();
		}




		int sheetCounts = xwb.getNumberOfSheets();
		Map<String, Object> excelMap = new TreeMap<String, Object>();
		for(int z=0; z<sheetCounts; z++){
			HSSFSheet sheet = xwb.getSheetAt(z);
			String sheetName = sheet.getSheetName();
			//������ȡ  ��ʼ
			HSSFRow xrCol = sheet.getRow(0);
			String[] colName=new String[xrCol.getPhysicalNumberOfCells()];
			Map<String, Object> mapColName = new TreeMap<String, Object>();
			for(int m=0; m<colName.length; m++){
				HSSFCell xcCol = xrCol.getCell(m);
				mapColName.put("col"+m,xcCol.getStringCellValue());
				colName[m] = xcCol.getStringCellValue();
			}
			ls = new ArrayList<Map<String,Object>>();
			ls.add(mapColName);
			//������ȡ  ����

			//�����ݶ�ȡ  ��ʼ
			int rowsCount = sheet.getLastRowNum()-sheet.getFirstRowNum()+1;
			for(int j=1; j<rowsCount; j++){
				HSSFRow xr = sheet.getRow(j);
				int colNum =   xr.getPhysicalNumberOfCells();
				Map<String, Object> map = new TreeMap<String,Object>();;
				for(int k=0; k<colNum; k++){
					HSSFCell xc = xr.getCell(k);

					try {
//						if(xc==null){
//							map.put(colName[k], "  ");
//						}
//						else if(xc.getCellType() == xc.CELL_TYPE_NUMERIC){
//							map.put(colName[k], xc.getNumericCellValue());
//						}
//						else if(xc.getCellType() == xc.CELL_TYPE_STRING){
//							map.put(colName[k], xc.getStringCellValue()+"");
//						}
						map.put(colName[k], getCellValue(xc));

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				ls.add(map);

			}
			excelMap.put(sheetName, ls);
		}
		//�����ݶ�ȡ  ����
		return excelMap;
	}

	
	 //��ȡ��Ԫ���ֵ  
    private String getCellValue(Cell cell) {  
        String cellValue = "";  
        DataFormatter formatter = new DataFormatter();  
        if (cell != null) {  
            //�жϵ�Ԫ�����ݵ����ͣ���ͬ���͵��ò�ͬ�ķ���  
            switch (cell.getCellType()) {  
                //��ֵ����  
                case Cell.CELL_TYPE_NUMERIC:  
                    //��һ���ж� ����Ԫ���ʽ�����ڸ�ʽ   
                    if (DateUtil.isCellDateFormatted(cell)) {  
                        cellValue = formatter.formatCellValue(cell);  
                    } else {  
                        //��ֵ  
                        double value = cell.getNumericCellValue();  
                        int intValue = (int) value;  
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    cellValue = cell.getStringCellValue();  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:  
                    cellValue = String.valueOf(cell.getBooleanCellValue());  
                    break;  
                    //�жϵ�Ԫ���ǹ�ʽ��ʽ����Ҫ��һ�����⴦�����õ���Ӧ��ֵ  
                case Cell.CELL_TYPE_FORMULA:{  
                    try{  
                        cellValue = String.valueOf(cell.getNumericCellValue());  
                    }catch(IllegalStateException e){  
                        cellValue = String.valueOf(cell.getRichStringCellValue());  
                    }  
                      
                }  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    cellValue = "";  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    cellValue = "";  
                    break;  
                default:  
                    cellValue = cell.toString().trim();  
                    break;  
            }  
        }  
        return cellValue.trim();  
    }  
	

	/**
	 * excel  תmap
	 * 
	 * @param fileName  �ļ���
	 * 
	 * @param is  �ļ�������
	 * 
	 * */
	public Map<String, Object> excelToMap(String fileName,InputStream is){

		Map<String, Object> map = null;
		if(fileName.contains(".xlsx")){
			map = xlsxToListByIndex(is);
		}else if(fileName.contains(".xls")){
			map = xlsToListByIndex(is);
		}
		return map;
	}


	/**
	 * ����excel���
	 * 
	 * @param colName  ��������  ˳������
	 * 
	 * @param dataName ��Ӧ������data�е�map��
	 * 
	 * @param  data  excel����
	 * 
	 * @param  path  �ļ������·��
	 * */
	public boolean buildExcel(String[] colName,String[] dataName,List<Map<String,Object>> data,String path){
		boolean flag = false;
		//创建xls文件对象
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("one");
		//行数
		int rowsCount = data.size();
		//列数 
		int colsCount = colName.length;
		//创建第一行
		XSSFRow xrow = xsheet.createRow(0);
		for(int i=0;i<colsCount; i++){
			//创建单元格
			XSSFCell xcell = xrow.createCell(i);
			//在单元格中添加数据
			xcell.setCellValue(colName[i]);
		}
		//第一行创建写入完毕

		for(int i=1; i<rowsCount; i++){
			XSSFRow dataRow = xsheet.createRow(i);
			for(int j=0; j<colsCount; j++){
				XSSFCell dataCell = dataRow.createCell(j);
				
				String val = "";
				if(data.get(i).get(dataName[j]) != null){
					val = data.get(i).get(dataName[j]).toString();
				}
				
				if(dataName[j].equals("ContractNo")){
					//System.out.println("这步里没有17595755:"+val);
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
	
	public boolean buildStockExcel(Map<String,String[]> map,List<Map<String,Object>> data,String path){
		boolean flag = false;
		//创建xls文件对象
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("one");
		XSSFCellStyle style = xwk.createCellStyle();
		XSSFFont font = xwk.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("微软雅黑");
		style.setFont(font);
	
		//行数
		int rowsCount = data.size();
		//列数 
		int colsCount = map.get("colName").length;
		
		int orderInfoColsCount = map.get("orderInfoColName").length;
		
		int purchaseInfoCount = map.get("purchaseInfoColName").length;
		
	
		//创建第一行
		XSSFRow xrow = xsheet.createRow(0);
		xrow.setHeightInPoints(50);
		for(int i=0;i<colsCount -1; i++){
			//创建单元格
			XSSFCell xcell = xrow.createCell(i);
			//在单元格中添加数据
			xcell.setCellValue(map.get("colName")[i]);
			xcell.setCellStyle(style);
		}
		for(int j=colsCount-1;j<colsCount+orderInfoColsCount-1; j++){
		
			//创建单元格
			XSSFCell xcell = xrow.createCell(j);
			//在单元格中添加数据
			xcell.setCellValue(map.get("orderInfoColName")[j-colsCount+1]);
			xcell.setCellStyle(style);

		}

	
		for(int j = colsCount+orderInfoColsCount-1;j<colsCount+orderInfoColsCount+purchaseInfoCount-1; j++){
			XSSFCell xcell = xrow.createCell(j);
			//在单元格中添加数据
		
			xcell.setCellValue(map.get("purchaseInfoColName")[j-colsCount-orderInfoColsCount+1]);
			xcell.setCellStyle(style);

		}
		
		//第一行创建写入完毕
		int currentRow = 1;

		for(int i=1; i<rowsCount; i++){
			int infoSize = ((List)data.get(i).get("OrderInfo")).size();
			int m;
			if(infoSize > 1){
				for(m = 0;m < infoSize - 1;m ++){
					XSSFRow dataRow = xsheet.createRow(currentRow+m);
					
					for(int j=0; j<colsCount-1; j++){
						XSSFCell dataCell = dataRow.createCell(j);
						
						
						String val = "";
						if(data.get(i).get(map.get("dataName")[j]) != null){
							val = data.get(i).get(map.get("dataName")[j]).toString();
						}
						
						
						
						dataCell.setCellValue(val);	
						dataCell.setCellStyle(style);

					}
			
					for(int j=colsCount-1;j<colsCount+orderInfoColsCount-1; j++){
						//创建单元格
						XSSFCell dataCell = dataRow.createCell(j);
						//在单元格中添加数据
						String val = "";
						
						val = ((List<Map<String,Object>>)data.get(i).get("OrderInfo")).get(m + 1).get(map.get("orderInfoDataName")[j-colsCount+1]).toString();
						
	
						
						dataCell.setCellValue(val);	
						dataCell.setCellStyle(style);
					}

					for(int j = colsCount+orderInfoColsCount-1;j<colsCount+orderInfoColsCount+purchaseInfoCount-1; j++){
						//创建单元格
						XSSFCell dataCell = dataRow.createCell(j);
						//在单元格中添加数据
						String val = "";
						String colName = map.get("purchaseInfoDataName")[j-colsCount-orderInfoColsCount+1];
						
						val = ((List<Map<String,Object>>)data.get(i).get("PurchaseInfo")).get(1).get(colName).toString();
						
						if(colName.equals("Money")&&!val.equals("")&&!val.equals("--")){
							double money = Double.parseDouble(val);
							dataCell.setCellValue(money);
						}else{
							dataCell.setCellValue(val);	
		
						}
						dataCell.setCellStyle(style);
						
					}
					
				}
			
				if(m > 1){
					for(int j=0; j<colsCount-1; j++){
						
						xsheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow+m-1, j, j));
					}
					
					for(int j = colsCount+orderInfoColsCount-1;j<colsCount+orderInfoColsCount+purchaseInfoCount-1; j++){
						xsheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow+m-1, j, j));
					}
				}
				
				currentRow = currentRow + m;
				
				
			}else{
				XSSFRow dataRow = xsheet.createRow(currentRow+0);
				
				for(int j=0; j<colsCount-1; j++){
					XSSFCell dataCell = dataRow.createCell(j);
					
					
					String val = "";
					if(data.get(i).get(map.get("dataName")[j]) != null){
						val = data.get(i).get(map.get("dataName")[j]).toString();
					}
					
					
					
					dataCell.setCellValue(val);	
					dataCell.setCellStyle(style);
				}
				for(int j = colsCount+orderInfoColsCount-1;j<colsCount+orderInfoColsCount+purchaseInfoCount-1; j++){
					//创建单元格
					XSSFCell dataCell = dataRow.createCell(j);
					//在单元格中添加数据
					String val = "";
					String colName = map.get("purchaseInfoDataName")[j-colsCount-orderInfoColsCount+1];
					val = ((List<Map<String,Object>>)data.get(i).get("PurchaseInfo")).get(1).get(colName).toString();
					if(colName.equals("Money")&&!val.equals("")&&!val.equals("--")){
						double money = Double.parseDouble(val);
						dataCell.setCellValue(money);
					}else{
						dataCell.setCellValue(val);	
	
					}
					dataCell.setCellStyle(style);
					
					
				}
				currentRow = currentRow + 1;
			}
					
			
			
		}

		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
			xwk.close();
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	


		return flag;
	}


	@Test
	public void test(){

	}

	public void buildExcel(List<Map<String,Object>> ls,String path){
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet xsheet = xwk.createSheet("库存情况");
		String[] colName = new String[]{
	       "序号","型号","描述","条码号","苏州仓库","香港仓库","厦门仓库","成都仓库","合肥仓库","深圳仓库","北京仓库","石家庄仓库",
	       "库存总数","列表单价","列表总价","预定时间","预定客户","合同号","预定数量","预定仓库","预计发货时间"};
		Map<String, String> warehouse = new HashMap<>();
		
		warehouse.put("Suzhou", "苏州仓库");
		warehouse.put("Hefei", "合肥仓库");
		warehouse.put("Xianggang","香港仓库");
		warehouse.put("Xiamen", "厦门仓库");
		warehouse.put("Chengdu", "成都仓库");
		warehouse.put("Shenzhen", "深圳仓库");
		warehouse.put("Beijing", "北京仓库");
		warehouse.put("Shijiazhuang", "石家庄仓库");
		warehouse.put("", "");
		int m = 0;
		int n = 0;
		boolean continuity = false;
		Map<Integer, Integer> index = new HashMap<>();
		String inventoryID = ls.get(1).get("InventoryID").toString();;
		for(int i = 2;i < ls.size();i ++){
			String current = ls.get(i).get("InventoryID").toString();
			if(current.equals(inventoryID)){
				if(continuity){
					n = n + 1;
				}else{
					m = i - 1;
					n = i;
				}
				continuity = true;
				
			}else{
				if(continuity){
					index.put(m, n);
					System.out.println("------"+m+" "+n);
				}
				continuity = false;
		
			}
			inventoryID = current;
		}
		
		
		
		xsheet.setColumnWidth(0, (int) 3000);
		xsheet.setColumnWidth(1, (int) 5000);
		xsheet.setColumnWidth(2, (int) 12000);
		xsheet.setColumnWidth(3, (int) 5000);
		xsheet.setColumnWidth(4, (int) 3000);
		xsheet.setColumnWidth(5, (int) 3000);
		xsheet.setColumnWidth(6, (int) 3000);
		xsheet.setColumnWidth(7, (int) 3000);
		xsheet.setColumnWidth(8, (int) 3000);
		xsheet.setColumnWidth(9, (int) 3000);
		xsheet.setColumnWidth(10, (int) 3000);
		xsheet.setColumnWidth(11, (int) 3000);
		xsheet.setColumnWidth(12, (int) 3000);
		xsheet.setColumnWidth(13, (int) 3000);
		xsheet.setColumnWidth(14, (int) 3000);
		xsheet.setColumnWidth(15, (int) 4000);
		xsheet.setColumnWidth(16, (int) 6000);
		xsheet.setColumnWidth(17, (int) 4000);
		xsheet.setColumnWidth(18, (int) 4000);
		xsheet.setColumnWidth(19, (int) 4000);
		xsheet.setColumnWidth(20, (int) 4000);
		for(int start : index.keySet()){
			int end = index.get(start);
			for(int col = 0;col<15;col++){
				CellRangeAddress region = new CellRangeAddress(start, end, col, col);
				xsheet.addMergedRegion(region);
			}
		}
		
		XSSFCellStyle center = xwk.createCellStyle();
		center.setWrapText(true);// 自动换行
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setVerticalAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		int colsCount = colName.length; 
		for(int i=0;i<ls.size();i++){
			XSSFRow xrow = xsheet.createRow(i);
			for(int j=0;j<colsCount; j++){
				XSSFCell xcell = xrow.createCell(j);
				xcell.setCellStyle(center);
				if(i==0){
					xcell.setCellValue(colName[j]);
				}else{
					Map<String,Object> map = ls.get(i);
					switch (colName[j]) {
					case "序号":
						xcell.setCellValue(i+"");
						break;
					case "型号":
						xcell.setCellValue(map.get("Model").toString());
						break;
					case "描述":
						xcell.setCellValue(map.get("Description").toString());
						break;
					case "条码号":
						xcell.setCellValue(map.get("PNCode").toString());
						break;
					case "库存总数":
						xcell.setCellValue(Integer.parseInt(map.get("InventoryQuantity").toString()));
						break;
					case "苏州仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Suzhou").toString()));
						break;
					case "香港仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Xianggang").toString()));
						break;
					case "厦门仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Xiamen").toString()));
						break;
					case "成都仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Chengdu").toString()));
						break;
					case "合肥仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Hefei").toString()));
						break;
					case "北京仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Beijing").toString()));
						break;
					case "深圳仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Shenzhen").toString()));
						break;
					case "石家庄仓库":
						xcell.setCellValue(Integer.parseInt(map.get("Shijiazhuang").toString()));
						break;
					case "列表单价":
						xcell.setCellValue(Double.parseDouble(map.get("SellerPriceOne").equals("--")?"0":map.get("SellerPriceOne").toString()));
						break;
					case "列表总价":
						xcell.setCellValue(Double.parseDouble(map.get("ListPrice").equals("--")?"0":map.get("ListPrice").toString()));
						break;
				/*	case "备注":
						xcell.setCellValue(map.get("Remarks")==null?"":map.get("Remarks").toString());
						break; */
					case "预定时间":
						xcell.setCellValue(map.get("OrderTime").equals("--")?"":map.get("OrderTime").toString());
						break;
					case "预定客户":
						xcell.setCellValue(map.get("Customer").equals("--")?"":map.get("Customer").toString());
						break;
					case "合同号":
						xcell.setCellValue(map.get("ContractNO").equals("--")?"":map.get("ContractNO").toString());
						break;
					case "预定数量":
						if(map.get("OrderQuantity").equals("--")){
							xcell.setCellValue("");
						}else{
							xcell.setCellValue(Integer.parseInt(map.get("OrderQuantity").toString()));
						}
						break;
					case "预定仓库":
						xcell.setCellValue(warehouse.get(map.get("Warehouse").equals("--")?"":map.get("Warehouse").toString()));
						break;
					case "预计发货时间":
						xcell.setCellValue(map.get("EstimatedShippingTime").equals("--")?"":map.get("EstimatedShippingTime").toString());
						break;
				
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
			xwk.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void buildExcel(List<Map<String, Object>> equipmentList,
			Map<String, List<Map<String, Object>>> inventoryInfoList, String date, String path) {
		       //创建xls文件对象
				XSSFWorkbook xwk = new XSSFWorkbook();
				//创建一个名为 one 的sheet
				XSSFSheet xsheet = xwk.createSheet(date+"库存情况");
				String[] dates = date.split(",");
				String[] colName = new String[]{
						"序号","型号","描述","供应商","期初数量",dates[0]+"库存",
						"出/入库","时间","数量","合同号","PO/合同金额(USD)","PO/合同金额(RMB)","编码",dates[1]+"库存"
				};
				//列数 
				int colsCount = colName.length; 
				//创建第一行
				XSSFRow xrow = xsheet.createRow(0);
				for(int i=0;i<colsCount; i++){
					//创建单元格
					XSSFCell xcell = xrow.createCell(i);
					//在单元格中添加数据
					xcell.setCellValue(colName[i]);
				}
				//合并单元格：sheet.addMergedRegion(new CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
				int row = 1;
				for(int i=0;i<equipmentList.size();i++){
					
					List<Map<String, Object>> inventoryInfoData = inventoryInfoList.get(equipmentList.get(i).get("ID").toString());
					if(inventoryInfoData!=null&&inventoryInfoData.size()>0){
						int start = row;
						for(int j=0;j<inventoryInfoData.size();j++){
							XSSFRow xsrow = xsheet.createRow(row);
							if(j==0){
							XSSFCell xscell7 = xsrow.createCell(0);
							xscell7.setCellValue(row);
							XSSFCell xscell = xsrow.createCell(1);
							xscell.setCellValue(equipmentList.get(i).get("Model")==null?"":equipmentList.get(i).get("Model").toString());
							XSSFCell xscell2 = xsrow.createCell(2);
							xscell2.setCellValue(equipmentList.get(i).get("Remark")==null?"":equipmentList.get(i).get("Remark").toString());
							XSSFCell xscell3 = xsrow.createCell(3);
							xscell3.setCellValue(equipmentList.get(i).get("Supplier")==null?"":equipmentList.get(i).get("Supplier").toString());
							XSSFCell xscell4 = xsrow.createCell(4);
							xscell4.setCellValue(equipmentList.get(i).get("InitialQuantity")==null?"0":equipmentList.get(i).get("InitialQuantity").toString());
							XSSFCell xscell5 = xsrow.createCell(5);
							
								xscell5.setCellValue(equipmentList.get(i).get("Inventory1")==null?equipmentList.get(i).get("InitialQuantity")==null?"0":equipmentList.get(i).get("InitialQuantity").toString():equipmentList.get(i).get("Inventory1").toString());
							}
							XSSFCell xscell6 = xsrow.createCell(6);
							String Type = inventoryInfoData.get(j).get("Types").toString().equals("1")?"入库":"出库";
							xscell6.setCellValue(Type);
							
							XSSFCell xscell8 = xsrow.createCell(7);
							xscell8.setCellValue(inventoryInfoData.get(j).get("OperatingTime")==null?"":inventoryInfoData.get(j).get("OperatingTime").toString());
							XSSFCell xscell9 = xsrow.createCell(8);
							xscell9.setCellValue(inventoryInfoData.get(j).get("Quantity")==null?"":inventoryInfoData.get(j).get("Quantity").toString());
							XSSFCell xscell10 = xsrow.createCell(9);
							xscell10.setCellValue(inventoryInfoData.get(j).get("ContractNo")==null?"":inventoryInfoData.get(j).get("ContractNo").toString());
							XSSFCell xscell11 = xsrow.createCell(10);
							xscell11.setCellValue(inventoryInfoData.get(j).get("USDQuotes")==null?"":inventoryInfoData.get(j).get("USDQuotes").toString());
							XSSFCell xscell12 = xsrow.createCell(11);
							xscell12.setCellValue(inventoryInfoData.get(j).get("RMBQuotes")==null?"":inventoryInfoData.get(j).get("RMBQuotes").toString());
							XSSFCell xscell13 = xsrow.createCell(12);
							xscell13.setCellValue(inventoryInfoData.get(j).get("OperationCode")==null?"":inventoryInfoData.get(j).get("OperationCode").toString());
							XSSFCell xscell14 = xsrow.createCell(13);
							if(j==0){
								xscell14.setCellValue(equipmentList.get(i).get("Inventory2").toString());
							}
							row++;
						}
						if(row-start>1){
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,0,0));
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,1,1));
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,2,2));
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,3,3));
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,4,4));
							
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,5,5));
							xsheet.addMergedRegion(new CellRangeAddress(start,row-1,13,13));
						}
					}else{
						XSSFRow xsrow = xsheet.createRow(row);
						XSSFCell xscell7 = xsrow.createCell(0);
						xscell7.setCellValue(row);
						XSSFCell xscell = xsrow.createCell(1);
						xscell.setCellValue(equipmentList.get(i).get("Model")==null?"":equipmentList.get(i).get("Model").toString());
						XSSFCell xscell2 = xsrow.createCell(2);
						xscell2.setCellValue(equipmentList.get(i).get("Remark")==null?"":equipmentList.get(i).get("Remark").toString());
						XSSFCell xscell3 = xsrow.createCell(3);
						xscell3.setCellValue(equipmentList.get(i).get("Supplier")==null?"":equipmentList.get(i).get("Supplier").toString());
						XSSFCell xscell4 = xsrow.createCell(4);
						xscell4.setCellValue(equipmentList.get(i).get("InitialQuantity")==null?"0":equipmentList.get(i).get("InitialQuantity").toString());
						XSSFCell xscell5 = xsrow.createCell(5);
						xscell5.setCellValue(equipmentList.get(i).get("Inventory1")==null?equipmentList.get(i).get("InitialQuantity")==null?"0":equipmentList.get(i).get("InitialQuantity").toString():equipmentList.get(i).get("Inventory1").toString());
						xsrow.createCell(6);
						xsrow.createCell(7);
						xsrow.createCell(8);
						xsrow.createCell(9);
						xsrow.createCell(10);
						xsrow.createCell(11);
						xsrow.createCell(12);
						XSSFCell xscell14 = xsrow.createCell(13);
						xscell14.setCellValue(equipmentList.get(i).get("Inventory1")==null?equipmentList.get(i).get("InitialQuantity")==null?"0":equipmentList.get(i).get("InitialQuantity").toString():equipmentList.get(i).get("Inventory1").toString());
						row++;
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
	
	public static void buildLogisticsExcel(List<Map<String, Object>> list,String path){
		//创建xls文件对象
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("合同统计");
		
		String[] colName = new String[]{
				"合同号","合同名称","合同类型","客户名称","联系人","联系方式","合同地区","销售代表","合同货期","实际货期","预计货期","合同状态"
		};
		
		int rowsCount = list.size();
		//列数 
		int colsCount = colName.length; 
	
		//创建第一行
		XSSFRow xrow = xsheet.createRow(0);
		for(int i=0;i<colsCount; i++){
			//创建单元格
			XSSFCell xcell = xrow.createCell(i);
			//在单元格中添加数据
			xcell.setCellValue(colName[i]);
		}
		for(int i=1; i<rowsCount; i++){
			XSSFRow dataRow = xsheet.createRow(i);
			for(int j=0; j<colsCount; j++){
				
				XSSFCell dataCell = dataRow.createCell(j);
				Map<String, Object> map = list.get(i);
				String val = "";
				if(colName[j].equals("产品型号")){
					val =  map.get("Model").toString().equals("")? "--":map.get("Model").toString();
				}
				if(colName[j].equals("数量")){
					val =  map.get("Number").toString().equals("")? "--":map.get("Number").toString();
				}
				if(colName[j].equals("类型")){
					val =  map.get("Classify").toString().equals("")? "--":map.get("Classify").toString();
					//System.out.println(val);
				}
				if(colName[j].equals("描述")){
					val =  map.get("Remarks").toString().equals("")? "--":map.get("Remarks").toString();
					//System.out.println(val);
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
			xwk.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void buildProductExcel(List<Map<String, Object>> list,String path){
		//创建xls文件对象
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("合同统计");
		
		String[] colName = new String[]{
				"产品型号","数量","类型","描述"
		};
		
		int rowsCount = list.size();
		//列数 
		int colsCount = colName.length; 
	
		//创建第一行
		XSSFRow xrow = xsheet.createRow(0);
		for(int i=0;i<colsCount; i++){
			//创建单元格
			XSSFCell xcell = xrow.createCell(i);
			//在单元格中添加数据
			xcell.setCellValue(colName[i]);
		}
		for(int i=1; i<rowsCount; i++){
			XSSFRow dataRow = xsheet.createRow(i);
			for(int j=0; j<colsCount; j++){
				
				XSSFCell dataCell = dataRow.createCell(j);
				Map<String, Object> map = list.get(i);
				String val = "";
				if(colName[j].equals("产品型号")){
					val =  map.get("Model").toString().equals("")? "--":map.get("Model").toString();
				}
				if(colName[j].equals("数量")){
					val =  map.get("Number").toString().equals("")? "--":map.get("Number").toString();
				}
				if(colName[j].equals("类型")){
					val =  map.get("Classify").toString().equals("")? "--":map.get("Classify").toString();
					//System.out.println(val);
				}
				if(colName[j].equals("描述")){
					val =  map.get("Remarks").toString().equals("")? "--":map.get("Remarks").toString();
					//System.out.println(val);
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
	
	public static void buildOrderExcel(List<Map<String, Object>> list,String path){
		//创建xls文件对象
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("合同统计");
		
		String[] colName = new String[]{
				"客户名称","联系人","联系方式","合同地区","合同名称","合同号","合同类型","销售代表","合同签订日期","合同货期","实际货期","预计货期","预计收款日期",
				"装机地点","装机时间","合同金额USD", "合同金额RMB", "付款条件","对应报价单","收款日期1", "收款金额1", "收款日期2", "收款金额2", "收款日期3", "收款金额3", 
				"是否付款","付款时间","是否开具发票","发票快递单号","开票日期","是否办理免税信用证","备注"
		};
		String[] dataName = new String[]{
			"Customer","Contact","ContactInfo","Area", "ContractTitle","ContractNo","ContractCategory" ,"SalesRepresentative",
			"DateOfSign", "CargoPeriod","ActualDelivery","ExpectedDeliveryPeriod","ExpectedReceiptDate",
			"InstalledSite","InstalledTime","USDQuotes","RMBQuotes","PaymentTerms","Number",
			"ReceiptDate1","ReceiptAmount1", "ReceiptDate2", "ReceiptAmount2", "ReceiptDate3", "ReceiptAmount3",
			"WhetherToPay","PayDate","WhetherToInvoice","TrackingNo","BillingDate","DutyFree","DutyFreeRemarks"
			
		};
		
		int rowsCount = list.size();
		//列数 
		int colsCount = colName.length; 
	
		//创建第一行
		XSSFRow xrow = xsheet.createRow(0);
		for(int i=0;i<colsCount; i++){
			//创建单元格
			XSSFCell xcell = xrow.createCell(i);
			//在单元格中添加数据
			xcell.setCellValue(colName[i]);
		}
		for(int i=1; i<rowsCount; i++){
			XSSFRow dataRow = xsheet.createRow(i);
			for(int j=0; j<colsCount; j++){
				
				XSSFCell dataCell = dataRow.createCell(j);
				Map<String, Object> map = list.get(i);
				String val = "";
				
				if(colName[j].contains("金额")){
					String mString = map.get(dataName[j]).toString();
					if(mString.equals("--")){
						dataCell.setCellValue("");
					}else{
					double money = Double.parseDouble(mString);
					dataCell.setCellValue(money);	
					}
					
				}else if(colName[j].equals("是否开具发票")){
					val = map.get(dataName[j]).toString();;
					switch (val) {
					case "1":
						dataCell.setCellValue("是");
						break;

					case "0":
						dataCell.setCellValue("否");
						break;
					}
				}else{
					val = map.get(dataName[j]).toString();
					dataCell.setCellValue(val);	
				}
			}

		}

		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);

			fo.flush();
			fo.close();
			xwk.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		OrderInfoDao dao = new OrderInfoDao();
//		List<Map<String, Object>> ls = dao.getModelAndNumber();
//		String path = "d:\\test\\7-9月机台合同统计.xlsx";
//		buildProductExcel(ls,path);
		List<Map<String, Object>> ls2 = dao.getAllModelAndNumber();
		String path2 = "d:\\test\\DPP210总数shuzi的合同统计.xlsx";
		buildProductExcel(ls2,path2);
	}
	
	
	
}
