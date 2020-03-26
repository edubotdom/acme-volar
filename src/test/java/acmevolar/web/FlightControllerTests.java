
package acmevolar.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.service.AirlineService;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;
import acmevolar.service.RunwayService;
import acmevolar.service.UserService;

@WebMvcTest(value = FlightController.class, includeFilters = {
	@ComponentScan.Filter(value = PlaneFormatter.class, type = FilterType.ASSIGNABLE_TYPE), @ComponentScan.Filter(value = FlightStatusTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE)
}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FlightControllerTests {

	private static final int	TEST_FLIGHT_ID	= 1;

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

		Airline airline1 = this.airlineService.findAirlineById(1);
		BDDMockito.given(this.airlineService.findAirlineById(1)).willReturn(airline1);

		Plane plane1 = new Plane();
		plane1.setId(1);
		plane1.setAirline(new Airline());
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

		BDDMockito.given(this.planeService.findPlaneById(1)).willReturn(plane1);
		BDDMockito.given(this.flightService.findPlanesbyAirline("airline1")).willReturn(planes);

		BDDMockito.given(this.flightService.findFlightById(FlightControllerTests.TEST_FLIGHT_ID)).willReturn(new Flight());
	}

	@WithMockUser(value = "airline1")
	@Test
	void testShowFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flights")).andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testShowAirlineFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_flights")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flights")).andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testShowFlight() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}", FlightControllerTests.TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/flightDetails"));
	}

	@WithMockUser(value = "airline1")
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

			.param("reference", "R-05").param("seats", "100").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "A-05, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
			.param("lands", "A-08, airport: Sevilla Airport, city: Sevilla").param("landDate", "2020-03-27").param("departDate", "2020-03-27")).andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-04").param("seats", "160").param("price", "120.0").param("flightStatus", "on_time").param("plane", "1").param("published", "1").param("departes", "1").param("lands", "2").param("landDate", "20123-06-06")
			.param("departDate", "20-06-06")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testInitUpdateFormAlreadyPublic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("seats", "170").param("price", "120.0").param("flightStatus", "1").param("plane", "V14-5").param("published", "1").param("departes", "1").param("lands", "2").param("landDate", "20-06-06").param("departDate", "20-06-06"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTests.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("seats", "180").param("price", "120.0").param("flightStatus", "1").param("plane", "1").param("published", "1").param("departes", "1").param("lands", "2").param("landDate", "20-06-06").param("departDate", "23435340-06-06"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

}
