package com.eoulu.util;

import java.util.Map;

public class ProcessComparator implements Comparable<Map<String,Object>>{
	private Map<String, Object> map;

	public int compareTo(Map<String, Object> map) {
		if(this.map.get("progressStatus")==null||this.map.get("progressStatus")==map.get("progressStatus")){
			return 0;
		}else if(Double.parseDouble(this.map.get("progressStatus").toString())<Double.parseDouble(map.get("progressStatus").toString())){
			return -1;
		}else{
			return 1;
		}
	}

}
