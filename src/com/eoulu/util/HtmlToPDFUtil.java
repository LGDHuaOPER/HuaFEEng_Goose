package com.eoulu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.w3c.dom.Document;


public class HtmlToPDFUtil {

	private final static String wkHtmlToPdf = "C:/Program Files/wkhtmltopdf/bin/wkhtmltopdf.exe";
	
	public static boolean convert(String srcPath, String destPath){  
        File file = new File(destPath);  
        File parent = file.getParentFile();  
        //如果pdf保存路径不存在，则创建路径  
        if(!parent.exists()){  
            parent.mkdirs();  
        }  
          
        StringBuilder cmd = new StringBuilder();  
        cmd.append(wkHtmlToPdf);  
        cmd.append(" ");  
        cmd.append(srcPath);  
        cmd.append(" ");  
        cmd.append(destPath);  
          
        boolean result = true;  
        try{  
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());  
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());  
            error.start();  
            output.start();  
            proc.waitFor();  
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
	
	public static void main(String[] args) {
		
		String in = "E:/result.doc";
		String out = "D:/testPdf.html";
//		System.out.println(convert(in, out));
		docToHtml(in, out);
	}
	
	public static void docToHtml(String input,String outPath){
		InputStream inputFile;
		try {
			inputFile = new FileInputStream(new File(input));
			HWPFDocument wordDocument = new HWPFDocument(inputFile);
			 WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			 wordToHtmlConverter.processDocument(wordDocument);
			  Document htmlDocument = wordToHtmlConverter.getDocument();
			  File htmlFile = new File(outPath);
			  OutputStream outStream = new FileOutputStream(htmlFile);
			  DOMSource domSource = new DOMSource(htmlDocument);
			  StreamResult streamResult = new StreamResult(outStream);
			  TransformerFactory factory = TransformerFactory.newInstance();
			  Transformer serializer = factory.newTransformer();
			  serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			  serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			  serializer.setOutputProperty(OutputKeys.METHOD, "html");
			  serializer.transform(domSource, streamResult);
			 
			  outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
