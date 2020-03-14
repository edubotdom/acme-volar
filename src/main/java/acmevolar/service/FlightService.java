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
import acmevolar.repository.FlightRepository;
import acmevolar.repository.springdatajpa.SpringDataAirlineRepository;
import acmevolar.repository.springdatajpa.SpringDataFlightRepository;

@Service
public class FlightService {

	private FlightRepository			flightRepository;
	private SpringDataFlightRepository	springDataFlightRepository;
	private SpringDataAirlineRepository	springDataAirlineRepository;


	@Autowired
	public FlightService(final FlightRepository flightRepository, final SpringDataFlightRepository springDataFlightRepository, final SpringDataAirlineRepository springDataAirlineRepository) {
		this.flightRepository = flightRepository;
		this.springDataFlightRepository = springDataFlightRepository;
		this.springDataAirlineRepository = springDataAirlineRepository;
	}

	@Transactional(readOnly = true)
	public List<FlightStatusType> findFlightStatusTypes() throws DataAccessException {
		return this.flightRepository.findFlightStatusTypes();
	}

	@Transactional(readOnly = true)
	public Flight findFlightById(final int id) throws DataAccessException {
		return this.flightRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Airline findAirlineByUsername(final String username) throws DataAccessException {
		return this.springDataAirlineRepository.findByUsername(username);
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
		return this.springDataFlightRepository.findPublishedFlight();
	}

	@Transactional(readOnly = true)
	public Collection<Flight> findAirlineFlight(final String username) {
		return this.springDataFlightRepository.findAirlineFlight(username);
	}

}
