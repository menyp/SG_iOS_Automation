package Native;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.WithTimeout;

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
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import com.applitools.eyes.Eyes;
import com.google.common.base.Function;


public class IosMethods {
	
	IOSDriver<MobileElement> driver;
	IosElements iosData;
	IosMethods genMeth;

	public void cleanLoginIos(IosMethods genMeth,  IosElements iosData , String user) throws ParserConfigurationException, SAXException, IOException,InterruptedException {
		
//Check language making sure keyboard is set to English
		MobileElement EmailField = genMeth.returnXpth(driver, genMeth, iosData.TEXTFIELDemailXpth);
		MobileElement PasswordField = genMeth.returnXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIASecureTextField[1]");

// Make sure that the English keyboard is open
		driver.tap(1, EmailField, 1000);
		genMeth.setEnglishKeyboard(genMeth);
		
// Make sure that the email & password fields are empty
		boolean isEmailEmpty = genMeth.checkIsElementVisible(By.id("E-Mail"));
		boolean isPasswordEmpty = genMeth.checkIsElementVisible(By.id("Password"));
		
		if (!isEmailEmpty){
			//driver.tap(1, EmailField, 1000);
			genMeth.clickName(genMeth, iosData.BTNclearText_Name);
			
		}
		if (!isPasswordEmpty){
			driver.tap(1, PasswordField, 1000);
			genMeth.clickName(genMeth, iosData.BTNclearText_Name);
			
		}
		genMeth.sendXpth(genMeth, iosData.TEXTFIELDemailXpth , iosData.userQA);	
		genMeth.sendXpth( genMeth, iosData.TEXTFIELDpasswordXpth, iosData.passwordQA);
		genMeth.clickId( genMeth, iosData.BTNloginID);
		
		//location popup handle
		genMeth.locationServicesHadle(genMeth);
		genMeth.clickXpth(driver, genMeth, iosData.IconBackToApplicationList_xpth);
	//	genMeth.clickXpth(driver, genMeth, "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[2]");
		


	}
	
	public void eyesCheckWindow(Eyes eyes, String testName, Boolean useEye) throws InterruptedException{
		
			if (useEye){
			eyes.setApiKey("Hbh6716cKDCgn8a9bMAREPM105nbW109PQe0993So5GwFpNM110");
			eyes.open(driver, "iOS_SG", testName);
			//eyes.setMatchTimeout(5);
			eyes.checkWindow("Sample Screen");
			eyes.close();
			
			}
			
	}	

	
	public void killAppAndroid(IOSDriver<MobileElement> driver)throws InterruptedException, IOException {

	//	driver.removeApp("com.pogoplug.android");
		driver.resetApp();
		//driver.removeApp(bundleId);
				
		try {
			driver.quit();
		} catch (Exception x) {
			// swallow exception
		}
		//driver = genMeth.setCapabilitiesIos();
	}
	

