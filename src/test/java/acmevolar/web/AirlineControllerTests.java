
package acmevolar.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;

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
import org.springframework.test.web.servlet.MockMvc;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.User;
import acmevolar.service.AirlineService;
import acmevolar.service.UserService;

@WebMvcTest(value = AirlineController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AirlineControllerTests {

	private static final int	TEST_AIRLINE_ID	= 1;

	@Autowired
	private AirlineController	airlineController;

	@MockBean
	protected UserService		userService;

	@MockBean
	protected AirlineService	airlineService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {

		User user1 = new User();
		user1.setUsername("airline1");
		user1.setPassword("airline1");
		user1.setEnabled(true);

		given(this.userService.findUserById("airline1").get()).willReturn(user1);

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
		airline1.setFlightsInternal(new HashSet<>());
		airline1.setPlanesInternal(new HashSet<>());
		given(this.airlineService.findAirlineById(1)).willReturn(airline1);
	}

	@Test
	void testAirlineList() throws Exception {
		this.mockMvc.perform(get("/airlines")).andExpect(status().isOk()).andExpect(model().attributeExists("airlines")).andExpect(view().name("airlines/airlinesList"));
	}

	@Test
	void testShowAirline() throws Exception {
		this.mockMvc.perform(get("/airlines/{airlineId}", AirlineControllerTests.TEST_AIRLINE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("airline")).andExpect(view().name("airlines/airlineDetails"));
	}

	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airlines/new")).andExpect(status().isOk()).andExpect(model().attributeExists("airline")).andExpect(view().name("airlines/createAirlineForm"));
	}

	@ParameterizedTest
	@CsvSource({
		"Betis Airways, 61335444-N, Spain, 666333111, airline@gmail.com, 2011-04-17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013-04-17, SEO-051",
	})
	void testProcessCreationFormSuccess(String name, String identification, String country, String phone, String email, String creationDate, String reference) throws Exception {
		this.mockMvc
			.perform(post("/airlines/new").with(csrf())
				.param("name", name)
				.param("identification", identification)
				.param("country", country)
				.param("phone", phone)
				.param("email", email)
				.param("creationDate", creationDate)
				.param("reference", reference))

			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/planes/new").with(csrf())

			.param("name", "Betis Airways").param("identification", "61335444-N")
			.param("country", "Spain").param("phone", "666333111").param("email", "airline@gmail.com")
			.param("creationDate", "2011-04-17").param("reference", "SEO-001"))

			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/planes/new").with(csrf()).param("name", "").param("identification", "61335444-N")
			.param("country", "Spain").param("phone", "666333111").param("email", "airline@gmail.com")
			.param("creationDate", "2011-04-17").param("reference", "SEO-001")).andExpect(model().attributeHasErrors("airline")).andExpect(view().name("airlines/createAirlineForm"));
	}

	@ParameterizedTest
	@CsvSource({
		", 61335444-N, Spain, 666333111, airline@gmail.com, 2011-04-17, SEO-001", 
		"Montella Airways, , Spain, 669363111, airline2@gmail.com, 2013-04-17, SEO-051",
		"Betis Airways, 61335444-N, , 666333111, airline@gmail.com, 2011-04-17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, , airline2@gmail.com, 2013-04-17, SEO-051",
		"Betis Airways, 61335444-N, Spain, 666333111, , 2011-04-17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013-04-17, ",
	})
	void testProcessCreationFormHasErrors(String name, String identification, String country, String phone, String email, String creationDate, String reference) throws Exception {
		this.mockMvc
			.perform(post("/planes/new").with(csrf())
				.param("name", name)
				.param("identification", identification)
				.param("country", country)
				.param("phone", phone)
				.param("email", email)
				.param("creationDate", creationDate)
				.param("reference", reference))

			.andExpect(model().attributeHasErrors("airline")).andExpect(view().name("airlines/createAirlineForm"));
	}

}
