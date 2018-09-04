package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.GoodsSize;
import com.eoulu.entity.PackingGoods;
import com.eoulu.entity.PackingItem;
import com.eoulu.entity.PackingList;
import com.eoulu.util.DBUtil;

public class PackingListDao {

	/**
	 * 查询所有
	 * @return
	 */
	public List<Map<String, Object>> getAllPacking(){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select * from t_packing_list";
		Object[] parameter = new Object[0];
		
		
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 根据箱单ID，查询对应的item信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getPackingItemByID(int id){
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();
		String sql="select t_packing_item.ID itemID,t_packing_item.Goods,t_packing_item.Description itemDescription,t_packing_item.Quantity "
				+ "from t_packing_item left join t_packing_list on t_packing_list.ID=t_packing_item.PackingID where t_packing_item.PackingID=?";
		
		Object[] parameter = new Object[]{id};
	
		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAllPacking(Page page){
    
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();


		String sql= "select t_order.ContractTitle,t_order.Customer CustomerName,t_packing_list.ContractNO,"
				+ "t_packing_list.Date,t_packing_list.DCNO,"
				+ "t_packing_list.FromADD,t_packing_list.FromAPP,t_packing_list.FromTel,t_packing_list.ToContact,"
				+ "t_packing_list.ID,t_packing_list.Model,t_packing_list.Origin,t_packing_list.Packing,"
				+ "t_packing_list.PackingCondition,t_packing_list.PackingListNO,t_packing_list.PONO,"
				+ "t_packing_list.Recepter,t_packing_list.Sender,t_packing_list.PONOAll,"
				+ "t_packing_list.ShippingMark,t_packing_list.ShipVia,t_packing_list.ToADD,"
				+ "t_packing_list.ToAPP,t_packing_list.ToATT,t_packing_list.TotalGrossWeight,"
				+ "t_packing_list.TotalNetWeight,t_packing_list.ToTel,t_packing_list.TrackingNO from t_packing_list "
				+ "left join t_order on t_order.ContractNo=t_packing_list.PONO order by t_packing_list.Date desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_packing_list order by Date desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	/**
	 * 根据合同号查询出所有的合同配置信息
	 * @param contractNO
	 * @return
	 */
	public List<Map<String,Object>> getContractConfigure(String contractNO){
		DBUtil db = new DBUtil();
		/*
		String sql = "select t_equipment.Model Goods,t_equipment.Remarks Description,"
				+ "t_order_info.Number Quantity"
				+ " from t_order "
				+ " left join  t_order_info on t_order_info.OrderID=t_order.ID left join t_equipment on"
				+ " t_equipment.ID=t_order_info.EquipmentModel where t_order.ContractNO=?";
				*/
		String sql = "select t_quote_delivery.Model Goods,t_quote_delivery.Description,t_quote_delivery.Quantity "
				+ " from t_quote_delivery left join t_quote_request on "
				+ "t_quote_request.QuoteID=t_quote_delivery.QuoteID where "
				+ "t_quote_request.ContractNO=?";
		Object[] parameter = new Object[1];
		parameter[0] = contractNO;
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public List<Map<String,Object>> getGoodsSizeByPackingId(int id){
		List<Map<String,Object>> ls;
		DBUtil db = new DBUtil();

		String sql = "select Dimension,GrossWeight,NetWeight,Quantity,ID SizeID from t_goods_size where PackingID=?";

		Object[] parameter = new Object[]{id};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	/**
	 * 添加PackingItem信息
	 * @param item
	 * @return
	 * @throws SQLException
	 */
	public boolean addPackingItemByContractNO(PackingItem item) throws SQLException{
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "insert into t_packing_item (Goods,Description,Quantity,PackingID,PONO) values (?,?,?,?,?)";
		Object[] parameter = new Object[5];
		parameter[0] = item.getGoods();
		parameter[1] = item.getDesciption();
		parameter[2] = item.getQuantity();
		parameter[3] = item.getPackingID();
		parameter[4] = item.getPONO();
		int count = db.executeUpdate(sql, parameter);
		if(count>=1){
			flag = true;
		}
		return flag;
	}
	public boolean updatePackingItem(PackingItem item){
		
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "update t_packing_item set Goods=?,Description=?,Quantity=? where ID=?";
		Object[] parameter = new Object[4];
		parameter[0] = item.getGoods();
		parameter[1] = item.getDesciption();
		parameter[2] = item.getQuantity();
		parameter[3] = item.getID();
	
		int count = db.executeUpdate(sql, parameter);
		if(count>0){
			flag = true;
		}
		return flag;
	}

	public boolean delatePackingItemByID(int id){
		
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_packing_item where ID=?";
		Object[] parameter = new Object[]{id};
		int count = db.executeUpdate(sql, parameter);
		if(count>0){
			flag = true;
		}
		
		return flag;
	}
public boolean delatePackingSize(int id){
		
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_goods_size where ID=?";
		Object[] parameter = new Object[]{id};
		
		int count = db.executeUpdate(sql, parameter);
		if(count>0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 插入箱单信息
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(PackingList packing){
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[24];
		String sql = "insert into t_packing_list (Date,PackingListNO,FromAPP,FromADD,FromTel,ToAPP,ToADD,ToATT,ToTel,"
				+ "ContractNO,PONO,Packing,ShipVia,DCNO,TotalGrossWeight,TotalNetWeight,Model,Origin,ShippingMark,PackingCondition,"
				+ "Sender,Recepter,PONOAll,ToContact) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		parameter[0]= packing.getDate();
		parameter[1]= packing.getPackingListNO();
		parameter[2]= packing.getFromAPP();
		parameter[3]= packing.getFromADD();
		parameter[4]= packing.getFromTel();
		parameter[5]= packing.getToAPP();
		parameter[6]= packing.getToADD();
		parameter[7]= packing.getToATT();
		parameter[8]= packing.getToTel();
		parameter[9]= packing.getContractNO();
		parameter[10]= packing.getPONO();
		parameter[11]= packing.getPacking();
		parameter[12]= packing.getShipVia();
		parameter[13]= packing.getDCNO();
		parameter[14]= packing.getTotalGrossWeight();
		parameter[15]= packing.getTotalNetWeight();
	
		parameter[16]= packing.getModel();
		
		parameter[17]= packing.getOrigin();
		parameter[18]= packing.getShippingMark();
		parameter[19]= packing.getPackingCondition();
		parameter[20]= packing.getSender();
		parameter[21]= packing.getRecepter();
		parameter[22]= packing.getPONOAll();
		parameter[23]= packing.getToContact();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	public boolean insertGoodsSize(GoodsSize goodsSize){
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] param = new Object[5];
		String sql = "insert into t_goods_size (Dimension,GrossWeight,NetWeight,Quantity,PackingID) values (?,?,?,?,?)";
		param[0] = goodsSize.getDimension();
		param[1] = goodsSize.getGrossWeight();
		param[2] = goodsSize.getNetWeight();
		param[3] = goodsSize.getQuantity();
		param[4] = goodsSize.getPackingID();
		
		int a = 0;
		a = db.executeUpdate(sql, param);

		if(a>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 获取packingId
	 * @param contractNo
	 * @return
	 */
	public int getPackingID(String contractNo){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_packing_list where PackingListNO=?";
		Object[] parameter = new Object[]{contractNo};
		
		
		ls = db.QueryToList(sql, parameter,true);
		
		int packingId = 0;
		if(ls.size()>1){
			packingId = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return packingId;
	}
	/**
	 * 修改箱单
	 * @param packing
	 * @return
	 */
	public boolean updatePackingList(PackingList packing ){
		
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[25];
		String sql = "update t_packing_list set Date=?,PackingListNO=?,FromAPP=?,FromADD=?,FromTel=?,ToAPP=?,ToADD=?,ToATT=?,ToTel=?,"
				+ "ContractNO=?,PONO=?,Packing=?,ShipVia=?,DCNO=?,TotalGrossWeight=?,TotalNetWeight=?,Model=?,"
				+ "Origin=?,ShippingMark=?,PackingCondition=?,"
				+ "Sender=?,Recepter=?,PONOAll=?,ToContact=? where ID=?";

		parameter[0]= packing.getDate();
		parameter[1]= packing.getPackingListNO();
		parameter[2]= packing.getFromAPP();
		parameter[3]= packing.getFromADD();
		parameter[4]= packing.getFromTel();
		parameter[5]= packing.getToAPP();
		parameter[6]= packing.getToADD();
		parameter[7]= packing.getToATT();
		parameter[8]= packing.getToTel();
		parameter[9]= packing.getContractNO();
		parameter[10]= packing.getPONO();
		parameter[11]= packing.getPacking();
		parameter[12]= packing.getShipVia();
		parameter[13]= packing.getDCNO();
		parameter[14]= packing.getTotalGrossWeight();
		parameter[15]= packing.getTotalNetWeight();
		parameter[16]= packing.getModel();
		parameter[17]= packing.getOrigin();
		parameter[18]= packing.getShippingMark();
		parameter[19]= packing.getPackingCondition();
		parameter[20]= packing.getSender();
		parameter[21]= packing.getRecepter();
		parameter[22]= packing.getPONOAll();
		parameter[23]= packing.getToContact();
		parameter[24]= packing.getID();
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}

	/**
	 * 修改货物尺寸
	 * @param goodsSize
	 * @return
	 */
	public boolean updateGoodsSize(GoodsSize goodsSize){
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] param = new Object[5];
		String sql = "update t_goods_size set Dimension=?,GrossWeight=?,NetWeight=?,Quantity=? where ID=?";
		param[0] = goodsSize.getDimension();
		param[1] = goodsSize.getGrossWeight();
		param[2] = goodsSize.getNetWeight();
		param[3] = goodsSize.getQuantity();
		param[4] = goodsSize.getID();
		System.out.println(goodsSize.getQuantity());
		int a = 0;
		a = db.executeUpdate(sql, param);
		System.out.println(a);

		if(a>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 搜索查询结果
	 * @param sql
	 * @param parameter 单一查询的参数
	 * @return
	 */
	public List<Map<String, Object>> getPackingList(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	public int getCountsByName(String sql,Object[] param){
		int counts = 0;
		DBUtil db = new DBUtil();
		
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1){
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		
		return counts;
	}
	
	public int getTodayPackingCounts(){
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(day);
		DBUtil db = new DBUtil();
		int count = 0;
		String sql = "select COUNT(ID) from t_packing_list where Date=?";
		Object[] param = new Object[]{today};
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return count;
	}
	
	public boolean insertPackingGoods(PackingGoods goods){
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "insert into t_packing_goods (Model,Description,Qty,PackingID,OperatingTime) values (?,?,?,?,?)";
		Object[] param = new Object[5];
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param[0] = goods.getModel();
		param[1] = goods.getDescription();
		param[2] = goods.getQty();
		param[3] = goods.getPackingID();
		param[4] = df.format(new Date());
		int temp = 0;
		temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	public boolean updatePackingGoods(PackingGoods goods){
		DBUtil db = new DBUtil();
		boolean flag = false;
		String sql = "update t_packing_goods set Model=?,Description=?,Qty=?,OperatingTime=? where ID=?";
		Object[] param = new Object[5];
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param[0] = goods.getModel();
		param[1] = goods.getDescription();
		param[2] = goods.getQty();
		param[3] = df.format(new Date());
		param[4] = goods.getID();
		int temp = 0;
		temp = db.executeUpdate(sql, param);
		if(temp >= 1){
			flag = true;
		}
		return flag;
	}
	public List<Map<String,Object>> getPackingGoodsBy(int packingID){
		List<Map<String,Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select t_packing_goods.Description goodsDescription,t_packing_goods.ID goodsID,t_packing_goods.Model,"
				+ "t_packing_goods.Qty from t_packing_goods left join t_packing_list"
				+ " on t_packing_goods.PackingID=t_packing_list.ID where t_packing_list.ID=? order by t_packing_goods.ID";
		Object[] parameter = new Object[]{packingID};
		ls = db.QueryToList(sql, parameter);
		
		return ls;
	}
	public boolean delatePackingGoods(int id){
		
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_packing_goods where ID=?";
		Object[] parameter = new Object[]{id};
		
		int count = db.executeUpdate(sql, parameter);
		if(count>0){
			flag = true;
		}
		
		return flag;
	}

	/**
	 * 根据箱单ID和PONO的第一个PO号
	 * 判断配置表中是否存在数据
	 * @param packingID
	 * @param po
	 * @return
	 */
	public boolean getItemByPOAndID(int packingID,String po){
		DBUtil db = new DBUtil();
		String sql = "select * from t_packing_item where PackingID=? and PONO=?";
		Object[] param = new Object[]{packingID,po};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		boolean flag = false;
		if(ls.size()>1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 根据箱单ID判断是否存在对应配置
	 * @param packingID
	 * @return
	 */
	public boolean getItemByID(int packingID){
		DBUtil db = new DBUtil();
		String sql = "select * from t_packing_item where PackingID=?";
		Object[] param = new Object[]{packingID};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		boolean flag = false;
		if(ls.size()>1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 根据箱单ID删除配置
	 * @param packingID
	 * @return
	 */
	public boolean deleteItemByID(int packingID){
		DBUtil db = new DBUtil();
		String sql = "delete from t_packing_item where PackingID=?";
		Object[] param = new Object[]{packingID};
		boolean flag = false;
		int count = db.executeUpdate(sql, param);
		if(count >= 1){
			flag = true;
		}
		
		return flag;
	}
	
	public String getPONOByID(int id){
		DBUtil db = new DBUtil();
		String sql = "select PONO from t_packing_list where ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String po = "";
		if(ls.size()>1){
			po = ls.get(1).get("PONO").toString();
		}
		return po;
	}
	
	public boolean saveLogisticsInfo(PackingList packingList){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_packing_list set LogisticsCompany = ?,TrackingNO = ? where ID = ?";
		Object[] param = new Object[3];
		param[0] = packingList.getLogisticsCompany();
		param[1] = packingList.getTrackingNO();
		param[2] = packingList.getID();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
		
	}
	
	public boolean saveMail(PackingList pList){
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_packing_mail(PackingID,ToList,CopyList,Subject,Content,Attachment) values(?,?,?,?,?,?)";
		Object[] param = new Object[6];
		param[0] = pList.getID();
		param[1] = pList.getToList();
		param[2] = pList.getCopyList();
		param[3] = pList.getSubject();
		param[4] = pList.getContent();
		param[5] = pList.getAttachment();
		
		int result = dbUtil.executeUpdate(sql, param);
		
		return result>0?true:false;
	}
	
}
