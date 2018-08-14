package qatools;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class selectableTest {
	
	private WebDriver driver;
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
	}
		
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
		
	 @Test
	  public void selectableTest() {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Selectable")).click();
		    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Serialize'])"
		    		+ "[1]/following::div[2]")).click();
		    
		    WebElement item = driver.findElement(By.cssSelector("#selectable > li:nth-child(3)"));
		    
		    System.out.println(item.getCssValue("background-color"));
		    
	  }

	@After
	public void tearDown() {
	  driver.close();
	}

}
