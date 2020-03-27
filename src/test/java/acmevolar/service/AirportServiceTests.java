package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.service.exceptions.DuplicatedAirportNameException;
import acmevolar.service.exceptions.IncorrectCartesianCoordinatesException;
import acmevolar.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AirportServiceTests {

	@Autowired
	protected AirportService airportService;

	@Test
	void shouldFindAirportsByName() {
		Collection<Airport> airport = this.airportService.findAirportsByName("Sevilla Airport");
		assertThat(airport.size()).isEqualTo(1);
	}

	@Test
	void shouldFindAirport() {
		Collection<Airport> airports = this.airportService.findAirports();

		Airport airport1 = EntityUtils.getById(airports, Airport.class, 1);
		assertThat(airport1.getName()).isEqualTo("Sevilla Airport");
		assertThat(airport1.getMaxNumberOfPlanes()).isEqualTo(50);
		assertThat(airport1.getMaxNumberOfClients()).isEqualTo(600);
		assertThat(airport1.getLatitude()).isEqualTo(37.4180000);
		assertThat(airport1.getLongitude()).isEqualTo(-5.8931100);
		assertThat(airport1.getCode()).isEqualTo("SVQ");
		assertThat(airport1.getCity()).isEqualTo("Sevilla");
	}

	@Test
	@Transactional
	public void shouldInsertAirport() {

		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(2000);
		airport.setMaxNumberOfClients(25000);
		airport.setLatitude(40.642098);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");

		try {
			this.airportService.saveAirport(airport);
		} catch (DataAccessException | IncorrectCartesianCoordinatesException | DuplicatedAirportNameException e) {
			e.printStackTrace();
		}

		assertThat(airport.getId().longValue()).isNotEqualTo(0);

		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
		int found = airports.size();

		assertThat(found).isEqualTo(1);
	}

	@Test
	@Transactional
	public void shouldThrowInsertExceptionIncorretCardinalCoordinates() {

		Airport airportWithIncorretCardinalCoordinates = new Airport();
		airportWithIncorretCardinalCoordinates.setName("JFK Airport");
		airportWithIncorretCardinalCoordinates.setMaxNumberOfPlanes(2000);
		airportWithIncorretCardinalCoordinates.setMaxNumberOfClients(25000);
		airportWithIncorretCardinalCoordinates.setLatitude(92.5);
		airportWithIncorretCardinalCoordinates.setLongitude(-73.789288);
		airportWithIncorretCardinalCoordinates.setCode("JFK");
		airportWithIncorretCardinalCoordinates.setCity("New York");

		Assertions.assertThrows(IncorrectCartesianCoordinatesException.class, () -> {
			airportService.saveAirport(airportWithIncorretCardinalCoordinates);
		});
	}
	
	@Test
	@Transactional
	public void shouldThrowDuplicatedAirportNameException() {
		Airport firstAirport = new Airport();
		firstAirport.setName("JFK Airport");
		firstAirport.setMaxNumberOfPlanes(2000);
		firstAirport.setMaxNumberOfClients(25000);
		firstAirport.setLatitude(17.5);
		firstAirport.setLongitude(-73.789288);
		firstAirport.setCode("JFK");
		firstAirport.setCity("New York");
		
		try {
			airportService.saveAirport(firstAirport);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IncorrectCartesianCoordinatesException e) {
			e.printStackTrace();
		} catch (DuplicatedAirportNameException e) {
			e.printStackTrace();
		}
		
		Airport secondAirport = new Airport();
		secondAirport.setName("JFK Airport");
		secondAirport.setMaxNumberOfPlanes(100);
		secondAirport.setMaxNumberOfClients(1000);
		secondAirport.setLatitude(60.2);
		secondAirport.setLongitude(-173.789288);
		secondAirport.setCode("NYC");
		secondAirport.setCity("New York City");
		
		Assertions.assertThrows(DuplicatedAirportNameException.class, () -> {
			airportService.saveAirport(secondAirport);
		});
				
	}
	
	@Test
	@Transactional
	public void shouldNotInsertNegativeMaxNumberOfPlanes() {
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(-1);
		airport.setMaxNumberOfClients(25000);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
		
	}
	
	@Test
	@Transactional
	public void shouldNotInsertNegativeMaxNumberOfClients() {
		
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(1000);
		airport.setMaxNumberOfClients(-1);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
		
	}
	
	@Test
	@Transactional
	public void shouldInsert0MaxNumberOfPlanes() {
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(0);
		airport.setMaxNumberOfClients(25000);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		try {
			this.airportService.saveAirport(airport);
		} catch (DataAccessException | IncorrectCartesianCoordinatesException | DuplicatedAirportNameException e) {
			e.printStackTrace();
		}

		assertThat(airport.getId().longValue()).isNotEqualTo(0);

		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
		int found = airports.size();

		assertThat(found).isEqualTo(1);
		
	}
	
	@Test
	@Transactional
	public void shouldInsert0MaxNumberOfClients() {
		
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(1000);
		airport.setMaxNumberOfClients(0);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		try {
			this.airportService.saveAirport(airport);
		} catch (DataAccessException | IncorrectCartesianCoordinatesException | DuplicatedAirportNameException e) {
			e.printStackTrace();
		}

		assertThat(airport.getId().longValue()).isNotEqualTo(0);

		Collection<Airport> airports = this.airportService.findAirportsByName("JFK Airport");
		int found = airports.size();

		assertThat(found).isEqualTo(1);
		
	}
	
	@Test
	@Transactional
	public void shouldNotInsertBigMaxNumberOfPlanes() {
		
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(30001);
		airport.setMaxNumberOfClients(12125);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
		
	}
	
	@Test
	@Transactional
	public void shouldNotInsertBigMaxNumberOfClients() {
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(12125);
		airport.setMaxNumberOfClients(30001);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
	}
	
	
	@Test
	@Transactional
	public void shouldNotInsertCodeWithMoreThan3Letters() {
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(12125);
		airport.setMaxNumberOfClients(10001);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFKF");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
	}
		
	@Test
	@Transactional
	public void shouldNotInsertCodeWithNumbers() {
		
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(12125);
		airport.setMaxNumberOfClients(10001);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JF4");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
		
	}
	
	@Test
	@Transactional
	public void shouldNotInsertCodeWithLessThan3Letters() {
		
		Airport airport = new Airport();
		airport.setName("JFK Airport");
		airport.setMaxNumberOfPlanes(12125);
		airport.setMaxNumberOfClients(10001);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JF");
		airport.setCity("New York");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
		
	}
	
	
	@Test
	@Transactional
	public void shouldNotInsertCityWithNumbers() {
		Airport airport = new Airport();
		airport.setName("JFK Airport 1");
		airport.setMaxNumberOfPlanes(12125);
		airport.setMaxNumberOfClients(10001);
		airport.setLatitude(17.5);
		airport.setLongitude(-73.789288);
		airport.setCode("JFK");
		airport.setCity("New York 1");
		
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			airportService.saveAirport(airport);
		});
	}
	
}