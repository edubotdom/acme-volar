
package acmevolar.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.openqa.selenium.By;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RunwayStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;

	@When("elimino una pista con referencia {string} del aeropuerto con nombre {string}")
	public void accederAVistaRunwaysByAirportName(String reference, String airportName) {
		getDriver().findElement(By.linkText("AIRPORTS")).click();
		getDriver().findElement(By.linkText(airportName)).click();
		getDriver().findElement(By.xpath("//div/div/button[3]")).click();//List Runways
		
		assertEquals("Delete", getDriver().findElement(By.xpath("//table[@id='runwayTable']/thead/tr/th[4]")).getText());
		assertEquals(reference, getDriver().findElement(By.xpath("//table[@id='runwayTable']/tbody/tr[2]/td[1]")).getText());
		assertEquals("landing", getDriver().findElement(By.xpath("//table[@id='runwayTable']/tbody/tr[2]/td[2]")).getText());
		assertEquals("Delete", getDriver().findElement(By.xpath("(//a[contains(text(),'Delete')])[2]")).getText());
		
		getDriver().findElement(By.xpath("(//a[contains(text(),'Delete')])[2]")).click();
	}
	
	
	@Then("no se encuentra en la vista del listado de pistas la referencia {string}")
	public void noEncontrarRunwayByReference(String reference) {
		assertNotEquals(reference, getDriver().findElement(By.xpath("//table[@id='runwayTable']/tbody/tr[2]/td[1]")).getText());
		stopDriver();
	}

}
