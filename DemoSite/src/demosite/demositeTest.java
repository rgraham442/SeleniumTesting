	package demosite;

	import org.junit.*;
	import static org.junit.Assert.*;
	import org.openqa.selenium.*;
	import org.openqa.selenium.chrome.ChromeDriver;

	public class demositeTest {
	  private WebDriver driver;

	  @BeforeClass
		public static void init() {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
		}
		
		@Before
		public void setUp() {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}

	  @Test
	  public void loginTest() {
	    driver.get("http://thedemosite.co.uk/");
	    driver.findElement(By.linkText("3. Add a User")).click();
	    driver.findElement(By.name("username")).click();
	    driver.findElement(By.name("username")).sendKeys("user");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='MySQL'])[1]/following::td[2]")).click();
	    driver.findElement(By.name("password")).click();
	    driver.findElement(By.name("password")).sendKeys("pass");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='MySQL'])[1]/following::td[2]")).click();
	    driver.findElement(By.name("FormsButton2")).click();
	    
	    driver.findElement(By.linkText("4. Login")).click();
	    driver.findElement(By.name("username")).click();
	    driver.findElement(By.name("username")).sendKeys("user");
	    driver.findElement(By.name("password")).click();
	    driver.findElement(By.name("password")).sendKeys("pass");
	    driver.findElement(By.name("FormsButton2")).click();
	    WebElement login = driver.findElement(By.cssSelector("body > table > tbody > tr > td.auto-style1 > big > blockquote > blockquote > font > "
	    		+ "center > b"));
	    assertEquals("The attempt was unsuccessful",login.getText(),"**Successful Login**");
	  }

	  @After
	  public void tearDown() {
	  driver.close();
	  }
	}
