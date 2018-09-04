package com.eoulu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.entity.OriginFactory;
import com.eoulu.entity.QuoteSystem;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.MailRecordService;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.MailRecordServiceImpl;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.eoulu.util.ConventerToPDFUtil;
import com.eoulu.util.Java2Word;
import com.eoulu.util.SendPOEmailUtil;
import com.google.gson.Gson;

@WebServlet("/SendOtherEmail")
public class SendOtherEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SendOtherEmailServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MailRecordService record = new MailRecordServiceImpl();
		QuoteSystemService service = new QuoteSystemServiceImpl();
		OriginFactoryService factory = new OriginFactoryServiceImpl();
		boolean flag = false;
		QuoteSystemDao dao = new QuoteSystemDao();
		QuoteSystem quoteSystem = new QuoteSystem();
		int id = Integer.parseInt(req.getParameter("QuoteID"));
		String user = req.getSession().getAttribute("user")==null ? "":req.getSession().getAttribute("user").toString();
		String email = req.getSession().getAttribute("email")==null ? "":req.getSession().getAttribute("email").toString();
		if(user.endsWith("@eoulu.com")){
			int index = user.indexOf("@");
			user = user.substring(0, index);	
		}
//		List<Map<String, Object>> ls = service.getOtherPOByQuoteID(id);
//		String image = req.getServletContext().getRealPath("/") + "image\\EOULUlogo.png";
//		String path = req.getServletContext().getRealPath("/") + "down\\" + "其他供应商PO.xlsx";
//		URLDecoder.decode(path, "UTF-8");
//		flag = service.exportOtherPOExcel(id, path, image,user,email);
		String status = req.getParameter("OtherStatus");
		HashMap<String, Object> data = new HashMap<>();
		List<Map<String,Object>> ls = dao.getQuoteOtherPO(id);
		List<Map<String,Object>> part = dao.getOtherTemp(id);
		String number = "";
		if(ls.size()>1){
			 number = ls.get(1).get("Number")==null?"":ls.get(1).get("Number").toString().trim();
			String version = ls.get(1).get("Version")==null?"":ls.get(1).get("Version").toString();
			String vendorOne = ls.get(1).get("VendorOne")==null?"":ls.get(1).get("VendorOne").toString();
			String vendorTwo = ls.get(1).get("VendorTwo")==null?"":ls.get(1).get("VendorTwo").toString();
			String vendorThree = ls.get(1).get("VendorThree")==null?"":ls.get(1).get("VendorThree").toString();
			String vendorFour = ls.get(1).get("VendorFour")==null?"":ls.get(1).get("VendorFour").toString();
			String forwarderOne = ls.get(1).get("ForwarderOne")==null?"":ls.get(1).get("ForwarderOne").toString();
			String forwarderTwo = ls.get(1).get("ForwarderTwo")==null?"":ls.get(1).get("ForwarderTwo").toString();
			String forwarderThree = ls.get(1).get("ForwarderThree")==null?"":ls.get(1).get("ForwarderThree").toString();
			String forwarderFour = ls.get(1).get("ForwarderFour")==null?"":ls.get(1).get("ForwarderFour").toString();
			String ShipOne = ls.get(1).get("ShipCompany")==null?"":ls.get(1).get("ShipCompany").toString();
			String ShipTwo = ls.get(1).get("ShipAddr")==null?"":ls.get(1).get("ShipAddr").toString();
			String ShipThree = ls.get(1).get("ShipTel")==null?"":ls.get(1).get("ShipTel").toString();
			String ShipFour = ls.get(1).get("ShipAttn")==null?"":ls.get(1).get("ShipAttn").toString();
			String creditTerm = ls.get(1).get("CreditTerm")==null?"":ls.get(1).get("CreditTerm").toString();
			String deliveryTerm = ls.get(1).get("DeliveryTerm")==null?"":ls.get(1).get("DeliveryTerm").toString();
			String shippingMark = ls.get(1).get("ShippingMark")==null?"":ls.get(1).get("ShippingMark").toString();
			String contractNO = ls.get(1).get("ContractNO")==null?"":ls.get(1).get("ContractNO").toString();
			String shipment = ls.get(1).get("ShipmentPort")==null?"":ls.get(1).get("ShipmentPort").toString();
			String subTotal = ls.get(1).get("SubTotal")==null?"":ls.get(1).get("SubTotal").toString();
			String discounted = ls.get(1).get("Discounted")==null?"":ls.get(1).get("Discounted").toString();
			String finalTotal = ls.get(1).get("FinalTotal")==null?"":ls.get(1).get("FinalTotal").toString();
		
			data.put("${Numbers}", number);
			data.put("${Versions}",version );
			data.put("${VendorOne}",vendorOne );
			data.put("${VendorTwo}", vendorThree);
			data.put("${VendorThree}", vendorTwo);
			data.put("${VendorFour}",vendorFour );
			data.put("${ForwarderOne}", forwarderOne);
			data.put("${ForwarderTwo}", forwarderTwo);
			data.put("${ForwarderThree}", forwarderThree);
			data.put("${ForwarderFour}", forwarderFour);
			data.put("${ShipOne}", ShipOne);
			data.put("${ShipTwo}", ShipTwo);
			data.put("${ShipThree}", ShipThree);
			data.put("${ShipFour}",ShipFour );
			data.put("${CreditTerm}", creditTerm);
			data.put("${DeliveryTerm}",deliveryTerm );
			data.put("${ShippingMark}",shippingMark );
			data.put("${ContractNO}",contractNO );
			data.put("${Shipment}",shipment );
			data.put("${SubTotal}", "$"+subTotal);
			data.put("${Discounted}", discounted);
			data.put("${FinalTotal}", "$"+finalTotal);
			data.put("${Contact}", user);
			data.put(" ${Email}", email);
		}else{
			data.put("${Numbers}", "");
			data.put("${Versions}","" );
			data.put("${VendorOne}","" );
			data.put("${VendorTwo}", "");
			data.put("${VendorThree}","" );
			data.put("${VendorFour}","" );
			data.put("${ForwarderOne}", "");
			data.put("${ForwarderTwo}", "");
			data.put("${ForwarderThree}", "");
			data.put("${ForwarderFour}", "");
			data.put("${ShipOne}", "");
			data.put("${ShipTwo}", "");
			data.put("${ShipThree}", "");
			data.put("${ShipFour}","" );
			data.put("${CreditTerm}", "");
			data.put("${DeliveryTerm}","" );
			data.put("${ShippingMark}","" );
			data.put("${ContractNO}","" );
			data.put("${Shipment}",""  );
			data.put("${SubTotal}", "" );
			data.put("${Discounted}", "" );
			data.put("${FinalTotal}", "" );
			data.put("${Contact}", user);
			data.put(" ${Email}", email);
		}
		 ArrayList table = new ArrayList(part.size());
	        String[] fieldName = {"1","2","3","4","5","6"};
	        table.add(fieldName);
	        if(part.size()<1){
			        	String Model = "";
			        	String Description = "";
			        	String Quantity = "";
			        	String UnitPrice = "";
			        	String ExtendedPrice = "";
			        	 String[] field = {""+1,Model,Description,Quantity,UnitPrice,ExtendedPrice};
			             table.add(field);
	        }else{
	        	 for(int i=1; i<part.size();i++){
			        	String Model = part.get(i).get("Model")==null?"":part.get(i).get("Model").toString();
			        	String Description = part.get(i).get("Description")==null?"":part.get(i).get("Description").toString();
			        	String Quantity = part.get(i).get("Quantity")==null?"":part.get(i).get("Quantity").toString();
			        	String UnitPrice = part.get(i).get("UnitPrice")==null?"":part.get(i).get("UnitPrice").toString();
			        	String ExtendedPrice = part.get(i).get("ExtendedPrice")==null?"":part.get(i).get("ExtendedPrice").toString();
			        	 String[] field = {""+i,Model,Description,Quantity,"$"+UnitPrice,"$"+ExtendedPrice};
			             table.add(field);
			        }
	        }
	       
	        data.put("table$2@7",table);
	        Java2Word word = new Java2Word();
	        String downLoadUrl = req.getServletContext().getRealPath("/")+"down\\(USD)OtherSupplierPO-"+number.trim()+".doc";
	        long time1 = System.currentTimeMillis();
	        word.toWord("E:/Model/其他供应商PO-美金word模板.docx",downLoadUrl,data,"end");
	        System.out.println("time cost : " + (System.currentTimeMillis() - time1));
			String loadUrl = "down\\(USD)OtherSupplierPO-"+number.trim()+".doc";
			ConventerToPDFUtil cf = new ConventerToPDFUtil();
			cf.word2pdf(downLoadUrl);
			loadUrl = "down\\" + "(USD)OtherSupplierPO-"+number.trim()+".pdf";
