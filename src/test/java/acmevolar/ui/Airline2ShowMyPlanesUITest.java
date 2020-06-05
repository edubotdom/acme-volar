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
public class Airline2ShowMyPlanesUITest {
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
  public void testAirline2ShowMyPlanes() throws Exception {
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("LOGIN")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("airline1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("airline1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.linkText("MY PLANES")).click();
    assertEquals("Planes", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th")).getText());
    assertEquals("Max of Seats", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[2]")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[3]")).getText());
    assertEquals("Manufacturer", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[4]")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[5]")).getText());
    assertEquals("Number of Kilometers", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[6]")).getText());
    assertEquals("Max Distance", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[7]")).getText());
    assertEquals("Last maintenance", driver.findElement(By.xpath("//table[@id='planesTable']/thead/tr/th[8]")).getText());
//    driver.findElement(By.xpath("//table[@id='planesTable']/tbody/tr/td")).click();
    driver.findElement(By.linkText("V14-5")).click();
    assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Max of Seats", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Manufacturer", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Number of Kilometers", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("Max Distance", driver.findElement(By.xpath("//tr[7]/th")).getText());
    assertEquals("Last maintenance", driver.findElement(By.xpath("//tr[8]/th")).getText());
    assertEquals("V14-5", driver.findElement(By.xpath("//b")).getText());
    assertEquals("150", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("This is a description", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("Boeing", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("B747", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("500000.23", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("2000000.0", driver.findElement(By.xpath("//tr[7]/td")).getText());
    assertEquals("2011-04-17 00:00:00.0", driver.findElement(By.xpath("//tr[8]/td")).getText());
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
