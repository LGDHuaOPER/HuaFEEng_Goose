package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.util.DBUtil;

/**
 * Servlet implementation class ExportImplementation
 */
@WebServlet(description = "导入Excel数据到软件实施", urlPatterns = { "/ExportImplementation" })
public class ExportImplementation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportImplementation() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String sql = "SELECT CustomerName,Contact,Content,RequiremenrRemarks,ExpectedTime,Priority,Classify,Responsible,EOULURemarks FROM t_excel_import ";
		String sql2 = "SELECT ID CustomerID FROM t_customer WHERE CustomerName LIKE ? AND Contact LIKE ?";
		String sql3 = "INSERT INTO t_software_implementation (CustomerID,Engineer) VALUES (?,?)";
		String sql4 = "INSERT into t_customer (CustomerName,Contact) VALUES (?,?)";
		String sql5 = "INSERT INTO t_software_implementation_service_content (Content,CompleteTime,ImplementionID,Priority,ResponsibleMan,RequirmentClassify,Description) VALUES (?,?,?,?,?,?,?)";
		String sql6 = "SELECT ID ImplementionID from t_software_implementation WHERE CustomerID=? AND Engineer=? AND Machine='' AND SoftwareVersion=''";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, null);
		String result = "导入成功！";
		for(int i=1;i<ls.size();i++){
			String name = ls.get(i).get("CustomerName").toString().trim();
			String contact = ls.get(i).get("Contact").toString().trim();
			String content = ls.get(i).get("Content").toString().trim();
			String remarks = ls.get(i).get("RequiremenrRemarks").toString().trim();
			String expectedTime = (ls.get(i).get("ExpectedTime")==null||ls.get(i).get("ExpectedTime").equals(""))?"0000-00-00":ls.get(i).get("ExpectedTime").toString().trim();
			String priority = ls.get(i).get("Priority").toString().trim();
			String classify = ls.get(i).get("Classify").toString().trim();
			String responsible = ls.get(i).get("Responsible").toString().trim();
			String eoulu = ls.get(i).get("EOULURemarks").toString().trim();
			String engineer = "黄渊源";
			eoulu = remarks+eoulu;
			List<Map<String,Object>> temp = new DBUtil().QueryToList(sql2, new Object[]{"%"+name+"%","%"+contact+"%"});
			int customerID  = 0;
			if(temp.size()>1){
				customerID = Integer.parseInt(temp.get(1).get("CustomerID").toString());
				
			}else{
				Object id;
				try {
					id = new DBUtil().insertGetId(sql4, new Object[]{name,contact});
					customerID =  Integer.parseInt(id.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			if(customerID>0){
			}else{
				result = "客户信息ID获取失败!";
				break;
			}
			List<Map<String,Object>> list  = new DBUtil().QueryToList(sql6, new Object[]{customerID,engineer});
			int implementationID = 0;
			if(list.size()>1){
				implementationID = Integer.parseInt(list.get(1).get("ImplementionID").toString());
				
			}else{
				Object software;
				try {
					software = new DBUtil().insertGetId(sql3, new Object[]{customerID,engineer});
					implementationID = Integer.parseInt(software.toString());
					System.out.println("implementationID="+implementationID);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			if(implementationID>0){
				boolean flag = new DBUtil().executeUpdate(sql5, new Object[]{content,expectedTime,implementationID,priority,responsible,classify,eoulu})>0?true:false;
				if(!flag){
					result = "服务登记内容存储失败!";
					break;
				}
			}else{
				result = "软件实施存储失败!";
				break;
			}
			
			
			
			
		}
		response.getWriter().write(result);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
