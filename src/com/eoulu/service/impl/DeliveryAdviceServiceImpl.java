package com.eoulu.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.dao.DeliveryAdviceDao;
import com.eoulu.dao.QuoteSystemDao;
import com.eoulu.entity.DeliveryAdvice;
import com.eoulu.entity.QuoteDelivery;
import com.eoulu.service.DeliveryAdviceService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendPreparationEmailUtil;

public class DeliveryAdviceServiceImpl implements DeliveryAdviceService{

	private static final String rootURL = "E:\\LogisticsFile\\File\\";
	@Override
	public Map<String, Object> getAdvice(int quoteID) {
		Map<String, Object> map = new HashMap<>();
		DeliveryAdviceDao dao = new DeliveryAdviceDao();
		List<Map<String, Object>> ls = dao.getAdvice(quoteID);
		List<Map<String, Object>> ls2 = new ArrayList<>();
		ls2 = ls.size()>1?dao.getAdviceList(quoteID):dao.getDelivery(quoteID);
		map.put("advice", ls);
		map.put("commodity", ls2);
		return map;
	}


	@Override
	public String opreate(HttpServletRequest request) {
		DeliveryAdviceDao dao = new DeliveryAdviceDao();
		DeliveryAdvice advice = new DeliveryAdvice();
		String result = "";
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		int quoteID = Integer.parseInt(request.getParameter("QuoteID")==null?"0":request.getParameter("QuoteID"));
		String packingFile = request.getParameter("PackingFile")==null?"":request.getParameter("PackingFile");
		String poNo = request.getParameter("PONO")==null?"":request.getParameter("PONO");
		String soNo = request.getParameter("SONO")==null?"":request.getParameter("SONO");
		String originService = request.getParameter("OriginService")==null?"":request.getParameter("OriginService");
		String company = request.getParameter("Company")==null?"":request.getParameter("Company");
		String contact = request.getParameter("Contact")==null?"":request.getParameter("Contact");
		String email = request.getParameter("Email")==null?"":request.getParameter("Email");
		String linkTel = request.getParameter("LinkTel")==null?"":request.getParameter("LinkTel");
		String installPlace = request.getParameter("InstallPlace")==null?"":request.getParameter("InstallPlace");
		advice.setID(id);
		advice.setPackingFile(packingFile);
		advice.setPONO(poNo);
		advice.setSONO(soNo);
		advice.setOriginService(originService);
		advice.setCompany(company);
		advice.setContact(contact);
		advice.setEmail(email);
		advice.setLinkTel(linkTel);
		advice.setInstallPlace(installPlace);
		advice.setQuoteID(quoteID);
		boolean flag = id==0?dao.insert(advice):dao.update(advice);
		result = flag?"提交成功！":"提交失败！";
		String[] deliveryID = request.getParameterValues("DeliveryID[]")==null?new String[0]:request.getParameterValues("DeliveryID[]");
		String[] model = request.getParameterValues("Model[]")==null?new String[0]:request.getParameterValues("Model[]");
		String[] description = request.getParameterValues("Description[]")==null?new String[0]:request.getParameterValues("Description[]");
		String[] quantity = request.getParameterValues("Quantity[]")==null?new String[0]:request.getParameterValues("Quantity[]");
		String[] remarks = request.getParameterValues("Remarks[]")==null?new String[0]:request.getParameterValues("Remarks[]");
		delete(deliveryID, quoteID);
		for(int i=0;i<deliveryID.length;i++){
			QuoteDelivery delivery = new QuoteDelivery();
			delivery.setID(Integer.parseInt(deliveryID[i]));
			delivery.setModel(model[i]==null?"":model[i]);
			delivery.setDescription(description[i]==null?"":description[i]);
			delivery.setQuantity(Integer.parseInt(quantity[i]==null?"0":quantity[i]));
			delivery.setRemarks(remarks[i]==null?"":remarks[i]);
			delivery.setQuoteID(quoteID);
			boolean temp = Integer.parseInt(deliveryID[i])==0?dao.insertDelivery(delivery):dao.upadteDelivery(delivery);
			if(!temp){
				result = "商品信息提交失败！";
				break;
			}
		}
		if(!getContent(request)){
			result = "邮件发送失败！";
		}
		return result;
	}

