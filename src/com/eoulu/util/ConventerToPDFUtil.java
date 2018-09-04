package com.eoulu.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class ConventerToPDFUtil {
	private static final String[] OFFICE_POSTFIXS = { "doc", "docx", "xls", "xlsx", "ppt", "pptx" };

	private ArrayList<String> Office_Formats = new ArrayList<String>();

	private static final String PDF_POSTFIX = "pdf";
	
	private static Lock lock = new ReentrantLock();// 锁对象

	public static void word2pdf(String inputFilePath) {
		System.out.println("inputFilePath---"+inputFilePath);
		lock.lock();// 得到锁
		try{
		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
//获取OpenOffice的安装目录
		String officeHome = getOfficeHome();
		
		System.out.print(officeHome);
		config.setOfficeHome(officeHome);
//启动OpenOffice的服务
		OfficeManager officeManager = config.buildOfficeManager();
		officeManager.start();

		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		String outputFilePath = getOutputFilePath(inputFilePath);
		System.out.println("outputFilePath--"+outputFilePath);
		File inputFile = new File(inputFilePath);
		if (inputFile.exists()) {// 找不到源文件, 则返回
			File outputFile = new File(outputFilePath);
			if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径
				outputFile.getParentFile().mkdirs();
			}
			converter.convert(inputFile, outputFile);
		}

		officeManager.stop();
		}finally {
			lock.unlock();// 释放锁
		}
	}
	
	public static File word2pdfIos(String inputFilePath) {
		System.out.println("inputFilePath---"+inputFilePath);
		File outputFile = null;
		lock.lock();// 得到锁
		try{
		
		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
//获取OpenOffice的安装目录
		String officeHome = getOfficeHome();
		
		System.out.print(officeHome);
		config.setOfficeHome(officeHome);
//启动OpenOffice的服务
		OfficeManager officeManager = config.buildOfficeManager();
		officeManager.start();

		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		String outputFilePath = getOutputFilePath(inputFilePath);
		System.out.println("outputFilePath--"+outputFilePath);
		File inputFile = new File(inputFilePath);
		if (inputFile.exists()) {// 找不到源文件, 则返回
			outputFile = new File(outputFilePath);
			if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径
				outputFile.getParentFile().mkdirs();
			}
			converter.convert(inputFile, outputFile);
		}

		officeManager.stop();
		}finally {
			lock.unlock();// 释放锁
		}
		return outputFile;
	}

	public static String getOutputFilePath(String inputFilePath) {
		String outputFilePath = "";
		if(inputFilePath.endsWith(".doc")){
			outputFilePath = inputFilePath.replaceAll(".doc", ".pdf");
		}
		if(inputFilePath.endsWith(".docx")){
			outputFilePath = inputFilePath.replaceAll(".docx", ".pdf");
		}
		return outputFilePath;
	}

	public static String getOfficeHome() {
		String osName = System.getProperty("os.name");//获取当前系统的名称
		if (Pattern.matches("Linux.*", osName)) {
			return "/opt/openoffice.org3";
		} else if (Pattern.matches("Windows.*", osName)) {
			return "C:/Program Files (x86)/OpenOffice 4";
		} else if (Pattern.matches("Mac.*", osName)) {
			return "/Application/OpenOffice.org.app/Contents";
		}
		return null;
	}

	public static void main(String[] args) {
//		String inputFilePath = "E:/result.doc";
//		word2pdf(inputFilePath);
		
		ConventerToPDFUtil a = new ConventerToPDFUtil();
//		a.excelToPdf("E:/EOULU发货清单&包装&运输 (21).xlsx", "E:/excelToPdf.pdf");
		a.excelToPdf("D:/新建文件夹/2018129 (2).xlsx", "E:/2018129 (2).pdf");
	}

	public void converterFile(String inputFilePath, String outputFilePath, OfficeDocumentConverter converter) {

		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		if (outputFile.exists()) {
			outputFile.getParentFile().mkdirs();
		}
		converter.convert(inputFile, outputFile);
		System.out.println("文件：" + inputFilePath + "\n转换为\n目标文件：" + outputFile + "\n成功！");
	}

	public String getPostfix(String inputFilePath) {
		String[] p = inputFilePath.split("\\.");
		if (p.length > 0) {
			return p[p.length - 1];
		} else {
			return null;
		}
	}

	public String generateDefaultOutputFilePath(String inputFilePath) {

		String outputFilePath = inputFilePath.replaceAll("." + getPostfix(inputFilePath),
				"_" + getPostfix(inputFilePath) + ".pdf");

		return outputFilePath;
	}

	public boolean excelToPdf(String inputFilePath, String outputFilePath) {
		boolean flag = false;
		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
		String officeHome = getOfficeHome();
		config.setOfficeHome(officeHome);
		OfficeManager officeManager = config.buildOfficeManager();
		officeManager.start();
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		long begin_time = new Date().getTime();
		File inputFile = new File(inputFilePath);
		Collections.addAll(Office_Formats, OFFICE_POSTFIXS);
		if ((null != inputFilePath) && (inputFile.exists())) {
			if (Office_Formats.contains(getPostfix(inputFilePath))) {
				if (null == outputFilePath) {
					String outputFilePath_new = generateDefaultOutputFilePath(inputFilePath);
					converterFile(inputFilePath, outputFilePath_new, converter);
					flag = true;
				} else {
					converterFile(inputFilePath, outputFilePath, converter);
					flag = true;
				}
			}
		} else {
			System.out.println("con't find the resource");
		}
		long end_time = new Date().getTime();
		System.out.println("文件转换耗时：[" + (end_time - begin_time) + "]ms");
		officeManager.stop();
		return flag;
	}
}
