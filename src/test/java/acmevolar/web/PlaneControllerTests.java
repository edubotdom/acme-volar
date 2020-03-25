
package acmevolar.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.service.AirlineService;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

@WebMvcTest(value = PlaneController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlaneControllerTests {

	private static final int	TEST_PLANE_ID	= 1;

	@Autowired
	private PlaneController		planeController;

	@MockBean
	private PlaneService		planeService;

	@MockBean
	protected FlightService		flightService;

	@MockBean
	protected AirlineService	airlineService;

	@Autowired
	private MockMvc				mockMvc;


	/*
	 * @BeforeEach void setup() { Airline a1 =
	 * this.airlineService.findAirlineById(1); Flight f1 =
	 * this.flightService.findFlightById(1);
	 *
	 * Plane plane = new Plane(); plane.setAirline(a1);
	 * plane.setDescription("Mock description"); plane.setFlightsInternal(new
	 * HashSet<Flight>());
	 * plane.setLastMaintenance(java.util.Date.from(Instant.now().minusSeconds(1)));
	 * plane.setManufacter("Boeing"); plane.setMaxDistance(45000.);
	 * plane.setMaxSeats(300); plane.setModel("Renton 737");
	 * plane.setNumberOfKm(34200.); plane.setReference("REF1");
	 *
	 * f1.setPlane; Assertions.assertThat(f1.getPlane()).isEqualTo;
	 *
	 * this.planeService.savePlane;
	 *
	 * this.flightService.saveFlight(f1);
	 *
	 * // checks that id has been generated
	 * Assertions.assertThat(plane.getId()).isNotNull(); }
	 */

	@BeforeEach
	void setup() {
		given(this.planeService.findPlaneById(PlaneControllerTests.TEST_PLANE_ID)).willReturn(new Plane());
		given(this.flightService.findAirlineByUsername("airline1")).willReturn(new Airline());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowPlaneList() throws Exception {
		this.mockMvc.perform(get("/planes")).andExpect(status().isOk()).andExpect(model().attributeExists("planes")).andExpect(view().name("planes/planesList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowMyPlaneList() throws Exception {
		this.mockMvc.perform(get("/my_planes")).andExpect(status().isOk()).andExpect(model().attributeExists("planes")).andExpect(view().name("planes/planesList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowPlane() throws Exception {
		this.mockMvc.perform(get("/planes/{planeId}", PlaneControllerTests.TEST_PLANE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/planeDetails"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/planes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/planes/new").with(csrf())

			.param("reference", "reference").param("maxSeats", "200").param("description", "description").param("manufacter", "manufacter").param("model", "model").param("numberOfKm", "100").param("maxDistance", "500")
			.param("lastMaintenance", "2011-04-17"))

			.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/planes/{planeId}/edit", TEST_PLANE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf())
			.param("reference", "reference2").param("maxSeats", "200").param("description", "description2").param("manufacter", "manufacter2").param("model", "model2").param("numberOfKm", "100").param("maxDistance", "500")
			.param("lastMaintenance", "2011-04-17"))

			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/planes/{planeId}"));
	}

	/**
	 * Buscar un vuelo no disponible y que el sistema no permita consultar la
	 * información de su avión.
	 *
	 * @throws Exception
	 */

	/*
	 * @WithMockUser(username = "airline1", value = "airline1", authorities = {
	 * "airline" })
	 *
	 * @Test void shouldNotFindPlaneInformationByFlight() throws Exception { //no
	 * modelandview found
	 * this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}",
	 * PlaneControllerTests.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status()
	 * .isOk())/ .andExpect(MockMvcResultMatchers.model().attributeExists("plane"))
	 */
	// .andExpect(MockMvcResultMatchers.view().name("planes/planeDetails")) /;

	/*
	 * .andExpect(MockMvcResultMatchers.model().attribute("plane",
	 * Matchers.hasProperty("reference",
	 * Matchers.is("V14-5")))).andExpect(MockMvcResultMatchers.model().attribute(
	 * "plane", Matchers.hasProperty("max_seats", Matchers.is(150))))
	 * .andExpect(MockMvcResultMatchers.model().attribute("plane",
	 * Matchers.hasProperty("description", Matchers.is("This is a description"))))
	 * .andExpect(MockMvcResultMatchers.model().attribute("plane",
	 * Matchers.hasProperty("last_maintenance",
	 * Matchers.is("2011-04-17 00:00:00.0")))).andExpect(MockMvcResultMatchers.view(
	 * ).name("planes/planeDetails"));
	 *
	 * }
	 */

	// HU-T 16 Feature: Trabajador registra información de aviones
	/**
	 * Registro información sobre un avión y posteriormente puedo consultar esta
	 * información.
	 *
	 * @throws Exception
	 */

	// @WithMockUser(value = "airline1")

	// @Test
	// void createPlaneAndGetItsInformation() throws Exception {
	// //model attribute 'plane' does not exist
	// //java.lang.AssertionError: Range for response status value 200
	// expected:<REDIRECTION> but was:<SUCCESSFUL>
	// this.mockMvc
	// .perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference",
	// "reference").param("maxSeats", "200").param("description",
	// "description").param("manufacter", "manufacter")
	// .param("model", "model").param("numberOfKm", "100").param("maxDistance",
	// "500").param("lastMaintenance", "2011-04-17"))
	// //.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	//
	// //.andExpect(MockMvcResultMatchers.view().name("redirect:/planes/{planeId}"));
	//
	// //.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("planes/planeDetails"))
	//
	// .andExpect(MockMvcResultMatchers.model().attributeExists("plane"));
	//
	// //this.mockMvc.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf())/
	// .param("name", "Betty").param("type", "hamster").param("birthDate",
	// "2015/02/12") /)
	// //
	// .andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/planes/{planeId}"));
	// }

}
