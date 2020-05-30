
package acmevolar.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
public class AirlineRegistration {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		this.driver.get("http://localhost:" + this.port + "/");
		this.driver.findElement(By.linkText("REGISTER AS AN AIRLINE")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("bestAirline");
		this.driver.findElement(By.id("identification")).click();
		this.driver.findElement(By.id("identification")).click();
		this.driver.findElement(By.id("identification")).clear();
		this.driver.findElement(By.id("identification")).sendKeys("bestAirline");
		this.driver.findElement(By.id("reference")).click();
		this.driver.findElement(By.id("reference")).clear();
		this.driver.findElement(By.id("reference")).sendKeys("AL-96541");
		this.driver.findElement(By.id("email")).click();
		this.driver.findElement(By.id("email")).clear();
		this.driver.findElement(By.id("email")).sendKeys("bestairline@bestairline.com");
		this.driver.findElement(By.id("country")).clear();
		this.driver.findElement(By.id("country")).sendKeys("Spain");
		this.driver.findElement(By.id("phone")).clear();
		this.driver.findElement(By.id("phone")).sendKeys("666999666");
		this.driver.findElement(By.id("user.username")).clear();
		this.driver.findElement(By.id("user.username")).sendKeys("bestAirline");
		this.driver.findElement(By.id("user.password")).clear();
		this.driver.findElement(By.id("user.password")).sendKeys("bestAirline");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("bestAirline", this.driver.findElement(By.xpath("//strong")).getText());
		assertEquals("bestAirline", this.driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("AL-96541", this.driver.findElement(By.xpath("//tr[3]/td")).getText());
		assertEquals("bestairline@bestairline.com", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
		assertEquals("Spain", this.driver.findElement(By.xpath("//tr[5]/td")).getText());
		assertEquals("666999666", this.driver.findElement(By.xpath("//tr[6]/td")).getText());

	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
