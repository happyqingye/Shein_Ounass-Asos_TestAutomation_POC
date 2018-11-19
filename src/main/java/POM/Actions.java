package POM;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import bsh.classpath.BshClassPath.DirClassSource;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class Actions {

	
	public static ArrayList<WebDriver> allDrivers;
	public WebDriver driver;
	
	
	public void initiateTheWebDriver() throws MalformedURLException {
		//System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
//		DesiredCapabilities cap = DesiredCapabilities.chrome();
//		cap.setPlatform(Platform.LINUX);
//		cap.setVersion("");
//		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
//		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void takeScreenShot(String name) {
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String os = System.getProperty("os.name");
		String directoryName ="";
		if(os.toLowerCase().contains("windows")) {
			directoryName="ScreenShots\\";
		}
		else {
			directoryName="ScreenShots/" ;
		}
		try {
		  FileUtils.copyFile(src, new File(directoryName+name+".png"));
		}
		catch (IOException e)
		 {
		  System.out.println(e.getMessage());
		  
		 }

	}
	public void setText(By b, String text) {
		waitUntil(b, "presenceOfElement");
		//System.out.println(element);
		try {
			driver.findElement(b).clear();
			driver.findElement(b).sendKeys(text);
			String actualValue = ( driver.findElement(b).getAttribute("value")==null) ? driver.findElement(b).getAttribute("innerHTML") : driver.findElement(b).getAttribute("value");
			
			assertEquals(actualValue,text );
		} catch (Exception e) {
			Assert.fail("Couldn't set text because of " + e.getMessage());
		}

	}

	public void clickOn(By b) {
		waitUntil(b, "elementToBeClickable");
		try {
			driver.findElement(b).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Couldn't click because of" + e.getMessage());
		}
		
	}

	public void navigateToPage(String url,By b) {
		driver.get(url);
		WebElement element = waitUntil(b, "presenceOfElement");
		assertNotNull(element, "Navigation Failed to this Website "+url);
	}
	
	
	public WebElement waitUntil(By b, String condition) {
		try {
			WebElement element = null;
			switch (condition) {

			case "presenceOfElement":
				element = (new WebDriverWait(driver,6)).until(ExpectedConditions.presenceOfElementLocated(b));
				return element;

			case "elementToBeClickable":
				element = (new WebDriverWait(driver, 6)).until(ExpectedConditions.elementToBeClickable(b));
				return element;
				

			default:
				element = null;
				Assert.fail("Wrong condition");
			}
		return element ;
		} catch (Exception e) {
			//Assert.fail("Couldn't find the element because of " + e.getMessage());
			return null;
		}
	}

	public void closeTheBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			Assert.fail("Couldn't close the browser because of " + e.getMessage());
		}
	}
	
	public void erasePastTestData() {
		File screenShot = new File("ScreenShots");
		File[] listFiles = screenShot.listFiles();
		for (File file : listFiles) {
			file.delete();
		}
	}
}
