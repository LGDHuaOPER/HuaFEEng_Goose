package com.eoulu.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InsuranceGoods;
import com.eoulu.entity.InsuranceSlip;
import com.eoulu.util.DBUtil;

public class InsuranceSlipDao {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInsuranceSlip(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();

		String sql= "select t_transport_insurance_slip.AdditionFactor,t_transport_insurance_slip.Address,t_transport_insurance_slip.AirWayBillNO GoodsNO,t_transport_insurance_slip.Applicant,"
				+ "t_transport_insurance_slip.Contacts,t_transport_insurance_slip.ContactsTel,t_transport_insurance_slip.ContractNO,"
				+ "t_transport_insurance_slip.Currency,t_transport_insurance_slip.Date ApplicantDate,"
				+ "t_transport_insurance_slip.DCNO,t_transport_insurance_slip.Departure Inchoat,t_transport_insurance_slip.DepartureDate StartDate,"
				+ "t_transport_insurance_slip.Destination,"
				+ "t_transport_insurance_slip.InvoiceAmount,t_transport_insurance_slip.InvoiceNO,t_transport_insurance_slip.InvoiceAmount*1.1 InsuranceUSD,"
				+ "t_transport_insurance_slip.OperatingTime,t_transport_insurance_slip.PackingQty PackingNumber,"
				+ "t_transport_insurance_slip.ShipFlight,"
				+ "t_transport_insurance_slip.ShippingMark,t_transport_insurance_slip.ShippingType,t_transport_insurance_slip.Via Pass,"
				+ "t_transport_insurance_slip.ZipCode,t_transport_insurance_slip.ID,"
				+ "t_transport_insurance_slip.InsuredName,t_transport_insurance_slip.PayableAt,"
				+ "t_transport_insurance_slip.Indemnity,t_transport_insurance_slip.ToPort,"
				+ "t_transport_insurance_slip.TransUtil,t_transport_insurance_slip.BLNO,"
				+ "t_transport_insurance_slip.InsuranceType,t_transport_insurance_slip.Transport,"
				+ "t_order.Customer,t_order.ContractTitle from t_transport_insurance_slip "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_slip.ContractNO order by t_transport_insurance_slip.Date desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_transport_insurance_slip order by OperatingTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}

