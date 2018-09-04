package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.eoulu.dao.InsuranceSlipDao;
import com.eoulu.entity.InsuranceGoods;
import com.eoulu.entity.InsuranceSlip;
import com.eoulu.service.InsuranceSlipService;
import com.eoulu.service.LogInfoService;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.DBUtil;
import com.eoulu.util.InsuranceUtil;
import com.eoulu.util.Java2Word;
import com.eoulu.util.MethodUtil;

public class InsuranceSlipServiceImpl implements InsuranceSlipService{
	public static final Map<String, Object> classify_MAP; 

	static{
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
		
		return new InsuranceSlipDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getInsuranceSlip(Page page) {
		
		return new InsuranceSlipDao().getInsuranceSlip(page);
	}
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
		String[] to = map.get("consignee").split("；");// request.getParameterValues("Consignee[]");
		String[] tocopy = map.get("copyList").split("；");// request.getParameterValues("CopyList[]");
		String subject = map.get("subject");// request.getParameter("Subject");
		String content = map.get("content");// request.getParameter("Content");
		content.replaceAll("<div>", "<br><br>");
		content.replaceAll("</div>", "<br><br>");
		String[] fileList = new String[] {getFile(Integer.parseInt(map.get("ID")),request.getServletContext().getRealPath("/")) };
		MethodUtil mu = new MethodUtil();
		content = mu.getEmailSign(content, "NA");
		boolean flag = util.doSendHtmlEmail(to, tocopy, subject, content, fileList);
		if(flag){
			new InsuranceDirectiveDao().updateStatus(Integer.parseInt(map.get("ID")));
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-运输保险";
			String description = "邮件-"+subject;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}
	private String getFile(int ID,String path) {
		Map<String,Object> map = new InsuranceSlipDao().getInsurancePolicyInfo(ID);
		HashMap<String,Object> dataMap = (HashMap<String,Object>)map.get("map");
		String[] modelNames = getModelName(map.get("InsuranceType").toString(),dataMap.get("${InsuredName}"));
		
		Java2Word word = new Java2Word();
		String downLoadUrl = path+ "down\\"+modelNames[1]+".doc";
		word.toWord("E:\\Model\\"+modelNames[0]+".doc", downLoadUrl, dataMap,"end");
		ConventerToPDFUtil util = new ConventerToPDFUtil();
		util.word2pdf(downLoadUrl);
		return path+"down\\"+modelNames[1]+".pdf";
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
				//if (item.isFormField()) {
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
//				} else {
//					fileName = item.getName();
//					if (fileName == null || fileName.trim().equals("")) {
//						continue;
//					}
//					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
//					InputStream inputStream = item.getInputStream();
//					File tempFile = new File(tempPath + fileName);
//					if (tempFile.exists()) {
//						tempFile.delete();
//						isExist = "exists";
//					}
//					FileOutputStream outputStream = new FileOutputStream(tempPath + File.separator + fileName);
//					int i;
//					while ((i = inputStream.read(data)) != -1) {
//						outputStream.write(data, 0, i);
//					}
//					inputStream.close();
//					outputStream.close();
//					filePath = tempPath + fileName;
//					System.out.println(fileName);
//				}
			}
		}
		//map.put("fileName", fileName);
		//map.put("filePath", filePath);
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
	public boolean addInsuranceSlip(HttpServletRequest request) {
		InsuranceSlip slip = new InsuranceSlip();
		InsuranceSlipDao dao = new InsuranceSlipDao();
		List<String> ls = new ArrayList<>();
		String InvoiceNO = request.getParameter("InvoiceNO");
		String DepartureDate = request.getParameter("StartDate");
		String AirWayBillNO = request.getParameter("GoodsNO");
		String ContractNO = request.getParameter("ContractNO");
		String DCNO = request.getParameter("DCNO");
		String Departure = request.getParameter("Inchoat");
		String Via = request.getParameter("Pass");
		String Destination = request.getParameter("Destination");
		String ShippingMark = request.getParameter("ShippingMark");
		String PackingQty = request.getParameter("PackingNumber");
	
		String Date = request.getParameter("ApplicantDate");
		String AdditionFactor = request.getParameter("AdditionFactor");
		String Address = request.getParameter("Address");
		String Applicant = request.getParameter("Applicant");
		String Contacts = request.getParameter("Contacts");
		String ContactsTel = request.getParameter("ContactsTel");
		String Currency = request.getParameter("Currency");
		String InvoiceAmount = request.getParameter("InvoiceAmount");
		String ShipFlight = request.getParameter("ShipFlight");
		String ShippingType = request.getParameter("ShippingType");
		String ZipCode = request.getParameter("ZipCode");
		String InsuredName = request.getParameter("InsuredName");
		String PayableAt = request.getParameter("PayableAt");
		String Indemnity = request.getParameter("Indemnity");
		String ToPort = request.getParameter("ToPort");
		String TransUtil = request.getParameter("TransUtil");
		String BLNO = request.getParameter("BLNO");
		String InsuranceType = request.getParameter("InsuranceType");
		String Transport = request.getParameter("Transport");
		/*
		try {
			InvoiceNO = new String(request.getParameter("InvoiceNO").getBytes("ISO-8859-1"),"utf-8");
			DepartureDate = new String(request.getParameter("StartDate").getBytes("ISO-8859-1"),"utf-8");
			AirWayBillNO = new String(request.getParameter("GoodsNO").getBytes("ISO-8859-1"),"utf-8");
			ContractNO = new String(request.getParameter("ContractNO").getBytes("ISO-8859-1"),"utf-8");
			DCNO = new String(request.getParameter("DCNO").getBytes("ISO-8859-1"),"utf-8");
			Departure = new String(request.getParameter("Inchoat").getBytes("ISO-8859-1"),"utf-8");
			Via = new String(request.getParameter("Pass").getBytes("ISO-8859-1"),"utf-8");
			Destination = new String(request.getParameter("Destination").getBytes("ISO-8859-1"),"utf-8");
			ShippingMark = new String(request.getParameter("ShippingMark").getBytes("ISO-8859-1"),"utf-8");
			PackingQty = new String(request.getParameter("PackingNumber").getBytes("ISO-8859-1"),"utf-8");
			
			Address = new String(request.getParameter("Address").getBytes("ISO-8859-1"),"utf-8");
			Applicant = new String(request.getParameter("Applicant").getBytes("ISO-8859-1"),"utf-8");
			Contacts = new String(request.getParameter("Contacts").getBytes("ISO-8859-1"),"utf-8");
			ContactsTel = new String(request.getParameter("ContactsTel").getBytes("ISO-8859-1"),"utf-8");
			ShipFlight = new String(request.getParameter("ShipFlight").getBytes("ISO-8859-1"),"utf-8");
			ShippingType = new String(request.getParameter("ShippingType").getBytes("ISO-8859-1"),"utf-8");
			//String ShippingMarkNO = new String(request.getParameter("ShippingMarkNO").getBytes("ISO-8859-1"),"utf-8");
			InsuredName = new String(request.getParameter("InsuredName").getBytes("ISO-8859-1"),"utf-8");
			PayableAt = new String(request.getParameter("PayableAt").getBytes("ISO-8859-1"),"utf-8");
			Indemnity = new String(request.getParameter("Indemnity").getBytes("ISO-8859-1"),"utf-8");
			ToPort = new String(request.getParameter("ToPort").getBytes("ISO-8859-1"),"utf-8");
			TransUtil = new String(request.getParameter("TransUtil").getBytes("ISO-8859-1"),"utf-8");
			BLNO = new String(request.getParameter("BLNO").getBytes("ISO-8859-1"),"utf-8");
			InsuranceType = new String(request.getParameter("InsuranceType").getBytes("ISO-8859-1"),"utf-8");
			Transport = new String(request.getParameter("Transport").getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		*/
		slip.setTransport(Transport);
		slip.setAdditionFactor(Double.parseDouble(AdditionFactor));
		slip.setAddress(Address);
		slip.setAirWayBillNO(AirWayBillNO);
		slip.setApplicant(Applicant);
		slip.setContacts(Contacts);
		slip.setContactsTel(ContactsTel);
		slip.setContractNO(ContractNO);
		slip.setCurrency(Currency);
		slip.setDate(Date);
		slip.setDCNO(DCNO);
		slip.setDeparture(Departure);
		slip.setDepartureDate(DepartureDate);
		slip.setDestination(Destination);
		
		slip.setInvoiceAmount(Double.parseDouble(InvoiceAmount));
		slip.setInvoiceNO(InvoiceNO);
		slip.setPackingQty(PackingQty);
		slip.setShipFlight(ShipFlight);
		slip.setShippingMark(ShippingMark);
		slip.setShippingType(ShippingType);
		slip.setVia(Via);
		slip.setZipCode(ZipCode);
		slip.setInsuredName(InsuredName);
		slip.setPayableAt(PayableAt);
		slip.setIndemnity(Indemnity);
		slip.setToPort(ToPort);
		slip.setTransUtil(TransUtil);
		slip.setBLNO(BLNO);
		slip.setInsuranceType(InsuranceType);
		boolean flag = dao.insert(slip);
		int id = 0;
		if(flag){
			id = dao.getIDByContractNO(ContractNO);
		}
		if(request.getParameter("isExist").equals("yes") && id != 0){
			String[] model = request.getParameterValues("Model[]");
			for(int i=0 ; i<model.length; i++){
				InsuranceGoods goods = new InsuranceGoods();
				goods.setModel(model[i]);
				goods.setSlipID(id);
				
				if(dao.insert(goods)){
					ls.add("true");
				}else{
					ls.add("false");
				}
			}
		}
		if(flag && !ls.contains("false")){
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-运输保险";
			String description = "新增-"+ContractNO;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean updateInsuranceSlip(HttpServletRequest request) {
		InsuranceSlip slip = new InsuranceSlip();
		InsuranceSlipDao dao = new InsuranceSlipDao();
		List<String> ls = new ArrayList<>();
		String ID = request.getParameter("ID");
		String InvoiceNO = request.getParameter("InvoiceNO");
		String DepartureDate = request.getParameter("StartDate");
		String AirWayBillNO = request.getParameter("GoodsNO");
		String ContractNO = request.getParameter("ContractNO");
		String DCNO = request.getParameter("DCNO");
		String Departure = request.getParameter("Inchoat");
		String Via = request.getParameter("Pass");
		String Destination = request.getParameter("Destination");
		String ShippingMark = request.getParameter("ShippingMark");
		String PackingQty = request.getParameter("PackingNumber");
	
		String Date = request.getParameter("ApplicantDate");
		String AdditionFactor = request.getParameter("AdditionFactor");
		String Address = request.getParameter("Address");
		String Applicant = request.getParameter("Applicant");
		String Contacts = request.getParameter("Contacts");
		String ContactsTel = request.getParameter("ContactsTel");
		String Currency = request.getParameter("Currency");
		String InvoiceAmount = request.getParameter("InvoiceAmount");
		String ShipFlight = request.getParameter("ShipFlight");
		String ShippingType = request.getParameter("ShippingType");
		String ZipCode = request.getParameter("ZipCode");
		String InsuredName = request.getParameter("InsuredName");
		String PayableAt = request.getParameter("PayableAt");
		String Indemnity = request.getParameter("Indemnity");
		String ToPort = request.getParameter("ToPort");
		String TransUtil = request.getParameter("TransUtil");
		String BLNO = request.getParameter("BLNO");
		String InsuranceType = request.getParameter("InsuranceType");
		String Transport = request.getParameter("Transport");
		/*
		try {
			InvoiceNO = new String(request.getParameter("InvoiceNO").getBytes("ISO-8859-1"),"utf-8");
			DepartureDate = new String(request.getParameter("StartDate").getBytes("ISO-8859-1"),"utf-8");
			AirWayBillNO = new String(request.getParameter("GoodsNO").getBytes("ISO-8859-1"),"utf-8");
			ContractNO = new String(request.getParameter("ContractNO").getBytes("ISO-8859-1"),"utf-8");
			DCNO = new String(request.getParameter("DCNO").getBytes("ISO-8859-1"),"utf-8");
			Departure = new String(request.getParameter("Inchoat").getBytes("ISO-8859-1"),"utf-8");
			Via = new String(request.getParameter("Pass").getBytes("ISO-8859-1"),"utf-8");
			Destination = new String(request.getParameter("Destination").getBytes("ISO-8859-1"),"utf-8");
			ShippingMark = new String(request.getParameter("ShippingMark").getBytes("ISO-8859-1"),"utf-8");
			PackingQty = new String(request.getParameter("PackingNumber").getBytes("ISO-8859-1"),"utf-8");
			
			Address = new String(request.getParameter("Address").getBytes("ISO-8859-1"),"utf-8");
			Applicant = new String(request.getParameter("Applicant").getBytes("ISO-8859-1"),"utf-8");
			Contacts = new String(request.getParameter("Contacts").getBytes("ISO-8859-1"),"utf-8");
			ContactsTel = new String(request.getParameter("ContactsTel").getBytes("ISO-8859-1"),"utf-8");
			ShipFlight = new String(request.getParameter("ShipFlight").getBytes("ISO-8859-1"),"utf-8");
			ShippingType = new String(request.getParameter("ShippingType").getBytes("ISO-8859-1"),"utf-8");
			InsuredName = new String(request.getParameter("InsuredName").getBytes("ISO-8859-1"),"utf-8");
			PayableAt = new String(request.getParameter("PayableAt").getBytes("ISO-8859-1"),"utf-8");
			Indemnity = new String(request.getParameter("Indemnity").getBytes("ISO-8859-1"),"utf-8");
			ToPort = new String(request.getParameter("ToPort").getBytes("ISO-8859-1"),"utf-8");
			TransUtil = new String(request.getParameter("TransUtil").getBytes("ISO-8859-1"),"utf-8");
			BLNO = new String(request.getParameter("BLNO").getBytes("ISO-8859-1"),"utf-8");
			InsuranceType = new String(request.getParameter("InsuranceType").getBytes("ISO-8859-1"),"utf-8");
			
			Transport = new String(request.getParameter("Transport").getBytes("ISO-8859-1"),"utf-8");
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		*/
		slip.setTransport(Transport);
		slip.setInsuredName(InsuredName);
		slip.setPayableAt(PayableAt);
		slip.setIndemnity(Indemnity);
		slip.setToPort(ToPort);
		slip.setTransUtil(TransUtil);
		slip.setBLNO(BLNO);
		slip.setInsuranceType(InsuranceType);
		slip.setAdditionFactor(Double.parseDouble(AdditionFactor));
		slip.setAddress(Address);
		slip.setAirWayBillNO(AirWayBillNO);
		slip.setApplicant(Applicant);
		slip.setContacts(Contacts);
		slip.setContactsTel(ContactsTel);
		slip.setContractNO(ContractNO);
		slip.setCurrency(Currency);
		slip.setDate(Date);
		slip.setDCNO(DCNO);
		slip.setDeparture(Departure);
		slip.setDepartureDate(DepartureDate);
		slip.setDestination(Destination);
		
		slip.setInvoiceAmount(Double.parseDouble(InvoiceAmount));
		slip.setInvoiceNO(InvoiceNO);
		slip.setPackingQty(PackingQty);
		slip.setShipFlight(ShipFlight);
		slip.setShippingMark(ShippingMark);
		slip.setShippingType(ShippingType);
		slip.setVia(Via);
		slip.setZipCode(ZipCode);
		slip.setID(Integer.parseInt(ID));
		boolean flag = dao.update(slip);
		System.out.println("flag"+flag);
		if(request.getParameter("isExist").equals("yes")){
			String[] model = request.getParameterValues("Model[]");
			String[] ids = request.getParameterValues("InfoID[]");
			for(int i=0 ; i<model.length; i++){
				InsuranceGoods goods = new InsuranceGoods();
				if(Integer.parseInt(ids[i]) == 0){
					goods.setModel(model[i]);
					goods.setSlipID(Integer.parseInt(ID));
					if(dao.insert(goods)){
						ls.add("true");
					}else{
						ls.add("false");
					}
				}else{
					goods.setModel(model[i]);
					goods.setID(Integer.parseInt(ids[i]));
					if(dao.updateInsuranceGoods(goods)){
						ls.add("true");
					}else{
						ls.add("false");
					}
				}
				
			}
		}
		if(flag && !ls.contains("false")){
			flag = true;
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-运输保险";
			String description = "修改-"+ContractNO;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean deleteInsuranceSlip(int id) {
		
		return new InsuranceSlipDao().delete(id);
	}

	@Override
	public int getCountByClassifyInOne(String classify, Object parameter) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(t_transport_insurance_slip.ID) from t_transport_insurance_slip "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_slip.ContractNO where ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "t_transport_insurance_slip."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getInsuranceSlipByClassifyInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select t_transport_insurance_slip.AdditionFactor,t_transport_insurance_slip.Address,t_transport_insurance_slip.AirWayBillNO GoodsNO,t_transport_insurance_slip.Applicant,"
				+ "t_transport_insurance_slip.Contacts,t_transport_insurance_slip.ContactsTel,t_transport_insurance_slip.ContractNO,"
				+ "t_transport_insurance_slip.Currency,t_transport_insurance_slip.Date ApplicantDate,"
				+ "t_transport_insurance_slip.DCNO,t_transport_insurance_slip.Departure Inchoat,t_transport_insurance_slip.DepartureDate StartDate,"
				+ "t_transport_insurance_slip.Destination,"
				+ "t_transport_insurance_slip.InvoiceAmount,t_transport_insurance_slip.InvoiceNO,t_transport_insurance_slip.InvoiceNO*1.1 InvoiceAmount,"
				+ "t_transport_insurance_slip.OperatingTime,t_transport_insurance_slip.PackingQty PackingNumber,"
				+ "t_transport_insurance_slip.ShipFlight,"
				+ "t_transport_insurance_slip.ShippingMark,t_transport_insurance_slip.ShippingType,t_transport_insurance_slip.Via Pass,"
				+ "t_transport_insurance_slip.ZipCode,t_transport_insurance_slip.ID,"
				+ "t_transport_insurance_slip.InsuredName,t_transport_insurance_slip.PayableAt,"
				+ "t_transport_insurance_slip.Indemnity,t_transport_insurance_slip.ToPort,"
				+ "t_transport_insurance_slip.TransUtil,t_transport_insurance_slip.BLNO,"
				+ "t_transport_insurance_slip.InsuranceType,t_transport_insurance_slip.Transport,"
				+ "t_order.Customer,t_order.ContractTitle from t_transport_insurance_slip "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_slip.ContractNO  ";
		for(int i=0 ; i<obj.length ; i++){
			if(classify.equals("客户名称") || classify.equals("合同名称")){
				sql += "where t_order."+classify_MAP.get(classify)+" like ?";
			}else{
				sql += "where t_transport_insurance_slip."+classify_MAP.get(classify)+" like ?";
			}
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " order by t_transport_insurance_slip.Date desc limit ?,?";
		Object[] param;
		if(obj.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else{
			param = new Object[obj.length+2];
			for(int i=0 ; i< obj.length ; i++){
				param[i] = obj[i];
			}
			param[obj.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj.length+1] = page.getRows();
		}
		return new InsuranceSlipDao().getInsuranceSlip(sql, param);
	}

	@Override
	public int getCountByClassifyInTwo(String classify1, Object parameter1, String classify2, Object parameter2) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select count(t_transport_insurance_slip.ID) from t_transport_insurance_slip "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_slip.ContractNO ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_transport_insurance_slip."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_transport_insurance_slip."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2;
		Object[] param = new Object[obj1.length+obj2.length];
		param[0]=obj1[0];
		param[1]=obj2[0];
		System.out.println("zuhe:"+sql);
		return new DBUtil().getCountsByName(sql, param);
	}

	@Override
	public List<Map<String, Object>> getInsuranceSlipByClassifyInTwo(String classify1, Object parameter1,
			String classify2, Object parameter2, Page page) {
		Object[] obj1 = null;
		switch(classify_MAP.get(classify1).toString()){
		default: obj1=new Object[1]; obj1[0]="%"+parameter1+"%";
		}
		String sql1 = "select t_transport_insurance_slip.AdditionFactor,t_transport_insurance_slip.Address,t_transport_insurance_slip.AirWayBillNO GoodsNO,t_transport_insurance_slip.Applicant,"
				+ "t_transport_insurance_slip.Contacts,t_transport_insurance_slip.ContactsTel,t_transport_insurance_slip.ContractNO,"
				+ "t_transport_insurance_slip.Currency,t_transport_insurance_slip.Date ApplicantDate,"
				+ "t_transport_insurance_slip.DCNO,t_transport_insurance_slip.Departure Inchoat,t_transport_insurance_slip.DepartureDate StartDate,"
				+ "t_transport_insurance_slip.Destination,"
				+ "t_transport_insurance_slip.InvoiceAmount,t_transport_insurance_slip.InvoiceNO,t_transport_insurance_slip.InvoiceNO*1.1 InvoiceAmount,"
				+ "t_transport_insurance_slip.OperatingTime,t_transport_insurance_slip.PackingQty PackingNumber,"
				+ "t_transport_insurance_slip.ShipFlight,"
				+ "t_transport_insurance_slip.ShippingMark,t_transport_insurance_slip.ShippingType,t_transport_insurance_slip.Via Pass,"
				+ "t_transport_insurance_slip.ZipCode,t_transport_insurance_slip.ID,"
				+ "t_transport_insurance_slip.InsuredName,t_transport_insurance_slip.PayableAt,"
				+ "t_transport_insurance_slip.Indemnity,t_transport_insurance_slip.ToPort,"
				+ "t_transport_insurance_slip.TransUtil,t_transport_insurance_slip.BLNO,"
				+ "t_transport_insurance_slip.InsuranceType,t_transport_insurance_slip.Transport,"
				+ "t_order.Customer,t_order.ContractTitle from t_transport_insurance_slip "
				+ "left join t_order on t_order.ContractNo=t_transport_insurance_slip.ContractNO  ";
		for(int i=0 ; i<obj1.length ; i++){
			if(classify1.equals("客户名称") || classify1.equals("合同名称")){
				sql1 += "where t_order."+classify_MAP.get(classify1)+" like ?";
			}else{
				sql1 += "where t_transport_insurance_slip."+classify_MAP.get(classify1)+" like ?";
			}
			if(i<obj1.length-1){
				sql1 += " or ";
			}
		}
		
		Object[] obj2 = null;
		String sql2 = "";
		switch(classify_MAP.get(classify2).toString()){
		default: obj2=new Object[1]; obj2[0]="%"+parameter2+"%";
		}
		for(int i=0 ; i<obj2.length ; i++){
			if(classify2.equals("客户名称") || classify2.equals("合同名称")){
				sql2 += "t_order."+classify_MAP.get(classify2)+" like ?";
			}else{
				sql2 += "t_transport_insurance_slip."+classify_MAP.get(classify2)+" like ?";
			}
			if(i<obj2.length-1){
				sql2 += " or ";
			}
		}
		String sql = sql1 +" and "+sql2+" order by t_transport_insurance_slip.Date desc limit ?,?";
		Object[] param;
		if(obj1.length == 0 && obj2.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else if(obj1.length != 0 && obj2.length == 0){
			param = new Object[obj1.length+2];
			for(int i=0 ; i<obj1.length ; i++){
				param[i] = obj1[i];
			}
			param[obj1.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj1.length+1] = page.getRows();
		}else if(obj1.length == 0 && obj2.length != 0){
			param = new Object[obj2.length+2];
			for(int i=0 ; i<obj2.length ; i++){
				param[i] = obj2[i];
			}
			param[obj2.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj2.length+1] = page.getRows();
		}else{
			param = new Object[obj1.length+obj2.length+2];
			
			for(int i=0 ; i<param.length-2 ; i++){
				if(i == 0){
					param[i] = obj1[0];
				}
				if(i == 1){
					param[i] = obj2[0];
				}
				
			}
			param[param.length-2] = (page.getCurrentPage()-1)*page.getRows();
			param[param.length-1] = page.getRows();
		}
		return new InsuranceSlipDao().getInsuranceSlip(sql, param);
	}

	@Override
	public List<Map<String, Object>> getInsuranceGoods(int id) {
		
		return new InsuranceSlipDao().getInsuranceGoods(id);
	}

	@Override
	public boolean deleteInsuranceGoods(int id) {
		
		return new InsuranceSlipDao().deleteInsuranceGoods(id);
	}

	@Override
	public Boolean searchContractNO(String ContractNO) {
		
		
		return new InsuranceSlipDao().getIDByContractNO(ContractNO)>=1;
	}

	@Override
	public String DownloadInsurancePolicy(HttpServletRequest request) {
		Map<String,Object> map = new InsuranceSlipDao().getInsurancePolicyInfo(Integer.parseInt(request.getParameter("ID")));
		HashMap<String,Object> dataMap = (HashMap<String,Object>)map.get("map");
		String[] modelNames = getModelName(map.get("InsuranceType").toString(),dataMap.get("${InsuredName}"));
		
		Java2Word word = new Java2Word();
		String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\"+modelNames[1]+".doc";
		word.toWord("E:\\Model\\"+modelNames[0]+".doc", downLoadUrl, dataMap,"end");
		ConventerToPDFUtil util = new ConventerToPDFUtil();
		util.word2pdf(downLoadUrl);
		String loadUrl = "down\\"+modelNames[1]+".pdf";
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "物流系统";
		String description = "下载-"+modelNames[1]+".pdf" + request.getParameter("ID");
		log.insert(request, JspInfo, description);
		return loadUrl;
	}
public static void main(String[] args) {
	long time1= System.currentTimeMillis();
	Map<String,Object> map = new InsuranceSlipDao().getInsurancePolicyInfo(38);
	long time2= System.currentTimeMillis();
	System.out.println(time2-time1);
	HashMap<String,Object> dataMap = (HashMap<String,Object>)map.get("map");
	String[] modelNames = new InsuranceSlipServiceImpl().getModelName(map.get("InsuranceType").toString(),dataMap.get("InsuredName"));
	long time3= System.currentTimeMillis();
	System.out.println(time3-time2);
	Java2Word word = new Java2Word();
	String downLoadUrl = "E:\\down\\"+modelNames[1]+".doc";
	word.toWord("E:\\Model\\"+modelNames[0]+".doc", downLoadUrl, dataMap,"end");
	long time4= System.currentTimeMillis();
	System.out.println(time4-time3);
	ConventerToPDFUtil util = new ConventerToPDFUtil();
	util.word2pdf(downLoadUrl);
	long time5= System.currentTimeMillis();
	System.out.println(time5-time4);
}
	private String[] getModelName(String InsuranceType,Object InsuredName) {
		String[] modelName = new String[2];
		switch (InsuranceType) {
		case " DomesticParcel":
			modelName[0]="国内货物运输险投保单邮包苏州模板";
			modelName[1]="(DomesticParcel)transportInsurancePolicy";
			break;
		case "DomesticAir":
			modelName[0]="国内货物运输险投保单陆运苏州模板";
			modelName[1]="(DomesticLandTransport)transportTnsurancePolicy";
					break;
		case "InOutParcel":
			if(InsuredName.toString().contains("香港")) {
				modelName[0]="进出口货物运输保险投保单邮包香港模板";
				modelName[1]="(ImportAndExportParcels)transportInsuranceIolicy";
			}else {
				modelName[0]="进出口货物运输保险投保单邮包苏州模板";
				modelName[1]="(ImportAndExportParcels)transportInsuranceIolicy";
			}
			break;
		case "InOutAir":
			if(InsuredName.toString().contains("香港")) {
				modelName[0]="进出口货物运输保险投保单航空香港模板";
				modelName[1]="(ImportAndExportAirlines)transportInsurancePolicy";
			}else {
				modelName[0]="进出口货物运输保险投保单航空苏州模板1";
				modelName[1]="(ImportAndExportAirlines)transportInsurancePolicy";
			}
			break;
		default:
			modelName[0]="国内货物运输险投保单邮包苏州模板";
			modelName[1]="(DomesticParcel)transportInsurancePolicy";
			break;
		}
		return modelName;
	}

}
