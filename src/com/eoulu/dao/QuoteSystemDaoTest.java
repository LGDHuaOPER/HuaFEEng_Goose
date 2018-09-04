package com.eoulu.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;

public class QuoteSystemDaoTest {

	/*
	@Test
	public void testGetCustomerInfo() {
		QuoteSystemDao dao =new QuoteSystemDao();
		List<Map<String,Object>> ls = dao.getCustomerInfo();
		System.out.println(ls.size());
		//System.out.println(ls);
	}

	@Test
	public void testGetCustomerInfoByCustomerName() {
		QuoteSystemDao dao =new QuoteSystemDao();
		String name = "%杨%";
		List<Map<String,Object>> ls = dao.getCustomerInfoByCustomerName(name);
		System.out.println(ls);
	}
	
	
	@Test
	public void testGetCustomerInfoByContact() {
		QuoteSystemDao dao =new QuoteSystemDao();
		String name = "%杨%";
		List<Map<String,Object>> ls = dao.getCustomerInfoByContact(name);
		System.out.println(ls);
	}
	
	
	@Test
	public void testGetStaffInfo() {
		QuoteSystemDao dao =new QuoteSystemDao();
		List<Map<String,Object>> ls = dao.getStaffInfo();
		System.out.println(ls);
	}
	
	
	@Test
	public void testGetStaffInfoByStaffName() {
		QuoteSystemDao dao =new QuoteSystemDao();
		String name = "%赵%";
		List<Map<String,Object>> ls = dao.getStaffInfoByStaffName(name);
		System.out.println(ls);
	}
	
	@Test
	public void testGetStaffInfoByDepartment() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCommodityInfo() {
		QuoteSystemDao dao =new QuoteSystemDao();
		
		List<Map<String,Object>> ls = dao.getCommodityInfo();
		System.out.println(ls);
	}
	
	@Test
	public void testGetCommodityInfoByModel() {
		QuoteSystemDao dao =new QuoteSystemDao();
		String name = "%123";
		List<Map<String,Object>> ls = dao.getCommodityInfoByModel(name);
		System.out.println(ls);
	}
	
	
	@Test
	public void testGetQuoteSystem() {
		QuoteSystemDao dao =new QuoteSystemDao();
		Page page = new Page();
		page.setCurrentPage(1);
		page.setRecordCounts(2);
		page.setRows(10);
		List<Map<String,Object>> ls = dao.getQuoteSystem(page);
		System.out.println(ls);
	}

	@Test
	public void testGetAllCounts() {
		QuoteSystemDao dao =new QuoteSystemDao();
		
		
		System.out.println(dao.getAllCounts());
	}
	*/
	/*
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetTodayCount() {
 QuoteSystemDao dao =new QuoteSystemDao();
		
		
		System.out.println(dao.getTodayCount());
	}

	@Test
	public void testDelete() {
		
	}

}
