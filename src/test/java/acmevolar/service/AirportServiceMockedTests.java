
package acmevolar.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.User;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.AirportRepository;
import acmevolar.repository.PlaneRepository;
import acmevolar.service.exceptions.IncorrectCartesianCoordinatesException;

@ExtendWith(MockitoExtension.class)
public class AirportServiceMockedTests {

	private static final int	TEST_PLANE_ID	= 1;

	private static final int	TEST_AIRLINE_ID	= 1;

	private static final int	TEST_AIRPORT_ID	= 1;

	@Mock

	private AirlineRepository	airlineRepository;

	@Mock

	private PlaneRepository		planeRepository;

	@Mock

	private AirportRepository	airportRepository;

	// We are going to use @InjectMocks besides @BeforeEach

	@InjectMocks

	private PlaneService		planeService;

	@InjectMocks

	private FlightService		flightService;

	@InjectMocks

	private AirportService		airportService;

	private Plane				plane;

	private Airline				airline;

	public Airport				airport;


	@Before
	void setup() {

		MockitoAnnotations.initMocks(this);

		// PRIMERA AEROLÍNEA

		User user = new User();

		user.setUsername("airline1");

		user.setPassword("airline1");

		user.setEnabled(true);

		this.airline = new Airline();

		this.airline.setId(AirportServiceMockedTests.TEST_AIRLINE_ID);

		this.airline.setName("Sevilla Este Airways");

		this.airline.setIdentification("61333744-N");

		this.airline.setCountry("Spain");

		this.airline.setPhone("644584458");

		this.airline.setEmail("minardi@gmail.com");

		LocalDate localDate1 = LocalDate.parse("2010-11-07");

		this.airline.setCreationDate(localDate1);

		this.airline.setReference("SEA-001");

		this.airline.setUser(user);

		this.airline.setFlightsInternal(new HashSet<>());

		this.airline.setPlanesInternal(new HashSet<>());

		//RUNAWAYS AERPUERTO 1

		Runway runway1 = new Runway();
		Runway runway2 = new Runway();
		Runway runway3 = new Runway();
		Set<Runway> runways = new HashSet<>();
		runways.add(runway1);
		runways.add(runway2);
		runways.add(runway3);

		// AEROPUERTO 1

		this.airport.setCity("Seul");
		this.airport.setCode("ICN");
		this.airport.setId(AirportServiceMockedTests.TEST_AIRPORT_ID);
		this.airport.setLatitude(1.0);
		this.airport.setLongitude(1.0);
		this.airport.setMaxNumberOfClients(9000);
		this.airport.setMaxNumberOfPlanes(400);
		this.airport.setName("Aeropuerto Internacional de Incheon");
		this.airport.setRunwaysInternal(runways);

	}

	@Test
	public void createAirportTest() {

		Airport airport2 = new Airport();
		
		Runway runway1 = new Runway();
		Runway runway2 = new Runway();
		Runway runway3 = new Runway();
		Set<Runway> runways = new HashSet<>();
		runways.add(runway1);
		runways.add(runway2);
		runways.add(runway3);

		airport2.setCity("Ciudad mentira");
		airport2.setCode("ABC");
		airport2.setId(2);
		airport2.setLatitude(1.0);
		airport2.setLongitude(-1.0);
		airport2.setMaxNumberOfClients(9000);
		airport2.setMaxNumberOfPlanes(400);
		airport2.setName("Aeropuerto TEST");
		airport2.setRunwaysInternal(runways); 

		Mockito.verify(this.airportRepository, Mockito.times(1)).save(airport2);

	}

	@Test
	public void shouldfindAriportById() {

		Airport expected = this.airportService.findAirportById(AirportServiceMockedTests.TEST_AIRPORT_ID);

		Assertions.assertThat(expected).isEqualTo(this.airport);

	}

	@Test
	public void shouldInsertAirportIntoDatabaseAndGenerateId() throws DataAccessException, IncorrectCartesianCoordinatesException {

		this.airportService.saveAirport(this.airport);

		Mockito.verify(this.airportRepository, Mockito.times(1)).save(this.airport);

	}

	@Test
	public void shouldFindAirportbyName() {

		Airport expected = this.airportService.findAirportsByName("Aeropuerto Internacional de Incheon").get(0);

		Assertions.assertThat(expected).isEqualTo(this.airport);

	}

	@Test
	public void shouldDeleteAirport() {

		this.airportService.deleteAirport(this.airport);

		Assertions.assertThat(this.airportRepository.findAll().stream().allMatch(x -> !x.equals(this.airport)));

	}

}
