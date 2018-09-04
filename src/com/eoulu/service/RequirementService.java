/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Requirement;

/**
 * @author zhangkai
 *
 */
public interface RequirementService {

	/**
	 * ��ѯ������Ϣ
	 * */
	public List<Map<String,Object>> RequirementQuery(Page page,String classify1,String parameter1);
	/**
	 * ��ѯ������Ϣ
	 * */
	public List<Map<String,Object>> MixRequirementQuery(Page page,String classify1,String parameter1,String classify2,String parameter2);
	
	/**
	 * ����������Ϣ
	 * */
	public boolean RequirementAdd(Requirement requirement,HttpServletRequest request);
	
	/**
	 * ɾ��������Ϣ
	 * */
	public boolean RequirementDelete(Requirement requirement);
	
	/**
	 * �޸�������Ϣ
	 * */
	public boolean RequirementModify(Requirement requirement,HttpServletRequest request);
	
	
	/**
	 * ��ȡ��������
	 * */
	public int getRequirementCounts(String type1,String parameter1);
	/**
	 * ��ȡ�������������
	 * */
	public int getRequirementCounts(String type1,String parameter1,String type2,String parameter2);
	
	/**
	 * ��������ͳ��������
	 * */
	public Map<String,Object> getStatisticsByArea(String startTime,String endTime);
	
	public Map<String, Object> getStatisticsByAreaPerMonth(String startTime,String endTime);
	/**
	 * ����ʱ���ͳ�Ƹ��˳ɵ���
	 * */
	public List<Map<String,Object>> getStatisticsBySales(String startTime,String endTime);
	
	/**
	 * ��ȡ���е�Ʒ��
	 * */
	public List<Map<String,Object>> getAllRequirementBrand();
	
	
	/**
	 * ��ȡ���е����
	 * */
	public List<Map<String,Object>> getAllRequirementClassify();
	
	
	/**
	 * ��ȡ���е�Ԥ�Ƴɵ�ʱ��
	 * */
	public List<Map<String,Object>> getAllRequirementOrderTime();
	
	
	/**
	 * ��ȡ���еı���״̬
	 * */
	public List<Map<String,Object>> getAllRequirementQuotes();
	
	
	
	/**
	 * ��ȡ���е�������Դ
	 * */
	public List<Map<String,Object>> getAllRequirementSource();
	
	
	/**
	 * �����˳ɵ��ʽ����ַ���ƴ��
	 * */
	public String getStatisticsBySalesToString(String startTime,String endTime);
	
	
	public int getProvinceID(String Province);
	
	public String getArea(String province);
	
	public boolean updatePreparation(Requirement requirement);
	
	
	public boolean sendEmail(HttpServletRequest request);
	
	public String getCustomerName(int id);
	
	public List<Map<String,Object>> getCustomerContactInfo(int id);
	/**
	 * 模糊查询客户信息
	 * @param content
	 * @return
	 */
	public List<Map<String,Object>> getAllCustomer(String content);
	/**
	 * 查询客户省份
	 * @param id
	 * @return
	 */
	public Map<String,Object> getProvince(int id);
	/**
	 * 省级
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Object> getStatisticsByProvince(String startTime, String endTime);
	/**
	 * 市级
	 * @param startTime
	 * @param endTime
	 * @param province
	 * @return
	 */
	public List<Map<String, Object>> getStatisticsByCity(String startTime, String endTime,String province);
	
	public String getCustomerID(String name);
	/**
	 * 导出
	 * @param classify1
	 * @param parameter1
	 * @param classify2
	 * @param parameter2
	 * @return
	 */
	public List<Map<String,Object>> Export(String classify1,String parameter1,String classify2,String parameter2);
	
	public List<Map<String,Object>> getTime();
	
	public List<Map<String, Object>> getProvinceMap(String startTime,String endTime);
	
}
