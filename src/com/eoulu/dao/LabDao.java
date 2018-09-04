package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Laboratory;
import com.eoulu.util.DBUtil;

public class LabDao {


		
	public boolean insert(Laboratory lab){
		
		
		DBUtil dbUtil = new DBUtil();
		
		String sql = "insert into t_laboratory(CommodityID,Number,Laboratory,Picture) values(?,?,?,?)";
		Object[] param = new Object[4];
		param[0] = lab.getCommodityID();
		param[1] = lab.getNumber();
		param[2] = lab.getLaboratory();
		param[3] = lab.getPicture();

	
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public boolean update(Laboratory lab){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_laboratory set CommodityID=?,Number=?,Laboratory=?,Picture=? where ID=?";
		Object[] param = new Object[5];
		param[0] = lab.getCommodityID();
		param[1] = lab.getNumber();
		param[2] = lab.getLaboratory();
		param[3] = lab.getPicture();
		param[4] = lab.getID();

	
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	public List<Map<String, Object>> getDataByPage(Page page){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_laboratory.ID,t_laboratory.CommodityID,t_commodity_info.Model,"
				+ "t_commodity_info.CommodityName Description,t_laboratory.Laboratory,"
				+ "t_laboratory.Number,t_laboratory.Picture from t_laboratory "
				+ "left join t_commodity_info on t_laboratory.CommodityID=t_commodity_info.ID "
				+ "order by t_laboratory.ID desc limit ?,?" ;
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(t_laboratory.ID) Count from t_laboratory ";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql,null);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean saveConfig(List<Map<String, Object>> config,int LabID){
		DBUtil dbUtil = new DBUtil();
		String sql1 = "delete from t_lab_config where LabID=?";
		String sql = "insert into t_lab_config(PartID,Qty,LabID) values(?,?,?)";
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1,new Object[]{LabID});
			for(int i = 0;i < config.size();i ++){
				dbUtil.executeUpdateNotClose(sql, new Object[]{config.get(i).get("PartID"),
						config.get(i).get("Qty"),LabID});
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}
		
	}
	
	public List<Map<String, Object>> getComfig(int LabID){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_lab_config.ID,t_lab_config.PartID,t_commodity_info.Model,"
				+ "t_commodity_info.CommodityName Description,t_lab_config.Qty from t_lab_config "
				+ "left join t_commodity_info on t_lab_config.PartID=t_commodity_info.ID "
				+ "where t_lab_config.LabID=?" ;
		Object[] parameter = new Object[]{LabID};
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
		
	}
	
	
}
