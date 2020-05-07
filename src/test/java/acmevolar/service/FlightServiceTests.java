
package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FlightServiceTests {

	@Autowired
	protected FlightService		flightService;

	@Autowired
	protected PlaneService		planeService;

	@Autowired
	protected RunwayService		runwayService;

	@Autowired
	protected AirlineService	airlineService;
/*

	@Test
	@Transactional
	void shouldFindFlightById() throws ParseException {
		Flight flight1 = this.flightService.findFlightById(1);

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(1);

		Runway runway2 = this.runwayService.findRunwayById(2);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-06 14:05:00";
		Timestamp departDate1 = Timestamp.valueOf(stringDepartDate1);

		String stringLandDate1 = "2020-06-06 15:00:00";
		Timestamp landDate1 = Timestamp.valueOf(stringLandDate1);

		assertThat(flight1.getAirline()).isEqualTo(airline1);
		assertThat(flight1.getDepartDate()).isEqualTo(departDate1);
		assertThat(flight1.getDepartes()).isEqualTo(runway1);
		assertThat(flight1.getFlightStatus()).isEqualTo(flightStatusType1);
		assertThat(flight1.getId()).isEqualTo(1);
		assertThat(flight1.getLandDate()).isEqualTo(landDate1);
		assertThat(flight1.getLands()).isEqualTo(runway2);
		assertThat(flight1.getPlane()).isEqualTo(plane1);
		assertThat(flight1.getPrice()).isEqualTo(150.0);
		assertThat(flight1.getPublished()).isEqualTo(true);
		assertThat(flight1.getReference()).isEqualTo("R-01");
		assertThat(flight1.getSeats()).isEqualTo(250);
	}

	@Test
	@Transactional
	public void shouldSaveFlight() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		try {
			this.flightService.saveFlight(flight);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		assertThat(flight.getId()).isNotNull();

		Flight flightFound = this.flightService.findFlightById(flight.getId());

		assertThat(flight.equals(flightFound));
	}

	@Test
	@Transactional
	public void shouldFindFlights() {
		Collection<Flight> flights = this.flightService.findFlights();

		assertThat(!flights.isEmpty());
		assertThat(flights).asList();
	}

	@Test
	@Transactional
	public void shouldFindPublishedFlights() {
		Collection<Flight> publishedFlights = this.flightService.findPublishedFlight();

		assertThat(!publishedFlights.isEmpty());
		assertThat(publishedFlights).asList();

		List<Flight> publishedFlightsStream = publishedFlights.stream().filter(x -> x.getPublished()).collect(Collectors.toList());
		assertThat(publishedFlights.size() == publishedFlightsStream.size());
	}

	@Test
	@Transactional
	public void shouldFindPublishedFutureFlights() {
		Collection<Flight> publishedFutureFlights = this.flightService.findPublishedFutureFlight();

		assertThat(!publishedFutureFlights.isEmpty());
		assertThat(publishedFutureFlights).asList();

		Date currentDate = new Date(System.currentTimeMillis());
		List<Flight> publishedFutureFlightsStream = publishedFutureFlights.stream().filter(x -> x.getPublished() && x.getDepartDate().compareTo(currentDate) >= 0).collect(Collectors.toList());
		assertThat(publishedFutureFlights.size() == publishedFutureFlightsStream.size());
	}

	@Test
	@Transactional
	public void shouldFindAirlineFlights() {
		Collection<Flight> airlineFlights = this.flightService.findAirlineFlight("airline1");

		assertThat(!airlineFlights.isEmpty());
		assertThat(airlineFlights).asList();

		Airline airline = this.flightService.findAirlineByUsername("airline1");
		List<Flight> airlineFlightsStream = airlineFlights.stream().filter(x -> x.getAirline().equals(airline)).collect(Collectors.toList());
		assertThat(airlineFlights.size() == airlineFlightsStream.size());
	}

	@Test
	@Transactional
	void shouldFindFlightByReference() throws ParseException {
		Flight flight1 = this.flightService.findFlightByReference("R-01");

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(1);

		Runway runway2 = this.runwayService.findRunwayById(2);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-06 14:05:00";
		Timestamp departDate1 = Timestamp.valueOf(stringDepartDate1);

		String stringLandDate1 = "2020-06-06 15:00:00";
		Timestamp landDate1 = Timestamp.valueOf(stringLandDate1);

		assertThat(flight1.getAirline()).isEqualTo(airline1);
		assertThat(flight1.getDepartDate()).isEqualTo(departDate1);
		assertThat(flight1.getDepartes()).isEqualTo(runway1);
		assertThat(flight1.getFlightStatus()).isEqualTo(flightStatusType1);
		assertThat(flight1.getId()).isEqualTo(1);
		assertThat(flight1.getLandDate()).isEqualTo(landDate1);
		assertThat(flight1.getLands()).isEqualTo(runway2);
		assertThat(flight1.getPlane()).isEqualTo(plane1);
		assertThat(flight1.getPrice()).isEqualTo(150.0);
		assertThat(flight1.getPublished()).isEqualTo(true);
		assertThat(flight1.getReference()).isEqualTo("R-01");
		assertThat(flight1.getSeats()).isEqualTo(250);
	}

	@Test
	@Transactional
	public void shouldFindFlightStatusTypes() {
		Collection<FlightStatusType> flightStatusTypes = this.flightService.findFlightStatusTypes();

		assertThat(!flightStatusTypes.isEmpty());
		assertThat(flightStatusTypes).asList();
	}

	@Test
	@Transactional
	void shouldFindAirlineByUsername() throws ParseException {
		Airline airline1 = this.flightService.findAirlineByUsername("airline1");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String stringCreationDate1 = "2010-11-07";
		LocalDate creationDate1 = LocalDate.parse(stringCreationDate1, formatter);

		User user1 = this.flightService.findAirlineByUsername("airline1").getUser();

		assertThat(airline1.getCountry()).isEqualTo("Spain");
		assertThat(airline1.getCreationDate()).isEqualTo(creationDate1);
		assertThat(airline1.getEmail()).isEqualTo("minardi@gmail.com");
		assertThat(airline1.getIdentification()).isEqualTo("61333744-N");
		assertThat(airline1.getName()).isEqualTo("Sevilla Este Airways");
		assertThat(airline1.getPhone()).isEqualTo("644584458");
		assertThat(airline1.getReference()).isEqualTo("SEA-001");
		assertThat(airline1.getUser()).isEqualTo(user1);
	}

	@Test
	@Transactional
	public void shouldFindPlanesByAirline() {
		Collection<Plane> airlinePlanes = this.flightService.findPlanesbyAirline("airline1");

		assertThat(!airlinePlanes.isEmpty());
		assertThat(airlinePlanes).asList();

		Airline airline = this.flightService.findAirlineByUsername("airline1");
		List<Plane> airlinePlanesStream = airlinePlanes.stream().filter(x -> x.getAirline().equals(airline)).collect(Collectors.toList());
		assertThat(airlinePlanes.size() == airlinePlanesStream.size());
	}

	@Test
	@Transactional
	public void shouldFindDepartingRunways() {
		Collection<Runway> departingRunways = this.flightService.findDepartingRunways();

		assertThat(!departingRunways.isEmpty());
		assertThat(departingRunways).asList();

		List<Runway> departingRunwaysStream = departingRunways.stream().filter(x -> x.getFlightsDepartes().size() > 0).collect(Collectors.toList());
		assertThat(departingRunways.size() == departingRunwaysStream.size());
	}

	@Test
	@Transactional
	public void shouldFindLandingRunways() {
		Collection<Runway> landingRunways = this.flightService.findDepartingRunways();

		assertThat(!landingRunways.isEmpty());
		assertThat(landingRunways).asList();

		List<Runway> landingRunwaysStream = landingRunways.stream().filter(x -> x.getFlightsDepartes().size() > 0).collect(Collectors.toList());
		assertThat(landingRunways.size() == landingRunwaysStream.size());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	@Transactional
	public void shouldNotSaveFlightEmptyReference() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullSeats() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(null);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNegativeSeats() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(-150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullPrice() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(null);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNegativePrice() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(-100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullFlightStatus() throws ParseException {

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(null);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullPlane() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(null);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullPublished() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(null);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullDepartes() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway2 = this.runwayService.findRunwayById(1);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(null);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(runway2);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}

	@Test
	@Transactional
	public void shouldNotSaveFlightNullLands() throws ParseException {

		List<FlightStatusType> flightStatusList = this.flightService.findFlightStatusTypes().stream().filter(x -> x.getId().equals(1)).collect(Collectors.toList());
		FlightStatusType flightStatusType1 = flightStatusList.get(0);

		Plane plane1 = this.planeService.findPlaneById(1);

		Runway runway1 = this.runwayService.findRunwayById(2);

		Airline airline1 = this.airlineService.findAirlineById(1);

		String stringDepartDate1 = "2020-06-07 14:05";
		Date departDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringDepartDate1);

		String stringLandDate1 = "2020-06-07 15:00";
		Date landDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(stringLandDate1);

		Flight flight = new Flight();
		flight.setAirline(airline1);
		flight.setDepartDate(departDate1);
		flight.setDepartes(runway1);
		flight.setFlightStatus(flightStatusType1);
		flight.setLandDate(landDate1);
		flight.setLands(null);
		flight.setPlane(plane1);
		flight.setPrice(100.0);
		flight.setPublished(true);
		flight.setReference("R-02");
		flight.setSeats(150);

		assertThrows(ConstraintViolationException.class, () -> {
			this.flightService.saveFlight(flight);
		});
	}
*/
}
