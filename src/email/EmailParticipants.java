package email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
	 * 
	 * <pre>
	 * Task Manager - Your Participation is Requested
	 * </pre>
	 */
	private static final String STANDARD_TEMPLATE_SUBJECT = "Task Manager - Your Participation is Requested";

	/**
	 * A standard message header that the user will see upon receiving an email:
	 * 
	 * <pre>
	 * {admin} ({adminEmail}) has created a form using Task Manager, and needs
	 * your response! Please follow the link:
	 * 
	 * &lt;a href="{getURL}"&gt;{URL}&lt;/a&gt;
	 * 
	 * and provide your responses as soon as possible.
	 * 
	 * Thanks,
	 * The Task Manager Team
	 * </pre>
	 * 
	 * The keywords within the message ({admin}, {adminEmail}, {getURL} and
	 * {url}) are replaced with their proper values before being sent to the
	 * user.
	 */
	private static final String STANDARD_TEMPLATE_BODY;
	static {
		STANDARD_TEMPLATE_BODY = new StringBuilder()
				.append("%s (%s) has created a form using Task Manager, and needs your response!")
				.append("Please follow the link:\n\n")
				.append("&lt;a href=\"%s\"&gt;%s&lt;/a&gt;\n\n")
				.append("and provide your response(s) as soon as possible.\n\n")
				.append("Thanks,\nThe Task Manager Team").toString();
	}

	private static final String STANDARD_REEMAIL_SUBJECT = "Task Manager - Your Participation is Requested Again";

	/**
	 * 
	 */
	private static final String STANDARD_REEMAIL_BODY;
	static {
		STANDARD_REEMAIL_BODY = new StringBuilder()
				.append("%s (%s) created a form using Task Manager, and is still waiting for your response!")
				.append("Please follow the link:\n\n")
				.append("&lt;a href=\"%s\"&gt;%s&lt;/a&gt;\n\n")
				.append("and provide your response(s) as soon as possible.\n\n")
				.append("Thanks,\nThe Task Manager Team").toString();
	}

	/**
	 * Emails all of the participants of a form and asks that they complete the
	 * created form.
	 * 
	 * @param formID
	 *            the ID of the corresponding form
	 * @param admin
	 *            the name of the administrator of the form
	 * @param adminEmail
	 *            the email of the administrator who created the form
	 * @param participantEmails
	 *            a list of all participants' emails
	 * @param subject
	 *            the administrator-defined subject of the email message. If
	 *            null or empty, uses a standard email subject.
	 * @param body
	 *            the administrator-defined body of the email message. If null
	 *            or empty, uses a standard email body.
	 * 
	 * @throws MessagingException
	 *             if there is an issue in the JavaMail API when sending an
	 *             email
	 * @throws IllegalArgumentException
	 *             if any of the given arguments are null or empty, or if the
	 *             array of participant emails is length 0
	 */
	public static void emailParticipants(Form form) throws MessagingException,
			IllegalArgumentException {
		// Initial check to make sure that the important arguments have been set
		if (form == null) {
			throw new IllegalArgumentException();
		}

		String hostName = "www.xmlers.com";
		int portNumber = 19600;

		String to = "daliashea@gmail.com";
		String subject = STANDARD_TEMPLATE_SUBJECT;
		String message = setMessageValues(STANDARD_TEMPLATE_BODY, "", "", "",
				"");
		// User u1 = new User("daliashea@gmail.com");
		// User u2 = new User("daliaem66@hotmail.com");
		// ArrayList<User> users = new ArrayList<User>();
		// users.add(u1); users.add(u2);
		for (User u : form.getParticipants()) {
			to = u.getEmail();
			// Source:
			// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java
			try (Socket kkSocket = new Socket(hostName, portNumber);
					PrintWriter out = new PrintWriter(
							kkSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(kkSocket.getInputStream()));) {
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in));
				String fromServer;
				String fromUser;

				while ((fromServer = in.readLine()) != null) {
					//System.out.println("Server: " + fromServer);

					out.println(to);
					out.println(subject);
					out.println(message);
				}
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for the connection to "
						+ hostName);
				System.exit(1);
			}
		}

	}

	/**
	 * Sends an email to all participants who haven't completed the given
	 * formID's questions to remind them to do so.
	 * 
	 * @param formID
	 *            the ID of the corresponding form
	 * @param participantEmails
	 *            a list of all participants' emails
	 * 
	 * @throws MessagingException
	 *             if there is an issue in the JavaMail API when sending an
	 *             email
	 * @throws IllegalArgumentException
	 *             if any of the given arguments are null or empty, or if the
	 *             array of participant emails is length 0
	 */
	public static void reemailParticipants(Form form)
			throws MessagingException, IllegalArgumentException {
		if (form == null) {
			throw new IllegalArgumentException();
		}

		String hostName = "www.xmlers.com";
		int portNumber = 19600;

		String to = "daliashea@gmail.com";
		String subject = STANDARD_TEMPLATE_SUBJECT;
		String message = setMessageValues(STANDARD_TEMPLATE_BODY, "", "", "",
				"");
		for (User u : form.getParticipants()) {
			boolean responded = false;
			for (User p : form.getRespondedParticipants()) {
				if (p.getEmail().equalsIgnoreCase(u.getEmail())) {
					responded = true;
				}
			}
			if (!responded) {
				to = u.getEmail();
				// Source:
				// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java
				try (Socket kkSocket = new Socket(hostName, portNumber);
						PrintWriter out = new PrintWriter(
								kkSocket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(
								new InputStreamReader(kkSocket.getInputStream()));) {
					BufferedReader stdIn = new BufferedReader(
							new InputStreamReader(System.in));
					String fromServer;
					String fromUser;

					while ((fromServer = in.readLine()) != null) {
						System.out.println("Server: " + fromServer);

						out.println(to);
						out.println(subject);
						out.println(message);
					}
				} catch (IOException e) {
					System.err
							.println("Couldn't get I/O for the connection to "
									+ hostName);
					System.exit(1);
				}
			}
		}

	}

	/**
	 * Sets the keywords within the given body String with their proper values
	 * as passed into the {@link #emailParticipants(Form)} method.
	 * 
	 * @param admin
	 *            the name of the administrator of the form
	 * @param adminEmail
	 *            the email of the administrator who created the form
	 * @param getUrl
	 *            a URL that contains a key-value GET request, where the value
	 *            is a urlencode of the "view" page with associated form ID.
	 *            This way, the user can log in and the site redirects them to
	 *            the corect page.
	 * @param url
	 *            a URL that the user sees in the message body
	 * @return the modified String
	 */
	private static String setMessageValues(String body, String admin,
			String adminEmail, String getUrl, String url) {
		return String.format(body, admin, adminEmail, getUrl, url);
	}
}
