/**
 * 
 */
package com.eoulu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Equipment;
import com.eoulu.util.DBUtil;

import com.mysql.jdbc.Connection;

/**
 * @author zhangkai
 *
 */
public class EquipmentDao {

	/**
	 * ͨ��equipmentID��ȡequipment
	 * 
	 * @param equipment  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getEquipmentByID(Equipment equipment){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_equipment where ID=?";
		Object[] parameter = new Object[]{equipment.getID()};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}


	
	/**
	 * ��ȡ�����豸������
	 * */
	public int getEquipmentCounts(){
		DBUtil db = new DBUtil();
		String sql="select count(ID) allCounts from t_equipment";
		Object[] parameter = new Object[0];

		List<Map<String,Object>> ls;

		ls = db.QueryToList(sql, parameter);
		
		int result = ls.size();
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("allCounts").toString());
		return result;
	}
	
	
	
	
	
	/**
	 * ���չ�Ӧ�̲�ѯ��������
	 * */
	public int getEquipmentCounts(int suppiler,String model){
		List<Map<String,Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql = "select count(ID) allCounts from t_equipment  where t_equipment.Supplier=? and t_equipment.Model like ?";
		Object[] parameter = new Object[]{suppiler,"%"+model+"%"};
		
		

		ls = db.QueryToList(sql, parameter);
		
		int result = ls.size();
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("allCounts").toString());
		return result;	
	}

	
