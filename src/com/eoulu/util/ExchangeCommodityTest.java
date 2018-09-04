package com.eoulu.util;

import java.util.List;
import java.util.Map;

public class ExchangeCommodityTest {

	public static void deleteNoRepeat() {
		// 删除商品管理表中有，且报价系统中没有关联，Excel中没有的
		String sql5 = "SELECT ID,Model FROM t_commodity_info WHERE Model NOT IN (SELECT Model FROM t_commodity_jjj)"
				+ "AND ID NOT IN (SELECT Commodity ID FROM t_commodity_temp ) AND  ID NOT IN "
				+ "(SELECT PartID ID FROM t_quote_cascade_temp ) AND ID NOT IN (SELECT PartID ID FROM"
				+ " t_quote_other_temp ) AND  ID NOT IN (SELECT PartID ID FROM t_quote_other_supplier_temp )";
		String sql6 = "delete from t_commodity_info where ID=?";

		DBUtil db5 = new DBUtil();
		List<Map<String, Object>> ls5 = db5.QueryToList(sql5, null);
		if (ls5.size() > 1) {
			for (int i = 1; i < ls5.size(); i++) {
				DBUtil db6 = new DBUtil();

				int id = Integer.parseInt(ls5.get(i).get("ID").toString());
				System.out.println(id);
				int count = db6.executeUpdate(sql6, new Object[] { id });
			}
			System.out.println("删除Excel中没有的");
		}
	}

