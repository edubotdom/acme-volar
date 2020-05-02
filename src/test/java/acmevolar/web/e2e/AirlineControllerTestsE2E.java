
package acmevolar.web.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-mysql.properties")

class AirlineControllerTestsE2E {

	private static final int	TEST_AIRLINE_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(value = "anonymous")
	@Test
	void testAirlineList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airlines")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airlines"))
			.andExpect(MockMvcResultMatchers.view().name("airlines/airlinesList"));
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testShowAirline() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airlines/{airlineId}", AirlineControllerTestsE2E.TEST_AIRLINE_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airline"))
			.andExpect(MockMvcResultMatchers.view().name("airlines/airlineDetails"));
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airlines/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airline"))
			.andExpect(MockMvcResultMatchers.view().name("airlines/createAirlineForm"));
	}

	@WithMockUser(value = "anonymous")
	@ParameterizedTest
	@CsvSource({
		"Betis Airways, 61335444-N, Spain, 666333111, airline@gmail.com, 2011/04/17, SEO-001", "Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013/04/17, SEO-051",
	})
	void testProcessCreationFormSuccess(final String name, final String identification, final String country, final String phone, final String email, final String creationDate, final String reference) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airlines/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("identification", identification).param("country", country).param("phone", phone).param("email", email)
				.param("creationDate", creationDate).param("reference", reference))

			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airlines/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("name", "Betis Airways").param("identification", "61335444-N").param("country", "Spain").param("phone", "666333111").param("email", "airline@gmail.com").param("creationDate", "2011/04/17").param("reference", "SEO-001"))

			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airlines/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "").param("identification", "61335444-N").param("country", "Spain").param("phone", "666333111")
				.param("email", "airline@gmail.com").param("creationDate", "2011/03/27").param("reference", "SEO-001"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airline")).andExpect(MockMvcResultMatchers.view().name("airlines/createAirlineForm"));
	}

	@WithMockUser(value = "anonymous")
	@ParameterizedTest
	@CsvSource({
		"Fecha sin barra, 61335444-N, Spain, 666333111, airline@gmail.com, 2011-04-17, SEO-001", "Montellano Airways, , Spain, 669363111, airline2@gmail.com, 2013/04/17, SEO-051",
		"Betis Airways, 61335444-N, , 666333111, airline@gmail.com, 2011/04/17, SEO-001", "Montella Airways, 61835494-N, Spain, , airline2@gmail.com, 2013/04/17, SEO-051", "Betis Airways, 61335444-N, Spain, 666333111, , 2011/04/17, SEO-001",
		"Montella Airways, 61835494-N, Spain, 669363111, airline2@gmail.com, 2013/04/17, ",
	})
	void testProcessCreationFormHasErrors(final String name, final String identification, final String country, final String phone, final String email, final String creationDate, final String reference) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airlines/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("identification", identification).param("country", country).param("phone", phone).param("email", email)
				.param("creationDate", creationDate).param("reference", reference))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airline")).andExpect(MockMvcResultMatchers.view().name("airlines/createAirlineForm"));
	}

}
