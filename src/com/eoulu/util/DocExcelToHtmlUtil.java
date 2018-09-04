package com.eoulu.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
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

public class DocExcelToHtmlUtil {
	/**
	 * 回车符ASCII码
	 */
	private static final short ENTER_ASCII = 13;

	/**
	 * 空格符ASCII码
	 */
	private static final short SPACE_ASCII = 32;

	/**
	 * 水平制表符ASCII码
	 */
	private static final short TABULATION_ASCII = 9;

	public static String htmlText = "";
	public static String htmlTextTbl = "";
	public static int counter = 0;
	public static int beginPosi = 0;
	public static int endPosi = 0;
	public static int beginArray[];
	public static int endArray[];
	public static String htmlTextArray[];
	public static boolean tblExist = false;

	// public static void main(String argv[])
	// {
	// try {
	// getWordAndStyle(inputFile);
	// System.out.println(htmlText);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	/**
	 * 读取每个文字样式
	 * 
	 * @param fileName
	 * @throws Exception
	 */

	public static String getWordAndStyle(String fileName, String htmlPath) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		HWPFDocument doc = new HWPFDocument(in);

		Range rangetbl = doc.getRange();// 得到文档的读取范围
		TableIterator it = new TableIterator(rangetbl);
		int num = 100;

		beginArray = new int[num];
		endArray = new int[num];
		htmlTextArray = new String[num];

		// 取得文档中字符的总数
		int length = doc.characterLength();

		PicturesTable pTable = doc.getPicturesTable();

		htmlText = "<html><head><title>" + doc.getSummaryInformation().getTitle() + "</title></head><body>";
		// 创建临时字符串,好加以判断一串字符是否存在相同格式

		if (it.hasNext()) {
			readTable(it, rangetbl);
		}

		int cur = 0;

		String tempString = "";
		for (int i = 0; i < length - 1; i++) {
			// 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
			Range range = new Range(i, i + 1, doc);

			CharacterRun cr = range.getCharacterRun(0);
			if (tblExist) {
				if (i == beginArray[cur]) {
					htmlText += tempString + htmlTextArray[cur];
					tempString = "";
					i = endArray[cur] - 1;
					cur++;
					continue;
				}
			}
			if (pTable.hasPicture(cr)) {
				htmlText += tempString;
				// 读写图片
				readPicture(pTable, cr);
				tempString = "";
			} else {

				Range range2 = new Range(i + 1, i + 2, doc);
				// 第二个字符
				CharacterRun cr2 = range2.getCharacterRun(0);
				char c = cr.text().charAt(0);

				// System.out.println(i+"::"+range.getEndOffset()+"::"+range.getStartOffset()+"::"+c);

				// 判断是否为回车符
				if (c == ENTER_ASCII) {
					tempString += "<br/>";

				}
				// 判断是否为空格符
				else if (c == SPACE_ASCII)
					tempString += " ";
				// 判断是否为水平制表符
				else if (c == TABULATION_ASCII)
					tempString += "    ";
				// 比较前后2个字符是否具有相同的格式
				boolean flag = compareCharStyle(cr, cr2);
				if (flag)
					tempString += cr.text();
				else {
					String fontStyle = "<span style='font-family:" + cr.getFontName() + ";font-size:"
							+ cr.getFontSize() / 2 + "pt;";

					if (cr.isBold()) {
						fontStyle += "font-weight:bold;";
					}
					if (cr.isItalic()) {
						fontStyle += "font-style:italic;";
					}

					htmlText += fontStyle + "'>" + tempString + cr.text() + "</span>";
					tempString = "";
					System.out.println("格式：" + htmlText);
				}
			}
		}

		htmlText += tempString + "</body></html>";
		writeFile(htmlText, htmlPath);

		return htmlText;
	}

	/**
	 * 读写文档中的表格
	 * 
	 * @param pTable
	 * @param cr
	 * @throws Exception
	 */
	public static void readTable(TableIterator it, Range rangetbl) throws Exception {

		htmlTextTbl = "";
		// 迭代文档中的表格

		counter = -1;
		while (it.hasNext()) {
			tblExist = true;
			htmlTextTbl = "";
			Table tb = (Table) it.next();
			beginPosi = tb.getStartOffset();
			endPosi = tb.getEndOffset();

			System.out.println("............" + beginPosi + "...." + endPosi);
			counter = counter + 1;
			// 迭代行，默认从0开始
			beginArray[counter] = beginPosi;
			endArray[counter] = endPosi;

			htmlTextTbl += "<table border='1'>";
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);

				htmlTextTbl += "<tr>";
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					int cellWidth = td.getWidth();

					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text().toString().trim();
						if (s == "") {
							s = " ";
						}

						htmlTextTbl += "<td width='" + cellWidth + "'>" + s + "</td>";

					}
				}
				htmlTextTbl += "</tr>";
			}
			htmlTextTbl += "</table>";
			htmlTextArray[counter] = htmlTextTbl;
			System.out.println("表格：" + htmlTextTbl);

		}
	}

	/**
	 * 读写文档中的图片
	 * 
	 * @param pTable
	 * @param cr
	 * @throws Exception
	 */
	public static void readPicture(PicturesTable pTable, CharacterRun cr) throws Exception {
		// 提取图片
		Picture pic = pTable.extractPicture(cr, false);
		String afileName = pic.suggestFullFileName();
		OutputStream out = new FileOutputStream(new File("c://test" + File.separator + afileName));
		pic.writeImageContent(out);
		// htmlText += "<img src="c://test//" + afileName + ""
		// mce_src="c://test//" + afileName + ""/>";
	}

	public static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
		boolean flag = false;
		if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()
				&& cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 写文件
	 * 
	 * @param s
	 */
	public static void writeFile(String s, String htmlPath) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(htmlPath);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(s);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	public static void main(String[] args) {
		/*
		OPCPackage opcPackage;
		try {

			InputStream is = new FileInputStream(new File("E:/Commercial Invoice-10%.doc"));
			WordExtractor ex = new WordExtractor(is);
			String text2003 = ex.getText();
			System.out.println(text2003);
			// opcPackage = POIXMLDocument.openPackage("E:Cascade PO-word
			// 模板.docx");
			// POIXMLTextExtractor extractor = new
			// XWPFWordExtractor(opcPackage);
			// String text2007 = extractor.getText();
			// System.out.println(text2007);
		} catch (IOException e) {
			e.printStackTrace();
			// } catch (XmlException e) {
			// e.printStackTrace();
			// } catch (OpenXML4JException e) {
			// e.printStackTrace();
		}
*/
		FileInputStream in;
		try {
			in = new FileInputStream("E:/Commercial Invoice-10%.docx");
			XWPFDocument  swpf = new XWPFDocument (in);
			Iterator<XWPFTable> tables = swpf.getTablesIterator();
			List<XWPFTableRow> rows;
			List<XWPFTableCell> cells;
			FileWriter fileWriter = new FileWriter(new File("result.txt"));

			
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
					
//				
				} // end for
			} // end while

			fileWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // 载入文档
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
