
package acmevolar.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Authorities;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.model.User;
import acmevolar.repository.RunwayRepository;

@ExtendWith(MockitoExtension.class)
public class RunwayServiceMockedTests {

	private static final int	TEST_RUNWAY_ID	= 1;

	@Mock
	private RunwayRepository	runwayRepository;

	// We are going to use @InjectMocks besides @BeforeEach
	@InjectMocks
	protected RunwayService		runwayService;

	private Runway				runway;


	@Before
	void setup() {

		MockitoAnnotations.initMocks(this);

		User user1 = new User();
		user1.setUsername("client1");
		user1.setPassword("client1");
		user1.setEnabled(true);

		Authorities authority1 = new Authorities();
		authority1.setUsername("client1");
		authority1.setAuthority("client");

		Client client1 = new Client();
		client1.setId(1);
		client1.setName("Sergio Pérez");
		client1.setIdentification("53933261-P");
		client1.setPhone("644584458");
		client1.setEmail("minardi@gmail.com");
		LocalDate localDate2 = LocalDate.parse("1994-09-07");
		client1.setBirthDate(localDate2);
		client1.setCreationDate(localDate2);
		client1.setUser(user1);

		///////////////////////////////////////////////////////////////

		RunwayType runwayType1 = new RunwayType();
		runwayType1.setName("take_off");
		runwayType1.setId(1);

		RunwayType runwayType2 = new RunwayType();
		runwayType2.setName("landing");
		runwayType2.setId(2);

		List<RunwayType> rts = new ArrayList<RunwayType>();
		rts.add(runwayType1);
		rts.add(runwayType2);

		this.runway.setName("Example Runway");
		this.runway.setRunwayType(runwayType1);

		this.runwayService.saveRunway(this.runway);

		Runway runway2 = new Runway();
		runway2.setId(2);
		runway2.setName("Example Runway 2");
		runway2.setRunwayType(runwayType1);

		this.runwayService.saveRunway(runway2);

		Runway runway3 = new Runway();
		runway3.setId(3);
		runway3.setName("Example Runway 3");
		runway3.setRunwayType(runwayType2);

		this.runwayService.saveRunway(runway3);

		Airport airport = new Airport();
		airport.setId(1);
		airport.setName("Sevilla Airport");
		airport.setMaxNumberOfPlanes(200);
		airport.setMaxNumberOfClients(200);
		airport.setLatitude(123.123);
		airport.setLongitude(78.987);
		airport.setCode("VGA");
		airport.setCity("Sevilla");

		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(new Airline());
		flight1.setDepartDate(Date.from(Instant.now().minusSeconds(6000)));
		flight1.setDepartes(runway2);
		flight1.setFlightStatus(new FlightStatusType());
		flight1.setLandDate(Date.from(Instant.now().plusSeconds(6000)));
		flight1.setLands(runway3);
		flight1.setPlane(new Plane());
		flight1.setPrice(0.);
		flight1.setPublished(false);
		flight1.setReference("F-01");
		flight1.setSeats(10);

		List<Flight> flights = new ArrayList<Flight>();
		flights.add(flight1);

	}
/*
	@Test
	public void shouldInsertRunwayIntoDatabaseAndGenerateId() {
		this.runwayService.saveRunway(this.runway);
		Mockito.verify(this.runwayRepository, Mockito.times(1)).save(this.runway);
	}
*/
	@Test
	public void shouldInsertRunwayWithFlights() {

	}

	@Test
	public void testFindRunwaysTypes() {

	}

	@Test
	public void testFindRunwaysByName() {

	}

	@Test
	public void shouldNotInsertRunwayWithEmptyName() {

	}

	@Test
	public void shouldNotInsertRunwayWithNoAirlineNeitherType() {

	}

	@Test
	public void shouldDeleteRunway() {

	}

}
