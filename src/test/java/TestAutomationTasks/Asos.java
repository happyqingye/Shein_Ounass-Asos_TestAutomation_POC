package TestAutomationTasks;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.annotations.*;

import POM.Actions;
import POM.AsosPageObject;

public class Asos {
	public Actions actions = new Actions();
	AsosPageObject asos;
	private static Properties properties = new Properties();
	@BeforeClass
	@Parameters({"browser"})
	public void setUp(String browser) throws Exception,MalformedURLException {
		properties.load(new FileReader(new File("test.properties")));
		actions.initiateTheWebDriver(browser);
		asos = new AsosPageObject(actions);
	}

	@AfterClass
	public void tearDown() throws Exception {
		actions.closeTheBrowser();
	}
	
	//@Test
	public void VerifyProductsPageLoadMoreBtn() {
		actions.driver.get(properties.getProperty("AsosWebsite"));
		assertEquals(asos.getShowProductsCount(), 72);
		asos.loadMoreProducts();
		assertTrue(asos.getShowProductsCount()>72);
	}
	
	@Test
	public void VerifyAnonFilter() {
		actions.driver.get(properties.getProperty("AsosWebsite"));
		asos.filterByAnon(5);
	}
	}
