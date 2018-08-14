package qatools;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class selectableTest {
	
	private WebDriver driver;
	private static ExtentReports extent;
	private static String webAddress;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		extent = new ExtentReports("C:\\Users\\Admin\\External Apps\\reports\\extentReport.html", true);
		ExtentTest initialisationInformation = extent.startTest("Initialisation method");
		
		try (FileInputStream file = new FileInputStream("C:\\Users\\Admin\\External Apps\\reports\\files\\reporting.xlsx");
				XSSFWorkbook myBook = new XSSFWorkbook(file);) {
			initialisationInformation.log(LogStatus.INFO, "Workbook found");
			webAddress = myBook.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
			initialisationInformation.log(LogStatus.INFO, "Web Address found, value: " + webAddress);
			initialisationInformation.log(LogStatus.PASS, "DDT Success");

		} catch (FileNotFoundException e) {
			initialisationInformation.log(LogStatus.FATAL, "Workbook File not found");
			initialisationInformation.log(LogStatus.INFO, "workbook path: C:\\Users\\Admin\\External Apps\\reports\\files\\reporting.xlsx");
		} catch (IOException e) {
			initialisationInformation.log(LogStatus.FATAL, "Could not create workbook");
		}

	}
		
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
		
	 @Test
	  public void defaultTest() {
		ExtentTest extentOGTest = extent.startTest("Default selector test");
		driver.get(webAddress);
		extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		driver.findElement(By.linkText("Selectable")).click();
		driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Serialize'])"
		   		+ "[1]/following::div[2]")).click();
		extentOGTest.log(LogStatus.INFO, "Clicking selectable element");
		    
		WebElement item = driver.findElement(By.cssSelector("#selectable > li.ui-widget-content.ui-corner-left.ui-selectee.ui-selected"));
		
		try {
			assertNotEquals("The item was not selected", item.getCssValue("background-color"), "rgba(255,255,255,1)");
			extentOGTest.log(LogStatus.PASS, "Element is selectable, navigated successfully");
		} catch (AssertionError e) {
			extentOGTest.log(LogStatus.FAIL, "Element not displayed");
			fail();
		}
		    
	  }
	 
	 @Test
	  public void gridTest() {
		 ExtentTest extentOGTest = extent.startTest("Grid selector test");
			driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		    driver.findElement(By.linkText("Selectable")).click();
		    driver.findElement(By.id("ui-id-2")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the grid selector");
		    driver.findElement(By.cssSelector("#selectable_grid > li:nth-child(1)")).click();
		    extentOGTest.log(LogStatus.INFO, "Selecting the element");
		    
		    WebElement item = driver.findElement(By.cssSelector("#selectable_grid > li.ui-state-default.ui-corner-left.ui-selectee.ui-selected"));
		    
		    try {
				assertNotEquals("The item was not selected", item.getCssValue("background-color"), "rgba(255,255,255,1)");
				extentOGTest.log(LogStatus.PASS, "Element is selectable, navigated successfully");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
	 }
	 
	 @Test
	  public void serialTest() {
		 ExtentTest extentOGTest = extent.startTest("Seial selector test");
			driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
		    driver.findElement(By.linkText("Selectable")).click();
		    driver.findElement(By.id("ui-id-3")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the serialize selector");
		    driver.findElement(By.cssSelector("#selectable-serialize > li:nth-child(1)")).click();
		    extentOGTest.log(LogStatus.INFO, "Selecting the element");
		    
		    String result = driver.findElement(By.cssSelector("#select-result")).getText();
		    
		    assertNotEquals("The item was not selected", result, "none");
		    try {
		    	assertNotEquals("The item was not selected", result, "none");
				extentOGTest.log(LogStatus.PASS, "Element is selectable, result changes based on element, navigated successfully");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
	 }

	@After
	public void tearDown() {
	  driver.close();
	  extent.flush();
	}

}
