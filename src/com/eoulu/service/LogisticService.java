/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.SizeInfo;

/**
 * @author zhangkai
 *
 */
public interface LogisticService {

	/**
	 * ��ȡ��Ӧ����ϸ�Ͷ�����ϸ
	 * */
	public List<Map<String,Object>> getLogistic(int id);
	
	
	/**
	 * ������ͬ���ü�������Ϣ
	 * 
	 * @param id  ������id
	 * 
	 * @param orderInfo ������ϸ
	 * 
	 * @param logistics ������Ϣ
	 * */
	public boolean orderInfoAdd(OrderInfo orderInfo,Logistics logistics);
	
	
	/**
	 * �޸ĺ�ͬ���ü�������Ϣ
	 * 
	 * */
	public boolean orderInfoModify(OrderInfo orderInfo,Logistics logistics,String[] param1,String[] param2);
	
	/**
	 * 修改单条物流信息
	 * @param orderInfo
	 * @param logistics
	 * @return
	 */
	public boolean orderInfoModify2(OrderInfo orderInfo,Logistics logistics);
	
	public boolean addSizeInfo(List<SizeInfo> size);
	
	
	
}
