
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
class RunwayControllerTestsE2E {

	private static final int TEST_RUNWAY_ID1 = 1; // Departes from Sevilla

	private static final int TEST_AIRPORT_ID1 = 1;// Sevilla

	
	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(value = "airline1"/*, authorities = { "airline" }*/)
	@Test
	void testShowRunwayList() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}/runways", TEST_AIRPORT_ID1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("runways", "airport")).andExpect(view().name("runways/runwayList"));
	}

	@Test
	void testUnauthorizedShowRunwayList() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}/runways", TEST_AIRPORT_ID1)).andExpect(status().is4xxClientError());
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
