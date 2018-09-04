package com.eoulu.syn;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.service.StaffApplicationService;
import com.eoulu.service.impl.StaffApplicationServiceImpl;
import com.eoulu.util.Java2Word;

public class ExportApplication {
	private Lock lock = new ReentrantLock();// 锁对象

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportWord(HttpServletRequest request) {
		String loadUrl = "";
		lock.lock();
		int id = Integer
				.parseInt(request.getParameter("ID") == null ? "0" : request.getParameter("ID").toString());
		String type = request.getParameter("Type") == null ? "" : request.getParameter("Type");
		StaffApplicationService service = new StaffApplicationServiceImpl();
		HashMap<String, Object> map = new HashMap<>();
		Map<String, String> map2 = new HashMap<>();
		map2.put("年休假", "${classify1}");
		map2.put("事假", "${classify2}");
		map2.put("病假", "${classify3}");
		map2.put("婚假", "${classify4}");
		map2.put("产假", "${classify5}");
		map2.put("丧假", "${classify6}");
		map2.put("其他", "${classify7}");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String department = request.getParameter("Department") == null ? ""
				: request.getParameter("Department").toString();
		String name = request.getParameter("RealName") == null ? "" : request.getParameter("RealName").toString();
		String classify = request.getParameter("Classify") == null ? "" : request.getParameter("Classify").toString();
		String reason = request.getParameter("Reason") == null ? "" : request.getParameter("Reason").toString();
		String startTime = request.getParameter("StartTime") == null ? df.format(new Date())
				: request.getParameter("StartTime").toString();
		String endTime = request.getParameter("EndTime") == null ? df.format(new Date())
				: request.getParameter("EndTime").toString();

		Set<String> key = map2.keySet();
		for (String k : key) {
			if (k.equals(classify)) {
				map.put(map2.get(k), "√");
			} else {
				map.put(map2.get(k), "□");
			}
		}
		Date start = null;
		Date end = null;
		DecimalFormat df2 = new DecimalFormat("0.0");
		try {
			start = df.parse(startTime);
			end = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
		int hour = (int) ((end.getTime() - start.getTime()) % (1000 * 60 * 60 * 24) / (1000 * 60 * 60));
		if (start.getHours() <= 9 && end.getHours() >= 18) {
			day += 1;
			hour = 0;
		}
		if (hour == 0) {
			map.put("${Count}", day + "天");
		} else if (day == 0) {
			map.put("${Count}", hour + "小时");
		} else {
			map.put("${Count}", day + "天" + hour + "小时");
		}
		map.put("${Department}", department);
		map.put("${RealName}", name);
		map.put("${Reason}", reason);
		map.put("${startTime}", startTime.replaceFirst("-", "年").replaceAll("-", "月").replaceAll(" ", "日"));
		map.put("${endTime}", endTime.replaceFirst("-", "年").replaceAll("-", "月").replaceAll(" ", "日"));
		Java2Word word = new Java2Word();
		String path = request.getServletContext().getRealPath("/") + "down/《请假申请单》.doc";
		word.toWord("E:\\Model\\《请假申请单》-模板.doc", path, map,"end");
		loadUrl = "down/《请假申请单》.doc";

		lock.unlock();
		return loadUrl;
	}

	public static void main(String[] args) {
		String a = "2017-01-12 12:14";
		String b = "2017-01-15 18:18";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			System.out.println(df.parse(b).getHours());
			System.out.println((df.parse(b).getTime() - df.parse(a).getTime()) / (1000 * 60 * 60 * 24) + "天");
			System.out.println(
					(df.parse(b).getTime() - df.parse(a).getTime()) % (1000 * 60 * 60 * 24) / (1000 * 60 * 60) + "小时");
			// DecimalFormat df2 = new DecimalFormat("0.0");
			// System.out.println(
			// df2.format((double) (df.parse(b).getTime() -
			// df.parse(a).getTime()) / (1000 * 60 * 60 * 24)));

			// System.out.println((int)Math.floor(Double.parseDouble("2.1231331")));
			// System.out.println(("2.1231331").substring("2.1231331".indexOf('.')
			// + 1));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
