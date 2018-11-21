package TestAutomationTasks;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import java.net.MalformedURLException;
import org.testng.annotations.*;

import POM.Actions;
import POM.OunassPageObject;

public class Ounass1 {
	public Actions actions = new Actions();
	private static Properties properties = new Properties();
	OunassPageObject ounass;
	@BeforeClass
	@Parameters({"browser"})
	public void setUp(String browser) throws Exception,MalformedURLException {
		properties.load(new FileReader(new File("test.properties")));
		actions.initiateTheWebDriver(browser);
		ounass = new OunassPageObject(actions);
	}

	@AfterClass
	public void tearDown() throws Exception {
	actions.closeTheBrowser();
	}

	@Test
	public void loginWithFacebook() {
		ounass.goToHomePage(properties.getProperty("OunassWebsite"));
		ounass.loginWithFacebook(properties.getProperty("OunassEmail"), properties.getProperty("OunassPass"));
		
	}
	}
