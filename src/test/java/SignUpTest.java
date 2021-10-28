import static org.junit.Assert.assertEquals;

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
import org.openqa.selenium.support.ui.Select;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;

@SpiraTestConfiguration(
		url = "https://rmit-university.spiraservice.net",
		login = "s3694521", 
		rssToken = "{154EFD18-A5F9-4920-943D-69749269049D}", 
		projectId = 489
	)

@TestMethodOrder(OrderAnnotation.class)
class SignUpTest {
	private static ChromeDriver driver;
	private WebElement element;
	
	
	@BeforeAll
	public static void setUp() throws Exception {
		System.setProperty("Webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();	
	}
	
	
	@Test
	@Order(1)
	@SpiraTestCase(testCaseId = 9394)
	public void testWebSiteCon() {
		driver.get("http://automationpractice.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		assertEquals("My Store", driver.getTitle());
	}
	
	@Test
	@Order(2)
	@SpiraTestCase(testCaseId = 9395)
	public void signInButton() {
		driver.findElement(By.cssSelector("#header > div.nav > div > div > nav > div.header_user_info > a")).click();
		assertEquals("Login - My Store", driver.getTitle());
	}
	

	@Test
	@Order(3)
	@SpiraTestCase(testCaseId = 9401)
	public void emailExistCheck() throws InterruptedException {
		//Enter non-registered email address
		driver.findElement(By.cssSelector("#email_create")).sendKeys("stephen@example.com");
		//Click Create account button at login page
		driver.findElement(By.id("SubmitCreate")).click();
		//Waiting for elements loading
		Thread.sleep(2000);
		//get error message
		String error = driver.findElement(By.xpath("//*[@id=\"create_account_error\"]/ol/li")).getText();
		assertEquals("An account using this email address "
				+ "has already been registered. "
				+ "Please enter a valid password or request a new one.", error);
		Thread.sleep(3000);
	}
	
	@Test
	@Order(4)
	@SpiraTestCase(testCaseId = 9396)
	public void enterEmailAndCheckAutoInput() throws InterruptedException {
		//Clear the registered email from input bar
		driver.findElement(By.cssSelector("#email_create")).clear();
		//Enter non-registered email address
		driver.findElement(By.cssSelector("#email_create")).sendKeys("stephen123456789@example.com");
		//Click Create account button at login page
		driver.findElement(By.id("SubmitCreate")).click();
		//Waiting for elements loading
		Thread.sleep(3000);
		//get Actual value from input bar
		String email = driver.findElement(By.id("email")).getAttribute("value");
		assertEquals("stephen123456789@example.com", email);
	}
	
	@Test
	@Order(5)
	@SpiraTestCase(testCaseId = 9397)
	public void enterInfoAndCreate() throws InterruptedException {
		Thread.sleep(2000);
		//Select Title -- Mr
		driver.findElement(By.id("id_gender1")).click();
		//Enter First name
		driver.findElement(By.id("customer_firstname")).sendKeys("Hongyang");
		//Enter Last name
		driver.findElement(By.id("customer_lastname")).sendKeys("Lyu");
		//Enter password
		driver.findElement(By.id("passwd")).sendKeys("123456");
		//Enter Address first name
		driver.findElement(By.id("firstname")).sendKeys("Hongyang");
		//Enter Address last name
		driver.findElement(By.id("lastname")).sendKeys("Lyu");
		//Enter Address
		driver.findElement(By.id("address1")).sendKeys("4/7A parker st, Ormond");
		//Enter City
		driver.findElement(By.id("city")).sendKeys("Melbourne");
		//Select State
		element = driver.findElement(By.id("id_state"));
		Select select = new Select(element);
		select.selectByValue("32");
		//Enter post code
		driver.findElement(By.id("postcode")).sendKeys("00000");
		//Enter phone number
		driver.findElement(By.id("phone_mobile")).sendKeys("0412345678");
		//Click button to create account
		driver.findElement(By.id("submitAccount")).click();
		//Waiting for elements loading
		Thread.sleep(2000);
		//should be my account page
		String welcome = driver.findElementByXPath("//*[@id=\"center_column\"]/p").getText();
		assertEquals("Welcome to your account."
				+ " Here you can manage all of your "
				+ "personal information and orders.", welcome);
	}
	
	@AfterAll
	public static void closeBrowser() {
		driver.close();
	}

}
