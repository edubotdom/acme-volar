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
public class ClientControllerTestsE2E {

	private static final int TEST_CLIENT_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	

	@WithMockUser(value = "spring")
	@Test
	void testShowClientList() throws Exception {
		this.mockMvc.perform(get("/clients")).andExpect(status().isOk()).andExpect(model().attributeExists("clients"))
				.andExpect(view().name("clients/clientsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClient() throws Exception {
		this.mockMvc.perform(get("/clients/{clientId}", ClientControllerTestsE2E.TEST_CLIENT_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/clientDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/clients/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("clients/createClientForm"));
	}

	@WithMockUser(value = "spring")
	@ParameterizedTest
	@CsvSource({
		"Pepito Pinotes, 123456789X, 1997/10/10, 987654321, pepitopinotes@alum.us.es",
		"JU4N N06UER01, 666999666T, 1925/11/10, 634964979, jnogtir@alum.us.es",
		"DIOS DANNY, 4458X, 1997/10/10, 4458, diosDanny4458@dany.god",
	})
	void testProcessCreationFormSuccess(String name, String identification, String birthDate, String phone, String email) throws Exception {
		this.mockMvc.perform(post("/clients/new").with(csrf())
				.param("name", name)
				.param("identification", identification)
				.param("birthDate", birthDate)
				.param("phone", phone)
				.param("email", email))
				.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@CsvSource({
		"Pepito Pinotes, 123456789X, 2050/10/10, 987654321, pepitopinotes@alum.us.es",
		"JU4N N06UER01, 666999666T, 1925/11/10, notanumber, jnogtir@alum.us.es",
		"DIOS DANNY, 4458X, 1997/10/10, 4458, diosDanny4458.dany.god",
	})
	void testProcessCreateFormHasErrors(String name, String identification, String birthDate, String phone, String email) throws Exception {
		this.mockMvc.perform(post("/clients/new").with(csrf())
				.param("name", name)
				.param("identification", identification)
				.param("birthDate", birthDate)
				.param("phone", phone)
				.param("email", email))
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(view().name("clients/createClientForm"));
		
	}

}
