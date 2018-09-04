package com.eoulu.util;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.junit.Test;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

/**
 * ���ݿ����������
 * 
 * 
 * @author zhangkai
 * @version 1.0
 * @date 2017/3/20
 * */
public class DBUtil {

	Properties pro = new Properties();

	private String driver = null;
	private String url = null,user = null, password=null;
	private Connection connection = null;
	private PreparedStatement pst = null;
	ResultSet rs = null;


	public DBUtil(){
		try {
			pro.load(DBUtil.class.getResourceAsStream("jdbc.properties"));
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			try {
				Class.forName(driver);
				connection = (Connection) DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * 
	 * ��ȡ���ݿ�����
	 * 
	 * 
	 * @return Connection
	 */
	public Connection getConnection(){


		return connection;
	}
	public ResultSet Query(String sql){

		rs = null;
		try {
			pst = (PreparedStatement) connection.prepareStatement(sql);
			rs = pst.executeQuery();//永远不能为null
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 
	 * ִ�в�ѯsql���
	 * 
	 * @param sql sql���
	 * 
	 * @param parameter SQL����еĲ���
	 * 
	 * @return ResultSet<P>������������ر����ݿ�����,���÷���closed();</p>
	 */
	public ResultSet Query(String sql, Object[] parameter){

		rs = null;
		int paraCount =0;
		if(parameter!=null){
			paraCount=parameter.length;
		}
		try {
			pst = (PreparedStatement) connection.prepareStatement(sql);
			if(paraCount!=0){
				if(!parameter[0].equals("库存")){
					for(int i = 0; i<paraCount; i++){
						pst.setObject(i+1, parameter[i]);
					}
				}else{
					for(int i = 1; i<paraCount; i++){
						pst.setObject(i, parameter[i]);
						System.out.println("各个问号："+parameter[i]);
					}
				}
			}
			
			rs = pst.executeQuery();//永远不能为null
		//	System.out.println(rs);
//			while(rs.next()){
//				
//			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 查询刚插入记录的ID
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object insertGetId(String sql, Object[] params) throws Exception {  
		int paraCount = params.length;
        try {  
           
            pst = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
            for(int i = 0; i<paraCount; i++){
				pst.setObject(i+1, params[i]);
			}
			 pst.executeUpdate();

            rs = pst.getGeneratedKeys();  
              
            Object retId = null;  
            if (rs.next()){  
                retId = rs.getObject(1);  
            }else{  
                throw new Exception("insert or generate keys failed..");  
           
            } 
            return retId;  
        } catch (Exception e) {  
            throw e;  
        } finally {  
           closed();  
        }  
  
    }  
	
	public Object insertGetIdNotClose(String sql, Object[] params) throws Exception {  
		int paraCount = params.length;
        try {  
           
            pst = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
            for(int i = 0; i<paraCount; i++){
				pst.setObject(i+1, params[i]);
			}
			 pst.executeUpdate();

            rs = pst.getGeneratedKeys();  
              
            Object retId = null;  
            if (rs.next()){  
                retId = rs.getObject(1);  
            }else{  
                throw new Exception("insert or generate keys failed..");  
           
            } 
            return retId;  
        } catch (Exception e) {  
            throw e;  
        } 
  
    }

	/**
	 * 
	 * ִ�в�ѯsql���
	 * 
	 * @param sql sql���
	 * 
	 * @param parameter SQL����еĲ���
	 * 
	 * @return List,��һ������Ϊ����������Ϊ��������  size()>1Ϊ������  ����û�в�ѯ������
	 */
	public List<Map<String,Object>> QueryToList(String sql, Object[] parameter){

		
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		rs = null;
		int paraCount =0;
		if(parameter!=null){
			paraCount=parameter.length;
		}
		
		try {

			rs = Query(sql,parameter);

			/*ResultSet To List Start*/
			ResultSetMetaData rsm =  (ResultSetMetaData) rs.getMetaData();//返回 ResultSetMetaData 对象。 

			int columnCounts = rsm.getColumnCount(),column=0;
			String[] columnName = new String[columnCounts];

			/*������� ��ʼ*/
			Map<String,Object> mapTitle = new TreeMap<String,Object>();
			while(column++<columnCounts){
				mapTitle.put("col"+column, rsm.getColumnLabel(column));
				columnName[column-1]=rsm.getColumnLabel(column);
			}
			ls.add(mapTitle);
			/*������� ����*/

			/*���������   ��ʼ*/
			columnCounts++;
			int times=1;
			while(rs.next()){
				Map<String,Object> map = new TreeMap<String,Object>();
				for(int i=1; i<columnCounts; i++){
					map.put(columnName[i-1], rs.getString(i)==null?"--":rs.getString(i));
				}
				ls.add(map);
			}
			/*���������   ����*/
			/*ResultSet To List End*/

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closed();
		}
		return ls;
	}



	/**
	 * 
	 * ִ�в�ѯsql���,����ʹ�õģ�����ر�����
	 * 
	 * @param sql sql���
	 * 
	 * @param parameter SQL����еĲ���
	 * 
	 * @param para  ���ֲ�����ʵ������  
	 * 
	 * @return List,��һ������Ϊ����������Ϊ��������  size()>1Ϊ������  ����û�в�ѯ������
	 */
	public List<Map<String,Object>> QueryToList(String sql, Object[] parameter,boolean para){

		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		rs = null;
		int paraCount = parameter.length;
		try {

			rs = Query(sql,parameter);

			/*ResultSet To List Start*/
			ResultSetMetaData rsm =  (ResultSetMetaData) rs.getMetaData();

			int columnCounts = rsm.getColumnCount(),column=0;
			String[] columnName = new String[columnCounts];

			/*������� ��ʼ*/
			Map<String,Object> mapTitle = new TreeMap<String,Object>();
			while(column++<columnCounts){
				mapTitle.put("col"+column, rsm.getColumnLabel(column));
				columnName[column-1]=rsm.getColumnLabel(column);
			}
			ls.add(mapTitle);
			/*������� ����*/

			/*���������   ��ʼ*/
			columnCounts++;
			int times=1;
			while(rs.next()){
				Map<String,Object> map = new TreeMap<String,Object>();
				for(int i=1; i<columnCounts; i++){
					map.put(columnName[i-1], rs.getString(i)==null?"--":rs.getString(i));
				}
				ls.add(map);
			}
			/*���������   ����*/
			/*ResultSet To List End*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}




	/**
	 * ���ݿ���ɾ��
	 * 
	 * @param sql sql���
	 * 
	 * @param parameter SQL����еĲ���
	 * 
	 * @return ��Ӱ�������
	 * */
	public int executeUpdate(String sql,Object[] parameter){

		
		int paraCount = parameter.length;
		int result = 0;
		try {
			pst = (PreparedStatement) connection.prepareStatement(sql);
			for(int i = 0; i<paraCount; i++){
				pst.setObject(i+1, parameter[i]);
			}
			result = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closed();
		}
		return result;
	}

public int executeUpdateOpen(String sql,Object[] parameter){

		
		int paraCount = parameter.length;
		int result = 0;
		try {
			pst = (PreparedStatement) connection.prepareStatement(sql);
			for(int i = 0; i<paraCount; i++){
				pst.setObject(i+1, parameter[i]);
			}
			result = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

 
	/**
	 * ���ݿ���ɾ��,���ر����ӣ���Ҫ�ֶ��رգ������ʱ��ʹ��
	 * 
	 * @param sql sql���
	 * 
	 * @param parameter SQL����еĲ���
	 * 
	 * @return ��Ӱ�������
	 * @throws SQLException 
	 * */
	public int executeUpdateNotClose(String sql,Object[] parameter) throws SQLException{

		
		int paraCount = parameter.length;
		int result = 0;


		pst = (PreparedStatement) connection.prepareStatement(sql);
		for(int i = 0; i<paraCount; i++){
			pst.setObject(i+1, parameter[i]);
		}
		result = pst.executeUpdate();
		
		
		//System.out.println("DBUtil:"+result);
		return result;
	}
	
public int executeUpdateNotClose(String sql) throws SQLException{

		int result = 0;
		pst = (PreparedStatement) connection.prepareStatement(sql);
		result = pst.executeUpdate();
	
		return result;
	}



	@Test
	public void test(){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_order_info";
		Object[] parameter = new Object[0];


		ls = db.QueryToList(sql, parameter);
		System.out.println(new Gson().toJson(ls));

	}


	/**
	 * 
	 * �ر����ݿ�����
	 * 
	 * 
	 */
	public void closed(){

		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		if(pst!=null)
			try {
				pst.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		if(connection!=null)
			try {  
				connection.close();  
			} catch (SQLException e) {  
				e.printStackTrace();  
			}  


	}

	/**
	 * 查询记录数
	 * @param sql
	 * @param param
	 * @return
	 */
	public int getCountsByName(String sql,Object[] param){
		int counts = 0;
		DBUtil db = new DBUtil();
		
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1){
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}
	
}
