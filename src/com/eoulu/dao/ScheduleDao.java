package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Schedule;
import com.eoulu.util.DBUtil;

public class ScheduleDao {

	public List<Map<String, Object>> getAllData(Page page, String date) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT * FROM t_schedule where Date=? order by ID desc limit ?,?";

		Object[] param = new Object[] { date, (page.getCurrentPage()-1)*page.getRows(),page.getRows() };
		ls = db.QueryToList(sql, param);

		return ls;
	}

	public int getAllCounts(String date) {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_schedule where Date=? ";
		Object[] param = new Object[] { "AllCounts", date };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public boolean insert(Schedule factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "insert into t_schedule (`Name`,CustomerUnit,ServiceItem,TransportTool,TrainNumber,Departure,Hotel,Date,Destination,DepartureDate,DestinationDate,OperatingTime,TravelDistance,HotelExpense,TrafficExpense) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[15];
		param[0] = factory.getName();
		param[1] = factory.getCustomerUnit();
		param[2] = factory.getServiceItem();
		param[3] = factory.getTransportTool();
		param[4] = factory.getTrainNumber();
		param[5] = factory.getDeparture();
		param[6] = factory.getHotel();
		param[7] = factory.getDate();
		param[8] = factory.getDestination();
		param[9] = factory.getDepartureDate();
		param[10] = factory.getDestinationDate();
		param[11] = dfg.format(new Date());
		param[12] = factory.getTravelDistance();
		param[13] = factory.getHotelExpense();
		param[14] = factory.getTrafficExpense();

		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean update(Schedule factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_schedule set `Name`=?,CustomerUnit=?,ServiceItem=?,TransportTool=?,TrainNumber=?,Departure=?,Hotel=?,Date=?,Destination=?,DepartureDate=?,DestinationDate=?,OperatingTime=?,TravelDistance=?,HotelExpense=?,TrafficExpense=? where ID=?";
		Object[] param = new Object[16];
		param[0] = factory.getName();
		param[1] = factory.getCustomerUnit();
		param[2] = factory.getServiceItem();
		param[3] = factory.getTransportTool();
		param[4] = factory.getTrainNumber();
		param[5] = factory.getDeparture();
		param[6] = factory.getHotel();
		param[7] = factory.getDate();
		param[8] = factory.getDestination();
		param[9] = factory.getDepartureDate();
		param[10] = factory.getDestinationDate();
		param[11] = dfg.format(new Date());
		param[12] = factory.getTravelDistance();
		param[13] = factory.getHotelExpense();
		param[14] = factory.getTrafficExpense();
		param[15] = factory.getID();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean delete(int id){
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "delete from t_schedule where ID = ?";
		Object[] param = new Object[1];
		param[0] = id;
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
			
		
	}
	

	public List<Map<String, Object>> getPerson() {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT Name from t_sales_representative";
		Object[] param = null;

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public List<Map<String, Object>> getAllDataByName(String name, Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT * FROM t_schedule where replace(Name,' ','') = replace(?,' ','') order by ID desc limit ?,?";
		Object[] param = new Object[] { name, (page.getCurrentPage()-1)*page.getRows(),page.getRows() };

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public List<Map<String, Object>> getDateByName(String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT DISTINCT Date FROM t_schedule where replace(Name,' ','') = replace(?,' ','') ";
		Object[] param = new Object[] { name };

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public int getCountsByName(String name) {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_schedule where replace(Name,' ','') = replace(?,' ','')";
		Object[] param = new Object[] { "AllCounts", name };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public static Map<String, Object> getDataByTime(String startTime, String endTime,String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		if (startTime.equals("0000-00-00") && endTime.equals("0000-00-00")) {
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = null;
			ls = db.QueryToList(sql, param);
		} else if(name.equals("")){
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where Date BETWEEN ? AND ? AND Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = new Object[] { startTime, endTime };
			ls = db.QueryToList(sql, param);
		}else{
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where replace(Name,' ','') = replace(?,' ','') and Date BETWEEN ? AND ? AND Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = new Object[] {name, startTime, endTime };
			ls = db.QueryToList(sql, param);
		}

		Map<String, Object> map = new LinkedHashMap();
		if (ls.size() > 1) {
			for (int i = 0; i < ls.size(); i++) {
				if (ls.get(i).get("Destination") != null) {
					String key = ls.get(i).get("Destination").toString();
					String value = ls.get(i).get("Count").toString();
					map.put(key, value);
				}

			}
		}
//		System.out.println(map);
		return map;
	}
/*
	public static String[] getProvince(String startTime, String endTime,String name) {
		List<Map<String, Object>> ls = null;
		Set<String> set = new HashSet<>();
		DBUtil db = new DBUtil();
		if (startTime.equals("0000-00-00") && endTime.equals("0000-00-00")) {
			String sql = "SELECT a.province FROM "
					+ "(select t_provinces.province from t_provinces LEFT JOIN t_cities ON "
					+ " t_cities.provinceid=t_provinces.provinceid where t_cities.city IN "
					+ "(select DISTINCT(Destination) city from t_schedule ) UNION "
					+ " select t_provinces.province from t_provinces  where t_provinces.province IN "
					+ "(select DISTINCT(Destination) city from t_schedule ) )a";
			Object[] param = null;
			ls = db.QueryToList(sql, param);
		} else if(name.equals("")){
			String sql = "SELECT a.province FROM "
					+ "(select t_provinces.province from t_provinces LEFT JOIN t_cities ON "
					+ " t_cities.provinceid=t_provinces.provinceid where t_cities.city IN "
					+ "(select DISTINCT(Destination) city from t_schedule where Date" + " BETWEEN ? AND ? ) UNION "
					+ " select t_provinces.province from t_provinces  where t_provinces.province IN "
					+ "(select DISTINCT(Destination) city from t_schedule where Date" + " BETWEEN ? AND ? ) )a";
			Object[] param = new Object[] { startTime, endTime,startTime, endTime };
			ls = db.QueryToList(sql, param);
		}else{
			String sql = "SELECT a.province FROM "
					+ "(select t_provinces.province from t_provinces LEFT JOIN t_cities ON "
					+ " t_cities.provinceid=t_provinces.provinceid where t_cities.city IN "
					+ "(select DISTINCT(Destination) city from t_schedule where replace(Name,' ','') = replace(?,' ','') and Date" + " BETWEEN ? AND ? ) UNION "
					+ " select t_provinces.province from t_provinces  where t_provinces.province IN "
					+ "(select DISTINCT(Destination) city from t_schedule where replace(Name,' ','') = replace(?,' ','') and Date" + " BETWEEN ? AND ? ) )a";
			Object[] param = new Object[] { name, startTime, endTime, name, startTime, endTime };
			ls = db.QueryToList(sql, param);
		}
		if (ls.size() > 1) {
			for (int i = 1; i < ls.size(); i++) {
				String province = ls.get(i).get("province").toString();
				set.add(province);
			}

		}
		String[] att = new String[set.size()];
		set.toArray(att);
		return att;
	}*/
	public List<Map<String,Object>> getProvinceOrder (String startTime, String endTime,String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		if (startTime.equals("0000-00-00") && endTime.equals("0000-00-00")) {
			String sql = "SELECT province,count(province) times from t_schedule LEFT JOIN t_provinces "
					+"on t_schedule.Destination = t_provinces.province GROUP BY province UNION "
					+"SELECT province,COUNT(province) times from t_schedule LEFT JOIN t_cities ON "
					+"t_schedule.Destination = t_cities.city LEFT JOIN t_provinces ON "
					+"t_cities.provinceid = t_provinces.provinceid GROUP BY province ORDER BY times DESC";
			Object[] param = null;
			ls = db.QueryToList(sql, param);
		} else if(name.equals("")){
			String sql ="SELECT province,count(province) times from t_schedule LEFT JOIN t_provinces "
					+"on t_schedule.Destination = t_provinces.province where Date" + " BETWEEN ? AND ? GROUP BY province UNION "
					+"SELECT province,COUNT(province) times from t_schedule LEFT JOIN t_cities ON "
					+"t_schedule.Destination = t_cities.city LEFT JOIN t_provinces ON "
					+"t_cities.provinceid = t_provinces.provinceid where Date" + " BETWEEN ? AND ? GROUP BY province ORDER BY times DESC";
			Object[] param = new Object[] { startTime, endTime,startTime, endTime };
			ls = db.QueryToList(sql, param);
		}else{
			String sql = "SELECT province,count(province) times from t_schedule LEFT JOIN t_provinces "
					+"on t_schedule.Destination = t_provinces.province where replace(Name,' ','') = replace(?,' ','') and Date" + " BETWEEN ? AND ?  GROUP BY province UNION "
					+"SELECT province,COUNT(province) times from t_schedule LEFT JOIN t_cities ON "
					+"t_schedule.Destination = t_cities.city LEFT JOIN t_provinces ON "
					+"t_cities.provinceid = t_provinces.provinceid where replace(Name,' ','') = replace(?,' ','') and Date" + " BETWEEN ? AND ?  GROUP BY province ORDER BY times DESC";
			Object[] param = new Object[] { name, startTime, endTime, name, startTime, endTime };
			ls = db.QueryToList(sql, param);
		}
	
		return ls;
	}
	public List<Map<String,Object>> queryByCondition(String parameter,Object[] oj){
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT * FROM t_schedule WHERE "+parameter+" =? order by ID limit ?,?";
		}else{
			if(oj.length == 2){
				sql = "SELECT * FROM t_schedule order by ID limit ?,?";
			}else{
				sql = "SELECT * FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') order by ID limit ?,?";
			}
		}
			
		List<Map<String,Object>> ls = db.QueryToList(sql, oj);
		return ls;
	}
	public int queryCounts(String parameter,Object[] oj){
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT count(ID) FROM t_schedule WHERE "+parameter+" =? ";
		}else{
			if(oj == null){
				sql = "SELECT count(ID) FROM t_schedule";
			}else{
				sql = "SELECT count(ID) FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') order by ID";
			}
		}
	
		List<Map<String,Object>> ls = db.QueryToList(sql, oj);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}
	public List<Map<String, Object>> getDate(String parameter,Object[] oj) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT DISTINCT Date FROM t_schedule WHERE "+parameter+" =? ";
		}else{
			if(oj == null){
				sql = "SELECT DISTINCT Date FROM t_schedule";
			}else{
				sql = "SELECT DISTINCT Date FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') ";
			}
		}
				

		ls = db.QueryToList(sql, oj);

		return ls;

	}
	public List<Map<String,Object>> getEngineer(){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql =  "SELECT StaffName FROM (SELECT StaffName FROM t_staff  union " 
				+ "ALL SELECT Name StaffName  FROM t_schedule) t GROUP BY(REPLACE(StaffName,' ','')) "
				+ "ORDER BY COUNT(REPLACE(StaffName,' ','')) DESC";
		ls = db.QueryToList(sql, null);
		return ls;
	}
	
	public List<Map<String,Object>> getDistanceOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select Name, SUM(TravelDistance) SumDistance from t_schedule  where Date BETWEEN ? AND ? GROUP BY (replace(Name,' ','')) ORDER BY SumDistance DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getFrequenceOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select Name, Count(Name) times from t_schedule  where Date BETWEEN ? AND ? GROUP BY (replace(Name,' ','')) ORDER BY times DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getExpenseOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select replace(Destination,'市','') Destination,SUM(HotelExpense+TrafficExpense) Expense from t_schedule " 
				+"WHERE Destination != '' AND Destination IS NOT NULL AND Date BETWEEN ? AND ? GROUP BY (replace(Destination,'市','')) ORDER BY Expense DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getCity(){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,Departure,Destination FROM t_schedule WHERE Departure != ''  AND Destination != ''";
		ls = db.QueryToList(sql, null);
		return ls;
	}
	public void updateDistance(List<Map<String,String>> list){
		DBUtil dbUtil= new DBUtil();
		Connection connection = dbUtil.getConnection();
		try {
			connection.setAutoCommit(false);

			String sql = "update t_schedule set TravelDistance=? where ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			for(int i = 0;i < list.size();i ++){
				statement.setFloat(1, Float.parseFloat(list.get(i).get("Distance")));
				statement.setInt(2, Integer.parseInt(list.get(i).get("ID")));
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
	
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			dbUtil.closed();
		}
	}
	
	
	
	public static void main(String[] args){
		ScheduleDao  dao= new ScheduleDao();
		List<Map<String,Object>> ls = dao.getCity();
		for(int i = 1;i < ls.size();i ++){
			String departure = ls.get(i).get("Departure").toString();
			if(!departure.endsWith("市")&&!departure.endsWith("东")&&!departure.endsWith("西")&&!departure.endsWith("南")&&!departure.endsWith("北")){
				Map<String, Object> old = ls.get(i);
				if(departure.length()>6){
					old.put("Departure", "福州");
				}else{
					old.put("Departure", departure+"市");
				}
				
			}
			
		}
		System.out.println(ls);
	}

	
}
