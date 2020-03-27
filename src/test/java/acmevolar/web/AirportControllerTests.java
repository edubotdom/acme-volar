package acmevolar.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
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
import acmevolar.model.Airport;
import acmevolar.model.Flight;
import acmevolar.model.Plane;
import acmevolar.model.api.Clouds;
import acmevolar.model.api.Coord;
import acmevolar.model.api.Forecast;
import acmevolar.model.api.Main;
import acmevolar.model.api.Sys;
import acmevolar.model.api.Wind;
import acmevolar.service.AirportService;
import acmevolar.service.ForecastService;
import acmevolar.service.RunwayService;
import reactor.core.publisher.Mono;

@WebMvcTest(value = AirportController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AirportControllerTests {

	private static final int TEST_AIRPORT_ID = 1;

	@Autowired
	protected AirportController airportController;

	@MockBean
	private AirportService airportService;

	//@Autowired
	@MockBean
	private ForecastService forecastService;
	
	@MockBean
	private Mono<Forecast> mono;

	@MockBean
	protected RunwayService runwayService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Airport a1 = new Airport(); //'Sevilla Airport', 50, 600, 37.4180000, -5.8931100, 'SVQ', 'Sevilla'

		a1.setId(1);
		a1.setName("Sevilla Airport");
		a1.setMaxNumberOfPlanes(50);
		a1.setMaxNumberOfClients(600);
		a1.setLatitude(37.4180000);
		a1.setLongitude(-5.8931100);
		a1.setCode("SVQ");
		a1.setCity("Sevilla");

		given(this.airportService.findAirportById(AirportControllerTests.TEST_AIRPORT_ID)).willReturn(a1);
		
		
		Forecast f1 = new Forecast();
		f1.setBase("base");
		
		Clouds c = new Clouds();
		c.setAll(0);
		Coord co = new Coord();
		co.setLat(0);
		co.setLon(0);
		Main m = new Main();
		m.setFeels_like(0);
		m.setHumidity(0);
		m.setPressure(0);
		m.setTemp(0);
		m.setTemp_max(0);
		m.setTemp_min(0);
		Sys s = new Sys();
		s.setCountry("country");
		s.setSunrise(0);
		s.setSunset(0);
		s.setType(0);
		Wind w = new Wind();
		w.setDeg(0);
		w.setSpeed(0);
	
		f1.setClouds(c);
		f1.setCod(0);
		f1.setCoord(co);
		f1.setDt(0);
		f1.setMain(m);
		f1.setName("name");
		f1.setSys(s);
		f1.setTimezone(0);
		f1.setVisibility(0);
		f1.setWind(w);
		
		given(this.forecastService.searchForecastByCity(a1.getCity())).willReturn(mono);
		
		given(this.forecastService.searchForecastByCity(a1.getCity()).block()).willReturn(f1);
		

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAirportList() throws Exception {
		this.mockMvc.perform(get("/airports")).andExpect(status().isOk()).andExpect(model().attributeExists("airports"))
				.andExpect(view().name("airports/airportList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowPlane() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}", AirportControllerTests.TEST_AIRPORT_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("airport")).andExpect(view().name("airports/airportDetails"));
	}

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

				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "50")
				.param("maxNumberOfClients", "600").param("latitude", "37.4180000").param("longitude", "-5.8931100")
				.param("code", "SVQ").param("city", "Sevilla")).andExpect(status().is3xxRedirection());;
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
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Sevilla Airports").param("maxNumberOfPlanes", "201")
				.param("maxNumberOfClients", "100").param("latitude", "11.98").param("longitude", "78.987")
				.param("code", "VBA").param("city", "Madrid")).andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Betis Airport")
				.param("code", "DEP").param("latitude", "190.345")).andExpect(model().attributeHasErrors("airport"))
				.andExpect(status().isOk()).andExpect(view().name("airports/createAirportForm"));
	}

}
