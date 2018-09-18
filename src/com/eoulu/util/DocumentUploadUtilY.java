package com.eoulu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class DocumentUploadUtilY {
	public Map<String,String> upload(HttpServletRequest request,String savePath){
		String tempPath = "D:\\tempZipFile";
        File tmpFile = new File(tempPath);
        File saveFile = new File(savePath);
        Map<String,String> map = new HashMap<>();
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        if(!saveFile.exists()){
        	saveFile.mkdirs();
        }
       
        String message = "";
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 100);
            factory.setRepository(tmpFile);
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 监听文件上传进度
            upload.setProgressListener(new ProgressListener() {
                public void update(long pBytesRead, long pContentLength,int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理："
                            + pBytesRead);
                }
            });
            upload.setHeaderEncoding("UTF-8");
            upload.setFileSizeMax(1024 * 1024*100);
            upload.setSizeMax(1024 * 1024 * 10*100);
           
            List<FileItem> list = upload.parseRequest(request);
            System.out.println(list);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    map.put(name, value);
                    // value = new String(value.getBytes("iso8859-1"),"UTF-8");
 
                    System.out.println(name + "=" + value);
                } else {
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                
                    filename = filename
                            .substring(filename.lastIndexOf("\\") + 1);
                    String fileExtName = filename.substring(filename
                            .lastIndexOf(".") + 1);
                    System.out.println("上传的文件的扩展名是：" + fileExtName);
                    InputStream in = item.getInputStream();
                    String saveFilename = makeFileName(filename);
                    FileOutputStream out = new FileOutputStream(savePath
                            + "\\" + saveFilename);
                    map.put("Path", savePath + "\\" + saveFilename);
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
           
                    in.close();
                    out.close();
                    item.delete();
                    message = "文件上传成功！";
                }
            }
      
   
        } catch (Exception e) {
            message = "文件上传失败！";
            e.printStackTrace();
        }
        map.put("Response", message);
        return map;
 
    }
	
	public Map<String,String> uploadNotRename(HttpServletRequest request,String savePath){
		String tempPath = "D:\\tempZipFile";
        File tmpFile = new File(tempPath);
        File saveFile = new File(savePath);
        Map<String,String> map = new HashMap<>();
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        if(!saveFile.exists()){
        	saveFile.mkdirs();
        }
    
        String message = "";
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 100);
            factory.setRepository(tmpFile);
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 监听文件上传进度
            upload.setProgressListener(new ProgressListener() {
                public void update(long pBytesRead, long pContentLength,int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理："
                            + pBytesRead);
                }
            });
            upload.setHeaderEncoding("UTF-8");
            upload.setFileSizeMax(1024 * 1024*100);
            upload.setSizeMax(1024 * 1024 * 10*100);
           
            List<FileItem> list = upload.parseRequest(request);
            System.out.println(list);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    map.put(name, value);
                    // value = new String(value.getBytes("iso8859-1"),"UTF-8");
 
                    System.out.println(name + "=" + value);
                } else {
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    filename = filename
                            .substring(filename.lastIndexOf("\\") + 1);
       
                    InputStream in = item.getInputStream();
                    FileOutputStream out = new FileOutputStream(savePath
                            + "\\" + filename);
                    map.put("Path", savePath + "\\" + filename);
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
           
                    in.close();
                    out.close();
                    item.delete();
                    message = "文件上传成功！";
                }
            }
      
   
        } catch (Exception e) {
            message = "文件上传失败！";
            e.printStackTrace();
        }
        map.put("Response", message);
        return map;
 
    }
	
	public Map<String, Object> batchUpload(HttpServletRequest request,String savePath) {
		String tempPath = "D:\\tempZipFile";
        File tmpFile = new File(tempPath);
        File saveFile = new File(savePath);
        Map<String,Object> map = new HashMap<>();
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        
        if(!saveFile.exists()){
        	saveFile.mkdir();
        }
        List<Map<String, String>> messageList = new ArrayList<>();
      
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 100);
        factory.setRepository(tmpFile);
        ServletFileUpload upload = new ServletFileUpload(factory);
   
        upload.setHeaderEncoding("UTF-8");
        upload.setFileSizeMax(1024 * 1024*100);
        upload.setSizeMax(1024 * 1024 * 10*100);
       
        List<FileItem> list;
		try {
			list = upload.parseRequest(request);
			
	        System.out.println(list);
	        for (FileItem item : list) {
	            if (item.isFormField()) {
	                String name = item.getFieldName();
	              
	                String value = item.getString("UTF-8");
	                map.put(name, value);
	 
	            } else {
	            
	            	InputStream in = null;;
	            	FileOutputStream out = null;
	            	String filename = null;
	            		
	            	try{
		                filename = item.getName();
		                System.out.println(filename);
		                if (filename == null || filename.trim().equals("")) {
		                    continue;
		                }
		                filename = filename
	                            .substring(filename.lastIndexOf("\\") + 1);
		                in = item.getInputStream();
		            
		                out = new FileOutputStream(savePath
		                        + "\\" + filename);
		                
		                byte buffer[] = new byte[1024];
		                int len = 0;
		                while ((len = in.read(buffer)) > 0) {
		                    out.write(buffer, 0, len);
		                }
		                map.put("Path", savePath + "\\" + filename);
		                map.put("FileName", filename);
		                Map<String, String> fileInfo = new HashMap<>();
		                fileInfo.put("Path",savePath + "\\" + filename);
		                fileInfo.put("FileName",filename);
		                fileInfo.put("Message","上传成功");
		                messageList.add(fileInfo);
		             
	            	}catch(Exception e){
		                Map<String, String> fileInfo = new HashMap<>();

	            		fileInfo.put("Path",savePath + "\\" + filename);
		                fileInfo.put("FileName",filename);
		                fileInfo.put("Message","上传失败");
		                messageList.add(fileInfo);
	            		
	            	}finally {
	            		try {
							in.close();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						} 
	                    item.delete();
					}
	            }
	        }
      	
        } catch(FileUploadException e){
        	map.put("Response", "文件上传失败！");
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("FileInfo", messageList);
        return map;
	}
	
	//新建文件夹存放
	public Map<String, Object> folderBatchUpload(HttpServletRequest request,String savePath) {
		String tempPath = "D:\\tempZipFile";
        File tmpFile = new File(tempPath);
       
        Map<String,Object> map = new HashMap<>();
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        
       
        List<Map<String, String>> messageList = new ArrayList<>();
      
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 100);
        factory.setRepository(tmpFile);
        ServletFileUpload upload = new ServletFileUpload(factory);
   
        upload.setHeaderEncoding("UTF-8");
        upload.setFileSizeMax(1024 * 1024*100);
        upload.setSizeMax(1024 * 1024 * 10*100);
       
        List<FileItem> list;
		try {
			list = upload.parseRequest(request);
			
	        System.out.println(list);
	        for (FileItem item : list) {
	            if (item.isFormField()) {
	                String name = item.getFieldName();
	              
	                String value = item.getString("UTF-8");
	                if(name.equals("Folder")){
	                	System.out.println("文件夹："+value);
	                	savePath += value;
	                	File saveFile = new File(savePath);
	                	 if(!saveFile.exists()){
	                     	saveFile.mkdir();
	                     }
	                }
	                map.put(name, value);
	 
	            } else {
	            
	            	InputStream in = null;;
	            	FileOutputStream out = null;
	            	String filename = null;
	            	
	            		
	            	try{
	            		Map<String, String> fileInfo = new HashMap<>();
		                filename = item.getName();
		                System.out.println(filename);
		                if (filename == null || filename.trim().equals("")) {
		                    continue;
		                }
		                filename = filename
	                            .substring(filename.lastIndexOf("\\") + 1);
		                in = item.getInputStream();
		                File file0 = new File(savePath);
		                if(!file0.exists()){
		                	file0.mkdirs();
		                }
		                
		                File file = new File(savePath + "\\" + filename);
		                if(file.exists()){
			                fileInfo.put("FileName",filename);
			                fileInfo.put("Message","文件已存在");
		                }else{
		                	out = new FileOutputStream(file);
		                
			                byte buffer[] = new byte[1024];
			                int len = 0;
			                while ((len = in.read(buffer)) > 0) {
			                    out.write(buffer, 0, len);
			                }
			                map.put("Path", savePath + "\\" + filename);
			                map.put("FileName", filename);
			                fileInfo.put("Path",savePath + "\\" + filename);
			                fileInfo.put("FileName",filename);
			                fileInfo.put("Message","上传成功");
			             
		                }
		                messageList.add(fileInfo);
		             
	            	}catch(Exception e){
		                Map<String, String> fileInfo = new HashMap<>();

	            		fileInfo.put("Path",savePath + "\\" + filename);
		                fileInfo.put("FileName",filename);
		                fileInfo.put("Message","上传失败");
		                messageList.add(fileInfo);
	            		
	            	}finally {
	            		try {
							in.close();
							if(out != null){
								out.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						} 
	                    item.delete();
					}
	            }
	        }
      	
        } catch(FileUploadException e){
        	map.put("Response", "文件上传失败！");
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("FileInfo", messageList);
        return map;
	}
	
	

    /**
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @param filename
     *            文件的原始名称
     * @return uuid+"_"+文件的原始名称
     */
    private String makeFileName(String filename) { 
        // 为防止多用户上传导致文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }

	

}
