package com.eoulu.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
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

public class FormfactorMailUtil {
	private Lock lock = new ReentrantLock();// 锁对象
	// 设置服务器
	private static String KEY_SMTP = "mail.smtp.host";
	private static String VALUE_SMTP = "smtp.office365.com";
	// 服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	private static String VALUE_PROPS = "true";
	// 发件人用户名、密码
	private String SEND_USER = null;// 昵称
	private String SEND_UNAME = null;
	private String SEND_PWD = null;
	// 建立会话
	private MimeMessage message;
	private Session s;// 邮件会话对象
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	/*
	 * 初始化方法
	 */
	public FormfactorMailUtil(String USER, String UNAME, String PWD) {
		System.setProperty("java.Net.preferIPv4Stack", "true");
		System.setProperty("java.net.preferIPv6Addresses", "true");
		System.getProperties().setProperty("mail.mime.splitlongparameters", "false");

		SEND_USER = USER;
		SEND_UNAME = UNAME;
		SEND_PWD = PWD;
		System.out.println("SEND_USER=="+SEND_USER);
		System.out.println("SEND_PWD=="+SEND_PWD);
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, VALUE_SMTP);
//		props.setProperty("mail.smtp.connectiontimeout", "80000");//connectiontimeout--建立socket连接，socket在这个时间内有效
		props.setProperty("mail.smtp.timeout", "80000");//timeout是socket建立连接后进行的读写超时数
		props.put(KEY_PROPS, VALUE_PROPS);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.socketFactory.fallback", "true");
		// props.put("mail.smtp.auth", "true");
		s = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
			}
		});
		s.setDebug(true);
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
	 *            附件列表 临时文件地址
	 */
	public String sendHtmlEmail(String subject, String content, String[] fileList, String[] to, String[] copyto) {
		lock.lock();
		String result = "发送成功！";
		try {
			// // 发件人
			// InternetAddress from = new InternetAddress(SEND_USER);
			// message.setFrom(from);
			// // 收件人
			// InternetAddress to = new InternetAddress(receiveUser);
			// message.setRecipient(Message.RecipientType.TO, to);
			// // 邮件标题
			// message.setSubject(headName);
			// String content = sendHtml.toString();
			// // 邮件内容,也可以使纯文本"text/plain"
			// message.setContent(content, "text/html;charset=GBK");

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
					FileDataSource fds = new FileDataSource(new File(fileList[i]));
					bp.setDataHandler(new DataHandler(fds));
					bp.setFileName(MimeUtility.encodeText(
							new File(fileList[i]).getName().replaceAll("\r", "").replaceAll("\n", ""), "UTF-8", "B"));
					mp.addBodyPart(bp);
				}
			}
			message.setContent(mp);
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码

			try {
				if (VALUE_PROPS.equals("true")) {
					transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
				}
				// 发送
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (Exception e) {
				
				if (e.toString().contains("MailConnectException")) {
					result = "邮箱服务器连接超时！";
				}
				if (e.toString().contains("AuthenticationFailedException")) {
					result = "邮箱密码验证未通过！";
				}
				System.out.println("出现异常：" + e);
			}

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
		System.out.println(result);
		return result;
	}

	public boolean doSendHtmlEmail(String subject, String content, String[] fileList, String[] to, String[] copyto) {
		lock.lock();
		boolean success = true;
		try {
			// // 发件人
			// InternetAddress from = new InternetAddress(SEND_USER);
			// message.setFrom(from);
			// // 收件人
			// InternetAddress to = new InternetAddress(receiveUser);
			// message.setRecipient(Message.RecipientType.TO, to);
			// // 邮件标题
			// message.setSubject(headName);
			// String content = sendHtml.toString();
			// // 邮件内容,也可以使纯文本"text/plain"
			// message.setContent(content, "text/html;charset=GBK");

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
					FileDataSource fds = new FileDataSource(new File(fileList[i]));
					bp.setDataHandler(new DataHandler(fds));
					bp.setFileName(MimeUtility.encodeText(
							new File(fileList[i]).getName().replaceAll("\r", "").replaceAll("\n", ""), "UTF-8", "B"));
					mp.addBodyPart(bp);
				}
			}
			message.setContent(mp);
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码

			try {
				if (VALUE_PROPS.equals("true")) {
					transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
				}
				// 发送
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (Exception e) {
				success = false;
				System.out.println("出现异常：" + e);
			}

		} catch (AddressException e) {
			e.printStackTrace();
			success = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			success = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			success = false;
		}
		lock.unlock();
		System.out.println(success ? "send success!" : "发送失败");
		return success;
	}
	
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
	
	public static void main(String[] args){
		FormfactorMailUtil util = new FormfactorMailUtil("AChen3@formfactor.com", "AChen3@formfactor.com","w04c0me!");
		util.sendHtmlEmail("test", "test", null, new String[]{"sunmengying@eoulu.com"}, new String[]{"sunmengying@eoulu.com"});
	}

}
