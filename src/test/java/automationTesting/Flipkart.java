package automationTesting;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Flipkart {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"Chromedriver_path");
		//Edit Chromedriver_path with your actual path
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		driver = new ChromeDriver();

		// if we want to use FireFox driver we can edit the system set property or we
		// can use if/else condition and use more than one browsers

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.manage().window().maximize();

		driver.get("https://www.flipkart.com/");

	}

	@Test(priority = 1, groups = "Sanity, Regression")
	public void closeLoginmodal() {
		driver.findElement(By.xpath("//span[text()='✕']")).click();
	}

	@Test(priority = 2, groups = "Sanity, Regression")
	public void hoverMI() throws Exception {
		WebElement mobiles = driver.findElement(By.xpath("//span[text()='Mobiles']"));
		mobiles.click();
		WebElement electronics = driver.findElement(By.xpath("//span[text()='Electronics']"));
		actions = new Actions(driver);
		actions.moveToElement(electronics).perform();
		Thread.sleep(2000);
		WebElement mi = driver.findElement(By.xpath("//a[text()='Mi']"));
		actions.moveToElement(mi).click().perform();
		actions.release();
	}

	@Test(priority = 3, groups = "Sanity, Regression")
	public void priceSlider() throws Exception {

		Thread.sleep(2000);
		WebElement priceslider = driver.findElement(
				By.xpath("/html/body/div/div/div[3]/div[1]/div[1]/div/div[1]/div/section[2]/div[3]/div[1]/div[2]/div"));
		Thread.sleep(2000);
		Actions action = new Actions(driver);
		action.dragAndDropBy(priceslider, -50, 0).perform();

		Thread.sleep(1000);
		action.release();

		WebElement dropdown = driver.findElement(By.xpath(
				"//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div/div[1]/div/section[2]/div[4]/div[3]/select"));
		Select selectprice = new Select(dropdown);
		selectprice.selectByIndex(2);

	}

	@Test (priority =4, groups = "Sanity, Regression")
	public void search() throws Exception {
		
		WebElement searchbar = driver.findElement(By.xpath("//input[@type='text']"));
		searchbar.sendKeys("APPLE iPhone 14 (Midnight, 128 GB)");
		
		WebElement subBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		subBtn.click();
		
		Thread.sleep(5000);
		
		WebElement phone = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[2]/div/div/div/a/div[3]/div[1]/div[1]"));
		phone.click();
		
		Set <String> set = driver.getWindowHandles();
		
		Iterator <String> it = set.iterator();
		
		String parentwindowID = it.next();
		String childwindowID = it.next();
		
		driver.switchTo().window(childwindowID);
		
		WebElement price = driver.findElement(By.xpath("//div[@class='_30jeq3 _16Jk6d']"));
		String priceamount = price.getText();
		String numericvalue = priceamount.replaceAll("[₹,]", "");
		double amount = Double.parseDouble(numericvalue);
		
		if (amount >= 0) {
            System.out.println("Product amount is greater than or equal to 0: " + amount);
        } else {
            System.out.println("Product amount is less than 0: " + amount);
        }
	}

	@Test(priority = 5, groups = "Sanity, Regression ")
	public void productverify() throws Exception {

		Thread.sleep(3000);

		WebElement Frame = driver.findElement(By.xpath(
				"//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[1]/div/div[1]/div[1]/div/div[1]/ul/li[1]/div/div/img"));

		boolean status = Frame.isDisplayed();
		System.out.println(status);

		WebElement VideoIcon = driver.findElement(By.xpath(
				"//*[@id='container']/div/div[3]/div[1]/div[1]/div[1]/div/div[1]/div[1]/div/div[1]/ul/li[2]/div/div[3]"));
		VideoIcon.click();

		WebElement pincode = driver.findElement(By.id("pincodeInputId"));
		actions.sendKeys("12345").build().perform();
		actions.release();

		Thread.sleep(2000);

		WebElement viewdetails = driver.findElement(By.xpath("//span[text()='View Details']"));
		viewdetails.click();

		WebElement cross = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/button"));
		cross.click();

		Thread.sleep(2000);

		WebElement addtocart = driver
				.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[2]/div/ul/li[1]/button"));
		addtocart.click();

		driver.quit();

	}

	@AfterClass
	public void closebrowser() {
		driver.quit();
	}
}
