package com.eoulu.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.eoulu.entity.ExcelReplaceDataVO;

public class ExcelReplaceUtil {
	/** 
     * 替换Excel模板文件内容 
     * @param datas 文档数据 
     * @param sourceFilePath Excel模板文件路径 
     * @param targetFilePath Excel生成文件路径 
     */  
    @SuppressWarnings("deprecation")
	public static boolean replaceModel(List<ExcelReplaceDataVO> datas, String sourceFilePath, String targetFilePath) {  
        boolean bool = true;  
        try {  
  
            POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(sourceFilePath));     
            HSSFWorkbook wb = new HSSFWorkbook(fs);  
            HSSFSheet sheet = wb.getSheetAt(0);  
              
            for (ExcelReplaceDataVO data : datas) {  
                //获取单元格内容  
                HSSFRow row = sheet.getRow(data.getRow());     
                HSSFCell cell = row.getCell((short)data.getColumn());  
                String str = cell.getStringCellValue();  
                  
                //替换单元格内容  
                str = str.replace(data.getKey(), data.getValue());  
                  
                //写入单元格内容  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
         
                cell.setCellValue(str);     
            }  
  
            // 输出文件     
            FileOutputStream fileOut = new FileOutputStream(targetFilePath);     
            wb.write(fileOut);     
            fileOut.close();     
  
        } catch (Exception e) {  
            bool = false;  
            e.printStackTrace();  
        }  
        return bool;  
    }  
    public static void insertRow(HSSFWorkbook wb, HSSFSheet sheet, int starRow,int rows) {
    	sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows,true,false);
    	//  Parameters:
    	//   startRow - the row to start shifting
    	//   endRow - the row to end shifting
    	//   n - the number of rows to shift
    	//   copyRowHeight - whether to copy the row height during the shift
    	//   resetOriginalRowHeight - whether to set the original row's height to the default
    	  
    	starRow = starRow - 1;

    	for (int i = 0; i < rows; i++) {

	    	HSSFRow sourceRow = null;
	    	HSSFRow targetRow = null;
	    	HSSFCell sourceCell = null;
	    	HSSFCell targetCell = null;
	    	short m;
	
		    starRow = starRow + 1;
		    sourceRow = sheet.getRow(starRow);
		    targetRow = sheet.createRow(starRow + 1);
		    targetRow.setHeight(sourceRow.getHeight());

		    for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
	
			    sourceCell = sourceRow.getCell(m);
			    targetCell = targetRow.createCell(m);
		
			
			    targetCell.setCellStyle(sourceCell.getCellStyle());
			    targetCell.setCellType(sourceCell.getCellType());
			    switch (targetCell.getCellType()) {

	    	

	    		case Cell.CELL_TYPE_BOOLEAN:

	    			targetCell.setCellValue(sourceCell.getBooleanCellValue());

	    			break;

	    		case Cell.CELL_TYPE_ERROR:

	    			targetCell.setCellValue(sourceCell.getErrorCellValue());

	    			break;

	    		case Cell.CELL_TYPE_FORMULA:
	    			targetCell.setCellFormula(sourceCell.getCellFormula());

	    			break;
	    	

	    		case Cell.CELL_TYPE_NUMERIC:

	    			targetCell.setCellValue(sourceCell.getNumericCellValue());

	    			break;

	    		case Cell.CELL_TYPE_STRING:

	    			targetCell.setCellValue(sourceCell.getStringCellValue());

	    			break;
			    }

	    		
			   
			   
		    }
	    }
    }
    	

    	   
    	  

    	 
    public static void main(String[] args){
    	/*List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();  
		ExcelReplaceDataVO v01 = new ExcelReplaceDataVO();
		v01.setColumn(2);
		v01.setRow(5);
		v01.setKey("{InvoiceTitle}");
		v01.setValue("ffff");
		datas.add(v01);
		replaceModel(datas, "E:/Model/开票委托单.xls", "E:/开票申请/开票委托单.xls");*/
		ExcelReplaceUtil util = new ExcelReplaceUtil();
		 POIFSFileSystem fs  = null;     
         HSSFWorkbook wb = null;
		try {
			fs  =new POIFSFileSystem(new FileInputStream("E:/Model/开票委托单.xls"));
			wb = new HSSFWorkbook(fs);
			  
            
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		HSSFSheet sheet = wb.getSheetAt(0);
		insertRow(wb, sheet, 11, 5);
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream("E:/开票申请/开票委托单.xls");
			wb.write(fo);
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    
	
		
		
    }


}
