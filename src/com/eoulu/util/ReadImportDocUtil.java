package com.eoulu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ReadImportDocUtil {

	public static void main(String[] args) {
//		ReadAbstract("E:/Commercial Invoice-10%.doc");
		System.out.println(extractDoc("E:\\BugzillaV5.0.1修改版.doc", "E:/Commercial-Invoice-10%.txt"));
	}
	
	public static void ReadAbstract(String path){
		OPCPackage opcPackage;
		try {

//			InputStream is = new FileInputStream(new File(path));
//			WordExtractor ex = new WordExtractor(is);
//			String text2003 = ex.getText();
//			System.out.println(text2003);
			 opcPackage = POIXMLDocument.openPackage("E:/Commercial Invoice-10%.docx");
			 POIXMLTextExtractor extractor = new
			 XWPFWordExtractor(opcPackage);
			 String text2007 = extractor.getText();
			 System.out.println(text2007);
		} catch (IOException e) {
			e.printStackTrace();
			 } catch (XmlException e) {
			 e.printStackTrace();
			 } catch (OpenXML4JException e) {
			 e.printStackTrace();
		}
	}
	public void importDoc(String input){
	
		FileInputStream in;
		
		try {
			in = new FileInputStream(input);
			if(input.endsWith(".doc")){
				POIFSFileSystem pfs = new POIFSFileSystem(in);
				HWPFDocument hwpf = new HWPFDocument(pfs);
				Range range = hwpf.getRange();// 得到文档的读取范围
				TableIterator it = new TableIterator(range);

//				FileWriter fileWriter = new FileWriter(new File("result.txt"));

				// 迭代文档中的表格
				while (it.hasNext()) {
					Table tb = (Table) it.next();
					// 迭代行，默认从0开始
					if (tb.numRows() > 0) {
						for(int i=0 ; i<tb.numRows();i++){
//							System.out.println("num:"+i);
							TableRow tr = tb.getRow(i);
//							System.out.println("tr:"+tr.numCells());
							// 迭代列，默认从0开始
							for(int j=0 ; j<tr.numCells();j++){
								TableCell td1 = tr.getCell(j);// 取得单元格
								// 取得单元格的内容
								String str1 = td1.text().trim();
								if(i==1 & j==1){
									System.out.println(str1.split(":")[1].split("\r")[0]);
									System.out.println(str1.split(":")[2].split("\r")[0]);
									System.out.println(str1.split(":")[3].split("\r")[0]);
								}
								
							}
								
						}
						
					}
				}
			}
			
			if(input.endsWith(".docx")){
				in = new FileInputStream("E:/Commercial Invoice-10%.docx");
				XWPFDocument  swpf = new XWPFDocument (in);
				Iterator<XWPFTable> tables = swpf.getTablesIterator();
				List<XWPFTableRow> rows;
				List<XWPFTableCell> cells;
//				FileWriter fileWriter = new FileWriter(new File("result.txt"));
				
				// 迭代文档中的表格
				while (tables.hasNext()) {
					XWPFTable tb = (XWPFTable) tables.next();
					// 迭代行，默认从0开始
					rows = tb.getRows();
					if ( rows.size() > 0) {
						for(int i=0 ; i<rows.size();i++){
							XWPFTableRow tr = tb.getRow(i);
							// 迭代列，默认从0开始
							cells = tr.getTableCells();
							 for (XWPFTableCell cell : cells) {  
					                System.out.println(cell.getText());;  
					            }  
								
						}
						
					} 
				} 
			}
			

//			fileWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // 载入文档
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 把word文档转换为TXT格式的文档
	 * @param inputFile word文件的绝对路径
	 * @param outputFile 转化为TXT文件后的绝对路径
	 * @return 转化之后的TXT文件的字符串
	 */
	public static String extractDoc(String inputFile, String outputFile) {
		
		String content = "";
		
		ComThread.InitSTA();
		
		//打开word应用程序
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		Dispatch doc2 = null ;
		try {
			//设置word不可见
			app.setProperty("Visible", new Variant(false));
	
			Dispatch doc1 = app.getProperty("Documents").toDispatch();//所有文档窗口

			 doc2 = Dispatch.invoke(doc1,"Open",Dispatch.Method,new Object[] { inputFile, new Variant(false),
							new Variant(true) }, new int[1]).toDispatch();			

			//作为TXT格式，保存到了临时文件
			Dispatch.invoke(doc2, "SaveAs", Dispatch.Method, new Object[] {
					outputFile, new Variant(7) }, new int[1]);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭Word
			Variant f = new Variant(false);
			Dispatch.call(doc2, "Close", f);//doc2关闭
			app.invoke("Quit", new Variant[] {});//退出word
			ComThread.Release();
			content = getContent(outputFile);
			//WordString.delete(outputFile);
			ReadImportDocUtil.forceDelete(outputFile);
		}
		return content;
		
	}
	
	/**
	 * 删除文件
	 * @param file 需要删除的文件的路径
	 */
//	public static void delete(String file) {
//		File afile = new File(file);
////		boolean bool = false;
//		while(afile.exists())
//		{ 
//			afile.delete();
////			int i = 1 ;
//		}
		
//	}
	/**
	 * 强力删除文件
	 * @param file 需要删除的文件的路径
	 */
	public static boolean forceDelete(String file)   
	{   
		File f =new File(file);
	    boolean result = false;   
	    int tryCount = 0;   
	    while(!result && tryCount++ <10)   
	   {   
	    	System.gc();   //System.gc()直接调用了系统内存回收
	    	result = f.delete();   
	   }   
	   return result;   
	}
	
	
	
	/**
	 * 取得TXT文件的内容
	 * @param file TXT文件的路径
	 * @return TXT文件中的字符串
	 */
	public static String getContent(String file) {
		StringBuffer sb = new StringBuffer();
		String temp = "";
//		int count=0;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
			BufferedReader bis = new BufferedReader(isr);
			while((temp=bis.readLine()) != null ){
				sb.append(temp);
//				if( count<3 && count>=1){
//					sb.append(temp);
//				}
//				count++;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = sb.toString().substring(0,200);
		return result;
	}
	
	
}
