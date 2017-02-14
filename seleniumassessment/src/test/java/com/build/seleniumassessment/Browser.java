package com.build.seleniumassessment;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Browser class which contains general Selenium functionality,
 * as well as more specific actions relating to Build.com.
 * If I was designing a full scale framework, I would put the general Selenium functionality here
 * and break more specific functionality into subclasses based on the object model decided upon.
 */
public class Browser {
	public WebDriver driver;
	public WebDriverWait wait;
	public int number_in_cart;
	
	public Browser()
	{
		number_in_cart = 0;
		final File file = new File("driver/chromedriver.exe");
    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    	driver = new ChromeDriver();
    	wait = new WebDriverWait(driver, 10);
    	driver.navigate().to("http://www.build.com");
    	
    	// wait for main page to load
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-content")));
	}
	
	public void teardown()
	{
		driver.quit();
	}
	
    public WebElement waitAndGetByName(String query)
    {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(query)));
    	return driver.findElement(By.name(query));
    }
    
    public WebElement waitAndGetByLinkText(String query)
    {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(query)));
    	return driver.findElement(By.linkText(query));
    }
    
    public WebElement waitAndGetByClassName(String query)
    {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(query)));
    	return driver.findElement(By.className(query));
    }
    
    public WebElement waitAndGetById(String query)
    {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(query)));
    	return driver.findElement(By.id(query));
    }
    
    public WebElement waitAndGetByCss(String query)
    {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(query)));
    	return driver.findElement(By.cssSelector(query));
    }
    
    /* Searches and adds item to cart */
    public void addItemToCart(String name, int quantity)
    {
    	WebElement search_box = waitAndGetById("search_txt");
    	search_box.sendKeys(name);
    	search_box.sendKeys(Keys.ENTER);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("recs-desktop")));
    	
    	if(quantity != 1)
    	{
    		WebElement qty_field = waitAndGetByName("qty");
			qty_field.click();
			qty_field.sendKeys(Keys.BACK_SPACE);
			qty_field.sendKeys(String.valueOf(quantity));
    	}
    	waitAndGetByClassName("add-to-cart-btn").click();
    	number_in_cart += quantity;
    	if(number_in_cart > 2)
    	{
    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Checkout")));
    	}
    	else
    	{
    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Proceed to Cart")));
    	}
    }
    
    /* Begins the checkout procedure */
    public void checkoutProcedure()
    {
    	System.out.println(driver.getTitle());
    	if(!(driver.getTitle()).contains("Shopping Cart"))
    	{
    		waitAndGetByClassName("cart-text").click();
    	}
    	waitAndGetByLinkText("Checkout").click();
    	waitAndGetByName("guestLoginSubmit").click();
    	setShippingAndPaymentInfo();
    	waitAndGetByCss("input[value='Review Order']").click();
    }
    
    /* Fills in shipping and payment info */
    public void setShippingAndPaymentInfo()
    {
    	waitAndGetById("shippingfirstname").sendKeys("Test");
    	waitAndGetById("shippinglastname").sendKeys("Test");
    	waitAndGetById("shippingaddress1").sendKeys("123 Test");
    	waitAndGetById("shippingpostalcode").sendKeys("95928");
    	waitAndGetById("shippingcity").sendKeys("Chico");
    	waitAndGetById("shippingstate_1").sendKeys("c");
    	waitAndGetById("shippingphonenumber").sendKeys("8053048468");
    	waitAndGetById("emailAddress").sendKeys("testing123@gmail.com");
    	waitAndGetById("creditCardNumber").sendKeys("4111111111111111");
    	waitAndGetById("creditCardMonth").sendKeys("01");
    	waitAndGetById("creditCardYear").sendKeys("2020");
    	waitAndGetById("creditcardname").sendKeys("Test Test");
    	waitAndGetById("creditCardCVV2").sendKeys("123");
    }
}
