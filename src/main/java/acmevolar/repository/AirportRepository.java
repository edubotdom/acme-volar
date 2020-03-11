
package acmevolar.repository;

import org.springframework.dao.DataAccessException;

import acmevolar.model.Airport;

public interface AirportRepository {

	/**
	 * Retrieve a <code>Airport</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>Airport</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Airport findById(int id) throws DataAccessException;

}
