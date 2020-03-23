package com.mail;

import java.util.Map;

public class MailSendEntity {
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
	public MailSendEntity() {
		super();
	}
	public MailSendEntity(String senderUserAccount, String senderUserAlias, String senderPassword,
			String mail_smtp_host, String mail_properties, String toUser, String ccUser, String bccUser,
			String encoding, String subject, String contentType, Object content, String files) {
		super();
		this.senderUserAccount = senderUserAccount;
		this.senderUserAlias = senderUserAlias;
		this.senderPassword = senderPassword;
		this.mail_smtp_host = mail_smtp_host;
		this.mail_properties = mail_properties;
		this.toUser = toUser;
		this.ccUser = ccUser;
		this.bccUser = bccUser;
		this.encoding = encoding;
		this.subject = subject;
		this.contentType = contentType;
		this.content = content;
		this.files = files;
	}
	public MailSendEntity(Map<String, String> params) {
		super();
		this.senderUserAccount = params.get("senderUserAccount");
		this.senderUserAlias = params.get("senderUserAlias");
		this.senderPassword = params.get("senderPassword");
		this.mail_smtp_host = params.get("mail_smtp_host");
		this.mail_properties = params.get("mail_properties");
		this.toUser = params.get("toUser");
		this.ccUser = params.get("ccUser");
		this.bccUser = params.get("bccUser");
		this.encoding = params.get("encoding");
		this.subject = params.get("subject");
		this.contentType = params.get("contentType");
		this.content = params.get("content");
		this.files = params.get("files");
	}
	public String getSenderUserAccount() {
		return senderUserAccount;
	}
	public void setSenderUserAccount(String senderUserAccount) {
		this.senderUserAccount = senderUserAccount;
	}
	public String getSenderUserAlias() {
		return senderUserAlias;
	}
	public void setSenderUserAlias(String senderUserAlias) {
		this.senderUserAlias = senderUserAlias;
	}
	public String getSenderPassword() {
		return senderPassword;
	}
	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
	public String getMail_smtp_host() {
		return mail_smtp_host;
	}
	public void setMail_smtp_host(String mail_smtp_host) {
		this.mail_smtp_host = mail_smtp_host;
	}
	public String getMail_properties() {
		return mail_properties;
	}
	public void setMail_properties(String mail_properties) {
		this.mail_properties = mail_properties;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getCcUser() {
		return ccUser;
	}
	public void setCcUser(String ccUser) {
		this.ccUser = ccUser;
	}
	public String getBccUser() {
		return bccUser;
	}
	public void setBccUser(String bccUser) {
		this.bccUser = bccUser;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "MailSendEntity [senderUserAccount=" + senderUserAccount + ", senderUserAlias=" + senderUserAlias
				+ ", senderPassword=" + senderPassword + ", mail_smtp_host=" + mail_smtp_host + ", mail_properties="
				+ mail_properties + ", toUser=" + toUser + ", ccUser=" + ccUser + ", bccUser=" + bccUser + ", encoding="
				+ encoding + ", subject=" + subject + ", contentType=" + contentType + ", content=" + content
				+ ", files=" + files + "]";
	}
}
