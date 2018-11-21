package POM;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

public class AsosPageObject {
	Actions actions;
	String resultsLabel = "p[data-auto-id='styleCount']",
			loadmoreBtn= "a[data-auto-id='loadMoreProducts']",
			brandDropDown = "li[data-dropdown-id='brand']",
			anonBrand = "label[for='brand_15660']",
			filteredLabel = "#plp > div > div > div:nth-child(1) > div > div > div > div.St7n08U > ul > li:nth-child(4) > div > div > div > header > div > p._3wPu-0a",
			filteredValueLabel = "#plp > div > div > div:nth-child(1) > div > div > div > div.St7n08U > ul > li:nth-child(4) > div > div > div > header > div > p._1vMrQym",
			showingLabel = "p[data-auto-id='productsProgressBar']";
	int results=0,showProducts=0;
	public AsosPageObject(Actions actions) {
		this.actions=actions;
	}
	
	public int getResults() {
		String x = actions.driver.findElement(By.cssSelector(resultsLabel)).getAttribute("innerHTML");
		String result ="";
		for(int i=0;i<x.length();i++) {
			if(Character.isDigit(x.charAt(i))){
				result+=x.charAt(i);
			}
		}
		return Integer.parseInt(result);
	}
	
	public int getShowProductsCount() {
		String x = actions.driver.findElement(By.cssSelector(showingLabel)).getAttribute("innerHTML");
		x = x.substring(0, x.indexOf("of"));
		String result ="";
		for(int i=0;i<x.length();i++) {
			if(Character.isDigit(x.charAt(i))){
				result+=x.charAt(i);
			}
		}
		return Integer.parseInt(result);
	}
	
	public void loadMoreProducts() {
		if(getShowProductsCount()<getResults()) {
			actions.clickOn(By.cssSelector(loadmoreBtn));
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void filterByAnon(int results) {
		actions.clickOn(By.cssSelector(brandDropDown));
		actions.clickOn(By.cssSelector(anonBrand));
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(actions.driver.findElement(By.cssSelector(filteredValueLabel)).getAttribute("innerHTML"), "Anon");
		assertEquals(actions.driver.findElement(By.cssSelector(filteredLabel)).getAttribute("innerHTML"),"1 selected");
		assertEquals(getResults(),results);
	}
}
