
package acmevolar.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;


	@When("creo una cantidad de {string} reservas del vuelo con referencia {string}")
	public void creoReserva(String cantidad, String reference) throws Exception {
		creaReserva(cantidad, reference, port, getDriver());
	}

	private static void creaReserva(String cantidad, String reference, int port, WebDriver driver) {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.linkText("FLIGHTS")).click();
		driver.findElement(By.linkText(reference)).click();
		driver.findElement(By.linkText("Book Flight")).click();
		driver.findElement(By.id("quantity")).click();
		driver.findElement(By.id("quantity")).clear();
		driver.findElement(By.id("quantity")).sendKeys(cantidad);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("soy redirigido a la vista de mis reservas, donde está mi reserva de {string} asientos para el vuelo {string}")
	public void redirigidoAVistaReservas(String cantidad, String reference) throws Exception {
		assertEquals("Books", getDriver().findElement(By.xpath("//h2")).getText());
		assertEquals(cantidad, getDriver().findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td")).getText());
		assertEquals("approved", getDriver().findElement(By.xpath("//table[@id='booksTable']/tbody/tr[2]/td[4]")).getText());
		assertEquals(reference, getDriver().findElement(By.xpath("(//a[contains(text(),'" + reference + "')])[2]")).getText());
		stopDriver();
	}

	@Then("soy redirigido a la vista de creación de la reserva con errores {string}")
	public void redirigidoAVistaCreacionReserva(String errores) throws Exception {
		assertEquals(errores, getDriver().findElement(By.xpath("//form[@id='add-book-form']/div[1]/div/div/span[2]")).getText());
		stopDriver();
	}
}
