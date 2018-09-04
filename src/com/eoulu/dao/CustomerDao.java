/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Authority;
import com.eoulu.entity.Customer;
import com.eoulu.entity.TaxInfo;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

/**
 * @author zhangkai
 *
 */
public class CustomerDao {


	/**
	 * ���տͻ����Ʋ�ѯ�ͻ�ID
	 * */
	public int customerIsExist(String customerName,DBUtil db){
		
		String sql ="select ID from t_customer where t_customer.CustomerName=?";
		Object[] parameter = new Object[]{customerName};
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter,false);
		
		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("ID").toString());
		
		
		return result;
		
	}
	
	
	
	/**
	 * ͨ��customerID��ȡcustomer
	 * 
	 * @param customer  
	 * 
	 * @return List ��һ��Ϊ����   �Ժ�Ϊ������
	 * */
	public List<Map<String, Object>> getCustomerByID(Customer customer){
		List<Map<String, Object>> ls = null;

		DBUtil db = new DBUtil();
		String sql="select * from t_customer where ID=?";
		Object[] parameter = new Object[]{customer.getID()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}


	/**
	 * �ͻ���Ϣ����
	 * 
	 * @return ����ɹ�true������ʧ��false
	 * */
	public boolean insert(Customer customer){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "insert into t_customer (ID,CustomerName,Contact,ContactInfo1,ContactInfo2,"
				+ "ContactAddress,Area,ZipCode,FaxNumber,Email,CustomerClassify,ShorthandCoding,"
				+ "EnglishName,Website,CustomerDepartment,DepartmentEnglish,CustomerLevel,City,AreaName) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[19];
		parameter[0] = customer.getID();
		parameter[1] = customer.getCustomerName();
		parameter[2] = customer.getContact();
		parameter[3] = customer.getContactInfo1();
		parameter[4] = customer.getContactInfo2();
		parameter[5] = customer.getContactAddress();
		parameter[6] = customer.getArea();
		parameter[7] = customer.getZipCode();
		parameter[8] = customer.getFaxNumber();
		parameter[9] = customer.getEmail();
		parameter[10] = customer.getCustomerClassify();
		parameter[11] = customer.getShorthandCoding()==null?"":customer.getShorthandCoding();
		parameter[12] = customer.getEnglishName();
		parameter[13] = customer.getWebsite();
		parameter[14] = customer.getCustomerDepartment();
		parameter[15] = customer.getDepartmentEnglish();
		parameter[16] = customer.getCustomerLevel();
		parameter[17] = customer.getCity();
		parameter[18] = customer.getAreaName();
		

		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}


	
	/**
	 * �ͻ���Ϣ����
	 * 
	 * @return ����ɹ�true������ʧ��false
	 * @throws SQLException 
	 * */
	public boolean insert(Customer customer,DBUtil db) throws SQLException{
		boolean flag = false;

		String sql = "insert into t_customer (ID,CustomerName,Contact,ContactInfo1,ContactInfo2,ContactAddress,Area,ZipCode,FaxNumber,Email,CustomerClassify,ShorthandCoding,EnglishName,Website,CustomerDepartment,DepartmentEnglish) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[16];
		parameter[0] = customer.getID();
		parameter[1] = customer.getCustomerName();
		parameter[2] = customer.getContact();
		parameter[3] = customer.getContactInfo1();
		parameter[4] = customer.getContactInfo2();
		parameter[5] = customer.getContactAddress();
		parameter[6] = customer.getArea();
		parameter[7] = customer.getZipCode();
		parameter[8] = customer.getFaxNumber();
		parameter[9] = customer.getEmail();
		parameter[10] = customer.getCustomerClassify();
		parameter[11] = customer.getShorthandCoding()==null?"":customer.getShorthandCoding();
		parameter[12] = customer.getEnglishName();
		parameter[13] = customer.getWebsite();
		parameter[14] = customer.getCustomerDepartment();
		parameter[15] = customer.getDepartmentEnglish();
		int result = db.executeUpdateNotClose(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}
	
	
	
	/**
	 * ɾ���ͻ���Ϣ
	 * 
	 * @return ɾ���ɹ�����true�����ʧ�ܷ���false
	 * */
	public boolean delete(int id){
		boolean flag = false;

		DBUtil db = new DBUtil();
		Connection conn = db.getConnection();


		try {
			conn.setAutoCommit(false);

			String sql = "delete from t_customer where t_customer.ID=?";
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
	 * ��ѯ�ͻ���Ϣ���з�ҳ
	 * */
	public List<Map<String,Object>> getCustomerByPage(Page page,String customerName){

		DBUtil db = new DBUtil();
		String sql = "select * from t_customer where t_customer.CustomerName like ? order by ID desc limit ?,?";
		List<Map<String,Object>> ls = null;
		Object[] parameter = new Object[]{"%"+customerName+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};


		ls = db.QueryToList(sql, parameter);



		return ls;

	}
	
	
	

	/**
	 * ��ѯ�ͻ���Ϣ�����������ղ�ѯ����
	 * */
	public int getCustomerCounts(String customerName){

		DBUtil db = new DBUtil();
		String sql = "select count(*) allCounts from t_customer where t_customer.CustomerName like ?";
		List<Map<String,Object>> ls = null;
		Object[] parameter = new Object[]{"%"+customerName+"%"};


		ls = db.QueryToList(sql, parameter);

        int result = 0;
		
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("allCounts").toString());			

		return result;

	}

	
	/**
	 * �޸Ŀͻ���Ϣ
	 * 
	 * @return �޸ĳɹ�true���޸�ʧ��false
	 * */
	public boolean modify(Customer customer){

		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "update t_customer set CustomerName=?,Contact=?,ContactInfo1=?,ContactInfo2=?,"
				+ "ContactAddress=?,Area=?,ZipCode=?,FaxNumber=?,Email=?,CustomerClassify=?,"
				+ "ShorthandCoding=?,EnglishName=?,Website=?,CustomerDepartment=?,DepartmentEnglish=?,"
				+ "CustomerLevel=?,City=?,AreaName=? where t_customer.ID=?";
		Object[] parameter = new Object[19];
		
		parameter[0] = customer.getCustomerName();
		parameter[1] = customer.getContact();
		parameter[2] = customer.getContactInfo1();
		parameter[3] = customer.getContactInfo2();
		parameter[4] = customer.getContactAddress();
		parameter[5] = customer.getArea();
		parameter[6] = customer.getZipCode();
		parameter[7] = customer.getFaxNumber();
		parameter[8] = customer.getEmail();
		parameter[9] = customer.getCustomerClassify();
		parameter[10] = customer.getShorthandCoding()==null?"":customer.getShorthandCoding();
		parameter[11] = customer.getEnglishName();
		parameter[12] = customer.getWebsite();
		parameter[13] = customer.getCustomerDepartment();
		parameter[14] = customer.getDepartmentEnglish();
		parameter[15] = customer.getCustomerLevel();
		parameter[18] = customer.getID();
		parameter[16] = customer.getCity();
		parameter[17] = customer.getAreaName();

		int result = db.executeUpdate(sql, parameter);


		flag = result>0?true:false;

		return flag;		

	}
	
	
	/**
	 * ��ѯ���еĿͻ���Ϣ
	 * */
	public List<Map<String,Object>> getAllCustomer(){
		String sql = "select t_customer.ID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1,t_customer.Email,t_customer.EnglishName from t_customer";
		Object[] parameter = new Object[0];
		
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, parameter);
	}
	
	
	public List<Map<String,Object>> getAllCustomer(String content){
		String sql = "select t_customer.ID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 from t_customer where CustomerName like ?";
		Object[] parameter = new Object[]{"%"+content+"%"};
		
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, parameter);
	}
	
	public boolean saveTaxInfo(TaxInfo taxInfo){
		String sql = "update t_customer set InvoiceTitle=?,TaxPayerIdentityNO=?,RegisterAddress=?,"
				+ "Telephone=?,DepositBank=?,Account=?,InvoiceRecepter=?,LinkAddress=?,LinkTel=?,"
				+ "LinkZipCode=? where ID=?";
		Object[] param = new Object[11];
		param[0] = taxInfo.getInvoiceTitle();
		param[1] = taxInfo.getTaxPayerIdentityNO();
		param[2] = taxInfo.getRegisterAddress();
		param[3] = taxInfo.getTelephone();
		param[4] = taxInfo.getDepositBank();
		param[5] = taxInfo.getAccount();
		param[6] = taxInfo.getInvoiceRecepter();
		param[7] = taxInfo.getLinkAddress();
		param[8] = taxInfo.getLinkTel();
		param[9] = taxInfo.getLinkEmail();
		param[10] = taxInfo.getID();
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getTaxInfo(int ID){
		String sql = "select InvoiceTitle,TaxPayerIdentityNO,RegisterAddress,"
				+ "Telephone,DepositBank,Account,InvoiceRecepter,LinkAddress,LinkTel,LinkZipCode "
				+ "from t_customer where ID=?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{ID});
		return list;
		
	}
	
	public List<Map<String, Object>> getTaxInfoForBill(String customerName,String contact){
		String sql = "select InvoiceTitle,TaxPayerIdentityNO,RegisterAddress,"
				+ "Telephone,DepositBank,Account,InvoiceRecepter,LinkAddress,LinkTel,LinkZipCode "
				+ "from t_customer where CustomerName=? and Contact = ?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{customerName,contact});

		return list;
		
	}
	
	


}