//	@Test
//	public void test(){
//		System.out.println(getEquipmentCounts(18,""));
//	}
	/**
	 * ���������е��豸
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * 
	 * */
	public List<Map<String, Object>> getAllEquipment(){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_equipment";
		Object[] parameter = new Object[0];


		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ���豸�������豸  ģ������
	 * 
	 * 
	 * �з�ҳ
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * 
	 * */
	public List<Map<String, Object>> getEquipmentByName(String equipment,Page page){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="SELECT t_equipment.ID,t_equipment.DeviceName,t_equipment.Model,t_equipment.Supplier SupplierID,t_equipment.Remarks,t_supplier.Name SupplierName FROM t_equipment LEFT JOIN t_supplier ON t_equipment.Supplier=t_supplier.ID where DeviceName like ?";
		Object[] parameter = new Object[]{"%"+equipment+"%"};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ���豸�������豸  ģ������
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * 
	 * */
	public List<Map<String, Object>> getEquipmentByName(String equipment){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_equipment where Model like ?"; 
		Object[] parameter = new Object[]{"%"+equipment+"%"};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * �Զ����������豸  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * 
	 * */
	public List<Map<String, Object>> getEquipmentByOrderID(String OrderID){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select t_order_info.ID,t_order_info.Number,t_equipment.ID equipmentID,t_equipment.Model,t_equipment.Remarks,t_equipment.Price, "
				+ "t_quotes.RMBQuotes,t_quotes.USDQuotes,t_payment_terms.`Condition` from t_order_info  "
				+ "LEFT JOIN t_equipment on t_order_info.EquipmentModel=t_equipment.ID "
				+ "LEFT JOIN t_quotes on t_order_info.OrderID=t_quotes.OrderID "
				+ "LEFT JOIN t_order on t_order.ID=t_order_info.OrderID "
				+ "LEFT JOIN t_payment_terms on t_quotes.PaymentTerms=t_payment_terms.ID "
				+ "where t_order_info.OrderID=?"; 
		
		Object[] parameter = new Object[]{OrderID};
		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * ���豸���ƻ�ȡ�豸ID
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * 
	 * */
	public int getEquipmentIDByName(String equipment){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select ID from t_equipment where Model=?"; 
		Object[] parameter = new Object[]{equipment};


		ls = db.QueryToList(sql, parameter);
		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		return result;
	}


	/**
	 * ����Ʒ�ͺŲ������ݿ���
	 * 
	 * 
	 * @return ����ɹ�����true��ʧ�ܷ���false
	 * */
	public boolean insert(String model,String remarks){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "insert into t_equipment (Model,Remarks) values (?,?)";
		Object[] parameter = new Object[]{model,remarks};


		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}


	/**
	 * ����Ʒ�ͺŲ������ݿ���
	 * 
	 * �������
	 * 
	 * @return ����ɹ�����true��ʧ�ܷ���false
	 * @throws SQLException 
	 * */
	public boolean insert(String model,String remarks,DBUtil db) throws SQLException{
		boolean flag = false;

		String sql = "insert into t_equipment (Model,Remarks) values (?,?)";
		Object[] parameter = new Object[]{model,remarks};


		int result = db.executeUpdateNotClose(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}

	/**
	 * �޸Ĳ�Ʒ�ͺű������
	 * @throws SQLException 
	 * */
	public boolean modify(String model,Object[] para,DBUtil db) throws SQLException{

		boolean flag = false;

		String sql = "update t_equipment set EquipmentUnit=?,DeliveryTime=?,SourceArea=?,ItemCode=?,CommodityCategory=?,Suppiler=? where t_equipment.Model=?";
		Object[] parameter = new Object[7];
		
		parameter[0] = para[0];
		parameter[1] = para[1];
		parameter[2] = para[2];
		parameter[3] = para[3];
		parameter[4] = para[4];
		parameter[5] = para[5];
		parameter[6] = para[6];


		int result = db.executeUpdateNotClose(sql, parameter);

		flag = result>0?true:false;

		return flag;

	}
	
	
	/**
	 * ���չ�Ӧ��+�ͺ�����ѯ�豸��Ϣ,�з�ҳ
	 * */
	public List<Map<String,Object>> getEquipmentBySupiler(Page page,int suppiler,String model){
		List<Map<String,Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql = "select t_equipment.ID,t_equipment.Model,t_equipment.Remarks,t_equipment.EquipmentUnit,t_equipment.DeliveryTime,"
				+ "t_equipment.SourceArea,t_equipment.ItemCode,t_equipment.CommodityCategory,t_supplier.Name Supplier from t_equipment "
				+ "left join t_supplier on t_equipment.Supplier=t_supplier.ID where t_equipment.Supplier=? and t_equipment.Model like ? limit ?,?";
		Object[] parameter = new Object[]{suppiler,"%"+model+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		
		
		ls = db.QueryToList(sql, parameter);
		
		return ls;		
	}
	
	
	
	/**
	 * ���չ�Ӧ��+�ͺ�����ѯ�豸��Ϣ,�з�ҳ
	 * */
	public List<Map<String,Object>> getEquipmentByPage(Page page){
		List<Map<String,Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql = "select t_equipment.ID,t_equipment.Model,t_equipment.Remarks,t_equipment.EquipmentUnit,t_equipment.DeliveryTime,"
				+ "t_equipment.SourceArea,t_equipment.ItemCode,t_equipment.CommodityCategory,t_supplier.Name Supplier from t_equipment "
				+ "left join t_supplier on t_equipment.Supplier=t_supplier.ID limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		
		ls = db.QueryToList(sql, parameter);
		
		return ls;		
	}
	
//	@Test
//	public void test(){
//		Page page = new Page();
//		page.setCurrentPage(900);
//		page.setRows(10);
//		System.out.println(getEquipmentByPage(page));
//	}
	
	/**
	 * ����µĲ�Ʒ�ͺ�
	 * 
	 * @return ��ӳɹ�����true�����ʧ�ܷ���false
	 * */
	public boolean equipmentAdd(Object[] parameter){
		boolean flag = false;
		
		DBUtil db = new DBUtil();
		
		String sql = "insert into t_equipment (Model,Remarks,EquipmentUnit,DeliveryTime,SourceArea,ItemCode,CommodityCategory,Supplier) values (?,?,?,?,?,?,?,?)";
		
		int result = db.executeUpdate(sql, parameter);
		
		flag = result>0?true:false;
		
		return flag;
	}
	
	
	
	/**
	 * ɾ����Ʒ�ͺ�
	 * 
	 * @return ɾ���ɹ�����true�����ʧ�ܷ���false
	 * */
	public boolean delete(int id){
		boolean flag = false;
		
		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();
		
		
		try {
			conn.setAutoCommit(false);
			
			String sql = "delete from t_equipment where t_equipment.ID=?";
			Object[] parameter = new Object[]{id};
			
			int result = db.executeUpdate(sql, parameter);
			
			if(result>1)
				conn.rollback();
				
				
			flag = result==1?true:false;
			
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return flag;
	}
	
	
	/**
	 * �޸Ĳ�Ʒ�ͺ���Ϣ
	 * */
	public boolean modifyEquipment(Object[] para,int id){
		boolean flag = false;
		
		DBUtil db = new DBUtil();
		String sql = "update t_equipment set EquipmentUnit=?,DeliveryTime=?,SourceArea=?,ItemCode=?,CommodityCategory=?,Supplier=? where t_equipment.ID=?";
		Object[] parameter = new Object[7];
		
		parameter[0] = para[0];
		parameter[1] = para[1];
		parameter[2] = para[2];
		parameter[3] = para[3];
		parameter[4] = para[4];
		parameter[5] = para[5];
		parameter[6] = id;
		int result = db.executeUpdate(sql, parameter);
		flag = result>0?true:false;
		return flag;		
		
	}



	public List<Map<String, Object>> getAllEquipmentInfo() {
		String sql = "select ID,Model,Remarks,(select Name from t_supplier where ID = t_equipment.Supplier) Supplier,InitialQuantity from t_equipment;";
		DBUtil db = new DBUtil();
		ResultSet rs = db.Query(sql, null);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ID", rs.getObject("ID"));
				map.put("Model", rs.getObject("Model"));
				map.put("Remarks", rs.getObject("Remarks"));
				map.put("Supplier", rs.getObject("Supplier"));
				map.put("InitialQuantity", rs.getObject("InitialQuantity"));
				list.add(map);
			}
			db.closed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean isExist(String model){
		DBUtil db = new DBUtil();
		String sql = "select * from t_equipment where Model=?";
		Object[] param = new Object[]{model};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		boolean flag = false;
		if(ls.size()>1){
			flag = true;
		}
		return flag;
	}
	

//	@Test
//	public void test(){
//		Object[] parameter = new Object[]{"CC2530","��ע","PCS","PCS","PCS","102552","ccs","10"};
//		System.out.println(equipmentAdd(parameter));
//	}

}
