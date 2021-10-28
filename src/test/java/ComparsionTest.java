import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;


@SpiraTestConfiguration(
		url = "https://rmit-university.spiraservice.net",
		login = "s3694521", 
		rssToken = "{154EFD18-A5F9-4920-943D-69749269049D}", 
		projectId = 489
	)

@TestMethodOrder(OrderAnnotation.class)
class ComparsionTest {

	private static ChromeDriver driver;
	private WebElement element;
	private WebDriverWait wait;
	private Actions action;
	
	@BeforeAll
	public static void setUp() throws Exception {
		System.setProperty("Webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();	
	}

	@Test
	@Order(1)
	@SpiraTestCase(testCaseId = 9669)
	public void findSummerDress() throws InterruptedException {
		driver.get("http://automationpractice.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		action=new Actions(driver);
		action.moveToElement(driver.findElementByXPath("//*[@id=\"block_top_menu\"]/ul/li[1]/a")).perform();
		Thread.sleep(3000);
		driver.findElementByXPath("//*[@id=\"block_top_menu\"]/ul/li[1]/ul/li[2]/ul/li[3]/a").click();
		assertEquals("SUMMER DRESSES ", driver.findElementByCssSelector("#center_column > h1 > span.cat-name").getText());
	}
	
	@Test
	@Order(2)
	@SpiraTestCase(testCaseId = 9670)
	public void sortByPrice() throws InterruptedException {
		//sort dresses between $16-$29
		//find Top price button
		element = driver.findElementByXPath("//*[@id=\"layered_price_slider\"]/a[2]");
		action = new Actions(driver);
		action.dragAndDropBy(element, -30, 0).perform();
		//leave already enough time to loading...
		Thread.sleep(10000);
		Boolean filtered = driver.findElements(By.cssSelector("#center_column > ul > "
				+ "li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-line."
				+ "last-item-of-tablet-line.last-mobile-line")).size() <= 0;
		assertTrue(filtered);
	}
	
	@Test
	@Order(3)
	@SpiraTestCase(testCaseId = 9833)
	public void sortByColor() throws InterruptedException {
		//refresh the page
		driver.navigate().refresh();
		//only showing one white dress
		driver.findElementById("layered_id_attribute_group_8").click();
		//leave already enough time to loading...
		Thread.sleep(10000);
		//Printed Summer Dress should be filtered
		Boolean filtered1 = driver.findElements
				(
					By.cssSelector
					(
						"#center_column > ul > "
						+ "li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.first-in"
						+ "-line.last-line.first-item-of-tablet-line.first-item-of-mo"
						+ "bile-line.last-mobile-line"
					)
				).size() <= 0;
		//Printed Chiffon Dress should be filtered
		Boolean filtered2 = driver.findElements	
				(
						By.cssSelector("#center_column > ul > li.ajax_block_product.col-"
								+ "xs-12.col-sm-6.col-md-4.last-in-line.last-line.first-"
								+ "item-of-tablet-line.last-item-of-mobile-line.last-mobile-line"
								)
				).size() <= 0;
				
				
				
		assertTrue(filtered1 && filtered2);
		driver.navigate().refresh();
		Thread.sleep(3000);
	}
	
	@Test
	@Order(4)
	@SpiraTestCase(testCaseId = 9671)
	public void addToComparsion() throws InterruptedException {
		//add first product
		driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div[3]/div[2]/a")).click();
		Thread.sleep(3000);
		//add second product
		driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[3]/div/div[3]/div[2]/a")).click();
		Thread.sleep(3000);
		//Click compare button
		driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[3]/div/form/button")).click();
		Thread.sleep(3000);     
		boolean twoExist = (
					driver.findElements(By.cssSelector("#product_comparison > "
					+ "tbody > tr:nth-child(1) > "
					+ "td.ajax_block_product.comparison_infos."
					+ "product-block.product-5")).size() > 0
				) 
				&& 
				(
					driver.findElements(By.cssSelector("#product_comparison "
					+ "> tbody > tr:nth-child(1) > "
					+ "td.ajax_block_product.comparison_infos."
					+ "product-block.product-7")).size() > 0
				);
		
		assertTrue(twoExist);
	}
	
	@Test
	@Order(5)
	@SpiraTestCase(testCaseId = 9673)
	public void deleteOneFromCompare() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"product_comparison\"]/tbody/tr[1]/td[3]/div[1]/a").click();
		Thread.sleep(3000);
		boolean exist = driver.findElements(By.cssSelector("#product_comparison "
				+ "> tbody > tr:nth-child(1) > "
				+ "td.ajax_block_product.comparison_infos."
				+ "product-block.product-7")).size() > 0;
		assertTrue(!exist);
	}
	
	@Test
	@Order(6)
	@SpiraTestCase(testCaseId = 9674)
	public void backToRootPage() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"center_column\"]/ul/li/a").click();
		Thread.sleep(3000);
		assertEquals("http://automationpractice.com/index.php", driver.getCurrentUrl());
	}
	
	@AfterAll
	public static void closeBrowser() {
		driver.close();
	}

}
