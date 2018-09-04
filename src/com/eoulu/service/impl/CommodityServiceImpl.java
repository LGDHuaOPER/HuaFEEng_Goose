package com.eoulu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ItemTestDao;
import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.dao.SupplierDao;
import com.eoulu.dao.SupplierInfoDao;
import com.eoulu.entity.CommodityInfo;
import com.eoulu.entity.CommodityMail;
import com.eoulu.entity.SupplierInfo;
import com.eoulu.service.CommodityService;
import com.eoulu.util.CommodityMailUtil;
import com.eoulu.util.DBUtil;
import com.eoulu.util.MethodUtil;

public class CommodityServiceImpl implements CommodityService {

	public static final Map<String, Object> classify_MAP;

	static{
		Map<String, Object> map = new HashMap<>();
		map.put("型号规格", "Model");
		map.put("过期价格", "CostPrice");
		map.put("商品名称", "CommodityName");
		
		classify_MAP=map;
	}
	
	
	@Override
	public List<Map<String, Object>> getCommodityInfo(Page page) {
		return new QuoteSystemDao().getCommodityInfo(page);
	}
	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		case "DeliveryPeriod":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "CostPrice":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "DiscountCost":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceOne":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceTwo":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceThree":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select count(ID) from t_commodity_info  ";
		for (int i = 0; i < obj.length; i++) {
			if (classify_MAP.get(classify).equals("DeliveryPeriod") || classify_MAP.get(classify).equals("DiscountCost")
					|| classify_MAP.get(classify).equals("SellerPriceOne")
					|| classify_MAP.get(classify).equals("SellerPriceTwo")
					|| classify_MAP.get(classify).equals("SellerPriceThree")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =?";
			} else if (classify_MAP.get(classify).equals("CostPrice")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " like ?";
			}
		}

