package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.OriginalQuotationDao;
import com.eoulu.entity.OriginalQuotation;
import com.eoulu.service.OriginalQuotationService;
import com.eoulu.util.PDFReaderUtil;

public class OriginalQuotationServiceImpl implements OriginalQuotationService {

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String type, String column1, String content1,
			String column2, String content2) {   
		return new OriginalQuotationDao().getDataByPage(page, type, column1, content1, column2, content2);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
		return new OriginalQuotationDao().getCounts(type, column1, content1, column2, content2);
	}

	@Override
	public boolean insert(OriginalQuotation originalQuotation) {
	
		return new OriginalQuotationDao().insert(originalQuotation);
	}

	public String getQuoteTotal(String fileName){
		String result = PDFReaderUtil.getTextFromPDF(fileName);
		int beginIndex = result.indexOf("Quote Total: USD");
		if(beginIndex == -1){
			beginIndex = result.indexOf("Quote Total:USD");
		}
		int endIndex = 0;
		for(int i = beginIndex;i < beginIndex +50;i++ ){
			if(result.charAt(i)=='\n'){
				endIndex = i;
				break;
			}
		}
		String text = result.substring(beginIndex,endIndex);
		String quoteTotal = text.split(":")[1].trim();
		return quoteTotal;
		
	}

	@Override
	public boolean update(OriginalQuotation originalQuotation) {
		return new OriginalQuotationDao().update(originalQuotation);
	}

}
