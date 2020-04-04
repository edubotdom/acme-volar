package acmevolar.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Airline22BookUpdate {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
	@LocalServerPort
	private int				port;
	
  @BeforeEach
  public void setUp() throws Exception {
	  System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
	  driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  
  @Test
  public void testAirline22BookUpdate() throws Exception {
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.linkText("LOGIN")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("airline1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("airline1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("MY BOOKS")).click();
    assertEquals("Books", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.linkText("Edit Book")).click();
    assertEquals("Book details", driver.findElement(By.xpath("//h2")).getText());
    new Select(driver.findElement(By.id("bookStatusType"))).selectByVisibleText("cancelled");
    driver.findElement(By.xpath("//option[@value='cancelled']")).click();
    driver.findElement(By.id("submit")).click();
    assertEquals("BookStatusType", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th[4]")).getText());
    assertEquals("cancelled", driver.findElement(By.xpath("//table[@id='booksTable']/tbody/tr/td[4]")).getText());
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
