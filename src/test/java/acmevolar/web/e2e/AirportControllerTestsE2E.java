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
public class AirportControllerTestsE2E {

	private static final int TEST_AIRPORT_ID = 1;

	@Autowired
	private MockMvc mockMvc;	
	
	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testShowAirportList() throws Exception {
		this.mockMvc.perform(get("/airports")).andExpect(status().isOk()).andExpect(model().attributeExists("airports"))
				.andExpect(view().name("airports/airportList"));
	}

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testShowAirport() throws Exception {
		this.mockMvc.perform(get("/airports/{airportId}", AirportControllerTestsE2E.TEST_AIRPORT_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("airport")).andExpect(view().name("airports/airportDetails"));
	}

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/airports/new")).andExpect(status().isOk())
				.andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/airports/new").with(csrf())

				.param("name", "Sevilla Airport 2").param("maxNumberOfPlanes", "50")
				.param("maxNumberOfClients", "600").param("latitude", "37.4180000").param("longitude", "-5.8931100")
				.param("code", "SVS").param("city", "Sevilla")).andExpect(status().is3xxRedirection());;
	}
	
	@WithMockUser(username="airline1",authorities= {"airline"})
	@ParameterizedTest 
	@CsvSource({
	    "Madrid Airport, 35, 500, 55.55, 49.112, MAC, Madrid",
	    "Tongoliki Airport, 25, 350, 72.10, 87.123, ATC, Togoliki Menor",
	    "Chicago Airport, 40, 650, 82.92, -73.9012, CKP, Chicago",
	    "Arellano Airport, 1, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
	}) 
	void testProcessCreationFormSuccess(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
			String longitude, String code, String city) throws Exception {    
		this.mockMvc.perform(post("/airports/new").with(csrf())
				.param("name", name)
				.param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude)
				.param("longitude", longitude)
				.param("code", code)
				.param("city", city))

				.andExpect(status().is3xxRedirection());
	} 

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/airports/new").with(csrf())

				.param("name", "Sevilla Airport").param("maxNumberOfPlanes", "200")
				.param("maxNumberOfClients", "description").param("latitude", "190.123").param("longitude", "78.987")
				.param("code", "VGA").param("city", "Sevilla")).andExpect(model().attributeHasErrors("airport"))
				.andExpect(view().name("airports/createAirportForm"));
	}
	
	@WithMockUser(username="airline1",authorities= {"airline"})
	@ParameterizedTest 
	@CsvSource({
	    "Madrid Airport, 35, 500, -100.89, 49.112, MAC, Madrid",
	    "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor",
	    "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago",
	    "Arellano Airport, -1000, 1, -1.1111111, 1.1111111, EGD, La casa de Dani",
	}) 
	void testProcessCreationFormHasErrors(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
			String longitude, String code, String city) throws Exception {    
		this.mockMvc.perform(post("/airports/new").with(csrf())
				.param("name", name)
				.param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude)
				.param("longitude", longitude)
				.param("code", code)
				.param("city", city))
		
				.andExpect(model().attributeHasErrors("airport"))
				.andExpect(view().name("airports/createAirportForm"));
	} 

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/airports/{airportId}/edit", TEST_AIRPORT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("airport")).andExpect(view().name("airports/createAirportForm"));
	}

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
				.param("name", "Sevilla Airports").param("maxNumberOfPlanes", "201")
				.param("maxNumberOfClients", "100").param("latitude", "11.98").param("longitude", "78.987")
				.param("code", "VBA").param("city", "Madrid"))
		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/airports/{airportId}"));
	}
	
	@WithMockUser(username="airline1",authorities= {"airline"})
	@ParameterizedTest 
	@CsvSource({
	    "Madrid Airport 2, 35, 500, 55.55, 49.112, MAA, Madrid",
	    "Tongoliki Airport 2, 25, 350, 72.10, 87.123, ATX, Togoliki Menor",
	    "Chicago Airport 2, 40, 650, 82.92, -73.9012, CKL, Chicago",
	    "Arellano Airport 2, 1, 1, -1.1111111, 1.1111111, EAZ, La casa de Dani",
	}) 
	void testProcessUpdateFormSuccess(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
			String longitude, String code, String city) throws Exception {    
		this.mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
				.param("name", name)
				.param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude)
				.param("longitude", longitude)
				.param("code", code)
				.param("city", city))

				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/airports/{airportId}"));
	} 

	@WithMockUser(username="airline1",authorities= {"airline"})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf()).param("name", "Betis Airport")
				.param("code", "DEP").param("latitude", "190.345")).andExpect(model().attributeHasErrors("airport"))
				.andExpect(status().isOk()).andExpect(view().name("airports/createAirportForm"));
	}
	
	@WithMockUser(username="airline1",authorities= {"airline"})
	@ParameterizedTest 
	@CsvSource({
		"123, 35, -2, 10.89, 49.112, MAC, Madrid",
	    "Tongoliki Airport, 25, 350, 72.10, 200.9172, ATC, Togoliki Menor",
	    "Chicago Airport, 40, -12, 82.92, -73.9012, CKP, Chicago",
	    "Arellano Airport, 0, 1, -1.1111111, 1.1111111, 123, La casa de Dani",
	}) 
	void testProcessUpdateFormHasErrors(String name, String maxNumberOfPlanes, String maxNumberOfClients, String latitude, 
			String longitude, String code, String city) throws Exception {    
		this.mockMvc.perform(post("/airports/{airportId}/edit", TEST_AIRPORT_ID).with(csrf())
				.param("name", name)
				.param("maxNumberOfPlanes", maxNumberOfPlanes)
				.param("maxNumberOfClients", maxNumberOfClients)
				.param("latitude", latitude)
				.param("longitude", longitude)
				.param("code", code)
				.param("city", city))
				
				.andExpect(model().attributeHasErrors("airport"))
				.andExpect(view().name("airports/createAirportForm"));
	} 

}
