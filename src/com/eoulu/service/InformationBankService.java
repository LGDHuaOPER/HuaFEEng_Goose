package com.eoulu.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.model.SavedByEntry;
import org.junit.validator.PublicClassValidator;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Customer;
import com.eoulu.entity.Equipment;
import com.eoulu.entity.TaxInfo;

public interface InformationBankService {

	/**
	 * ��Ӳ�Ʒ�ͺ���Ϣ
	 * */
	public boolean EquipmentAdd(Equipment equipment);
	
	
	/**
	 * �޸Ĳ�Ʒ�ͺ���Ϣ
	 * */
	public boolean EquipmentModify(Equipment equipment);
	
	
	/**
	 * ɾ����Ʒ�ͺ���Ϣ
	 * */
	public boolean EquipmentDelete(int id);
	
	
	/**
	 * ��ѯ��Ʒ�ͺ���Ϣ
	 * */
	public List<Map<String,Object>> EquipmentQueryByCommon(Page page);
	
	
	
	/**
	 * ��ѯ��Ʒ�ͺ���Ϣ
	 * */
	public List<Map<String,Object>> EquipmentQueryBySuppiler(Page page,int suppiler,String model);
	
	/**
	 * �����豸��������
	 * */
	public int getEquipmentCountsByCommon();
	
	/**
	 * ��Ӧ��+��Ʒ�ͺŲ�ѯ����
	 * */
	public int getEquipmentCountsBySupiler(int supiler,String model);
	
	
	
	
	/**
	 * ��ӿͻ���Ϣ
	 * */
	public boolean CustomerAdd(Customer customer);
	
	
	/**
	 * �޸Ŀͻ���Ϣ
	 * */
	public boolean CustomerModify(Customer customer);
	
	
	/**
	 * ɾ���ͻ���Ϣ
	 * */
	public boolean CustomerDelete(int id);
	
	
	/**
	 * ��ѯ�ͻ���Ϣ
	 * */
	public List<Map<String,Object>> CustomerQueryByName(Page page,String customerName);
	
	
	/**
	 *��ѯ�ͻ���������
	 * */
	public int CustomerQueryCounts(String customerName);
	
	/**
	 * ��ȡ���еĿͻ���Ϣ
	 * */
	public List<Map<String,Object>> getAllCustomer();
	
	public boolean isExist(String model);
	
	public String saveTaxInfo(TaxInfo taxInfo); //开票信息
	
	public List<Map<String, Object>> getTaxInfo(int ID);
	
	public List<Map<String, Object>> getTaxInfoForBill(String customerName,String contact);
	
}
