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
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class interactableTest {
	
	private WebDriver driver;
	private static ExtentReports extent;
	private static String webAddress;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Admin\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		extent = new ExtentReports("C:\\Users\\Admin\\External Apps\\reports\\extentReportDraggable.html", true);
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
	  public void droppableTest() {
		  ExtentTest extentOGTest = extent.startTest("Droppable default test");
		  driver.get(webAddress);
		  extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
			
		  driver.findElement(By.linkText("Droppable")).click();
		  extentOGTest.log(LogStatus.INFO, "Switching to the correct UI");
		  
		  WebElement drag = driver.findElement(By.cssSelector("#draggableview"));
		  WebElement drop = driver.findElement(By.cssSelector("#droppableview"));
		  
		  Actions builder = new Actions(driver);

		  Action dragAndDrop = builder.clickAndHold(drag)
		     .release(drop)
		     .build();

		  dragAndDrop.perform();
		  extentOGTest.log(LogStatus.INFO, "Dragging and dropping the element in the correct place");
		    
		  String result = driver.findElement(By.cssSelector("#droppableview > p")).getText();
		  try {
		  	assertEquals("The item was not dropped correctly", result, "Dropped!");
			extentOGTest.log(LogStatus.PASS, "Element can successfully drop into other element, navigated successfully");
		} catch (AssertionError e) {
			extentOGTest.log(LogStatus.FAIL, "Element not displayed");
			fail();
		}
		    
	  }
	  
	  @Test
	  public void droppable2Test() {
		  ExtentTest extentOGTest = extent.startTest("Droppable accept test");
		  driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
			
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-2")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the correct UI");
		    
		    WebElement dragInvalid = driver.findElement(By.cssSelector("#draggable-nonvalid"));
		    WebElement drag = driver.findElement(By.cssSelector("#draggableaccept"));
		    WebElement drop = driver.findElement(By.cssSelector("#droppableaccept"));
		    
		    Actions builder = new Actions(driver);

		    Action dragFail = builder.clickAndHold(dragInvalid)
			       .release(drop)
			       .build();

			dragFail.perform();
			extentOGTest.log(LogStatus.INFO, "Dragging and dropping the element that is not valid");
			
			String result = driver.findElement(By.cssSelector("#droppableaccept > p")).getText();		    
			try {
				assertNotEquals("The item was not dropped correctly", result, "Dropped!");
				extentOGTest.log(LogStatus.PASS, "Non-valid element did not drop into the other");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
			
		    Action dragPass = builder.clickAndHold(drag)
		       .release(drop)
		       .build();

		    dragPass.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging and dropping the element that is valid");
		    
		    result = driver.findElement(By.cssSelector("#droppableaccept > p")).getText();		    
		    try {
		    	assertEquals("The item was not dropped correctly", result, "Dropped!");
				extentOGTest.log(LogStatus.PASS, "Valid element dropped into the other");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
		    
	  }
	  
	  @Test
	  public void droppable3Test() {
		  ExtentTest extentOGTest = extent.startTest("Droppable propagation test");
		  driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
			
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-3")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the correct UI");
		    
		    WebElement dragProp = driver.findElement(By.cssSelector("#draggableprop"));
		    WebElement dropGreedy = driver.findElement(By.cssSelector("#droppable2-inner"));
		    WebElement dropGiving = driver.findElement(By.cssSelector("#droppable-inner"));
		    
		    Actions builder = new Actions(driver);

		    Action dragGreed = builder.clickAndHold(dragProp)
			       .release(dropGreedy)
			       .build();

			dragGreed.perform();
			extentOGTest.log(LogStatus.INFO, "Dragging and dropping to the inner element that accepts to itself");
			
			String result = driver.findElement(By.cssSelector("#droppable2-inner > p")).getText();
			String resultOut = driver.findElement(By.cssSelector("#droppableprop2 > p")).getText();
			
			try {
				assertNotEquals("The item was not dropped correctly", resultOut, "Dropped!");
				assertEquals("The item was not dropped correctly", result, "Dropped!");
				extentOGTest.log(LogStatus.PASS, "Only the inner accepted the element");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
		    
		    
		    Action dragPass = builder.clickAndHold(dragProp)
		       .release(dropGiving)
		       .build();

		    dragPass.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging and dropping to the inner element that accepts to itself and the outer element");
		    
		    result = driver.findElement(By.cssSelector("#droppable-inner > p")).getText();
		    resultOut = driver.findElement(By.cssSelector("#droppableprop > p")).getText();
		    assertEquals("The item was not dropped correctly", result, "Dropped!");
		    assertEquals("The item was not dropped correctly", resultOut, "Dropped!");
		    
		    try {
		    	assertEquals("The item was not dropped correctly", result, "Dropped!");
			    assertEquals("The item was not dropped correctly", resultOut, "Dropped!");
				extentOGTest.log(LogStatus.PASS, "Both inner and outer accepted the element");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
		    
	  }	  
	  
	  @Test
	  public void droppable4Test() throws InterruptedException {
		  ExtentTest extentOGTest = extent.startTest("Droppable revert test");
		  driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
			
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-4")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the correct UI");
		    
		    WebElement dragRevert = driver.findElement(By.cssSelector("#draggablerevert"));
		    int reverterLocationX = dragRevert.getLocation().getX();
		    int reverterLocationY = dragRevert.getLocation().getY();
		    WebElement dragDropRevert = driver.findElement(By.cssSelector("#draggablerevert2"));
		    int reverterLocation2X = dragRevert.getLocation().getX();
		    int reverterLocation2Y = dragRevert.getLocation().getY();
		    WebElement dropRevert = driver.findElement(By.cssSelector("#droppablerevert"));
		    
		    Actions builder = new Actions(driver);

		    Action dragReverter = builder.clickAndHold(dragRevert)
			       .release(dropRevert)
			       .build();

		    dragReverter.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging and dropping the element that returns to its position");
		    Thread.sleep(2000);
		    extentOGTest.log(LogStatus.INFO, "Waiting 2 seconds");
			try {
				assertEquals("The revert X has changed.", dragRevert.getLocation().getX(), reverterLocationX);
				assertEquals("The revert Y has changed.", dragRevert.getLocation().getY(), reverterLocationY);
				extentOGTest.log(LogStatus.PASS, "The element reverted to its original position");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
		    
		    
		    Action dragPass = builder.clickAndHold(dragDropRevert)
		       .release(dropRevert)
		       .build();

		    dragPass.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging and dropping the element that does not return to its position");
		    Thread.sleep(2000);
		    extentOGTest.log(LogStatus.INFO, "Waiting 2 seconds");
			
			try {
				assertNotEquals("The revert X has changed.", dragDropRevert.getLocation().getX(), reverterLocation2X);
				assertNotEquals("The revert Y has changed.", dragDropRevert.getLocation().getY(), reverterLocation2Y);
				extentOGTest.log(LogStatus.PASS, "The element did not revert to its original position");
			} catch (AssertionError e) {
				extentOGTest.log(LogStatus.FAIL, "Element not displayed");
				fail();
			}
		    
	  }	  
	  
	  @Test
	  public void droppable5Test() {
		  ExtentTest extentOGTest = extent.startTest("Droppable shopping cart test");
			driver.get(webAddress);
			extentOGTest.log(LogStatus.INFO, "Navigating to " + webAddress);
			
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-5")).click();
		    extentOGTest.log(LogStatus.INFO, "Switching to the correct UI");
		    
		    WebElement dragcartShirt = driver.findElement(By.cssSelector("#ui-id-7 > ul > li:nth-child(1)"));
		    WebElement dragcartBag = driver.findElement(By.cssSelector("#ui-id-9 > ul > li:nth-child(3)"));
		    WebElement dragcartGadget = driver.findElement(By.cssSelector("#ui-id-11 > ul > li:nth-child(2)"));
		    WebElement dropCart = driver.findElement(By.cssSelector("#cart > div > ol"));
		    
		    Actions builder = new Actions(driver);

		    Action dragShirt = builder.clickAndHold(dragcartShirt)
			       .release(dropCart)
			       .build();

		    dragShirt.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging shirt item to the cart");
		    
		    
		    Action dragBag = builder.clickAndHold(dragcartBag)
		       .release(dropCart)
		       .build();

		    dragBag.perform();
		    extentOGTest.log(LogStatus.INFO, "Dragging bag item to the cart");
		    
		    Action dragGadget = builder.clickAndHold(dragcartGadget)
				       .release(dropCart)
				       .build();

			dragGadget.perform();
			extentOGTest.log(LogStatus.INFO, "Dragging gadget item to the cart");
			
			String cart = driver.findElement(By.cssSelector("#cart > div > ol > li")).getText();
			assertNotEquals("The shopping cart is still empty", cart, "Add your items here");
			
			try {
				assertNotEquals("The shopping cart is still empty", cart, "Add your items here");
				extentOGTest.log(LogStatus.PASS, "The element(s) were added to the cart");
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
