
package acmevolar.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import acmevolar.model.Airport;
import acmevolar.projections.AirportListAttributes;

public interface AirportRepository {

	Collection<Airport> findAll() throws DataAccessException;

	/**
	 * Retrieve a <code>Airport</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>Airport</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Airport findAirportById(int id) throws DataAccessException;

	List<Airport> findAirportsByName(String airportName) throws DataAccessException;

	List<AirportListAttributes> findAllAirportAttributes() throws DataAccessException;

	void delete(Airport airport);

	void save(Airport airport);

	Optional<Airport> findById(int airportId);
}
