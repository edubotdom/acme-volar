package acmevolar.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
import java.util.HashSet;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sun.el.parser.ParseException;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Flight;
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


	@BeforeEach
	void setup() throws ParseException {
		Plane p1 = new Plane();
		p1.setAirline(new Airline());

		p1.setFlightsInternal(new HashSet<Flight>());
		p1.setId(1);
		p1.setReference("V14-5");
		p1.setMaxSeats(150);
		p1.setDescription("This is a description");
		p1.setManufacter("manufacturer");
		p1.setModel("model");
		p1.setNumberOfKm(100.);
		p1.setMaxDistance(200.);
		p1.setLastMaintenance(Date.from(Instant.parse("2011-04-17T00:00:00.00Z")));

		given(this.planeService.findPlaneById(PlaneControllerTests.TEST_PLANE_ID)).willReturn(p1);
		given(this.flightService.findAirlineByUsername("airline1")).willReturn(new Airline());
		
		List<Plane> aviones = new ArrayList<Plane>();
		aviones.add(p1);
		given(this.flightService.findPlanesbyAirline("airline1")).willReturn(aviones);
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowPlaneList() throws Exception {
		this.mockMvc.perform(get("/planes")).andExpect(status().isOk()).andExpect(model().attributeExists("planes")).andExpect(view().name("planes/planesList"));
	}
	
	@Test
	void testShowPlaneListUnauthorized() throws Exception {
		this.mockMvc.perform(get("/planes")).andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowMyPlaneList() throws Exception {
		this.mockMvc.perform(get("/my_planes")).andExpect(status().isOk()).andExpect(model().attributeExists("planes")).andExpect(view().name("planes/planesList"));
	}

	@Test
	void testShowMyPlaneListUnauthorized() throws Exception {
		this.mockMvc.perform(get("/my_planes")).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowPlane() throws Exception {
		this.mockMvc.perform(get("/planes/{planeId}", PlaneControllerTests.TEST_PLANE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/planeDetails"));
	}

	@Test
	void testShowPlaneUnauthorized()throws Exception {
		this.mockMvc.perform(get("/planes/{planeId}", PlaneControllerTests.TEST_PLANE_ID)).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(username = "airline1", value = "airline1", authorities = {
		"airline"
	})
	@Test
	void shouldFindPlaneInformation() throws Exception {
		this.mockMvc.perform(get("/planes/{planeId}", PlaneControllerTests.TEST_PLANE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(model().attribute("plane", hasProperty("reference", is("V14-5"))))
			.andExpect(model().attribute("plane", hasProperty("maxSeats", is(150)))).andExpect(model().attribute("plane", hasProperty("description", is("This is a description")))).andExpect(model().attribute("plane", hasProperty("lastMaintenance")))
			.andExpect(view().name("planes/planeDetails"));

	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/planes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/createPlaneForm"));
	}
	
	@Test
	void testInitCreationFormUnauthorized() throws Exception {
		this.mockMvc.perform(get("/planes/new")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, 500, 2011-04-17", "reference2, 300, description2, manufacturer2, model2, 200, 600, 2012-05-18", "reference3, 400, description3, manufacturer3, model3, 300, 700, 2013-06-19",
		"reference4, 500, description4, manufacturer4, model4, 400, 800, 2014-07-20",
	})
	void testProcessCreationFormSuccess(String reference, String maxSeats, String description, String manufacturer, String model, String numberOfKm, String maxDistance, String lastMaintenance) throws Exception {
		this.mockMvc
			.perform(post("/planes/new").with(csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer).param("model", model).param("numberOfKm", numberOfKm)
				.param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))

			.andExpect(status().is3xxRedirection());
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
	
	@Test
	void testProcessCreationFormUnauthorized() throws Exception {
		this.mockMvc.perform(post("/planes/new").with(csrf())

			.param("reference", "reference").param("maxSeats", "200").param("description", "description").param("manufacter", "manufacter").param("model", "model").param("numberOfKm", "100").param("maxDistance", "500")
			.param("lastMaintenance", "2011-04-17"))

			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/planes/new").with(csrf()).param("reference", "reference").param("maxSeats", "-200").param("description", "description").param("manufacter", "manufacter").param("model", "model").param("numberOfKm", "100")
			.param("maxDistance", "500").param("lastMaintenance", "2011-04-17")).andExpect(model().attributeHasErrors("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, -500, 2011-04-17", "reference2, 300, 200, manufacturer2, model2, -200, 600, 2012-05-18", "75, 400, 200, 100, 0, 300, 700, FECHA",
		"reference4, -500, description4, manufacturer4, model4, -400, -800, -2014-07-20",
	})
	void testProcessCreationFormHasErrors(String reference, String maxSeats, String description, String manufacturer, String model, String numberOfKm, String maxDistance, String lastMaintenance) throws Exception {
		this.mockMvc
			.perform(post("/planes/new").with(csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer).param("model", model).param("numberOfKm", numberOfKm)
				.param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))

			.andExpect(model().attributeHasErrors("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/planes/{planeId}/edit", TEST_PLANE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("plane")).andExpect(view().name("planes/createPlaneForm"));
	}
	
	@Test
	void testInitUpdateFormUnauthorized() throws Exception {
		mockMvc.perform(get("/planes/{planeId}/edit", TEST_PLANE_ID)).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, 500, 2011-04-17", "reference2, 300, description2, manufacturer2, model2, 200, 600, 2012-05-18", "reference3, 400, description3, manufacturer3, model3, 300, 700, 2013-06-19",
		"reference4, 500, description4, manufacturer4, model4, 400, 800, 2014-07-20",
	})
	void testProcessUpdateFormSuccess(String reference, String maxSeats, String description, String manufacturer, String model, String numberOfKm, String maxDistance, String lastMaintenance) throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer).param("model", model)
			.param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance)).andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf()).param("reference", "reference2").param("maxSeats", "200").param("description", "description2").param("manufacter", "manufacter2").param("model", "model2")
			.param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2011-04-17")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/planes/{planeId}"));
	}
	
	@Test
	void testProcessUpdateFormUnauthorized() throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf()).param("reference", "reference2").param("maxSeats", "200").param("description", "description2").param("manufacter", "manufacter2").param("model", "model2")
			.param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2011-04-17")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, -500, 2011-04-17", "reference2, 300, 200, manufacturer2, model2, -200, 600, 2012-05-18", "75, 400, 200, 100, 0, 300, 700, FECHA",
		"reference4, -500, description4, manufacturer4, model4, -400, -800, -2014-07-20",
	})
	void testProcessUpdateFormHasErrors(String reference, String maxSeats, String description, String manufacturer, String model, String numberOfKm, String maxDistance, String lastMaintenance) throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer).param("model", model)
			.param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance)).andExpect(model().attributeHasErrors("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/planes/{planeId}/edit", TEST_PLANE_ID).with(csrf()).param("reference", "reference").param("maxSeats", "-200").param("description", "description2").param("manufacter", "manufacter2").param("model", "model2")
			.param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2019-04-17")).andExpect(model().attributeHasErrors("plane")).andExpect(view().name("planes/createPlaneForm"));
	}

}
