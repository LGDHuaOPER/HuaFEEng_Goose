package com.eoulu.util;

import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.schema.FileResourceLoader;

public class ExportExcel<T> {
	public void exportExcel(String[] headers,Collection<T> dataset, String fileName,HttpServletResponse response,Map<String, String> map) {  
        // 声明一个工作薄  
        XSSFWorkbook workbook = new XSSFWorkbook();  
        // 生成一个表格  
        XSSFSheet sheet = workbook.createSheet(fileName);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 20);  
        // 产生表格标题行  
        XSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        try {  
            // 遍历集合数据，产生数据行  
            Iterator<T> it = dataset.iterator();  
            int index = 0;  
            while (it.hasNext()) {  
                index++;  
                row = sheet.createRow(index);  
                T t = (T) it.next();  
           
                for (short i = 0; i < headers.length; i++) {  
                    XSSFCell cell = row.createCell(i);   
                    String fieldName = map.get(headers[i]); 
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = "";  
                    // 其它数据类型都当作字符串简单处理  
                    if(value != null && value != ""){  
                        textValue = value.toString();  
                    }  
                    if (textValue != null) {  
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);  
                        cell.setCellValue(richString);  
                    }  
                }  
                
            }  
            getExportedFile(workbook, fileName,response);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  


	
      
    /** 
     *  
     * 方法说明: 指定路径下生成EXCEL文件 
     * @return 
     */  
    public void getExportedFile(XSSFWorkbook workbook, String name,HttpServletResponse response) throws Exception {  
        BufferedOutputStream fos = null;  
        try {  
            String fileName = name + ".xlsx";  
            response.setContentType("application/x-msdownload");  
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));  
            fos = new BufferedOutputStream(response.getOutputStream());  
            workbook.write(fos);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                fos.close();  
            }  
        }  
    }  

}
