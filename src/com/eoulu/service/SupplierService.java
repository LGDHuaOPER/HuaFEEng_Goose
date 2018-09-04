/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Supplier;
import com.eoulu.entity.SupplierBank;

/**
 * @author zhangkai
 *
 */
public interface SupplierService {

	/**
	 * ��ȡ���еĹ�Ӧ��
	 * */
	public List<Map<String,Object>> getAllSupplier();
	
	
	
	/**
	 * ���շ�ҳ��ѯ��Ӧ����Ϣ
	 * */
	public List<Map<String, Object>> getSupplierByPage(Page page,String column1,String content1);
	
	
	/**
	 * ���й�Ӧ�̵�����
	 * */
	public int getSupplierCounts(String column1,String content1);
	
	
	/**
	 * ��ӹ�Ӧ����Ϣ
	 * */
	public boolean insertSupplier(Supplier supplier,SupplierBank bank);
	
	
	/**
	 * �޸Ĺ�Ӧ����Ϣ
	 * */
	public boolean modifySupplier(Supplier supplier,SupplierBank bank);
	
	public List<Map<String,Object>> querySupplier(String key);
	
}
