package TestAutomationTasks;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.testng.annotations.*;

import java.net.MalformedURLException;
import POM.Actions;
import POM.SheinGoods;
import POM.SheinPageObject;

public class Shein {
	public Actions actions = new Actions();
	private static Properties properties = new Properties();
	SheinPageObject shein;
	SheinGoods sheinGoods ;

	@BeforeTest
	public void suiteSetUp() {
		actions.erasePastTestData();
	}
	
	@BeforeClass
	@Parameters({"browser"})
	public void setUp(String browser) throws Exception,MalformedURLException {
		System.out.println(browser);
		properties.load(new FileReader(new File("test.properties")));
		actions.initiateTheWebDriver(browser);
		shein = new SheinPageObject(actions);
		sheinGoods =new SheinGoods(actions);
	}

	@AfterClass
	public void tearDown() throws Exception {
		actions.closeTheBrowser();
	}

	
	@Test(priority=1)
	public void invalidSignUp() {
		shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
		assertFalse(shein.createAccount(properties.getProperty("email"), properties.getProperty("wrongPass")),
				"SignUp Error");

	}

	@Test(priority=2)
	public void invalidSignIn() {
		shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
		assertFalse(shein.signIn(properties.getProperty("email"), properties.getProperty("wrongPass")), "SignIn Error");

	}
}
