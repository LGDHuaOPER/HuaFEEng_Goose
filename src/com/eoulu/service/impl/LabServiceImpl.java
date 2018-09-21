package com.eoulu.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
	public List<Map<String, Object>> getDataByPage(String Laboratory,Page page) {
		return new LabDao().getDataByPage(Laboratory,page);
	}

	@Override
	public boolean insert(Laboratory lab) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime = format.format(new Date());
		lab.setUpdateTime(updateTime);
		
		return new LabDao().insert(lab);
	}

	@Override
	public boolean update(Laboratory lab) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime = format.format(new Date());
		lab.setUpdateTime(updateTime);
		
		return new LabDao().update(lab);
	}

	@Override
	public int getCounts(String Laboratory) {
		return new LabDao().getCounts(Laboratory);
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
	
	
	public boolean deleteFile(int ID, String fileName) {
		boolean flag = false;
		LabDao dao = new LabDao();
		List<Map<String, Object>> list = dao.getDocument(ID);
		String fileStr = "";
		if(list.size()>1){
			fileStr = list.get(1).get("Document").toString();
		}
		String[] fileArr = fileStr.split("::");
		fileStr = "";
		for(int i = 0;i < fileArr.length;i++){
			if(!fileArr[i].equals(fileName)){
				fileStr += (fileArr[i]+"::");
			}
		}
		if(ID!= 0){
			dao.updateDocument(ID, fileStr);
		}
	
		String folder = "E:\\LogisticsFile\\File\\LabDocument\\";
		if(!fileName.equals("")){
			try {
				FileUtils.forceDelete(new File(folder+fileName));
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
	
		return flag;
	}

}