	public static void updateRepeat() {
		// String sql = "SELECT a.Model,a.Number FROM (SELECT Model,COUNT(Model)
		// Number FROM "
		// + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 )a LEFT JOIN
		// "
		// + "t_commodity_jjj ON a.Model=t_commodity_jjj.Model WHERE
		// t_commodity_jjj.Model<>''";
		// 在1中结果报价系统中型号重复的不同ID，更改为统一ID
		String sql = "SELECT Model,COUNT(Model) Number FROM " + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1";
		String sql1 = "SELECT MIN(ID) MinID FROM  t_commodity_info WHERE t_commodity_info.Model=?";
		String sql2 = "SELECT t_commodity_temp.ID ,t_commodity_temp.Commodity FROM t_commodity_temp LEFT JOIN "
				+ "t_commodity_info ON t_commodity_temp.Commodity=t_commodity_info.ID  WHERE "
				+ "t_commodity_info.Model=?";
		String sql3 = "update t_commodity_temp set Commodity=? where ID=?";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			String model = ls.get(i).get("Model").toString();
			DBUtil db1 = new DBUtil();
			List<Map<String, Object>> ls1 = db1.QueryToList(sql1, new Object[] { model });
			// System.out.println(i);
			if (ls1.get(1).get("MinID") != null && !ls1.get(1).get("MinID").equals("--")) {
				int minID = Integer.parseInt(ls1.get(1).get("MinID").toString());
				DBUtil db2 = new DBUtil();
				List<Map<String, Object>> ls2 = db2.QueryToList(sql2, new Object[] { model });
				int c = 0;
				for (int j = 1; j < ls2.size(); j++) {
					int tempID = Integer.parseInt(ls2.get(j).get("ID").toString());
					int commodity = Integer.parseInt(ls2.get(j).get("Commodity").toString());
					if (commodity != minID) {
						System.out.println(c++);
						DBUtil db3 = new DBUtil();
						Object[] param = new Object[] { minID, tempID };
						int count = db3.executeUpdate(sql3, param);
						if (count < 1) {
							System.out.println("更新失败！" + tempID);
							break;
						}
					}
				}
			}

		}
		System.out.println("报价单商品表更新完毕！");
	}

	public static void updateRepeatCascadePO() {
		// String sql = "SELECT a.Model,a.Number FROM (SELECT Model,COUNT(Model)
		// Number FROM "
		// + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 )a LEFT JOIN
		// "
		// + "t_commodity_jjj ON a.Model=t_commodity_jjj.Model WHERE
		// t_commodity_jjj.Model<>''";
		String sql = "SELECT Model,COUNT(Model) Number FROM "
				+ "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 ";

		String sql1 = "SELECT MIN(ID) MinID FROM  t_commodity_info WHERE t_commodity_info.Model=?";
		String sql2 = "SELECT t_quote_cascade_temp.ID ,t_quote_cascade_temp.PartID FROM t_quote_cascade_temp LEFT "
				+ "JOIN t_commodity_info ON t_quote_cascade_temp.PartID=t_commodity_info.ID  WHERE "
				+ "t_commodity_info.Model=?";
		String sql3 = "update t_quote_cascade_temp set PartID=? where ID=?";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			String model = ls.get(i).get("Model").toString();
			DBUtil db1 = new DBUtil();
			List<Map<String, Object>> ls1 = db1.QueryToList(sql1, new Object[] { model });
			if (ls1.get(1).get("MinID") != null && !ls1.get(1).get("MinID").equals("--")) {
				int minID = Integer.parseInt(ls1.get(1).get("MinID").toString());
				DBUtil db2 = new DBUtil();
				List<Map<String, Object>> ls2 = db2.QueryToList(sql2, new Object[] { model });
				int c = 0;
				for (int j = 1; j < ls2.size(); j++) {
					int tempID = Integer.parseInt(ls2.get(j).get("ID").toString());
					int commodity = Integer.parseInt(ls2.get(j).get("PartID").toString());
					if (commodity != minID) {
						System.out.println(c++);
						DBUtil db3 = new DBUtil();
						Object[] param = new Object[] { minID, tempID };
						int count = db3.executeUpdate(sql3, param);
						if (count < 1) {
							System.out.println("更新失败！" + tempID);
							break;
						}
					}
				}
			}

		}
		System.out.println("CacadePO更新完毕！");
	}

	public static void updateRepeatOtherPO() {
		// String sql = "SELECT a.Model,a.Number FROM (SELECT Model,COUNT(Model)
		// Number FROM "
		// + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 )a LEFT JOIN
		// "
		// + "t_commodity_jjj ON a.Model=t_commodity_jjj.Model WHERE
		// t_commodity_jjj.Model<>''";
		String sql = "SELECT Model,COUNT(Model) Number FROM "
				+ "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 ";

		String sql1 = "SELECT MIN(ID) MinID FROM  t_commodity_info WHERE t_commodity_info.Model=?";
		String sql2 = "SELECT t_quote_other_temp.ID ,t_quote_other_temp.PartID FROM t_quote_other_temp LEFT JOIN "
				+ "t_commodity_info ON t_quote_other_temp.PartID=t_commodity_info.ID  WHERE t_commodity_info.Model=?";
		String sql3 = "update t_quote_other_temp set PartID=? where ID=?";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			String model = ls.get(i).get("Model").toString();
			DBUtil db1 = new DBUtil();
			List<Map<String, Object>> ls1 = db1.QueryToList(sql1, new Object[] { model });
			if (ls1.get(1).get("MinID") != null && !ls1.get(1).get("MinID").equals("--")) {
				int minID = Integer.parseInt(ls1.get(1).get("MinID").toString());
				DBUtil db2 = new DBUtil();
				List<Map<String, Object>> ls2 = db2.QueryToList(sql2, new Object[] { model });
				int c = 0;
				for (int j = 1; j < ls2.size(); j++) {
					int tempID = Integer.parseInt(ls2.get(j).get("ID").toString());
					int commodity = Integer.parseInt(ls2.get(j).get("PartID").toString());
					if (commodity != minID) {
						System.out.println(c++);
						DBUtil db3 = new DBUtil();
						Object[] param = new Object[] { minID, tempID };
						int count = db3.executeUpdate(sql3, param);
						if (count < 1) {
							System.out.println("更新失败！" + tempID);
							break;
						}
					}
				}
			}

		}
		System.out.println("Other PO更新完毕！");
	}

	public static void updateRepeatOtherSupplierPO() {
		// String sql = "SELECT a.Model,a.Number FROM (SELECT Model,COUNT(Model)
		// Number FROM "
		// + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 )a LEFT JOIN
		// "
		// + "t_commodity_jjj ON a.Model=t_commodity_jjj.Model WHERE
		// t_commodity_jjj.Model<>''";
		String sql = "SELECT Model,COUNT(Model) Number FROM "
				+ "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 ";

		String sql1 = "SELECT MIN(ID) MinID FROM  t_commodity_info WHERE t_commodity_info.Model=?";
		String sql2 = "SELECT t_quote_other_supplier_temp.ID ,t_quote_other_supplier_temp.PartID FROM t_quote_other_supplier_temp LEFT JOIN "
				+ "t_commodity_info ON t_quote_other_supplier_temp.PartID=t_commodity_info.ID  WHERE t_commodity_info.Model=?";
		String sql3 = "update t_quote_other_supplier_temp set PartID=? where ID=?";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			String model = ls.get(i).get("Model").toString();
			DBUtil db1 = new DBUtil();
			List<Map<String, Object>> ls1 = db1.QueryToList(sql1, new Object[] { model });
			if (ls1.get(1).get("MinID") != null && !ls1.get(1).get("MinID").equals("--")) {
				int minID = Integer.parseInt(ls1.get(1).get("MinID").toString());
				DBUtil db2 = new DBUtil();
				List<Map<String, Object>> ls2 = db2.QueryToList(sql2, new Object[] { model });
				int c = 0;
				for (int j = 1; j < ls2.size(); j++) {
					int tempID = Integer.parseInt(ls2.get(j).get("ID").toString());
					int commodity = Integer.parseInt(ls2.get(j).get("PartID").toString());
					if (commodity != minID) {
						System.out.println(c++);
						DBUtil db3 = new DBUtil();
						Object[] param = new Object[] { minID, tempID };
						int count = db3.executeUpdate(sql3, param);
						if (count < 1) {
							System.out.println("更新失败！" + tempID);
							break;
						}
					}
				}
			}

		}
		System.out.println("Other Supplier PO更新完毕！");
	}

	public static void OnlyMinID() {
		// String sql = "SELECT a.Model,a.Number FROM (SELECT Model,COUNT(Model)
		// Number FROM "
		// + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1 )a LEFT JOIN
		// "
		// + "t_commodity_jjj ON a.Model=t_commodity_jjj.Model WHERE
		// t_commodity_jjj.Model<>''";

		String sql = "SELECT Model,COUNT(Model) Number FROM " + "t_commodity_info GROUP BY Model HAVING COUNT(Model)>1";
		String sql1 = "SELECT Min(ID) MinID from t_commodity_info WHERE Model=?";
		String sql2 = "SELECT ID from t_commodity_info WHERE Model=?";
		String sql3 = "Delete  from t_commodity_info WHERE ID=?";
		String temp = "SELECT t_commodity_temp.ID ,t_commodity_temp.Commodity FROM t_commodity_temp LEFT JOIN "
				+ "t_commodity_info ON t_commodity_temp.Commodity=t_commodity_info.ID  WHERE "
				+ "t_commodity_info.Model=?";
		String po1 = "SELECT t_quote_cascade_temp.ID ,t_quote_cascade_temp.PartID FROM t_quote_cascade_temp LEFT "
				+ "JOIN t_commodity_info ON t_quote_cascade_temp.PartID=t_commodity_info.ID  WHERE "
				+ "t_commodity_info.Model=?";
		String po2 = "SELECT t_quote_other_temp.ID ,t_quote_other_temp.PartID FROM t_quote_other_temp LEFT JOIN "
				+ "t_commodity_info ON t_quote_other_temp.PartID=t_commodity_info.ID  WHERE t_commodity_info.Model=?";
		String po3 = "SELECT t_quote_other_supplier_temp.ID ,t_quote_other_supplier_temp.PartID FROM t_quote_other_supplier_temp LEFT JOIN "
				+ "t_commodity_info ON t_quote_other_supplier_temp.PartID=t_commodity_info.ID  WHERE t_commodity_info.Model=?";
		DBUtil db = new DBUtil();

		Object[] param = new Object[1];
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			String model = ls.get(i).get("Model").toString();
//			param[0] = model;
//			DBUtil db_temp = new DBUtil();
//			DBUtil db_po1 = new DBUtil();
//			DBUtil db_po2 = new DBUtil();
//			DBUtil db_po3 = new DBUtil();
			// if(db_temp.QueryToList(temp, param).size()>1
			// ||db_po1.QueryToList(po1, param).size()>1
			// ||db_po2.QueryToList(po2, param).size()>1
			// ||db_po3.QueryToList(po3, param).size()>1){
			// System.out.println(model);
			// break;
			// }else{

			DBUtil db1 = new DBUtil();
			List<Map<String, Object>> ls1 = db1.QueryToList(sql1, new Object[] { model });// 最小ID
//			if (ls1.get(1).get("MinID") != null && !ls1.get(1).get("MinID").equals("--")) {
				int minID = Integer.parseInt(ls1.get(1).get("MinID").toString());
				DBUtil db2 = new DBUtil();
				List<Map<String, Object>> ls2 = db2.QueryToList(sql2, new Object[] { model });// 每个型号的所有ID
int c=0;
				if (ls2.size() > 2) {
					for (int j = 1; j < ls2.size(); j++) {
						int tempID = Integer.parseInt(ls2.get(j).get("ID").toString());
						// System.out.println(tempID);
						if (tempID>minID ) {
							c++;
							System.out.println(c);
							DBUtil db3 = new DBUtil();
							int count = db3.executeUpdate(sql3, new Object[] { tempID });
							if (count < 1) {
								System.out.println("删除失败！");
								break;
							}
						}
					}
				}

//			} else {
//				System.out.println(ls1);
//				break;
//			}

			// }

		}
		System.out.println("删除多余的，保留最小的ID");

	}

	public static void deleteSupplierAndMail() {
		String sql = "SELECT ID FROM t_supplier_info WHERE Commodity NOT IN (SELECT ID Commodity FROM t_commodity_info )";
		String sql2 = "delete from t_supplier_info where ID=?";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		for (int i = 1; i < ls.size(); i++) {
			int id = Integer.parseInt(ls.get(i).get("ID").toString());
			System.out.println(id);
			DBUtil db2 = new DBUtil();
			int count = db2.executeUpdate(sql2, new Object[] { id });
			if (count < 1) {
				System.out.println("删除失败");
				break;
			}
		}
		System.out.println("删除供应商");

		String sql3 = "SELECT ID FROM t_commodity_mail WHERE Commodity NOT IN (SELECT ID Commodity FROM t_commodity_info )";
		String sql4 = "delete from t_commodity_mail where ID=?";
		DBUtil db3 = new DBUtil();
		List<Map<String, Object>> ls3 = db3.QueryToList(sql3, null);
		for (int i = 1; i < ls3.size(); i++) {
			int id = Integer.parseInt(ls3.get(i).get("ID").toString());
			System.out.println(id);
			DBUtil db4 = new DBUtil();
			int count = db4.executeUpdate(sql4, new Object[] { id });
			if (count < 1) {
				System.out.println("删除失败");
				break;
			}
		}

		System.out.println("删除邮件模板");
	}

	public static void exchange() {
		DBUtil db = new DBUtil();
		String sql = "select * from t_commodity_jjj";// 要导入导数据库的数据
		String sql2 = "UPDATE  t_commodity_info SET CommodityName=?,Unit=?,DeliveryPeriod=?,"
				+ "ProducingArea=?,CostPrice=?,DiscountCost=?,SellerPriceOne=?,SellerPriceTwo=?,"
				+ "SellerPriceThree=?,Supplier=?,ProductCategory=?,QuoteTime=?,Item=?,ItemDescription=?,OperatingTime=? "
				+ "WHERE Model=?";
		String sql3 = "SELECT ID FROM t_commodity_info WHERE Model=?";
		String sql4 = "INSERT INTO t_commodity_info ( CommodityName, Unit,DeliveryPeriod, ProducingArea,CostPrice,  "
				+ "DiscountCost,SellerPriceOne,  SellerPriceTwo,SellerPriceThree, Supplier,  ProductCategory, QuoteTime, "
				+ " Item, ItemDescription, OperatingTime,Model)" + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		Object[] param = new Object[16];
		for (int i = 1; i < ls.size(); i++) {
			DBUtil db2 = new DBUtil();
			Map<String, Object> map = ls.get(i);
			param[0] = map.get("CommodityName") == null ? "" : map.get("CommodityName").toString();
			param[1] = map.get("Unit") == null ? "" : map.get("Unit").toString();
			param[2] = map.get("DeliveryPeriod") == null ? "" : map.get("DeliveryPeriod").toString();
			param[3] = map.get("ProducingArea") == null ? "" : map.get("ProducingArea").toString();
			param[4] = Double.parseDouble(map.get("CostPrice") == "--" ? "0" : map.get("CostPrice").toString());
			param[5] = Double.parseDouble(map.get("DiscountCost") == "--" ? "0" : map.get("DiscountCost").toString());
			param[6] = Double
					.parseDouble(map.get("SellerPriceOne") == "--" ? "0" : map.get("SellerPriceOne").toString());
			param[7] = Double
					.parseDouble(map.get("SellerPriceTwo") == "--" ? "0" : map.get("SellerPriceTwo").toString());
			param[8] = Double
					.parseDouble(map.get("SellerPriceThree") == "--" ? "0" : map.get("SellerPriceThree").toString());
			param[9] = map.get("Supplier") == null ? "" : map.get("Supplier").toString();
			param[10] = map.get("ProductCategory").toString();
			param[11] = map.get("QuoteTime") == "--" ? "0000-00-00" : map.get("QuoteTime").toString();
			param[12] = map.get("Item") == null ? "" : map.get("Item").toString();
			param[13] = map.get("ItemDescription") == null ? "" : map.get("ItemDescription").toString();
			param[14] = map.get("OperatingTime") == "--" ? "0000-00-00" : map.get("OperatingTime").toString();
			param[15] = map.get("Model").toString();
			System.out.println(map.get("Model").toString());
			DBUtil db3 = new DBUtil();
			if (db3.QueryToList(sql3, new Object[] { map.get("Model").toString() }).size() > 1) {
				int count = db2.executeUpdate(sql2, param);
				System.out.println("xiugai");
				if (count < 1) {
					System.out.println(ls.get(i));
					break;
				}
			} else {
				DBUtil db4 = new DBUtil();
				int count = db4.executeUpdate(sql4, param);
				System.out.println("Insert");
				if (count < 1) {
					System.out.println(ls.get(i));
					break;
				}
			}

		}
		System.out.println("导入完毕！");
	}

	public static void main(String[] args) {

//		 deleteNoRepeat();
//		 updateRepeat();
//		 updateRepeatCascadePO();
//		 updateRepeatOtherPO();
//		 updateRepeatOtherSupplierPO();
//		OnlyMinID();
//		 deleteSupplierAndMail();
		 exchange();

	}
}
