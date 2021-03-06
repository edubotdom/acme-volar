
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
public class AnonymousAirlineListAndShowUITest {

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
	public void testAnonymousAirlineListAndShow() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		assertEquals("Airlines", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.linkText("Sevilla Este Airways")).click();
		assertEquals("Airline Information", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Name", driver.findElement(By.xpath("//th")).getText());
		assertEquals("Identification", driver.findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals("Reference", driver.findElement(By.xpath("//tr[3]/th")).getText());
		assertEquals("Email", driver.findElement(By.xpath("//tr[4]/th")).getText());
		assertEquals("Country", driver.findElement(By.xpath("//tr[5]/th")).getText());
		assertEquals("Phone", driver.findElement(By.xpath("//tr[6]/th")).getText());
		assertEquals("Creation Date", driver.findElement(By.xpath("//tr[7]/th")).getText());
		driver.findElement(By.xpath("//body/div/div")).click();
		assertEquals("Sevilla Este Airways", driver.findElement(By.xpath("//b")).getText());
		assertEquals("61333744-N", driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("SEA-001", driver.findElement(By.xpath("//tr[3]/td")).getText());
		assertEquals("minardi@gmail.com", driver.findElement(By.xpath("//tr[4]/td")).getText());
		assertEquals("Spain", driver.findElement(By.xpath("//tr[5]/td")).getText());
		assertEquals("2010-11-07", driver.findElement(By.xpath("//tr[7]/td")).getText());
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
