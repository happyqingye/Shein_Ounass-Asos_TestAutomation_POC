package TestAutomationTasks;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import POM.*;

public class Shein {
	static Actions actions = new Actions();
	private static Properties properties = new Properties();
    SheinPageObject shein = new SheinPageObject(); 
    SheinGoods sheinGoods = new SheinGoods();
    
    
    @BeforeClass
    public static void setUp() throws Exception {
    	properties.load(new FileReader(new File("test.properties")));
        actions.initiateTheWebDriver(properties.getProperty("chromeDriverPath"));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        actions.closeTheBrowser();  
        	
    }
    
    
    @Test
    public void sheinHappyScenario() throws InterruptedException {
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	if(properties.getProperty("newClient")=="true") {
    		shein.createAccount(
    	    		properties.getProperty("email"),properties.getProperty("pass"));
    		properties.setProperty("newClient", "false");
    	}
    	else {
    		shein.signIn(
    	    		properties.getProperty("email"),properties.getProperty("pass"));		
    	}
    	
    	String [] items = {properties.getProperty("firstItemLink"),
    			properties.getProperty("secondItemLink"),
    			properties.getProperty("thirdItemLink")};
    	
    	shein.EditAndVerifyAccountSettings(properties.getProperty("email"),properties.getProperty("phone"),
    			properties.getProperty("date") ,properties.getProperty("country"));
    	
    	
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
}
