
package acmevolar.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
	
	@When("accedo a mi listado de reservas")
	public void accedeListadoReservas() {
		getDriver().get("http://localhost:" + port + "/");
		getDriver().findElement(By.linkText("MY BOOKS")).click();
		assertEquals("Books", getDriver().findElement(By.xpath("//h2")).getText());
	}
	
	@When("accedo a la vista de de edición de la reserva con id {string} del vuelo con referencia {string} y la cambio a {string}")
	public void accedeEditarReserva(String id, String reference, String bookStatus) {
		getDriver().get("http://localhost:" + port + "/books/"+id+"/edit");
		assertEquals("Book details", getDriver().findElement(By.xpath("//h2")).getText());
		new Select(getDriver().findElement(By.id("bookStatusType"))).selectByVisibleText(bookStatus);
		getDriver().findElement(By.xpath("//option[@value='"+bookStatus+"']")).click();
		getDriver().findElement(By.id("submit")).click();
	}
	
	@When("intento acceder a la vista de de edición de la reserva con id {string} del vuelo con referencia {string}")
	public void accedeEditarReservaAjena(String id, String reference) {
		getDriver().get("http://localhost:" + port + "/books/"+id+"/edit");
	}
	
	
	@Then("encuentro un resultado coincidiendo con mi vuelo con referencia {string} y estado {string}")
	public void compruebaVueloEnListadoBook(String reference, String bookStatus) throws Exception {
		getDriver().get("http://localhost:" + port + "/");
		getDriver().findElement(By.linkText("MY BOOKS")).click();
		assertEquals("Books", getDriver().findElement(By.xpath("//h2")).getText());
		assertEquals(reference, getDriver().findElement(By.linkText((reference))).getText());
		assertEquals(bookStatus, getDriver().findElement(By.xpath(("//table[@id='booksTable']/tbody/tr/td[4]"))).getText());
		stopDriver();
	}
	
	@Then("no encuentro el vuelo ajeno a mi con referencia {string}")
	public void compruebaNoVueloEnListadoBook(String reference) throws Exception {
		assertNotEquals(reference, getDriver().findElement(By.xpath("//table[@id='booksTable']/tbody/tr/td[5]")).getText());
		stopDriver();
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