	//提交时删除
	public void delete(String[] deliveryID,int quoteID){
		System.out.println(Arrays.toString(deliveryID));
		DeliveryAdviceDao dao = new DeliveryAdviceDao();
		String[] attr = dao.getAdviceItem(quoteID);//所有数据表中商品ID
		if(attr.length!=0){
			for(int i=0;i<attr.length;i++){
				boolean flag = false;
					for(int j=0;j<deliveryID.length;j++){
						if(deliveryID[j].equals(attr[i])){
							flag =true;
							break;
						}
					}
					if(!flag){
						boolean temp = dao.delete(Integer.parseInt(attr[i]));
						System.out.println(temp?"删除成功":"删除失败");
					}
				
			}
		}
		
	}


	@Override
	public String getContractFile(int quoteID) {
		DeliveryAdviceDao dao = new DeliveryAdviceDao();
		String result = dao.getContractFile(quoteID);
		String temp = result.equals("")?"无合同文件和技术协议文件，是否继续提交备货？":"";
		if(result.contains(";")){
			temp = result.split(";").length>1?"":"无技术协议文件，是否继续提交备货？";
		}
		if(!result.equals("")&&!result.contains(";")){
			temp  = "无合同文件，是否继续提交备货？";
		}
		return temp;
	}
	