		return new DBUtil().getCountsByName(sql, obj);
	}
	@Override
	public List<Map<String, Object>> getQueryByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		case "DeliveryPeriod":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "CostPrice":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "DiscountCost":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceOne":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceTwo":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		case "SellerPriceThree":
			obj = new Object[1];
			obj[0] = parameter;
			break;
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select t_commodity_info.Item,t_commodity_info.ItemDescription ,t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier,t_commodity_info.ProductCategory ,"
				+ "t_commodity_info.QuoteTime from t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID ";
		for (int i = 0; i < obj.length; i++) {
			if (classify_MAP.get(classify).equals("DeliveryPeriod") || classify_MAP.get(classify).equals("DiscountCost")
					|| classify_MAP.get(classify).equals("SellerPriceOne")
					|| classify_MAP.get(classify).equals("SellerPriceTwo")
					|| classify_MAP.get(classify).equals("SellerPriceThree")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =?";
			} else if (classify_MAP.get(classify).equals("CostPrice")) {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql += "where  t_commodity_info." + classify_MAP.get(classify) + " like ?";
			}
			
		}
		sql += " order by ID desc limit ?,?";
		Object[] param = new Object[3];
		param[0] = obj[0];
		param[1] = (page.getCurrentPage() - 1) * page.getRows();
		param[2] = page.getRows();
		return new QuoteSystemDao().getQueryList(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		case "DeliveryPeriod":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "CostPrice":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "DiscountCost":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceOne":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceTwo":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceThree":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql1 = "select count(t_commodity_info.ID) from t_commodity_info  where";
		for (int i = 0; i < obj1.length; i++) {
			if (classify_MAP.get(classify1).equals("DeliveryPeriod") || classify_MAP.get(classify1).equals("DiscountCost")
					|| classify_MAP.get(classify1).equals("SellerPriceOne")
					|| classify_MAP.get(classify1).equals("SellerPriceTwo")
					|| classify_MAP.get(classify1).equals("SellerPriceThree")) {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " =?";
			} else if (classify_MAP.get(classify1).equals("CostPrice")) {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql1 += "  t_commodity_info." + classify_MAP.get(classify1) + " like ?";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";

		switch (classify_MAP.get(classify2).toString()) {
		case "DeliveryPeriod":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "CostPrice":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "DiscountCost":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceOne":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceTwo":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceThree":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify_MAP.get(classify2).equals("DeliveryPeriod") || classify_MAP.get(classify2).equals("DiscountCost")
					|| classify_MAP.get(classify2).equals("SellerPriceOne")
					|| classify_MAP.get(classify2).equals("SellerPriceTwo")
					|| classify_MAP.get(classify2).equals("SellerPriceThree")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =?";
			} else if (classify_MAP.get(classify2).equals("CostPrice")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " like ?";
			}
		}
		String sql = sql1 + " and " + sql2;
		Object[] param = new Object[obj1.length + obj2.length];
		param[0] = obj1[0];
		param[1] = obj2[0];
		return new DBUtil().getCountsByName(sql, param);
	}
	
	@Override
	public List<Map<String, Object>> getQueryByClassifyInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		case "DeliveryPeriod":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "CostPrice":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "DiscountCost":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceOne":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceTwo":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		case "SellerPriceThree":
			obj1 = new Object[1];
			obj1[0] = parameter1;
			break;
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql1 = "select t_commodity_info.Item,t_commodity_info.ItemDescription ,t_commodity_info.ID,t_commodity_info.CommodityName,t_commodity_info.Model,"
				+ "t_commodity_info.Unit,t_commodity_info.DeliveryPeriod,t_commodity_info.ProducingArea,"
				+ "t_commodity_info.CostPrice,t_commodity_info.DiscountCost,"
				+ "t_commodity_info.SellerPriceOne UnitPrice,t_commodity_info.SellerPriceTwo,"
				+ "t_commodity_info.SellerPriceThree,t_supplier.`Name` Supplier,t_commodity_info.ProductCategory ,"
				+ "t_commodity_info.QuoteTime from t_commodity_info LEFT JOIN t_supplier ON t_commodity_info.Supplier=t_supplier.ID ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify_MAP.get(classify1).equals("DeliveryPeriod") || classify_MAP.get(classify1).equals("DiscountCost")
					|| classify_MAP.get(classify1).equals("SellerPriceOne")
					|| classify_MAP.get(classify1).equals("SellerPriceTwo")
					|| classify_MAP.get(classify1).equals("SellerPriceThree")) {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " =?";
			} else if (classify_MAP.get(classify1).equals("CostPrice")) {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql1 += "where  t_commodity_info." + classify_MAP.get(classify1) + " like ?";
			}
		}

		Object[] obj2 = null;
		String sql2 = "";

		switch (classify_MAP.get(classify2).toString()) {
		case "DeliveryPeriod":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "CostPrice":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "DiscountCost":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceOne":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceTwo":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		case "SellerPriceThree":
			obj2 = new Object[1];
			obj2[0] = parameter2;
			break;
		default:
			obj2 = new Object[1];
			obj2[0] = "%" + parameter2 + "%";
		}
		for (int i = 0; i < obj2.length; i++) {
			if (classify_MAP.get(classify2).equals("DeliveryPeriod") || classify_MAP.get(classify2).equals("DiscountCost")
					|| classify_MAP.get(classify2).equals("SellerPriceOne")
					|| classify_MAP.get(classify2).equals("SellerPriceTwo")
					|| classify_MAP.get(classify2).equals("SellerPriceThree")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =?";
			} else if (classify_MAP.get(classify2).equals("CostPrice")) {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " =? and t_commodity_info.QuoteTime<"
						+ "'" + df.format(new Date()) + "'";
			} else {
				sql2 += "  t_commodity_info." + classify_MAP.get(classify2) + " like ?";
			}
		}
		String sql = sql1 + " and " + sql2 + " order by ID desc limit ?,?";
		Object[] param = new Object[obj1.length + obj2.length + 2];
		param[0] = obj1[0];
		param[1] = obj2[0];
		param[2] = (page.getCurrentPage() - 1) * page.getRows();
		param[3] = page.getRows();
		return new QuoteSystemDao().getQueryList(sql, param);
	}
	
	
	@Override
	public String operateCommodityInfo(HttpServletRequest request) {
		boolean flag = false;
		String name = request.getParameter("CommodityName");
		String model = request.getParameter("Model");
		String unit = request.getParameter("Unit");
		String producingArea = request.getParameter("ProducingArea");
		String deliveryPeriod = request.getParameter("DeliveryPeriod");
		String costPrice = request.getParameter("CostPrice");
		String discountCost = request.getParameter("DiscountCost");
		String sellerPriceOne = request.getParameter("SellerPriceOne");
		String sellerPriceTwo = request.getParameter("SellerPriceTwo");
		String sellerPriceThree = request.getParameter("SellerPriceThree");
		String supplier = request.getParameter("Supplier");
		String ProductCategory = request.getParameter("ProductCategory");
		
		String QuoteTime = request.getParameter("QuoteTime") == null ? "0000-00-00"
				: request.getParameter("QuoteTime").toString();
		QuoteTime = request.getParameter("QuoteTime") == "" ? "0000-00-00"
				: request.getParameter("QuoteTime").toString();
		int id = Integer.parseInt(request.getParameter("ID"));
		
		String item = request.getParameter("Item");
		String description = request.getParameter("ItemDescription");
		CommodityInfo info = new CommodityInfo();
		info.setProductCategory(ProductCategory);
		info.setCommodityName(name);
		info.setModel(model.trim());
		info.setUnit(unit);
		info.setProducingArea(producingArea);
		info.setDeliveryPeriod(deliveryPeriod);
		info.setItem(item);
		info.setItemDescription(description);
		if (costPrice == null || costPrice.equals("--") || costPrice.equals("")) {
			info.setCostPrice(0);
		} else {
			info.setCostPrice(Double.parseDouble(costPrice));
		}
		if (discountCost == null || discountCost.equals("--") || discountCost.equals("")) {
			info.setDiscountCost(0);
		} else {
			info.setDiscountCost(Double.parseDouble(discountCost));
		}
		if (sellerPriceOne == null || sellerPriceOne.equals("--") || sellerPriceOne.equals("")) {
			info.setSellerPriceOne(0);
		} else {
			info.setSellerPriceOne(Double.parseDouble(sellerPriceOne));
		}
		if (sellerPriceTwo == null || sellerPriceTwo.equals("--") || sellerPriceTwo.equals("")) {
			info.setSellerPriceTwo(0);
		} else {
			info.setSellerPriceTwo(Double.parseDouble(sellerPriceTwo));
		}
		if (sellerPriceThree == null || sellerPriceThree.equals("--") || sellerPriceThree.equals("")) {
			info.setSellerPriceThree(0);
		} else {
			info.setSellerPriceThree(Double.parseDouble(sellerPriceThree));
		}
		if((supplier==null || supplier.equals("--")||supplier.equals(" "))){
			info.setSupplier(""+15);
		}else{
			int supplierID = new SupplierDao().getID(supplier);
			supplierID = supplierID==0?new SupplierDao().insertSupplier(supplier):supplierID;
			info.setSupplier(""+supplierID);
		}
		info.setQuoteTime(QuoteTime);
		info.setID(id);

		int SupplierID = request.getParameter("SupplierID") == null ? 0
				: Integer.parseInt(request.getParameter("SupplierID"));
		String PurchaseModel = request.getParameter("PurchaseModel") == null ? model
				: request.getParameter("PurchaseModel");
		String PurchaseLink = request.getParameter("PurchaseLink") == null ? "" : request.getParameter("PurchaseLink");
		String PurchaseContact = request.getParameter("PurchaseContact") == null ? ""
				: request.getParameter("PurchaseContact");
		String PurchaseEmail = request.getParameter("PurchaseEmail") == null ? ""
				: request.getParameter("PurchaseEmail");
		String PaymentCondition = request.getParameter("PaymentCondition") == null ? ""
				: request.getParameter("PaymentCondition");
		String PurchaseRecord = request.getSession().getAttribute("fileName") == null ? ""
				: request.getSession().getAttribute("fileName").toString();
		String PurchaseRecordPath = request.getSession().getAttribute("filePath") == null ? ""
				: request.getSession().getAttribute("filePath").toString();
		String ValidTime = request.getParameter("ValidTime") == null ? "0000-00-00" : request.getParameter("ValidTime");
		ValidTime = request.getParameter("ValidTime") == "" ? "0000-00-00" : request.getParameter("ValidTime");
		SupplierInfo sup = new SupplierInfo();
		sup.setModel(PurchaseModel);
		sup.setPurchaseLink(PurchaseLink);
		sup.setPurchaseContact(PurchaseContact);
		sup.setPurchaseEmail(PurchaseEmail);
		sup.setPaymentCondition(PaymentCondition);
		sup.setValidTime(ValidTime);
		sup.setID(SupplierID);
		sup.setPurchaseRecord(PurchaseRecord);
		sup.setPurchaseRecordPath(PurchaseRecordPath);
		SupplierInfoDao dao = new SupplierInfoDao();
		String result = "";
		// 操作商品管理
		if (id == 0) {
			// flag = new QuoteSystemDao().insertCommodityInfo(info);
			if(!dao.isExistModel(model)){
				
				id = dao.insertCommodityInfo(info);
				if (id != 0) {
					flag = true;
				}else{
					result = "商品信息保存失败！";
				}
			}else{
				 result = "型号规格已存在！";
			}
			
		} else {
			flag = new QuoteSystemDao().updateCommodityInfo(info);
		}

		sup.setCommodity(id);
		boolean temp = false;
		// 操作供应商管理
		if (SupplierID == 0) {
			SupplierID = dao.insert(sup);
			if ((PurchaseRecordPath == null || PurchaseRecordPath.equals("")) && SupplierID != 0) {
				temp = true;
			}
			System.out.println("PurchaseRecordPath---"+PurchaseRecordPath+"-s----"+SupplierID);
			if ((PurchaseRecordPath != null && !PurchaseRecordPath.equals("")) && SupplierID != 0) {
//				temp = dao.insertFile(sup);
				temp = dao.updateFile(sup);
				if(temp){
					request.getSession().removeAttribute("fileName");
					request.getSession().removeAttribute("filePath");
				}
			}

		} else {
			temp = dao.update(sup);
		}
		if(!temp){
			result = "供应商管理保存失败！";
		}
		int MailID = request.getParameter("MailID") == null ? 0 : Integer.parseInt(request.getParameter("MailID"));
		String Consignee = request.getParameter("Consignee");
		String CCList = request.getParameter("CCList") == null ? "" : request.getParameter("CCList").toString();
		String Subject = request.getParameter("Subject") == null ? "" : request.getParameter("Subject").toString();
		String Content = request.getParameter("Content") == null ? "" : request.getParameter("Content").toString();
		CommodityMail mail = new CommodityMail();
		mail.setID(MailID);
		mail.setCommodity(id);
		mail.setConsignee(Consignee);
		mail.setCCList(CCList);
		mail.setSubject(Subject);
		mail.setContent(Content);
		// 操作邮件
			boolean temp2 = false;
			if (MailID == 0) {
				temp2 = dao.insertMail(mail);
			} else {
				
				temp2 = dao.updateMail(mail);
			}
			if(!temp2){
				result = "邮件保存失败！";
			}
			if(flag && temp && temp2){
				result = "保存成功！";
			}

		return result;
	}

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String filePath = null;
		String SupplierID = null;
		String Commodity = null;
		String isExist = "notExists";
		DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
		factory.setRepository(file01);// 设置临时目录
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		ServletFileUpload upload = new ServletFileUpload(factory);// 2、创建一个文件上传解析器
		upload.setHeaderEncoding("UTF-8");// 解决上传文件名的中文乱码
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!upload.isMultipartContent(request)) {
			// 按照传统方式获取数据

		}
		java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		items = upload.parseRequest(request); // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		byte data[] = new byte[1024];
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {
					if ("SupplierID".equals(item.getFieldName())) {
						SupplierID = item.getString("utf-8");

					}
					if ("Commodity".equals(item.getFieldName())) {
						Commodity = item.getString("utf-8");

					}
				} else {
					// 如果fileitem中封装的是上传文件，得到上传的文件名称，
					fileName = item.getName();
					
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					// fileName = fileName.substring(
					// item.getName().lastIndexOf(File.separator) + 1,
					// item.getName().length());
					// System.out.println("test:::::::"+fileName);
					InputStream inputStream = item.getInputStream();
					// OutputStream outputStream = new FileOutputStream(tempPath
					// + fileName);
					// 先判断下文件是否存在，若存在先进行删除
					File tempFile = new File(tempPath + fileName);
					if (tempFile.exists()) {
						tempFile.delete();
						isExist = "exists";
					}
					// 创建一个文件输出流
					FileOutputStream outputStream = new FileOutputStream(tempPath + File.separator + fileName);
					int i;
					while ((i = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, i);
					}
					inputStream.close();
					outputStream.close();
					filePath = tempPath + fileName;
				}
			}
		}
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		map.put("isExist", isExist);
		map.put("SupplierID", SupplierID);
		map.put("Commodity", Commodity);
		return map;
	}

	@Override
	public boolean operateFile(SupplierInfo info) {
		boolean flag = false;
		if (info.getID() != 0) {
			flag = new SupplierInfoDao().updateFile(info);
		}
		System.out.println(flag);
		return flag;
	}

	@Override
	public String sendMail(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID"));
		int MailID = request.getParameter("MailID") == null ? 0 : Integer.parseInt(request.getParameter("MailID"));
		String Consignee = request.getParameter("Consignee") == null ? ""
				: request.getParameter("Consignee").toString();
		String CCList = request.getParameter("CCList") == null ? "" : request.getParameter("CCList").toString();
		String Subject = request.getParameter("Subject") == null ? "" : request.getParameter("Subject").toString();
		String Content = request.getParameter("Content") == null ? "" : request.getParameter("Content").toString();
		CommodityMail mail = new CommodityMail();
		mail.setID(MailID);
		mail.setCommodity(id);
		mail.setConsignee(Consignee);
		mail.setCCList(CCList);
		mail.setSubject(Subject);
		mail.setContent(Content);
		String user = request.getSession().getAttribute("email").toString();
		String password = request.getParameter("Password");
		CommodityMailUtil util = new CommodityMailUtil(user, password);
		MethodUtil m = new MethodUtil();
		Content = m.getEmailSign(Content, "NA");
		boolean flag = util.doSendHtmlEmail(Subject, Content, null, Consignee.split(";"), CCList.split(";"));
		String result = "";
		if(!flag){
			result = "检查邮箱格式/密码是否正确！";
		}else{
			SupplierInfoDao dao = new SupplierInfoDao();
			boolean temp = false;
			if (MailID == 0) {
				temp = dao.insertMail(mail);
			} else {
				temp = dao.updateMail(mail);
			}
			if (temp) {
				result = "发送&保存成功！";
			} else {
				result = "已发送，保存不成功！";
			}
		}
		
		return result;
	}

	@Override
	public String getPath(int id) {
		return new SupplierInfoDao().getPath(id);
	}

	@Override
	public Map<String, Object> getAllInfo(int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("supplier", new SupplierInfoDao().getSupplier(id));
		map.put("mail", new SupplierInfoDao().getModel(id));
		return map;
	}

	@Override
	public String getFileName(int id) {
		return new SupplierInfoDao().getFileName(id);
	}
	
	public static boolean ImportItemIntoCommodity(){
		ItemTestDao dao = new ItemTestDao();
		List<String> list = new ArrayList<>();
		List<Map<String,Object>> ls = dao.getAllDatas();
		for(int i=1;i<ls.size();i++){
			Map<String,Object> map = ls.get(i);
			String model = map.get("Model").toString();
			String item = map.get("Item").toString();
			String description = map.get("ItemDescription").toString();
			CommodityInfo info = new CommodityInfo();
			info.setItem(item);
			info.setItemDescription(description);
			info.setModel(model);
			if(dao.update(info)){
				list.add("true");
			}else{
				list.add("false");
			}
		}
		boolean flag = false;
		if(list.contains("false")){
			flag = true;
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> getEquipmentByName(String equipment) {
		return new QuoteSystemDao().getEquipmentByName(equipment);
	}
	
}
