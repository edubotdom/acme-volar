
package acmevolar.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import acmevolar.configuration.SecurityConfiguration;
import acmevolar.model.Airline;
import acmevolar.model.Airport;
import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.model.User;
import acmevolar.service.AirlineService;
import acmevolar.service.BookService;
import acmevolar.service.FlightService;
import acmevolar.service.PlaneService;
import acmevolar.service.RunwayService;
import acmevolar.service.UserService;

@WebMvcTest(value = BookController.class, includeFilters = {
	@ComponentScan.Filter(value = BookStatusTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE)
}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class BookControllerTests {

	private static final int	TEST_FLIGHT_ID			= 1;

	private static final int	TEST_FLIGHT_ID_PUBLIC	= 2;

	private static final int	TEST_BOOK_ID			= 1;

	@Autowired
	protected BookController	bookController;

	@MockBean
	protected BookService		bookService;

	//@MockBean
	//protected FlightController	flightController;

	@MockBean
	private FlightService		flightService;

	@MockBean
	private PlaneService		planeService;

	@MockBean
	private RunwayService		runwayService;

	@MockBean
	private AirlineService		airlineService;

	@MockBean
	private UserService			userService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() throws ParseException {

		FlightStatusType flightStatusOnTime = new FlightStatusType();
		flightStatusOnTime.setId(1);
		flightStatusOnTime.setName("on_time");

		FlightStatusType flightStatusDelayed = new FlightStatusType();
		flightStatusDelayed.setId(2);
		flightStatusDelayed.setName("delayed");

		FlightStatusType flightStatusCancelled = new FlightStatusType();
		flightStatusCancelled.setId(3);
		flightStatusCancelled.setName("cancelled");

		List<FlightStatusType> flightStatusTypes = new ArrayList<>();
		flightStatusTypes.add(flightStatusOnTime);
		flightStatusTypes.add(flightStatusDelayed);
		flightStatusTypes.add(flightStatusCancelled);
		BDDMockito.given(this.flightService.findFlightStatusTypes()).willReturn(flightStatusTypes);

		User user1 = new User();
		user1.setUsername("airline1");
		user1.setPassword("airline1");
		user1.setEnabled(true);

		Airline airline1 = new Airline();
		airline1.setId(1);
		airline1.setName("Sevilla Este Airways");
		airline1.setIdentification("61333744-N");
		airline1.setCountry("Spain");
		airline1.setPhone("644584458");
		airline1.setEmail("minardi@gmail.com");
		LocalDate localDate1 = LocalDate.parse("2010-11-07");
		airline1.setCreationDate(localDate1);
		airline1.setReference("SEA-001");
		airline1.setUser(user1);
		BDDMockito.given(this.flightService.findAirlineByUsername(user1.getUsername())).willReturn(airline1);

		String stringDate = "2011-04-17";
		Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
		String stringDate1 = "31/12/2020";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate1);
		String stringDate2 = "31/12/2020";
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate2);

		Plane plane1 = new Plane();
		plane1.setId(1);
		plane1.setAirline(airline1);
		plane1.setDescription("This is a description");
		plane1.setLastMaintenance(date0);
		plane1.setManufacter("Boeing");
		plane1.setMaxDistance(2000000.0);
		plane1.setMaxSeats(150);
		plane1.setModel("B747");
		plane1.setNumberOfKm(500000.23);
		plane1.setReference("V14-5");

		List<Plane> planes = new ArrayList<>();
		planes.add(plane1);

		Airport ap = new Airport();
		ap.setCity("city");
		ap.setCode("code");
		ap.setLatitude(0.);
		ap.setLongitude(0.);
		ap.setMaxNumberOfClients(100);
		ap.setMaxNumberOfPlanes(100);
		ap.setName("airport1");
		ap.setRunwaysInternal(new HashSet<>());
		ap.setId(1);

		Airport ap2 = new Airport();
		ap2.setCity("city2");
		ap2.setCode("code");
		ap2.setLatitude(0.);
		ap2.setLongitude(0.);
		ap2.setMaxNumberOfClients(100);
		ap2.setMaxNumberOfPlanes(100);
		ap2.setName("airport2");
		ap2.setRunwaysInternal(new HashSet<>());
		ap2.setId(2);

		RunwayType rt_landing = new RunwayType();
		rt_landing.setId(1);
		rt_landing.setName("landing");

		RunwayType rt_take_off = new RunwayType();
		rt_take_off.setId(2);
		rt_take_off.setName("take_off");

		Runway r_depart = new Runway();
		r_depart.setAirport(ap2);
		r_depart.setId(1);
		r_depart.setName("runway1");
		r_depart.setFlightsDepartes(new HashSet<Flight>());
		r_depart.setFlightsLands(new HashSet<Flight>());
		r_depart.setRunwayType(rt_take_off);

		Runway r_landing = new Runway();
		r_landing.setAirport(ap);
		r_landing.setId(2);
		r_landing.setName("runway2");
		r_landing.setFlightsDepartes(new HashSet<Flight>());
		r_landing.setFlightsLands(new HashSet<Flight>());
		r_landing.setRunwayType(rt_landing);

		List<Runway> departingRunways = new ArrayList<>();
		departingRunways.add(r_depart);
		BDDMockito.given(this.flightService.findDepartingRunways()).willReturn(departingRunways);

		List<Runway> landingRunways = new ArrayList<>();
		landingRunways.add(r_landing);
		BDDMockito.given(this.flightService.findLandingRunways()).willReturn(landingRunways);

		List<Runway> runways = new ArrayList<>();
		runways.add(r_depart);
		runways.add(r_landing);
		BDDMockito.given(this.runwayService.findAllRunway()).willReturn(runways);

		BDDMockito.given(this.planeService.findPlaneById(1)).willReturn(plane1);
		BDDMockito.given(this.flightService.findPlanesbyAirline("airline1")).willReturn(planes);

		Flight flight1 = new Flight();
		flight1.setId(1);
		flight1.setAirline(airline1);
		flight1.setDepartDate(Date.from(Instant.now().plusSeconds(600)));
		flight1.setDepartes(r_depart);
		flight1.setFlightStatus(flightStatusOnTime);
		flight1.setLandDate(Date.from(Instant.now().plusSeconds(6000)));
		flight1.setLands(r_landing);
		flight1.setPlane(plane1);
		flight1.setPrice(0.);
		flight1.setPublished(false);
		flight1.setReference("F-01");
		flight1.setSeats(10);

		Flight flight2 = new Flight();
		flight2.setId(2);
		flight2.setAirline(airline1);
		flight2.setDepartDate(Date.from(Instant.now().plusSeconds(600)));
		flight2.setDepartes(r_depart);
		flight2.setFlightStatus(flightStatusOnTime);
		flight2.setLandDate(Date.from(Instant.now().plusSeconds(6000)));
		flight2.setLands(r_landing);
		flight2.setPlane(plane1);
		flight2.setPrice(0.);
		flight2.setPublished(true);
		flight2.setReference("F-02");
		flight2.setSeats(10);

		BDDMockito.given(this.flightService.findFlightById(1)).willReturn(flight1);

		BDDMockito.given(this.flightService.findFlightById(2)).willReturn(flight2);

		BDDMockito.given(this.planeService.findPlanes()).willReturn(planes);

		Book book = new Book();
		Book book2 = new Book();

		BookStatusType bst1 = new BookStatusType();
		bst1.setId(1);
		bst1.setName("approved");
		BookStatusType bst2 = new BookStatusType();
		bst2.setId(1);
		bst2.setName("cancelled");

		List<BookStatusType> book_status = new ArrayList<>();
		book_status.add(bst1);
		book_status.add(bst2);

		BDDMockito.given(this.bookService.findBookStatusTypeById(1)).willReturn(bst1);
		BDDMockito.given(this.bookService.findBookStatusTypeById(2)).willReturn(bst2);
		BDDMockito.given(this.bookService.findBookStatusTypes()).willReturn(book_status);

		Client client = new Client();
		client.setId(1);
		client.setIdentification("identification");//
		client.setBirthDate(LocalDate.of(1990, 12, 12));
		client.setPhone("987456321");//
		client.setEmail("email@email.com");//
		client.setCreationDate(LocalDate.of(2010, 12, 12));

		book.setBookStatusType(bst1);
		book.setClient(client);
		book.setFlight(flight1);
		book.setId(1);
		book.setMoment(LocalDate.now());
		book.setPrice(200.);
		book.setQuantity(2);

		book2.setBookStatusType(bst2);
		book2.setClient(client);
		book2.setFlight(flight2);
		book2.setId(2);
		book2.setMoment(LocalDate.now());
		book2.setPrice(100.);
		book2.setQuantity(1);

		List<Book> books = new ArrayList<>();
		books.add(book);
		books.add(book2);

		BDDMockito.given(this.bookService.findBookById(1)).willReturn(book);
		BDDMockito.given(this.bookService.findBookById(2)).willReturn(book2);

		BDDMockito.given(this.bookService.findClientBookFuture("client1")).willReturn(books);
		BDDMockito.given(this.bookService.findAirlineBookFuture("client1")).willReturn(books);
		BDDMockito.given(this.bookService.findClientBook("client1")).willReturn(books);
		BDDMockito.given(this.bookService.findAirlineBook("client1")).willReturn(books);
		BDDMockito.given(this.bookService.findClientByUsername("client1")).willReturn(client);

	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@Test
	void testClientBookList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/client")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("books")).andExpect(MockMvcResultMatchers.view().name("books/bookList"));
	}

	@WithMockUser(value = "client1", authorities = {
		"client"
	})
	@Test
	void testAirlineBookList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/airline")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("books")).andExpect(MockMvcResultMatchers.view().name("books/bookList"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/{flightId}/new", TEST_FLIGHT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{flightId}/new", TEST_FLIGHT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("quantity", "10")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/{bookId}/edit", BookControllerTests.TEST_BOOK_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("book"))
			.andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTests.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", "cancelled").param("quantity","2"))
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
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTests.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", bookStatusType).param("quantity","2"))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/books/airline"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTests.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", "fallo").param("quantity","2"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("book")).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	@WithMockUser(value = "airline1", authorities = {
		"airline"
	})
	@ParameterizedTest
	@CsvSource({
		"offered", "''",
	})
	void testProcessUpdateFormHasErrors(String bookStatusType) throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit", BookControllerTests.TEST_BOOK_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bookStatusType", bookStatusType).param("quantity","2"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("book")).andExpect(MockMvcResultMatchers.view().name("books/createBookForm"));
	}

	//////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testClientBookListAnonymous() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/books/client")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
