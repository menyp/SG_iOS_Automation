package Native;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;

import io.appium.java_client.remote.IOSMobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
//import com.applitools.eyes.StitchMode;
import com.google.common.base.Function;

public class IosMethods {

	IOSDriver<MobileElement> driver;
	IosElements iosData;
	Eyes eyes = new Eyes();
	
	//Boolean useEye = true;
	//boolean skipfailure = true;
	IosMethods genMeth;

	/*
	public IosMethods(){
		
		
		
		
		
	}
	
	*/

	public void cleanLoginIos(IosMethods genMeth,String user, String password) throws ParserConfigurationException, SAXException,

			IOException, InterruptedException {
		//set Publisher & Authentication server
		//Open the Servers settings screen
        genMeth.clickXpth(genMeth, "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[2]");

		genMeth.clickId(genMeth, "QA Publisher");
		genMeth.clickId(genMeth, "QA Authentication");

		
		
		//Publisher name (Server label)
		//genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "QA Publisher");
		
		//Publisher URL (Server URL)
		//genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[2]", "https://publisher.skygiraffe.com/api/v1/");
		//genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		//Authentication server
		//genMeth.clickId(genMeth, "Add Authentication Server");
		//Publisher name (Server label)
		//genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "QA Authentication");
				
		//Publisher URL (Server URL)
		//genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[2]", "https://skygiraffeauthorizationserver.skygiraffe.com/oauth2/token");
		//genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		
		// Check language making sure keyboard is set to English
		//MobileElement EmailField = genMeth.returnXpth(driver, genMeth,iosData.TEXTFIELDemailID);
		//MobileElement PasswordField = genMeth.returnXpth(driver, genMeth,iosData.TEXTFIELDpasswordID);

		// Make sure that the English keyboard is open
		//driver.tap(1, EmailField, 1000);
		genMeth.clickId(genMeth, iosData.TEXTFIELDemailID);
		
		genMeth.setEnglishKeyboard(genMeth);

		// Make sure that the email & password fields are empty
		//boolean isEmailEmpty = genMeth.checkIsElementVisible(By.id("Username"));
	//	boolean isEmailEmpty = genMeth.checkIsElementVisible(By.id("E-Mail"));
		boolean isEmailEmpty = genMeth.checkIsElementVisible(By.id(iosData.TEXTFIELDemailID));
		boolean isPasswordEmpty = genMeth.checkIsElementVisible(By.id(iosData.TEXTFIELDpasswordID));

		if (!isEmailEmpty) {

			genMeth.clickId(genMeth, iosData.BTNclearText_Name);

		}
		if (!isPasswordEmpty) {
			//driver.tap(1, PasswordField, 1000);
			genMeth.clickId(genMeth, iosData.TEXTFIELDpasswordID);
			genMeth.clickId(genMeth, iosData.BTNclearText_Name);

		}
		//genMeth.sendXpth(genMeth, iosData.TEXTFIELDemailXpth, user);
		genMeth.sendId(genMeth, iosData.TEXTFIELDemailID, user);
		//genMeth.sendXpth(genMeth, iosData.TEXTFIELDpasswordXpth, password);		
		genMeth.sendId(genMeth, iosData.TEXTFIELDpasswordID, password);
	    //genMeth.clickId(genMeth, "return");
		genMeth.clickId(genMeth, iosData.BTNloginID);
		boolean isAllow = genMeth.checkIsElementVisible(By.id("Allow"));
		if (isAllow) {
			genMeth.clickId(genMeth, "Allow");
		}
	

		// Check if default app is open
//		genMeth.eyesCheckWindow(eyes, "Default app is open - SQL Golden App",useEye);

		// genMeth.clickXpth(genMeth, iosData.IconBackToApplicationList_xpth);

		
	}
	public void eyesCheckWindow(String testName, Boolean useEye, IosMethods genMeth, boolean  skipfailure)

