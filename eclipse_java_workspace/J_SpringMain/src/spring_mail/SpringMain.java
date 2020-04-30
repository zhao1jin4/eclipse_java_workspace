package spring_mail;

import java.io.File;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
//   spring-framework-reference
public class SpringMain
{
	public static void mailHTML() throws Exception
	{
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");

		MimeMessage message = sender.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("test@host.com");

		// use the true flag to indicate the text included is HTML
		helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

		// let's include the infamous windows Sample file (this time copied to c:/)
		FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
		
		helper.addInline("identifier1234", res);

		sender.send(message);
	}
	public static void attachement() throws Exception
	{
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");

		MimeMessage message = sender.createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("test@host.com");

		helper.setText("Check out this image!");

		// let's attach the infamous windows Sample file (this time copied to c:/)
		FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addAttachment("CoolImage.jpg", file);
		sender.send(message);
		
		//未测试----
		helper.setText("<img src=\"cid:abc\">");//cid:  , 还可写其它的HTML
		helper.addInline("abc", new FileSystemResource(new File("c:/Sample.jpg")));

	}
	public static void main(String[] args) throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring_mail/mail_benas.xml");
		JavaMailSender mailSender = (JavaMailSender) context.getBean("mailSender");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("abcd@163.com");
		mail.setTo("abcd@gmail.com");
		mail.setSubject(" 测试spring Mail");
		mail.setText("hello,java");
		mailSender.send(mail);

		
		
		//----------------------------------------------------------
		MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				mimeMessage.addRecipients(Message.RecipientType.TO,"aa@sina.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("aa@sina.com"));
				mimeMessage.setFrom(new InternetAddress("mail@mycompany.com"));
				mimeMessage.setSubject("标题");
				//mimeMessage.setText("hello");
				MimeMultipart ma=new MimeMultipart("alternative");//Java EE Doc中有的
				//MimeMultipart ma=new MimeMultipart("multipart");
				mimeMessage.setContent(ma);
				
				BodyPart plainText =new MimeBodyPart();
				plainText.setText("plaint text");
				ma.addBodyPart(plainText);
				
				BodyPart html =new MimeBodyPart();
				html.setContent("<html><h1>this is HTML text</h1></html>", "text/html");
				
				ma.addBodyPart(html);
				
			}
		};
		mailSender.send(preparator);
		//----------------------------------------------------------
		
		
		
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.host.com");
		
		Properties prop=new Properties();
		prop.put("mail.smtp.auth", "true");
		sender.setJavaMailProperties(prop);
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("test@host.com");
		helper.setText("Thank you for ordering!");

		sender.send(message);
		
		//---------------------------
		
		
		//---------------------------
	}

}