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
	 * ͨ��Status��ȡID
	 * 
	 * @param contractStatus  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
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
	 * ͨ��contractStatusID��ȡcontractStatus
	 * 
	 * @param contractStatus  
	 * 
	 * @return int  0Ϊû������   ����0ΪID
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
	 * ��ȡ���еĺ�ͬ״̬����
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
