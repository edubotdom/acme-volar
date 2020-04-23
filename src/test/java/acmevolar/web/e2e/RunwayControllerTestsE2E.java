
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RunwayControllerTestsE2E {

	private static final int	TEST_RUNWAY_ID1		= 1; // Departes from Sevilla

	private static final int	TEST_AIRPORT_ID1	= 1;// Sevilla

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowRunwayList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways", RunwayControllerTestsE2E.TEST_AIRPORT_ID1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("runways", "airport")).andExpect(MockMvcResultMatchers.view().name("runways/runwayList"));
	}

	@Test
	void testUnauthorizedShowRunwayList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways", RunwayControllerTestsE2E.TEST_AIRPORT_ID1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("runway", "airport")).andExpect(MockMvcResultMatchers.view().name("runways/createOrUpdateRunwaysForm"));
	}

	@Test
	void testUnauthorizedInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Runway Example").param("runwayType", "landing"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	void testUnauthorizedProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Runway Example").param("runwayType", "landing"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"runway1, landing", "runway2, take_off", "runway3, landing", "runway4, take_off",
	})
	void testProcessCreationFormSuccess(final String name, final String runwayType) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("runwayType", runwayType))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/new", RunwayControllerTestsE2E.TEST_AIRPORT_ID1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "").param("runwayType", "take_off"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("runway")).andExpect(MockMvcResultMatchers.view().name("runways/createOrUpdateRunwaysForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/{runwayId}/edit", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("runway", "runwayTypes")).andExpect(MockMvcResultMatchers.view().name("runways/createOrUpdateRunwaysForm"));
	}

	@Test
	void testUnauthorizedInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/{runwayId}/edit", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/{runwayId}/edit", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1).with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("name", "Runway Example").param("runwayType", "landing")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@Test
	void testUnauthorizedProcessUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/{airportId}/runways/{runwayId}/edit", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testDeleteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/{runwayId}/delete", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testDeleteHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/{runwayId}/delete", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, 2)).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	void testDeleteUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/runways/{runwayId}/delete", RunwayControllerTestsE2E.TEST_AIRPORT_ID1, RunwayControllerTestsE2E.TEST_RUNWAY_ID1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
