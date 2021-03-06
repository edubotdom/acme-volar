package acmevolar.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginAndExceptionStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;
	
	@Then("se me redirige a la vista de excepción")
	public void redirigidoAExcepción() throws Exception {		

		assertEquals("Something happened...", getDriver().findElement(By.xpath("//h2")).getText());
		
		stopDriver();
	}
	
	@Given("I am not logged in the system")
	public void IamNotLogged() throws Exception{		
		getDriver().get("http://localhost:"+port);
		WebElement element=getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a"));
		if(element==null || !element.getText().equalsIgnoreCase("login")) {
			getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
			getDriver().findElement(By.linkText("Logout")).click();
			getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		}
	}

	@Given("estoy iniciado sesión como {string}")
	public void estoyLogueadoComo(String username) throws Exception {		
		getDriver().get("http://localhost:"+port);
		WebElement element=getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a"));
		if(element==null || !element.getText().equalsIgnoreCase("login")) {
			getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
			getDriver().findElement(By.linkText("Logout")).click();
			getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		}
		loginAs(username,passwordOf(username),port, getDriver());		
	}
	
	@When("I do login as user {string}")
	public void IdoLoginAs(String username) throws Exception {		
		loginAs(username,passwordOf(username),port, getDriver());		
	}
	
	
	public static void loginAs(String username,int port,WebDriver driver) {
		loginAs(username,passwordOf(username),port, driver);
	}
	
	public static void loginAs(String username,String password,int port,WebDriver driver) {				
		driver.get("http://localhost:"+port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
	private static String passwordOf(String username) {
		String result="4dm1n";
		if("owner1".equals(username))
			result="0wn3r";
		if("client1".equals(username))
			result="client1";
		if("airline1".equals(username))
			result="airline1";
		return result;
	}

	@Then("{string} appears as the current user")
	public void asCurretUserAppears(String username) throws Exception {		
		assertEquals(username.toUpperCase(),
				getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		stopDriver();
	}
	
	@When("I try to do login as user {string} with an invalid password")
	public void ItryToDoLoginWithAnInvalidPasswordAs(String username) throws Exception {
		loginAs(username,UUID.randomUUID().toString(),port,getDriver());
	}
	
	@Then("the login form is shown again")
	public void theLoginFormIsShownAgain() throws Exception {
		assertEquals(getDriver().getCurrentUrl(),"http://localhost:"+port+"/login-error");
		stopDriver();
	}
	
}
