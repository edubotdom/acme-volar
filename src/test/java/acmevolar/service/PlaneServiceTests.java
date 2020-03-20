
package acmevolar.service;

import java.util.Collection;

import  static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PlaneServiceTests {

	@Autowired
	protected PlaneService	planeService;

	@Autowired
	protected FlightService	flightService;
	
	@Autowired
	private MockMvc mockMvc;


	@Test
	void shouldFindPlaneInformation() {
		Collection<Plane> planes = this.planeService.findPlanes();

		Plane plane1 = EntityUtils.getById(planes, Plane.class, 1);
		assertThat(plane1.getReference()).isEqualTo("V14-5");
		assertThat(plane1.getDescription()).isEqualTo("This is a description");
		assertThat(plane1.getMaxSeats()).isEqualTo(150);
		assertThat(plane1.getNumberOfKm()).isEqualTo(500000.23);
		assertThat(plane1.getAirline().getId()).isEqualTo(1);
	}

	/**
	 * Buscar un vuelo existente en la base de datos y disponible y poder consultar la información del avión asignado a este.
	 */
	@Test
	void shouldFindPlaneInformationByFlight() {
		Collection<Flight> flights = this.flightService.findPublishedFlight();

		Flight flight1 = EntityUtils.getById(flights, Flight.class, 1);
		assertThat(flight1).isNotNull();
		assertThat(flight1.getPlane()).isNotNull();
		assertThat(flight1.getPlane()).isInstanceOf(Plane.class);
		assertThat(flight1).matches(f -> f.getPublished() == true, "Está publicado");

	}

	/**
	 * Buscar un aeropuerto inexistente en la base de datos y que el sistema no permita consultar la información de su avión
	 */
	@Test
	void shouldNotFindPlaneInformation() {
		Collection<Flight> flights = this.flightService.findPublishedFlight();

		Assertions.assertThrows(ObjectRetrievalFailureException.class, () -> EntityUtils.getById(flights, Flight.class, 100));

	}

	/**
	 * Buscar un vuelo no disponible y que el sistema no permita consultar la información de su avión.
	 */
	@Test
	void shouldNotFindPlaneInformationByFlight() {
		Collection<Flight> flights = this.flightService.findPublishedFlight();

		Flight flight1 = EntityUtils.getById(flights, Flight.class, 1);
		flight1.setPublished(false);
		assertThat(flight1).isNotNull();
		assertThat(flight1.getPlane()).isNotNull();
		assertThat(flight1.getPlane()).isInstanceOf(Plane.class);
		assertThat(flight1).matches(f -> f.getPublished() == false, "No está publicado");

	}
	
	
	
	
	
}
