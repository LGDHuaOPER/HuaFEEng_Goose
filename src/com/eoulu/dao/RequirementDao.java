/**
 * 
 */
package com.eoulu.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Requirement;
import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 */
public class RequirementDao {
	/**
	 * ������ֵ�ԣ�requirement�����Ϣ������������ʱ�򷽱㴦��ǰ̨�����ݣ�ǰ̨�������������ݿ��Ķ�Ӧ��ϵ
	 * */
	public static final Map<String, Object> classify_MAP; 


	static{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("客户名称", "CustomerName");
		map.put("区域", "Area");//requirementΪArea��t_areaΪAreaName
		map.put("需求来源", "DemandSources");//requirementΪDemandSources��t_requirement_sourceΪSource
		map.put("需求类别", "RequirementClassify");//requirementΪRequirementClassify��t_requirement_classifyΪClassify
		map.put("省份", "Province");
		map.put("销售负责人", "SalesMan");
		map.put("客户等级", "CustomerLevel");
		map.put("商务负责人", "BusinessMan");
		map.put("联系人","Contact");
		map.put("联系方式", "ContactInfo");
		map.put("Ref.No", "Ref.No");
		map.put("预计下单时间", "ExceptedPayTime");
		map.put("", "");
		classify_MAP = map;
	}

	/**
	 * �����������������
	 * 
	 * @return ������ȷ����true������ʧ�ܷ���false
	 * */
	public boolean insert(Requirement requirement){
		System.out.println(requirement.getRefNo()+"---"+requirement.getArea());
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "insert into t_requirement (SequenceNumber,RequirementDate,CustomerName,Area,Brand,RequirementContent,RequirementClassify,"
				+ "DemandSources,WhetherQuotes,SalesMan,SingleProbability,ExceptedPayTime,WhetherSingle,ProgressStatus,FollowPlan,"
				+ "QuotationNumber,USDQuotes,RMBQuotes,Province,Preparation,RefNo,BusinessMan) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] parameter = new Object[22];
		

		//������ֵ
		parameter[0] = requirement.getSequenceNumber();
		parameter[1] = requirement.getRequirementDate();
		parameter[2] = requirement.getCustomerName();
		parameter[3] = requirement.getArea();
		parameter[4] = requirement.getBrand();
		parameter[5] = requirement.getRequirementContent();
		parameter[6] = requirement.getRequirementClassify();
		parameter[7] = requirement.getDemandSources();
		parameter[8] = requirement.getWhetherQuotes();
		parameter[9] = requirement.getSalesMan();
		parameter[10] = requirement.getSingleProbability();
		parameter[11] = requirement.getExceptedPayTime();
		parameter[12] = requirement.getWhetherSingle();
		parameter[13] = requirement.getProgressStatus();
		parameter[14] = requirement.getFollowPlan();
		parameter[15] = requirement.getQuotationNumber();
		parameter[16] = requirement.getUSDQuotes();
		parameter[17] = requirement.getRMBQuotes();
		parameter[18] = requirement.getProvince();
		parameter[19] = requirement.getPreparation();
		parameter[20] = requirement.getRefNo();
		parameter[21] = requirement.getBusinessMan();
		
		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}
	
