package acmevolar.ui;



import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRegistrationAndLogin {
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
  public void testClientRAL() throws Exception {
	  
	driver.get("http://localhost:"+port+"/");
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //h2 | ]]
    assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[2]/a")).click();
    assertEquals("Register as a client!", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("usu");
    driver.findElement(By.id("name")).sendKeys(Keys.DOWN);
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("usuario-prueba");
    driver.findElement(By.id("user.password")).click();
    driver.findElement(By.id("user.password")).clear();
    driver.findElement(By.id("user.password")).sendKeys("usuario-prueba");
    driver.findElement(By.id("identification")).click();
    driver.findElement(By.id("identification")).clear();
    driver.findElement(By.id("identification")).sendKeys("53949661-A");
    driver.findElement(By.id("birthDate")).click();
    driver.findElement(By.linkText("12")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("usuarioprueba@gmail.com");
    driver.findElement(By.id("phone")).click();
    driver.findElement(By.id("user.username")).click();
    driver.findElement(By.id("user.username")).clear();
    driver.findElement(By.id("user.username")).sendKeys("usuario-prueba");
    driver.findElement(By.id("phone")).click();
    driver.findElement(By.id("phone")).clear();
    driver.findElement(By.id("phone")).sendKeys("619203456");
    assertEquals("Register", driver.findElement(By.xpath("//button[@type='submit']")).getText());
    driver.findElement(By.id("name")).click();
    assertEquals("usuario-prueba", driver.findElement(By.id("name")).getAttribute("value"));
    driver.findElement(By.id("identification")).click();
    assertEquals("53949661-A", driver.findElement(By.id("identification")).getAttribute("value"));
    driver.findElement(By.id("birthDate")).click();
    assertEquals("2020/05/12", driver.findElement(By.id("birthDate")).getAttribute("value"));
    driver.findElement(By.id("email")).click();
    assertEquals("usuarioprueba@gmail.com", driver.findElement(By.id("email")).getAttribute("value"));
    driver.findElement(By.id("phone")).click();
    assertEquals("619203456", driver.findElement(By.id("phone")).getAttribute("value"));
    driver.findElement(By.id("user.username")).click();
    assertEquals("usuario-prueba", driver.findElement(By.id("user.username")).getAttribute("value"));
    driver.findElement(By.id("user.password")).click();
    assertEquals("usuario-prueba", driver.findElement(By.id("user.password")).getAttribute("value"));
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//h2")).click();
    driver.findElement(By.xpath("//h2")).click();
    driver.findElement(By.xpath("//h2")).click();
    driver.findElement(By.xpath("//h2")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //h2 | ]]
    assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
    driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[1]/a")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("usuario-prueba");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("usuario-prueba");
    assertEquals("Sign in", driver.findElement(By.xpath("//button[@type='submit']")).getText());
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//h2")).click();
    driver.findElement(By.xpath("//h2")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [doubleClick | //h2 | ]]
    assertEquals("Welcome", driver.findElement(By.xpath("//h2")).getText());
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
