package acmevolar.web;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Authorities;
import acmevolar.model.Client;
import acmevolar.model.User;
import acmevolar.service.AuthoritiesService;
import acmevolar.service.ClientService;
import acmevolar.service.UserService;

@WebMvcTest(value = ClientController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ClientControllerTests {

	private static final int TEST_CLIENT_ID = 1;
	
	@Autowired
	protected ClientController clientController;

	@MockBean
	private ClientService clientService;

	@MockBean
	protected UserService userService;

	@MockBean
	protected AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("pepito1");
		user.setPassword("pepito1");
		user.setEnabled(true);

		Authorities authorities = new Authorities();
		authorities.setUsername("pepito1");
		authorities.setAuthority("client");

		Client client = new Client();
		client.setId(1);
		client.setName("Pepito Palotes");
		client.setBirthDate(LocalDate.of(1995, 10, 19));
		client.setCreationDate(LocalDate.of(2019, 4, 3));
		client.setEmail("notaemail");
		client.setPhone("666333666");
		client.setUser(user);
		client.setIdentification("53933123X");

		given(this.clientService.findClientById(ClientControllerTests.TEST_CLIENT_ID)).willReturn(client);

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClientList() throws Exception {
		this.mockMvc.perform(get("/clients")).andExpect(status().isOk()).andExpect(model().attributeExists("clients"))
				.andExpect(view().name("clients/clientsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClient() throws Exception {
		this.mockMvc.perform(get("/clients/{clientId}", ClientControllerTests.TEST_CLIENT_ID))
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
