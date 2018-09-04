package com.eoulu.syn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.eoulu.util.Java2Word;

public class ExportFAT {
	private Lock lock = new ReentrantLock();
	public String exportFile(List<Map<String, Object>> list){
		HashMap<String, Object> data = new HashMap<>();
		lock.lock();
		String ContractNO = list.get(1).get("ContractNO").toString();
		String DCNO = list.get(1).get("DCNO").toString();
		String CommoditySpecifications = list.get(1).get("CommoditySpecifications").toString();
		String Qty = list.get(1).get("Qty").toString();
		String Model = list.get(1).get("Model").toString();
		String PriceTerm = list.get(1).get("PriceTerm").toString();
		String Packing = list.get(1).get("Packing").toString();
		String TotalValue = list.get(1).get("TotalValue").toString();
		String Balance = list.get(1).get("Balance").toString();
		String Percent = list.get(1).get("Percent").toString();
		String EndUser = list.get(1).get("EndUser").toString();
		String Beneficiary = list.get(1).get("Beneficiary").toString();
		String Applicant = list.get(1).get("Applicant").toString();
		data.put("${ContractNO}",ContractNO);
		data.put("${DCNO}", DCNO);
		data.put("${CommoditySpecifications}", CommoditySpecifications);
		data.put("${Qty}", Qty);
		data.put("${Model}", Model);
		data.put("${PriceTerm}", PriceTerm);
		data.put("${Packing}", Packing);
		data.put("${TotalValue}", TotalValue);
		data.put("${Balance}", Balance);
		data.put("${Percent}", Percent);
		data.put("${EndUser}", EndUser);
		data.put("${Beneficiary}", Beneficiary);
		data.put("${Applicant}", Applicant);
		
		Java2Word word = new Java2Word();
		String basePath = "E:/LogisticsFile/File/FAT-"+list.get(1).get("ContractNO")+".doc";
		word.toWord("E:/Model/FAT模板.doc",basePath, data,"end");
		lock.unlock();
		
		return "FAT-"+list.get(1).get("ContractNO")+".doc";
		
	}

}
