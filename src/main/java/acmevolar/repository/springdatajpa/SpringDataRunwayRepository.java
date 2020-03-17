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

import acmevolar.model.Airport;
import acmevolar.model.Runway;
import acmevolar.model.RunwayType;
import acmevolar.repository.FlightRepository;
import acmevolar.repository.RunwayRepository;

/**
 * Spring Data JPA specialization of the {@link FlightRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataRunwayRepository extends RunwayRepository, Repository<Runway, Integer> {

	@Override
	@Query("SELECT r FROM RunwayType r ORDER BY r.name")
	List<RunwayType> findRunwaysTypes() throws DataAccessException;
	
	@Override
	@Query("SELECT a FROM Airport a where a.id=:airportId")
	Airport findAirportById(Integer airportId) throws DataAccessException;

	@Override
	@Query("SELECT r FROM Runway r where r.id=:runwayId")
	Runway findById(int runwayId) throws DataAccessException;

	@Override
	@Query("SELECT r FROM Runway r where r.runwayType.name = 'take_off'")
	List<Runway> findDepartingRunways() throws DataAccessException;

	@Override
	@Query("SELECT r FROM Runway r where r.runwayType.name = 'landing'")
	List<Runway> findLandingRunways() throws DataAccessException;

}