	public void signOutFromStartup(IosMethods genMeth, IosElements iosData) throws InterruptedException, IOException {
		genMeth.clickId(genMeth, iosData.BTNdoneName);
		genMeth.clickXpth(driver, genMeth, iosData.BTNsettingsIconXpth);
		genMeth.clickName(genMeth, iosData.BTNlogoutName);
		
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
			throws IOException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName",genMeth.getValueFromPropFile("deviceName"));
		capabilities.setCapability("device",genMeth.getValueFromPropFile("device"));
		capabilities.setCapability("udid", genMeth.getValueFromPropFile("udid"));
		capabilities.setCapability(CapabilityType.VERSION,genMeth.getValueFromPropFile("CapabilityType.VERSION"));
		//capabilities.setCapability(CapabilityType.PLATFORM,genMeth.getValueFromPropFile("CapabilityType.PLATFORM"));
		//capabilities.setCapability("platformName",genMeth.getValueFromPropFile("platformName"));
		capabilities.setCapability("app",genMeth.getValueFromPropFile("appPath"));

		try {

			driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

		}

		catch (MalformedURLException e) {

			genMeth.takeScreenShot(driver, genMeth,"Faliled to open iOS driver");
			org.testng.Assert.fail("WebElement"+ " Faliled to open iOS driver");
		}
		return driver;
	}	

	
	public String getValueFromPropFile(String key) {
		Properties properties = new Properties();
		String value = "";
		try {
			
			properties.load(getClass().getResourceAsStream("/resources/config.properties"));
			//properties.load(getClass().getResourceAsStream("/resources/webui.properties"));
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

		// Now you can do whatever you need to do with it, for example copy somewhere
		String imagePath = genMeth.getValueFromPropFile("screenshotPath")  + currentDate + "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}
	
	public void takeScreenShotPositive(IosMethods genMeth, String imageName) throws IOException {
		String currentTime = genMeth.currentTime();
		File scrFile = (driver.getScreenshotAs(OutputType.FILE));
		String currentDate = genMeth.currentDate();

		// Now you can do whatever you need to do with it, for example copy somewhere
		String imagePath = genMeth.getValueFromPropFile("screenshotPathPositive")  + currentDate +  "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}




	/*
	 * ***************************************************
	 * Web Element Handling *
	 * ***************************************************
	 */

	// ==================== RETURN ELEMENT

		public WebElement returnCss(IOSDriver<MobileElement> driver, String cssSelector)
			throws InterruptedException {

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

	public WebElement returnId(IOSDriver<MobileElement> driver,IosMethods genMeth, String id)
			throws InterruptedException {


		try {

			genMeth.fluentwait(driver, By.id(id));

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver, By.id(id));
		return myElement;

	}

	public WebElement returnClassName(IOSDriver<MobileElement> driver, IosMethods genMeth,  String className)
			throws InterruptedException {


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

	public MobileElement returnXpth(IOSDriver<MobileElement> driver, IosMethods genMeth, String xpth)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.xpath(xpth));

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpth + " didn't display");
		}

		MobileElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
		return myElement;

	}

	public MobileElement returnName(IOSDriver<MobileElement> driver, IosMethods genMeth, String name)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.name(name));

		}

		catch (Exception e) {

			org.testng.Assert.fail(name + " didn't display");

		}

		MobileElement myElement = genMeth.fluentwait(driver, By.name(name));
		return myElement;

	}
	
	public WebElement returnBy(IOSDriver<MobileElement> driver, IosMethods genMeth, By by)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, by);

		}

		catch (Exception e) {

			org.testng.Assert.fail(by + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver, by);
		return myElement;

	}

	// ========= CLICK an ELEMENT =========================================================================

	public void clickBy(IOSDriver<MobileElement> driver, IosMethods genMeth, By by) throws InterruptedException {


		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}
	
	
	public void tapBy(IOSDriver<MobileElement> driver, IosMethods genMeth, By by) throws InterruptedException {


		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			driver.tap(1, myElement, 1000);
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}

	public void clickCss(IOSDriver<MobileElement> driver, IosMethods genMeth, String cssSelector)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.cssSelector(cssSelector));
			myElement.click();

		}

		catch (Exception e) {

			org.testng.Assert.fail(cssSelector + " didn't display");

		}

	}

	public void clickId1( IosMethods genMeth,
			String id) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.click();
		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

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
	
	public void tapId( IosMethods genMeth,
			String id) throws InterruptedException {

		try {

			MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
			driver.tap(1, myElement, 1000);

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

		}
	}

	public void clickClassName(IOSDriver<MobileElement> driver, IosMethods genMeth, String className)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className)).click();

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + " didn't display");

		}

	}
	

	public void clickXpth(IOSDriver<MobileElement> driver, IosMethods genMeth, String xpth)
			throws InterruptedException, IOException {

		By by = By.xpath(xpth);

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();
			//driver.findElementByXPath(xpth).click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpth);
			org.testng.Assert.fail(xpth + " didn't display");

		}

	}
	
	public void tapXpth( IosMethods genMeth, String xpth)
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

	public void clickName( IosMethods genMeth, String name )
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

// ======================== SEND ELEMENT =========================================

	public void sendBy(IOSDriver<MobileElement> driver, IosMethods genMeth, By by, String send)
			throws InterruptedException, IOException {

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

	public void sendId( IosMethods genMeth, String id, String send)
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

	public void sendClassName(IOSDriver<MobileElement> driver, IosMethods genMeth, String className, String send)
			throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className)).sendKeys(send);

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + "didn't displayed");

		}

	}
	
	public void sendXpth( IosMethods genMeth, String xpth, String send)
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
	public void sendXpth(IOSDriver<MobileElement> driver, IosMethods genMeth, String xpth, String send)
			throws IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, xpth);
			org.testng.Assert.fail(xpth + "didn't displayed");

		}

	}