//			URLDecoder.decode(loadUrl,"UTF-8");
//			System.out.println(loadUrl);
//		if (status.equals("yes") && flag) {
			String[] to = req.getParameter("ToEmail").split(",");
			String[] copy = req.getParameter("CopyTo").split(",");
			String name = req.getParameter("NickName");
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
			// 显示邮件内容
			sb.append("<body style='font-family:微软雅黑;font-size:14px;'>");
			
			sb.append("<span style='font-family:Arial;font-size:14px;'>Hi,All,</span><br><br>");
			sb.append("<span style='font-family:Arial;font-size:14px;'>Please kindly refer to the attachment.</span><br>");
			sb.append("<span style='font-family:Arial;font-size:14px;'>Would you please help enter it and arrange a soonest ship date?</span><br><br>");
			sb.append("<span style='font-family:Arial;font-size:14px;'>Thanks for your support！</span><br>");
			sb.append("<span style='color:#000080'>------------------------</span><br>");
			sb.append("<span style='color:#000080;font-family:Arial;font-size:18px;font-weight:800;'>Eoulu<span><br>");
			sb.append("<span style='color:#000080;font-family:宋体;font-size:13px;font-weight:800;'>苏州伊欧陆系统集成有限公司/<span>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:13px;font-weight:800;'>Suzhou Eoulu System Integration Co., Ltd<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址: 苏州工业园区星湖街218号生物纳米园A7楼305室<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 305, Building A7, No.218 Xinghu Road, Suzhou Industrial Park, Suzhou, China<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:215000  电话/Tel: +86-512-62757360  传真/Fax: +86-512-62757313<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>手机/MP: +86-18566664208 邮箱:remind@eoulu.com<span><br>");
			sb.append("<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>公司网址: <span>");
			sb.append(
					"<span style='color:rgb(87,0,255);font-family:Arial,宋体;font-size:12px;font-weight:200;'> http://www.eoulu.com <span><br><br>");
			sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>厦门办事处/<span>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Xiamen Office<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址： 厦门思明区世茂海峡大厦A塔4508-1室<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 4508-1, Block A, Shimao Haixia Mansion, Siming District, Xiamen<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:361001 电话/Tel:+86-592-2052302  <span><br><br>");

			sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>深圳伊欧陆微电子系统有限公司/<span>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Shenzhen Eoulu Micro-System Co.,Ltd<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：深圳宝安国际机场中城T3·space产业创新园D座501<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Unit 501,Floor 5, Block D of Zhongcheng T3·space Industrial Innovation Park,  Bao'an International Airport, Bao'an, Shenzhen<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:518100 电话/Tel: +86-755-2162 7980/7981 传真/Fax: +86-755-26975515<span><br><br>");
			// sb.append("<span
			// style='color:#000080;font-family:宋体;font-weight:800;font-size:13px;'>北京办事处/<span>");
			// sb.append(
			// "<span
			// style='color:#000080;font-family:Arial;font-weight:800;font-size:13px;'>Beijing
			// Office<span><br>");
			// sb.append(
			// "<span
			// style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址:
			// 北京市朝阳区广渠路金茂府23号院6号楼520室<span><br>");
			// sb.append(
			// "<span
			// style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:100085
			// 电话/Tel: +86-10-62915850 传真/Fax: +86-10-62916716<span><br><br>");
			sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>成都办事处/<span>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Chengdu Office<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址: 成都市成华区二环路东二段7号招商东城国际A栋2005室<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:610000  电话/Tel: +86-028-84474790  <span><br><br>");
			sb.append("<span style='color:#000080;font-family:宋体;font-weight:800;font-size:13px'>合肥办事处/<span>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-weight:800;font-size:13px'>Hefei Office<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:宋体;font-size:12px;font-weight:200;'>地址：合肥高新区望江西路800号高新创业园A4栋302室<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Room 302, Building A4, No.800 Wangjiangxi Road, High-tech Starting-up business incubation, Hefei, China<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial,宋体;font-size:12px;font-weight:200;'>邮编/P.C:230088  电话/Tel: +86-551-64382598<span><br><br>");
			sb.append("<span style='color:#000080;font-family:Arial;font-weight:800;'>HK EOULU TRADING LIMITED<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Suite 2, Room1201, 12/F., Chinachem Johnston Plaza, 178-186 Johnston Road, Wanchai, Hong Kong<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Tel:00852-21527388   Fax:00852-35719160<span><br><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>Please consider your environmental responsibility before printing this e-mail.This e-mail may contain confidential or privileged information.<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>If you are not the intended recipient (or received it in error) please notify the sender immediately and delete this e-mail. Unauthorized copying,<span><br>");
			sb.append(
					"<span style='color:#000080;font-family:Arial;font-size:12px;font-weight:200;'>disclosure or distribution of the material in this e-mail is strictly forbidden.<span><br><br>");
			sb.append("</body></html>");

			String content = sb.toString();
			String subject = "Eoulu：New Customer Request PO"+number;
			String path = req.getServletContext().getRealPath("/")+loadUrl;
			System.out.println(path);
			String[] fileList = new String[] { path };
			SendPOEmailUtil util = new SendPOEmailUtil();
			boolean temp = util.doSendHtmlEmail(to, copy, subject, content, fileList);
			if (temp) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "报价系统";
				String description = "邮件-其他供应商 PO"+service.getQuoteNumber(id);
				log.insert(req, JspInfo, description);
				if (ls.size() > 1) {
					int POID = Integer.parseInt(ls.get(1).get("ID").toString());
					String po = ls.get(1).get("Number").toString();
					if (po != null && !po.equals("") && !factory.getInfoByPO(POID,"OtherSupplier")) {
						OriginFactory entity = new OriginFactory();
						entity.setPO(po);
						entity.setSO("");
						entity.setFactoryPeriod("0000-00-00");
						entity.setDelayPeriod("0000-00-00");
						entity.setDelayReason("");
						entity.setPOID(POID);
						entity.setPOType("OtherSupplier");
						entity.setType("no");
						factory.insert(entity);
						System.out.println();
					}
				}
				quoteSystem.setOtherStatus(status);
				quoteSystem.setID(id);
				if (dao.updateOtherStatus(quoteSystem) && record.insert(req)) {
					flag = true;
				}
			}
//		}
//		System.out.println(flag);
//		if (status.equals("no")) {
//			quoteSystem.setOtherStatus(status);
//			quoteSystem.setID(id);
//			if (dao.updateOtherStatus(quoteSystem)) {
//				flag = true;
//			}
//		}
		resp.getWriter().write(new Gson().toJson(flag));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
