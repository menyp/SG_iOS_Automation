package Native;
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
	String appIdentifier;
	
	IosElements iosData;
	IOSDriver<MobileElement> driver;
	IosMethods genMeth = new IosMethods();
	Eyes eyes = new Eyes();
	Boolean useEye = true;

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) throws ParserConfigurationException, SAXException, IOException, InterruptedException, jdk.internal.org.xml.sax.SAXException {
		
        // This is your api key, make sure you use it in all your tests.
		
		//Set the tests configuration
		StartServerPath = genMeth.getValueFromPropFile("StartServerPath");
		StopServerPath = genMeth.getValueFromPropFile("StopServerPath");
		webElementXmlPath = genMeth.getValueFromPropFile("webElementXmlPath");
		webElementXmlLang = genMeth.getValueFromPropFile("webElementXmlLang");
		appIdentifier = genMeth.getValueFromPropFile("appIdentifier");
		
		//iosData= new IosElements(webElementXmlLang, webElementXmlPath);
		iosData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
		driver = genMeth.setCapabilitiesIos(genMeth);
			
		genMeth.cleanLoginIos(genMeth, iosData.userQA); 
 
	
	}

	@BeforeMethod (alwaysRun = true)
	public void checkHomeScreen() throws InterruptedException, IOException, ParserConfigurationException, SAXException, jdk.internal.org.xml.sax.SAXException{


		// Check if the client still logged in & in StartUp screen before each test
		if (driver == null) {
			try {
//				driver.removeApp(genMeth.getValueFromPropFile("appPackage"));
				driver.quit();
			} catch (Exception e) {
				// swallow if fails
			}
			
			driver = genMeth.setCapabilitiesIos(genMeth);
			iosData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
			genMeth.cleanLoginIos( genMeth, iosData.userQA );
		}

		else {
			boolean StartUpScreenDisplay = genMeth.checkIsElementVisible( By.name("SQL Golden App"));

			if (StartUpScreenDisplay != true) {

				try {
					driver.removeApp(appIdentifier);
					driver.quit();
				} catch (Exception e) {
					// swallow if fails
				}

				driver = genMeth.setCapabilitiesIos(genMeth);
				iosData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
				genMeth.cleanLoginIos( genMeth, iosData.userQA);

			}

		}

	}
		
	
	@Test (enabled = true ,testName = "Sample App Dashboard DailySales", retryAnalyzer = Retry.class, description = "Dashboard DailySales" ,
			groups= {"Sanity IOS1"}  /*dependsOnMethods={"testLogin"}*/)	

	public void sampleAplicationDashboardDailySales() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//Logout from startup page
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
		
		//useEye = true;
	
// Login to sample app & open Dashboard report

		genMeth.eyesCheckWindow(eyes, "SampleApp Main screen", useEye);
		genMeth.clickName(genMeth,  iosData.DashboardName);
		genMeth.eyesCheckWindow(eyes, "Dashboard Tab", useEye);
//		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone5(500);
		genMeth.eyesCheckWindow(eyes, "World wide orders Tab", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth,  iosData.DashboardName);
		
// Open Sales Bar
		// Change eye back to true once oleg fix the decimal issue
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.SalesName);
		//set Eye UI to false due to ordinal change
