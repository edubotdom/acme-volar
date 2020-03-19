
package acmevolar.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Plane;
import acmevolar.service.AirlineService;
import acmevolar.service.PlaneService;

@WebMvcTest(value = PlaneController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlaneControllerTests {

	private static final int	TEST_PLANE_ID	= 1;
	private static final int	TEST_AIRLINE_ID	= 1;

	@Autowired
	private PlaneController		planeController;

	@MockBean
	private PlaneService		planeService;

	@MockBean
	private AirlineService		airlineService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {
		BDDMockito.given(this.airlineService.findAirlineById(PlaneControllerTests.TEST_AIRLINE_ID)).willReturn(new Airline());
		BDDMockito.given(this.planeService.findPlaneById(PlaneControllerTests.TEST_PLANE_ID)).willReturn(new Plane());
	}
/*
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/pets/new", TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("pet"));
	}
*/
}
