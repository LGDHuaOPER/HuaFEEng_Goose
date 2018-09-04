package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ShipmentDao;
import com.eoulu.entity.Shipment;
import com.eoulu.entity.ShipmentSize;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ShipmentService;
import com.eoulu.util.DBUtil;

public class ShipmentServiceImpl implements ShipmentService{

	public static final Map<String, Object> classify_MAP; 
	static{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "Customer");
		map.put("合同号", "ContractNO");
		map.put("订单号", "PONO");
		map.put("信用证号", "DCNO");
	
		classify_MAP = map;
	}
	@Override
	public int getAllCounts() {
		
		return new ShipmentDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getShipment(Page page) {
		
		return new ShipmentDao().getShipment(page);
	}

	@Override
	public boolean addShipment(HttpServletRequest request) {
		ShipmentDao dao = new ShipmentDao();
		List<String> ls = new ArrayList<>();
		Shipment shipment = new Shipment();
		String sizeExist = request.getParameter("sizeExist");
		//System.out.println(sizeExist);
		String InfoAPP = request.getParameter("InfoAPP");
		String InfoADD = request.getParameter("InfoADD");
		String InfoTel = request.getParameter("InfoTel");
		String DCNO = request.getParameter("DCNO");
		String AirWaybillNO = request.getParameter("AirWaybillNO");
		String ContractNO = request.getParameter("ContractNO");
		String FlightNO = request.getParameter("FlightNO");
		String GoodsDescription = request.getParameter("GoodsDescription");
		String Qty = request.getParameter("Qty");
		String Total = request.getParameter("Total");
		String ShipmentValue = request.getParameter("ShipmentValue");
		String CarrierName = request.getParameter("CarrierName");
		String DepartureDate = request.getParameter("DepartureDate");
		String DepartAirport = request.getParameter("DepartAirport");
		String DestAirport = request.getParameter("DestAirport");
		String Date = request.getParameter("Date");
		//System.out.println(InfoAPP);
		shipment.setInfoAPP(InfoAPP);
		shipment.setInfoADD(InfoADD);
		shipment.setInfoTel(InfoTel);
		shipment.setDCNO(DCNO);
		shipment.setAirWaybillNO(AirWaybillNO);
		shipment.setContractNO(ContractNO);
		shipment.setFlightNO(FlightNO);
		shipment.setGoodsDescription(GoodsDescription);
		shipment.setQty(Integer.parseInt(Qty));
		shipment.setTotal(Integer.parseInt(Total));
		shipment.setValue(Double.parseDouble(ShipmentValue));
		shipment.setCarrierName(CarrierName);
		shipment.setDepartureDate(DepartureDate);
		shipment.setDepartAirport(DepartAirport);
		shipment.setDestAirport(DestAirport);
		shipment.setDate(Date);
		boolean flag = dao.insert(shipment);
		
		if(flag && sizeExist.equals("yes")){
			int ShipmentID = dao.getShipmentID(ContractNO);
			String[] DIM = request.getParameterValues("DIM[]");
			//System.out.println("test"+Arrays.toString(DIM));
			String[] GrossWeight = request.getParameterValues("GrossWeight[]");
			ShipmentSize size = new ShipmentSize();
			for(int i=0 ; i<DIM.length; i++){
				size.setDIM(DIM[i]);
				size.setGrossWeight(GrossWeight[i]);
				size.setShipmentID(ShipmentID);
				boolean temp = dao.insertSize(size);
				if(temp){
					ls.add("true");
				}else{
					ls.add("false");
				}
			}
			
		}
		if(flag && !ls.contains("false")){
			flag = true;
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "商务部-发货通知";
			String description = "新增-"+ContractNO;
			logs.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean updateShipment(HttpServletRequest request) {
		ShipmentDao dao = new ShipmentDao();
		List<String> ls = new ArrayList<>();
		Shipment shipment = new Shipment();
		String sizeExist = request.getParameter("sizeExist");
		String InfoAPP = request.getParameter("InfoAPP");
		String InfoADD = request.getParameter("InfoADD");
		String InfoTel = request.getParameter("InfoTel");
		String DCNO = request.getParameter("DCNO");
		System.out.println("修改："+DCNO);
		String AirWaybillNO = request.getParameter("AirWaybillNO");
		System.out.println("修改："+AirWaybillNO);
		String ContractNO = request.getParameter("ContractNO");
		String FlightNO = request.getParameter("FlightNO");
		String GoodsDescription = request.getParameter("GoodsDescription");
		String Qty = request.getParameter("Qty");
		String Total = request.getParameter("Total");
		String ShipmentValue = request.getParameter("ShipmentValue");
		String CarrierName = request.getParameter("CarrierName");
		String DepartureDate = request.getParameter("DepartureDate");
		String DepartAirport = request.getParameter("DepartAirport");
		String DestAirport = request.getParameter("DestAirport");
		String Date = request.getParameter("Date");
		String id = request.getParameter("ID");
		shipment.setInfoAPP(InfoAPP);
		shipment.setInfoADD(InfoADD);
		shipment.setInfoTel(InfoTel);
		shipment.setDCNO(DCNO);
		shipment.setAirWaybillNO(AirWaybillNO);
		shipment.setContractNO(ContractNO);
		shipment.setFlightNO(FlightNO);
		shipment.setGoodsDescription(GoodsDescription);
		shipment.setQty(Integer.parseInt(Qty));
		shipment.setTotal(Integer.parseInt(Total));
		shipment.setValue(Double.parseDouble(ShipmentValue));
		shipment.setCarrierName(CarrierName);
		shipment.setDepartureDate(DepartureDate);
		shipment.setDepartAirport(DepartAirport);
		shipment.setDestAirport(DestAirport);
		shipment.setDate(Date);
		shipment.setID(Integer.parseInt(id));
		boolean flag = dao.updateShipment(shipment);
		if(flag && sizeExist.equals("sizeExist")){
			String[] ids = request.getParameterValues("ID[]");
			String[] DIM = request.getParameterValues("DIM[]");
			String[] GrossWeight = request.getParameterValues("GrossWeight[]");
			ShipmentSize size = new ShipmentSize();
			for(int i=0 ; i<DIM.length; i++){
				size.setID(Integer.parseInt(ids[i]));
				size.setDIM(DIM[i]);
				size.setGrossWeight(GrossWeight[i]);
			
				boolean temp = dao.updateSize(size);
				if(temp){
					ls.add("true");
				}else{
					ls.add("false");
				}
			}
			
		}
		
		if(flag && !ls.contains("false")){
			flag = true;
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "商务部-发货通知";
			String description = "修改-"+ContractNO;
			logs.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean deleteShipment(int id) {
		
		return new ShipmentDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_shipment.ID) from t_shipment "
				+ "left join t_order on t_order.ContractNo=t_shipment.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_shipment."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getShipmentByClassifyInOne(String classify, Object parameter, Page page) {
		System.out.println(classify+":"+parameter);
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql =  "select t_shipment.AirWaybillNO,t_shipment.CarrierName,t_shipment.ContractNO,t_shipment.Date,t_shipment.DCNO,t_shipment.DepartAirport,"
				+ "t_shipment.DepartureDate,t_shipment.DestAirport,t_shipment.FlightNO,t_shipment.GoodsDescription,t_shipment.ID,t_shipment.InfoADD,"
				+ "t_shipment.InfoAPP,t_shipment.InfoTel,t_shipment.Qty,t_shipment.Total,t_shipment.ShipmentValue Value ,t_order.ContractTitle,t_order.Customer "
				+ " from t_shipment left join t_order on t_shipment.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_shipment."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by t_shipment.OperatingTime desc limit ?,?";
		Object[] param;
		if(obj.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else{
			param = new Object[obj.length+2];
			for(int i=0 ; i< obj.length ; i++){
				param[i] = obj[i];
			}
			param[obj.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj.length+1] = page.getRows();
		}
		return new ShipmentDao().getShipment(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_shipment.ID) from t_shipment "
				+ "left join t_order on t_order.ContractNo=t_shipment.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_shipment."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_shipment."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2;
		Object[] param = new Object[obj1.length+obj2.length];
		param[0]=obj1[0];
		param[1]=obj2[0];

		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getShipmentByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select t_shipment.AirWaybillNO,t_shipment.CarrierName,t_shipment.ContractNO,t_shipment.Date,t_shipment.DCNO,t_shipment.DepartAirport,"
				+ "t_shipment.DepartureDate,t_shipment.DestAirport,t_shipment.FlightNO,t_shipment.GoodsDescription,t_shipment.ID,t_shipment.InfoADD,"
				+ "t_shipment.InfoAPP,t_shipment.InfoTel,t_shipment.Qty,t_shipment.Total,t_shipment.ShipmentValue Value ,t_order.ContractTitle,t_order.Customer "
				+ " from t_shipment left join t_order on t_shipment.ContractNO=t_order.ContractNo ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_shipment."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_shipment."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_shipment.OperatingTime desc limit ?,?";
		Object[] param;
		if(obj1.length == 0 && obj2.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else if(obj1.length != 0 && obj2.length == 0){
			param = new Object[obj1.length+2];
			for(int i=0 ; i<obj1.length ; i++){
				param[i] = obj1[i];
			}
			param[obj1.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj1.length+1] = page.getRows();
		}else if(obj1.length == 0 && obj2.length != 0){
			param = new Object[obj2.length+2];
			for(int i=0 ; i<obj2.length ; i++){
				param[i] = obj2[i];
			}
			param[obj2.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj2.length+1] = page.getRows();
		}else{
			param = new Object[obj1.length+obj2.length+2];
			
			for(int i=0 ; i<param.length-2 ; i++){
				if(i == 0){
					param[i] = obj1[0];
				}
				if(i == 1){
					param[i] = obj2[0];
				}
				
			}
			param[param.length-2] = (page.getCurrentPage()-1)*page.getRows();
			param[param.length-1] = page.getRows();
		}
		return new ShipmentDao().getShipment(sql, param);
	}

	@Override
	public List<Map<String, Object>> getShipmentSize(int id) {
		
		return new ShipmentDao().getShipmentSize(id);
	}

}
