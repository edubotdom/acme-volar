
package acmevolar.service;

import  static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class PlaneServiceTests {

	@Autowired
	protected PlaneService	planeService;

	@Autowired
	protected FlightService	flightService;
	
	@Autowired
	protected AirlineService	airlineService;
	
	//Registrar un avión correctamente
	@Test
	@Transactional
	public void shouldInsertPlaneIntoDatabaseAndGenerateId() {

		Airline a1 = this.airlineService.findAirlineById(1);
		Flight f1 = this.flightService.findFlightById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF1");
		
		f1.setPlane(plane);
		assertThat(f1.getPlane()).isEqualTo(plane);

        this.planeService.savePlane(plane);

		this.flightService.saveFlight(f1);

		// comprueba si está insertado
		assertThat(plane.getId()).isNotNull();
	}
	
	//No puede insertarse un avión con plazas negativas
	@Test
	@Transactional
	public void shouldNotInsertPlaneNegativeSeats() {

		Airline a1 = this.airlineService.findAirlineById(1);
		Flight f1 = this.flightService.findFlightById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(-300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF1");
		

		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			f1.setPlane(plane);
			planeService.savePlane(plane);
		});
	}
	
	//No puede insertarse un avión con plazas negativas
	@Test
	@Transactional
	public void shouldNotInsertPlaneNegativeDistance() {

		Airline a1 = this.airlineService.findAirlineById(1);
		Flight f1 = this.flightService.findFlightById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(-45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF1");
		
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			f1.setPlane(plane);
			planeService.savePlane(plane);
		});
	}
	
	//No puede insertarse un avión con kilómetros negativos
	@Test
	@Transactional
	public void shouldNotInsertPlaneNegativeKm() {

		Airline a1 = this.airlineService.findAirlineById(1);
		Flight f1 = this.flightService.findFlightById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(-34200.);
		plane.setReference("REF1");

		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			f1.setPlane(plane);
			planeService.savePlane(plane);
		});
	}
	
	//No puede insertarse un avión con último mantenimiento futuro
	@Test
	@Transactional
	public void shouldNotInsertPlaneWrongMaintenance() {

		Airline a1 = this.airlineService.findAirlineById(1);
		Flight f1 = this.flightService.findFlightById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().plusSeconds(90000)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF1");
		

		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			f1.setPlane(plane);
			planeService.savePlane(plane);
		});
	}

	@Test
	@Transactional
	public void shouldDeletePlaneById() {
	
		Long nPrevioAviones = planeService.findPlanes().stream().count();
		
		//Creo un avión para eliminarlo
		Airline a1 = this.airlineService.findAirlineById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF_shouldDeletePlaneById");
		
		
        this.planeService.savePlane(plane);

		//elimino avión
		this.planeService.deleteById(plane.getId());

		//compruebo si se ha eliminado
		Long nActualAviones = planeService.findPlanes().stream().count();
		assertThat(nActualAviones.equals(nPrevioAviones)).isTrue();
	}
	
	@Test
	@Transactional
	public void shouldDeletePlane() {
	
		Long nPrevioAviones = planeService.findPlanes().stream().count();
		
		//Creo un avión para eliminarlo
		Airline a1 = this.airlineService.findAirlineById(1);
		
		Plane plane = new Plane();
		plane.setAirline(a1);
		plane.setDescription("Mock description");
		plane.setFlightsInternal(new HashSet<Flight>());
		plane.setLastMaintenance(Date.from(Instant.now().minusSeconds(1)));
		plane.setManufacter("Boeing");
		plane.setMaxDistance(45000.);
		plane.setMaxSeats(300);
		plane.setModel("Renton 737");
		plane.setNumberOfKm(34200.);
		plane.setReference("REF_shouldDeletePlane");
		
		
        this.planeService.savePlane(plane);

		//elimino avión
		this.planeService.deletePlane(plane);

		//compruebo si se ha eliminado
		Long nActualAviones = planeService.findPlanes().stream().count();
		assertThat(nActualAviones.equals(nPrevioAviones)).isTrue();
	}
	
	@Test
	void shouldFindPlanes() {
		Collection<Plane> planes = this.planeService.findPlanes();

		//Plane plane1 = EntityUtils.getById(planes, Plane.class, 1);
		assertThat(!planes.isEmpty()).isTrue();
		assertThat(planes).asList();
	}
	
	@Test
	void shouldFindAirlineByUsername() {

		Airline a1 = planeService.findAirlineByUsername("airline1");
		
		assertThat(a1).isNotNull();
		assertThat(a1.getCountry().equals("Spain")).isTrue();
		assertThat(a1.getCreationDate()).isInstanceOf(LocalDate.class);
		assertThat(a1.getEmail().equals("minardi@gmail.com")).isTrue();
		assertThat(a1.getFlights()).asList();
		assertThat(a1.getIdentification().equals("61333744-N")).isTrue();
		assertThat(a1.getName().equals("Sevilla Este Airways")).isTrue();
		assertThat(a1.getPhone().equals("644584458")).isTrue();
		assertThat(a1.getPlanes()).asList();
		assertThat(a1.getReference().equals("SEA-001")).isTrue();
	}
	
	@Test
	void shouldGetAllPlanesFromAirline() {
		Airline a1 = planeService.findAirlineByUsername("airline1");
		Collection<Plane> planes = this.planeService.getAllPlanesFromAirline(a1);

		//Plane plane1 = EntityUtils.getById(planes, Plane.class, 1);
		assertThat(!planes.isEmpty()).isTrue();
		assertThat(planes).asList();
	}
	
	@Test
	void shouldgetAllPlanesFromAirlineId() {
		Airline a1 = planeService.findAirlineByUsername("airline1");
		Collection<Plane> planes = this.planeService.getAllPlanesFromAirline(a1.getUser().getUsername());

		//Plane plane1 = EntityUtils.getById(planes, Plane.class, 1);
		assertThat(!planes.isEmpty()).isTrue();
		assertThat(planes).asList();
	}
	
	@Test
	void shouldFindPlaneInformationById() {
		Plane plane1 = this.planeService.findPlaneById(1);

		assertThat(plane1.getReference()).isEqualTo("V14-5");
		assertThat(plane1.getDescription()).isEqualTo("This is a description");
		assertThat(plane1.getMaxSeats()).isEqualTo(150);
		assertThat(plane1.getNumberOfKm()).isEqualTo(500000.23);
		assertThat(plane1.getAirline().getId()).isEqualTo(1);
	}
	
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
