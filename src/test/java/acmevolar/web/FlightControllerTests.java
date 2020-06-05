
package acmevolar.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.model.User;
import acmevolar.service.AirlineService;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;
import acmevolar.service.RunwayService;
import acmevolar.service.UserService;

@WebMvcTest(value = FlightController.class, includeFilters = {
	@ComponentScan.Filter(value = PlaneFormatter.class, type = FilterType.ASSIGNABLE_TYPE), @ComponentScan.Filter(value = FlightStatusTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
	@ComponentScan.Filter(value = RunwayFormatter.class, type = FilterType.ASSIGNABLE_TYPE)
}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FlightControllerTests {

	private static final int	TEST_FLIGHT_ID			= 1;

	private static final int	TEST_FLIGHT_ID_PUBLIC	= 2;

	@Autowired
	protected FlightController	flightController;

	@MockBean
	private FlightService		flightService;

	@MockBean
	private PlaneService		planeService;

	@MockBean
	private RunwayService		runwayService;

	@MockBean
	private AirlineService		airlineService;

	@MockBean
	private UserService			userService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() throws ParseException {

		FlightStatusType flightStatusOnTime = new FlightStatusType();
		flightStatusOnTime.setId(1);
		flightStatusOnTime.setName("on_time");

		FlightStatusType flightStatusDelayed = new FlightStatusType();
		flightStatusDelayed.setId(2);
		flightStatusDelayed.setName("delayed");

		FlightStatusType flightStatusCancelled = new FlightStatusType();
		flightStatusCancelled.setId(3);
		flightStatusCancelled.setName("cancelled");

		List<FlightStatusType> flightStatusTypes = new ArrayList<>();
		flightStatusTypes.add(flightStatusOnTime);
		flightStatusTypes.add(flightStatusDelayed);
		flightStatusTypes.add(flightStatusCancelled);
		BDDMockito.given(this.flightService.findFlightStatusTypes()).willReturn(flightStatusTypes);

		//Airline airline1 = this.airlineService.findAirlineById(1);
		//BDDMockito.given(this.airlineService.findAirlineById(1)).willReturn(airline1);

		User user1 = new User();
		user1.setUsername("airline1");
		user1.setPassword("airline1");
		user1.setEnabled(true);

		//Authorities authority1 = new Authorities();
		//authority1.setUsername("airline");
		//authority1.setUsername("airline");

		Airline airline1 = new Airline();
		airline1.setId(1);
		airline1.setName("Sevilla Este Airways");
		airline1.setIdentification("61333744-N");
		airline1.setCountry("Spain");
		airline1.setPhone("644584458");
		airline1.setEmail("minardi@gmail.com");
		LocalDate localDate1 = LocalDate.parse("2010-11-07");
		airline1.setCreationDate(localDate1);
		airline1.setReference("SEA-001");
		airline1.setUser(user1);
		BDDMockito.given(this.flightService.findAirlineByUsername(user1.getUsername())).willReturn(airline1);

		Plane plane1 = new Plane();
		plane1.setId(1);
		plane1.setAirline(airline1);
		plane1.setDescription("This is a description");
		String stringDate = "2011-04-17";
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
		plane1.setLastMaintenance(date1);
		plane1.setManufacter("Boeing");
		plane1.setMaxDistance(2000000.0);
		plane1.setMaxSeats(150);
		plane1.setModel("B747");
		plane1.setNumberOfKm(500000.23);
		plane1.setReference("V14-5");

		List<Plane> planes = new ArrayList<>();
		planes.add(plane1);

		Airport ap = new Airport();
		ap.setCity("city");
		ap.setCode("code");
		ap.setLatitude(0.);
		ap.setLongitude(0.);
		ap.setMaxNumberOfClients(100);
		ap.setMaxNumberOfPlanes(100);
		ap.setName("airport1");
		ap.setRunwaysInternal(new HashSet<>());
		ap.setId(1);

		Airport ap2 = new Airport();
		ap2.setCity("city2");
		ap2.setCode("code");
		ap2.setLatitude(0.);
		ap2.setLongitude(0.);
		ap2.setMaxNumberOfClients(100);
		ap2.setMaxNumberOfPlanes(100);
		ap2.setName("airport2");
		ap2.setRunwaysInternal(new HashSet<>());
		ap2.setId(2);

		RunwayType rt_landing = new RunwayType();
		rt_landing.setId(1);
		rt_landing.setName("landing");

		RunwayType rt_take_off = new RunwayType();
		rt_take_off.setId(2);
		rt_take_off.setName("take_off");

		Runway r_depart = new Runway();
		r_depart.setAirport(ap2);
		r_depart.setId(1);
		r_depart.setName("runway1");
		r_depart.setFlightsDepartes(new HashSet<Flight>());
		r_depart.setFlightsLands(new HashSet<Flight>());
		r_depart.setRunwayType(rt_take_off);

		Runway r_landing = new Runway();
		r_landing.setAirport(ap);
		r_landing.setId(2);
		r_landing.setName("runway2");
		r_landing.setFlightsDepartes(new HashSet<Flight>());
		r_landing.setFlightsLands(new HashSet<Flight>());
		r_landing.setRunwayType(rt_landing);

		List<Runway> departingRunways = new ArrayList<>();
		departingRunways.add(r_depart);
		BDDMockito.given(this.flightService.findDepartingRunways()).willReturn(departingRunways);

		List<Runway> landingRunways = new ArrayList<>();
		landingRunways.add(r_landing);
		BDDMockito.given(this.flightService.findLandingRunways()).willReturn(landingRunways);

		List<Runway> runways = new ArrayList<>();
		runways.add(r_depart);
		runways.add(r_landing);
		BDDMockito.given(this.runwayService.findAllRunway()).willReturn(runways);

		BDDMockito.given(this.planeService.findPlaneById(1)).willReturn(plane1);
		BDDMockito.given(this.flightService.findPlanesbyAirline("airline1")).willReturn(planes);

		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(airline1);
		flight1.setDepartDate(Date.from(Instant.now().plusSeconds(6000)));
		flight1.setDepartes(r_depart);
		flight1.setFlightStatus(flightStatusOnTime);
		flight1.setLandDate(Date.from(Instant.now().plusSeconds(60000)));
		flight1.setLands(r_landing);
		flight1.setPlane(plane1);
		flight1.setPrice(0.);
		flight1.setPublished(false);
		flight1.setReference("F-01");
		flight1.setSeats(10);

		Flight flight2 = new Flight();
		flight2.setId(2);
		flight2.setAirline(airline1);
		flight2.setDepartDate(Date.from(Instant.now().plusSeconds(6000)));
		flight2.setDepartes(r_depart);
		flight2.setFlightStatus(flightStatusOnTime);
		flight2.setLandDate(Date.from(Instant.now().plusSeconds(60000)));
		flight2.setLands(r_landing);
		flight2.setPlane(plane1);
		flight2.setPrice(0.);
		flight2.setPublished(true);
		flight2.setReference("F-02");
		flight2.setSeats(10);
		
		List<Flight> flights = new ArrayList<>();
		flights.add(flight1);
		flights.add(flight2);

		BDDMockito.given(this.flightService.findFlightById(1)).willReturn(flight1);

		BDDMockito.given(this.flightService.findFlightById(2)).willReturn(flight2);

		BDDMockito.given(this.planeService.findPlanes()).willReturn(planes);
		
		BDDMockito.given(this.flightService.findPublishedFutureFlight()).willReturn(flights);
		
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flights")).andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowAirlineFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_flights")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flights")).andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowFlight() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}", FlightControllerTests.TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/flightDetails"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "100").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway2, airport: airport1, city: city', 'runway1, airport: airport2, city: city2', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2022-03-27, 2022-03-27",
	})
	void testProcessCreationFormSuccess(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price).param("flightStatus", flightStatus).param("plane", plane)
				.param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "12100").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"'', 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, -100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, -100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, abcd, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'error', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27", "R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'error', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2015-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2013-03-27"
	})
	void testProcessCreationFormHasErrors(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price).param("flightStatus", flightStatus).param("plane", plane)
				.param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateFormAlreadyPublic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID_PUBLIC)).andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "150").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway2, airport: airport1, city: city', 'runway1, airport: airport2, city: city2', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2022-03-27, 2022-03-27",
	})
	void testProcessUpdateFormSuccess(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price)
				.param("flightStatus", flightStatus).param("plane", plane).param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "438450").param("price", "-20.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"'', 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, -100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, -100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, abcd, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'error', 'runway2, airport: airport1, city: city', 2021-03-27, 2021-03-27", "R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'error', 2021-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2015-03-27, 2021-03-27",
		"R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'runway2, airport: airport1, city: city', 2021-03-27, 2013-03-27"
	})
	void testProcessUpdateFormHasErrors(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price)
				.param("flightStatus", flightStatus).param("plane", plane).param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testShowAirlineFlightListAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_flights")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testInitCreationFormAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testProcessCreationFormSuccessAnonymous() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "100").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testProcessCreationFormHasErrorsAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "12100").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testInitUpdateFormSuccessAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testInitUpdateFormAlreadyPublicAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID_PUBLIC)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testProcessUpdateFormSuccessAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "150").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void testProcessUpdateFormHasErrorsAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-05").param("seats", "438450").param("price", "-20.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "runway1, airport: airport2, city: city2")
			.param("lands", "runway2, airport: airport1, city: city").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
