package com.eoulu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.dao.TransportDirectiveDao;
import com.eoulu.entity.TransportDirective;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.TransportDirectiveService;
import com.eoulu.util.GeneratePdfUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.TransportDirectiveUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TransportDirectiveServiceImpl implements TransportDirectiveService{

	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new TransportDirectiveDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		return new TransportDirectiveDao().getAllCounts();
	}

	@Override
	public boolean operate(HttpServletRequest request) {
		
		 int id = Integer.parseInt(request.getParameter("ID"));
		String goodsInfo = request.getParameter("GoodsInfo")==null?"":request.getParameter("GoodsInfo");
		List<Map<String, Object>> goods = new ArrayList<>();
		Map<String, Object> updateMap = null;
		String con = "";
		if(!goodsInfo.equals("")){
			JSONArray array = JSONArray.fromObject(goodsInfo);
			for(int i = 0;i < array.size();i ++){
				JSONObject object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				String contractNO = object.get("ContractNO").toString();
				if(i == array.size() - 1){
					con += contractNO;
				}else{
					con += contractNO+"、";
				}
				updateMap.put("ContractNO", contractNO);
				updateMap.put("ReceivingAddress", object.get("ReceivingAddress").toString());
				updateMap.put("ReceivingCompany", object.get("ReceivingCompany").toString());
				updateMap.put("ReceivingContact", object.get("ReceivingContact").toString());
				updateMap.put("ReceivingTel", object.get("ReceivingTel").toString());
				updateMap.put("AddrID", Integer.parseInt(object.get("AddrID").toString()));
				updateMap.put("Size", object.get("Size").toString());
				updateMap.put("Weight", Double.parseDouble(object.get("Weight").equals("")?"0.0":object.get("Weight").toString()));
				updateMap.put("Quantity", object.get("Quantity").toString());
				updateMap.put("WoodenPallet", object.get("WoodenPallet").toString());
				updateMap.put("UnloadPlat", object.get("UnloadPlat").toString());
				updateMap.put("PickCompany", object.get("PickCompany").toString());
				updateMap.put("PickAddress", object.get("PickAddress").toString());
				updateMap.put("PickContact", object.get("PickContact").toString());
				updateMap.put("PickTel", object.get("PickTel").toString());
		
				goods.add(updateMap);
			}
			
		}
		
		String PackingDate = request.getParameter("PackingDate").equals("")?"0000-00-00 00:00:00": request.getParameter("PackingDate");
		String transporter = request.getParameter("transporters");

		int Total1 = 0;
		if(request.getParameter("Total1")!= null && !request.getParameter("Total1").equals("")){
			Total1 = Integer.parseInt(request.getParameter("Total1"));
		}
		int Total2 = 0;
		if(request.getParameter("Total2")!= null && !request.getParameter("Total2").equals("")){
			Total2 = Integer.parseInt(request.getParameter("Total2"));
		}
		int Total3 = 0;
		if(request.getParameter("Total3")!= null && !request.getParameter("Total3").equals("")){
			Total3 = Integer.parseInt(request.getParameter("Total3"));
		}
		TransportDirective td = new TransportDirective();
		
		
		td.setTransporter(transporter);
		td.setID(id);
		td.setContractNO(con);
		td.setPackingDate(PackingDate);
		td.setTotal1(Total1);
		td.setTotal2(Total2);
		td.setTotal3(Total3);
		td.setStatus("未发送");
		
		
		TransportDirectiveDao dao = new TransportDirectiveDao();
		
		boolean flag = dao.saveTransport(td, goods);
		if(flag){
			if(id==0){
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "物流部-国内货物运输指令";
				String description = "新增-"+con;
				log.insert(request, JspInfo, description);
			}else{
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "物流部-国内货物运输指令";
				String description = "修改-"+con;
				log.insert(request, JspInfo, description);
			}
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getModel(int id) {
		List<Map<String, Object>> ls = new TransportDirectiveDao().getAddress(id);
		
		return ls;
	}

	@Override
	public String sendEmail(HttpServletRequest request) {

		TransportDirectiveDao dao = new TransportDirectiveDao();
		
		
		String uploadFilePath = request.getServletContext().getRealPath("/") + "down\\";
		File uploadFile = new File(uploadFilePath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		String imagePath = request.getServletContext().getRealPath("/") + "down\\国内运输指令.pdf";
		
		String consignee = request.getParameter("Consignee");
		String password = request.getParameter("Password");
		String time = request.getParameter("time");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String ID = request.getParameter("ID");
		System.out.println("consignee:"+consignee);
		TransportDirectiveUtil util = new TransportDirectiveUtil(request,password);
		String[] to = consignee.split(";");
		int id = Integer.parseInt(ID);
		List<Map<String,Object>> base = dao.getTotal(id);
		List<Map<String,Object>> addr = dao.getAddress(id);
		System.out.println("time:"+time);
		GeneratePdfUtil.createPDF(imagePath, addr, base,time);
		String email = request.getSession().getAttribute("email").toString();
		StaffInfoDao st = new StaffInfoDao();
		List<Map<String,Object>> ls1 = st.getTelAndName(email);
		String tel = "NA";
		String username = "NA";
		if(ls1.size()>1){
			tel = ls1.get(1).get("LinkTel")=="--"?"NA":ls1.get(1).get("LinkTel").toString();
			username = ls1.get(1).get("StaffName")=="--"?"NA":ls1.get(1).get("StaffName").toString();
		}		
		String[] fileList =new String[] { imagePath};
		StringBuffer sb = new StringBuffer();

		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>我司有有一票货物，从"+address1+"运输到"+address2+"，提货时间"+time+"</span><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>注意事项：</span><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>烦请尽快安排处理，并给出航班信息，谢谢！</span><br><br>");

		MethodUtil util2 = new MethodUtil();
		String content = util2.getStaffEmailSign(sb.toString(), username, tel,email);

		String subject = "EOULU："+time+"从"+address1+"到"+address2+"的提货指令";

		String result = util.doSendHtmlEmail(to, subject, content, fileList);
	
		LogInfoService log = new LogInfoServiceImpl();
		String JspInfo = "物流部-国内货物运输指令";
		String description = "邮件-运输事宜:结果-"+result+"，密码-"+password;
		log.insert(request, JspInfo, description);
		if(result.equals("发送成功！")){
			TransportDirective td = new TransportDirective();
			td.setStatus("已发送");
			td.setID(id);
			if(!dao.upadteStatus(td)){
				result+="发送状态更新失败";
			}
		}
		
		return result;
		
	}

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String filePath = null;
		String id = null;
		String password = "";
		String consignee = "";
		String isExist = "notExists";
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
					if ("Consignee".equals(item.getFieldName())) {
						consignee = item.getString("utf-8");

					}
					if ("ID".equals(item.getFieldName())) {
						id = item.getString("utf-8");

					}
					if("Image".equals(item.getFieldName())){
						fileName = item.getString("utf-8");

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
					System.out.println(fileName);
				}
			}
		}
		System.out.println(consignee+"----"+fileName);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		map.put("password", password);
		map.put("consignee", consignee);
		map.put("isExist", isExist);
		map.put("ID", id);
		return map;
	}
	/*
	public static void printPDF(String path,String fileName){
		Document doc = new Document(PageSize.A4);
		try {
			String end = "";
			if(path.endsWith(".doc")){
				end = path.replace(".doc", ".pdf");
			}else
			if(path.endsWith(".txt")){
				end = path.replace(".txt", ".pdf");
			}else
			if(path.endsWith(".docx")){
				end = path.replace(".docx", ".pdf");
			}else{
				end = path+".pdf";
			}
			
			System.out.println(path.split(".").length+"---"+path);
			PdfWriter.getInstance(doc, new FileOutputStream(end));
			File file = new File(path);
	        byte[] bytes = new byte[(int) file.length()];
	        InputStream input = new FileInputStream(fileName);
	        input.read(bytes, 0, (int)file.length());
	        String content = new String(bytes, "GBK");
	        doc.open();
	        Paragraph p = new Paragraph(content);
	        doc.add(p);
	        doc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	*/
	 public static void htmlCodeComeFromFile(String filePath, String pdfPath) {  
	        Document document = new Document();  
	        try {  
	            StyleSheet st = new StyleSheet();  
	            st.loadTagStyle("body", "leading", "16,0");  
	            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));  
	            document.open();  
	            ArrayList p = HTMLWorker.parseToList(new FileReader(filePath), st);  
	            for(int k = 0; k < p.size(); ++k) { 
	                document.add((Element) p.get(k));  
	   	            
	            }  
	            document.close();  
	            System.out.println("文档创建成功");  
	        }catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	 
	 
	  public void htmlCodeComeString(String htmlCode, String pdfPath) {  
	        Document doc = new Document(PageSize.A4);  
	        try {  
	            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));  
	            doc.open();  
	            // 解决中文问题  
	            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
	            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);  
	            Paragraph t = new Paragraph(htmlCode, FontChinese);  
	            doc.add(t);  
	            doc.close();  
	            System.out.println("文档创建成功");  
	        }catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  

	@Override
	public boolean deleteAddress(HttpServletRequest request) {

		int id = Integer.parseInt(request.getParameter("AddrID"));
		return new TransportDirectiveDao().deleteAddress(id);
	}

	@Override
	public int getAllCounts(String type1, String searchContent1) {
		return new TransportDirectiveDao().getAllCounts(type1,searchContent1);
	}

	@Override
	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1) {
		return new TransportDirectiveDao().getAllData(page,type1,searchContent1);
	}

	@Override
	public int getAllCounts(String type1, String searchContent1, String type2, String searchContent2) {
		return new TransportDirectiveDao().getAllCounts(type1,searchContent1,type2,searchContent2);
	}

	@Override
	public List<Map<String, Object>> getAllData(Page page, String type1, String searchContent1, String type2,
			String searchContent2) {
		return new TransportDirectiveDao().getAllData(page,type1,searchContent1,type2,searchContent2);
	}
}
