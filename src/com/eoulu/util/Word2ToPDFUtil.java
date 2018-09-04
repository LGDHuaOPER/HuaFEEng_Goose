package com.eoulu.util;

import java.io.File;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Word2ToPDFUtil {

	
	public static boolean pdf(String input,String out){
		long start = System.currentTimeMillis();  
        ActiveXComponent app = null;  
        try {  
            app = new ActiveXComponent("Word.Application");  
            app.setProperty("Visible", false);  
            Dispatch docs = app.getProperty("Documents").toDispatch();  
            Dispatch doc = Dispatch.call(docs, "Open", input, false, true).toDispatch();  
            File tofile = new File(out);  
            if (tofile.exists()) {  
                tofile.delete();  
            }  
            Dispatch.call(doc, "SaveAs", out, 17);  
            Dispatch.call(doc, "Close", false);  
            long end = System.currentTimeMillis();  
            return true;  
        } catch (Exception e) {  
        	e.printStackTrace();
            return false;  
        } finally {  
            if (app != null) {  
                app.invoke("Quit", new Variant[]{});  
            }  
            ComThread.Release();
        }  
	}
	
	
	private static ActiveXComponent com = null;
	private static Object doc = null;
	private final static Variant False = new Variant(false);
	private final static Variant True = new Variant(true);
	
	/**
	 * 打开word文档
	 * @param docPath
	 * @return
	 */
	public static boolean OpenWord(String docPath){
		
		com = new ActiveXComponent("Word.Application");//建立ActiveX部件
		try{
			Object wordDocs = com.getProperty("Documents").toDispatch();//返回com.toDispatch
			
			//调用com.Documents.Open方法打开指定word文档，返回doc
			doc = Dispatch.invoke((Dispatch)wordDocs, "Open", Dispatch.Method, new Object[]{docPath}, new int[1]).toDispatch();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
		
	}
	/**
	 * 关闭word文档
	 */
	public static void closedWord(){
		if(doc!=null){
			int save = 0;
			Variant doNotSaveChange = new Variant(save);
			com.invoke("Quit", new Variant[]{doNotSaveChange} );
			com =null;
			ComThread.Release();
		}
	}
	
	public static void docToPDF(String docPath,String psPath,String pdfPath){
		
		if(!OpenWord(docPath)){
			closedWord();
			return;
		}
		//建立Adobe Distiller的com对象
		ActiveXComponent distiller = new ActiveXComponent("PDFDistiller.PDFDistiller.1");
		try{
		
		com.setProperty("ActivePrinter", new Variant("RICOH Aficio MP C2800"));//设置当前使用的打印机
		System.out.println("置当前使用的打印机");
		Variant background=False;//是否在后台运行
		Variant append = False;//是否追加打印机
		
		int printAllDoc = 0;
		Variant range = new Variant(printAllDoc);//打印所以文档
		
		Variant psName = new Variant(psPath);//输出ps文件路径
		//调用word文档的打印方法：将word文档打印为postScript文档
		Dispatch.callN((Dispatch)doc, "PrintOut", new Variant[]{background,append,range,psName});
		
		Variant inputPs = new Variant(psPath);
		Variant outPdf = new Variant(pdfPath);
		Variant option = new Variant("");
		
		Dispatch.callN(distiller, "FileToPDF", new Variant[]{inputPs,outPdf,option});
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closedWord();
		}
	}
	public static void main(String[] args) {
		String input = "E:/EOULU美金合同参数.doc";
		String out = "E:/test.ps";
		String pdf = "E:/return.pdf";
//		docToPDF(input, out, pdf);
//		System.out.println(pdf(input, out));
		try {
			System.out.println(wordToPDF(input, pdf));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		ActiveXComponent distiller = new ActiveXComponent("PDFDistiller.PDFDistiller.1");
	}
	
	
	
	
	 static final int wdFormatPDF = 17;// PDF 格式      
	    public static int wordToPDF(String sfileName,String toFileName) throws Exception{      
	              
	        System.out.println("启动Word...");        
	        long start = System.currentTimeMillis();        
	        ActiveXComponent app = null;    
	        Dispatch doc = null;    
	        try {        
	            app = new ActiveXComponent("Word.Application");   
	            // 设置word不可见  
	            app.setProperty("Visible", new Variant(false));    
	            // 打开word文件  
	            Dispatch docs = app.getProperty("Documents").toDispatch();     
//	          doc = Dispatch.call(docs,  "Open" , sourceFile).toDispatch();     
	            doc = Dispatch.invoke(docs,"Open",Dispatch.Method,new Object[] {                      
	               sfileName, new Variant(false),new Variant(true) }, new int[1]).toDispatch();  
	            System.out.println("打开文档..." + sfileName);    
	            System.out.println("转换文档到PDF..." + toFileName);        
	            File tofile = new File(toFileName);      
	           // System.err.println(getDocPageSize(new File(sfileName)));  
	            if (tofile.exists()) {        
	                tofile.delete();        
	            }          
//	          Dispatch.call(doc, "SaveAs",  destFile,  17);     
	         // 作为html格式保存到临时文件：：参数 new Variant(8)其中8表示word转html;7表示word转txt;44表示Excel转html;17表示word转成pdf。。  
	            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {                  
	                toFileName, new Variant(17) }, new int[1]);      
	            long end = System.currentTimeMillis();        
	            System.out.println("转换完成..用时：" + (end - start) + "ms.");                  
	        } catch (Exception e) {    
	            e.printStackTrace();    
	            System.out.println("========Error:文档转换失败：" + e.getMessage());        
	        }catch(Throwable t){  
	            t.printStackTrace();  
	        } finally {    
	            // 关闭word  
	            Dispatch.call(doc,"Close",false);    
	            System.out.println("关闭文档");    
	            if (app != null)        
	                app.invoke("Quit", new Variant[] {});        
	            }    
	          //如果没有这句话,winword.exe进程将不会关闭    
	           ComThread.Release();    
	           return 1;  
	           }    
	    public  int getDocPageSize(String filePath)  throws Exception {  
	        XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));  
	        int pages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();//总页数  
	        int wordCount = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getCharacters();// 忽略空格的总字符数 另外还有getCharactersWithSpaces()方法获取带空格的总字数。          
	        System.out.println ("pages=" + pages + " wordCount=" + wordCount);  
	        return pages;  
	    }  
	
}
