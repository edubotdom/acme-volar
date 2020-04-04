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
public class Airline5CreateFlightUITest {
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
  public void testAirline5CreateFlight() throws Exception {
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
    driver.findElement(By.linkText("FLIGHTS")).click();
    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/flights/new'\"]")).click();
    driver.findElement(By.id("reference")).click();
    assertEquals("Register a flight!", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("r123");
    driver.findElement(By.id("seats")).clear();
    driver.findElement(By.id("seats")).sendKeys("5");
    driver.findElement(By.id("price")).clear();
    driver.findElement(By.id("price")).sendKeys("5");
    new Select(driver.findElement(By.id("flightStatus"))).selectByVisibleText("on_time");
    driver.findElement(By.xpath("//option[@value='on_time']")).click();
    new Select(driver.findElement(By.id("published"))).selectByVisibleText("true");
    driver.findElement(By.xpath("//option[@value='true']")).click();
    new Select(driver.findElement(By.id("plane"))).selectByVisibleText("RB9");
    driver.findElement(By.xpath("//option[@value='RB9']")).click();
    new Select(driver.findElement(By.id("lands"))).selectByVisibleText("A-06, airport: Sevilla Airport, city: Sevilla");
    driver.findElement(By.xpath("//option[@value='A-06, airport: Sevilla Airport, city: Sevilla']")).click();
    new Select(driver.findElement(By.id("departes"))).selectByVisibleText("A-19, airport: Aeropuerto Federico García Lorca Granada-Jaén, city: Granada");
    driver.findElement(By.xpath("//option[@value='A-19, airport: Aeropuerto Federico García Lorca Granada-Jaén, city: Granada']")).click();
    driver.findElement(By.id("landDate")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.id("departDate")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.linkText("29")).click();
    driver.findElement(By.id("landDate")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.id("departDate")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Flight Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Seats", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Published", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Airline", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Depart airport", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Depart runway", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("Depart Date", driver.findElement(By.xpath("//tr[7]/th")).getText());
    assertEquals("Landing airport", driver.findElement(By.xpath("//tr[8]/th")).getText());
    assertEquals("Landing runway", driver.findElement(By.xpath("//tr[9]/th")).getText());
    assertEquals("Land Date", driver.findElement(By.xpath("//tr[10]/th")).getText());
    assertEquals("Plane", driver.findElement(By.xpath("//tr[11]/th")).getText());
    assertEquals("Price", driver.findElement(By.xpath("//tr[12]/th")).getText());
    assertEquals("r123", driver.findElement(By.linkText("r123")).getText());
    assertEquals("true", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("Sevilla Este Airways", driver.findElement(By.linkText("Sevilla Este Airways")).getText());
    assertEquals("Aeropuerto Federico García Lorca Granada-Jaén", driver.findElement(By.linkText("Aeropuerto Federico García Lorca Granada-Jaén")).getText());
    assertEquals("A-19", driver.findElement(By.linkText("A-19")).getText());
    assertEquals("Sevilla Airport", driver.findElement(By.linkText("Sevilla Airport")).getText());
    assertEquals("A-06", driver.findElement(By.linkText("A-06")).getText());
    assertEquals("An-124", driver.findElement(By.linkText("An-124")).getText());
    assertEquals("5.0", driver.findElement(By.xpath("//tr[12]/td")).getText());
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
