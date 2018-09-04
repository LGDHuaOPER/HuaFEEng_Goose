/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 * ������е�Ʒ��
 */
public class RequirementBrandDao {

	public List<Map<String,Object>> getAllRequirementBrand(){
		
		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_brand";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();
		
		
		ls = db.QueryToList(sql, parameter);
		
		return ls;
	}
	
	
	
	/**
	 * ͨ��Ʒ������ѯ��ID
	 * 
	 * @return 0û�����ֵ   ����ֵ����1���������
	 * */
	public int brandIsExit(String brand){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_brand where Brand=?";
		Object[] parameter = new Object[]{brand};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * ����Ʒ����Ϣ
	 * */
	public int insert(String brand){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_brand (Brand) values (?)";
		Object[] parameter = new Object[]{brand};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}
	
}
