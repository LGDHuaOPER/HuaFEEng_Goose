package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.FormFactorMail;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OriginFactory;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

public class OriginFactoryDao {

	public List<Map<String, Object>> getAllData(Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,PO,SO,FactoryPeriod,DelayPeriod,DelayReason,Type,"
				+ "ShippingInstruction,SOA,BAFA,TargetDate,TrackingNO,PaymentLC,DutyExemption,InvoiceNo InvoiceNo1,InvoiceFile InvoiceFile1,InvoiceNo2,InvoiceFile2,InvoiceNo3,InvoiceFile3,EstimatePaymentDate,PushShipDate,PushTracking,Inform"
				+ " from t_origin_factory where POType='Cascade' Or POType='CascadeComplete' order by ID desc limit ?,?";
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, param);

		return ls;
	}

	public int getAllCounts() {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_origin_factory where POType='Cascade' Or POType='CascadeComplete' ";
		Object[] param = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public boolean insert(OriginFactory factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_origin_factory (PO,SO,FactoryPeriod,DelayPeriod,DelayReason,OperatingTime,Type,POID,POType,"
				+ "ShippingInstruction,SOA,BAFA,TargetDate,TrackingNO,PaymentLC,DutyExemption,PushShipDate,PushTracking) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[18];
		param[0] = factory.getPO();
		param[1] = factory.getSO();
		param[2] = factory.getFactoryPeriod();
		param[3] = factory.getDelayPeriod();
		param[4] = factory.getDelayReason();
		param[5] = dfg.format(new Date());
		param[6] = factory.getType();
		param[7] = factory.getPOID();
		param[8] = factory.getPOType();
		param[9] = factory.getShippingInstruction();
		param[10] = factory.getSOA();
		param[11] = factory.getBAFA();
		param[12] = factory.getTargetDate();
		param[13] = factory.getTrackingNO();
		param[14] = factory.getPaymentLC();
		param[15] = factory.getDutyExemption();
		param[16] = "NO";
		param[17] = "NO";
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean update(OriginFactory factory,Logistics log) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "update t_origin_factory set PO=?,SO=?,FactoryPeriod=?,DelayPeriod=?,DelayReason=?,OperatingTime=?,"
				+ "ShippingInstruction=?,SOA=?,BAFA=?,TargetDate=?,TrackingNO=?,PaymentLC=?,DutyExemption=?,InvoiceNo=?,InvoiceFile=?,"
				+ "InvoiceNo2=?,InvoiceFile2=?,InvoiceNo3=?,InvoiceFile3=?,EstimatePaymentDate=? where ID=?";
		Object[] param1 = new Object[21];
		param1[0] = factory.getPO();
		param1[1] = factory.getSO();
		param1[2] = factory.getFactoryPeriod();
		param1[3] = factory.getDelayPeriod();
		param1[4] = factory.getDelayReason();
		param1[5] = dfg.format(new Date());
		param1[6] = factory.getShippingInstruction();
		param1[7] = factory.getSOA();
		param1[8] = factory.getBAFA();
		param1[9] = factory.getTargetDate();
		param1[10] = factory.getTrackingNO();
		param1[11] = factory.getPaymentLC();
		param1[12] = factory.getDutyExemption();
		param1[13] = factory.getInvoiceNo1();
		param1[14] = factory.getInvoiceFile1();
		param1[15] = factory.getInvoiceNo2();
		param1[16] = factory.getInvoiceFile2();
		param1[17] = factory.getInvoiceNo3();
		param1[18] = factory.getInvoiceFile3();
		param1[19] = factory.getEstimatePaymentDate();
		param1[20] = factory.getID();
		
		String sql2 = "update t_logistics set SONO=?,FactoryShipment=? where PONO=?";
		Object[] param2 = new Object[3];
		param2[0] = log.getSONO();
		param2[1] = log.getFactoryShipment();
		param2[2] = log.getPONO();
		Connection conn = db.getConnection();
		try {
			conn.setAutoCommit(false);
			db.executeUpdateNotClose(sql1, param1);
			db.executeUpdateNotClose(sql2, param2);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
	
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			return false;
		
		}finally{
			db.closed();
		}
	
	
	}
	
	public boolean updateType(OriginFactory factory) {
		DBUtil db = new DBUtil();
		String sql = "update t_origin_factory set Type=? where ID=?";
		Object[] param = new Object[2];
		param[0] = factory.getType();
		param[1] = factory.getID();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean delete (int id){
		DBUtil db = new DBUtil();
		String sql = "delete from t_origin_factory where ID=?";
		Object[] param = new Object[]{id};
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	
	public boolean updateLogisticsPO(Logistics factory) {
		DBUtil db = new DBUtil();
		String sql = "update t_logistics set SONO=?,FactoryShipment=? where PONO=?";
		Object[] param = new Object[3];
		param[0] = factory.getSONO();
		param[1] = factory.getFactoryShipment();
		param[2] = factory.getPONO();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 判断PO是否已向原厂下了
	 * @param po
	 * @return
	 */
	public boolean getInfoByPO(int poID,String type) {
		System.out.println("dao:"+poID+"---"+type);
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,PO,SO,FactoryPeriod,DelayPeriod,DelayReason,Type from t_origin_factory where POID=? and POType=?";
		Object[] param = new Object[] {poID,type};
        boolean flag = false;
		ls = db.QueryToList(sql, param);
		if(ls.size()>1){
			flag = true;
		}

		return flag;
	}

	public String getEndUrser(String po) {
		String EndCompany = "";
		DBUtil db = new DBUtil();
		String sql = "select EndCompany from t_quote_cascade_po where Number=?";
		Object[] param = new Object[] {po};
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1) {
			EndCompany = ls.get(1).get("EndCompany").toString();
		}
		return EndCompany;
	}

	public boolean updateFileFlag(int ID, int Type) {
		DBUtil db = new DBUtil();
		String sql = "";
		if(Type==1) {
			sql = "update t_origin_factory set SOA=? where ID=?";
		}else {
			sql = "update t_origin_factory set BAFA=? where ID=?";
		}
		Object[] param = new Object[] {"YES",ID};
		
		return db.executeUpdate(sql, param)>0?true:false;
	}

	public int getAllCountsByTwo(String type1, String content1, String type2, String content2) {
		String sql1 = "";
		String sql2 = "";
		switch (type1) {
		case "PO":
			sql1 = " and  PO like ?";
			break;
		case "SO":
			sql1 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		switch (type2) {
		case "PO":
			sql2 = " and  PO like ?";
			break;
		case "SO":
			sql2 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_origin_factory where (POType='Cascade' OR POType='CascadeComplete') "+sql1+sql2;
		
		Object[] param = new Object[] { "AllCounts", "%"+content1+"%","%"+content2+"%"};
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public List<Map<String, Object>> getAllDataByTwo(Page page,String type1, String content1, String type2, String content2) {
		String sql1 = "";
		String sql2 = "";
		switch (type1) {
		case "PO":
			sql1 = " and  PO like ?";
			break;
		case "SO":
			sql1 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		switch (type2) {
		case "PO":
			sql2 = " and  PO like ?";
			break;
		case "SO":
			sql2 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,PO,SO,FactoryPeriod,DelayPeriod,DelayReason,Type,"
				+ "ShippingInstruction,SOA,BAFA,TargetDate,TrackingNO,PaymentLC,DutyExemption,InvoiceNo InvoiceNo1,InvoiceFile InvoiceFile1,InvoiceNo2,InvoiceFile2,InvoiceNo3,InvoiceFile3,EstimatePaymentDate,PushShipDate,PushTracking,Inform"
				+ " from t_origin_factory where (POType='Cascade' OR POType='CascadeComplete')"+sql1+sql2+" order by ID desc limit ?,?";
		Object[] param = new Object[] { "%"+content1+"%","%"+content2+"%",(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, param);

		return ls;
	}

	public int getAllCountsByOne(String type1, String content1) {
		String sql1 = "";
		switch (type1) {
		case "PO":
			sql1 = " and  PO like ?";
			break;
		case "SO":
			sql1 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_origin_factory where (POType='Cascade' OR POType='CascadeComplete')"+sql1;
		
		Object[] param = new Object[] { "AllCounts", "%"+content1+"%"};
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public List<Map<String, Object>> getAllDataByOne(Page page, String type1, String content1) {
		String sql1 = "";
		switch (type1) {
		case "PO":
			sql1 = " and  PO like ?";
			break;
		case "SO":
			sql1 = " and  SO like ?";
			break;
		case "Factory Date":
			sql1 = " and FactoryPeriod like ?";
			break;
		case "Delay Date":
			sql1 = " and DelayPeriod like ?";
			break;
		default:
			break;
		}
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,PO,SO,FactoryPeriod,DelayPeriod,DelayReason,Type,"
				+ "ShippingInstruction,SOA,BAFA,TargetDate,TrackingNO,PaymentLC,DutyExemption,InvoiceNo InvoiceNo1,InvoiceFile InvoiceFile1,InvoiceNo2,InvoiceFile2,InvoiceNo3,InvoiceFile3,EstimatePaymentDate,PushShipDate,PushTracking,Inform"
				+ " from t_origin_factory where (POType='Cascade' OR POType='CascadeComplete')"+sql1+" order by ID desc limit ?,?";
		Object[] param = new Object[] { "%"+content1+"%",(page.getCurrentPage() - 1) * page.getRows(), page.getRows()};

		ls = db.QueryToList(sql, param);

		return ls;
	}
	
	public boolean updatePushDate(int factoryID,String type){
		DBUtil db = new DBUtil();
		String sql = "update t_origin_factory set "+type+" = 'YES' where ID = ?";
		int result = db.executeUpdate(sql, new Object[]{factoryID});
		return result>0?true:false;			
	}
	public boolean updateInform(int factoryID,String address){
		DBUtil db = new DBUtil();
		String sql = "update t_origin_factory set Inform = ? where ID = ?";
		int result = db.executeUpdate(sql, new Object[]{address,factoryID});
		return result>0?true:false;			
	}
	
	public boolean saveMail(FormFactorMail mail){
		DBUtil db1 = new DBUtil();
		DBUtil db2 = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql1 = "select ID from t_origin_factory_mail_record where FactoryID = ? and Type = ?";
		Object[] param1 = new Object[]{mail.getFactoryID(),mail.getType()};
		
		List<Map<String, Object>> list = db1.QueryToList(sql1, param1);
		String sql2 = "";
		Object[] param2 = new Object[7];
		if(list.size()>1){
			sql2 = "update t_origin_factory_mail_record set Subject = ?,Receptor=?,CCList=?,MainBody=?,OperatingTime=? where FactoryID = ? and Type = ?";
			
			param2[0] = mail.getSubject();
			param2[1] = mail.getReceptor();
			param2[2] = mail.getCopyList();
			param2[3] = mail.getMainBody();
			
			param2[4] = dfg.format(new Date());
			param2[5] = mail.getFactoryID();
			param2[6] = mail.getType();
		}else{
			sql2 = "insert into t_origin_factory_mail_record(Type,Subject,Receptor,CCList,MainBody,FactoryID,OperatingTime) values(?,?,?,?,?,?,?)";
			param2[0] = mail.getType();
			param2[1] = mail.getSubject();
			param2[2] = mail.getReceptor();
			param2[3] = mail.getCopyList();
			param2[4] = mail.getMainBody();
			param2[5] = mail.getFactoryID();
			param2[6] = dfg.format(new Date());
		}
		

		int result = db2.executeUpdate(sql2, param2);
		return result>0?true:false;
	}
	
	public List<Map<String,Object>> getMail(String type,int factoryID){
		DBUtil db = new DBUtil();
		String sql = "select Subject,Receptor,CCList,MainBody from t_origin_factory_mail_record where FactoryID = ? and Type = ?";
		return db.QueryToList(sql, new Object[]{factoryID,type});
	}
}
