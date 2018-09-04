package com.eoulu.syn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.eoulu.util.ConventerToPDFUtil;

public class PreviewSoftwarePDF {
	private Lock lock = new ReentrantLock();// 锁对象

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportSoftware(String path,String load) {
		lock.lock();// 得到锁
		for (int i = 0; i < 20; i++) {
			System.out.println("i--" + i);
		}
		long time1 = System.currentTimeMillis();

		try {
			ConventerToPDFUtil util = new ConventerToPDFUtil();

			String pdfUrl = load;
			if (path.endsWith(".doc")) {
				util.word2pdf(path);
				pdfUrl =  pdfUrl.replaceAll(".doc", ".pdf");
			}
			if (path.endsWith(".docx")) {
				util.word2pdf(path);
				pdfUrl = pdfUrl.replaceAll(".docx", ".pdf");
			}
			if (path.endsWith(".xls")) {

				pdfUrl = pdfUrl.replaceAll(".xls", ".pdf");
				util.excelToPdf(path, pdfUrl);
			}
			if (path.endsWith(".xlsx")) {
				pdfUrl = pdfUrl.replaceAll(".xlsx", ".pdf");
				System.out.println("test----"+pdfUrl);
				util.excelToPdf(path, pdfUrl);
			}
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			return pdfUrl;
		} finally {
			lock.unlock();// 释放锁
		}

	}
}
