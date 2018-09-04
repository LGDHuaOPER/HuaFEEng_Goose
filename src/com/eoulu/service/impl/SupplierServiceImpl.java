/**
 * 
 */
package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SupplierBankDao;
import com.eoulu.dao.SupplierDao;
import com.eoulu.entity.Supplier;
import com.eoulu.entity.SupplierBank;
import com.eoulu.service.SupplierService;

/**
 * @author zhangkai
 *
 */
public class SupplierServiceImpl implements SupplierService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.SupplierService#getAllSupplier()
	 */
	
	private static Map<String, String> map;
	static{
		map = new HashMap<>();
		map.put("供应商名称", "Name");
		map.put("联系人", "Contact");
		map.put("产品", "Product");
	}
	@Override
	public List<Map<String, Object>> getAllSupplier() {
		// TODO Auto-generated method stub
		return new SupplierDao().getAllSupplier();
	}

	/*
	 * ��ȡ��Ӧ����Ϣ�����շ�ҳ
	 * 
	 * */
	@Override
	public List<Map<String, Object>> getSupplierByPage(Page page,String column1,String content1) {

		SupplierDao supplierDao = new SupplierDao();

		String sql = "select t_supplier.ID,Name,Contact,ContactInfo,Address,Email,t_supplier_bank.Company,"
				+ "t_supplier_bank.Account,t_supplier_bank.Bank,t_supplier_bank.TaxCode,"
				+ "t_supplier_bank.SWIFTCode,Product from t_supplier left join t_supplier_bank"
				+ " on t_supplier.Name = t_supplier_bank.Supplier ";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*10,page.getRows()};
		if(!column1.equals("")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by ID desc limit ?,?";

		List<Map<String,Object>> ls = supplierDao.executeQuery(sql, parameter);

		return ls;
	}

	/*
	 * ��ȡ��Ӧ�̵�����
	 * */
	@Override
	public int getSupplierCounts(String column1,String content1) {

		SupplierDao supplierDao = new SupplierDao();

		String sql = "select count(t_supplier.ID) counts from t_supplier left join t_supplier_bank"
				+ " on t_supplier.Name = t_supplier_bank.Supplier ";
		Object[] parameter = new Object[0];
		if(!column1.equals("")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}
		

		List<Map<String,Object>> ls = supplierDao.executeQuery(sql, parameter);

		int result = 0;
		if(ls.size()>1){
			result = Integer.parseInt(ls.get(1).get("counts").toString());
		}

		return result;
	}

	/*
	 * ������Ӧ����Ϣ
	 * */
	@Override
	public boolean insertSupplier(Supplier supplier,SupplierBank bank) {

		return new SupplierDao().insert(supplier, bank);
	}

	/*
	 * �޸Ĺ�Ӧ����Ϣ
	 * */
	@Override
	public boolean modifySupplier(Supplier supplier,SupplierBank bank) {
		return new SupplierDao().update(supplier, bank);
	}
	
	public List<Map<String,Object>> querySupplier(String key){
		return new SupplierDao().querySupplier(key);
	}

}
