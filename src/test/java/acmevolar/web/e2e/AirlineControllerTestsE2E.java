
package acmevolar.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AirlineControllerTestsE2E {

	private static final int	TEST_AIRLINE_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;

	@WithMockUser(value = "anonymous")
	@Test
	void testAirlineList() throws Exception {
		this.mockMvc.perform(get("/airlines")).andExpect(status().isOk()).andExpect(model().attributeExists("airlines")).andExpect(view().name("airlines/airlinesList"));
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testShowAirline() throws Exception {
		this.mockMvc.perform(get("/airlines/{airlineId}", AirlineControllerTestsE2E.TEST_AIRLINE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("airline")).andExpect(view().name("airlines/airlineDetails"));
	}
	
	@WithMockUser(value = "anonymous")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airlines/new")).andExpect(status().isOk()).andExpect(model().attributeExists("airline")).andExpect(view().name("airlines/createAirlineForm"));
	}

	@WithMockUser(value = "anonymous")
	@ParameterizedTest
	@CsvSource({
		"Betis Airways, 61335444-N, Spain, 666333111, airline@gmail.com, 2011/04/17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013/04/17, SEO-051",
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

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/airlines/new").with(csrf())

			.param("name", "Betis Airways").param("identification", "61335444-N")
			.param("country", "Spain").param("phone", "666333111").param("email", "airline@gmail.com")
			.param("creationDate", "2011/04/17").param("reference", "SEO-001"))

			.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/airlines/new").with(csrf()).param("name", "").param("identification", "61335444-N")
			.param("country", "Spain").param("phone", "666333111").param("email", "airline@gmail.com")
			.param("creationDate", "2011/03/27").param("reference", "SEO-001")).andExpect(model().attributeHasErrors("airline")).andExpect(view().name("airlines/createAirlineForm"));
	}

	@WithMockUser(value = "anonymous")
	@ParameterizedTest
	@CsvSource({
		"Fecha sin barra, 61335444-N, Spain, 666333111, airline@gmail.com, 2011-04-17, SEO-001", 
		"Montellano Airways, , Spain, 669363111, airline2@gmail.com, 2013/04/17, SEO-051",
		"Betis Airways, 61335444-N, , 666333111, airline@gmail.com, 2011/04/17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, , airline2@gmail.com, 2013/04/17, SEO-051",
		"Betis Airways, 61335444-N, Spain, 666333111, , 2011/04/17, SEO-001", 
		"Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013/04/17, ",
	})
	void testProcessCreationFormHasErrors(String name, String identification, String country, String phone, String email, String creationDate, String reference) throws Exception {
		this.mockMvc
			.perform(post("/airlines/new").with(csrf())
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
