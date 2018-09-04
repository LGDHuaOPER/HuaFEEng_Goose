/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.AreaDao;
import com.eoulu.service.AreaService;

/**
 * @author zhangkai
 *
 */
public class AreaServiceImpl implements AreaService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.AreaService#getAllArea()
	 */
	@Override
	public List<Map<String, Object>> getAllArea() {
		// TODO Auto-generated method stub
		return new AreaDao().getArea();
	}

}
