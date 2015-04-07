package Native;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import jdk.internal.org.xml.sax.SAXException;




public class DroidElements {
	
	
	//Buttons
	String BTNloginID;
	String BTNforgotPasswordID;
	String BTNsettingsLoginScreenID;
	String BTNsampleAccountID;
	String BTNweeklyOperationsID;
	String BTNlogoutName;
	String BTNokForErrorPopupID;
	String BTNcancelForgotPasswordID;
	String BTNrecoverPasswordID;
	String BTNresetPasswordID;
	
	//TextFields	
	String TEXTFIELDemailID;
	String TEXTFIELDpasswordID;
	String TEXTFIELDrecoveryEmailID;
	
	
	
	
	//STRINGS
	String password;
	String badPassword;
	String scrollDown;
	String scrollUp;
	String User;
	String InvalidRecoverEmailName;
	String ConfCodeIncorrectName;
	
	
	//Checkbox
	String Checkbox_rememberme_ID;
	
	
	//Icons
	
	
	//General Info
	String ConnectionAIRPLANE_MODE;
	String ConnectionWIFI;
	String CoonectionDATA;
	String ConnectionALL;
	
	

	
	public DroidElements(String langXml, String xmlPath ) throws ParserConfigurationException, SAXException, IOException, InterruptedException, org.xml.sax.SAXException{
		this.BTNloginID = XmlHandel.readAndroidXml("BTNloginID", langXml, xmlPath);
		this.BTNforgotPasswordID = XmlHandel.readAndroidXml("BTNforgotPasswordID", langXml, xmlPath);
		this.BTNsettingsLoginScreenID = XmlHandel.readAndroidXml("BTNsettingsLoginScreenID", langXml, xmlPath);
		this.BTNsampleAccountID = XmlHandel.readAndroidXml("BTNsampleAccountID", langXml, xmlPath);
		this.BTNweeklyOperationsID = XmlHandel.readAndroidXml("BTNweeklyOperationsID", langXml, xmlPath);
		this.BTNlogoutName = XmlHandel.readAndroidXml("BTNlogoutName", langXml, xmlPath);
		this.BTNokForErrorPopupID = XmlHandel.readAndroidXml("BTNokForErrorPopupID", langXml, xmlPath);
		this.BTNcancelForgotPasswordID = XmlHandel.readAndroidXml("BTNcancelForgotPasswordID", langXml, xmlPath);
		this.BTNrecoverPasswordID = XmlHandel.readAndroidXml("BTNrecoverPasswordID", langXml, xmlPath);
		this.BTNresetPasswordID = XmlHandel.readAndroidXml("BTNresetPasswordID", langXml, xmlPath);



		

		this.TEXTFIELDemailID = XmlHandel.readAndroidXml("TEXTFIELDemailID", langXml, xmlPath);
		this.TEXTFIELDpasswordID = XmlHandel.readAndroidXml("TEXTFIELDpasswordID", langXml, xmlPath);
		this.TEXTFIELDrecoveryEmailID = XmlHandel.readAndroidXml("TEXTFIELDrecoveryEmailID", langXml, xmlPath);


		
		this.Checkbox_rememberme_ID = XmlHandel.readAndroidXml("Checkbox_rememberme_ID", langXml, xmlPath);

		
		this.password = XmlHandel.readAndroidXml("password", langXml, xmlPath);
		this.scrollDown = XmlHandel.readAndroidXml("scrollDown", langXml, xmlPath);
		this.scrollUp = XmlHandel.readAndroidXml("scrollUp", langXml, xmlPath);		
		this.ConnectionAIRPLANE_MODE = XmlHandel.readAndroidXml("ConnectionAIRPLANE_MODE", langXml, xmlPath);
		this.ConnectionWIFI = XmlHandel.readAndroidXml("ConnectionWIFI", langXml, xmlPath);
		this.CoonectionDATA = XmlHandel.readAndroidXml("CoonectionDATA", langXml, xmlPath);
		this.ConnectionALL = XmlHandel.readAndroidXml("ConnectionALL", langXml, xmlPath);
		this.User = XmlHandel.readAndroidXml("User", langXml, xmlPath);
		this.InvalidRecoverEmailName = XmlHandel.readAndroidXml("InvalidRecoverEmailName", langXml, xmlPath);
		this.ConfCodeIncorrectName = XmlHandel.readAndroidXml("ConfCodeIncorrectName", langXml, xmlPath);




	
		

	}
	
}
