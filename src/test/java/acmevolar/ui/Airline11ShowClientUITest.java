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
public class Airline11ShowClientUITest {

	@LocalServerPort
	private int				port;
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAirline11ShowClient() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
    assertEquals("Clients", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.linkText("Sergio Pérez")).click();
    assertEquals("Client Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Identification", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Birth Date", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Email", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Phone", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Creation Date", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("Sergio Pérez", driver.findElement(By.xpath("//b")).getText());
    assertEquals("53933261-P", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("1994-09-07", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("checoperez@gmail.com", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("644584458", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("2005-09-07", driver.findElement(By.xpath("//tr[6]/td")).getText());
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
