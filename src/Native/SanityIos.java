package Native;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.xml.sax.SAXException;

import com.applitools.eyes.Eyes;


//MobileElement e2; //test will wait for this diring 20 seconds

  public class SanityIos {
	  
	@WithTimeout(time = 30, unit = TimeUnit.SECONDS)
	@iOSFindBy (id = "relevant id need to be added here")

	
	String currentDateFolder;
	String webElementXmlLang;
	String webElementXmlPath;
	String StartServerPath;
	String StopServerPath;
	IosElements iosData;
	IOSDriver<MobileElement> driver;
	IosMethods genMeth = new IosMethods();
	Eyes eyes = new Eyes();
	Boolean useEye = true;
	
	/*
	public SanityIos() {
		// TODO Auto-generated constructor stub
	}
	*/

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) throws ParserConfigurationException, SAXException, IOException, InterruptedException, jdk.internal.org.xml.sax.SAXException {
		
        // This is your api key, make sure you use it in all your tests.
		
		//Set the tests configuration
		StartServerPath = genMeth.getValueFromPropFile("StartServerPath");
		StopServerPath = genMeth.getValueFromPropFile("StopServerPath");
		webElementXmlPath = genMeth.getValueFromPropFile("webElementXmlPath");
		webElementXmlLang = genMeth.getValueFromPropFile("webElementXmlLang");
		
		iosData= new IosElements(webElementXmlLang, webElementXmlPath);
		driver = genMeth.setCapabilitiesIos(genMeth);
		genMeth.cleanLoginIos(genMeth,iosData, iosData.User); 
	
		
	
		
	}

	@BeforeMethod (alwaysRun = true)
	public void checkHomeScreen() throws InterruptedException, IOException, ParserConfigurationException, SAXException{

//		genMeth.setWifiOn();

		// Check if the client still logged in & in StartUp screen before each test
		if (driver == null) {
			try {
				driver.removeApp(genMeth.getValueFromPropFile("appPackage"));
				driver.quit();
			} catch (Exception e) {
				// swallow if fails
			}
			driver = genMeth.setCapabilitiesIos(genMeth);
			genMeth.cleanLoginIos( genMeth,iosData, iosData.User );
		}

		else {
			boolean StartUpScreenDisplay = genMeth.checkIsElementVisible( By.id("Applications"));

			if (StartUpScreenDisplay != true) {

				try {
					driver.removeApp(genMeth.getValueFromPropFile("appPackage"));
					driver.quit();
				} catch (Exception e) {
					// swallow if fails
				}

				driver = genMeth.setCapabilitiesIos(genMeth);
				genMeth.cleanLoginIos( genMeth, iosData, iosData.User);

			}

		}

	}
	
	@AfterMethod(enabled = false, dependsOnMethods = { "connectionLost" })
	
	public void enabledWifi() {

		//genMeth.setWifiOn();
	}
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "Test the login via the sample button" ,
			groups= {"Sanity IOS"}  /*dependsOnMethods={"testLogin"}*/)	

	public void loginSample() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		//Logout from startup page
		genMeth.signOutFromStartup(genMeth, iosData);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
			
		// Login to sample app & open Dashboard report
		genMeth.eyesCheckWindow(eyes, "SampleApp Main screen", useEye);
		genMeth.clickName(genMeth,  iosData.DashboardName);
		genMeth.eyesCheckWindow(eyes, "Dashboard Tab", useEye);
		genMeth.swipeRightIphone6Plus(1000);
		genMeth.eyesCheckWindow(eyes, "World wide orders Tab", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth,  iosData.DashboardName);

		// Open Sales Bar
		genMeth.clickId(genMeth, iosData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- Show All", useEye);
		genMeth.clickId(genMeth, iosData.ReturnsName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- show Sales/Net Sales", useEye);
		genMeth.clickId(genMeth, iosData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp show Net Sales", useEye);
		genMeth.clickId(genMeth, iosData.NetSalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales - show Empty", useEye);
		
		genMeth.clickId(genMeth, iosData.SalesName);
		genMeth.clickId(genMeth, iosData.ReturnsName);
		genMeth.clickId(genMeth, iosData.NetSalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- Show All", useEye);
		
		//Open Sales Pie
		genMeth.clickId(genMeth, iosData.DailySalesBarID);
		genMeth.clickId(genMeth, iosData.DailysalesPieID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Net Sales", useEye);
		genMeth.clickId(genMeth, iosData.DestinyUSAID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Net Sales - Destiny USA", useEye);

		genMeth.clickId(genMeth, iosData.ReturnsName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Returns", useEye);
		
		genMeth.clickId(genMeth, iosData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Sales", useEye);
		genMeth.clickId(genMeth, iosData.DailysalesPieID);
		genMeth.clickId(genMeth, iosData.Last12hoursID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye);
		
		// Check slicer in Sparklines
		genMeth.clickXpth(driver, genMeth, iosData.BTNSlicerIconXpth);
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickId(genMeth, iosData.DestinyUSAID);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines / Destiny USA", useEye);
		
		//Clear the Slicer
		genMeth.clickXpth(driver, genMeth, iosData.BTNSlicerIconXpth);
		genMeth.clickName(genMeth, iosData.BTNClearName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
//Open Daily Sales from main screen
		genMeth.clickId(genMeth, iosData.DailySalesID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar (no back icon)- Show All", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
//OPEN SERVICE CALLS
		genMeth.clickId(genMeth, iosData.ServiceCallsID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls", useEye);
		
// InGrid Action- First layer
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "1");
		Thread.sleep(8000);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "3");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 3", useEye);	
		//Open the Slicer
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]");
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickId(genMeth, iosData.MallOfAmerica_Id);
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls - See All (Mall of America", useEye);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "1");
		Thread.sleep(10000);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "4");
		Thread.sleep(6000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 4", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.BTNBackName);

		//Open service calls map
		genMeth.clickId(genMeth, iosData.ServiceCallsMapID);
		genMeth.clickXpth(driver, genMeth, iosData.MallofAmericaOnMapXpath);
		
		//Check is Location popup is displayed
		genMeth.clickId(genMeth, iosData.BTNmapphoneiconID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Phone Icon Option", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		genMeth.clickName(genMeth, iosData.BTNMapCarIconName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Car Icon Option", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAButton[5]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls:5", useEye);
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]");
		genMeth.clickXpth(driver, genMeth, iosData.MallofAmericaOnMapXpath);
		genMeth.clickName(genMeth, iosData.BTNBackName);

		//Create new service call
		genMeth.clickId(genMeth, iosData.BTNnewServiceCallId);
		genMeth.eyesCheckWindow(eyes, "New Service Call", useEye);
		
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.eyesCheckWindow(eyes, "Branch simple list", useEye);
		genMeth.clickId(genMeth, iosData.MallOfAmerica_Id);
		
		genMeth.clickId(genMeth, "Assigned To");
		genMeth.clickId(genMeth, "Jessica Blue");
		
		genMeth.clickId(genMeth, "Category");
		genMeth.clickId(genMeth, "Computer");
		
		genMeth.clickId(genMeth, "Item");
		genMeth.clickId(genMeth, "Memory card");
		
		genMeth.clickId(genMeth, "Description");
		genMeth.setEnglishKeyboard(genMeth);
		genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "Meny The Best");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		genMeth.clickId(genMeth, iosData.BTNpriority_Name);
		genMeth.clickId(genMeth, "1");
		
		genMeth.eyesCheckWindow(eyes, "New service call with parameters", useEye);
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		
		genMeth.eyesCheckWindow(eyes, "New service call Actions collections +", useEye);
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAStaticText[1]");
		genMeth.eyesCheckWindow(eyes, "New Service Call", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNBackName);

		//Order lookup	
		genMeth.clickId(genMeth, iosData.OrderLookup_ID);
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Order Lookup parameters", useEye);
		genMeth.clickName(genMeth, "Start Date");
		MobileElement UIAPickerWheel = driver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAPicker[1]/UIAPickerWheel[1]");
		UIAPickerWheel.sendKeys("July");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.clickName(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "List of Orders", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		//Operations
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[7]/UIAStaticText[2]");
		genMeth.eyesCheckWindow(eyes, "Inventory", useEye);
		//Open grid second layer
		genMeth.clickName(genMeth, iosData.MallOfAmerica_Id);
		genMeth.eyesCheckWindow(eyes, "Inventory second layer", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone6Plus(1000);
		
		genMeth.eyesCheckWindow(eyes, "Orders", useEye);
		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone6Plus(1000);
		genMeth.eyesCheckWindow(eyes, "Place New Order", useEye);
		
//Open the place new order
		MobileElement El = driver.findElementByXPath(iosData.BTNplaceNewOrder_Xpth);
		El.click();
		
		genMeth.eyesCheckWindow(eyes, "Place new order parameters", useEye);
		genMeth.clickName(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "Place new order parameters missing", useEye);
		genMeth.clickName(genMeth, iosData.BTNokName);
		//Fill the parameters
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickName(genMeth, iosData.MallOfAmerica_Id);
		genMeth.clickName(genMeth, "ProductID");
		genMeth.accessToCameraHandle(genMeth);
		genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		Thread.sleep(2000);
		genMeth.clickName(genMeth, "Quantity");
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "Place new order All parameters", useEye);
		genMeth.clickName(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "Place New Order", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
// Technicians
		genMeth.clickName(genMeth, "Technicians");
		genMeth.eyesCheckWindow(eyes, "Technicians", useEye);
		
// 	Phone Icon
		genMeth.clickName(genMeth, "Phone");
		genMeth.eyesCheckWindow(eyes, "Technicians- Phone", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, "Phone");
// Add to contacts
		genMeth.clickName(genMeth, iosData.BTNaddContact_Name);
		genMeth.accessToContactsHandle(genMeth);
		genMeth.eyesCheckWindow(eyes, "Technicians- Added by SkyGiraffe screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
// Mail Icon
		genMeth.clickName(genMeth, "Email");
		genMeth.eyesCheckWindow(eyes, "Technicians- New Message screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);

// Map Icon
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "Technicians- Address screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
// Swipe along the technicians Cover Flow

		genMeth.swipeRightIphone6Plus(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians- cover flow John Grant", useEye);
				
		Thread.sleep(1000);



	}

	
	@Test (enabled = false , retryAnalyzer = Retry.class, testName="Sanity Tests", description = "Test the Upload utility with Android" ,
			groups= {"Sanity IOS1"})	
	public void uploadImage() throws ParserConfigurationException, SAXException, IOException, InterruptedException{
			}
	
	
	@Test(enabled = true, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Test TOUR for New accounts and for upgrade accounts",
			groups = { "Sanity IOS1" })
	public void tour() throws Exception, Throwable {

			}
	
	@Test(enabled = true, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Sign up- Create new user (Negetive positive test), Privacy Policy, TRUSTe",
			groups = { "Sanity IOS1" })
	public void createNewUser() throws Exception, Throwable {
			
	}
	
	@Test (enabled = true, retryAnalyzer = Retry.class, testName = "Terms of service & TrusTe", description = "Test the TOS & TrusTe Links" ,
			groups = {"Sanity IOS1"})
	public void termsOfServiceAndTruste () throws InterruptedException, IOException, ParserConfigurationException, SAXException{
		
				
	}
	
	@Test(enabled = true, groups = { "Sanity IOS1" }, testName = "Sanity Tests", description = "login with bad/missing credentials", retryAnalyzer = Retry.class)
	public void badCredentials() throws Exception, Throwable {

		genMeth.signOutFromStartup(genMeth, iosData);
		// Login with bad user name
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, "bad name");
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, iosData.password);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		
		genMeth.clickId(genMeth, iosData.BTNokName);

		// Login with bad password 
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, iosData.User);
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, "bad password");
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		
		// Login with bad user name & password 
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, "bad name");
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, "bad password");
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		
		// Login with empty Name
		genMeth.clearId(genMeth, iosData.TEXTFIELDemailXpth);
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, iosData.password);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		// Login with empty Password
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, iosData.User);
		genMeth.clearId(genMeth, iosData.TEXTFIELDpasswordXpth);
		genMeth.clickId(genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		// Login with empty Name & password
		genMeth.clearId(genMeth, iosData.TEXTFIELDemailXpth);
		genMeth.clearId(genMeth, iosData.TEXTFIELDpasswordXpth);
		genMeth.clickId(genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		// Forgot your password Negative (attempt to restore password with a non
		// existing email)

		// Forgot your password Positive (attempt to restore password with an
		// existing email)
	
	}
	
	@Test(enabled = true, groups = { "Sanity IOS1" } , testName = "Sanity Tests", description = "Settings: create & restore a snapshot" )
	public void forgotYourPassword() throws Exception, Throwable {
		
		genMeth.signOutFromStartup(genMeth, iosData);
		
		//Cancel forgot password
		genMeth.clickId(genMeth, iosData.BTNforgotPasswordID);
		genMeth.isElementVisible(By.id(iosData.BTNcancelForgotPasswordID));
//		genMeth.pressBackButton();
		genMeth.eyesCheckWindow(eyes, "Recover Password", useEye);
		genMeth.clickId(genMeth, iosData.BTNcancelForgotPasswordID);
		
		//recover with invalid mail
		genMeth.clickId(genMeth, iosData.BTNforgotPasswordID);
		genMeth.clearId(genMeth, iosData.TEXTFIELDrecoveryEmailID);
		genMeth.clickId(genMeth, iosData.BTNrecoverPasswordID);
		//genMeth.isElementVisible(By.name(iosData.InvalidRecoverEmailName));
//		genMeth.pressBackButton();
		genMeth.eyesCheckWindow(eyes, "Recover Password Invalid Mail", useEye);
		
		
		//recover with a valid mail
		genMeth.sendId(genMeth, iosData.TEXTFIELDrecoveryEmailID, iosData.User);
		genMeth.clickId(genMeth, iosData.BTNrecoverPasswordID);
		genMeth.isElementVisible(By.id(iosData.BTNresetPasswordID));
		genMeth.eyesCheckWindow(eyes, "Recover Password valid mail", useEye);
			
		//Attempt to reset password with incorrect confirmation code 
		genMeth.clickId(genMeth, iosData.BTNresetPasswordID);
	//	genMeth.isElementVisible(By.name(iosData.ConfCodeIncorrectName));
		genMeth.eyesCheckWindow(eyes, "Recover Password Incorrect code", useEye);
		genMeth.clickId(genMeth, iosData.BTNokName);
		//in order to be able to fully test the confirmation process i will need a generic code that will pass (need to ask DEV)
		
		
		//Re-Send confirmation code
		
		
	}
	
	@Test(enabled = true, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Search functionality & filter",
			groups = { "Sanity IOS1" })
	public void search() throws Exception, Throwable {
		
			}
	
	
	@Test (enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Settings: Save Login",
			groups = { "Sanity IOS1" })
			
	public void settingsKeepMeSignedIn() throws Exception, Throwable {

			}

	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Settings: Backup Enable/disable without upload in the background",
			groups = { "Regression Droid1" })
	public void settingsBackupEnableDisable() throws Exception, Throwable {

		// Disable the Backup from tour
		

		// "Enable" the backup form LSM (Left Side Menu) & cancel it
		

		// Enable the Backup form LSM (Left Side Menu)
	

		// Enable the backup from settings (first enable it & then disable it from backup screen)
		

	}
	

	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Switching from Foreground to Background and vice versa use cases",
			groups = { "Sanity IOS1" })
	public void foregroundBackgroundSwitch() throws Exception, Throwable {

		//Take the app to background & foreground x times
		
		
		//Take the app to sleep/lock  x times
	

	}
	
	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "connection lost handling", description = "Checking how the app owrks while connection is lost & back again", dependsOnGroups = { "Sanity*" }, groups = { "Sanity IOS1" })
	public void connectionLost() throws InterruptedException, IOException,
			ParserConfigurationException, SAXException {

	}
	
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		// driver.removeApp("com.pogoplug.android");

		try {
			//genMeth.setWifiOn();
			driver.removeApp("dc87ff52b20857e60a521f18599db44338eaf5cc");
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SendResults sr = new SendResults("elicherni444@gmail.com",
				"meny@skygiraffe.com", "TestNG results", "Test Results");
		sr.sendTestNGResult();

	}

}