//		useEye = true;
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
		//genMeth.clickId(genMeth, iosData.DestinyUSAID);
		//genMeth.clickName(genMeth, iosData.DestinyUSAID);
		
		try {
			driver.findElementById(iosData.DestinyUSAID).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Net Sales - Destiny USA", useEye);

		genMeth.clickId(genMeth, iosData.ReturnsName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Returns", useEye);
		
		genMeth.clickId(genMeth, iosData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Sales", useEye);
		genMeth.clickId(genMeth, iosData.DailysalesPieID);
		genMeth.clickId(genMeth, iosData.Last12hoursID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye);
		
	
// Check slicer in Sparklines
		genMeth.clickXpth(genMeth, iosData.BTNSlicerIconXpth);
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickId(genMeth, iosData.DestinyUSAID);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines / Destiny USA", useEye);
		
//Clear the Slicer
		genMeth.clickXpth(genMeth, iosData.BTNSlicerIconXpth);
		genMeth.clickName(genMeth, iosData.BTNClearName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
//Open Daily Sales from main screen
		genMeth.clickId(genMeth, iosData.DailySalesID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar (no back icon)- Show All", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		genMeth.clickName(genMeth, "M");
		
	}
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOS"}  /*dependsOnMethods={"testLogin"}*/)	

	public void sampleAplicationServiceCalls() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//OPEN SERVICE CALLS
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
		
		//genMeth.clickName(genMeth,  iosData.DashboardName);
		
		genMeth.clickId(genMeth, iosData.ServiceCallsID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls", useEye);
		
// InGrid Action- First layer
		//genMeth.clickName(genMeth, iosData.BTNpriority_Name);
	/*	genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAStaticText[7]");
		genMeth.clickName(genMeth, "1");
		Thread.sleep(3000);
		genMeth.swipedownIphone5(1000);
		genMeth.swipeUpIphone5(1000);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "3");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 3", useEye);	*/
		
		//Open the Slicer
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]");
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickId(genMeth, iosData.MallOfAmerica_Id);
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Slicer Mall Of America", useEye);
/*
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]");
		Thread.sleep(3000);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "1");
		Thread.sleep(10000);
		genMeth.clickName(genMeth, iosData.BTNpriority_Name);
		genMeth.clickName(genMeth, "4");
		Thread.sleep(6000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 4", useEye);
		
*/		
		//Open the second layer
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[1]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Second layer", useEye);
		
		//Mobile & Email Contact Details/Person
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[16]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile Contact Person -Cards", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[17]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email Contact Person -Cards", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);
		
		genMeth.scrollDown(driver);
		genMeth.scrollDown(driver);

		//Mobile / Email / Map / URL - Address section
		//Phone
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[20]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile (Address Section)", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		//Email
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[21]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email (Address Section)", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);
		
		// URL
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[22]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- URL ((Address Section))", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		//Map
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[24]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile Maps (Address Section)", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		// Mobile / Email (Address Section)
		//Mobile
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[28]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Phone (Assigned To Section)", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		// Email 
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[29]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email (Assigned To Section)", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);
		
		//Close Service Call Action
		genMeth.clickName(genMeth, "Close Service Call");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Close Service Calls - Action", useEye);
		genMeth.clickName(genMeth, "Comments");
		genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "Meny The Best");
		
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.clickName(genMeth, "Parts");
		genMeth.clickName(genMeth, "Drawer");
		genMeth.clickName(genMeth, "SolutionType");
		genMeth.clickName(genMeth, "Replaced cash drawer");
		genMeth.clickName(genMeth, "Status");
		genMeth.clickName(genMeth, "Open");
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Close Service Calls - After Action", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, iosData.Icon_AllApps_Name);
				
	}
	
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOS"}  /*dependsOnMethods={"testLogin"}*/)	

	public void sampleAplicationServiceCallsMapNewServicecall() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//OPEN SERVICE CALLS Map
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
		
		//Open service calls map
		genMeth.clickId(genMeth, iosData.ServiceCallsMapID);
		Thread.sleep(1000);
		genMeth.clickXpth(genMeth, iosData.MallofAmericaOnMapXpath);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America", useEye);

		
		//Check is Location popup is displayed
		//genMeth.clickId(genMeth, iosData.BTNmapphoneiconID);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[2]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Phone Icon Option", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
	//	genMeth.clickName(genMeth, iosData.BTNMapCarIconName);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[2]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Car Direction", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		//go back to the map tab via the back navigation icon
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[3]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls:5", useEye);
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
		Thread.sleep(2000);

		//genMeth.eyesCheckWindow(eyes, "New Service Call", useEye);
		genMeth.eyesCheckWindow(eyes, "New service call Actions collections +", useEye);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAStaticText[1]");
		genMeth.eyesCheckWindow(eyes, "New Service Call", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.Icon_AllApps_Name);

	}
 

	@Test (enabled = true ,testName = "Sample App OrderLookup Operation", retryAnalyzer = Retry.class, description = "OrderLookup Operation" ,
			groups= {"Sanity IOS102"}  /*dependsOnMethods={"testLogin"}*/)	

	public void sampleAplicationOrderLookupOperation() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//OPEN Order Lookup
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
			
