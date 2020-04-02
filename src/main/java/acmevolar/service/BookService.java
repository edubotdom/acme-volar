
package acmevolar.service;

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
	/*
	 * @Transactional(readOnly = true)
	 * public Plane findPlaneById(final int id) throws DataAccessException {
	 * return this.planeRepository.findById(id);
	 * }
	 *
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
