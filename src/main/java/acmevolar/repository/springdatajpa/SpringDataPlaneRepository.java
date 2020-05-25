
package acmevolar.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Plane;
import acmevolar.projections.PlaneListAttributes;
import acmevolar.repository.PlaneRepository;

public interface SpringDataPlaneRepository extends PlaneRepository, Repository<Plane, Integer> {

	@Override
	@Query("SELECT p FROM Plane p WHERE p.airline.id =:id")
	List<Plane> findPlanesByAirlineId(int id) throws DataAccessException;

	@Override
	@Query("SELECT plane FROM Plane plane WHERE plane.airline.user.username =:airline ")
	List<Plane> findPlanesbyAirline(@Param("airline") String airline) throws DataAccessException;

	@Override
	@Query("SELECT plane FROM Plane plane WHERE plane.reference =:reference")
	Plane findByReference(String reference);

	@Override
	@Query("SELECT p.id AS id, p.reference AS reference, " + "p.maxSeats AS maxSeats, p.description AS description, p.manufacter AS manufacter, " + "p.model AS model, p.numberOfKm AS numberOfKm, p.maxDistance AS maxDistance, p.lastMaintenance AS lastMaintenance "
		+ " FROM Plane p WHERE p.airline.user.username =:airline")
	List<PlaneListAttributes> findAllAirlinePlaneListAttributes(@Param("airline") String airline) throws DataAccessException;
}
