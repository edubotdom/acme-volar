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
public class Airline21AirportValidationUITest {
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
  public void testAirline21AirportValidation() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.linkText("AIRPORTS")).click();
    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/airports/new'\"]")).click();
    assertEquals("Airport", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Airport", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div/label")).getText());
    assertEquals("Max Number Of Planes", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[2]/label")).getText());
    assertEquals("Max Number Of Clients", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[3]/label")).getText());
    assertEquals("Latitude", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[4]/label")).getText());
    assertEquals("Longitude", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[5]/label")).getText());
    assertEquals("Code", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[6]/label")).getText());
    assertEquals("City", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[7]/label")).getText());
    assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div/div/span[2]")).getText());
    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[2]/div/span[2]")).getText());
    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[3]/div/span[2]")).getText());
    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[4]/div/span[2]")).getText());
    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[5]/div/span[2]")).getText());
    driver.findElement(By.id("code")).click();
    driver.findElement(By.id("code")).clear();
    driver.findElement(By.id("code")).sendKeys("YOR");
    driver.findElement(By.id("city")).click();
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("York");
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("name");
    driver.findElement(By.id("maxNumberOfPlanes")).clear();
    driver.findElement(By.id("maxNumberOfPlanes")).sendKeys("-55");
    driver.findElement(By.id("maxNumberOfClients")).clear();
    driver.findElement(By.id("maxNumberOfClients")).sendKeys("-55");
    driver.findElement(By.id("latitude")).clear();
    driver.findElement(By.id("latitude")).sendKeys("-55");
    driver.findElement(By.id("longitude")).clear();
    driver.findElement(By.id("longitude")).sendKeys("-55");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("tiene que estar entre 0 y 30000", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[2]/div/span[2]")).getText());
    assertEquals("tiene que estar entre 0 y 30000", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[3]/div/span[2]")).getText());
    driver.findElement(By.id("maxNumberOfClients")).click();
    driver.findElement(By.id("maxNumberOfClients")).clear();
    driver.findElement(By.id("maxNumberOfClients")).sendKeys("55");
    driver.findElement(By.id("maxNumberOfPlanes")).click();
    driver.findElement(By.id("maxNumberOfPlanes")).clear();
    driver.findElement(By.id("maxNumberOfPlanes")).sendKeys("55");
    driver.findElement(By.id("latitude")).click();
    driver.findElement(By.id("latitude")).clear();
    driver.findElement(By.id("latitude")).sendKeys("-555.0");
    driver.findElement(By.id("longitude")).click();
    driver.findElement(By.id("longitude")).clear();
    driver.findElement(By.id("longitude")).sendKeys("-555.0");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("tiene que estar entre -90 y 90", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[4]/div/span[2]")).getText());
    assertEquals("tiene que estar entre -180 y 180", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[5]/div/span[2]")).getText());
    driver.findElement(By.id("longitude")).click();
    driver.findElement(By.id("longitude")).clear();
    driver.findElement(By.id("longitude")).sendKeys("55.0");
    driver.findElement(By.id("latitude")).click();
    driver.findElement(By.id("latitude")).clear();
    driver.findElement(By.id("latitude")).sendKeys("55.0");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Airport Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("name", driver.findElement(By.xpath("//b")).getText());
    assertEquals("55", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("55", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("55.0", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("55.0", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("YOR", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("York", driver.findElement(By.xpath("//tr[7]/td")).getText());
    assertEquals("Weather in York", driver.findElement(By.xpath("//h2[2]")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Max Number Of Planes", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Max Number Of Clients", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Latitude", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Longitude", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Code", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("City", driver.findElement(By.xpath("//tr[7]/th")).getText());
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
