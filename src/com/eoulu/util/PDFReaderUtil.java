package com.eoulu.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class PDFReaderUtil {
	public static String getTextFromPDF(String pdfFilePath) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            //使用文件输入流读取PDF存放路径
            is=new FileInputStream(pdfFilePath);
            //将输入流传入进行转换器PDFParser 
            PDFParser parser =new PDFParser(is);
            parser.parse();
            //从转换器获得PDDocument
            document= parser.getPDDocument();
            //将PDDocument放入文件剥离器PDFTextStripper剥离文字
            PDFTextStripper stripper = new  PDFTextStripper();
            //设置文本是否按照位置排序
            stripper.setSortByPosition(true);
            //设置起始页
           
            result=stripper.getText(document);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
	
	public static void main(String[] args) {
        String pdfFilePath="D:\\test\\HKEO-Jiangsu RUGAO-Tesla 200mm-183957-R1-WW-20180528.pdf";
        String result =null;
        result=getTextFromPDF(pdfFilePath);
        int beginIndex = result.indexOf("Quote Total: USD");
		int endIndex = 0;
		for(int i = beginIndex;i < beginIndex +50;i++ ){
			if(result.charAt(i)=='\n'){
				endIndex = i;
				break;
			}
		}
		String text = result.substring(beginIndex,endIndex);
		
        System.out.println(text);
        String quoteTotal = text.split(":")[1].trim();
        System.out.println(quoteTotal);
     }  

}
