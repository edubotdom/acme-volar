package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
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
	
	
}