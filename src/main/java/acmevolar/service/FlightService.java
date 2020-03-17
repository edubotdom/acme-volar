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

package acmevolar.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Airline;
import acmevolar.model.Flight;
import acmevolar.model.FlightStatusType;
import acmevolar.model.Plane;
import acmevolar.model.Runway;
import acmevolar.repository.AirlineRepository;
import acmevolar.repository.FlightRepository;
import acmevolar.repository.PlaneRepository;
import acmevolar.repository.RunwayRepository;

@Service
public class FlightService {

	private FlightRepository		flightRepository;
	private AirlineRepository		airlineRepository;
	private final PlaneRepository	planeRepository;
	private final RunwayRepository	runwayRepository;


	@Autowired
	public FlightService(final FlightRepository flightRepository, final AirlineRepository airlineRepository, final PlaneRepository planeRepository, final RunwayRepository runwayRepository) {
		this.flightRepository = flightRepository;
		this.airlineRepository = airlineRepository;
		this.planeRepository = planeRepository;
		this.runwayRepository = runwayRepository;
	}

	@Transactional(readOnly = true)
	public Flight findFlightById(final int id) throws DataAccessException {
		return this.flightRepository.findById(id);
	}

	@Transactional
	public void saveFlight(final Flight flight) throws DataAccessException {
		this.flightRepository.save(flight);
	}

	@Transactional(readOnly = true)
	public Collection<Flight> findFlights() throws DataAccessException {
		return this.flightRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Flight> findPublishedFlight() {
		return this.flightRepository.findPublishedFlight();
	}
	
	@Transactional(readOnly = true)
	public Collection<Flight> findPublishedFutureFlight() {
		return this.flightRepository.findPublishedFutureFlight();
	}

	@Transactional(readOnly = true)
	public Collection<Flight> findAirlineFlight(final String username) {
		return this.flightRepository.findAirlineFlight(username);
	}

	//FlightStatusType
	@Transactional(readOnly = true)
	public List<FlightStatusType> findFlightStatusTypes() throws DataAccessException {
		return this.flightRepository.findFlightStatusTypes();
	}

	//Airline
	@Transactional(readOnly = true)
	public Airline findAirlineByUsername(final String username) throws DataAccessException {
		return this.airlineRepository.findByUsername(username);
	}

	//Plane
	@Transactional(readOnly = true)
	public List<Plane> findPlanesbyAirline(final String airline) throws DataAccessException {
		return this.planeRepository.findPlanesbyAirline(airline);
	}

	//Runway
	@Transactional(readOnly = true)
	public List<Runway> findDepartingRunways() throws DataAccessException {
		return this.runwayRepository.findDepartingRunways();
	}

	public List<Runway> findLandingRunways() throws DataAccessException {
		return this.runwayRepository.findLandingRunways();
	}

}
