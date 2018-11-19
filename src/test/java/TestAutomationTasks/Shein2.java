package TestAutomationTasks;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.testng.annotations.*;

import POM.Actions;
import POM.SheinGoods;
import POM.SheinPageObject;

public class Shein2 {
	public Actions actions = new Actions();
	private static Properties properties = new Properties();
	SheinPageObject shein;
	SheinGoods sheinGoods ;

	@BeforeClass
	public void setUp() throws Exception {
		
		properties.load(new FileReader(new File("test.properties")));
		actions.initiateTheWebDriver();
		shein = new SheinPageObject(actions);
		sheinGoods =new SheinGoods(actions);
	}

	@AfterClass
	public void tearDown() throws Exception {
		actions.closeTheBrowser();
	}

	//@Test(priority=3)
	public void sheinHappyScenario() throws InterruptedException {
		shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
		if (properties.getProperty("newClient") == "true") {
			assertTrue(shein.createAccount(properties.getProperty("email"), properties.getProperty("pass")),
					"Sign Up Error");
			properties.setProperty("newClient", "false");
		} else {
			assertTrue(shein.signIn(properties.getProperty("email"), properties.getProperty("pass")), "SignIn Error");
		}

		String[] items = { properties.getProperty("firstItemLink"), properties.getProperty("secondItemLink"),
				properties.getProperty("thirdItemLink") };

		shein.EditAndVerifyAccountSettings(properties.getProperty("email"), properties.getProperty("phone"),
				properties.getProperty("date"), properties.getProperty("country"));

		sheinGoods.navigateToItem(properties.getProperty("firstItemLink"));
		sheinGoods.addToBag();

		sheinGoods.navigateToItem(properties.getProperty("secondItemLink"));
		sheinGoods.chooseSize(properties.getProperty("eurSize"));
		sheinGoods.addToBag();

		sheinGoods.navigateToItem(properties.getProperty("thirdItemLink"));
		sheinGoods.chooseSize(properties.getProperty("size"));
		sheinGoods.addToBag();

		sheinGoods.verifyBag(items);

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
