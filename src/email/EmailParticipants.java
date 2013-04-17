package email;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailParticipants {
	
	/**
	 * Emails all of the participants of a form and asks that they complete the created form.
	 * 
	 * @param formID the ID of the corresponding form to retrieve basic information for
	 * @param adminEmail the email of the administrator who created the form
	 * @param participantEmails a list of all participants' emails
	 * @param subject the subject of the email message. If null or empty, uses a standard email subject.
	 * @param body the body of the email message. If null or empty, uses a standard email body.
	 */
	public static void emailParticipants(String formID, String adminEmail, String[] participantEmails, String subject, String body) {
		if (isNullOrEmpty(formID) || 
				isNullOrEmpty(adminEmail) || 
				null == participantEmails || 0 == participantEmails.length) {
			throw new IllegalArgumentException();
		}
		
		System.setProperty("mail.smtp.host", "localhost");
		
		try {
			Session session = Session.getDefaultInstance(System.getProperties());
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminEmail));
			for (String participant : participantEmails) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(participant));
			}
			
			message.setSubject(isNullOrEmpty(subject) ? "" : subject);
			message.setText(isNullOrEmpty(body) ? "" : body);
			message.setSentDate(new Date());
			
			Transport.send(message);
			
		} catch (Exception e) {
			
		}
	}
	
	public static void reemailParticipants(String formID) {
		
	}
	
	private static boolean isNullOrEmpty(String str) {
		return (null == str || str.isEmpty());
	}
}
