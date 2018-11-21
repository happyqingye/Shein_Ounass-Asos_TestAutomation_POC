package POM;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class OunassPageObject {
	Actions actions ;
	
	private static Properties properties = new Properties();
	
	@FindAll({
		@FindBy(css=".login-button"),// userIcon 
		@FindBy(css="#onesignal-popover-cancel-button")// rejcetNewCollectionsPopUp 
	})
	ArrayList<WebElement> homePageElements ;
    //WebElement userIcon , rejectNewCollectionsPopUp ;
	
	String amberBtn = ".pull-left>a",
		facebookBtn = "#btnCreateAccountWithFacebook",
		userIcon = ".login-button",
		rejectNewCollectionsPopUp= "#onesignal-popover-cancel-button",
		facebookEmail = "#email",
		facebookPass = "#pass",
		facebookLoginBtn = "input[name='login']",
		facebookConfirmBtn = "input[name='__CONFIRM__']",
		facebookError = "#errorData",
		account = "a[href='customer']",
		toBeClickable= "elementToBeClickable";
		
	
	public OunassPageObject(Actions actions) {
		this.actions=actions;
		try {
			OunassPageObject.properties.load(new FileReader(new File("test.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loginWithFacebook(String username,String pass) {
		goToUsers();
		selectAmber();
		amberLoginWithFacebook(username, pass);
		verifyLogin();
	}
	
	public void goToHomePage(String url) {
		actions.navigateToPage(url, By.cssSelector(userIcon));
		handleCollectionsPopUp();
	}
	
	public void handleCollectionsPopUp() {
		if(actions.waitUntil(By.cssSelector(rejectNewCollectionsPopUp),toBeClickable)!=null) {
			actions.clickOn(By.cssSelector(rejectNewCollectionsPopUp));
		}
	}
	
	public void goToUsers() {
		actions.clickOn(By.cssSelector(userIcon));
	}
	
	public void selectAmber() {
		actions.clickOn(By.cssSelector(amberBtn));
	}
	
	public void amberLoginWithFacebook(String username,String pass) {
		//actions.clickOn(By.cssSelector(facebookBtn));
		

		actions.driver.switchTo().defaultContent();
		actions.driver.switchTo().frame(0);
		ArrayList l =new ArrayList();
		actions.clickOn(By.cssSelector(facebookBtn));
		for(int i=0;i<10;i++){
			l = new ArrayList(actions.driver.getWindowHandles());
			if(l.size()<2){
				try{
					Thread.sleep(500);
					actions.clickOn(By.cssSelector(facebookBtn));
				}
				catch(Exception e){}
			}
			else{break;}
		}
		actions.driver.switchTo().defaultContent();

		try {
			Thread.sleep(3000);
			if(l.size() >=2){
				actions.driver.switchTo().window(l.get(1).toString());
				actions.setText(By.cssSelector(facebookEmail),username);
				actions.setText(By.cssSelector(facebookPass),pass);
				actions.clickOn(By.cssSelector(facebookLoginBtn));
				if(actions.waitUntil(By.cssSelector(facebookConfirmBtn), "presenceOfElement") !=null){
					actions.clickOn(By.cssSelector(facebookConfirmBtn));
				}
				actions.driver.switchTo().window(l.get(0).toString());
				actions.driver.switchTo().defaultContent();
				actions.driver.switchTo().frame(0);
				if(actions.waitUntil(By.cssSelector(facebookError), "presenceOfElement")!=null) {
					Assert.fail("This Account can't be connected to Amber via Facebook please contact the support");
				}
			}
			else{Assert.fail("Couldn't Open Facebook Window");}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void verifyLogin() {
		goToHomePage(properties.getProperty("OunassWebsite")+"customer/login");
		if(actions.waitUntil(By.cssSelector(account), "presenceOfElement")==null) {
			Assert.fail("Login Failed");
		}
	}
}
