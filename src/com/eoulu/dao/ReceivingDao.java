package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Receiving;
import com.eoulu.util.DBUtil;

public class ReceivingDao {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getReceiving(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();

		String sql= "select t_order.ContractTitle,t_order.Customer,t_receiving_report.ContractNO,"
				+ "t_receiving_report.ConfirmDate,t_receiving_report.Engineer,t_receiving_report.FirstParty Sender,"
				+ "t_receiving_report.GuaranteeDate,t_receiving_report.ID,t_receiving_report.Model,"
				+ "t_receiving_report.PONO,t_receiving_report.SecondParty Receptor,t_receiving_report.TestDate,"
				+ "t_receiving_report.Warranty from t_receiving_report left join t_order on "
				+ "t_receiving_report.ContractNO=t_order.ContractNo order by t_receiving_report.OperatingTime desc limit ?,?";

		
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String,Object>> getReceivingByContract(String contractNO){
		String sql = "select ContractNO,ConfirmDate,Engineer,FirstParty Sender,GuaranteeDate,Model,"
				+ "PONO,SecondParty Receptor,TestDate,Warranty from t_receiving_report"
				+ " where ContractNO=?";
		return new DBUtil().QueryToList(sql, new Object[]{contractNO});
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_receiving_report order by OperatingTime desc";
		
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
	public boolean insert(Receiving receiving){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[11];
		String sql = "insert into t_receiving_report (ContractNO,ConfirmDate,Engineer,FirstParty,GuaranteeDate,"
				+ "Model,PONO,SecondParty,TestDate,Warranty,OperatingTime) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?)";
		parameter[0] = receiving.getContractNO();
		parameter[1] = receiving.getConfirmDate();
		parameter[2] = receiving.getEngineer();
		parameter[3] = receiving.getFirstParty();
		parameter[4] = receiving.getGuaranteeDate();
		parameter[5] = receiving.getModel();
		parameter[6] = receiving.getPONO();
		parameter[7] = receiving.getSecondParty();
		parameter[8] = receiving.getTestDate();
		parameter[9] = receiving.getWarranty();
		parameter[10] = df.format(new Date());
		int i = 0;
		i = db.executeUpdate(sql, parameter);
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
	public boolean updateReceiving(Receiving receiving){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[12];
		String sql = "update t_receiving_report set ContractNO=?,ConfirmDate=?,Engineer=?,FirstParty=?,"
				+ "GuaranteeDate=?,Model=?,PONO=?,SecondParty=?,TestDate=?,Warranty=?,OperatingTime=?"
				+ " where ID=?";

		parameter[0] = receiving.getContractNO();
		parameter[1] = receiving.getConfirmDate();
		parameter[2] = receiving.getEngineer();
		parameter[3] = receiving.getFirstParty();
		parameter[4] = receiving.getGuaranteeDate();
		parameter[5] = receiving.getModel();
		parameter[6] = receiving.getPONO();
		parameter[7] = receiving.getSecondParty();
		parameter[8] = receiving.getTestDate();
		parameter[9] = receiving.getWarranty();
		parameter[10] = df.format(new Date());
		parameter[11] = receiving.getID();
		
		int i = 0;
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
		String sql = "delete from t_receiving_report where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
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
	public List<Map<String, Object>> getReceiving(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	
	
}
