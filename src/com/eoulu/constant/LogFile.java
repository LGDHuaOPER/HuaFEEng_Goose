package com.eoulu.constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {
	public static final String FLAG="YES";
	public static void toWrite(String action) throws IOException{
		if("YES".equals(FLAG)){
			String logFileName = "C:\\temp\\logFile.txt";
			File logFile = new File(logFileName);
			 if(!logFile.exists()){
			    	try {
						logFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			 FileWriter fileWritter = new FileWriter(logFileName,true);
	         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	         Date nowTime = new Date(System.currentTimeMillis());
	         SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         String retStrFormatNowDate = sdFormatter.format(nowTime);
	         bufferWritter.write(retStrFormatNowDate+":"+action+"\r\n");
	         bufferWritter.close();
         }
	}
}