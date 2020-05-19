/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acmevolar.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.projections.FlightListAttributes;
import acmevolar.repository.FlightRepository;

/**
 * Spring Data JPA specialization of the {@link FlightRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataFlightRepository extends FlightRepository, Repository<Flight, Integer> {

	@Override
	@Query("SELECT fstype FROM FlightStatusType fstype ORDER BY fstype.name")
	List<FlightStatusType> findFlightStatusTypes() throws DataAccessException;

	@Override
	@Query("SELECT flight FROM Flight flight WHERE flight.published = '1' ")
	List<Flight> findPublishedFlight() throws DataAccessException;

	@Override
	@Query("SELECT flight FROM Flight flight WHERE flight.published = '1' AND flight.departDate >= current_date()")
	List<Flight> findPublishedFutureFlight() throws DataAccessException;
	
	@Override
	@Query("SELECT flight FROM Flight flight WHERE flight.airline.user.username =:username AND flight.departDate >= current_date()")
	List<Flight> findAirlineFlight(@Param("username") String username) throws DataAccessException;
	
	@Override
	@Query("SELECT flight FROM Flight flight WHERE flight.reference = :reference")
	Flight findFlightByReference(@Param("reference") String reference) throws DataAccessException;

	@Override
	@Query("SELECT f.id AS id, f.reference AS reference, f.price AS price, "
			+ "f.landDate AS landDate, f.departDate AS departDate, f.airline.name AS airlineName, f.airline.id AS airlineId, "
			+ "f.plane.id AS planeId, f.plane.model AS planeModel, f.departes.airport.id AS departAirportId, "
			+ "f.departes.airport.city AS departAirportCity, f.lands.airport.id AS landAirportId, f.lands.airport.city AS landAirportCity"
			+ " FROM Flight f WHERE f.airline.user.username =:username AND f.departDate >= current_date()")
	List<FlightListAttributes> findAllAirlineFlightListAttributes(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT f.id AS id, f.reference AS reference, f.price AS price, "
			+ "f.landDate AS landDate, f.departDate AS departDate, f.airline.name AS airlineName, f.airline.id AS airlineId, "
			+ "f.plane.id AS planeId, f.plane.model AS planeModel, f.departes.airport.id AS departAirportId, "
			+ "f.departes.airport.city AS departAirportCity, f.lands.airport.id AS landAirportId, f.lands.airport.city AS landAirportCity"
			+ " FROM Flight f WHERE f.published = '1' AND f.departDate >= current_date()")
	List<FlightListAttributes> findAllClientFlightListAttributesPublishedFuture() throws DataAccessException;

}
