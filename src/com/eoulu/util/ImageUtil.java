package com.eoulu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {

	/**
	 * 将base64编码转成图片
	 * @param imgStr
	 * @param path
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null){
			return false;
		}
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
		// 解密
		byte[] b = decoder.decodeBuffer(imgStr);
		// 处理数据
		for (int i = 0; i < b.length; ++i) {
		if (b[i] < 0) {
		b[i] += 256;
		}
		}
		OutputStream out = new FileOutputStream(path);
		out.write(b);
		out.flush();
		out.close();
		return true;
		} catch (Exception e){
			return false;
		}
		
		
	}
	/**
	 * 将图片转成base64
	 * @param imgFile
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
	
	public static void main(String[] args) {
		String strImg = getImageStr("E:/test/删除.png");
		//	    System.out.println(strImg);
//		String a = "data:image/png;base64,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";
	    generateImage(strImg, "E:/test/删除w.PNG");
	}
	
	/**
	 * 处理二进制流的表单
	 * @param request
	 * @return
	 */
	public Map<String, String> getFile(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();// 用于存储表单非二进制流部分
		String fileName = null;
		String filePath = null;
		String password = "";
		String consignee = "";
		String image = "";
		String path = request.getServletContext().getRealPath("/") + "down\\image.jpeg";;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart表单数据。
		if (isMultipart == true) { // 如果是二进制流形式的表单
			FileItemFactory factory = new DiskFileItemFactory();// 通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			
			File savedFile = null;//new File(path);
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();

				if (item.isFormField()) {// 检测是否为普通表单
											// 如果是普通表单项目，将其名字与对应的value值放入map中。
					String fieldName = item.getFieldName();
					try {
						map.put(fieldName, item.getString("UTF-8"));
						if ("Password".equals(item.getFieldName())) {
							password = item.getString("utf-8");

						}
						if ("Consignee".equals(item.getFieldName())) {
							consignee = item.getString("utf-8");

						}
						if ("Image".equals(item.getFieldName())) {
							image = item.getString("utf-8");

						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} // 获取表单value时确定数据格式，如果不写，有中文的话会乱码

				} else { // 如果是提交的图片
					File fullFile = new File(item.getName()); // 获取提交的文件
					 savedFile=new
					 File(request.getServletContext().getRealPath("/") + "down\\",fullFile.getName());
					 System.out.println("util:"+fullFile.getName());
					 //在项目下新建该文件，
					try {
						item.write(savedFile); // 写入文件

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
		
		System.out.println(image);
		generateImage(image, path);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		map.put("password", password);
		map.put("consignee", consignee);
		return map;
	}
}
