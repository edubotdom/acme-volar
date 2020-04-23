
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
public class BookControllerTestsE2E {

	private static final int	TEST_FLIGHT_ID			= 1;

	private static final int	TEST_BOOK_ID			= 1;

	@Autowired
	private MockMvc				mockMvc;

	
	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@Test
	void testClientBookList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/client")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("books")).andExpect(MockMvcResultMatchers.view().name("books/bookList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testAirlineBookList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/airline"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("books/bookList"));
	}

	@WithMockUser(value = "client1", authorities = {
			"client"
		})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/{flightId}/new", TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{flightId}/new", TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("quantity", "10"))
				
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@ParameterizedTest
	@CsvSource({
		"10", "1", "2", "5",
	})
	void testProcessCreationFormSuccess(final String quantity) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{flightId}/new", TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("quantity", quantity)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{flightId}/new", TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("quantity", "0")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("book"))
			.andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@ParameterizedTest
	@CsvSource({
		"-10", "0",
	})
	void testProcessCreationFormHasErrors(final String quantity) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{flightId}/new", TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("quantity", quantity)).andExpect(MockMvcResultMatchers.model().attributeHasErrors("book"))
			.andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/{bookId}/edit", BookControllerTestsE2E.TEST_BOOK_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("book"))
			.andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTestsE2E.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", "cancelled").param("quantity","2"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/books/airline"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"approved", "cancelled"
	})
	void testProcessUpdateFormSuccess(String bookStatusType) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTestsE2E.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", bookStatusType).param("quantity","2"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/books/airline"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTestsE2E.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", "fallo").param("quantity","2"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("book")).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"offered", "test_status",
	})
	void testProcessUpdateFormHasErrors(String bookStatusType) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTestsE2E.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", bookStatusType).param("quantity","2"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("book")).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	@WithMockUser(value = "anonymous")
	@Test
	void testClientBookListAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/client")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
