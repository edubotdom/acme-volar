package acmevolar.service;

import  static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.model.User;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.PlaneRepository;

@ExtendWith(MockitoExtension.class)
public class PlaneServiceMockedTests {
	
	private static final int	TEST_PLANE_ID	= 1;
	private static final int	TEST_AIRLINE_ID	= 1;

	@Mock
	private AirlineRepository airlineRepository;
	
	@Mock
	private PlaneRepository planeRepository;
	
	// We are going to use @InjectMocks besides @BeforeEach
	@InjectMocks
	private PlaneService planeService;
	
	@InjectMocks
	private FlightService flightService;
	
	private Plane plane;
	
	private Airline airline;
	
	@Before
	void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		// PRIMERA AEROLÍNEA
		
		User user = new User();
		user.setUsername("airline1");
		user.setPassword("airline1");
		user.setEnabled(true);
		
		airline = new Airline();
		airline.setId(TEST_AIRLINE_ID);
		airline.setName("Sevilla Este Airways");
		airline.setIdentification("61333744-N");
		airline.setCountry("Spain");
		airline.setPhone("644584458");
		airline.setEmail("minardi@gmail.com");
		LocalDate localDate1 = LocalDate.parse("2010-11-07");
		airline.setCreationDate(localDate1);
		airline.setReference("SEA-001");
		airline.setUser(user);
		airline.setFlightsInternal(new HashSet<>());
		airline.setPlanesInternal(new HashSet<>());

		plane.setFlightsInternal(new HashSet<Flight>());
		//plane.setId(TEST_PLANE_ID);
		plane.setReference("V14-5");
		plane.setMaxSeats(150);
		plane.setDescription("This is a description");
		plane.setManufacter("manufacturer");
		plane.setModel("model");
		plane.setNumberOfKm(100.);
		plane.setMaxDistance(200.);
		plane.setLastMaintenance(Date.from(Instant.parse("2011-04-17T00:00:00.00Z")));
		airline.addPlane(plane);
		
		planeService.savePlane(plane);
		
		Plane plane2 = new Plane();
		plane2.setFlightsInternal(new HashSet<Flight>());
		plane2.setId(2);
		plane2.setReference("V18-5");
		plane2.setMaxSeats(150);
		plane2.setDescription("This is a description");
		plane2.setManufacter("manufacturer");
		plane2.setModel("model");
		plane2.setNumberOfKm(100.);
		plane2.setMaxDistance(200.);
		plane2.setLastMaintenance(Date.from(Instant.parse("2011-04-17T00:00:00.00Z")));
		airline.addPlane(plane2);
		
		planeService.savePlane(plane2);
		
		// SEGUNDA AEROLÍNEA
		
		User user2 = new User();
		user2.setUsername("airline2");
		user2.setPassword("airline2");
		user2.setEnabled(true);
		
		Airline	airline2 = new Airline();
		airline2.setId(2);
		airline2.setName("Edumig Airways");
		airline2.setIdentification("61333754-N");
		airline2.setCountry("Spain");
		airline2.setPhone("644584468");
		airline2.setEmail("edumig@gmail.com");
		LocalDate localDate2 = LocalDate.parse("2010-11-08");
		airline2.setCreationDate(localDate2);
		airline2.setReference("SEA-002");
		airline2.setUser(user2);
		airline2.setFlightsInternal(new HashSet<>());
		airline2.setPlanesInternal(new HashSet<>());
		
		Plane plane3 = new Plane();
		
		plane3.setFlightsInternal(new HashSet<Flight>());
		plane3.setId(3);
		plane3.setReference("V24-2");
		plane3.setMaxSeats(150);
		plane3.setDescription("This is a description");
		plane3.setManufacter("manufacturer");
		plane3.setModel("model");
		plane3.setNumberOfKm(100.);
		plane3.setMaxDistance(200.);
		plane3.setLastMaintenance(Date.from(Instant.parse("2011-04-17T00:00:00.00Z")));
		airline2.addPlane(plane3);
		
		planeService.savePlane(plane3);
	}
	
	@Test
	public void createPlaneTest() {
		planeService.savePlane(plane);
		Mockito.verify(planeRepository, Mockito.times(1)).save(plane);
	}
	
	@Test
	public void shouldfindPlaneById() {
		Plane expected = planeService.findPlaneById(TEST_PLANE_ID);
		assertThat(expected).isEqualTo(plane);
	}
	
	@Test
	public void shouldInsertPlaneIntoDatabaseAndGenerateId() {
		planeService.savePlane(plane);
		Mockito.verify(planeRepository, Mockito.times(1)).save(plane);
	}
	
	@Test
	public void shouldDeletePlaneById() {
		planeService.deleteById(TEST_PLANE_ID);
		Mockito.verify(planeRepository, Mockito.times(1)).deleteById(TEST_PLANE_ID);
	}
	
	@Test
	void shouldFindPlanes() {
		assertThat(planeService.findPlanes().size()).isEqualTo(3);
	}
	
	// NO FUNCIONA
	@Test
	void shouldFindAirlineByUsername() {
		assertThat(planeService.findAirlineByUsername("airline1")).isEqualTo(airline);
	}
	
	void shouldGetAllPlanesFromAirline() {
		
	}
	
	// NO FUNCIONA
	@Test
	void shouldGetAllPlanesFromAirlineId() {
		assertThat(planeService.getAllPlanesFromAirline(airline.getUser().getUsername()).size()).isEqualTo(2);
	}
	
	void shouldFindPlaneInformationById() {
		
	}
	
	void shouldFindPlaneInformation() {
		
	}
	
	void shouldFindPlaneInformationByFlight() {
		
	}
	
	void shouldNotFindPlaneInformation() {
		
	}
	
	void shouldNotFindPlaneInformationByFlight() {
		
	}
	
}
