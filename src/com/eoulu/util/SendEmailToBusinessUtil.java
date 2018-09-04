package com.eoulu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class SendEmailToBusinessUtil {
	String[] to = null;
	String[] copyto = null;
	
    private String SEND_USER = null;//昵称
    private String SEND_UNAME = null;
    private String SEND_PWD = null;
    public boolean doSendHtmlEmail(String subject, String content, String[] fileList) {
    	Properties pro = new Properties();
    	Properties pro2 = new Properties();
    	List<String> list = new ArrayList<>();
    	List<String> ls = new ArrayList<>();
    	try {
//    		System.out.println(SendEmailToBusinessUtil.class.getResourceAsStream("email.properties"));
			pro.load(SendEmailToBusinessUtil.class.getResourceAsStream("email.properties"));
			SEND_USER = pro.getProperty("SEND_USER");
			SEND_UNAME = pro.getProperty("SEND_UNAME");
			SEND_PWD = pro.getProperty("SEND_PWD");
			
			try {
				pro2.load(SendEmailToBusinessUtil.class.getResourceAsStream("business.properties"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			int toCount = Integer.parseInt(pro2.getProperty("to"));
			for(int i=0 ; i<toCount ; i++){
				int temp = i+1;
				String key = "To"+temp;
				list.add(pro2.getProperty(key));
				
			}
			to = new String[toCount];
			for(int i=0 ; i<list.size();i++){
				to[i] = list.get(i);
			}
			int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
			copyto = new String[copytoCount];
			for(int i=0 ; i<copytoCount ; i++){
				int temp = i+1;
				String key = "CopyTo"+temp;
				ls.add(pro2.getProperty(key));
				
			}
			for(int i=0 ; i<ls.size();i++){
				copyto[i] = ls.get(i);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	return new JavaMailToolsUtil(SEND_USER, SEND_UNAME, SEND_PWD).doSendHtmlEmail(subject, content, fileList, to, copyto);
    }
    
    
    public Map<String, String> getForm(File file01,HttpServletRequest request,String tempPath) throws FileUploadException, IOException{
		Map<String, String> map=new HashMap<String, String>();
		String fileName=null;
		String content=null;
		String ContractTitle = null;
		String ContractNo = null;
		String PONO = "";
		DiskFileItemFactory factory = new DiskFileItemFactory();//1、创建一个DiskFileItemFactory工厂
	    factory.setRepository(file01);//设置临时目录
	    factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
	    ServletFileUpload upload = new ServletFileUpload(factory);//2、创建一个文件上传解析器
	    upload.setHeaderEncoding("UTF-8");//解决上传文件名的中文乱码
	    //3、判断提交上来的数据是否是上传表单的数据
        if(!upload.isMultipartContent(request)){
            //按照传统方式获取数据
           
        }
	    java.util.List<org.apache.commons.fileupload.FileItem> items = null;
	    items = upload.parseRequest(request); //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	    byte data[] = new byte[1024];
	    if (items != null){
	        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
	            FileItem item = (FileItem) iterator.next();
	            if (item.isFormField()) {
	            	if("Content".equals(item.getFieldName())) 
	                 {
	            		content = item.getString("utf-8");
	                	System.out.println("content:"+content);
	                 }
	            	if("ContractTitle".equals(item.getFieldName())){
	            		ContractTitle = item.getString("utf-8");
	            	}
	            	if("ContractNo".equals(item.getFieldName())){
	            		ContractNo = item.getString("utf-8");
	            	}
	            	if("PONO".equals(item.getFieldName())){
	            		PONO = item.getString("utf-8");
	            	}
	            }else{
	            	 //如果fileitem中封装的是上传文件，得到上传的文件名称，
                    fileName = item.getName();
                    if(fileName==null||fileName.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
//	                fileName = fileName.substring(
//	                        item.getName().lastIndexOf(File.separator) + 1,
//	                        item.getName().length());
//	                System.out.println("test:::::::"+fileName);
	                InputStream inputStream = item.getInputStream();
	               // OutputStream outputStream = new FileOutputStream(tempPath + fileName);
	                //创建一个文件输出流
                    FileOutputStream outputStream = new FileOutputStream(tempPath+File.separator+fileName);
	                int i;
					while ((i = inputStream.read(data)) != -1) {
	                    outputStream.write(data, 0, i);
	                }
	                inputStream.close();
	                outputStream.close();
	                fileName = tempPath + fileName;
	                System.out.println(fileName);
	            }
	        }
	    }
	    map.put("fileName", fileName);
	    map.put("content", content);
	    map.put("ContractTitle", ContractTitle);
	    map.put("ContractNo", ContractNo);
	    map.put("PONO",PONO);
		return map;
	}
    
    
    
}
