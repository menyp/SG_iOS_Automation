package Native;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import jdk.internal.org.xml.sax.SAXException;




public class DroidElements {
	
	
	//Buttons
	String BTN_login_ID;
	String BTN_forgotPassword_ID;
	String BTN_settingsLoginScreen_ID;
	
	//TextFields	
	String TextField_email_ID;
	String TextField_password_ID;
	
	
	
	
	//STRINGS
	String password;
	String badPassword;
	String scrollDown;
	String scrollUp;
	String User;
	
	
	//Checkbox
	String Checkbox_rememberme_ID;
	
	
	//Icons
	
	
	//General Info
	String ConnectionAIRPLANE_MODE;
	String ConnectionWIFI;
	String CoonectionDATA;
	String ConnectionALL;
	
	

	
	public DroidElements(String langXml, String xmlPath ) throws ParserConfigurationException, SAXException, IOException, InterruptedException, org.xml.sax.SAXException{
		this.BTN_login_ID = XmlHandel.readAndroidXml("BTN_login_ID", langXml, xmlPath);
		this.BTN_forgotPassword_ID = XmlHandel.readAndroidXml("BTN_forgotPassword_ID", langXml, xmlPath);
		this.BTN_settingsLoginScreen_ID = XmlHandel.readAndroidXml("BTN_settingsLoginScreen_ID", langXml, xmlPath);

		this.TextField_email_ID = XmlHandel.readAndroidXml("TextField_email_ID", langXml, xmlPath);
		this.TextField_password_ID = XmlHandel.readAndroidXml("TextField_password_ID", langXml, xmlPath);

		this.Checkbox_rememberme_ID = XmlHandel.readAndroidXml("Checkbox_rememberme_ID", langXml, xmlPath);

		this.password = XmlHandel.readAndroidXml("password", langXml, xmlPath);
		this.scrollDown = XmlHandel.readAndroidXml("scrollDown", langXml, xmlPath);
		this.scrollUp = XmlHandel.readAndroidXml("scrollUp", langXml, xmlPath);		
		this.ConnectionAIRPLANE_MODE = XmlHandel.readAndroidXml("ConnectionAIRPLANE_MODE", langXml, xmlPath);
		this.ConnectionWIFI = XmlHandel.readAndroidXml("ConnectionWIFI", langXml, xmlPath);
		this.CoonectionDATA = XmlHandel.readAndroidXml("CoonectionDATA", langXml, xmlPath);
		this.ConnectionALL = XmlHandel.readAndroidXml("ConnectionALL", langXml, xmlPath);
		this.User = XmlHandel.readAndroidXml("User", langXml, xmlPath);


	
		

	}
	
}