	public boolean delete(int ID){
		DBUtil db = new DBUtil();
		String sql = "delete from t_requirement where ID=?";
		Object[] param = new Object[]{ID};
		boolean flag = false;
		int i = 0;
		try {
			i = db.executeUpdateNotClose(sql, param);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	public List<Map<String,Object>> getProvince(){
		List<Map<String,Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,Province from t_province";
		ls = db.QueryToList(sql, null);
		return ls;
	}
	
	public int getProvinceID(String province){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_province where Province=?";
		Object[] parameter = new Object[]{province};
		
		
		ls = db.QueryToList(sql, parameter,true);
		
		int id = 0;
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}
	/**
	 * �����������������,�����������
	 * 
	 * @return ������ȷ����true������ʧ�ܷ���false
	 * @throws SQLException 
	 * */
	public boolean insert(Requirement requirement,DBUtil db) throws SQLException{
		boolean flag = false;

		String sql = "insert into t_requirement (SequenceNumber,RequirementDate,CustomerName,Area,Brand,RequirementContent,RequirementClassify,"
				+ "DemandSources,WhetherQuotes,SalesMan,SingleProbability,ExceptedPayTime,WhetherSingle,ProgressStatus,FollowPlan,"
				+ "QuotationNumber,USDQuotes,RMBQuotes,Province,CustomerLevel,Preparation) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[21];
		
		//������ֵ
		parameter[0] = requirement.getSequenceNumber();
		parameter[1] = requirement.getRequirementDate();
		parameter[2] = requirement.getCustomerName();
		parameter[3] = requirement.getArea();
		parameter[4] = requirement.getBrand();
		parameter[5] = requirement.getRequirementContent();
		parameter[6] = requirement.getRequirementClassify();
		parameter[7] = requirement.getDemandSources();
		parameter[8] = requirement.getWhetherQuotes();
		parameter[9] = requirement.getSalesMan();
		parameter[10] = requirement.getSingleProbability();
		parameter[11] = requirement.getExceptedPayTime();
		parameter[12] = requirement.getWhetherSingle();
		parameter[13] = requirement.getProgressStatus();
		parameter[14] = requirement.getFollowPlan();
		parameter[15] = requirement.getQuotationNumber();
		parameter[16] = requirement.getUSDQuotes();
		parameter[17] = requirement.getRMBQuotes();
		parameter[18] = requirement.getProvince();
		parameter[19] = requirement.getCustomerLevel();
		parameter[20] = requirement.getPreparation();

		int result = db.executeUpdateNotClose(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}



	/**
	 * �޸�������е�����
	 * 
	 * @return �޸���ȷ����true���޸�ʧ�ܷ���false
	 * */
	public boolean modiify(Requirement requirement){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "update t_requirement set SequenceNumber=?,RequirementDate=?,CustomerName=?,Area=?,Brand=?,RequirementContent=?,RequirementClassify=?,"
				+ "DemandSources=?,WhetherQuotes=?,SalesMan=?,SingleProbability=?,ExceptedPayTime=?,WhetherSingle=?,ProgressStatus=?,FollowPlan=?,"
				+ "QuotationNumber=?,USDQuotes=?,RMBQuotes=?,Province=?,RefNo=?,BusinessMan=? where ID=?";
		Object[] parameter = new Object[22];


		//������ֵ
		parameter[0] = requirement.getSequenceNumber();
		parameter[1] = requirement.getRequirementDate();
		parameter[2] = requirement.getCustomerName();
		parameter[3] = requirement.getArea();
		parameter[4] = requirement.getBrand();
		parameter[5] = requirement.getRequirementContent();
		parameter[6] = requirement.getRequirementClassify();
		parameter[7] = requirement.getDemandSources();
		parameter[8] = requirement.getWhetherQuotes();
		parameter[9] = requirement.getSalesMan();
		parameter[10] = requirement.getSingleProbability();
		parameter[11] = requirement.getExceptedPayTime();
		parameter[12] = requirement.getWhetherSingle();
		parameter[13] = requirement.getProgressStatus();
		parameter[14] = requirement.getFollowPlan();
		parameter[15] = requirement.getQuotationNumber();
		parameter[16] = requirement.getUSDQuotes();
		parameter[17] = requirement.getRMBQuotes();
		parameter[18] = requirement.getProvince();
		parameter[19] = requirement.getRefNo();
		parameter[20] = requirement.getBusinessMan();
		parameter[21] = requirement.getID();
		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}
	
	
	
	/**
	 * �޸�������е�����
	 * 
	 * @return �޸���ȷ����true���޸�ʧ�ܷ���false
	 * @throws SQLException 
	 * */
	public boolean modiifyCustomer(Requirement requirement,DBUtil db) throws SQLException{
		boolean flag = false;

		String sql = "update t_requirement set t_requirement.CustomerName=? where t_requirement.ID=?";
		Object[] parameter = new Object[2];


		//������ֵ
		parameter[0] = requirement.getCustomerName();
		parameter[1] = requirement.getID();
		




		int result = db.executeUpdateNotClose(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}


	/**
	 * ��ѯ������е����ݣ��з�ҳ����
	 * 
	 * @return list����
	 * */
	public List<Map<String,Object>> queryByPage(Page page,String classify1,String parameter1){
		List<Map<String,Object>> ls;
		DBUtil db = new DBUtil();
		String sql = null;
		Object[] parameter = null;
		
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+ 
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "Area":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "DemandSources":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "RequirementClassify":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "Province":
			sql = "select t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Province like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "SalesMan":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_sales_representative.`Name` like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "CustomerLevel":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.CustomerLevel like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "BusinessMan":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.BusinessMan like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "Contact":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.Contact like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "ContactInfo":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.ContactInfo1 like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "Ref.No":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RefNo like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "ExceptedPayTime":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.ExceptedPayTime like ? order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		case "":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) order by t_requirement.RequirementDate desc limit  ?,?";
			break;
		}
		parameter = new Object[]{"%"+parameter1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		ls = db.QueryToList(sql, parameter);


		return ls;
	}
	
	public List<Map<String,Object>> queryByPage(String classify1,String parameter1){
		List<Map<String,Object>> ls;
		DBUtil db = new DBUtil();
		String sql = null;
		Object[] parameter = null;
		
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+ 
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  order by t_requirement.RequirementDate desc";
			break;
		case "Area":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) order by t_requirement.RequirementDate desc ";
			break;
		case "DemandSources":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) order by t_requirement.RequirementDate desc ";
			break;
		case "RequirementClassify":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) order by t_requirement.RequirementDate desc ";
			break;
		case "Province":
			sql = "select t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Province like ? order by t_requirement.RequirementDate desc ";
			break;
		case "SalesMan":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_sales_representative.`Name` like ? order by t_requirement.RequirementDate desc ";
			break;
		case "CustomerLevel":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.CustomerLevel like ? order by t_requirement.RequirementDate desc ";
			break;
		case "BusinessMan":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.BusinessMan like ? order by t_requirement.RequirementDate desc ";
			break;
		case "Contact":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.Contact like ? order by t_requirement.RequirementDate desc ";
			break;
		case "ContactInfo":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.ContactInfo1 like ? order by t_requirement.RequirementDate desc ";
			break;
		case "Ref.No":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RefNo like ? order by t_requirement.RequirementDate desc ";
			break;
		case "ExceptedPayTime":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.ExceptedPayTime like ? order by t_requirement.RequirementDate desc ";
			break;
		case "":
			sql = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) order by t_requirement.RequirementDate desc ";
			break;
		}
		parameter = new Object[]{"%"+parameter1+"%"};
		ls = db.QueryToList(sql, parameter);