			throws InterruptedException, IOException {
			
		
		if (useEye) {
			Thread.sleep(5000);
			eyes.setMatchTimeout(20);
			eyes.setApiKey("Hbh6716cKDCgn8a9bMAREPM105nbW109PQe0993So5GwFpNM110");

	        String version = "0.2";
	        
	        // Define the OS and hosting application to identify the baseline
	        eyes.setHostOS("Mac");
			eyes.setHostApp("My maxthon browser");
			
			BufferedImage img;

			eyes.setMatchLevel(MatchLevel.STRICT);
			
			//eyes.open("SG_Android", testName, new RectangleSize(785, 1087));  compatible with the old Samsung
			eyes.open("SG_iOS", testName, new RectangleSize(785, 1087));  
			
			// Load page image and validate
			File scrFile = (driver.getScreenshotAs(OutputType.FILE));
			img = ImageIO.read(scrFile);

			// Visual validation point #1
			Rectangle rect = new Rectangle(0, 0, 640, 1136);

			eyes.setSaveFailedTests(false);

			img = genMeth.cropImage(img, rect);
			eyes.checkImage(img, "Sample");

				if (skipfailure) {
					// Use the below code instead of eyes.close(); --> It will allow to continue the test even if the UI testing will fail
					com.applitools.eyes.TestResults testResult = eyes.close(false);
					

				}

				else {
					
					eyes.close();

			}

		}
		
	}

	
	/*
	
	public void eyesCheckWindow(String testName, Boolean useEye, IosMethods genMeth, boolean  skipfailure)

			throws InterruptedException, IOException {
			
		
		if (useEye) {
			Thread.sleep(2000);
			eyes.setMatchTimeout(20);
			
			eyes.setApiKey("Hbh6716cKDCgn8a9bMAREPM105nbW109PQe0993So5GwFpNM110");
			 //Switch between the versions to generate test failure.
	        String version = "0.2";
	        
	        // Define the OS and hosting application to identify the baseline
	        eyes.setHostOS("Mac");
			eyes.setHostApp("My maxthon browser");
			
			BufferedImage img;

			eyes.setMatchLevel(MatchLevel.STRICT);
			
			//eyes.open("SG_Android", testName, new RectangleSize(785, 1087));  compatible with the old Samsung
			eyes.open("SG_Android", testName, new RectangleSize(785, 1087));  
			// Load page image and validate
			File scrFile = (driver.getScreenshotAs(OutputType.FILE));
			img = ImageIO.read(scrFile);

			// Visual validation point #1
			//Rectangle rect = new Rectangle(0, 0, 1080, 1940);
			Rectangle rect = new Rectangle(0, 0, 1080, 1810);
			//eyes.setSaveNewTests(true);
			eyes.setSaveFailedTests(false);

			img = genMeth.cropImage(img, rect);
			eyes.checkImage(img, "Sample");
	            

				if (skipfailure) {
					// Use the below code instead of eyes.close(); --> It will allow to continue the test even if the UI testing will fail
					com.applitools.eyes.TestResults testResult = eyes.close(false);
					

				}

				else {
					
					eyes.close();

			}

		}
	}
*/

	
	
	public void killAppAndroid(IOSDriver<MobileElement> driver)
			throws InterruptedException, IOException {

		// driver.removeApp("com.pogoplug.android");
		driver.resetApp();
		// driver.removeApp(bundleId);

		try {
			driver.quit();
		} catch (Exception x) {
			// swallow exception
		}
		// driver = genMeth.setCapabilitiesIos();
	}

	public void signOutFromStartup(IosMethods genMeth)
			throws InterruptedException, IOException {
	//	genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickXpth(genMeth, iosData.BTNsettingsIconXpth);
		genMeth.clickXpth(genMeth, iosData.BTNlogoutXpth);

	}

	public void scroll(IOSDriver<MobileElement> driver, String direction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", direction);
		js.executeScript("mobile: scroll", scrollMap);
	}

	public void scrollUp(IOSDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", "up");
		js.executeScript("mobile: scroll", scrollMap);
	}

	public void scrollDown(IOSDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", "down");
		js.executeScript("mobile: scroll", scrollMap);
	}

