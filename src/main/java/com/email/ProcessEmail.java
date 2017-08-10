package com.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import com.entity.Household;
import com.helper.DataEncryption;



public class ProcessEmail {
	final private String configFile = "email.properties";
	final private String from = "customer_service@peachyscoffee.com";
	final private String password = "In_heaven3!";

	@Value("${app.files.configDir}")
	private String configPath;
	
		
	public void sendMail(final Email email) throws Exception {
		Properties properties = new Properties();
		
		URL url = new URL(configPath + configFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
        try {
            properties.load(in);
        } catch (SecurityException | IOException | IllegalArgumentException e) {
            throw e;
        } finally {
            try { in.close(); }
            catch (IOException ie) { throw ie; }
        }

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email.getFrom(),
						email.getPassword());
			}
		};

		Session session = Session.getInstance(properties, auth);
		MimeMessage msg = new MimeMessage(session);
			
		msg.setSubject(email.getSubject());
		msg.setContent(email.getMessage(), "text/html");

		msg.setFrom(new InternetAddress(email.getFrom(), email.getName()));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));

		Transport.send(msg);

	}
	
	public void sendLoginLink(Household user, String baseUrl) throws Exception {
		Email email = new Email();
		String encodedId = DataEncryption.encode(String.valueOf(user.getHousehold_id()));
				
		email.setName("Peachy's Coffee");
		email.setFrom(from);
		email.setPassword(password);
		email.setSubject("Welcome to Peachy's Coffee");
		String msg = 	 "<h1>Welcome " + user.getName()+ "</h1>" +
		             	 "<h3>Please click the link to activate your account</h3>" +
		             	 "<a href='" + baseUrl + encodedId + "&prKey=" + user.getPassword() + "'>" +
		             	 "Activate your accout</a>";

		email.setTo(user.getUsername());
		email.setMessage(msg);
		
		sendMail(email);
		
	}

	public void sendPasswordRecovery(Household user, String baseUrl, String token) throws Exception {
		
		Email email = new Email();
		String encodedId = DataEncryption.encode(String.valueOf(user.getHousehold_id()));
				
		email.setName("Peachy's Coffee");
		email.setFrom(from);
		email.setPassword(password);
		email.setSubject("Password Recovery");
		String msg = 	 "<h1>Click the link to reset your password</h1>" +
		             	 "<a href='" + baseUrl + encodedId + "&prKey=" + token + "'>" +
		             	 "Reset Password</a>";

		email.setTo(user.getUsername());
		email.setMessage(msg);
		
		sendMail(email);
		
	}
	
		
		
	

}
