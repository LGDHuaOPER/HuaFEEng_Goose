package com.eoulu.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.dao.InventoryDao;
import com.eoulu.entity.InventoryInfo;
import com.eoulu.entity.InventoryOrder;
import com.eoulu.service.InventoryService;

public class InventoryServiceImpl implements InventoryService {

	private static Map<String, String> classify_map = null;

	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("型号", "Model");
		map.put("供应商", "Supplier");
		map.put("入库时间", "OperateDate");
		map.put("出库时间", "OperateDate");
		map.put("入库编码", "OperationCode");
		map.put("出库编码", "OperationCode");
		map.put("合同号", "ContractNo");
		map.put("备注", "Remarks");
		map.put("列表价", "SellerPriceOne");
		map.put("列表总价", "ListPrice");
		classify_map = map;
	}

	
	public List<Map<String, Object>> getAllData(Page page, String classify, String content,String column) {
		String condition = "";
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		if (!classify.equals("") && !content.equals("")) {
			switch (classify) {
			case "备注":
				condition = " where t_inventory." + classify_map.get(classify) + " like ? ";
				param = new Object[] { "%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "型号":
				condition = " where t_commodity_info." + classify_map.get(classify) + " like ? ";
				param = new Object[] { "%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "供应商":
				condition = " where t_supplier.`Name` like ? ";
				param = new Object[] { "%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			
			case "入库时间":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " between ? and ?) ";
				param = new Object[] { 1,content.split(";")[0],content.split(";")[1], (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "出库时间":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " between ? and ?) ";
				param = new Object[] { 2,content.split(";")[0],content.split(";")[1], (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "入库编码":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] { 1,"%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "出库编码":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] { 2,"%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "合同号":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] {"%" + content + "%", (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "列表价":
				condition = " where t_commodity_info." + classify_map.get(classify) + " = ? ";
				param = new Object[] {   Double.parseDouble(content) , (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			case "列表总价":
				condition = " where t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne = ? ";
				param = new Object[] {   Double.parseDouble(content) , (page.getCurrentPage() - 1) * page.getRows(),
						page.getRows() };
				break;
			}
		}
		if(column.equals("")){
			column = "InventoryQuantity";
		}
		return new InventoryDao().getAllData(column,condition,param);
	}

	@Override
	public int getAllCounts(String classify, String content) {
		String condition = "LEFT JOIN t_commodity_info ON t_inventory.CommodityID=t_commodity_info.ID LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID ";
				
			
		Object[] param = null;
		if (!classify.equals("") && !content.equals("")) {
			switch (classify) {
			case "备注":
				condition = " where t_inventory." + classify_map.get(classify) + " like ? ";
				param = new Object[] { "%" + content + "%"};
				break;
			case "型号":
				condition += " where t_commodity_info." + classify_map.get(classify) + " like ? ";
				param = new Object[] { "%" + content + "%"};
				break;
			case "供应商":
				condition += " where t_supplier.`Name` like ? ";
				param = new Object[] { "%" + content + "%"};
				break;
			case "入库时间":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " between ? and ?) ";
				param = new Object[] { 1,content.split(";")[0],content.split(";")[1]};
				break;
			case "出库时间":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " between ? and ?) ";
				param = new Object[] { 2,content.split(";")[0],content.split(";")[1]};
				break;
			case "入库编码":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] { 1,"%" + content + "%"};
				break;
			case "出库编码":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info.Types=? and t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] { 2,"%" + content + "%"};
				break;
			case "合同号":
				condition = " where t_inventory.ID IN(select InventoryID from t_inventory_store_info where t_inventory_store_info." + classify_map.get(classify) + " like ?) ";
				param = new Object[] {"%" + content + "%"};
				break;
			case "列表价":
				condition += " where t_commodity_info." + classify_map.get(classify) + " = ? ";
				param = new Object[] {  Double.parseDouble(content)};
				break;
			case "列表总价":
				condition += " where t_inventory.InventoryQuantity*t_commodity_info.SellerPriceOne = ? ";
				param = new Object[] {   Double.parseDouble(content)};
				break;
			}
		}
		return new InventoryDao().getAllCounts(condition,param);
	}

	@Override
	public String operate(HttpServletRequest request) {
		int CommodityID = Integer
				.parseInt(request.getParameter("CommodityID") == null ? "0" : request.getParameter("CommodityID"));
		
		int ID = Integer
				.parseInt(request.getParameter("ID") == null ? "0" : request.getParameter("ID"));
		int InventoryQuantity = Integer.parseInt(
				request.getParameter("InventoryQuantity") == null ? "0" : request.getParameter("InventoryQuantity"));
		String Remarks = request.getParameter("Remarks") == null ? "" : request.getParameter("Remarks").trim();
		String PNCode = request.getParameter("PNCode") == null? "":request.getParameter("PNCode");
		InventoryInfo info = new InventoryInfo();
		info.setCommodityID(CommodityID);
		info.setInventoryQuantity(InventoryQuantity);
		info.setRemarks(Remarks);
		info.setPNCode(PNCode);
	
		String Type = request.getParameter("Type") == null?"add":request.getParameter("Type");
		String result = "";
		switch (Type) {
		case "add":
			result = new InventoryDao().insert(info);
			break;

		case "update":
			info.setID(ID);
			result = new InventoryDao().update(info);
			break;
		}
	

		return result;
	}

	@Override
	public List<Map<String, Object>> QueryModel(HttpServletRequest request) {
		String content = request.getParameter("content") == null ? "" : request.getParameter("content").trim();
		return new InventoryDao().QueryModel(content);
	}

	@Override
	public boolean operateStoreInfo(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID") == null ? "0" : request.getParameter("ID"));
		int InventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		int Types = Integer.parseInt(request.getParameter("Types") == null ? "0" : request.getParameter("Types"));
		int Quantity = Integer
				.parseInt(request.getParameter("Quantity") == null ? "0" : request.getParameter("Quantity"));
		String ContractNo = request.getParameter("ContractNo") == null ? "" : request.getParameter("ContractNo").trim();
		String OperationCode = request.getParameter("OperationCode") == null ? ""
				: request.getParameter("OperationCode").trim();
		String OperateDate = request.getParameter("OperateDate") == null ? "" : request.getParameter("OperateDate");
		String POUSD = request.getParameter("POUSD") == null ? "0" : request.getParameter("POUSD");
		String PORMB = request.getParameter("PORMB") == null ? "0" : request.getParameter("PORMB");
		String ContractUSD = request.getParameter("ContractUSD") == null ? "0" : request.getParameter("ContractUSD");
		String ContractRMB = request.getParameter("ContractRMB") == null ? "0" : request.getParameter("ContractRMB");
		String Warehouse = request.getParameter("Warehouse") == null ? "" : request.getParameter("Warehouse");
		InventoryInfo info = new InventoryInfo();
	
		info.setInventoryID(InventoryID);
		info.setTypes(Types);
		info.setQuantity(Quantity);
		info.setContractNo(ContractNo);
		info.setOperationCode(OperationCode);
		info.setOperateDate(OperateDate);
		info.setPOUSD(Integer.parseInt(POUSD.equals("")?"0":POUSD));
		info.setPORMB(Integer.parseInt(PORMB.equals("")?"0":PORMB));
		info.setContractUSD(Integer.parseInt(ContractUSD.equals("")?"0":ContractUSD));
		info.setContractRMB(Integer.parseInt(ContractRMB.equals("")?"0":ContractRMB));
		info.setWarehouse(Warehouse);
	
		
		boolean result = false;
		
		InventoryDao dao = new InventoryDao();
		synchronized (InventoryDao.class) {
			
			if (id == 0) {
				switch (Types) {
				case 1:
					result = dao.insertStoreInfo(info) ;
					break;
	
				case 2:
					result = dao.insertOutOfStock(info);
					break;
				}
			} else {
				info.setID(id);
				int oldNum = dao.getOldQuantity(id).size()>1?Integer.parseInt(dao.getOldQuantity(id).get(1).get("Quantity").toString()):0;
				int realNum = Quantity - oldNum;
				
				switch (Types) {
				case 1:
					result = dao.updateStoreInfo(info,realNum) ;
					break;
	
				case 2:
					result = dao.updateOutOfStock(info,realNum) ;
					break;
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String operationTime = sdf.format(new Date());
		dao.updateOperationTime(operationTime);
		return result;
	}

	@Override
	public List<Map<String, Object>> getStoreInfo(HttpServletRequest request) {
		InventoryDao dao = new InventoryDao();
		int inventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		int type = Integer.parseInt(request.getParameter("Types") == null ? "0" : request.getParameter("Types"));
		//String Warehouse = request.getParameter("Warehouse") == null ? "" : request.getParameter("Warehouse");
		return type == 1 ? dao.getStoreInfo(inventoryID, type) : dao.getOutOfStock(inventoryID, type);
	}

	@Override
	public  boolean operateCustomerOrder(HttpServletRequest request) {
		int InventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		String CustomerID = request.getParameter("CustomerID");
		int OrderQuantity = Integer.parseInt(request.getParameter("OrderQuantity").equals("")?"0":request.getParameter("OrderQuantity"));
		int id = request.getParameter("ID") == null ? 0:Integer.parseInt(request.getParameter("ID"));
		String Warehouse = request.getParameter("Warehouse");
		InventoryDao dao = new InventoryDao();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		int oldNum = dao.getOldOrder(id).size()>1?Integer.parseInt(dao.getOldOrder(id).get(1).get("OrderQuantity").toString()):0;

		Map<String, Object> map = new HashMap<>();
		
		map.put("Suzhou", "苏州仓库");
		map.put("Hefei", "合肥仓库");
		map.put("Xianggang","香港仓库");
		map.put("Xiamen", "厦门仓库");
		map.put("Chengdu", "成都仓库");
		map.put("Shenzhen", "深圳仓库");
		map.put("Beijing", "北京仓库");
		map.put("Shijiazhuang", "石家庄仓库");
	

		InventoryOrder order = new InventoryOrder();
		order.setInventoryID(InventoryID);
		order.setOrderQuantity(OrderQuantity);
		order.setCustomerID(Integer.parseInt(CustomerID));
		order.setWarehouse(Warehouse);
		boolean result = false;
		synchronized (InventoryDao.class) {
			if(id!=0){
				order.setID(id);
				order.setRealNum(OrderQuantity-oldNum);
				result = dao.updateCustomerOrder(order, map);
			}else{
				result = dao.insertCustomerOrder(order, map);
			}
			dao.updateRemarks(InventoryID);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String operationTime = sdf.format(new Date());
		dao.updateOperationTime(operationTime);
		return result;
	}
	@Override
	public boolean deleteCustomerOrder(HttpServletRequest request) {
		int InventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		int OrderQuantity = Integer.parseInt(request.getParameter("OrderQuantity").equals("")?"0":request.getParameter("OrderQuantity"));
		int id = request.getParameter("ID") == null ? 0:Integer.parseInt(request.getParameter("ID"));
		String Warehouse = request.getParameter("Warehouse");
		InventoryOrder order = new InventoryOrder();
		order.setID(id);
		order.setInventoryID(InventoryID);
		order.setOrderQuantity(OrderQuantity);
		order.setWarehouse(Warehouse);
		InventoryDao dao = new InventoryDao();
		boolean result = false;
		synchronized (InventoryDao.class) {
			result = dao.deleteCustomerOrder(order);
			dao.updateRemarks(InventoryID);	
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String operationTime = sdf.format(new Date());
		dao.updateOperationTime(operationTime);
		return result;
	}
	
	

	@Override
	public List<Map<String, Object>> getCustomerOrder(HttpServletRequest request) {
		int InventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		//String Warehouse = request.getParameter("Warehouse") == null ? "" : request.getParameter("Warehouse");
		return new InventoryDao().getCustomerOrder(InventoryID);
	}

	@Override
	public List<Map<String, Object>> getAllCustomer(HttpServletRequest request) {

		String content = request.getParameter("content") == null ? "" : request.getParameter("content").trim();
		return new CustomerDao().getAllCustomer(content);
	}

	@Override
	public boolean deleteInventory(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID") == null ? "0" : request.getParameter("ID"));
		int InventoryID = Integer
				.parseInt(request.getParameter("InventoryID") == null ? "0" : request.getParameter("InventoryID"));
		int Types = Integer.parseInt(request.getParameter("Types") == null ? "0" : request.getParameter("Types"));
		String Warehouse = request.getParameter("Warehouse") == null ? "" : request.getParameter("Warehouse");
		InventoryDao dao = new InventoryDao();
		boolean result = false;
		synchronized (InventoryDao.class) {
				
			int oldNum = dao.getOldQuantity(id).size()>1?Integer.parseInt(dao.getOldQuantity(id).get(1).get("Quantity").toString()):0;	
			int realNum = 0 - oldNum;
			InventoryInfo info = new InventoryInfo();
			info.setID(id);
			info.setInventoryID(InventoryID);
			info.setQuantity(realNum);
			info.setTypes(Types);
			info.setWarehouse(Warehouse);
			result = new InventoryDao().deleteInventory(info);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String operationTime = sdf.format(new Date());
		dao.updateOperationTime(operationTime);
		return result;
	}



	

	@Override
	public List<Map<String, Object>> getAllData() {
		InventoryDao dao = new InventoryDao();
		List<Map<String,Object>> list = dao.getAllData();
	
		return list;
	}

	@Override
	public 	String addPNCode(int CommodityID, String PNCode) {
		InventoryDao inventoryDao = new InventoryDao();
		if(inventoryDao.queryPNCode(CommodityID,PNCode).size()>1){
			return "条形码已存在，是否覆盖？";
		}else{
			return new InventoryDao().addPNCode(CommodityID, PNCode)==true?"添加成功":"添加失败";
		}
	}

	@Override
	public boolean updatePNCode(int CommodityID, String PNCode) {
		return new InventoryDao().updatePNCode(CommodityID, PNCode);
	}

	@Override
	public List<Map<String, Object>> getInventory(String PNCode) {
		return new InventoryDao().getInventory(PNCode);
	}

	@Override
	public List<Map<String, Object>> getInfoByWarehouse(String warehouse, int commodityID) {
		
		return new InventoryDao().getInfoByWarehouse(warehouse, commodityID);
	}

	@Override
	public boolean updateLatestInventory(String time) {
		
		return new InventoryDao().updateLatestInventory(time);
	}

	@Override
	public List<Map<String, Object>> getTimeInfo() {
		
		return new InventoryDao().getTimeInfo();
	}

	

}
