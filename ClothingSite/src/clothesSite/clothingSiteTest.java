package clothesSite;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class clothingSiteTest {
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
	  public void dressTest() {
		  driver.get("http://automationpractice.com/index.php");
		  driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Summer Dresses'])[1]/following::a[1]")).click();
		  String result = driver.findElement(By.cssSelector("#center_column > h1 > span.heading-counter")).getText();
		  assertFalse("There were no dresses found", result.contains("0"));
	  }
	  
	  @Test
	  public void dressSearchTest() {
		  driver.get("http://automationpractice.com/index.php");
		  driver.findElement(By.id("search_query_top")).click();
		  driver.findElement(By.id("search_query_top")).sendKeys("dress");
		  driver.findElement(By.name("submit_search")).click();
		  String result = driver.findElement(By.cssSelector("#center_column > h1 > span.heading-counter")).getText();
		  assertFalse("There were no dresses found from the search", result.contains("0"));
	  }
	  

	  @After
	  public void tearDown() {
	  driver.close();
	  }
	}
