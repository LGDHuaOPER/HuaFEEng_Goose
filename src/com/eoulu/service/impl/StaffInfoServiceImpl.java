package com.eoulu.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.StaffInfo;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.StaffInfoService;
import com.eoulu.util.DocumentUploadUtilY;
import com.eoulu.util.ExportExcel;
import com.eoulu.util.ZipUtils;

public class StaffInfoServiceImpl implements StaffInfoService{
	
	private static Map<String, String> fieldMap = new HashMap<>();
	static{
		fieldMap.put("姓名","StaffName");
		fieldMap.put("所属部门","Department");
		fieldMap.put("职位", "Job");
		fieldMap.put("工作地", "WorkPlace");
		
	}
	
	

	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new StaffInfoDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		return new StaffInfoDao().getAllCounts();
	}

	@Override
	public boolean operate(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StaffInfo info = new StaffInfo();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String name = request.getParameter("StaffName")==null?"":request.getParameter("StaffName").trim();
		String iDCard = request.getParameter("IDCard")==null?"":request.getParameter("IDCard").trim();
		String nation = request.getParameter("Nation")==null?"":request.getParameter("Nation").trim();
		String nativePlace = request.getParameter("NativePlace")==null?"":request.getParameter("NativePlace").trim();
		String politicalStatus = request.getParameter("PoliticalStatus")==null?"":request.getParameter("PoliticalStatus").trim();
		String linkTel = request.getParameter("LinkTel")==null?"":request.getParameter("LinkTel").trim();
		String graduateInstitutions = request.getParameter("GraduateInstitutions")==null?"":request.getParameter("GraduateInstitutions").trim();
		String major = request.getParameter("Major")==null?"":request.getParameter("Major").trim();
		String detailAddress = request.getParameter("DetailAddress")==null?"":request.getParameter("DetailAddress").trim();
		String entryDate = request.getParameter("EntryDate")==null?"0000-00-00":request.getParameter("EntryDate").trim();
		String job = request.getParameter("Job")==null?"":request.getParameter("Job").trim();
		String department = request.getParameter("Department")==null?"":request.getParameter("Department").trim();
		String workPlace = request.getParameter("WorkPlace")==null?"":request.getParameter("WorkPlace").trim();
		String staffCode = request.getParameter("StaffCode")==null?"":request.getParameter("StaffCode").trim();
		String staffMail = request.getParameter("StaffMail")==null?"":request.getParameter("StaffMail").trim();
		String background = request.getParameter("EducationalBackground")==null?"":request.getParameter("EducationalBackground").trim();
		String passport = request.getParameter("Passport")==null?"":request.getParameter("Passport").trim();
		String gender = request.getParameter("Gender")==null?"":request.getParameter("Gender").trim();
		String accountNumber = request.getParameter("AccountNumber") == null?"":request.getParameter("AccountNumber").trim();
		String depositBank = request.getParameter("DepositBank") == null?"":request.getParameter("DepositBank");
		String remarks = request.getParameter("Remarks") == null?"":request.getParameter("Remarks");
		String IDCardFile = request.getParameter("IDCardFile") == null?"":request.getParameter("IDCardFile");
		String passportFile = request.getParameter("PassportFile") == null?"":request.getParameter("PassportFile");
		
		
		info.setID(id);
		info.setStaffName(name);
		info.setIDCard(iDCard);
		info.setNativePlace(nativePlace);
		info.setNation(nation);
		info.setPoliticalStatus(politicalStatus);
		info.setLinkTel(linkTel);
		info.setGraduateInstitutions(graduateInstitutions);
		info.setMajor(major);
		info.setDetailAddress(detailAddress);
		info.setEntryDate(entryDate);
		info.setWorkPlace(workPlace);
		info.setStaffCode(staffCode);
		info.setStaffMail(staffMail);
		info.setGender(gender);
		info.setJob(job);
		info.setDepartment(department);
		info.setEducationalBackground(background);
		info.setPassport(passport);
		info.setAccountNumber(accountNumber);
		info.setDepositBank(depositBank);
		info.setRemarks(remarks);
		info.setIDCardFile(IDCardFile);
		info.setPassportFile(passportFile);
		StaffInfoDao dao = new StaffInfoDao();
		boolean flag = false;
		if(id==0){
			flag = dao.insert(info);
			if(flag){
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "人事部-员工信息";
				String description = "新增-" + name;
				log.insert(request, JspInfo, description);
			}
		}else{
			flag = dao.update(info);
			if(flag){
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "人事部-员工信息";
				String description = "更改-" + name;
				log.insert(request, JspInfo, description);
			}
		}
		return flag;
	}

	@Override
	public boolean delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StaffInfoDao dao = new StaffInfoDao();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		return dao.delete(id);
	}

	@Override
	public List<Map<String, Object>> singleQuery(HttpServletRequest request,Page page) {
		// TODO Auto-generated method stub
		String field = request.getParameter("field1")==null?"":request.getParameter("field1");
		String content = request.getParameter("content1")==null?"":request.getParameter("content1");
		field = fieldMap.get(field);
		StaffInfoDao dao = new StaffInfoDao();
		return dao.singleQuery(page,field, content);
	}

	@Override
	public List<Map<String, Object>> maxQuery(HttpServletRequest request,Page page) {
		// TODO Auto-generated method stub
		String field1 = request.getParameter("field1")==null?"":request.getParameter("field1");
		String content1 = request.getParameter("content1")==null?"":request.getParameter("content1");
		String field2 = request.getParameter("field2")==null?"":request.getParameter("field2");
		String content2 = request.getParameter("content2")==null?"":request.getParameter("content2");
		field1 = fieldMap.get(field1);
		field2 = fieldMap.get(field2);
		StaffInfoDao dao = new StaffInfoDao();
		return dao.maxQuery(page,field1, content1,field2,content2);
	}

	@Override
	public int getSingleQueryCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String field = request.getParameter("field1")==null?"":request.getParameter("field1");
		String content = request.getParameter("content1")==null?"":request.getParameter("content1");
		field = fieldMap.get(field);
		StaffInfoDao dao = new StaffInfoDao();
		return dao.getSingleQueryCount(field, content);
	}

	@Override
	public int getMaxQueryCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String field1 = request.getParameter("field1")==null?"":request.getParameter("field1");
		String content1 = request.getParameter("content1")==null?"":request.getParameter("content1");
		String field2 = request.getParameter("field2")==null?"":request.getParameter("field2");
		String content2 = request.getParameter("content2")==null?"":request.getParameter("content2");
		field1 = fieldMap.get(field1);
		field2 = fieldMap.get(field2);
		StaffInfoDao dao = new StaffInfoDao();
		return dao.getMaxQueryCount(field1, content1, field2, content2);
	}
	



	@Override
	public void exportExcel(HttpServletResponse resp) {
		// TODO Auto-generated method stub
		ExportExcel<StaffInfo> ee= new ExportExcel<StaffInfo>();  
        
		String[] headers = { "序号","员工编码","员工姓名","性别","职位","入职时间","联系手机","家庭住址",
				"公司邮箱","身份证号","民族","籍贯","政治面貌","毕业院校","专业","所属部门","工作地","学历","护照号","银行卡号","开户行"};
		StaffInfoDao dao = new StaffInfoDao();
		Map<String, String> map = new HashMap<>();
		map.put("序号", "ID");
		map.put("员工编码", "StaffCode");
		map.put("员工姓名", "StaffName");
		map.put("性别", "Gender");
		map.put("职位", "Job");
		map.put("入职时间", "EntryDate");
		map.put("联系手机", "LinkTel");
		map.put("家庭住址", "DetailAddress");
		map.put("公司邮箱", "StaffMail");
		map.put("身份证号", "IDCard");
		map.put("民族", "Nation");
		map.put("籍贯", "NativePlace");
		map.put("政治面貌", "PoliticalStatus");
		map.put("毕业院校", "GraduateInstitutions");
		map.put("专业", "Major");
		map.put("所属部门", "Department");
		map.put("工作地", "WorkPlace");
		map.put("学历", "EducationalBackground");
		map.put("护照号", "Passport");
		map.put("银行卡号","AccountNumber");
		map.put("开户行", "DepositBank");
	
		
		
        List<StaffInfo> list = dao.getAllDataForExcel();
   
        String fileName = "员工信息表";
        ee.exportExcel(headers, list, fileName, resp,map);
        
		
	}

	@Override
	public List<Map<String, Object>> getStaffName(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StaffInfoDao dao = new StaffInfoDao();
		String keyWord = request.getParameter("keyword")==null?"":request.getParameter("keyword");
		return dao.getStaffName(keyWord);
	}

	@Override
	public List<Map<String, Object>> getNameAndMail(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StaffInfoDao dao = new StaffInfoDao();
		String keyWord = request.getParameter("keyword")==null?"":request.getParameter("keyword");
		return dao.getNameANDMail(keyWord);
		
	}

	@Override
	public String uploadFile(HttpServletRequest request) {
		DocumentUploadUtilY util = new DocumentUploadUtilY();
		String basePath = "E:\\LogisticsFile\\File\\Photo";
		Map<String,String> map = util.uploadNotRename(request, basePath);
	
		String response = map.get("Response");
	
		return response;
	}

	@Override
	public void exportPhoto(HttpServletResponse response) {

		String srcDir = "E:\\LogisticsFile\\File\\Photo";
		BufferedOutputStream fos = null;
		try {
			fos = new BufferedOutputStream(response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}  

		response.setContentType("application/x-msdownload");  
        response.setHeader("Content-Disposition", "attachment;filename=photos.zip");  
		ZipUtils.toZip(srcDir,fos,true);
		
		
	}
	
	
}
