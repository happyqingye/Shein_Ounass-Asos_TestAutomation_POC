package TestAutomationTasks;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.annotations.*;

import POM.Actions;

public class Asos {
	public Actions actions = new Actions();
	private static Properties properties = new Properties();
	@BeforeClass
	//@Parameters({"browser"})
	public void setUp() throws Exception,MalformedURLException {
		properties.load(new FileReader(new File("test.properties")));
		actions.initiateTheWebDriver("");

	}

	@AfterClass
	public void tearDown() throws Exception {
	//actions.closeTheBrowser();
	}
	
	@Test
	public void loginWithFacebook() {
		actions.driver.get("");
		System.out.println(actions.driver.findElement(By.cssSelector("p[data-auto-id='styleCount']")).getAttribute("innerHTML"));
		System.out.println(actions.driver.findElement(By.cssSelector("p[data-auto-id='productsProgressBar']")).getAttribute("innerHTML"));
		
	}
	}
