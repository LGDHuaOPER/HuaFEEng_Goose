package com.eoulu.syn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class ExportInvoiceFile {
	
	private Lock lock = new ReentrantLock();
	HashMap<String, Object> data = new HashMap<>();
	
	public String exportInvoice(HttpServletRequest request){
		lock.lock();
		String loadUrl = null;
		try{
			long time1 = System.currentTimeMillis();
			HashMap<String, Object> data = new HashMap<>();
			String ContractNO = request.getParameter("ContractNO") == null?"":request.getParameter("ContractNO");
			String PONO = request.getParameter("PONO")==null?"":request.getParameter("PONO");
			String InvoiceNO = request.getParameter("InvoiceNO") == null?"":request.getParameter("InvoiceNO");
			String DCNO = request.getParameter("DCNO")==null?"":request.getParameter("DCNO");
			String Applicant = request.getParameter("Applicant")==null?"":request.getParameter("Applicant");
			String AddInfo = request.getParameter("AddInfo")==null?"":request.getParameter("AddInfo");
			String Tel = request.getParameter("Tel")==null?"":request.getParameter("Tel");
			String Fax = request.getParameter("Fax")==null?"":request.getParameter("Fax");
			String EndUser = request.getParameter("EndUser")==null?"":request.getParameter("EndUser");
			String OtherReference = request.getParameter("OtherReference")==null?"":request.getParameter("OtherReference");
			String DepartureDate = request.getParameter("DepartureDate")==null?"":request.getParameter("DepartureDate");
			String Vessel = request.getParameter("Vessel")==null?"":request.getParameter("Vessel");
			String Departure = request.getParameter("Departure")==null?"":request.getParameter("Departure");
			String Destination = request.getParameter("Destination")==null?"":request.getParameter("Destination");
			String PaymentRemark = request.getParameter("PaymentRemark")==null?"":request.getParameter("PaymentRemark");
			
			String GoodsInfo = request.getParameter("GoodsInfo") == null?"":request.getParameter("GoodsInfo");
			String Packing = request.getParameter("Packing")==null?"":request.getParameter("Packing");
			String Origin = request.getParameter("Origin")==null?"":request.getParameter("Origin");
			String Manufacturer = request.getParameter("Manufacturer")==null?"":request.getParameter("Manufacturer");
			String ShippingMark = request.getParameter("ShippingMark")==null?"":request.getParameter("ShippingMark");
			String TotalAmount = request.getParameter("TotalAmount")==null?"":request.getParameter("TotalAmount");
			String Date = request.getParameter("Date")==null?"":request.getParameter("Date");
			String AirPort = request.getParameter("AirPort")==null?"":request.getParameter("AirPort");
			String isExist = request.getParameter("isExist");
			data.put("${ContractNO}", ContractNO);
			data.put("${PONO}",PONO );
			data.put("${InvoiceNO}", InvoiceNO);
			data.put("${DCNO}", DCNO);
			data.put("${Applicant}", Applicant);
			data.put("${AddInfo}", AddInfo);
			data.put("${Tel}", Tel);
			data.put("${Fax}", Fax);
			data.put("${EndUser}", EndUser);
			data.put("${OtherReference}", OtherReference);
			data.put("${DepartureDate}", DepartureDate);
			data.put("${Vessel}", Vessel);
			data.put("${Departure}", Departure);
			data.put("${Destination}", Destination);
			data.put("${PaymentRemark}", PaymentRemark);
			data.put("${Packing}", Packing);
			data.put("${Origin}", Origin);
			data.put("${Manufacturer}", Manufacturer);
			data.put("${ShippingMark1}", ShippingMark.split("&&",2)[0]);
			data.put("${ShippingMark2}", ShippingMark.split("&&",2)[1]);
			data.put("${AirPort}", AirPort);
			data.put("${TotalAmount}", TotalAmount);
			data.put("${Date}", Date);
			
			List<Map<String, String>> goodsList = new ArrayList<>();
			if(!GoodsInfo.equals("")){
				JSONArray array = JSONArray.fromObject(GoodsInfo);
				JSONObject object = null;
				Map<String,String> updateMap = null;
				
				for(int i = 0;i < array.size();i ++){
					object = array.getJSONObject(i);
					updateMap = new HashMap<>();
					updateMap.put("Description",(String)object.get("Description"));
					updateMap.put("Model",(String)object.get("Model"));
					updateMap.put("Qty",(String)object.get("Qty"));
					updateMap.put("Unit", (String)object.get("Unit"));
					updateMap.put("UnitUSDPrice", (String)object.get("UnitUSDPrice"));
					updateMap.put("TotalUSDAmount", (String)object.get("TotalUSDAmount"));
					
					goodsList.add(updateMap);
				}
			}
			
			if(goodsList.size()>0){
				ArrayList<String[]> table = new ArrayList<>();
				table.add(new String[]{"1","2","3","4","5","6"});
				for(int i = 0;i < goodsList.size();i ++){
					table.add(new String[]{(i+1)+"",goodsList.get(i).get("Model")+"\n"+goodsList.get(i).get("Description"),
							goodsList.get(i).get("Unit"),goodsList.get(i).get("Qty"),goodsList.get(i).get("UnitUSDPrice"),
							goodsList.get(i).get("TotalUSDAmount")});
				}
				data.put("table$9@1", table);
			}
			
			Java2Word word = new Java2Word();
			//String downLoadUrl = "E:/LogisticsFile/File/Invoice-" + InvoiceNO
				//			+ ".doc";
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\Invoice-" + InvoiceNO
					+ ".doc";
			if(isExist.equals("true")){
		
				word.toWord("E:/Model/Invoice-带章.doc", downLoadUrl, data,"middle");
			
			}else{
				word.toWord("E:/Model/Invoice-无章.doc", downLoadUrl, data,"middle");
			}
		
			System.out.println("time cost : " + (System.currentTimeMillis() - time1));
		
			ConventerToPDFUtil.word2pdf(downLoadUrl);
			loadUrl = "down\\Invoice-" + InvoiceNO
					+ ".pdf";

		
			
			
		}finally{
			lock.unlock();
		}
		return loadUrl;

	}
	

}
