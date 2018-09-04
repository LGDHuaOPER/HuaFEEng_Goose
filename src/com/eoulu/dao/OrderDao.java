package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.BillCharge;
import com.eoulu.entity.Consignee;
import com.eoulu.entity.Order;
import com.eoulu.entity.PurchaseInfo;
import com.eoulu.util.DBUtil;

import net.sf.json.JSONArray;

/**
 * @author zhangkai
 * 
 *         Order������ݲ�����
 * 
 */
public class OrderDao {

	/**
	 * ͨ��orderID��ȡorder
	 * 
	 * @param order
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public List<Map<String, Object>> getOrderByID(Order order) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select * from t_order where ID=?";
		Object[] parameter = new Object[] { order.getID() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public boolean isExist(Order order){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID from t_order where ContractNo=?";
		Object[] parameter = new Object[] { order.getContractNo()};

		ls = db.QueryToList(sql, parameter);
		return ls.size()>1?true:false;
	}

	/**
	 * ��ȡ���е�order
	 * 
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public List<Map<String, Object>> getAllOrder() {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select * from t_order";
		Object[] parameter = new Object[0];

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ��ȡ���е�order���ҷ�ҳ
	 * 
	 * @param page
	 *            ��ҳ�Ĳ���
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public List<Map<String, Object>> getAllOrder(Page page, int type) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ContractPath,t_order.isSend,t_payment_status.Status WhetherToPay, t_order.ExpectedReceiptDate,t_order.ID,t_order.ExpectedReceiptDate,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,t_order.CargoPeriod"
				+ ",t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod,t_order.ActualPaymentTime,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,t_order.PurchaseMail,t_requirement_classify.Classify ContractCategory,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,"
				+ "DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1"
				+ " from t_order left join t_area on t_order.Area=t_area.ID left join "
				+ "t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID "
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID ";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		if (type != 3) {
			sql += "where PageType=? ";
			parameter = new Object[] { type, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		sql += "order by t_order.DateOfSign desc limit ?,?";

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 按条件显示未发货的订单 条件是非已完结的，非等待付款的；实际货期不为空或者“0000-00-00”。后者是固定的
	 * 
	 * @param page
	 * @param condition
	 *            合同状态的条件
	 * @param column
	 *            排序的字段
	 * @return
	 */
	public List<Map<String, Object>> getAllOrderByActualDelivery(Page page, String condition, String column, int type) {
		List<Map<String, Object>> ls = null;

		if (condition.equals("AllNoSend")) {
			condition = "t_contract_status.id<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = "t_contract_status.id<>14 and t_contract_status.id<>17 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		
		

		DBUtil db = new DBUtil();
		String sql = "select t_order.QuoteNumber,t_quote_system.Number,t_order.TechnologyPath,t_order.ContractPath,t_order.isSend,t_payment_status.Status WhetherToPay,t_order.ExpectedReceiptDate,t_order.ID,t_order.ExpectedReceiptDate,t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_sales_representative.Name SalesRepresentative,t_order.Contact,t_order.ContactInfo,t_order.DateOfSign,"
				+ "t_order.CargoPeriod,t_order.ActualDelivery,t_order.ActualPaymentTime,t_order.ExpectedDeliveryPeriod,t_contract_status.Status Status,"
				+ "t_order.InstalledTime,t_order.InstalledSite,t_order.Remarks,t_requirement_classify.Classify ContractCategory,DATEDIFF(t_order.ActualDelivery,t_order.CargoPeriod) D3_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.ExpectedDeliveryPeriod) D3_D4,DATEDIFF(t_order.ExpectedDeliveryPeriod,t_order.CargoPeriod) D4_D2,"
				+ "DATEDIFF(t_order.ActualDelivery,t_order.DateOfSign) D3_D1"
				+ " from t_order left join t_area on t_order.Area=t_area.ID left join"
				+ " t_sales_representative on t_order.SalesRepresentative=t_sales_representative.ID "
				+ "left join  t_quotes on t_quotes.OrderID=t_order.ID "
				+ "left join t_payment_status on t_quotes.WhetherToPay=t_payment_status.ID "
				+ "left join t_contract_status on t_order.Status=t_contract_status.ID "
				+ "left join t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID"
				+ " left join t_quote_system on t_order.QuoteNumber=t_quote_system.ID  where ";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		if (type != 3) {
			sql += " PageType=? and ";
			parameter = new Object[] { type, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		sql += condition;
		sql += " (t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null) order by t_order." + column
				+ " desc limit ?,?";
		// System.out.println("条件："+sql);

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> getTransportDetail(Page page, String condition,int type) {
		DBUtil dbUtil = new DBUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String current = format.format(new Date());
	
		List<Map<String, Object>> ls = null;
		
		String sql = "select t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_order.DateOfSign,t_order.CargoPeriod,t_order.ExpectedDeliveryPeriod,t_order.ActualDelivery from t_order"
				+ " left join t_area on t_order.Area=t_area.ID  where ";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		if(type != 3){
			sql += " PageType=? and ";
			parameter = new Object[] { type, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}

		if (condition.equals("AllNoSend")) {
			condition = "Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = "Status<>14 and Status<>17 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		
		if(condition.equals("Overdue")){
			condition = "Status<>14 and CargoPeriod < ? and";
			parameter = new Object[] { type, current, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
	
		if(condition.equals("OverdueRisk")){
			condition = "Status<>14 and DATEDIFF(CargoPeriod,ExpectedDeliveryPeriod) < 8 and "
					+ "CargoPeriod >= ExpectedDeliveryPeriod and CargoPeriod > ? and";
			parameter = new Object[] { type, current, (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		}
		
			
		sql += condition;
		sql += " (t_order.ActualDelivery='0000-00-00' or t_order.ActualDelivery is null) order by t_order.CargoPeriod" 
				+ " desc limit ?,?";
		ls = dbUtil.QueryToList(sql, parameter);
		return ls;
}
		
		
		
				
		
	
	

	/**
	 * ͨ��ContractNo��ȡorderID
	 * 
	 * @param contractNo��ͬ��
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public int getOrderID(String contractNo) {
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;

		String sql = "select ID from t_order where ContractNo=?";
		Object[] parameter = new Object[] { contractNo };

		ls = db.QueryToList(sql, parameter);

		int orderID = 0;
		if (ls.size() > 1)
			orderID = Integer.parseInt(ls.get(1).get("ID").toString());
		return orderID;
	}

	/**
	 * ͨ��ContractNo��ȡorderID
	 * 
	 * @param contractNo��ͬ��
	 * 
	 * @return List ��һ��Ϊ���� �Ժ�Ϊ������
	 */
	public int getOrderID(String contractNo, DBUtil db) {
		List<Map<String, Object>> ls = null;

		String sql = "select ID from t_order where ContractNo=?";
		Object[] parameter = new Object[] { contractNo };

		ls = db.QueryToList(sql, parameter, true);

		int orderID = 0;
		if (ls.size() > 1)
			orderID = Integer.parseInt(ls.get(1).get("ID").toString());
		return orderID;
	}

	/**
	 * ��Ӷ�����Ϣ
	 * 
	 * 
	 * 
	 * @param order
	 *            ������Ϣ
	 * 
	 * @return boolean �ɹ�����true ���򷵻�false
	 * @throws SQLException
	 */

	public boolean insert(Order order, DBUtil db) throws SQLException {

		boolean flag = false;
		String sql = "insert into t_order (Customer,Area,ContractNo,ContractTitle,SalesRepresentative"
				+ ",Contact,ContactInfo,DateOfSign,CargoPeriod,Status,InstalledTime,InstalledSite"
				+ ",Remarks,ContractCategory,ExpectedReceiptDate,isSend,PageType,ContractPath,TechnologyPath,QuoteNumber,ActualPaymentTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[21];
		// ��order�е���Ϣ��ֵ��Object������
		parameter[0] = order.getCustomer();
		parameter[1] = order.getArea();
		parameter[2] = order.getContractNo();
		parameter[3] = order.getContractTitle();
		parameter[4] = order.getSalesRepresentative();
		parameter[5] = order.getContact();
		parameter[6] = order.getContactInfo();
		parameter[7] = order.getDateOfSign().equals("") ? "0000-00-00" : order.getDateOfSign();
		parameter[8] = order.getCargoPeriod().equals("") ? "0000-00-00" : order.getCargoPeriod();
		parameter[9] = 9;
		parameter[10] = order.getInstalledTime().equals("") ? "0000-00-00" : order.getInstalledTime();
		parameter[11] = order.getInstalledSite();
		parameter[12] = order.getRemarks();
		parameter[13] = order.getContractCategory();
		parameter[14] = order.getExpectedReceiptDate().equals("") ? "0000-00-00" : order.getExpectedReceiptDate();
		parameter[15] = order.getIsSend();
		parameter[16] = order.getPageType();
		parameter[17] = order.getContractPath();
		parameter[18] = order.getTechnologyPath();
		parameter[19] = order.getQuoteNumber();
		parameter[20] = order.getActualPaymentTime().equals("")? "0000-00-00" : order.getActualPaymentTime();
		int i = db.executeUpdateNotClose(sql, parameter);

		if (i >= 1) {
			flag = true;
		}

		return flag;
	}

	/**
	 * ��ѯ���ж���������
	 * 
	 */
	public int getAllCounts(int type) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID)?  from t_order order by DateOfSign desc";

		Object[] parameter = new Object[] { "AllCounts" };
		if (type != 3) {
			sql = "select count(ID)  from t_order where PageType=? order by DateOfSign desc";
			parameter = new Object[] { type };
		}
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}
	
	public int getShippedCounts(int type,String startTime,String endTime) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) Count from t_order where (DATE_FORMAT( t_order.ActualDelivery,'%Y-%m') between ? and ?) and PageType = ?";
		Object[] parameter = new Object[]{startTime,endTime,type};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get("Count").toString());

		return counts;
		
	}
	
	public List<Map<String,Object>> getShippedDetail(Page page,int type,String startTime,String endTime) {
	
		DBUtil db = new DBUtil();
		String sql = "select t_order.Customer,t_area.AreaName Area,t_order.ContractNo,t_order.ContractTitle,"
				+ "t_order.DateOfSign,t_order.CargoPeriod,t_order.ExpectedDeliveryPeriod,t_order.ActualDelivery from t_order"
				+ " left join t_area on t_order.Area=t_area.ID where (DATE_FORMAT( t_order.ActualDelivery,'%Y-%m') between ? and ?) "
				+ "and PageType = ? order by t_order.CargoPeriod" 
				+ " desc limit ?,?";
		Object[] parameter = new Object[]{startTime,endTime,type,(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		return ls;	
	}
	


	/**
	 * 
	 * @return
	 */
	public int getAllCountsIfActualDelivery(String condition, String column, int type) {
		int counts = 0;
		DBUtil db = new DBUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String current = format.format(new Date());
		
		String sql = "select count(ID) ? from t_order where ";
		Object[] parameter = new Object[] { "AllCounts" };
		if (type != 3) {
			sql = "select count(ID)  from t_order where PageType=? and ";
			parameter = new Object[] { type };
		}
		
		if (condition.equals("AllNoSend")) {
			condition = "Status<>14 and";
		}
		if (condition.equals("OtherNoSend")) {
			condition = "Status<>17 and Status<>14 and";
		}
		if (condition.equals("All")) {
			condition = "";
		}
		if(condition.equals("Overdue")){
			condition = "Status<>14 and CargoPeriod < ? and";
			parameter = new Object[] {type,current};
		}
		
		if(condition.equals("OverdueRisk")){
			condition = "Status<>14 and DATEDIFF(CargoPeriod,ExpectedDeliveryPeriod) < 8 and "
					+ "CargoPeriod >= ExpectedDeliveryPeriod and CargoPeriod > ? and";
			parameter = new Object[] {type,current};
		}


		sql += condition + " (ActualDelivery='0000-00-00' or ActualDelivery is null) order by ";
		sql += column + " desc";
		
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}
	


	/**
	 * ͨ����������ü�¼��,���Զ������
	 * 
	 * @param sql
	 *            ��Ӧ��select������������䣩��䣬parameter��������
	 */
	public int getCountsByName(String sql, Object[] parameter) {
		int counts = 0;
		DBUtil db = new DBUtil();

		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;

	}

	/**
	 * ͨ����������ü�¼��ҳ
	 * 
	 * @param sql
	 *            ��Ӧ��select������������䣩��䣬parameter��������
	 */
	public List<Map<String, Object>> getOrder(String sql, Object[] parameter) {
		int counts = 0;
		DBUtil db = new DBUtil();

		// System.out.println("shisha:"+Arrays.toString(parameter));
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		// System.out.println("返回几条数据："+ls.size());
		return ls;

	}

	/**
	 * �޸�Order��Ϣ
	 * 
	 * @throws SQLException
	 * 
	 */
	public boolean modify(Order order, DBUtil db) throws SQLException {

		boolean flag = false;
		String sql = "update t_order set Customer=?,Area=?,ContractNo=?,ContractTitle=?,SalesRepresentative=?,Contact=?,"
				+ "ContactInfo=?,DateOfSign=?,CargoPeriod=?,InstalledTime=?,InstalledSite=?,Remarks=?,"
				+ "ContractCategory=?,ExpectedReceiptDate=?,ContractPath=?,TechnologyPath=?,QuoteNumber=?,ActualPaymentTime=? where ID=?";

		// ��quotes�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[19];
		parameter[0] = order.getCustomer();
		parameter[1] = order.getArea();
		parameter[2] = order.getContractNo();
		parameter[3] = order.getContractTitle();
		parameter[4] = order.getSalesRepresentative();
		parameter[5] = order.getContact();
		parameter[6] = order.getContactInfo();
		parameter[7] = order.getDateOfSign();
		parameter[8] = order.getCargoPeriod().equals("") ? "0000:00:00" : order.getCargoPeriod();
		// parameter[9] = order.getStatus();
		parameter[9] = order.getInstalledTime().equals("") ? "0000:00:00" : order.getInstalledTime();
		parameter[10] = order.getInstalledSite();
		parameter[11] = order.getRemarks();
		parameter[12] = order.getContractCategory();
		parameter[13] = order.getExpectedReceiptDate().equals("") ? "0000-00-00" : order.getExpectedReceiptDate();
		parameter[14] = order.getContractPath();
		parameter[15] = order.getTechnologyPath();
		parameter[16] = order.getQuoteNumber();
		parameter[17] = order.getActualPaymentTime().equals("") ? "0000-00-00" : order.getActualPaymentTime();
		parameter[18] = order.getID();

		int i = db.executeUpdateNotClose(sql, parameter);

		if (i >= 1) {
			flag = true;
		}

		return flag;
	}

	/**
	 * ͨ��idɾ��order�������
	 * 
	 * @throws SQLException
	 */
	public boolean deleteOrder(String id, DBUtil db) throws SQLException {
		boolean flag = false;

		String sql = "delete from t_order where ID=?";
		Object[] parameter = new Object[] { id };

		if (db.executeUpdateNotClose(sql, parameter) > 0) {
			flag = true;
		}

		return flag;
	}

	/**
	 * �޸�Ԥ�ƻ��ں�ʵ�ʻ���
	 */
	public boolean modifyLogisticsTime(Order order) {

		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "update t_order set ActualDelivery=?,ExpectedDeliveryPeriod=?  where ID=?";

		// ��quotes�е���Ϣ��ֵ��Object������
		Object[] parameter = new Object[3];
		parameter[0] = order.getActualDelivery().equals("") ? "0000-00-00" : order.getActualDelivery();
		parameter[1] = order.getExpectedDeliveryPeriod().equals("") ? "0000-00-00" : order.getExpectedDeliveryPeriod();
		parameter[2] = order.getID();

		int i = db.executeUpdate(sql, parameter);

		if (i >= 1) {
			flag = true;
		}

		return flag;
	}

	/**
	 * ִ�зǲ�ѯ���
	 */
	public int executeUpdate(String sql, Object[] parameter) {
		DBUtil db = new DBUtil();

		int result = db.executeUpdate(sql, parameter);

		return result;
	}

	public boolean insertConsignee(Consignee consignee) {
		DBUtil db = new DBUtil();
		String sql = "insert into t_consignee (Company,Address,Contacts,Tel) values (?,?,?,?)";
		Object[] param = new Object[4];
		param[0] = consignee.getCompany();
		param[1] = consignee.getAddress();
		param[2] = consignee.getContacts();
		param[3] = consignee.getTel();
		boolean flag = false;
		try {
			int temp = db.executeUpdateNotClose(sql, param);
			if (temp >= 1) {
				flag = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateConsignee(Consignee consignee) {
		DBUtil db = new DBUtil();
		String sql = "update t_consignee set Company=?,Address=?,Contacts=?,Tel=? where ID=?";
		Object[] param = new Object[5];
		param[0] = consignee.getCompany();
		param[1] = consignee.getAddress();
		param[2] = consignee.getContacts();
		param[3] = consignee.getTel();
		param[4] = consignee.getID();
		boolean flag = false;
		try {
			int temp = db.executeUpdateNotClose(sql, param);
			if (temp >= 1) {
				flag = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return flag;
	}

	public boolean getQuoteNumber(int OrderID, int QuoteID) {
		String sql = "select QuoteNumber from t_order where ID=?";
		Object[] param = new Object[] { OrderID };
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		System.out.println(ls);
		boolean flag = false;
		if (ls.size() > 1) {
			int id = Integer.parseInt(ls.get(1).get("QuoteNumber")=="--"?"0":ls.get(1).get("QuoteNumber").toString());
			if (id==0 || id == QuoteID) {
				flag = true;
			}
		}
		return flag;
	}
	// @Test
	// public void test(){
	// Order order = new Order();
	// order.setID(238);
	// order.setActualDelivery("2017-10-11");
	// order.setExpectedDeliveryPeriod("2017-09-10");
	// System.out.println(modifyLogisticsTime(order));
	// }
	public boolean addPurchaseInfo(PurchaseInfo info){
		DBUtil db = new DBUtil();
		String sql = "insert into t_purchase_info values(?,?,?,?)";
		String sql2 = "update t_order set ContractPath=? where ID=?";
		Object[] param = new Object[4];
		param[0] = info.getOrderID();
		param[1] = info.getProduct();
		param[2] = info.getMoney();
		param[3] = info.getUse();
		Object[] param2 = new Object[]{info.getContractPath(),info.getOrderID()};
		int result = 0;
		try {
			result = db.executeUpdateNotClose(sql, param);
			result = db.executeUpdate(sql2, param2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result > 0?true:false;
	}
	
	public boolean updatePurchaseInfo(PurchaseInfo info){
		DBUtil db = new DBUtil();
		String sql = "update t_purchase_info set Product= ?,Money= ?,UseFor=?  where OrderID=?";
		String sql2 = "update t_order set ContractPath=? where ID=?";
		Object[] param = new Object[4];
		
		param[0] = info.getProduct();
		param[1] = info.getMoney();
		param[2] = info.getUse();
		param[3] = info.getOrderID();
		Object[] param2 = new Object[]{info.getContractPath(),info.getOrderID()};
		int result = 0;
		try {
			result = db.executeUpdateNotClose(sql, param);
			result = db.executeUpdate(sql2, param2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result > 0?true:false;
	}
	public List<Map<String, Object>> queryPurchaseInfo(int ID){
		DBUtil db = new DBUtil();
		String sql = "select t_order.Customer,t_order.ContractPath,t_purchase_info.Product,"
				+ "t_purchase_info.Money,t_purchase_info.UseFor "
				+ "from t_order join t_purchase_info on t_order.ID = t_purchase_info.OrderID where t_order.ID=?";
		Object[] param = new Object[]{ID};
		List<Map<String, Object>> list = db.QueryToList(sql, param);
		return list;
	}
	public List<Map<String, Object>> getPurchaseInfoForExcel(int ID){
		DBUtil db = new DBUtil();
		String sql = "select t_order.Customer,t_order.ContractPath,t_purchase_info.Product,"
				+ "t_purchase_info.Money,t_purchase_info.UseFor,t_supplier_bank.Company,"
				+ "t_supplier_bank.Account,t_supplier_bank.Bank "
				+ "from t_order left join t_purchase_info on t_order.ID = t_purchase_info.OrderID " 
				+ "left join t_supplier_bank on t_order.Customer=t_supplier_bank.Supplier where t_order.ID=?";
		Object[] param = new Object[]{ID};
		List<Map<String, Object>> list = db.QueryToList(sql, param);
		return list;
	}
	

	
	
	public boolean setPurchaseMail(int ID){
		DBUtil db = new DBUtil();
		String sql = "update t_order set PurchaseMail='已发送' where ID=?";
		Object[] param = new Object[]{ID};
		int result = db.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean saveBillCharge(BillCharge billCharge){
		DBUtil dbUtil = new DBUtil();
		Connection connection = dbUtil.getConnection();
		String sql = "update t_order set SumOfQuantity=?,SumOfTaxPrice=?,InvoiceTitle=?,"
				+ "TaxPayerIdentityNO=?,RegisterAddress=?,Telephone=?,DepositBank=?,Account=?,"
				+ "InvoiceRecepter=?,LinkAddress=?,LinkTel=?,LinkZipCode=? where ID = ?";
		Object[] param = new Object[13];
		param[0] = billCharge.getSumOfQuantity();
		param[1] = billCharge.getSumOfTaxPrice();
		param[2] = billCharge.getInvoiceTitle();
		param[3] = billCharge.getTaxPayerIdentityNO();
		param[4] = billCharge.getRegisterAddress();
		param[5] = billCharge.getTelephone();
		param[6] = billCharge.getDepositBank();
		param[7] = billCharge.getAccount();
		param[8] = billCharge.getInvoiceRecepter();
		param[9] = billCharge.getLinkAddress();
		param[10] = billCharge.getLinkTel();
		param[11] = billCharge.getLinkZipCode();
		param[12] = billCharge.getOrderID();
		List<Map<String, Object>> goods = billCharge.getSaleGoods();
		try {
			connection.setAutoCommit(false);
			int result = dbUtil.executeUpdateNotClose(sql, param);
			
			deleteGoods(billCharge.getOrderID());
			sql = "insert into t_sales_content (GoodsTaxName,MeasurementUnit,TypeSpecification,"
					+ "Quantity,UnitPrice,SumOfMoney,TaxRate,TaxAmount,TotalPriceTax,OrderID,OrderInfoID) values(?,?,?,?,?,?,?,?,?,?,?)";
			
			for(int i = 0;i < goods.size();i++){
				
				param = goods.get(i).values().toArray();
				dbUtil.executeUpdateNotClose(sql, param);
			}
			if(goods.isEmpty()){
				sql = "insert into t_sales_content (OrderID) values(?)";
				param = new Object[]{billCharge.getOrderID()};
				dbUtil.executeUpdateNotClose(sql, param);
			}
			connection.commit();
			connection.setAutoCommit(true);
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			return false;
			
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public List<Map<String, Object>> getBillCharge(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "select SumOfQuantity,SumOfTaxPrice,InvoiceTitle,"
				+ "TaxPayerIdentityNO,RegisterAddress,Telephone,DepositBank,Account,"
				+ "InvoiceRecepter,LinkAddress,LinkTel,LinkZipCode from t_order where ID=?";
		Object[] param = new Object[]{ID};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, param);
		String sql1 = "select GoodsTaxName,MeasurementUnit,TypeSpecification,"
				+ "Quantity,UnitPrice,SumOfMoney,TaxRate,TaxAmount,TotalPriceTax,OrderInfoID from t_sales_content "
				+ "where OrderID=?";
		List<Map<String, Object>> goods = new DBUtil().QueryToList(sql1,param );
		JSONArray goodsJson = JSONArray.fromObject(goods);
		list.get(1).put("Goods", goodsJson);
		return list;
		
	}
	public boolean deleteGoods(int orderInfoID){
		DBUtil dbUtil = new DBUtil();
		String sql = "delete from t_sales_content where OrderID = ?";
		int result = dbUtil.executeUpdate(sql, new Object[]{orderInfoID});
		return result > 0?true:false;
	}

}
