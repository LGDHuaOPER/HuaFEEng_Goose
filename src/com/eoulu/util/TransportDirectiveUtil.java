package com.eoulu.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

public class TransportDirectiveUtil {
	private String[] copyto = null;
	private Lock lock = new ReentrantLock();// 锁对象
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.eoulu.com";
	private static String KEY_PROPS = "mail.smtp.auth";
	private static String VALUE_PROPS = "true";
	private String SEND_USER = null;
	private String SEND_UNAME = null;
	private String SEND_PWD = null;
	private MimeMessage message;
	private Session s;
	private Multipart mp;

	public TransportDirectiveUtil(HttpServletRequest request, String password) {
		System.setProperty("java.Net.preferIPv4Stack", "true");
		System.setProperty("java.net.preferIPv6Addresses", "true");
		System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
		Properties pro2 = new Properties();
		List<String> ls = new ArrayList<>();
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("transport.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
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
		SEND_USER = (String) request.getSession().getAttribute("email");
		SEND_UNAME = (String) request.getSession().getAttribute("email");
		SEND_PWD = password;
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, VALUE_SMTP);
		props.setProperty(KEY_PROPS, VALUE_PROPS);
		props.setProperty("mail.smtp.connectiontimeout", "80000");//connectiontimeout--建立socket连接，socket在这个时间内有效
		props.setProperty("mail.smtp.timeout", "80000");//timeout是socket建立连接后进行的读写超时数
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
		s = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
			}
		});
		message = new MimeMessage(s);
	}

	/**
	 * 
	 * 
	 * @param to
	 *            收件人
	 * @param copyto
	 *            抄送
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param fileList
	 *            附件列表
	 */
	public String doSendHtmlEmail(String[] to, String subject, String content, String[] fileList) {
		lock.lock();
		String result = "发送成功！";
		try {

			mp = new MimeMultipart();

			// 自定义发件人昵称
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText(SEND_USER);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 设置发件人
			// mimeMsg.setFrom(new InternetAddress(from));
			message.setFrom(new InternetAddress(SEND_USER, nick));
			// 设置收件人
			if (to != null && to.length > 0) {
				String toListStr = getMailList(to);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toListStr));
			}
			// 设置抄送人
			if (copyto != null && copyto.length > 0) {
				String ccListStr = getMailList(copyto);
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccListStr));
			}
			// 设置主题
			message.setSubject(subject);
			// 设置正文
			BodyPart bp = new MimeBodyPart();
			bp.setContent(content, "text/html;charset=utf-8");
			mp.addBodyPart(bp);
			// 设置附件
			if (fileList != null && fileList.length > 0) {
				for (int i = 0; i < fileList.length; i++) {
					bp = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(fileList[i]);
					bp.setDataHandler(new DataHandler(fds));
					bp.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
					mp.addBodyPart(bp);
				}
			}
			message.setContent(mp);
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			if (VALUE_PROPS.equals("true")) {
				transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
			}

			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("send success!");
		} catch (AddressException e) {
			e.printStackTrace();
			result = "收件人邮箱格式有误！";
		} catch (MessagingException e) {
			e.printStackTrace();
			result = "服务器解析IP出错！";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = "不支持特殊的字符集！";
		}
		lock.unlock();
		return result;
	}

	/**
	 * 
	 * @param mailArray
	 *            抄送人列表
	 * @return
	 */
	public String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}

			}
		}
		return toList.toString();
	}

	/***
	 * 设置邮件的主题
	 */
	private boolean setMailSub(String mailsubject) {
		try {
			message.setSubject(mailsubject, "GBK");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * 设置邮件体格式
	 */
	private boolean setMailBody(String mailBody) {
		BodyPart bdyPart = new MimeBodyPart();
		try {
			bdyPart.setContent(mailBody, "text/html;charset=GBK");
			mp.addBodyPart(bdyPart);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * 添加邮件附件
	 */

	private boolean addAttach(String filePath) {
		BodyPart bdy = new MimeBodyPart();
		try {
			FileDataSource dataSource = new FileDataSource(filePath);
			bdy.setDataHandler(new DataHandler(dataSource));
			bdy.setFileName(dataSource.getName()); // 设置附件名
			mp.addBodyPart(bdy);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
