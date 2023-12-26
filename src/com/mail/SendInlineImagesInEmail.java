package com.mail;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendInlineImagesInEmail {
	final String senderEmailId = "ggadre56@gmail.com";
	final String senderPassword = "sar@ng123";
	final String emailSMTPserver = "smtp.gmail.com";

	public SendInlineImagesInEmail(String receiverEmail, String subject, String messageText, String imagePath) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", "465");

		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);

			Message emailMessage = new MimeMessage(session);
			emailMessage.setFrom(new InternetAddress(senderEmailId));
			emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
			emailMessage.setSubject(subject);

			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(messageText);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(imagePath);
			messageBodyPart2.setDataHandler(new DataHandler(source));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			emailMessage.setContent(multipart);

			Transport.send(emailMessage);

			System.out.println("Email send successfully.");
		} catch (Exception e) {
			System.out.println(e);
			System.err.println("Error in sending email.");
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmailId, senderPassword);
		}
	}

	
}