		return ls;
	}
	
	/**
	 * ��ϲ�ѯ������е����ݣ��з�ҳ����
	 * 
	 * @return list����
	 * */
	public List<Map<String,Object>> queryMixByPage(Page page,String classify1,String parameter1,String classify2,String parameter2){

		List<Map<String,Object>> ls;
		DBUtil db = new DBUtil();
		String sql1 = null;
		Object[] parameter = null;
		
//		System.out.println(area);
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+ 
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  ";
			break;
		case "Area":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		case "DemandSources":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) ";
			break;
		case "RequirementClassify":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) ";
			break;
		case "Province":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Province like ? ";
			break;
		case "SalesMan":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_sales_representative.`Name` like ? ";
			break;
		case "CustomerLevel":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.CustomerLevel like ? ";
			break;
		case "BusinessMan":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.BusinessMan like ? ";
			break;
			
		case "Contact":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.Contact like ? ";
			break;
		case "ContactInfo":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.ContactInfo1 like ? ";
			break;
		case "Ref.No":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.ExceptedPayTime like ? ";
			break;
		case "":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		}
		
		//ƴ��sql���
		String sql2=null;
		switch(classify_MAP.get(classify2).toString()){
		case "CustomerName":
			sql2 = " and t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  ";
			break;
		case "Area":
			sql2 = " and t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		case "DemandSources":
			sql2 = " and t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) ";
			break;
		case "RequirementClassify":
			sql2 = " and t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) ";
			break;
		case "Province":
			sql2 = " and t_requirement.Province like ?  ";
			break;
		case "SalesMan":
			sql2 = " and t_sales_representative.`Name` like ?  ";
			break;
		case "CustomerLevel":
			sql2 = " and t_customer.CustomerLevel like ?  ";
			break;
		case "BusinessMan":
			sql2 = " and t_requirement.BusinessMan like ?  ";
			break;
		case "Contact":
			sql2 = " and t_customer.Contact like ? ";
			break;
		case "ContactInfo":
			sql2 = " and t_customer.ContactInfo1 like ?  ";
			break;
		case "Ref.No":
			sql2 = " and t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql2 = " and t_requirement.ExceptedPayTime like ? ";
			break;
		case "":
			sql2 = " and t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		}
		parameter = new Object[]{"%"+parameter1+"%","%"+parameter2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		String sql=sql1+sql2+" order by t_requirement.RequirementDate desc limit  ?,?";
		ls = db.QueryToList(sql, parameter);

		return ls;
	}

	public List<Map<String,Object>> queryMixByPage(String classify1,String parameter1,String classify2,String parameter2){

		List<Map<String,Object>> ls;
		DBUtil db = new DBUtil();
		String sql1 = null;
		Object[] parameter = null;
		
//		System.out.println(area);
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+ 
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  ";
			break;
		case "Area":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		case "DemandSources":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) ";
			break;
		case "RequirementClassify":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) ";
			break;
		case "Province":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Province like ? ";
			break;
		case "SalesMan":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_sales_representative.`Name` like ? ";
			break;
		case "CustomerLevel":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.CustomerLevel like ? ";
			break;
		case "BusinessMan":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.BusinessMan like ? ";
			break;
			
		case "Contact":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.Contact like ? ";
			break;
		case "ContactInfo":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_customer.ContactInfo1 like ? ";
			break;
		case "Ref.No":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.ExceptedPayTime like ? ";
			break;
		case "":
			sql1 = "select t_requirement.BusinessMan,t_customer.EnglishName,t_customer.Email,t_requirement.Preparation,t_requirement.ID,t_customer.CustomerLevel,t_requirement.Province,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
					"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,(case when t_requirement.WhetherSingle=1 then '是' else '否' end) WhetherSingle,t_requirement.ProgressStatus,t_customer.Website,t_customer.CustomerDepartment,t_customer.DepartmentEnglish,t_requirement.RefNo,"+
					"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
					" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+  
					"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		}
		
		//ƴ��sql���
		String sql2=null;
		switch(classify_MAP.get(classify2).toString()){
		case "CustomerName":
			sql2 = " and t_requirement.CustomerName in(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)  ";
			break;
		case "Area":
			sql2 = " and t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		case "DemandSources":
			sql2 = " and t_requirement.DemandSources in(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?) ";
			break;
		case "RequirementClassify":
			sql2 = " and t_requirement.RequirementClassify in (select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?) ";
			break;
		case "Province":
			sql2 = " and t_requirement.Province like ?  ";
			break;
		case "SalesMan":
			sql2 = " and t_sales_representative.`Name` like ?  ";
			break;
		case "CustomerLevel":
			sql2 = " and t_customer.CustomerLevel like ?  ";
			break;
		case "BusinessMan":
			sql2 = " and t_requirement.BusinessMan like ?  ";
			break;
		case "Contact":
			sql2 = " and t_customer.Contact like ? ";
			break;
		case "ContactInfo":
			sql2 = " and t_customer.ContactInfo1 like ?  ";
			break;
		case "Ref.No":
			sql2 = " and t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql2 = " and t_requirement.ExceptedPayTime like ? ";
			break;
		case "":
			sql2 = " and t_requirement.Area in(select t_area.ID from t_area where t_area.AreaName like ? ) ";
			break;
		}
		parameter = new Object[]{"%"+parameter1+"%","%"+parameter2+"%"};
		String sql=sql1+sql2+" order by t_requirement.RequirementDate desc ";
		ls = db.QueryToList(sql, parameter);

		return ls;
	}

	/**
	 * ɾ��������е�����
	 * 
	 * @return ɾ����ȷ����true��ɾ��ʧ�ܷ���false
	 * */
	public boolean delete(Requirement requirement){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "delete from t_requirement where ID=?";
		Object[] parameter = new Object[]{requirement.getID()};


		int result = db.executeUpdate(sql, parameter);

		flag = result>0?true:false;

		return flag;
	}


	/**
	 * ͳ�Ʋ�ѯ������
	 * 
	 * @return 
	 * */
	public int getRequirementCounts(String classify1,String parameter1){

		DBUtil db = new DBUtil();
		String sql = null;
		Object[] parameter =null;
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql = "select count(*) allCounts from t_requirement where t_requirement.CustomerName in"
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "Area":
			sql = "select count(*) allCounts from t_requirement where t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			parameter = new Object[]{parameter1};
			break;
		case "DemandSources":
			sql = "select count(*) allCounts from t_requirement where t_requirement.DemandSources in"
					+ "(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "RequirementClassify":
			sql = "select count(*) allCounts from t_requirement where t_requirement.RequirementClassify "
					+ "in(select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "Province":
			sql = "select count(*) allCounts from t_requirement where t_requirement.Province like ? ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "SalesMan":
			sql = "select count(*) allCounts from t_requirement where SalesMan in (select ID SalesMan from t_sales_representative where t_sales_representative.`Name` like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "CustomerLevel":
			sql = "select count(*) allCounts from t_requirement  where t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerLevel like ?)  ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "BusinessMan":
			sql = "select count(*) allCounts from t_requirement where t_requirement.BusinessMan like ? ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "Contact":
			sql = "select count(*) allCounts from t_requirement  where t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.Contact like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "ContactInfo":
			sql = "select count(*) allCounts from t_requirement  where t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.ContactInfo1 like ?)";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "Ref.No":
			sql = "select count(*) allCounts from t_requirement where t_requirement.RefNo like ? ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "ExceptedPayTime":
			sql = "select count(*) allCounts from t_requirement where t_requirement.ExceptedPayTime like ? ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "":
			sql = "select count(*) allCounts from t_requirement where t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		}


		List<Map<String,Object>> ls;
		ls = db.QueryToList(sql, parameter);

		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("allCounts").toString());


		return result;

	}
	/**
	 * ͳ����ϲ�ѯ������
	 * 
	 * @return 
	 * */
	public int getMixRequirementCounts(String classify1,String parameter1,String classify2,String parameter2){

		DBUtil db = new DBUtil();
		String sql1 = null;
		Object[] parameter =null;
		switch(classify_MAP.get(classify1).toString()){
		case "CustomerName":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.CustomerName in"
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)";
			break;
		case "Area":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			break;
		case "DemandSources":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.DemandSources in"
					+ "(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?)";
			break;
		case "RequirementClassify":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.RequirementClassify "
					+ "in(select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?)";
			break;
		case "Province":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.Province like ? ";
			
			break;
		case "SalesMan":
			sql1 = "select count(*) allCounts from t_requirement where SalesMan in (select ID SalesMan from t_sales_representative where t_sales_representative.`Name` like ?)";
			
			break;
		case "CustomerLevel":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.CustomerName in " + 
					"(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerLevel like ?) ";
			
			break;
		case "BusinessMan":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.BusinessMan like ? ";
			break;
		case "Contact":
			sql1 = "select count(*) allCounts from t_requirement  where t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.Contact like ?)";
			break;
		case "ContactInfo":
			sql1 = "select count(*) allCounts from t_requirement  where t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.ContactInfo1 like ?)";
			break;
		case "Ref.No":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.ExceptedPayTime like ? ";
			break;
		case "":
			sql1 = "select count(*) allCounts from t_requirement where t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			break;
		}

		//sql���ƴ��
		String sql2 = null;
		switch(classify_MAP.get(classify2).toString()){
		case "CustomerName":
			sql2 = " and t_requirement.CustomerName in"
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerName like ?)";

			break;
		case "Area":
			sql2 = " and t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			break;
		case "DemandSources":
			sql2 = " and t_requirement.DemandSources in"
					+ "(select t_requirement_source.ID from t_requirement_source where t_requirement_source.Source like ?)";
			break;
		case "RequirementClassify":
			sql2 = " and t_requirement.RequirementClassify "
					+ "in(select t_requirement_classify.ID from t_requirement_classify where t_requirement_classify.Classify like ?)";
			break;
		case "Province":
			sql2 = " and t_requirement.Province like ? ";
			break;
		case "SalesMan":
			sql2 = "and t_requirement.SalesMan in (select ID SalesMan from t_sales_representative where t_sales_representative.`Name` like ?)";
			
			break;
		case "CustomerLevel":
			sql2 = " and t_requirement.CustomerName in " + 
					"(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.CustomerLevel like ?) ";
			break;
		case "BusinessMan":
			sql2 = " and t_requirement.BusinessMan like ? ";
			break;
		case "Contact":
			sql2 = " and t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.Contact like ?)";
			break;
		case "ContactInfo":
			sql2 = " and t_requirement.CustomerName in  "
					+ "(select t_customer_2.ID from t_customer t_customer_2 where t_customer_2.ContactInfo1 like ?)";
			break;
		case "Ref.No":
			sql2 = " and t_requirement.RefNo like ? ";
			break;
		case "ExceptedPayTime":
			sql2 = " and  t_requirement.ExceptedPayTime like ? ";
			parameter = new Object[]{"%"+parameter1+"%"};
			break;
		case "":
			sql2 = " and t_requirement.Area in"
					+ "(select t_area.ID from t_area where t_area.AreaName like ? )";
			break;
		}
		
		String sql=sql1+sql2;
		List<Map<String,Object>> ls;
		parameter = new Object[]{"%"+parameter1+"%","%"+parameter2+"%"};
		ls = db.QueryToList(sql, parameter);

		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("allCounts").toString());

		return result;

	}

	
	
	/**
	 * ��ȡ���е�������Ϣ
	 * */
	public List<Map<String,Object>> getAllRequirement(){
		String sql  = "select * from t_requirement";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[0];
		
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		
		return ls;
	}
	
	public boolean updatePreparation(Requirement requirement){
		DBUtil db = new DBUtil();
		String sql = "update t_requirement set Preparation=? where ID=?";
		Object[] param = new Object[2];
		param[0] = requirement.getPreparation();
		param[1] = requirement.getID();
		boolean flag = false;
		try {
			int count = db.executeUpdateNotClose(sql, param);
			if(count >= 1){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public int getID(String date,String name,String content){
		DBUtil db = new DBUtil();
		String sql = "select ID from t_requirement where RequirementDate=? and CustomerName=? and RequirementContent=?";
		Object[] param = new Object[]{date,name,content};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		int id = 0;
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;		
	}
	
	public List<Map<String,Object>> getCustomerInfo(int id){
		DBUtil db = new DBUtil();
		String sql = "select  t_requirement_source.Source,t_sales_representative.`Name`,t_customer.EnglishName,t_customer.Email,t_customer.Contact,"
				+ "t_customer.ContactInfo1,t_customer.ContactInfo2,t_customer.CustomerName,"
				+ "t_area.AreaName from t_requirement left join t_customer on "
				+ "t_requirement.CustomerName=t_customer.ID "
				+ "LEFT JOIN t_area on t_requirement.Area=t_area.ID "
				+ "LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID "
				+ " LEFT JOIN t_requirement_source on t_requirement.DemandSources=t_requirement_source.ID "
				+ " where t_requirement.ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;		
	}
	
	public String getCustomerName(int id){
		DBUtil db = new DBUtil();
		String sql = "select CustomerName from t_customer where ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String name = "";
		if(ls.size()>1){
			name = ls.get(1).get("CustomerName").toString();
		}
		return name;	
	}
	public List<Map<String,Object>> getCustomerContactInfo(int id){
		DBUtil db = new DBUtil();
		String sql = "select CustomerName,Contact,ContactInfo1 from t_customer where ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;	
	}
	public List<Map<String,Object>> getRequirement(int id){
		DBUtil db = new DBUtil();
		String sql = "select * from t_requirement where ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	
	public List<Map<String,Object>> getAllCustomer(String content){
		DBUtil db = new DBUtil();
		String sql = "select ID,CustomerName,Contact,ContactInfo1,City,Area,Email,CustomerDepartment from t_customer ";
		Object[] param = null;
		if(content!=null && !content.equals("")){
			sql += " where CustomerName like ? or Contact like ?";
			param = new Object[]{"%"+content+"%","%"+content+"%"};
		}
		return db.QueryToList(sql, param);
		
	}
	
	public String getProvince(int id){
		DBUtil db = new DBUtil();
		String sql = "select ID,Area from t_customer where ID=?";
		Object[] param = new Object[]{id};
		 List<Map<String,Object>> ls = db.QueryToList(sql, param);
		 return ls.get(1).get("Area").toString();
	}
	
	public List<Map<String,Object>> getTime(){
		String sql = "SELECT MIN(RequirementDate) StartTime,MAX(RequirementDate) EndTime FROM t_requirement";
		return new DBUtil().QueryToList(sql, null);
	}
	
	public List<Map<String, Object>> getProvinceMap(String startTime,String endTime){
		String sql = "select replace((trim(replace(Province,'省',''))),'-','') NewProvince,count(ID) Count from t_requirement where RequirementDate between ? and ? "
				+ "group by NewProvince Order by Count desc";
		return new DBUtil().QueryToList(sql, new Object[]{startTime,endTime});
	}
//	@Test
//	public void test(){
//		String sql = "select t_requirement.ID,t_requirement.SequenceNumber,t_requirement.RequirementDate,t_customer.ID CustomerID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1 ContactInfo,t_area.AreaName,t_requirement_brand.Brand,t_requirement.RequirementContent,t_requirement_classify.Classify,t_requirement_source.Source,"+ 
//				"t_requirement_quotes.WhetherQuotes,t_sales_representative.`Name` SalesMan,t_single_probability.Probability SingleProbability,t_single_probability.ID SingleProbabilityID,t_requirement.ExceptedPayTime,t_requirement.WhetherSingle,t_requirement.ProgressStatus,"+
//				"t_requirement.FollowPlan,t_requirement.QuotationNumber,t_requirement.USDQuotes,t_requirement.RMBQuotes from t_requirement left join t_requirement_brand on t_requirement_brand.ID=t_requirement.Brand left join t_requirement_classify on t_requirement.RequirementClassify=t_requirement_classify.ID  "+
//				" LEFT JOIN t_requirement_quotes on t_requirement.WhetherQuotes=t_requirement_quotes.ID left join t_requirement_source ON "+ 
//				"t_requirement.DemandSources=t_requirement_source.ID left join t_single_probability on t_requirement.SingleProbability=t_single_probability.ID LEFT JOIN t_area on t_requirement.Area=t_area.ID LEFT JOIN t_sales_representative on t_requirement.SalesMan=t_sales_representative.ID left join t_customer on t_requirement.CustomerName=t_customer.ID where t_requirement.CustomerName like ? and t_requirement.Area=1 order by t_requirement.RequirementDate desc limit  ?,?";
//		
//		
//		System.out.println(sql);
//	}
}
