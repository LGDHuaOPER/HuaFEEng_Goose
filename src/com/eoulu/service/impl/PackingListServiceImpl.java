package com.eoulu.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.PackingListDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.GoodsSize;
import com.eoulu.entity.PackingGoods;
import com.eoulu.entity.PackingItem;
import com.eoulu.entity.PackingList;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.PackingListService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class PackingListServiceImpl implements PackingListService {

	public static final Map<String, Object> classify_MAP;

	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "Customer");
		map.put("合同号", "ContractNO");
		map.put("货物名称", "Model");
		map.put("Applicant", "ToAPP");
		map.put("箱单号", "PackingListNO");

		classify_MAP = map;
	}

	@Override
	public int getAllCounts() {

		return new PackingListDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getAllPacking(Page page) {
		return new PackingListDao().getAllPacking(page);
	}

	@Override

	public List<Map<String, Object>> getPackingItemByID(int id) {

		return new PackingListDao().getPackingItemByID(id);

	}

	@Override
	public boolean addPackingListAndGoodsSize(HttpServletRequest request) {

		PackingList list = new PackingList();
		PackingListDao dao = new PackingListDao();
		List<String> ls = new ArrayList<>();
		String model = request.getParameter("Model");
		System.out.println("model" + model);
		list.setDate(request.getParameter("Date"));
		list.setPackingListNO(request.getParameter("PackingListNO"));

		list.setToContact(request.getParameter("ToContact"));
		list.setFromAPP(request.getParameter("FromAPP"));
		list.setSender(request.getParameter("Sender"));
		list.setRecepter(request.getParameter("Recepter"));
		list.setFromADD(request.getParameter("FromADD"));
		list.setToAPP(request.getParameter("ToAPP"));
		list.setToADD(request.getParameter("ToADD"));
		list.setToATT(request.getParameter("ToATT"));
		list.setOrigin(request.getParameter("Origin"));
		list.setShippingMark(request.getParameter("ShippingMark"));
		list.setPackingCondition(request.getParameter("PackingCondition"));
		list.setPacking(request.getParameter("Packing"));
		list.setShipVia(request.getParameter("ShipVia"));

		list.setFromTel(request.getParameter("FromTel"));
		list.setToTel(request.getParameter("ToTel"));
		String contractNO = request.getParameter("ContractNO");
		list.setContractNO(contractNO.equals("")?"NA":contractNO);
		String PONO = "";
		if (request.getParameter("PONOAll") == null || request.getParameter("PONOAll").equals("")
				|| request.getParameter("PONOAll").equals("NA")) {
			PONO = contractNO.trim();
		} else {
			PONO = request.getParameter("PONOAll").split(",")[0].trim();
		}
		list.setPONO(PONO);
		list.setPONOAll(request.getParameter("PONOAll"));
		list.setDCNO(request.getParameter("DCNO"));
		list.setTotalGrossWeight(request.getParameter("TotalGrossWeight"));
		list.setTotalNetWeight(request.getParameter("TotalNetWeight"));

		List<Map<String, Object>> contractConfig = dao.getContractConfigure(PONO);
		// System.out.println(PONO+"---"+contractConfig.size());
		if (model.equals("NA")) {
			if (contractConfig.size() > 1) {
				list.setModel(contractConfig.get(1).get("Goods").toString());
			}
		} else {
			list.setModel(model);

		}
		boolean flag = dao.insert(list);
		int packingId = 0;
		packingId = dao.getPackingID(request.getParameter("PackingListNO"));

		if (contractConfig.size() > 1) {
			PackingItem item = new PackingItem();
			for (int i = 1; i < contractConfig.size(); i++) {
				String goods = contractConfig.get(i).get("Goods").toString();
				item.setGoods(goods);
				String desciption = contractConfig.get(i).get("Description").toString();
				item.setDesciption(desciption);
				item.setPONO(PONO);
				if (contractConfig.get(i).get("Quantity").toString().equals("--")) {
					item.setQuantity(0);
				} else {
					int quantity = Integer.parseInt(contractConfig.get(i).get("Quantity").toString());
					item.setQuantity(quantity);
				}

				item.setPackingID(packingId);
				try {
					if (dao.addPackingItemByContractNO(item)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		if (packingId != 0) {
			if (request.getParameter("isExistSize").equals("yes")) {

				GoodsSize goodsSize = new GoodsSize();
				String[] dimension = request.getParameterValues("Dimension[]");
				String[] grossWeight = request.getParameterValues("GrossWeight[]");
				String[] netWeight = request.getParameterValues("NetWeight[]");
				String[] quantity = request.getParameterValues("Quantity[]");
				for (int i = 0; i < dimension.length; i++) {
					goodsSize.setDimension(dimension[i]);
					goodsSize.setGrossWeight(Double.parseDouble(grossWeight[i]));
					goodsSize.setNetWeight(Double.parseDouble(netWeight[i]));
					goodsSize.setQuantity(Integer.parseInt(quantity[i]));
					goodsSize.setPackingID(packingId);
					if (dao.insertGoodsSize(goodsSize)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}
			}

			if (request.getParameter("isExistGoods").equals("yes")) {
				PackingGoods goods = new PackingGoods();
				String[] goodsModel = request.getParameterValues("goodsModel[]");
				String[] description = request.getParameterValues("Description[]");
				String[] qty = request.getParameterValues("Qty[]");
				for (int i = 0; i < goodsModel.length; i++) {
					goods.setModel(goodsModel[i]);
					goods.setDescription(description[i]);
					goods.setQty(qty[i]);
					goods.setPackingID(packingId);

					if (dao.insertPackingGoods(goods)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}
			}
		}
		if (flag && !ls.contains("false")) {
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "箱单页面";
			String description = "新增-"+request.getParameter("PackingListNO");
			log.insert(request, JspInfo, description);
		}

		return flag;
	}

	@Override
	public boolean deletePackingItem(int id) {
		boolean flag = false;
		PackingListDao dao = new PackingListDao();
		flag = dao.delatePackingItemByID(id);
		return flag;
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		String sql = "select count(t_packing_list.ID) from t_packing_list "
				+ "left join t_order on t_order.ContractNo=t_packing_list.ContractNO where ";
		for (int i = 0; i < obj.length; i++) {
			if (classify.equals("客户名称") || classify.equals("合同名称")) {
				sql += "t_order." + classify_MAP.get(classify) + " like ?";
			} else {
				sql += "t_packing_list." + classify_MAP.get(classify) + " like ?";
			}
			if (i < obj.length - 1) {
				sql += " or ";
			}
		}

		return new PackingListDao().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getPackingListByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		String sql = "select t_order.ContractTitle,t_order.Customer CustomerName,t_packing_list.ContractNO,"
				+ "t_packing_list.Date,t_packing_list.DCNO,"
				+ "t_packing_list.FromADD,t_packing_list.FromAPP,t_packing_list.FromTel,"
				+ "t_packing_list.ID,t_packing_list.Model,t_packing_list.Origin,t_packing_list.Packing,"
				+ "t_packing_list.PackingCondition,t_packing_list.PackingListNO,t_packing_list.PONO,"
				+ "t_packing_list.Recepter,t_packing_list.Sender,t_packing_list.PONOAll,"
				+ "t_packing_list.ShippingMark,t_packing_list.ShipVia,t_packing_list.ToADD,"
				+ "t_packing_list.ToAPP,t_packing_list.ToATT,t_packing_list.TotalGrossWeight,"
				+ "t_packing_list.TotalNetWeight,t_packing_list.ToTel,t_packing_list.TrackingNO from t_packing_list "
				+ "left join t_order on t_order.ContractNo=t_packing_list.ContractNO ";
		for (int i = 0; i < obj.length; i++) {
			if (classify.equals("客户名称") || classify.equals("合同名称")) {
				sql += "where t_order." + classify_MAP.get(classify) + " like ?";
			} else {
				sql += "where t_packing_list." + classify_MAP.get(classify) + " like ?";
			}
			if (i < obj.length - 1) {
				sql += " or ";
			}
		}
		sql += " order by t_packing_list.Date desc limit ?,?";
		Object[] param;
		if (obj.length == 0) {
			param = new Object[2];
			param[0] = (page.getCurrentPage() - 1) * page.getRows();
			param[1] = page.getRows();
		} else {
			param = new Object[obj.length + 2];
			for (int i = 0; i < obj.length; i++) {
				param[i] = obj[i];
			}
			param[obj.length] = (page.getCurrentPage() - 1) * page.getRows();
			param[obj.length + 1] = page.getRows();
		}
		return new PackingListDao().getPackingList(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		String sql1 = "select count(t_packing_list.ID) from t_packing_list "
				+ "left join t_order on t_order.ContractNo=t_packing_list.ContractNO ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify1.equals("客户名称") || classify1.equals("合同名称")) {
				sql1 += "where t_order." + classify_MAP.get(classify1) + " like ?";
			} else {
				sql1 += "where t_packing_list." + classify_MAP.get(classify1) + " like ?";
			}
			if (i < obj1.length - 1) {
				sql1 += " or ";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";
		switch (classify_MAP.get(classify2).toString()) {
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify2.equals("客户名称") || classify2.equals("合同名称")) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
			} else {
				sql2 += "t_packing_list." + classify_MAP.get(classify2) + " like ?";
			}
			if (i < obj2.length - 1) {
				sql2 += " or ";
			}
		}
		String sql = sql1 + " and " + sql2;
		Object[] param = new Object[obj1.length + obj2.length];
		param[0] = obj1[0];
		param[1] = obj2[0];
		// for(int i=0 ; i<obj1.length ; i++){
		// param[i] = obj1[i];
		// }
		// for(int i=obj1.length ; i<param.length ; i++){
		// param[i] = obj2[i];
		// }
		return new PackingListDao().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getPackingListByClassifyInTwo(String classify1, Object parameter1,
			String classify2, Object parameter2, Page page) {

		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		String sql1 = "select t_order.ContractTitle,t_order.Customer CustomerName,t_packing_list.ContractNO,"
				+ "t_packing_list.Date,t_packing_list.DCNO,"
				+ "t_packing_list.FromADD,t_packing_list.FromAPP,t_packing_list.FromTel,"
				+ "t_packing_list.ID,t_packing_list.Model,t_packing_list.Origin,t_packing_list.Packing,"
				+ "t_packing_list.PackingCondition,t_packing_list.PackingListNO,t_packing_list.PONO,"
				+ "t_packing_list.Recepter,t_packing_list.Sender,t_packing_list.PONOAll,"
				+ "t_packing_list.ShippingMark,t_packing_list.ShipVia,t_packing_list.ToADD,"
				+ "t_packing_list.ToAPP,t_packing_list.ToATT,t_packing_list.TotalGrossWeight,"
				+ "t_packing_list.TotalNetWeight,t_packing_list.ToTel,t_packing_list.TrackingNO from t_packing_list "
				+ "left join t_order on t_order.ContractNo=t_packing_list.ContractNO ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify1.equals("客户名称") || classify1.equals("合同名称")) {
				sql1 += "where t_order." + classify_MAP.get(classify1) + " like ?";
			} else {
				sql1 += "where t_packing_list." + classify_MAP.get(classify1) + " like ?";
			}
			if (i < obj1.length - 1) {
				sql1 += " or ";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";
		switch (classify_MAP.get(classify2).toString()) {
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify2.equals("客户名称") || classify2.equals("合同名称")) {
				sql2 += "t_order." + classify_MAP.get(classify2) + " like ?";
			} else {
				sql2 += "t_packing_list." + classify_MAP.get(classify2) + " like ?";
			}
			if (i < obj2.length - 1) {
				sql2 += " or ";
			}
		}
		String sql = sql1 + " and " + sql2 + " order by t_packing_list.Date desc limit ?,?";
		Object[] param;
		if (obj1.length == 0 && obj2.length == 0) {
			param = new Object[2];
			param[0] = (page.getCurrentPage() - 1) * page.getRows();
			param[1] = page.getRows();
		} else if (obj1.length != 0 && obj2.length == 0) {
			param = new Object[obj1.length + 2];
			for (int i = 0; i < obj1.length; i++) {
				param[i] = obj1[i];
			}
			param[obj1.length] = (page.getCurrentPage() - 1) * page.getRows();
			param[obj1.length + 1] = page.getRows();
		} else if (obj1.length == 0 && obj2.length != 0) {
			param = new Object[obj2.length + 2];
			for (int i = 0; i < obj2.length; i++) {
				param[i] = obj2[i];
			}
			param[obj2.length] = (page.getCurrentPage() - 1) * page.getRows();
			param[obj2.length + 1] = page.getRows();
		} else {
			param = new Object[obj1.length + obj2.length + 2];

			for (int i = 0; i < param.length - 2; i++) {
				if (i == 0) {
					param[i] = obj1[0];
				}
				if (i == 1) {
					param[i] = obj2[0];
				}

			}
			param[param.length - 2] = (page.getCurrentPage() - 1) * page.getRows();
			param[param.length - 1] = page.getRows();
		}

		return new PackingListDao().getPackingList(sql, param);
	}

	@Override
	public int getTodayPackingCounts() {

		return new PackingListDao().getTodayPackingCounts();
	}

	@Override
	public List<Map<String, Object>> getGoodsSizeById(int id) {
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> size = new PackingListDao().getGoodsSizeByPackingId(id);
		if (size != null) {
			map.put("size", size);
		}
		List<Map<String, Object>> goods = new PackingListDao().getPackingGoodsBy(id);
		if (goods != null) {
			map.put("goods", goods);
		}
		ls.add(map);
		return ls;
	}

	@Override
	public boolean ModifyPackingListAll(HttpServletRequest request) {// 修改
		PackingList list = new PackingList();
		PackingListDao dao = new PackingListDao();
		List<String> ls = new ArrayList<>();
		list.setID(Integer.parseInt(request.getParameter("ID")));
		list.setModel(request.getParameter("Model"));
		list.setDate(request.getParameter("Date"));
		list.setPackingListNO(request.getParameter("PackingListNO"));
		list.setFromAPP(request.getParameter("FromAPP"));
		list.setFromADD(request.getParameter("FromADD"));
		list.setToAPP(request.getParameter("ToAPP"));
		list.setToADD(request.getParameter("ToADD"));
		list.setToATT(request.getParameter("ToATT"));
		list.setOrigin(request.getParameter("Origin"));
		list.setShippingMark(request.getParameter("ShippingMark"));
		list.setPackingCondition(request.getParameter("PackingCondition"));
		list.setPacking(request.getParameter("Packing"));
		list.setShipVia(request.getParameter("ShipVia"));
		list.setFromTel(request.getParameter("FromTel"));
		list.setToTel(request.getParameter("ToTel"));
		list.setToContact(request.getParameter("ToContact"));
		String contractNO = request.getParameter("ContractNO");
		list.setContractNO(contractNO);
		String PONO = "";
		if (request.getParameter("PONOAll") == null || request.getParameter("PONOAll").equals("")
				|| request.getParameter("PONOAll").equals("NA")) {
			PONO = contractNO.trim();
		} else {
			PONO = request.getParameter("PONOAll").split(",")[0].trim();
		}
		System.out.println(contractNO+"---"+PONO+"--All"+request.getParameter("PONOAll"));
		list.setPONO(PONO);
		list.setPONOAll(request.getParameter("PONOAll"));
		list.setDCNO(request.getParameter("DCNO"));
		list.setTotalGrossWeight(request.getParameter("TotalGrossWeight"));
		list.setTotalNetWeight(request.getParameter("TotalNetWeight"));
		list.setSender(request.getParameter("Sender"));
		list.setRecepter(request.getParameter("Recepter"));
		boolean flag = dao.updatePackingList(list);
		// System.out.println("kongge:"+PONO + "-----" +
		// request.getParameter("PONOAll"));
		List<Map<String, Object>> contractConfig = dao.getContractConfigure(PONO);
		// System.out.println(contractConfig);
		int packingID = Integer.parseInt(request.getParameter("ID"));
		// System.out.println(dao.getItemByPOAndID(packingID, PONO) + "----" +
		// packingID);
		// A.先根据箱单ID和PONO查询是否有数据
		if (!dao.getItemByPOAndID(packingID, PONO)) {
			// System.out.println("根据箱单ID与PONO" + dao.getItemByID(packingID));
			// B.若A无，再根据箱单ID查询是否有数据，若有，则根据箱单ID先删除，再根据PONO进行获取，再添加
			if (dao.getItemByID(packingID)) {
				// System.out.println("根据箱单ID");
				if (dao.deleteItemByID(packingID)) {
					// System.out.println("删除");
					if (contractConfig.size() > 1) {
						// System.out.println("huoqu");
						PackingItem item = new PackingItem();
						for (int i = 1; i < contractConfig.size(); i++) {
							String goods = contractConfig.get(i).get("Goods").toString();
							item.setGoods(goods);
							item.setPONO(PONO);
							String desciption = contractConfig.get(i).get("Description").toString();
							item.setDesciption(desciption);
							if (contractConfig.get(i).get("Quantity").toString().equals("--")) {
								item.setQuantity(0);
							} else {
								int quantity = Integer.parseInt(contractConfig.get(i).get("Quantity").toString());
								item.setQuantity(quantity);
							}

							item.setPackingID(Integer.parseInt(request.getParameter("ID")));
							try {
								if (dao.addPackingItemByContractNO(item)) {
									ls.add("true");
								} else {
									ls.add("false");
								}
							} catch (SQLException e) {

								e.printStackTrace();
							}
						}
					}
				} else {
					ls.add("false");
				}

			} else {// 若B无，则根据PONO进行获取，再添加
				// System.out.println("若B无，则根据PONO进行获取，再添加");
				if (contractConfig.size() > 1) {
					// System.out.println("若B无"+contractConfig.size());
					PackingItem item = new PackingItem();
					for (int i = 1; i < contractConfig.size(); i++) {
						String goods = contractConfig.get(i).get("Goods").toString();
						item.setGoods(goods);
						item.setPONO(PONO);
						String desciption = contractConfig.get(i).get("Description").toString();
						item.setDesciption(desciption);
						if (contractConfig.get(i).get("Quantity").toString().equals("--")) {
							item.setQuantity(0);
						} else {
							int quantity = Integer.parseInt(contractConfig.get(i).get("Quantity").toString());
							item.setQuantity(quantity);
						}

						item.setPackingID(Integer.parseInt(request.getParameter("ID")));
						try {
							if (dao.addPackingItemByContractNO(item)) {
								ls.add("true");
							} else {
								ls.add("false");
							}
						} catch (SQLException e) {

							e.printStackTrace();
						}
					}
				}
			}

		} else if (request.getParameter("isExistItem").equals("yes")) {
			// System.out.println("已存在");
			String[] id = request.getParameterValues("itemID[]");
			String[] goods = request.getParameterValues("Goods[]");
			String[] desciption = request.getParameterValues("ItemDescription[]");
			String[] quantity = request.getParameterValues("ItemQuantity[]");
			PackingItem packingItem = new PackingItem();
			for (int i = 0; i < id.length; i++) {
				packingItem.setGoods(goods[i]);
				packingItem.setPONO(PONO);
				packingItem.setDesciption(desciption[i]);
				packingItem.setQuantity(Integer.parseInt(quantity[i]));
				if (Integer.parseInt(id[i]) == 0) {
					packingItem.setPackingID(Integer.parseInt(request.getParameter("ID")));
					try {
						if (dao.addPackingItemByContractNO(packingItem)) {
							ls.add("true");
						} else {
							ls.add("false");
						}
					} catch (SQLException e) {

						e.printStackTrace();
					}
				} else {
					packingItem.setID(Integer.parseInt(id[i]));
					boolean temp = dao.updatePackingItem(packingItem);
					if (temp) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}

			}
		}

		if (request.getParameter("isExistSize").equals("yes")) {
			String[] id = request.getParameterValues("sizeID[]");
			String[] dimension = request.getParameterValues("Dimension[]");
			String[] grossWeight = request.getParameterValues("GrossWeight[]");
			String[] netWeight = request.getParameterValues("NetWeight[]");
			String[] quantity = request.getParameterValues("Quantity[]");
			GoodsSize goodsSize = new GoodsSize();

			for (int i = 0; i < id.length; i++) {
				goodsSize.setDimension(dimension[i]);

				goodsSize.setGrossWeight(Double.parseDouble(grossWeight[i]));
				goodsSize.setNetWeight(Double.parseDouble(netWeight[i]));
				goodsSize.setQuantity(Integer.parseInt(quantity[i]));

				if (Integer.parseInt(id[i]) == 0) {
					goodsSize.setPackingID(Integer.parseInt(request.getParameter("ID")));
					if (dao.insertGoodsSize(goodsSize)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					goodsSize.setID(Integer.parseInt(id[i]));
					boolean temp = dao.updateGoodsSize(goodsSize);

					if (temp) {
						ls.add("true");
					} else {
						ls.add("false");
					}

				}

			}
		}
		if (request.getParameter("isExistGoods").equals("yes")) {
			String[] id = request.getParameterValues("goodsID[]");
			String[] goodsModel = request.getParameterValues("goodsModel[]");
			String[] description = request.getParameterValues("Description[]");
			String[] qty = request.getParameterValues("Qty[]");
			PackingGoods goods = new PackingGoods();
			for (int i = 0; i < id.length; i++) {

				goods.setModel(goodsModel[i]);
				goods.setDescription(description[i]);
				goods.setQty(qty[i]);
				if (Integer.parseInt(id[i]) == 0) {
					// System.out.println("test");
					goods.setPackingID(Integer.parseInt(request.getParameter("ID")));
					if (dao.insertPackingGoods(goods)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					goods.setID(Integer.parseInt(id[i]));
					if (dao.updatePackingGoods(goods)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}

			}

		}
		if (flag && !ls.contains("false")) {
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "箱单页面";
			String description = "修改-"+request.getParameter("PackingListNO");
			log.insert(request, JspInfo, description);
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getOtherAll(int id, String contractNO) {
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> size = new PackingListDao().getGoodsSizeByPackingId(id);

		if (size != null) {
			map.put("size", size);
		}
		List<Map<String, Object>> goods = new PackingListDao().getPackingGoodsBy(id);
		if (goods != null) {
			map.put("goods", goods);
		}
		List<Map<String, Object>> item = // new
											// PackingListDao().getContractConfigure(contractNO);
				new PackingListDao().getPackingItemByID(id);
		if (item != null) {
			map.put("item", item);
		}
		// for(int i=0 ; i<size.size() ; i++){
		// if(i != 0){
		// ls.add(size.get(i));
		// }
		// }
		// for(int i=0 ; i<goods.size() ; i++){
		// if(i != 0){
		// ls.add(goods.get(i));
		// }
		// }
		// for(int i=0 ; i<item.size() ; i++){
		// if(i != 0){
		// ls.add(item.get(i));
		// }
		// }
		if (map != null) {
			ls.add(map);
		}

		return ls;

	}

	@Override
	public boolean deletePackingSize(int id) {

		return new PackingListDao().delatePackingSize(id);
	}

	@Override
	public boolean deletePackingGoods(int id) {

		return new PackingListDao().delatePackingGoods(id);
	}

	@Override
	public boolean operatePackingItem(HttpServletRequest request) {

		List<String> ls = new ArrayList<>();
		PackingListDao dao = new PackingListDao();
		String packingID = request.getParameter("PackingID");
		String po = dao.getPONOByID(Integer.parseInt(packingID));
		String[] ids = request.getParameterValues("itemID[]");
		String[] goods = request.getParameterValues("Goods[]");
		String[] desciption = request.getParameterValues("Description[]");
		String[] quantity = request.getParameterValues("Quantity[]");

		for(int i=0 ;i<ids.length; i++){
			PackingItem item = new PackingItem();
			item.setID(Integer.parseInt(ids[i]));
			item.setGoods(goods[i]);
			item.setDesciption(desciption[i]);
			item.setQuantity(Integer.parseInt(quantity[i]));
			item.setPONO(po);
			item.setPackingID(Integer.parseInt(packingID));
			if(Integer.parseInt(ids[i]) == 0){
				try {
					boolean temp = dao.addPackingItemByContractNO(item);
					if(temp){
						ls.add("true");
					}else{
						ls.add("false");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				boolean temp = dao.updatePackingItem(item);
				if(temp){
					ls.add("true");
				}else{
					ls.add("false");
				}
			}
		}
		boolean flag = false;
		if(!ls.contains("false")){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean getContractConfigure(HttpServletRequest request) {
		String str = "";
		String contractNO = request.getParameter("ContractNO");
		if (request.getParameter("PONOAll") == null || request.getParameter("PONOAll").equals("")
				|| request.getParameter("PONOAll").equals("NA")) {
			str = contractNO.trim();
		} else {
			str = request.getParameter("PONOAll").split(",")[0].trim();
		}
		List<Map<String,Object>> ls = new PackingListDao().getContractConfigure(str);
		boolean flag = false;
		if(ls.size() >1){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean sendLogisticsMail(PackingList pList) {
		String sign = "jiangyaping@eoulu.com";
		Properties properties = new Properties();
		InputStream in = SendMailUtil.class.getResourceAsStream("email.properties");
		String user = "";
		String uname = "";
		String pwd = "";
		try {
			properties.load(in);
			user = properties.getProperty("SEND_USER");
			uname = properties.getProperty("SEND_UNAME");
			pwd = properties.getProperty("SEND_PWD");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PackingListDao dao = new PackingListDao();
		dao.saveLogisticsInfo(pList);
	
		String content = "<span style='font-family:微软雅黑;font-size:14px;'>"+pList.getContent()+"</span><br><br>";
		StaffInfoDao dao2 = new StaffInfoDao();
		List<Map<String, Object>> list = dao2.getTelAndName(sign);
		String name = "NA";
		String tel = "NA";
		if(list.size()>1){
			name = list.get(1).get("StaffName").toString();
			tel = list.get(1).get("LinkTel").toString();
		}
		String[] to = pList.getToList().split(";");
		String[] copyto = pList.getCopyList().split(";");
		String[] fileList = null;
		if(!pList.getAttachment().equals("")){
			String filePath = "E:\\LogisticsFile\\File\\" + pList.getAttachment();
			fileList = new String[]{filePath};
		}
			
		content = new MethodUtil().getStaffEmailSign(content, name, tel, sign);
		boolean result = new JavaMailToolsUtil(user,uname,pwd).doSendHtmlEmail(pList.getSubject(), content, fileList, to, copyto);
		if(result){
			dao.saveMail(pList);
	
		}
		
		return result;
		
	}

}
