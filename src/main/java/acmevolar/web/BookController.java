
package acmevolar.web;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.service.BookService;
import acmevolar.service.FlightService;

@Controller
public class BookController {

	private final BookService	bookService;
	private final FlightService	flightService;

	private static final String	VIEWS_BOOKS_CREATE_OR_UPDATE_FORM	= "books/createBookForm";


	@Autowired
	public BookController(final BookService bookService, final FlightService flightService) {
		this.bookService = bookService;
		this.flightService = flightService;
	}

	@PreAuthorize("hasAuthority('client')")
	@GetMapping(value = "/books/{flightId}/new")
	public String initCreationForm(@PathVariable("flightId") final int flightId, final Map<String, Object> model) {
		Book book = new Book();
		Flight flight = this.flightService.findFlightById(flightId);

		LocalDate departDate = flight.getDepartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (LocalDate.now().isAfter(departDate)) {
			return "redirect:/books/client";
		}

		model.put("flight", flight);
		model.put("book", book);

		return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;
	}

	@PreAuthorize("hasAuthority('client')")
	@PostMapping(value = "/books/{flightId}/new")
	public String processCreationForm(@PathVariable("flightId") final int flightId, @Valid final Book book, final BindingResult result, final Map<String, Object> model) throws Exception {

		Flight flight = this.flightService.findFlightById(flightId);
		Integer seatsBooked = this.bookService.sumSeatsBooked(flightId);

		BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
		book.setBookStatusType(bookStatusType);
		LocalDate date = LocalDate.now();
		book.setMoment(date);
		String clientUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Client client = this.bookService.findClientByUsername(clientUsername);
		book.setClient(client);
		book.setFlight(flight);
		Double price = book.getFlight().getPrice() * book.getQuantity();
		book.setPrice(price);

		LocalDate departDate = book.getFlight().getDepartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (seatsBooked == null) {
			seatsBooked = 0;
		}

		if (result.hasErrors()) {

			//	Client client = SecurityContextHolder.getContext().getAuthentication().;

			model.put("flight", flight);
			return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;

		} else if (flight.getSeats() - (book.getQuantity() + seatsBooked) < 0) {
			result.rejectValue("quantity", "NotEnoughSeats", "There are only " + (flight.getSeats() - seatsBooked) + " seats in this flight!");
			model.put("flight", flight);
			return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;
		} else if (book.getMoment().isAfter(departDate)) {
			throw new Exception("You cannot create a book for a past flight");
		} else {

			this.bookService.saveBook(book);

			return "redirect:/books/client";
		}
	}

	//UPDATE
	@PreAuthorize("hasAuthority('airline')")
	@GetMapping(value = "/books/{bookId}/edit")
	public String initUpdateForm(@PathVariable("bookId") final int bookId, final ModelMap model) {
		Book book = this.bookService.findBookById(bookId);

		List<BookStatusType> bookStatusTypes = this.bookService.findBookStatusTypes();

		LocalDate departDate = book.getFlight().getDepartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (LocalDate.now().isAfter(departDate)) {
			return "redirect:/books/airline";
		}

		model.put("book", book);
		model.put("bookStatusTypes", bookStatusTypes);
		model.put("flight", book.getFlight());
		return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;
	}

	@PreAuthorize("hasAuthority('airline')")
	@PostMapping(value = "/books/{bookId}/edit")
	public String processUpdateForm(@Valid final Book book, final BindingResult result, @PathVariable("bookId") final int bookId, final ModelMap model) {

		if (result.hasErrors()) {

			List<BookStatusType> bookStatusTypes = this.bookService.findBookStatusTypes();
			model.put("bookStatusTypes", bookStatusTypes);
			model.put("flight", book.getFlight());
			return BookController.VIEWS_BOOKS_CREATE_OR_UPDATE_FORM;

		} else {
			Book bookToUpdate = this.bookService.findBookById(bookId);
			BeanUtils.copyProperties(bookToUpdate, book, "bookStatusType");

			try {
				this.bookService.saveBook(book);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
			return "redirect:/books/airline";
		}
	}

	//list mine client

	//list mine airline

	@PreAuthorize("hasAuthority('airline')")
	@GetMapping("/books/airline")
	public String AirlineBookList(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Book> books = this.bookService.findAirlineBookFuture(username);
		model.put("books", books);
		return "books/bookList";
	}

	@PreAuthorize("hasAuthority('client')")
	@GetMapping("/books/client")
	public String ClientBookList(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Book> books = this.bookService.findClientBookFuture(username);
		model.put("books", books);
		return "books/bookList";
	}

	//show Book
	//	@PreAuthorize("hasAuthority('airline')")
	//	@GetMapping("/books/{bookId}")
	//	public ModelAndView showBook(@PathVariable("bookId") final int bookId) {
	//		ModelAndView mav = new ModelAndView("books/bookDetails");
	//		mav.addObject(this.bookService.findBookById(bookId));
	//		return mav;
	//	}

}
