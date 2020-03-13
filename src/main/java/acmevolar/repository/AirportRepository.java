
package acmevolar.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import acmevolar.model.Airport;

public interface AirportRepository extends CrudRepository<Airport, Integer> {

	@Override
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

}
