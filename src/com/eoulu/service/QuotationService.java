/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Quotation;
import com.eoulu.entity.QuotationConfiguration;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��5��24�� ����3:10:29 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface QuotationService {

	/**
	 * ����ҳ����ȡ���۵���Ϣ
	 * */
	public List<Map<String,Object>> getQuotationByPage(Page page);
	/**
	 * ���ձ��۵��Ż�ȡ���۵���Ϣ
	 * */
	public List<Map<String,Object>> getQuotationByQuotationID(String QuotationID);
	/**
	 * ��ȡ������Ϣ����ҳ��
	 * */
	public int getQuotationCounts();
	
	/**
	 * ���뱨����Ϣ
	 * */
	public boolean insertQuotation(Quotation quotation);
	
	
	/**
	 * �޸ı�����Ϣ
	 * */
	public boolean modifyQuotation(Quotation quotation);
	
	
	/**
	 * ��ȡ������Ϣ
	 * */
	public List<Map<String,Object>> getQuotationConf(int id);
	
	/**
	 * ����������Ϣ
	 * */
	public boolean insertQuotationConf(QuotationConfiguration configuration);
	/**
	 * ɾ��������Ϣ
	 * */
	public boolean deleteQuotationConf(QuotationConfiguration configuration);
	
	/**
	 * �޸�������Ϣ
	 * */
	public boolean modifyQuotationConf(QuotationConfiguration configuration);
	
}
