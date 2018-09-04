/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.DutyFreeDao;
import com.eoulu.service.DutyFreeService;

/**
 * @author zhangkai
 *
 */
public class DutyFreeServiceImpl implements DutyFreeService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.DutyFreeService#getAllDuty()
	 */
	@Override
	public List<Map<String, Object>> getAllDuty() {
		// TODO Auto-generated method stub
		return new DutyFreeDao().getAllDutyFree();
	}

}
