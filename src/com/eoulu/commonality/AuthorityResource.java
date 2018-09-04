/**
 * 
 */
package com.eoulu.commonality;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.eoulu.util.DBUtil;

/**
 * @author zhangkai
 *
 *
 * Ȩ�޲�����
 */
public class AuthorityResource {

	//����Ȩ�޵Ķ�Ӧ��
	public static Map<String,Object> map = new HashMap<String,Object>();
	
	//���ݿ������е�Ȩ�޶�ȡ�������д����  ��ʼ
	static{
		String sql = "select t_authority.Name,t_authority.ReqUrl from t_authority";
		DBUtil db = new DBUtil();
		List<Map<String,Object>> ls = db.QueryToList(sql, new Object[0]);
		
		int authLength = ls.size();

		for(int i=1; i<authLength; i++){
		map.put(ls.get(i).get("ReqUrl").toString().trim(),ls.get(i).get("ReqUrl").toString().trim());	
		}
	}
	//���ݿ������е�Ȩ�޶�ȡ�������д����  ����
	
	//�ж��û��Ƿ����ָ��ҳ��ķ���Ȩ��
	public static boolean isExist(HttpServletRequest request,String authority){
		boolean flag = false;
		String systemAuthority = map.get(authority)==null?"":map.get(authority).toString();
		String[] auth = (String[]) request.getSession().getAttribute("authorities");
		
		for(String kk : auth){
			if(kk.equals(authority))
				flag = true;
		}
		return flag;
	}
	
	
	
	
//	//�����û������ʵ����Ӻ�Ȩ�޵ĶԱȣ�����Ȩ�޾���ת�����߱�����ת����ҳ��
//	public static void LoginSuccess(HttpServletRequest request,HttpServletResponse response,String authority){
//		if(isExist(request,authority)){
//			try {
//				request.getRequestDispatcher(authority).forward(request, response);
//			} catch (ServletException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}else{
//			try {
//				request.getRequestDispatcher("Error?status=404").forward(request, response);
//			} catch (ServletException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	
	/**
	 * �жϵ�ַ�Ƿ���Ҫ����
	 * */
	public static boolean isController(String authority){
		boolean flag = false;
		
		flag = map.containsKey(authority);
		
		return flag;
	}
	
	
	
//	@Test
//	public void test(){
//		
//		
//	}
	
}
