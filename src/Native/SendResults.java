package Native;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
 
public class SendResults {
	
	public String mailFrom, mailTo, mailTitle, mailText, htmlPayLoad;	
	
	public SendResults(String mf, String mt, String mtitle, String mtext) {
		mailFrom = mf;
		mailTo = mt;
		mailTitle = mtitle;
		mailText = mtext;
		System.out.println("The SendResults class is loaded.");
		System.out.println("Registered email target: " + mailTo);
		System.out.println("Registered sender: " + mailFrom);
	}
	
	private void getTestNGResult() throws IOException {
		FileInputStream stream = null;
		try { // testNG stores results in the ./test-output directory by default
			stream = new FileInputStream(new File("test-output/emailable-report.html"));
//			stream = new FileInputStream(new File("/Users/qa/Desktop/Appium/runnable_Jars/test-output/emailable-report.html"));											 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		try { 
			FileChannel fc = stream.getChannel(); 
			MappedByteBuffer bb = fc.map( FileChannel.MapMode.READ_ONLY, 0, fc.size() ); 
			/* Instead of using default, pass in a decoder. */ 
			htmlPayLoad = Charset.defaultCharset().decode(bb).toString(); 
		} finally { 
			stream.close(); 
		}
	}
	
	public void sendTestNGResult() throws IOException {
		getTestNGResult();
		TLSGMailer gmailer = new TLSGMailer();
		gmailer.sendTestNGResult(mailFrom, mailTo, mailTitle, mailText, htmlPayLoad );
	}
	
	public void sendRegularEmail() throws IOException {
		getTestNGResult();
		TLSGMailer gmailer = new TLSGMailer();
		gmailer.sendRegularEmail(mailFrom, mailTo, mailTitle, htmlPayLoad );
	}
	
	
}

