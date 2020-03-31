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
public class Airline20EditAirport {
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
  public void testAirline20EditAirport() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.xpath("//h2")).click();
    driver.findElement(By.linkText("AIRPORTS")).click();
    driver.findElement(By.linkText("Aeropuerto de Huesca-Pirineos")).click();
    assertEquals("Airport Information", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/airports/10/edit'\"]")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Aeropuerto de Huesca");
    assertEquals("Airport", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div/label")).getText());
    assertEquals("Max Number Of Planes", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[2]/label")).getText());
    assertEquals("Max Number Of Clients", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[3]/label")).getText());
    assertEquals("Max Number Of Clients", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[3]/label")).getText());
    assertEquals("Latitude", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[4]/label")).getText());
    assertEquals("Longitude", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[5]/label")).getText());
    assertEquals("Code", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[6]/label")).getText());
    assertEquals("City", driver.findElement(By.xpath("//form[@id='add-airport-form']/div/div[7]/label")).getText());
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Airport Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Name", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Max Number Of Planes", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Max Number Of Clients", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Latitude", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Longitude", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Code", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("City", driver.findElement(By.xpath("//tr[7]/th")).getText());
    assertEquals("Aeropuerto de Huesca", driver.findElement(By.xpath("//b")).getText());
    assertEquals("25", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("200", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("42.0451", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("0.1924", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("HSK", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("Huesca", driver.findElement(By.xpath("//tr[7]/td")).getText());
    assertEquals("Weather in Huesca", driver.findElement(By.xpath("//h2[2]")).getText());
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
