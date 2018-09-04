package com.eoulu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.eoulu.dao.InsuranceDirectiveDao;
import com.eoulu.entity.DeliveryAddress;
import com.eoulu.entity.InsuranceDirective;
import com.eoulu.entity.TransportGoodsInfo;
import com.eoulu.service.InsuranceDirectiveService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.InsuranceUtil;
import com.eoulu.util.MethodUtil;

public class InsuranceDirectiveServiceImpl implements InsuranceDirectiveService {

	public static final Map<String, Object> classify_MAP;

	static {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("合同名称", "ContractTitle");
		map.put("客户名称", "Customer");
		map.put("合同号", "ContractNO");
		map.put("PONO", "PONO");
		map.put("信用证号", "DCNO");
		map.put("SONO", "SONO");
		map.put("提货日期", "TakingDate");

		classify_MAP = map;
	}

	@Override
	public int getAllCounts() {

		return new InsuranceDirectiveDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getInsuranceDirective(Page page) {

		return new InsuranceDirectiveDao().getInsuranceDirective(page);
	}

	@Override
	public boolean addInsuranceDirective(HttpServletRequest request) {
		InsuranceDirective directive = new InsuranceDirective();
		InsuranceDirectiveDao dao = new InsuranceDirectiveDao();
		List<String> ls = new ArrayList<>();



		String SONO = request.getParameter("SONO");
		String PONO = request.getParameter("PONO");
		String ContractNO = request.getParameter("ContractNO");
		String TakingDate = request.getParameter("TakingDate");
		String DCNO = request.getParameter("DCNO");
		String ConsigneeName = request.getParameter("ConsigneeName");
		String ConsigneeADD = request.getParameter("ConsigneeADD");
		String ConsigneeATTN = request.getParameter("ConsigneeATTN");
		String ConsigneeTel = request.getParameter("ConsigneeTel");
		String Shipment = request.getParameter("Shipment");
		String Destination = request.getParameter("Destination");
		String InvoiceUSD = request.getParameter("InvoiceUSD");
		String FinalDestination = request.getParameter("FinalADD");
		String InsuredLiability = request.getParameter("Insured");
		String Currency = request.getParameter("Currency");
		String address = request.getParameter("Address");
		String ShippingMarkADD = request.getParameter("ShippingMarkADD");
		String ShippingMarkNO = request.getParameter("ShippingMarkNO");
		String InWarehouse = request.getParameter("InWarehouse");
		String WaybillNum = request.getParameter("WaybillNum");
		directive.setInWarehouse(InWarehouse);
		directive.setWaybillNum(WaybillNum);
		directive.setSONO(SONO);
		directive.setPONO(PONO);
		directive.setContractNO(ContractNO);
		if (TakingDate == null || TakingDate.equals("") || TakingDate.equals("NA")) {
			directive.setTakingDate("0000-00-00 00:00:00");
		} else {
			directive.setTakingDate(TakingDate);
		}

		directive.setDCNO(DCNO);
		directive.setConsigneeName(ConsigneeName);
		directive.setConsigneeADD(ConsigneeADD);
		directive.setConsigneeATTN(ConsigneeATTN);
		directive.setConsigneeTel(ConsigneeTel);
		directive.setShipment(Shipment);
		directive.setDestination(Destination);
		directive.setInvoiceUSD(Double.parseDouble(InvoiceUSD));
		directive.setFinalDestination(FinalDestination);
		directive.setInsuredLiability(InsuredLiability);
		directive.setCurrency(Currency);
		directive.setAddress(address);
		directive.setShippingMarkADD(ShippingMarkADD);
		directive.setShippingMarkNO(ShippingMarkNO);
		int directiveID = dao.insert(directive);
		if (request.getParameter("isExist").equals("yes") && directiveID != 0) {
			String[] name = request.getParameterValues("Name[]");

			String[] Address = request.getParameterValues("PackingAddress[]");
			String[] Applicant = request.getParameterValues("Applicant[]");
			String[] Tel = request.getParameterValues("Tel[]");

			for (int i = 0; i < name.length; i++) {
				DeliveryAddress add = new DeliveryAddress();
				add.setName(name[i]);
				
				add.setAddress(Address[i]);
				add.setApplicant(Applicant[i]);
				add.setTel(Tel[i]);
				add.setDirectiveID(directiveID);
				if (dao.insert(add)) {
					ls.add("true");
				} else {
					ls.add("false");
				}
			}
		}
		if (request.getParameter("isExistSize").equals("yes") && directiveID != 0) {
			String[] Model = request.getParameterValues("Model[]");
			String[] Description = request.getParameterValues("Description[]");
			String[] Qty = request.getParameterValues("Qty[]");
			String[] Size = request.getParameterValues("Size[]");

			for (int i = 0; i < Model.length; i++) {
				TransportGoodsInfo info = new TransportGoodsInfo();
				info.setModel(Model[i]);
				info.setDescription(Description[i]);
				info.setQty(Integer.parseInt(Qty[i]));
				info.setSize(Size[i]);
				info.setDirectiveID(directiveID);
				if (dao.insertGoodsInfo(info)) {
					ls.add("true");
				} else {
					ls.add("false");
				}
			}

		}
		boolean flag = false;
		if (directiveID>1 && !ls.contains("false")) {
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-保险指令";
			String description = "新增"+ContractNO;
			log.insert(request, JspInfo, description);
		}

		return flag;
	}

	@Override
	public boolean updateInsuranceDirective(HttpServletRequest request) {
		InsuranceDirective directive = new InsuranceDirective();
		InsuranceDirectiveDao dao = new InsuranceDirectiveDao();
		List<String> ls = new ArrayList<>();
		String id = request.getParameter("ID");

		String SONO = request.getParameter("SONO");
		String PONO = request.getParameter("PONO");
		String ContractNO = request.getParameter("ContractNO");
		String TakingDate = request.getParameter("TakingDate");
		String DCNO = request.getParameter("DCNO");
		String ConsigneeName = request.getParameter("ConsigneeName");
		String ConsigneeADD = request.getParameter("ConsigneeADD");
		String ConsigneeATTN = request.getParameter("ConsigneeATTN");
		String ConsigneeTel = request.getParameter("ConsigneeTel");
		String Shipment = request.getParameter("Shipment");
		String Destination = request.getParameter("Destination");
		String InvoiceUSD = request.getParameter("InvoiceUSD");
		String FinalDestination = request.getParameter("FinalADD");
		String InsuredLiability = request.getParameter("Insured");
		String Currency = request.getParameter("Currency");
		String directiveAddress = request.getParameter("Address");
		String ShippingMarkADD = request.getParameter("ShippingMarkADD");
		String ShippingMarkNO = request.getParameter("ShippingMarkNO");
		String InWarehouse = request.getParameter("InWarehouse");
		String WaybillNum = request.getParameter("WaybillNum");
		directive.setInWarehouse(InWarehouse);
		directive.setWaybillNum(WaybillNum);
		directive.setID(Integer.parseInt(id));
		directive.setSONO(SONO);
		directive.setPONO(PONO);
		directive.setContractNO(ContractNO);
		directive.setTakingDate(TakingDate);
		directive.setDCNO(DCNO);
		directive.setConsigneeName(ConsigneeName);
		directive.setConsigneeADD(ConsigneeADD);
		directive.setConsigneeATTN(ConsigneeATTN);
		directive.setConsigneeTel(ConsigneeTel);
		directive.setShipment(Shipment);
		directive.setDestination(Destination);
		directive.setInvoiceUSD(Double.parseDouble(InvoiceUSD));
		directive.setFinalDestination(FinalDestination);
		directive.setInsuredLiability(InsuredLiability);
		directive.setCurrency(Currency);
		directive.setAddress(directiveAddress);
		directive.setShippingMarkADD(ShippingMarkADD);
		directive.setShippingMarkNO(ShippingMarkNO);
		boolean flag = dao.update(directive);
	
		if (request.getParameter("isExist").equals("yes")) {
			String[] name = request.getParameterValues("Name[]");
			String[] Address = request.getParameterValues("PackingAddress[]");
			String[] Applicant = request.getParameterValues("Applicant[]");
			String[] Tel = request.getParameterValues("Tel[]");
			String[] ids = request.getParameterValues("AddressID[]");
			System.out.println(Arrays.toString(name)+Arrays.toString(Address));
			for (int i = 0; i < name.length; i++) {
				DeliveryAddress address = new DeliveryAddress();

				address.setName(name[i]);
		
				address.setAddress(Address[i]);
				address.setApplicant(Applicant[i]);
				address.setTel(Tel[i]);
				if (ids==null || Integer.parseInt(ids[i]) == 0) {
					address.setDirectiveID(Integer.parseInt(id));
					if (dao.insert(address)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					address.setID(Integer.parseInt(ids[i]));
					if (dao.updateDeliveryAddress(address)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				}

			}
		}
		if (request.getParameter("isExistSize").equals("yes")) {
			String[] Model = request.getParameterValues("Model[]");
			String[] Description = request.getParameterValues("Description[]");
			String[] Qty = request.getParameterValues("Qty[]");
			String[] Size = request.getParameterValues("Size[]");
			String[] infoId = request.getParameterValues("InfoID[]");
			for (int i = 0; i < infoId.length; i++) {
				TransportGoodsInfo info = new TransportGoodsInfo();
				info.setModel(Model[i]);
				info.setDescription(Description[i]);
				if (Qty[i].equals("") || Qty[i].equals("NA") || Qty[i] == null) {
					info.setQty(0);
				} else {
					info.setQty(Integer.parseInt(Qty[i]));
				}
				info.setSize(Size[i]);
				if (Integer.parseInt(infoId[i]) == 0) {
					info.setDirectiveID(Integer.parseInt(id));
					if (dao.insertGoodsInfo(info)) {
						ls.add("true");
					} else {
						ls.add("false");
					}
				} else {
					info.setID(Integer.parseInt(infoId[i]));
					System.out.println("updateGoodsInfo");
					if (dao.updateGoodsInfo(info)) {
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
			String JspInfo = "物流部-保险指令";
			String description = "修改"+ContractNO;
			log.insert(request, JspInfo, description);
		} else {
			flag = false;
		}

		return flag;
	}

	@Override
	public boolean deleteInsuranceDirective(int id) {

		return new InsuranceDirectiveDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		String sql = "select count(t_transport_insurance_directive.ID) from t_transport_insurance_directive "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_directive.ContractNO where ";
		for (int i = 0; i < obj.length; i++) {
			if (classify.equals("客户名称") || classify.equals("合同名称")) {
				sql += "t_order." + classify_MAP.get(classify) + " like ?";
			} else {
				sql += "t_transport_insurance_directive." + classify_MAP.get(classify) + " like ?";
			}
			if (i < obj.length - 1) {
				sql += " or ";
			}
		}

		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getInsuranceDirectiveByClassifyInOne(String classify, Object parameter,
			Page page) {
		Object[] obj = null;
		switch (classify_MAP.get(classify).toString()) {
		default:
			obj = new Object[1];
			obj[0] = "%" + parameter + "%";
		}
		String sql = "select t_transport_insurance_directive.ShippingMarkNO,t_transport_insurance_directive.ShippingMarkADD,t_transport_insurance_directive.ID,t_transport_insurance_directive.SONO,t_transport_insurance_directive.PONO,t_transport_insurance_directive.ContractNO,"
				+ "t_transport_insurance_directive.TakingDate,t_transport_insurance_directive.DCNO,t_transport_insurance_directive.ConsigneeADD,t_transport_insurance_directive.ConsigneeATTN,"
				+ "t_transport_insurance_directive.ConsigneeName,t_transport_insurance_directive.ConsigneeTel,"
				+ "t_transport_insurance_directive.Shipment,t_transport_insurance_directive.Destination,t_transport_insurance_directive.InvoiceUSD,t_transport_insurance_directive.InvoiceUSD*1.1 InsuranceUSD,"
				+ "t_transport_insurance_directive.FinalDestination FinalADD,t_transport_insurance_directive.InsuredLiability Insured,"
				+ "t_transport_insurance_directive.Currency,t_transport_insurance_directive.Address,t_transport_insurance_directive.OperatingTime,t_order.ContractTitle,t_order.Customer,"
				+ "t_transport_insurance_directive.InWarehouse,t_transport_insurance_directive.WaybillNum,t_transport_insurance_directive.IsSend from t_transport_insurance_directive "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_directive.ContractNO  ";
		for (int i = 0; i < obj.length; i++) {
			if (classify.equals("客户名称") || classify.equals("合同名称")) {
				sql += "where t_order." + classify_MAP.get(classify) + " like ?";
			} else {
				sql += "where t_transport_insurance_directive." + classify_MAP.get(classify) + " like ?";
			}
			if (i < obj.length - 1) {
				sql += " or ";
			}
		}
		sql += " order by t_transport_insurance_directive.TakingDate desc limit ?,?";
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
		return new InsuranceDirectiveDao().getInsuranceDirective(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		String sql1 = "select count(t_transport_insurance_directive.ID) from t_transport_insurance_directive "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_directive.ContractNO ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify1.equals("客户名称") || classify1.equals("合同名称")) {
				sql1 += "where t_order." + classify_MAP.get(classify1) + " like ?";
			} else {
				sql1 += "where t_transport_insurance_directive." + classify_MAP.get(classify1) + " like ?";
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
				sql2 += "t_transport_insurance_directive." + classify_MAP.get(classify2) + " like ?";
			}
			if (i < obj2.length - 1) {
				sql2 += " or ";
			}
		}
		String sql = sql1 + " and " + sql2;
		Object[] param = new Object[obj1.length + obj2.length];
		param[0] = obj1[0];
		param[1] = obj2[0];

		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getInsuranceDirectiveByClassifyInTwo(String classify1, Object parameter1,
			String classify2, Object parameter2, Page page) {
		Object[] obj1 = null;
		switch (classify_MAP.get(classify1).toString()) {
		default:
			obj1 = new Object[1];
			obj1[0] = "%" + parameter1 + "%";
		}
		String sql1 = "select t_transport_insurance_directive.ShippingMarkNO,t_transport_insurance_directive.ShippingMarkADD,t_transport_insurance_directive.ID,t_transport_insurance_directive.SONO,t_transport_insurance_directive.PONO,t_transport_insurance_directive.ContractNO,"
				+ "t_transport_insurance_directive.TakingDate,t_transport_insurance_directive.DCNO,t_transport_insurance_directive.ConsigneeADD,t_transport_insurance_directive.ConsigneeATTN,"
				+ "t_transport_insurance_directive.ConsigneeName,t_transport_insurance_directive.ConsigneeTel,"
				+ "t_transport_insurance_directive.Shipment,t_transport_insurance_directive.Destination,t_transport_insurance_directive.InvoiceUSD,t_transport_insurance_directive.InvoiceUSD*1.1 InsuranceUSD,"
				+ "t_transport_insurance_directive.FinalDestination FinalADD,t_transport_insurance_directive.InsuredLiability Insured,"
				+ "t_transport_insurance_directive.Currency,t_transport_insurance_directive.Address,t_transport_insurance_directive.OperatingTime,t_order.ContractTitle,t_order.Customer,"
				+ "t_transport_insurance_directive.InWarehouse,t_transport_insurance_directive.WaybillNum,t_transport_insurance_directive.IsSend from t_transport_insurance_directive "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_directive.ContractNO  ";
		for (int i = 0; i < obj1.length; i++) {
			if (classify1.equals("客户名称") || classify1.equals("合同名称")) {
				sql1 += "where t_order." + classify_MAP.get(classify1) + " like ?";
			} else {
				sql1 += "where t_transport_insurance_directive." + classify_MAP.get(classify1) + " like ?";
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
				sql2 += "t_transport_insurance_directive." + classify_MAP.get(classify2) + " like ?";
			}
			if (i < obj2.length - 1) {
				sql2 += " or ";
			}
		}
		String sql = sql1 + " and " + sql2 + " order by t_transport_insurance_directive.TakingDate desc limit ?,?";
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
		return new InsuranceDirectiveDao().getInsuranceDirective(sql, param);
	}

	@Override
	public List<Map<String, Object>> getDeliveryAddress(int id) {
		List<Map<String, Object>> ls = new InsuranceDirectiveDao().getDeliveryAddress(id);
		List<Map<String, Object>> ls2 = new InsuranceDirectiveDao().getTransportGoodsInfo(id);
		for (int i = 0; i < ls2.size(); i++) {
			if (i != 0) {
				ls.add(ls2.get(i));
			}
		}

		return ls;
	}

	@Override
	public boolean deleteDeliveryAddress(int id) {

		return new InsuranceDirectiveDao().deleteDeliveryAddress(id);
	}

	@Override
	public boolean deleteGoodsInfo(int id) {

		return new InsuranceDirectiveDao().deleteGoodsInfo(id);
	}

	@Override
	public List<Map<String, Object>> getAddressAndModel(int id) {
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> address = new InsuranceDirectiveDao().getDeliveryAddress(id);
		if (address != null) {
			map.put("address", address);
		}
		List<Map<String, Object>> goods = new InsuranceDirectiveDao().getTransportGoodsInfo(id);
		if (goods != null) {
			map.put("goods", goods);
		}

		if (map != null) {
			ls.add(map);
		}
		return ls;
	}

	@Override
	public boolean sendEmail(HttpServletRequest request) {

		String uploadFilePath = request.getServletContext().getRealPath("/") + "down\\";
		File uploadFile = new File(uploadFilePath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		String tempFilePath = request.getServletContext().getRealPath("/") + "down\\";
		File tempPathFile = new File(tempFilePath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		Map<String, String> map = null;
		try {
			map = getForm(tempPathFile, request, uploadFilePath);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		InsuranceUtil util = new InsuranceUtil(request, map.get("password"));
		String[] to = map.get("consignee").split(";");// request.getParameterValues("Consignee[]");
		String[] tocopy = map.get("copyList").split(";");// request.getParameterValues("CopyList[]");
		String subject = map.get("subject");// request.getParameter("Subject");
		String content = map.get("content");// request.getParameter("Content");
		content.replaceAll("<div>", "<br><br>");
		content.replaceAll("</div>", "<br><br>");
		String[] fileList = new String[] { map.get("filePath") };
		MethodUtil mu = new MethodUtil();

		content = mu.getEmailSign(content, "NA");
		


		boolean flag = util.doSendHtmlEmail(to, tocopy, subject, content, fileList);
		if(flag){
			new InsuranceDirectiveDao().updateStatus(Integer.parseInt(map.get("ID")));
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-运输指令";
			String description = "邮件-"+subject;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String filePath = null;
		String password = "";
		String consignee = "";
		String copyList = "";
		String subject = "";
		String content = "";
		String isExist = "notExists";
		String ID ="";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(file01);
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		if (!upload.isMultipartContent(request)) {

		}
		java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		items = upload.parseRequest(request);
		byte data[] = new byte[1024];
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {
					if ("Password".equals(item.getFieldName())) {
						password = item.getString("utf-8");

					}
					if("ID".equals(item.getFieldName())) {
						ID = item.getString("utf-8");
					}
					if ("Consignee".equals(item.getFieldName())) {
						consignee = item.getString("utf-8");

					}
					if ("CopyList".equals(item.getFieldName())) {
						copyList = item.getString("utf-8");

					}
					if ("Subject".equals(item.getFieldName())) {
						subject = item.getString("utf-8");

					}
					if ("Content".equals(item.getFieldName())) {
						content = item.getString("utf-8");

					}
				} else {
					fileName = item.getName();
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					InputStream inputStream = item.getInputStream();
					File tempFile = new File(tempPath + fileName);
					if (tempFile.exists()) {
						tempFile.delete();
						isExist = "exists";
					}
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
		map.put("password", password);
		map.put("consignee", consignee);
		map.put("copyList", copyList);
		map.put("subject", subject);
		map.put("content", content);
		map.put("isExist", isExist);
		map.put("ID", ID);

		return map;
	}

	@Override
	public List<Map<String, Object>> getAllEmail() {
		return new InsuranceDirectiveDao().getAllEmail();
	}

	
}
