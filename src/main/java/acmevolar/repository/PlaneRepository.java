package acmevolar.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import acmevolar.model.Plane;

public interface PlaneRepository extends CrudRepository<Plane, Integer> {
	
	Plane findById(int id) throws DataAccessException;
	
	Collection<Plane> findAll() throws DataAccessException;

	void deleteById(int id);
	
	List<Plane> findPlanesByAirlineId(int id) throws DataAccessException;


}
