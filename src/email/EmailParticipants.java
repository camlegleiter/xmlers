package email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.mail.MessagingException;

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
	 * A standard subject header that the owner will see when participants fill out form:
	 * 
	 * <pre>
	 * Task Manager - Your Participation is Requested
	 * </pre>
	 */
	private static final String STANDARD_RESPONSE_SUBJECT = "Task Manager - A User Has Submitted a Response";

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
				.append("%s has created the form \"%s\" using Task Manager, and needs your response!\n\n")
				.append("Please follow the link:\n\n")
				.append("%s\n\n")
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
				.append("%s created the form \"%s\" using Task Manager, and is still waiting for your response!\n\n")
				.append("Please follow the link:\n\n")
				.append("%s\n\n")
				.append("and provide your response(s) as soon as possible.\n\n")
				.append("Thanks,\nThe Task Manager Team").toString();
	}
	private static final String STANDARD_RESPONSE_BODY;
	static {
		STANDARD_RESPONSE_BODY = new StringBuilder()
				.append("%s has submitted a response to the form \"%s\".\n\n")
				.append("Please follow the link:\n\n")
				.append("%s\n\n")
				.append("to view your form records.\n\n")
				.append("Thanks,\nThe Task Manager Team").toString();
	}

	/**
	 * Emails all of the participants of a form and asks that they complete the
	 * created form.
	 * 
	 * @param formID
	 *            the ID of the corresponding form
	 * @param appUrl
	 *            the base URL for the website, retrieved from
	 *            HttpServletRequest.getContextPath()
	 * 
	 * @throws MessagingException
	 *             if there is an issue in the JavaMail API when sending an
	 *             email
	 * @throws IllegalArgumentException
	 *             if any of the given arguments are null or empty, or if the
	 *             array of participant emails is length 0
	 */
	public static void emailParticipants(Form form, String appUrl, User owner)
			throws MessagingException, IllegalArgumentException {
		// Initial check to make sure that the important arguments have been set
		if (form == null) {
			throw new IllegalArgumentException();
		}
		String to = "";
		String subject = STANDARD_TEMPLATE_SUBJECT;

		String[] message = setMessageValues(STANDARD_TEMPLATE_BODY,
				owner.getEmail(), form.getTitle(), appUrl);

		for (User u : form.getParticipants()) {
			to = u.getEmail();
			System.out.println(to);
			connectAndSend(to, subject, message);
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
	public static void reemailParticipants(Form form, String appUrl, User owner)
			throws MessagingException, IllegalArgumentException {
		if (form == null) {
			throw new IllegalArgumentException();
		}

		String to = "";
		String subject = STANDARD_REEMAIL_SUBJECT;
		String[] message = setMessageValues(STANDARD_REEMAIL_BODY,
				owner.getEmail(), form.getTitle(), appUrl + "/login.jsp");
		for (User u : form.getParticipants()) {
			boolean responded = false;
			for (User p : form.getRespondedParticipants()) {
				if (p.getEmail().equalsIgnoreCase(u.getEmail())) {
					responded = true;
				}
			}
			if (!responded) {
				to = u.getEmail();
				connectAndSend(to, subject, message);
			}
		}

	}

	/**
	 * Sets the keywords within the given body String with their proper values
	 * as passed into the {@link #emailParticipants(Form)} method.
	 * 
	 * @param body
	 *            the message body to be updated with the other parameters
	 * @param admin
	 *            the name of the administrator of the form
	 * @param adminEmail
	 *            the email of the administrator who created the form
	 * @param url
	 *            a URL that the user sees in the message body
	 * @return the modified String
	 */
	private static String[] setMessageValues(String body, String adminEmail,
			String formName, String url) {
		String formatted = String.format(body, adminEmail, formName, url);
		return formatted.split("\\n");
	}

	public static void emailOwner(Form form, String appUrl, User participant, User owner) {
		// Initial check to make sure that the important arguments have been set
		if (form == null) {
			throw new IllegalArgumentException();
		}

		String to = "";
		String subject = STANDARD_RESPONSE_SUBJECT;

		String[] message = setMessageValues(STANDARD_RESPONSE_BODY,
				participant.getEmail(), form.getTitle(), appUrl);

		to = owner.getEmail();
		connectAndSend(to, subject, message);
	}

	private static void connectAndSend(String to, String subject,
			String[] message) {
		String hostName = "www.xmlers.com";
		int portNumber = 19600;
		// Source:
		// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java
		try (Socket kkSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String fromServer;
			String fromUser;

			while ((fromServer = in.readLine()) != null) {
				// System.out.println("Server: " + fromServer);
				out.println(to);
				out.println("SUBJECT");
				out.println(subject);
				out.println("MESSAGE");
				for (int i = 0; i < message.length; i++){
					out.println(message[i]);
				}
				out.println("END");
			}
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}
}
