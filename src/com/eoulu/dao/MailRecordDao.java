package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MailRecord;
import com.eoulu.entity.OriginFactory;
import com.eoulu.util.DBUtil;

public class MailRecordDao {

	public List<Map<String, Object>> getAllData(Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select ID,ToEmail,CopyTo,NickName,QuoteID,Type,OperatingTime from t_mail_record order by ID desc limit ?,?";
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		ls = db.QueryToList(sql, param);

		return ls;
	}

	public int getAllCounts() {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_mail_record ";
		Object[] param = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public boolean insert(MailRecord record) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_mail_record (ToEmail,CopyTo,NickName,QuoteID,Type,OperatingTime) values (?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = record.getToEmail();
		param[1] = record.getCopyTo();
		param[2] = record.getNickName();
		param[3] = record.getQuoteID();
		param[4] = record.getType();
		param[5] = dfg.format(new Date());
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean update(MailRecord record) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_mail_record set ToEmail=?,CopyTo=?,NickName=?,QuoteID=?,Type=?,OperatingTime=? where ID=?";
		Object[] param = new Object[7];
		param[0] = record.getToEmail();
		param[1] = record.getCopyTo();
		param[2] = record.getNickName();
		param[3] = record.getQuoteID();
		param[4] = record.getType();
		param[5] = dfg.format(new Date());
		param[6] = record.getID();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean updateType(MailRecord factory) {
		DBUtil db = new DBUtil();
		String sql = "update t_mail_record set Type=? where ID=?";
		Object[] param = new Object[2];
		param[0] = factory.getType();
		param[1] = factory.getID();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean delete (int id){
		DBUtil db = new DBUtil();
		String sql = "delete from t_mail_record where ID=?";
		Object[] param = new Object[]{id};
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	
}
