package acmevolar.service;

import  static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
import acmevolar.model.User;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceMockedTests {
	
	private static final int TEST_AIRLINE_ID = 1;
	
	@Mock
	private AirlineRepository airlineRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private AirlineService airlineService;
	
	@InjectMocks
	private UserService userService;
	
	private Airline airline;
	
	private Airline airlineIncorrect;
	
	@Before
	void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		User user1 = new User();
		user1.setUsername("airline1");
		user1.setPassword("airline1");
		user1.setEnabled(true);
		
		airline.setName("Sevilla Este Airways");
		airline.setIdentification("61333744-N");
		airline.setCountry("Spain");
		airline.setPhone("644584458");
		airline.setEmail("minardi@gmail.com");
		LocalDate localDate1 = LocalDate.parse("2010-11-07");
		airline.setCreationDate(localDate1);
		airline.setReference("PPT-001");
		airline.setUser(user1);
		airline.setFlightsInternal(new HashSet<>());
		airline.setPlanesInternal(new HashSet<>());
		
		airlineService.saveAirline(airline);
		
		User user2 = new User();
		user2.setUsername("airline2");
		user2.setPassword("airline2");
		user2.setEnabled(true);
		
		airlineIncorrect = new Airline();
		airlineIncorrect.setUser(user2);
		
	}
	
	// NO FUNCIONAsddsds
	@Test
	public void shouldInsertAirlineIntoDatabase() {
		airlineService.saveAirline(airline);
		Mockito.verify(airlineRepository,Mockito.times(1)).save(airline);
	}
	
	// NO FUNCIONA
	@Test
	public void shouldNotInsertAirlineIntoDatabase() {
		Mockito.verify(airlineService, Mockito.times(0)).saveAirline(airlineIncorrect);;
	}
	
	@Test
	public void shouldFindAirlines() {
		assertThat(airlineService.findAirlines().size()).isEqualTo(0);
	}
	
	@Test
	public void shouldFindAirlineById() {
		assertThat(airlineService.findAirlineById(TEST_AIRLINE_ID)).isEqualTo(airline);
	}
	
	@Test
	public void shouldNotFindAirlineById() {
		assertThat(airlineService.findAirlineById(200)).isNull();
	}
	
}
