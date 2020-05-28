
package acmevolar.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Book;
import acmevolar.model.BookStatusType;
import acmevolar.model.Client;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.BookRepository;
import acmevolar.repository.springdatajpa.SpringDataPlaneRepository;

@Service
public class BookService {

	private BookRepository bookRepository;


	@Autowired
	public BookService(final BookRepository bookRepository, final SpringDataPlaneRepository springPlaneRepository, final AirlineRepository airlineRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional(readOnly = true)
	public Book findBookById(final int id) throws DataAccessException {
		return this.bookRepository.findById(id);
	}

	@Transactional
	public void saveBook(final Book book) throws DataAccessException {
		this.bookRepository.save(book);
	}

	public BookStatusType findBookStatusTypeById(final int bookStatusTypeId) throws DataAccessException {
		return this.bookRepository.findBookStatusTypeById(bookStatusTypeId);
	}

	public Client findClientByUsername(final String clientUsername) throws DataAccessException {
		return this.bookRepository.findClientByUsername(clientUsername);
	}

	@Transactional(readOnly = true)
	public List<BookStatusType> findBookStatusTypes() throws DataAccessException {
		return this.bookRepository.findBookStatusTypes();
	}

	@Transactional(readOnly = true)
	public Collection<Book> findAirlineBook(final String username) {
		return this.bookRepository.findAirlineBook(username);
	}

	@Transactional(readOnly = true)
	public Collection<Book> findClientBook(final String username) {
		return this.bookRepository.findClientBook(username);
	}

	@Transactional(readOnly = true)
	public Integer sumSeatsBooked(final Integer flightId) {
		return this.bookRepository.sumSeatsBooked(flightId);
	}

	@Transactional(readOnly = true)
	public Collection<Book> findAirlineBookFuture(final String username) {
		return this.bookRepository.findAirlineBookFuture(username);
	}

	@Transactional(readOnly = true)
	public Collection<Book> findClientBookFuture(final String username) {
		return this.bookRepository.findClientBookFuture(username);
	}

}
