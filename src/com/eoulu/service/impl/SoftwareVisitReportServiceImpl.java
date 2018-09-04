package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SoftwareVisitReportDao;
import com.eoulu.entity.VisitReport;
import com.eoulu.service.SoftwareVisitReportService;
import com.eoulu.util.ReadZipUtil;

public class SoftwareVisitReportServiceImpl implements SoftwareVisitReportService {

	@Override
	public List<Map<String, Object>> getAllData(Page page,  String content, String year) {
		if (content != null ) {
			content = "%" + content + "%";
		}
		return new SoftwareVisitReportDao().getAllData(page,  content, year);
	}

	@Override
	public int getCounts(String content, String year) {
		if ( content != null ) {
			content = "%" + content + "%";
		}
		return new SoftwareVisitReportDao().getCounts(content, year);
	}

	@Override
	public String getFileName(int id) {
		return new SoftwareVisitReportDao().getFileName(id);
	}

	@Override
	public boolean insert(HttpServletRequest request) {

		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		SoftwareVisitReportDao dao = new SoftwareVisitReportDao();
		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util = new ReadZipUtil();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = util.getForm(filePath, request, tempPath);
		} catch (FileUploadException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		String name = map.get("fileName");
		String path = map.get("filePath");
		String year = map.get("year");
		String isExist = map.get("isExist");
		VisitReport report = new VisitReport();
		report.setFileName(name);
		report.setFilePath(path);
		report.setYear(year);
		if (isExist.equals("exists")) {
			dao.delete(name);
		}
		boolean flag = dao.insert(report);

		return flag;
	}

	@Override
	public boolean moreManualAdd(HttpServletRequest request) {
		List<String> flags = new ArrayList<>();
		boolean flag = false;
		String path01 = "D:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\temp";
		File filePath = new File(path01);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}

		String tempPath = "E:\\LogisticsFile\\File\\";
		ReadZipUtil util = new ReadZipUtil();
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		try {
			ls = util.getMoreForm(filePath, request, tempPath);
		} catch (FileUploadException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		Map<String, String> map = ls.get(ls.size() - 1);
		String year = map.get("year");
		for (int i = 0; i < ls.size() - 1; i++) {
			VisitReport report = new VisitReport();
			Map<String, String> tempMap = ls.get(i);
		
			String name = tempMap.get("fileName");
			String path = tempMap.get("filePath");
			report.setFileName(name);
			report.setFilePath(path);
			report.setYear(year);
			boolean temp = new SoftwareVisitReportDao().insert(report);
			if (temp) {
				flags.add("true");
			} else {
				flags.add("flase");
			}

		}
		if (!flags.contains("flase")) {
			flag = true;
		}
		return flag;
	}

}
