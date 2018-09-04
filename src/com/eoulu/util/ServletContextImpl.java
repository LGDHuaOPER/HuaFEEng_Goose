package com.eoulu.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eoulu.dao.NonStandardDao;
import com.eoulu.entity.NonStandard;
import com.eoulu.service.PageVisitService;
import com.eoulu.service.ReimburseService;
import com.eoulu.service.impl.NonStandardReminderServiceImpl;
import com.eoulu.service.impl.PageVisitServiceImpl;
import com.eoulu.service.impl.ReimburseServiceImpl;


public class ServletContextImpl implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		String[] timer = (String[]) context.getAttribute("timer");
		for(int i = 0;i < timer.length;i ++){
			NonStandardReminderServiceImpl reminder = (NonStandardReminderServiceImpl) context.getAttribute(timer[i]);
			reminder.getTimer().cancel();
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		
		Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();
        ServletContext context = servletContext.getServletContext();
        context.setAttribute("startTime", time);
     
        
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Properties props = new Properties();  
		        try {
					props.load(SendMailUtil.class.getResourceAsStream("rada.properties"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        String startTime = props.getProperty("startTime");
		        String endTime = props.getProperty("endTime");
		        if(endTime.equals("")){
		        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        	endTime = simpleDateFormat.format(new Date());
		        }
				RadaDataUtil.CalculateRadaData3(startTime,endTime);				
			}
	    };  
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        
		Date now = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = f.format(cal.getTime()).split(" ")[0];
		Date b=null;
		try {
			b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date+" 01:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        //第一个参数是触发事件s，第二个参数是执行延迟时间s，第三个参数是时间间隔  
		System.out.println("开始倒计时："+((b.getTime()-now.getTime())/1000));
        service.scheduleAtFixedRate(runnable, (b.getTime()-now.getTime())/1000, 24*60*60, TimeUnit.SECONDS);  
        NonStandardDao dao = new NonStandardDao();
        List<NonStandard> list = dao.getRemindProject();
        String[] timer = new String[list.size()];
        for(int i = 0;i < list.size();i ++){
        	NonStandard nonStandard = list.get(i);
        	NonStandardReminderServiceImpl reminder = new NonStandardReminderServiceImpl();
        	int ID = nonStandard.getID();

			reminder.setNonStandard(nonStandard);
			context.setAttribute("reminder"+ID, reminder);
			timer[i] = "reminder"+ID;
			
			System.out.println("名字："+"reminder"+ID);
			reminder.load();
        }
        context.setAttribute("timer",timer);
        
        //访问统计数据定时保存
        PageVisitService service2 = new PageVisitServiceImpl();
        List<Map<String,Object>> pvData = Collections.synchronizedList(service2.getData());
        
        context.setAttribute("pvData", pvData);
        ScheduledExecutorService service3 = Executors  
                .newSingleThreadScheduledExecutor();
        service3.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> pvData = (List<Map<String, Object>>) context.getAttribute("pvData");
            	PageVisitService service = new PageVisitServiceImpl();
            	service.savePVData(pvData);
				
			}
        	
        }, 60*30, 60*30, TimeUnit.SECONDS);
        
        //每月25日报销通知邮件
        Calendar calendar = Calendar.getInstance();
        Date nowTime = new Date();
        calendar.setTime(nowTime);
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(nowTime);
        if(calendar.before(nowCal)){
     
        	calendar.add(Calendar.DATE,1);
        }

        ScheduledExecutorService service4 = Executors  
                .newSingleThreadScheduledExecutor();
        service4.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());

				if(calendar.get(Calendar.DAY_OF_MONTH)==25){
					try{
						ReimburseService service = new ReimburseServiceImpl();
						service.sendNoticeMail();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
        	
        },(calendar.getTimeInMillis()-nowTime.getTime())/1000,24*60*60, TimeUnit.SECONDS);

	}
	

	
	
//	public static void main(String[] args) throws ParseException {
//		Calendar cal = Calendar.getInstance();
//		Date now = cal.getTime();
//		cal.add(Calendar.DAY_OF_MONTH, 1);
//		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = f.format(cal.getTime()).split(" ")[0];
//		Date b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date+" 00:00:00");
//		System.out.println(b);
//		System.out.println(b.getTime()-now.getTime());
//	}
//	 public static void main(String[] args) {
//	        Format f = new SimpleDateFormat("yyyy-MM-dd");
//	 
//	        Date today = new Date();
//	        System.out.println("今天是:" + f.format(today));
//	 
//	        Calendar c = Calendar.getInstance();
//	        c.setTime(today);
//	        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
//	 
//	        Date tomorrow = c.getTime();
//	        System.out.println("明天是:" + f.format(tomorrow));
//	    }
}
