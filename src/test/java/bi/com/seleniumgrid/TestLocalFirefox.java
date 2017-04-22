package bi.com.seleniumgrid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestLocalFirefox {

	public WebDriver driver;
	public String URL, Node;
	String pageTitgle;
	String sWelcome;
	String sActivationLink;


	@BeforeTest
	public void launchbrowser() throws MalformedURLException {
		URL = "https://bryant.pprd.goalquestprogram.com";
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\geckodriver.exe");
		
		String Node = "http://192.168.0.108:5557/wd/hub";
		DesiredCapabilities cap = DesiredCapabilities.firefox();
		cap.setBrowserName("firefox");
		driver = new RemoteWebDriver(new URL(Node), cap);
		
		//driver = new FirefoxDriver();
		//driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Launch website
		driver.navigate().to(URL);
		driver.manage().window().maximize();
		}
	
	
	
	@Test
	public void calculatepercent() throws Exception {
		
		Thread.sleep(3000);
		//Verify Page title
		pageTitgle = driver.getTitle().trim();
		Assert.assertEquals(pageTitgle, "GoalQuest");
		//Enter the username, password and click on log in
		driver.findElement(By.xpath("//*[@id='formUserId']")).sendKeys("cp-012");
		driver.findElement(By.xpath("//*[@id='formPassword']")).sendKeys("Testing123!");
		driver.findElement(By.xpath("//*[@id='app-root']/div/div[1]/div/form/a")).click();
		Thread.sleep(3000);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//Verify the welcome page
		sWelcome= driver.findElement(By.xpath("//*[@id='welcome']/div[1]/div[1]/h3")).getText().trim();
		Assert.assertEquals(sWelcome, "WELCOME");
		Thread.sleep(3000);
		//Click on log out. 
		driver.findElement(By.xpath("//*[@id='app-root']/div/div[1]/div/div[3]/div[2]/div/span[2]/a[2]")).click();
		Thread.sleep(3000);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//Verify page title
		sActivationLink= driver.findElement(By.xpath("//*[@id='app-root']/div/div[1]/div/form/p/a")).getText().trim().toString();
		Assert.assertEquals(sActivationLink, "Activate Account");
	}
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
	
}
