package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.LabDao;
import com.eoulu.entity.Laboratory;
import com.eoulu.service.LabService;
import com.eoulu.util.GeneratePdfUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LabServiceImpl implements LabService{

	@Override
	public List<Map<String, Object>> getDataByPage(Page page) {
		return new LabDao().getDataByPage(page);
	}

	@Override
	public boolean insert(Laboratory lab) {
		return new LabDao().insert(lab);
	}

	@Override
	public boolean update(Laboratory lab) {
		return new LabDao().update(lab);
	}

	@Override
	public int getCounts() {
		return new LabDao().getCounts();
	}

	@Override
	public boolean saveConfig(String configJson, int LabID) {
		List<Map<String, Object>> config = new ArrayList<>();
		if(!configJson.equals("")){
			Map<String, Object> updateMap = null;
			JSONArray array = JSONArray.fromObject(configJson);
			for(int i = 0;i < array.size();i ++){
				JSONObject object = array.getJSONObject(i);
				updateMap = new HashMap<>();
				updateMap.put("PartID", object.getInt("PartID"));
				updateMap.put("Qty", object.getInt("Qty"));
				config.add(updateMap);
			}
		}
		return new LabDao().saveConfig(config, LabID);
	}

	@Override
	public List<Map<String, Object>> getConfig(int LabID) {
		LabDao dao = new LabDao();
		return dao.getComfig(LabID);
	}
	
	@Test
	public void test(){
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println(list.size());
	}

	@Override
	public void exportConfig(int LabID, String Model,String path) {
		List<Map<String, Object>> config = new LabDao().getComfig(LabID);
		GeneratePdfUtil.createLabConfig(path, config, Model);
	}

}
