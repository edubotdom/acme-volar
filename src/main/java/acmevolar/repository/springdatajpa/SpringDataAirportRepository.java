
package acmevolar.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Airport;
import acmevolar.projections.AirportListAttributes;
import acmevolar.repository.AirportRepository;

public interface SpringDataAirportRepository extends AirportRepository, Repository<Airport, Integer> {

	@Override
	@Query("SELECT airport FROM Airport airport WHERE airport.id =:id")
	Airport findAirportById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM Airport a WHERE a.name =:airportName")
	List<Airport> findAirportsByName(String airportName) throws DataAccessException;

	@Override
	@Query("SELECT a.id AS id, a.name AS name, a.maxNumberOfPlanes AS maxNumberOfPlanes, a.maxNumberOfClients AS maxNumberOfClients, a.latitude AS latitude, a.longitude AS longitude, a.code AS code, a.city AS city FROM Airport a")
	List<AirportListAttributes> findAllAirportAttributes();

}
