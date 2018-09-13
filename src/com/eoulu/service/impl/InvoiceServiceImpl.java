package com.eoulu.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.InvoiceDao;
import com.eoulu.dao.OrderDao;
import com.eoulu.entity.CompeteInvoice;
import com.eoulu.entity.Invoice;
import com.eoulu.entity.Item;
import com.eoulu.service.InvoiceService;
import com.eoulu.util.DBUtil;

public class InvoiceServiceImpl implements InvoiceService{

	/**
	 * 常量键值对
	 * */
	public static final Map<String, Object> classify_MAP; 


	static{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "CustomerName");
		map.put("合同号", "ContractNO");
		map.put("PO", "PONO");
		map.put("发票号", "InvoiceNO");
		map.put("货物名称", "Product");
		map.put("信用证号", "DCNO");
		map.put("Applicant", "Applicant");
		map.put("最终用户", "EndUser");
		map.put("其他供参考", "OtherReference");
		map.put("出发日期", "DepartureDate");
		map.put("出发地", "Departure");
		map.put("目的地", "Destination");
		map.put("付款方式", "PaymentRemark");
		map.put("包装", "Packing");
		map.put("货源地", "Origin");
		map.put("制造商", "Manufacturer");
		map.put("运输标志", "ShippingMark");
		map.put("机场", "AirPort");
		map.put("发票类型", "Type");//int
		map.put("航班", "Vessel");
		map.put("发票签订时间", "Date");
		map.put("添加信息", "AddInfo");
		map.put("电话传真", "TelFax");
		map.put("总费用", "TotalAmount");//int
		map.put("90%金额", "NinePaid");//int
		map.put("10%金额", "TenPaid");//int
	
