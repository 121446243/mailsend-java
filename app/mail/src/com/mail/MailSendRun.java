package com.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */
public class MailSendRun {

	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static List<String> getFileContent(String filePath) throws IOException {
		List<String> list = new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String line = "";
		while ((line=br.readLine())!=null) {
			list.add(line);
		}
		return list;
	}
	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String getFileContentWithTxt(String filePath) throws IOException {
		StringBuffer buff = new StringBuffer();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String line = "";
		while ((line=br.readLine())!=null) {
			buff.append(line+"\n");
		}
		return buff.toString();
	}
	
	/**
	 * 初始化邮件发送实体
	 * @param propPath
	 * @return
	 * @throws Exception
	 */
	public static MailSendEntity initMailSendParamsFromProp(String mailPropPath) throws Exception {
		List<String> list = getFileContent(mailPropPath);
		Map<String, String> mapParams = new HashMap<>();
		for (String line : list) {
			if(line.startsWith("#"))
				continue;
			mapParams.put(line.substring(0,line.indexOf("=")), line.substring(line.indexOf("=")+1));
		}
		return new MailSendEntity(mapParams);
	}
	
	/**
	 * 发送text&html格式的邮件
	 * @param mailPropPath
	 * @param subject
	 * @param textContent
	 * @param files
	 * @throws Exception
	 */
	public static void sendMailWithTextHtml(String mailPropPath, String subject, String textContent, String files) throws Exception {
		// 获取邮件发送信息
		if(mailPropPath==null||mailPropPath.trim().length()==0) {
			String propPath = MailSendRun.class.getResource("").getPath();
			String propName = "mail.send.prop";
			mailPropPath = propPath+File.separator+propName;
		}
		// 初始化邮件发送信息
		MailSendEntity mailSendEntity = initMailSendParamsFromProp(mailPropPath);
		mailSendEntity.setSubject(subject);
		mailSendEntity.setContent(textContent);
		if(files!=null && files.trim().length()>0)
			mailSendEntity.setFiles(files);
		System.out.println("==="+mailSendEntity);
		//发邮件
		MailSendService.sendMail(mailSendEntity);
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		String subject = "我是邮件标题_"+System.currentTimeMillis();
		String textContent = "我是邮件内容";
		textContent = getFileContentWithTxt("E:\\4.html");
		String files = "E:\\2.log;E:\\3.log;E:\\4.html";
		sendMailWithTextHtml(null, subject, textContent, files);
		
	}
	
	
}
