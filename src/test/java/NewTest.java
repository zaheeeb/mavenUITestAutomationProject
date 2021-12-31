import static org.testng.Assert.assertEquals;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

public class NewTest {
	
  WebDriver driver;
  
  @BeforeTest
  public void testSetup(){
	    // Setuo chromedriver
	     System.setProperty("webdriver.chrome.driver", "/Users/zahir.babur@schibsted.com/Desktop/QA/MavenProject/chromedriver");
	     driver = new ChromeDriver();
	     
	    // Wait Explicitly 10 seconds for elements to be visible
	     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	     
	    // Maximize window
	     driver.manage().window().maximize();
	     
	    // Access site 
	     driver.get("http://www.konakart.com/konakart/Welcome.action");
  }
  @Test
  public void test() {
	  // Access advance search page to search desired items from games with given price range.   
	     WebElement advanceSearch = driver.findElement(By.xpath("//div[@id='header']//a[@id='adv-search-link']"));
	     advanceSearch.click();
	     Select category = new Select(driver.findElement(By.name("categoryId")));
	     category.selectByVisibleText("Games");
	     driver.findElement(By.xpath("//*[@id=\"priceFromStr\"]")).sendKeys("39");
	     WebElement priceTo = driver.findElement(By.xpath("//*[@id=\"priceToStr\"]"));
	     priceTo.sendKeys("80");
	  
	  // Click on search button
	     WebElement searchBtn = driver.findElement(By.xpath("//span[normalize-space()='Search']"));
	     searchBtn.click();
	  
	  // Store price of selected game
	     String gameonePrice = driver.findElement(By.xpath("//div[@id='item-overview']/div[2]/ul/li[2]/div/div[3]/div")).getText().substring(1);
	     Double gameOnePriceDouble = Double.parseDouble(gameonePrice);
	  
	  // Add item to cart (Spent many hours to try to add item to cart by using movetoElement method but didn't have success. When you run test case and reach to list of adds please move mouse manually over the items and then it finds element to add it to cart.)
         //Actions actions = new Actions(driver);
         //actions.moveToElement(driver.findElement(By.xpath("//li[2]/div/div/div[2]/div/a"))).perform();
         
	     driver.findElement(By.xpath("//li[2]/div/div/div[2]/div/a")).click();
	   
	  // Verify Item is added to cart
	    {
	      List<WebElement> elements = driver.findElements(By.cssSelector(".shopping-cart-title"));
	      assert(elements.size() > 0);
	    }
	    
	  // Open first item and get the name
	    WebElement  elem = driver.findElement(By.xpath("//div[@id='item-overview']/div[2]/ul/li/div/a"));
	    String str = elem.getAttribute("innerHTML");
	    driver.findElement(By.xpath("//div[@id='item-overview']/div[2]/ul/li/div/a")).click();
	  
	  // Get the name of game on details page
	    WebElement  gameName = driver.findElement(By.xpath("//h1"));
	    String gameTitle = gameName.getAttribute("innerHTML");
	
	 // Assert game name is same.
	    assertEquals(str,gameTitle);
	  
	 // Get total price of 2 games
	    String gameTwoPrice = driver.findElement(By.xpath("(//div[@id='product-price']/span)[2]")).getText().substring(1);
	    Double gameTwoPriceDouble = Double.parseDouble(gameTwoPrice);
	    Double gameTwoPricesDouble = gameTwoPriceDouble*2;
	    Double SubTotal = (gameTwoPricesDouble + gameOnePriceDouble);
	   
     // assert 4 images of ad
	   {
	      List<WebElement> images = driver.findElements(By.xpath("//div[@id='gallery_nav']/a/img"));
	      assert(images.size() == 4);
	    }
	    
     // Select and add 2 items to cart
	    driver.findElement(By.cssSelector("#AddToCartForm #prodQuantityId")).click();
	    {
	    	{
	    	      WebElement dropdown = driver.findElement(By.cssSelector("#AddToCartForm #prodQuantityId"));
	    	      dropdown.findElement(By.xpath("//form[@id='AddToCartForm']//select[@id='prodQuantityId']//option[. = '2']")).click();
	    	    }
	    }
	    driver.findElement(By.xpath("//div[2]/div/form/div[6]/a")).click();
	    
     // Navigate to shopping cart
	    driver.findElement(By.xpath("//div[@id='shopping-cart']/span[2]")).click();
	    
     // Get total price and make sure calculated price is correct.
	    String Total = driver.findElement(By.xpath("//td[@id='cost-overview']/table/tbody/tr[1]/td[2]")).getText().substring(1);
	    Double Totals = Double.parseDouble(Total);
	    assertEquals(Totals, SubTotal);
	    
     // Navigate to checkout.
	    driver.findElement(By.xpath("//div/a/span")).click();
	    
     // Navigate to create new account because can't checkout only as guest
	    driver.findElement(By.xpath("//div[2]/div/a/span")).click();
	  
	 // Enter/select required fields to create new account
	    driver.findElement(By.id("state")).click();
	   
	    {
	      WebElement dropdown = driver.findElement(By.id("state"));
	      dropdown.findElement(By.xpath("//select[@id='state']//option[. = 'Alabama']")).click();
	    }
	    
	    driver.findElement(By.xpath("(//input[@name='gender'])[1]")).click();
	    driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("First Name");
	    driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Last Name");
	    driver.findElement(By.id("datepicker")).click();
	    driver.findElement(By.linkText("1")).click();
	 // Generate random number to be used in unique email.
	    Random random = new Random();
	    int num = random.nextInt(200);
	    driver.findElement(By.id("emailAddr")).sendKeys("abc"+ num +"@abc.com");
	    driver.findElement(By.id("streetAddress")).sendKeys("street 1");
	    driver.findElement(By.id("postcode")).sendKeys("53800");
	    driver.findElement(By.id("city")).sendKeys("city");
	    driver.findElement(By.id("telephoneNumber")).sendKeys("123456789");
	    driver.findElement(By.id("password")).sendKeys("12345678");
	    driver.findElement(By.id("passwordConfirmation")).sendKeys("12345678");
	    
	    driver.findElement(By.id("continue-button")).click();
	    
	 // Click next on overview page (can't test change of shipping method because there is only one value to be selected)     
	    driver.findElement(By.xpath("//a[@id='continue-button']/span")).click();
	    
	 // Click next on newsletter page   
	    driver.findElement(By.xpath("//a[@id='continue-button']/span")).click();
	    
	 // Assert the status of the order is pending
	    String status = driver.findElement(By.xpath("//td[4]/div")).getText();
	    assertEquals(status, "Pending");
	    	 
  }
  @AfterTest
  public void teardown(){
	 // Quit window
	    driver.quit();
  }

}


