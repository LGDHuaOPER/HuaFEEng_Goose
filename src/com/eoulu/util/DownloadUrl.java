package com.eoulu.util;

import java.io.IOException;
import java.util.Properties;

public class DownloadUrl {

	public String getRootUrl(){
		Properties pro = new Properties();
		try {
			pro.load(DownloadUrl.class.getResourceAsStream("download.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pro.getProperty("rootUrl");
	}
	
}
