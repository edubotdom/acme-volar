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
public class AirportStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;
	
	@When("creo un aeropuerto con nombre {string} con coordenadas {string} y {string}")
	public void creoAeropuerto(String nombre, String coordX, String coordY) throws Exception {		
		creAeropuerto(nombre, coordX, coordY ,port, getDriver());		
	}
	
	@When("elimino un aeropuerto con nombre {string}")
	public void eliminoAeropuerto(String nombre) throws Exception {		
		eliminAeropuerto(nombre, port, getDriver());		
	}
	
	@When("elimino un aeropuerto con vuelos")
	public void eliminoAeropuertoConVuelos() throws Exception {		
		eliminAeropuertoConVuelos(port, getDriver());		
	}
	
	@When("actualizo un aeropuerto con nombre {string} con nuevas coordenadas {string} y {string}")
	public void actualizoAeropuertoConVuelos(String nombre, String coordX, String coordY) throws Exception {		
		Integer coordXPrevioActualizar = Integer.valueOf(coordX)+5;
		Integer coordYPrevioActualizar = Integer.valueOf(coordY)+5;
		creAeropuerto(nombre,  coordXPrevioActualizar.toString(), coordYPrevioActualizar.toString() ,port, getDriver());
		actualizAeropuerto(nombre, coordX, coordY, port, getDriver());		
	}

	private static void actualizAeropuerto(String nombre, String coordX, String coordY, int port, WebDriver driver) {				
		driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.xpath("//h2")).click();
	    driver.findElement(By.linkText("AIRPORTS")).click();
	    driver.findElement(By.linkText(nombre)).click();
	    driver.findElement(By.xpath("//body/div/div/button[1]")).click();
	    driver.findElement(By.id("latitude")).click();
	    driver.findElement(By.id("latitude")).clear();
	    driver.findElement(By.id("latitude")).sendKeys(coordX);
	    driver.findElement(By.id("longitude")).click();
	    driver.findElement(By.id("longitude")).clear();
	    driver.findElement(By.id("longitude")).sendKeys(coordY);
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
		    
		}
	
	private static void eliminAeropuertoConVuelos(int port, WebDriver driver) {				
	   //ELIMINACION
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("AIRPORTS")).click();
	    driver.findElement(By.linkText("Sevilla Airport")).click();
	    driver.findElement(By.xpath("//body/div/div")).click();
	    driver.findElement(By.xpath("//body/div/div/button[2]")).click();
	    
	}
	
	private static void eliminAeropuerto(String nombre, int port, WebDriver driver) {				
		 //CREACION
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.xpath("//img")).click();
	    driver.findElement(By.linkText("AIRPORTS")).click();
	    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/airports/new'\"]")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys(nombre);
	    driver.findElement(By.id("maxNumberOfPlanes")).click();
	    driver.findElement(By.id("maxNumberOfPlanes")).clear();
	    driver.findElement(By.id("maxNumberOfPlanes")).sendKeys("55");
	    driver.findElement(By.id("maxNumberOfClients")).clear();
	    driver.findElement(By.id("maxNumberOfClients")).sendKeys("55");
	    driver.findElement(By.id("latitude")).clear();
	    driver.findElement(By.id("latitude")).sendKeys("55");
	    driver.findElement(By.id("longitude")).clear();
	    driver.findElement(By.id("longitude")).sendKeys("55");
	    driver.findElement(By.id("code")).clear();
	    driver.findElement(By.id("code")).sendKeys("ZQW");
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("London");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	   //ELIMINACION
	    driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.linkText("AIRPORTS")).click();
	    driver.findElement(By.linkText(nombre)).click();
	    driver.findElement(By.xpath("//body/div/div")).click();
	    driver.findElement(By.xpath("//body/div/div/button[2]")).click();
	}
	
	private static void creAeropuerto(String nombre, String coordX, String coordY, int port, WebDriver driver) {				
		driver.get("http://localhost:"+port+"/");
	    driver.findElement(By.xpath("//img")).click();
	    driver.findElement(By.linkText("AIRPORTS")).click();
	    driver.findElement(By.xpath("//button[@onclick=\"window.location.href='/airports/new'\"]")).click();
	    driver.findElement(By.id("name")).click();
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys(nombre);
	    driver.findElement(By.id("maxNumberOfPlanes")).click();
	    driver.findElement(By.id("maxNumberOfPlanes")).clear();
	    driver.findElement(By.id("maxNumberOfPlanes")).sendKeys("55");
	    driver.findElement(By.id("maxNumberOfClients")).clear();
	    driver.findElement(By.id("maxNumberOfClients")).sendKeys("55");
	    driver.findElement(By.id("latitude")).clear();
	    driver.findElement(By.id("latitude")).sendKeys(coordX);
	    driver.findElement(By.id("longitude")).clear();
	    driver.findElement(By.id("longitude")).sendKeys(coordY);
	    driver.findElement(By.id("code")).clear();
	    driver.findElement(By.id("code")).sendKeys("APT");
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("London");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("se me redirige a la vista con detalles de aeropuerto {string} y coordenadas {string} y {string}")
	public void redirigidoAVistaVuelo(String nombre, String coordX, String coordY) throws Exception {		
	    assertEquals("Airport Information", getDriver().findElement(By.xpath("//h2")).getText());
	    assertEquals("Name", getDriver().findElement(By.xpath("//th")).getText());
	    assertEquals("Max Number Of Planes", getDriver().findElement(By.xpath("//tr[2]/th")).getText());
	    assertEquals("Max Number Of Clients", getDriver().findElement(By.xpath("//tr[3]/th")).getText());
	    assertEquals("Latitude", getDriver().findElement(By.xpath("//tr[4]/th")).getText());
	    assertEquals("Longitude", getDriver().findElement(By.xpath("//tr[5]/th")).getText());
	    assertEquals("Code", getDriver().findElement(By.xpath("//tr[6]/th")).getText());
	    assertEquals("City", getDriver().findElement(By.xpath("//tr[7]/th")).getText());
	    assertEquals(nombre, getDriver().findElement(By.xpath("//b")).getText());
	    assertEquals("55", getDriver().findElement(By.xpath("//tr[2]/td")).getText());
	    assertEquals("55", getDriver().findElement(By.xpath("//tr[3]/td")).getText());
	    assertEquals(coordX, getDriver().findElement(By.xpath("//tr[4]/td")).getText());
	    assertEquals(coordY, getDriver().findElement(By.xpath("//tr[5]/td")).getText());
	    assertEquals("APT", getDriver().findElement(By.xpath("//tr[6]/td")).getText());
	    assertEquals("London", getDriver().findElement(By.xpath("//tr[7]/td")).getText());
	    assertEquals("Weather in London", getDriver().findElement(By.xpath("//h2[2]")).getText());
	    assertEquals("Temperature", getDriver().findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
	    assertEquals("Humidity", getDriver().findElement(By.xpath("//table[2]/tbody/tr[2]/th")).getText());
	    assertEquals("Pressure", getDriver().findElement(By.xpath("//table[2]/tbody/tr[3]/th")).getText());
	    assertEquals("Visibility", getDriver().findElement(By.xpath("//table[2]/tbody/tr[4]/th")).getText());
		
		stopDriver();
	}

	@Then("se me redirige a la vista de creación de aeropuerto con errores {string} y {string}")
	public void redirigidoAVistaCreacion(String errorX, String errorY) throws Exception {		

	    assertEquals(errorX, getDriver().findElement(By.xpath("//form[@id='add-airport-form']/div/div[4]/div/span[2]")).getText());
	    assertEquals(errorY, getDriver().findElement(By.xpath("//form[@id='add-airport-form']/div/div[5]/div/span[2]")).getText());
		
		stopDriver();
	}
	

	@Then("se me redirige a la vista con el listado de aeropuertos")
	public void redirigidoAListadoAeropuertos() throws Exception {		

	    assertEquals("Airports", getDriver().findElement(By.xpath("//h2")).getText());
		
		stopDriver();
	}
	
}
