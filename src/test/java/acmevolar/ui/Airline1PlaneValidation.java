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
public class Airline1PlaneValidation {
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
  public void testAirline1PlaneValidation() throws Exception {
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
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Register a Plane!", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div/label")).getText());
    assertEquals("Max seats", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/label")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[3]/label")).getText());
    assertEquals("Manufacter", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[4]/label")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[5]/label")).getText());
    assertEquals("Number of Km", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[6]/label")).getText());
    assertEquals("Max distance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[7]")).getText());
    assertEquals("Last Maintenance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[8]/label")).getText());
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("wer");
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("QWE");
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).clear();
    driver.findElement(By.id("maxSeats")).sendKeys("234");
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("DES");
    driver.findElement(By.id("manufacter")).click();
    driver.findElement(By.id("manufacter")).clear();
    driver.findElement(By.id("manufacter")).sendKeys("MAN");
    driver.findElement(By.id("model")).click();
    driver.findElement(By.id("model")).clear();
    driver.findElement(By.id("model")).sendKeys("MOD");
    driver.findElement(By.id("numberOfKm")).click();
    driver.findElement(By.id("numberOfKm")).clear();
    driver.findElement(By.id("numberOfKm")).sendKeys("-555");
    driver.findElement(By.id("maxDistance")).click();
    driver.findElement(By.id("maxDistance")).click();
    driver.findElement(By.id("maxDistance")).clear();
    driver.findElement(By.id("maxDistance")).sendKeys("-555");
    driver.findElement(By.id("lastMaintenance")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.id("model")).click();
    driver.findElement(By.id("model")).clear();
    driver.findElement(By.id("model")).sendKeys("");
    driver.findElement(By.id("manufacter")).click();
    driver.findElement(By.id("manufacter")).clear();
    driver.findElement(By.id("manufacter")).sendKeys("");
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("");
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).clear();
    driver.findElement(By.id("maxSeats")).sendKeys("");
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("reference")).click();
    assertEquals("You must fill reference", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div/div/span[2]")).getText());
    assertEquals("You must fill max seats", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/div/span[2]")).getText());
    assertEquals("You must fill description", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[3]/div/span[2]")).getText());
    assertEquals("You must fill manufacturer", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[4]/div/span[2]")).getText());
    assertEquals("You must fill model", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[5]/div/span[2]")).getText());
    driver.findElement(By.id("reference")).click();
    driver.findElement(By.id("reference")).clear();
    driver.findElement(By.id("reference")).sendKeys("REF");
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).clear();
    driver.findElement(By.id("maxSeats")).sendKeys("-43");
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("DFG");
    driver.findElement(By.id("manufacter")).click();
    driver.findElement(By.id("manufacter")).clear();
    driver.findElement(By.id("manufacter")).sendKeys("FDR");
    driver.findElement(By.id("model")).click();
    driver.findElement(By.id("model")).clear();
    driver.findElement(By.id("model")).sendKeys("GFD");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("You must introduce a positive number.", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/div/span[2]")).getText());
    assertEquals("You must introduce a positive number.", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[6]/div/span[2]")).getText());
    assertEquals("You must introduce a positive number.", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[7]/div/span[2]")).getText());
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).click();
    driver.findElement(By.id("maxSeats")).clear();
    driver.findElement(By.id("maxSeats")).sendKeys("43");
    driver.findElement(By.id("numberOfKm")).click();
    driver.findElement(By.id("numberOfKm")).clear();
    driver.findElement(By.id("numberOfKm")).sendKeys("555.0");
    driver.findElement(By.id("maxDistance")).click();
    driver.findElement(By.id("maxDistance")).clear();
    driver.findElement(By.id("maxDistance")).sendKeys("555.0");
    driver.findElement(By.id("lastMaintenance")).click();
    driver.findElement(By.id("lastMaintenance")).clear();
    driver.findElement(By.id("lastMaintenance")).sendKeys("2020-03-30");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("REF", driver.findElement(By.xpath("//b")).getText());
    assertEquals("43", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("DFG", driver.findElement(By.xpath("//tr[3]/td")).getText());
    assertEquals("FDR", driver.findElement(By.xpath("//tr[4]/td")).getText());
    assertEquals("GFD", driver.findElement(By.xpath("//tr[5]/td")).getText());
    assertEquals("555.0", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("555.0", driver.findElement(By.xpath("//tr[7]/td")).getText());
    assertEquals("Reference", driver.findElement(By.xpath("//th")).getText());
    assertEquals("Max of Seats", driver.findElement(By.xpath("//tr[2]/th")).getText());
    assertEquals("Description", driver.findElement(By.xpath("//tr[3]/th")).getText());
    assertEquals("Manufacturer", driver.findElement(By.xpath("//tr[4]/th")).getText());
    assertEquals("Model", driver.findElement(By.xpath("//tr[5]/th")).getText());
    assertEquals("Max Distance", driver.findElement(By.xpath("//tr[7]/th")).getText());
    assertEquals("Last maintenance", driver.findElement(By.xpath("//tr[8]/th")).getText());
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
