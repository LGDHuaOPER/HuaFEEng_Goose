package com.eoulu.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.RequirementService;
import com.eoulu.service.StatisticsService;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.service.impl.StatisticsServiceImpl;

/**
 * ����ͳ��ҳ�����ؽӿڣ����ʸýӿڿ��Է��ʵ�����ͳ�Ƶ�ҳ��
 */
@WebServlet("/Statistics")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatisticsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		 Calendar calendar = Calendar.getInstance();
		 int currentMonth = calendar.get(Calendar.MONTH) + 1;
		 int currentYear = calendar.get(Calendar.YEAR);
		StatisticsService statisticsService = new StatisticsServiceImpl();
		RequirementService requirementService = new RequirementServiceImpl();

		String year = request.getParameter("Year");
		String month = request.getParameter("Month");
		String temp = month;
		System.out.println(year+"=======M-----"+month);
		String start = "";
		String end = "";
		String start2 = "";
		String end2 = "";
		if(year==null){
			year = "2018";
			month = "";
			start = "2018-01-01";;
			end = sdf.format(new Date());
			 start2 = "2018-01";
			 end2 = sdf1.format(new Date());
		}
		else{
			if (month.equals("All")) {//按年份查询
				month = "01";
				start = year+"-"+month+"-01";
				end = ""+(Integer.parseInt(year)+1)+"-"+month+"-01";
				 start2 = year+"-"+month;
				 end2 = ""+Integer.parseInt(year)+"-12";
			}else{//按年月查询
				start = year+"-"+month+"-01";
				if(month.equals("12")){
					end =  ""+(Integer.parseInt(year)+1)+"-01-01";
				}else{
					if(Integer.parseInt(month)<9){
						end = year+"-0"+(Integer.parseInt(month)+1)+"-01";
					}else{
						end = year+"-"+(Integer.parseInt(month)+1)+"-01";
					}
				}
		
				 start2 = year+"-"+month;
				 end2 = year+"-"+month;
			}
		}
		
		System.out.println("start:"+start+"---"+end);
		System.out.println("start2:"+start2+"---"+end2);
		request.setAttribute("salesStatics",
				requirementService.getStatisticsBySalesToString(start, end));
		request.setAttribute("area", statisticsService.getAreaDataToString(start2, end2));
		request.setAttribute("sales", statisticsService.getSalesDataToString(start, end));
		request.setAttribute("targetValue", statisticsService.getTargetValue("2017-01","2018-01"));
		request.setAttribute("completeValue", statisticsService.getCompleteValue(start2, end2));
		request.setAttribute("AreaStatisticsPerMonth", statisticsService.getStatisticsByAreaPerMonth(year));

		// request.setAttribute("salesStatics",
		// requirementService.getStatisticsBySalesToString("2017-01-01",
		// "2018-01-01"));
		// request.setAttribute("area",
		// statisticsService.getAreaDataToString("2018-01", "2018-12"));
		// request.setAttribute("sales",
		// statisticsService.getSalesDataToString("2018-01-01", "2019-01-01"));
		// request.setAttribute("targetValue",
		// statisticsService.getTargetValue("2018-01", "2018-12"));
		// request.setAttribute("completeValue",
		// statisticsService.getCompleteValue("2018-01", "2018-12"));
		// String thisYear = "2018";
		// request.setAttribute("AreaStatisticsPerMonth",
		// statisticsService.getStatisticsByAreaPerMonth(thisYear));

		request.setAttribute("Year", year);
		request.setAttribute("Month", temp);
		request.setAttribute("currentYear", currentYear);
		request.setAttribute("currentMonth", currentMonth);

		request.getRequestDispatcher("WEB-INF\\statistics.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
