
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
public class AnonymousFlightListAndShow {

	@LocalServerPort
	private int				port;

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAnonymousFlightListAndShow() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.linkText("FLIGHTS")).click();
		assertEquals("Flights", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.linkText("R-01")).click();
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
		assertEquals("R-01", driver.findElement(By.xpath("//td")).getText());
		assertEquals("250", driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("true", driver.findElement(By.xpath("//tr[3]/td")).getText());
		assertEquals("Sevilla Este Airways", driver.findElement(By.linkText("Sevilla Este Airways")).getText());
		assertEquals("Sevilla Airport", driver.findElement(By.xpath("//tr[5]/td")).getText());
		assertEquals("A-01", driver.findElement(By.xpath("//tr[6]/td")).getText());
		assertEquals("2020-06-06 14:05:00.0", driver.findElement(By.xpath("//tr[7]/td")).getText());
		assertEquals("Adolfo Suárez Madrid-Barajas Airport", driver.findElement(By.xpath("//tr[8]/td")).getText());
		assertEquals("A-02", driver.findElement(By.xpath("//tr[9]/td")).getText());
		assertEquals("2020-06-06 15:00:00.0", driver.findElement(By.xpath("//tr[10]/td")).getText());
		assertEquals("B747", driver.findElement(By.linkText("B747")).getText());
		assertEquals("150.0", driver.findElement(By.xpath("//tr[12]/td")).getText());
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
