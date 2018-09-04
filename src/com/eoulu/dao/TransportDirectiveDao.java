package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.TransportAddress;
import com.eoulu.entity.TransportDirective;
import com.eoulu.util.DBUtil;

public class TransportDirectiveDao {
	

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllData(Page page) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select t_transport_directive.ID,ContractNO,Total1,Total2,Total3,PackingDate,Status,t_transporters.Transporters Transporters "
				+ " from t_transport_directive left join  t_transporters "
				+ "on t_transporters.ID = t_transport_directive.Transporter"
				+ " order by PackingDate desc limit ?,?";
		Object[] parameter = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int getAllCounts() {
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_transport_directive order by ID desc";

		Object[] parameter = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);

		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());

		return counts;
	}

	/**
	 * 添加
	 * 
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(TransportDirective td) {

		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[7];
		String sql = "insert into t_transport_directive (ContractNO,PackingDate,Total1,Total2,Total3,Status,Transporter) "
				+ "values (?,?,?,?,?,?,?)";
		parameter[0] = td.getContractNO();
		parameter[1] = td.getPackingDate();
		parameter[2] = td.getTotal1();
		parameter[3] = td.getTotal2();
		parameter[4] = td.getTotal3();
		parameter[5] = td.getStatus();
		parameter[6] = getTransporterID(td.getTransporter());
		System.out.println(parameter[6]);
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	private int getTransporterID(String transporter) {
		DBUtil db = new DBUtil();
		String sql = "select ID from t_transporters where Transporters=?";
		Object[] parameter = new Object[1];
		parameter[0] = transporter;
		List<Map<String,Object>> result = db.QueryToList(sql, parameter);
		if(result==null||result.size()<2) {
			DBUtil db2 = new DBUtil();
			String sql2 = "insert into t_transporters(Transporters) values (?)";
			int i = db2.executeUpdate(sql2, parameter);
			if(i>0) {
				DBUtil db3 = new DBUtil();
				result = db3.QueryToList(sql, parameter);
			}
		}
		return Integer.parseInt(result.get(1).get("ID").toString());
	}

	public boolean upadte(TransportDirective td) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[7];
		String sql = "update t_transport_directive set ContractNO=?,PackingDate=?,Total1=?,Total2=?,Total3=?,Transporter=? "
				+ " where ID=?";
		parameter[0] = td.getContractNO();
		parameter[1] = td.getPackingDate();
		parameter[2] = td.getTotal1();
		parameter[3] = td.getTotal2();
		parameter[4] = td.getTotal3();
		parameter[6] = td.getID();
		parameter[5] = getTransporterID(td.getTransporter());
		System.out.println("parameter[6]:"+parameter[6]);
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean insertAddress(TransportAddress td) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[16];
		String sql = "insert into t_transport_address (Size,Weight,Quantity,WoodenPallet,UnloadPlat,"
				+ "PickCompany,PickAddress,PickContact,PickTel,OperatingTime,ReceivingAddress,ReceivingCompany,ReceivingContact,"
				+ "ReceivingTel,DirectiveID,ContractNO) " + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = td.getSize();
		parameter[1] = td.getWeight();
		parameter[2] = td.getQuantity();
		parameter[3] = td.getWoodenPallet();
		parameter[4] = td.getUnloadPlat();
		parameter[5] = td.getPickCompany();
		parameter[6] = td.getPickAddress();
		parameter[7] = td.getPickContact();
		parameter[8] = td.getPickTel();
		parameter[9] = df.format(new Date());
		parameter[10] = td.getReceivingAddress();
		parameter[11] = td.getReceivingCompany();
		parameter[12] = td.getReceivingContact();
		parameter[13] = td.getReceivingTel();
		parameter[14] = td.getDirectiveID();
		parameter[15] = td.getContractNO();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean updateAddress(TransportAddress td) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[16];
		String sql = "update t_transport_address set Size=?,Weight=?,Quantity=?,WoodenPallet=?,UnloadPlat=?,"
				+ "PickCompany=?,PickAddress=?,PickContact=?,PickTel=?,OperatingTime=?,ReceivingAddress=?,ReceivingCompany=?,ReceivingContact=?,"
				+ "ReceivingTel=?,ContractNO=? where ID=?";
		parameter[0] = td.getSize();
		parameter[1] = td.getWeight();
		parameter[2] = td.getQuantity();
		parameter[3] = td.getWoodenPallet();
		parameter[4] = td.getUnloadPlat();
		parameter[5] = td.getPickCompany();
		parameter[6] = td.getPickAddress();
		parameter[7] = td.getPickContact();
		parameter[8] = td.getPickTel();
		parameter[9] = df.format(new Date());
		parameter[10] = td.getReceivingAddress();
		parameter[11] = td.getReceivingCompany();
		parameter[12] = td.getReceivingContact();
		parameter[13] = td.getReceivingTel();
		parameter[14] = td.getContractNO();
		parameter[15] = td.getID();

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public List<Map<String, Object>> getAddress(int id) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select ID AddrID,ReceivingAddress,ReceivingCompany,ReceivingContact,ReceivingTel,Size,Weight,Quantity,WoodenPallet,UnloadPlat,"
				+ "PickCompany,PickAddress,PickContact,PickTel,ContractNO from t_transport_address  where DirectiveID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public List<Map<String, Object>> getTotal(int id) {

		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();

		String sql = "select Total1,Total2,Total3,PackingDate,t_transporters.Transporters from t_transport_directive "
				+ "left join t_transporters on t_transporters.id = t_transport_directive.Transporter"
				+ " where t_transport_directive.ID=?";
		Object[] parameter = new Object[] { id };

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public int getID() {
		int id = 0;
		DBUtil db = new DBUtil();
		String sql = "select MAX(ID) ID  from t_transport_directive ";

		
		List<Map<String, Object>> ls = db.QueryToList(sql, null);

		if (ls.size() > 1) {
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}

		return id;
	}

	public boolean upadteStatus(TransportDirective td) {
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[2];
		String sql = "update t_transport_directive set Status=?  " + " where ID=?";
		parameter[0] = td.getStatus();
		parameter[1] = td.getID();

		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if (i >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean deleteAddress(int id) {

		DBUtil db = new DBUtil();

		String sql = "delete from t_transport_address  where ID=?";
		Object[] parameter = new Object[] { id };
		boolean flag = false;
		int count = db.executeUpdate(sql, parameter);
		if (count >= 1) {
			flag = true;
		}
		return flag;
	}

	public int getAllCounts(String type1, String searchContent1) {
		String field = "";
		int Transporter  = -1;
		switch (type1) {
		case "合同号":
			field="ContractNO";
			break;
		case "提货日期":
			field="PackingDate";
			break;
		case "运输商":
			field="Transporter";
			Transporter = 0;
				break;
		default:
			field="ContractNO";
			break;
		}
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql1 = "select count(ID) ? from t_transport_directive where "+field+" like ?";
		String sql2 = "select count(t_transport_directive.ID) ? from t_transport_directive"
				+ " left join t_transporters on t_transporters.ID = t_transport_directive.Transporter where t_transporters.Transporters like ?";
		Object[] parameter = new Object[2];
		parameter[0]="AllCounts";
		String sql = Transporter==0?sql2:sql1;
		parameter[1]="%"+searchContent1+"%";
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		return counts;
	}


	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1) {
		List<Map<String, Object>> ls = null;
		String field = "";
		int Transporter  = -1;
		switch (type1) {
		case "合同号":
			field="ContractNO";
			break;
		case "提货日期":
			field="PackingDate";
			break;
		case "运输商":
			field="Transporter";
			Transporter = 0;
				break;
		default:
			field="ContractNO";
			break;
		}
		DBUtil db = new DBUtil();
		String sql = "select t_transport_directive.ID,ContractNO,Total1,Total2,Total3,PackingDate,Status,t_transporters.Transporters Transporters"
				+ " from t_transport_directive left join  t_transporters "
				+ "on t_transporters.ID = t_transport_directive.Transporter "
				+ "where "+(Transporter==0?"t_transporters.Transporters":field)+" like ?"
				+ " order by PackingDate desc limit ?,?";
		System.out.println(sql);
		Object[] parameter = new Object[3];
		parameter[0]="%"+searchContent1+"%";
		parameter[1] = (page.getCurrentPage() - 1) * page.getRows();
		parameter[2] = page.getRows();
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	public int getAllCounts(String type1, String searchContent1, String type2, String searchContent2) {
		int counts = 0;
		DBUtil db = new DBUtil();
		String field1 = "";
		int Transporter1  = -1;
		switch (type1) {
		case "合同号":
			field1="ContractNO";
			break;
		case "提货日期":
			field1="PackingDate";
			break;
		case "运输商":
			field1="Transporter";
			Transporter1 = 0;
				break;
		default:
			field1="ContractNO";
			break;
		}
		String field2 = "";
		int Transporter2  = -1;
		switch (type2) {
		case "合同号":
			field2="ContractNO";
			break;
		case "提货日期":
			field2="PackingDate";
			break;
		case "运输商":
			field2="Transporter";
			Transporter2 = 0;
				break;
		default:
			field2="ContractNO";
			break;
		}
		String sql1 = "select count(t_transport_directive.ID) ? from t_transport_directive where "+field1+" like ? and "+field2+" like ? ";
		String sql2 = "select count(t_transport_directive.ID) ? from t_transport_directive"
				+ " left join t_transporters on t_transporters.ID=t_transport_directive.Transporter where "
				+(Transporter1==0?"t_transporters.Transporters":field1)+" like ? and "
				+(Transporter2==0?"t_transporters.Transporters":field2)+"like ?";
		String sql = Transporter1==0||Transporter2==0?sql2:sql1;
		Object[] parameter = new Object[3];
		parameter[0]="AllCounts";
		parameter[1]="%"+searchContent1+"%";
		parameter[2]="%"+searchContent2+"%";
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		if (ls.size() > 1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		return counts;
	}

	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1, String type2,
			String searchContent2) {
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String field1 = "";
		int Transporter1  = -1;
		switch (type1) {
		case "合同号":
			field1="ContractNO";
			break;
		case "提货日期":
			field1="PackingDate";
			break;
		case "运输商":
			field1="Transporter";
			Transporter1 = 0;
				break;
		default:
			field1="ContractNO";
			break;
		}
		String field2 = "";
		int Transporter2  = -1;
		switch (type2) {
		case "合同号":
			field2="ContractNO";
			break;
		case "提货日期":
			field2="PackingDate";
			break;
		case "运输商":
			field2="Transporter";
			Transporter2 = 0;
				break;
		default:
			field2="ContractNO";
			break;
		}
		String sql = "select t_transport_directive.ID,ContractNO,Total1,Total2,Total3,PackingDate,Status,t_transporters.Transporters Transporters"
				+ " from t_transport_directive left join t_transporters "
				+ "on t_transporters.ID = t_transport_directive.Transporter "
				+ "where "+(Transporter1==0?"t_transporters.Transporters":field1)+" like ? and "
				+(Transporter2==0?"t_transporters.Transporters":field2)+" like ?"
				+ " order by PackingDate desc limit ?,?";
		Object[] parameter = new Object[4];
		
		parameter[0]="%"+searchContent1+"%";
		parameter[1]="%"+searchContent2+"%";
		parameter[2] = (page.getCurrentPage() - 1) * page.getRows();
		parameter[3] = page.getRows();
		ls = db.QueryToList(sql, parameter);
		return ls;
	} 
	
	public boolean saveTransport(TransportDirective td,List<Map<String, Object>> goods) {
		DBUtil dbUtil = new DBUtil();
		String sql1 = null;
		String sql2 = null;
		Object [] parameter = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection conn = dbUtil.getConnection();
	
		try {
			conn.setAutoCommit(false);
			if(td.getID()==0){
				int ID = 0;
				sql1 = "insert into t_transport_directive (ContractNO,PackingDate,Total1,Total2,Total3,Status,Transporter) "
						+ "values (?,?,?,?,?,?,?)";
				ID = Integer.parseInt(dbUtil.insertGetIdNotClose(sql1, new Object[]{td.getContractNO(),td.getPackingDate(),td.getTotal1(),
						td.getTotal2(),td.getTotal3(),td.getStatus(),getTransporterID(td.getTransporter())}).toString());	
				td.setID(ID);
				
			}else{
				sql1 = "update t_transport_directive set ContractNO=?,PackingDate=?,Total1=?,Total2=?,Total3=?,Transporter=? "
						+ " where ID=?";
				dbUtil.executeUpdateNotClose(sql1, new Object[]{td.getContractNO(),td.getPackingDate(),td.getTotal1(),
						td.getTotal2(),td.getTotal3(),getTransporterID(td.getTransporter()),td.getID()});		
			}
			

			for(int i = 0;i < goods.size();i ++){
				if((int)goods.get(i).get("AddrID")==0){
					sql2 = "insert into t_transport_address (Size,Weight,Quantity,WoodenPallet,UnloadPlat,"
							+ "PickCompany,PickAddress,PickContact,PickTel,OperatingTime,ReceivingAddress,ReceivingCompany,ReceivingContact,"
							+ "ReceivingTel,DirectiveID,ContractNO) " + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					parameter = new Object[16];
					parameter[0] = goods.get(i).get("Size");
					parameter[1] = goods.get(i).get("Weight");
					parameter[2] = goods.get(i).get("Quantity");
					parameter[3] = goods.get(i).get("WoodenPallet");
					parameter[4] = goods.get(i).get("UnloadPlat");
					parameter[5] = goods.get(i).get("PickCompany");
					parameter[6] = goods.get(i).get("PickAddress");
					parameter[7] = goods.get(i).get("PickContact");
					parameter[8] = goods.get(i).get("PickTel");
					parameter[9] = df.format(new Date());
					parameter[10] = goods.get(i).get("ReceivingAddress");
					parameter[11] = goods.get(i).get("ReceivingCompany");
					parameter[12] = goods.get(i).get("ReceivingContact");
					parameter[13] = goods.get(i).get("ReceivingTel");
					parameter[14] = td.getID();
					parameter[15] = goods.get(i).get("ContractNO");
					dbUtil.executeUpdateNotClose(sql2, parameter);
					
				}else{
				
					sql2 = "update t_transport_address set Size=?,Weight=?,Quantity=?,WoodenPallet=?,UnloadPlat=?,"
				+ "PickCompany=?,PickAddress=?,PickContact=?,PickTel=?,OperatingTime=?,ReceivingAddress=?,ReceivingCompany=?,ReceivingContact=?,"
				+ "ReceivingTel=?,ContractNO=? where ID=?";
					parameter = new Object[16];
					parameter[0] = goods.get(i).get("Size");
					parameter[1] = goods.get(i).get("Weight");
					parameter[2] = goods.get(i).get("Quantity");
					parameter[3] = goods.get(i).get("WoodenPallet");
					parameter[4] = goods.get(i).get("UnloadPlat");
					parameter[5] = goods.get(i).get("PickCompany");
					parameter[6] = goods.get(i).get("PickAddress");
					parameter[7] = goods.get(i).get("PickContact");
					parameter[8] = goods.get(i).get("PickTel");
					parameter[9] = df.format(new Date());
					parameter[10] = goods.get(i).get("ReceivingAddress");
					parameter[11] = goods.get(i).get("ReceivingCompany");
					parameter[12] = goods.get(i).get("ReceivingContact");
					parameter[13] = goods.get(i).get("ReceivingTel");
					parameter[15] = goods.get(i).get("AddrID");
					parameter[14] = goods.get(i).get("ContractNO");
					dbUtil.executeUpdateNotClose(sql2, parameter);
					
				}
				
			}
			conn.commit();
			return true;
			
		} catch (Exception e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		
		}finally {
			dbUtil.closed();
		}
		
	}
}
