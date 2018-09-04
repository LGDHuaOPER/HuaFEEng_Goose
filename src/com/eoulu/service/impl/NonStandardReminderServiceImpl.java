package com.eoulu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.eoulu.dao.NonStandardDao;
import com.eoulu.entity.NonStandard;
import com.eoulu.service.NonStandardService;
import com.eoulu.service.ReminderService;

public class NonStandardReminderServiceImpl implements ReminderService{
	Timer timer = new Timer();  
	NonStandard nonStandard;
	
	  
	public NonStandard getNonStandard() {
		return nonStandard;
	}

	public void setNonStandard(NonStandard nonStandard) {
		this.nonStandard = nonStandard;
	}

	class Item extends TimerTask {  
	        
	       
	      public void run() {  
	    	  
	         NonStandardService service = new NonStandardServiceImpl();
	         if(service.sendMail(nonStandard).equals("发送成功！")){
	        	 NonStandardDao dao = new NonStandardDao();
	        	 dao.updateRemindStatus(nonStandard.getID());
	        	 timer.cancel(); 
	         }
	         
	      }  
	   }  

	@Override
	public void load() {
		if(!nonStandard.getStageActualDate().equals("")){
			timer.cancel();
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date exceptDate = null;
		try {
			exceptDate = format.parse(nonStandard.getStageExpectedDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(exceptDate);
		calendar.add(Calendar.DATE, -1);
	
		
		System.out.println(format.format(calendar.getTime()));
	
		timer.schedule(new Item(),calendar.getTime());
	
			
		
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	


}
