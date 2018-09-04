package com.eoulu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.w3c.dom.Node;

import com.eoulu.entity.DocumentModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtil {
	public static void pdf(List<Map<String,Object>> Quotations,List<Map<String,Object>> equipment,String path,String fileName){
		Document document=new Document();
		try {
			PdfWriter.getInstance(document,new FileOutputStream(path+fileName+".pdf"));
			document.open();
			Image img = Image.getInstance("C:/Users/浣欐檽鍊�/Desktop/images/鎶ヤ环鍗曠涓�鐗�---鍓湰_01.gif");
			img.setAlignment(Element.ALIGN_CENTER);//灞呬腑瀵归綈
			img.scaleToFit(500,500);//澶у皬聽聽
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph("\n\n"));
			//鎻掑叆琛ㄦ牸
			PdfPTable table1=new PdfPTable(3);
			PdfPCell cell1;
			cell1 = new PdfPCell(new Phrase("Number:QUA024170321005"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER); //姘村钩灞呬腑 
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); //鍨傜洿灞呬腑
			table1.addCell(cell1);
			cell1 = new PdfPCell(new Phrase("Date Sent:2017/07/07"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER); //姘村钩灞呬腑 
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); //鍨傜洿灞呬腑
			table1.addCell(cell1);
			cell1 = new PdfPCell(new Phrase("Version:1"));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER); //姘村钩灞呬腑 
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); //鍨傜洿灞呬腑00000000
			table1.addCell(cell1);
			document.add(table1);
			document.add(new Paragraph("\n\n"));
			Image img3 = Image.getInstance("C:/Users/浣欐檽鍊�/Desktop/images/鐢佃瘽.png");
			img3.setAlignment(Element.ALIGN_CENTER);//灞呬腑瀵归綈
			img3.scaleToFit(50,50);//澶у皬聽
			document.add(img3);
			Image img4 = Image.getInstance("C:/Users/浣欐檽鍊�/Desktop/images/鑱旂郴浜�.png");
			img4.setAlignment(Element.ALIGN_CENTER);//灞呬腑瀵归綈
			img4.scaleToFit(50,50);//澶у皬聽
			document.add(img4);
			Image img5 = Image.getInstance("C:/Users/浣欐檽鍊�/Desktop/images/閭欢.png");
			img5.setAlignment(Element.ALIGN_CENTER);//灞呬腑瀵归綈
			img5.scaleToFit(50,50);//澶у皬聽
			document.add(img5);
			//鎻掑叆琛ㄦ牸
			document.add(new Paragraph("\n\n"));//绌烘牸
			PdfPTable table=new PdfPTable(6);
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("Quotation"));
			cell.setColspan(6);//鍚堝苟3鍒�
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //姘村钩灞呬腑 
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //鍨傜洿灞呬腑
			cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0)); //璁剧疆琛ㄦ牸鑳屾櫙棰滆壊涓虹伆鑹�
			table.addCell(cell);
			//cell = new PdfPCell(new Phrase("Cell聽with聽rowspan聽2"));
			//cell.setRowspan(2);
			//cell.setBorderColor(new BaseColor(255, 0, 0));//璁剧疆琛ㄦ牸杈规涓虹孩鑹�
			//table.addCell(cell);
			table.addCell("Item");
			table.addCell("Part");
			table.addCell("Description");
			table.addCell("Each锛圲SD锛�");
			table.addCell("Qty");
			table.addCell("Extend锛圲SD锛�");
			//
			table.addCell("1");
			table.addCell("EPS-150");
			table.addCell("This is a test remark");
			table.addCell("100");
			table.addCell("1");
			table.addCell("100");
			//
			table.addCell("2");
			table.addCell("EPS-150");
			table.addCell("This is a test remark");
			table.addCell("100");
			table.addCell("1");
			table.addCell("100");
			//琛ㄦ牸浣嶇疆100%聽聽
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			document.add(table);
			document.add(new Paragraph("\n\n"));
			Image img2 = Image.getInstance("C:/Users/浣欐檽鍊�/Desktop/images/1(2).jpg");
			img2.setAlignment(Element.ALIGN_CENTER);//灞呬腑瀵归綈
			img2.scaleToFit(1000,600);//澶у皬聽
			document.add(img2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			document.close();
		}
	}

	
	 public String getForm(File file01,HttpServletRequest request,String tempPath) throws FileUploadException, IOException{
			Map<String, String> map=new HashMap<String, String>();
			 DocumentModel model = new DocumentModel();
			String fileName=null;
			String name = null;
			DiskFileItemFactory factory = new DiskFileItemFactory();
		    factory.setRepository(file01);//设置临时目录
		    factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		    items = upload.parseRequest(request);
		    byte data[] = new byte[1024];
		    if (items != null){
		        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
		            FileItem item = (FileItem) iterator.next();
		            if (item.isFormField()) {
		            	if("name".equals(item.getFieldName())) 
		                 {
		            		name = item.getString("utf-8");
		                	System.out.println("content:"+name);
		                 }
		            	
		            }else{
		                fileName = item.getName().substring(
		                        item.getName().lastIndexOf(File.separator) + 1,
		                        item.getName().length());
		                InputStream inputStream = item.getInputStream();
		                OutputStream outputStream = new FileOutputStream(tempPath + fileName);
		                int i;
						while ((i = inputStream.read(data)) != -1) {
		                    outputStream.write(data, 0, i);
		                }
		                inputStream.close();
		                outputStream.close();
		                fileName = tempPath + fileName;
		            }
		        }
		    }
		    
		  
			return fileName;
		}
	 public static void htmlCodeComeFromFile(String filePath, String pdfPath) {  
	        Document document = new Document();  
	        try {  
	            StyleSheet st = new StyleSheet();  
	            st.loadTagStyle("body", "leading", "16,0");  
	            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));  
	            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);  
	            Font fontChinese = new Font(bfChinese, 12, Font.NORMAL); 
	            document.open();  
	            ArrayList p = HTMLWorker.parseToList(new FileReader(filePath), st);  
	            for(int k = 0; k < p.size(); ++k) {  
	                document.add((Element)p.get(k));  
	            	
	            }  
	            document.close();  
	            System.out.println("文档创建成功");  
	        }catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	    
	    }  
	 public static String toFormatedXML(Document object) throws Exception {
			Document doc = (Document) object;
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFactory.newTransformer();
			transFormer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			DOMSource domSource = new DOMSource((Node) doc);

			StringWriter sw = new StringWriter();
			StreamResult xmlResult = new StreamResult(sw);

			transFormer.transform(domSource, xmlResult);

			return sw.toString();

		}
	 public static boolean htmlCodeComeString(String htmlCode, String pdfPath) {  
		 boolean flag = false;
	        Document doc = new Document(PageSize.A4);  
	        try {  
	        	StyleSheet st = new StyleSheet();  
	            st.loadTagStyle("body", "leading", "16,0");
	            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));  
	            doc.open();  
	            ArrayList p = HTMLWorker.parseToList(new FileReader(pdfPath), st);  
	            for (int k = 0; k < p.size(); ++k){ 
	              doc.add((Element) p.get(k));  
	            }
	           
	            // 解决中文问题  
	            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
	            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);  
	            Paragraph t = new Paragraph(htmlCode, FontChinese);  
	            if(t != null){
	            	flag = true;
	            }
	            doc.add(t);  
	            doc.close();  
	            System.out.println("文档创建成功");  
	        }catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	        return flag;
	    }  
	 public static void main(String[] args) {  
		 
		 String htmlCode = "<html><head></head><body><span style='font-family:Times New Roman;font-size:10pt;'><br/></span>"
		 		+ "<table border='1' cellpadding='0' cellspacing='0'><tr><td width='10605'>commercial INVOICE</td></tr><tr><td width='5959'>Seller</td><td width='5959'>HK EOULU TRADING LIMITED</td><td width='5959'>ROOM 1501 GRAND MILLENNIUM PLAZA (LOWER BLOCK) 181 QUEEN'S ROAD CENTRAL, HONG KONG</td><td width='5959'>TEL: 00852-21527388</td><td width='5959'>FAX: 00852-35719160</td><td width='4646'>Invoice No. and Date</td><td width='4646'>CONTRACT NO.: GNHCHNHI17033111LXJ</td><td width='4646'>PO: 910071239</td><td width='4646'>INVOICE NO. : E100913</td><td width='4646'>DC No.: NA</td></tr><tr><td width='5959'>Applicant</td><td width='5959'>HiSilicon Technologies CO., LIMITED</td>"
		 		+ "<td width='5959'>Huawei Base, Bantian, Longgang District, Shenzhen, P.R.China</td><td width='5959'>TEL: +86 755 28780808</td><td width='5959'>FAX: +86 755 28357515</td><td width='4646'>The End User(if other than consignee)</td><td width='4646'>NA</td></tr><tr><td width='5959'></td><td width='4646'>Other reference</td><td width='4646'>NA</td></tr><tr><td width='5959'>Departure date:</td><td width='5959'>NA</td><td width='4646'>NA</td></tr><tr><td width='5959'>Vessel/flight          From</td><td width='5959'>HONGKONG</td><td width='4646'></td></tr><tr><td width='5959'>To</td><td width='5959'>SHENZHEN</td><td width='4646'></td></tr><tr><td width='878'>Item</td>"
		 		+ "<td width='3300'>Description</td><td width='1781'>Brand/Origin</td><td width='567'>Qty</td><td width='1984'>Unit price(USD)</td><td width='2095'>Total amount(USD)</td></tr><tr><td width='878'>1</td><td width='3300'>Summit12000B-M</td><td width='3300'>PROBE STATION PLATFORM, SEMI-AUTOMATI WITH MICROCHAMBER</td><td width='1781'>Cascade Microtech</td><td width='1781'>/Germany</td><td width='567'>1</td><td width='1984'>239,088</td><td width='2095'>239,088</td></tr><tr><td width='8510'>TOTAL AMOUNT (CIP SHENZHEN) USD:</td><td width='2095'>239,088</td></tr>"
		 		+ "<tr><td width='7869'>Bank details</td><td width='7869'>Bank name    HSBC HONGKONG</td><td width='7869'>Bank code    004</td><td width='7869'>Bank address  1 Queen’s Road Central, HongKong</td><td width='7869'>SWIFT Code  HSBCHKHHHKH</td><td width='7869'>Account name  HK EOULU TRADING LIMITED</td><td width='7869'>Account No   801-012469-838</td><td width='2736'></td><td width='2736'></td></tr><tr><td width='1929'>Signature:</td><td width='4680'></td><td width='3996'>Date:2017/6/30</td></tr></table><span style='font-family:Times New Roman;font-size:10pt;'><br/></body></html>";
		 boolean a = htmlCodeComeString(htmlCode, "e:test.pdf");
		 System.out.println(a);
	 }
}
