package TestAutomationTasks;
import static org.junit.Assert.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
    	File screenShote = new File("ScreenShots");
    	File[] listFiles = screenShote.listFiles();
		for(File file : listFiles){
			file.delete();
		}
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
    		assertTrue(shein.createAccount(
    	    		properties.getProperty("email"),properties.getProperty("pass")),"Sign Up Error");
    		properties.setProperty("newClient", "false");
    	}
    	else {
    		assertTrue(shein.signIn(
    	    		properties.getProperty("email"),properties.getProperty("pass")),"SignIn Error");		
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
    
    @Test
    public void invalidSignUp(){
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	assertFalse(shein.createAccount(properties.getProperty("email")
    			,properties.getProperty("wrongPass")),"SignUp Error");
    	
	    
    }
    @Test 
    public void invalidSignIn(){
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	assertFalse(shein.signIn(
	    		properties.getProperty("email"),properties.getProperty("wrongPass")),"SignIn Error");		

    }
}
