package com.eoulu.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.eoulu.dao.MailConfigDao;

public class DocumentUploadUtil {
	String[] copyto = null;
	String[] to = null;
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

	public DocumentUploadUtil(HttpServletRequest request, String password) {
		System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
		SEND_USER = (String) request.getSession().getAttribute("email");
		SEND_UNAME = (String) request.getSession().getAttribute("email");
		SEND_PWD = password;
		
		MailConfigDao dao = new MailConfigDao();
		List<Map<String, Object>> config = dao.getConfig("documentUpload");
		String toList = "";
		String copyList = "";
		if(config.size()>1){
			toList = config.get(1).get("ToList").toString();
			copyList = config.get(1).get("CopyList").toString();
		}
		to = toList.split(";");
		copyto = copyList.split(";");
		
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, VALUE_SMTP);
		props.setProperty(KEY_PROPS, VALUE_PROPS);
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
	public boolean doSendHtmlEmail(String subject, String content, String[] fileList) {
		boolean success = true;
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
					bp.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", null));
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
			success = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			success = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
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