//Order lookup	
		genMeth.clickId(genMeth, iosData.OrderLookup_ID);
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Order Lookup parameters", useEye);
		genMeth.clickName(genMeth, "Start Date");
		MobileElement UIAPickerWheel = driver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAPicker[1]/UIAPickerWheel[1]");
		UIAPickerWheel.sendKeys("July");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.clickName(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "List of Orders", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		//Operations
		genMeth.clickXpth(genMeth, " //UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[7]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Inventory", useEye);
		//Open grid second layer
		genMeth.clickName(genMeth, iosData.MallOfAmerica_Id);
		genMeth.eyesCheckWindow(eyes, "Inventory second layer", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		genMeth.clickName(genMeth, "Inventory");
		genMeth.clickName(genMeth, "Orders");
		/*genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);*/

		genMeth.eyesCheckWindow(eyes, "Orders", useEye);
		
		genMeth.clickName(genMeth, "Orders");
		genMeth.clickName(genMeth, "Place New Order");

		genMeth.eyesCheckWindow(eyes, "Place New Order", useEye);

		//Open the place new order
		MobileElement El = driver.findElementByXPath(iosData.BTNplaceNewOrder_Xpth);
		El.click();
		
		genMeth.eyesCheckWindow(eyes, "Place new order parameters", useEye);
//		genMeth.clickName(genMeth, iosData.BTNsubmit_ID);		
//		genMeth.eyesCheckWindow(eyes, "Place new order parameters missing", useEye);
//		genMeth.clickName(genMeth, iosData.BTNokName);
		
//Fill the parameters
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickName(genMeth, iosData.MallOfAmerica_Id);
		genMeth.clickName(genMeth, "ProductID");
//		genMeth.accessToCameraHandle(genMeth);
		Thread.sleep(1000);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");
		Thread.sleep(1000);
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
		genMeth.clickName(genMeth, iosData.Icon_AllApps_Name);
		
	}
	
	@Test (enabled = true ,testName = "Sample App Technicians", retryAnalyzer = Retry.class, description = "Technicians" ,
			groups= {"Sanity IOS"}  /*dependsOnMethods={"testLogin"}*/)	

	public void sampleAplicationTechnicians() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//OPEN Order Lookup
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
					
// Technicians
		genMeth.clickName(genMeth, "Technicians");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians", useEye);
		
// 	Phone Icon
		genMeth.clickName(genMeth, "Phone");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians- Phone", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, "Phone");
// Add to contacts
		genMeth.clickName(genMeth, iosData.BTNaddContact_Name);
//		genMeth.accessToContactsHandle(genMeth);
		genMeth.eyesCheckWindow(eyes, "Technicians- Added by SkyGiraffe screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
// Mail Icon
		genMeth.clickName(genMeth, "Email");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Technicians- New Message screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);

// Map Icon
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "Technicians- Address screen", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
// Swipe along the technicians Cover Flow
		genMeth.swipeRightIphone5(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians- cover flow John Grant", useEye);
				
	}
 
	
	@Test(enabled = true, testName = "URL Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS100" })

	public void Tabs_URL() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to URL Constant
		genMeth.clickName(genMeth, "URL");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow(eyes, "Tabs- URL Data Item", useEye);
		
		//go to URL data Item
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "URL Constant");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow(eyes, "Tabs- URL Constant", useEye);
		
		//Go Back to Startup screen
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);
		
	}
		
	
	
	
	@Test(enabled = true, testName = "Map,Dashboard, Charts Tabs", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS100" })

	public void Tabs_Map_Chart() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		//Open Dashboard tab Tab
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Dashboard DefLayouts");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Dashboard Default Layout", useEye);

		//Open Map By Address Tab
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Map By Address");
		                    
		Thread.sleep(3000);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address", useEye);
		genMeth.clickName(genMeth,"19501 Biscayne Blvd, Aventura, FL 33180, 1 item");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address- Aventura", useEye);
		
		//Driving Directions
		genMeth.clickId(genMeth, iosData.BTNdirection);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address- Driving directions", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		//Phone
		genMeth.clickId(genMeth, iosData.BTNmapphoneiconID);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address- Phone", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		//Navigation
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[1]" );
		genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address- Navigation to Aventura", useEye);
		
		//Navigation Back
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		//Open Map By GPS
		Thread.sleep(10000);
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Map By GPS");
		genMeth.clickName(genMeth, "1160 Garden State Plz,Paramus, 3 items");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Map By GPS- Press pin map", useEye);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[3]");

		//Open Bar Chart
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Bar Chart");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart", useEye);
		
		//Filter data
		genMeth.clickName(genMeth, "Sales");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart- Returns & Net Sales", useEye);
		
		genMeth.clickName(genMeth, "Returns");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart- Net Sales", useEye);
		
		genMeth.clickName(genMeth, "Net Sales");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart- No data", useEye);
		
		genMeth.clickName(genMeth, "Sales");
		genMeth.clickName(genMeth, "Returns");
		genMeth.clickName(genMeth, "Net Sales");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart", useEye);

		//Navigation to pie chart
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[2]");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart- Navigate to Pie Chart", useEye);
		
		//Navigate back to the Bar chart
		//genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[1]");
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(8000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart", useEye);

		
		//Pie Chart
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Pie chart");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Pie Chart", useEye);
		
		//Filter data	
		genMeth.clickName(genMeth, "Returns");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Pie Chart- Returns", useEye);
		
		genMeth.clickName(genMeth, "Net Sales");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Pie Chart- Net Sales", useEye);
		
		//Navigation to Bar chart
		genMeth.clickName(genMeth, "   Aventura Mall         ");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Pie Chart- Navigate to Bar Chart", useEye);
		
		//Navigation back to the Pie chart
		//genMeth.clickId(genMeth, "Aventura Mall");
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);

		Thread.sleep(10000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Pie Chart", useEye);
		
		//Go Back to Startup screen
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);

		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);

		
	}
	
	
	@Test(enabled = true, testName = "Cover Flow", retryAnalyzer = Retry.class, description = "Check the Cover Flow tab",
			groups = { "Sanity IOS100" })

	public void Tabs_CoverFlow() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to CoverFlow
		genMeth.clickName(genMeth, "All Tabs");
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.swipedownIphone5Long(1000);
		genMeth.clickName(genMeth, "Cover Flow");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow", useEye);
		genMeth.swipeRightIphone5(1000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- swipe John Grant", useEye);
		
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- swipe down", useEye);
		
		//Address
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- Address", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		//Phone
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.clickName(genMeth, "Phone");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- Phone", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		//Email
		genMeth.clickName(genMeth, "Email");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- Email", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);
		
		//URL
		genMeth.clickName(genMeth, "URL");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- URL", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.swipedownIphone5Long(1000);
		
		
		// Landline
		genMeth.clickName(genMeth, "Landline");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Cover Flow- Landline", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
	}

	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS100" })

	public void Tabs_List() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		genMeth.clickName(genMeth, "All Tabs");
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.swipedownIphone5Long(1000);
		genMeth.clickName(genMeth, "List");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List", useEye);
		
		//Map
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List Address", useEye);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		//MiniMap
		genMeth.clickName(genMeth, "Address Mini Map");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List Mini Map", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		//Phone
		genMeth.clickName(genMeth, "Phone");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List Phone", useEye);
		genMeth.swipedownIphone5Short(1000);

		
		//Email
		genMeth.clickName(genMeth, "Email");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List Email", useEye);
		genMeth.clickName(genMeth, iosData.BTNsend_Name);

		//URL
		genMeth.clickName(genMeth, "URL");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List URL", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		
		// Landline
		genMeth.clickName(genMeth, "Landline");
		genMeth.eyesCheckWindow(eyes, "All Tabs- List Landline", useEye);
		

		genMeth.clickId(genMeth, iosData.BTNseeAll_ID);		
		genMeth.eyesCheckWindow(eyes, "All Tabs- List See All", useEye);
		
		genMeth.swipedownIphone5Long(1000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- List See All scroll down", useEye);
		
		
		genMeth.clickName(genMeth, iosData.IconBack_Name);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);	
		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);

	}
	
	
	
	@Test(enabled = true, testName = "Grid two layer", retryAnalyzer = Retry.class, description = "Check the Grid two layer tab",
			groups = { "Sanity IOS100" })

	public void Tabs_Grid_Two_Layers() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to Grid
		genMeth.clickName(genMeth, "All Tabs");
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Grid - Two Layers");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers", useEye);
		
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);		
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);		
		genMeth.swipeRightIphone5(1000);


		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Swipe right", useEye);
		genMeth.clickName(genMeth, "kpi_green");
		
		//Second layer
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Second layer", useEye);
		genMeth.swipedownIphone5Long(1000);
		genMeth.setLandscapeMode();
		genMeth.setPortraitMode();
		
		//Map
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers-  Address", useEye);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		//MiniMap
		genMeth.clickName(genMeth, "Address Mini Map");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers-  Mini Map", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		//Phone
		genMeth.clickName(genMeth, "Phone");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Phone", useEye);
		genMeth.swipedownIphone5Short(1000);

		
		//Email
		genMeth.clickName(genMeth, "Email");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Email", useEye);
		genMeth.clickName(genMeth, iosData.BTNsend_Name);
