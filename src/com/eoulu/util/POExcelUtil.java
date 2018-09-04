package com.eoulu.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POExcelUtil {

	public void buildCascadePO(List<Map<String, Object>> list, List<Map<String, Object>> ls, String path,String image,String user,String email) {

		Map<String, Object> po = ls.get(0);
		XSSFWorkbook xwk = new XSSFWorkbook();
		
		InputStream is;
		int pictureIdx = 0;
		try {
			is = new FileInputStream(image); 
			byte[] bytes = IOUtils.toByteArray(is);  
			  
			// 增加图片到 Workbook  
			pictureIdx = xwk.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
			is.close();  
			  
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		CreationHelper helper = xwk.getCreationHelper();
		
		XSSFSheet xsheet = xwk.createSheet("Cascade PO");
		String[] colName = new String[] { "Item", "Part", "Description", "Qty", "Unit Price", "Extended Price" };
		Drawing drawing = xsheet.createDrawingPatriarch(); 
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(4);  
		anchor.setRow1(0);  
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		double a = 2.3;
		double b = 1;
		pict.resize(a,b); 
		int rowsCount = list.size() + 37;
		int colsCount = colName.length;

		XSSFCellStyle style = xwk.createCellStyle();//创建单元格样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);//自动换行
		XSSFCellStyle cell = xwk.createCellStyle();
		cell.setAlignment( XSSFCellStyle.ALIGN_RIGHT);//居右
		cell.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cell.setWrapText(true);//自动换行
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment( XSSFCellStyle.ALIGN_CENTER);//居中
		center.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		center.setWrapText(true);//自动换行
		
		xsheet.addMergedRegion(new CellRangeAddress(2, 4, 0, 2));// 2~11
		xsheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(9, 10, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(9, 9, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(10, 10, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 2));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 3, 5));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(19, 19, 0, 2));// 19~23
		xsheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(21, 21, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(22, 22, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(23, 23, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(19, 19, 3, 5));// 19~23
		xsheet.addMergedRegion(new CellRangeAddress(20, 20, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(21, 21, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(22, 22, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(23, 23, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(25, 25, 0, 5));// 25~29
		xsheet.addMergedRegion(new CellRangeAddress(26, 26, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(27, 27, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(28, 28, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(29, 29, 1, 5));
		xsheet.setColumnWidth(0, (int) 1500);
		xsheet.setColumnWidth(1, (int) 3000);
		xsheet.setColumnWidth(2, (int) 7000);
		xsheet.setColumnWidth(3, (int) 6000);
		xsheet.setColumnWidth(4, (int) 3000);
		xsheet.setColumnWidth(5, (int) 4000);
		for (int i = 0; i < rowsCount; i++) {
			XSSFRow dataRow = xsheet.createRow(i);
			if(i==0){
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
				dataRow.setHeightInPoints(90);
			}else{
				dataRow.setHeightInPoints(20);
			}
			if (i == 2) {
				System.out.println(111);
				dataRow.createCell(0).setCellValue("Purchase Order");
				String str = po.get("Number").toString() == null? " ":po.get("Number").toString();
				dataRow.createCell(3).setCellValue("Number: "+str );
			}
			if (i == 3) {
				String str =  po.get("Version").toString() == null? " ":po.get("Version").toString();
				dataRow.createCell(3).setCellValue("Versions: "+str);
			}
			if (i == 6) {
				dataRow.createCell(0).setCellValue("Bill");
				dataRow.createCell(3).setCellValue("Vendor");
			}
			if (i == 7) {
				dataRow.createCell(0).setCellValue("Wang Xiaoliang");
				dataRow.createCell(3).setCellValue("Cascade Microtech,Inc.");
			}
			if (i == 8) {
				dataRow.createCell(0).setCellValue("Email: wangxiaoliang@eoulu.com");
				dataRow.createCell(3).setCellValue("9100 SW Gemini Drive");
			}
			if (i == 9) {
				dataRow.createCell(0).setCellValue("Room 1501, Grand Millennium Plaza (Lower Block), 181 Queen's Road"
						+ "\n" + " Central, HONG KONG");
				dataRow.createCell(3).setCellValue("Beaverton, OR 97008");
			}
			if (i == 10) {
				dataRow.createCell(3).setCellValue("TEL:1-503-601-1741");
			}
			if (i == 11) {
				dataRow.createCell(0).setCellValue("TEL:00852-21527388");
				dataRow.createCell(3).setCellValue("E-mail:Darren_Martin@cmicro.com");
			}
			if (i == 13) {
				dataRow.createCell(0).setCellValue("Forwarder");
				dataRow.createCell(3).setCellValue("Ship to");
			}
			if (i == 14) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderOne") == null? " ":po.get("ForwarderOne").toString());
				dataRow.createCell(3).setCellValue("ShipCompany:" + po.get("ShipCompany") == null? " ":po.get("ShipCompany").toString());
			}
			if (i == 15) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderTwo") == null? " ":po.get("ForwarderTwo").toString());
				dataRow.createCell(3).setCellValue("ShipAddr:" + po.get("ShipAddr") == null? " ":po.get("ShipAddr").toString());
			}
			if (i == 16) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderThree") == null? " ":po.get("ForwarderThree").toString());
				dataRow.createCell(3).setCellValue("ShipTel:" + po.get("ShipTel") == null? " ":po.get("ShipTel").toString());
			}
			if (i == 17) {
				dataRow.createCell(0).setCellValue( po.get("ForwarderFour") == null? " ":po.get("ForwarderFour").toString());
				dataRow.createCell(3).setCellValue("ShipAttn:" + po.get("ShipAttn") == null? " ":po.get("ShipAttn").toString());
			}
			if (i == 19) {
				dataRow.createCell(0).setCellValue("End User");
				dataRow.createCell(3).setCellValue("Others");
			}
			if (i == 20) {
				dataRow.createCell(0).setCellValue(po.get("EndCompany") == null? " ":po.get("EndCompany").toString());
				dataRow.createCell(3).setCellValue("Credit Term:NET 60 days");
			}
			if (i == 21) {
				dataRow.createCell(0).setCellValue(po.get("EndAddr") == null? " ":po.get("EndAddr").toString());
				String str = po.get("DeliveryTerm").toString() == null? " ": po.get("DeliveryTerm").toString();
				dataRow.createCell(3).setCellValue("Delivery Term: FCA Factory," +str);
			}
			if (i == 22) {
				dataRow.createCell(0).setCellValue(po.get("ContactPerson") == null? " ": po.get("ContactPerson").toString());
//				dataRow.createCell(3).setCellValue("ShipTel:" +po.get("ShipTel") == null? " ":  po.get("ShipTel").toString());
			}
			if (i == 23) {
				dataRow.createCell(0).setCellValue(po.get("EndTel") == null? " ": po.get("EndTel").toString());
//				dataRow.createCell(3).setCellValue("ShipAttn:" +po.get("ShipAttn") == null? " ": po.get("ShipAttn").toString());
			}
			if (i == 25) {
				dataRow.createCell(0).setCellValue("Shipping Instruction");
			}
			if (i == 26) {
				XSSFCell c0 = dataRow.createCell(0);
				c0.setCellStyle(cell);
				c0.setCellValue("1");
			
				dataRow.createCell(1).setCellValue("Please do not put the original invoice with goods");
			}
			if (i == 27) {
				XSSFCell c0 = dataRow.createCell(0);
				c0.setCellStyle(cell);
				c0.setCellValue("2");
				String str = po.get("ShippingMark").toString() == null? " ":po.get("ShippingMark").toString();
				dataRow.createCell(1).setCellValue("SHIPPING MARK:" + str );
			}
			if (i == 28) {
				XSSFCell c0 = dataRow.createCell(0);
				c0.setCellStyle(cell);
				c0.setCellValue("3");
				String str = po.get("ContractNO").toString() == null? " ":po.get("ContractNO").toString();
				dataRow.createCell(1).setCellValue("Indicating CONTRACT NO.:" + str);
			}
			if (i == 29) {
				XSSFCell c0 = dataRow.createCell(0);
				c0.setCellStyle(cell);
				c0.setCellValue("4");
				String str = po.get("ShipmentPort").toString() == null? " ": po.get("ShipmentPort").toString();
				dataRow.createCell(1).setCellValue("Port of shipment: " +  str);
			}
			if (i == 31) {
				for (int j = 0; j < colsCount; j++) {
					XSSFCell c =dataRow.createCell(j);
					c.setCellValue(colName[j]);
					c.setCellStyle(center);
				}
			}
			if (i > 31 && i < rowsCount - 5) {
				for (int j = 0; j < colsCount; j++) {

					XSSFCell dataCell = dataRow.createCell(j);
					dataCell.setCellStyle(center);
					Map<String, Object> map = list.get(i - 32);
					String val = "";

					if (colName[j].equals("Item")) {
						val = i - 31 + "";
					}
					if (colName[j].equals("Part")) {
						if(map.get("Model") == null){
							val = "";
						}else{
							val = map.get("Model").toString().equals("") ? "--" : map.get("Model").toString();
						}
						
					}
					if (colName[j].equals("Description")) {
						if(map.get("Description") == null){
							val = "";
						}else{
							val = map.get("Description").toString().equals("") ? "--" : map.get("Description").toString();
						}
						
					}
					if (colName[j].equals("Qty")) {
						if(map.get("Quantity") == null){
							val = "";
						}else{
							val = map.get("Quantity").toString().equals("") ? "--" : map.get("Quantity").toString();
						}
						
					}
					if (colName[j].equals("Unit Price")) {
						if(map.get("UnitPrice") == null){
							val = "";
						}else{
							val = map.get("UnitPrice").toString().equals("") ? "--" : map.get("UnitPrice").toString();
						}
						
					}
					if (colName[j].equals("Extended Price")) {
						if(map.get("ExtendedPrice") == null){
							val = "";
						}else{
							val = map.get("ExtendedPrice").toString().equals("") ? "--"
									: map.get("ExtendedPrice").toString();
						}
						
					}
					dataCell.setCellValue(val);
					
				}

			}
			if ((i - list.size()) == 32) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c0 =dataRow.createCell(0);
				c0.setCellValue("Sub-total");
				c0.setCellStyle(cell);
				XSSFCell c = dataRow.createCell(5);
				String str = po.get("SubTotal").toString() == null ? " " :po.get("SubTotal").toString();
				c.setCellValue(str);
				c.setCellStyle(center);

			}
			if ((i - list.size()) == 33) {
				
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c0 =dataRow.createCell(0);
				c0.setCellValue("Discounted x%");
				c0.setCellStyle(cell);
				XSSFCell c = dataRow.createCell(5);
				
				c.setCellValue(po.get("Discounted") == null ? " " :po.get("Discounted").toString());
				c.setCellStyle(center);
			}
			if ((i - list.size()) == 34) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c0 =dataRow.createCell(0);
				c0.setCellValue("Final Total");
				c0.setCellStyle(cell);
				XSSFCell c = dataRow.createCell(5);
				
				c.setCellValue(po.get("FinalTotal") == null ? " " :po.get("FinalTotal").toString());
				c.setCellStyle(center);
			}
			if ((i - list.size()) == 35) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));

				XSSFCell c = dataRow.createCell(0);
				c.setCellStyle(center);
				c.setCellValue(
						"If you have any questions about your order, please contact:" + user);
			}
			if ((i - list.size()) == 36) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));
				XSSFCell c = dataRow.createCell(0);
				c.setCellStyle(center);
				c.setCellValue("E-Mail: " + email);
			}
		}

		FileOutputStream fo;

		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	public void buildOtherPO(List<Map<String, Object>> list, List<Map<String, Object>> ls, String path,String image,String user,String email) {
		 
		Map<String, Object> po = ls.get(0);
		XSSFWorkbook xwk = new XSSFWorkbook();
		InputStream is;
		int pictureIdx = 0;
		try {
			is = new FileInputStream(image); 
			byte[] bytes = IOUtils.toByteArray(is);  
			  
			// 增加图片到 Workbook  
			pictureIdx = xwk.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
			is.close();  
			  
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		CreationHelper helper = xwk.getCreationHelper();
		
		// 创建一个名为 xsheet 的sheet
		XSSFSheet xsheet = xwk.createSheet("其他供应商 PO");
		xsheet.setColumnWidth(0, (int) 1500);
		xsheet.setColumnWidth(1, (int) 3000);
		xsheet.setColumnWidth(2, (int) 7000);
		xsheet.setColumnWidth(3, (int) 6000);
		xsheet.setColumnWidth(4, (int) 3000);
		xsheet.setColumnWidth(5, (int) 4000);
		Drawing drawing = xsheet.createDrawingPatriarch(); 
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(4);  
		anchor.setRow1(0); 
		double a = 2.5;
		double b = 1;
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize(a,b); 
		String[] colName = new String[] { "Item", "Part", "Description", "Qty", "Unit Price", "Extended Price" };

		int rowsCount = list.size() + 35;
		// 列数
		int colsCount = colName.length;

		XSSFCellStyle style = xwk.createCellStyle();//创建单元格样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFCellStyle cell = xwk.createCellStyle();
		cell.setAlignment( XSSFCellStyle.ALIGN_RIGHT);
		Font font = xwk.createFont();//创建字体对象
		font.setColor(Font.COLOR_NORMAL);
		font.setFontHeightInPoints((short) (22));
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment( XSSFCellStyle.ALIGN_CENTER);
		XSSFCellStyle wrapStyle = xwk.createCellStyle();
		wrapStyle.setWrapText(true);
		XSSFCellStyle ver = xwk.createCellStyle();
		ver.setAlignment( XSSFCellStyle.VERTICAL_TOP );
		xsheet.addMergedRegion(new CellRangeAddress(2, 4, 0, 2));// 2~11
		xsheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(9, 10, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(9, 9, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(10, 10, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 2));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 3, 5));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(19, 19, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(21, 21, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(23, 23, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(24, 24, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(25, 25, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(26, 26, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(27, 27, 1, 5));

		for (int i = 0; i < rowsCount; i++) {
			XSSFRow dataRow = xsheet.createRow(i);
			if(i==0){
				// 设置合并单元格的区域 行开始,行结束,列开始,列结束 标题
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
				dataRow.setHeightInPoints(90);
			}else{
				dataRow.setHeightInPoints(20);
			}
			
			if (i == 2) {
				
				dataRow.createCell(0).setCellStyle(style);
				dataRow.createCell(0).setCellValue("Purchase Order");
				String str = po.get("Number").toString()== null ? "" :po.get("Number").toString();
				dataRow.createCell(3).setCellValue("Number: " +str  );
			}
			if (i == 3) {
				String str = po.get("Version").toString()== null ? "" :po.get("Version").toString();
				dataRow.createCell(3).setCellValue("Versions: " + str);
			}
			if (i == 6) {
				dataRow.createCell(0).setCellValue("Bill");
				dataRow.createCell(3).setCellValue("Vendor");
			}
			if (i == 7) {
				dataRow.createCell(0).setCellValue("Wang Xiaoliang");
				dataRow.createCell(3).setCellValue(po.get("VendorOne")== null ? "" :po.get("VendorOne").toString());
			}
			if (i == 8) {
				dataRow.createCell(0).setCellValue("Email: wangxiaoliang@eoulu.com");
				dataRow.createCell(3).setCellValue(po.get("VendorTwo")== null ? "" :po.get("VendorTwo").toString());
			}
			if (i == 9) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Room 1501, Grand Millennium Plaza (Lower Block), 181 Queen's Road Central, HONG KONG");
				c.setCellStyle(wrapStyle);
//				c.setCellStyle(ver);
				dataRow.createCell(3).setCellValue(po.get("VendorThree")== null ? "" :po.get("VendorThree").toString());
			}
			if (i == 11) {
				dataRow.createCell(0).setCellValue("TEL:00852-21527388");
			}
			if (i == 13) {
				dataRow.createCell(0).setCellValue("Forwarder");
				dataRow.createCell(3).setCellValue("Ship to");
			}
			if (i == 14) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderOne")== null ? "" :po.get("ForwarderOne").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipCompany")== null ? "" : po.get("ShipCompany").toString());
			}
			if (i == 15) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderTwo")== null ? "" : po.get("ForwarderTwo").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipAddr")== null ? "" : po.get("ShipAddr").toString());
			}
			if (i == 16) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderThree")== null ? "" : po.get("ForwarderThree").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipTel")== null ? "" : po.get("ShipTel").toString());
			}
			if (i == 17) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderFour")== null ? "" : po.get("ForwarderFour").toString());
				dataRow.createCell(3).setCellValue( po.get("ShipAttn")== null ? "" : po.get("ShipAttn").toString());
			}
			if (i == 19) {
				dataRow.createCell(0).setCellValue("Others");
			}
			if (i == 20) {
				String str = po.get("CreditTerm").toString()== null ? "" :po.get("CreditTerm").toString();
				dataRow.createCell(0).setCellValue("Credit Term:"+str);
			}
			if (i == 21) {
				String str = po.get("DeliveryTerm").toString()== null ? "" :po.get("DeliveryTerm").toString();
				dataRow.createCell(0).setCellValue("Delivery Term:" + str);
			}
			if (i == 23) {
				dataRow.createCell(0).setCellValue("Shipping Instruction");
			}
			if (i == 24) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("1");
				c.setCellStyle(cell);
				dataRow.createCell(1).setCellValue("Please do not put the original invoice with goods");
			}
			if (i == 25) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("2");
				c.setCellStyle(cell);
				String str = po.get("ShippingMark").toString()== null ? "" :po.get("ShippingMark").toString();
				dataRow.createCell(1).setCellValue("SHIPPING MARK:" + str);
			}
			if (i == 26) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("3");
				c.setCellStyle(cell);
				String str = po.get("ContractNO").toString()== null ? "" :po.get("ContractNO").toString();
				dataRow.createCell(1).setCellValue("Indicating CONTRACT NO.:" + str);
			}
			if (i == 27) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("4");
				c.setCellStyle(cell);
				String str = po.get("ShipmentPort").toString()== null ? "" :po.get("ShipmentPort").toString();
				dataRow.createCell(1).setCellValue("Port of shipment: " + str);
			}
			if (i == 29) {
				for (int j = 0; j < colsCount; j++) {
					XSSFCell dataCell = dataRow.createCell(j);
					dataCell.setCellValue(colName[j]);
					dataCell.setCellStyle(center);
				}
			}
			if (i > 29 && i < rowsCount - 5 && list.size()!=0) {
				for (int j = 0; j < colsCount; j++) {

					XSSFCell dataCell = dataRow.createCell(j);
					Map<String, Object> map = list.get(i - 30);
					String val = "";

					if (colName[j].equals("Item")) {
						val = i - 29 + "";
						dataCell.setCellStyle(center);
					}
					if (colName[j].equals("Part")) {
						dataCell.setCellStyle(center);
						if(map.get("Model")==null){
							val = " ";
						}else{
							val = map.get("Model").toString().equals("") ? "--" : map.get("Model").toString();
						}
						
					}
					if (colName[j].equals("Description")) {
						val = map.get("Description").toString().equals("") ? "--" : map.get("Description").toString();
						dataCell.setCellStyle(wrapStyle);
//						dataCell.setCellStyle(ver);
					}
					if (colName[j].equals("Qty")) {
						dataCell.setCellStyle(center);
						val = map.get("Quantity").toString().equals("") ? "--" : map.get("Quantity").toString();
					}
					if (colName[j].equals("Unit Price")) {
						dataCell.setCellStyle(center);
						val = map.get("UnitPrice").toString().equals("") ? "--" : map.get("UnitPrice").toString();
					}
					if (colName[j].equals("Extended Price")) {
						dataCell.setCellStyle(center);
						val = map.get("ExtendedPrice").toString().equals("") ? "--"
								: map.get("ExtendedPrice").toString();
					}

					dataCell.setCellValue(val);
				}

			}
			if ((i - list.size()) == 30) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Sub-total");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("SubTotal").toString());

			}
			if ((i - list.size()) == 31) {
				
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Discounted x%");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("Discounted").toString());
			}
			if ((i - list.size()) == 32) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Final Total");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("FinalTotal").toString());
			}
			if ((i - list.size()) == 33) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));
				XSSFCell c1 = dataRow.createCell(0);
				
				c1.setCellValue(
						"If you have any questions about your order, please contact:" + user);
				c1.setCellStyle(center);
			}
			if ((i - list.size()) == 34) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));
				XSSFCell c1 = dataRow.createCell(0);
				
				c1.setCellValue(" E-Mail: " + email);
				c1.setCellStyle(center);
			}
		}

		FileOutputStream fo;

		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void buildOtherRMBPO(List<Map<String, Object>> list, List<Map<String, Object>> ls, String path,String image,String user,String email) {
		 
		Map<String, Object> po = ls.get(0);
		XSSFWorkbook xwk = new XSSFWorkbook();
		InputStream is;
		int pictureIdx = 0;
		try {
			is = new FileInputStream(image); 
			byte[] bytes = IOUtils.toByteArray(is);  
			  
			// 增加图片到 Workbook  
			pictureIdx = xwk.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);  
			is.close();  
			  
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		CreationHelper helper = xwk.getCreationHelper();
		
		// 创建一个名为 xsheet 的sheet
		XSSFSheet xsheet = xwk.createSheet("其他供应商人民币 PO");
		xsheet.setColumnWidth(0, (int) 1500);
		xsheet.setColumnWidth(1, (int) 3000);
		xsheet.setColumnWidth(2, (int) 7000);
		xsheet.setColumnWidth(3, (int) 6000);
		xsheet.setColumnWidth(4, (int) 3000);
		xsheet.setColumnWidth(5, (int) 4000);
		Drawing drawing = xsheet.createDrawingPatriarch(); 
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(4);  
		anchor.setRow1(0); 
		double a = 2.5;
		double b = 1;
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		pict.resize(a,b); 
		String[] colName = new String[] { "Item", "Part", "Description", "Qty", "Unit Price", "Extended Price" };

		int rowsCount = list.size() + 35;
		// 列数
		int colsCount = colName.length;

		XSSFCellStyle style = xwk.createCellStyle();//创建单元格样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		XSSFCellStyle cell = xwk.createCellStyle();
		cell.setAlignment( XSSFCellStyle.ALIGN_RIGHT);
		Font font = xwk.createFont();//创建字体对象
		font.setColor(Font.COLOR_NORMAL);
		font.setFontHeightInPoints((short) (22));
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment( XSSFCellStyle.ALIGN_CENTER);
		XSSFCellStyle wrapStyle = xwk.createCellStyle();
		wrapStyle.setWrapText(true);
		XSSFCellStyle ver = xwk.createCellStyle();
		ver.setAlignment( XSSFCellStyle.VERTICAL_TOP );
		xsheet.addMergedRegion(new CellRangeAddress(2, 4, 0, 2));// 2~11
		xsheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(9, 10, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(9, 9, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(10, 10, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(11, 11, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 2));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 2));
		xsheet.addMergedRegion(new CellRangeAddress(13, 13, 3, 5));// 13~17
		xsheet.addMergedRegion(new CellRangeAddress(14, 14, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(15, 15, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(16, 16, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(17, 17, 3, 5));
		xsheet.addMergedRegion(new CellRangeAddress(19, 19, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(21, 21, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(23, 23, 0, 5));
		xsheet.addMergedRegion(new CellRangeAddress(24, 24, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(25, 25, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(26, 26, 1, 5));
		xsheet.addMergedRegion(new CellRangeAddress(27, 27, 1, 5));

		for (int i = 0; i < rowsCount; i++) {
			XSSFRow dataRow = xsheet.createRow(i);
			if(i==0){
				// 设置合并单元格的区域 行开始,行结束,列开始,列结束 标题
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
				xsheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
				dataRow.setHeightInPoints(90);
			}else{
				dataRow.setHeightInPoints(20);
			}
			
			if (i == 2) {
				
				dataRow.createCell(0).setCellStyle(style);
				dataRow.createCell(0).setCellValue("Purchase Order");
				String str = po.get("Number").toString()== null ? "" :po.get("Number").toString();
				dataRow.createCell(3).setCellValue("Number: " + str);
			}
			if (i == 3) {
				String str = po.get("Version").toString()== null ? "" :po.get("Version").toString();
				dataRow.createCell(3).setCellValue("Versions: " + str);
			}
			if (i == 6) {
				dataRow.createCell(0).setCellValue("Bill To");
				dataRow.createCell(3).setCellValue("Vendor");
			}
			if (i == 7) {
				dataRow.createCell(0).setCellValue("王晓亮");
				dataRow.createCell(3).setCellValue(po.get("VendorOne")== null ? "" :po.get("VendorOne").toString());
			}
			if (i == 8) {
				dataRow.createCell(0).setCellValue("邮箱: wangxiaoliang@eoulu.com");
				dataRow.createCell(3).setCellValue(po.get("VendorTwo")== null ? "" :po.get("VendorTwo").toString());
			}
			if (i == 9) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("苏州工业园区星湖街218号生物纳米园A7楼305室");
				c.setCellStyle(wrapStyle);
//				c.setCellStyle(ver);
				dataRow.createCell(3).setCellValue(po.get("VendorThree")== null ? "" :po.get("VendorThree").toString());
			}
			if (i == 11) {
				dataRow.createCell(0).setCellValue("电话:0512-62757360");
			}
			if (i == 13) {
				dataRow.createCell(0).setCellValue("Forwarder");
				dataRow.createCell(3).setCellValue("Ship to");
			}
			if (i == 14) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderOne")== null ? "" :po.get("ForwarderOne").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipCompany")== null ? "" : po.get("ShipCompany").toString());
			}
			if (i == 15) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderTwo")== null ? "" : po.get("ForwarderTwo").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipAddr")== null ? "" : po.get("ShipAddr").toString());
			}
			if (i == 16) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderThree")== null ? "" : po.get("ForwarderThree").toString());
				dataRow.createCell(3).setCellValue(po.get("ShipTel")== null ? "" : po.get("ShipTel").toString());
			}
			if (i == 17) {
				dataRow.createCell(0).setCellValue(po.get("ForwarderFour")== null ? "" : po.get("ForwarderFour").toString());
				dataRow.createCell(3).setCellValue( po.get("ShipAttn")== null ? "" : po.get("ShipAttn").toString());
			}
			if (i == 19) {
				dataRow.createCell(0).setCellValue("Others");
			}
			if (i == 20) {
				String str = po.get("CreditTerm").toString()== null ? "" :po.get("CreditTerm").toString();
				dataRow.createCell(0).setCellValue("Credit Term:"+str);
			}
			if (i == 21) {
				String str = po.get("DeliveryTerm").toString()== null ? "" :po.get("DeliveryTerm").toString();
				dataRow.createCell(0).setCellValue("Delivery Term:" +str );
			}
			if (i == 23) {
				dataRow.createCell(0).setCellValue("Shipping Instruction");
			}
			if (i == 24) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("1");
				c.setCellStyle(cell);
				dataRow.createCell(1).setCellValue("Please do not put the original invoice with goods");
			}
			if (i == 25) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("2");
				c.setCellStyle(cell);
				String str = po.get("ShippingMark").toString()== null ? "" :po.get("ShippingMark").toString();
				dataRow.createCell(1).setCellValue("SHIPPING MARK:" + str);
			}
			if (i == 26) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("3");
				c.setCellStyle(cell);
				String str = po.get("ContractNO").toString()== null ? "" :po.get("ContractNO").toString();
				dataRow.createCell(1).setCellValue("Indicating CONTRACT NO.:" + str+" on the waybil");
			}
			if (i == 27) {
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("4");
				c.setCellStyle(cell);
				String str = po.get("ShipmentPort").toString()== null ? "" :po.get("ShipmentPort").toString();
				dataRow.createCell(1).setCellValue("Port of shipment: " + str);
			}
			if (i == 29) {
				for (int j = 0; j < colsCount; j++) {
					XSSFCell dataCell = dataRow.createCell(j);
					dataCell.setCellValue(colName[j]);
					dataCell.setCellStyle(center);
				}
			}
			if (i > 29 && i < rowsCount - 5 && list.size()!=0) {
				for (int j = 0; j < colsCount; j++) {

					XSSFCell dataCell = dataRow.createCell(j);
					Map<String, Object> map = list.get(i - 30);
					String val = "";

					if (colName[j].equals("Item")) {
						val = i - 29 + "";
						dataCell.setCellStyle(center);
					}
					if (colName[j].equals("Part")) {
						dataCell.setCellStyle(center);
						if(map.get("Model")==null){
							val = " ";
						}else{
							val = map.get("Model").toString().equals("") ? "--" : map.get("Model").toString();
						}
						
					}
					if (colName[j].equals("Description")) {
						val = map.get("Description").toString().equals("") ? "--" : map.get("Description").toString();
						dataCell.setCellStyle(wrapStyle);
//						dataCell.setCellStyle(ver);
					}
					if (colName[j].equals("Qty")) {
						dataCell.setCellStyle(center);
						val = map.get("Quantity").toString().equals("") ? "--" : map.get("Quantity").toString();
					}
					if (colName[j].equals("Unit Price")) {
						dataCell.setCellStyle(center);
						val = map.get("UnitPrice").toString().equals("") ? "--" : map.get("UnitPrice").toString();
					}
					if (colName[j].equals("Extended Price")) {
						dataCell.setCellStyle(center);
						val = map.get("ExtendedPrice").toString().equals("") ? "--"
								: map.get("ExtendedPrice").toString();
					}

					dataCell.setCellValue(val);
				}

			}
			if ((i - list.size()) == 30) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Sub-total");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("SubTotal").toString());

			}
			if ((i - list.size()) == 31) {
				
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Discounted 0%");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("Discounted").toString());
			}
			if ((i - list.size()) == 32) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 4));
				XSSFCell c = dataRow.createCell(0);
				c.setCellValue("Final Total");
				c.setCellStyle(cell);
				XSSFCell c1 = dataRow.createCell(5);
				c1.setCellStyle(center);
				c1.setCellValue(po.get("FinalTotal").toString());
			}
			if ((i - list.size()) == 33) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));
				XSSFCell c1 = dataRow.createCell(0);
				
				c1.setCellValue(
						"If you have any questions about your order, please contact:" + user);
				c1.setCellStyle(center);
			}
			if ((i - list.size()) == 34) {
				dataRow.getSheet().addMergedRegion(new CellRangeAddress(i, i, 0, 5));
				XSSFCell c1 = dataRow.createCell(0);
				
				c1.setCellValue(" E-Mail: " + email);
				c1.setCellStyle(center);
			}
		}

		FileOutputStream fo;

		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	public static void main(String[] args) {
		String path = "D:\\temp\\test.xlsx";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Number", "210124");
		map.put("Version", "1.0");
		map.put("ForwarderOne", "1.0");
		map.put("ForwarderTwo", "1.0");
		map.put("ForwarderThree", "1.0");
		map.put("ForwarderFour", "1.0");
		map.put("ShipCompany", "1.0");
		map.put("ShipAddr", "1.0");
		map.put("ShipTel", "1.0");
		map.put("ShipAttn", "1.0");
		map.put("DeliveryTerm", "1.0");
		map.put("EndCompany", "1.0");
		map.put("EndAddr", "1.0");
		map.put("EndTel", "1.0");
		map.put("ContactPerson", "1.0");
		map.put("ShippingMark", "1.0");
		map.put("ContractNO", "1.0");
		map.put("ShipmentPort", "1.0");
		map.put("SubTotal", "1.0");
		map.put("Discounted", "1.0");
		map.put("FinalTotal", "1.0");
		map.put("Contact", "1.0");
		map.put("Email", "1.0");
		map.put("CreditTerm", "1.0");
		map.put("VendorOne", "1.0");
		map.put("VendorTwo", "1.0");
		map.put("VendorThree", "1.0");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("Part", "EP-07");
		map1.put("Description", "EYE-PASS MULTICONTACT POWER PROBE, xx CONTACTS");
		map1.put("Qty", "1");
		map1.put("UnitPrice", "12");
		map1.put("ExtendedPrice", "223");
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map1);
		List<Map<String, Object>> ls = new ArrayList<>();
		ls.add(map);
		POExcelUtil util = new POExcelUtil();
//		util.buildCascadePO(list, ls, path);

//util.buildOtherPO(list, ls, path);
		
	}
	*/
}
