/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.ContractStatusDao;
import com.eoulu.service.ContractStatusService;

/**
 * @author zhangkai
 *
 */
public class ContractStatusServiceImpl implements ContractStatusService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.ContractStatusService#getAllStatus()
	 */
	@Override
	public List<Map<String, Object>> getAllStatus() {
		// TODO Auto-generated method stub
		return new ContractStatusDao().getAllStatus();
	}

}
