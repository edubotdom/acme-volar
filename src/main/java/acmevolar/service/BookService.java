
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

	private BookRepository				bookRepository;
	private AirlineRepository			airlineRepository;
	private SpringDataPlaneRepository	springPlaneRepository;


	@Autowired
	public BookService(final BookRepository bookRepository, final SpringDataPlaneRepository springPlaneRepository, final AirlineRepository airlineRepository) {
		this.bookRepository = bookRepository;
		this.springPlaneRepository = springPlaneRepository;
		this.airlineRepository = airlineRepository;
	}

	@Transactional(readOnly = true)
	public Book findBookById(final int id) throws DataAccessException {
		return this.bookRepository.findById(id);
	}

	/*
	 * @Transactional(readOnly = true)
	 * public Plane findPlaneByReference(final String reference) throws DataAccessException {
	 * return this.planeRepository.findByReference(reference);
	 * }
	 */
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

	/*
	 * public void deleteById(final int id) throws DataAccessException {
	 * this.planeRepository.deleteById(id);
	 * }
	 *
	 * public void deletePlane(final Plane plane) throws DataAccessException {
	 * this.planeRepository.deleteById(plane.getId());
	 * }
	 *
	 */

	/*
	 * public void updatePlane(final Plane plane) throws DataAccessException {
	 * Integer id = plane.getId(); // extract id of a plane
	 * Plane plane2 = this.findPlaneById(id); // we know the original plane with that id
	 * this.deletePlane(plane2); // we delete the original
	 * this.planeRepository.save(plane); // we replace with the updated version
	 * }
	 */

	/*
	 *
	 * @Transactional(readOnly = true)
	 * public Collection<Plane> findPlanes() throws DataAccessException {
	 * return this.planeRepository.findAll();
	 * }
	 *
	 * //Airline
	 *
	 * @Transactional(readOnly = true)
	 * public Airline findAirlineByUsername(final String username) throws DataAccessException {
	 * return this.airlineRepository.findByUsername(username);
	 * }
	 *
	 * public Collection<Plane> getAllPlanesFromAirline(final String airline) {
	 * return this.planeRepository.findPlanesbyAirline(airline);
	 * }
	 *
	 * public List<Plane> getAllPlanesFromAirline(final Airline airline) {
	 * return this.springPlaneRepository.findPlanesByAirlineId(airline.getId());
	 * }
	 *
	 */

}
