package Native; 
/**
 * TLSGMailer class
 * INFO: This class sends smtp TLS email via the Gmail service on port 587.
 *       The port for smtp SSL email is 465 and isn't supported by this class.
 *       The POP port for SSL Gmail is 995.
 *       Always use full email address as login id.
 *
 * 
 * REQUIRES:  gmail.password file with the following item defined:
 *    mail.smtp.password=********
 * 
 */
 
import java.io.*;
import java.util.Properties;
import javax.mail.Message; 
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public final class TLSGMailer {	
 
	private static Properties gMailConfig = new Properties();
 
	static {
		fetchPassword();
		gMailConfig.put("mail.transport.protocol", "smtp");
		gMailConfig.put("mail.smtp.auth", "true");
		gMailConfig.put("mail.smtp.starttls.enable", "true");
		gMailConfig.put("mail.smtp.port", "587");
		gMailConfig.put("mail.smtp.host", "smtp.gmail.com");
	}
 
	public TLSGMailer()
	{
        System.out.println("Loading TLSGMailer class...");
	}
 
	public void sendTestNGResult(String mFrom, String mTo, String mTitle, String mText, String htmlPayload ) 
	{
		Session sessionTLS = Session.getInstance( gMailConfig ); 
		sessionTLS.setDebug(true);
		 
		MimeMessage messageTLS = new MimeMessage(sessionTLS); 
		try {
			messageTLS.setFrom( new InternetAddress( mFrom ) );
			MimeMultipart multipart = new MimeMultipart();
			messageTLS.setRecipients( Message.RecipientType.TO, InternetAddress.parse( mTo ) );
			MimeBodyPart tmpBp1 = new MimeBodyPart();
			MimeBodyPart tmpBp2 = new MimeBodyPart();
			messageTLS.setSubject( mTitle); 
			tmpBp1.setContent( mText, "text/plain");
			multipart.addBodyPart(tmpBp1);
			tmpBp2.setContent( htmlPayload, "text/html");
			multipart.addBodyPart(tmpBp2);
			messageTLS.setContent(multipart);			
		} catch (MessagingException e) {
			e.printStackTrace();
		} 		
		 
		Transport transportTLS;
		try {
			transportTLS = sessionTLS.getTransport();
			transportTLS.connect( "smtp.gmail.com" , 587, mFrom, gMailConfig.getProperty("mail.smtp.password") );
			transportTLS.sendMessage( messageTLS, messageTLS.getAllRecipients() ); 
			transportTLS.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException ex){
			System.err.println("Cannot send email. " + ex);
		}
		
	}
	
	public void sendRegularEmail(String mFrom, String mTo, String mTitle, String mText ) 
	{
		Session sessionTLS = Session.getInstance( gMailConfig ); 
		sessionTLS.setDebug(true);
		 
		MimeMessage messageTLS = new MimeMessage(sessionTLS); 
		try {
			messageTLS.setFrom( new InternetAddress( mFrom ) );
			MimeMultipart multipart = new MimeMultipart();
			messageTLS.setRecipients( Message.RecipientType.TO, InternetAddress.parse( mTo ) );
			MimeBodyPart tmpBp1 = new MimeBodyPart();
			messageTLS.setSubject( mTitle); 
//			tmpBp1.setContent( mText, "text/plain");
			tmpBp1.setContent( mText, "text/html");
			multipart.addBodyPart(tmpBp1);
			messageTLS.setContent(multipart);			
		} catch (MessagingException e) {
			e.printStackTrace();
		} 		
		 
		Transport transportTLS;
		try {
			transportTLS = sessionTLS.getTransport();
			transportTLS.connect( "smtp.gmail.com" , 587, mFrom, gMailConfig.getProperty("mail.smtp.password") );
			transportTLS.sendMessage( messageTLS, messageTLS.getAllRecipients() ); 
			transportTLS.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException ex){
			System.err.println("Cannot send email. " + ex);
		}
		
	}
 
	private static void fetchPassword() {
		InputStream input = null;
		try {
			//Properties prop =new Properties();
			gMailConfig.load(ClassLoader.getSystemClassLoader().getResourceAsStream("resources/gmail.password"));
			//input = new FileInputStream( "/out/com/resources/gmail.password" );
			//gMailConfig.load(input);
		}
		catch ( IOException ex ){
			System.err.println("Cannot open or load gmail.password file.");
		}
		finally {
			try {
				if ( input != null ) input.close();
			}
			catch ( IOException ex ){
				System.err.println( "Cannot close gmail.password file." );
			}
		}
	}
} 
