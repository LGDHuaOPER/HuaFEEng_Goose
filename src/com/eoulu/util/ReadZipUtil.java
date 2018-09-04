package com.eoulu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ReadZipUtil {

	public Map<String, String> getForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		String fileName = null;
		String area = null;
		String filePath = null;
		String year = null;
		String type = null;
		String remarks = null;
		String content = null;
		String author = null;
		String password = null;
		String isExist = "notExists";
		String ID = null;
		String DocuType = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
		factory.setRepository(file01);// 设置临时目录
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		ServletFileUpload upload = new ServletFileUpload(factory);// 2、创建一个文件上传解析器
		upload.setHeaderEncoding("UTF-8");// 解决上传文件名的中文乱码
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!upload.isMultipartContent(request)) {
			// 按照传统方式获取数据

		}
		java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		items = upload.parseRequest(request); // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		byte data[] = new byte[1024];
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {
				System.out.println("几次："+123123);
				FileItem item = (FileItem) iterator.next();
				if (item.isFormField()) {
					if ("Area".equals(item.getFieldName())) {
						area = item.getString("utf-8");

					}
					if ("Year".equals(item.getFieldName())) {
						year = item.getString("utf-8");

					}
					if ("Type".equals(item.getFieldName())) {
						type = item.getString("utf-8");

					}
					if("Remarks".equals(item.getFieldName())){
						remarks = item.getString("utf-8");
					}
					if("Content".equals(item.getFieldName())){
						content = item.getString("utf-8");
					}
					if("Author".equals(item.getFieldName())){
						author = item.getString("utf-8");
					}
					if("Password".equals(item.getFieldName())){
						password = item.getString("utf-8");
					}if("ID".equals(item.getFieldName())){
						ID = item.getString("utf-8");
					}if("DocuType".equals(item.getFieldName())){
						DocuType = item.getString("utf-8");
					}
				} else {
					// 如果fileitem中封装的是上传文件，得到上传的文件名称，
					fileName = item.getName();
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					System.out.println(fileName);
					// fileName = fileName.substring(
					// item.getName().lastIndexOf(File.separator) + 1,
					// item.getName().length());
					// System.out.println("test:::::::"+fileName);
					InputStream inputStream = item.getInputStream();
					// OutputStream outputStream = new FileOutputStream(tempPath
					// + fileName);
					// 先判断下文件是否存在，若存在先进行删除
					File tempFile = new File(tempPath + fileName);
					if (tempFile.exists()) {
						System.out.println("cunzai?:" + tempFile.exists());
						tempFile.delete();
						isExist = "exists";
					}
					// 创建一个文件输出流
					FileOutputStream outputStream = new FileOutputStream(tempPath + File.separator + fileName);
					int i;
					while ((i = inputStream.read(data)) != -1) {
						System.out.println("124");
						outputStream.write(data, 0, i);
					}
					inputStream.close();
					outputStream.close();
					
					filePath = tempPath + fileName;
					System.out.println(filePath);
				}
			}
		}
System.out.println(tempPath);
		map.put("fileName", fileName);
		map.put("filePath", filePath);
		map.put("area", area);
		map.put("year", year);
		map.put("type", type);
		map.put("remarks", remarks);
		map.put("content", content);
		map.put("author", author);
		map.put("isExist", isExist);
		map.put("password", password);
		map.put("ID", ID);
		map.put("DocuType", DocuType);
		return map;
	}

	public List<Map<String, String>> getMoreForm(File file01, HttpServletRequest request, String tempPath)
			throws FileUploadException, IOException {
		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		String area = null;
		String year = null;
		String type = null;
		String remarks = null;
		String content = null;
		String author = null;
		String password = null;
		String batch = null;
		String DocuType = null;
		int count = 0;

		DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
		factory.setRepository(file01);// 设置临时目录
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		ServletFileUpload upload = new ServletFileUpload(factory);// 2、创建一个文件上传解析器
		upload.setHeaderEncoding("UTF-8");// 解决上传文件名的中文乱码
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!upload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			System.out.println("不是表单");
		}
		java.util.List<org.apache.commons.fileupload.FileItem> items = null;
		items = upload.parseRequest(request); // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		byte data[] = new byte[1024];
		if (items != null) {
			for (Iterator iterator = items.iterator(); iterator.hasNext();) {

				String fileName = null;
				String filePath = null;

				FileItem item = (FileItem) iterator.next();
				
				count++;
				if (item.isFormField()) {
					if ("Area".equals(item.getFieldName())) {
						area = item.getString("utf-8");
						System.out.println(area + count);
					}
					if ("Year".equals(item.getFieldName())) {
						year = item.getString("utf-8");

					}
					if ("Type".equals(item.getFieldName())) {
						type = item.getString("utf-8");

					}
					if("Remarks".equals(item.getFieldName())){
						remarks = item.getString("utf-8");
					}
					if("Content".equals(item.getFieldName())){
						content = item.getString("utf-8");
					}
					if("Author".equals(item.getFieldName())){
						author = item.getString("utf-8");
					}
					if("Password".equals(item.getFieldName())){
						password = item.getString("utf-8");
					}
					if("DocuType".equals(item.getFieldName())){
						DocuType = item.getString("utf-8");
					}
					
				} else {
					Map<String, String> tempMap = new HashMap<String, String>();
					// 如果fileitem中封装的是上传文件，得到上传的文件名称，
					fileName = item.getName();
					System.out.println("文件：" + fileName);
					if (fileName == null || fileName.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					// fileName = fileName.substring(
					// item.getName().lastIndexOf(File.separator) + 1,
					// item.getName().length());
					// System.out.println("test:::::::"+fileName);
					InputStream inputStream = item.getInputStream();
					// OutputStream outputStream = new FileOutputStream(tempPath
					// + fileName);
					// 先判断下文件是否存在，若存在先进行删除
					File tempFile = new File(tempPath + fileName);
					String isExist = "notExists";
					if (tempFile.exists()) {
						System.out.println("cunzai?:" + tempFile.exists());
						tempFile.delete();
						isExist = "exists";
					}
					// 创建一个文件输出流
					FileOutputStream outputStream = new FileOutputStream(tempPath + File.separator + fileName);
					int i;
					while ((i = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, i);
					}
					inputStream.close();
					outputStream.close();
					filePath = tempPath + fileName;
					System.out.println(fileName);
					tempMap.put("fileName", fileName);
					tempMap.put("filePath", filePath);
					tempMap.put("isExist", isExist);
					System.out.println(isExist);
					ls.add(tempMap);
				}

			}
		}
		System.out.println(count);
		System.out.println(password + "~~~password");
		map.put("area", area);
		map.put("year", year);
		map.put("type", type);
		map.put("remarks", remarks);
		map.put("content", content);
		map.put("author", author);
		map.put("password", password);
		map.put("DocuType", DocuType);
		
		ls.add(map);
		System.out.println(ls);
		return ls;
	}

	public static String fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
		System.out.println("sourceFilePath:" + sourceFilePath);
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		String message = "";
		if (sourceFile.exists() == false) {
			System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
			;
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {

					System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
						;
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						zos.setEncoding("GBK");// 解决被压缩的文件名中文乱码
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							System.out.println(sourceFiles[i]);
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		message = zipFilePath + "\\" + fileName + ".zip";
		return message;
	}

	public static void main(String[] args) throws IOException {
		String no = "E:\\test\\";
		String lol = "E:\\新建文件夹\\";
		String name = "测试";
		System.out.println(fileToZip(no, lol, name));

	}

}
