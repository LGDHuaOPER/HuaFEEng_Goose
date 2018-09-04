package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface InsuranceDirectiveService {

	/**
	 * 所有数量
	 * @return
	 */
	public int getAllCounts();
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getInsuranceDirective(Page page);
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	public boolean addInsuranceDirective(HttpServletRequest request);
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	public boolean updateInsuranceDirective(HttpServletRequest request);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteInsuranceDirective(int id);
	/**
	 * 单一查询的记录数
	 * @param classify
	 * @param parameter
	 * @return
	 */
	public int getCountByClassifyInOne(String classify, Object parameter );
	/**
	 * 单一查询返回结果
	 * @param classify
	 * @param parameter
	 * @return
	 */
	public List<Map<String,Object>>  getInsuranceDirectiveByClassifyInOne(String classify, Object parameter,Page page);
	/**
	 * 组合查询记录数
	 * @param classify1
	 * @param parameter1
	 * @param classify2
	 * @param parameter2
	 * @return
	 */
    public int getCountByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2 );
	/**
	 * 组合查询结果
	 * @param classify1
	 * @param parameter1
	 * @param classify2
	 * @param parameter2
	 * @return
	 */
	public List<Map<String,Object>>  getInsuranceDirectiveByClassifyInTwo(String classify1, Object parameter1,String classify2, Object parameter2,Page page);
	
	/**
	 * 根据运输保险指令ID查询所有的提货地址
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getDeliveryAddress(int id);
	/**
	 * 根据ID删除提货地址
	 * @param id
	 * @return
	 */
	public  boolean deleteDeliveryAddress(int id);
	
	/**
	 * 根据ID删除货物信息
	 * @param id
	 * @return
	 */
	public  boolean deleteGoodsInfo(int id);
	/**
	 * 点击修改时获取提货地址与货物信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getAddressAndModel(int id);
	/**
	 * 发邮件
	 * @param request
	 * @return
	 */
	public boolean sendEmail(HttpServletRequest request);
	/**
	 * 获取所有用户的邮箱
	 * @return
	 */
	public List<Map<String,Object>> getAllEmail();
}
