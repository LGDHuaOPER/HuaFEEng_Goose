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
 *������е����t_requirement_classify ��
 */
public class RequirementClassifyDao {

	public List<Map<String,Object>> getAllRequirementClassify(){

		List<Map<String,Object>> ls;
		String sql = "select * from t_requirement_classify";
		Object[] parameter = new Object[0];
		DBUtil db = new DBUtil();


		ls = db.QueryToList(sql, parameter);

		return ls;
	}

	
	/**
	 * ͨ���������ѯ��ID
	 * 
	 * @return 0û�����ֵ   ����ֵ����1���������
	 * */
	public int classifyIsExit(String classify){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement_classify where Classify=?";
		Object[] parameter = new Object[]{classify};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
	}
	
	
	
	/**
	 * ���������Ϣ
	 * */
	public int insert(String classify){
		int result =  0;
		
		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement_classify (Classify) values (?)";
		Object[] parameter = new Object[]{classify};
		
		result = db.executeUpdate(sql, parameter);
		
		
		
		return result;
	}

//	@Test
//	public void test(){
//	System.out.println(classifyIsExit("��Һ�"));	
//	}
	
}