//		genMeth.clickName(genMeth, iosData.BTNsend_Name);

		//URL
		genMeth.clickName(genMeth, "URL");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- URL", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		genMeth.swipedownIphone5Long(1000);
		//genMeth.swipedownIphone5Long(1000);

		
		// Landline
		genMeth.clickName(genMeth, "Landline");
		genMeth.swipedownIphone5Long(1000);
		genMeth.clickName(genMeth, "Landline");

		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Landline",useEye);
				
		genMeth.clickName(genMeth, iosData.IconBack_Name);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);

		
		
	}
	
	@Test(enabled = true, testName = "Grid one layer", retryAnalyzer = Retry.class, description = "Check the Grid one layer tab",
			groups = { "Sanity IOS100" })

	public void Tabs_Grid_One_Layer() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to Grid
		genMeth.clickName(genMeth, "All Tabs");
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		
		genMeth.clickName(genMeth, "Grid - One Layer");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer", useEye);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);			
		
		// Map
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[7]");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer-  Address",useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		

		// MiniMap
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[9]");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer-  Mini Map",useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		// Phone
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[11]");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer- Phone",useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);


		// Email
		genMeth.swipeRightIphone5(1000);
			
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[7]");
		
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer- Email",useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);

		// URL
		
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[9]");
		
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer- URL", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		genMeth.swipeRightIphone5(1000);
		genMeth.swipedownIphone5Short(1000);
		
		// Landline
		genMeth.clickXpth(genMeth,"//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[21]");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer- Landline", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
				
		genMeth.eyesCheckWindow(eyes, "All Tabs- Grid one layer- Swipe right",useEye);

		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);	}
	
	
	@Test(enabled = true, testName = "Employee Directory", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity IOS101" })

	public void Tabs_Employee_Directory() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to Employee Directory tab
		genMeth.clickName(genMeth, "All Tabs");
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.swipedownIphone5Short(1000);
		
		genMeth.clickName(genMeth, "Employee Directory");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory main", useEye);
		
		//Search an employee (Empty search)
		genMeth.clickName(genMeth, "Search");
		genMeth.sendName(genMeth, "Search", "no emplyees found");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - empty search", useEye);
		
		genMeth.clickName(genMeth, iosData.BTNclearText_Name);
		
		//Search an employee
		genMeth.sendName(genMeth, "Search" , "Lane" );
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - search Specific employee", useEye);
		
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		
		//second layer
		genMeth.clickName(genMeth, "Lane R. Barlow");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Second layer", useEye);
		

		// Phone
		genMeth.clickName(genMeth, "Phone");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Phone", useEye);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipeUpIphone5Short(1000);

		
		// Email
		genMeth.clickName(genMeth, "Email");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Email", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);
		genMeth.clickName(genMeth, iosData.BTNdeleteDraft_Name);
		
		//Map
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.clickName(genMeth, "Address First");
		genMeth.eyesCheckWindow(eyes,"All Tabs- Employee Directory - Address First", useEye);

		
		// Mini Map
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);

		genMeth.clickName(genMeth, "Address (Second)");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Address second", useEye);
		genMeth.clickName(genMeth, iosData.BTNCancelName);

		// URL
		genMeth.clickName(genMeth, "Google");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - URL", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		//Social Networks
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[1]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - FaceBook", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[2]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Twitter", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[3]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Linkein", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[4]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Google+", useEye);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		
		//Navigation
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.swipedownIphone5Short(1000);
		genMeth.clickName(genMeth, "First_Name");
		Thread.sleep(6000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory - Navigation", useEye);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory, Back from navigation", useEye);
		
		//No Social Networks available
		genMeth.clickName(genMeth, "Callum R. Aguirre");
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory, No Social Networks", useEye);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		
		//No Google+ 
		genMeth.clickName(genMeth, "Caldwell Alexander");
		genMeth.swipedownIphone5Long(1000);
		genMeth.swipedownIphone5Long(1000);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory, No Google+", useEye);
		
		//Back to Startup screen
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, iosData.Icon_AllApps_Name);
		
		//Press info for the app
		genMeth.clickName(genMeth, iosData.IconApplicationInfo_Name);
		genMeth.eyesCheckWindow(eyes, "All Tabs- Employee Directory, Golden App info screen", useEye);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[2]");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
				
		//Verify Startup screen is open
		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);
		
	}

	
	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Test the Actions", groups = { "Sanity IOS2" })
	public void actions() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// Open QA_SQL Actions --> Ingrid report
		genMeth.clickName(genMeth, "A_DL testing");
		genMeth.clickXpth(genMeth,iosData.IconBackToApplicationList_xpth);
		genMeth.clickName(genMeth, "QA_SQL Actions");
		genMeth.clickName(genMeth, "InGrid");

		// Grid Dynamic List
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Grid- Dynamic List");
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[9]");
		
		genMeth.clickName(genMeth, "Val1");
		genMeth.clickName(genMeth, iosData.Iconaction_icon_green_Name);
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "iOS_Actions_Grid- DynamicList", useEye);
		genMeth.clickName(genMeth, "Drawer,12");
		Thread.sleep(3000);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[4]");
		genMeth.clickName(genMeth, iosData.BTNrefresh_Name);
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Grid- Dynamic List");
		genMeth.eyesCheckWindow(eyes, "iOS_Actions_Grid- DynamicList_Success", useEye);
		genMeth.swipeRightIphone6Plus(1000);
		
		// Grid Parameterized List
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[9]");
		genMeth.clickName(genMeth, "Val1");
		genMeth.clickName(genMeth, iosData.Iconaction_icon_green_Name);
		genMeth.clickName(genMeth, "Drawer,12");
		Thread.sleep(3000);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[4]");
		genMeth.clickName(genMeth, iosData.BTNrefresh_Name);
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Grid-ParameterizedSL");
		genMeth.eyesCheckWindow(eyes, "iOS_Actions_Grid- ParameterizedSL_Success", useEye);
		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone6Plus(1000);

		// Grid QR 
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAScrollView[1]/UIAScrollView[2]/UIAImage[9]");
		genMeth.clickXpth(genMeth, iosData.TEXTFIELDqr_Xpth);
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, "1");		
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.clickName(genMeth, iosData.Iconaction_icon_green_Name);
		genMeth.clickXpth(genMeth, iosData.TEXTFIELDqr_Xpth);
		genMeth.clickName(genMeth, iosData.BTNClearName);
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[4]");
		genMeth.clickName(genMeth, iosData.BTNrefresh_Name);
		genMeth.clickName(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickName(genMeth, "Grid- QR");
		genMeth.eyesCheckWindow(eyes, "iOS_Actions_Grid- QR_Success", useEye);
		
		//Back to startup screen
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickXpth(genMeth, iosData.IconBackToApplicationList_xpth);
		
	}
	
	
	
	@Test(enabled = true, groups = { "Sanity IOS2" }, testName = "Sanity Tests", description = "login with bad/missing credentials", retryAnalyzer = Retry.class)
	public void badCredentials() throws Exception, Throwable {

		genMeth.signOutFromStartup(genMeth);
		// Login with bad user name
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, "bad name");
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, iosData.passwordProd);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		
		genMeth.clickId(genMeth, iosData.BTNokName);

		// Login with bad password 
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, iosData.userQA);
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
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordXpth, iosData.passwordQA);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		// Login with empty Password
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailXpth, iosData.userQA);
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
	
	

	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Switching from Foreground to Background and vice versa use cases",
			groups = { "Sanity IOS2" })
	public void foregroundBackgroundSwitch() throws Exception, Throwable {

		//Take the app to background & foreground x times
		
		
		//Take the app to sleep/lock  x times
	

	}
	
	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "connection lost handling", description = "Checking how the app owrks while connection is lost & back again", dependsOnGroups = { "Sanity*" },
			groups = { "Sanity IOS2" })
	public void connectionLost() throws InterruptedException, IOException,
			ParserConfigurationException, SAXException {

	}
	
	
	
	
	
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		try {

			boolean isAppInstalled = driver.isAppInstalled(appIdentifier);
			if (isAppInstalled) {
				driver.removeApp(appIdentifier);
			}
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SendResults sr = new SendResults("elicherni444@gmail.com",
				"meny@skygiraffe.com", "TestNG results", "Test Results");
		//sr.sendTestNGResult();
		sr.sendRegularEmail();
		/*
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng2 = new TestNG();
		testng2.setTestClasses(new Class[] { SendReport.class });
		testng2.setGroups("send mail");
		testng2.addListener(tla);
		testng2.run();
*/
	}

	


	
}



