
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

public class AirportControllerTestsE2E {

	private static final int	TEST_AIRPORT_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowAirportList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airports"))
			.andExpect(MockMvcResultMatchers.view().name("airports/airportList"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowAirport() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}", 4)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airport"))
			.andExpect(MockMvcResultMatchers.view().name("airports/airportDetails"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("name", "Sevilla Airport 2").param("maxNumberOfPlanes", "50").param("maxNumberOfClients", "600").param("latitude", "37.4180000").param("longitude", "-5.8931100").param("code", "SVS").param("city", "Sevilla"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		;
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"Madrid Airport, 35, 500, 55.55, 49.112, MAC, Madrid", "Tongoliki Airport, 25, 350, 72.10, 87.123, ATC, Togoliki Menor", "Chicago Airport, 40, 650, 82.92, -73.9012, CKP, Chicago",
		"Arellano Airport, 1, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
	})
	void testProcessCreationFormSuccess(final String name, final String maxNumberOfPlanes, final String maxNumberOfClients, final String latitude, final String longitude, final String code, final String city) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("maxNumberOfPlanes", maxNumberOfPlanes).param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude).param("longitude", longitude).param("code", code).param("city", city))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/airports/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200").param("maxNumberOfClients", "description").param("latitude", "190.123").param("longitude", "78.987").param("code", "VGA").param("city", "Sevilla"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"Madrid Airport, 35, 500, -100.89, 49.112, MAC, Madrid", "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor", "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago",
		"Arellano Airport, -1000, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
	})
	void testProcessCreationFormHasErrors(final String name, final String maxNumberOfPlanes, final String maxNumberOfClients, final String latitude, final String longitude, final String code, final String city) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("maxNumberOfPlanes", maxNumberOfPlanes).param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude).param("longitude", longitude).param("code", code).param("city", city))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/airports/{airportId}/edit", AirportControllerTestsE2E.TEST_AIRPORT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("airport"))
			.andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/{airportId}/edit", AirportControllerTestsE2E.TEST_AIRPORT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Sevilla Airports").param("maxNumberOfPlanes", "201")
				.param("maxNumberOfClients", "100").param("latitude", "11.98").param("longitude", "78.987").param("code", "VBA").param("city", "Madrid"))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/airports/{airportId}"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"Madrid Airport 2, 35, 500, 55.55, 49.112, MAA, Madrid", "Tongoliki Airport 2, 25, 350, 72.10, 87.123, ATX, Togoliki Menor", "Chicago Airport 2, 40, 650, 82.92, -73.9012, CKL, Chicago",
		"Arellano Airport 2, 1, 1, -1.1111111, 1.1111111, EAZ, La casa de Dani",
	})
	void testProcessUpdateFormSuccess(final String name, final String maxNumberOfPlanes, final String maxNumberOfClients, final String latitude, final String longitude, final String code, final String city) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/{airportId}/edit", AirportControllerTestsE2E.TEST_AIRPORT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients).param("latitude", latitude).param("longitude", longitude).param("code", code).param("city", city))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/airports/{airportId}"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/{airportId}/edit", AirportControllerTestsE2E.TEST_AIRPORT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Betis Airport").param("code", "DEP").param("latitude", "190.345"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

	@WithMockUser(username = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"123, 35, -2, 10.89, 49.112, MAC, Madrid", "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor", "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago", "Arellano Airport, 0, 1, -1.1111111, 1.1111111, 123, La casa de Dani",
	})
	void testProcessUpdateFormHasErrors(final String name, final String maxNumberOfPlanes, final String maxNumberOfClients, final String latitude, final String longitude, final String code, final String city) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/airports/{airportId}/edit", AirportControllerTestsE2E.TEST_AIRPORT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients).param("latitude", latitude).param("longitude", longitude).param("code", code).param("city", city))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("airport")).andExpect(MockMvcResultMatchers.view().name("airports/createAirportForm"));
	}

}
