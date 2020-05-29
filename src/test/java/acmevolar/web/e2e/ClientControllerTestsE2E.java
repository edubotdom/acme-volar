
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
class ClientControllerTestsE2E {

	private static final int	TEST_CLIENT_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowClientList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("clients")).andExpect(MockMvcResultMatchers.view().name("clients/clientsList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testShowClient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/{clientId}", ClientControllerTestsE2E.TEST_CLIENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("client"))
			.andExpect(MockMvcResultMatchers.view().name("clients/clientDetails"));
	}

	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("clients/createClientForm"));
	}

	@ParameterizedTest
	@CsvSource({
		"Pepito Pinotes, 123456789X, 1997/10/10, 987654321, pepitopinotes@alum.us.es", "JU4N N06UER01, 666999666T, 1925/11/10, 634964979, jnogtir@alum.us.es", "DIOS DANNY, 4458X, 1997/10/10, 4458, diosDanny4458@dany.god",
	})
	void testProcessCreationFormSuccess(final String name, final String identification, final String birthDate, final String phone, final String email) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/clients/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("identification", identification).param("birthDate", birthDate).param("phone", phone).param("email", email))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@ParameterizedTest
	@CsvSource({
		"Pepito Pinotes, 123456789X, 2050/10/10, 987654321, pepitopinotes@alum.us.es", "JU4N N06UER01, 666999666T, 1925/11/10, notanumber, jnogtir@alum.us.es", "DIOS DANNY, 4458X, 1997/10/10, 4458, diosDanny4458.dany.god",
	})
	void testProcessCreateFormHasErrors(final String name, final String identification, final String birthDate, final String phone, final String email) throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/clients/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", name).param("identification", identification).param("birthDate", birthDate).param("phone", phone).param("email", email))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("client")).andExpect(MockMvcResultMatchers.view().name("clients/createClientForm"));

	}

}
