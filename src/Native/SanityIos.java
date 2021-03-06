package Native;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.xml.sax.SAXException;

import com.applitools.eyes.Eyes;

//MobileElement e2; //test will wait for this diring 20 seconds
enum EnvironmentMode {
	Prod, Staging, QA
}
	
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
	

	IOSDriver<MobileElement> driver;
	IosMethods genMeth = new IosMethods();
	Eyes eyes = new Eyes();
	Boolean useEye;
	boolean  skipfailure = true;
	IosElements iosData;
	EnvironmentMode EnvMode;
	AppiumDriverLocalService service;


	
	
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
		//Set the tests configuration
		EnvMode = genMeth.UserSetEnvironmentMode(EnvMode);
		useEye = genMeth.UseEye();
		
		
		iosData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
		//service = genMeth.startAppiumService();

		driver = genMeth.setCapabilitiesIos(genMeth);
		genMeth.cleanLoginIos(genMeth, iosData.userQA, iosData.passwordQA); 
	}

	@BeforeMethod (alwaysRun = true)
	public void checkHomeScreen() throws InterruptedException, IOException, ParserConfigurationException, SAXException, jdk.internal.org.xml.sax.SAXException{


		// Check if the client still logged in & in StartUp screen before each test
		if (driver == null) {
			try {
//				driver.removeApp(genMeth.getValueFromPropFile("appPackage"));
				//driver.quit();
				driver = genMeth.setCapabilitiesIos(genMeth);
				genMeth.cleanLoginIos( genMeth, iosData.userQA , iosData.passwordQA );
				
			} catch (Exception e) {
				// swallow if fails
				
			}
			
		}

		else {
			
			boolean StartUpScreenDisplay = genMeth.checkIsElementVisible( By.id("SQL Golden App"));

			if (StartUpScreenDisplay != true) {

				try {
					/*
					genMeth.clickId(genMeth, iosData.BTNsettingsIconXpth);
					driver.findElementById(iosData.BTNlogoutName).click();
					//genMeth.clickId(genMeth, iosData.BTNlogoutName);			
					driver.removeApp(appIdentifier);
					driver.quit();
					*/
					
					//reset app -->  reinstall the app (login screen will be displayed)
					driver.resetApp();
					//driver.resetApp();   

					
				} catch (Exception e) {
					driver.quit();
					// swallow if fails
				}
				
				/*
				try {
					driver.removeApp(appIdentifier);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					driver.quit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

*/
				
				//driver = genMeth.setCapabilitiesIos(genMeth);
				//iosData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
				genMeth.releaseAllow(genMeth);
				genMeth.cleanLoginIos( genMeth, iosData.userQA, iosData.passwordQA);

			}

		}

	}
		

	
	@Test(enabled = true, testName = "URL Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS" })

	public void Tabs_URL() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to URL Constant
	//	driver.findElementById("URL / News").click();
		genMeth.clickId(genMeth, "URL / News");

		Thread.sleep(20000);
		
		genMeth.eyesCheckWindow("iOS Tabs_URL - URL Data Item", useEye, genMeth, skipfailure);
		
		//go to URL data Item
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		
		genMeth.clickId(genMeth, "URL Constant");

		Thread.sleep(20000);
		genMeth.eyesCheckWindow("iOS Tabs_URL- URL Constant", useEye, genMeth,skipfailure);
		
		//Go Back to Startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth,skipfailure);
		
	}
		
	
	@Test(enabled = true, testName = "News Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS" })

	public void Tabs_News() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to News 

		genMeth.clickId(genMeth, "URL / News");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "News");

		genMeth.eyesCheckWindow("iOS Tabs_News- News", useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, "www.milliondollarhomepage.com");
		Thread.sleep(15000);

		genMeth.eyesCheckWindow("iOS Tabs_News - The milliion $ home page", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		genMeth.rotateLandscape();
		genMeth.eyesCheckWindow("iOS Tabs_News - News Landscape", useEye, genMeth, skipfailure);		
		genMeth.rotatePortrait();

		//Go Back to Startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	
	
	@Test(enabled = true, testName = "Dashboard  Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS" })

	public void Tabs_Dashboard() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		//Open Dashboard tab Tab
		genMeth.clickId(genMeth, "DashB/Cards/Employee");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard Default Layout", useEye, genMeth, skipfailure);
		
		//Navigate to Employee directory tab

		genMeth.clickId(genMeth, "Service Call ID1");
		Thread.sleep(14000);
		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard Default Layout- Navigate to Employee Directory", useEye, genMeth, skipfailure);

		//Navigate back to Dashboard
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(4000);
//		genMeth.clickId(genMeth, "Gauge");
		//genMeth.eyesCheckWindow("All Tabs- Dashboard Default Layout", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "DashB/Cards/Employee");
		Thread.sleep(8000);

		genMeth.clickId(genMeth, iosData.BTNSlicer);
		genMeth.swipedownIphone5Long(2, 1000);
		genMeth.clickId(genMeth, "Service Call ID1");
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, "Slicers");
	//	genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.swipedownIphone5Long(4, 1000);
		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard Advanced columns (Scroll down)", useEye, genMeth, skipfailure);

		//Gauge
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Dash with Gauge");
		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard- Gauge Half", useEye, genMeth, skipfailure);
		
		//Navigate
		genMeth.clickId(genMeth, "Gauge/Half/NoTar/Per");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard- Navigate to Map By GPS", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipedownIphone5Long(3, 1000);

		genMeth.eyesCheckWindow("iOS Tabs_Dashboard - Dashboard- Gauge Full/Soli", useEye, genMeth, skipfailure);

		//Back to Startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
				
	}
	
	
	@Test(enabled = true, testName = "Map", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS" })

	public void Tabs_Map() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		//Open Map By Address Tab
		genMeth.clickId(genMeth, "Map");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Map By Address");
		                    
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("iOS Tabs_Map - Map By Address Main", useEye, genMeth, skipfailure);
		genMeth.clickAccessibilityID("19501 Biscayne Blvd, Aventura, FL 33180, 1 item");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("iOS Tabs_Map - Map By Address- Aventura", useEye, genMeth, skipfailure);
		
		//Driving Directions
		genMeth.clickId(genMeth, iosData.BTNdirection);
		genMeth.eyesCheckWindow("iOS Tabs_Map - Map By Address- Driving directions", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		//Phone
		genMeth.clickId(genMeth, iosData.BTNmapphoneiconID);
		genMeth.eyesCheckWindow("iOS Tabs_Map - Map By Address- Phone", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);

		//Navigation to URL tab (need an id for the navigation iocn from Limor)
		genMeth.clickId(genMeth, iosData.Icon_Map_Navigation);
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("iOS Tabs_Map - URL Data Item", useEye, genMeth, skipfailure);
		
		//Navigation Back
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//Open Map By GPS
		Thread.sleep(10000);
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Map By GPS");
		genMeth.clickId(genMeth, "1 Garden State Plz,Paramus, 3 items");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Tabs_Map - Map By GPS- Press pin map", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.Icon_Map_Navigation);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
				
		//Back to Startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

	}
	
	@Test(enabled = true, testName = "Map Charts Tabs", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity IOS" })

	public void Tabs_Chart() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		//Open Bar Chart
		genMeth.clickId(genMeth, "Chart/CoverF/ActionC");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Tabs_Chart- Bar Chart", useEye, genMeth, skipfailure);
		
		//Filter data
		genMeth.clickId(genMeth, "Sales");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Bar Chart- Returns & Net Sales", useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, "Returns");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Bar Chart- Net Sales", useEye, genMeth, skipfailure);
		
		//genMeth.clickId(genMeth, "Net Sales");
		//genMeth.eyesCheckWindow(eyes, "All Tabs- Bar Chart- No data", useEye);
		
		genMeth.clickId(genMeth, "Sales");
		genMeth.clickId(genMeth, "Returns");
		//genMeth.clickId(genMeth, "Net Sales");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Bar Chart", useEye, genMeth, skipfailure);

		//Navigation to Dashboard tab
		genMeth.clickAccessibilityID(iosData.Icon_BarChart_Navigation);
		Thread.sleep(12000);
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Bar Chart- Navigate to Dashboard", useEye, genMeth, skipfailure);
		
		//Navigate back to the Bar chart
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Bar Chart", useEye, genMeth, skipfailure);
		
		//Pie Chart
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Pie Chart");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Pie Chart", useEye, genMeth, skipfailure);
		
		//Filter data	
		genMeth.clickId(genMeth, "Returns");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Pie Chart- Returns", useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, "Net Sales");
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Pie Chart- Net Sales", useEye, genMeth, skipfailure);
		
		//Navigation to Bar chart
		genMeth.clickAccessibilityID(iosData.Icon_PieChart_Navigation);
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Pie Chart- Navigate to Bar Chart", useEye, genMeth, skipfailure);
		
		//Navigation back to the Pie chart
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_Chart - Pie Chart", useEye, genMeth, skipfailure);
		
		//Go Back to Startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);

		
	}
	
	
	@Test(enabled = true, testName = "Cover Flow", retryAnalyzer = Retry.class, description = "Check the Cover Flow tab",
			groups = { "Sanity IOS" })

	public void Tabs_CoverFlow() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to CoverFlow
		genMeth.clickId(genMeth, "Chart/CoverF/ActionC");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Cover Flow");
		genMeth.eyesCheckWindow("All Tabs- Cover Flow", useEye, genMeth, skipfailure);
		genMeth.swipeRightIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- swipe John Grant", useEye, genMeth, skipfailure);
		
		genMeth.swipedownIphone5Short(2, 1000);
		genMeth.eyesCheckWindow("All Tabs- Cover Flow- swipe down", useEye, genMeth, skipfailure);
				
		//Address
		String xpth = "(//XCUIElementTypeStaticText[@name=\"Address\"])[1]";
		genMeth.clickXpth(genMeth, xpth);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- Address", useEye, genMeth, skipfailure);
		//genMeth.clickAccessibilityID("iosData.BTNCancelName");
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		//Address mini Map
		xpth = "(//XCUIElementTypeStaticText[@name=\"Address Mini Map\"])[2]";
		genMeth.clickXpth(genMeth, xpth);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- Address", useEye, genMeth, skipfailure);
		//genMeth.clickAccessibilityID("iosData.BTNCancelName");
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		//Phone
		xpth = "(//XCUIElementTypeStaticText[@name=\"Phone\"])[1]";
		genMeth.clickXpth(genMeth, xpth);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- Phone", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		//Email
		genMeth.swipedownIphone5Short(1, 1000);
		xpth = "(//XCUIElementTypeStaticText[@name=\"Email\"])[1]";
		genMeth.clickXpth(genMeth, xpth);
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.BTNdeleteDraft_Name);
		
		//URL
		xpth = "(//XCUIElementTypeStaticText[@name=\"URL\"])[1]";
		genMeth.clickXpth(genMeth, xpth);
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.swipedownIphone5Long(1, 1000);		
		
		// Landline
		xpth = "(//XCUIElementTypeStaticText[@name=\"Landline\"])[1]";
		genMeth.clickXpth(genMeth, xpth);
		genMeth.eyesCheckWindow("iOS Tabs_CoverFlow - Cover Flow- Landline", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);

		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);

		
	}

	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })

	public void Tabs_List_AdvancedColumns() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		genMeth.clickId(genMeth, "List / Grid");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List1", useEye, genMeth, skipfailure);
		
		//Phone
		genMeth.clickId(genMeth, "Phone");
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List Phone", useEye, genMeth, skipfailure);
		
		//Email
		genMeth.clickId(genMeth, "Email");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNsend_Name);
		Thread.sleep(2000);
		//URL
		genMeth.clickId(genMeth, "URL");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNdoneName);		
		Thread.sleep(3000);
		
		// Landline
		genMeth.clickId(genMeth, "Landline");
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List Landline", useEye, genMeth, skipfailure);
		
		//Address
		genMeth.clickId(genMeth, "Address");
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List Address", useEye, genMeth, skipfailure);
		
		//Mini Map
		genMeth.clickId(genMeth, "Address Mini Map");
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List (Address Mini Map options)", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		//Check other advanced
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List2", useEye, genMeth, skipfailure);
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List3", useEye, genMeth, skipfailure);
		
		//Second Layer
		genMeth.clickId(genMeth, iosData.BTNseeAll_ID);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List See All", useEye, genMeth, skipfailure);
		genMeth.swipedownIphone5Long(1, 1000);

		Thread.sleep(2000);
		genMeth.eyesCheckWindow("iOS Tabs_List_AdvancedColumns - List See All scroll down", useEye, genMeth, skipfailure);		
		
		//genMeth.clickId(genMeth, iosData.IconBack_Name);
		genMeth.clickAccessibilityID(iosData.IconBack_Nav_Name);
		genMeth.clickAccessibilityID(iosData.IconBack_Nav_Name);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		

	}
	
	
	
	@Test(enabled = true, testName = "Grid two layer Advanced", retryAnalyzer = Retry.class, description = "Check the Grid two layer tab",
			groups = { "Sanity IOS1" })

	public void Tabs_Grid_Two_Layers() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to Grid
		genMeth.clickId(genMeth, "List / Grid");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - Two Layers");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers", useEye, genMeth, skipfailure);	
		genMeth.clickAccessibilityID("icon_usa");
		//genMeth.clickId(genMeth, "icon_usa");
		
		//Second layer
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers- Second layer", useEye, genMeth, skipfailure);		
	
		//Phone
		genMeth.clickId(genMeth, "Phone");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers- Phone options open", useEye, genMeth, skipfailure);		
		
		
		//genMeth.clickId(genMeth, "Phone");
		//genMeth.eyesCheckWindow(eyes, "All Tabs- Grid two layers- Phone options closed", useEye);

		
		// Landline
		genMeth.clickId(genMeth, "Landline");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers- Landline", useEye, genMeth, skipfailure);		
		
		//URL
		genMeth.clickId(genMeth, "URL");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers- URL", useEye, genMeth, skipfailure);	
		genMeth.clickAccessibilityID(iosData.BTNdoneName);
		//genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		/*
		isVisible = genMeth.checkIsElementVisible(By.id("Text"));

		if (isVisible != true){
			org.testng.Assert.fail("Phone options are missing");
			
		}
		*/
		


		
		/*
		Boolean isVisible = genMeth.checkIsElementVisible(By.id("Apple maps"));
		if (isVisible != true){
			org.testng.Assert.fail("Address options are missing");
			
		}
		*/
		genMeth.swipedownIphone5Long(3, 1000);

		//Email
		genMeth.clickId(genMeth, "Email");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers- Grid two layers- Email", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.BTNdeleteDraft_Name);

		//genMeth.clickId(genMeth, iosData.BTNsend_Name);
		//Thread.sleep(3000);
		
		
		//Address
		genMeth.clickId(genMeth, "Address");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers-  Address", useEye, genMeth, skipfailure);	
		genMeth.swipedownIphone5Short(1, 1000);
		
		//MiniMap
		genMeth.clickId(genMeth, "Address Mini Map");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_Two_Layers - Grid two layers-  Mini Map", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		genMeth.clickAccessibilityID(iosData.IconBack_Nav_Name);	
		genMeth.clickAccessibilityID(iosData.IconBack_Nav_Name);		
		genMeth.clickAccessibilityID(iosData.IconBack_Nav_Name);		


		//genMeth.clickId(genMeth, iosData.IconBack_Name);
		//genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	@Test(enabled = true, testName = "Grid one layer", retryAnalyzer = Retry.class, description = "Check the Grid one layer tab Advanced & navigation",
			groups = { "Sanity IOS" })

	public void Tabs_Grid_One_Layer_AdvanceColumns() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to Grid
		genMeth.clickId(genMeth, "List / Grid");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		
		genMeth.clickId(genMeth, "Grid - One Layer");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer (Advanced - Part 1)", useEye, genMeth, skipfailure);		
		
		// Address
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer-  Address", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		

		// Mobile Phone
		genMeth.swipeRightIphone5Long(1, 1000);
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer- Phone", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);

		// MiniMap - Navigation to slicer report
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[5]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns- Grid one layer-  Mini Map", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(2000);

		// Email
		genMeth.swipeRightIphone5Long(3, 1000);
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]	");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer- Email", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.BTNdeleteDraft_Name);

		// URL
		Thread.sleep(1000);
		genMeth.clickXpth(genMeth, " //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		Thread.sleep(12000);
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer- URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		// Landline
		genMeth.clickXpth(genMeth,"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer- Landline", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.eyesCheckWindow("iOS Tabs_Grid_One_Layer_AdvanceColumns - Grid one layer- Swipe right", useEye, genMeth, skipfailure);		

		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		genMeth.swipeUpIphone5Long(1, 1000);
	
	
	}
	
	
	@Test(enabled = true, testName = "Employee Directory", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity IOS" })

	public void Tabs_Employee_Directory() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to Employee Directory tab
		

		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);
			
		genMeth.clickId(genMeth, "DashB/Cards/Employee");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		//genMeth.swipedownIphone5Short(1000);
		
		genMeth.clickId(genMeth, "Employee Directory");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory main", useEye, genMeth, skipfailure);		
		/*
		//Search an employee (Empty search)
		genMeth.clickId(genMeth, "Search");
		genMeth.sendId(genMeth, "Search", "no emplyees found");
		genMeth.eyesCheckWindow("All Tabs- Employee Directory - empty search", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.BTNclearText_Name);
		
		//Search an employee
		genMeth.sendId(genMeth, "Search" , "Lane" );
		genMeth.eyesCheckWindow("All Tabs- Employee Directory - search Specific employee", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		*/
		//second layer
		genMeth.clickId(genMeth, "Callum R. Aguirre");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Second layer", useEye, genMeth, skipfailure);		
		

		// Phone
		genMeth.clickId(genMeth, "Phone");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Phone", useEye, genMeth, skipfailure);		
	
		// Email
		genMeth.clickId(genMeth, "Email");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Email", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.BTNdeleteDraft_Name);
		
		//Map
		genMeth.swipedownIphone5Shorter(1, 1000);
		genMeth.swipedownIphone5Short(3, 1000);
		
		genMeth.clickId(genMeth, "Address First");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Address First", useEye, genMeth, skipfailure);		

		
		// Mini Map
		genMeth.swipedownIphone5Shorter(1, 1000);
		genMeth.clickId(genMeth, "Address (Second)");
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Address second", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		
		// Navigation
		genMeth.swipedownIphone5Short(2, 1000);
		genMeth.clickId(genMeth, "First_Name");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Navigation to Param report ED", useEye, genMeth,skipfailure);
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory, Back from navigation", useEye, genMeth, skipfailure);

		// URL
		genMeth.swipedownIphone5Long(2, 1000);
		genMeth.clickId(genMeth, "google.com");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - URL", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//Social Networks
		genMeth.swipedownIphone5Shorter(1, 1000);
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]");
		
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - FaceBook", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Twitter", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Linkein", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[4]");
		Thread.sleep(14000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory - Google+", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//No Google+/Twitter/Linkenin
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, "Caldwell Alexander");
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("iOS Employee Directory - Employee Directory, No Twitter/Linkedin/Google+", useEye, genMeth, skipfailure);		
		
		//Back to Startup screen
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		
		
		//Press info for the app (Doesn't work)
		//genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		//genMeth.clickId(genMeth, iosData.IconApplicationInfo_Name);
		//genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[2]");
		//genMeth.clickId(genMeth, "");
		//genMeth.eyesCheckWindow("All Tabs- Employee Directory, Golden App info screen", useEye, genMeth, skipfailure);
		//genMeth.swipedownIphone5Short(1, 1000);
		//genMeth.swipeRightIphone5Short(1, 1000);
				
		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	
	@Test(enabled = true, testName = "Parameterized report Grid", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity IOS" })

	public void Param_Report_Grid() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to parameterized report - Grid tab
		genMeth.swipedownIphone5Short(2, 1000);

		genMeth.clickId(genMeth, "Param Report Grid");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Param Report Grid- add Parameters", useEye, genMeth, skipfailure);		
		
		//Attempt to submit while mandatory is missing
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow("Param Report Grid- Mandatory field is missing", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNokName);

		//Insert parameters
		genMeth.clickId(genMeth, "SL-Device Types");
		genMeth.eyesCheckWindow("Param Report Grid- SL param", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "Mobile");
		Thread.sleep(8000);
		
		genMeth.clickId(genMeth, "PSL- Device Model");
		genMeth.eyesCheckWindow("Param Report Grid- PSL param", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "iPhone5");
		genMeth.eyesCheckWindow("Param Report Grid- All params were filled", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Param Report Grid- Grid first layer", useEye, genMeth, skipfailure);		
		//Go To second layer
		genMeth.clickId(genMeth, "iPhone5");
		genMeth.eyesCheckWindow("Param Report Grid- Grid second layer", useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	@Test(enabled = true, testName = "Parameterized report With all variables", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity IOS" })

	public void Param_Report_AllVariables() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		genMeth.swipedownIphone5Short(1, 1000);
		genMeth.clickId(genMeth, "Param Variables only");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Report with All Variables - SQL Golden App", useEye, genMeth, skipfailure);		

		
		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	
	@Test(enabled = true, testName = "Parameterized report List", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity IOS" })

	public void Param_Report_List() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to parameterized report - Grid tab
		genMeth.swipedownIphone5Long(1, 1000);

		genMeth.clickId(genMeth, "Param Report List");
		genMeth.eyesCheckWindow("Param Report List- add Parameters", useEye, genMeth, skipfailure);		
		
		//Attempt to submit while mandatory is missing
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		genMeth.eyesCheckWindow("Param Report List- Mandatory field is missing", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNokName);

		//Insert parameters
		genMeth.clickId(genMeth, "FreeText  (Priority)");
		genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.clickId(genMeth, "SL_ML (Priority)");
		genMeth.eyesCheckWindow("Param Report List- All params were filled", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Report List- FreeText Priority = 1", useEye, genMeth, skipfailure);		

		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "SL_ML (Priority)");
		genMeth.eyesCheckWindow("Param Report List- FreeText Priority = 2", useEye, genMeth, skipfailure);				

		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "SC(Up_Date<=MobDate)");
		genMeth.eyesCheckWindow("Param Report List- SC(Up_Date<=MobDate)", useEye, genMeth, skipfailure);				
		
		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(2, 1000);

		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);						
	}

	@Test(enabled = true, groups = { "Sanity IOS" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_DL_Dashboard()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report dashboard - DL- Device Info tab
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.clickId(genMeth, "Param DL-Dashboard");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Param Report Dashboard DL- add Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "SL- Devices Type");
		genMeth.eyesCheckWindow("Param Report Dashboard DL- SL param", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "Mobile");
		Thread.sleep(2000);
		
		genMeth.clickId(genMeth, "DL- Device Model");
		genMeth.eyesCheckWindow("Param Report Dashboard DL- DL param", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "iPhone6");
		genMeth.eyesCheckWindow("Param Report Dashboard DL- All params were filled", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Param Report Dashboard DL- Dashboard tab", useEye, genMeth, skipfailure);		
		
		//Navigate to Dashboard tab
//		genMeth.clickId(genMeth, "Device Type name (ParentName)");
		genMeth.clickId(genMeth, "Mobile");

		Thread.sleep(8000);
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Report Dashboard DL- Navigate to SL- Devices by Type tab", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.eyesCheckWindow("Param Report Dashboard DL- Dashboard tab", useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	
	@Test(enabled = true, groups = { "Sanity IOS"}, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_CoverFlow()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report dashboard - DL- Device Info tab
		genMeth.swipedownIphone5Long(2, 1000);

		genMeth.clickId(genMeth, "Param Rep Cover Flow");
		genMeth.eyesCheckWindow("Param Rep Cover Flow - Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "Insert Gender (F or M)");
		genMeth.releaseOK(genMeth);
		//genMeth.clickId(genMeth, iosData.BTNokName);
		genMeth.releaseOK(genMeth);
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Param Rep Cover Flow - QR", useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, "Insert Gender (F or M)");
		genMeth.clickId(genMeth, iosData.BTNClearName);
		genMeth.clickId(genMeth, "m");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Rep Cover Flow - QR1", useEye, genMeth, skipfailure);		
		
		//Go To cover flow tab by const (females)
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Const-Female Only");
		genMeth.eyesCheckWindow("Param Rep Cover Flow - Female", useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(2, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	@Test(enabled = true, groups = { "Sanity IOS" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_Chart()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.clickId(genMeth, "Param Report Chart");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep Chart- add Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "Choose Value");
		genMeth.clickId(genMeth, "Mall of America");
		genMeth.eyesCheckWindow("Param Rep Chart - SL", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("Param Rep Chart - SL Mall of america Bar", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("Param Rep Chart - SL Mall of america in bar chart", useEye, genMeth, skipfailure);		
		
		//Naviagte to param report
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeImage[2]");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Param Rep Chart - Navigation with auto submit to Param report map", useEye, genMeth, skipfailure);		
		//genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//Go To Pie tab
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "SL-SalesbyBranch-Pie");
		genMeth.eyesCheckWindow("Param Rep Chart - SL Mall of america Pie", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "Returns");
		genMeth.eyesCheckWindow("Param Rep Chart - SL Mall of america Pie- Returnes", useEye, genMeth, skipfailure);		

		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	@Test(enabled = true, groups = { "Sanity IOS" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_EmployeeDirectory()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipedownIphone5Long(2, 1000);

		genMeth.clickId(genMeth, "Param Report ED");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep ED - Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "Choose Value");
		genMeth.clickId(genMeth, "Female");
		genMeth.eyesCheckWindow("Param Rep ED -SL MB", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep ED - Female only", useEye, genMeth, skipfailure);		
		
		//Go To Employee tab by Login variable
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "ED by Login");
		genMeth.eyesCheckWindow("Param Rep ED - ED by Login", useEye, genMeth, skipfailure);		

		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(2, 1000);

		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	@Test(enabled = true, groups = { "Sanity IOS" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_Map()

			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.clickId(genMeth, "Param Report Map");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Rep Map - Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "Choose Value");
		genMeth.clickId(genMeth, "Mall of America");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("Param Rep Map - Mall Of america chosen", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("Param Rep Map - Mall Of america on map", useEye, genMeth, skipfailure);		

		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}

	@Test(enabled = true, groups = { "Sanity IOS" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_Cards()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipedownIphone5Long(2, 1000);

		genMeth.clickId(genMeth, "Param Report Cards");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Param Rep Cards - Parameters", useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickId(genMeth, "Default");
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
		
		genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.eyesCheckWindow("Param Rep Cards - Priority = 1", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Param Rep Cards - Priority = 1 service calls", useEye, genMeth, skipfailure);		

		//Back to startup screen
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(2, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}


	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })

	public void Actions_List_Cell() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		
		genMeth.clickId(genMeth, "L_G_C Actions");
		Thread.sleep(5000);
		
		//Set slicer to one item
		genMeth.clickId(genMeth, iosData.BTNSlicer);
		genMeth.swipedownIphone5Long(2, 1000);
		genMeth.clickId(genMeth, "Service Call ID");
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, "1 Slicers");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell- List Actions", useEye, genMeth, skipfailure);		
		
		//Execute action in the first layer
		//Free text description
		genMeth.clickId(genMeth, "Description");
		boolean checkAction = genMeth.checkIsElementVisible(By.id("Descrip 1"));
		if (checkAction) {
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "2");

		} else {
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "1");

		}
		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell- cell description - Success Popup", useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, iosData.BTNokName);

		
		//Priority (Simple List MB)
		genMeth.clickId(genMeth, "Priority");
		genMeth.clickId(genMeth, "91");
		checkAction = genMeth.checkIsElementVisible(By.id("Update Pirority (MB)"));

		if (checkAction) {
			genMeth.clickId(genMeth, "90");

		}
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell- Priority (Simple List MB) - Success Popup", useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		
		//PSL By Variable (PSL gets id's by mobile date)
		genMeth.clickId(genMeth, "Branch ID");
		genMeth.clickId(genMeth, "3");
		checkAction = genMeth.checkIsElementVisible(By.id("BranchID(PSL_Var)"));
		if (checkAction) {
			genMeth.clickId(genMeth, "1");
		}

		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell - cell description (PSL By Variable) - Success Popup", useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		
		
		//Assign To (Dynamic List)
		Thread.sleep(5000);
		genMeth.clickId(genMeth, "Assigned To");
		checkAction = genMeth.checkIsElementVisible(By.id("Daniel Arkle"));
		if (checkAction) {
			genMeth.clickId(genMeth, "Daniel Arkle");
		}
		else{
			genMeth.clickId(genMeth, "Adrian Lopez");
		}
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell - cell Assign To (DL)- Success Popup", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNokName);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell - Success V icons", useEye, genMeth, skipfailure);		

		
		//Action in second layer
		genMeth.swipedownIphone5Long(4, 1000);
	
		genMeth.clickId(genMeth, iosData.BTNseeAll_ID);
		Thread.sleep(2000);
		genMeth.swipedownIphone5Long(3, 1000);
	
		//QR code
		genMeth.clickId(genMeth, "QR");
		boolean isVisible =  genMeth.checkIsElementVisible(By.id(iosData.BTNokName));
		if (isVisible){
			genMeth.clickId(genMeth, iosData.BTNokName);
		}
		
		String random = genMeth.randomString();
			
		genMeth.clickId(genMeth, iosData.BTNClearName);
		genMeth.sendXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[1]", random);

		genMeth.clickId(genMeth, iosData.BTNdoneName);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Actions_List_Cell - QR Success Popup", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNokName);
		genMeth.eyesCheckWindow("List Actions- V Success Second Layer", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		

	}
	
	
	@Test(enabled = true, testName = "Actions_List_Row", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })
	
	public void Actions_List_Row() throws ParserConfigurationException, SAXException,
	IOException, InterruptedException {

		// go to List

		genMeth.clickId(genMeth, "L_G_C Actions");
		Thread.sleep(5000);

		// Set slicer to one item
		genMeth.clickId(genMeth, iosData.BTNSlicer);
		genMeth.swipedownIphone5Long(2, 1000);
		genMeth.clickId(genMeth, "Service Call ID");
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, "1 Slicers");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("List Actions- List Actions", useEye, genMeth, skipfailure);	
		genMeth.swipedownIphone5Long(4, 1000);
		
		// Row Action (All User input parameters)
		genMeth.clickId(genMeth, "PopUp- AddRow");

		// DateTime
		genMeth.clickId(genMeth, "DateTime");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		// Free_Text
		genMeth.clickId(genMeth, "Write");
		genMeth.sendXpth(genMeth,
				"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextView[1]",
				"New Row");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		// SL DI
		// genMeth.clickId(genMeth, "DeviceType_SL_ByName");
		genMeth.clickId(genMeth, "Device_Type_SL_DI");
		genMeth.clickId(genMeth, "Mobile");
		genMeth.clickId(genMeth, "Laptop");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		// DL
		genMeth.clickId(genMeth, "Device_Model_DL");
		genMeth.clickId(genMeth, "iPhone5");

		// PSL
		genMeth.clickId(genMeth, "Items_By_Category_PSL");
		genMeth.clickId(genMeth, "Keyboard (Cat 1)");

		// QR
		genMeth.clickId(genMeth, "QR");
		genMeth.releaseOK(genMeth);
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		// SL Manual List
		genMeth.clickId(genMeth, "SL_Manual_List");
		genMeth.clickId(genMeth, "2");

		// PSL with Variable
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.clickId(genMeth, "Items_SmallerThanMobileDate_PSL");
		genMeth.clickId(genMeth, "3");

		// image
		genMeth.swipedownIphone5Long(2, 1000);

		genMeth.clickXpth(genMeth,
				"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[9]/XCUIElementTypeStaticText[1]");
		// Open Gallery
		//genMeth.clickXpth(genMeth,
			//	"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[3]");
		genMeth.clickId(genMeth, "");
		genMeth.releaseOK(genMeth);
		genMeth.clickId(genMeth, "Favorites");
		genMeth.eyesCheckWindow("List Actions- Favorites", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "Photos");
		genMeth.clickId(genMeth, iosData.BTNCancelName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		// genMeth.clickId(genMeth, "Photo, Favorite, Portrait, 10 February
		// 2016, 14:42");
		// genMeth.clickId(genMeth, "Choose");

		// Signature
		genMeth.clickXpth(genMeth,
				"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[10]/XCUIElementTypeStaticText[1]");
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.eyesCheckWindow("List Actions- Signature", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "Clear");
		genMeth.eyesCheckWindow("List Actions- Clear Signature", useEye, genMeth, skipfailure);
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("List Actions- Image/Signature set", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(12000);
		genMeth.eyesCheckWindow("List Actions- PopUp - AddRow - Sucess Popup", useEye, genMeth, skipfailure);
		genMeth.releaseOK(genMeth);
		// genMeth.clickId(genMeth, iosData.BTNokName);

		// Row Action- Variables only (Adding a row to the all parameters table)
		try {
			genMeth.clickId(genMeth, "•••");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			genMeth.clickXpth(genMeth,
					"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[23]/XCUIElementTypeButton[2]");
		}

		genMeth.clickId(genMeth, "Add VarOnlyListRow");
		Thread.sleep(14000);
		genMeth.eyesCheckWindow("List Actions- Add VarOnlyListRow - Sucess Popup", useEye, genMeth, skipfailure);
		genMeth.releaseOK(genMeth);

		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Short(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);

	}

	@Test(enabled = true, testName = "Inline row action", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })

	public void Actions_List_Inline() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		genMeth.clickId(genMeth, "L_G_C Actions");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "List (Inline)");

		// go to List
		genMeth.swipedownIphone5Long(1, 1000);
	
		//Inline- AddRow
		genMeth.clickId(genMeth, "Inline- AddRow");
		
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Actions_List_Inline- Inline parameters default", useEye, genMeth, skipfailure);		
		
		
		//DateTime
		genMeth.clickId(genMeth, "DateTime");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.sendId(genMeth, "This is default value", "1");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//SL DI
		genMeth.clickId(genMeth, "Mobile");
		
		//DL
		genMeth.clickId(genMeth, "iPhone6");
		genMeth.swipedownIphone5Long(1, 1000);
		genMeth.swipedownIphone5Short(2, 1000);

		
		//PSL (Mapped to source Column )
		genMeth.clickId(genMeth, "Keyboard (Cat 1)");
		genMeth.swipedownIphone5Long(1, 1000);
	
		//QR
 		genMeth.clickId(genMeth, "QR");
 		genMeth.releaseOK(genMeth);
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		genMeth.swipedownIphone5Short(3, 1000);
		
		//SL Manual List
		genMeth.clickId(genMeth, "2");
		genMeth.swipedownIphone5Long(2, 1000);
	

		//PSL (Mapped to Variable/Auto fill - Mobile Date)
		//genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[23]");
		//genMeth.clickId(genMeth, "2");

		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS Actions_List_Inline- Actions_List_Inline Success Popup", useEye, genMeth, skipfailure);		
		genMeth.releaseOK(genMeth);
		
		
		//Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1, 1000);

		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		

	}
	
	
	@Test(enabled = true, testName = "Actions_Grid_One_Layer_CellAction", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })

	public void Actions_Grid_One_Layer_CellAction() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		// go to List
		genMeth.clickId(genMeth, "L_G_C Actions");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - One Layer");  
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer main view", useEye, genMeth, skipfailure);		

		//USER INPUT = Free Text (Description)
		boolean isTextDisplayed = genMeth.checkIsElementVisible(By.id("Descrip 1"));
		if (isTextDisplayed){
			genMeth.clickId(genMeth, "Descrip 1");
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "2");
		}
		else{
			genMeth.clickId(genMeth, "Descrip 2");
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "1");
		}
		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		Thread.sleep(10000);	
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- description (free text)", useEye, genMeth, skipfailure);		
		genMeth.releaseOK(genMeth);

		//USER INPUT = Simple List MB (Priority)
		isTextDisplayed = genMeth.checkIsElementVisible(By.id("90"));
		if (isTextDisplayed) {
			genMeth.clickId(genMeth, "90");
			genMeth.clickId(genMeth, "91");

		}
		else{
			genMeth.clickId(genMeth, "91");
			genMeth.clickId(genMeth, "90");
		}
		
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- Priority (Simple List MB)", useEye, genMeth, skipfailure);		
		genMeth.releaseOK(genMeth);

		// USER INPUT = PSL (ItemID)
		String xPath= "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]";
		genMeth.clickXpth(genMeth, xPath);
		genMeth.clickId(genMeth, "Video card  (Cat 1)");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- ItemID (PSL)", useEye, genMeth, skipfailure);
		genMeth.releaseOK(genMeth);
		
		genMeth.swipeRightIphone5Long(1, 1000);
		genMeth.swipeRightIphone5Short(2, 1000);


		//USER INPUT = QR
		String xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]";
		genMeth.clickXpth(genMeth, xpath);
		Thread.sleep(5000);
		genMeth.releaseOK(genMeth);
		String Random = genMeth.randomString();
		genMeth.clickId(genMeth, iosData.BTNClearName);

		//insert the random string to the QR & submit
		//QR textField xPath
		xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[1]";
		genMeth.sendXpth(genMeth, xpath, Random );
		
		/*
		isTextDisplayed = genMeth.checkIsElementVisible(By.id("01"));
		if (isTextDisplayed) {
			genMeth.clickId(genMeth, "01");
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "2");

		}
		else{
			genMeth.clickId(genMeth, "02");
			genMeth.clickId(genMeth, iosData.BTNkeyboardDelete);
			genMeth.clickId(genMeth, iosData.BtnkeyboardMoreNumbers);
			genMeth.clickId(genMeth, "1");
			
		}
		*/
		genMeth.clickId(genMeth, iosData.BTNdoneName);	
		
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- QR", useEye, genMeth, skipfailure);
		genMeth.releaseOK(genMeth);

		
		//USER INPUT = Simple List DI (Status) 
		xPath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]";
		genMeth.clickXpth(genMeth, xPath);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- Status (Simple List DI)", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "Closed");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer- Grid One Layer- Status success (Simple List DI)", useEye, genMeth, skipfailure);
		genMeth.releaseOK(genMeth);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
	}
		
	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity IOS" })

	public void Actions_Grid_One_Layer_RowAction() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		// go to List
		genMeth.clickId(genMeth, "L_G_C Actions");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - One Layer"); 
		genMeth.swipeRightIphone5Long(2, 1000);
		genMeth.swipeRightIphone5Short(1, 1000);


		//Row (AddRowAllParameters)
		genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeOther[5]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_One_Layer_RowAction - Grid One Layer- Row parameters before insert", useEye, genMeth, skipfailure);		

		//DateTime
		genMeth.clickId(genMeth, "DateTime");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//Free text
		genMeth.clickId(genMeth, "Free_Text1");
		Thread.sleep(2000);
		genMeth.sendXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextView[1]", "New row");			   
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//DateTime
		genMeth.clickId(genMeth, "DateTime");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//QR
		genMeth.clickId(genMeth, "QR_");
		genMeth.releaseOK(genMeth);
		Thread.sleep(3000);
		genMeth.sendXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]/XCUIElementTypeTextField[1]", "New QR");
		genMeth.clickId(genMeth, iosData.BTNdoneName);

		//SL Manual List
		genMeth.clickId(genMeth, "SL_Manual_List_");
		genMeth.clickId(genMeth, "1");

		//SL DI
		genMeth.clickId(genMeth, "Device_Type_SL_DI_");
		genMeth.clickId(genMeth, "Mobile");

		//DL
		genMeth.clickId(genMeth, "Device_Model_DL_");
		genMeth.clickId(genMeth, "iPhone6");
		
		//PSL (Source = column)
		genMeth.clickId(genMeth, "Items_By_Category_PSL");
		genMeth.clickId(genMeth, "Power Supply (Cat 1)");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_RowAction - Grid One Layer- Row parameters after insert", useEye, genMeth, skipfailure);	
		
		//PSL with Variable
		genMeth.swipedownIphone5Short(2, 1000);
		genMeth.clickId(genMeth, "Items_SmallerThanMobileDate_PSL");
		genMeth.clickId(genMeth, "3");
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_RowAction - Grid One Layer- after action executed", useEye, genMeth, skipfailure);		
		genMeth.releaseOK(genMeth);

		
		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Short(1, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		

	}
	
	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the Grid two layer actions",
			groups = { "Sanity IOS" })

	public void Actions_Grid_Two_Layer() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		genMeth.clickId(genMeth, "L_G_C Actions");
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - Two Layers");  
		genMeth.eyesCheckWindow("iOS Actions_Grid_Two_Layer- Grid Two Layer main view", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "3");
		
		//DL
		genMeth.clickId(genMeth, "ItemID");
		genMeth.clickId(genMeth, "Keyboard (Cat 1)");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS Actions_Grid_Two_Layer- Grid Two Layers- Update ItemID DL", useEye, genMeth, skipfailure);		
		genMeth.releaseOK(genMeth);
		
		//Row Action
		genMeth.clickId(genMeth, "m");
		genMeth.clickId(genMeth, "UpdateWithTableParam");
		genMeth.clickId(genMeth, "DummyParam1");
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickId(genMeth, "TableParams");
		genMeth.clickId(genMeth, "add icon table");
		genMeth.clickId(genMeth, "Priority");
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickId(genMeth, "Status");
		genMeth.clickId(genMeth, "Open");
		genMeth.clickId(genMeth, iosData.BTNsave);
		genMeth.eyesCheckWindow("iOS Actions_Grid_Two_Layer- Grid Two Layers- Table Parameter filled", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("iOS Actions_Grid_Two_Layer- Grid Two Layers- All parameters are filled", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNsubmit_ID);
		Thread.sleep(10000);
		
		//Check the push notification
		genMeth.eyesCheckWindow("iOS Actions_Grid_Two_Layer- Grid Two Layers- Action Success", useEye, genMeth, skipfailure);	
		genMeth.releaseOK(genMeth);
		
		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
	}


	@Test(enabled = true, groups = {"Sanity IOS"}, testName = "Sanity", description = "Slicer report")
	public void slicerReport() throws InterruptedException, IOException{
		
		// go to List
		genMeth.swipedownIphone5Long(3, 1000);
		genMeth.clickId(genMeth, "Slicer report");
		genMeth.clickId(genMeth, iosData.BTNSlicer);
		
		genMeth.clickId(genMeth, "BranchID");
		genMeth.clickId(genMeth, "7");
		genMeth.eyesCheckWindow("iOS Slicer Report- branchID selected", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("iOS Slicer Report- List (BranchID=7)", useEye, genMeth, skipfailure);		

		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		
		genMeth.clickId(genMeth, "Slicer Grid");
		genMeth.eyesCheckWindow("iOS Slicer Report- Grid (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Cover Flow");
		genMeth.eyesCheckWindow("iOS Slicer Report- Cover Flow (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Dashboard");
		genMeth.eyesCheckWindow("iOS Slicer Report- Dashboard (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Map");
//		genMeth.clickName(genMeth, "Garden State Plaza, Paramus, NJ, 1 item");
		genMeth.eyesCheckWindow("iOS Slicer Report- Map (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Cards");
		genMeth.eyesCheckWindow("iOS Slicer Report- Cards (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer News");
		genMeth.eyesCheckWindow("iOS Slicer Report- News (BranchID=7)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Bar Chart");
		genMeth.eyesCheckWindow("iOS Slicer Report- Bar chart empty slicing", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, iosData.BTNSlicer);
		genMeth.clickId(genMeth, "BranchID");
		genMeth.clickId(genMeth, "7");
		genMeth.clickId(genMeth, "Aventura Mall");
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("iOS Slicer Report- Bar chart Aventura Mall", useEye, genMeth, skipfailure);		


		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(3, 1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	
	
	@Test(enabled = true, groups = {"Sanity IOS"}, testName = "JTR_SameReport", description = "JTR_SameReport")
	public void JTR_SameReport() throws InterruptedException, IOException{
				

		// Open the JTR App
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "JTR App");
		genMeth.clickId(genMeth, "JTR- Same Report");
		Thread.sleep(5000);
		
		//JTR from List
		genMeth.clickId(genMeth, "Service Call ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from List",useEye, genMeth, skipfailure);
	
		// JTR from Grid Two Layers
		genMeth.clickId(genMeth, "1");
		genMeth.clickId(genMeth, "Service Call ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Grid", useEye, genMeth, skipfailure);
	
		// JTR from Bar Chart
		//genMeth.clickId(genMeth, "Aventura Mall");
		genMeth.clickId(genMeth, iosData.Icon_BarChart_Navigation);
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Bar Chart", useEye, genMeth, skipfailure);
		
		// JTR from Dashboard
		genMeth.clickId(genMeth, "Service Call ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Dashboard", useEye, genMeth, skipfailure);

		// JTR from Grid One Layer
		genMeth.clickId(genMeth, "H");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Grid One Layer", useEye, genMeth, skipfailure);
		
		// JTR from Map
		
		genMeth.clickId(genMeth, "19501 Biscayne Blvd, Aventura, FL 33180, 1 item");
		genMeth.clickId(genMeth, iosData.Icon_Map_Navigation);
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Map to Pie Chart", useEye, genMeth, skipfailure);

		// JTR from Pie Chart
		//Limor will add label in the next version - need to check if it will work ????????????
		//genMeth.clickId(genMeth, iosData.Icon_PieChart_Navigation);
		genMeth.clickId(genMeth, "   Aventura Mall         ");		
		
		
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Pie Chart to ED", useEye, genMeth, skipfailure);
		
		
		// JTR from ED
		genMeth.clickId(genMeth, "Abra");
		genMeth.clickId(genMeth, "Cell Phone");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from ED to Cover Flow", useEye, genMeth, skipfailure);
		
		// JTR from Cover Flow
		genMeth.clickId(genMeth, "Name");
		Thread.sleep(12000);
		genMeth.eyesCheckWindow("iOS JTR Same report - JTR from Cover Flow to List", useEye, genMeth, skipfailure);
		// Verify Startup screen is open
	


		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "SQL Golden App");
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	
	@Test(enabled = true, groups = {"Sanity IOS"}, testName = "JTR_SameReport", description = "JTR_SameReport")
	public void JTR_Param_Report_AutoSubmit() throws InterruptedException, IOException{
				

		// Open the JTR App
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "JTR App");
		genMeth.clickId(genMeth, "JTR- Nav to Param");
		Thread.sleep(5000);
		
		// JTR from List to HIDDEN REPORT
		genMeth.clickId(genMeth, "int");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from List To Hidden Report", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//JTR from List
		genMeth.clickId(genMeth, "id");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from List",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		
		// JTR from Grid One Layer
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes2");
		Thread.sleep(5000);
		genMeth.clickId(genMeth, "1");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Grid", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		// JTR from Dashboard
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes3");
		Thread.sleep(5000);
		genMeth.clickId(genMeth, "id");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Dashboard", useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		// JTR from Map
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes4");
		Thread.sleep(5000);
		genMeth.clickId(genMeth, "690 W Dekalb Pike, King of Prussia, Pennsylvania, 2 items");
		genMeth.clickId(genMeth, iosData.Icon_Map_Navigation);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Map", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
				
		// JTR from ED
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes5");
		genMeth.clickId(genMeth, "4.7");
		genMeth.clickId(genMeth, "id");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from ED", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
				
		// JTR from Cover Flow
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes6");
		Thread.sleep(2000);
		genMeth.clickId(genMeth, "id");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Cover Flow", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

				
		// JTR from Bar Chart
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes7");
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.Icon_BarChart_Navigation);
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Bar Chart", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
				
		// JTR from Pie Chart
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "AutoSubAllDataTypes8");
		Thread.sleep(2000);
		//Limor will add label in the next version - need to check if it will work ????????????
		//genMeth.clickId(genMeth, iosData.Icon_PieChart_Navigation);
		genMeth.clickId(genMeth, "   Aventura Mall         ");			
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("iOS JTR Param report - JTR from Pie Chart", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
				

		// Verify Startup screen is open
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "SQL Golden App");
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	

	@Test(enabled = true, testName = "Regression", retryAnalyzer = Retry.class, description = "Check the Grid one layer Row action",
			groups = { "Sanity IOS" })
	public void JTR_To_Slicer_Report() throws ParserConfigurationException, SAXException,
	IOException, InterruptedException {
		
		// Open the JTR App
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "JTR App");
		genMeth.clickId(genMeth, "JTR- To SlicerReport");
		Thread.sleep(18000);
		
		// JTR from List to HIDDEN Report
		genMeth.clickId(genMeth, "Service Call ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from List to HIDDEN Report", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		//JTR from List
		genMeth.clickId(genMeth, "Branch ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from List",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
	
		// JTR from Grid Two Layers
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - Two Layers");
		genMeth.clickId(genMeth, "3");
		genMeth.clickId(genMeth, "Branch ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Grid Two Layers",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		
		// JTR from Bar Chart
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "JTR- BarChart");
		genMeth.clickId(genMeth, iosData.Icon_BarChart_Navigation);
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Bar Chart",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		
		// JTR from Dashboard
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Dashboard DefLayouts");
		genMeth.clickId(genMeth, "Branch ID");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Dashboard", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		// JTR from Grid One Layer
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Grid - One Layer");
		genMeth.clickId(genMeth, "3");
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Grid One Layer", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		
		// JTR from Map
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "JTR- Map");
		genMeth.clickId(genMeth, "690 W Dekalb Pike, King of Prussia, Pennsylvania, 1 item");
		genMeth.clickId(genMeth, iosData.Icon_Map_Navigation);
		Thread.sleep(18000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Map", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		// JTR from Pie Chart
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "JTR- Pie Chart");
		genMeth.clickId(genMeth, "   Aventura Mall         ");			
		Thread.sleep(20000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Pie Chart", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);

		
		// JTR from ED
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Employee Directory");
		genMeth.clickId(genMeth, "Legal");
		genMeth.clickId(genMeth, "BranchID");
		Thread.sleep(20000);
		genMeth.eyesCheckWindow("JTR To Slicer report - JTR from ED", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);		
		
		// JTR from Cover Flow
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "JTR- Cover Flow");
		genMeth.clickId(genMeth, "Name");
		Thread.sleep(20000);
		genMeth.eyesCheckWindow("iOS JTR To Slicer report - JTR from Cover Flow", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);		
	
		// Verify Startup screen is open
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		Thread.sleep(2000);
		genMeth.clickId(genMeth, iosData.Icon_AllApps_Name);
		genMeth.clickId(genMeth, "SQL Golden App");


	}


	

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		
		if (driver != null){
		try {
			//driver.resetApp();   -->  reinstall the app (login screen will be displayed)
			
			//Logout from the app
			driver.findElementById(iosData.BTNsettingsIconXpth);
			//genMeth.clickId(genMeth, iosData.BTNsettingsIconXpth);
			driver.findElementById(iosData.BTNlogoutName).click();
			//genMeth.clickId(genMeth, iosData.BTNlogoutName);		
			
			driver.removeApp(appIdentifier);
			driver.quit();
			//service.stop();

			/*
			boolean isAppInstalled = driver.isAppInstalled(appIdentifier);
			if (isAppInstalled) {
				driver.removeApp(appIdentifier);
			}
			*/
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//service.stop();
			driver.quit();

			e.printStackTrace();
		}
		}
		else{
			driver.quit();
			//service.stop();
			
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
  
  
  
  
	/*
	
	@Test(enabled = false, groups = {"Sanity IOS1_deprecated"}, testName = "Sanity", description = "Slicer report")
	public void slicerReportWithSecurityFilter() throws InterruptedException, IOException{
		
		// go to List
		genMeth.swipedownIphone5Long(1000);
		genMeth.clickId(genMeth, "SlicerReport_Sfilter");
		genMeth.clickId(genMeth, iosData.BTNSlicer);
		
		genMeth.clickId(genMeth, "BranchID");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter - branchID 1,3 only", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, "3");
		genMeth.clickId(genMeth, iosData.BTNBackName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- List (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer One Layer");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Grid One Layer (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Two Layer");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Grid Two Layer (BranchID=3)", useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "3");
		genMeth.clickId(genMeth, iosData.IconBack_Name);
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Cover Flow");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Cover Flow (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Dashboard");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Dashboard (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Map");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Map (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Cards");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Cards (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer News");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- News (BranchID=3)", useEye, genMeth, skipfailure);		
		
		genMeth.clickId(genMeth, iosData.TabBarTitle_Name);
		genMeth.clickId(genMeth, "Slicer Bar Chart");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter- Bar Chart (BranchID=3)", useEye, genMeth, skipfailure);		

		// Verify Startup screen is open
		genMeth.clickId(genMeth, iosData.IconBack_Nav_Name);
		genMeth.swipeUpIphone5Long(1000);
		genMeth.swipeUpIphone5Long(1000);
		genMeth.eyesCheckWindow("Default app is open - SQL Golden App", useEye, genMeth, skipfailure);		
		
	}
	
	
	
	@Test(enabled = false, groups = { "Sanity IOS__" }, testName = "Sanity Tests", description = "login with bad/missing credentials", retryAnalyzer = Retry.class)
	public void badCredentials() throws Exception, Throwable {

		genMeth.signOutFromStartup(genMeth);
		// Login with bad user name
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailID, "bad name");
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordID, iosData.passwordProd);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		
		genMeth.clickId(genMeth, iosData.BTNokName);

		// Login with bad password 
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailID, iosData.userQA);
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordID, "bad password");
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		
		// Login with bad user name & password 
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailID, "bad name");
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordID, "bad password");
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Login Failed"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		
		// Login with empty Name
		genMeth.clearId(genMeth, iosData.TEXTFIELDemailID);
		genMeth.sendId( genMeth, iosData.TEXTFIELDpasswordID, iosData.passwordQA);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		// Login with empty Password
		genMeth.sendId( genMeth, iosData.TEXTFIELDemailID, iosData.userQA);
		genMeth.clearId(genMeth, iosData.TEXTFIELDpasswordID);
		genMeth.clickId(genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);

		// Login with empty Name & password
		genMeth.clearId(genMeth, iosData.TEXTFIELDemailID);
		genMeth.clearId(genMeth, iosData.TEXTFIELDpasswordID);
		genMeth.clickId(genMeth, iosData.BTNloginID);
		genMeth.isElementVisible(By.name("Bad Request"));
		genMeth.clickId(genMeth, iosData.BTNokName);
		
		// Forgot your password Negative (attempt to restore password with a non
		// existing email)

		// Forgot your password Positive (attempt to restore password with an
		// existing email)
	
	}
	

	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Switching from Foreground to Background and vice versa use cases",
			groups = { "Sanity IOS__" })
	public void foregroundBackgroundSwitch() throws Exception, Throwable {

		//Take the app to background & foreground x times
		
		
		//Take the app to sleep/lock  x times
	

	}
	
	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "connection lost handling", description = "Checking how the app owrks while connection is lost & back again", dependsOnGroups = { "Sanity*" },
			groups = { "Sanity IOS__" })
	public void connectionLost() throws InterruptedException, IOException,
			ParserConfigurationException, SAXException {

	}
	
	
	
	

	
	
	@Test (enabled = true ,testName = "Sample App Dashboard DailySales", retryAnalyzer = Retry.class, description = "Dashboard DailySales" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationDashboardDailySales() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		
//Logout from startup page
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, iosData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  iosData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 5.2");
		
		//useEye = true;
	
// Login to sample app & open Dashboard report

		genMeth.eyesCheckWindow(eyes, "SampleApp Main screen", useEye);
		genMeth.clickName(genMeth,  iosData.DashboardName);
		genMeth.eyesCheckWindow(eyes, "Dashboard Tab", useEye);
//		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone5(500);
		genMeth.eyesCheckWindow(eyes, "World wide orders Tab", useEye);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
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
		genMeth.clickName(genMeth, iosData.BTNSlicer);
		genMeth.clickId(genMeth, iosData.BranchID);
		genMeth.clickId(genMeth, iosData.DestinyUSAID);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines / Destiny USA", useEye);
		
//Clear the Slicer
		genMeth.clickName(genMeth, iosData.BTNSlicer);
		genMeth.clickName(genMeth, iosData.BTNClearName);
		genMeth.clickName(genMeth, iosData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
//Open Daily Sales from main screen
		genMeth.clickId(genMeth, iosData.DailySalesID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar (no back icon)- Show All", useEye);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
		genMeth.clickName(genMeth, "M");
		
	}
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

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
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 3", useEye);	
		
		//Open the Slicer
		genMeth.clickName(genMeth, iosData.BTNSlicer);
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
		Thread.sleep(2000);
		genMeth.clickName(genMeth, iosData.BTNBackName);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
				
	}
	
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

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
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);

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
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);

	}
 

	@Test (enabled = true ,testName = "Sample App OrderLookup Operation", retryAnalyzer = Retry.class, description = "OrderLookup Operation" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

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
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
		//Operations
		genMeth.clickXpth(genMeth, " //UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[7]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Inventory", useEye);
		//Open grid second layer
		genMeth.clickName(genMeth, iosData.MallOfAmerica_Id);
		genMeth.eyesCheckWindow(eyes, "Inventory second layer", useEye);
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		
		genMeth.clickName(genMeth, "Inventory");
		genMeth.clickName(genMeth, "Orders");
		/*genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);

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
		genMeth.clickName(genMeth, iosData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, iosData.Icon_AllApps_Name);
		
	}
	
	@Test (enabled = true ,testName = "Sample App Technicians", retryAnalyzer = Retry.class, description = "Technicians" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

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
 
	
	*/





