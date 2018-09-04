package com.eoulu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eoulu.entity.SizeInfo;

public class ReadExcelContentUtil {


	
	public static List<SizeInfo> readExcelContent(String fileNamePath,String contractNO){
		System.out.println("contractNO:"+fileNamePath);
		File file = new File(fileNamePath);
		List<SizeInfo> ls  = new ArrayList<SizeInfo>();
		try {
			InputStream is = new FileInputStream(file);
			if(fileNamePath.endsWith(".xls")){
				POIFSFileSystem fs = new POIFSFileSystem(is);
				HSSFWorkbook  wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				 List<CellRangeAddress> cras = getCombineCell(sheet); 
				 int rowNum = 100;
				 for(CellRangeAddress c : cras){
					int row =  c.getLastRow();
					if(rowNum > row && row >2){
						rowNum = row;
					}
				 }
//				int rowNum = sheet.getLastRowNum();
//				HSSFRow row = sheet.getRow(0);
//				int colNum = row.getPhysicalNumberOfCells();//获取不含空列的列数
				for(int row=0 ; row < rowNum ; row++ ){//获取当前工作簿的每一行
					HSSFRow xssfRow = sheet.getRow(row);
					SizeInfo sizeInfo =  new SizeInfo();
					if (xssfRow != null && row >2 ) {
					
						String dimension  = getCellFormatValue(xssfRow.getCell(1));
						System.out.println(dimension);
						double grossWeight = getCellFormatNumberValue(xssfRow.getCell(2));
						double netWeight = getCellFormatNumberValue(xssfRow.getCell(3));
						int quantity = (int) getCellFormatNumberValue(xssfRow.getCell(4));
						sizeInfo.setDimension(dimension);
						sizeInfo.setGrossWeight(grossWeight);
						sizeInfo.setNetWeight(netWeight);
						sizeInfo.setQuantity(quantity);
						sizeInfo.setContractNO(contractNO);
						
						ls.add(sizeInfo);
	                }
					
				}
			}
			if(fileNamePath.endsWith(".xlsx")){
				
				XSSFWorkbook wb = new XSSFWorkbook(is);
				for(int i = 0 ; i < wb.getNumberOfSheets(); i++){//读取每个工作簿
					XSSFSheet xssfSheet = wb.getSheetAt(i);
					if( xssfSheet == null){
						continue;
					}
					 //isMergedRegion(Sheet sheet,int row ,int column);//判断是不是合并单元格
					 List<CellRangeAddress> cras = getCombineCell(xssfSheet); 
					 int rowCount = 100;
					 for(CellRangeAddress c : cras){
						int row =  c.getLastRow();
						if(rowCount > row && row >2){
							rowCount = row;
						}
					 }
					 System.out.println("test:"+rowCount);
					for(int row=0 ; row < rowCount ; row++ ){//获取当前工作簿的每一行
						XSSFRow xssfRow = xssfSheet.getRow(row);
						SizeInfo sizeInfo =  new SizeInfo();
						if (xssfRow != null && row >2 ) {
						
							String dimension  = getValue(xssfRow.getCell(1));
							System.out.println(dimension);
							double grossWeight = Double.parseDouble(getValue(xssfRow.getCell(2)));
							double netWeight = Double.parseDouble(getValue(xssfRow.getCell(3)));
							int quantity = (int) Double.parseDouble(getValue(xssfRow.getCell(4)));
							sizeInfo.setDimension(dimension);
							sizeInfo.setGrossWeight(grossWeight);
							sizeInfo.setNetWeight(netWeight);
							sizeInfo.setQuantity(quantity);
							sizeInfo.setContractNO(contractNO);
							
							ls.add(sizeInfo);
		                }
						
					}
					
				}
				
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			
			e.printStackTrace();
		
		}
		return ls;
	}
	/**
	 * 判断指定的单元格是否为合并单元格
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	 private static boolean isMergedRegion(Sheet sheet,int row ,int column) {    
	      int sheetMergeCount = sheet.getNumMergedRegions();    
	      for (int i = 0; i < sheetMergeCount; i++) {    
	        CellRangeAddress range = sheet.getMergedRegion(i);    
	        int firstColumn = range.getFirstColumn();    
	        int lastColumn = range.getLastColumn();    
	        int firstRow = range.getFirstRow();    
	        int lastRow = range.getLastRow();    
	        if(row >= firstRow && row <= lastRow){    
	            if(column >= firstColumn && column <= lastColumn){    
	                return true;    
	            }    
	        }  
	      }    
	      return false;    
	    }
	/**
	 * 合并单元格处理,获取合并行
	 * @param sheet
	 * @return
	 */
	 public static List<CellRangeAddress> getCombineCell(Sheet sheet)    
	    {    
	        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();    
	        //获得一个 sheet 中合并单元格的数量    
	        int sheetmergerCount = sheet.getNumMergedRegions();    
	        //遍历所有的合并单元格    
	        for(int i = 0; i<sheetmergerCount;i++)     
	        {    
	            //获得合并单元格保存进list中    
	            CellRangeAddress ca = sheet.getMergedRegion(i);    
	            list.add(ca);    
	        }    
	        return list;    
	    }  
	 private static int getRowNum(List<CellRangeAddress> listCombineCell,Cell cell,Sheet sheet){  
	        int xr = 0;  
	        int firstC = 0;    
	        int lastC = 0;    
	        int firstR = 0;    
	        int lastR = 0;    
	        for(CellRangeAddress ca:listCombineCell)    
	        {  
	            //获得合并单元格的起始行, 结束行, 起始列, 结束列    
	            firstC = ca.getFirstColumn();    
	            lastC = ca.getLastColumn();    
	            firstR = ca.getFirstRow();    
	            lastR = ca.getLastRow();    
	            if(cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR)     
	            {    
	                if(cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC)     
	                {    
	                    xr = lastR;  
	                }   
	            }    
	              
	        }  
	        return xr;  
	          
	    }
	 /**    
	    * 获取合并单元格的值    
	    * @param sheet    
	    * @param row    
	    * @param column    
	    * @return    
	    */      
	    public String getMergedRegionValue(XSSFSheet xssfSheet ,int row , int column){      
	        int sheetMergeCount = xssfSheet.getNumMergedRegions();      
	              
	        for(int i = 0 ; i < sheetMergeCount ; i++){      
	            CellRangeAddress ca = xssfSheet.getMergedRegion(i);      
	            int firstColumn = ca.getFirstColumn();      
	            int lastColumn = ca.getLastColumn();      
	            int firstRow = ca.getFirstRow();      
	            int lastRow = ca.getLastRow();      
	                  
	            if(row >= firstRow && row <= lastRow){      
	                if(column >= firstColumn && column <= lastColumn){      
	                	XSSFRow xssfRow = xssfSheet.getRow(row);      
	                	XSSFCell fCell = xssfRow.getCell(firstColumn);      
	                    return getValue(fCell) ;      
	                }      
	            }      
	        }      
	              
	        return null ;      
	    }  
	public static String getValue(XSSFCell xssfRow) {

        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }
	
	
	/**
	 * 根据HSSFCell类型读取String格式
	 * @param cell
	 * @return
	 */
	public static String getCellFormatValue(HSSFCell cell) {
        String cellValue = "";
        if (cell != null) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellValue = cell.getStringCellValue();
        }
        return cellValue;
    }
	
	 /**
     * 根据HSSFCell类型读取日期格式
     */
    public static Date getCellFormatDateValue(HSSFCell cell) {
        Date date = null;
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            date = cell.getDateCellValue();
        }
        return date;
    }
    public static double getCellFormatNumberValue(HSSFCell cell){
    	double cellValue = 0;
    	 if (cell != null) {
             cell.setCellType(Cell.CELL_TYPE_NUMERIC);
             cellValue = cell.getNumericCellValue();
         }
    	 return cellValue;
    }
 
}
