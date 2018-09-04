package com.eoulu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.eoulu.dao.DocumentModelDao;
import com.eoulu.entity.DocumentModel;


public class DocumentModelUtil {
	private static final String encoding = "UTF-8";
	 public  boolean getForm(File file01,HttpServletRequest request,String tempPath) throws FileUploadException, IOException{
		System.out.println(12413);	
		
		 Map<String, String> map=new HashMap<String, String>();
			 DocumentModel model = new DocumentModel();
			String fileName=null;
			String name = null;
			String type = null;
			DiskFileItemFactory factory = new DiskFileItemFactory();
		    factory.setRepository(file01);//设置临时目录
		    factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		    items = upload.parseRequest(request);
		    byte data[] = new byte[1024];
		    if (items != null){
		        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
		            FileItem item = (FileItem) iterator.next();
		            if (item.isFormField()) {
		            	if("name".equals(item.getFieldName())) 
		                 {
		            		name = item.getString("utf-8");
		                	System.out.println("content:"+name);
		                 }
		            	if("type".equals(item.getFieldName())) 
		                 {
		            		type = item.getString("utf-8");
		                	System.out.println("content:"+type);
		                 }
		            }else{
		                fileName = item.getName().substring(
		                        item.getName().lastIndexOf(File.separator) + 1,
		                        item.getName().length());
		                InputStream inputStream = item.getInputStream();
		                OutputStream outputStream = new FileOutputStream(tempPath + fileName);
		                int i;
						while ((i = inputStream.read(data)) != -1) {
		                    outputStream.write(data, 0, i);
		                }
		                inputStream.close();
		                outputStream.close();
		                fileName = tempPath + fileName;
		            }
		        }
		    }
			if(fileName!=null){
				model.setFilePath(fileName);
				model.setFileName(name);
				model.setType(Integer.parseInt(type));
			}
		    System.out.println(type);
			DocumentModelDao dao = new DocumentModelDao();
			boolean flag = dao.add(model);

			return flag;
		}
//	 /**
//	  * 将文件转换为byte[],处理成byte存放到数据库
//	  * @param file 存放文件的临时目录+文件名
//	  * @return
//	  */
//	 public byte[] getBytes(File file){  
//	       byte[] buffer = null;  
//	          try {     
//	              FileInputStream fis = new FileInputStream(file);  
//	              ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
//	              byte[] b = new byte[1000];  
//	              int n;  
//	              while ((n = fis.read(b)) != -1) {  
//	                  bos.write(b, 0, n);  
//	              }  
//	              fis.close();  
//	              bos.close();  
//	              buffer = bos.toByteArray();  
//	          } catch (FileNotFoundException e) {  
//	              e.printStackTrace();  
//	          } catch (IOException e) {  
//	              e.printStackTrace();  
//	          }  
//	        return buffer;  
//	 }
	 
	
}
