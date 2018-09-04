/**
 * 
 */
package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 *������ȱ�t_requirement_quotes��
 */
public class RequirementQuotesDao {

	public List<Map<String,Object>> getAllRequirementQuotes(){

		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_quotes";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();


		ls = db.QueryToList(sql, parameter);

		return ls;
	}
	
	
	/**
	 * ͨ���Ƿ񱨼�����ѯ��ID
	 * 
	 * @return 0û�����ֵ   ����ֵ����1���������
	 * */
	public int quotesIsExit(String whetherQuotes){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_quotes where WhetherQuotes=?";
		Object[] parameter = new Object[]{whetherQuotes};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * �����Ƿ񱨼���Ϣ
	 * */
	public int insert(String whetherQuotes){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_quotes (WhetherQuotes) values (?)";
		Object[] parameter = new Object[]{whetherQuotes};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}
	
	
}
