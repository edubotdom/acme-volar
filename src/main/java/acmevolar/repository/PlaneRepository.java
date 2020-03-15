
package acmevolar.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Plane;


public interface PlaneRepository extends CrudRepository<Plane, Integer> {
	
	Plane findById(int id) throws DataAccessException;
	
	Collection<Plane> findAll() throws DataAccessException;

	void deleteById(int id);

	@Query("SELECT p FROM Plane p WHERE p.airline.id =:id")
	List<Plane> findPlanesByAirlineId(int id) throws DataAccessException;
	
	@Query("SELECT plane FROM Plane plane WHERE plane.airline.user.username =:airline ")
	List<Plane> findPlanesbyAirline(@Param("airline") String airline) throws DataAccessException;

}
