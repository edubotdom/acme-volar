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
public class Airline8ListRunway {
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
  public void testAirline8ListRunway() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/");
    assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.linkText("AIRPORTS")).click();
    driver.findElement(By.linkText("Sevilla Airport")).click();
    assertEquals("Airport Information", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/airports/1/runways'\"]")).click();
    assertEquals("Airport", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//th")).getText());
    assertEquals("City", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Sevilla Airport", driver.findElement(By.xpath("//b")).getText());
    assertEquals("Sevilla", driver.findElement(By.xpath("//tr[2]/td/b")).getText());
    assertEquals("Runways", driver.findElement(By.xpath("//h2[2]")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//table[@id='runwayTable']/thead/tr/th")).getText());
    assertEquals("Type", driver.findElement(By.xpath("//table[@id='runwayTable']/thead/tr/th[2]")).getText());
    assertEquals("Edit", driver.findElement(By.xpath("//table[@id='runwayTable']/thead/tr/th[3]")).getText());
    assertEquals("Delete", driver.findElement(By.xpath("//table[@id='runwayTable']/thead/tr/th[4]")).getText());
    assertEquals("A-01", driver.findElement(By.xpath("//table[@id='runwayTable']/tbody/tr/td")).getText());
    assertEquals("take_off", driver.findElement(By.xpath("//table[@id='runwayTable']/tbody/tr/td[2]")).getText());
    assertEquals("Edit", driver.findElement(By.linkText("Edit")).getText());
    assertEquals("Delete", driver.findElement(By.linkText("Delete")).getText());
    assertEquals("A-06", driver.findElement(By.xpath("//table[@id='runwayTable']/tbody/tr[2]/td")).getText());
    assertEquals("landing", driver.findElement(By.xpath("//table[@id='runwayTable']/tbody/tr[2]/td[2]")).getText());
    assertEquals("Edit", driver.findElement(By.xpath("(//a[contains(text(),'Edit')])[2]")).getText());
    assertEquals("Delete", driver.findElement(By.xpath("(//a[contains(text(),'Delete')])[2]")).getText());
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
