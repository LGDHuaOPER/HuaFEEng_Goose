/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.SalesRepresentativeDao;
import com.eoulu.service.SalesRepresentativeService;

/**
 * @author zhangkai
 *
 */
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService{

	@Override
	public List<Map<String, Object>> getAllSalesRep() {
		// TODO Auto-generated method stub
		return new SalesRepresentativeDao().getAllSales();
	}

}