	public IOSDriver<MobileElement> setCapabilitiesIos(IosMethods genMeth)
			throws IOException, ParserConfigurationException, SAXException,
			InterruptedException {
	
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,genMeth.getValueFromPropFile("deviceName"));
		//capabilities.setCapability("device",genMeth.getValueFromPropFile("deviceName"));


		//capabilities.setCapability(MobileCapabilityType.UDID, genMeth.getValueFromPropFile("udid_iPhone6"));
		capabilities.setCapability(MobileCapabilityType.UDID, genMeth.getValueFromPropFile("udid_iPhone5S"));

		//capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, genMeth.getValueFromPropFile("appIdentifier"));

		capabilities.setCapability("platformVersion","10.3.3");

		//capabilities.setCapability(CapabilityType.VERSION,genMeth.getValueFromPropFile("CapabilityType.VERSION"));
		
		
		capabilities.setCapability("app",genMeth.getValueFromPropFile("appPath"));
	    //capabilities.setCapability(MobileCapabilityType.APP, genMeth.getValueFromPropFile("appPath"));
		//capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, genMeth.getValueFromPropFile("appPath"));

		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
		//capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability("wdaLocalPort", 8100);

		
		
		//capabilities.setCapability("xcodeConfigFile","/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		

		capabilities.setCapability("xcodeOrgId", "7Y5J2RJXYV");
		capabilities.setCapability("xcodeSigningId", "iPhone Developer");

		
		
		//capabilities.setCapability("keychainPath", "/Users/menypeled/Library/Keychains/MyKeychaitest1.keychain-db"); 
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");


		capabilities.setCapability("useNewWDA", "false");
		//capabilities.setCapability("wdaLaunchTimeout", 10000);
		capabilities.setCapability("updatedWDABundleId", genMeth.getValueFromPropFile("appIdentifier"));
		//capabilities.setCapability("wdaConnectionTimeout", 100000);

		//capabilities.setCapability("autoAcceptAlerts", true);
		//capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, genMeth.getValueFromPropFile("Alert"));
		//capabilities.setCapability(IOSMobileCapabilityType.AUTO_DISMISS_ALERTS, "True");
		
