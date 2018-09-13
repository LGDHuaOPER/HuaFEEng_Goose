/**
 * 
 */
package com.eoulu.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

	@Override
	public void exportExcel(Customer customer, String path) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		//创建一个名为 one 的sheet
		XSSFSheet xsheet = xwk.createSheet("客户信息");
		
		String[] colName = new String[]{"客户名称","客户英文名称","联系人","客户部门","联系方式","电子邮箱","联系地址"};
		
		XSSFCellStyle style = xwk.createCellStyle();
		XSSFFont font = xwk.createFont();
		font.setFontName("微软雅黑");
		style.setFont(font);
		xsheet.setColumnWidth(0, (int) 3000);
		xsheet.setColumnWidth(1, (int) 12000);
		
		short height = 30*20;
		xsheet.setDefaultRowHeight(height);
		

		int rowCount = colName.length; 
	

		for(int i=0;i<rowCount; i++){
			//创建单元格
			XSSFRow dataRow = xsheet.createRow(i);
			XSSFCell colCell = dataRow.createCell(0);
			colCell.setCellStyle(style);
			colCell.setCellValue(colName[i]);
			XSSFCell dataCell = dataRow.createCell(1);
			dataCell.setCellStyle(style);
			switch (colName[i]) {
			case "客户名称":
				dataCell.setCellValue(customer.getCustomerName());
				break;

			case "客户英文名称":
				dataCell.setCellValue(customer.getEnglishName());
				break;
			case "联系人":
				dataCell.setCellValue(customer.getContact());
				break;
			case "客户部门":
				dataCell.setCellValue(customer.getCustomerDepartment());
				break;
			case "联系方式":
				dataCell.setCellValue(customer.getContactInfo1());
				break;
			case "电子邮箱":
				dataCell.setCellValue(customer.getEmail());
				break;
			case "联系地址":
				dataCell.setCellValue(customer.getContactAddress());
				break;
			
			}
		}
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
			xwk.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
		
}
