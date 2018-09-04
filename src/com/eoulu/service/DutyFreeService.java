/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 *
 */
public interface DutyFreeService {

	
	/**
	 * 获取所有的免税数据
	 * */
	public List<Map<String,Object>> getAllDuty();
}
