package acmevolar.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Airline4EditPlanesUITest {
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
  public void testAirline4EditPlanes() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.linkText("MY PLANES")).click();
    driver.findElement(By.linkText("V14-5")).click();
    assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/planes/1/edit'\"]")).click();
    assertEquals("Register a Plane!", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div/label")).getText());
    assertEquals("Max seats", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/label")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[3]/label")).getText());
    assertEquals("Manufacter", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[4]/label")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[5]/label")).getText());
    assertEquals("Number of Km", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[6]/label")).getText());
    assertEquals("Max distance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[7]/label")).getText());
    assertEquals("Last Maintenance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[8]/label")).getText());
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("RB6");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//th")).getText());
    assertEquals("RB6", driver.findElement(By.xpath("//b")).getText());
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
