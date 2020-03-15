
package acmevolar.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import acmevolar.model.Plane;

public interface PlaneRepository {

	Plane findById(int id) throws DataAccessException;

	void save(Plane plane) throws DataAccessException;

	Collection<Plane> findAll() throws DataAccessException;

	void deleteById(int id);

	List<Plane> findPlanesbyAirline(String airline) throws DataAccessException;

}
