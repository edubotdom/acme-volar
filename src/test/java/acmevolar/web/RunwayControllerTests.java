
package acmevolar.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.service.AirportService;
import acmevolar.service.FlightService;
import acmevolar.service.RunwayService;

@WebMvcTest(value = RunwayController.class, 
includeFilters = @ComponentScan.Filter(value = RunwayTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class RunwayControllerTests {

	private static final int TEST_RUNWAY_ID1 = 1; // Departes from Sevilla
	private static final int TEST_RUNWAY_TAKE_OFF_ID1 = 1;
	private static final int TEST_RUNWAY_ID4 = 4; // Lands in Madrid
	private static final int TEST_RUNWAY_LANDING_ID2 = 2;

	private static final int TEST_AIRPORT_ID1 = 1;// Sevilla
	private static final int TEST_AIRPORT_ID2 = 2; // Madrid

	@Autowired
	private RunwayController runwayController;

	@MockBean
	protected RunwayService runwayService;

	@MockBean
	protected AirportService airportService;

	@MockBean
	protected FlightService flightService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		RunwayType runwayType1 = new RunwayType();
		runwayType1.setName("take_off");
		runwayType1.setId(1);
		given(this.runwayService.findRunwayTypeById(RunwayControllerTests.TEST_RUNWAY_TAKE_OFF_ID1)).willReturn(runwayType1);

		RunwayType runwayType2 = new RunwayType();
		runwayType2.setName("landing");
		runwayType2.setId(2);
		given(this.runwayService.findRunwayTypeById(RunwayControllerTests.TEST_RUNWAY_LANDING_ID2)).willReturn(runwayType2);

		List<RunwayType> rts = new ArrayList<RunwayType>();
		rts.add(runwayType1);
		rts.add(runwayType2);
		given(this.runwayService.findRunwaysTypes()).willReturn(rts);

		Runway runway = new Runway();
		runway.setId(1);
		runway.setName("Example Runway");
		runway.setRunwayType(runwayType1);
		given(this.runwayService.findRunwayById(RunwayControllerTests.TEST_RUNWAY_ID1)).willReturn(runway);
		
		Runway runway2 = new Runway();
		runway.setId(2);
		runway.setName("Example Runway 2");
		runway.setRunwayType(runwayType1);
		given(this.runwayService.findRunwayById(2)).willReturn(runway2);
		
		Runway runway3 = new Runway();
		runway.setId(3);
		runway.setName("Example Runway 3");
		runway.setRunwayType(runwayType2);
		given(this.runwayService.findRunwayById(3)).willReturn(runway3);

		Airport airport = new Airport();
		airport.setId(1);
		airport.setName("Sevilla Airport");
		airport.setMaxNumberOfPlanes(200);
		airport.setMaxNumberOfClients(200);
		airport.setLatitude(123.123);
		airport.setLongitude(78.987);
		airport.setCode("VGA");
		airport.setCity("Sevilla");
		given(this.runwayService.findAirportById(RunwayControllerTests.TEST_AIRPORT_ID1)).willReturn(airport);
		
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
		given(this.flightService.findFlights()).willReturn(flights);
	}

	@WithMockUser(value = "client1"/*, authorities = { "airline" }*/)
	@Test
	void testShowRunwayList() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}/runways", TEST_AIRPORT_ID1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("runways", "airport")).andExpect(view().name("runways/runwayList"));
	}

	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("runway", "airport"))
				.andExpect(view().name("runways/createOrUpdateRunwaysForm"));
	}

	@Test
	void testUnauthorizedInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1)).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
				.perform(post("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1).with(csrf())
						.param("name", "Runway Example").param("runwayType", "landing"))
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void testUnauthorizedProcessCreationForm() throws Exception {
		this.mockMvc
				.perform(post("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1).with(csrf())
						.param("name", "Runway Example").param("runwayType", "landing"))
				.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
@ParameterizedTest 
@CsvSource({
    "runway1, landing",
    "runway2, take_off",
    "runway3, landing",
    "runway4, take_off",
}) 
void testProcessCreationFormSuccess(String name, String runwayType) throws Exception {    
	this.mockMvc.perform(post("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1).with(csrf())
			.param("name", name)
			.param("runwayType", runwayType))
			.andExpect(status().is3xxRedirection());
} 
	
	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
				.perform(post("/airports/{airportId}/runways/new", TEST_AIRPORT_ID1).with(csrf())
						.param("name", "")
						.param("runwayType", "take_off"))
				.andExpect(model().attributeHasErrors("runway")).andExpect(view().name("runways/createOrUpdateRunwaysForm"));
	}

	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/runways/{runwayId}/edit", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runway", "runwayTypes"))
				.andExpect(view().name("runways/createOrUpdateRunwaysForm"));
	}

	@Test
	void testUnauthorizedInitUpdateForm() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/runways/{runwayId}/edit", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1))
				.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/runways/{runwayId}/edit", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1)
				.with(csrf()).param("name", "Runway Example").param("runwayType", "landing"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/airports/{airportId}/runways"));
	}
	
	@Test
	void testUnauthorizedProcessUpdateForm() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/runways/{runwayId}/edit", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1)
				.with(csrf()))
				.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testDeleteSuccess() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/runways/{runwayId}/delete", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/airports/{airportId}/runways"));
	}
	
	@WithMockUser(value = "airline1", authorities = { "airline" })
	@Test
	void testDeleteHasErrors() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/runways/{runwayId}/delete", TEST_AIRPORT_ID1, 2))
				.andExpect(view().name("exception"));
	}
	
	@Test
	void testDeleteUnauthorized() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/runways/{runwayId}/delete", TEST_AIRPORT_ID1, TEST_RUNWAY_ID1))
				.andExpect(status().is4xxClientError());
	}

}