		capabilities.setCapability("newCommandTimeout", 100000);
		
		
		try {
			
		 //   AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		   // service.stop();

			//driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			try {
				driver = new IOSDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				driver.quit();
				driver = new IOSDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);

			}
			genMeth.releaseAllow(genMeth);
			

			// XCUIElementTypeButton

		}

		catch (MalformedURLException e) {

			genMeth.takeScreenShot(driver, genMeth,
					"Faliled to open iOS driver");
			org.testng.Assert
					.fail("WebElement" + " Faliled to open iOS driver");
		}

		// genMeth.sendNotificationHandle(genMeth);

		return driver;
	}
	
	public AppiumDriverLocalService startAppiumService() {

		 //AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		AppiumServiceBuilder c = new AppiumServiceBuilder();
		 AppiumDriverLocalService service =  AppiumDriverLocalService.buildService(c.usingPort(4724).withIPAddress("0.0.0.0"));
						
		boolean isServiceRunning =  service.isRunning();
		if (isServiceRunning){
			
			service.stop();
		}
		service.start();
		return service;

	}


	public String getValueFromPropFile(String key) {
		Properties properties = new Properties();
		String value = "";
		try {

			properties.load(getClass().getResourceAsStream(
					"/resources/config.properties"));
			// properties.load(getClass().getResourceAsStream("/resources/webui.properties"));
			{
				value = properties.getProperty(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	public void takeScreenShot(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String imageName) throws IOException {

		File scrFile = (driver.getScreenshotAs(OutputType.FILE));
		String currentTime = genMeth.currentTime();
		String currentDate = genMeth.currentDate();

		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		String imagePath = genMeth.getValueFromPropFile("screenshotPath")
				+ currentDate + "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}

	public void takeScreenShotPositive(IosMethods genMeth, String imageName)
			throws IOException {
		String currentTime = genMeth.currentTime();
		File scrFile = (driver.getScreenshotAs(OutputType.FILE));
		String currentDate = genMeth.currentDate();

		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		String imagePath = genMeth
				.getValueFromPropFile("screenshotPathPositive")
				+ currentDate
				+ "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}

	/*
	 * ***************************************************
	 * Web Element Handling *
	 * ***************************************************
	 */

	// ==================== RETURN ELEMENT

	public WebElement returnCss(IOSDriver<MobileElement> driver,
			String cssSelector) throws InterruptedException {

		IosMethods genMeth = new IosMethods();
		try {

			genMeth.fluentwait(driver, By.cssSelector(cssSelector));

		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement 'by css' can't be located");

		}

		WebElement myElement = genMeth.fluentwait(driver,
				By.cssSelector(cssSelector));
		return myElement;
	}

	public MobileElement returnId(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String id) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.id(id));

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

		}

		MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
		return myElement;

	}

	public WebElement returnClassName(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String className) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className));
		}

		catch (Exception e) {

			org.testng.Assert.fail(className + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver,
				By.className(className));
		return myElement;
	}

	public MobileElement returnXpth(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String xpth) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.xpath(xpth));

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpth + " didn't display");
		}

		MobileElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
		return myElement;

	}

	public MobileElement returnName(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String name) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.name(name));

		}

		catch (Exception e) {

			org.testng.Assert.fail(name + " didn't display");

		}

		MobileElement myElement = genMeth.fluentwait(driver, By.name(name));
		return myElement;

	}

	public WebElement returnBy(IOSDriver<MobileElement> driver,
			IosMethods genMeth, By by) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, by);

		}

		catch (Exception e) {

			org.testng.Assert.fail(by + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver, by);
		return myElement;

	}

	// ========= CLICK an ELEMENT
	// =========================================================================

	public void clickBy(IOSDriver<MobileElement> driver, IosMethods genMeth,
			By by) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}

	public void tapBy(IOSDriver<MobileElement> driver, IosMethods genMeth, By by)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			driver.tap(1, myElement, 1000);
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}

	public void clickCss(IOSDriver<MobileElement> driver, IosMethods genMeth,
			String cssSelector) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.cssSelector(cssSelector));
			myElement.click();

		}

		catch (Exception e) {

			org.testng.Assert.fail(cssSelector + " didn't display");

		}

	}


	public void clickId(IosMethods genMeth, String id)
			throws InterruptedException, IOException {

		try {
			MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.click();

			// driver.findElementById(id).click();

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, id);
			org.testng.Assert.fail(id + " didn't display");

		}
	}

	


	public void clickClassName(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String className) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className)).click();

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + " didn't display");

		}

	}

	public void clickXpth(IosMethods genMeth, String xpth)
			throws InterruptedException, IOException {

		By by = By.xpath(xpth);

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();
			// driver.findElementByXPath(xpth).click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpth);
			org.testng.Assert.fail(xpth + " didn't display");

		}

	}

	public void tapXpth(IosMethods genMeth, String xpth)
			throws InterruptedException, IOException {

		By by = By.xpath(xpth);

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			driver.tap(1, myElement, 1000);
		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpth);
			org.testng.Assert.fail(xpth + " didn't display");

		}

	}

	public void clickName(IosMethods genMeth, String name)
			throws InterruptedException, IOException {

		try {

			MobileElement myElement = genMeth.fluentwait(driver, By.name(name));
			myElement.click();
		}

		catch (Exception e) {
			// String testName = new
			// Object(){}.getClass().getEnclosingMethod().getName();
			genMeth.takeScreenShot(driver, genMeth, name);
			org.testng.Assert.fail(name + " didn't display");

		}

	}

	public void tapName(IosMethods genMeth, String name)
			throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.name(name));
			driver.tap(1, myElement, 1000);

		}

		catch (Exception e) {
			// String testName = new
			// Object(){}.getClass().getEnclosingMethod().getName();
			genMeth.takeScreenShot(driver, genMeth, name);
			org.testng.Assert.fail(name + " didn't display");

		}

	}

	// ======================== SEND ELEMENT
	// =========================================

	public void sendBy(IOSDriver<MobileElement> driver, IosMethods genMeth,
			By by, String send) throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail("WebElement'send by' can't be located");

		}

	}

	public void sendCss(IOSDriver<MobileElement> driver, IosMethods genMeth,
			String cssSelector, String send) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.cssSelector(cssSelector));
			myElement.sendKeys(send);
		}

		catch (Exception e) {

			org.testng.Assert.fail("Css selector " + cssSelector
					+ " can't be located");

		}

	}

	public void sendId(IosMethods genMeth, String id, String send)
			throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail(id + "didn't displayed");

		}

	}

	public void sendClassName(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String className, String send)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className)).sendKeys(send);

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + "didn't displayed");

		}

	}

	public void sendXpth(IosMethods genMeth, String xpth, String send)
			throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail(xpth + "didn't displayed");

		}

	}

	/*
	 * public void sendXpth(IOSDriver<MobileElement> driver, IosMethods genMeth,
	 * String xpth, String send) throws IOException {
	 * 
	 * try {
	 * 
	 * WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
	 * myElement.sendKeys(send);
	 * 
	 * }
	 * 
	 * catch (Exception e) {
	 * 
	 * genMeth.takeScreenShot(driver, genMeth, xpth);
	 * org.testng.Assert.fail(xpth + "didn't displayed");
	 * 
	 * }
	 * 
	 * }
	 */
	public void sendName(IosMethods genMeth, String name, String send)
			throws IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.name(name));
			myElement.sendKeys(send);
		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, name);
			org.testng.Assert.fail(name + "didn't displayed");

		}

	}

	// =========================Clear
	// WebElements=====================================================================

	public void clearXpth(IOSDriver<MobileElement> driver, IosMethods genMeth,
			String xpath) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpath));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpath + "didn't displayed");

		}

	}

	public void clearClassName(IOSDriver<MobileElement> driver,
			IosMethods genMeth, String className) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.className(className));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + "didn't displayed");

		}

	}

	public void clearId(IosMethods genMeth, String id)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + "didn't displayed");

		}

	}

	public void clearCss(IOSDriver<MobileElement> driver, IosMethods genMeth,
			String cssSelector) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.cssSelector(cssSelector));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(cssSelector + " can't be located");
		}

	}

	/*
	 * ******************************************************************************
	 * Avoid the Element not found exception *
	 * **********************************
	 * *******************************************
	 */

	// Look for an element in a few tries (with counter)
	public void waitForElementToBeInvisible(IOSDriver<MobileElement> driver,
			By byType, int numAttempts) throws IOException,
			ParserConfigurationException, SAXException {

		int count = 0;
		Boolean isInvisible = false;
		while (count < numAttempts) {

			try {
				isInvisible = new FluentWait<IOSDriver<MobileElement>>(driver)
						.withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions
								.invisibilityOfElementLocated(byType));

				if (isInvisible == true) {

					count = numAttempts;

				}

			}

			catch (Exception e) {
				count++;

			}

		}

		if (isInvisible == false) {
			IosMethods genMeth = new IosMethods();
			// str = new genData();
			String imageName = "Element isn't Invisible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not Invisible");
		}

	}

	public void waitForElementToBeVisible(IOSDriver<MobileElement> driver,
			By By, int numAttempts) throws IOException,
			ParserConfigurationException, SAXException {

		IosMethods genMeth = new IosMethods();
		int count = 0;
		WebElement elementToBeVisible = null;
		while (count < numAttempts) {
			try {
				elementToBeVisible = new FluentWait<IOSDriver<MobileElement>>(
						driver).withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.elementToBeClickable(By));

				if (elementToBeVisible != null) {

					count = numAttempts;

				}

			}

			catch (Exception e) {
				count++;
				// genMeth.takeScreenShot (driver, genMeth,
				// "Elelement not visible");
			}

		}

		if (elementToBeVisible == null) {
			String imageName = "Element isn't Visible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not Visible");
		}

	}

	@SuppressWarnings("rawtypes")
	public MobileElement fluentwait(IOSDriver driver, final By byType) {
		Wait<IOSDriver> wait = new FluentWait<IOSDriver>(driver)

		.withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		MobileElement foo = (MobileElement) wait
				.until(new Function<IOSDriver, MobileElement>() {
					public MobileElement apply(IOSDriver driver) {
						return (MobileElement) driver.findElement(byType);
					}
				});

		wait.until(ExpectedConditions.elementToBeClickable(byType));

		return foo;
	}

	public void isElementInvisible(By By) throws ParserConfigurationException,
			SAXException, IOException {

		try {

			(new WebDriverWait(driver, 30)).until(ExpectedConditions
					.invisibilityOfElementLocated(By));

		}

		catch (Exception e) {

			IosMethods genMeth = new IosMethods();
			String imageName = " Element is visible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " still visible");

		}

	}

	public void isElementVisible(By By) throws ParserConfigurationException,
			SAXException, IOException {

		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			new FluentWait<IOSDriver<MobileElement>>(driver)
					.withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {
			IosMethods genMeth = new IosMethods();
			String imageName = "Element is invisible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not visible");

		}

	}

	public boolean checkIsElementVisible(By By)
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<IOSDriver<MobileElement>>(driver)
					.withTimeout(15, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {

			// GenericMethods genMeth = new GenericMethods();
			// genData str = new genData();
			// String imageName = str.screenShotPath + " Element is invisible "
			// + genMeth.currentTime() + ".png";
			// genMeth.takeScreenShotNative(driver, imageName );
			// org.testng.Assert.fail("WebElement" + " is not visible");

		}
		if (element != null) {
			return isWebElementVisible = true;
		}

		else {
			return isWebElementVisible;

		}

	}

	public boolean fastCheckIsElementVisible(By By)
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<IOSDriver<MobileElement>>(driver)
					.withTimeout(5, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {

		}
		if (element != null) {
			return isWebElementVisible = true;
		}

		else {
			return isWebElementVisible;

		}

	}

	public boolean isElementInvisibleText(By By, String Text)
			throws ParserConfigurationException, SAXException, IOException {
		
		boolean isTextVisible= false;

		try {

			isTextVisible = (new WebDriverWait(driver, 45)).until(ExpectedConditions
					.invisibilityOfElementWithText(By, Text));

		}

		catch (Exception e) {

			IosMethods genMeth = new IosMethods();
			// String imageName = genMeth.getValueFromPropFile(key) + text +
			// " still visible "
			// + genMeth.currentTime() + ".png";
			genMeth.takeScreenShot(driver, genMeth, Text);
			org.testng.Assert.fail(Text + " still visible");

		}
		return isTextVisible;

	}

	public final class SessionIdentifierGenerator {
		private SecureRandom random = new SecureRandom();

		public String nextSessionId() {

			return new BigInteger(130, random).toString(32);

		}

	};

	public int getRandomInt() {
		Random rand = new Random();
		int n = rand.nextInt(50) + 1;
		return n;
	}

	// Creates a Random string
	public String randomString() {

		String text;
		SessionIdentifierGenerator x = new SessionIdentifierGenerator();
		text = x.nextSessionId();
		return text;
	}

	// Create a string with current date
	public String currentDate() {

		String curDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return curDate;
	}

	public String currentTime() {

		// String curDate = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		return curDate;
	}

	public void backgroundToForeground(IOSDriver<MobileElement> driver,
			int numOfTimes) {

		for (int count = 0; count < numOfTimes; count++) {

			driver.runAppInBackground(2);

		}

	}

	public void lockUnlock(IOSDriver<MobileElement> driver, int numOfTimes) {

		for (int count = 0; count < numOfTimes; count++) {

		//	driver.lockScreen(2);  this method is deprecated since java-client 4.0.0

		}

	}

	public void longPressElement(IOSDriver<MobileElement> driver,
			IosMethods genMeth, By By) {
		TouchAction action;
		WebElement el;
		try {
			action = new TouchAction(driver);
			el = genMeth.returnBy(driver, genMeth, By);
			action.longPress(el).waitAction(2000).release().perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setLandscapeMode() {

		//driver.rotate(ScreenOrientation.LANDSCAPE);  --> Deprecated in java-client 5.x
	}

	public void setPortraitMode() {

		//driver.rotate(ScreenOrientation.PORTRAIT);  --> Was deprecated on java client 5.x
	}

	public void setEnglishKeyboard(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		boolean isENG = genMeth.checkIsElementVisible(By.id("@"));
		if (isENG != true) {
			// change to English
			genMeth.clickId(genMeth, "English (US)");
		}

	}
	
	public IosElements setElements(String webElementXmlPath, String webElementXmlLang) throws ParserConfigurationException, jdk.internal.org.xml.sax.SAXException, IOException, InterruptedException, SAXException{
		

	iosData= new IosElements(webElementXmlLang, webElementXmlPath);
	
	
	return iosData;
	}

/*	public void locationServicesHadle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		boolean isLocationDisplay = genMeth.checkIsElementVisible(By
				.name("Allow"));
		if (isLocationDisplay) {

			genMeth.clickName(genMeth, "Allow");
		}

	}

	 public void accessToContactsHandle(IosMethods genMeth)
	 throws ParserConfigurationException, SAXException, IOException,
	 InterruptedException {
	 boolean isLocationDisplay =
	 genMeth.checkIsElementVisible(By.name(iosData.CameraPemissions_ID));
	 boolean isLocationDisplay = genMeth
	 .checkIsElementVisible(By.name("OK"));
	
	 if (isLocationDisplay) {
	
	 genMeth.clickName(genMeth, "OK");
	 }
	
	 }
*/
	/*public void accessToCameraHandle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		// boolean accessToCamera =
		// genMeth.checkIsElementVisible(By.name(iosData.CameraPemissions_ID));
		boolean accessToCamera = genMeth.checkIsElementVisible(By
				.name("Don't Allow"));

		if (accessToCamera) {

			genMeth.clickName(genMeth, iosData.BTNokName);
		}

	}

	public void sendNotificationHandle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		// boolean isLocationDisplay =
		// genMeth.checkIsElementVisible(By.name(iosData.CameraPemissions_ID));
		boolean isLocationDisplay = genMeth
				.checkIsElementVisible(By.name("OK"));

		if (isLocationDisplay) {

			genMeth.clickName(genMeth, "OK");
		}

	}

*/	public void swipeRightIphone6Plus(int miliseconds) throws InterruptedException {

		driver.swipe(370, 200, 140, 200, miliseconds);
		Thread.sleep(2000);

	}




	public void swipeRightIphone5Short(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 300, 75, 300, miliseconds);
			Thread.sleep(2000);

		}

	}

	
	public void swipeRightIphone5Long(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(250, 300, 75, 300, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	
	
	public void swipeRightIphone5Shortest(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(130, 300, 75, 300, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipedownIphone5Long(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 500, 150, 100, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	
	public void swipedownIphone5Short(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 500, 150, 400, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipedownIphone5Shorter(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 500, 150, 450, miliseconds);
			Thread.sleep(2000);

		}

	}

	
	
	public void swipedownIphone5Shortest(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 500, 150, 480, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipedownIphone6Plus(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(50, 650, 50, 200, miliseconds);
			Thread.sleep(2000);

		}

	}

	
	public void swipeUpIphone5Long(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 100, 150, 500, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipeUpIphone5Short(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(150, 400, 150, 500, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipeLandScapedownIphone5Long(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(200, 300, 200, 60, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipeLandScapedownIphone5Short(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(200, 300, 200, 150, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	
	public void swipeLandScapedownIphone5Shorter(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(200, 300, 200, 220, miliseconds);
			Thread.sleep(2000);

		}

	}

	
	public void swipeLandScapedownIphone5Shortest(int NumOfSwipe, int miliseconds) throws InterruptedException {

		int i;

		for (i = 0; i < NumOfSwipe; i++) {

			driver.swipe(200, 300, 200, 250, miliseconds);
			Thread.sleep(2000);

		}

	}
	
	

	
	
	
	
	public void openStratupScreen(IosMethods genMeth, IosElements iosData) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		
		
	//	boolean isStartupScreenDisplay = genMeth.checkIsElementVisible(By.name(iosData.Appium_Startup));

		boolean isStartupScreenDisplay = genMeth.checkIsElementVisible(By.name("Applications"));
		
		if (isStartupScreenDisplay != true ) {

			genMeth.signOutFromStartup(genMeth);
			genMeth.cleanLoginIos(genMeth, iosData.userQA, iosData.passwordQA);
		}

	}
	
	public void rotateLandscape(){

		//driver.rotate(ScreenOrientation.LANDSCAPE); deprecated on java client 5.x
	}
	
	public void rotatePortrait(){

	//	driver.rotate(ScreenOrientation.PORTRAIT);  --> deprecated on java client 5.x
	}
	
	
	public EnvironmentMode UserSetEnvironmentMode(EnvironmentMode EnvMode) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out
				.print("Please choose Environment mode(1 for QA, 2 for Staging or 3 for PROD):");
		byte number = scanner.nextByte();
		
		//Add a timer where after 10 seconds if no input was inserted then a default value will be setup

		if (number == 1) {
			EnvMode = EnvironmentMode.QA;
			System.out.println("Testing against QA Environment");


		} else if (number == 2) {
			EnvMode = EnvironmentMode.Staging;
			System.out.println("Testing against Stagins Environment");


		} else if (number == 3) {
			EnvMode = EnvironmentMode.Prod;
			System.out.println("Testing against PROD Environment");

		}
		return EnvMode;
	}
	
	public boolean UseEye() {
		boolean UseEye;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Do you want to use Applitools eye?(1 for Yes, or continue for No):");
		byte number = scanner.nextByte();
		//String choose = scanner.next();

		if (number == 1) {
			UseEye = true;
			System.out.println("Testing with Applitools visual testing");

		} else {
			UseEye = false;
			System.out.println("Testing without Applitools visual testing");

		}
		return UseEye;
	}

	private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = src.getSubimage(0, 30, rect.width, rect.height -118);
        return dest; 
     }
	
	public void releaseOK(IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		
		boolean isVisible =  genMeth.checkIsElementVisible(By.id(iosData.BTNokName));
		if (isVisible){
			genMeth.clickId(genMeth, iosData.BTNokName);
		}
	}
	
public void releaseAllow(IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		
		boolean isVisible =  genMeth.checkIsElementVisible(By.id("Allow"));
		if (isVisible){
			genMeth.clickId(genMeth, "Allow");
		}
	}
	
	
	// public void changeConnectionType(String mode) {
	//
	// NetworkConnection mobileDriver = (NetworkConnection) driver;
	// if (mode == "AIRPLANE_MODE") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.AIRPLANE_MODE);
	// }
	//
	// else if (mode == "WIFI") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.WIFI);
	//
	// }
	//
	// else if (mode == "DATA") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.DATA);
	//
	// }
	//
	// else if (mode == "ALL") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.ALL);
	//
	// }
	//
	// }
	//
	/*
	 * public void setAirplainMode() {
	 * 
	 * driver.setNetworkConnection(new NetworkConnectionSetting(true, false,
	 * false));
	 * 
	 * }
	 * 
	 * public void setWifiOn() {
	 * 
	 * driver.setNetworkConnection(new NetworkConnectionSetting(false, true,
	 * false));
	 * 
	 * }
	 * 
	 * public void pressHomeButton() { int Home = AndroidKeyCode.HOME;
	 * driver.sendKeyEvent(Home);
	 * 
	 * }
	 * 
	 * public void pressBackButton() { int Back = AndroidKeyCode.BACK;
	 * driver.sendKeyEvent(Back);
	 * 
	 * }
	 * 
	 * public void pressEnter() { int Enter = AndroidKeyCode.ENTER;
	 * driver.sendKeyEvent(Enter);
	 * 
	 * }
	 */
}
