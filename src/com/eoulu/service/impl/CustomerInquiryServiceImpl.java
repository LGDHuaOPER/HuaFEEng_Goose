package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.dao.CustomerInquiryDao;
import com.eoulu.dao.SoftwareImplementationDao;
import com.eoulu.dao.SoftwareProductDao;
import com.eoulu.entity.CustomerInquiry;
import com.eoulu.entity.CustomerInquiryRecord;
import com.eoulu.service.CustomerInquiryService;
import com.eoulu.util.ExportExcelUtil;

public class CustomerInquiryServiceImpl implements CustomerInquiryService{

	@Override
	public List<Map<String, Object>> getAllData(Page page, String content) {
		return new CustomerInquiryDao().getAllData(page, content);
	}

	@Override
	public int getAllCounts(String content) {
		return new CustomerInquiryDao().getAllCounts(content);
	}

	@Override
	public String operate(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		int customerID = Integer.parseInt(request.getParameter("CustomerID")==null?"0":request.getParameter("CustomerID"));
		String preSalesTable = request.getParameter("PreSalesTable")==null?"":request.getParameter("PreSalesTable");
		String quoteTime = request.getParameter("QuoteTime")==null?"":request.getParameter("QuoteTime");
		double totalPrice = Double.parseDouble(request.getParameter("TotalPrice")==null?"0":request.getParameter("TotalPrice"));
		String result = customerID==0?"客户信息为必填项！":"";
		CustomerInquiry inquiry = new CustomerInquiry();
		inquiry.setCustomerID(customerID);
		inquiry.setID(id);
		inquiry.setPreSalesTable(preSalesTable);
		inquiry.setQuoteTime(quoteTime);
		inquiry.setTotalPrice(totalPrice);
		CustomerInquiryDao dao = new CustomerInquiryDao();
		if(customerID!=0){
			if(id==0){
				id = dao.insert(inquiry);
				result = id==0?"添加失败":"添加成功";
				if(id!=0){
					boolean temp = operate(request, id);
					if(!temp){
						dao.deleteInquiry(id);
						dao.deleteRecord(id);
						result = "添加失败";
					}
				}
			}else{
				boolean temp = dao.update(inquiry);
				result = temp?"修改成功":"修改失败";
				if(temp){
					delete(request, id);
					result = operate(request, id)?"修改成功":"型号信息修改失败";
				}
			}
		}
		return result;
	}
	
	public boolean operate(HttpServletRequest request,int id){//添加、修改
		String[] recordID = request.getParameterValues("RecordID[]");
		String[] sequenceNO = request.getParameterValues("SequenceNO[]");
		String[] quantity = request.getParameterValues("Quantity[]");
		String[] productID = request.getParameterValues("ProductID[]");
		String[] isMain = request.getParameterValues("IsMain[]");
		boolean flag = true;
		for(int i=0;i<recordID.length;i++){
			int tempID = Integer.parseInt(recordID[i]);
			CustomerInquiryDao dao = new CustomerInquiryDao();
			CustomerInquiryRecord record = new CustomerInquiryRecord();
			record.setID(tempID);
			record.setIsMain(isMain[i]);
			record.setProductID(Integer.parseInt(productID[i]));
			record.setQuantity(Integer.parseInt(quantity[i]));
			record.setSequenceNO(sequenceNO[i]);
			record.setInquiryID(id);
			if(tempID==0){
				boolean temp = isMain[i].equals("yes")?dao.insertRecord(record):dao.insertRecordWithoutSequence(record);
				if(!temp){
					flag = false;
					break;
				}
			}else{
				if(!dao.updateRecord(record)){
					flag = false;
					break;
				}
			}
		}
		
		return flag;
	}

	public void delete(HttpServletRequest request,int id){//删除
		List<Map<String,Object>> ls = new CustomerInquiryDao().getRecordID(id);
		String[] recordID = request.getParameterValues("RecordID[]");
		for(int i=1;i<ls.size();i++){
			String temp = ls.get(i).get("ID").toString();
			boolean flag = false;
			for(int j=0;j<recordID.length;j++){
				if(temp.equals(recordID[j])){
					flag = true;
					break;
				}
			}
			if(!flag){
				boolean flags = new CustomerInquiryDao().delete(Integer.parseInt(temp));
				System.out.println(!flags?"删除失败":"");
			}
		}
		
	}
	
	@Override
	public boolean updateServiceTime(int id) {
		SoftwareImplementationDao dao = new SoftwareImplementationDao();
		CustomerInquiryDao dao1 = new CustomerInquiryDao();
		int CustomerID = Integer.parseInt(dao1.getCustomerID(id).get(1).get("CustomerID").toString());
		String preSalesTable = dao1.getCustomerID(id).get(1).get("PreSalesTable").toString();
		dao.insert(id,CustomerID,preSalesTable);
		return new CustomerInquiryDao().updateServiceTime(id);
	}

	@Override
	public List<Map<String, Object>> getOperateData(int id) {
		return new CustomerInquiryDao().getOperateData(id);
	}

	@Override
	public List<Map<String, Object>> getPreviewData(int id) {
		return new CustomerInquiryDao().getPreviewData(id);
	}

	@Override
	public List<Map<String, Object>> getAllCustomer(String content) {
		return new CustomerDao().getAllCustomer(content);
	}

	@Override
	public List<Map<String, Object>> getAllProductModel(String content,String column) {
		return new SoftwareProductDao().getAllProductModel(content,column);
	}
	@Override
	public void buildExcel(String path){
		List<Map<String,Object>> list = new ArrayList<>();
		CustomerInquiryDao dao = new CustomerInquiryDao();
		List<Map<String,Object>> ls = dao.getExcelData();//合并列的数据，包含主表ID
		int rowsCount = 0;
		for(int i=1;i<ls.size();i++){
			Map<String,Object> map = new HashMap<>();
			int id = Integer.parseInt(ls.get(i).get("ID").toString());
			map.put("CustomerName", ls.get(i).get("CustomerName"));
			map.put("QuoteTime", ls.get(i).get("QuoteTime"));
			map.put("TotalPrice", ls.get(i).get("TotalPrice"));
			map.put("Type", ls.get(i).get("Type"));
			List<Map<String,Object>> temp = dao.getPreviewData(id);
			rowsCount += (temp.size()-1);
			map.put("records", temp);
			list.add(map);
		}
		String[] titleNames = new String[]{"序列","客户","型号","描述","数量","价格","总价","类型","报价日期"};
		ExportExcelUtil util = new ExportExcelUtil();
		util.export(list, path, titleNames, "客户询价记录", rowsCount);
	}
	public static void main(String[] args) {
		CustomerInquiryService service= new CustomerInquiryServiceImpl();
		service.buildExcel("E:/test123.xlsx");
	}

	@Override
	public String getFileName(int id) {
		return new CustomerInquiryDao().getFileName(id);
	}
}
