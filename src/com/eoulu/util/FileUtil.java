/**
 * 
 */
package com.eoulu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * @author zhangkai
 *
 *
 * @date 2017/3/25
 */
public class FileUtil {

	
	private String path;
	
	
	
	public FileUtil(String path){
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * �����ļ�
	 * 
	 * @param is  Ҫд�����ݵ���
	 * 
	 * @return boolean   ����ɹ�����true����false
	 * 
	 * */
	public boolean saveFile(InputStream is){
		boolean flag = false;
		FileOutputStream fos = null;
		byte[] bytes = new byte[1024];
		
		try {
			fos = new FileOutputStream(path);
			while(is.read(bytes)>-1){
				fos.write(bytes);
				fos.flush();
			}
			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		return flag;
	}
	
	
	
}
