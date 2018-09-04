package com.eoulu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.dao.LabDao;
import com.eoulu.entity.OriginFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Cell;

public class GeneratePdfUtil {
	/*
	 * private void handleText(PdfWriter writer, String content, String color,
	 * float x, float y, float z) { PdfContentByte canvas =
	 * writer.getDirectContent(); Phrase phrase = new Phrase(content); if (color
	 * != null) { phrase = new Phrase(content, FontFactory.getFont(
	 * FontFactory.COURIER, 12, Font.NORMAL)); }
	 * 
	 * ColumnText.showTextAligned(canvas, Element.ALIGN_UNDEFINED, phrase, x, y,
	 * z); }
	 */
	public static File Pdf(String imagePath, String mOutputPdfFileName) {
		System.out.println(imagePath + "---" + mOutputPdfFileName);
		Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName));
			doc.open();

			doc.newPage();
			Image png1 = Image.getInstance(imagePath);
			float heigth = png1.getHeight();
			float width = png1.getWidth();
			// int percent = this.getPercent2(heigth, width);
			int percent = getPercent2(heigth, width);
			png1.setAlignment(Image.MIDDLE);
			png1.setAlignment(Image.TEXTWRAP);
			png1.scalePercent(percent + 3);
			doc.add(png1);
			// this.handleText(writer, "This is a test", "red", 400, 725, 0);
			doc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File mOutputPdfFile = new File(mOutputPdfFileName);
		if (!mOutputPdfFile.exists()) {
			mOutputPdfFile.deleteOnExit();
			return null;
		}
		return mOutputPdfFile;
	}

	public int getPercent1(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		if (h > w) {
			p2 = 297 / h * 100;
		} else {
			p2 = 210 / w * 100;
		}
		p = Math.round(p2);
		return p;
	}

	private static int getPercent2(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		p2 = 530 / w * 100;
		p = Math.round(p2);
		return p;
	}

	public static PdfPCell getPDFCell(String string, Font font) {
		// 创建单元格对象，将内容与字体放入段落中作为单元格内容
		PdfPCell cell = new PdfPCell(new Paragraph(string, font));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		// 设置最小单元格高度
		cell.setMinimumHeight(25);
		return cell;
	}

	public static PdfPCell mergeRow(String str, Font font, int row, int col) {

		// 创建单元格对象，将内容及字体传入
		PdfPCell cell = new PdfPCell(new Paragraph(str, font));
		// 设置单元格内容居中
		
		if(str.contains("公司")){
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		}else{
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(30*row);
		// 将该单元格所在列包括该单元格在内的i行单元格合并为一个单元格
		cell.setColspan(col);
		cell.setRowspan(row);

		return cell;
	}

	public static PdfPCell mergeCol(String str, Font font, int row, int col) {

		// 创建单元格对象，将内容及字体传入
		PdfPCell cell = new PdfPCell(new Paragraph(str, font));
		
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(30);
		// 将该单元格所在列包括该单元格在内的i行单元格合并为一个单元格
		cell.setColspan(col);
		cell.setRowspan(row);

		return cell;
	}
	public static void main(String[] args) {
		String path = "E:\\test\\test.pdf";
		OriginFactory factory = new OriginFactory();
		factory.setPO("po");
		factory.setSO("so");
		factory.setBAFA("bafa");
		factory.setDelayPeriod("delaydata");
		factory.setDelayReason("DelayReason");
		factory.setDutyExemption("duty");
		factory.setFactoryPeriod("factory");
		factory.setPaymentLC("lc");
		factory.setShippingInstruction("shipping");
		factory.setTargetDate("td");
		factory.setTrackingNO("tn");
		factory.setSOA("soa");
		factory.setDelayInfo("info");
		createPDF(path,factory);
	}
	public static String createPDF(String path,OriginFactory factory) {
		Rectangle pageSize = new Rectangle(600, 800);
		Document doc = new Document(pageSize, 20, 20, 20, 20);
		String PONO = "1) PO: "+factory.getPO();
		String SONO = "2) SO: "+factory.getSO();
		String FactoryDate = "7) Factory Date: "+factory.getFactoryPeriod();
		String DelayDate = "9) Delay Date: "+factory.getDelayPeriod();
		String DelayReason = "10) Delay Reason: "+factory.getDelayReason();
		String SOA = "3) SOA: "+factory.getSOA();
		String BAFA = "4) BAFA: "+factory.getBAFA();
		String TargetDate = "6) Target Date: "+factory.getTargetDate();
		String TrackingNO = "8) Tracking NO.: "+factory.getTrackingNO();
		String DelayInfo = "11) Delay Info: "+factory.getDelayInfo();
		String PaymentLC = "12) Payment/LC: "+factory.getPaymentLC();
		String DutyExemption = "13) Duty exemption: "+factory.getDutyExemption();
		String ShippingInstruction = "5) ShippingInstruction: ";
		String ShippingInstructionContent = factory.getShippingInstruction();
		OutputStream outputStream = null;
		try {
		BaseFont bfChinese = BaseFont.createFont("E:\\font\\Arial.ttf", BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED);
		Font textFont = new Font(bfChinese, 10, Font.NORMAL); // 正常
		Font redTextFont = new Font(bfChinese, 11, Font.NORMAL); // 正常,红色
		Font boldFont = new Font(bfChinese, 11, Font.BOLD); // 加粗
		Font redBoldFont = new Font(bfChinese, 11, Font.BOLD); // 加粗,红色
		Font firsetTitleFont = new Font(bfChinese, 22, Font.BOLD); // 一级标题
		Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); // 二级标题
		Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); // 下划线斜体
		
		
			outputStream = new FileOutputStream(new File(path));
			PdfWriter.getInstance(doc, outputStream);
			doc.open();
			doc.newPage();
			Paragraph p1 = new Paragraph();
			p1 = new Paragraph(PONO, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(SONO, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(SOA, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(BAFA, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(ShippingInstruction, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(ShippingInstructionContent, textFont);
			p1.setSpacingBefore(15);
			p1.setIndentationLeft(30); 
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(TargetDate, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(FactoryDate, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(TrackingNO, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(DelayDate, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(DelayReason, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(DelayInfo, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(PaymentLC, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			p1 = new Paragraph(DutyExemption, textFont);
			p1.setSpacingBefore(15);
			p1.setAlignment(Element.ALIGN_LEFT);
			doc.add(p1);
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
			System.err.println("document: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("pdf font: " + e.getMessage());
		} finally {
			if (doc != null) {
				doc.close();
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		return path;
		
	}
	public static String createPDF(String path, List<Map<String, Object>> ls, List<Map<String, Object>> base,String time) {
		// 创建文档实例
		Rectangle pageSize = new Rectangle(800, 610);// 横向
		Document doc = new Document(pageSize, 20, 20, 20, 20);
		
		// 添加中文字体
		OutputStream outputStream = null;
		try {
			BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			// 设置字体样式
			Font textFont = new Font(bfChinese, 8, Font.NORMAL); // 正常
			Font redTextFont = new Font(bfChinese, 11, Font.NORMAL); // 正常,红色
			Font boldFont = new Font(bfChinese, 11, Font.BOLD); // 加粗
			Font redBoldFont = new Font(bfChinese, 11, Font.BOLD); // 加粗,红色
			Font firsetTitleFont = new Font(bfChinese, 22, Font.BOLD); // 一级标题
			Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); // 二级标题
			Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); // 下划线斜体

			// 创建输出流
			outputStream = new FileOutputStream(new File(path));
			PdfWriter.getInstance(doc, outputStream);
			doc.open();
			doc.newPage();

			Paragraph p1 = new Paragraph();
			p1 = new Paragraph("EOULU国内运输指令", secondTitleFont);
			p1.setSpacingBefore(30);
			p1.setSpacingAfter(30);
			p1.setAlignment(Element.ALIGN_CENTER);
			doc.add(p1);
			// 创建一个有11列的表格
			String[] tabletitle = new String[] { "序号", "合同号", "尺寸(CM)", "重量(KG)", "数量（纸箱/木箱）", "是否需要打木托", "是否需要卸载平台",
					"提货时间", "提货信息", "收货信息","运输商", "总计" };
			PdfPTable table = new PdfPTable(tabletitle.length);
			// float width= 750f;
			// table.setTotalWidth(width);
			float[] widths = new float[] { 30f, 50f, 50f, 40f, 40f, 40f, 40f, 60f, 110f, 110f,110f, 110f };
			table.setTotalWidth(widths);
			table.setLockedWidth(true);
			for (String title : tabletitle) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, textFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(cell);
			}

			int rows = (ls.size() - 1) * 3;
			Map<String, Object> mm = null;
			if (base.size() > 1) {
				mm = base.get(1);
			}
			System.out.println("size:" + ls.size());
			for (int i = 1; i <= ls.size(); i++) {
				if(i != ls.size()){
					Map<String, Object> map = ls.get(i);
					for (int j = 0; j < 11; j++) {
						
						if (tabletitle[j].equals("序号")) {
							table.addCell(mergeRow(i + "", textFont, 3,1));
						}
						if (tabletitle[j].equals("合同号")) {
							table.addCell(mergeRow(map.get("ContractNO").toString(), textFont, 3,1));
						}
						if (tabletitle[j].equals("尺寸(CM)")) {
							table.addCell(mergeRow(map.get("Size").toString(), textFont, 3,1));
						}
						if (tabletitle[j].equals("重量(KG)")) {
							table.addCell(mergeRow(map.get("Weight").toString(), textFont, 3,1));
						}
						if (tabletitle[j].equals("数量（纸箱/木箱）")) {
							table.addCell(mergeRow(map.get("Quantity").toString(), textFont, 3,1));
						}
						if (tabletitle[j].equals("是否需要打木托")) {
							table.addCell(mergeRow(map.get("WoodenPallet").toString(), textFont, 3,1));
							System.out.println("木托:"+map.get("WoodenPallet").toString());
						}
						if (tabletitle[j].equals("是否需要卸载平台")) {
							table.addCell(mergeRow(map.get("UnloadPlat").toString(), textFont, 3,1));
							System.out.println("卸载:"+map.get("UnloadPlat").toString());
						}
						if (tabletitle[j].equals("提货时间")) {
							table.addCell(mergeRow(time,textFont,3,1));
						}
						if (tabletitle[j].equals("提货信息")) {
							table.addCell(mergeRow("公司：" + map.get("PickCompany").toString()+"\r\n"+"地址：" +map.get("PickAddress").toString()+"\r\n"+"联系人：" +map.get("PickContact")+"\r\n"+"手机：" +map.get("PickTel").toString(), textFont,3,1));
									
						}
						if (tabletitle[j].equals("收货信息")) {
							table.addCell(mergeRow("公司：" + map.get("ReceivingCompany").toString()+"\r\n"+"地址：" +map.get("ReceivingAddress").toString()+"\r\n"+"联系人：" +map.get("ReceivingContact").toString()+"\r\n"+"手机：" +map.get("ReceivingTel").toString(), textFont,3,1));
						}
						if (tabletitle[j].equals("运输商")) {
							table.addCell(mergeRow(mm.get("Transporters").toString(), textFont,3,1));
						}
						
					}
					if(i == 1){
						table.addCell(mergeRow(
								"共计 " + mm.get("Total1") + "个纸箱，" + mm.get("Total2") + "个木箱，" + mm.get("Total3") + "个收货地址",
								textFont, rows,1));
					}
				}else{
					table.addCell(mergeCol("注：货物不能叠加放置，请合理安排符合货物尺寸的车辆", textFont, 1,13));
				}
				
				
				
			}
			
			
			// table.setHeaderRows(1);
			doc.add(table);
			// table.deleteBodyRows();
		} catch (DocumentException e) {
			e.printStackTrace();
			System.err.println("document: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("pdf font: " + e.getMessage());
		} finally {
			if (doc != null) {
				doc.close();
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return path;
	}
	
	public static String createLabConfig(String path, List<Map<String, Object>> config,String model) {
		// 创建文档实例
		Rectangle pageSize = new Rectangle(800, 610);// 横向
		Document doc = new Document(pageSize, 20, 20, 20, 20);
		
		// 添加中文字体
		OutputStream outputStream = null;
		try {
			BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			// 设置字体样式
			Font titleFont = new Font(bfChinese,12,Font.ITALIC);  //斜体
			BaseColor color = new BaseColor(212,212,212);
			Font textFont = new Font(bfChinese, 12, Font.NORMAL); // 正常
			
			Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); // 二级标题
	

			// 创建输出流
			outputStream = new FileOutputStream(new File(path));
			PdfWriter.getInstance(doc, outputStream);
			doc.open();
			doc.newPage();

			Paragraph p1 = new Paragraph();
			p1 = new Paragraph(model+"-实验室配置", secondTitleFont);
			p1.setSpacingBefore(30);
			p1.setSpacingAfter(30);
			p1.setAlignment(Element.ALIGN_CENTER);
			doc.add(p1);
			// 创建一个有11列的表格
			String[] tabletitle = new String[] { "Item", "Part Number", "Description", "Qty"};
			PdfPTable table = new PdfPTable(tabletitle.length);
			// float width= 750f;
			// table.setTotalWidth(width);
			float[] widths = new float[] { 30f, 100f, 450f, 50f };
			table.setTotalWidth(widths);
			table.setLockedWidth(true);
			for (String title : tabletitle) {
				PdfPCell cell = new PdfPCell(new Paragraph(title, titleFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor(color);
				cell.setFixedHeight(30);
				table.addCell(cell);
			}

			
		
			for (int i = 1; i < config.size(); i++) {
			
				Map<String, Object> map = config.get(i);
				for (int j = 0; j < 4; j++) {
					PdfPCell cell = null;
					if (tabletitle[j].equals("Item")) {
						cell = new PdfPCell(new Paragraph(i + "", textFont));
						
					}
					if (tabletitle[j].equals("Part Number")) {
						cell = new PdfPCell(new Paragraph(map.get("Model").toString(), textFont));
					}
					if (tabletitle[j].equals("Description")) {
						cell = new PdfPCell(new Paragraph(map.get("Description").toString(), textFont));
					}
					if (tabletitle[j].equals("Qty")) {
						cell = new PdfPCell(new Paragraph(map.get("Qty").toString(), textFont));

					}
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setFixedHeight(40);
					table.addCell(cell);
					
				}
				
			}
				
				
			doc.add(table);
		
		} catch (DocumentException e) {
			e.printStackTrace();
			System.err.println("document: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("pdf font: " + e.getMessage());
		} finally {
			if (doc != null) {
				doc.close();
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return path;
	}


	private static PdfPTable createCell(PdfPTable table, String[] title, int row, int cols)
			throws DocumentException, IOException {
		// 添加中文字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bfChinese, 11, Font.BOLD);

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < cols; j++) {

				PdfPCell cell = new PdfPCell();

				if (i == 0 && title != null) {// 设置表头
					cell = new PdfPCell(new Phrase(title[j], font)); // 这样表头才能居中
					if (table.getRows().size() == 0) {
						cell.setBorderWidthTop(3);
					}
				}

				if (row == 1 && cols == 1) { // 只有一行一列
					cell.setBorderWidthTop(3);
				}

				if (j == 0) {// 设置左边的边框宽度
					cell.setBorderWidthLeft(3);
				}

				if (j == (cols - 1)) {// 设置右边的边框宽度
					cell.setBorderWidthRight(3);
				}

				if (i == (row - 1)) {// 设置底部的边框宽度
					cell.setBorderWidthBottom(3);
				}

				cell.setMinimumHeight(40); // 设置单元格高度
				cell.setUseAscender(true); // 设置可以居中
				cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中

				table.addCell(cell);
			}
		}

		return table;
	}
	
	@Test
	public void test(){
		List<Map<String, Object>> config = new LabDao().getComfig(1);
		createLabConfig("E:\\test.pdf", config, "EPS150");
	}

//	public static void main(String[] args) {
//		String path = "E:/新建文件夹/image.pdf";
//		String image = "E:/test/删除.png";
//		List<Map<String, Object>> ls = new ArrayList<>();
//		Map<String, Object> map = new HashMap<>();
//		map.put("1", "test");
//		map.put("2", "test");
//		map.put("3", "test");
//		map.put("4", "test");
//		map.put("5", "test");
//		map.put("6", "test");
//		map.put("7", "test");
//		map.put("8", "test");
//		map.put("9", "test");
//		map.put("10", "test");
//		map.put("11", "test");
//		ls.add(map);
//		// System.out.println(createPDF(path,ls));
//		// Pdf(image, path);
//	}
}
