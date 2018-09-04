package com.eoulu.syn;

import java.io.File;
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

public class ExportPackingPDF {
	private Lock lock = new ReentrantLock();// 锁对象
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportOtherPO(HttpServletRequest request) {
		lock.lock();// 得到锁
		long time1 = System.currentTimeMillis();
		String loadUrl = "";
		try {
			
			HashMap<String, Object> data = new HashMap<>();
			String type = request.getParameter("Type")==null?"":request.getParameter("Type");
			String Date = request.getParameter("Date")==null?"":request.getParameter("Date");
		
			String PackingListNO = request.getParameter("PackingListNO")==null?"":request.getParameter("PackingListNO");
			String FromAPP = request.getParameter("FromAPP")==null?"":request.getParameter("FromAPP");
			String FromADD = request.getParameter("FromADD")==null?"":request.getParameter("FromADD");
			String FromTel = request.getParameter("FromTel")==null?"":request.getParameter("FromTel");
			String FromFax = request.getParameter("FromFax")==null?"":request.getParameter("FromFax");
			String ToAPP = request.getParameter("ToAPP")==null?"":request.getParameter("ToAPP");
			String ToADD = request.getParameter("ToADD")==null?"":request.getParameter("ToADD");
			String ToATT = request.getParameter("ToATT")==null?"":request.getParameter("ToATT");
			String ToTel = request.getParameter("ToTel")==null?"":request.getParameter("ToTel");
			String ToFax = request.getParameter("ToFax")==null?"":request.getParameter("ToFax");
			String Title = request.getParameter("Title")==null?"":request.getParameter("Title");
			String ContractNO = request.getParameter("ContractNO")==null?"":request.getParameter("ContractNO");
			String PONO = request.getParameter("PONO")==null?"":request.getParameter("PONO");
			System.out.println("PONO=="+PONO);
			if(ContractNO.equals("NA") && PONO.equals("NA")){
				Title = "CONTRACT NO.";
			}else{
				if(!ContractNO.trim().equals("")&& !PONO.trim().equals("")){
					Title = "CONTRACT /PO NO.";
				}else if( ContractNO.trim().equals("") &&  !PONO.trim().equals("")){
					Title = "PO NO.";
				}else if(!ContractNO.trim().equals("")&& PONO.trim().equals("")){
					Title = "CONTRACT NO.";
				}
			}

			String Packing = request.getParameter("Packing")==null?"":request.getParameter("Packing");
			String Shipvia = request.getParameter("Shipvia")==null?"":request.getParameter("Shipvia");
			String DCNO = request.getParameter("DCNO")==null?"":request.getParameter("DCNO");
			
			String isExist=request.getParameter("isExist");
			
			data.put("${Date}", Date);
			data.put("${PackingListNO}", PackingListNO);
			data.put("${FromAPP}", FromAPP);
			data.put("${FromADD}", FromADD);
			data.put("${FromTel}", FromTel);
			data.put("${FromFax}", FromFax);
			data.put("${ToAPP}", ToAPP);
			data.put("${ToADD}", ToADD);
			data.put("${ToATT}", ToATT);
			data.put("${ToTel}", ToTel);
			data.put("${ToFax}", ToFax);
			data.put("${Title}", Title);
			data.put("${ContractNO}", ContractNO);
			data.put("${PONO}", PONO);
			data.put("${Packing}", Packing);
			data.put("${Shipvia}", Shipvia);
			data.put("${DCNO}", DCNO);

			
			if(type.equals("")){
				String year = request.getParameter("Year").equals("")?Date.split("-")[0]:request.getParameter("Year");
				String month = request.getParameter("Month").equals("")?Date.split("-")[1]:request.getParameter("Month");
				String day = request.getParameter("Day").equals("")?Date.split("-")[2]:request.getParameter("Day");
				String[] Model = request.getParameterValues("Model[]");
				String[] Description = request.getParameterValues("Description[]");
				String[] Quantity = request.getParameterValues("Quantity[]");
				if(Model!=null){
					ArrayList table = new ArrayList(Model.length);
					String[] fieldName = { "1", "2", "3", "4"};
					table.add(fieldName);
						for (int i = 0; i < Model.length; i++) {
							String[] field = { (i+1)+"", Model[i], Description[i], Quantity[i] };
							table.add(field);
						}
	
					data.put("table$4@3", table);
				}
				data.put("${Year}", year);
				data.put("${Month}", month);
				data.put("${Day}", day);
				
				String uploadFilePath = request.getServletContext().getRealPath("/") + "down\\";
				File uploadFile = new File(uploadFilePath);
				if (!uploadFile.exists()) {
					uploadFile.mkdirs();
				}
				Java2Word word = new Java2Word();
				String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\箱单配置-" + PackingListNO
						+ ".doc";
				if(isExist.equals("true")){
					if(FromAPP.startsWith("HK")){
						word.toWord("E:/Model/PackingList-配置信息-HK.doc", downLoadUrl, data,"end");
					}
					if(FromAPP.startsWith("苏州")){
						word.toWord("E:/Model/PackingList-配置信息-SZ.doc", downLoadUrl, data,"end");
					}
				}else{
					word.toWord("E:/Model/PackingList-配置信息-无章.doc", downLoadUrl, data,"end");
				}
			
				System.out.println("time cost : " + (System.currentTimeMillis() - time1));
				 loadUrl = "down\\箱单配置-" + PackingListNO
						+ ".doc";
				ConventerToPDFUtil.word2pdf(downLoadUrl);
				loadUrl = "down\\箱单配置-" + PackingListNO
						+ ".pdf";
	
			}
			else if(type.equals("size")){
				String sizeInfo = request.getParameter("SizeInfo") == null?"":request.getParameter("SizeInfo");
				String goodsInfo = request.getParameter("GoodsInfo") == null?"":request.getParameter("GoodsInfo");
				String SignDate = request.getParameter("SignDate").equals("")?Date:request.getParameter("SignDate");
				List<Map<String, String>> sizeList = new ArrayList<>();
				if(!sizeInfo.equals("")){
					JSONArray array = JSONArray.fromObject(sizeInfo);
					JSONObject object = null;
					Map<String,String> updateMap = null;
					
					for(int i = 0;i < array.size();i ++){
						object = array.getJSONObject(i);
						updateMap = new HashMap<>();
						updateMap.put("Dimension",(String)object.get("Dimension"));
						updateMap.put("GrossWeight", (String)object.get("GrossWeight"));
						updateMap.put("NetWeight", (String)object.get("NetWeight"));
						updateMap.put("Quantity", (String)object.get("Quantity"));
						
						sizeList.add(updateMap);
					}
				}
				
		
				List<Map<String, String>> goodsList = new ArrayList<>();
				if(!goodsInfo.equals("")){
					JSONArray array = JSONArray.fromObject(goodsInfo);
					JSONObject object = null;
					Map<String,String> updateMap = null;
					
					for(int i = 0;i < array.size();i ++){
						object = array.getJSONObject(i);
						updateMap = new HashMap<>();
						updateMap.put("GoodsDescription",(String)object.get("GoodsDescription"));
						updateMap.put("Model",(String)object.get("Model"));
						updateMap.put("Qty",(String)object.get("Qty"));
						goodsList.add(updateMap);
					}
				}
				
				String TotalGrossWeight = request.getParameter("TotalGrossWeight")==null?"":request.getParameter("TotalGrossWeight");
				String TotalNetWeight = request.getParameter("TotalNetWeight") == null?"":request.getParameter("TotalNetWeight");
		
				String Origin = request.getParameter("Origin") == null?"NA":request.getParameter("Origin");
				String ShippingMark = request.getParameter("ShippingMark") == null?"":request.getParameter("ShippingMark");
				String PackingCondition = request.getParameter("PackingCondition") == null?"NA":request.getParameter("PackingCondition");
			
				if(sizeList.size()>0){
					ArrayList<String[]> table = new ArrayList<>();
					table.add(new String[]{"1","2","3","4","5"});
					for(int i = 0;i < sizeList.size();i ++){
						table.add(new String[]{(i+1)+"",sizeList.get(i).get("Dimension").toString(),
								sizeList.get(i).get("GrossWeight").toString(),sizeList.get(i).get("NetWeight").toString(),
								sizeList.get(i).get("Quantity").toString()
						});
						
					}
				data.put("table$4@3", table);
				}
				data.put("${TotalGrossWeight}",TotalGrossWeight );
				data.put("${TotalNetWeight}", TotalNetWeight);
				
				HashMap<String, Object> data2 = new HashMap<>();
				if(goodsList.size()>0){
					ArrayList<String[]> table = new ArrayList<>();
					table.add(new String[]{"1"});
					for(int i  = 0;i < goodsList.size();i ++){
						StringBuilder sBuilder = new StringBuilder();
						sBuilder.append(goodsList.get(i).get("GoodsDescription"));
						sBuilder.append("\n");
						sBuilder.append("MODEL:  ");
						sBuilder.append(goodsList.get(i).get("Model"));
						sBuilder.append("\n");
						sBuilder.append("QTY:  ");
						sBuilder.append(goodsList.get(i).get("Qty"));
						table.add(new String[]{sBuilder.toString()});
					}
					int addRow = sizeList.size()==0?1:sizeList.size();
					data2.put("table$"+(7+addRow)+"@3", table);
					
				}else{
					ArrayList<String[]> table = new ArrayList<>();
					table.add(new String[]{"1"});
					table.add(new String[]{""});
					int addRow = sizeList.size()==0?1:sizeList.size();
					data2.put("table$"+(7+addRow)+"@3", table);
				}
				data.put("${Origin}", Origin.equals("")?"NA":Origin);
			
				data.put("${ShippingMark1}", ShippingMark.split("&&",2)[0]);
				data.put("${ShippingMark2}", ShippingMark.split("&&",2)[1]);
				data.put("${PackingCondition}", PackingCondition.equals("")?"NA":PackingCondition);
				data.put("${SignDate}", SignDate);
				Java2Word word = new Java2Word();
				String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\箱单尺寸-" + PackingListNO
						+ ".doc";
				//String downLoadUrl = "E:/LogisticsFile/File/箱单尺寸-" + PackingListNO
				//				+ ".doc";
				
				if(isExist.equals("true")){
					if(FromAPP.startsWith("HK")){
						word.toWord("E:/Model/PackingList-尺寸信息-HK.doc", downLoadUrl, data,"middle");
					}
					if(FromAPP.startsWith("苏州")){
						word.toWord("E:/Model/PackingList-尺寸信息-SZ.doc", downLoadUrl, data,"middle");
					}
				}else{
					word.toWord("E:/Model/PackingList-尺寸信息-无章.doc", downLoadUrl, data,"middle");
				}
				Java2Word word2 = new Java2Word();
				word2.toWord(downLoadUrl, downLoadUrl, data2,"middle");
				
				ConventerToPDFUtil.word2pdf(downLoadUrl);
				loadUrl = "down\\箱单尺寸-" + PackingListNO
						+ ".pdf";
				System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			}
			
		} finally {
			lock.unlock();// 释放锁
		}
		return loadUrl;
	}
	
}
