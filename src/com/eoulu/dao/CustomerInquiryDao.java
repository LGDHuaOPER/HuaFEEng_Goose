package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.eoulu.commonality.Page;
import com.eoulu.entity.CustomerInquiry;
import com.eoulu.entity.CustomerInquiryRecord;
import com.eoulu.util.DBUtil;

public class CustomerInquiryDao {

	public List<Map<String,Object>> getAllData(Page page,String content){
		String sql = "SELECT  t_customer_inquiry.CustomerID,t_customer_inquiry.ID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1,t_customer_inquiry.PreSalesTable,"
				+ "t_customer_inquiry.QuoteTime,t_customer_inquiry.ServiceTime,t_customer_inquiry.TotalPrice,t_customer_inquiry.Type FROM t_customer_inquiry "
				+ "LEFT JOIN t_customer ON t_customer_inquiry.CustomerID=t_customer.ID ";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(!content.equals("")){
			sql += " WHERE t_customer.CustomerName LIKE ? OR t_customer.Contact like ? ";
			 param = new Object[]{"%"+content+"%","%"+content+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " ORDER BY t_customer_inquiry.ID DESC LIMIT ?,?";
		return new DBUtil().QueryToList(sql, param);
	}
	
	public int getAllCounts(String content){
		String sql = "SELECT count(t_customer_inquiry.ID)  FROM t_customer_inquiry ";
		Object[] param = null;
		if(!content.equals("")){
			sql += " LEFT JOIN t_customer ON t_customer_inquiry.CustomerID=t_customer.ID WHERE t_customer.CustomerName LIKE ? OR t_customer.Contact like ? ";
			 param = new Object[]{"%"+content+"%","%"+content+"%"};
		}
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, param);
		int count = ls.size()>1?Integer.parseInt(ls.get(1).get("count(t_customer_inquiry.ID)").toString()):0;
		return count;
	}
	
	public int insert(CustomerInquiry inquiry){
		SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd ");
		SimpleDateFormat df2 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_customer_inquiry (CustomerID,PreSalesTable,QuoteTime,TotalPrice,OperatingTime,Type) values (?,?,?,?,?,?)";
		Object[] param = new Object[]{inquiry.getCustomerID(),inquiry.getPreSalesTable(),df.format(new Date()),inquiry.getTotalPrice(),df2.format(new Date()),"成本价"};
		int id = 0;
		try {
			id = Integer.parseInt(String.valueOf(new DBUtil().insertGetId(sql, param)));
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean update(CustomerInquiry inquiry){
		SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd ");
		String sql = "update t_customer_inquiry set CustomerID=?,PreSalesTable=?,TotalPrice=?,OperatingTime=? where ID=?";
		Object[] param = new Object[]{inquiry.getCustomerID(),inquiry.getPreSalesTable(),inquiry.getTotalPrice(),df.format(new Date()),inquiry.getID()};
		
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean updateServiceTime(int id){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "update t_customer_inquiry set ServiceTime=? where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{df.format(new Date()),id})>0?true:false;
	}
	public List<Map<String,Object>> getCustomerID(int id){
		String sql = "SELECT CustomerID,PreSalesTable from t_customer_inquiry where ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public List<Map<String,Object>> getOperateData(int id){
		String sql = "SELECT t_customer_inquiry_record.ProductID,t_customer_inquiry_record.ID,t_customer_inquiry_record.SequenceNO,t_customer_inquiry_record.Quantity,t_software_product.Model, "
				+ "t_software_product.PackageName,t_software_product.Remarks,t_customer_inquiry_record.IsMain,"
				+ "FORMAT((t_software_product.Count*t_software_product.HourlyWage*t_software_product.Cycle*t_software_product.PremiumIndex*MaintenanceIndex+t_software_product.TransportAllowance+t_software_product.AccommodationAllowance+t_software_product.MissionAllowance),2) Price"
				+ " FROM t_customer_inquiry_record LEFT JOIN t_software_product ON t_customer_inquiry_record.ProductID=t_software_product.ID WHERE t_customer_inquiry_record.InquiryID=? order by t_customer_inquiry_record.SequenceNO ";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public List<Map<String,Object>> getPreviewData(int id){
		String sql = "SELECT t_customer_inquiry_record.ID,t_customer_inquiry_record.SequenceNO,t_customer_inquiry_record.Quantity,t_software_product.Model, "
				+ "t_software_product.PackageName,t_software_product.Remarks,"
				+ "FORMAT((t_software_product.Count*t_software_product.HourlyWage*t_software_product.Cycle*t_software_product.PremiumIndex*MaintenanceIndex+t_software_product.TransportAllowance+t_software_product.AccommodationAllowance+t_software_product.MissionAllowance),2) Price"
				+ " FROM t_customer_inquiry_record LEFT JOIN t_software_product ON t_customer_inquiry_record.ProductID=t_software_product.ID WHERE t_customer_inquiry_record.InquiryID=? AND t_customer_inquiry_record.IsMain<>'' ";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public boolean insertRecord(CustomerInquiryRecord record){
		String sql = "insert into t_customer_inquiry_record (SequenceNO,Quantity,InquiryID,ProductID,IsMain) values (?,?,?,?,?)";
		Object[] param = new Object[]{record.getSequenceNO(),record.getQuantity(),record.getInquiryID(),record.getProductID(),record.getIsMain()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	public boolean insertRecordWithoutSequence(CustomerInquiryRecord record){
		String sql = "insert into t_customer_inquiry_record (SequenceNO,Quantity,InquiryID,ProductID) values (?,?,?,?)";
		Object[] param = new Object[]{record.getSequenceNO(),record.getQuantity(),record.getInquiryID(),record.getProductID()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	public boolean updateRecord(CustomerInquiryRecord record){
		String sql = "update t_customer_inquiry_record set SequenceNO=?,Quantity=?,ProductID=? where ID=?";
		Object[] param = new Object[]{record.getSequenceNO(),record.getQuantity(),record.getProductID(),record.getID()};
		
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean delete(int id){
		String sql = "delete from t_customer_inquiry_record where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	
	public boolean deleteRecord(int id){
		String sql = "delete from t_customer_inquiry_record where InquiryID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	
	public boolean deleteInquiry(int id){
		String sql = "delete from t_customer_inquiry where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	
	public List<Map<String,Object>> getExcelData(){
		String sql = "SELECT  t_customer.CustomerName,t_customer_inquiry.QuoteTime,t_customer_inquiry.TotalPrice,t_customer_inquiry.Type,t_customer_inquiry.ID "
				+ "FROM t_customer_inquiry LEFT JOIN t_customer ON t_customer_inquiry.CustomerID=t_customer.ID";
		return new DBUtil().QueryToList(sql, null);
	}
	
	public List<Map<String,Object>> getRecordID(int id){
		String sql = "select ID from t_customer_inquiry_record WHERE InquiryID=?";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public String getFileName(int id){
		String sql = "SELECT PreSalesTable FROM t_customer_inquiry where ID=?";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, new Object[]{id});
		return ls.size()>1?ls.get(1).get("PreSalesTable").toString():"";
	}
	
}
