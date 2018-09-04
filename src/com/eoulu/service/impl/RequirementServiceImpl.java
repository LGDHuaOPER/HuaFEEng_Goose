/**
 * 
 */
package com.eoulu.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.RequirementBrandDao;
import com.eoulu.dao.RequirementClassifyDao;
import com.eoulu.dao.RequirementDao;
import com.eoulu.dao.RequirementOrderTimeDao;
import com.eoulu.dao.RequirementQuotesDao;
import com.eoulu.dao.RequirementSourceDao;
import com.eoulu.entity.Requirement;
import com.eoulu.service.RequirementService;
import com.eoulu.util.DBUtil;
import com.eoulu.util.RequirementUtil;

/**
 * @author zhangkai
 *
 */
public class RequirementServiceImpl implements RequirementService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eoulu.service.RequirementService#RequirementQuery(com.eoulu.
	 * commonality.Page)
	 */
	@Override
	public List<Map<String, Object>> RequirementQuery(Page page, String classify1, String parameter1) {
		// TODO Auto-generated method stub
		return new RequirementDao().queryByPage(page, classify1, parameter1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.eoulu.service.RequirementService#RequirementAdd(com.eoulu.commonality
	 * .Page)
	 */
	@Override
	public boolean RequirementAdd(Requirement requirement, HttpServletRequest request) {

		RequirementDao dao = new RequirementDao();
		String contents = request.getParameter("Content");
		boolean flag = dao.insert(requirement);
		int id = 0;
		if (flag) {
			id = dao.getID(requirement.getRequirementDate(), requirement.getCustomerName(),
					requirement.getRequirementContent());
		}
		String CustomerName = "";
		String Contact = "";
		String ContactTel = "";
		String Email = "";
		String Area = "";
		String Province = requirement.getProvince();
		String Requirement = requirement.getRequirementContent();
		String Source = "";
		String SalesMan = "";
		List<Map<String, Object>> ls = null;
		if (id != 0) {
			ls = dao.getCustomerInfo(id);
			if (ls.size() > 1) {
				CustomerName = ls.get(1).get("CustomerName").toString();
				Contact = ls.get(1).get("Contact").toString();
				ContactTel = ls.get(1).get("ContactInfo1").toString();
				Email = ls.get(1).get("Email").toString();
				Area = ls.get(1).get("AreaName").toString();
				SalesMan = ls.get(1).get("Name").toString();
				Source = ls.get(1).get("Source").toString();
			}
		}

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

		RequirementUtil util = new RequirementUtil();
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
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>吕总，您好！</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>以下是今天收到的新客户需求信息，请您查收！</span><br><br>");
		sb.append(contents + "<br>");
	
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		
		sb.append("</body></html>");
		String content = sb.toString();
		String subject = "Eoulu新需求通知——" + CustomerName + Requirement;
		String[] fileList = null;
		String umail = request.getSession().getAttribute("email").toString();
		boolean temp = false;
		if (flag && id != 0) {
			temp = util.doSendHtmlEmail(subject, content, fileList,umail);
		}

		return temp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eoulu.service.RequirementService#RequirementDelete(com.eoulu.
	 * commonality.Page)
	 */
	@Override
	public boolean RequirementDelete(Requirement requirement) {
		return new RequirementDao().delete(requirement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eoulu.service.RequirementService#RequirementModify(com.eoulu.
	 * commonality.Page)
	 */
	@Override
	public boolean RequirementModify(Requirement requirement, HttpServletRequest request) {
		RequirementDao dao = new RequirementDao();
		int id = requirement.getID();
		List<Map<String, Object>> rq = dao.getRequirement(id);
		String change = "";
		if (rq.size() > 1) {

			int area = Integer.parseInt(rq.get(1).get("Area").toString());
			if (requirement.getArea() != area) {
				change += "区域";
			}
			String brand = rq.get(1).get("Brand").toString();
			System.out.println("品牌:" + brand + "----change:" + requirement.getBrand());
			if (!((brand.equals("13") || brand.equals("12") || brand.equals("11") || brand.equals(""))
					&& (requirement.getBrand().equals("13") || requirement.getBrand().equals("12")
							|| requirement.getBrand().equals("11") || requirement.getBrand().equals("")))
					&& !requirement.getBrand().equals(brand)) {
				if (change.equals("")) {
					change += "品牌";
				} else {
					change += ",品牌";
				}

			}
			// String level = rq.get(1).get("CustomerLevel").toString();
			// if (!requirement.getCustomerLevel().equals(level) &&
			// !(requirement.getCustomerLevel().equals("")&& level.equals("")))
			// {
			// if(change.equals("")){
			// change += "客户等级";
			// }else{
			// change += ",客户等级";
			// }
			//
			// }
			String BusinessMan = rq.get(1).get("BusinessMan").toString() == null ? ""
					: rq.get(1).get("BusinessMan").toString();
			if (!requirement.getBusinessMan().equals(BusinessMan)
					&& !(requirement.getBusinessMan().equals("") && BusinessMan.equals(""))) {
				if (change.equals("")) {
					change += "商务负责人";
				} else {
					change += ",商务负责人";
				}

			}
			String RequirementDate = rq.get(1).get("RequirementDate").toString() == null ? ""
					: rq.get(1).get("RequirementDate").toString();
			if (!requirement.getRequirementDate().equals(RequirementDate)
					&& !(requirement.getRequirementDate().equals("") && RequirementDate.equals(""))) {
				if (change.equals("")) {
					change += "时间";
				} else {
					change += ",时间";
				}

			}
			String CustomerName = rq.get(1).get("CustomerName").toString() == null ? ""
					: rq.get(1).get("CustomerName").toString();
			System.out.println(CustomerName);
			System.out.println(requirement.getCustomerName());
			if (!requirement.getCustomerName().equals(CustomerName)
					&& !(requirement.getCustomerName().equals("") && CustomerName.equals(""))) {
				if (change.equals("")) {
					change += "客户名称";
				} else {
					change += ",客户名称";
				}
			}
			String RequirementContent = rq.get(1).get("RequirementContent").toString() == null ? ""
					: rq.get(1).get("RequirementContent").toString();
			if (!requirement.getRequirementContent().equals(RequirementContent)
					&& !(requirement.getRequirementContent().equals("") && RequirementContent.equals(""))) {
				if (change.equals("")) {
					change += "具体需求";
				} else {
					change += ",具体需求";
				}
			}
			String RequirementClassify = rq.get(1).get("RequirementClassify").toString() == null ? ""
					: rq.get(1).get("RequirementClassify").toString();
			System.out.println("需求类别:" + RequirementClassify + "----change:" + requirement.getRequirementClassify());
			if (!requirement.getRequirementClassify().equals(RequirementClassify)
					&& !((requirement.getRequirementClassify().equals("8")
							|| requirement.getRequirementClassify().equals("")
									&& (RequirementClassify.equals("8") || RequirementClassify.equals(""))))) {
				if (change.equals("")) {
					change += "需求类别";
				} else {
					change += ",需求类别";
				}
			}
			String DemandSources = rq.get(1).get("DemandSources").toString() == null ? ""
					: rq.get(1).get("DemandSources").toString();
			if (!requirement.getDemandSources().equals(DemandSources)
					&& !(requirement.getDemandSources().equals("") && DemandSources.equals(""))) {
				if (change.equals("")) {
					change += "需求来源";
				} else {
					change += ",需求来源";
				}
			}
			String WhetherQuotes = rq.get(1).get("WhetherQuotes").toString() == null ? ""
					: rq.get(1).get("WhetherQuotes").toString();
			System.out.println("是否报价:" + WhetherQuotes + "----change:" + requirement.getWhetherQuotes());
			if (!requirement.getWhetherQuotes().equals(WhetherQuotes)
					&& !((requirement.getWhetherQuotes().equals("13") || requirement.getWhetherQuotes().equals(""))
							&& (WhetherQuotes.equals("13") || WhetherQuotes.equals("")))) {
				if (change.equals("")) {
					change += "是否报价";
				} else {
					change += ",是否报价";
				}
			}
			String SalesMan = rq.get(1).get("SalesMan").toString() == null ? "" : rq.get(1).get("SalesMan").toString();
			if (requirement.getSalesMan() != Integer.parseInt(SalesMan)) {
				if (change.equals("")) {
					change += "销售负责人";
				} else {
					change += ",销售负责人";
				}
			}
			String SingleProbability = rq.get(1).get("SingleProbability").toString() == null ? ""
					: rq.get(1).get("SingleProbability").toString();
			if (!requirement.getSingleProbability().equals(SingleProbability)
					&& !(requirement.getSingleProbability().equals("") && SingleProbability.equals(""))) {
				if (change.equals("")) {
					change += "成单概率";
				} else {
					change += ",成单概率";
				}
			}
			String ExceptedPayTime = rq.get(1).get("ExceptedPayTime").toString() == null ? ""
					: rq.get(1).get("ExceptedPayTime").toString();
			if (!requirement.getExceptedPayTime().equals(ExceptedPayTime)
					&& !(requirement.getExceptedPayTime().equals("") && ExceptedPayTime.equals(""))) {
				if (change.equals("")) {
					change += "预计下单时间";
				} else {
					change += ",预计下单时间";
				}
			}
			String WhetherSingle = rq.get(1).get("WhetherSingle").toString() == null ? ""
					: rq.get(1).get("WhetherSingle").toString();
			if (!requirement.getWhetherSingle().equals(WhetherSingle)
					&& !(requirement.getWhetherSingle().equals("") && WhetherSingle.equals(""))) {
				if (change.equals("")) {
					change += "是否成单";
				} else {
					change += ",是否成单";
				}
			}
			String ProgressStatus = rq.get(1).get("ProgressStatus").toString() == null ? ""
					: rq.get(1).get("ProgressStatus").toString();
			if (!requirement.getProgressStatus().equals(ProgressStatus)
					&& !(requirement.getProgressStatus().equals("") && ProgressStatus.equals(""))) {
				if (change.equals("")) {
					change += "进度状况";
				} else {
					change += ",进度状况";
				}
			}
			String FollowPlan = rq.get(1).get("FollowPlan").toString() == null ? ""
					: rq.get(1).get("FollowPlan").toString();
			if (!requirement.getFollowPlan().equals(FollowPlan)
					&& !(requirement.getFollowPlan().equals("") && FollowPlan.equals(""))) {
				if (change.equals("")) {
					change += "跟单计划";
				} else {
					change += ",跟单计划";
				}
			}
			String QuotationNumber = rq.get(1).get("QuotationNumber").toString() == null ? ""
					: rq.get(1).get("QuotationNumber").toString();
			if (!requirement.getQuotationNumber().equals(QuotationNumber)
					&& !(requirement.getQuotationNumber().equals("") && QuotationNumber.equals(""))) {
				if (change.equals("")) {
					change += "报价单号";
				} else {
					change += ",报价单号";
				}
			}
			String USDQuotes = rq.get(1).get("USDQuotes").toString() == null ? ""
					: rq.get(1).get("USDQuotes").toString();
			if (requirement.getUSDQuotes() != Integer.parseInt(USDQuotes)) {
				if (change.equals("")) {
					change += "美金报价";
				} else {
					change += ",美金报价";
				}
			}
			String RMBQuotes = rq.get(1).get("RMBQuotes").toString() == null ? ""
					: rq.get(1).get("RMBQuotes").toString();
			if (requirement.getRMBQuotes() != Integer.parseInt(RMBQuotes)) {
				if (change.equals("")) {
					change += "人民币报价";
				} else {
					change += ",人民币报价";
				}
			}
			String Province = rq.get(1).get("Province").toString() == null ? "" : rq.get(1).get("Province").toString();
			if (!requirement.getProvince().equals(Province)
					&& !(requirement.getProvince().equals("") && Province.equals(""))) {
				if (change.equals("")) {
					change += "省份";
				} else {
					change += ",省份";
				}
			}
			String refNo = rq.get(1).get("RefNo").toString() == null ? "" : rq.get(1).get("RefNo").toString();
			if (!requirement.getRefNo().equals(refNo) && !(requirement.getRefNo().equals("") && refNo.equals(""))) {
				if (change.equals("")) {
					change += "Ref.No";
				} else {
					change += ",Ref.No";
				}
			}

		}

		String before = request.getParameter("Before");
		String after = request.getParameter("After");
		boolean flag = dao.modiify(requirement);

		String CustomerName = "";
		// String Contact = "";
		// String ContactTel = "";
		// String Email = "";
		// String Area = "";
		// String Province = requirement.getProvince();
		String Requirement = requirement.getRequirementContent();
		// String Source = "";
		// String SalesMan = "";
		List<Map<String, Object>> ls = null;
		if (id != 0) {
			ls = dao.getCustomerInfo(id);
			if (ls.size() > 1) {
				CustomerName = ls.get(1).get("CustomerName").toString();
				// Contact = ls.get(1).get("Contact").toString();
				// ContactTel = ls.get(1).get("ContactInfo1").toString();
				// Email = ls.get(1).get("Email").toString();
				// Area = ls.get(1).get("AreaName").toString();
				// SalesMan = ls.get(1).get("Name").toString();
				// Source = ls.get(1).get("Source").toString();
			}
		}

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

		RequirementUtil util = new RequirementUtil();
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
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>吕总，您好！</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>需求：</span>" + CustomerName + Requirement + " "
				+ "<span style='font-family:微软雅黑;font-size:14px;color:red;'>" + change
				+ "</span><span style='font-family:微软雅黑;font-size:14px;'>信息发生以下变更，请您查收！</span>" + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>需求变更后：</span>" + after + "<br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>需求变更前：</span>" + before + "<br>");
		
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
	
		sb.append("</body></html>");
		String content = sb.toString();
		String subject = "Eoulu：需求信息更新通知——" + CustomerName + Requirement;
		String[] fileList = null;
		String umail = request.getSession().getAttribute("email").toString();
		boolean temp = false;
		if (flag && id != 0) {
			temp = util.doSendHtmlEmail(subject, content, fileList,umail);
		}

		return temp;

	}

	@Override
	public int getRequirementCounts(String classify1, String parameter1) {
		// TODO Auto-generated method stub
		return new RequirementDao().getRequirementCounts(classify1, parameter1);
	}

	/**
	 * ��������ͳ�Ƴ�����ĸ����·ݵ�������
	 */
	@Override
	public Map<String, Object> getStatisticsByArea(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		DBUtil db = new DBUtil();
		// ��ѯ�����е���������
		List<Map<String, Object>> areaLS = null;
		String sql = "select ID,AreaName from t_area order by ID desc";
		areaLS = db.QueryToList(sql, new Object[0], false);

		int length = areaLS.size();
		for (int i = 1; i < length; i++) {
			sql = "select count(t_requirement.ID) itemCounts,t_area.AreaName,MONTH(t_requirement.RequirementDate) requirementMonth from t_requirement LEFT JOIN t_area on t_requirement.Area=t_area.ID where t_requirement.Area=?  and t_requirement.RequirementDate BETWEEN ? and ? GROUP BY Month(t_requirement.RequirementDate)";
			Object[] parameter = new Object[] { areaLS.get(i).get("ID"), startTime, endTime };
			List<Map<String, Object>> requirementLS = db.QueryToList(sql, parameter, false);
			map.put(areaLS.get(i).get("AreaName").toString(), requirementLS);
		}
		db.closed();

		// �����ؽ���м���ȱʧ���·�
		List<Map<String, Object>> westSouthLS = (List<Map<String, Object>>) map.get("西南区");
		List<Map<String, Object>> southLS = (List<Map<String, Object>>) map.get("南方");
		List<Map<String, Object>> northLS = (List<Map<String, Object>>) map.get("北方");

		List<Map<String, Object>> resultWestSouthLS = new ArrayList<Map<String, Object>>(),
				resultSouthLS = new ArrayList<Map<String, Object>>(),
				resultNorthLS = new ArrayList<Map<String, Object>>();

		Map<String, Object> titleMap = new HashMap<String, Object>();
		titleMap.put("col1", "itemCounts");
		titleMap.put("col2", "AreaName");
		titleMap.put("col3", "requirementMonth");
		resultWestSouthLS.add(titleMap);
		resultSouthLS.add(titleMap);
		resultNorthLS.add(titleMap);

		// ����12���µĿյ� list ��ʼ
		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "南方");
			dataMap.put("requirementMonth", i);
			resultSouthLS.add(dataMap);
		}

		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "西南区");
			dataMap.put("requirementMonth", i);
			resultWestSouthLS.add(dataMap);
		}

		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "北方");
			dataMap.put("requirementMonth", i);
			resultNorthLS.add(dataMap);
		}
		// ����12���µĿյ� list ����

		// ��ʼ�Է��ص����ݸ�ֵ ��ʼ
		// ���Ϸ�
		for (int i = 1; i < 13; i++) {
			int counts = 0;
			for (int j = 1; j < westSouthLS.size(); j++) {
				if (Integer.parseInt(westSouthLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(westSouthLS.get(j).get("itemCounts").toString());
					resultWestSouthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}

			}

		}

		// �Ϸ�
		for (int i = 1; i < 13; i++) {
			int counts = 0;

			for (int j = 1; j < southLS.size(); j++) {
				if (Integer.parseInt(southLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(southLS.get(j).get("itemCounts").toString());
					resultSouthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}
			}

		}

		// ����
		for (int i = 1; i < 13; i++) {
			int counts = 0;

			for (int j = 1; j < northLS.size(); j++) {
				if (Integer.parseInt(northLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(northLS.get(j).get("itemCounts").toString());
					resultNorthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}
			}

		}

		// ��ʼ�Է��ص����ݸ�ֵ ����

		// System.out.println(resultWestSouthLS);
		// System.out.println(resultSouthLS);
		// System.out.println(resultNorthSouthLS);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("南方", resultSouthLS);
		resultMap.put("西南区", resultWestSouthLS);
		resultMap.put("北方", resultNorthLS);
		return resultMap;
	}

	@Override
	public Map<String, Object> getStatisticsByAreaPerMonth(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		DBUtil db = new DBUtil();
		// ��ѯ�����е���������
		List<Map<String, Object>> areaLS = null;
		String sql = "select ID,AreaName from t_area order by ID desc";
		areaLS = db.QueryToList(sql, new Object[0], false);

		int length = areaLS.size();
		for (int i = 1; i < length; i++) {
			sql = "select count(t_requirement.ID) itemCounts,t_area.AreaName,MONTH(t_requirement.RequirementDate) requirementMonth from t_requirement LEFT JOIN t_area on t_requirement.Area=t_area.ID where t_requirement.Area=?  and t_requirement.RequirementDate BETWEEN ? and ? GROUP BY Month(t_requirement.RequirementDate)";
			Object[] parameter = new Object[] { areaLS.get(i).get("ID"), startTime, endTime };
			List<Map<String, Object>> requirementLS = db.QueryToList(sql, parameter, false);
			map.put(areaLS.get(i).get("AreaName").toString(), requirementLS);
		}
		db.closed();

		// �����ؽ���м���ȱʧ���·�
		List<Map<String, Object>> westSouthLS = (List<Map<String, Object>>) map.get("西南区");
		List<Map<String, Object>> southLS = (List<Map<String, Object>>) map.get("南方");
		List<Map<String, Object>> northLS = (List<Map<String, Object>>) map.get("北方");

		List<Map<String, Object>> resultWestSouthLS = new ArrayList<Map<String, Object>>(),
				resultSouthLS = new ArrayList<Map<String, Object>>(),
				resultNorthLS = new ArrayList<Map<String, Object>>();

		Map<String, Object> titleMap = new HashMap<String, Object>();
		titleMap.put("col1", "itemCounts");
		titleMap.put("col2", "AreaName");
		titleMap.put("col3", "requirementMonth");
		resultWestSouthLS.add(titleMap);
		resultSouthLS.add(titleMap);
		resultNorthLS.add(titleMap);

		// ����12���µĿյ� list ��ʼ
		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "南方");
			dataMap.put("requirementMonth", i);
			resultSouthLS.add(dataMap);
		}

		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "西南区");
			dataMap.put("requirementMonth", i);
			resultWestSouthLS.add(dataMap);
		}

		for (int i = 1; i < 13; i++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("itemCounts", "0");
			dataMap.put("AreaName", "北方");
			dataMap.put("requirementMonth", i);
			resultNorthLS.add(dataMap);
		}
		// ����12���µĿյ� list ����

		// ��ʼ�Է��ص����ݸ�ֵ ��ʼ
		// ���Ϸ�
		for (int i = 1; i < 13; i++) {
			int counts = 0;
			for (int j = 1; j < westSouthLS.size(); j++) {
				if (Integer.parseInt(westSouthLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(westSouthLS.get(j).get("itemCounts").toString());
					resultWestSouthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}

			}

		}

		// �Ϸ�
		for (int i = 1; i < 13; i++) {
			int counts = 0;

			for (int j = 1; j < southLS.size(); j++) {
				if (Integer.parseInt(southLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(southLS.get(j).get("itemCounts").toString());
					resultSouthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}
			}

		}

		// ����
		for (int i = 1; i < 13; i++) {
			int counts = 0;

			for (int j = 1; j < northLS.size(); j++) {
				if (Integer.parseInt(northLS.get(j).get("requirementMonth").toString()) == i) {
					counts = Integer.parseInt(northLS.get(j).get("itemCounts").toString());
					resultNorthLS.get(i).put("itemCounts", counts);
				} else {
					counts = 0;
				}
			}

		}

		// ��ʼ�Է��ص����ݸ�ֵ ����

		// System.out.println(resultWestSouthLS);
		// System.out.println(resultSouthLS);
		// System.out.println(resultNorthSouthLS);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("南方", resultSouthLS);
		resultMap.put("西南区", resultWestSouthLS);
		resultMap.put("北方", resultNorthLS);
		return resultMap;
	}

	@Test
	public void test() {
		getStatisticsByArea("2017-01-01", "2018-01-01");
	}

	@Override
	public List<Map<String, Object>> getStatisticsBySales(String startTime, String endTime) {

		List<Map<String, Object>> orderLS;
		List<Map<String, Object>> allRequirementLS;
		List<Map<String, Object>> resultLS = new ArrayList<Map<String, Object>>();

		String orderSql = "select B.allCounts,t_sales_representative.`Name` from t_sales_representative LEFT JOIN (select t_requirement.SalesMan,COUNT(t_requirement.ID) allCounts from t_requirement where t_requirement.WhetherSingle=1 and t_requirement.RequirementDate BETWEEN ? and ? GROUP BY t_requirement.SalesMan)B on t_sales_representative.ID=B.SalesMan";
		String allRequirementSql = "select B.allCounts,t_sales_representative.`Name` from t_sales_representative LEFT JOIN (select t_requirement.SalesMan,COUNT(t_requirement.ID) allCounts from t_requirement where t_requirement.RequirementDate BETWEEN ? and ? GROUP BY t_requirement.SalesMan)B on t_sales_representative.ID=B.SalesMan";
		Object[] parameter = new Object[] { startTime, endTime };

		DBUtil db = new DBUtil();

		orderLS = db.QueryToList(orderSql, parameter, false);
		allRequirementLS = db.QueryToList(allRequirementSql, parameter, false);

		int length = orderLS.size();

		// ͨ����ѯ�����Ķ�Ӧ���۴�����ѳɵ���������е�����������۵ĸ��˳ɵ���
		for (int i = 1; i < length - 1; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			float persent, orderCounts, allCounts;
			map.put("Name", orderLS.get(i).get("Name"));

			orderCounts = orderLS.get(i).get("allCounts").equals("--") ? 0
					: Float.parseFloat(orderLS.get(i).get("allCounts").toString());
			allCounts = allRequirementLS.get(i).get("allCounts").equals("--") ? 0
					: Float.parseFloat(allRequirementLS.get(i).get("allCounts").toString());

			// System.out.println(orderCounts+" "+allCounts);
			persent = orderCounts / (allCounts <= 0 ? 1 : allCounts);
			map.put("percent", persent);

			resultLS.add(map);
		}

		return resultLS;
	}

	// @Test
	// public void test(){
	// System.out.println(getStatisticsByArea("2017-01-01","2018-01-01"));
	// }

	@Override
	public List<Map<String, Object>> getAllRequirementBrand() {
		// TODO Auto-generated method stub
		return new RequirementBrandDao().getAllRequirementBrand();
	}

	@Override
	public List<Map<String, Object>> getAllRequirementClassify() {
		// TODO Auto-generated method stub
		return new RequirementClassifyDao().getAllRequirementClassify();
	}

	@Override
	public List<Map<String, Object>> getAllRequirementOrderTime() {
		// TODO Auto-generated method stub
		return new RequirementOrderTimeDao().getAllRequirementOrderTime();
	}

	@Override
	public List<Map<String, Object>> getAllRequirementQuotes() {
		// TODO Auto-generated method stub
		return new RequirementQuotesDao().getAllRequirementQuotes();
	}

	@Override
	public List<Map<String, Object>> getAllRequirementSource() {
		// TODO Auto-generated method stub
		return new RequirementSourceDao().getAllRequirementSource();
	}

	/**
	 * ���˳ɵ���ƴ���ַ�������ǰ̨ʹ��
	 */
	@Override
	public String getStatisticsBySalesToString(String startTime, String endTime) {
		List<Map<String, Object>> ls = getStatisticsBySales(startTime, endTime);

		int length = ls.size();
		String[][] salesTatic = new String[][] { { "", "" } };
		for (int i = 0; i < length; i++) {
			salesTatic[0][0] += "'";
			salesTatic[0][0] += ls.get(i).get("Name");
			salesTatic[0][0] += "'";
			salesTatic[0][1] += Float.parseFloat(ls.get(i).get("percent").toString()) * 100;
			if (i < length - 1) {
				salesTatic[0][0] += ",";
				salesTatic[0][1] += ",";
			}

		}

		return "[[" + salesTatic[0][0] + "],[" + salesTatic[0][1] + "]]";
	}

	@Override
	public int getRequirementCounts(String type1, String parameter1, String type2, String parameter2) {

		return new RequirementDao().getMixRequirementCounts(type1, parameter1, type2, parameter2);
	}

	@Override
	public List<Map<String, Object>> MixRequirementQuery(Page page, String classify1, String parameter1,
			String classify2, String parameter2) {

		return new RequirementDao().queryMixByPage(page, classify1, parameter1, classify2, parameter2);
	}

	@Override
	public int getProvinceID(String Province) {

		return new RequirementDao().getProvinceID(Province);
	}

	@Override
	public boolean updatePreparation(Requirement requirement) {
		return new RequirementDao().updatePreparation(requirement);
	}

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String filePath = null;
		String CustomerName = "";
		String Contact = "";
		String ContactTel = "";
		String Email = "";
		String Area = "";
		String Province = "";
		String Requirement = "";
		String Source = "";
		String SalesMan = "";
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
					if ("CustomerName".equals(item.getFieldName())) {
						CustomerName = item.getString("utf-8");

					}
					if ("Contact".equals(item.getFieldName())) {
						Contact = item.getString("utf-8");

					}
					if ("ContactTel".equals(item.getFieldName())) {
						ContactTel = item.getString("utf-8");

					}
					if ("Email".equals(item.getFieldName())) {
						Email = item.getString("utf-8");

					}
					if ("Area".equals(item.getFieldName())) {
						Area = item.getString("utf-8");

					}
					if ("Province".equals(item.getFieldName())) {
						Province = item.getString("utf-8");

					}
					if ("Requirement".equals(item.getFieldName())) {
						Requirement = item.getString("utf-8");

					}
					if ("Source".equals(item.getFieldName())) {
						Source = item.getString("utf-8");

					}
					if ("SalesMan".equals(item.getFieldName())) {
						SalesMan = item.getString("utf-8");

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
		map.put("CustomerName", CustomerName);
		map.put("Contact", Contact);
		map.put("ContactTel", ContactTel);
		map.put("Email", Email);
		map.put("Area", Area);
		map.put("Province", Province);
		map.put("Requirement", Requirement);
		map.put("Source", Source);
		map.put("SalesMan", SalesMan);
		map.put("isExist", isExist);

		return map;
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
		RequirementUtil util = new RequirementUtil();
		String CustomerName = map.get("CustomerName");
		String Contact = map.get("Contact");
		String ContactTel = map.get("ContactTel");
		String Email = map.get("Email");
		String Area = map.get("Area");
		String Province = map.get("Province");
		String Requirement = map.get("Requirement");
		String Source = map.get("Source");
		String SalesMan = map.get("SalesMan");

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
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>吕总，您好！</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>请查收以下收到的新客户需求：</span><br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>客户名称：</span>" + CustomerName + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>联系人：</span>" + Contact + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>联系方式：</span>" + ContactTel + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>邮箱：</span>" + Email + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>区域：</span>" + Area + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>省份：</span>" + Province + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>具体需求：</span>" + Requirement + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>需求来源：</span>" + Source + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>销售负责人：</span>" + SalesMan + "<br><br>");
		sb.append("<span style='font-family:微软雅黑;font-size:14px;'>以上请您悉知！</span><br>");
		
		
		String content = sb.toString();

		StringBuffer sbs = new StringBuffer();
		sbs.append("<span style='font-family:微软雅黑;font-size:14px;'>Eoulu新需求通知——</span>" + CustomerName + Requirement);
		String subject = sbs.toString();
		String[] fileList = null;
		String umail = request.getSession().getAttribute("email").toString();
		boolean flag = util.doSendHtmlEmail(subject, content, fileList,umail);

		return flag;
	}

	@Override
	public String getCustomerName(int id) {
		return new RequirementDao().getCustomerName(id);
	}

	@Override
	public List<Map<String, Object>> getCustomerContactInfo(int id) {
		return new RequirementDao().getCustomerContactInfo(id);
	}

	/**
	 * 根据省份获取区域
	 * 
	 * @param province
	 * @return
	 */
	public String getArea(String province) {
		String result = "南方";
		String[] north = new String[] { "北京", "天津", "河北" };
		String[] westSouth = new String[] { "四川", "陕西", "甘肃", "黑龙江", "重庆", "辽宁", "安徽", "河南", "山东", "广西", "山西", "吉林",
				"新疆", "内蒙古", "云南", "贵州", "西藏", "宁夏" };
		for (String a : north) {
			if (province.contains(a) || a.equals(province)) {
				result = "北方";
			}
		}
		for (String a : westSouth) {
			if (province.contains(a) || a.equals(province)) {
				result = "西南区";
			}
		}
		if (province == null || province.equals("--") || province.equals("")) {
			result = "";
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAllCustomer(String content) {
		return new RequirementDao().getAllCustomer(content);
	}

	@Override
	public Map<String, Object> getProvince(int id) {
		Map<String, Object> map = new HashMap<>();
		String province = new RequirementDao().getProvince(id);
		String area = getArea(province);
		map.put("province", province);
		map.put("area", area);
		return map;
	}

	@Override
	public Map<String, Object> getStatisticsByProvince(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		DBUtil db = new DBUtil();
		// ��ѯ�����е���������
		List<Map<String, Object>> areaLS = null;
		String sql = "SELECT Province  FROM t_requirement WHERE Province<>''AND Province<>'--' GROUP BY Province ";
		areaLS = db.QueryToList(sql, new Object[0], false);

		int length = areaLS.size();
		for (int i = 1; i < length; i++) {
			sql = "select count(t_requirement.ID) itemCounts,t_requirement.Province,"
					+ "MONTH(t_requirement.RequirementDate) requirementMonth from t_requirement "
					+ "where t_requirement.Province=?  and t_requirement.RequirementDate "
					+ "BETWEEN ? and ? GROUP BY Month(t_requirement.RequirementDate)";
			Object[] parameter = new Object[] { areaLS.get(i).get("Province"), startTime, endTime };
			List<Map<String, Object>> requirementLS = db.QueryToList(sql, parameter, false);
			map.put(areaLS.get(i).get("Province").toString(), requirementLS);
		}
		db.closed();

		return map;
	}

	public static void main(String[] args) {
		RequirementService ser = new RequirementServiceImpl();
		System.out.println(ser.getStatisticsByProvince("2017-01-01", "2018-01-01"));

	}

	@Override
	public List<Map<String, Object>> getStatisticsByCity(String startTime, String endTime, String province) {
		String sql = "select count(t_requirement.ID) itemCounts," + "t_customer.City from t_requirement "
				+ "LEFT JOIN t_customer ON t_requirement.CustomerName=t_customer.ID where ";
		Object[] param = new Object[] { startTime, endTime };
		if (!province.equals("")) {
			sql += "t_requirement.Province=?  and ";
			param = new Object[] { province, startTime, endTime };
		}
		sql += "t_requirement.RequirementDate " + "BETWEEN ? and ? GROUP BY  t_customer.City";

		return new DBUtil().QueryToList(sql, param);
	}

	public String getCustomerID(String name) {
		DBUtil db = new DBUtil();
		String sql = "select ID from t_customer where CustomerName=?";
		Object[] param = new Object[] { name };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls.get(1).get("ID").toString();
	}

	@Override
	public List<Map<String, Object>> Export(String classify1, String parameter1, String classify2, String parameter2) {

		if (classify2 == null && parameter2 == null) {
			return new RequirementDao().queryByPage(classify1, parameter1);
		} else {
			return new RequirementDao().queryMixByPage(classify1, parameter1, classify2, parameter2);
		}
	}

	@Override
	public List<Map<String, Object>> getTime() {
		return new RequirementDao().getTime();
	}

	public static void builDetailsExcel(Map<String, Object> map, String path,String month) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet sheet = xwk.createSheet("需求统计");
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4,4));
		
		sheet.setColumnWidth(0, (int) 3000);
		sheet.setColumnWidth(1, (int) 9000);
		sheet.setColumnWidth(2, (int) 9000);
		sheet.setColumnWidth(3, (int) 9000);
		sheet.setColumnWidth(4, (int) 3000);
		XSSFColor xssfColor = new XSSFColor(); 
		xssfColor.setARGBHex("FFBFBFBF");
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		center.setFillBackgroundColor(xssfColor);
		int colCount = 5;
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("北方");
		int rowsCount =1;
		if(month.equals("All")){
			rowsCount = list.size()-1;
		}
		for (int j = 0; j < rowsCount+2; j++) {
			XSSFRow xrow = sheet.createRow(j);
			if (j < 2) {
				for (int i = 0; i < colCount; i++) {
					XSSFCell xcell = xrow.createCell(i);
					if (j == 0) {
						if (i == 0) {
							xcell.setCellValue("月份");
						}
						if (i == 1) {
							xcell.setCellValue("北方区域");
						}
						if (i == 2) {
							xcell.setCellValue("南方区域");
						}
						if (i == 3) {
							xcell.setCellValue("西南区域");
						}
						if(i == 4){
							xcell.setCellValue("月总计");
						}
						
					} else {
						xcell.setCellValue("需求");
						
					}
					xcell.setCellStyle(center);
				}
			} else {
				for (int i = 0; i < colCount; i++) {
					List<Map<String,Object>> ls = null;
					XSSFCell xcell = xrow.createCell(i);
					int key = j-1;
					if(!month.equals("All")){
						key = Integer.parseInt(month);
					}
					if (i == 0) {
						ls = (List<Map<String, Object>>) map.get("北方");
						xcell.setCellValue(Integer.parseInt(ls.get(key).get("requirementMonth").toString()));
					}
					if (i == 1) {
						ls = (List<Map<String, Object>>) map.get("北方");
						xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
					}
					if (i == 2) {
						ls = (List<Map<String, Object>>) map.get("南方");
						xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
					}
					if (i == 3) {
						ls = (List<Map<String, Object>>) map.get("西南区");
						xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
					}
					if(i == 4){
						xcell.setCellFormula("SUM(B"+(j+1)+":D"+(j+1)+")");
					}
					xcell.setCellStyle(center);
				}
			}
		}
		XSSFRow xrow = sheet.createRow(rowsCount+2);
		for (int i = 0; i < colCount; i++) {
			XSSFCell xcell = xrow.createCell(i);
			if(i == 0){
				xcell.setCellValue("小计");
			}
			if(i == 1){
				xcell.setCellFormula("SUM(B3:B"+(3+rowsCount-1)+")");
			}
			if(i == 2){
				xcell.setCellFormula("SUM(C3:C"+(3+rowsCount-1)+")");
			}
			if(i == 3){
				xcell.setCellFormula("SUM(D3:D"+(3+rowsCount-1)+")");
			}
			if(i == 4){
				xcell.setCellFormula("SUM(E3:E"+(3+rowsCount-1)+")");
			}
			xcell.setCellStyle(center);
		}
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void builAllDetailsExcel(List<Map<String, Object>> list, String path,int start) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet sheet = xwk.createSheet("需求统计");
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
		
		for(int i=1;i<list.size();i++){
			sheet.addMergedRegion(new CellRangeAddress(0+i*15, 1+i*15, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(0+i*15, 1+i*15, 4, 4));
		}
		sheet.setColumnWidth(0, (int) 3000);
		sheet.setColumnWidth(1, (int) 9000);
		sheet.setColumnWidth(2, (int) 9000);
		sheet.setColumnWidth(3, (int) 9000);
		sheet.setColumnWidth(4, (int) 3000);
		XSSFColor xssfColor = new XSSFColor(); 
		xssfColor.setARGBHex("FFBFBFBF");
		XSSFCellStyle center = xwk.createCellStyle();
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		center.setFillBackgroundColor(xssfColor);
		int colCount = 5;
		int rowsCount =0;
		for(int k=0;k<list.size();k++){
			Map<String, Object> temp = list.get(k);
			List<Map<String,Object>> lsTemp = (List<Map<String, Object>>) temp.get("北方");
			rowsCount = 15*(k+1);
			start = k==0?start+0:start+1;
			
			for (int j = rowsCount-15; j < rowsCount-1; j++) {
				XSSFRow xrow = sheet.createRow(j);
				if (j < rowsCount-15+2 && j>=rowsCount-15) {
					for (int i = 0; i < colCount; i++) {
						XSSFCell xcell = xrow.createCell(i);
						if (j == k*15) {
							if (i == 0) {
								xcell.setCellValue(start+"");
							}
							if (i == 1) {
								xcell.setCellValue("北方区域");
							}
							if (i == 2) {
								xcell.setCellValue("南方区域");
							}
							if (i == 3) {
								xcell.setCellValue("西南区域");
							}
							if(i == 4){
								xcell.setCellValue("月总计");
							}
							
						} else {
							xcell.setCellValue("需求");
							
						}
						xcell.setCellStyle(center);
					}
				} else {
					Map<String,Object> map = temp ;
					for (int i = 0; i < colCount; i++) {
						List<Map<String,Object>> ls = null;
						XSSFCell xcell = xrow.createCell(i);
						int key = k==0?j-1:j-k*15-1;
						if (i == 0) {
							ls = (List<Map<String, Object>>) map.get("北方");
							xcell.setCellValue(ls.get(key).get("requirementMonth").toString());
						}
						if (i == 1) {
							ls = (List<Map<String, Object>>) map.get("北方");
							xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
						}
						if (i == 2) {
							ls = (List<Map<String, Object>>) map.get("南方");
							xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
						}
						if (i == 3) {
							ls = (List<Map<String, Object>>) map.get("西南区");
							xcell.setCellValue(Integer.parseInt(ls.get(key).get("itemCounts").toString()));
						}
						if(i == 4){
							xcell.setCellFormula("SUM(B"+(j+1)+":D"+(j+1)+")");
						}
						xcell.setCellStyle(center);
					}
				}
			}
			XSSFRow xrow = sheet.createRow(rowsCount-1);
			System.out.println("小计:"+rowsCount);
			for (int i = 0; i < colCount; i++) {
				XSSFCell xcell = xrow.createCell(i);
				System.out.println("列:"+i);
				if(i == 0){
					xcell.setCellValue("小计");
				}
				if(i == 1){
					xcell.setCellFormula("SUM(B"+(rowsCount-15+3)+":B"+(rowsCount-1)+")");
				}
				if(i == 2){
					xcell.setCellFormula("SUM(C"+(rowsCount-15+3)+":C"+(rowsCount-1)+")");
				}
				if(i == 3){
					xcell.setCellFormula("SUM(D"+(rowsCount-15+3)+":D"+(rowsCount-1)+")");
				}
				if(i == 4){
					xcell.setCellFormula("SUM(E"+(rowsCount-15+3)+":E"+(rowsCount-1)+")");
				}
				xcell.setCellStyle(center);
			}
			
		}
		XSSFRow xrow = sheet.createRow(rowsCount);
		for (int i = 0; i < colCount; i++) {
			XSSFCell xcell = xrow.createCell(i);
			if(i == 0){
				xcell.setCellValue("总计");
			}
			if(i == 1){
				xcell.setCellFormula("SUM(B15,B30,B45)");
			}
			if(i == 2){
				xcell.setCellFormula("SUM(C15,C30,C45)");
			}
			if(i == 3){
				xcell.setCellFormula("SUM(D15,D30,D45)");
			}
			if(i == 4){
				xcell.setCellFormula("SUM(E15,E30,E45)");
			}
			xcell.setCellStyle(center);
		}
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getProvinceMap(String startTime, String endTime) {
		return new RequirementDao().getProvinceMap(startTime, endTime);
	}
	
}
