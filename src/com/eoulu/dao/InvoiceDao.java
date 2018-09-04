package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Invoice;
import com.eoulu.entity.Item;
import com.eoulu.util.DBUtil;

public class InvoiceDao {

	/**
	 * 查询所有的发票不含产品信息
	 * @return
	 */
	public List<Map<String, Object>> getAllInvoice(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_invoice";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 表格中显示的发票信息
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAllInvoice(int id){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_item.ID ,t_item.Goods,t_item.InvoiceID,t_item.Qty,t_item.TotalUSDAmount,t_item.Unit,t_item.UnitUSDPrice "
				+ "from t_item left join t_invoice on t_invoice.ID=t_item.InvoiceID where  t_item.InvoiceID=?";
		//Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		Object[] parameter = new Object[]{id};
		//System.out.println(sql);
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String,Object>> getOnlyInvoice(Page page){

		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();

		String sql= "select A.Customer CustomerName,A.ContractTitle,t_invoice.ID,t_invoice.AddInfo,t_invoice.AirPort,t_invoice.Applicant,"
				+ "t_invoice.ContractNO,t_invoice.Date,t_invoice.DCNO,t_invoice.Departure,"
				+ "t_invoice.DepartureDate,t_invoice.Destination,t_invoice.EndUser,t_invoice.InvoiceNO,"
				+ "t_invoice.Manufacturer,t_invoice.NinePaid,t_invoice.OperatingTime,t_invoice.Origin,"
				+ "t_invoice.OtherReference,t_invoice.Packing,t_invoice.PaymentRemark,t_invoice.PONO,"
				+ "t_invoice.Product,t_invoice.ShippingMark,t_invoice.TelFax,t_invoice.TenPaid,t_invoice.TotalAmount,"
				+ "t_invoice.Type,t_invoice.Vessel "
				+ "from t_invoice "
				+ " left join t_order A on t_invoice.ContractNO=A.ContractNo "
				+ " order by t_invoice.InvoiceNO desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 发票数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_invoice order by Date desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	/**
	 * 插入发票信息
	 * @param invoice
	 * @param db
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int insert(Invoice invoice,DBUtil dbUtil) throws NumberFormatException, Exception{
		 Date d = new Date(); 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String operate = sdf.format(d);  
		Object[] parameter = new Object[26];
		String sql = "insert into t_invoice (ContractNO,PONO,InvoiceNO,DCNO,Applicant,EndUser,OtherReference,DepartureDate,Departure,"
				+ "Destination,PaymentRemark,Packing,Origin,Manufacturer,ShippingMark,AirPort,Type,Vessel,Date,OperatingTime,AddInfo,TelFax,"
				+ "TotalAmount,NinePaid,TenPaid,Product) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		parameter[0]=invoice.getContractNO();
		parameter[1]=invoice.getPONO();
		parameter[2]=invoice.getInvoiceNO();
		parameter[3]=invoice.getDCNO();
		parameter[4]=invoice.getApplicant();
		parameter[5]=invoice.getEndUser();
		parameter[6]=invoice.getOtherReference();
		parameter[7]=invoice.getDepartureDate();
		parameter[8]=invoice.getDeparture();
		parameter[9]=invoice.getDestination();
		parameter[10]=invoice.getPaymentRemark();
		parameter[11]=invoice.getPacking();
		parameter[12]=invoice.getOrigin();
		parameter[13]=invoice.getManufacturer();
		parameter[14]=invoice.getShippingMark();
		parameter[15]=invoice.getAirPort();
		parameter[16]=invoice.getType();
		parameter[17]=invoice.getVessel();
		parameter[18]=invoice.getDate();
		parameter[19]=operate;
		parameter[20]=invoice.getAdd();
		parameter[21]=invoice.getTelFax();
		parameter[22]=invoice.getTotalAmount();
		parameter[23]=invoice.getNinePaid();
		parameter[24]=invoice.getTenPaid();
		parameter[25]=invoice.getProduct();
		int i = 0;
		i = Integer.parseInt(dbUtil.insertGetIdNotClose(sql, parameter).toString());
		
		return i;
	}
	
	public boolean insertItem(Item item,DBUtil db) throws SQLException{

		boolean flag = false;
		Object[] param = new Object[6];
		String sql = "insert into t_item (InvoiceID,Goods,Unit,Qty,UnitUSDPrice,TotalUSDAmount) values (?,?,?,?,?,?)";
		param[0] = item.getInvoiceID();
		param[1] = item.getGoods();
		param[2] = item.getUnit();
		param[3] = item.getQty();
		param[4] = item.getUnitUSDPrice();
		param[5] = item.getTotalUSDAmount();
		int a = 0;;
		a = db.executeUpdateNotClose(sql, param);

		if(a>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 单一查询结果
	 * @param sql
	 * @param parameter 单一查询的参数
	 * @return
	 */
	public List<Map<String, Object>> getInvoice(String sql,Object[] parameter){
		
		System.out.println("dao层："+sql);
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		//System.out.println("ls:"+ls.size());
		//System.out.println("ls:"+ls.get(1));
		return ls;
		
	}
	public int getCountsByName(String sql,Object[] param){
		int counts = 0;
		DBUtil db = new DBUtil();

		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	public int getInvoiceID(String contractNo){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_invoice where ContractNo=?";
		Object[] parameter = new Object[]{contractNo};
		
		
		ls = db.QueryToList(sql, parameter,true);
		
		int invoiceID = 0;
		if(ls.size()>1)
			invoiceID = Integer.parseInt(ls.get(1).get("ID").toString());
		return invoiceID;
	}
	
	public boolean ModifyInvoice(Invoice invoice,DBUtil db) throws SQLException{
		Date d = new Date(); 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String operate = sdf.format(d); 
		boolean flag = false;
		Object[] parameter = new Object[27];
		String sql = "update t_invoice set ContractNO=?,PONO=?,InvoiceNO=?,DCNO=?,Applicant=?,EndUser=?,OtherReference=?"
				+ ",DepartureDate=?,Departure=?,Destination=?,PaymentRemark=?,Packing=?,Origin=?,Manufacturer=?,ShippingMark=?,"
				+ "AirPort=?,Type=?,OperatingTime=?,Vessel=?,Date=?,AddInfo=?,TelFax=?,TotalAmount=?,NinePaid=?,TenPaid=?,Product=? where ID=?";
		parameter[0]=invoice.getContractNO();
		parameter[1]=invoice.getPONO();
		parameter[2]=invoice.getInvoiceNO();
		parameter[3]=invoice.getDCNO();
		parameter[4]=invoice.getApplicant();
		parameter[5]=invoice.getEndUser();
		parameter[6]=invoice.getOtherReference();
		parameter[7]=invoice.getDepartureDate();
		parameter[8]=invoice.getDeparture();
		parameter[9]=invoice.getDestination();
		parameter[10]=invoice.getPaymentRemark();
		parameter[11]=invoice.getPacking();
		parameter[12]=invoice.getOrigin();
		parameter[13]=invoice.getManufacturer();
		parameter[14]=invoice.getShippingMark();
		parameter[15]=invoice.getAirPort();
		parameter[16]=invoice.getType();
		parameter[17]=operate;
		parameter[18]=invoice.getVessel();
		parameter[19]=invoice.getDate();
		parameter[20]=invoice.getAdd();
		parameter[21]=invoice.getTelFax();
		parameter[22]=invoice.getTotalAmount();
		parameter[23]=invoice.getNinePaid();
		parameter[24]=invoice.getTenPaid();
		parameter[25]=invoice.getProduct();
		parameter[26]=invoice.getID();
		
		
		int a = 0;
		a = db.executeUpdateNotClose(sql, parameter);
		if(a > 0){
			flag = true;
		}
		return flag;
	}
	public boolean ModifyItem(Item item,DBUtil db) throws SQLException{
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "update t_item set Goods=?,Unit=?,Qty=?,UnitUSDPrice=?,TotalUSDAmount=? where ID=?";
				
		parameter[0]=item.getGoods();
		parameter[1]=item.getUnit();
		parameter[2]=item.getQty();
		parameter[3]=item.getUnitUSDPrice();
		parameter[4]=item.getTotalUSDAmount();
		parameter[5]=item.getID();
		
		int a = 0;
		a = db.executeUpdateNotClose(sql, parameter);
		if(a > 0){
			flag = true;
		}
		return flag;
	}
}
