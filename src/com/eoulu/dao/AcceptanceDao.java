package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Acceptance;
import com.eoulu.util.DBUtil;

public class AcceptanceDao {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAcceptance(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();

		String sql= "select t_order.ContractTitle,t_order.Customer,t_acceptance_certificate.ContractNO,"
				+ "t_acceptance_certificate.Applicant,t_acceptance_certificate.Balance,"
				+ "t_acceptance_certificate.Beneficiary,t_acceptance_certificate.CommoditySpecifications,"
				+ "t_acceptance_certificate.DCNO,t_acceptance_certificate.EndUser,t_acceptance_certificate.ID,"
				+ "t_acceptance_certificate.Model,t_acceptance_certificate.OperatingTime,"
				+ "t_acceptance_certificate.Packing,t_acceptance_certificate.Percent,"
				+ "t_acceptance_certificate.PriceTerm,t_acceptance_certificate.Qty,"
				+ "t_acceptance_certificate.TotalValue "
				+ "from t_acceptance_certificate left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO "
				+ "order by t_acceptance_certificate.OperatingTime desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> getDataByContractNO(String contractNO){
		String sql= "select ContractNO,Applicant,Balance,Beneficiary,CommoditySpecifications,"
				+ "DCNO,EndUser,Model,Packing,Percent,PriceTerm,Qty,TotalValue "
				+ "from t_acceptance_certificate where ContractNO=?";
		return new DBUtil().QueryToList(sql, new Object[]{contractNO});
	
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_acceptance_certificate order by OperatingTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(Acceptance acceptance){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[14];
		String sql = "insert into t_acceptance_certificate (ContractNO,DCNO,CommoditySpecifications,Qty,Model,"
				+ "PriceTerm,Packing,TotalValue,Balance,Percent,EndUser,Beneficiary,Applicant,OperatingTime) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = acceptance.getContractNO();
		parameter[1] = acceptance.getDCNO();
		parameter[2] = acceptance.getCommoditySpecifications();
		parameter[3] = acceptance.getQty();
		parameter[4] = acceptance.getModel();
		parameter[5] = acceptance.getPriceTerm();
		parameter[6] = acceptance.getPacking();
		parameter[7] = acceptance.getTotalValue();
		parameter[8] = acceptance.getBalance();
		parameter[9] = acceptance.getPercent();
		parameter[10] = acceptance.getEndUser();
		parameter[11] = acceptance.getBeneficiary();
		parameter[12] = acceptance.getApplicant();
		parameter[13] = df.format(new Date());
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean updateAcceptance(Acceptance acceptance){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[15];
		String sql = "update t_acceptance_certificate set ContractNO=?,DCNO=?,CommoditySpecifications=?,Qty=?,Model=?,"
				+ "PriceTerm=?,Packing=?,TotalValue=?,Balance=?,Percent=?,EndUser=?,Beneficiary=?,Applicant=?,OperatingTime=?"
				+ " where ID=?";
		parameter[0] = acceptance.getContractNO();
		parameter[1] = acceptance.getDCNO();
		parameter[2] = acceptance.getCommoditySpecifications();
		parameter[3] = acceptance.getQty();
		parameter[4] = acceptance.getModel();
		parameter[5] = acceptance.getPriceTerm();
		parameter[6] = acceptance.getPacking();
		parameter[7] = acceptance.getTotalValue();
		parameter[8] = acceptance.getBalance();
		parameter[9] = acceptance.getPercent();
		parameter[10] = acceptance.getEndUser();
		parameter[11] = acceptance.getBeneficiary();
		parameter[12] = acceptance.getApplicant();
		parameter[13] = df.format(new Date());
		parameter[14] = acceptance.getID();
		
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_acceptance_certificate where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getAcceptance(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	
	/*
	public List<Map<String, Object>> getAcceptanceByContract(String contractNO){
		String sql = "select t_order.ContractTitle,t_order.Customer,t_acceptance_certificate.ContractNO,"
				+ "t_acceptance_certificate.Applicant,t_acceptance_certificate.Balance,"
				+ "t_acceptance_certificate.Beneficiary,t_acceptance_certificate.CommoditySpecifications,"
				+ "t_acceptance_certificate.DCNO,t_acceptance_certificate.EndUser,t_acceptance_certificate.ID,"
				+ "t_acceptance_certificate.Model,t_acceptance_certificate.OperatingTime,"
				+ "t_acceptance_certificate.Packing,t_acceptance_certificate.Percent,"
				+ "t_acceptance_certificate.PriceTerm,t_acceptance_certificate.Qty,"
				+ "t_acceptance_certificate.TotalValue "
				+ "from t_order t_acceptance_certificate left join t_order on t_order.ContractNo=t_acceptance_certificate.ContractNO"
				+ " where 
	}
	*/
	
	
}
