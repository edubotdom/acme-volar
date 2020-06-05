	
package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airport;
import acmevolar.model.Flight;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class RunwayServiceTests {

	@Autowired
	protected FlightService		flightService;

	@Autowired
	protected RunwayService		runwayService;

	@Autowired
	protected AirportService	airportService;

	//	@Autowired
	//	private MockMvc mockMvc;


	//Registrar una pista correctamente
	@Test
	@Transactional
	public void shouldInsertRunwayIntoDatabaseAndGenerateId() {

		Airport airport1 = this.airportService.findAirportById(1);
		RunwayType runwayType1 = this.runwayService.findRunwayTypeById(1);
		List<Runway> runwaysActual = this.runwayService.findAllRunway();
		int numRunwaysActual = runwaysActual.size();

		Runway runway = new Runway();
		runway.setName("Example runway");
		runway.setRunwayType(runwayType1);
		runway.setAirport(airport1);

		assertThat(runway.getRunwayType()).isEqualTo(runwayType1);
		assertThat(runway.getAirport()).isEqualTo(airport1);

		this.runwayService.saveRunway(runway);

		List<Runway> runwaysPosterior = this.runwayService.findAllRunway();
		int numRunwaysPosterior = runwaysPosterior.size();

		// checks that id has been generated
		assertThat(runway.getId()).isNotNull();
		//check that runway is inserted
		assertThat(numRunwaysActual == numRunwaysPosterior - 1);
	}

	//Registrar una pista correctamente con vuelos
	@Test
	@Transactional
	public void shouldInsertRunwayWithFlights() {

		Airport airport1 = this.airportService.findAirportById(1);
		RunwayType runwayType1 = this.runwayService.findRunwayTypeById(1);
		Flight flight1 = this.flightService.findFlightById(1);
		Flight flight2 = this.flightService.findFlightById(2);

		Runway runway = new Runway();
		runway.setName("Example runway");
		runway.setRunwayType(runwayType1);
		runway.setAirport(airport1);

		Set<Flight> setDepartes = new HashSet<Flight>();
		setDepartes.add(flight1);

		Set<Flight> setLands = new HashSet<Flight>();
		setLands.add(flight2);

		runway.setFlightsDepartes(setDepartes);
		runway.setFlightsLands(setLands);

		this.runwayService.saveRunway(runway);

		// checks that flights are introduced
		assertThat(runway.getFlightsDepartes()).contains(flight1);
		assertThat(runway.getFlightsLands()).contains(flight2);
	}

	//Test de un metodo
	@Test
	@Transactional
	public void testFindRunwaysTypes() {

		List<RunwayType> runwayType = this.runwayService.findRunwaysTypes();
		List<String> runwayTypeNames = runwayType.stream().map(r -> r.getName()).collect(Collectors.toList());

		List<String> names = new ArrayList<>();
		names.add("take_off");
		names.add("landing");
		// checks types are correct
		assertThat(runwayTypeNames).containsAll(names);
	}

	//Test de un metodo
	@Test
	@Transactional
	public void testFindRunwaysByName() {

		Airport airport1 = this.airportService.findAirportById(1);
		RunwayType runwayType1 = this.runwayService.findRunwayTypeById(1);

		Runway runway = new Runway();
		runway.setName("Example runway");
		runway.setRunwayType(runwayType1);
		runway.setAirport(airport1);

		this.runwayService.saveRunway(runway);

		List<Runway> runwayTypeByName = this.runwayService.findRunwaysByName("Example runway");

		// checks method
		assertThat(runwayTypeByName).contains(runway);

	}

	//Registrar una pista sin nombre
	@Test
	@Transactional
	public void shouldNotInsertRunwayWithEmptyName() {

		Airport airport1 = this.airportService.findAirportById(1);
		RunwayType runwayType1 = this.runwayService.findRunwayTypeById(1);

		Runway runway = new Runway();

		runway.setRunwayType(runwayType1);
		runway.setAirport(airport1);

		assertThat(runway.getRunwayType()).isEqualTo(runwayType1);
		assertThat(runway.getAirport()).isEqualTo(airport1);

		// checks that id has been generated
		assertThrows(ConstraintViolationException.class, () -> {
			runway.setName("");
			this.runwayService.saveRunway(runway);
		});
	}

	//Registrar una pista sin aeropuerto ni tipo
	@Test
	@Transactional
	public void shouldNotInsertRunwayWithNoAirlineNeitherType() {

		Airport airport1 = null;
		RunwayType runwayType1 = null;

		Runway runway = new Runway();
		runway.setName("Example runway");

		assertThrows(ConstraintViolationException.class, () -> {
			runway.setRunwayType(runwayType1);
			runway.setAirport(airport1);
			this.runwayService.saveRunway(runway);
		});
	}
	
	//Borrar una pista correctamente
		@Test
		@Transactional
		public void shouldDeleteRunway() {

			Airport airport1 = this.airportService.findAirportById(1);
			RunwayType runwayType1 = this.runwayService.findRunwayTypeById(1);
			List<Runway> runwaysActual = this.runwayService.findAllRunway();
			int numRunwaysActual = runwaysActual.size();

			Runway runway = new Runway();
			runway.setName("Example runway");
			runway.setRunwayType(runwayType1);
			runway.setAirport(airport1);
			this.runwayService.saveRunway(runway);
			int runwayId=runway.getId();

			this.runwayService.deleteRunwayById(runwayId);

			List<Runway> runwaysPosterior = this.runwayService.findAllRunway();
			int numRunwaysPosterior = runwaysPosterior.size();

			//check that runway is deleted
			assertThat(numRunwaysActual == numRunwaysPosterior + 1);
		}

}
