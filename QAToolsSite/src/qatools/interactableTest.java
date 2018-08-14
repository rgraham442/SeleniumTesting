package qatools;

import static org.junit.Assert.*;

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

public class interactableTest {
	
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
	  public void droppableTest() {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Droppable")).click();
		    WebElement drag = driver.findElement(By.cssSelector("#draggableview"));
		    WebElement drop = driver.findElement(By.cssSelector("#droppableview"));
		    
		    Actions builder = new Actions(driver);

		    Action dragAndDrop = builder.clickAndHold(drag)
		       .release(drop)
		       .build();

		    dragAndDrop.perform();
		    
		    String result = driver.findElement(By.cssSelector("#droppableview > p")).getText();
		    assertEquals("The item was not dropped correctly", result, "Dropped!");
		    
	  }
	  
	  @Test
	  public void droppable2Test() {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-2")).click();
		    WebElement dragInvalid = driver.findElement(By.cssSelector("#draggable-nonvalid"));
		    WebElement drag = driver.findElement(By.cssSelector("#draggableaccept"));
		    WebElement drop = driver.findElement(By.cssSelector("#droppableaccept"));
		    
		    Actions builder = new Actions(driver);

		    Action dragFail = builder.clickAndHold(dragInvalid)
			       .release(drop)
			       .build();

			dragFail.perform();
			
			String result = driver.findElement(By.cssSelector("#droppableaccept > p")).getText();
			assertNotEquals("The item was not dropped correctly", result, "Dropped!");
		    
		    Action dragPass = builder.clickAndHold(drag)
		       .release(drop)
		       .build();

		    dragPass.perform();
		    
		    result = driver.findElement(By.cssSelector("#droppableaccept > p")).getText();
		    assertEquals("The item was not dropped correctly", result, "Dropped!");
		    
	  }
	  
	  @Test
	  public void droppable3Test() {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-3")).click();
		    WebElement dragProp = driver.findElement(By.cssSelector("#draggableprop"));
		    WebElement dropGreedy = driver.findElement(By.cssSelector("#droppable2-inner"));
		    WebElement dropGiving = driver.findElement(By.cssSelector("#droppable-inner"));
		    
		    Actions builder = new Actions(driver);

		    Action dragGreed = builder.clickAndHold(dragProp)
			       .release(dropGreedy)
			       .build();

			dragGreed.perform();
			
			String result = driver.findElement(By.cssSelector("#droppable2-inner > p")).getText();
			String resultOut = driver.findElement(By.cssSelector("#droppableprop2 > p")).getText();
			assertNotEquals("The item was not dropped correctly", resultOut, "Dropped!");
			assertEquals("The item was not dropped correctly", result, "Dropped!");
		    
		    
		    Action dragPass = builder.clickAndHold(dragProp)
		       .release(dropGiving)
		       .build();

		    dragPass.perform();
		    
		    result = driver.findElement(By.cssSelector("#droppable-inner > p")).getText();
		    resultOut = driver.findElement(By.cssSelector("#droppableprop > p")).getText();
		    assertEquals("The item was not dropped correctly", result, "Dropped!");
		    assertEquals("The item was not dropped correctly", resultOut, "Dropped!");
		    
	  }	  
	  
	  @Test
	  public void droppable4Test() throws InterruptedException {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-4")).click();
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
		    Thread.sleep(2000);
			assertEquals("The revert X has changed.", dragRevert.getLocation().getX(), reverterLocationX);
			assertEquals("The revert Y has changed.", dragRevert.getLocation().getY(), reverterLocationY);
		    
		    
		    Action dragPass = builder.clickAndHold(dragDropRevert)
		       .release(dropRevert)
		       .build();

		    dragPass.perform();
		    Thread.sleep(2000);
			assertNotEquals("The revert X has changed.", dragDropRevert.getLocation().getX(), reverterLocation2X);
			assertNotEquals("The revert Y has changed.", dragDropRevert.getLocation().getY(), reverterLocation2Y);
		    
	  }	  
	  
	  @Test
	  public void droppable5Test() {
			driver.get("http://demoqa.com/");
		    driver.findElement(By.linkText("Droppable")).click();
		    driver.findElement(By.id("ui-id-5")).click();
		    WebElement dragcartShirt = driver.findElement(By.cssSelector("#ui-id-7 > ul > li:nth-child(1)"));
		    WebElement dragcartBag = driver.findElement(By.cssSelector("#ui-id-9 > ul > li:nth-child(3)"));
		    WebElement dragcartGadget = driver.findElement(By.cssSelector("#ui-id-11 > ul > li:nth-child(2)"));
		    WebElement dropCart = driver.findElement(By.cssSelector("#cart > div > ol"));
		    
		    Actions builder = new Actions(driver);

		    Action dragShirt = builder.clickAndHold(dragcartShirt)
			       .release(dropCart)
			       .build();

		    dragShirt.perform();
		    
		    
		    Action dragBag = builder.clickAndHold(dragcartBag)
		       .release(dropCart)
		       .build();

		    dragBag.perform();
		    
		    Action dragGadget = builder.clickAndHold(dragcartGadget)
				       .release(dropCart)
				       .build();

			dragGadget.perform();
			
			String cart = driver.findElement(By.cssSelector("#cart > div > ol > li")).getText();
			assertNotEquals("The shopping cart is still empty", cart, "Add your items here");
		    
	  }	  

	  @After
	  public void tearDown() {
	  driver.close();
	  }

}
