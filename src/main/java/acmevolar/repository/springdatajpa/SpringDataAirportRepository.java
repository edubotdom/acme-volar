
package acmevolar.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Airport;
import acmevolar.repository.AirportRepository;

public interface SpringDataAirportRepository extends AirportRepository, Repository<Airport, Integer> {

	@Override
	@Query("SELECT airport FROM Airport airport WHERE airport.id =:id")
	Airport findAirportById(@Param("id") int id);
	
	@Query("SELECT a FROM Airport a where a.name =:airportName")
	List<Airport> findAirportsByName(String airportName) throws DataAccessException;

}
