package email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import form.Form;
import form.User;

public class EmailParticipants {
	
	/**
	 * A standard subject header that the user will see upon receiving an email:
	 * <i><p>Task Manager - Your Participation is Requested</p></i>
	 */
	private static final String STANDARD_TEMPLATE_SUBJECT = "Task Manager - Your Participation is Requested";
	
	/**
	 * A standard message header that the user will see upon receiving an email:
	 * 
	 * <i><p>{admin} ({adminEmail}) has created a form using Task Manager, and needs your response! Please
	 * follow the link:</p>
	 * <a href="{get-url}">{url}</a>
	 * <p>and provide your responses as soon as possible.</p>
	 * <p>Thanks,<br>
	 * The Task Manager Team</p></i>
	 * 
	 * The keywords within the message ({admin}, {adminEmail}, and {url}) are replaced with their proper values
	 * before being sent to the user.
	 */
	private static final String STANDARD_TEMPLATE_BODY = 
			new StringBuilder("\\[admin\\] (\\[adminEmail\\]) has created a form using Task Manager, and needs your response!")
				.append("Please follow the link:\n\n\\")
				.append("<a href=\"[getUrl]\">[url\\]</a>\n\n")
				.append("and provide your response(s) as soon as possible.\n\n") 
				.append("Thanks,\nThe Task Manager Team")
				.toString();
	
	/**
	 * Emails all of the participants of a form and asks that they complete the created form.
	 * 
	 * @param formID 
	 * 			the ID of the corresponding form
	 * @param admin
	 * 			the name of the administrator of the form
	 * @param adminEmail 
	 * 			the email of the administrator who created the form
	 * @param participantEmails 
	 * 			a list of all participants' emails
	 * @param subject 
	 * 			the administrator-defined subject of the email message. If null or empty, uses a standard email subject.
	 * @param body 
	 * 			the administrator-defined body of the email message. If null or empty, uses a standard email body.
	 * 
	 * @throws MessagingException
	 * 			if there is an issue in the JavaMail API when sending an email
	 * @throws IllegalArgumentException
	 * 			if any of the given arguments are null or empty, or if the array of participant emails is length 0
	 */
	public static void emailParticipants(Form form) throws MessagingException, IllegalArgumentException {
		// Initial check to make sure that the important arguments have been set
		if (form == null) {
			throw new IllegalArgumentException();
		}
		
		String port = "19500"; //19500
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.xmlers.com");
		props.put("mail.smtp.socketFactory.port", port);//465
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);//465
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("TaskManager","thepassword");
					}
				});
		
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("TaskManager@xmlers.com"));
			for (User u : form.getParticipants()) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
			}
			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse("daliashea@gmail.com"));
			message.setSubject(STANDARD_TEMPLATE_SUBJECT);
			message.setText(STANDARD_TEMPLATE_BODY);
			message.setSentDate(new Date());
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Sends an email to all participants who haven't completed the given formID's questions to remind them to do so.
	 * 
	 * @param formID 
	 * 			the ID of the corresponding form
	 * @param participantEmails
	 * 			a list of all participants' emails
	 * 
	 * @throws MessagingException
	 * 			if there is an issue in the JavaMail API when sending an email
	 * @throws IllegalArgumentException
	 * 			if any of the given arguments are null or empty, or if the array of participant emails is length 0
	 */
	public static void reemailParticipants(Form form) throws MessagingException, IllegalArgumentException {
		if (form == null) {
			throw new IllegalArgumentException();
		}
		
		String port = "19500"; //19500
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.xmlers.com");
		props.put("mail.smtp.socketFactory.port", port);//465
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);//465
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("TaskManager","thepassword");
					}
				});
		
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("TaskManager@xmlers.com"));
			for (User u : form.getParticipants()) {
				boolean responded = false;
				for(User p: form.getRespondedParticipants()){
					if(p.getEmail().equalsIgnoreCase(u.getEmail())){
						responded = true;
					}
				}
				if(!responded){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
				}
			}
			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse("daliashea@gmail.com"));
			message.setSubject(STANDARD_TEMPLATE_SUBJECT);
			message.setText(STANDARD_TEMPLATE_BODY);
			message.setSentDate(new Date());
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Utility method for checking if a given String is either null or empty (length() is 0)
	 * @param str
	 * 			the String to check
	 * @return
	 * 			true if the given String is either null or empty
	 */
	private static boolean isNullOrEmpty(String str) {
		return (null == str || str.isEmpty());
	}
	
	/**
	 * Sets the keywords within the STANDARD_TEMPLATE_BODY String with their proper values
	 * as passed into the {@link #emailParticipants(String, String, String, String[], String, String)} method.
	 * 
	 * @param admin 
	 * 			the name of the administrator of the form
	 * @param adminEmail 
	 * 			the email of the administrator who created the form
	 * @param getUrl 
	 * 			a URL that contains a key-value GET request, where the value is a urlencode of the "view"
	 * 			page with associated form ID. This way, the user can log in and the site redirects them
	 * 			to the corect page.
	 * @param url 
	 * 			a URL that the user sees in the message body
	 * @return 
	 * 			the modified String
	 */
	private static String setMessageValues(String admin, String adminEmail, String getUrl, String url) {
		return STANDARD_TEMPLATE_BODY
				.replaceAll("[admin]", admin)
				.replaceAll("[adminEmail]", adminEmail)
				.replaceAll("[url]", url);
	}
}