	public boolean mail(String subject,String content,String[] filelist){
		Properties props = new Properties();
		Properties pro = new Properties();
		boolean flag = false;
		try {
			props.load(SendPreparationEmailUtil.class.getResourceAsStream("email.properties"));
			String SEND_USER = props.getProperty("SEND_USER");
			String SEND_UNAME = props.getProperty("SEND_UNAME");
			String SEND_PWD = props.getProperty("SEND_PWD");
			pro.load(SendPreparationEmailUtil.class.getResourceAsStream("deliveryAdvice.properties"));
			int toCount = Integer.parseInt(pro.getProperty("to"));
			String[] to = new String[toCount];
			for(int i=0 ; i<toCount ; i++){
				int temp = i+1;
				String key = "To"+temp;
				to[i] = pro.getProperty(key);
				
			}
			int copytoCount = Integer.parseInt(pro.getProperty("copyto"));
			String[] copyto = new String[copytoCount];
			for(int i=0 ; i<copytoCount ; i++){
				int temp = i+1;
				String key = "CopyTo"+temp;
				copyto[i] = pro.getProperty(key);
				
			}
			JavaMailToolsUtil util = new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD);
			flag = util.doSendHtmlEmail(subject, content, filelist, to, copyto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean getContent(HttpServletRequest request){
		QuoteSystemDao sys = new QuoteSystemDao();
		DeliveryAdviceDao dao = new DeliveryAdviceDao();
		int quoteID = Integer.parseInt(request.getParameter("QuoteID")==null?"0":request.getParameter("QuoteID"));
		String packingFile = request.getParameter("PackingFile")==null?"":request.getParameter("PackingFile");
		String poNo = request.getParameter("PONO")==null?"":request.getParameter("PONO");
		String soNo = request.getParameter("SONO")==null?"":request.getParameter("SONO");
		String originService = request.getParameter("OriginService")==null?"":request.getParameter("OriginService");
		originService = originService.equals("是")?"有":"没有";
		String company = request.getParameter("Company")==null?"":request.getParameter("Company");
		String contact = request.getParameter("Contact")==null?"":request.getParameter("Contact");
		String email = request.getParameter("Email")==null?"":request.getParameter("Email");
		String linkTel = request.getParameter("LinkTel")==null?"":request.getParameter("LinkTel");
		String installPlace = request.getParameter("InstallPlace")==null?"":request.getParameter("InstallPlace");
		String table = request.getParameter("Table");
		String name = sys.getQuoteRequestName(quoteID).size()>1?sys.getQuoteRequestName(quoteID).get(1).get("Name").toString():"";
		String contractNO = sys.getQuoteRequestContractNO(quoteID).size()>1?sys.getQuoteRequestContractNO(quoteID).get(1).get("ContractNO").toString().trim():"";
		System.out.println("ff"+contractNO);
		String expectedDeliveryPeriod = sys.getExpectedDeliveryPeriod(contractNO).size()>1?sys.getExpectedDeliveryPeriod(contractNO).get(1).get("ExpectedDeliveryPeriod").toString():"";
		String subject = "Eoulu：发货通知-"+contractNO+"-"+name;
		String content = "<span style='font-family:微软雅黑;font-size:14px;'>丽丽姐，您好！</span><br><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>合同号："+contractNO+"，合同名称："+name+"，预计货期为："+expectedDeliveryPeriod+"。具体信息如下：</span><br>";
		String file = dao.getContractFile(quoteID);
		System.out.println("file=="+file);
		String contractFile = "";
		String technology = "";
		if(!file.equals("")){
			if(!file.contains(";")){
				contractFile = file;
			}else{
				contractFile = file.split(";")[0];
				if(file.split(";").length==2){
					technology = file.split(";")[1];
				}
				
			}
		}
		String FAT = new AcceptanceServiceImpl().exportReceiving(contractNO);
		String Receiving = new ReceivingServiceImpl().exportReceiving(contractNO);
		
		
		if(!technology.equals("")&&!technology.equals("--")){
			content += "<span style='font-family:微软雅黑;font-size:14px;'>1、附件"+technology+"是此票技术协议。</span><br>";
		}else{
			content += "<span style='font-family:微软雅黑;font-size:14px;'>1、此票无技术协议。</span><br>";
		}
		content += "<span style='font-family:微软雅黑;font-size:14px;'>2、附件"+packingFile+"是箱单文件</span><br>";
		if((!Receiving.equals("无验收报告"))&&(!FAT.equals("无FAT"))){
	
			content += "<span style='font-family:微软雅黑;font-size:14px;'>3、附件"+Receiving+"("+FAT+")是此票验收报告（或FAT）文件</span><br>";
		}else if((!Receiving.equals("无验收报告"))&&FAT.equals("无FAT")){
			content += "<span style='font-family:微软雅黑;font-size:14px;'>3、附件"+Receiving+"是此票验收报告文件</span><br>";
		}else{
			content += "<span style='font-family:微软雅黑;font-size:14px;'>3、此票无验收报告文件</span><br>";
		}
		content += "<span style='font-family:微软雅黑;font-size:14px;'>4、PO："+poNo+"<span style='padding-left:50px;'></span>SO:"+soNo+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>5、此次"+originService+"购买原厂装机服务。</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>6、最终用户信息：</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>公司名称："+company+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>联系人："+contact+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>Email："+email+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>联系电话："+linkTel+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>装机地点："+installPlace+"</span><br>";
		content += "<span style='font-family:微软雅黑;font-size:14px;'>7、以下为此票货物的发货清单</span><br>";
		content += table;
		content += "<span style='font-family:微软雅黑;font-size:14px;'>以上请您查收，如有疑问请随时联系。谢谢您！</span><br>";
		String[] fileList = null;
		packingFile = rootURL  + packingFile;
		String tech = "";
		String recei = "";
		String FA = "";
		if(!technology.equals("")&&!technology.equals("--")){
			tech = "1";
		}else{
			tech = "0";
		}
		if(Receiving.equals("无验收报告")){
			recei = "0";
		}else{
			recei = "1";
		}
		if(FAT.equals("无FAT")){
			FA = "0";
		}else{
			FA = "1";
		}
		switch (tech+recei+FA) {
		case "111":
			technology = rootURL  + technology;
			Receiving = rootURL + Receiving;
			FAT = rootURL + FAT;
			try {
				URLDecoder.decode(technology, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			 fileList = new String[]{technology,packingFile,Receiving,FAT};
			break;
		case "110":
			technology = rootURL  + technology;
			Receiving = rootURL + Receiving;
			try {
				URLDecoder.decode(technology, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			 fileList = new String[]{technology,packingFile,Receiving};
			break;
		case "100":
			technology = rootURL  + technology;
			try {
				URLDecoder.decode(technology, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			 fileList = new String[]{technology,packingFile};
			 break;
		case "101":
			technology = rootURL  + technology;
			FAT = rootURL + FAT;
			try {
				URLDecoder.decode(technology, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			 fileList = new String[]{technology,packingFile,FAT};
			 break;
		case "011":
			Receiving = rootURL + Receiving;
			FAT = rootURL + FAT;
		
			fileList = new String[]{packingFile,Receiving,FAT};
			break;
		case "010":
			Receiving = rootURL + Receiving;
			fileList = new String[]{packingFile,Receiving};
			break;
		case "001":
			FAT = rootURL + FAT;
			
			fileList = new String[]{packingFile,FAT};
			break;
		case "000":
			fileList = new String[]{packingFile};
			break;
		default:
			break;
		}
		
		
		
		System.out.println(Arrays.toString(fileList));
		content = new MethodUtil().getEmailSign(content, "NA");
		boolean flag = mail(subject, content, fileList);
		if(flag){
			boolean temp2 = dao.updateMailStatus(quoteID);
		}
		return flag;
				
	}
	
}
