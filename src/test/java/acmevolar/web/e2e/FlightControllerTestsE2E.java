
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
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FlightControllerTestsE2E {

	private static final int	TEST_FLIGHT_ID			= 1;
	private static final int	TEST_FLIGHT_ID2			= 2;

	private static final int	TEST_FLIGHT_ID_PUBLIC	= 2;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("flights"))
			.andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowAirlineFlightList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_flights"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("flights"))
			.andExpect(MockMvcResultMatchers.view().name("flights/flightList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowFlight() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}", FlightControllerTestsE2E.TEST_FLIGHT_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/flightDetails"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf()))
//			.param("reference", "R-20")
//			.param("seats", "100")
//			.param("price", "100.0")
//			.param("flightStatus", "on_time")
//			.param("plane", "V14-5")
//			.param("published", "true")
//			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
//			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
//			.param("landDate", "2021-03-27")
//			.param("departDate", "2021-03-2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"R-21, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-01-27, 2021-01-27",
		"R-22, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-02-27, 2021-02-27",
		"R-23, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2022-03-27, 2022-03-27",
	})
	void testProcessCreationFormSuccess(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price).param("flightStatus", flightStatus).param("plane", plane)
				.param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("reference", "R-24")
			.param("seats", "12100")
			.param("price", "100.0")
			.param("flightStatus", "on_time")
			.param("plane", "V14-5")
			.param("published", "true")
			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
			.param("landDate", "2021-03-27")
			.param("departDate", "2021-03-27"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"'', 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-25, -100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-26, 100, -100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-27, 100, 100.0, abcd, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-28, 100, 100.0, on_time, V14-5, true, 'error', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27", "R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'error', 2021-03-27, 2021-03-27",
		"R-29, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2015-03-27, 2021-03-27",
		"R-30, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2013-03-27"
	})
	void testProcessCreationFormHasErrors(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price).param("flightStatus", flightStatus).param("plane", plane)
				.param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline2", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID2))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("flight"))
		.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateFormAlreadyPublic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID_PUBLIC)).andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "R-32").param("seats", "150").param("price", "100.0").param("flightStatus", "on_time").param("plane", "V14-5").param("published", "true").param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid").param("landDate", "2021-03-27").param("departDate", "2021-03-27")).andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"R-33, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-34, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-35, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2022-03-27, 2022-03-27",
	})
	void testProcessUpdateFormSuccess(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
					.param("reference", reference)
					.param("seats", seats)
					.param("price", price)
					.param("flightStatus", flightStatus)
					.param("plane", plane)
					.param("published", published)
					.param("departes", departes)
					.param("lands", lands)
					.param("landDate", landDate)
					.param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.view().name("redirect:/flights/{flightId}"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("reference", "R-36")
			.param("seats", "438450")
			.param("price", "-20.0")
			.param("flightStatus", "on_time")
			.param("plane", "V14-5")
			.param("published", "true")
			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
			.param("landDate", "2021-03-27")
			.param("departDate", "2021-03-27"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight"))
			.andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"'', 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-37, -100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-38, 100, -100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-39, 100, 100.0, abcd, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2021-03-27",
		"R-40, 100, 100.0, on_time, V14-5, true, 'error', 'A-01, airport: Sevilla Airport, city: Sevilla', 2021-03-27, 2021-03-27", "R-05, 100, 100.0, on_time, V14-5, true, 'runway1, airport: airport2, city: city2', 'error', 2021-03-27, 2021-03-27",
		"R-41, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2015-03-27, 2021-03-27",
		"R-42, 100, 100.0, on_time, V14-5, true, 'A-01, airport: Sevilla Airport, city: Sevilla', 'A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid', 2021-03-27, 2013-03-27"
	})
	void testProcessUpdateFormHasErrors(final String reference, final String seats, final String price, final String flightStatus, final String plane, final String published, final String departes, final String lands, final String landDate,
		final String departDate) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("seats", seats).param("price", price)
				.param("flightStatus", flightStatus).param("plane", plane).param("published", published).param("departes", departes).param("lands", lands).param("landDate", landDate).param("departDate", departDate))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("flight")).andExpect(MockMvcResultMatchers.view().name("flights/createFlightForm"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	
	@WithMockUser(value = "anonymous")
	@Test
	void testShowAirlineFlightListAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_flights"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testInitCreationFormAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/new"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessCreationFormUnauthorizedAnonymous() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("reference", "R-43")
			.param("seats", "100")
			.param("price", "100.0")
			.param("flightStatus", "on_time")
			.param("plane", "V14-5")
			.param("published", "true")
			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
			.param("landDate", "2021-03-27")
			.param("departDate", "2021-03-27"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
//
//	@WithMockUser(value = "anonymous")
//	@Test
//	void testProcessCreationFormHasErrorsAnonymous() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/new").with(SecurityMockMvcRequestPostProcessors.csrf())
//			.param("reference", "R-44")
//			.param("seats", "12100")
//			.param("price", "100.0")
//			.param("flightStatus", "on_time")
//			.param("plane", "V14-5")
//			.param("published", "true")
//			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
//			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
//			.param("landDate", "2021-03-27")
//			.param("departDate", "2021-03-27"))
//			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
//	}

	@WithMockUser(value = "anonymous")
	@Test
	void testInitUpdateFormUnauthorizedAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testInitUpdateFormAlreadyPublicUnauthorizedAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID_PUBLIC)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(value = "anonymous")
	@Test
	void testProcessUpdateFormUnauthorizedAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("reference", "R-45")
			.param("seats", "150")
			.param("price", "100.0")
			.param("flightStatus", "on_time")
			.param("plane", "V14-5")
			.param("published", "true")
			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
			.param("landDate", "2021-03-27")
			.param("departDate", "2021-03-27"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

//	@WithMockUser(value = "anonymous")
//	@Test
//	void testProcessUpdateFormHasErrorsAnonymous() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/flights/{flightId}/edit", FlightControllerTestsE2E.TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
//			.param("reference", "R-46")
//			.param("seats", "438450")
//			.param("price", "-20.0")
//			.param("flightStatus", "on_time")
//			.param("plane", "V14-5")
//			.param("published", "true")
//			.param("departes", "A-01, airport: Sevilla Airport, city: Sevilla")
//			.param("lands", "A-02, airport: Adolfo Suárez Madrid-Barajas Airport, city: Madrid")
//			.param("landDate", "2021-03-27")
//			.param("departDate", "2021-03-27"))
//			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
//	}

}
