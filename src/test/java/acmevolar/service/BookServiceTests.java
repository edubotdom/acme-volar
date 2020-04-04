
package acmevolar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.model.Flight;
import acmevolar.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BookServiceTests {

	@Autowired
	protected BookService	bookService;
	
	@Autowired	
	protected FlightService flightService;

	
	@Test
	@Transactional
	public void shouldInsertBookIntoDatabaseAndGenerateId() {

		Client client = this.bookService.findClientByUsername("client1");
		BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
		Book book= new Book();
		Flight flight = this.flightService.findFlightById(1);		
		
		book.setBookStatusType(bookStatusType);
		book.setClient(client);
		book.setFlight(flight);
		book.setMoment(LocalDate.now());
		book.setPrice(200.);
		book.setQuantity(2);

		this.bookService.saveBook(book);

		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldNotInsertBookNegativePrice() {

		Client client = this.bookService.findClientByUsername("client1");
		BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
		Book book= new Book();
		Flight flight = this.flightService.findFlightById(1);		
		
		book.setBookStatusType(bookStatusType);
		book.setClient(client);
		book.setFlight(flight);
		book.setMoment(LocalDate.now());
		book.setPrice(-200.);
		book.setQuantity(2);



		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.bookService.saveBook(book);
			});
		}
	
	@Transactional
	public void shouldNotInsertBookNegativeQuantity() {

		Client client = this.bookService.findClientByUsername("client1");
		BookStatusType bookStatusType = this.bookService.findBookStatusTypeById(1);
		Book book= new Book();
		Flight flight = this.flightService.findFlightById(1);		
		
		book.setBookStatusType(bookStatusType);
		book.setClient(client);
		book.setFlight(flight);
		book.setMoment(LocalDate.now());
		book.setPrice(200.);
		book.setQuantity(-2);



		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.bookService.saveBook(book);
			});
		}
	
	@Test
	public void shouldFindFutureAirlineBooks() {
		Collection<Book> books = this.bookService.findAirlineBookFuture("airline1");

		assertThat(!books.isEmpty());
		assertThat(books).asList();
	}
	
	//Negative case -> given a client user
	@Test
	public void shouldNotFindFutureAirlineBooks() {
		Collection<Book> books = this.bookService.findAirlineBookFuture("client1");

		assertThat(books.isEmpty());
	}
	
	@Test
	public void shouldFindAirlineBooks() {
		Collection<Book> books = this.bookService.findAirlineBook("airline1");

		assertThat(!books.isEmpty());
		assertThat(books).asList();
	}
	
	//Negative case -> given a client user
	@Test
	public void shouldNotFindAirlineBooks() {
		Collection<Book> books = this.bookService.findAirlineBook("client1");

		assertThat(books.isEmpty());
	}
	
	@Test
	public void shouldFindClientBooks() {
		Collection<Book> books = this.bookService.findClientBook("client1");

		assertThat(!books.isEmpty());
		assertThat(books).asList();
	}
	
	//Negative case -> given a airline user
	@Test
	public void shouldNotFindClientBooks() {
		Collection<Book> books = this.bookService.findClientBook("airline1");

		assertThat(books.isEmpty());
	}
	
	@Test
	public void shouldFindFutureClientBooks() {
		Collection<Book> books = this.bookService.findClientBookFuture("client1");

		assertThat(!books.isEmpty());
		assertThat(books).asList();
	}
	
	//Negative case -> given a client user
	@Test
	public void shouldNotFindFutureClientBooks() {
		Collection<Book> books = this.bookService.findClientBookFuture("airline1");

		assertThat(books.isEmpty());
	}

	@Test
	public void shouldSumSeatsBooked() {
		Integer flightId= 1;
		Integer book_number = this.bookService.sumSeatsBooked(flightId);

		assertThat(book_number>=0);
	}
	
	//Given non existing flight id
	@Test
	public void shouldNotSumSeatsBooked() {
		Integer flightId= -1;
		Integer book_number = this.bookService.sumSeatsBooked(flightId);

		assertThat(book_number==null);
	}
	
	@Test
	public void shouldFindBookStatusTypes() {
		List<BookStatusType> bookStatusTypes = this.bookService.findBookStatusTypes();

		assertThat(bookStatusTypes.size()==2);
	}
	
	@Test
	public void shouldFindClientByUsername() {
		String clientUsername = "client1";
		Client client = this.bookService.findClientByUsername(clientUsername);

		assertThat(client).isInstanceOf(Client.class);
	}
	
	//Given a non existing username
	@Test
	public void shouldNotFindClientByUsername() {
		String clientUsername = "client-1";
		Client client = this.bookService.findClientByUsername(clientUsername);

		assertThat(client==null);
	}
	
	@Test
	public void shouldFindBookStatusTypeById() {
		Integer bookStatusId= 1;
		BookStatusType bookStatus = this.bookService.findBookStatusTypeById(bookStatusId);

		assertThat(bookStatus.getName().equals("approved"));
		
		Integer bookStatusId2= 2;
		BookStatusType bookStatus2 = this.bookService.findBookStatusTypeById(bookStatusId2);

		assertThat(bookStatus2.getName().equals("cancelled"));
	}
	
	@Test
	public void shouldNotFindBookStatusTypeById() {
		Integer bookStatusId= -1;
		BookStatusType bookStatus = this.bookService.findBookStatusTypeById(bookStatusId);

		assertThat(bookStatus==null);
			}
	
	@Test
	public void shouldFindBookById() {
		Integer bookId= 1;
		Book book = this.bookService.findBookById(bookId);

		assertThat(book.getBookStatusType()).isInstanceOf(BookStatusType.class);
		assertThat(book.getClient()).isInstanceOf(Client.class);
		assertThat(book.getFlight()).isInstanceOf(Flight.class);
		assertThat(book.getId()==1);
		assertThat(book.getMoment()).isInstanceOf(LocalDate.class);
		assertThat(book.getPrice()>=0);
		assertThat(book.getQuantity()>0);
		
	}
	
	@Test
	public void shouldNotFindBookById() {
		Integer bookId= -1;
		Book book = this.bookService.findBookById(bookId);

		assertThat(book==null);		
	}
	
}
