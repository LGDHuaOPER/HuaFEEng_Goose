/**
 * 
 */
package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.CustomerDao;
import com.eoulu.dao.EquipmentDao;
import com.eoulu.entity.Customer;
import com.eoulu.entity.Equipment;
import com.eoulu.entity.TaxInfo;
import com.eoulu.service.InformationBankService;

/**
 * @author zhangkai
 *
 * ���Ͽ�Ĳ���service
 */
public class InformationBankServiceImpl implements InformationBankService{

	/**
	 * ����µ��豸�ͺ�
	 * */
	@Override
	public boolean EquipmentAdd(Equipment equipment) {
		
		Object[] parameter = new Object[8];
		parameter[0] = equipment.getModel();
		parameter[1] = equipment.getRemarks();
		parameter[2] = equipment.getEquipmentUnit();
		parameter[3] = equipment.getDeliveryTime();
		parameter[4] = equipment.getSourceArea();
		parameter[5] = equipment.getItemCode();
		parameter[6] = equipment.getCommodityCategory();
		parameter[7] = equipment.getSuppiler();
		return new EquipmentDao().equipmentAdd(parameter);
	}

	/**
	 * �豸�ͺ��޸�
	 * */
	@Override
	public boolean EquipmentModify(Equipment equipment) {
		
		
		Object[] para = new Object[6];
		para[0] = equipment.getEquipmentUnit();
		para[1] = equipment.getDeliveryTime();
		para[2] = equipment.getSourceArea();
		para[3] = equipment.getItemCode();
		para[4] = equipment.getCommodityCategory();
		para[5] = equipment.getSuppiler();
		
		return new EquipmentDao().modifyEquipment(para, equipment.getID());
	}

	/**
	 * �豸�ͺ�ɾ��
	 * */
	@Override
	public boolean EquipmentDelete(int id) {
		// TODO Auto-generated method stub
		return new EquipmentDao().delete(id);
	}

	/**
	 * ��ҳ��ѯ�ͺ����ݣ����չ�Ӧ�̲�ѯ
	 * */
	@Override
	public List<Map<String, Object>> EquipmentQueryBySuppiler(Page page,int suppiler,String model) {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentBySupiler(page, suppiler, model);
	}

	/**
	 * ��ҳ��ѯ�ͺ�����
	 * */
	@Override
	public List<Map<String, Object>> EquipmentQueryByCommon(Page page) {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentByPage(page);
	}

	/**
	 * �����豸�ͺ�����
	 * */
	@Override
	public int getEquipmentCountsByCommon() {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentCounts();
	}
	
	/**
	 * ��Ӧ��+��Ʒ�ͺŵ�����
	 * */
	@Override
	public int getEquipmentCountsBySupiler(int supiler,String model) {
		// TODO Auto-generated method stub
		return new EquipmentDao().getEquipmentCounts(supiler,model);
	}
	
	
	

	/**
	 * ����ͻ���Ϣ
	 * */
	@Override
	public boolean CustomerAdd(Customer customer) {
		return new CustomerDao().insert(customer);
	}

	/**
	 * �޸Ŀͻ���Ϣ
	 * */
	@Override
	public boolean CustomerModify(Customer customer) {
		// TODO Auto-generated method stub
		return new CustomerDao().modify(customer);
	}

	
	/**
	 * ɾ���ͻ���Ϣ
	 * */
	@Override
	public boolean CustomerDelete(int id) {
		// TODO Auto-generated method stub
		return new CustomerDao().delete(id);
	}

	/**
	 * ��ѯ�ͻ���Ϣ
	 * */
	@Override
	public List<Map<String, Object>> CustomerQueryByName(Page page, String customerName) {
		// TODO Auto-generated method stub
		return new CustomerDao().getCustomerByPage(page, customerName);
	}

	/**
	 * �ͻ�������
	 * */
	@Override
	public int CustomerQueryCounts(String customerName) {
		// TODO Auto-generated method stub
		return new CustomerDao().getCustomerCounts(customerName);
	}

	
	/**
	 * ��ȡ���еĿͻ���Ϣ
	 * */
	@Override
	public List<Map<String, Object>> getAllCustomer() {
		CustomerDao customerDao = new CustomerDao();
		return customerDao.getAllCustomer();
	}

	@Override
	public boolean isExist(String model) {
		return new EquipmentDao().isExist(model);
	}

	@Override
	public String saveTaxInfo(TaxInfo taxInfo) {
	
		return new CustomerDao().saveTaxInfo(taxInfo) == true?"保存成功":"保存失败";
	}

	@Override
	public List<Map<String, Object>> getTaxInfo(int ID) {
		// TODO Auto-generated method stub
		return new CustomerDao().getTaxInfo(ID);
	}
	public List<Map<String, Object>> getTaxInfoForBill(String customerName,String contact) {
		// TODO Auto-generated method stub
		return new CustomerDao().getTaxInfoForBill(customerName, contact);
	}

}
