package com.eoulu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {

	private final static String hostname = "192.168.3.100";// "ftp://192.168.3.100/";
	private final static int port = 21;// 14147;
	private final static String username = "test";
	private final static String password = "123456";

	/**
	 * 
	 * @param basePath
	 *            FTP服务器基础目录
	 * @param filePath
	 *            FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return
	 */
	public static boolean uploadFile(String basePath, String filePath, String filename, InputStream input) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(hostname, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.out.println(11111);
				return result;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				// 如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir))
						continue;
					tempPath +=  dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {// 上传文件到tempPath
						System.out.println("gsgsggsg");
						if (!ftp.makeDirectory(tempPath)) {
							System.out.println(22222);
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			// 设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			 ftp.setControlEncoding("GBK"); //设置编码为GBK
			//在前面设置好路径，缓冲，编码，文件类型后，开始上传文件
			if (!ftp.storeFile(filename, input)) {
				System.out.println(33333);
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
			System.out.println(444);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String pathname = "E:\\";
		String fileName = "D:\\孟迪\\201797.xlsx";

		// // 生成一个文件名
		// // 获取旧的名字
		// String oldName = uploadFile.getOriginalFilename();
		// String newName = IDUtils.genImageName();
		// //新名字
		// newName = newName + oldName.substring(oldName.lastIndexOf("."));
		// //上传的路径
		// String imagePath = new DateTime().toString("/yyyy/mm/dd");
		// //端口号
		// int port = Integer.parseInt(FTP_PORT);
		// System.out.println(FTP_BASEPATH);
		// //调用方法，上传文件
		// boolean result = FtpUtil.uploadFile(FTP_BASEPATH, imagePath,
		// newName, uploadFile.getInputStream());
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			boolean flag = uploadFile("", "\\test", "D:\\孟迪\\201797.xlsx", inputStream);
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param host
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downloadFile(String remotePath, String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(hostname, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
}
