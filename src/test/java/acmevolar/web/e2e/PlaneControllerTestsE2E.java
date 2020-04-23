
package acmevolar.web.e2e;

import org.hamcrest.Matchers;
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
class PlaneControllerTestsE2E {

	private static final int	TEST_PLANE_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@Test
	void testShowPlaneListUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowMyPlaneList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_planes")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("planes")).andExpect(MockMvcResultMatchers.view().name("planes/planesList"));
	}

	@Test
	void testShowMyPlaneListUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/my_planes")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowPlane() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}", PlaneControllerTestsE2E.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("plane"))
			.andExpect(MockMvcResultMatchers.view().name("planes/planeDetails"));
	}

	@Test
	void testShowPlaneUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}", PlaneControllerTestsE2E.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "airline1", value = "airline1", authorities = {
		"airline"
	})
	@Test
	void shouldFindPlaneInformation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}", PlaneControllerTestsE2E.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("plane"))
			.andExpect(MockMvcResultMatchers.model().attribute("plane", Matchers.hasProperty("reference", Matchers.is("V14-5")))).andExpect(MockMvcResultMatchers.model().attribute("plane", Matchers.hasProperty("maxSeats", Matchers.is(150))))
			.andExpect(MockMvcResultMatchers.model().attribute("plane", Matchers.hasProperty("description", Matchers.is("This is a description")))).andExpect(MockMvcResultMatchers.model().attribute("plane", Matchers.hasProperty("lastMaintenance")))
			.andExpect(MockMvcResultMatchers.view().name("planes/planeDetails"));

	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("plane"))
			.andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@Test
	void testInitCreationFormUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, 500, 2011-04-17", "reference2, 300, description2, manufacturer2, model2, 200, 600, 2012-05-18", "reference3, 400, description3, manufacturer3, model3, 300, 700, 2013-06-19",
		"reference4, 500, description4, manufacturer4, model4, 400, 800, 2014-07-20",
	})
	void testProcessCreationFormSuccess(final String reference, final String maxSeats, final String description, final String manufacturer, final String model, final String numberOfKm, final String maxDistance, final String lastMaintenance)
		throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer)
				.param("model", model).param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "reference").param("maxSeats", "200").param("description", "description").param("manufacter", "manufacter").param("model", "model").param("numberOfKm", "100").param("maxDistance", "500")
			.param("lastMaintenance", "2011-04-17"))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf())

			.param("reference", "reference").param("maxSeats", "200").param("description", "description").param("manufacter", "manufacter").param("model", "model").param("numberOfKm", "100").param("maxDistance", "500")
			.param("lastMaintenance", "2011-04-17"))

			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", "reference").param("maxSeats", "-200").param("description", "description").param("manufacter", "manufacter")
				.param("model", "model").param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2011-04-17"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("plane")).andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, -500, 2011-04-17", "reference2, 300, 200, manufacturer2, model2, -200, 600, 2012-05-18", "75, 400, 200, 100, 0, 300, 700, FECHA",
		"reference4, -500, description4, manufacturer4, model4, -400, -800, -2014-07-20",
	})
	void testProcessCreationFormHasErrors(final String reference, final String maxSeats, final String description, final String manufacturer, final String model, final String numberOfKm, final String maxDistance, final String lastMaintenance)
		throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description).param("manufacter", manufacturer)
				.param("model", model).param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))

			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("plane")).andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("plane"))
			.andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@Test
	void testInitUpdateFormUnauthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, 500, 2011-04-17", "reference2, 300, description2, manufacturer2, model2, 200, 600, 2012-05-18", "reference3, 400, description3, manufacturer3, model3, 300, 700, 2013-06-19",
		"reference4, 500, description4, manufacturer4, model4, 400, 800, 2014-07-20",
	})
	void testProcessUpdateFormSuccess(final String reference, final String maxSeats, final String description, final String manufacturer, final String model, final String numberOfKm, final String maxDistance, final String lastMaintenance)
		throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("maxSeats", maxSeats)
			.param("description", description).param("manufacter", manufacturer).param("model", model).param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", "reference2").param("maxSeats", "200")
				.param("description", "description2").param("manufacter", "manufacter2").param("model", "model2").param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2011-04-17"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@Test
	void testProcessUpdateFormUnauthorized() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", "reference2").param("maxSeats", "200")
				.param("description", "description2").param("manufacter", "manufacter2").param("model", "model2").param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2011-04-17"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"reference1, 200, description1, manufacturer1, model1, 100, -500, 2011-04-17", "reference2, 300, 200, manufacturer2, model2, -200, 600, 2012-05-18", "75, 400, 200, 100, 0, 300, 700, FECHA",
		"reference4, -500, description4, manufacturer4, model4, -400, -800, -2014-07-20",
	})
	void testProcessUpdateFormHasErrors(final String reference, final String maxSeats, final String description, final String manufacturer, final String model, final String numberOfKm, final String maxDistance, final String lastMaintenance)
		throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", reference).param("maxSeats", maxSeats).param("description", description)
				.param("manufacter", manufacturer).param("model", model).param("numberOfKm", numberOfKm).param("maxDistance", maxDistance).param("lastMaintenance", lastMaintenance))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("plane")).andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/planes/{planeId}/edit", PlaneControllerTestsE2E.TEST_PLANE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("reference", "reference").param("maxSeats", "-200")
				.param("description", "description2").param("manufacter", "manufacter2").param("model", "model2").param("numberOfKm", "100").param("maxDistance", "500").param("lastMaintenance", "2019-04-17"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("plane")).andExpect(MockMvcResultMatchers.view().name("planes/createPlaneForm"));
	}

}
