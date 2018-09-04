package com.eoulu.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.dao.QuoteSystemDao;

public class ModelClassification {

	public static Map<String,List<String>> getModelMap(){
		
		List<Map<String, Object>> list = new QuoteSystemDao().GetModelByCategory("探针台");
		Map<String,List<String>> ModelMap = new HashMap<String,List<String>>();
		List<String> ModelList = null;
		String classify = "";
		for(int i=1;i<list.size();i++) {
			String model = list.get(i).get("Model").toString().trim();
			String temp = model.replaceAll(" ", "");
			if(temp.toUpperCase().startsWith("1495".toUpperCase())){
				classify = "PA300PS-MA";
			}
			else if(temp.toUpperCase().startsWith("CM300-F".toUpperCase())){
				classify = "CM300-F";
			}
			else if(temp.toUpperCase().startsWith("CM300-O".toUpperCase())){
				classify = "CM300-O";
			}
			else if(temp.toUpperCase().startsWith("CM300-S".toUpperCase())){
				classify = "CM300-S";
			}
			else if(temp.toUpperCase().startsWith("ELITE300/AP".toUpperCase())){
				classify = "ELITE 300/AP";
			}
			else if(temp.toUpperCase().startsWith("ELITE300/M".toUpperCase())){
				classify = "ELITE 300/M";
			}
			else if(temp.toUpperCase().startsWith("SPS200TESLA".toUpperCase())){
				classify = "SPS200TESLA";
			}
			else if(temp.toUpperCase().startsWith("TESLA300/AP".toUpperCase())){
				classify = "TESLA 300/AP";
			}
			else if(temp.toUpperCase().startsWith("TESLA300/M".toUpperCase())){
				classify = "TESLA 300/M";
			}
			else if(temp.toUpperCase().startsWith("APS200TESLA".toUpperCase())){
				classify = "APS200TESLA";
			}
			else if(temp.toUpperCase().startsWith("PA200-BR".toUpperCase())){
				classify = "PA200-BR";
			}
			else if(temp.toUpperCase().startsWith("PA200DS-BR".toUpperCase())){
				classify = "PA200DS-BR";
			}
			else if(temp.toUpperCase().startsWith("PM8-COAX".toUpperCase())){
				classify = "PM8-COAX";
			}
			else if(temp.toUpperCase().startsWith("EPS150RF".toUpperCase())||temp.toUpperCase().startsWith("EPS150LT".toUpperCase())){
				classify = "EPS150RF";
			}
			else if(temp.toUpperCase().startsWith("EPS150COAX".toUpperCase())||temp.toUpperCase().startsWith("MPS150".toUpperCase())
					||temp.toUpperCase().startsWith("ES-150-PACK".toUpperCase())||temp.toUpperCase().startsWith("M150".toUpperCase())){
				classify = "EPS150COAX";
			}
			else if(temp.toUpperCase().startsWith("PLV50".toUpperCase())){
				classify = "PLV50";
			}
			else if(temp.toUpperCase().startsWith("PMV200".toUpperCase())){
				classify = "PMV200";
			}else{
				continue;
			}		

			if(ModelMap.containsKey(classify)){	
				ModelList = ModelMap.get(classify);
			}else{
				ModelList = new ArrayList<String>();	
			}
			ModelList.add(model);
			ModelMap.put(classify, ModelList);
		}		
		return ModelMap;
	}
	public static void main(String[] args) {
		//System.out.println(ModelClassification.getClassifyMap());
		//System.out.println(ModelClassification.getClassifyMap().size());
		
	 
		Map<String,List<String>> ModelMap = ModelClassification.getModelMap();
		
		
	}
	public static List<String> getModelList(){
		List<String> ModelList = new ArrayList<String>();
		Map<String,List<String>> ModelMap = ModelClassification.getModelMap();
		for(String classify:ModelMap.keySet()) {
			ModelList.addAll(ModelMap.get(classify)); //需要归类的型号名
		}
		return ModelList;
	}
	public static Map<String,String> getClassifyMap(){
		Map<String,List<String>> ModelMap = ModelClassification.getModelMap();
		Map<String,String> ClassifyMap = new HashMap<String,String>();
		for(String classify:ModelMap.keySet()) {
			for(String model:ModelMap.get(classify)){
				ClassifyMap.put(model, classify);                    
			}
		}
		return ClassifyMap;
	}
	
	public static String getEncoding(String str) {  
	    String encode[] = new String[]{  
	            "UTF-8",  
	            "ISO-8859-1",  
	            "GB2312",  
	            "GBK",  
	            "GB18030",  
	            "Big5",  
	            "Unicode",  
	            "ASCII"  
	    };  
	    for (int i = 0; i < encode.length; i++){  
	        try {  
	            if (str.equals(new String(str.getBytes(encode[i]), encode[i]))) {  
	                return encode[i];  
	            }  
	        } catch (Exception ex) {  
	        }  
	    }  
	      
	    return "";  
	}  
	
}
