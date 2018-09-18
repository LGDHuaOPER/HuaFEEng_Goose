package com.eoulu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.eoulu.commonality.Page;
import com.eoulu.dao.FormfactoryfilesDao;
import com.eoulu.dao.OriginFactoryDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OriginFactory;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.util.DownloadUrl;
import com.eoulu.util.GeneratePdfUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendCustomerRequestUtil;

public class OriginFactoryServiceImpl implements OriginFactoryService{

	private static final String rootUrl = "E:\\LogisticsFile\\File\\";
	private static final String webUrl = new DownloadUrl().getRootUrl();
	
	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new OriginFactoryDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		return new OriginFactoryDao().getAllCounts();
	}

	@Override
	public String update(HttpServletRequest request) {

		String po = request.getParameter("PO").trim();

		String so = request.getParameter("SO").trim();
		String factoryPeriod = request.getParameter("FactoryPeriod")==""?"0000-00-00":request.getParameter("FactoryPeriod");
		String delayPeriod = request.getParameter("DelayPeriod")==""?"0000-00-00": request.getParameter("DelayPeriod");
		String delayReason = request.getParameter("DelayReason")==null?"": request.getParameter("DelayReason");
		String ShippingInstruction = request.getParameter("ShippingInstruction");
		String SOA = request.getParameter("SOA");
		String BAFA = request.getParameter("BAFA");
		String TargetDate = request.getParameter("TargetDate")==""?"0000-00-00":request.getParameter("TargetDate");
		String TrackingNO = request.getParameter("TrackingNO").trim();
		String PaymentLC = request.getParameter("PaymentLC").trim();
		String DutyExemption = request.getParameter("DutyExemption");
		String DelayInfo = request.getParameter("DelayInfo").trim();
		String InvoiceNo1 = request.getParameter("InvoiceNo1").trim();
		String InvoiceFile1 = request.getParameter("InvoiceFile1");
		String InvoiceNo2 = request.getParameter("InvoiceNo2").trim();
		String InvoiceFile2 = request.getParameter("InvoiceFile2");
		String InvoiceNo3 = request.getParameter("InvoiceNo3").trim();
		String InvoiceFile3 = request.getParameter("InvoiceFile3");
		String EstimatePaymentDate = request.getParameter("EstimatePaymentDate")==""?"0000-00-00": request.getParameter("EstimatePaymentDate");

		int ID = Integer.parseInt(request.getParameter("ID"));
		OriginFactory factory = new OriginFactory();
		factory.setDelayInfo(DelayInfo);
		factory.setShippingInstruction(ShippingInstruction);
		factory.setSOA(SOA);
		factory.setBAFA(BAFA);
		factory.setTargetDate(TargetDate);
		factory.setTrackingNO(TrackingNO);
		factory.setPaymentLC(PaymentLC);
		factory.setDutyExemption(DutyExemption);
		factory.setPO(po);
		factory.setSO(so);
		factory.setFactoryPeriod(factoryPeriod);
		factory.setDelayPeriod(delayPeriod);
		factory.setDelayReason(delayReason);
		factory.setInvoiceNo1(InvoiceNo1);
		factory.setInvoiceFile1(InvoiceFile1);
		factory.setInvoiceNo2(InvoiceNo2);
		factory.setInvoiceFile2(InvoiceFile2);
		factory.setInvoiceNo3(InvoiceNo3);
		factory.setInvoiceFile3(InvoiceFile3);
		factory.setEstimatePaymentDate(EstimatePaymentDate);
		factory.setID(ID);
		Logistics log = new Logistics();
		log.setPONO(po);
		log.setSONO(so);
		log.setFactoryShipment(factoryPeriod);
		boolean temp = new OriginFactoryDao().update(factory,log);
	
		LogInfoService logs = new LogInfoServiceImpl();
		if(temp){
			String JspInfo = "FORMFACTOR";
			String description = po+"更新操作";
			logs.insert(request, JspInfo, description);
		}
		String flag = temp?"保存成功！":"修改失败！";
		return flag;
	}
	
	public String sendEmail(HttpServletRequest request) {
		String po = request.getParameter("PO").trim();

		String so = request.getParameter("SO").trim();
		String factoryPeriod = request.getParameter("FactoryPeriod")==""?"0000-00-00":request.getParameter("FactoryPeriod");
		String delayPeriod = request.getParameter("DelayPeriod")==""?"0000-00-00": request.getParameter("DelayPeriod");
		String delayReason = request.getParameter("DelayReason")==null?"": request.getParameter("DelayReason");
		String ShippingInstruction = request.getParameter("ShippingInstruction");
		String SOA = request.getParameter("SOA");
		String BAFA = request.getParameter("BAFA");
		String TargetDate = request.getParameter("TargetDate")==""?"0000-00-00":request.getParameter("TargetDate");
		String TrackingNO = request.getParameter("TrackingNO").trim();
		String PaymentLC = request.getParameter("PaymentLC").trim();
		String DutyExemption = request.getParameter("DutyExemption");
		String DelayInfo = request.getParameter("DelayInfo").trim();
		
		String EstimatePaymentDate = request.getParameter("EstimatePaymentDate")==""?"0000-00-00": request.getParameter("EstimatePaymentDate");
		String pwd = request.getParameter("pwd").trim();
		int ID = Integer.parseInt(request.getParameter("ID"));
		OriginFactory factory = new OriginFactory();
		factory.setDelayInfo(DelayInfo);
		factory.setShippingInstruction(ShippingInstruction);
		factory.setSOA(SOA);
		factory.setBAFA(BAFA);
		factory.setTargetDate(TargetDate);
		factory.setTrackingNO(TrackingNO);
		factory.setPaymentLC(PaymentLC);
		factory.setDutyExemption(DutyExemption);
		factory.setPO(po);
		factory.setSO(so);
		factory.setFactoryPeriod(factoryPeriod);
		factory.setDelayPeriod(delayPeriod);
		factory.setDelayReason(delayReason);
	
		factory.setEstimatePaymentDate(EstimatePaymentDate);
		factory.setID(ID);
		String result = "";
		if(request.getSession().getAttribute("email").equals("AChen3@formfactor.com")){
			//if(request.getSession().getAttribute("email").equals("wangning@eoulu.com")){
			result = SendMailByFormFactory(factory,rootUrl,request.getSession(),pwd);
		}else{
			result = SengEmail(factory,rootUrl,request.getSession(),pwd);
		}
		LogInfoService logs = new LogInfoServiceImpl();
		String JspInfo = "FORMFACTOR";
		String description = "原厂邮件发送-提示信息："+result;
		logs.insert(request, JspInfo, description);
		return result;
			
		
	}
	
	
	private String SendMailByFormFactory(OriginFactory factory,String path, HttpSession session,String password){
		String uploadFilePath =  path;
		File uploadFile = new File(uploadFilePath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		String tempFilePath = path ;
		File tempPathFile = new File(tempFilePath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		String imagePath = path + "New-customer-request.pdf";
		GeneratePdfUtil.createPDF(imagePath,factory);
		String[] fileList =new String[] { imagePath};
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head><META content='text/html; charset=gbk' http-equiv=Content-Type>");
		sb.append("<META name=GENERATOR content='MSHTML 8.00.6001.18702'>");
		sb.append("<style type='text/css'>");
		sb.append(".STYLE1 {color: #000000}");
		sb.append(
				"TABLE {FONT-SIZE: 12px; COLOR: #444444;LINE-HEIGHT: 14px; FONT-FAMILY: '宋体', 'Arial'; TEXT-DECORATION: none;}");
		sb.append(".STYLE3 { font-size: 13px;color: #FD9800; font-weight: bold;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body style='font-family:Arial,微软雅黑;font-sizel:14px;'>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Hi, All,</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>We have updated information, please see the attachment.</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Thanks for your support!</span><br><br>");
		sb.append("---------------------------------------");
		String weburl = session.getServletContext().getRealPath("/");
		System.out.println(weburl);
		
		sb.append("<div style='color: rgb(31,73,125);font-size:14px;font-family:Arial,微软雅黑;'>"
		+"<img src='"+weburl+"image/mail-sign1.jpg' alt='FORMFACTOR'>"
		+"<p style='line-height: 15px;'>Best Regards</p>"
		+"<p style='line-height: 15px;'>Ailin Chen</p>"
		+"<p style='line-height: 15px;'>Suzhou Office: &nbsp;0512-69170108-8092</p>"
		+"<p style='line-height: 15px;'>Shanghai Office: &nbsp;021-33303188-103</p>"
		+"</div>");
		sb.append("</body></html>");
		String subject = "LOGISTIC INFORMATION UPDATE，"+factory.getPO()+","+factory.getSO();
		SendCustomerRequestUtil requestUtil = new SendCustomerRequestUtil();
		String flag = requestUtil.doSendHtmlEmail(session.getAttribute("email").toString(),session.getAttribute("email").toString(), password,subject, sb.toString(), fileList,"formfactor");
		return flag;

		
	}

	private String SengEmail(OriginFactory factory,String path, HttpSession session,String password) {
		String uploadFilePath =  path;
		File uploadFile = new File(uploadFilePath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		String tempFilePath = path ;
		File tempPathFile = new File(tempFilePath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		String imagePath = path + "New-customer-request.pdf";
		GeneratePdfUtil.createPDF(imagePath,factory);
		String[] fileList =new String[] { imagePath};
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head><META content='text/html; charset=gbk' http-equiv=Content-Type>");
		sb.append("<META name=GENERATOR content='MSHTML 8.00.6001.18702'>");
		sb.append("<style type='text/css'>");
		sb.append(".STYLE1 {color: #000000}");
		sb.append(
				"TABLE {FONT-SIZE: 12px; COLOR: #444444;LINE-HEIGHT: 14px; FONT-FAMILY: '宋体', 'Arial'; TEXT-DECORATION: none;}");
		sb.append(".STYLE3 { font-size: 13px;color: #FD9800; font-weight: bold;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body style='font-family:Arial,微软雅黑;font-sizel:14px;'>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Hi, All,</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>We have updated information, please see the attachment.</span><br><br>");
		sb.append("<span style='font-family:Arial;font-size:14px;'>Thanks for your support!</span><br><br>");
		StaffInfoDao st = new StaffInfoDao();
		String email = session.getAttribute("email").toString();
		List<Map<String,Object>> ls1 = st.getTelAndName(email);
		String tel = "NA";
		String username = "NA";
		if(ls1.size()>1){
			tel = ls1.get(1).get("LinkTel")=="--"?"NA":ls1.get(1).get("LinkTel").toString();
			username = ls1.get(1).get("StaffName")=="--"?"NA":ls1.get(1).get("StaffName").toString();
		}
		MethodUtil util = new MethodUtil();
		String content = util.getStaffEmailSign(sb.toString(), username, tel, email);
		
		content+="</body></html>";
		String subject = "LOGISTIC INFORMATION UPDATE，PO"+factory.getPO()+",SO"+factory.getSO();
		SendCustomerRequestUtil requestUtil = new SendCustomerRequestUtil();
		String flag = requestUtil.doSendHtmlEmail(session.getAttribute("email").toString(),session.getAttribute("email").toString(), password,subject, content, fileList,"eoulu");
		return flag;
	}

	@Override
	public boolean delete(int id) {
		return new OriginFactoryDao().delete(id);
	}

	@Override
	public boolean updateType(OriginFactory factory) {
		
		return new OriginFactoryDao().updateType(factory);
	}

	@Override
	public boolean insert(OriginFactory factory) {
		return new OriginFactoryDao().insert(factory);
	}

	@Override
	public boolean getInfoByPO(int poID,String type) {
		return new OriginFactoryDao().getInfoByPO(poID,type);
	}

	@Override
	public boolean upLoadFiles(HttpServletRequest request) {
		boolean flag = false;
		String path01 = "D:\\tempZipFile";
		File filePath = new File(path01);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		String tempPath = rootUrl;
		Map<String,String> map = new HashMap<>();
		try {
			map = getForm(filePath, request, tempPath);
		} catch (FileUploadException | IOException e) {
			e.printStackTrace();
		}
		String name = map.get("fileName");
		String ID = map.get("ID");
		String Type = map.get("Type");
		System.out.println("formfactory:::"+name);
	    flag= new FormfactoryfilesDao().insert(webUrl+name,Integer.parseInt(ID),Integer.parseInt(Type));
		boolean flag2 = new OriginFactoryDao().updateFileFlag(Integer.parseInt(ID),Integer.parseInt(Type));
	    if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "文档上传";
			String description = "上传-PDF";
			log.insert(request, JspInfo, description);
		}
		
		return flag;
	}

	private Map<String, String> getForm(File tempFilePath, HttpServletRequest request, String tempPath) throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String ID = null;
		String filePath = null;
		String Type = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
		factory.setRepository(tempFilePath);// 设置临时目录
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
					if ("ID".equals(item.getFieldName())) {
						ID = item.getString("utf-8");
					}
					if ("Type".equals(item.getFieldName())) {
						Type = item.getString("utf-8");

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
					File tempFile = new File(tempPath + fileName);
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
		map.put("ID", ID);
		map.put("Type", Type);
		return map;
	}

	@Override
	public String downLoadFiles(HttpServletRequest request) {
		int ID = Integer.parseInt(request.getParameter("ID"));
		int Type = Integer.parseInt(request.getParameter("Type"));
		System.out.println(ID+","+new FormfactoryfilesDao().getFilePath(ID,Type));
		String path = new FormfactoryfilesDao().getFilePath(ID,Type);
		String rootUrl = new DownloadUrl().getRootUrl();
		return path.contains("down")?path.replaceFirst("down/", rootUrl):path;
	}

	@Override
	public int getAllCountsByTwo(String type1, String content1, String type2, String content2) {
		return new OriginFactoryDao().getAllCountsByTwo(type1,content1,type2,content2);
	}

	@Override
	public List<Map<String, Object>> getAllDataByTwo(Page page, String type1, String content1, String type2, String content2) {
		return new OriginFactoryDao().getAllDataByTwo(page,type1,content1,type2,content2);
	}

	@Override
	public int getAllCountsByOne(String type1, String content1) {
		return new OriginFactoryDao().getAllCountsByOne(type1,content1);
	}

	@Override
	public List<Map<String, Object>> getAllDataByone(Page page, String type1, String content1) {
		return new OriginFactoryDao().getAllDataByOne(page,type1,content1);
	}

	@Override
	public List<Map<String, Object>> getMail(int factoryID, String type) {
		
		return new OriginFactoryDao().getMail(type, factoryID);
	}


}
