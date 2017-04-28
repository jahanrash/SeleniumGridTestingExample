/*
 * (C) Copyright 2016 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package bi.com.seleniumgrid;

// import org.junit.After;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Test with PhantomJS.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class PhantomJsTest {

  private static String PHANTOMJS_BINARY ;
  private WebDriver driver;
  String pageTitle;

  @BeforeSuite
  public static void setupClass() {
    PHANTOMJS_BINARY = System.getProperty("phantomjs.binary");
    //PhantomJsDriverManager.getInstance().setup();
  }

  @BeforeTest
  public void setupTest() {
    driver = new PhantomJSDriver();
  }

  @AfterTest
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void test() {

    final DesiredCapabilities capabilities = new DesiredCapabilities();

    // Configure our WebDriver to support JavaScript and be able to find the PhantomJS binary
    capabilities.setJavascriptEnabled(true);
    capabilities.setCapability("takesScreenshot", false);
    capabilities.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            PHANTOMJS_BINARY
    );

    final WebDriver driver = new PhantomJSDriver(capabilities);
    // Your test code here. For example:
    WebDriverWait wait = new WebDriverWait(driver, 30); // 30 seconds of timeout
    driver.get("https://en.wikipedia.org/wiki/Main_Page"); // navigate to Wikipedia

    pageTitle = driver.getTitle().trim();
    Assert.assertEquals(pageTitle, "GoalQuest");


    System.out.println("Page title is: " + driver.getTitle());

    By searchInput = By.id("searchInput"); // search for "Software"
    wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
    driver.findElement(searchInput).sendKeys("Software");
    By searchButton = By.id("searchButton");
    wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    driver.findElement(searchButton).click();

    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
        "Computer software")); // assert that the resulting page contains a text
  }

}