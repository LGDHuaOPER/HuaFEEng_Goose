package com.eoulu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

public class PDFTest {

	public static void main(String[] args) {
		
		 List<String> ponum=new ArrayList<String>();
		 add(ponum, 26);
		 List<String> line=new ArrayList<String>();
		 add(line, 26);
		 List<String> part=new ArrayList<String>();
		 add(part, 26);
		 List<String> description=new ArrayList<String>();
		 add(description, 26);
		 List<String> origin=new ArrayList<String>();
		 add(origin, 26);
		 
		 Document document=new Document();
		 try {
			//BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			 BaseFont bfChinese=BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			Font keyfont=new Font(bfChinese,8,Font.BOLD);
			Font textfont=new Font(bfChinese,8,Font.NORMAL);
			 PdfWriter.getInstance(document, new FileOutputStream(new File("D:\\POReceiveReport.pdf")));
			 document.open();
			 int recordPerPage=10;
			          int fullPageRequired=ponum.size()/recordPerPage;
			          int remainPage=ponum.size()%recordPerPage>1?1:0;
			          int totalPage=fullPageRequired+remainPage;
			          
			         for(int j=0;j<totalPage;j++){
			             document.newPage();
			             
			             //create page number
			              String pageNo=leftPad("页码: "+(j+1)+" / "+totalPage,615);
			             Paragraph pageNumber=new Paragraph(pageNo, keyfont) ;
			            document.add(pageNumber);
			             
			             //create title image
			             Image jpeg=Image.getInstance("D:\\title.PNG");
			             jpeg.setAlignment(Image.ALIGN_CENTER);
			             jpeg.scaleAbsolute(530, 37);
			              document.add(jpeg);
			             
			              //header information
			             Table tHeader=new Table(2);
			             float[] widthsHeader={2f,3f};
			              tHeader.setWidths(widthsHeader);
			              tHeader.setWidth(100);
			              
			             
			          //   tHeader.getDefaultLayout().setBorder(PdfPCell.NO_BORDER);
			             
			             
			            String compAdd="河源市高新技术开发区兴业大道中66号";
			             String company="丰达音响（河源）有限公司";
			              String vendor="V006";
			             String vendorName="中山市卢氏五金有限公司";
			            String ccn="FHH";
			             String mas_loc="FHH";
			              String delivery_note="20130718001";
			             String receive_date="20130718";
			              String dept="H11";
			             String asn="0123456789";
			            
			              Cell c1Header=new Cell(new Paragraph("地址："+compAdd,keyfont));
			             
			             tHeader.addCell(c1Header);
			           c1Header=new Cell(new Paragraph("供应商："+vendor,keyfont));
			             tHeader.addCell(c1Header);
			             c1Header=new Cell(new Paragraph("公司："+company,keyfont));
			             tHeader.addCell(c1Header);
			             c1Header=new Cell(new Paragraph("供应商工厂："+vendorName,keyfont));
			             tHeader.addCell(c1Header);
			             c1Header = new Cell(new Paragraph("CCN:   "+ccn+"    Master Loc:   "+mas_loc,keyfont));
			             tHeader.addCell(c1Header);
			            c1Header = new Cell(new Paragraph("送货编号: "+delivery_note+"                             送货日期: "+receive_date,keyfont));
			             tHeader.addCell(c1Header);
		             c1Header=new Cell(new Paragraph("Dept："+dept,keyfont));
			             tHeader.addCell(c1Header);
			             c1Header=new Cell(new Paragraph("ASN#："+asn,keyfont));
			            tHeader.addCell(c1Header);
			             document.add(tHeader);
			             //record header field
		             Table t=new Table(5);
			             float[] widths={1.5f,1f,1f,1.5f,1f};
			             t.setWidths(widths);
			            t.setWidth(100);
			           //  t.getDefaultLayout().setBorder(PdfPCell.NO_BORDER);
			             Cell c1 = new Cell(new Paragraph("PO#",keyfont));
			            t.addCell(c1);
			            c1 = new Cell(new Paragraph("Line",keyfont));
		             t.addCell(c1);
			             c1 = new Cell(new Paragraph("Part#",keyfont));
			             t.addCell(c1);
			             c1 = new Cell(new Paragraph("Description",keyfont));
			             t.addCell(c1); 
			             c1 = new Cell(new Paragraph("Origin",keyfont));
			            t.addCell(c1); 
			             //calculate the real records within a page ,to calculate the last record number of every page
			             int maxRecordInPage= j+1 ==totalPage ? (remainPage==0?recordPerPage:(ponum.size()%recordPerPage)):recordPerPage;
		            
			            for(int i=j*recordPerPage;i<((j*recordPerPage)+maxRecordInPage);i++){
			                Cell c2=new Cell(new Paragraph(ponum.get(i), textfont));
			                 t.addCell(c2);
			                 c2=new Cell(new Paragraph(line.get(i), textfont));
			                 t.addCell(c2);
		                 c2=new Cell(new Paragraph(part.get(i), textfont));
			                 t.addCell(c2);
		                c2=new Cell(new Paragraph(description.get(i), textfont));
			                 t.addCell(c2);
			                c2=new Cell(new Paragraph(origin.get(i), textfont));
			                 t.addCell(c2);
	             }
			             document.add(t);
			             
		             if(j+1==totalPage){
			 
			                Paragraph foot11 = new Paragraph("文件只作  Foster 收貨用"+printBlank(150)+"__________________________",keyfont);
			                document.add(foot11);
			                Paragraph foot12 = new Paragraph("Printed from Foster supplier portal"+printBlank(134)+company+printBlank(40)+"版本: 1.0",keyfont);
			                document.add(foot12);
			                HeaderFooter footer11=new HeaderFooter(foot11, true);
			                 footer11.setAlignment(HeaderFooter.ALIGN_BOTTOM);
			                 HeaderFooter footer12=new HeaderFooter(foot12, true);
			                 footer12.setAlignment(HeaderFooter.ALIGN_BOTTOM);
			             }
		         }
			         document.close();
		} catch (DocumentException | IOException e) {
			
			e.printStackTrace();
		}
		 
	}

	public static void add(List<String> list,int num){
		 for(int i=0;i<num;i++){
			 list.add("test"+i);
		 }
	}
	 public static String leftPad(String str, int i) {
		          int addSpaceNo = i-str.length();
		          String space = ""; 
		          for (int k=0; k<addSpaceNo; k++){
		                  space= " "+space;
		          };
		         String result =space + str ;
		          return result;
		      }
	 public static String printBlank(int tmp){
		           String space="";
		           for(int m=0;m<tmp;m++){
		               space=space+" ";
		         }
		          return space;
		    }
}
