package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.SupplierBank;
import com.eoulu.util.DBUtil;

public class SupplierBankDao {
	
	public boolean addSupplierBank(SupplierBank bank){
		DBUtil db = new DBUtil();
		String sql = "insert into t_supplier_bank(Supplier,Company,Account,Bank) values(?,?,?,?)";
		Object[] param = new Object[4];
		param[0] = bank.getSupplier();
		param[1] = bank.getCompany();
		param[2] = bank.getAccount();
		param[3] = bank.getBank();
		int result = db.executeUpdate(sql, param);
		return result > 0?true:false;
			
	}
	public List<Map<String, Object>> querySupplier(String supplier){
		DBUtil db = new DBUtil();
		String sql = "select * from t_supplier_bank where Supplier = ?";
		Object[] param = new Object[]{supplier};
		List<Map<String, Object>> list = db.QueryToList(sql, param);
		return list;
	}
	public boolean updateSupplierBank(SupplierBank bank){
		DBUtil db = new DBUtil();
		String sql = "update t_supplier_bank set Company = ?,Account = ?,Bank=? where Supplier = ?";
		Object[] param = new Object[4];
		param[3] = bank.getSupplier();
		param[0] = bank.getCompany();
		param[1] = bank.getAccount();
		param[2] = bank.getBank();
		int result = db.executeUpdate(sql, param);
		return result > 0?true:false;
			
	}


}
