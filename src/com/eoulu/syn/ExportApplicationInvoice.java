package com.eoulu.syn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.ExcelReplaceDataVO;
import com.eoulu.util.ExcelReplaceUtil;
import com.eoulu.util.Java2Word;
import com.eoulu.util.NumToChineseRMBUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportApplicationInvoice {
	private Lock lock = new ReentrantLock();
	

	public String getInvoiceFolder(HttpServletRequest request) {
		lock.lock();
		HashMap<String, Object> data = new HashMap<>();
		String CustomerName = request.getParameter("CustomerName")==null?"":request.getParameter("CustomerName");
		String ContractNO = request.getParameter("ContractNO")==null?"":request.getParameter("ContractNO");
		String ContractName = request.getParameter("ContractName")==null?"":request.getParameter("ContractName");
		String SumOfTaxPrice = request.getParameter("SumOfTaxPrice")==null?"":request.getParameter("SumOfTaxPrice");
		String InvoiceTitle = request.getParameter("InvoiceTitle")==null?"":request.getParameter("InvoiceTitle");
		String TaxPayerIdentityNO = request.getParameter("TaxPayerIdentityNO") == null?"":request.getParameter("TaxPayerIdentityNO").trim();
		String RegisterAddress = request.getParameter("RegisterAddress") == null?"":request.getParameter("RegisterAddress").trim();
		String Telephone = request.getParameter("Telephone") == null?"":request.getParameter("Telephone").trim();
		String DepositBank = request.getParameter("DepositBank") == null?"":request.getParameter("DepositBank").trim();
		String Account = request.getParameter("Account") == null?"":request.getParameter("Account").trim();
		String InvoiceRecepter = request.getParameter("InvoiceRecepter") == null?"":request.getParameter("InvoiceRecepter").trim();
		String LinkAddress = request.getParameter("LinkAddress") == null?"":request.getParameter("LinkAddress");
		String LinkTel = request.getParameter("LinkTel") == null?"":request.getParameter("LinkTel");
		String LinkZipCode = request.getParameter("LinkZipCode") == null?"":request.getParameter("LinkZipCode").trim();
		String contract = request.getParameter("Contract") == null?"":request.getParameter("Contract");
		String SumOfQuantity = request.getParameter("SumOfQuantity") == null?"":request.getParameter("SumOfQuantity");
		String Goods = request.getParameter("Goods") == null?"":request.getParameter("Goods");
		String UserMail = request.getSession().getAttribute("email").toString();
		
		String UserName = new StaffInfoDao().getTelAndName(UserMail).get(1).get("StaffName").toString();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String date = simpleDateFormat.format(new Date());
		data.put("{CustomerName}", CustomerName);
		data.put("{ContractNO}", ContractNO);
		try {
			data.put("{SumOfTaxPrice}", NumToChineseRMBUtil.arabNumToChineseRMB(Double.parseDouble(SumOfTaxPrice)));
		} catch (Exception e1) {
		
			e1.printStackTrace();
		}
		data.put("{UserName}", UserName);
		data.put("{UserMail}", UserMail);
		data.put("{Date}",date);
		Java2Word word = new Java2Word();
		String basePath = "E:/开票资料-"+ContractNO+"-"+ContractName;
	
		File folder = new File(basePath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		String downLoadUrl = basePath+"/发票签收回执.doc";
		word.toWord("E:/Model/发票签收回执.doc",downLoadUrl, data,"end");
		data.clear();
		data.put("{InvoiceTitle}",InvoiceTitle);
		data.put("{TaxPayerIdentityNO}",TaxPayerIdentityNO);
		data.put("{RegisterAddress}",RegisterAddress);
		data.put("{Telephone}",Telephone);
		data.put("{DepositBank}",DepositBank);
		data.put("{Account}",Account);
		data.put("{InvoiceRecepter}",InvoiceRecepter);
		data.put("{LinkAddress}",LinkAddress);
		data.put("{LinkTel}",LinkTel);
		data.put("{LinkZipCode}",LinkZipCode);
		Java2Word word2 = new Java2Word();
		
		downLoadUrl = basePath+"/开票信息.doc";
		word2.toWord("E:/Model/开票信息.doc",downLoadUrl, data,"end");
	
		
		
		List<Map<String, String>> list = new ArrayList<>();
		JSONArray array = JSONArray.fromObject(Goods);
		if(array.size()>0){
			JSONObject object = null;
			Map<String,String> updateMap = null;
			
			for(int i = 0;i < array.size();i ++){
				object = array.getJSONObject(i);
				updateMap = new LinkedHashMap<>();
				updateMap.put("GoodsTaxName",(String)object.get("GoodsTaxName"));
				
				updateMap.put("TypeSpecification", (String)object.get("TypeSpecification"));
				updateMap.put("MeasurementUnit", (String)object.get("MeasurementUnit"));
				updateMap.put("Quantity", object.get("Quantity").toString());
				updateMap.put("UnitPrice", object.get("UnitPrice").toString());
				updateMap.put("SumOfMoney", object.get("SumOfMoney").toString());
				updateMap.put("TaxRate", object.get("TaxRate").toString());
				updateMap.put("TaxAmount", object.get("TaxAmount").toString());
				updateMap.put("TotalPriceTax", object.get("TotalPriceTax").toString());
			
				list.add(updateMap);
			}
			
		}

		POIFSFileSystem fs  = null;     
        HSSFWorkbook wb = null;
		try {
			fs  =new POIFSFileSystem(new FileInputStream("E:/Model/开票委托单.xls"));
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			ExcelReplaceUtil.insertRow(wb, sheet, 11, list.size()-1);
			FileOutputStream fo = null;
		
			fo = new FileOutputStream(basePath+"/开票委托单.xls");
			wb.write(fo);
			fo.flush();
			fo.close();
			  
           
			
		} catch (IOException e) {
	
			e.printStackTrace();
		} 
		
	
		
		
		List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();  
		ExcelReplaceDataVO v01 = new ExcelReplaceDataVO();
		v01.setColumn(2);
		v01.setRow(5);
		v01.setKey("{InvoiceTitle}");
		v01.setValue(InvoiceTitle);
		datas.add(v01);
		ExcelReplaceDataVO v02 = new ExcelReplaceDataVO();
		v02.setColumn(7);
		v02.setRow(5);
		v02.setKey("{Telephone}");
		v02.setValue(Telephone);
		datas.add(v02);
		ExcelReplaceDataVO v03 = new ExcelReplaceDataVO();
		v03.setColumn(2);
		v03.setRow(6);
		v03.setKey("{RegisterAddress}");
		v03.setValue(RegisterAddress);
		datas.add(v03);
		ExcelReplaceDataVO v04 = new ExcelReplaceDataVO();
		v04.setColumn(7);
		v04.setRow(6);
		v04.setKey("{LinkZipCode}");
		v04.setValue(LinkZipCode);
		datas.add(v04);
		ExcelReplaceDataVO v05 = new ExcelReplaceDataVO();
		v05.setColumn(2);
		v05.setRow(7);
		v05.setKey("{TaxPayerIdentityNO}");
		v05.setValue(TaxPayerIdentityNO);
		datas.add(v05);
		ExcelReplaceDataVO v06 = new ExcelReplaceDataVO();
		v06.setColumn(2);
		v06.setRow(8);
		v06.setKey("{DepositBank}");
		v06.setValue(DepositBank);
		datas.add(v06);
		ExcelReplaceDataVO v07 = new ExcelReplaceDataVO();
		v07.setColumn(2);
		v07.setRow(8);
		v07.setKey("{Account}");
		v07.setValue(Account);
		datas.add(v07);
		for(int i = 0;i < list.size();i ++){
			Iterator<Map.Entry<String,String >> iterator = list.get(i).entrySet().iterator();
			int j = 0;
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				ExcelReplaceDataVO v0 = new ExcelReplaceDataVO();
				v0.setColumn(j);
				v0.setRow(11+i);
				v0.setKey("{"+entry.getKey()+"}");
				v0.setValue(entry.getValue().toString());
				datas.add(v0);
				j++;
			}
			
			
		}
		ExcelReplaceDataVO v08 = new ExcelReplaceDataVO();
		v08.setColumn(3);
		v08.setRow(11+list.size());
		v08.setKey("{SumOfQuantity}");
		v08.setValue(SumOfQuantity);
		datas.add(v08);
		ExcelReplaceDataVO v09 = new ExcelReplaceDataVO();
		v09.setColumn(8);
		v09.setRow(11+list.size());
		v09.setKey("{SumOfTaxPrice}");
		v09.setValue(SumOfTaxPrice);
		datas.add(v09);
		ExcelReplaceDataVO v010 = new ExcelReplaceDataVO();
		v010.setColumn(1);
		v010.setRow(11+list.size()+1);
		v010.setKey("{ContractNO}");
		v010.setValue(ContractNO);
		datas.add(v010);
		ExcelReplaceDataVO v011 = new ExcelReplaceDataVO();
		v011.setColumn(5);
		v011.setRow(11+list.size()+2);
		v011.setKey("{InvoiceRecepter}");
		v011.setValue(InvoiceRecepter);
		datas.add(v011);
		ExcelReplaceDataVO v012 = new ExcelReplaceDataVO();
		v012.setColumn(5);
		v012.setRow(11+list.size()+2);
		v012.setKey("{LinkTel}");
		v012.setValue(LinkTel);
		datas.add(v012);
		
		ExcelReplaceDataVO v013 = new ExcelReplaceDataVO();
		v013.setColumn(5);
		v013.setRow(11+list.size()+2);
		v013.setKey("{LinkAddress}");
		v013.setValue(LinkAddress);
		datas.add(v013);
		
		Calendar calendar = Calendar.getInstance();
		ExcelReplaceDataVO v014 = new ExcelReplaceDataVO();
		v014.setColumn(0);
		v014.setRow(11+list.size()+7);
		v014.setKey("{Year}");
		v014.setValue(String.valueOf(calendar.get(Calendar.YEAR)));
		datas.add(v014);
		
		ExcelReplaceDataVO v015 = new ExcelReplaceDataVO();
		v015.setColumn(1);
		v015.setRow(11+list.size()+7);
		v015.setKey("{Month}");
		v015.setValue(calendar.get(Calendar.MONTH)+1<10?"0"+(calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH)+1)+"");
		datas.add(v015);
		ExcelReplaceDataVO v016 = new ExcelReplaceDataVO();
		v016.setColumn(2);
		v016.setRow(11+list.size()+7);
		v016.setKey("{Day}");
		v016.setValue(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		datas.add(v016);
		
		
		for(int i = 0;i < datas.size();i ++){
			System.out.println(datas.get(i));
		}
		
		ExcelReplaceUtil.replaceModel(datas, basePath+"/开票委托单.xls", basePath+"/开票委托单.xls");
		
	
		FileOutputStream foStream = null;
		FileInputStream fiStream = null;
		try {
			foStream = new FileOutputStream(basePath+"/"+contract);
			fiStream = new FileInputStream("E:/LogisticsFile/File/"+contract); 
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = fiStream.read(buffer))>0){
				foStream.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}finally {
			try {
				fiStream.close();
				foStream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
		
		lock.unlock();
		
		 
		
		
		return basePath;
			
	}

}
