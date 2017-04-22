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

public class TestGridClass {

	public WebDriver driver;
	public String URL, Node;
	String pageTitgle;
	String sWelcome;
	String sActivationLink;
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;
	@Parameters("browser")
	@BeforeTest
	public void launchbrowser(String browser) throws MalformedURLException {
		String URL = "https://bryant.pprd.goalquestprogram.com";
		if (browser.equalsIgnoreCase("firefox")) {
		System.out.println(" Executing on FireFox");
		String Node = "http://192.168.0.108:5557/wd/hub";
		//String Node = "http://192.168.0.121:5557/wd/hub";
		DesiredCapabilities cap = DesiredCapabilities.firefox();
		cap.setBrowserName("firefox");
		driver = new RemoteWebDriver(new URL(Node), cap);
		// Puts an Implicit wait, Will wait for 10 seconds before throwing
		// exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Launch website
		driver.navigate().to(URL);
		driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("chrome")) {
		System.out.println(" Executing on CHROME");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		String Node = "http://192.168.0.121:5556/wd/hub";
		//String Node = "http://172.17.0.10:5555/wd/hub";
		driver = new RemoteWebDriver(new URL(Node), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Launch website
		driver.navigate().to(URL);
		driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("ie")) {
		System.out.println(" Executing on IE");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("ie");
		String Node = "http://192.168.0.121:5555/wd/hub";
		driver = new RemoteWebDriver(new URL(Node), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Launch website
		driver.navigate().to(URL);
		driver.manage().window().maximize();
		} else {
		throw new IllegalArgumentException("The Browser Type is Undefined");
		}
	}
	
	
	
	@Test
	public void calculatepercent() throws Exception {
		Thread.sleep(10000);
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
