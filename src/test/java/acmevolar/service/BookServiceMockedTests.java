package acmevolar.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import acmevolar.model.Airline;
import acmevolar.model.Book;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.model.User;
import acmevolar.repository.BookRepository;
import acmevolar.repository.ClientRepository;
import acmevolar.repository.FlightRepository;
import acmevolar.repository.PlaneRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceMockedTests {

	private static final int TEST_BOOK_ID = 1;
	private static final int TEST_AIRLINE_ID = 1;
	
	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private PlaneRepository planeRepository;
	
	@Mock
	private FlightRepository flightRepository;
	
	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private BookService bookService;
	
	@InjectMocks
	private PlaneService planeService;
	
	@InjectMocks
	private FlightService flightService;
	
	@InjectMocks
	private ClientService clientService;
	
	private Airline airline;
	
	private Plane plane;
	
	private Book book;
	
	private Client client;
	
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
		
	}
}
