package webdriverTasks;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTest {
	static WebDriver myDriver;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
	}
	
	@Before
	public void setUp() {
		 myDriver = new ChromeDriver();
		myDriver.manage().window().maximize();
	}
	@Test
	public void openGoogleTest() {
		myDriver.get("http://www.google.co.uk");
		WebElement myElement = 	myDriver.findElement(By.cssSelector("#tsf > div.tsf-p > div.jsb > center > input[type=\"submit\"]:nth-child(1)"));
		assertTrue(myElement.isDisplayed());	
	}
	
	@After
	public void tearDown() {
		myDriver.close();
	}

}
