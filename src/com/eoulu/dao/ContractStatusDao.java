/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;


import com.eoulu.entity.Authority;
import com.eoulu.entity.ContractStatus;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class ContractStatusDao {

	/**
	 * 通过Status获取ID
	 * 
	 * @param contractStatus  
	 * 
	 * @return List 第一行为列名   以后为数据行
	 * */
	public List<Map<String, Object>> getContractStatusByID(ContractStatus contractStatus){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_contract_status where ID=?";
		Object[] parameter = new Object[]{contractStatus.getID()};


		ls = db.QueryToList(sql, parameter);
		return ls;
	}


	/**
	 * 通过contractStatusID获取contractStatus
	 * 
	 * @param contractStatus  
	 * 
	 * @return int  0为没有数据   大于0为ID
	 * */
	public int getID(String contractStatus){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select ID from t_contract_status where Status=?";
		Object[] parameter = new Object[]{contractStatus};


		ls = db.QueryToList(sql, parameter);
		int id=0;
		if(ls.size()>1)
			id=Integer.parseInt(ls.get(1).get("ID").toString());
		return id;
	}

	/**
	 * 获取所有的合同状态数据
	 * */
	public List<Map<String,Object>> getAllStatus(){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select t_contract_status.ID,t_contract_status.Status from t_contract_status";
		Object[] parameter = new Object[0];


		ls = db.QueryToList(sql, parameter);
		return ls;
	}


}
