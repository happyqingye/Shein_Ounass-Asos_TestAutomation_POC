package POM;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions {

	public static ArrayList<WebDriver> allDrivers;
	public static WebDriver driver;
	
	public void initiateMultipleWebDrivers(int numbers,String [] paths) {
		
	}
	
	public void initiateTheWebDriver(String chromeDriverPath) throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		Actions.driver = new ChromeDriver();
		Actions.driver.manage().window().maximize();
		Actions.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public void setText(By b, String text) {
		waitUntil(b, "presenceOfElement");
		//System.out.println(element);
		try {
			Actions.driver.findElement(b).clear();
			Actions.driver.findElement(b).sendKeys(text);
			String actualValue = ( Actions.driver.findElement(b).getAttribute("value")==null) ? Actions.driver.findElement(b).getAttribute("innerHTML") : Actions.driver.findElement(b).getAttribute("value");
			
			assertEquals(actualValue,text );
		} catch (Exception e) {
			Assert.fail("Couldn't set text because of " + e.getMessage());
		}

	}

	public void clickOn(By b) {
		waitUntil(b, "elementToBeClickable");
		try {
			Actions.driver.findElement(b).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Couldn't click because of" + e.getMessage());
		}
		
	}

	public void navigateToPage(String url,By b) {
		Actions.driver.get(url);
		WebElement element = waitUntil(b, "presenceOfElement");
		assertNotNull(element, "Navigation Failed to this Website "+url);
	}
	
	
	public WebElement waitUntil(By b, String condition) {
		try {
			WebElement element = null;
			switch (condition) {

			case "presenceOfElement":
				element = (new WebDriverWait(driver,30)).until(ExpectedConditions.presenceOfElementLocated(b));
				return element;

			case "elementToBeClickable":
				element = (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(b));
				return element;
				

			default:
				element = null;
				Assert.fail("Wrong condition");
			}
		return element ;
		} catch (Exception e) {
			Assert.fail("Couldn't find the element because of " + e.getMessage());
			return null;
		}
	}

	public void closeTheBrowser() {
		try {
			Actions.driver.close();
		} catch (Exception e) {
			Assert.fail("Couldn't close the browser because of " + e.getMessage());
		}
	}
}
