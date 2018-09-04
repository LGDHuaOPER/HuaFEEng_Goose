package com.eoulu.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.eoulu.constant.ModelClassification;
import com.eoulu.dao.QuoteSystemDao;

public class RadaDataUtil {
	public static void CalculateRadaData3(String startDate,String endDate) {

		long time1 = System.currentTimeMillis();
		
		QuoteSystemDao dao = new QuoteSystemDao();
		//删除所有的数据
		dao.deleteAll();
		

		//--------------型号的-------------
		//获取所有型号
		List<String> modelList = dao.getAllModel();
		
		Map<String,Double> priceVlues = dao.getUnitPriceValueByModel();
		List<String> ModelList = ModelClassification.getModelList();
		System.out.println(ModelList);
		for(String model:modelList) {
			boolean flag = false;
			for(String Model:ModelList) {
				flag = flag||Model.equals(model);
				if(flag) {
					break;
				}
			}
			if(flag) {
				continue;
			}
			double priceVlue = priceVlues.get(model);
			double ValueVlue = dao.getValueValueByModel(model,startDate,endDate);
			double ClientVlue = dao.getClientValueByModel(model,startDate,endDate);
			double SalesValue = dao.getSaleValuesByModel(model,startDate,endDate);
			double NumberValue = dao.getNumberValueByModel(model,startDate,endDate);
			String SalesValueString = dao.getSalesValueString(model,startDate,endDate);
			dao.insertRaderData(model,priceVlue,NumberValue,SalesValue,ClientVlue,ValueVlue,SalesValueString,dao.getCategoryByModel(model));
			
		}
		//特殊型号！
		System.out.println("----特殊型号------");
		Map<String,List<String>> ModelMap = ModelClassification.getModelMap();
		for(String model:ModelMap.keySet()) {
			double priceVlue = dao.getUnitPriceValueByModel(ModelMap.get(model));
			double ValueVlue = dao.getValueValueByModel(ModelMap.get(model),startDate,endDate);
			double ClientVlue = dao.getClientValueByModel(ModelMap.get(model),startDate,endDate);
			double SalesValue = dao.getSalesValueByModel(ModelMap.get(model),startDate,endDate);
			System.out.println(ModelMap.get(model));
			double NumberValue = dao.getNumberValueByModel(ModelMap.get(model),startDate,endDate);
			String SalesValueString = dao.getSalesValueString(ModelMap.get(model),startDate,endDate);
			dao.insertRaderData(model,priceVlue,NumberValue,SalesValue,ClientVlue,ValueVlue,SalesValueString,dao.getCategoryByModel(ModelMap.get(model).get(0)));
		}
		System.out.println("型号结束");
		//-------------类型的---------------
		//获取所有类型
		List<String> ProductCategoryList = dao.getAllProductCategory();
		//---单价的---
		for(String ProductCategory:ProductCategoryList) {
			if(ProductCategory==null||"其他".equals(ProductCategory)) {
				continue;                                                
			}
			double priceValueP = dao.getPriceValueByProductCategory(ProductCategory);
			double NumberValueP = dao.getNumberValueByProductCategory(ProductCategory,startDate,endDate);
			double SalesValueP = dao.getSalesValueByProductCategory(ProductCategory,startDate,endDate);
			double ClientValueP = dao.getClientValueByProductCategory(ProductCategory,startDate,endDate);
			double ValueValueP = dao.getValueValueByProductCategory(ProductCategory,startDate,endDate);
			String SalesValueString = dao.getSalesValueStringByProductCategory(ProductCategory,startDate,endDate);
			dao.insertRaderData(ProductCategory,priceValueP,NumberValueP,SalesValueP,ClientValueP,ValueValueP,SalesValueString,"All");
		}
		long time2 = System.currentTimeMillis();
		System.out.println("计算原始数据所用时间为:"+(time2-time1));
	
	}/**
	public static void CalculateRadaData() {
		long time1 = System.currentTimeMillis();
		
		QuoteSystemDao dao = new QuoteSystemDao();
		//删除所有的数据
		dao.deleteAll();
		long time2 = System.currentTimeMillis();
		System.out.println("删除所有数据："+(time2-time1));
		//--------------型号的-------------
		//获取所有型号
		List<String> modelList = dao.getAllModel();
		long time3 = System.currentTimeMillis();
		System.out.println("获取所有型号："+(time3-time2));
		Map<String,Double> SalesValues = dao.getSalesValuesByModel();
		Map<String,Double> priceVlues = dao.getUnitPriceValueByModel();
		Map<String,Double> ValueVlues = new HashMap<String,Double>();
		Map<String,Double> ClientVlues = new HashMap<String,Double>();
		double maxSales = 0.0;
		double minSales = 0.0;
		double maxprice = 0.0;
		double minprice = 0.0;
		double maxValue = 0.0;
		double minValue = 0.0;
		double maxClient = 0.0;
		double minClient = 0.0;
		List<String> ModelList = ModelClassification.getModelList();
		for(String model:modelList) {
			boolean flag = false;
			for(String Model:ModelList) {
				flag = flag||Model.toUpperCase().equals(model);
				if(flag) {
					break;
				}
			}
			if(flag) {
				continue;
			}
			double priceVlue = priceVlues.get(model);
			double ValueVlue = dao.getValueValueByModel(model);
			ValueVlues.put(model, ValueVlue);
			double ClientVlue = dao.getClientValueByModel(model);
			ClientVlues.put(model,ClientVlue);
			double SalesValue = SalesValues.get(model);
			
			maxSales = maxSales>SalesValue?maxSales:SalesValue;
			minSales = minSales<SalesValue?minSales:SalesValue;
			maxprice = maxprice>priceVlue?maxprice:priceVlue;
			minprice = minprice<priceVlue?minprice:priceVlue;
			maxValue = maxValue>ValueVlue?maxValue:ValueVlue;
			minValue = minValue<ValueVlue?minValue:ValueVlue;
			maxClient = maxClient>ClientVlue?maxClient:ClientVlue;
			minClient = minClient<ClientVlue?minClient:ClientVlue;
		}
		//特殊型号！
		Map<String,List<String>> ModelMap = ModelClassification.getModelMap();
		for(String model:ModelMap.keySet()) {
			double priceVlue = dao.getUnitPriceValueByModel(ModelMap.get(model));
			priceVlues.put(model, priceVlue);
			double ValueVlue = dao.getValueValueByModel(ModelMap.get(model));
			ValueVlues.put(model, ValueVlue);
			double ClientVlue = dao.getClientValueByModel(ModelMap.get(model));
			ClientVlues.put(model,ClientVlue);
			double SalesValue = dao.getSalesValueByModel(ModelMap.get(model));
			SalesValues.put(model,SalesValue);
			maxSales = maxSales>SalesValue?maxSales:SalesValue;
			minSales = minSales<SalesValue?minSales:SalesValue;
			maxprice = maxprice>priceVlue?maxprice:priceVlue;
			minprice = minprice<priceVlue?minprice:priceVlue;
			maxValue = maxValue>ValueVlue?maxValue:ValueVlue;
			minValue = minValue<ValueVlue?minValue:ValueVlue;
			maxClient = maxClient>ClientVlue?maxClient:ClientVlue;
			minClient = minClient<ClientVlue?minClient:ClientVlue;
		}
		//计算分数
		for(String model:modelList) {
			boolean flag = false;
			for(String Model:ModelList) {
				flag = flag||Model.toUpperCase().equals(model);
				if(flag) {
					break;
				}
			}
			if(flag) {
				continue;
			}
			double priceScore = maxprice-minprice==0?1:(priceVlues.get(model)-minprice)/(maxprice-minprice);
			double ValueScore = maxValue-minValue==0?1:(ValueVlues.get(model)-minValue)/(maxValue-minValue);
			double ClientScore = maxClient-minClient==0?1:(ClientVlues.get(model)-minClient)/(maxClient-minClient);
			double SalesScore = maxSales-minSales==0?1:(SalesValues.get(model)-minSales)/(maxSales-minSales);
			dao.insetIntoRadar(model,priceScore,1,ValueScore,ClientScore,SalesScore);
		}
		for(String model:ModelMap.keySet()) {
			double priceScore = maxprice-minprice==0?1:(priceVlues.get(model)-minprice)/(maxprice-minprice);
			double ValueScore = maxValue-minValue==0?1:(ValueVlues.get(model)-minValue)/(maxValue-minValue);
			double ClientScore = maxClient-minClient==0?1:(ClientVlues.get(model)-minClient)/(maxClient-minClient);
			double SalesScore = maxSales-minSales==0?1:(SalesValues.get(model)-minSales)/(maxSales-minSales);
			dao.insetIntoRadar(model,priceScore,1,ValueScore,ClientScore,SalesScore);
		}
		System.out.println("型号结束");
		//-------------类型的---------------
		//获取所有类型
		List<String> ProductCategoryList = dao.getAllProductCategory();
		//---单价的---
		Map<String,Double> priceValuesP = new HashMap<String, Double>();
		Map<String,Double> NumberValuesP = new HashMap<String, Double>();
		Map<String,Double> SalesValuesP = new HashMap<String, Double>();
		Map<String,Double> ClientValuesP = new HashMap<String, Double>();
		Map<String,Double> ValueValuesP = new HashMap<String, Double>();
		double maxSalesP = 0.0;
		double minSalesP = 0.0;
		double maxNumberP = 0.0;
		double minNumberP = 0.0;
		double maxpriceP = 0.0;
		double minpriceP = 0.0;
		double maxValueP = 0.0;
		double minValueP = 0.0;
		double maxClientP = 0.0;
		double minClientP = 0.0;
		for(String ProductCategory:ProductCategoryList) {
			if(ProductCategory==null||"其他".equals(ProductCategory)) {
				continue;                                                
			}
			double priceValueP = dao.getPriceValueByProductCategory(ProductCategory);
			double NumberValueP = dao.getNumberValueByProductCategory(ProductCategory);
			double SalesValueP = dao.getSalesValueByProductCategory(ProductCategory);
			double ClientValueP = dao.getClientValueByProductCategory(ProductCategory);
			double ValueValueP = dao.getValueValueByProductCategory(ProductCategory);
			priceValuesP.put(ProductCategory,priceValueP);
			NumberValuesP.put(ProductCategory,NumberValueP);
			SalesValuesP.put(ProductCategory,SalesValueP);
			ClientValuesP.put(ProductCategory,ClientValueP);
			ValueValuesP.put(ProductCategory,ValueValueP);
			maxSalesP = maxSalesP>SalesValueP?maxSalesP:SalesValueP;
			minSalesP = minSalesP<SalesValueP?minSalesP:SalesValueP;
			maxpriceP = maxpriceP>priceValueP?maxpriceP:priceValueP;
			minpriceP = minpriceP<priceValueP?minpriceP:priceValueP;
			maxValueP = maxValueP>ValueValueP?maxValueP:ValueValueP;
			minValueP = minValueP<ValueValueP?minValueP:ValueValueP;
			maxClientP = maxClientP>ClientValueP?maxClientP:ClientValueP;
			minClientP = minClientP<ClientValueP?minClientP:ClientValueP;
			maxNumberP = maxNumberP>NumberValueP?maxNumberP:NumberValueP;
			minNumberP = minNumberP<NumberValueP?minNumberP:NumberValueP;
		}
		//计算分数
		for(String productCategory:ProductCategoryList) {
			if(productCategory==null||"其他".equals(productCategory)) {
				continue;
			}
			//单价分数
			double priceScoreP = maxpriceP-minpriceP==0?1:1-(priceValuesP.get(productCategory)-minpriceP)/(maxpriceP-minpriceP);
			//数量
			double NumberScoreP = maxNumberP-minNumberP==0?1:(NumberValuesP.get(productCategory)-minNumberP)/(maxNumberP-minNumberP);
			//价值当量分数
			double ValueScoreP = maxValueP-minValueP==0?1:(ValueValuesP.get(productCategory)-minValueP)/(maxValueP-minValueP);
			//客户分布分数
			double ClientScoreP = maxClientP-minClientP==0?1:(ClientValuesP.get(productCategory)-minClientP)/(maxClientP-minClientP);
			//月销量分数
			double SalesScoreP = maxSalesP-minSalesP==0?1:(SalesValuesP.get(productCategory)-minSalesP)/(maxSalesP-minSalesP);
			dao.insetIntoRadar(productCategory,priceScoreP,NumberScoreP,ValueScoreP,ClientScoreP,SalesScoreP);
		}
		System.out.println("结束");
		System.out.println(System.currentTimeMillis()-time1);
	}
	
	public static void CalculateRadaData2() {
		long time1 = System.currentTimeMillis();
		
		QuoteSystemDao dao = new QuoteSystemDao();
		//删除所有的数据
		dao.deleteAll();
		System.out.println("shanchujieshu");
		//--------------型号的-------------
		//获取所有型号
		List<String> modelList = dao.getAllModel();
		//---单价的---
		double priceAvg = dao.getUnitPriceAvgByModel();
		double priceStdev = dao.getUnitPriceStdevByModel();
		//---数量的---
		//所有型号的数量都为0.5，不再保存
		//---价值当量---
		double ValueAvg = dao.getValueAvgByModel();
		double ValueStdev = dao.getValueStdevByModel();
		//---客户分散---
		double ClientAvg = dao.getClientAvgByModel();
		double ClientStdev = dao.getClientStdevByModel();
		//---月销量---
		Map<String,Double> SalesValues = dao.getSalesValuesByModel();
		
		double sum = 0;
		for(Double value:SalesValues.values()) {
			sum+=value==null?0:value;
		}
		double SalesAvg = sum/SalesValues.size();
		double varianceSum = 0;
		for(Double value:SalesValues.values()) {
			value=value==null?0:value;
			varianceSum+=(value-SalesAvg)*(value-SalesAvg);
		}
		double variance = varianceSum/SalesValues.size();
		double SalesStdev=Math.sqrt(variance);
		//计算分数
		for(String model:modelList) {
			double priceVlue = dao.getUnitPriceValueByModel(model);	
			double ValueVlue = dao.getValueValueByModel(model);	
			double ClientVlue = dao.getClientValueByModel(model);	
			//单价分数
			double priceScore = priceStdev==0?0.5:(priceVlue-priceAvg)/priceStdev;
			priceScore = priceScore>0?priceScore:0-priceScore;
//			//价值当量分数
			double ValueScore = ValueStdev==0?0.5:(ValueVlue-ValueAvg)/ValueStdev;
			ValueScore = ValueScore>0?ValueScore:0-ValueScore;
//			//客户分布分数
			double ClientScore = ClientStdev==0?0.5:(ClientVlue-ClientAvg)/ClientStdev;
			ClientScore = ClientScore>0?ClientScore:0-ClientScore;
//			//月销量分数
			double SalesScore = SalesStdev==0?0.5:(SalesValues.get(model)-SalesAvg)/SalesStdev;
			SalesScore = SalesScore>0?SalesScore:0-SalesScore;
			dao.insetIntoRadar(model,priceScore,0.5,ValueScore,ClientScore,SalesScore);
		}
		System.out.println("型号结束");
		//-------------类型的---------------
		//获取所有类型
		List<String> ProductCategoryList = dao.getAllProductCategory();
		//---单价的---
		double priceAvgP = dao.getUnitPriceAvgByProductCategor();
		double priceStdevP = dao.getUnitPriceStdevByProductCategor();
		Map<String,Double> priceValuesP = new HashMap<String, Double>();
		Map<String,Double> NumberValuesP = new HashMap<String, Double>();
		Map<String,Double> SalesValuesP = new HashMap<String, Double>();
		Map<String,Double> ClientValuesP = new HashMap<String, Double>();
		Map<String,Double> ValueValuesP = new HashMap<String, Double>();
		for(String ProductCategory:ProductCategoryList) {
			if(ProductCategory==null) {
				continue;
			}
			priceValuesP.put(ProductCategory,dao.getPriceValueByProductCategory(ProductCategory));
			NumberValuesP.put(ProductCategory,dao.getNumberValueByProductCategory(ProductCategory));
			SalesValuesP.put(ProductCategory,dao.getSalesValueByProductCategory(ProductCategory));
			ClientValuesP.put(ProductCategory,dao.getClientValueByProductCategory(ProductCategory));
			ValueValuesP.put(ProductCategory,dao.getValueValueByProductCategory(ProductCategory));
		}
		//---数量的---
		double NumberAvgP = dao.getNumberAvgByProductCategor();
		double NumberStdevP =dao.getNumberStdevPByProductCategor(); ;
		//---价值当量---
		
		double ValueAvgP = 0.0;
		for(Double value:ValueValuesP.values()){
			ValueAvgP+=value==null?0:value;
		}
		ValueAvgP = ValueAvgP/ValueValuesP.size();
		double ValueStdevP = 0.0;
		for(Double value:ValueValuesP.values()) {
			value=value==null?0:value;
			ValueStdevP+=(value-ValueStdevP)*(value-ValueStdevP);
		}
		ValueStdevP = ValueStdevP/ValueValuesP.size();
		ValueStdevP = Math.sqrt(ValueStdevP);
		//---客户分散---
		double ClientAvgP = 0.0;
		double ClientStdevP = 0.0;
		for(Double value:ClientValuesP.values()){
			ClientAvgP+=value==null?0:value;
		}
		ClientAvgP = ClientAvgP/ClientValuesP.size();
		for(Double value:ClientValuesP.values()) {
			value=value==null?0:value;
			ClientStdevP+=(value-ClientAvgP)*(value-ClientAvgP);
		}
		ClientStdevP = ClientStdevP/ClientValuesP.size();
		ClientStdevP = Math.sqrt(ClientStdevP);
		//---月销量---
		double sumP = 0;
		System.out.println(SalesValuesP);
		for(Double value:SalesValuesP.values()) {
			
			sumP+=value==null?0:value;
		}
		double SalesAvgP = sumP/SalesValuesP.size();
		double varianceSumP = 0;
		for(Double value:SalesValuesP.values()) {
			value=value==null?0:value;
			varianceSumP+=(value-SalesAvgP)*(value-SalesAvgP);
		}
		double varianceP = varianceSumP/SalesValuesP.size();
		double SalesStdevP=Math.sqrt(varianceP);
		//计算分数
		for(String ProductCategory:ProductCategoryList) {
			if(ProductCategory==null) {
				continue;
			}
			//单价分数
			double priceScoreP = priceStdevP==0?0.5:(priceValuesP.get(ProductCategory)-priceAvgP)/priceStdevP;
			priceScoreP = priceScoreP>0?priceScoreP:0-priceScoreP;
			//数量
			double NumberScoreP = NumberStdevP==0?0.5:(ValueValuesP.get(ProductCategory)-NumberAvgP)/NumberStdevP;
			NumberScoreP = NumberScoreP>0?NumberScoreP:0-NumberScoreP;
			//价值当量分数
			double ValueScoreP = ValueStdevP==0?0.5:(ValueValuesP.get(ProductCategory)-ValueAvgP)/ValueStdevP;
			ValueScoreP = ValueScoreP>0?ValueScoreP:0-ValueScoreP;
			//客户分布分数
			double ClientScoreP = ClientStdevP==0?0.5:(ClientValuesP.get(ProductCategory)-ClientAvgP)/ClientStdevP;
			ClientScoreP = ClientScoreP>0?ClientScoreP:0-ClientScoreP;
			//月销量分数
			double SalesScoreP = SalesStdevP==0?0.5:(SalesValuesP.get(ProductCategory)-SalesAvgP)/SalesStdevP;
			SalesScoreP = SalesScoreP>0?SalesScoreP:0-SalesScoreP;
			dao.insetIntoRadar(ProductCategory,priceScoreP,NumberScoreP,ValueScoreP,ClientScoreP,SalesScoreP);
		}
		System.out.println("结束");
		System.out.println(System.currentTimeMillis()-time1);
	}  */
	public static void main(String[] args) {
		Properties props = new Properties();  
        try {
			props.load(SendMailUtil.class.getResourceAsStream("rada.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String startTime = props.getProperty("startTime");
        String endTime = props.getProperty("endTime");
        if(endTime.equals("")){
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	endTime = simpleDateFormat.format(new Date());
        }
		RadaDataUtil.CalculateRadaData3(startTime,endTime);	
	}

}
