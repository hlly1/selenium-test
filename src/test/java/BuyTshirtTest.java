import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;

@SpiraTestConfiguration(
	url = "https://rmit-university.spiraservice.net",
	login = "s3694521", 
	rssToken = "{154EFD18-A5F9-4920-943D-69749269049D}", 
	projectId = 489
)

/*
 * This test is checking buying 1 Faded Short Sleeve T-shirts with not logged in.
 * For testing redirecting to correct page, I d like to use unique content (e.g. title or <h1>) to check for some URLs are dynamic.
 * But I will also try to use URL to check
 */
@TestMethodOrder(OrderAnnotation.class)
class BuyTshirtTest {

	private static ChromeDriver driver;

	
	@BeforeAll
	public static void setUp() throws Exception {
		System.setProperty("Webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();	
	}

	@Test
	@Order(1)
	@SpiraTestCase(testCaseId = 9432)
	public void addOneToCart() throws InterruptedException {
		driver.get("http://automationpractice.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElementByXPath("//*[@id=\"homefeatured\"]/li[1]/div/div[2]/div[2]/a[1]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a").click();
		assertEquals("http://automationpractice.com/index.php?controller=order", driver.getCurrentUrl());
	}
	
	@Test
	@Order(2)
	@SpiraTestCase(testCaseId = 9433)
	public void TshirtNameCheck() {
		assertEquals("Faded Short Sleeve T-shirts", driver.findElementByXPath("//*[@id=\"product_1_1_0_0\"]/td[2]/p/a").getText());
	}
	
	@Test
	@Order(3)
	@SpiraTestCase(testCaseId = 9434)
	public void avaiableCheck() {
		assertEquals("In stock", driver.findElementByXPath("//*[@id=\"product_1_1_0_0\"]/td[3]/span").getText());
	}
	
	@Test
	@Order(4)
	@SpiraTestCase(testCaseId = 9435)
	public void unitPriceCheck() {
		assertEquals("$16.51", driver.findElementByXPath("//*[@id=\"product_price_1_1_0\"]/span").getText());
	}	
	
	@Test
	@Order(5)
	@SpiraTestCase(testCaseId = 9436)
	public void totalPriceCheck() {
		assertEquals("$18.51", driver.findElementByXPath("//*[@id=\"total_price\"]").getText());
	}
	
	@Test
	@Order(6)
	@SpiraTestCase(testCaseId = 9437)
	public void jumpToLogin() {
		driver.findElementByXPath("//*[@id=\"center_column\"]/p[2]/a[1]").click();
		assertEquals("http://automationpractice.com/index.php?"
				+ "controller=authentication&multi-shipping=0"
				+ "&display_guest_checkout=0&back=http%3A%2F%2Fautoma"
				+ "tionpractice.com%2Findex.php%3Fcontroller%3Dord"
				+ "er%26step%3D1%26multi-shipping%3D0", driver.getCurrentUrl());
	}
	
	@Test
	@Order(7)
	@SpiraTestCase(testCaseId = 9438)
	public void enterToLogin() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElementByXPath("//*[@id=\"email\"]").sendKeys("stephen@example.com");
		driver.findElementByXPath("//*[@id=\"passwd\"]").sendKeys("123456");
		driver.findElementById("SubmitLogin").click();
		Thread.sleep(3000);
		assertEquals("ADDRESSES"
				, driver.findElementByXPath("//*[@id=\"center_column\"]/h1").getText());
	}
	
	@Test
	@Order(8)
	@SpiraTestCase(testCaseId = 9439)
	public void jumpToShippingPage() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"center_column\"]/form/p/button").click();
		Thread.sleep(3000);
		assertEquals("SHIPPING", driver.findElementByXPath("//*[@id=\"carrier_area\"]/h1").getText());
	}	
	
	@Test
	@Order(9)
	@SpiraTestCase(testCaseId = 9565)
	public void notAgreeTerm() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"form\"]/p/button").click();
		Thread.sleep(3000);
		assertEquals("You must agree to the terms of "
				+ "service before continuing.", driver.findElementByXPath("//*[@id=\"order\"]/div[2]/div/div/div/div/p").getText());
	}	
	
	@Test
	@Order(10)
	@SpiraTestCase(testCaseId = 9440)
	public void agreeAndjumpToPayment() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"order\"]/div[2]/div/div/a").click();
		Thread.sleep(3000);
		driver.findElementById("cgv").click();
		driver.findElementByXPath("//*[@id=\"form\"]/p/button").click();
		Thread.sleep(3000);
		assertEquals("PLEASE CHOOSE YOUR PAYMENT METHOD", driver.findElementByXPath("//*[@id=\"center_column\"]/h1").getText());
	}
	
	@Test
	@Order(11)
	@SpiraTestCase(testCaseId = 9442)
	public void payByCheck() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"HOOK_PAYMENT\"]/div[2]/div/p/a").click();
		Thread.sleep(3000);
		assertEquals("ORDER SUMMARY", driver.findElementByXPath("//*[@id=\"center_column\"]/h1").getText());
	}	
	
	@Test
	@Order(12)
	@SpiraTestCase(testCaseId = 9443)
	public void confirmOrder() throws InterruptedException {
		driver.findElementByXPath("//*[@id=\"cart_navigation\"]/button").click();
		Thread.sleep(3000);
		assertEquals("Your order on My Store is complete.", driver.findElementByXPath("//*[@id=\"center_column\"]/p[1]").getText());
	}
	
	@AfterAll
	public static void closeBrowser() {
		driver.close();
	}
	
}