*/
	public void sendName( IosMethods genMeth, String name, String send)
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

	// =========================Clear WebElements=====================================================================

	public void clearXpth(IOSDriver<MobileElement> driver, IosMethods genMeth, String xpath)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpath));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpath + "didn't displayed");

		}

	}

	public void clearClassName(IOSDriver<MobileElement> driver, IosMethods genMeth, String className)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver,
					By.className(className));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(className + "didn't displayed");

		}

	}

	public void clearId( IosMethods genMeth, String id)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + "didn't displayed");

		}

	}

	public void clearCss(IOSDriver<MobileElement> driver, IosMethods genMeth, String cssSelector)
			throws InterruptedException {

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
	 * *****************************************************************************
	 */

	// Look for an element in a few tries (with counter)
	public void waitForElementToBeInvisible(IOSDriver<MobileElement> driver, By byType,
			int numAttempts) throws IOException, ParserConfigurationException,SAXException {

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

	public void waitForElementToBeVisible(IOSDriver<MobileElement> driver, By By,int numAttempts) 
			throws IOException, ParserConfigurationException,SAXException {
		
		IosMethods genMeth = new IosMethods();
		int count = 0;
		WebElement elementToBeVisible = null;
		while (count < numAttempts) {
			try {
				elementToBeVisible = new FluentWait<IOSDriver<MobileElement>>(driver)
						.withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.elementToBeClickable(By));

				if (elementToBeVisible != null) {

					count = numAttempts;

				}

			}

			catch (Exception e) {
				count++;
//				genMeth.takeScreenShot (driver, genMeth, "Elelement not visible");
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

				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		MobileElement foo = (MobileElement) wait.until(new Function<IOSDriver, WebElement>() {
			public MobileElement apply(IOSDriver driver) {
				return (MobileElement) driver.findElement(byType);
			}
		});

		wait.until(ExpectedConditions.elementToBeClickable(byType));

		return foo;
	}


	public void isElementInvisible( By By)
			throws ParserConfigurationException, SAXException, IOException {

		try {

			(new WebDriverWait(driver, 45)).until(ExpectedConditions
					.invisibilityOfElementLocated(By));

		}

		catch (Exception e) {

			IosMethods genMeth = new IosMethods();
			String imageName = " Element is visible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " still visible");

		}

	}

	public void isElementVisible( By By)
			throws ParserConfigurationException, SAXException, IOException {

		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			new FluentWait<IOSDriver>(driver)
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

	public boolean checkIsElementVisible( By By )
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<IOSDriver>(driver)
					.withTimeout(5, TimeUnit.SECONDS)
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
	
	public boolean fastCheckIsElementVisible( By By )
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<IOSDriver>(driver)
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


	public void isElementInvisibleText(By By, String Text)
			throws ParserConfigurationException, SAXException, IOException {

		try {

			(new WebDriverWait(driver, 45)).until(ExpectedConditions
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
		String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		return curDate;
	}


	public void backgroundToForeground(IOSDriver<MobileElement> driver, int numOfTimes) {

		for (int count = 0; count < numOfTimes; count++) {

			driver.runAppInBackground(2);

		}

	}

	public void lockUnlock(IOSDriver<MobileElement> driver, int numOfTimes) {

		for (int count = 0; count < numOfTimes; count++) {

			driver.lockScreen(2);

		}

	}
	
	public void longPressElement(IOSDriver<MobileElement> driver, IosMethods genMeth, By By){
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

		driver.rotate(ScreenOrientation.LANDSCAPE);
	}

	public void setPortraitMode() {

		driver.rotate(ScreenOrientation.PORTRAIT);
	}

	public void setEnglishKeyboard(IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException,InterruptedException {
		boolean isENG = genMeth.checkIsElementVisible(By.name("space"));
		if (isENG != true) {
			// change to English
			genMeth.clickId(genMeth, "English (US)");
		}
		
		
	}
	
	public void locationServicesHadle (IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		boolean isLocationDisplay = genMeth.checkIsElementVisible(By.name("Allow"));
		if (isLocationDisplay){
			
			genMeth.clickName(genMeth, "Allow");
		}
		
		
	}
	
	public void accessToContactsHandle(IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		//boolean isLocationDisplay = genMeth.checkIsElementVisible(By.name(iosData.CameraPemissions_ID));
		boolean isLocationDisplay = genMeth.checkIsElementVisible(By.name("OK"));

		if (isLocationDisplay){
			
			genMeth.clickName(genMeth, "OK");
		}
		
		
	}
	
	public void accessToCameraHandle (IosMethods genMeth) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
	//	boolean accessToCamera = genMeth.checkIsElementVisible(By.name(iosData.CameraPemissions_ID));
		boolean accessToCamera = genMeth.checkIsElementVisible(By.name("Don't Allow"));

		if (accessToCamera){
			
			genMeth.clickName(genMeth, iosData.BTNokName);
		}
		
		
	}
	
	
	public void swipeRightIphone6Plus(int miliseconds){
		
		driver.swipe(370, 200, 140, 200, miliseconds);

	}
	
	
public void swipedownIphone6Plus(int miliseconds){
		
		driver.swipe(50, 650, 50, 200, miliseconds);

	}
	
//	public void changeConnectionType(String mode) {
//
//		NetworkConnection mobileDriver = (NetworkConnection) driver;
//		if (mode == "AIRPLANE_MODE") {
//
//			mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.AIRPLANE_MODE);
//		}
//
//		else if (mode == "WIFI") {
//
//			mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.WIFI);
//
//		}
//
//		else if (mode == "DATA") {
//
//			mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.DATA);
//
//		}
//
//		else if (mode == "ALL") {
//
//			mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.ALL);
//
//		}
//		
//	}
//	
/*
	public void setAirplainMode() {

		driver.setNetworkConnection(new NetworkConnectionSetting(true, false,
				false));

	}

	public void setWifiOn() {

		driver.setNetworkConnection(new NetworkConnectionSetting(false, true,
				false));

	}

	public void pressHomeButton() {
		int Home = AndroidKeyCode.HOME;
		driver.sendKeyEvent(Home);

	}

	public void pressBackButton() {
		int Back = AndroidKeyCode.BACK;
		driver.sendKeyEvent(Back);

	}

	public void pressEnter() {
		int Enter = AndroidKeyCode.ENTER;
		driver.sendKeyEvent(Enter);

	}
	*/
}
