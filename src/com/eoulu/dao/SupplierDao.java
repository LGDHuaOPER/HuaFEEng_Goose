/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.Supplier;
import com.eoulu.entity.SupplierBank;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

/**
 * @author zhangkai
 *
 */
public class SupplierDao {

	
	/**
	 * ��ȡ���еĹ�Ӧ����Ϣ
	 * */
	public List<Map<String,Object>> getAllSupplier(){
		
		String sql = "select ID,Name,Contact,ContactInfo from t_supplier";
		
		return new DBUtil().QueryToList(sql, new Object[0]);
		
	}
	
	
	/**
	 * ���ݹ�Ӧ�̵����ֻ�ȡID
	 * */
	public int getID(String suppilerName){
		String sql = "select t_supplier.ID from t_supplier where t_supplier.Name=?";
		Object[] parameter = new Object[]{suppilerName};
		DBUtil db = new DBUtil();
		
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		return result;
	}
	
	
	public List<Map<String,Object>> querySupplier(String key){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_supplier.ID,t_supplier.Name,t_supplier.Contact,t_supplier.ContactInfo from t_supplier where t_supplier.Name like ?";
		Object[] param = new Object[]{"%"+key+"%"};
		return dbUtil.QueryToList(sql, param);
	}
	
	public List<Map<String,Object>> querySupplierBank(String supplier){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID from t_supplier_bank where Supplier = ?";
		Object[] param = new Object[]{supplier};
		return dbUtil.QueryToList(sql, param);
	}
	
	
	
	/**
	 * ִ�в�ѯ��sql���
	 * */
	public List<Map<String,Object>> executeQuery(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		return ls;
		
	}
	
	
	/**
	 * ִ����ɾ�ĵ�sql���
	 * */
	
	public boolean insert(Supplier supplier,SupplierBank bank){
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		String sql1 = "insert into t_supplier (Name,Contact,ContactInfo,Address,Email,Product) values (?,?,?,?,?,?)";
		Object[] parameter1 = new Object[]{supplier.getName(),supplier.getContact(),supplier.getContactInfo(),supplier.getAddress()
				,supplier.getEmail(),supplier.getProduct()};
		String sql2 = "";
		Object[] parameter2 = null;
		if(querySupplierBank(supplier.getName()).size()>1){
			sql2 = "update t_supplier_bank set Company = ?,Account=?,Bank=?,TaxCode=?,SWIFTCode=? where Supplier = ?";
			parameter2 = new Object[]{bank.getCompany(),bank.getAccount(),bank.getBank(),bank.getTaxCode(),bank.getSWIFTCode(),bank.getSupplier()};
		}else{
			sql2 = "insert into t_supplier_bank(Supplier,Company,Account,Bank,TaxCode,SWIFTCode) values(?,?,?,?,?,?)";
			parameter2 = new Object[]{bank.getSupplier(),bank.getCompany(),bank.getAccount(),bank.getBank(),bank.getTaxCode(),bank.getSWIFTCode()};
		}
		try{
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, parameter1);
			dbUtil.executeUpdateNotClose(sql2, parameter2);
			conn.commit();
			return true;
		}catch(SQLException e){
			try {
				conn.rollback();
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}
		
	}
	
	public boolean update(Supplier supplier,SupplierBank bank){
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		String sql1 = "update t_supplier set Name=?,Contact=?,ContactInfo=?,Address=?,Email=?,Product=? where ID=?";
		Object[] parameter1 = new Object[]{supplier.getName(),supplier.getContact(),supplier.getContactInfo(),supplier.getAddress()
				,supplier.getEmail(),supplier.getProduct(),supplier.getID()};

		String sql2 = "";
		Object[] parameter2 = null;
		if(querySupplierBank(supplier.getName()).size()>1){
			sql2 = "update t_supplier_bank set Company = ?,Account=?,Bank=?,TaxCode=?,SWIFTCode=? where Supplier = ?";
			parameter2 = new Object[]{bank.getCompany(),bank.getAccount(),bank.getBank(),bank.getTaxCode(),bank.getSWIFTCode(),bank.getSupplier()};
		}else{
			sql2 = "insert into t_supplier_bank(Supplier,Company,Account,Bank,TaxCode,SWIFTCode) values(?,?,?,?,?,?)";
			parameter2 = new Object[]{bank.getSupplier(),bank.getCompany(),bank.getAccount(),bank.getBank(),bank.getTaxCode(),bank.getSWIFTCode()};
		}
	
		try{
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, parameter1);
			dbUtil.executeUpdateNotClose(sql2, parameter2);
			conn.commit();
			return true;
		}catch(SQLException e){
			try {
				conn.rollback();
				conn.close();
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
		return false;
		
	}

	public int insertSupplier(String supplier) {


		String sql = "insert into t_supplier (Name) values (?)";
		Object[] parameter = new Object[]{supplier};

		Object id = 0;
		try {
			id = new DBUtil().insertGetId(sql, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Integer.parseInt(String.valueOf(id));
	}
}
