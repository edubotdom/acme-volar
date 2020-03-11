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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acmevolar.model.Flight;
import acmevolar.repository.FlightRepository;
import acmevolar.repository.springdatajpa.SpringDataFlightRepository;
import acmevolar.service.exceptions.DuplicatedPetNameException;

@Service
public class FlightService {

	private FlightRepository			flightRepository;
	private SpringDataFlightRepository	springDataFlightRepository;


	@Autowired
	public FlightService(final FlightRepository flightRepository, final SpringDataFlightRepository springDataFlightRepository) {
		this.flightRepository = flightRepository;
		this.springDataFlightRepository = springDataFlightRepository;
	}

	/*
	 * @Transactional(readOnly = true)
	 * public List<FlightStatusType> findPetTypes() throws DataAccessException {
	 * return flightRepository.findFlightStatusTypes();
	 * }
	 */
	@Transactional(readOnly = true)
	public Flight findFlightById(final int id) throws DataAccessException {
		return this.flightRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void saveFlight(final Flight flight) throws DataAccessException, DuplicatedPetNameException {
		/*
		 * Pet otherPet=flight.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
		 * if (StringUtils.hasLength(pet.getName()) && (otherPet!= null && otherPet.getId()!=pet.getId())) {
		 * throw new DuplicatedPetNameException();
		 * }else
		 */ this.flightRepository.save(flight);
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
	public Collection<Flight> findAirlineFlight(String username) {
		return this.springDataFlightRepository.findAirlineFlight(username);
	}

}
