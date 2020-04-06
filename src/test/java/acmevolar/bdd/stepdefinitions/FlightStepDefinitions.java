
package acmevolar.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;


	@When("selecciono un vuelo con referencia {string}")
	public void accederAVistaVueloByReference(String reference) {
		getDriver().get("http://localhost:" + port + "/");
		getDriver().findElement(By.linkText("FLIGHTS")).click();
		getDriver().findElement(By.linkText(reference)).click();
	}

	@When("soy redirigido a la vista del vuelo con referencia {string}")
	public void comprobarVistaVuelo(String reference) {
		assertEquals("Flight Information", getDriver().findElement(By.xpath("//h2")).getText());
		assertEquals("Reference", getDriver().findElement(By.xpath("//tr[1]/th")).getText());
		assertEquals(reference, getDriver().findElement(By.linkText(reference)).getText());
	}

	@When("accedo a la vista un vuelo pasado con id {string}")
	public void consultaVueloPasadoById(String id) throws Exception {
		getDriver().get("http://localhost:" + port + "/flights/" + id);
	}

	@When("creo un vuelo con referencia {string} con número de asientos {string}")
	public void creoVuelo(String reference, String seats) throws Exception {
		creaVuelo(reference, seats, port, getDriver());
	}

	private static void creaVuelo(String reference, String seats, int port, WebDriver driver) {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.linkText("FLIGHTS")).click();
		driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/flights/new'\"]")).click();
		assertEquals("Register a flight!", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("reference")).clear();
		driver.findElement(By.id("reference")).sendKeys(reference);
		driver.findElement(By.id("seats")).click();
		driver.findElement(By.id("seats")).clear();
		driver.findElement(By.id("seats")).sendKeys(seats);
		new Select(driver.findElement(By.id("flightStatus"))).selectByVisibleText("on_time");
		driver.findElement(By.xpath("//option[@value='on_time']")).click();
		driver.findElement(By.id("price")).click();
		driver.findElement(By.id("price")).clear();
		driver.findElement(By.id("price")).sendKeys("50");
		new Select(driver.findElement(By.id("published"))).selectByVisibleText("true");
		driver.findElement(By.xpath("//option[@value='true']")).click();
		new Select(driver.findElement(By.id("plane"))).selectByVisibleText("FW18");
		driver.findElement(By.xpath("//option[@value='FW18']")).click();
		new Select(driver.findElement(By.id("lands"))).selectByVisibleText("A-03, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid");
		driver.findElement(By.xpath("//option[@value='A-03, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid']")).click();
		new Select(driver.findElement(By.id("departes"))).selectByVisibleText("A-01, airport: Sevilla Airport, city: Sevilla");
		driver.findElement(By.xpath("//option[@value='A-01, airport: Sevilla Airport, city: Sevilla']")).click();
		driver.findElement(By.id("landDate")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.linkText("30")).click();
		driver.findElement(By.id("departDate")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		driver.findElement(By.linkText("21")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("soy redirigido a la vista de detalles del vuelo {string} y tiene número de asientos {string}")
	public void redirigidoAVistaVueloCreado(String reference, String asientos) throws Exception {
		assertEquals("Reference", getDriver().findElement(By.xpath("//tr[1]/th")).getText());
		assertEquals("Seats", getDriver().findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals(reference, getDriver().findElement(By.xpath("//tr[1]/td")).getText());
		assertEquals(asientos, getDriver().findElement(By.xpath("//tr[2]/td")).getText());
		stopDriver();
	}

	@Then("soy redirigido a la vista de creación del vuelo con errores {string}")
	public void redirigidoAVistaCreacionAvion(String errores) throws Exception {
		assertEquals(errores, getDriver().findElement(By.xpath("//form[@id='add-flight-form']/div[1]/div[2]/div/span[2]")).getText());
		stopDriver();
	}
	
	@Then("soy redirigido al listado de vuelos")
	public void redireccionAlListadoVuelos() {
		assertEquals("Flights", getDriver().findElement(By.xpath("/html/body/div/div/h2")).getText());
	}
}
