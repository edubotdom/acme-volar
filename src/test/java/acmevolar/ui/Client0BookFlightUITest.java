package acmevolar.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Client0BookFlightUITest {
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
  public void testClientBookFlight() throws Exception {
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.linkText("LOGIN")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("FLIGHTS")).click();
    driver.findElement(By.linkText("R-01")).click();
    driver.findElement(By.linkText("Book Flight")).click();
    driver.findElement(By.id("quantity")).click();
    driver.findElement(By.id("quantity")).clear();
    driver.findElement(By.id("quantity")).sendKeys("1");
    driver.findElement(By.id("submit")).click();
    assertEquals("Books", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("Quantity", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th")).getText());
    assertEquals("Price", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th[2]")).getText());
    assertEquals("Moment", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th[3]")).getText());
    assertEquals("BookStatusType", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th[4]")).getText());
    assertEquals("Flight", driver.findElement(By.xpath("//table[@id='booksTable']/thead/tr/th[5]")).getText());
    assertEquals("1", driver.findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td")).getText());
    assertEquals("150.0", driver.findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td[2]")).getText());
    assertEquals(LocalDate.now().toString(), driver.findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td[3]")).getText());
    assertEquals("approved", driver.findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td[4]")).getText());
    assertEquals("R-01", driver.findElement(By.xpath("(//a[contains(text(),'R-01')])[2]")).getText());
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
