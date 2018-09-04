package com.eoulu.util;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfHeaderFooter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.field.RtfTotalPageNumber;

public class DocExportUtil {

	/*
	 * public static boolean readwriteWord(String filePath, Map<String,String>
	 * haderMap,Map<String ,String> bodyMap,Map<String,String> footMap){
	 * //读取word模板 FileInputStream in = null; try { in = new FileInputStream(new
	 * File(filePath)); } catch (FileNotFoundException e1) {
	 * e1.printStackTrace(); } HWPFDocument hdt = null; try { hdt = new
	 * HWPFDocument(in); } catch (IOException e1) { e1.printStackTrace(); }
	 * System.out.println(1111111112); //读取word页眉内容 Range harderRange=
	 * hdt.getHeaderStoryRange(); //替换word页眉内容 // for(Map.Entry<String, String>
	 * entry:haderMap.entrySet()){ // harderRange.replaceText("${" +
	 * entry.getKey() + "}", entry.getValue()); // } harderRange.replaceText("",
	 * "yemei"); System.out.println("harderRange:"+harderRange); //读取页脚内容 Range
	 * footRange=hdt.getFootnoteRange(); //替换页脚内容 //
	 * for(Map.Entry<String,String> entry:footMap.entrySet()){ //
	 * footRange.replaceText("${" + entry.getKey().trim() + "}",
	 * entry.getValue()); // } footRange.replaceText("", "yejiao");
	 * System.out.println("footRange"+footRange); //读取word文本内容 Range bodyRange =
	 * hdt.getRange(); //替换文本内容 // for (Map.Entry<String,String> entry:
	 * bodyMap.entrySet()) { // bodyRange.replaceText("${" + entry.getKey() +
	 * "}",entry.getValue()); // // } System.out.println(bodyRange); //
	 * PicturesTable picturesTable= hdt.getPicturesTable(); //
	 * //hdt.addPicture(bytes, XWPFDocument.PICTURE_TYPE_JPEG); // //
	 * FileInputStream fis = new
	 * FileInputStream("F:\\picture\\http_imgload.jpg"); // //将图片添加到xlsx文件 //
	 * int picinx = hdt.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG); //
	 * fis.close(); //
	 * 
	 * 
	 * 
	 * ByteArrayOutputStream ostream = new ByteArrayOutputStream(); String
	 * fileName = ""+System.currentTimeMillis(); fileName += ".doc";
	 * 
	 * FileOutputStream out = null; try { out = new
	 * FileOutputStream("E:\\test\\"+fileName,true); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } try {
	 * hdt.write(ostream); } catch (IOException e) { e.printStackTrace(); }
	 * //输出字节流 try { out.write(ostream.toByteArray()); return true; } catch
	 * (IOException e) { e.printStackTrace(); } try { out.close(); } catch
	 * (IOException e) { e.printStackTrace(); } try { ostream.close(); } catch
	 * (IOException e) { e.printStackTrace(); } return false; }
	 */
	public static Font setFontStyle(String family, float size, int style) {
		return setFontStyle(family, Color.BLACK, size, style);
	}

	public static Font setFontStyle(String family, Color color, float size, int style) {
		Font font = new Font();
		font.setFamily(family);
		font.setColor(color);
		font.setSize(size);
		font.setStyle(style);
		return font;
	}

	public static Paragraph setParagraphStyle(String content, Font font, float firstLineIndent, String appendStr) {
		Paragraph par = setParagraphStyle(content, font, 0f, 12f);
		Phrase phrase = new Phrase();
		phrase.add(par);
		phrase.add(appendStr);
		Paragraph paragraph = new Paragraph(phrase);
		paragraph.setFirstLineIndent(firstLineIndent);
		// 设置对齐方式为两端对齐
		paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
		return paragraph;
	}

	public static Paragraph setParagraphStyle(String content, Font font, float leading, int alignment) {
		return setParagraphStyle(content, font, 0f, leading, 0f, alignment);
	}

	public static Paragraph setParagraphStyle(String content, Font font, float firstLineIndent, float leading,
			String appendStr) {
		Phrase phrase = setPhraseStyle(content, appendStr);
		Paragraph par = new Paragraph(phrase);
		par.setFont(font);
		par.setFirstLineIndent(firstLineIndent);
		par.setLeading(leading);
		// 设置对齐方式为两端对齐
		par.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
		return par;
	}

	private static Phrase setPhraseStyle(String content, String appendStr) {
		Chunk chunk = new Chunk(content);
		// 填充的背景颜色为浅灰色
		chunk.setBackground(Color.LIGHT_GRAY);
		Phrase phrase = new Phrase(chunk);
		phrase.add(appendStr);
		return phrase;
	}

