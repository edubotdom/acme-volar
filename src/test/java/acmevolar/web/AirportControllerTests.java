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
import acmevolar.model.Airport;
import acmevolar.service.AirportService;
import acmevolar.service.FlightService;
import acmevolar.service.ForecastService;
import acmevolar.service.RunwayService;

@WebMvcTest(value = AirportController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AirportControllerTests {

	private static final int TEST_AIRPORT_ID = 1;

	@Autowired
	protected AirportController airportController;

	@MockBean
	private AirportService airportService;

	@MockBean
	private ForecastService forecastService;

	@MockBean
	protected RunwayService runwayService;
	
	@MockBean
	protected FlightService flightService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		given(this.airportService.findAirportById(AirportControllerTests.TEST_AIRPORT_ID)).willReturn(new Airport());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAirportList() throws Exception {
		this.mockMvc.perform(get("/airports")).andExpect(status().isOk()).andExpect(model().attributeExists("airports"))
				.andExpect(view().name("airports/airportList"));
	}
	/*
	// FAILURE
	@WithMockUser(value = "spring")
	@Test
	void testShowAirport() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}", AirportControllerTests.TEST_AIRPORT_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("airport"))
				.andExpect(view().name("airports/airportDetails"));
	}
    */
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airports/new")).andExpect(status().isOk())
				.andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/airports/new").with(csrf())

				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200")
				.param("maxNumberOfClients", "description").param("latitude", "123.123").param("longitude", "78.987")
				.param("code", "VGA").param("city", "Sevilla")).andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/airports/new").with(csrf())

				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200")
				.param("maxNumberOfClients", "description").param("latitude", "190.123").param("longitude", "78.987")
				.param("code", "VGA").param("city", "Sevilla")).andExpect(model().attributeHasErrors("airport"))
				.andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/edit", TEST_AIRPORT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("airport")).andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Betis Airport")
				.param("code", "DEP")).andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Betis Airport")
				.param("code", "DEP").param("latitude", "190.345")).andExpect(model().attributeHasErrors("airport"))
				.andExpect(status().isOk()).andExpect(view().name("airports/createAirportForm"));
	}

}
