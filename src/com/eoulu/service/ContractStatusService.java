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
public interface ContractStatusService {

	
	/**
	 * 获取所有的合同状态
	 * */
	public List<Map<String,Object>> getAllStatus();
}
