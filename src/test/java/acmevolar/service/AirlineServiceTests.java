
package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class AirlineServiceTests {

	@Autowired
	protected AirlineService	airlineService;

	@Autowired
	protected UserService		userService;


	@Test
	@Transactional
	public void shouldInsertAirlineIntoDatabaseAndGenerateId() {

		User user = this.userService.findUserById("airline1").get();

		Airline airline = new Airline();
		airline.setName("Sevilla Este Airways");
		airline.setIdentification("61333744-N");
		airline.setCountry("Spain");
		airline.setPhone("644584458");
		airline.setEmail("minardi@gmail.com");
		LocalDate localDate1 = LocalDate.parse("2010-11-07");
		airline.setCreationDate(localDate1);
		airline.setReference("SEA-001");
		airline.setUser(user);

		this.airlineService.saveAirline(airline);

		assertThat(airline.getId()).isNotNull();
	}

	@Test
	@Transactional
	public void shouldNotInsertAirline() {

		Airline airline = new Airline();
		airline.setName("");
		airline.setIdentification("");
		airline.setCountry("");
		airline.setPhone("");
		airline.setEmail("");
		airline.setCreationDate(null);
		airline.setReference("");

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.airlineService.saveAirline(airline);
		});
	}

	@Test
	public void shouldFindAirlines() {
		Collection<Airline> airlines = this.airlineService.findAirlines();

		assertThat(!airlines.isEmpty());
		assertThat(airlines).asList();
	}

	@Test
	public void shouldFindAirlineById() {
		Airline airline = this.airlineService.findAirlineById(1);

		assertThat(airline.getName().equals("Sevilla Este Airways"));
		assertThat(airline.getIdentification().equals("61333744-N"));
		assertThat(airline.getCountry().equals("Spain"));
		assertThat(airline.getPhone().equals("644584458"));
		assertThat(airline.getEmail().equals("minardi@gmail.com"));
		assertThat(airline.getCreationDate().equals(LocalDate.parse("2010-11-07")));
		assertThat(airline.getReference().equals("SEA-001"));
	}

	@Test
	public void shouldNotFindAirlineById() {
		Airline airline = this.airlineService.findAirlineById(200);

		assertThat(airline == null);
	}
}