		classify_MAP = map;
	}
	
	@Override
	public List<Map<String, Object>> getAllInvoice(int ID) {
		System.out.println(new InvoiceDao().getAllInvoice(ID));
		return new InvoiceDao().getAllInvoice(ID);
	}

	@Override
	public int getAllcounts() {
		
		return new InvoiceDao().getAllCounts();
	}


	@Override
	public List<Map<String, Object>> getInvoiceByPageInOne(String classify, Object parameter, Page page) {
		String sql=null;
		Object[] obj = null;
	
		//生成参数数组
		switch(classify_MAP.get(classify).toString()){
		case "TotalAmount":obj = new Object[1];obj[0]=parameter;
		case "NinePaid":obj = new Object[1];obj[0]=parameter;
		case "TenPaid":obj = new Object[1];obj[0]=parameter;
		case "Type":obj = new Object[1];obj[0]=parameter;break;
		default:obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		System.out.println("obj多长啊："+obj.length);
		//构建sql语句
		int length = obj.length;                 
		

		sql = "select A.Customer CustomerName,A.ContractTitle,t_invoice.ID,t_invoice.AddInfo,t_invoice.AirPort,t_invoice.Applicant,"
				+ "t_invoice.ContractNO,t_invoice.Date,t_invoice.DCNO,t_invoice.Departure,"
				+ "t_invoice.DepartureDate,t_invoice.Destination,t_invoice.EndUser,t_invoice.InvoiceNO,"
				+ "t_invoice.Manufacturer,t_invoice.NinePaid,t_invoice.OperatingTime,t_invoice.Origin,"
				+ "t_invoice.OtherReference,t_invoice.Packing,t_invoice.PaymentRemark,t_invoice.PONO,"
				+ "t_invoice.Product,t_invoice.ShippingMark,t_invoice.TelFax,t_invoice.TenPaid,t_invoice.TotalAmount,"
				+ "t_invoice.Type,t_invoice.Vessel from t_invoice "
				+ " left join t_order A on t_invoice.ContractNO=A.ContractNo where ";
		if(classify.equals("发票类型")||classify.equals("总费用")||classify.equals("90%金额")||classify.equals("10%金额")){
			sql+="t_invoice."+classify_MAP.get(classify)+" = ?";
		}else if(classify.equals("合同名称") || classify.equals("客户名称")){
			sql+="A."+classify_MAP.get(classify)+" like ?";
		}
		else{
			for(int i=0; i<length; i++){
				sql+="t_invoice."+classify_MAP.get(classify)+" like ?";
				if(i<length-1){
					sql+=" or ";
				}
			}
		}
		
		sql+=" order by t_invoice.Date limit ?,?";
		
		//构建带有分页信息的参数数组
		Object[] param = null;
		if(length == 0){
			param = new Object[obj.length+3];
			param[length]=0;
			param[length+1]=(page.getCurrentPage()-1)*page.getRows();
			param[length+2]=page.getRows();
		}else{
			param = new Object[obj.length+2];
			for(int i=0; i<length; i++){
				param[i]=obj[i];
			}
			
			param[length]=(page.getCurrentPage()-1)*page.getRows();
			param[length+1]=page.getRows();
		}
		//System.out.println(param.length);
       System.out.println("sql语句:"+Arrays.toString(param));
		return new InvoiceDao().getInvoice(sql, param);
		
	}

	@Override
	public List<Map<String, Object>> queryResult(List<Map<String, Object>> queryList) {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> getInvoiceByTime(String classify, String start_time1, String end_time1,
			Page page) {
		String sql=null;
		Object[] obj = new Object[2];

		//生成参数数组
		 obj[0]=start_time1;
		 obj[1]=end_time1;

		int length = obj.length;
		sql = "select A.ContractTitle,A.Customer CustomerName,t_invoice.ID,t_invoice.ContractNO,t_invoice.InvoiceNO,t_invoice.PONO,t_invoice.DCNO,t_invoice.Applicant, "
				+ "t_invoice.EndUser,t_invoice.OtherReference,t_invoice.DepartureDate,t_invoice.Vessel,t_invoice.Departure,"
				+ "t_invoice.Destination,t_invoice.PaymentRemark,t_invoice.Packing,t_invoice.Origin,t_invoice.Manufacturer,"
				+ "t_invoice.ShippingMark,t_invoice.OperatingTime,t_invoice.Type,t_invoice.AirPort,t_invoice.Date,t_item.Goods,"
				+ "t_item.Qty,t_item.TotalUSDAmount,t_item.Unit,t_item.UnitUSDPrice from t_invoice left join t_item on t_invoice.ID=t_item.InvoiceID "
				+ " left join t_order A on t_invoice.ContractNO=A.ContractNo ";
				
		Object[] param;
		if(!"".equals(start_time1) && !"".equals(end_time1)){
			sql+=" where t_invoice."+classify_MAP.get(classify)+" between ? and ?";

				sql+=" order by t_invoice.Date desc limit ?,?";

			
			//构建带有分页信息的参数数组
			param= new Object[obj.length+2];
			for(int i=0; i<length; i++){
				param[i]=obj[i];
			}
			param[length]=(page.getCurrentPage()-1)*page.getRows();
			param[length+1]=page.getRows();
		}else{

				sql+=" order by t_invoice.Date desc limit ?,?";

			param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
			
		}

		return new InvoiceDao().getInvoice(sql, param);
	}

	@Override
	public int getCountByClassify(String classify, Object parameter) {
		String sql="";
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		int length = obj.length;
		sql = "select count(t_invoice.ID) from t_invoice ";
		for(int i=0; i<length; i++){
			if(classify.equals("客户名称")){

				sql += " left join t_order A on t_invoice.ContractNO=A.ContractNo "
						
						+ "where A."+classify_MAP.get(classify)+" like ?";
			}else if(classify.equals("合同名称")){

				sql += " left join t_order A on t_invoice.ContractNO=A.ContractNo "
						
						+ "where A."+classify_MAP.get(classify)+" like ?";
			}
			else{

				sql+="where t_invoice."+classify_MAP.get(classify)+" like ?";
			}
			if(i<length-1){
				sql+=" or ";
			}
		}

		return new InvoiceDao().getCountsByName(sql, obj);
	}

	@Override
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1) {
		String sql=null;
		Object[] obj ;
		sql = "select count(t_invoice.ID) from t_invoice  ";
		if(!"".equals(start_time1) && !"".equals(end_time1)){
			obj = new Object[2];
			//生成参数数组
			 obj[0]=start_time1;
			 obj[1]=end_time1;
			sql+="where "+classify_MAP.get(classify)+" between ? and ?";
		}else{
			obj=null;
		}
		return new InvoiceDao().getCountsByName(sql, obj);
		
	}

	
	@Override
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,
			Map<String, Object> map2) {
		//System.out.println(map2);
		String sql1=null;
		Object[] obj1 = null;
	
		switch(classify_MAP.get(classify1).toString()){
		case "DepartureDate":obj1=new Object[2]; obj1[0]=map1.get("1").toString();obj1[1]=map1.get("2").toString();
		case "Date": obj1=new Object[2]; obj1[0]=map1.get("1").toString();obj1[1]=map1.get("2").toString();break;
		default: obj1=new Object[1]; obj1[0]="%"+map1.get("1").toString()+"%";
		}
		int length11 = map1.size();
		
		if(length11==1){
			sql1 = "select count(ID) from t_invoice  where ";
			for(int i=0; i<obj1.length; i++){
				sql1+="t_invoice."+classify_MAP.get(classify1)+" like ?";
				if(i<obj1.length-1){
					sql1+=" or ";
				}
			}
			sql1+=" and ";
		}else if(length11==2){
			if(!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())){
				sql1 = "select count(ID) from t_invoice  where ";
				sql1+="t_invoice."+classify_MAP.get(classify1)+" between ? and ?";
				sql1+=" and ";
			}else{
				sql1 = "select count(ID) from t_invoice where ";
				obj1=null;
			}
		}
	//后半句sql语句构建
		String sql2=null;
		Object[] obj2 = null;

		switch(classify_MAP.get(classify2).toString()){
		case "DepartureDate":obj2=new Object[2]; obj2[0]=map2.get("1").toString();obj2[1]=map2.get("2").toString();
		case "Date": obj2=new Object[2]; obj2[0]=map2.get("1").toString();obj2[1]=map2.get("2").toString();break;
		default: obj2=new Object[1]; obj2[0]="%"+map2.get("1").toString()+"%";
		}
		int length22 = map2.size();
		
		if(length22==1){
			sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
			for(int i=0; i<obj2.length; i++){
				sql2+="t_invoice."+classify_MAP.get(classify2)+" like ?";
				if(i<obj2.length-1)
					sql2+=" or ";
			}
		}else if(length22==2){
			if(!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())){
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
				sql2+="where t_invoice."+classify_MAP.get(classify2)+" between ? and ?";
			}else{
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice ";
				obj2=null;
			}
		}
		String sql = sql1 +"t_invoice."+classify_MAP.get(classify2)+" in(" + sql2+")";
		int length1;
		int length2; 
		int allCounts=0;
		if(obj1!=null && obj2!=null ){
			//System.out.println(111);
			allCounts=obj1.length+obj2.length;
			length1=obj1.length;
			length2=obj2.length;
		}else if(obj1==null && obj2!=null){
			//System.out.println(222);
			allCounts = obj2.length;
			length1=0;
			length2=obj2.length;
		}else if(obj2==null && obj1!=null){
			//System.out.println(333);
			allCounts = obj1.length;
			length2=0;
			length1=obj1.length;
		}else{
			//System.out.println(444);
			allCounts=0;
			length1=0;
			length2=0;
		}
		Object[] sqlObj = new Object[allCounts];
		int times = 0;
		//System.out.println("allCounts:"+allCounts);
		
		for(int i=0; i<length1; i++){
			//System.out.println("cishu:"+times++);
			sqlObj[i]=obj1[i];
		}
		for(int i=0; i<length2; i++){
			//System.out.println(obj2[i]);
			sqlObj[length1]=obj2[i];
		}
		//System.out.println(Arrays.toString(sqlObj));
		return new InvoiceDao().getCountsByName(sql, sqlObj);
	}

	
	@Override
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2) {
		
				String sql1=null;
				Object[] obj1 = null;

				switch(classify_MAP.get(classify1).toString()){
				
				default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
				}
				int length1 = obj1.length;
				sql1 = "select count(t_invoice.ID) from t_invoice left join t_order A on t_invoice.ContractNO=A.ContractNo where  ";
				if(classify1.equals("发票类型")||classify1.equals("总费用")||classify1.equals("90%金额")||classify1.equals("10%金额")){
					sql1+="t_invoice."+classify_MAP.get(classify1)+" = ?";
				}else if(classify1.equals("合同名称") || classify1.equals("客户名称")){
					sql1+="A."+classify_MAP.get(classify1)+" like ?";
				}else{
					for(int i=0; i<length1; i++){
						sql1+="t_invoice."+classify_MAP.get(classify1)+" like ?";
						if(i<length1-1)
							sql1+=" or ";
					}
				}
				

				//后半句sql语句构建
				String sql2="";
				Object[] obj2 = null;

				switch(classify_MAP.get(classify2).toString()){
				
				default:obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
				}
				int length2 = obj2.length;
				//sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
				if(classify2.equals("发票类型")||classify2.equals("总费用")||classify2.equals("90%金额")||classify2.equals("10%金额")){
					sql2+="t_invoice."+classify_MAP.get(classify2)+" = ?";
				}else if(classify2.equals("合同名称") || classify2.equals("客户名称")){
					sql2+="A."+classify_MAP.get(classify2)+" like ?";
				}else{
					for(int i=0; i<length2; i++){
						sql2+="t_invoice."+classify_MAP.get(classify2)+" like ?";
						if(i<length2-1)
							sql2+=" or ";
					}
				}
				//System.out.println("etst:2:"+sql2);
				String sql = sql1 +" and "+sql2;
//				String sql = sql1 +" and t_invoice."+classify_MAP.get(classify2)+" in(" + sql2+")";
				int allCounts = obj1.length+obj2.length;
				Object[] sqlObj = new Object[allCounts];
				int times = 0;
				for(int i=0; i<length1; i++){
					sqlObj[times++]=obj1[i];
				}
				for(int i=0; i<length2; i++){
					sqlObj[times++]=obj2[i];
				}

				return new InvoiceDao().getCountsByName(sql, sqlObj);
	}

	
	@Override
	public List<Map<String, Object>> getInvoiceByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page) {
		String sql1=null;
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+map1.get("1").toString()+"%";
		}
		sql1 = "selectA.ContractTitle,A.Customer CustomerName,t_invoice.ID,t_invoice.ContractNO,t_invoice.InvoiceNO,t_invoice.PONO,t_invoice.DCNO,t_invoice.Applicant, "
				+ "t_invoice.EndUser,t_invoice.OtherReference,t_invoice.DepartureDate,t_invoice.Vessel,t_invoice.Departure,"
				+ "t_invoice.Destination,t_invoice.PaymentRemark,t_invoice.Packing,t_invoice.Origin,t_invoice.Manufacturer,"
				+ "t_invoice.ShippingMark,t_invoice.OperatingTime,t_invoice.Type,t_invoice.AirPort,t_invoice.Date,"
				+ " from t_invoice left join t_order A on t_invoice.ContractNO=A.ContractNo where  ";
		int length1 = map1.size();
		if(length1==1){
			sql1+="where ";
			for(int i=0; i<obj1.length; i++){
				sql1+="t_invoice."+classify_MAP.get(classify1)+" like ?";
				if(i<obj1.length-1)
					sql1+=" or ";
			}
		}else if(length1==2){
			if(!"".equals(map1.get("1").toString()) && !"".equals(map1.get("2").toString())){
				sql1+="where t_invoice.";
				sql1+=classify_MAP.get(classify1)+" between ? and ?";
			}else{
				obj1=null;
			}
		}
	
		//后半句sql语句构建
		String sql2=null;
		Object[] obj2 = null;

		switch(classify_MAP.get(classify2).toString()){
		
		default: obj2=new Object[1]; obj2[0]="%"+map2.get("1").toString()+"%";
		}
		int length2 = map2.size();
		
		if(length2==1){
			sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
			for(int i=0; i<obj2.length; i++){
				sql2+="t_invoice."+classify_MAP.get(classify2)+" like ?";
				if(i<obj2.length-1)
					sql2+=" or ";
			}
		}else if(length2==2){
			if(!"".equals(map2.get("1").toString()) && !"".equals(map2.get("2").toString())){
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice where ";
				sql2+="t_invoice."+classify_MAP.get(classify2)+" between ? and ?";
			}else{
				sql2 = "select t_invoice."+classify_MAP.get(classify2)+" from t_invoice ";
				obj2=null;
			}
		}

		String sql = sql1 +"  t_invoice."+classify_MAP.get(classify2)+" in(" + sql2+")";
		
		int allCounts = 0;
		if(obj1==null && obj2!=null){
			allCounts = obj2.length;
			length2=obj2.length;
			length1=0;
		}else if(obj2==null && obj1!=null){
			allCounts = obj1.length;
			length1=obj1.length;
			length2=0;
		}else if(obj1!=null && obj2!=null ){
			allCounts=obj1.length+obj2.length;
			length1=obj1.length;
			length2=obj2.length;
		}else{
			allCounts=0;
			length1=0;
			length2=0;
		}
		Object[] sqlObj = new Object[allCounts+2];
		int times = 0;
		for(int i=0; i<length1; i++){
			sqlObj[times++]=obj1[i];
		}
		for(int i=0; i<length2; i++){
			sqlObj[times++]=obj2[i];
		}
		sql+=" order by t_invoice.Date desc limit ?,?";
		sqlObj[allCounts]=(page.getCurrentPage()-1)*page.getRows();
		sqlObj[allCounts+1]=page.getRows();
		return new OrderDao().getOrder(sql, sqlObj);
	}


	@Override
	public List<Map<String, Object>> getInvoiceByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		String sql1=null;
		Object[] obj1 = null;
		Object[] sqlObj = null;//参数
		String sql = null;//整句

		switch(classify_MAP.get(classify1).toString()){
		case "TotalAmount":obj1 = new Object[1];obj1[0]=parameter1;
		case "NinePaid":obj1 = new Object[1];obj1[0]=parameter1;
		case "TenPaid":obj1 = new Object[1];obj1[0]=parameter1;
		case "Type":obj1 = new Object[1];obj1[0]=parameter1;break;
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		int length1 = obj1.length;
		sql1 = "select A.Customer CustomerName,A.ContractTitle,t_invoice.ID,t_invoice.AddInfo,t_invoice.AirPort,t_invoice.Applicant,"
				+ "t_invoice.ContractNO,t_invoice.Date,t_invoice.DCNO,t_invoice.Departure,"
				+ "t_invoice.DepartureDate,t_invoice.Destination,t_invoice.EndUser,t_invoice.InvoiceNO,"
				+ "t_invoice.Manufacturer,t_invoice.NinePaid,t_invoice.OperatingTime,t_invoice.Origin,"
				+ "t_invoice.OtherReference,t_invoice.Packing,t_invoice.PaymentRemark,t_invoice.PONO,"
				+ "t_invoice.Product,t_invoice.ShippingMark,t_invoice.TelFax,t_invoice.TenPaid,t_invoice.TotalAmount,"
				+ "t_invoice.Type,t_invoice.Vessel from t_invoice "
				+ " left join t_order A on t_invoice.ContractNO=A.ContractNo where ";

		

//		if(length1 == 0){
//			sql1+="t_invoice."+classify_MAP.get(classify1)+" = ?";
//		}
		if(classify1.equals("发票类型")||classify1.equals("总费用")||classify1.equals("90%金额")||classify1.equals("10%金额")){

			sql1+="t_invoice."+classify_MAP.get(classify1)+" = ?";
		}else if(classify1.equals("合同名称") || classify1.equals("客户名称")){
			sql1+="A."+classify_MAP.get(classify1)+" like ?";
		}else{

			for(int i=0; i<length1; i++){
				sql1+="t_invoice."+classify_MAP.get(classify1)+" like ?";
				
				if(i<length1-1)
					sql1+=" or ";
			}

		}
		


		//后半句sql语句构建
		//String sql2=null;
		Object[] obj2 = null;

		switch(classify_MAP.get(classify2).toString()){
		case "TotalAmount":obj2 = new Object[1];obj2[0]=parameter2;
		case "NinePaid":obj2 = new Object[1];obj2[0]=parameter2;
		case "TenPaid":obj2 = new Object[1];obj2[0]=parameter2;
		case "Type":obj2 = new Object[1];obj2[0]=parameter2;
		default:obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		int length2 = obj2.length;

//		if(length2 == 0){
//			sql1+=" and t_invoice."+classify_MAP.get(classify2)+" = ?";
//		}
		String sql2 = "";
		if(classify2.equals("发票类型")||classify2.equals("总费用")||classify2.equals("90%金额")||classify2.equals("10%金额")){
			sql2+="t_invoice."+classify_MAP.get(classify2)+" = ?";
		}else if(classify2.equals("合同名称") || classify2.equals("客户名称")){
			sql2+="A."+classify_MAP.get(classify2)+" like ?";
		}else{
			for(int i=0; i<length2; i++){

				sql2+="  t_invoice."+classify_MAP.get(classify2)+" like ?";
				if(i<length2-1)
					sql2+=" or ";
			}
		}
		

		sql = sql1 +" and "+sql2;
		//构建带有分页信息的参数数组
		int allCounts = obj1.length+obj2.length;
		
		int times = 0;
		if(length1 == 0 && length2 != 0){
			sqlObj = new Object[allCounts+3];
			sqlObj[0]=0;
			for(int i=0; i<length2; i++){
				sqlObj[times++]=obj2[i];
			}
			sqlObj[allCounts+1]=(page.getCurrentPage()-1)*page.getRows();
			sqlObj[allCounts+2]=page.getRows();
		}else if(length1 != 0 && length2 == 0){
			sqlObj = new Object[allCounts+3];
			for(int i=0; i<length1; i++){
				sqlObj[times++]=obj1[i];
			}
			sqlObj[1]=0;
			sqlObj[allCounts+1]=(page.getCurrentPage()-1)*page.getRows();
			sqlObj[allCounts+2]=page.getRows();
		}else if(length1 == 0 && length2 == 0){
			sqlObj = new Object[allCounts+4];
			sqlObj[0]=0;
			sqlObj[1]=0;
			sqlObj[allCounts+2]=(page.getCurrentPage()-1)*page.getRows();
			sqlObj[allCounts+3]=page.getRows();
		}else{
			sqlObj = new Object[allCounts+2];
			for(int i=0; i<length1; i++){
				sqlObj[times++]=obj1[i];
			}
			for(int i=0; i<length2; i++){
				sqlObj[times++]=obj2[i];
			}
			sqlObj[allCounts]=(page.getCurrentPage()-1)*page.getRows();
			sqlObj[allCounts+1]=page.getRows();
		}	
		
		sql+=" order by t_invoice.Date desc limit ?,?";
	
		System.out.println("参数："+Arrays.toString(sqlObj));
		System.out.println("组合查询： "+sql);
		return new InvoiceDao().getInvoice(sql, sqlObj);
	}

	@Override
	public CompeteInvoice UpdateInvoiceByRequest(HttpServletRequest request) {
		//System.out.println(123);
		CompeteInvoice competeInvoice = new CompeteInvoice();
		Invoice invoice = new Invoice();
		invoice.setID(Integer.parseInt(request.getParameter("ID")));
		invoice.setContractNO(request.getParameter("ContractNO"));
		//System.out.println("合同："+request.getParameter("ContractNO"));
		invoice.setAirPort(request.getParameter("AirPort"));
		invoice.setApplicant(request.getParameter("Applicant"));
		invoice.setDate(request.getParameter("Date").toString()=="--"?"0000-00-00":request.getParameter("Date").toString());
		invoice.setDCNO(request.getParameter("DCNO"));
		invoice.setDeparture(request.getParameter("Departure"));
		invoice.setDepartureDate(request.getParameter("DepartureDate").toString()=="NA"?"0000-00-00":request.getParameter("DepartureDate").toString());
		
		invoice.setDestination(request.getParameter("Destination"));
		invoice.setEndUser(request.getParameter("EndUser"));
		invoice.setPacking(request.getParameter("Packing"));
		invoice.setInvoiceNO(request.getParameter("InvoiceNO"));
		invoice.setManufacturer(request.getParameter("Manufacturer"));
		
		invoice.setOrigin(request.getParameter("Origin"));
		invoice.setOtherReference(request.getParameter("OtherReference"));
		invoice.setPaymentRemark(request.getParameter("PaymentRemark"));
		invoice.setPONO(request.getParameter("PONO"));
		invoice.setShippingMark(request.getParameter("ShippingMark"));
		invoice.setType(Integer.parseInt(request.getParameter("Type")));
		invoice.setVessel(request.getParameter("Vessel"));
		invoice.setAdd(request.getParameter("Add"));
		invoice.setTelFax(request.getParameter("TelFax"));
		competeInvoice.setInvoice(invoice);
		Item item = new Item();
		
		item.setID(Integer.parseInt(request.getParameter("itemID")));
		item.setGoods(request.getParameter("Goods"));
		item.setInvoiceID(invoice.getID()); 
		item.setQty(Integer.parseInt(request.getParameter("Qty")));
		item.setTotalUSDAmount(Double.parseDouble(request.getParameter("TotalUSDAmount")));
		item.setUnit(request.getParameter("Unit"));
		item.setUnitUSDPrice(Double.parseDouble(request.getParameter("UnitUSDPrice")));
		competeInvoice.setItem(item);
		
		return competeInvoice;
	}
	
	
	public int invoiceAdd(Invoice invoice,HttpServletRequest request ) {

		int flag = 0;
		
		Item item = new Item();
		InvoiceDao dao = new InvoiceDao();
		DBUtil db = new DBUtil();
		Connection connection = db.getConnection();
		int invoiceID = 0;
		
		try {
			connection.setAutoCommit(false);
			invoiceID = dao.insert(invoice, db);
			if(invoiceID != 0){

				String[] Goods = request.getParameterValues("Goods[]");
				String[] Unit = request.getParameterValues("Unit[]");
				String[] Qty = request.getParameterValues("Qty[]");
				String[] TotalUSDAmount = request.getParameterValues("TotalUSDAmount[]");
				String[] UnitUSDPrice = request.getParameterValues("UnitUSDPrice[]");
				
			
	            for(int i=0;i<Goods.length;i++){
					item.setGoods(Goods[i]);
					item.setInvoiceID(invoiceID);
					item.setUnit(Unit[i]);
					item.setQty(Integer.parseInt(Qty[i]));
					item.setTotalUSDAmount(Double.parseDouble(TotalUSDAmount[i]));
					item.setUnitUSDPrice(Double.parseDouble(UnitUSDPrice[i]));
					dao.insertItem(item,db);  
	            }
			}
			connection.commit();
			flag = 1;
			return flag;
		
		} catch (Exception e) {
		
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return flag;
		}finally {
			db.closed();
		}
	

		
	}

	
	@Override
	public int InvoiceModify(Invoice invoice, HttpServletRequest request) {
		int flag = 0;
		Item item = new Item();
		InvoiceDao dao = new InvoiceDao();
		DBUtil db = new DBUtil();
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			dao.ModifyInvoice(invoice, db);
			String itemFlag = request.getParameter("itemFlag");
			if(itemFlag.equals("yes")){
				String[] itemID = request.getParameterValues("itemID[]");
				String[] Goods = request.getParameterValues("Goods[]");
				String[] Unit = request.getParameterValues("Unit[]");
				String[] Qty = request.getParameterValues("Qty[]");
				String[] TotalUSDAmount = request.getParameterValues("TotalUSDAmount[]");
				String[] UnitUSDPrice = request.getParameterValues("UnitUSDPrice[]");
				if(itemID.length !=  0){
		            for(int i=0;i<Goods.length;i++){
						item.setGoods(Goods[i]);
						
						item.setUnit(Unit[i]);
						item.setQty(Integer.parseInt(Qty[i]));
						item.setTotalUSDAmount(Double.parseDouble(TotalUSDAmount[i]));
						item.setUnitUSDPrice(Double.parseDouble(UnitUSDPrice[i]));
						if(Integer.parseInt(itemID[i]) == 0){
							item.setInvoiceID(invoice.getID());
							dao.insertItem(item,db);
					
						}else{
							item.setID(Integer.parseInt(itemID[i]));
							dao.ModifyItem(item,db);  
						}
		            }
				}
			}
			connection.commit();
			flag = 1;
			return 	flag;				
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return flag;

		}finally {
			db.closed();
		}
		
	}

	@Override
	public List<Map<String, Object>> getOnlyInvoice(Page page) {
	
		return new InvoiceDao().getOnlyInvoice(page);
	}

	

}
