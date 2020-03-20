
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
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;

@WebMvcTest(value = PlaneController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PlaneControllerTests {

	private static final int	TEST_PLANE_ID	= 1;
	private static final int	TEST_AIRLINE_ID	= 1;
	private static final int	TEST_FLIGHT_ID1	= 1;
	private static final int	TEST_FLIGHT_ID2	= 2;

	@Autowired
	private PlaneController		planeController;

	@MockBean
	private PlaneService		planeService;

	@MockBean
	private AirlineService		airlineService;

	@Autowired
	protected FlightService		flightService;

	@Autowired
	private MockMvc				mockMvc;


	/*@BeforeEach
	void setup() {
		BDDMockito.given(this.airlineService.findAirlineById(PlaneControllerTests.TEST_AIRLINE_ID)).willReturn(new Airline());
		BDDMockito.given(this.planeService.findPlaneById(PlaneControllerTests.TEST_PLANE_ID)).willReturn(new Plane());
	}*/

	/**
	 * Buscar un vuelo no disponible y que el sistema no permita consultar la información de su avión.
	 *
	 * @throws Exception
	 */
	@Test
	@WithMockUser(value = "client")
	void shouldNotFindPlaneInformationByFlight() throws Exception {
		//Collection<Flight> flights = this.flightService.findPublishedFlight();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}", PlaneControllerTests.TEST_FLIGHT_ID1)).andExpect(MockMvcResultMatchers.status().isNotFound());

		/*
		 * Flight flight1 = EntityUtils.getById(flights, Flight.class, 1);
		 * flight1.setPublished(false);
		 * assertThat(flight1).isNotNull();
		 * assertThat(flight1.getPlane()).isNotNull();
		 * assertThat(flight1.getPlane()).isInstanceOf(Plane.class);
		 * assertThat(flight1).matches(f -> f.getPublished() == false, "No está publicado");
		 */

	}
}
