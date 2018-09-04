package com.eoulu.syn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.eoulu.util.Java2Word;

public class ExportWorkLog {
	
	private Lock lock = new ReentrantLock();
	HashMap<String, Object> data = new HashMap<>();
	
	public String exportLog(HttpServletRequest request){
		lock.lock();
		try{
			String Name = request.getParameter("Name")==null?"":request.getParameter("Name").trim();
			String Morning = request.getParameter("Morning")==null?"":request.getParameter("Morning");
			String Afternoon = request.getParameter("Afternoon")==null?"":request.getParameter("Afternoon");
			String MorningPlan = request.getParameter("MorningPlan")==null?"":request.getParameter("MorningPlan"); 
			String AfternoonPlan = request.getParameter("AfternoonPlan")==null?"":request.getParameter("AfternoonPlan"); 
			String Introspection = request.getParameter("Introspection")==null?"":request.getParameter("Introspection");
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
			String date = simpleDateFormat.format(new Date());
			data = new HashMap<>();
			data.put("${Date}", date);
			data.put("${Name}", Name);
			data.put("${Morning}", Morning);
			data.put("${Afternoon}", Afternoon);
			data.put("${MorningPlan}", MorningPlan);
			data.put("${AfternoonPlan}", AfternoonPlan);
			data.put("${Introspection}", Introspection);
			
			String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\工作日志"+date+"-"+Name
					+ ".doc";
			Java2Word word = new Java2Word();
			word.toWord("E:/Model/工作日志模板.doc", downLoadUrl, data, "end");
			return downLoadUrl;
		}finally{
			lock.unlock();
		}
	}
	
	@Test
	public void test(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
		System.out.println(simpleDateFormat.format(new Date()));
		
	}

}