	public int getIDByContractNO(String ContractNO){
		
		int id = 0;
		DBUtil db = new DBUtil();
		String sql = "select ID from t_transport_insurance_slip where ContractNO=?";
		
		Object[] parameter = new Object[]{ContractNO};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());//get(ls.get(0).get("ID")).
			
		}
		return id;
	}
	
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(InsuranceSlip info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[30];
		String sql = "insert into t_transport_insurance_slip (AdditionFactor,Address,AirWayBillNO,Applicant,"
				+ "Contacts,ContactsTel,ContractNO,Currency,Date,"
				+ "DCNO,Departure,DepartureDate,Destination,"
				+ "InvoiceAmount,InvoiceNO,PackingQty,ShipFlight,"
				+ "ShippingMark,ShippingType,Via,ZipCode,OperatingTime,"
				+ "InsuredName,PayableAt,Indemnity,ToPort,TransUtil,BLNO,InsuranceType,Transport) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		parameter[0] = info.getAdditionFactor();
		parameter[1] = info.getAddress();
		parameter[2] = info.getAirWayBillNO();
		parameter[3] = info.getApplicant();
		parameter[4] = info.getContacts();
		parameter[5] = info.getContactsTel();
		parameter[6] = info.getContractNO();
		parameter[7] = info.getCurrency();
		parameter[8] = info.getDate();
		parameter[9] = info.getDCNO();
		parameter[10] = info.getDeparture();
		parameter[11] = info.getDepartureDate();
		parameter[12] = info.getDestination();
		
		parameter[13] = info.getInvoiceAmount();
		parameter[14] = info.getInvoiceNO();
		parameter[15] = info.getPackingQty();
		parameter[16] = info.getShipFlight();
		parameter[17] = info.getShippingMark();
		parameter[18] = info.getShippingType();
		parameter[19] = info.getVia();
		parameter[20] = info.getZipCode();
		parameter[21] = df.format(new Date());
		parameter[22] = info.getInsuredName();
		parameter[23] = info.getPayableAt();
		parameter[24] = info.getIndemnity();
		parameter[25] = info.getToPort();
		parameter[26] = info.getTransUtil();
		parameter[27] = info.getBLNO();
		parameter[28] = info.getInsuranceType();
		parameter[29] = info.getTransport();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean update(InsuranceSlip info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[31];
		String sql = "update t_transport_insurance_slip set AdditionFactor=?,Address=?,AirWayBillNO=?,Applicant=?,"
				+ "Contacts=?,ContactsTel=?,ContractNO=?,Currency=?,Date=?,"
				+ "DCNO=?,Departure=?,DepartureDate=?,Destination=?,"
				+ "InvoiceAmount=?,InvoiceNO=?,PackingQty=?,ShipFlight=?,"
				+ "ShippingMark=?,ShippingType=?,Via=?,ZipCode=?,OperatingTime=?,"
				+ "InsuredName=?,PayableAt=?,Indemnity=?,ToPort=?,TransUtil=?,BLNO=?,InsuranceType=?,Transport=? where ID=?";
		parameter[0] = info.getAdditionFactor();
		parameter[1] = info.getAddress();
		parameter[2] = info.getAirWayBillNO();
		parameter[3] = info.getApplicant();
		parameter[4] = info.getContacts();
		parameter[5] = info.getContactsTel();
		parameter[6] = info.getContractNO();
		parameter[7] = info.getCurrency();
		parameter[8] = info.getDate();
		parameter[9] = info.getDCNO();
		parameter[10] = info.getDeparture();
		parameter[11] = info.getDepartureDate();
		parameter[12] = info.getDestination();
		
		parameter[13] = info.getInvoiceAmount();
		parameter[14] = info.getInvoiceNO();
		parameter[15] = info.getPackingQty();
		parameter[16] = info.getShipFlight();
		parameter[17] = info.getShippingMark();
		parameter[18] = info.getShippingType();
		parameter[19] = info.getVia();
		parameter[20] = info.getZipCode();
		parameter[21] = df.format(new Date());
		parameter[22] = info.getInsuredName();
		parameter[23] = info.getPayableAt();
		parameter[24] = info.getIndemnity();
		parameter[25] = info.getToPort();
		parameter[26] = info.getTransUtil();
		parameter[27] = info.getBLNO();
		parameter[28] = info.getInsuranceType();
		parameter[29] = info.getTransport();
		parameter[30] = info.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_transport_insurance_slip where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 
	 * @param DirectiveID
	 * @return
	 */
	public List<Map<String,Object>> getInsuranceGoods(int id){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select Model,ID from t_transport_insurance_goods where SlipID=?";
		Object[] parameter = new Object[]{id};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public boolean insert(InsuranceGoods info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[3];
		String sql = "insert into t_transport_insurance_goods (Model,SlipID,OperatingTime)"
				+ "values (?,?,?) ";
		parameter[0] = info.getModel();
		parameter[1] = info.getSlipID();
		parameter[2] = df.format(new Date());
	
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	} 
	public boolean updateInsuranceGoods(InsuranceGoods info){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[3];
		String sql =  "update t_transport_insurance_goods set Model=?,"
				+ "OperatingTime=? where ID=?";
		parameter[0] = info.getModel();
		parameter[1] = df.format(new Date());
		parameter[2] = info.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	public boolean deleteInsuranceGoods(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_transport_insurance_goods where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getInsuranceSlip(String sql,Object[] parameter){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	public Map<String, Object> getInsurancePolicyInfo(int ID) {
		String sql = "select InvoiceNO,DepartureDate,AirWayBillNO,InvoiceAmount,Currency,ContractNO,DCNO,"
				+ "Departure,Via,Destination,AdditionFactor,ShippingMark,PackingQty,Date,InsuredName,PayableAt,"
				+ "Indemnity,ToPort,TransUtil,BLNO,InsuranceType,Transport "
				+ "from t_transport_insurance_slip where ID =?";
		Object[] parameter = new Object[] {ID};
		List<Map<String, Object>> ls = getInsuranceSlip(sql, parameter);
		Map<String, Object> lsmap = ls.get(1);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("${InvoiceNO}", lsmap.get("InvoiceNO").toString());
		map.put("${DepartureDate}", lsmap.get("DepartureDate").toString());
		map.put("${AirWayBillNO}", lsmap.get("AirWayBillNO").toString());
		map.put("${InvoiceAmount}", lsmap.get("InvoiceAmount").toString());
		
		BigDecimal b1 = new BigDecimal(lsmap.get("InvoiceAmount").toString());
		BigDecimal b2 = new BigDecimal("1.1");
		Double invoiceNum = b1.multiply(b2).doubleValue();
		//Double invoiceNum = Double.parseDouble(lsmap.get("InvoiceAmount").toString())*1.1;
		BigDecimal bg = new BigDecimal(invoiceNum);
	
		map.put("${InvoiceAmount2}", String.valueOf(bg.setScale(2, RoundingMode.UP)));
		map.put("${Currency}", lsmap.get("Currency").toString());
		map.put("${ContractNO}", lsmap.get("ContractNO").toString());
		map.put("${DCNO}", lsmap.get("DCNO").toString());
		map.put("${Departure}", lsmap.get("Departure").toString());
		map.put("${Via}", lsmap.get("Via").toString());
		map.put("${Destination}", lsmap.get("Destination").toString());
		map.put("${AdditionFactor}", lsmap.get("AdditionFactor").toString());
		map.put("${ShippingMark1}", lsmap.get("ShippingMark").toString().split("&&")[0]);
		map.put("${ShippingMark2}", lsmap.get("ShippingMark").toString().split("&&")[1]);
		map.put("${PackingQty}", lsmap.get("PackingQty").toString());
		String[] datas = lsmap.get("Date").toString().split("-");
		map.put("${Date}", datas[0]+" 年  "+datas[1]+" 月 "+datas[2]+" 日");
		
		if("InOutParcel".equals(lsmap.get("InsuranceType").toString())||"InOutAir".equals(lsmap.get("InsuranceType").toString())) {
			map.put("${InsuredName}", lsmap.get("InsuredName").toString());
			map.put("${PayableAt}", lsmap.get("PayableAt").toString());
			map.put("${Indemnity}", lsmap.get("Indemnity").toString());
			map.put("${ToPort}", lsmap.get("ToPort").toString());
			map.put("${TransUtil}", lsmap.get("TransUtil").toString());
			map.put("${BLNO}", lsmap.get("BLNO").toString());
			map.put("${Transport}", lsmap.get("Transport").toString());
			map.put("${Date}", datas[0]+" 年/Y  "+datas[1]+" 月/M "+datas[2]+" 日/D");
		}
		
		String sql2 = "select Model from t_transport_insurance_goods where SlipID =?";
		Object[] parameter2 = new Object[] {ID};
		List<Map<String, Object>> ls2 = getInsuranceSlip(sql2, parameter2);
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<ls2.size();i++) {
			sb.append(ls2.get(i).get("Model").toString().replaceAll("</div>", "").replaceAll("<div>", "\r"));
			if(i!=ls2.size()-1) {
				sb.append("\r");
			}
		}
		map.put("${goodsName}", sb.toString());
		Map<String, Object> mapAll = new HashMap<String, Object>();
		mapAll.put("map", map);
		mapAll.put("InsuranceType", lsmap.get("InsuranceType").toString());
		return mapAll;
	}
}
