package email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	public static void emailParticipants(String formID, String admin, String adminEmail, String[] participantEmails, String subject, String body) throws MessagingException, IllegalArgumentException {
		// Initial check to make sure that the important arguments have been set
		if (isNullOrEmpty(formID) || isNullOrEmpty(admin) || isNullOrEmpty(adminEmail) || 
				(null == participantEmails || 0 == participantEmails.length)) {
			throw new IllegalArgumentException();
		}
		
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.host", "");
		System.setProperty("mail.smtp.host", "localhost");
		Session session = Session.getDefaultInstance(System.getProperties());
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(adminEmail));
		for (String participantEmail : participantEmails) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(participantEmail));
		}
		
		message.setSubject(isNullOrEmpty(subject) ? STANDARD_TEMPLATE_SUBJECT : subject);
		message.setContent(isNullOrEmpty(body) ? setMessageValues(admin, adminEmail, "", "") : body, "text/html; charset=ISO-8859-1");
		message.setSentDate(new Date());
		
		Transport transport = session.getTransport();
		transport.connect();
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
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
	public static void reemailParticipants(String formID, String admin, String adminEmail, String[] participantEmails) throws MessagingException, IllegalArgumentException {
		if (isNullOrEmpty(formID) || 
				(null == participantEmails || 0 == participantEmails.length)) {
			throw new IllegalArgumentException();
		}
		
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.host", "");
		System.setProperty("mail.smtp.host", "localhost");
		Session session = Session.getDefaultInstance(System.getProperties());
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(adminEmail));
		for (String participantEmail : participantEmails) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(participantEmail));
		}
		
		//message.setSubject(isNullOrEmpty(subject) ? STANDARD_TEMPLATE_SUBJECT : subject);
		//message.setContent(isNullOrEmpty(body) ? setMessageValues(admin, adminEmail, "", "") : body, "text/html; charset=ISO-8859-1");
		message.setSentDate(new Date());
		
		Transport transport = session.getTransport();
		transport.connect();
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
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
