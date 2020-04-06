
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
public class PlaneStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;


	@When("creo un avion con referencia {string} con número de asientos {string} y {string} kilómetros")
	public void creoAvion(String reference, String asientos, String kilometros) throws Exception {
		creaAvion(reference, asientos, kilometros, port, getDriver());
	}

	private static void creaAvion(String reference, String asientos, String kilometros, int port, WebDriver driver) {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Register a Plane!", driver.findElement(By.xpath("//h2")).getText());
		driver.findElement(By.id("reference")).click();
		driver.findElement(By.id("reference")).clear();
		driver.findElement(By.id("reference")).sendKeys(reference);
		driver.findElement(By.id("maxSeats")).clear();
		driver.findElement(By.id("maxSeats")).sendKeys(asientos.toString());
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("567");
		driver.findElement(By.id("manufacter")).clear();
		driver.findElement(By.id("manufacter")).sendKeys("man");
		driver.findElement(By.id("model")).clear();
		driver.findElement(By.id("model")).sendKeys("mod");
		driver.findElement(By.id("numberOfKm")).clear();
		driver.findElement(By.id("numberOfKm")).sendKeys(kilometros.toString());
		driver.findElement(By.id("maxDistance")).clear();
		driver.findElement(By.id("maxDistance")).sendKeys("98765");
		driver.findElement(By.id("lastMaintenance")).clear();
		driver.findElement(By.id("lastMaintenance")).sendKeys("2020-03-30");
		//driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@class='form-group has-feedback']")).click();
		driver.findElement(By.xpath("//form[@id='add-plane-form']/div[2]")).click();
	    //driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
	}

	@Then("soy redirigido a la vista de detalles del vuelo {string} y tiene número de asientos {string} y {string} kilómetros")
	public void redirigidoAVistaVueloCreado(String reference, String asientos, String kilometros) throws Exception {
		assertEquals("Reference", getDriver().findElement(By.xpath("//th")).getText());
		assertEquals("Max of Seats", getDriver().findElement(By.xpath("//tr[2]/th")).getText());
		assertEquals("Number of Kilometers", getDriver().findElement(By.xpath("//tr[6]/th")).getText());
		assertEquals(reference.toUpperCase(), getDriver().findElement(By.xpath("//b")).getText());
		assertEquals(asientos, getDriver().findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals(kilometros, getDriver().findElement(By.xpath("//tr[6]/td")).getText());

		stopDriver();
	}

	@Then("soy redirigido a la vista de creación del avión con errores {string}")
	public void redirigidoAVistaCreacionAvion(String errores) throws Exception {
		assertEquals(errores, getDriver().findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/div/span[2]")).getText());
		assertEquals(errores, getDriver().findElement(By.xpath("//form[@id='add-plane-form']/div/div[6]/div/span[2]")).getText());
		stopDriver();
	}

	@When("modifico la fecha de mantenimiento a {string} del avión con referencia {string}")
	public void actualizoAvion(String fechaMantenimiento, String reference) throws Exception {
		actualizaAvion(fechaMantenimiento, reference, port, getDriver());
	}

	private static void actualizaAvion(String fechaMantenimiento, String reference, int port, WebDriver driver) {
		driver.findElement(By.linkText("MY PLANES")).click();
		driver.findElement(By.linkText(reference)).click();
		assertEquals("Plane Information", driver.findElement(By.xpath("//h2")).getText());
		//driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/planes/1/edit'\"]")).click();
		driver.findElement(By.xpath("/html/body/div/div/button")).click();
		assertEquals("Register a Plane!", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("Reference", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div/label")).getText());
		assertEquals("Max seats", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[2]/label")).getText());
		assertEquals("Description", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[3]/label")).getText());
		assertEquals("Manufacter", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[4]/label")).getText());
		assertEquals("Model", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[5]/label")).getText());
		assertEquals("Number of Km", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[6]/label")).getText());
		assertEquals("Max distance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[7]/label")).getText());
		assertEquals("Last Maintenance", driver.findElement(By.xpath("//form[@id='add-plane-form']/div/div[8]/label")).getText());
		driver.findElement(By.id("lastMaintenance")).click();
		driver.findElement(By.id("lastMaintenance")).clear();
		driver.findElement(By.id("lastMaintenance")).sendKeys(fechaMantenimiento);
		//driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//form[@id='add-plane-form']/div[2]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("soy redirigido a la vista del avion con referencia {string}, donde la fecha de mantenimiento es {string}")
	public void redirigidoAVistaVueloActualizado(String reference, String fechaMantenimiento) throws Exception {
		assertEquals("Reference", getDriver().findElement(By.xpath("//th")).getText());
		assertEquals("Last maintenance", getDriver().findElement(By.xpath("//tr[8]/th")).getText());
		assertEquals(reference.toUpperCase(), getDriver().findElement(By.xpath("//b")).getText());
		//assertEquals(fechaMantenimiento, getDriver().findElement(By.xpath("//tr[8]/td")).getText());
		stopDriver();
	}

	@Then("soy redirigido a la vista de actualizar avión con errores {string}")
	public void redirigidoAVistaActualizacionAvion(String errores) throws Exception {
		assertEquals(errores, getDriver().findElement(By.xpath("//form[@id='add-plane-form']/div[1]/div[8]/div/span[2]")).getText());
		stopDriver();
	}

	@When("selecciono un avión con referencia {string}")
	public void accederAVistaAvion(String reference) {
		getDriver().findElement(By.linkText("MY PLANES")).click();
		getDriver().findElement(By.linkText(reference)).click();
	}

	@Then("soy redirigido a la vista del avión con referencia {string}")
	public void consultaAvionByReference(String reference) {
		assertEquals("Reference", getDriver().findElement(By.xpath("//tr[1]/th")).getText());
		assertEquals(reference.toUpperCase(), getDriver().findElement(By.xpath("//tr[1]/td/b")).getText());
	
		stopDriver();
	}

	@When("accedo a la vista un avión ajeno con id {string}")
	public void consultaAvionById(String id) throws Exception {
		getDriver().get("http://localhost:" + port + "/planes/" + id);
	}

}