	public static Paragraph setParagraphStyle(String content, Font font, float firstLineIndent, float leading) {
		return setParagraphStyle(content, font, firstLineIndent, leading, 0.6f, Paragraph.ALIGN_JUSTIFIED_ALL);
	}

	public static Paragraph setParagraphStyle(String content, Font font, float firstLineIndent, float leading,
			float indentationRight, int alignment) {
		Paragraph par = new Paragraph(content, font);
		par.setFirstLineIndent(firstLineIndent);
		par.setLeading(leading);
		par.setIndentationRight(indentationRight);
		par.setAlignment(alignment);
		return par;
	}

	public static void main(String[] args) {
		// exportDoc("e:\\test.doc");

	}

	public static void exportDoc(String fileName, HttpServletRequest req) {
		try {
			Document doc = new Document();
			FileOutputStream outPath = new FileOutputStream(fileName);
			RtfWriter2.getInstance(doc, outPath);
			doc.open();
			// 设置页边距，上、下25.4毫米，即为72f，左、右31.8毫米，即为90f
			doc.setMargins(30f, 30f, 52f, 72f);

			Font tfont = setFontStyle("Arial", 10f, Font.BOLD);
			Font bfont = setFontStyle("MicrosoftYaHei", 8f, Font.BOLD);

			Phrase content = new Phrase();
			 StringBuffer sb = new StringBuffer(); 
			InputStream is = req.getInputStream();
			System.out.println(121212 + "---" + is);
			byte[] bytes = new byte[1024];
			int temp = 0;
			while ((temp = is.read(bytes)) != -1) {
				// outPath.write(bytes, 0, temp);
				// 首行缩进2字符，行间距1.5倍行距
				sb.append(bytes);
			}
			Paragraph bodyEndPar = setParagraphStyle(sb.toString(), bfont, 32f, 18f);
			System.out.println(bytes.toString());
			doc.add(bodyEndPar);
			// 添加页眉
			Image headerImage = Image.getInstance("d:\\image\\yemei.jpg");
			headerImage.setAbsolutePosition(50, 90);
			headerImage.setAlignment(Image.RECTANGLE);

			headerImage.scalePercent(90);
			Paragraph headerPara1 = new Paragraph();
			headerPara1.setAlignment(HeaderFooter.ALIGN_RIGHT);
			Phrase headerPara = new Phrase();
			Table table = new Table(1);
			int width[] = { 10 };
			table.setWidths(width);
			table.setBorderWidth(1);
			table.setBorder(0);
			table.setBorderColor(Color.BLACK);
			table.setPadding(0);
			table.setSpacing(0);
			table.setWidth(100);
			Cell cell = new Cell(headerImage);
			// cell.setBorderWidth(0f); // 设置表格没有边框
			cell.setBorderWidthBottom(1);
			cell.setHeader(true);
			cell.setRowspan(1);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			headerPara.add(table);
			HeaderFooter header = new HeaderFooter(headerPara, false);
			header.setAlignment(HeaderFooter.ALIGN_RIGHT);
			doc.setHeader(header); // 设置好页眉
			/*
			 * Image img = Image.getInstance("d:\\image\\cpyemei.png");
			 * img.setAbsolutePosition(0, 0); img.setAlignment(Image.RECTANGLE);
			 * img.scalePercent(74); doc.add(img);
			 */
			// 设置页脚
			Image imgFooter = Image.getInstance("d:\\image\\yejiaoChina.png");
			imgFooter.setAbsolutePosition(0, 0);
			imgFooter.setAlignment(Image.RECTANGLE);
			imgFooter.scalePercent(74);
			// RtfPageNumber number = new RtfPageNumber();
			// Paragraph parafooter = new Paragraph();
			// parafooter.add(new Phrase("第"));
			// parafooter.add(number);
			// parafooter.add(new Phrase("页/共"));
			// parafooter.add(new RtfTotalPageNumber());
			// parafooter.add(new Phrase("页"));
			Phrase footerPara = new Phrase();
			footerPara.add(new Paragraph(new Chunk(imgFooter, 30f, 20f)));
			HeaderFooter footer = new HeaderFooter(footerPara, false);
			footer.setAlignment(Element.ALIGN_CENTER);
			footer.setBorder(Rectangle.NO_BORDER);
			doc.setFooter(footer);

			/*
			 * Table table2 = new Table(3); int width2[] = { 35,30,35 };
			 * table2.setWidths(width2); table2.setBorderWidth(1);
			 * table2.setBorder(0); table2.setBorderColor(Color.BLUE);
			 * table2.setPadding(0); table2.setSpacing(0); table2.setWidth(100);
			 * Paragraph number = setParagraphStyle("Number:", tfont, 10f,
			 * Paragraph.ALIGN_CENTER); Cell cell1 = new Cell(number);
			 * cell1.setBorderWidthBottom(5); cell1.setBorderWidthLeft(5);
			 * cell1.setBorderWidthRight(5); cell1.setBorderWidthTop(5);
			 * cell1.setBorderColor(Color.BLUE); cell1.setHeader(true);
			 * cell1.setRowspan(1);
			 * cell1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table2.addCell(cell1);
			 * 
			 * Paragraph date = setParagraphStyle("Date Sent:", tfont, 10f,
			 * Paragraph.ALIGN_CENTER); Cell cell2 = new Cell(date);
			 * cell2.setBorderWidthBottom(1); cell2.setBorderWidthRight(1);
			 * cell2.setBorderWidthTop(1); cell2.setBorderColor(Color.BLUE);
			 * cell2.setHeader(true); cell2.setRowspan(1);
			 * cell2.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table2.addCell(cell2);
			 * 
			 * Paragraph version = setParagraphStyle("Versions:", tfont, 10f,
			 * Paragraph.ALIGN_CENTER); Cell cell3 = new Cell(version);
			 * cell3.setBorderWidthBottom(1); cell3.setBorderWidthRight(1);
			 * cell3.setBorderWidthTop(1); cell3.setBorderColor(Color.BLUE);
			 * cell3.setHeader(true); cell3.setRowspan(1);
			 * cell3.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table2.addCell(cell3); doc.add(table2);
			 * 
			 * 
			 * Table table3 = new Table(3); int width3[] = { 5,60,25 };
			 * table3.setWidths(width3); cell3.setBorderWidth(0f);
			 * table3.setBorderWidth(0); table3.setBorder(0);
			 * table3.setBorderColor(Color.BLUE); table3.setPadding(10);
			 * table3.setSpacing(0); table3.setWidth(100);
			 * 
			 * Cell c0 = new Cell(""); c0.setBorder(0); c0.setHeader(true);
			 * c0.setRowspan(1); c0.setVerticalAlignment(Element.ALIGN_CENTER);
			 * c0.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * table3.addCell(c0); Paragraph customer =
			 * setParagraphStyle("杭州半导体", tfont, 10f, Paragraph.ALIGN_LEFT);
			 * Cell cleft = new Cell(customer); cleft.setBorder(0);
			 * cleft.setHeader(true); cleft.setRowspan(1);
			 * cleft.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cleft.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * table3.addCell(cleft); Paragraph com =
			 * setParagraphStyle("苏州伊欧陆系统集成有限公司", tfont, 10f,
			 * Paragraph.ALIGN_LEFT); Cell comRight = new Cell(com);
			 * comRight.setBorder(0); comRight.setHeader(true);
			 * comRight.setRowspan(1);
			 * comRight.setVerticalAlignment(Element.ALIGN_CENTER);
			 * comRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table3.addCell(comRight); Cell c1 = new Cell("");
			 * c1.setBorder(0); c1.setHeader(true); c1.setRowspan(1);
			 * c1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * table3.addCell(c1); Paragraph customer1 = setParagraphStyle(
			 * "China(People's Public)", tfont, 10f, Paragraph.ALIGN_LEFT); Cell
			 * cleft1 = new Cell(customer1); cleft1.setBorder(0);
			 * cleft1.setHeader(true); cleft1.setRowspan(1);
			 * cleft1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * cleft1.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * table3.addCell(cleft1); Paragraph com1 =
			 * setParagraphStyle("China", tfont, 10f, Paragraph.ALIGN_LEFT);
			 * Cell comRight1 = new Cell(com1); comRight1.setBorder(0);
			 * comRight1.setHeader(true); comRight1.setRowspan(1);
			 * comRight1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * comRight1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table3.addCell(comRight1); doc.add(table3);
			 */

			// 设置空行
			Paragraph blankRow = new Paragraph(18f, " ", bfont);
			Paragraph deptPar = setParagraphStyle("（无聊的小熊/dvainoy）", bfont, 12f, Paragraph.ALIGN_RIGHT);
			Paragraph datePar = setParagraphStyle("2013-04-30", bfont, 12f, Paragraph.ALIGN_RIGHT);

			Paragraph bodyEndPar1 = setParagraphStyle("图片后继续添加", bfont, 32f, 18f);

			doc.close();

			// if(outPath!=null){
			// outPath.flush();
			// outPath.close();
			is.close();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// public static void exportDoc(String fileName,HttpServletRequest req){
	//
	// }

}
