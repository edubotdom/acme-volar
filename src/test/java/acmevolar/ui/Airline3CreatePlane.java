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
public class Airline3CreatePlane {
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
  public void testAirline3CreatePlane() throws Exception {
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
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Register a Plane!", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("reference2");
    driver.findElement(By.id("maxSeats")).clear();
    driver.findElement(By.id("maxSeats")).sendKeys("654");
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("567");
    driver.findElement(By.id("manufacter")).clear();
    driver.findElement(By.id("manufacter")).sendKeys("man");
    driver.findElement(By.id("model")).clear();
    driver.findElement(By.id("model")).sendKeys("mod");
    driver.findElement(By.id("numberOfKm")).clear();
    driver.findElement(By.id("numberOfKm")).sendKeys("56789");
    driver.findElement(By.id("maxDistance")).clear();
    driver.findElement(By.id("maxDistance")).sendKeys("98765");
    driver.findElement(By.id("lastMaintenance")).clear();
    driver.findElement(By.id("lastMaintenance")).sendKeys("2020-03-30");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Max of Seats", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Manufacturer", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Number of Kilometers", driver.findElement(By.xpath("//tr[6]/th")).getText());
    assertEquals("Max Distance", driver.findElement(By.xpath("//tr[7]/th")).getText());
    assertEquals("Last maintenance", driver.findElement(By.xpath("//tr[8]/th")).getText());
    assertEquals("reference2", driver.findElement(By.xpath("//b")).getText());
    assertEquals("654", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("567", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("man", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("mod", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("56789.0", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("98765.0", driver.findElement(By.xpath("//tr[7]/td")).getText());
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
