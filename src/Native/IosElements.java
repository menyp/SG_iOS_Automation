package Native;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import jdk.internal.org.xml.sax.SAXException;

public class IosElements {
	
	
	//Buttons
	String BTNloginID;
	String BTNforgotPasswordID;
	String BTNsettingsLoginScreenID;
	String BTNsampleAccountID;
	String BTNweeklyOperationsID;
	String BTNokForErrorPopupID;
	String BTNcancelForgotPasswordID;
	String BTNrecoverPasswordID;
	String BTNresetPasswordID;
	String BTNdoneName;
	String BTNsettingsIconXpth;
	String BTNlogoutName;
	String BTNBackName;
	String BTNClearName;
	String BTNSlicerIconXpth;
	String BTNmapphoneiconID;
	String BTNCancelName;
	String BTNMapCarIconName;
	
	//TextFields	
	String TEXTFIELDemailXpth;
	String TEXTFIELDpasswordXpth;
	String TEXTFIELDrecoveryEmailID;
	
	
	
	
	//STRINGS
	String password;
	String badPassword;
	String scrollDown;
	String scrollUp;
	String User;
	String DashboardName;
	String SalesName;
	String ReturnsName;
	String NetSalesName;
	
	String DailySalesBarID;
	String DailysalesPieID;
	String DestinyUSAID;
	String Last12hoursID;
	String BranchID;
	String DailySalesID;
	String ServiceCallsID;
	String ServiceCallsMapID;
	String MallofAmericaOnMapName;
	

	
	
	//Checkbox
	
	
	//Icons
	
	
	//General Info
	String ConnectionAIRPLANE_MODE;
	String ConnectionWIFI;
	String CoonectionDATA;
	String ConnectionALL;
	
	

	
	public IosElements(String langXml, String xmlPath ) throws ParserConfigurationException, SAXException, IOException, InterruptedException, org.xml.sax.SAXException{
		this.BTNloginID = XmlHandel.readAndroidXml("BTNloginID", langXml, xmlPath);
		this.BTNforgotPasswordID = XmlHandel.readAndroidXml("BTNforgotPasswordID", langXml, xmlPath);
		this.BTNsettingsLoginScreenID = XmlHandel.readAndroidXml("BTNsettingsLoginScreenID", langXml, xmlPath);
		this.BTNsampleAccountID = XmlHandel.readAndroidXml("BTNsampleAccountID", langXml, xmlPath);
		this.BTNcancelForgotPasswordID = XmlHandel.readAndroidXml("BTNcancelForgotPasswordID", langXml, xmlPath);
		this.BTNrecoverPasswordID = XmlHandel.readAndroidXml("BTNrecoverPasswordID", langXml, xmlPath);
		this.BTNresetPasswordID = XmlHandel.readAndroidXml("BTNresetPasswordID", langXml, xmlPath);
		this.BTNdoneName = XmlHandel.readAndroidXml("BTNdoneName", langXml, xmlPath);
		this.BTNsettingsIconXpth = XmlHandel.readAndroidXml("BTNsettingsIconXpth", langXml, xmlPath);
		this.BTNlogoutName = XmlHandel.readAndroidXml("BTNlogoutName", langXml, xmlPath);
		this.BTNBackName = XmlHandel.readAndroidXml("BTNBackName", langXml, xmlPath);
		this.BTNClearName = XmlHandel.readAndroidXml("BTNClearName", langXml, xmlPath);
		this.BTNSlicerIconXpth = XmlHandel.readAndroidXml("BTNSlicerIconXpth", langXml, xmlPath);
		this.BTNmapphoneiconID = XmlHandel.readAndroidXml("BTNmapphoneiconID", langXml, xmlPath);
		this.BTNCancelName = XmlHandel.readAndroidXml("BTNCancelName", langXml, xmlPath);
		this.BTNMapCarIconName = XmlHandel.readAndroidXml("BTNMapCarIconName", langXml, xmlPath);

		
		
		
		this.TEXTFIELDemailXpth = XmlHandel.readAndroidXml("TEXTFIELDemailXpth", langXml, xmlPath);
		this.TEXTFIELDpasswordXpth = XmlHandel.readAndroidXml("TEXTFIELDpasswordXpth", langXml, xmlPath);
		this.TEXTFIELDrecoveryEmailID = XmlHandel.readAndroidXml("TEXTFIELDrecoveryEmailID", langXml, xmlPath);

		
		this.password = XmlHandel.readAndroidXml("password", langXml, xmlPath);
		this.scrollDown = XmlHandel.readAndroidXml("scrollDown", langXml, xmlPath);
		this.scrollUp = XmlHandel.readAndroidXml("scrollUp", langXml, xmlPath);		
		this.ConnectionAIRPLANE_MODE = XmlHandel.readAndroidXml("ConnectionAIRPLANE_MODE", langXml, xmlPath);
		this.ConnectionWIFI = XmlHandel.readAndroidXml("ConnectionWIFI", langXml, xmlPath);
		this.CoonectionDATA = XmlHandel.readAndroidXml("CoonectionDATA", langXml, xmlPath);
		this.ConnectionALL = XmlHandel.readAndroidXml("ConnectionALL", langXml, xmlPath);
		this.User = XmlHandel.readAndroidXml("User", langXml, xmlPath);
		this.DashboardName = XmlHandel.readAndroidXml("DashboardName", langXml, xmlPath);
		this.SalesName = XmlHandel.readAndroidXml("SalesName", langXml, xmlPath);
		this.ReturnsName = XmlHandel.readAndroidXml("ReturnsName", langXml, xmlPath);
		this.NetSalesName = XmlHandel.readAndroidXml("NetSalesName", langXml, xmlPath);	
		this.DailySalesBarID = XmlHandel.readAndroidXml("DailySalesBarID", langXml, xmlPath);		
		this.DailysalesPieID = XmlHandel.readAndroidXml("DailysalesPieID", langXml, xmlPath);		
		this.DestinyUSAID = XmlHandel.readAndroidXml("DestinyUSAID", langXml, xmlPath);		
		this.Last12hoursID = XmlHandel.readAndroidXml("Last12hoursID", langXml, xmlPath);		
		this.BranchID = XmlHandel.readAndroidXml("BranchID", langXml, xmlPath);		
		this.DailySalesID = XmlHandel.readAndroidXml("DailySalesID", langXml, xmlPath);		
		this.ServiceCallsID = XmlHandel.readAndroidXml("ServiceCallsID", langXml, xmlPath);		
		this.ServiceCallsMapID = XmlHandel.readAndroidXml("ServiceCallsMapID", langXml, xmlPath);		
		this.MallofAmericaOnMapName = XmlHandel.readAndroidXml("MallofAmericaOnMapName", langXml, xmlPath);		

		
		
		
		

	}
	
}
