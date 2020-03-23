package com.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSendService {
//	// 发送者邮箱
//	private String senderUserAccount = "13155150136@163.com";
//	// 发件人昵称
//	private String senderUserAlias = "陈鸿飞";
//	// 发送者邮箱的密码
//	private String senderPassword = "chen123q";
//	// 使用的邮箱官方网站
//	private String mail_smtp_host = "smtp.163.com";
//	// mail 其他参数
//	private String mail_properties = 
//			  "mail.transport.protocol:smtp"
//			+ ";mail.smtp.port:465"
//			+ ";mail.smtp.ssl.enable:true"
//			+ ";mail.smtp.auth:true"
//			;
//	// 收件人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
//	private String toUser = "chenhf@yusys.com.cn";
//	// 抄送人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
//	private String ccUser = "hecl@yusys.com.cn,308643145@qq.com";
//	// 密送人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
//	private String bccUser = "121446243@qq.com";
//	// 编码
//	private String encoding = "utf-8";
//	// 邮件主题
//	private String subject = "测试主题_"+System.currentTimeMillis();
//	// 邮件正文类型
//	private String contentType = "text/html;charset=" + encoding;
//	// 邮件内容
//	private Object content = "测试，测试"
//			+ "<table border='1'>"
//			+ "<tr><td>第1行第1列</td><td>第1行第2列</td></tr>"
//			+ "<tr><td>第2行第1列</td><td>第2行第2列</td></tr>"
//			+ "</table>";
//	private String files = "E:\\1.log;E:\\2.log";
	// 发送者邮箱
	private String senderUserAccount = null;
	// 发件人昵称
	private String senderUserAlias = null;
	// 发送者邮箱的密码
	private String senderPassword = null;
	// 使用的邮箱官方网站
	private String mail_smtp_host = null;
	// mail 其他参数
	private String mail_properties = 
			  "mail.transport.protocol:smtp"
			+ ";mail.smtp.port:465"
			+ ";mail.smtp.ssl.enable:true"
			+ ";mail.smtp.auth:true"
			;
	// 收件人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
	private String toUser = null;
	// 抄送人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
	private String ccUser = null;
	// 密送人，多个以 ","分隔， 如：121446243@qq.com,hecl@yusys.com.cn
	private String bccUser = null;
	// 编码
	private String encoding = "UTF-8";
	// 邮件主题
	private String subject = null;
	// 邮件正文类型
	private String contentType = "text/html;charset=" + encoding;
	// 邮件正文内容
	private Object content = null;
	// 邮件附件路径列表，多个文件以 “;”分隔
	private String files = null;
	

	public void service() throws MessagingException, UnsupportedEncodingException {
		// 1.创建参数设置,用于连接邮件服务器
		Properties props = new Properties();
		if(mail_properties!=null && mail_properties.trim()!=null) {
			try {
				String[] mail_properties_arr = mail_properties.split(";");
				for (String str : mail_properties_arr) {
					try {
						props.setProperty(str.split(":")[0], str.split(":")[1]);
					} catch(Exception e) {System.err.print("mail_properties:"+str+" parse error, igore");}
				}
			} catch (Exception e) {}
		}
		props.setProperty("mail.smtp.host", mail_smtp_host);
		
		// 2.根据配置创建会话对象
		Session session = Session.getInstance(props);
		session.setDebug(true);
		// 3.创建一封信
		MimeMessage message = createMessage(session);
		// 4.根据Session 获取邮件传送对象
		Transport transport = session.getTransport();
		// 5.获取连接
		transport.connect(senderUserAccount, senderPassword);
		// 6.发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		// 7.关闭连接
		transport.close();
	}

	private MimeMessage createMessage(Session session) throws UnsupportedEncodingException, MessagingException {
		// 1、创建一封邮件
		MimeMessage message = new MimeMessage(session);
		// 2、From:发件人
		message.setFrom(new InternetAddress(senderUserAccount, senderUserAlias, encoding));
		if(senderUserAlias==null||senderUserAlias.trim().equals(""))
			senderUserAlias = senderUserAccount.substring(0,senderUserAccount.indexOf("@"));
		// 3、To:收件人
//		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverMailAccount, "1", encoding));
		setRecipients(toUser, message, encoding, MimeMessage.RecipientType.TO);
		// 3、cc:抄送人
		setRecipients(ccUser, message, encoding, MimeMessage.RecipientType.CC);
		// 3、bcc:密送人
		setRecipients(bccUser, message, encoding, MimeMessage.RecipientType.BCC);
		// 4、Subject:邮件主题
		message.setSubject(subject, encoding);
		
		// 5.1、Content:邮件正文
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, contentType);
		multipart.addBodyPart(messageBodyPart);
		// 5.2 File: 增加附件
        addTach(files, multipart, encoding);
		
		message.setContent(multipart);
		
		// 6、设置发件时间
		message.setSentDate(new Date());
		// 7、保存设置
		message.saveChanges();
		return message;
	}
	
	/**
	 * 设置收件人地址信息
	 * @param user	收件人，多个以 "," 分隔，如: 123@qq.com,456@qq.com
	 * @param message	
	 * @param encoding	编码
	 * @param recipientType	收件人类型  TO-收件;CC-抄送;BCC-密送
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private void setRecipients(String user, Message message, String encoding, RecipientType recipientType) throws MessagingException, UnsupportedEncodingException {
		if (user != null && !user.trim().equals("")) {
			String[] toUserArr = user.split(",");
			InternetAddress[] receiverMailAccountsAddr = new InternetAddress[toUserArr.length];
			for(int i=0; i<receiverMailAccountsAddr.length; i++) {
				receiverMailAccountsAddr[i] = new InternetAddress(toUserArr[i], toUserArr[i].substring(0,toUserArr[i].indexOf("@")), encoding);
			}
			message.setRecipients(recipientType, receiverMailAccountsAddr);
		}
	}
	
	 /**
	  * 添加多个附件
	  * @param fileList	文件列表，以“;”分隔，输入文件绝对路径
	  * @param multipart
	  * @throws MessagingException
	  * @throws UnsupportedEncodingException
	  */
    private void addTach(String files, Multipart multipart, String encoding)
            throws MessagingException, UnsupportedEncodingException {
    	if(files==null || files.trim().length()==0) {
    		return ;
    	}
    	String[] fileList = files.split(";");
        for (int index = 0; index < fileList.length; index++) {
        	if(new File(fileList[index]).exists() && new File(fileList[index]).isFile()) {
	            MimeBodyPart mailArchieve = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(fileList[index]);
	            mailArchieve.setDataHandler(new DataHandler(fds));
	//            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "GBK", "B"));
	            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), encoding, encoding));
	            multipart.addBodyPart(mailArchieve);
        	} else {
        		System.err.println("MailSendService.addTach:file["+fileList[index]+"] is directory or not exists");
        	}
        }
    }
    
    public static void sendMail(MailSendEntity mailInfo) throws UnsupportedEncodingException, MessagingException {
    	MailSendService mss = new MailSendService();
    	mss.senderUserAccount = mailInfo.getSenderUserAccount();
    	mss.senderUserAlias = mailInfo.getSenderUserAlias();
    	mss.senderPassword = mailInfo.getSenderPassword();
    	mss.mail_smtp_host = mailInfo.getMail_smtp_host();
    	mss.mail_properties = mailInfo.getMail_properties();
    	mss.toUser = mailInfo.getToUser();
    	mss.ccUser = mailInfo.getCcUser();
    	mss.bccUser = mailInfo.getBccUser();
    	mss.encoding = mailInfo.getEncoding();
    	mss.subject = mailInfo.getSubject();
    	mss.contentType = mailInfo.getContentType();
    	mss.content = mailInfo.getContent();
    	mss.files = mailInfo.getFiles();
    	
    	//发送邮件
    	mss.service();
    }
}











