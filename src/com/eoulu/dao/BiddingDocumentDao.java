package com.eoulu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.BiddingDocument;
import com.eoulu.util.DBUtil;

public class BiddingDocumentDao {
	private static Map<String, String> Classify_Map;
	static{
		Classify_Map = new HashMap<>();
		Classify_Map.put("文件名称", "FileName");
		Classify_Map.put("编写人","Submitter");
		Classify_Map.put("评分", "Score");
		Classify_Map.put("年份", "Year");
	}
	
	public List<Map<String, Object>> getAllData(Page page,String column,String content){
		String sql = "select ID,FileName,Path,Submitter,Score,Year from t_bidding_document ";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(!column.equals("")){
			sql += "where "+Classify_Map.get(column)+" like ? ";
			param = new Object[]{"%"+content+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		
		}
		sql += "order by ID desc limit ?,?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, param);
		
	}
	
	public int getCounts(String column,String content){
		String sql = "select COUNT(ID) Count from t_bidding_document ";
		System.out.println(column);
		System.out.println(content);
		Object[] param = null;
		if(!column.equals("")){
			sql += "where "+Classify_Map.get(column)+" like ? ";
			param = new Object[]{"%"+content+"%"};
			
		}
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, param);
		return Integer.parseInt(list.get(1).get("Count").toString());
			
	}
	
	public boolean insert(BiddingDocument document){
		String sql = "insert into t_bidding_document(FileName,Path,Year) values(?,?,?)";
		Object[] param = new Object[3];
		param[0] = document.getFileName();
		param[1] = document.getPath();
		param[2] = document.getYear();
		
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
				
	}
	
	public boolean update(BiddingDocument document){
		String sql = "update t_bidding_document set Year = ?,Submitter= ?,Score= ? where ID= ?";
		Object[] param = new Object[4];
		param[0] = document.getYear();
		param[1] = document.getSubmitter();
		param[2] = document.getScore();
		param[3] = document.getID();
		
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
	}

}
