package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SoftwareProductDao;
import com.eoulu.entity.SoftwareProduct;
import com.eoulu.service.SoftwareProductService;

public class SoftwareProductServiceImpl implements SoftwareProductService{

	@Override
	public List<Map<String, Object>> getAllData(Page page) {
		return new SoftwareProductDao().getAllData(page);
	}

	@Override
	public int getAllCounts() {
		return new SoftwareProductDao().getAllCounts();
	}

	@Override
	public boolean operate(HttpServletRequest request) {
		SoftwareProduct product = new SoftwareProduct();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String name = request.getParameter("PackageName");
		String model = request.getParameter("Model");
		int count = Integer.parseInt(request.getParameter("Count")==null?"0":request.getParameter("Count"));
		double hourlyWage = Double.parseDouble(request.getParameter("HourlyWage")==null?"0":request.getParameter("HourlyWage"));
		String classify = request.getParameter("PackageClassify");
		double cycle = Double.parseDouble(request.getParameter("Cycle")==null?"0":request.getParameter("Cycle"));
		String brand = request.getParameter("Brand");
		double premium = Double.parseDouble(request.getParameter("PremiumIndex")==null?"0":request.getParameter("PremiumIndex"));
		double maintenance = Double.parseDouble(request.getParameter("MaintenanceIndex")==null?"0":request.getParameter("MaintenanceIndex"));
		double transport = Double.parseDouble(request.getParameter("TransportAllowance")==null?"0":request.getParameter("TransportAllowance"));
		double accommodation = Double.parseDouble(request.getParameter("AccommodationAllowance")==null?"0":request.getParameter("AccommodationAllowance"));
		double mission = Double.parseDouble(request.getParameter("MissionAllowance")==null?"0":request.getParameter("MissionAllowance"));
		String remarks = request.getParameter("Remarks");
		product.setID(id);
		product.setModel(model);
		product.setCount(count);
		product.setHourlyWage(hourlyWage);
		product.setPackageClassify(classify);
		product.setCycle(cycle);
		product.setBrand(brand);
		product.setPremiumIndex(premium);
		product.setMaintenanceIndex(maintenance);
		product.setTransportAllowance(transport);
		product.setAccommodation(accommodation);
		product.setMissionAllowance(mission);
		product.setRemarks(remarks);
		product.setPackageName(name);
		
		return id==0?new SoftwareProductDao().insert(product):new SoftwareProductDao().update(product);
	}

	@Override
	public List<Map<String, Object>> getQueryResult(String content, Page page) {
		return new SoftwareProductDao().getQueryResult(content, page);
	}

	@Override
	public int getQueryCounts(String content) {
		return new SoftwareProductDao().getQueryCounts(content);
	}

}
